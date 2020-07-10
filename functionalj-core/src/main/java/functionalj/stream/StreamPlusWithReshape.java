// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.stream;

import static functionalj.function.Func.f;
import static functionalj.stream.StreamPlus.empty;
import static functionalj.stream.StreamPlus.from;
import static functionalj.stream.StreamPlusHelper.sequential;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.result.NoMoreResultException;
import lombok.val;

public interface StreamPlusWithReshape<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /**
     * Segment the stream into sub stream with the fix length of count. The last portion may be shorter.
     * 
     * @param count  the element count of the sub stream.
     * @return       the stream of sub stream.
     */
    public default StreamPlus<StreamPlus<DATA>> segment(int count) {
        return segment(count, true);
    }
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * Depending on the includeTail flag, the last sub stream may not be included if its length is not `count`.
     * 
     * @param count        the element count of the sub stream.
     * @param includeTail  the flag indicating if the last sub stream that does not have count element is to be included 
     *                       as opposed to thrown away.
     * @return             the stream of sub stream.
     */
    public default StreamPlus<StreamPlus<DATA>> segment(int count, boolean includeTail) {
        val index = new AtomicInteger(0);
        return segment(data -> {
                    val currentIndex = index.getAndIncrement();
                    return (currentIndex % count) == 0;
                },
                includeTail);
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     * 
     * @param startCondition  the start new segment condition.
     * @return                the stream of sub stream.
     */
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<? super DATA> startCondition) {
        return segment(startCondition, true);
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * 
     * @param startCondition            the start new segment condition.
     * @param includeIncompletedSegment the flag to include the last incomplete segment.
     * @return                          the stream of sub stream.
     */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            boolean                 includeIncompletedSegment) {
        val streamPlus = streamPlus();
        return sequential(streamPlus, stream -> {
            // TODO - Find a way to make it fully lazy. Try tryAdvance or iterator.
            val list = new AtomicReference<>(new ArrayList<DATA>());
            val adding = new AtomicBoolean(false);
            
            val streamOrNull = (Function<DATA, StreamPlus<DATA>>)((DATA data) ->{
                if (startCondition.test(data)) {
                    adding.set(true);
                    val retList = list.getAndUpdate(l -> new ArrayList<DATA>());
                    list.get().add(data);
                    return retList.isEmpty()
                            ? null
                            : StreamPlus.from(retList.stream());
                }
                if (adding.get()) {
                    list.get().add(data);
                }
                return null;
            });
            val mainStream = StreamPlus.from(stream.mapToObj(streamOrNull)).filterNonNull();
            if (!includeIncompletedSegment)
                return mainStream;
            
            val mainSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->mainStream;
            val tailSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->{
                return StreamPlus.of(from(list.get().stream()));
            };
            val resultStream
                    = StreamPlus.of(mainSupplier, tailSupplier)
                    .flatMap(Supplier::get);
            
            resultStream
            .onClose(()->stream.close());
            
            return resultStream;
        });
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true.
     * The tail sub stream will always be included.
     * 
     * @param startCondition  the start new segment condition.
     * @param endCondition    the end segment condition.
     * @return                the stream of sub stream.
     */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            Predicate<? super DATA> endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true.
     * 
     * @param startCondition            the start new segment condition.
     * @param includeIncompletedSegment the flag to include the last incomplete segment.
     * @return                          the stream of sub stream.
     */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            Predicate<? super DATA> endCondition,
            boolean                 includeIncompletedSegment) {
        val streamPlus = streamPlus();
        return sequential(streamPlus, stream -> {
         // TODO - Find a way to make it fully lazy. Try tryAdvance.
            val list = new AtomicReference<>(new ArrayList<DATA>());
            val adding = new AtomicBoolean(false);
            
            StreamPlus<StreamPlus<DATA>> resultStream 
                = StreamPlus.from(
                    stream
                    .mapToObj(i -> {
                        val shouldStart = startCondition.test(i);
                        if (shouldStart) {
                            adding.set(true);
                        }
                        if (includeIncompletedSegment && adding.get()) {
                            list.get().add(i);
                        }
                        
                        if (endCondition.test(i)) {
                            adding.set(shouldStart);
                            val retList = list.getAndUpdate(l -> new ArrayList<DATA>());
                            return StreamPlus.from(retList.stream());
                        }
                        
                        if (!includeIncompletedSegment && adding.get()) {
                            list.get().add(i);
                        }
                        return null;
                    }))
                    .filterNonNull();
            
            resultStream
            .onClose(()->stream.close());
            
            return resultStream;
        });
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     * 
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     * 
     * @param segmentSize  the function to determine the size of the next segment.
     * @return             the stream of sub stream.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<StreamPlus<DATA>> segmentSize(Func1<? super DATA, Integer> segmentSize) {
        val streamPlus = streamPlus();
        return sequential(streamPlus, stream -> {
            val listRef = new AtomicReference<List<DATA>>(new ArrayList<DATA>());
            val leftRef = new AtomicInteger(-1);
            
            val head 
                = stream
                .map(each -> {
                    int left = leftRef.get();
                    if (left == -1) {
                        Integer newSize = segmentSize.apply(each);
                        if ((newSize == null) || (newSize == 0)) {
                            return null;
                        } else if (newSize == 0) {
                            return StreamPlus.empty();
                        } else if (newSize == 1) {
                            return StreamPlus.of(each);
                        } else {
                            val list = listRef.get();
                            list.add(each);
                            leftRef.set(newSize - 1);
                        }
                    } else if (left == 1) {
                        val list = listRef.getAndSet(new ArrayList<DATA>());
                        list.add(each);
                        
                        leftRef.set(-1);
                        return StreamPlus.from(list.stream());
                        
                    } else {
                        val list = listRef.get();
                        list.add(each);
                        leftRef.decrementAndGet();
                    }
                    return null;
                })
                .filterNonNull()
                ;
            
            StreamPlus<StreamPlus<DATA>> resultStream 
                = (StreamPlus<StreamPlus<DATA>>)StreamPlus.of(
                    f(()-> head),
                    f(()-> StreamPlus.of(StreamPlus.from(listRef.get().stream())))
                )
                .flatMap(s -> (StreamPlus<DATA>)s.get());
            
            resultStream
            .onClose(()->stream.close());
            
            return resultStream;
        });
    }
    /**
     * Combine the current value with the one before it using then combinator everytime the condition to collapse is true.
     * 
     * @param conditionToCollapse  the condition.
     * @param combinator           the combinator.
     * @return                     the combined result stream.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapseWhen(
            Predicate<? super DATA>                 conditionToCollapse,
            Func2<? super DATA, ? super DATA, DATA> combinator) {
        val iterator = streamPlus().iterator();
        DATA first = null;
        
        if (!iterator.hasNext()) {
            return empty();
        }
        try {
            first = iterator.next();
        } catch (NoSuchElementException e) {
            return empty();
        }
        
        val prev = new AtomicReference<Object>(first);
        StreamPlus<DATA> resultStream = StreamPlus.generateWith(()->{
            if (prev.get() == StreamPlusHelper.dummy)
                throw new NoMoreResultException();
            
            while(true) {
                DATA next;
                if (!iterator.hasNext()) {
                    val yield = prev.get();
                    prev.set(StreamPlusHelper.dummy);
                    return (DATA)yield;
                }
                
                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    val yield = prev.get();
                    prev.set(StreamPlusHelper.dummy);
                    return (DATA)yield;
                }
                if (conditionToCollapse.test(next)) {
                    prev.set(combinator.apply((DATA)prev.get(), next));
                } else {
                    val yield = prev.get();
                    prev.set(next);
                    return (DATA)yield;
                }
            }
        });
        
        return resultStream;
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * 
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     * 
     * @param segmentSize  the function to determine the size of the next segment.
     * @return             the stream of sub stream.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapseSize(
            Func1<? super DATA, Integer>            segmentSize, 
            Func2<? super DATA, ? super DATA, DATA> combinator) {
        val firstObj = new Object();
        val iterator = streamPlus().iterator();
        val prev = new AtomicReference<Object>(firstObj);
        StreamPlus<DATA> resultStream = StreamPlus.generateWith(()->{
            if (prev.get() == StreamPlusHelper.dummy)
                throw new NoMoreResultException();
            
            while(true) {
                DATA next;
                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    if (prev.get() == firstObj)
                        throw new NoMoreResultException();
                    
                    val yield = prev.get();
                    prev.set(StreamPlusHelper.dummy);
                    return (DATA)yield;
                }
                
                Integer newSize = segmentSize.apply(next);
                if ((newSize == null) || (newSize == 0)) {
                    continue;
                }
                
                if (newSize == 1) {
                    return (DATA)next;
                }
                
                prev.set(next);
                for (int i = 0; i < (newSize - 1); i++) {
                    try {
                        next = iterator.next();
                        prev.set(combinator.apply((DATA)prev.get(), next));
                    } catch (NoSuchElementException e) {
                        val yield = prev.get();
                        prev.set(StreamPlusHelper.dummy);
                        return (DATA)yield;
                    }
                }
                
                val yield = prev.get();
                prev.set(firstObj);
                return (DATA)yield;
            }
        });
        
        return resultStream;
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     * 
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     * 
     * @param segmentSize  the function to determine the size of the next segment.
     * @param mapper       the mapper.
     * @return             the stream of sub stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> collapseSize(
            Func1<DATA, Integer>          segmentSize, 
            Func1<DATA, TARGET>           mapper, 
            Func2<TARGET, TARGET, TARGET> combinator) {
        val firstObj = new Object();
        val iterator = streamPlus().iterator();
        val prev = new AtomicReference<Object>(firstObj);
        StreamPlus<TARGET> resultStream = StreamPlus.generateWith(()->{
            if (prev.get() == StreamPlusHelper.dummy)
                throw new NoMoreResultException();
            
            while(true) {
                DATA next;
                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    if (prev.get() == firstObj)
                        throw new NoMoreResultException();
                    
                    val yield = prev.get();
                    prev.set(StreamPlusHelper.dummy);
                    return (TARGET)yield;
                }
                
                Integer newSize = segmentSize.apply(next);
                if ((newSize == null) || (newSize == 0)) {
                    continue;
                }
                
                if (newSize == 1) {
                    val target = (TARGET)mapper.apply((DATA)next);
                    return target;
                }
                
                TARGET target = (TARGET)mapper.apply((DATA)next);
                prev.set(target);
                for (int i = 0; i < (newSize - 1); i++) {
                    try {
                        next   = iterator.next();
                        target = (TARGET)mapper.apply((DATA)next);
                        val prevValue = (TARGET)prev.get();
                        val newValue  = combinator.apply(prevValue, target);
                        prev.set(newValue);
                    } catch (NoSuchElementException e) {
                        val yield = prev.get();
                        prev.set(StreamPlusHelper.dummy);
                        return (TARGET)yield;
                    }
                }
                
                val yield = prev.get();
                prev.set(firstObj);
                return (TARGET)yield;
            }
        });
        
        return resultStream;
    }
}