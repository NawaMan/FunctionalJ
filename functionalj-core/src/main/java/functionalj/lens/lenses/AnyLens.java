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
        var spec  = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        
        var read  = spec.getRead();
        var value = read.apply(host);
        return value;
    }
    
    @Override
    default HOST apply(HOST host, DATA data) {
        var spec  = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        
        var write    = spec.getWrite();
        var newValue = write.apply(host, data);
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
            var newValue = dataSupplier.get();
            return apply(host, newValue);
        };
    }
    default Func1<HOST, HOST> changeTo(Function<DATA, DATA> dataMapper) {
        return host -> {
            var oldValue = read(host);
            var newValue = dataMapper.apply(oldValue);
            return apply(host, newValue);
        };
    }
    default Func1<HOST, HOST> changeTo(BiFunction<HOST, DATA, DATA> mapper) {
        return host -> {
            var oldValue = read(host);
            var newValue = mapper.apply(host, oldValue);
            return apply(host, newValue);
        };
    }
    
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, DATA data) {
        return host -> {
            var originalData = apply(host);
            var shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            var newData = data;
            return apply(host, newData);
        };
    }
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, Supplier<DATA> dataSupplier) {
        return host -> {
            var originalData = apply(host);
            var shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            var newData = dataSupplier.get();
            return apply(host, newData);
        };
    }
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, Function<DATA, DATA> dataMapper) {
        return host -> {
            var originalData = apply(host);
            var shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            var newData = dataMapper.apply(originalData);
            return apply(host, newData);
        };
    }
    default Func1<HOST, HOST> changeOnly(Predicate<DATA> check, BiFunction<HOST, DATA, DATA> mapper) {
        return host -> {
            var originalData = apply(host);
            var shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            
            var newData = mapper.apply(host, originalData);
            return apply(host, newData);
        };
    }
    
}
