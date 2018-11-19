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
package functionalj.annotations.record.generator.model;

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
