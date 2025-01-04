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
package functionalj.functions;

import static functionalj.lens.Access.theInt;
import static functionalj.stream.intstream.IntStreamPlus.iterate;
import lombok.val;

public class IntFuncs {
    
    private static int[] factors = iterate(1, theInt.time(10)).limit(10).toArray();
    
    public static int digitAt(int value, int digit) {
        if (digit < 0)
            return 0;
        if (digit > 9)
            return 0;
        val factor = factors[digit];
        return (value / factor) % 10;
    }
    
    public static int digitValueAt(int value, int digit) {
        if (digit < 0)
            return 0;
        if (digit > 9)
            return 0;
        val factor = factors[digit];
        return ((value / factor) % 10) * factor;
    }
    
    public static int factorValueAt(int value, int digit) {
        if (digit < 0)
            return 0;
        if (digit > 9)
            return 0;
        val factor = factors[digit];
        return (value / factor) == 0 ? 0 : factor;
    }
    
    public static int factorial(int value) {
        if (value <= 0) {
            return 1;
        }
        int factorial = 1;
        for (int i = 1; i <= value; i++) {
            factorial *= i;
        }
        return factorial;
    }
    
    public static int largestFactorOf(int value) {
        for (int index = factors.length; index-- > 0; ) {
            val factor = factors[index];
            if (factor < value) {
                return factor;
            }
        }
        return 0;
    }
    
    public static int largestFactorIndexOf(int value) {
        for (int index = factors.length; index-- > 0; ) {
            val factor = factors[index];
            if (factor < value) {
                return index;
            }
        }
        return 0;
    }
    // TODO - toBinary
    // TODO - toHex
    // TODO - toBase
}
