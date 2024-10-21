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
import functionalj.tuple.Tuple2;

public interface MathOperators<NUMBER> {
    
    public NUMBER zero();
    
    public NUMBER one();
    
    public NUMBER minusOne();
    
    public Integer asInteger(NUMBER number);
    
    public Long asLong(NUMBER number);
    
    public Double asDouble(NUMBER number);
    
    public BigInteger asBigInteger(NUMBER number);
    
    public BigDecimal asBigDecimal(NUMBER number);
    
    public NUMBER add(NUMBER number1, NUMBER number2);
    
    public NUMBER subtract(NUMBER number1, NUMBER number2);
    
    public NUMBER multiply(NUMBER number1, NUMBER number2);
    
    public NUMBER divide(NUMBER number1, NUMBER number2);
    
    public NUMBER remainder(NUMBER number1, NUMBER number2);
    
    public Tuple2<NUMBER, NUMBER> divideAndRemainder(NUMBER number, NUMBER divisor);
    
    public NUMBER pow(NUMBER number, NUMBER n);
    
    public NUMBER abs(NUMBER number);
    
    public NUMBER negate(NUMBER number);
    
    public NUMBER signum(NUMBER number);
    
    public NUMBER min(NUMBER number1, NUMBER number2);
    
    public NUMBER max(NUMBER number1, NUMBER number2);
}
