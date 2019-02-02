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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import nawaman.nullablej.nullable.Nullable;

class BigIntegerAccessConstants {
    public static final BigInteger MINUS_ONE = BigInteger.ZERO.subtract(BigInteger.ONE);
}

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface BigIntegerAccess<HOST> 
        extends 
            NumberAccess<HOST, BigInteger, BigIntegerAccess<HOST>>, 
            ConcreteAccess<HOST, BigInteger, BigIntegerAccess<HOST>> {
    
    @Override
    public default BigIntegerAccess<HOST> newAccess(Function<HOST, BigInteger> access) {
        return access::apply;
    }
    
    
    public default MathOperators<BigInteger> __mathOperators() {
        return __BigIntegerMathOperators;
    }
    
    public static MathOperators<BigInteger> __BigIntegerMathOperators = new MathOperators<BigInteger>() {

        @Override
        public BigInteger zero() {
            return BigInteger.ZERO;
        }
        @Override
        public BigInteger one() {
            return BigInteger.ONE;
        }
        @Override
        public BigInteger minusOne() {
            return BigIntegerAccessConstants.MINUS_ONE;
        }
        @Override
        public Integer toInt(BigInteger number) {
            return toBigInteger(number).intValue();
        }
        @Override
        public Long toLong(BigInteger number) {
            return toBigInteger(number).longValue();
        }
        @Override
        public Double toDouble(BigInteger number) {
            return toBigInteger(number).doubleValue();
        }
        @Override
        public BigInteger toBigInteger(BigInteger number) {
            return Nullable.of(number).orElse(BigInteger.ZERO);
        }
        @Override
        public BigDecimal toBigDecimal(BigInteger number) {
            return new BigDecimal(toBigInteger(number));
        }
        
        @Override
        public BigInteger add(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.add(v2);
        }
        @Override
        public BigInteger subtract(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.subtract(v2);
        }
        @Override
        public BigInteger multiply(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.multiply(v2);
        }
        @Override
        public BigInteger divide(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.divide(v2);
        }
        @Override
        public BigInteger remainder(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.remainder(v2);
        }

        @Override
        public Tuple2<BigInteger, BigInteger> divideAndRemainder(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            BigInteger[] rs = v1.divideAndRemainder(v2);
            return Tuple.of(rs[0], rs[1]);
        }

        @Override
        public BigInteger pow(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.pow(v2.intValue());
        }

        @Override
        public BigInteger abs(BigInteger number) {
            BigInteger v = (number == null) ? BigInteger.ZERO : number;
            return v.abs();
        }
        @Override
        public BigInteger negate(BigInteger number) {
            BigInteger v = (number == null) ? BigInteger.ZERO : number;
            return v.negate();
        }
        @Override
        public BigInteger signum(BigInteger number) {
            BigInteger v = (number == null) ? BigInteger.ZERO : number;
            return BigInteger.valueOf(v.signum());
        }

        @Override
        public BigInteger min(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.min(v2);
        }
        @Override
        public BigInteger max(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.max(v2);
        }
        
    };
    
}
