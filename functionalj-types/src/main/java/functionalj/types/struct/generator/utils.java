// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct.generator;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

class utils {
    
    static <IN> Function<IN, Stream<IN>> delimitWith(IN delimiter) {
        AtomicBoolean isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter, in);
        };
    }
    
    static <IN> Function<IN, Stream<IN>> delimitWith(Supplier<? extends IN> delimiter) {
        AtomicBoolean isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter.get(), in);
        };
    }
    
    static <I> Function<I, String> prependWith(String prefix) {
        return in -> prefix + in;
    }
    
    static String stringOf(Object obj) {
        return (obj == null) ? null : String.valueOf(obj);
    }
    
    static Predicate<? super String> strNotNullOrEmpty() {
        return str -> (str != null) && !str.isEmpty();
    }
    
    static <TYPE> Function<TYPE, TYPE> themAll() {
        return it -> it;
    }
    
    static <I> Function<I, String> toStr() {
        return (i) -> stringOf(i);
    }
    
    static String withMethodName(Getter getter) {
        String name = getter.name();
        return "with" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    @SafeVarargs
    static <T> List<T> listOf(T... ts) {
        return stream(ts).filter(Objects::nonNull).collect(toList());
    }
    
    @SafeVarargs
    static <T> List<T> listOf(Stream<T>... ts) {
        return stream(ts).filter(Objects::nonNull).flatMap(themAll()).filter(Objects::nonNull).collect(toList());
    }
}
