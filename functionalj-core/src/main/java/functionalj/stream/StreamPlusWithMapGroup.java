// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.stream.StreamPlusHelper.sequential;

import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.Func10;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import functionalj.function.ObjectObjectToDoubleFunctionPrimitive;
import functionalj.function.ObjectObjectToIntFunctionPrimitive;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.Tuple10;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import functionalj.tuple.Tuple7;
import functionalj.tuple.Tuple8;
import functionalj.tuple.Tuple9;
import lombok.val;

class StreamPlusWithMapGroupHelper {
    
    static <DATA, TARGET> StreamPlus<TARGET> mapGroup(StreamPlus<DATA> streamPlus, int count, Func4<Object[], Integer, Integer, Consumer<? super TARGET>, Void> processNormal, Func4<Object[], Integer, Integer, Consumer<? super TARGET>, Void> processTail) {
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            val spliterator = new Spliterators.AbstractSpliterator<TARGET>(splitr.estimateSize(), 0) {
        
                Object[] array = new Object[count * 10];
        
                int start = 0;
        
                int end = 0;
        
                boolean used = false;
        
                @Override
                public boolean tryAdvance(Consumer<? super TARGET> consumer) {
                    Consumer<? super DATA> action = elem -> {
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
                            end = length - 1;
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
        });
    }
    
    static <DATA, TARGET> IntStreamPlus mapGroupToInt(StreamPlus<DATA> streamPlus, int count, Func4<Object[], Integer, Integer, IntConsumer, Void> processNormal, Func4<Object[], Integer, Integer, IntConsumer, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
        
            Object[] array = new Object[count * 10];
        
            int start = 0;
        
            int end = 0;
        
            boolean used = false;
        
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                Consumer<DATA> action = elem -> {
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
                        end = length - 1;
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
    
    static <DATA, TARGET> DoubleStreamPlus mapGroupToDouble(StreamPlus<DATA> streamPlus, int count, Func4<Object[], Integer, Integer, DoubleConsumer, Void> processNormal, Func4<Object[], Integer, Integer, DoubleConsumer, Void> processTail) {
        val splitr = streamPlus.spliterator();
        val spliterator = new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
        
            Object[] array = new Object[count * 10];
        
            int start = 0;
        
            int end = 0;
        
            boolean used = false;
        
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                Consumer<DATA> action = elem -> {
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
                        end = length - 1;
                    }
                };
                boolean hasNext = splitr.tryAdvance(action);
                if (!hasNext && !used && (processTail != null)) {
                    processTail.apply(array, start, end, consumer);
                }
                return hasNext;
            }
        };
        return DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
    }
}

