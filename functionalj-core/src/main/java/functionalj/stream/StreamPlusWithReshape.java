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
        var index = new AtomicInteger(0);
        return segment(data -> {
                    var currentIndex = index.getAndIncrement();
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
        var newStorage = (Supplier<ArrayList<DATA>>)ArrayList::new;
        var toStreamPlus = Func1.<ArrayList<DATA>, Stream<DATA>>of(ArrayList::stream).then(StreamPlus::from);
        
        var streamPlus = streamPlus();
        return sequentialToObj(streamPlus, stream -> {
            var list = new AtomicReference<>(newStorage.get());
            var adding = new AtomicBoolean(false);
            
            var streamOrNull = (Function<DATA, StreamPlus<DATA>>)((DATA data) ->{
                if (startCondition.test(data)) {
                    adding.set(true);
                    var resultList = list.getAndUpdate(l -> newStorage.get());
                    list.get().add(data);
                    return resultList.isEmpty() ? null : toStreamPlus.apply(resultList);
                }
                if (adding.get()) {
                    list.get().add(data);
                }
                return null;
            });
            var mainStream = StreamPlus.from(stream.mapToObj(streamOrNull)).filterNonNull();
            if (!includeIncompletedSegment)
                return mainStream;
            
            var mainSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->mainStream;
            var tailSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->StreamPlus.of(toStreamPlus.apply(list.get()));
            var resultStream
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
        var includeIncompletedSegment = incompletedSegment == IncompletedSegment.included;
        return segment(startCondition, endCondition, includeIncompletedSegment);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<StreamPlus<DATA>> segment(
            Predicate<? super DATA> startCondition,
            Predicate<? super DATA> endCondition,
            boolean                 includeIncompletedSegment) {
        var newStorage   = (Supplier<ArrayList<DATA>>)ArrayList::new;
        var toStreamPlus = Func1.<ArrayList<DATA>, Stream<DATA>>of(ArrayList::stream).then(StreamPlus::from);
        
        // TODO - Find a way to make it fully lazy. Try tryAdvance.
        var streamPlus = streamPlus();
        StreamPlus<StreamPlus<DATA>> returnStream = sequentialToObj(streamPlus, stream -> {
            var list         = new AtomicReference<>(newStorage.get());
            var adding       = new AtomicBoolean(false);
            
            StreamPlus<StreamPlus<DATA>> resultStream 
                = StreamPlus.from(
                    stream
                    .mapToObj(i -> {
                        var shouldStart = startCondition.test(i);
                        if (shouldStart) {
                            adding.set(true);
                        }
                        if (includeIncompletedSegment && adding.get()) {
                            list.get().add(i);
                        }
                        
                        if (endCondition.test(i)) {
                            adding.set(shouldStart);
                            var resultList = list.getAndUpdate(l -> newStorage.get());
                            return toStreamPlus.apply(resultList);
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
        return returnStream;
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     * 
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     */
    public default StreamPlus<StreamPlus<DATA>> segmentSize(Func1<? super DATA, Integer> segmentSize) {
        var newStorage   = (Supplier<ArrayList<DATA>>)ArrayList::new;
        var toStreamPlus = Func1.<ArrayList<DATA>, Stream<DATA>>of(ArrayList::stream).then(StreamPlus::from);
        var emptyStream  = (Supplier<StreamPlus<DATA>>)StreamPlus::empty;
        var singleStream = (Function<DATA, StreamPlus<DATA>>)(data -> StreamPlus.of(data));
        
        // TODO - Find a way to make it fully lazy. Try tryAdvance.
        var streamPlus = streamPlus();
        return sequentialToObj(streamPlus, stream -> {
            var listRef = new AtomicReference<>(newStorage.get());
            var leftRef = new AtomicInteger(-1);
            
            var head 
                = stream
                .mapToObj(each -> {
                    int left = leftRef.get();
                    if (left == -1) {
                        var newSize = segmentSize.apply(each);
                        if (newSize == null) {
                            return null;
                        } else if (newSize == 0) {
                            return emptyStream.get();
                        } else if (newSize == 1) {
                            return singleStream.apply(each);
                        } else {
                            var list = listRef.get();
                            list.add(each);
                            leftRef.set(newSize - 1);
                        }
                    } else if (left == 1) {
                        var list = listRef.getAndSet(newStorage.get());
                        list.add(each);
                        
                        leftRef.set(-1);
                        return toStreamPlus.apply(list);
                        
                    } else {
                        var list = listRef.get();
                        list.add(each);
                        leftRef.decrementAndGet();
                    }
                    return null;
                })
                .filterNonNull()
                ;
            
            var resultStream 
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
        
        var iterator = streamPlus().iterator();
        
        if (!iterator.hasNext()) {
            return empty();
        }
        try {
            first = iterator.next();
        } catch (NoSuchElementException e) {
            return empty();
        }
        
        var prev = new AtomicReference<Object>(first);
        StreamPlus<DATA> resultStream = generateWith(()->{
            if (prev.get() == dummy)
                throw new NoMoreResultException();
            
            while(true) {
                DATA next;
                var prevValue = (DATA)prev.get();
                if (!iterator.hasNext()) {
                    var yield = prevValue;
                    prev.set(dummy);
                    return yield;
                }
                
                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    var yield = prevValue;
                    prev.set(dummy);
                    return yield;
                }
                if (conditionToCollapse.test(next)) {
                    var newValue = combinator.apply(prevValue, next);
                    prev.set(newValue);
                } else {
                    var yield = prevValue;
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
        
        var firstObj = new Object();
        var iterator = streamPlus().iterator();
        var prev = new AtomicReference<Object>(firstObj);
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
                    
                    var yield = (DATA)prev.get();
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
                        var yield = prev.get();
                        prev.set(dummy);
                        return (DATA)yield;
                    }
                }
                
                var yield = (DATA)prev.get();
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
        var dummy = StreamPlusHelper.dummy;
        
        var firstObj = new Object();
        var iterator = streamPlus().iterator();
        var prev = new AtomicReference<Object>(firstObj);
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
                    
                    var yield = prev.get();
                    prev.set(StreamPlusHelper.dummy);
                    return (TARGET)yield;
                }
                
                Integer newSize = segmentSize.apply(next);
                if ((newSize == null) || (newSize == 0)) {
                    continue;
                }
                
                if (newSize == 1) {
                    var target = (TARGET)mapper.apply((DATA)next);
                    return target;
                }
                
                TARGET target = (TARGET)mapper.apply((DATA)next);
                prev.set(target);
                for (int i = 0; i < (newSize - 1); i++) {
                    try {
                        next   = iterator.next();
                        target = (TARGET)mapper.apply((DATA)next);
                        var prevValue = (TARGET)prev.get();
                        var newValue  = combinator.apply(prevValue, target);
                        prev.set(newValue);
                    } catch (NoSuchElementException e) {
                        var yield = prev.get();
                        prev.set(StreamPlusHelper.dummy);
                        return (TARGET)yield;
                    }
                }
                
                var yield = prev.get();
                prev.set(firstObj);
                return (TARGET)yield;
            }
        });
        
        return resultStream;
    }
    
}
