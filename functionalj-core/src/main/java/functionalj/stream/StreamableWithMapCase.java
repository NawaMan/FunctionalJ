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

import java.util.function.Function;
import java.util.stream.Stream;

public interface StreamableWithMapCase<DATA> {
    
    public <TARGET> Streamable<TARGET> deriveWith(
            Function<Stream<DATA>, Stream<TARGET>> action);
    
    //== mapCase ==
    
    public default <T> Streamable<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapCase(mapper1, mapper2);
        });
    }
    
    public default <T> Streamable<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapCase(mapper1, mapper2, mapper3);
        });
    }
    
    public default <T> Streamable<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapCase(mapper1, mapper2, mapper3, mapper4);
        });
    }
    
    public default <T> Streamable<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapCase(mapper1, mapper2, mapper3, mapper4, mapper5);
        });
    }
    
    public default <T> Streamable<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapCase(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
        });
    }
}
