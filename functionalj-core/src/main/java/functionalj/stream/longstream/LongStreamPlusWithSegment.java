// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.longstream;

import static functionalj.function.Func.f;

import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import functionalj.stream.GrowOnlyLongArray;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.LongIteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;

interface LongAdvancer {
    boolean advance(IntConsumer intConsumer, int i);
}

class LongStreamPlusWithSegmentHelper {
    
    static final Object dummy = new Object();
    
    static final int[] dummyArray = new int[0];
    
}

// TODO - Segment and Collapse should mirror each other
//        Umm ... not sure should rethink

public interface LongStreamPlusWithSegment {
    
    public     LongStreamPlus useIterator     (Function<LongIteratorPlus, LongStreamPlus> action);
    public <T> StreamPlus<T>  useIteratorToObj(Function<LongIteratorPlus, StreamPlus<T>> action);
    public <T> StreamPlus<T>  sequentialToObj (Function<LongStreamPlus,   StreamPlus<T>> action);
    public     LongStreamPlus sequential      (Function<LongStreamPlus,   LongStreamPlus> action);
    
    public void close();
    
    
    //== segment ==
    
    public default StreamPlus<LongStreamPlus> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<LongStreamPlus> segment(int count, IncompletedSegment incompletedSegment) {
        return segment(count, (incompletedSegment == IncompletedSegment.included));
    }
    public default StreamPlus<LongStreamPlus> segment(int count, boolean includeIncompletedSegment) {
        val index = new AtomicInteger(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeIncompletedSegment);
    }
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, IncompletedSegment incompletedSegment) {
        return segment(startCondition, (incompletedSegment == IncompletedSegment.included));
    }
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, boolean includeIncompletedSegment) {
        return sequentialToObj(stream -> {
            // TODO - Find a way to make it fully lazy. Try tryAdvance.
            val list = new AtomicReference<>(new GrowOnlyLongArray());
            val adding = new AtomicBoolean(false);
            
            val streamOrNull = (LongFunction<LongStreamPlus>)((long data) ->{
                if (startCondition.test(data)) {
                    adding.set(true);
                    val retList = list.getAndUpdate(l -> new GrowOnlyLongArray());
                    list.get().add(data);
                    return retList.isEmpty()
                            ? null
                            : LongStreamPlus.from(retList.stream());
                }
                if (adding.get()) 
                    list.get().add(data);
                return null;
            });
            val mainStream = StreamPlus.from(stream.mapToObj(streamOrNull)).filterNonNull();
            if (!includeIncompletedSegment)
                return mainStream;
            
            val mainSupplier = (Supplier<StreamPlus<LongStreamPlus>>)()->mainStream;
            val tailSupplier = (Supplier<StreamPlus<LongStreamPlus>>)()->{
                return StreamPlus.of(
                        LongStreamPlus.from(
                                list
                                .get()
                                .stream())
                );
            };
            val resultStream
                    = StreamPlus.of(mainSupplier, tailSupplier)
                    .flatMap(Supplier::get);
            
            resultStream
            .onClose(()->LongStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, LongPredicate endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, LongPredicate endCondition, IncompletedSegment incompletedSegment) {
        return segment(startCondition, endCondition, incompletedSegment == IncompletedSegment.included);
    }
    
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, LongPredicate endCondition, boolean includeIncompletedSegment) {
        return sequentialToObj(stream -> {
            // TODO - Find a way to make it fully lazy. Try tryAdvance.
            val list = new AtomicReference<>(new GrowOnlyLongArray());
            val adding = new AtomicBoolean(false);
            
            StreamPlus<LongStreamPlus> mainStream 
                = StreamPlus.from(
                    stream
                    .mapToObj(i -> {
                        if (startCondition.test(i)) {
                            adding.set(true);
                        }
                        if (adding.get()) {
                            list.get().add(i);
                        }
                        
                        if (endCondition.test(i)) {
                            adding.set(false);
                            val retList = list.getAndUpdate(l -> new GrowOnlyLongArray());
                            return retList.stream();
                        }
                        return (LongStreamPlus)null;
                    })
                    .filterNonNull()
                );
            
            if (!includeIncompletedSegment)
                return mainStream;
            
            val mainSupplier = (Supplier<StreamPlus<LongStreamPlus>>)()->mainStream;
            val tailSupplier = (Supplier<StreamPlus<LongStreamPlus>>)()->{
                return StreamPlus.of(
                        LongStreamPlus.from(
                                list
                                .get()
                                .stream())
                );
            };
            val resultStream
                    = StreamPlus.of(mainSupplier, tailSupplier)
                    .flatMap(Supplier::get);
            
            resultStream
            .onClose(()->LongStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    public default StreamPlus<LongStreamPlus> segmentSize(LongToIntFunction segmentSize) {
        return segmentSize(segmentSize, true);
    }
    
    public default StreamPlus<LongStreamPlus> segmentSize(LongToIntFunction segmentSize, IncompletedSegment incompletedSegment) {
        return segmentSize(segmentSize, incompletedSegment == IncompletedSegment.included);
    }
    
    public default StreamPlus<LongStreamPlus> segmentSize(LongToIntFunction segmentSize, boolean includeTail) {
        return sequentialToObj(stream -> {
            // TODO - Find a way to make it fully lazy. Try tryAdvance.
            val listRef = new AtomicReference<>(new GrowOnlyLongArray());
            val leftRef = new AtomicLong(-1);
            
            val head 
                = stream
                .mapToObj(each -> {
                    long left = leftRef.get();
                    if (left == -1) {
                        int newSize = segmentSize.applyAsInt(each);
                        if (newSize == 0) {
                            return LongStreamPlus.empty();
                        } else if (newSize == 1) {
                            return LongStreamPlus.of(each);
                        } else {
                            val list = listRef.get();
                            list.add(each);
                            leftRef.set(newSize - 1);
                        }
                    } else if (left == 1) {
                        val list = listRef.getAndSet(new GrowOnlyLongArray());
                        list.add(each);
                        
                        leftRef.set(-1);
                        return list.stream();
                    } else {
                        val list = listRef.get();
                        list.add(each);
                        leftRef.decrementAndGet();
                    }
                    return null;
                })
                .filterNonNull()
                ;
            
            StreamPlus<LongStreamPlus> resultStream 
                = (StreamPlus<LongStreamPlus>)StreamPlus.of(
                    f(()-> head),
                    f(()-> includeTail ? StreamPlus.of(listRef.get().stream()) : null)
                )
                .filterNonNull()
                .flatMap(s -> s.get());
            
            resultStream
            .onClose(()->LongStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    //== Collapse ==
    
    public default LongStreamPlus collapseWhen(LongPredicate conditionToCollapse, LongBinaryOperator concatFunc) {
        return useIterator(iterator -> {
            long first;
            try {
                first = iterator.next();
            } catch (NoSuchElementException e) {
                return LongStreamPlus.empty();
            }
            
            val prev = new long[][] { new long[] { first }};
            val isDone = new boolean[] { false };
            LongStreamPlus resultStream = LongStreamPlus.generate(()->{
                if (prev[0] == null) {
                    isDone[0] = true;
                    return Integer.MIN_VALUE;
                }
                
                while(true) {
                    long next;
                    try {
                        next = iterator.nextLong();
                    } catch (NoSuchElementException e) {
                        val yield = prev[0][0];
                        prev[0] = null;
                        return yield;
                    }
                    if (conditionToCollapse.test(next)) {
                        prev[0][0] = concatFunc.applyAsLong(prev[0][0], next);
                    } else {
                        val yield = prev[0][0];
                        prev[0][0] = next;
                        return yield;
                    }
                }
            })
            .takeUntil(i -> isDone[0]);
            
            return resultStream;
        });
    }
    
    public default LongStreamPlus collapseAfter(LongPredicate conditionToCollapseNext, LongBinaryOperator concatFunc) {
        return useIterator(iterator -> {
            long first;
            try {
                first = iterator.next();
            } catch (NoSuchElementException e) {
                return LongStreamPlus.empty();
            }
            
            val prev = new long[][] { new long[] { first }};
            val isDone = new boolean[] { false };
            val collapseNext = new boolean[] { conditionToCollapseNext.test(first) };
            LongStreamPlus resultStream = LongStreamPlus.generate(()->{
                if (prev[0] == null) {
                    isDone[0] = true;
                    return Integer.MIN_VALUE;
                }
                
                while(true) {
                    long next;
                    try {
                        next = iterator.nextLong();
                    } catch (NoSuchElementException e) {
                        val yield = prev[0][0];
                        prev[0] = null;
                        return yield;
                    }
                    boolean collapseNow = collapseNext[0];
                    collapseNext[0] = conditionToCollapseNext.test(next);
                    if (collapseNow) {
                        prev[0][0] = concatFunc.applyAsLong(prev[0][0], next);
                    } else {
                        val yield = prev[0][0];
                        prev[0][0] = next;
                        return yield;
                    }
                }
            })
            .takeUntil(i -> isDone[0]);
            
            return resultStream;
        });
    }
    
    public default LongStreamPlus collapseSize(
            LongUnaryOperator  segmentSize, 
            LongBinaryOperator concatFunc) {
        return collapseSize(segmentSize, concatFunc, true);
    }
    
    public default LongStreamPlus collapseSize(
            LongUnaryOperator  segmentSize, 
            LongBinaryOperator concatFunc,
            IncompletedSegment incompletedSegment) {
        return collapseSize(segmentSize, concatFunc, incompletedSegment == IncompletedSegment.included);
    }
    
    public default LongStreamPlus collapseSize(
            LongUnaryOperator  segmentSize, 
            LongBinaryOperator concatFunc,
            boolean includeTail) {
        return sequential(stream -> {
            val splitr = stream.spliterator();
            val value = new AtomicReference<AtomicLong>(null);
            LongStreamPlus head = LongStreamPlus.from(StreamSupport.longStream(new Spliterators.AbstractLongSpliterator(splitr.estimateSize(), 0) {
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    val count = new AtomicLong(0);
                    val hasNext = new AtomicBoolean();
                    do {
                        hasNext.set(splitr.tryAdvance((long next) -> {
                            if (count.get() == 0) {
                                long newSize = segmentSize.applyAsLong(next);
                                if (newSize <= 0) {
                                    count.set(1);
                                    value.set(null);
                                } else {
                                    count.set(newSize);
                                    value.set(new AtomicLong(next));
                                }
                            } else {
                                long newValue = concatFunc.applyAsLong(value.get().get(), next);
                                value.get().set(newValue);
                            }
                        }));
                    } while(count.decrementAndGet() > 0);
                    
                    if ((value.get() != null) && (hasNext.get() || includeTail)) {
                        consumer.accept(value.get().get());
                        count.set(0);
                        value.set(null);
                    }
                    
                    return hasNext.get();
                }
            }, false));
            
            LongStreamPlus tail = (includeTail && (value.get() != null))
                     ? LongStreamPlus.of(value.get().get()) 
                     : LongStreamPlus.empty();
            val resultStream 
                = StreamPlus.of(
                    f(()-> head),
                    f(()-> tail)
                )
                .map(each -> each.get())
                .filterNonNull()
                .reduce(LongStreamPlus::concat)
                .get();
            
            resultStream
            .onClose(()->{
                f(()->head.close()).runCarelessly();
                f(()->tail.close()).runCarelessly();
                LongStreamPlusWithSegment.this.close();
            });
            
            return resultStream;
        });
    }
}
