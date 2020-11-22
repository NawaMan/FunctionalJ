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
package functionalj.ref;

import static functionalj.ref.WhenAbsent.UseDefault;

import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.result.Result;
import lombok.val;

public class RefBuilder<DATA> {
    
    private final Class<DATA>    dataClass;
    private final Supplier<DATA> elseSupplier;
    
    RefBuilder(Class<DATA> dataClass) {
        this(dataClass, null);
    }
    RefBuilder(Class<DATA> dataClass, Supplier<DATA> elseSupplier) {
        this.dataClass    = dataClass;
        this.elseSupplier = elseSupplier;
    }
    
    public Ref<DATA> orTypeDefault() {
        return orTypeDefaultOrGet(null);
    }
    public Ref<DATA> orTypeDefaultOrGet(Supplier<DATA> manualDefault) {
        return defaultToTypeDefault()
                .whenAbsentUseDefaultOrGet(manualDefault);
    }
    
    public RefBuilder<DATA> whenAbsentUse(DATA defaultValue) {
        return new RefBuilder<DATA>(dataClass, WhenAbsent.Use(defaultValue));
    }
    public RefBuilder<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
        return new RefBuilder<DATA>(dataClass, WhenAbsent.Get(defaultSupplier));
    }
    public RefBuilder<DATA> whenAbsentUseTypeDefault() {
        return new RefBuilder<DATA>(dataClass, WhenAbsent.UseDefault(dataClass));
    }
    
    public Ref<DATA> defaultToNull() {
        @SuppressWarnings("unchecked")
        var result = (Result<DATA>)Result.ofNull();
        var ref    = new RefOf.FromResult<>(dataClass, result, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultTo(DATA value) {
        var result = Result.valueOf(value);
        var ref    = new RefOf.FromResult<>(dataClass, result, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultFrom(Ref<DATA> anotherRef) {
        var ref = new RefOf.FromRef<DATA>(dataClass, anotherRef, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultFrom(Func0<DATA> supplier) {
        var ref = new RefOf.FromSupplier<DATA>(dataClass, supplier, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultToTypeDefault() {
        return new RefBuilder<DATA>(dataClass, UseDefault(dataClass))
                .defaultFrom(UseDefault(dataClass));
    }
    
    
    public Ref<DATA> dictateToNull() {
        var ref = defaultToNull();
        return ref.dictate();
    }
    public Ref<DATA> dictateTo(DATA value) {
        var ref = defaultTo(value);
        return ref.dictate();
    }
    public Ref<DATA> dictateFrom(Func0<DATA> supplier) {
        var ref = defaultFrom(supplier);
        return ref.dictate();
    }
    
}
