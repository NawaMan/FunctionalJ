// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.lenses.java.time;

import java.time.chrono.IsoEra;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface IsoEraAccess<HOST> extends AnyAccess<HOST, IsoEra>, EraAccess<HOST, IsoEra>, ConcreteAccess<HOST, IsoEra, IsoEraAccess<HOST>> {
    
    public static <H> IsoEraAccess<H> of(Function<H, IsoEra> func) {
        return func::apply;
    }
    
    public default IsoEraAccess<HOST> newAccess(Function<HOST, IsoEra> accessToValue) {
        return accessToValue::apply;
    }
    
    public default IntegerAccessPrimitive<HOST> getValue() {
        return host -> {
            val value = apply(host);
            return value.getValue();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isBce() {
        return host -> {
            val value = apply(host);
            return value == IsoEra.BCE;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isCe() {
        return host -> {
            val value = apply(host);
            return value == IsoEra.CE;
        };
    }
}
