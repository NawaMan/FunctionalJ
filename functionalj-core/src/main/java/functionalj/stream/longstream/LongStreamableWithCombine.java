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
package functionalj.stream.longstream;

import functionalj.function.LongBiFunctionPrimitive;
import functionalj.function.LongBiPredicatePrimitive;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.stream.ZipWithOption;
import functionalj.streamable.Streamable;
import functionalj.tuple.LongLongTuple;
import functionalj.tuple.LongTuple2;

public interface LongStreamableWithCombine {
    
    public LongStreamPlus longStream();
    
    public default LongStreamable concatWith(LongStreamable tail) {
        return () -> {
            return longStream().concatWith(tail.longStream());
        };
    }
    
    public default LongStreamable mergeWith(LongStreamable anotherStreamable) {
        return () -> {
            return longStream().mergeWith(anotherStreamable.longStream());
        };
    }
    
    public default <ANOTHER> Streamable<LongTuple2<ANOTHER>> zipWith(Streamable<ANOTHER> anotherStreamable) {
        return () -> {
            return longStream().zipWith(anotherStreamable.stream());
        };
    }

    public default <ANOTHER> Streamable<LongTuple2<ANOTHER>> zipWith(Streamable<ANOTHER> anotherStreamable,
            ZipWithOption option) {
        return () -> {
            return longStream().zipWith(anotherStreamable.stream());
        };
    }

    public default <ANOTHER, TARGET> Streamable<TARGET> zipWith(Streamable<ANOTHER> anotherStreamable,
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return () -> {
            return longStream().zipWith(anotherStreamable.stream(), merger);
        };
    }

    public default Streamable<LongLongTuple> zipWith(LongStreamable anotherStreamable) {
        return () -> {
            return longStream().zipWith(anotherStreamable.longStream());
        };
    }

    public default Streamable<LongLongTuple> zipWith(LongStreamable anotherStreamable, long defaultValue) {
        return () -> {
            return longStream().zipWith(anotherStreamable.longStream(), defaultValue);
        };
    }

    public default Streamable<LongLongTuple> zipWith(LongStreamable anotherStreamable, long defaultValue1,
            long defaultValue2) {
        return () -> {
            return longStream().zipWith(anotherStreamable.longStream(), defaultValue1, defaultValue2);
        };
    }

    public default LongStreamable zipWith(LongStreamable anotherStreamable, LongBiFunctionPrimitive merger) {
        return () -> {
            return longStream().zipWith(anotherStreamable.longStream(), merger);
        };
    }

    public default LongStreamable zipWith(LongStreamable anotherStreamable, long defaultValue,
            LongBiFunctionPrimitive merger) {
        return () -> {
            return longStream().zipWith(anotherStreamable.longStream(), defaultValue, merger);
        };
    }

    public default LongStreamable zipWith(LongStreamable anotherStreamable, long defaultValue1, long defaultValue2,
            LongBiFunctionPrimitive merger) {
        return () -> {
            return longStream().zipWith(anotherStreamable.longStream(), defaultValue1, defaultValue2, merger);
        };
    }

    public default <T> Streamable<T> zipToObjWith(LongStreamable anotherStreamable, LongLongBiFunction<T> merger) {
        return () -> {
            return longStream().zipToObjWith(anotherStreamable.longStream(), merger);
        };
    }

    public default <T> Streamable<T> zipToObjWith(LongStreamable anotherStreamable, long defaultValue,
            LongLongBiFunction<T> merger) {
        return () -> {
            return longStream().zipToObjWith(anotherStreamable.longStream(), defaultValue, merger);
        };
    }
    
    public default <T> Streamable<T> zipToObjWith(LongStreamable anotherStreamable, long defaultValue1, long defaultValue2,
            LongLongBiFunction<T> merger) {
        return () -> {
            return longStream().zipToObjWith(anotherStreamable.longStream(), defaultValue1, defaultValue2, merger);
        };
    }
    
    public default <ANOTHER, TARGET> Streamable<TARGET> zipToObjWith(Streamable<ANOTHER> anotherStreamable,
            ZipWithOption option, LongObjBiFunction<ANOTHER, TARGET> merger) {
        return () -> {
            return longStream().zipWith(anotherStreamable.stream(), merger);
        };
    }

    public default LongStreamable choose(LongStreamable anotherStreamable, LongBiPredicatePrimitive selectThisNotAnother) {
        return () -> {
            return longStream().choose(anotherStreamable.longStream(), selectThisNotAnother);
        };
    }

}
