// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.math.BigDecimal;
import java.math.BigInteger;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;

public class IntMathOperators implements MathOperators<Integer> {
    
    public static MathOperators<Integer> instance = new IntMathOperators();
    
    @Override
    public Integer zero() {
        return 0;
    }
    
    @Override
    public Integer one() {
        return 1;
    }
    
    @Override
    public Integer minusOne() {
        return -1;
    }
    
    @Override
    public Integer asInteger(Integer number) {
        return (number == null) ? 0 : number.intValue();
    }
    
    @Override
    public Long asLong(Integer number) {
        return (long) asInteger(number);
    }
    
    @Override
    public Double asDouble(Integer number) {
        return (double) asInteger(number);
    }
    
    @Override
    public BigInteger asBigInteger(Integer number) {
        return BigInteger.valueOf(asInteger(number));
    }
    
    @Override
    public BigDecimal asBigDecimal(Integer number) {
        return new BigDecimal(asInteger(number));
    }
    
    @Override
    public Integer add(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return v1 + v2;
    }
    
    @Override
    public Integer subtract(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return v1 - v2;
    }
    
    @Override
    public Integer multiply(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return v1 * v2;
    }
    
    @Override
    public Integer divide(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return v1 / v2;
    }
    
    @Override
    public Integer remainder(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return v1 % v2;
    }
    
    @Override
    public Tuple2<Integer, Integer> divideAndRemainder(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return Tuple.of(v1 / v2, v1 % v2);
    }
    
    @Override
    public Integer pow(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return (int) Math.pow(v1, v2);
    }
    
    @Override
    public Integer abs(Integer number) {
        int v = (number == null) ? 0 : number.intValue();
        return Math.abs(v);
    }
    
    @Override
    public Integer negate(Integer number) {
        int v = (number == null) ? 0 : number.intValue();
        return Math.negateExact(v);
    }
    
    @Override
    public Integer signum(Integer number) {
        int v = (number == null) ? 0 : number.intValue();
        return (int) Math.signum(v);
    }
    
    @Override
    public Integer min(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return Math.min(v1, v2);
    }
    
    @Override
    public Integer max(Integer number1, Integer number2) {
        int v1 = (number1 == null) ? 0 : number1.intValue();
        int v2 = (number2 == null) ? 0 : number2.intValue();
        return Math.max(v1, v2);
    }
}
