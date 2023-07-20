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

import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import functionalj.function.ObjectDoubleToDoubleFunctionPrimitive;
import lombok.NonNull;
import lombok.val;

public class DoubleAccessEqual<HOST> implements BooleanAccessPrimitive<HOST> {
    
    final boolean isNegate;
    
    final DoubleAccess<HOST> access;
    
    final ObjectDoubleToDoubleFunctionPrimitive<HOST> anotherValueFunction;
    
    DoubleAccessEqual(boolean isNegate, @NonNull DoubleAccess<HOST> access, @NonNull ObjectDoubleToDoubleFunctionPrimitive<HOST> anotherValueFunction) {
        this.isNegate = isNegate;
        this.access = access;
        this.anotherValueFunction = anotherValueFunction;
    }
    
    public boolean test(HOST host) {
        val value = access.applyAsDouble(host);
        val anotherValue = anotherValueFunction.applyAsDouble(host, value);
        val error = Math.abs(value - anotherValue);
        val precision = DoubleAccess.equalPrecisionToUse.get().getAsDouble();
        return isNegate != (error <= precision);
    }
    
    public DoubleAccessEqualPrecision<HOST> withIn(double precision) {
        return new DoubleAccessEqualPrecision<>(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecision<HOST> withPrecision(double precision) {
        return new DoubleAccessEqualPrecision<>(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecision<HOST> withPrecision(@NonNull DoubleSupplier precisionSupplier) {
        return new DoubleAccessEqualPrecision<>(this, error -> precisionSupplier.getAsDouble());
    }
    
    public DoubleAccessEqualPrecision<HOST> withPrecision(@NonNull DoubleUnaryOperator precisionFunction) {
        return new DoubleAccessEqualPrecision<>(this, precisionFunction);
    }
}
