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
package functionalj.ref;

import defaultj.api.IProvideDefault;
import functionalj.function.Func0;
import functionalj.result.Result;
import lombok.val;

public class RefTo<DATA> extends Ref<DATA> {
    
    public static final Ref<IProvideDefault> defaultProvider = Ref.of(IProvideDefault.class).defaultFrom(IProvideDefault.defaultProvider()::get).whenAbsentGet(IProvideDefault.defaultProvider()::get);
    
    private final int hashCode;
    
    RefTo(Class<DATA> dataClass) {
        super(dataClass, null);
        hashCode = dataClass.hashCode();
    }
    
    @Override
    protected Result<DATA> findResult() {
        val result = Result.of(() -> {
            val provider = defaultProvider.value();
            val dataType = getDataType();
            val value = provider.get(dataType);
            return (DATA) value;
        });
        return result;
    }
    
    final Ref<DATA> whenAbsent(Func0<DATA> defaultSupplier) {
        // No effect
        return this;
    }
    
    public final int hashCode() {
        return hashCode;
    }
    
    public final boolean equals(Object another) {
        if (this == another)
            return true;
        if (another == null)
            return false;
        if (!(another instanceof RefOf))
            return false;
        @SuppressWarnings("unchecked")
        val anotherRef = (RefOf<DATA>) another;
        if (!anotherRef.getDataType().equals(this.getDataType()))
            return false;
        return this.hashCode() == another.hashCode();
    }
}
