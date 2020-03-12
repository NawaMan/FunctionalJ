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
package functionalj.stream.intstream;

import static functionalj.function.Func.f;

import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import functionalj.stream.GrowOnlyIntArray;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.IntIteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;

interface IntAdvancer {
    boolean advance(IntConsumer intConsumer, int i);
}

class IntStreamPlusWithSegmentHelper {
    
    static final Object dummy = new Object();
    
    static final int[] dummyArray = new int[0];
    
}

// TODO - Segment and Collapse should mirror each other
//        Umm ... not sure should rethink

public interface IntStreamPlusWithSegment {
    
    public     IntStreamPlus useIterator     (Function<IntIteratorPlus, IntStreamPlus> action);
    public <T> StreamPlus<T> useIteratorToObj(Function<IntIteratorPlus, StreamPlus<T>> action);
    public <T> StreamPlus<T> sequentialToObj (Function<IntStreamPlus,   StreamPlus<T>> action);
    public     IntStreamPlus sequential      (Function<IntStreamPlus,   IntStreamPlus> action);
    
    public IntStream intStream();
    
    public void close();
    
    
    //== segment ==
    
    public default StreamPlus<IntStreamPlus> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<IntStreamPlus> segment(int count, IncompletedSegment incompletedSegment) {
        return segment(count, (incompletedSegment == IncompletedSegment.included));
    }
    public default StreamPlus<IntStreamPlus> segment(int count, boolean includeIncompletedSegment) {
        val index = new AtomicInteger(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeIncompletedSegment);
    }
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IncompletedSegment incompletedSegment) {
        return segment(startCondition, (incompletedSegment == IncompletedSegment.included));
    }
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, boolean includeIncompletedSegment) {
        return sequentialToObj(stream -> {
            // TODO - Find a way to make it fully lazy. Try tryAdvance.
            val list = new AtomicReference<>(new GrowOnlyIntArray());
            val adding = new AtomicBoolean(false);
            
            val streamOrNull = (IntFunction<IntStreamPlus>)((int data) ->{
                if (startCondition.test(data)) {
                    adding.set(true);
                    val retList = list.getAndUpdate(l -> new GrowOnlyIntArray());
                    list.get().add(data);
                    return retList.isEmpty()
                            ? null
                            : IntStreamPlus.from(retList.stream());
                }
                if (adding.get()) 
                    list.get().add(data);
                return null;
            });
            val mainStream = StreamPlus.from(stream.mapToObj(streamOrNull)).filterNonNull();
            if (!includeIncompletedSegment)
                return mainStream;
            
            val mainSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->mainStream;
            val tailSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->{
                return StreamPlus.of(
                        IntStreamPlus.from(
                                list
                                .get()
                                .stream())
                );
            };
            val resultStream
                    = StreamPlus.of(mainSupplier, tailSupplier)
                    .flatMap(Supplier::get);
            
            resultStream
            .onClose(()->IntStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition, IncompletedSegment incompletedSegment) {
        return segment(startCondition, endCondition, incompletedSegment == IncompletedSegment.included);
    }
    
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition, boolean includeIncompletedSegment) {
        return sequentialToObj(stream -> {
            // TODO - Find a way to make it fully lazy. Try tryAdvance.
            val list = new AtomicReference<>(new GrowOnlyIntArray());
            val adding = new AtomicBoolean(false);
            
            StreamPlus<IntStreamPlus> mainStream 
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
                            val retList = list.getAndUpdate(l -> new GrowOnlyIntArray());
                            return retList.stream();
                        }
                        return (IntStreamPlus)null;
                    })
                    .filterNonNull()
                );
            
            if (!includeIncompletedSegment)
                return mainStream;
            
            val mainSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->mainStream;
            val tailSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->{
                return StreamPlus.of(
                        IntStreamPlus.from(
                                list
                                .get()
                                .stream())
                );
            };
            val resultStream
                    = StreamPlus.of(mainSupplier, tailSupplier)
                    .flatMap(Supplier::get);
            
            resultStream
            .onClose(()->IntStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    public default StreamPlus<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize) {
        return segmentSize(segmentSize, true);
    }
    
    public default StreamPlus<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize, IncompletedSegment incompletedSegment) {
        return segmentSize(segmentSize, incompletedSegment == IncompletedSegment.included);
    }
    
    public default StreamPlus<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize, boolean includeTail) {
        return sequentialToObj(stream -> {
            // TODO - Find a way to make it fully lazy. Try tryAdvance.
            val listRef = new AtomicReference<>(new GrowOnlyIntArray());
            val leftRef = new AtomicInteger(-1);
            
            val head 
                = stream
                .mapToObj(each -> {
                    int left = leftRef.get();
                    if (left == -1) {
                        int newSize = segmentSize.applyAsInt(each);
                        if (newSize == 0) {
                            return IntStreamPlus.empty();
                        } else if (newSize == 1) {
                            return IntStreamPlus.of(each);
                        } else {
                            val list = listRef.get();
                            list.add(each);
                            leftRef.set(newSize - 1);
                        }
                    } else if (left == 1) {
                        val list = listRef.getAndSet(new GrowOnlyIntArray());
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
            
            StreamPlus<IntStreamPlus> resultStream 
                = (StreamPlus<IntStreamPlus>)StreamPlus.of(
                    f(()-> head),
                    f(()-> includeTail ? StreamPlus.of(listRef.get().stream()) : null)
                )
                .filterNonNull()
                .flatMap(s -> s.get());
            
            resultStream
            .onClose(()->IntStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    //== Collapse ==
    
    public default IntStreamPlus collapseWhen(IntPredicate conditionToCollapse, IntBinaryOperator concatFunc) {
        return useIterator(iterator -> {
            int first;
            try {
                first = iterator.next();
            } catch (NoSuchElementException e) {
                return IntStreamPlus.empty();
            }
            
            val prev = new int[][] { new int[] { first }};
            val isDone = new boolean[] { false };
            IntStreamPlus resultStream = IntStreamPlus.generate(()->{
                if (prev[0] == null) {
                    isDone[0] = true;
                    return Integer.MIN_VALUE;
                }
                
                while(true) {
                    int next;
                    try {
                        next = iterator.nextInt();
                    } catch (NoSuchElementException e) {
                        val yield = prev[0][0];
                        prev[0] = null;
                        return yield;
                    }
                    if (conditionToCollapse.test(next)) {
                        prev[0][0] = concatFunc.applyAsInt(prev[0][0], next);
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
    
    public default IntStreamPlus collapseAfter(IntPredicate conditionToCollapseNext, IntBinaryOperator concatFunc) {
        return useIterator(iterator -> {
            int first;
            try {
                first = iterator.next();
            } catch (NoSuchElementException e) {
                return IntStreamPlus.empty();
            }
            
            val prev = new int[][] { new int[] { first }};
            val isDone = new boolean[] { false };
            val collapseNext = new boolean[] { conditionToCollapseNext.test(first) };
            IntStreamPlus resultStream = IntStreamPlus.generate(()->{
                if (prev[0] == null) {
                    isDone[0] = true;
                    return Integer.MIN_VALUE;
                }
                
                while(true) {
                    int next;
                    try {
                        next = iterator.nextInt();
                    } catch (NoSuchElementException e) {
                        val yield = prev[0][0];
                        prev[0] = null;
                        return yield;
                    }
                    boolean collapseNow = collapseNext[0];
                    collapseNext[0] = conditionToCollapseNext.test(next);
                    if (collapseNow) {
                        prev[0][0] = concatFunc.applyAsInt(prev[0][0], next);
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
    
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc) {
        return collapseSize(segmentSize, concatFunc, true);
    }
    
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc,
            IncompletedSegment incompletedSegment) {
        return collapseSize(segmentSize, concatFunc, incompletedSegment == IncompletedSegment.included);
    }
    
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc,
            boolean includeTail) {
        return sequential(stream -> {
            val splitr = stream.spliterator();
            val value = new AtomicReference<AtomicInteger>(null);
            IntStreamPlus head = IntStreamPlus.from(StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    val count = new AtomicInteger(0);
                    val hasNext = new AtomicBoolean();
                    do {
                        hasNext.set(splitr.tryAdvance((int next) -> {
                            if (count.get() == 0) {
                                int newSize = segmentSize.applyAsInt(next);
                                if (newSize <= 0) {
                                    count.set(1);
                                    value.set(null);
                                } else {
                                    count.set(newSize);
                                    value.set(new AtomicInteger(next));
                                }
                            } else {
                                int newValue = concatFunc.applyAsInt(value.get().get(), next);
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
            
            IntStreamPlus tail = (includeTail && (value.get() != null))
                     ? IntStreamPlus.of(value.get().get()) 
                     : IntStreamPlus.empty();
            val resultStream 
                = StreamPlus.of(
                    f(()-> head),
                    f(()-> tail)
                )
                .map(each -> each.get())
                .filterNonNull()
                .reduce(IntStreamPlus::concat)
                .get();
            
            resultStream
            .onClose(()->{
                f(()->head.close()).runCarelessly();
                f(()->tail.close()).runCarelessly();
                IntStreamPlusWithSegment.this.close();
            });
            
            return resultStream;
        });
    }
}
