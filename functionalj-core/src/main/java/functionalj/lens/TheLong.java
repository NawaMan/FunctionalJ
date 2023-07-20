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
package functionalj.lens;

import functionalj.lens.lenses.LongToLongAccessPrimitive;

public class TheLong implements LongToLongAccessPrimitive {
    
    public static final TheLong theLong = new TheLong();
    
    public long sum(long i1, long i2) {
        return i1 + i2;
    }
    
    public long product(long i1, long i2) {
        return i1 * i2;
    }
    
    public long subtract(long i1, long i2) {
        return i1 - i2;
    }
    
    public long diff(long i1, long i2) {
        return i2 - i1;
    }
    
    public long ratio(long i1, long i2) {
        return i1 / i2;
    }
    
    @Override
    public long applyLongToLong(long host) {
        return host;
    }
}
