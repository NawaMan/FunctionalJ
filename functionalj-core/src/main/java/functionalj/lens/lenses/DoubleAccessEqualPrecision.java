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
package functionalj.lens.lenses;

import static functionalj.lens.lenses.DoubleAccess.equalPrecisionToUse;
import static java.util.Objects.requireNonNull;
import static nullablej.nullable.Nullable.nullable;
import java.util.function.DoubleUnaryOperator;
import lombok.NonNull;
import lombok.val;

public class DoubleAccessEqualPrecision<HOST> implements BooleanAccessPrimitive<HOST> {
    
    final DoubleAccessEqual<HOST> equals;
    
    final DoubleUnaryOperator precisionFromErrorFunction;
    
    public DoubleAccessEqualPrecision(@NonNull DoubleAccessEqual<HOST> equals, @NonNull DoubleUnaryOperator precisionFromErrorFunction) {
        this.equals = requireNonNull(equals);
        this.precisionFromErrorFunction = nullable(precisionFromErrorFunction).orElse((error) -> equalPrecisionToUse.get().getAsDouble());
    }
    
    @Override
    public boolean test(HOST host) {
        val value = equals.access.applyAsDouble(host);
        val anotherValue = equals.anotherValueFunction.applyAsDouble(host, value);
        val error = Math.abs(value - anotherValue);
        val precision = precisionFromErrorFunction.applyAsDouble(error);
        return equals.isNegate != (error <= precision);
    }
}
