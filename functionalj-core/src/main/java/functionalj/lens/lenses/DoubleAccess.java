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

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import functionalj.function.Func1;
import lombok.val;

@SuppressWarnings("javadoc")
public interface DoubleAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Double, DoubleAccess<HOST>>, 
                        ToDoubleFunction<HOST>, 
                        ConcreteAccess<HOST, Double, DoubleAccess<HOST>> {
    
    public static <H> DoubleAccess<H> of(Function<H, Double> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof DoubleAccess) {
            return (DoubleAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToDoubleFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToDoubleFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Double>)accessToValue;
            val access = (DoubleAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }

        val func   = (Function<H, Double>)accessToValue;
        val access = (DoubleAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> DoubleAccess<H> ofPrimitive(ToDoubleFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (DoubleAccessPrimitive<H>)accessToValue::applyAsDouble;
        return access;
    }

    
    public double applyAsDouble(HOST host);
    
    public Double applyUnsafe(HOST host) throws Exception;

    
    @Override
    public default DoubleAccess<HOST> newAccess(Function<HOST, Double> accessToValue) {
        return of(accessToValue);
    }
    
    public default MathOperators<Double> __mathOperators() {
        return DoubleMathOperators.instance;
    }
    
}
