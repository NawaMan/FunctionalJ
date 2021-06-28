// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.Func4;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntIntToDoubleFunctionPrimitive;
import functionalj.function.IntIntToLongFunctionPrimitive;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.tuple.IntIntTuple;
import lombok.val;



class IntStreamPlusWithMapGroupHelper {
    
    static <TARGET> StreamPlus<TARGET> mapGroup(
            IntStreamPlus streamPlus,
            int count,
            Func4<int[], Integer, Integer, Consumer<? super TARGET>, Void> processNormal,
            Func4<int[], Integer, Integer, Consumer<? super TARGET>, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractSpliterator<TARGET>(splitr.estimateSize(), 0) {
            int[]    array = new int[count*10];
            int      start = 0;
            int      end   = 0;
            boolean  used  = false;
            @Override
            public boolean tryAdvance(Consumer<? super TARGET> consumer) {
                IntConsumer action = elem -> {
                    array[end] = elem;
                    end++;
                    int length = end - start;
                    if (length >= count) {
                        processNormal.apply(array, start, end, consumer);
                        used = true;
                        start++;
                    }
                    if (end >= array.length) {
                        System.arraycopy(array, start, array, 0, length - 1);
                        start = 0;
                        end   = length - 1;
                    }
                };
                boolean hasNext = splitr.tryAdvance(action);
                if (!hasNext && !used && (processTail != null)) {
                    processTail.apply(array, start, end, consumer);
                }
                return hasNext;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, false));
    }
    
    static <TARGET> IntStreamPlus mapGroupToInt(
            IntStreamPlus streamPlus,
            int count,
            Func4<int[], Integer, Integer, IntConsumer, Void> processNormal,
            Func4<int[], Integer, Integer, IntConsumer, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
            int[]    array = new int[count*10];
            int      start = 0;
            int      end   = 0;
            boolean  used  = false;
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                IntConsumer action = elem -> {
                    array[end] = elem;
                    end++;
                    int length = end - start;
                    if (length >= count) {
                        processNormal.apply(array, start, end, consumer);
                        used = true;
                        start++;
                    }
                    if (end >= array.length) {
                        System.arraycopy(array, start, array, 0, length - 1);
                        start = 0;
                        end   = length - 1;
                    }
                };
                boolean hasNext = splitr.tryAdvance(action);
                if (!hasNext && !used && (processTail != null)) {
                    processTail.apply(array, start, end, consumer);
                }
                return hasNext;
            }
        };
        return IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
    }
    
    static <DATA, TARGET> LongStreamPlus mapGroupToLong(
            IntStreamPlus streamPlus,
            int count,
            Func4<int[], Integer, Integer, LongConsumer, Void> processNormal,
            Func4<int[], Integer, Integer, LongConsumer, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractLongSpliterator(splitr.estimateSize(), 0) {
            int[]   array = new int[count*10];
            int     start = 0;
            int     end   = 0;
            boolean used  = false;
            @Override
            public boolean tryAdvance(LongConsumer consumer) {
                IntConsumer action = elem -> {
                    array[end] = elem;
                    end++;
                    int length = end - start;
                    if (length >= count) {
                        processNormal.apply(array, start, end, consumer);
                        used = true;
                        start++;
                    }
                    if (end >= array.length) {
                        System.arraycopy(array, start, array, 0, length - 1);
                        start = 0;
                        end   = length - 1;
                    }
                };
                boolean hasNext = splitr.tryAdvance(action);
                if (hasNext && !used) {
                    hasNext = splitr.tryAdvance(action);
                }
                
                if (!hasNext && !used && (processTail != null)) {
                    processTail.apply(array, start, end, consumer);
                }
                return hasNext;
            }
        };
        return LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
    }
    
    static <DATA, TARGET> DoubleStreamPlus mapGroupToDouble(
            IntStreamPlus streamPlus,
            int count,
            Func4<int[], Integer, Integer, DoubleConsumer, Void> processNormal,
            Func4<int[], Integer, Integer, DoubleConsumer, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
            int[]    array = new int[count*10];
            int      start = 0;
            int      end   = 0;
            boolean  used  = false;
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                IntConsumer action = elem -> {
                    array[end] = elem;
                    end++;
                    int length = end - start;
                    if (length >= count) {
                        processNormal.apply(array, start, end, consumer);
                        used = true;
                        start++;
                    }
                    if (end >= array.length) {
                        System.arraycopy(array, start, array, 0, length - 1);
                        start = 0;
                        end   = length - 1;
                    }
                };
                boolean hasNext = splitr.tryAdvance(action);
                if (hasNext && !used) {
                    hasNext = splitr.tryAdvance(action);
                }
                
                if (!hasNext && !used && (processTail != null)) {
                    processTail.apply(array, start, end, consumer);
                }
                return hasNext;
            }
        };
        return DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
    }
}



