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
import java.util.function.LongPredicate;

import lombok.val;

@FunctionalInterface
public interface LongToBooleanAccessPrimitive 
                    extends 
                        BooleanAccessPrimitive<Long>,
                        LongPredicate {
    
    //== abstract functionalities ==
    
    public boolean applyLongToBoolean(long host);
    
    
    //== default functionalities ==
    
    public default boolean test(long value) {
        return applyLongToBoolean(value);
    }
    
    public default boolean test(Long host) {
        return applyLongToBoolean(host);
    }
    
    public default boolean applyAsBoolean(long operand) {
        return applyLongToBoolean(operand);
    }
    
    public default boolean applyAsBoolean(Long host) {
        return applyLongToBoolean(host);
    }
    
    //== Functionality ==
    
    @Override
    public default LongToBooleanAccessPrimitive negate() {
        return host -> {
            val boolValue = test(host);
            return !boolValue;
        };
    }
    
    
    public default LongToBooleanAccessPrimitive or(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue || anotherBoolean;
        };
    }
    public default LongToBooleanAccessPrimitive or(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue || anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive or(LongToBooleanAccessPrimitive anotherAccess) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue || anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive and(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue && anotherBoolean;
        };
    }
    public default LongToBooleanAccessPrimitive and(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue && anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive and(LongToBooleanAccessPrimitive anotherAccess) {
        return host -> {
            val boolValue    = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue && anotherValue;
        };
    }
    
}
