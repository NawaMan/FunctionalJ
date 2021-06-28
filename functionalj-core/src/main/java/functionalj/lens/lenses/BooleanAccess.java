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
package functionalj.lens.lenses;

import static java.util.Objects.requireNonNull;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import functionalj.function.Func1;
import lombok.val;


public interface BooleanAccess<HOST> 
                    extends 
                        AnyAccess<HOST, Boolean>, 
                        Predicate<HOST>, 
                        ConcreteAccess<HOST, Boolean, BooleanAccess<HOST>> {
    
    public static <H> BooleanAccess<H> of(Function<H, Boolean> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof BooleanAccess) {
            return (BooleanAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof Predicate) {
            @SuppressWarnings("unchecked")
            val func1  = (Predicate<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Boolean>)accessToValue;
            val access = (BooleanAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }
        
        val func   = (Function<H, Boolean>)accessToValue;
        val access = (BooleanAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> BooleanAccess<H> ofPrimitive(Predicate<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (BooleanAccessPrimitive<H>)accessToValue::test;
        return access;
    }
    
    @Override
    public default BooleanAccess<HOST> newAccess(Function<HOST, Boolean> accessToValue) {
        return of(accessToValue);
    }
    
    //== abstract functionalities ==
    
    public boolean test(HOST host);
    
    public Boolean applyUnsafe(HOST host) throws Exception;
    
    //== Functionality ==
    
    public default BooleanAccessPrimitive<HOST> nagate() {
        return host -> {
            val boolValue = test(host);
            return !boolValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> or(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue || anotherBoolean;
        };
    }
    public default BooleanAccessPrimitive<HOST> or(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue || anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> or(BooleanAccess<HOST> anotherAccess) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue || anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> and(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue && anotherBoolean;
        };
    }
    public default BooleanAccessPrimitive<HOST> and(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue && anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> and(BooleanAccess<HOST> anotherAccess) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue && anotherValue;
        };
    }
    
    // TODO -Select Obj ... make sure we can put the lens of that object after.
    
    public default IntegerAccessPrimitive<HOST> selectInt(int choiceTrue, int choiceFalse) {
        return host -> {
            val boolValue    = test(host);
            return boolValue ? choiceTrue : choiceFalse;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> selectInt(
            ToIntFunction<HOST> choiceTrue, 
            ToIntFunction<HOST> choiceFalse) {
        return host -> {
            val boolValue    = test(host);
            return boolValue 
                    ? choiceTrue.applyAsInt(host)
                    : choiceFalse.applyAsInt(host);
        };
    }
    
    public default LongAccessPrimitive<HOST> selectLong(long choiceTrue, long choiceFalse) {
        return host -> {
            val boolValue    = test(host);
            return boolValue ? choiceTrue : choiceFalse;
        };
    }
    
    public default LongAccessPrimitive<HOST> selectLong(
            ToLongFunction<HOST> choiceTrue, 
            ToLongFunction<HOST> choiceFalse) {
        return host -> {
            val boolValue    = test(host);
            return boolValue 
                    ? choiceTrue.applyAsLong(host)
                    : choiceFalse.applyAsLong(host);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> selectDouble(double choiceTrue, double choiceFalse) {
        return host -> {
            val boolValue    = test(host);
            return boolValue ? choiceTrue : choiceFalse;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> selectDouble(
            ToDoubleFunction<HOST> choiceTrue, 
            ToDoubleFunction<HOST> choiceFalse) {
        return host -> {
            val boolValue    = test(host);
            return boolValue 
                    ? choiceTrue.applyAsDouble(host)
                    : choiceFalse.applyAsDouble(host);
        };
    }
    
}
