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
package functionalj.stream.longstream;

import static functionalj.tuple.IntLongTuple.tuple;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

import functionalj.function.IntLongBiFunction;
import functionalj.function.IntegerLongToLongFunctionPrimitive;
import functionalj.function.LongObjBiFunction;
import functionalj.stream.StreamPlus;
import functionalj.tuple.IntLongTuple;
import lombok.val;


public interface LongStreamPlusWithMapWithIndex {
    
    // // TODO - to int, long, double
    
    public LongStreamPlus longStreamPlus();
    
    /** @return  the stream of each value and index. */
    public default StreamPlus<IntLongTuple> mapWithIndex() {
        val index = new AtomicInteger();
        val streamPlus = longStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val currentIndex = index.getAndIncrement();
                    val tuple        = tuple(currentIndex, each);
                    return tuple;
                });
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default LongStreamPlus mapWithIndex(IntegerLongToLongFunctionPrimitive combinator) {
        val index = new AtomicInteger();
        val streamPlus = longStreamPlus();
        return streamPlus
                .map(each -> {
                    val currentIndex = index.getAndIncrement();
                    val target       = combinator.applyAsLong(currentIndex, each);
                    return target;
                });
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T> StreamPlus<T> mapToObjWithIndex(IntLongBiFunction<T> combinator) {
        val index = new AtomicInteger();
        val streamPlus = longStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val currentIndex = index.getAndIncrement();
                    val target       = combinator.applyIntAndLong(currentIndex, each);
                    return target;
                });
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T1, T> StreamPlus<T> mapWithIndex(
                LongUnaryOperator    valueMapper,
                IntLongBiFunction<T> combiner) {
        val index = new AtomicInteger();
        val streamPlus = longStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val currentIndex = index.getAndIncrement();
                    val value        = valueMapper.applyAsLong(each);
                    val target       = combiner.applyIntAndLong(currentIndex, value);
                    return target;
                });
    }
    
    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> StreamPlus<T> mapToObjWithIndex(
                LongFunction<? extends T1>      valueMapper,
                LongObjBiFunction<? super T1, T> combiner) {
        val index = new AtomicInteger();
        val streamPlus = longStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val currentIndex = index.getAndIncrement();
                    val value        = valueMapper.apply(each);
                    val target       = combiner.applyAsLong(currentIndex, value);
                    return target;
                });
    }
    
}
