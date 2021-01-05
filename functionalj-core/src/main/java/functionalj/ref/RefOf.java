// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Random;
import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.result.Result;
import lombok.val;


public abstract class RefOf<DATA> extends Ref<DATA> {
    
    private static final Random random = new Random();
    
    private final int hashCode = random.nextInt();
    
    public RefOf(Class<DATA> dataClass) {
        super(dataClass, null);
    }
    RefOf(Class<DATA> dataClass, Supplier<DATA> elseSupplier) {
        super(dataClass, elseSupplier);
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
        val anotherRef = (RefOf<DATA>)another;
        if (!anotherRef.getDataType().equals(this.getDataType()))
            return false;
        
        return this.hashCode() == another.hashCode();
    }
    
    //== Sub classes ==
    
    public static class FromResult<DATA> extends RefOf<DATA> {
        
        private final Result<DATA> result;
        
        FromResult(Class<DATA> dataClass, Result<DATA> result, Supplier<DATA> elseSupplier) {
            super(dataClass, elseSupplier);
            this.result = (result != null)
                    ? result
                    : Result.ofNull();
        }
        
        @Override
        protected final Result<DATA> findResult() {
            return result;
        }
        
        protected Ref<DATA> whenAbsent(Func0<DATA> whenAbsent) {
            return new RefOf.FromResult<>(getDataType(), result, whenAbsent);
        }
    }
    
    public static class FromSupplier<DATA> extends RefOf<DATA> {
        
        @SuppressWarnings("rawtypes")
        private static final Func0 notExist = ()->Result.ofNotExist();
        
        private final Func0<DATA> supplier;
        
        @SuppressWarnings("unchecked")
        FromSupplier(Class<DATA> dataClass, Func0<DATA> supplier, Supplier<DATA> elseSupplier) {
            super(dataClass, elseSupplier);
            this.supplier = (supplier != null)
                    ? supplier
                    : notExist;
        }
        
        @Override
        protected final Result<DATA> findResult() {
            val result = Result.of(supplier);
            return result;
        }
        
        protected Ref<DATA> whenAbsent(Func0<DATA> whenAbsent) {
            return new RefOf.FromSupplier<>(getDataType(), supplier, whenAbsent);
        }
        
    }
    
    public static class FromRef<DATA> extends RefOf<DATA> {
        
        private final Ref<DATA> anotherRef;
        
        FromRef(Class<DATA> dataClass, Ref<DATA> anotherRef, Supplier<DATA> elseSupplier) {
            super(dataClass, elseSupplier);
            this.anotherRef = anotherRef;
        }
        
        @Override
        protected final Result<DATA> findResult() {
            val result = Result.of(anotherRef.valueSupplier()).whenAbsentGet(whenAbsentSupplier);
            return result;
        }
        
        protected Ref<DATA> whenAbsent(Func0<DATA> whenAbsent) {
            return new RefOf.FromRef<>(getDataType(), anotherRef, whenAbsent);
        }
    }
    
}
