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

import java.util.function.BooleanSupplier;
import java.util.function.IntPredicate;

import lombok.val;

@FunctionalInterface
public interface IntegerToBooleanAccessPrimitive 
                    extends 
                        BooleanAccessPrimitive<Integer>, 
                        IntPredicate {
    
    //== abstract functionalities ==
    
    public boolean applyIntToBoolean(int host);
    
    
    //== default functionalities ==
    
    @Override
    public default boolean test(int value) {
        return applyIntToBoolean(value);
    }
    
    @Override
    public default boolean test(Integer host) {
        return applyIntToBoolean(host);
    }
    
    public default boolean applyAsBoolean(int operand) {
        return applyIntToBoolean(operand);
    }
    
    public default boolean applyAsBoolean(Integer host) {
        return applyIntToBoolean(host);
    }
    
    //== Functionality ==
    
    @Override
    public default IntegerToBooleanAccessPrimitive negate() {
        return host -> {
            val boolValue = test(host);
            return !boolValue;
        };
    }
    
    
    public default IntegerToBooleanAccessPrimitive or(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue || anotherBoolean;
        };
    }
    public default IntegerToBooleanAccessPrimitive or(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue || anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive or(IntegerToBooleanAccessPrimitive anotherAccess) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue || anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive and(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue && anotherBoolean;
        };
    }
    public default IntegerToBooleanAccessPrimitive and(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue && anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive and(IntegerToBooleanAccessPrimitive anotherAccess) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue && anotherValue;
        };
    }
    
}
