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

import static functionalj.ref.WhenAbsent.UseDefault;
import java.util.function.Supplier;
import functionalj.function.Func0;
import functionalj.function.Traced;
import functionalj.result.Result;
import functionalj.supportive.CallerId;
import lombok.val;

public class RefBuilder<DATA> {
    
    private final String toString;
    
    private final Class<DATA> dataClass;
    
    private final Supplier<DATA> elseSupplier;
    
    RefBuilder(String toString, Class<DATA> dataClass) {
        this(toString, dataClass, null);
    }
    
    RefBuilder(String toString, Class<DATA> dataClass, Supplier<DATA> elseSupplier) {
        this.toString     = (toString != null) ? toString : CallerId.instance.trace(Traced::extractLocationString);
        this.dataClass    = dataClass;
        this.elseSupplier = elseSupplier;
    }
    
    public Ref<DATA> orTypeDefault() {
        return orTypeDefaultOrGet(null);
    }
    
    public Ref<DATA> orTypeDefaultOrGet(Supplier<DATA> manualDefault) {
        return defaultToTypeDefault().whenAbsentUseDefaultOrGet(manualDefault);
    }
    
    public RefBuilder<DATA> whenAbsentUse(DATA defaultValue) {
        return new RefBuilder<DATA>(toString, dataClass, WhenAbsent.Use(defaultValue));
    }
    
    public RefBuilder<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
        return new RefBuilder<DATA>(toString, dataClass, WhenAbsent.Get(defaultSupplier));
    }
    
    public RefBuilder<DATA> whenAbsentReferTo(Ref<DATA> sourceRef) {
        return new RefBuilder<DATA>(toString, dataClass, WhenAbsent.Get(sourceRef::get));
    }
    
    public RefBuilder<DATA> whenAbsentUseTypeDefault() {
        return new RefBuilder<DATA>(toString, dataClass, WhenAbsent.UseDefault(dataClass));
    }
    
    public Ref<DATA> defaultToNull() {
        @SuppressWarnings("unchecked")
        val result = (Result<DATA>) Result.ofNull();
        val ref = new RefOf.FromResult<>(toString, dataClass, result, elseSupplier);
        return ref;
    }
    
    public Ref<DATA> defaultTo(DATA value) {
        val result = Result.valueOf(value);
        val ref = new RefOf.FromResult<>(toString, dataClass, result, elseSupplier);
        return ref;
    }
    
    public Ref<DATA> defaultFrom(Ref<DATA> anotherRef) {
        val ref = new RefOf.FromRef<DATA>(toString, dataClass, anotherRef, elseSupplier);
        return ref;
    }
    
    public Ref<DATA> defaultFrom(Func0<DATA> supplier) {
        val ref = new RefOf.FromSupplier<DATA>(toString, dataClass, supplier, elseSupplier);
        return ref;
    }
    
    public Ref<DATA> defaultToTypeDefault() {
        return new RefBuilder<DATA>(toString, dataClass, UseDefault(dataClass)).defaultFrom(UseDefault(dataClass));
    }
    
    public Ref<DATA> dictateToNull() {
        val ref = defaultToNull();
        return ref.dictate();
    }
    
    public Ref<DATA> dictateTo(DATA value) {
        val ref = defaultTo(value);
        return ref.dictate();
    }
    
    public Ref<DATA> dictateFrom(Func0<DATA> supplier) {
        val ref = defaultFrom(supplier);
        return ref.dictate();
    }
}
