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
import java.util.function.Predicate;

import functionalj.function.Func1;
import lombok.val;

@SuppressWarnings("javadoc")
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
    
    
    public boolean test(HOST host);
    
    public Boolean applyUnsafe(HOST host) throws Exception;

    
    @Override
    public default BooleanAccess<HOST> newAccess(Function<HOST, Boolean> accessToValue) {
        return of(accessToValue);
    }
    
    public default BooleanAccessPrimitive<HOST> nagate() {
        return booleanPrimitiveAccess(false, bool -> !bool);
    }
    public default BooleanAccessPrimitive<HOST> or(boolean anotherBoolean) {
        return booleanPrimitiveAccess(false, bool -> bool || anotherBoolean);
    }
    public default BooleanAccessPrimitive<HOST> and(boolean anotherBoolean) {
        return booleanPrimitiveAccess(false, bool -> bool && anotherBoolean);
    }
    public default BooleanAccessPrimitive<HOST> or(Predicate<? super HOST> anotherPredicate) {
        return host -> {
            boolean bool1 = test(host);
            return bool1 || anotherPredicate.test(host);
        };
    }
    public default BooleanAccessPrimitive<HOST> and(Predicate<? super HOST> anotherPredicate) {
        return host -> {
            boolean bool1 = test(host);
            return bool1 && anotherPredicate.test(host);
        };
    }
    
}

