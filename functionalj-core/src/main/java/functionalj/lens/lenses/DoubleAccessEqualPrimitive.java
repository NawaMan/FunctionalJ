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

import static functionalj.lens.lenses.DoubleAccess.equalPrecisionToUse;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import lombok.NonNull;
import lombok.val;

public class DoubleAccessEqualPrimitive extends DoubleAccessEqual<Double> implements DoubleToBooleanAccessPrimitive {
    
    final DoubleBinaryOperator anotherValueFunction;
    
    DoubleAccessEqualPrimitive(boolean isNegate, @NonNull DoubleToDoubleAccessPrimitive access, @NonNull DoubleBinaryOperator anotherValueFunction) {
        super(isNegate, access, (host, value) -> anotherValueFunction.applyAsDouble(host, value));
        this.anotherValueFunction = anotherValueFunction;
    }
    
    @Override
    public boolean test(double host) {
        val value = access.applyAsDouble(host);
        val anotherValue = anotherValueFunction.applyAsDouble(host, value);
        val error = Math.abs(value - anotherValue);
        val precision = equalPrecisionToUse.get().getAsDouble();
        return isNegate != (error <= precision);
    }
    
    @Override
    public boolean applyDoubleToBoolean(double host) {
        return test(host);
    }
    
    public DoubleAccessEqualPrecisionPrimitive withIn(double precision) {
        return new DoubleAccessEqualPrecisionPrimitive(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecisionPrimitive withPrecision(double precision) {
        return new DoubleAccessEqualPrecisionPrimitive(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecisionPrimitive withPrecision(@NonNull DoubleSupplier precisionSupplier) {
        return new DoubleAccessEqualPrecisionPrimitive(this, error -> precisionSupplier.getAsDouble());
    }
    
    public DoubleAccessEqualPrecisionPrimitive withPrecision(@NonNull DoubleUnaryOperator precisionFunction) {
        return new DoubleAccessEqualPrecisionPrimitive(this, precisionFunction);
    }
}
