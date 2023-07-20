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
package functionalj.stream;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.stream.doublestream.DoubleStep;

public class DoubleStepTest {
    
    @Test
    public void testFromTo() {
        assertEquals("[0.0, 7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).to(34).toList().toString());
        assertEquals("[0.0, 7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).to(35).toList().toString());
        assertEquals("[0.0, 7.0, 14.0, 21.0, 28.0, 35.0]", DoubleStep.of(7).to(35).inclusive().toList().toString());
        assertEquals("[7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).startFrom(7).to(34).toList().toString());
        assertEquals("[7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).startFrom(7).to(35).toList().toString());
        assertEquals("[7.0, 14.0, 21.0, 28.0, 35.0]", DoubleStep.of(7).startFrom(7).to(35).inclusive().toList().toString());
        assertEquals("[7.0, 0.0, -7.0, -14.0, -21.0, -28.0]", DoubleStep.of(7).startFrom(7).to(-34).toList().toString());
        assertEquals("[7.0, 0.0, -7.0, -14.0, -21.0, -28.0]", DoubleStep.of(7).startFrom(7).to(-35).toList().toString());
        assertEquals("[7.0, 0.0, -7.0, -14.0, -21.0, -28.0, -35.0]", DoubleStep.of(7).startFrom(7).to(-35).inclusive().toList().toString());
    }
}
