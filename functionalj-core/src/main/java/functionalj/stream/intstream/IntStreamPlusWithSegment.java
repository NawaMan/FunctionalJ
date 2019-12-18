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

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.result.NoMoreResultException;
import functionalj.stream.GrowOnlyIntArray;
import functionalj.stream.IntIteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;

public interface IntStreamPlusWithSegment {
    
    public IntStreamPlus     useIterator    (Function<IntIteratorPlus, IntStreamPlus> action);
    public <T> StreamPlus<T> sequentialToObj(Function<IntStreamPlus, StreamPlus<T>> action);
    
    public void close();
    
    
    //== segment ==
    
    public default StreamPlus<IntStreamPlus> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<IntStreamPlus> segment(int count, boolean includeTail) {
        val index = new AtomicInteger(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeTail);
    }
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, boolean includeTail) {
        return sequentialToObj(stream -> {
            // TODO - Find a way to make it fully lazy.
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
                if (adding.get()) list.get().add(data);
                return null;
            });
            val mainStream = StreamPlus.from(stream.mapToObj(streamOrNull)).filterNonNull();
            if (!includeTail)
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
    
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition, boolean includeTail) {
        return sequentialToObj(stream -> {
            val list = new AtomicReference<>(new GrowOnlyIntArray());
            val adding = new AtomicBoolean(false);
            
            StreamPlus<IntStreamPlus> resultStream 
                = StreamPlus.from(
                    stream
                    .mapToObj(i -> {
                        if (startCondition.test(i)) {
                            adding.set(true);
                        }
                        if (includeTail && adding.get()) list.get().add(i);
                        if (endCondition.test(i)) {
                            adding.set(false);
                            val retList = list.getAndUpdate(l -> new GrowOnlyIntArray());
                            return retList.stream();
                        }
                        
                        if (!includeTail && adding.get()) list.get().add(i);
                        return (IntStreamPlus)null;
                    })
                    .filterNonNull()
                );
            
            resultStream
            .onClose(()->IntStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    public default StreamPlus<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize) {
        return sequentialToObj(stream -> {
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
                    Func.f(()-> head),
                    Func.f(()-> StreamPlus.of(listRef.get().stream()))
                )
                .flatMap(s -> s.get());
            
            resultStream
            .onClose(()->IntStreamPlusWithSegment.this.close());
            
            return resultStream;
        });
    }
    
    public default IntStreamPlus collapse(IntPredicate conditionToCollapse, IntBinaryOperator concatFunc) {
        // TODO - Consider immitating what takeUntil() does.
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
    
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc) {
        return useIterator(iterator -> {
            val prev = new int[][] { null };
            IntStreamPlus resultStream = IntStreamPlus.generate(()->{
                if (prev[0] == null)
                    throw new NoMoreResultException();
                
                while(true) {
                    int next;
                    try {
                        next = iterator.next();
                    } catch (NoSuchElementException e) {
                        if (prev[0] == null)
                            throw new NoMoreResultException();
                        
                        val yield = prev[0][0];
                        prev[0] = null;
                        return yield;
                    }
                    
                    int newSize = segmentSize.applyAsInt(next);
                    if (newSize == 0) {
                        continue;
                    }
                    
                    if (newSize == 1) {
                        return next;
                    }
                    
                    prev[0] = new int[] { next };
                    for (int i = 0; i < (newSize - 1); i++) {
                        try {
                            next = iterator.nextInt();
                            prev[0][0] = concatFunc.applyAsInt(prev[0][0], next);
                        } catch (NoSuchElementException e) {
                            val yield = prev[0][0];
                            prev[0] = null;
                            return yield;
                        }
                    }
                    
                    val yield = prev[0][0];
                    prev[0] = null;
                    return yield;
                }
            });
            return resultStream;
        });
    }
//    
//    @SuppressWarnings("unchecked")
//    public default <TARGET> StreamPlus<TARGET> collapseSize(
//            IntUnaryOperator              segmentSize, 
//            IntFunction<TARGET>           mapper,
//            Func2<TARGET, TARGET, TARGET> concatFunc) {
//        val firstObj = new Object();
//        return useIterator(iterator -> {
//            val prev = new AtomicReference<Object>(firstObj);
//            StreamPlus<TARGET> resultStream = StreamPlus.generateWith(()->{
//                if (prev.get() == StreamPlusHelper.dummy)
//                    throw new NoMoreResultException();
//                
//                while(true) {
//                    DATA next;
//                    try {
//                        next = iterator.next();
//                    } catch (NoSuchElementException e) {
//                        if (prev.get() == firstObj)
//                            throw new NoMoreResultException();
//                        
//                        val yield = prev.get();
//                        prev.set(StreamPlusHelper.dummy);
//                        return (TARGET)yield;
//                    }
//                    
//                    Integer newSize = segmentSize.apply(next);
//                    if ((newSize == null) || (newSize == 0)) {
//                        continue;
//                    }
//                    
//                    if (newSize == 1) {
//                        val target = (TARGET)mapper.apply((DATA)next);
//                        return target;
//                    }
//                    
//                    TARGET target = (TARGET)mapper.apply((DATA)next);
//                    prev.set(target);
//                    for (int i = 0; i < (newSize - 1); i++) {
//                        try {
//                            next   = iterator.next();
//                            target = (TARGET)mapper.apply((DATA)next);
//                            val prevValue = (TARGET)prev.get();
//                            val newValue  = concatFunc.apply(prevValue, target);
//                            prev.set(newValue);
//                        } catch (NoSuchElementException e) {
//                            val yield = prev.get();
//                            prev.set(StreamPlusHelper.dummy);
//                            return (TARGET)yield;
//                        }
//                    }
//                    
//                    val yield = prev.get();
//                    prev.set(firstObj);
//                    return (TARGET)yield;
//                }
//            });
//            
//            return resultStream;
//        });
//    }
}
