//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.annotations.struct.generator;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.val;

class utils {
    
    static <IN> Function<IN, Stream<IN>> delimitWith(IN delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter, in);
        };
    }
    
    static <IN> Function<IN, Stream<IN>> delimitWith(Supplier<? extends IN> delimiter) {
        val isFirst = new AtomicBoolean(true);
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
        val name = getter.getName();
        return "with" + name.substring(0,1).toUpperCase() + name.substring(1);
    }
    
    @SafeVarargs
    static <T> List<T> listOf(T ... ts) {
        return stream(ts)
                .filter(Objects::nonNull)
                .collect(toList());
    }
    
    @SafeVarargs
    static <T> List<T> listOf(Stream<T> ... ts) {
        return stream(ts)
                .filter(Objects::nonNull)
                .flatMap(themAll())
                .filter(Objects::nonNull)
                .collect(toList());
    }

}
