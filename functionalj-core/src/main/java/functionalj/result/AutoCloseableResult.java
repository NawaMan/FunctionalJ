// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.result;

import static functionalj.function.Func.f;
import functionalj.function.Func0;

public class AutoCloseableResult<DATA extends AutoCloseable> extends DerivedResult<DATA> implements AutoCloseable {
    
    public static <D extends AutoCloseable> AutoCloseableResult<D> valueOf(D value) {
        return new AutoCloseableResult<D>(() -> value);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes", "resource" })
    public static <D extends AutoCloseable> AutoCloseableResult<D> from(Result<D> result) {
        if (result instanceof AutoCloseableResult)
            return (AutoCloseableResult) result;
        return new AutoCloseableResult(() -> result.get());
    }
    
    AutoCloseableResult(Func0<DATA> dataSupplier) {
        super(dataSupplier);
    }
    
    @Override
    public void close() throws Exception {
        ifPresent(f(value -> {
            value.close();
        }));
    }
}
