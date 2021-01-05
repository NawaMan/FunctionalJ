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
package functionalj.stream.doublestream;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.StreamSupport;

import functionalj.function.DoubleDoubleBiFunction;
import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.function.Func1;
import functionalj.function.Func4;
import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.DoubleDoubleTuple;
import lombok.val;


class DoubleStreamPlusWithMapGroupHelper {
    
    static <TARGET> StreamPlus<TARGET> mapGroup(
            DoubleStreamPlus streamPlus,
            int count,
            Func4<double[], Integer, Integer, Consumer<? super TARGET>, Void> processNormal,
            Func4<double[], Integer, Integer, Consumer<? super TARGET>, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractSpliterator<TARGET>(splitr.estimateSize(), 0) {
            double[] array = new double[count*10];
            int      start = 0;
            int      end   = 0;
            boolean  used  = false;
            @Override
            public boolean tryAdvance(Consumer<? super TARGET> consumer) {
                DoubleConsumer action = elem -> {
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
            DoubleStreamPlus streamPlus,
            int count,
            Func4<double[], Integer, Integer, IntConsumer, Void> processNormal,
            Func4<double[], Integer, Integer, IntConsumer, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
            double[] array = new double[count*10];
            int      start = 0;
            int      end   = 0;
            boolean  used  = false;
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                DoubleConsumer action = elem -> {
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
    
    static <DATA, TARGET> DoubleStreamPlus mapGroupToDouble(
            DoubleStreamPlus streamPlus,
            int count,
            Func4<double[], Integer, Integer, DoubleConsumer, Void> processNormal,
            Func4<double[], Integer, Integer, DoubleConsumer, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
            double[] array = new double[count*10];
            int      start = 0;
            int      end   = 0;
            boolean  used  = false;
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                DoubleConsumer action = elem -> {
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


public interface DoubleStreamPlusWithMapGroup {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamPlus mapTwo(DoubleBinaryOperator combinator) {
        return DoubleStreamPlusWithMapGroupHelper.mapGroupToDouble(
                doubleStreamPlus(), 2, 
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
    public default DoubleStreamPlus mapGroup(int count, ToDoubleFunction<DoubleStreamPlus> combinator) {
        return DoubleStreamPlusWithMapGroupHelper.mapGroupToDouble(
                doubleStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    val value      = combinator.applyAsDouble(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    val value      = combinator.applyAsDouble(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                });
    }
    
    // == Object ==
    
    /** @return  the stream of  each previous value and each current value. */
    public default StreamPlus<DoubleDoubleTuple> mapTwoToObj() {
        return DoubleStreamPlusWithMapGroupHelper.mapGroup(
                doubleStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    consumer.accept(DoubleDoubleTuple.of(prev, curr));
                    return (Void)null;
                },
                null);
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> StreamPlus<TARGET> mapGroupToObj(DoubleDoubleBiFunction<TARGET> combinator) {
        return DoubleStreamPlusWithMapGroupHelper.mapGroup(
                doubleStreamPlus(), 2, 
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
    public default StreamPlus<DoubleStreamPlus> mapGroupToObj(int count) {
        return DoubleStreamPlusWithMapGroupHelper.<DoubleStreamPlus>mapGroup(
                doubleStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    consumer.accept(streamPlus);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    consumer.accept(streamPlus);
                    return (Void)null;
                });
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> StreamPlus<TARGET> mapGroupToObj(int count, Func1<DoubleStreamPlus, ? extends TARGET> combinator) {
        return mapGroupToObj(count)
                .map(combinator);
    }
    
    //== Int ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamPlus mapTwoToInt(DoubleDoubleToIntFunctionPrimitive combinator) {
        return DoubleStreamPlusWithMapGroupHelper.mapGroupToInt(
                doubleStreamPlus(), 2, 
                (array, start, end, consumer) -> {
                    val prev  = array[start];
                    val curr  = array[start + 1];
                    val value = combinator.applyAsDoubleAndDouble(prev, curr);
                    consumer.accept(value);
                    return (Void)null;
                },
                null);
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamPlus mapGroupToInt(int count, ToIntFunction<DoubleStreamPlus> combinator) {
        return DoubleStreamPlusWithMapGroupHelper.mapGroupToInt(
                doubleStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    val value      = combinator.applyAsInt(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    val value      = combinator.applyAsInt(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                });
    }
    
    //== Double ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamPlus mapTwoToDouble(DoubleBinaryOperator combinator) {
        return DoubleStreamPlusWithMapGroupHelper.mapGroupToDouble(
                doubleStreamPlus(), 2, 
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
    public default DoubleStreamPlus mapGroupToDouble(int count, ToDoubleFunction<DoubleStreamPlus> combinator) {
        return DoubleStreamPlusWithMapGroupHelper.mapGroupToDouble(
                doubleStreamPlus(), count, 
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, count);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    val value      = combinator.applyAsDouble(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                },
                (array, start, end, consumer) -> {
                    val iterator   = new ArrayBackedDoubleIteratorPlus(array, start, end - start);
                    val streamPlus = new ArrayBackedDoubleStreamPlus(iterator);
                    val value      = combinator.applyAsDouble(streamPlus);
                    consumer.accept(value);
                    return (Void)null;
                });
    }
    
}
