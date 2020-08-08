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
package functionalj.streamable.intstreamable;

import static functionalj.streamable.Streamable.deriveFrom;

import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.streamable.Streamable;
import functionalj.tuple.IntIntTuple;

public interface IntStreamableWithMapWithIndex extends AsIntStreamable {

    // TODO - to int, long, double

    /** @return  the stream of each value and index. */
    public default Streamable<IntIntTuple> mapWithIndex() {
        return Streamable.deriveFrom(this, stream -> stream.mapWithIndex());
    }

    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default IntStreamable mapWithIndex(IntBinaryOperator combinator) {
        return IntStreamable.deriveFrom(this, stream -> stream.mapWithIndex(combinator));
    }

    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T> Streamable<T> mapToObjWithIndex(IntIntBiFunction<T> combinator) {
        return deriveFrom(this, stream -> stream.mapToObjWithIndex(combinator));
    }

    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> Streamable<T> mapWithIndex(
            IntUnaryOperator    valueMapper,
            IntIntBiFunction<T> combinator) {
        return deriveFrom(this, stream -> stream.mapWithIndex(valueMapper, combinator));
    }

    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> Streamable<T> mapToObjWithIndex(
            IntFunction<? extends T1>       valueMapper,
            IntObjBiFunction<? super T1, T> combinator) {
        return deriveFrom(this, stream -> stream.mapToObjWithIndex(valueMapper, combinator));
    }
}
