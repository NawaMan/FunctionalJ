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
package functionalj.stream;

import java.util.function.Function;
import java.util.stream.Stream;

public interface StreamableWithMapFirst<DATA> {
    
    public <TARGET> Streamable<TARGET> deriveWith(
            Function<Stream<DATA>, Stream<TARGET>> action);
    
    //== mapFirst ==

    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     * 
     * @param <T>      the target type.
     * @param mapper1  the first mapper.
     * @param mapper2  the second mapper.
     * @return         the result of the first map result that is not null.
     */
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapFirst(mapper1, mapper2);
        });
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     * 
     * @param <T>      the target type.
     * @param mapper1  the first mapper.
     * @param mapper2  the second mapper.
     * @param mapper3  the third mapper.
     * @return         the result of the first map result that is not null.
     */
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapFirst(mapper1, mapper2, mapper3);
        });
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     * 
     * @param <T>      the target type.
     * @param mapper1  the first mapper.
     * @param mapper2  the second mapper.
     * @param mapper3  the third mapper.
     * @param mapper4  the forth mapper.
     * @return         the result of the first map result that is not null.
     */
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapFirst(mapper1, mapper2, mapper3, mapper4);
        });
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     * 
     * @param <T>      the target type.
     * @param mapper1  the first mapper.
     * @param mapper2  the second mapper.
     * @param mapper3  the third mapper.
     * @param mapper4  the forth mapper.
     * @param mapper5  the fifth mapper.
     * @param mapper6  the sixth mapper.
     * @return         the result of the first map result that is not null.
     */
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5);
        });
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     * 
     * @param <T>      the target type.
     * @param mapper1  the first mapper.
     * @param mapper2  the second mapper.
     * @param mapper3  the third mapper.
     * @param mapper4  the forth mapper.
     * @param mapper5  the fifth mapper.
     * @param mapper6  the sixth mapper.
     * @return         the result of the first map result that is not null.
     */
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
        });
    }
}
