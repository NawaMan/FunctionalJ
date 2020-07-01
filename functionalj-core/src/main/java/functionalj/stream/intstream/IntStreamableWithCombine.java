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

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.stream.Streamable;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;

public interface IntStreamableWithCombine {

    public IntStreamPlus intStream();

    public default IntStreamable concatWith(IntStreamable tail) {
        return () -> {
            return intStream().concatWith(tail.intStream());
        };
    }

    public default IntStreamable mergeWith(IntStreamable anotherStreamable) {
        return () -> {
            return intStream().mergeWith(anotherStreamable.intStream());
        };
    }

    public default <ANOTHER> Streamable<IntTuple2<ANOTHER>> zipWith(Streamable<ANOTHER> anotherStreamable) {
        return () -> {
            return intStream().zipWith(anotherStreamable.stream());
        };
    }

    public default <ANOTHER> Streamable<IntTuple2<ANOTHER>> zipWith(Streamable<ANOTHER> anotherStreamable,
            ZipWithOption option) {
        return () -> {
            return intStream().zipWith(anotherStreamable.stream(), option);
        };
    }

    public default <ANOTHER, TARGET> Streamable<TARGET> zipWith(Streamable<ANOTHER> anotherStreamable,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return () -> {
            return intStream().zipWith(anotherStreamable.stream(), merger);
        };
    }

    public default Streamable<IntIntTuple> zipWith(IntStreamable anotherStreamable) {
        return () -> {
            return intStream().zipWith(anotherStreamable.intStream());
        };
    }

    public default Streamable<IntIntTuple> zipWith(IntStreamable anotherStreamable, int defaultValue) {
        return () -> {
            return intStream().zipWith(anotherStreamable.intStream(), defaultValue);
        };
    }

    public default Streamable<IntIntTuple> zipWith(IntStreamable anotherStreamable, int defaultValue1,
            int defaultValue2) {
        return () -> {
            return intStream().zipWith(anotherStreamable.intStream(), defaultValue1, defaultValue2);
        };
    }

    public default IntStreamable zipWith(IntStreamable anotherStreamable, IntBiFunctionPrimitive merger) {
        return () -> {
            return intStream().zipWith(anotherStreamable.intStream(), merger);
        };
    }

    public default IntStreamable zipWith(IntStreamable anotherStreamable, int defaultValue,
            IntBiFunctionPrimitive merger) {
        return () -> {
            return intStream().zipWith(anotherStreamable.intStream(), defaultValue, merger);
        };
    }

    public default IntStreamable zipWith(IntStreamable anotherStreamable, int defaultValue1, int defaultValue2,
            IntBiFunctionPrimitive merger) {
        return () -> {
            return intStream().zipWith(anotherStreamable.intStream(), defaultValue1, defaultValue2, merger);
        };
    }

    public default <T> Streamable<T> zipToObjWith(IntStreamable anotherStreamable, IntIntBiFunction<T> merger) {
        return () -> {
            return intStream().zipToObjWith(anotherStreamable.intStream(), merger);
        };
    }

    public default <T> Streamable<T> zipToObjWith(IntStreamable anotherStreamable, int defaultValue,
            IntIntBiFunction<T> merger) {
        return () -> {
            return intStream().zipToObjWith(anotherStreamable.intStream(), defaultValue, merger);
        };
    }

    public default <T> Streamable<T> zipToObjWith(IntStreamable anotherStreamable, int defaultValue1, int defaultValue2,
            IntIntBiFunction<T> merger) {
        return () -> {
            return intStream().zipToObjWith(anotherStreamable.intStream(), defaultValue1, defaultValue2, merger);
        };
    }

    public default <ANOTHER, TARGET> Streamable<TARGET> zipToObjWith(Streamable<ANOTHER> anotherStreamable,
            ZipWithOption option, IntObjBiFunction<ANOTHER, TARGET> merger) {
        return () -> {
            return intStream().zipWith(anotherStreamable.stream(), option, merger);
        };
    }

    public default IntStreamable choose(IntStreamable anotherStreamable, IntBiPredicatePrimitive selectThisNotAnother) {
        return () -> {
            return intStream().choose(anotherStreamable.intStream(), selectThisNotAnother);
        };
    }

}