public interface IntStreamPlusWithMapGroup {
    
    public IntStreamPlus intStreamPlus();
    
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamPlus mapTwo(IntBinaryOperator combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToInt(
                intStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    val value = combinator.applyAsInt(prev, curr);
                    consumer.accept(value);
                    return (Void)null;
                },
                null);
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamPlus mapGroup(int count, ToIntFunction<IntStreamPlus> combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToInt(
                intStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsInt(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsInt(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                });
    }
    
    // == Object ==
    
    /** @return  the stream of  each previous value and each current value. */
    public default StreamPlus<IntIntTuple> mapTwoToObj() {
        return IntStreamPlusWithMapGroupHelper.mapGroup(
                intStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    consumer.accept(IntIntTuple.of(prev, curr));
                    return (Void)null;
                },
                null);
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> StreamPlus<TARGET> mapTwoToObj(IntIntBiFunction<TARGET> combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroup(
                intStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    val value = combinator.apply(prev, curr);
                    consumer.accept(value);
                    return (Void)null;
                },
                null);
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default StreamPlus<IntStreamPlus> mapGroupToObj(int count) {
        return IntStreamPlusWithMapGroupHelper.<IntStreamPlus>mapGroup(
                intStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    consumer.accept(streamPlus);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    consumer.accept(streamPlus);
                    return (Void)null;
                });
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> StreamPlus<TARGET> mapGroupToObj(int count, Func1<IntStreamPlus, ? extends TARGET> combinator) {
        return mapGroupToObj(count)
                .map(combinator);
    }
    
    //== Int ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamPlus mapTwoToInt(IntBinaryOperator combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToInt(
                intStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    val value = combinator.applyAsInt(prev, curr);
                    consumer.accept(value);
                    return (Void)null;
                },
                null);
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamPlus mapGroupToInt(int count, ToIntFunction<IntStreamPlus> combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToInt(
                intStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsInt(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsInt(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                });
    }
    
    //== Long ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default LongStreamPlus mapTwoToLong(IntIntToLongFunctionPrimitive combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToLong(
                intStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    val value = combinator.applyAsIntAndInt(prev, curr);
                    consumer.accept(value);
                    return (Void)null;
                },
                null);
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default LongStreamPlus mapGroupToLong(int count, ToLongFunction<IntStreamPlus> combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToLong(
                intStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsLong(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsLong(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                });
    }
    
    //== Double ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamPlus mapTwoToDouble(IntIntToDoubleFunctionPrimitive combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToDouble(
                intStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    val value = combinator.applyAsDouble(prev, curr);
                    consumer.accept(value);
                    return (Void)null;
                },
                null);
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamPlus mapGroupToDouble(int count, ToDoubleFunction<IntStreamPlus> combinator) {
        return IntStreamPlusWithMapGroupHelper.mapGroupToDouble(
                intStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsDouble(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedIntIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedIntStreamPlus(iterator);
                    val value      = combinator.applyAsDouble(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                });
    }
    
}
