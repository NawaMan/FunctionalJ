// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import lombok.val;

@FunctionalInterface
public interface DoubleToBooleanAccessPrimitive extends BooleanAccessPrimitive<Double>, DoublePredicate {
    
    // == abstract functionalities ==
    public boolean applyDoubleToBoolean(double host);
    
    // == default functionalities ==
    @Override
    public default boolean test(double value) {
        return applyDoubleToBoolean(value);
    }
    
    @Override
    public default boolean test(Double host) {
        return applyDoubleToBoolean(host);
    }
    
    public default boolean applyAsBoolean(double operand) {
        return applyDoubleToBoolean(operand);
    }
    
    public default boolean applyAsBoolean(Double host) {
        return applyDoubleToBoolean(host);
    }
    
    // == Functionality ==
    @Override
    public default DoubleToBooleanAccessPrimitive negate() {
        return host -> {
            val boolValue = test(host);
            return !boolValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive or(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue || anotherBoolean;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive or(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue || anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive or(DoubleToBooleanAccessPrimitive anotherAccess) {
        return host -> {
            val boolValue = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue || anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive and(boolean anotherBoolean) {
        return host -> {
            val boolValue = test(host);
            return boolValue && anotherBoolean;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive and(BooleanSupplier anotherSupplier) {
        return host -> {
            val boolValue = test(host);
            val anotherValue = anotherSupplier.getAsBoolean();
            return boolValue && anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive and(DoubleToBooleanAccessPrimitive anotherAccess) {
        return host -> {
            val boolValue = test(host);
            val anotherValue = anotherAccess.apply(host);
            return boolValue && anotherValue;
        };
    }
    
    // TODO -Select Obj ... make sure we can put the lens of that object after.
    public default DoubleToIntegerAccessPrimitive selectInt(int choiceTrue, int choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue : choiceFalse;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive selectInt(DoubleToIntFunction choiceTrue, DoubleToIntFunction choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue.applyAsInt(host) : choiceFalse.applyAsInt(host);
        };
    }
    
    public default DoubleToLongAccessPrimitive selectLong(long choiceTrue, long choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue : choiceFalse;
        };
    }
    
    public default DoubleToLongAccessPrimitive selectLong(DoubleToLongFunction choiceTrue, DoubleToLongFunction choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue.applyAsLong(host) : choiceFalse.applyAsLong(host);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive selectDouble(double choiceTrue, double choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue : choiceFalse;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive selectDouble(DoubleUnaryOperator choiceTrue, DoubleUnaryOperator choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue.applyAsDouble(host) : choiceFalse.applyAsDouble(host);
        };
    }
    
    public default DoubleToStringAccessPrimitive selectDouble(String choiceTrue, String choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue : choiceFalse;
        };
    }
    
    public default DoubleToStringAccessPrimitive selectDouble(DoubleFunction<String> choiceTrue, DoubleFunction<String> choiceFalse) {
        return host -> {
            val boolValue = test(host);
            return boolValue ? choiceTrue.apply(host) : choiceFalse.apply(host);
        };
    }
}
