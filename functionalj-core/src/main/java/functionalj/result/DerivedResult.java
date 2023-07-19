// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import functionalj.function.Func1;

public class DerivedResult<DATA> extends Result<DATA> {
    
    private final Func0<Object> dataSupplier;
    
    public DerivedResult(Func0<DATA> dataSupplier) {
        this.dataSupplier = f(() -> {
            try {
                return dataSupplier.applySafely().__valueData();
            } catch (Exception e) {
                return new ExceptionHolder(e);
            }
        }).memoize();
    }
    
    public <ORG> DerivedResult(Result<ORG> orgValue, Func1<Result<ORG>, Result<DATA>> mapper) {
        this.dataSupplier = f(() -> {
            try {
                return mapper.applyUnsafe(orgValue).__valueData();
            } catch (Exception e) {
                return new ExceptionHolder(e);
            }
        }).memoize();
    }
    
    @Override
    Object __valueData() {
        return dataSupplier.get();
    }
}
