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
package functionalj.lens.lenses;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface StringLens<HOST>
        extends
            StringAccess<HOST>,
            AnyLens<HOST, String> {
    
    public static <HOST> StringLens<HOST> of(LensSpec<HOST, String> spec) {
        return () -> spec;
    }
    
    
    @Override
    default String apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    @Override
    default HOST apply(HOST host, String data) {
        return lensSpec().getWrite().apply(host, data);
    }
    
    default String read(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    default Func1<HOST, HOST> changeTo(String data) {
        return host -> lensSpec().getWrite().apply(host, data);
    }
    default Func1<HOST, HOST> changeTo(Supplier<String> supplier) {
        return host -> lensSpec().getWrite().apply(host, supplier.get());
    }
    default Func1<HOST, HOST> changeTo(Function<String, String> function) {
        return host -> lensSpec().getWrite().apply(host, function.apply(read(host)));
    }
    default Func1<HOST, HOST> changeTo(BiFunction<HOST, String, String> function) {
        return host -> lensSpec().getWrite().apply(host, function.apply(host, read(host)));
    }
    
}
