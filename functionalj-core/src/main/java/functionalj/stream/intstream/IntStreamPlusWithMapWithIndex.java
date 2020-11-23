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
package functionalj.stream.intstream;

import static functionalj.tuple.IntIntTuple.tuple;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.stream.StreamPlus;
import functionalj.tuple.IntIntTuple;
import lombok.val;



public interface IntStreamPlusWithMapWithIndex {
    
    // // TODO - to int, long, double
    
    public IntStreamPlus intStreamPlus();
    
    /** @return  the stream of each value and index. */
    public default StreamPlus<IntIntTuple> mapWithIndex() {
        val index = new AtomicInteger();
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val currentIndex = index.getAndIncrement();
                    val tuple        = tuple(currentIndex, each);
                    return tuple;
                });
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default IntStreamPlus mapWithIndex(IntBinaryOperator combinator) {
        val index = new AtomicInteger();
        val streamPlus = intStreamPlus();
        return streamPlus
                .map(each -> {
                    val currentIndex = index.getAndIncrement();
                    val target       = combinator.applyAsInt(currentIndex, each);
                    return target;
                });
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T> StreamPlus<T> mapToObjWithIndex(IntIntBiFunction<T> combinator) {
        val index = new AtomicInteger();
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val currentIndex = index.getAndIncrement();
                    val target       = combinator.applyInt(currentIndex, each);
                    return target;
                });
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T1, T> StreamPlus<T> mapWithIndex(
                IntUnaryOperator    valueMapper,
                IntIntBiFunction<T> combiner) {
        val index = new AtomicInteger();
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val currentIndex = index.getAndIncrement();
                    val value        = valueMapper.applyAsInt(each);
                    val target       = combiner.apply(currentIndex, value);
                    return target;
                });
    }
    
    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> StreamPlus<T> mapToObjWithIndex(
                IntFunction<? extends T1>       valueMapper,
                IntObjBiFunction<? super T1, T> combiner) {
        val index = new AtomicInteger();
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(each -> {
                    val i      = index.getAndIncrement();
                    val value  = valueMapper.apply(each);
                    val target = combiner.apply(i, value);
                    return target;
                });
    }
    
}
