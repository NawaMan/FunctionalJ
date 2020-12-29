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
import static functionalj.stream.StreamPlus.generateWith;
import static functionalj.stream.StreamPlus.streamOf;
import static functionalj.stream.StreamPlusHelper.sequentialToObj;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.result.NoMoreResultException;
import lombok.val;


public interface StreamPlusWithReshape<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * The last portion may be shorter.
     **/
    public default StreamPlus<StreamPlus<DATA>> segmentSize(int count) {
        return segmentSize(count, true);
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
    public default StreamPlus<StreamPlus<DATA>> segmentSize(int count, boolean includeTail) {
        val index = new AtomicInteger(0);
        return segment(data -> {
                    val currentIndex = index.getAndIncrement();
                    return (currentIndex % count) == 0;
                },
                includeTail);
    }
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * Depending on the includeTail flag, the last sub stream may not be included if its length is not `count`.
     *
     * @param count        the element count of the sub stream.
     * @param includeTail  the option indicating if the last sub stream that does not have count element is to be included
     *                       as opposed to thrown away.
     * @return             the stream of sub stream.
     */
    public default StreamPlus<StreamPlus<DATA>> segmentSize(int count, IncompletedSegment incompletedSegment) {
        return segmentSize(count, (incompletedSegment == IncompletedSegment.included));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<? super DATA> startCondition) {
        return segment(startCondition, true);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<? super DATA> startCondition, IncompletedSegment incompletedSegment) {
        return segment(startCondition, (incompletedSegment == IncompletedSegment.included));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            boolean                 includeIncompletedSegment) {
        // TODO - Find a way to make it fully lazy. Try tryAdvance or iterator.
        val newStorage = (Supplier<ArrayList<DATA>>)ArrayList::new;
        val toStreamPlus = Func1.<ArrayList<DATA>, Stream<DATA>>of(ArrayList::stream).then(StreamPlus::from);
        
        val streamPlus = streamPlus();
        return sequentialToObj(streamPlus, stream -> {
            val list = new AtomicReference<>(newStorage.get());
            val adding = new AtomicBoolean(false);
            
            val streamOrNull = (Function<DATA, StreamPlus<DATA>>)((DATA data) ->{
                if (startCondition.test(data)) {
                    adding.set(true);
                    val resultList = list.getAndUpdate(l -> newStorage.get());
                    list.get().add(data);
                    return resultList.isEmpty() ? null : toStreamPlus.apply(resultList);
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
            val tailSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->StreamPlus.of(toStreamPlus.apply(list.get()));
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
     */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            Predicate<? super DATA> endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            Predicate<? super DATA> endCondition,
            IncompletedSegment incompletedSegment) {
        val includeIncompletedSegment = incompletedSegment == IncompletedSegment.included;
        return segment(startCondition, endCondition, includeIncompletedSegment);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            Predicate<? super DATA> endCondition,
            boolean                 includeIncompletedSegment) {
        val newStorage   = (Supplier<ArrayList<DATA>>)ArrayList::new;
        val toStreamPlus = Func1.<ArrayList<DATA>, Stream<DATA>>of(ArrayList::stream).then(StreamPlus::from);
        val list         = new AtomicReference<ArrayList<DATA>>(null);
        
        val streamPlus = streamPlus();
        val head = (Supplier<StreamPlus<StreamPlus<DATA>>>)() -> {
            return sequentialToObj(streamPlus, stream -> {
                return stream.mapToObj(i -> {
                    boolean canStart = startCondition.test(i);
                    boolean canEnd   = endCondition.test(i);
                    val theList = list.get();
                    
                    // Add if the list is already there
                    if (!canStart && theList != null) {
                        theList.add(i);
                    }
                    // If end, remove list.
                    if (canEnd && (theList != null)) {
                        list.set(null);
                    }
                    // If start added list.
                    if (canStart && (theList == null)) {
                        list.set(newStorage.get());
                    }
                    // If start, make sure to add this one.
                    if (canStart) {
                        list.get().add(i);
                    }
                    
                    if (!canEnd || (theList == null))
                        return null;
                    
                    return toStreamPlus.apply(theList);
                })
                .filterNonNull();
            });
        };
        val tail = (Supplier<StreamPlus<StreamPlus<DATA>>>)() -> {
            val theList = list.get();
            return (includeIncompletedSegment && (theList != null) && !theList.isEmpty())
                    ? streamOf(toStreamPlus.apply(theList))
                    : null;
        };
        
        return streamOf(head, tail)
                .flatMap(supplier -> supplier.get())
                .filterNonNull();
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     */
    public default StreamPlus<StreamPlus<DATA>> segmentSize(Func1<? super DATA, Integer> segmentSize) {
        val newStorage   = (Supplier<ArrayList<DATA>>)ArrayList::new;
        val toStreamPlus = Func1.<ArrayList<DATA>, Stream<DATA>>of(ArrayList::stream).then(StreamPlus::from);
        val emptyStream  = (Supplier<StreamPlus<DATA>>)StreamPlus::empty;
        val singleStream = (Function<DATA, StreamPlus<DATA>>)(data -> StreamPlus.of(data));
        
        // TODO - Find a way to make it fully lazy. Try tryAdvance.
        val streamPlus = streamPlus();
        return sequentialToObj(streamPlus, stream -> {
            val listRef = new AtomicReference<>(newStorage.get());
            val leftRef = new AtomicInteger(-1);
            
            val head
                = stream
                .mapToObj(each -> {
                    int left = leftRef.get();
                    if (left == -1) {
                        val newSize = segmentSize.apply(each);
                        if (newSize == null) {
                            return null;
                        } else if (newSize == 0) {
                            return emptyStream.get();
                        } else if (newSize == 1) {
                            return singleStream.apply(each);
                        } else {
                            val list = listRef.get();
                            list.add(each);
                            leftRef.set(newSize - 1);
                        }
                    } else if (left == 1) {
                        val list = listRef.getAndSet(newStorage.get());
                        list.add(each);
                        
                        leftRef.set(-1);
                        return toStreamPlus.apply(list);
                        
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
                = StreamPlus.of(
                    f(()-> head),
                    f(()-> StreamPlus.of(toStreamPlus.apply(listRef.get())))
                )
                .flatMap(s -> s.get());
            
            resultStream
            .onClose(()->stream.close());
            
            return resultStream;
        });
    }
    
    /** Combine the current value with the one before it using then combinator everytime the condition to collapse is true. */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapseWhen(
            Predicate<? super DATA>                 conditionToCollapse,
            Func2<? super DATA, ? super DATA, DATA> combinator) {
        Object dummy = StreamPlusHelper.dummy;
        DATA first = null;
        
        val iterator = streamPlus().iterator();
        
        if (!iterator.hasNext()) {
            return empty();
        }
        try {
            first = iterator.next();
        } catch (NoSuchElementException e) {
            return empty();
        }
        
        val prev = new AtomicReference<Object>(first);
        StreamPlus<DATA> resultStream = generateWith(()->{
            if (prev.get() == dummy)
                throw new NoMoreResultException();
            
            while(true) {
                DATA next;
                val prevValue = (DATA)prev.get();
                if (!iterator.hasNext()) {
                    val yield = prevValue;
                    prev.set(dummy);
                    return yield;
                }
                
                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    val yield = prevValue;
                    prev.set(dummy);
                    return yield;
                }
                if (conditionToCollapse.test(next)) {
                    val newValue = combinator.apply(prevValue, next);
                    prev.set(newValue);
                } else {
                    val yield = prevValue;
                    prev.set(next);
                    return yield;
                }
            }
        });
        
        return resultStream;
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapseSize(
            Func1<? super DATA, Integer>            segmentSize,
            Func2<? super DATA, ? super DATA, DATA> combinator) {
        Object dummy = StreamPlusHelper.dummy;
        
        val firstObj = new Object();
        val iterator = streamPlus().iterator();
        val prev = new AtomicReference<Object>(firstObj);
        StreamPlus<DATA> resultStream = generateWith(()->{
            if (prev.get() == dummy)
                throw new NoMoreResultException();
            
            while(true) {
                DATA next;
                Object prevValue = prev.get();
                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    if (prevValue == firstObj)
                        throw new NoMoreResultException();
                    
                    val yield = (DATA)prev.get();
                    prev.set(dummy);
                    return yield;
                }
                
                Integer newSize = segmentSize.apply(next);
                if ((newSize == null) || (newSize == 0)) {
                    continue;
                }
                
                if (newSize == 1) {
                    return next;
                }
                
                prev.set(next);
                for (int i = 0; i < (newSize - 1); i++) {
                    try {
                        next = iterator.next();
                        DATA newValue = combinator.apply((DATA)prev.get(), next);
                        prev.set(newValue);
                    } catch (NoSuchElementException e) {
                        val yield = prev.get();
                        prev.set(dummy);
                        return (DATA)yield;
                    }
                }
                
                val yield = (DATA)prev.get();
                prev.set(firstObj);
                return yield;
            }
        });
        
        return resultStream;
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> collapseSize(
            Func1<DATA, Integer>          segmentSize,
            Func1<DATA, TARGET>           mapper,
            Func2<TARGET, TARGET, TARGET> combinator) {
        val dummy = StreamPlusHelper.dummy;
        
        val firstObj = new Object();
        val iterator = streamPlus().iterator();
        val prev = new AtomicReference<Object>(firstObj);
        StreamPlus<TARGET> resultStream = StreamPlus.generateWith(()->{
            if (prev.get() == dummy)
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