public interface StreamPlusWithMapGroup<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple2<DATA, DATA>> mapTwo() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 2, (array, start, end, consumer) -> {
            val prev = (DATA) array[start];
            val curr = (DATA) array[start + 1];
            consumer.accept(Tuple2.of(prev, curr));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple3<DATA, DATA, DATA>> mapThree() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 3, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            consumer.accept(Tuple3.of(value1, value2, value3));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple4<DATA, DATA, DATA, DATA>> mapFour() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 4, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            consumer.accept(Tuple4.of(value1, value2, value3, value4));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple5<DATA, DATA, DATA, DATA, DATA>> mapFive() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 5, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            consumer.accept(Tuple5.of(value1, value2, value3, value4, value5));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple6<DATA, DATA, DATA, DATA, DATA, DATA>> mapSix() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 6, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            consumer.accept(Tuple6.of(value1, value2, value3, value4, value5, value6));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple7<DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapSeven() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 7, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value7 = (DATA) array[start + 6];
            consumer.accept(Tuple7.of(value1, value2, value3, value4, value5, value6, value7));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple8<DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapEight() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 8, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value7 = (DATA) array[start + 6];
            val value8 = (DATA) array[start + 7];
            consumer.accept(Tuple8.of(value1, value2, value3, value4, value5, value6, value7, value8));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple9<DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapNine() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 9, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value7 = (DATA) array[start + 6];
            val value8 = (DATA) array[start + 7];
            val value9 = (DATA) array[start + 8];
            consumer.accept(Tuple9.of(value1, value2, value3, value4, value5, value6, value7, value8, value9));
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<Tuple10<DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapTen() {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 10, (array, start, end, consumer) -> {
            val value1  = (DATA) array[start];
            val value2  = (DATA) array[start + 1];
            val value3  = (DATA) array[start + 2];
            val value4  = (DATA) array[start + 3];
            val value5  = (DATA) array[start + 4];
            val value6  = (DATA) array[start + 5];
            val value7  = (DATA) array[start + 6];
            val value8  = (DATA) array[start + 7];
            val value9  = (DATA) array[start + 8];
            val value10 = (DATA) array[start + 9];
            consumer.accept(Tuple10.of(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10));
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(BiFunction<? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 2, (array, start, end, consumer) -> {
            val prev = (DATA) array[start];
            val curr = (DATA) array[start + 1];
            val value = combinator.apply(prev, curr);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func3<? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 3, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value = combinator.apply(value1, value2, value3);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func4<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 4, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value = combinator.apply(value1, value2, value3, value4);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func5<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus, 5, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value = combinator.apply(value1, value2, value3, value4, value5);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func6<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus(), 6, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value = combinator.apply(value1, value2, value3, value4, value5, value6);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func7<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus(), 7, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value7 = (DATA) array[start + 6];
            val value = combinator.apply(value1, value2, value3, value4, value5, value6, value7);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func8<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus(), 8, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value7 = (DATA) array[start + 6];
            val value8 = (DATA) array[start + 7];
            val value = combinator.apply(value1, value2, value3, value4, value5, value6, value7, value8);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func9<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus(), 9, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value7 = (DATA) array[start + 6];
            val value8 = (DATA) array[start + 7];
            val value9 = (DATA) array[start + 8];
            val value = combinator.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> mapGroup(Func10<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return StreamPlusWithMapGroupHelper.mapGroup(streamPlus(), 10, (array, start, end, consumer) -> {
            val value1 = (DATA) array[start];
            val value2 = (DATA) array[start + 1];
            val value3 = (DATA) array[start + 2];
            val value4 = (DATA) array[start + 3];
            val value5 = (DATA) array[start + 4];
            val value6 = (DATA) array[start + 5];
            val value7 = (DATA) array[start + 6];
            val value8 = (DATA) array[start + 7];
            val value9 = (DATA) array[start + 8];
            val value10 = (DATA) array[start + 9];
            val value = combinator.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default StreamPlus<StreamPlus<DATA>> mapGroup(int count) {
        return StreamPlusWithMapGroupHelper.<DATA, StreamPlus<DATA>>mapGroup(streamPlus(), count, (array, start, end, consumer) -> {
            val iterator = new ArrayBackedIteratorPlus<>(array, start, count);
            val streamPlus = new ArrayBackedStreamPlus<>(iterator);
            consumer.accept((StreamPlus<DATA>) (StreamPlus) streamPlus);
            return (Void) null;
        }, (array, start, end, consumer) -> {
            val iterator = new ArrayBackedIteratorPlus<>(array, start, end - start);
            val streamPlus = new ArrayBackedStreamPlus<>(iterator);
            consumer.accept((StreamPlus<DATA>) (StreamPlus) streamPlus);
            return (Void) null;
        });
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default <TARGET> StreamPlus<TARGET> mapGroup(int count, Func1<? super StreamPlus<? extends DATA>, ? extends TARGET> combinator) {
        return mapGroup(count).map(combinator);
    }
    
    // == Int ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default IntStreamPlus mapTwoToInt(ObjectObjectToIntFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroupToInt(streamPlus, 2, (array, start, end, consumer) -> {
            val prev = (DATA) array[start];
            val curr = (DATA) array[start + 1];
            val value = combinator.apply(prev, curr);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default IntStreamPlus mapGroupToInt(int count, ToIntFunction<? super StreamPlus<? extends DATA>> combinator) {
        return StreamPlusWithMapGroupHelper.mapGroupToInt(streamPlus(), count, (array, start, end, consumer) -> {
            val iterator = new ArrayBackedIteratorPlus<>(array, start, count);
            val streamPlus = new ArrayBackedStreamPlus<>(iterator);
            val value = combinator.applyAsInt((StreamPlus<? extends DATA>) streamPlus);
            consumer.accept(value);
            return (Void) null;
        }, (array, start, end, consumer) -> {
            val iterator = new ArrayBackedIteratorPlus<>(array, start, end - start);
            val streamPlus = new ArrayBackedStreamPlus<>(iterator);
            val value = combinator.applyAsInt((StreamPlus<? extends DATA>) streamPlus);
            consumer.accept(value);
            return (Void) null;
        });
    }
    
    // == Double ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default DoubleStreamPlus mapTwoToDouble(ObjectObjectToDoubleFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        val streamPlus = streamPlus();
        return StreamPlusWithMapGroupHelper.mapGroupToDouble(streamPlus, 2, (array, start, end, consumer) -> {
            val prev = (DATA) array[start];
            val curr = (DATA) array[start + 1];
            val value = combinator.apply(prev, curr);
            consumer.accept(value);
            return (Void) null;
        }, null);
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    @SuppressWarnings("unchecked")
    public default DoubleStreamPlus mapGroupToDouble(int count, ToDoubleFunction<? super StreamPlus<? extends DATA>> combinator) {
        return StreamPlusWithMapGroupHelper.mapGroupToDouble(streamPlus(), count, (array, start, end, consumer) -> {
            val iterator = new ArrayBackedIteratorPlus<>(array, start, count);
            val streamPlus = new ArrayBackedStreamPlus<>(iterator);
            val value = combinator.applyAsDouble((StreamPlus<? extends DATA>) streamPlus);
            consumer.accept(value);
            return (Void) null;
        }, (array, start, end, consumer) -> {
            val iterator = new ArrayBackedIteratorPlus<>(array, start, end - start);
            val streamPlus = new ArrayBackedStreamPlus<>(iterator);
            val value = combinator.applyAsDouble((StreamPlus<? extends DATA>) streamPlus);
            consumer.accept(value);
            return (Void) null;
        });
    }
}
