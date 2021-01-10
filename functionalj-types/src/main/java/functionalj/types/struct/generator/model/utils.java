// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct.generator.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Stream;

import lombok.val;


class utils {
    
    static <IN extends List<? extends OUT>, OUT> Function<? super IN, Stream<? extends OUT>> allLists() {
        return it -> it.stream();
    }
    
    static <IN> Function<IN, Stream<IN>> delimitWith(IN delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter, in);
        };
    }
    
    static <TYPE> Function<TYPE, TYPE> themAll() {
        return it -> it;
    }
    
    static <I> Function<I, String> toStr() {
        return (i) -> stringOf(i);
    }
    
    static String stringOf(Object obj) {
        return (obj == null) ? null : String.valueOf(obj);
    }
    
    static String prefixWith(String str, String prefix) {
        if (str == null)
            return null;
        if (str.isEmpty())
            return null;
        return prefix + str;
    }
    
    static <I> Function<I, String> wrapWith(String prefix, String suffix) {
        return in -> prefix + in + suffix;
    }
    
}
