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

import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;


@FunctionalInterface
public interface DoubleToIntegerAccessPrimitive extends IntegerAccessPrimitive<Double>, DoubleToIntFunction, DoubleFunction<Integer> {
    
    public int applyDoubleToInt(double host);
    
    public default int applyAsInt(double operand) {
        return applyDoubleToInt(operand);
    }
    
    public default int applyAsInt(Double host) {
        return applyDoubleToInt(host);
    }
    
    @Override
    public default Integer apply(double host) {
        return applyDoubleToInt(host);
    }
    
    // TODO - Will need to duplicate some of the thing here :-(
    
    public default DoubleToBooleanAccessPrimitive thatIsOdd() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue % 2 != 0;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsEven() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue % 2 == 0;
        };
    }
    public default DoubleToStringAccessPrimitive asString() {
        return host -> {
            int intValue = applyAsInt(host);
            return "" + intValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive factorial() {
        return host -> {
            int intValue = applyAsInt(host);
            if (intValue <= 0) {
                return 1;
            }
            
            // TODO - We should set up a Ref so people can over write this with a better (like faster) method.
            int factorial = 1;
            for (int i = 1; i <= intValue; i++) {
                factorial *= i;
            }
            return factorial;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive toDouble() {
        return host -> {
            int intValue = applyAsInt(host);
            return (double)intValue;
        };
    }
    
}
