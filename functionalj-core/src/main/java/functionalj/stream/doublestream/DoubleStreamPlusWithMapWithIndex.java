// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import functionalj.function.DoubleObjBiFunction;
import functionalj.function.IntDoubleBiFunction;
import functionalj.function.IntDoubleToDoubleFunctionPrimitive;
import functionalj.function.IntDoubleToIntFunction;
import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.IntDoubleTuple;
import lombok.val;

public interface DoubleStreamPlusWithMapWithIndex {
    
    // // TODO - to int, long, double
    public DoubleStreamPlus doubleStreamPlus();
    
    /**
     * @return  the stream of each value and index.
     */
    public default StreamPlus<IntDoubleTuple> mapWithIndex() {
        val index = new AtomicInteger();
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToObj(each -> {
            val currentIndex = index.getAndIncrement();
            val tuple = IntDoubleTuple.of(currentIndex, each);
            return tuple;
        });
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default DoubleStreamPlus mapWithIndex(IntDoubleToDoubleFunctionPrimitive combinator) {
        val index = new AtomicInteger();
        val streamPlus = doubleStreamPlus();
        return streamPlus.map(each -> {
            val currentIndex = index.getAndIncrement();
            val target = combinator.applyIntDouble(currentIndex, each);
            return target;
        });
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default IntStreamPlus mapToIntWithIndex(IntDoubleToIntFunction combinator) {
        val index = new AtomicInteger();
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToInt(each -> {
            val currentIndex = index.getAndIncrement();
            val target = combinator.applyIntAndDouble(currentIndex, each);
            return target;
        });
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default <T> StreamPlus<T> mapToObjWithIndex(IntDoubleBiFunction<T> combinator) {
        val index = new AtomicInteger();
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToObj(each -> {
            val currentIndex = index.getAndIncrement();
            val target = combinator.applyIntAndDouble(currentIndex, each);
            return target;
        });
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default <T> StreamPlus<T> mapWithIndex(DoubleUnaryOperator valueMapper, IntDoubleBiFunction<T> combiner) {
        val index = new AtomicInteger();
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToObj(each -> {
            val currentIndex = index.getAndIncrement();
            val value = valueMapper.applyAsDouble(each);
            val target = combiner.applyIntAndDouble(currentIndex, value);
            return target;
        });
    }
    
    /**
     * Create a stream whose value is the combination between the mapped value of this stream and its index.
     */
    public default <T1, T> StreamPlus<T> mapToObjWithIndex(DoubleFunction<? extends T1> valueMapper, DoubleObjBiFunction<? super T1, T> combiner) {
        val index = new AtomicInteger();
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToObj(each -> {
            val i = index.getAndIncrement();
            val value = valueMapper.apply(each);
            val target = combiner.applyAsDouble(i, value);
            return target;
        });
    }
}
