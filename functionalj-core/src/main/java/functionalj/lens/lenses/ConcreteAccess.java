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
package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.Supplier;
import functionalj.lens.core.AccessCreator;

/*
This is (unexpected) work around for the reusability problems for these common methods whose are SELF typed.
This way, we just need to duplicate these methods here and AnyAccess.
All concrete access still have to implement this interface and the newAccess method.
 */
public interface ConcreteAccess<HOST, DATA, ACCESS extends AnyAccess<HOST, DATA>> extends AnyAccess<HOST, DATA>, AccessCreator<HOST, DATA, ACCESS> {
    
    public ACCESS newAccess(Function<HOST, DATA> accessToValue);
    
    // We unfortunately have to duplicate this.
    public default ACCESS orDefaultTo(DATA fallbackValue) {
        return newAccess(__internal__.orDefaultTo(this, fallbackValue));
    }
    
    public default ACCESS orDefaultFrom(Supplier<? extends DATA> fallbackValueSupplier) {
        return newAccess(__internal__.orDefaultFrom(this, fallbackValueSupplier));
    }
    
    public default <EXCEPTION extends RuntimeException> ACCESS orThrow() {
        return newAccess(__internal__.orThrow(this));
    }
    
    public default <EXCEPTION extends RuntimeException> ACCESS orThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return newAccess(__internal__.orThrow(this, exceptionSupplier));
    }
    
    public default OptionalAccess<HOST, DATA, ACCESS> toOptional() {
        return __internal__.toOptional(this, f -> newAccess(f));
    }
    
    public default NullableAccess<HOST, DATA, ACCESS> toNullable() {
        return __internal__.toNullable(this, f -> newAccess(f));
    }
    
    public default ResultAccess<HOST, DATA, ACCESS> toResult() {
        return __internal__.toResult(this, f -> newAccess(f));
    }
}
