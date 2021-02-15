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

import static functionalj.lens.lenses.DoubleAccess.equalPrecisionToUse;

import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.DoubleLongToDoubleFunction;
import lombok.NonNull;
import lombok.val;

public class LongToDoubleAccessEqualPrimitive extends DoubleAccessEqual<Long> implements LongToBooleanAccessPrimitive {
    
    final DoubleLongToDoubleFunction anotherValueFunction;
    
    LongToDoubleAccessEqualPrimitive(
            boolean isNegate,
            @NonNull LongToDoubleAccessPrimitive access,
            @NonNull DoubleLongToDoubleFunction  anotherValueFunction) {
        super(isNegate, access, (host, value) -> anotherValueFunction.applyAsDouble(host, value));
        this.anotherValueFunction = anotherValueFunction;
    }
    
    @Override
    public boolean test(long host) {
        val value        = access.applyAsDouble(host);
        val anotherValue = anotherValueFunction.applyAsDouble(host, value);
        val error        = Math.abs(value - anotherValue);
        val precision    = equalPrecisionToUse.get().getAsDouble();
        return isNegate != (error <= precision);
    }
    
    @Override
    public boolean applyLongToBoolean(long host) {
        return test(host);
    }
    
    public LongToDoubleAccessEqualPrecisionPrimitive withIn(double precision) {
        return new LongToDoubleAccessEqualPrecisionPrimitive(this, error -> precision);
    }
    
    public LongToDoubleAccessEqualPrecisionPrimitive withPrecision(double precision) {
        return new LongToDoubleAccessEqualPrecisionPrimitive(this, error -> precision);
    }
    
    public LongToDoubleAccessEqualPrecisionPrimitive withPrecision(@NonNull DoubleSupplier precisionSupplier) {
        return new LongToDoubleAccessEqualPrecisionPrimitive(this, error -> precisionSupplier.getAsDouble());
    }
    
    public LongToDoubleAccessEqualPrecisionPrimitive withPrecision(@NonNull DoubleUnaryOperator precisionFunction) {
        return new LongToDoubleAccessEqualPrecisionPrimitive(this, precisionFunction);
    }
    
}
