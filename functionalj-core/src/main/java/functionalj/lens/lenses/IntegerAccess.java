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
import java.util.function.ToIntFunction;

import functionalj.function.Func1;
import lombok.val;

@SuppressWarnings("javadoc")
public interface IntegerAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Integer, IntegerAccess<HOST>>,
                        ToIntFunction<HOST>,
                        ConcreteAccess<HOST, Integer, IntegerAccess<HOST>> {
    
    public static <H> IntegerAccess<H> of(Function<H, Integer> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof IntegerAccess) {
            return (IntegerAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToIntFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToIntFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Integer>)accessToValue;
            val access = (IntegerAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }

        val func   = (Function<H, Integer>)accessToValue;
        val access = (IntegerAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> IntegerAccess<H> ofPrimitive(ToIntFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (IntegerAccessPrimitive<H>)accessToValue::applyAsInt;
        return access;
    }
    
    
    
    public int applyAsInt(HOST host);
    
    public Integer applyUnsafe(HOST host) throws Exception;

    
    @Override
    public default IntegerAccess<HOST> newAccess(Function<HOST, Integer> accessToValue) {
        return of(accessToValue);
    }
    
    
    public default MathOperators<Integer> __mathOperators() {
        return IntMathOperators.instance;
    }
    
    public default IntegerAccess<HOST> bitAnd(int value) {
        return intPrimitiveAccess(0, i -> i & value);
    }
    public default IntegerAccess<HOST> bitOr(int value) {
        return intPrimitiveAccess(0, i -> i | value);
    }
    public default BooleanAccess<HOST> bitAt(int bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return booleanAccess(false, i -> (i & p) != 0);
    }
    
}
