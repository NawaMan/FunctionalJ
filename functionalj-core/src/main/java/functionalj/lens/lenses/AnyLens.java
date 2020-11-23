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
package functionalj.lens.lenses;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.WriteLens;
import lombok.val;


@FunctionalInterface
public interface AnyLens<HOST, DATA> extends AnyAccess<HOST, DATA>, WriteLens<HOST, DATA> {
    
    public static <T> AnyLens<T, T> of(LensSpec<T, T> spec) {
        return () -> spec;
    }
    
    
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA applyUnsafe(HOST host) throws Exception {
        val spec  = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        
        val read  = spec.getRead();
        val value = read.apply(host);
        return value;
    }
    
    @Override
    default HOST apply(HOST host, DATA data) {
        val spec  = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        
        val write    = spec.getWrite();
        val newValue = write.apply(host, data);
        return newValue;
    }
    
    default DATA read(HOST host) {
        return apply(host);
    }
    
    default Func1<HOST, HOST> changeTo(DATA data) {
        return host -> {
            return apply(host, data);
        };
    }
    default Func1<HOST, HOST> changeTo(Supplier<DATA> dataSupplier) {
        return host -> {
            val newValue = dataSupplier.get();
            return apply(host, newValue);
        };
    }
    default Func1<HOST, HOST> changeTo(Function<DATA, DATA> dataMapper) {
        return host -> {
            val oldValue = read(host);
            val newValue = dataMapper.apply(oldValue);
            return apply(host, newValue);
        };
    }
    default Func1<HOST, HOST> changeTo(BiFunction<HOST, DATA, DATA> mapper) {
        return host -> {
            val oldValue = read(host);
            val newValue = mapper.apply(host, oldValue);
            return apply(host, newValue);
        };
    }
    
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, DATA data) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            val newData = data;
            return apply(host, newData);
        };
    }
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, Supplier<DATA> dataSupplier) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            val newData = dataSupplier.get();
            return apply(host, newData);
        };
    }
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, Function<DATA, DATA> dataMapper) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            val newData = dataMapper.apply(originalData);
            return apply(host, newData);
        };
    }
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, BiFunction<HOST, DATA, DATA> mapper) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            val newData = mapper.apply(host, originalData);
            return apply(host, newData);
        };
    }
    
}
