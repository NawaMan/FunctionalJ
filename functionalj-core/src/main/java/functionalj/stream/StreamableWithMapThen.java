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
package functionalj.stream;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;

public interface StreamableWithMapThen<DATA> {
    
    public <TARGET> Streamable<TARGET> deriveWith(
            Function<Stream<DATA>, Stream<TARGET>> action);
    
    //== mapThen ==
    
    public default <T1, T2, T> 
        Streamable<T> mapThen(
                Function<? super DATA, T1> mapper1,
                Function<? super DATA, T2> mapper2,
                BiFunction<T1, T2, T> function) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from   (stream)
                    .mapThen(mapper1, mapper2, function);
        });
    }
    public default <T1, T2, T3, T> 
        Streamable<T> mapThen(
                Function<? super DATA, T1> mapper1,
                Function<? super DATA, T2> mapper2,
                Function<? super DATA, T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from   (stream)
                    .mapThen(mapper1, mapper2, mapper3, function);
        });
    }
    public default <T1, T2, T3, T4, T> 
        Streamable<T> mapThen(
                Function<? super DATA, T1> mapper1,
                Function<? super DATA, T2> mapper2,
                Function<? super DATA, T3> mapper3,
                Function<? super DATA, T4> mapper4,
                Func4<T1, T2, T3, T4, T> function) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from   (stream)
                    .mapThen(mapper1, mapper2, mapper3, mapper4, function);
        });
    }
    public default <T1, T2, T3, T4, T5, T> 
        Streamable<T> mapThen(
                Function<? super DATA, T1> mapper1,
                Function<? super DATA, T2> mapper2,
                Function<? super DATA, T3> mapper3,
                Function<? super DATA, T4> mapper4,
                Function<? super DATA, T5> mapper5,
                Func5<T1, T2, T3, T4, T5, T> function) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from   (stream)
                    .mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, function);
        });
    }
    public default <T1, T2, T3, T4, T5, T6, T> 
        Streamable<T> mapThen(
                Function<? super DATA, T1> mapper1,
                Function<? super DATA, T2> mapper2,
                Function<? super DATA, T3> mapper3,
                Function<? super DATA, T4> mapper4,
                Function<? super DATA, T5> mapper5,
                Function<? super DATA, T6> mapper6,
                Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from   (stream)
                    .mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, function);
        });
    }
}
