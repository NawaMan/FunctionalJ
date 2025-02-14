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
package functionalj.types.choice;

import java.util.Objects;

public class CheckEquals {
    
    private CheckEquals() {
    }
    
    public static boolean checkEquals(byte a, byte b) {
        return a == b;
    }
    
    public static boolean checkEquals(short a, short b) {
        return a == b;
    }
    
    public static boolean checkEquals(int a, int b) {
        return a == b;
    }
    
    public static boolean checkEquals(long a, long b) {
        return a == b;
    }
    
    public static boolean checkEquals(float a, float b) {
        return a == b;
    }
    
    public static boolean checkEquals(double a, double b) {
        return a == b;
    }
    
    public static boolean checkEquals(boolean a, boolean b) {
        return a == b;
    }
    
    public static boolean checkEquals(Object a, Object b) {
        return ((a == null) && (b == null)) || Objects.equals(a, b);
    }
}
