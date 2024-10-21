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
package functionalj.function;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TracedTest {
    
    @Test
    public void testTraced() {
        assertEquals("Predicate::Predicate1@functionalj.function.TracedConstants#28", TracedConstants.namedPredicate.toString());
        assertEquals("Predicate@functionalj.function.TracedConstants#30", TracedConstants.nonamePredicate.toString());
        assertEquals("F1::F1", TracedConstants.namedNoTracedFunc1.toString());
        assertEquals("F1::F2@functionalj.function.TracedConstants#34", TracedConstants.namedTracedFunc1.toString());
        assertEquals("F1@functionalj.function.TracedConstants#36", TracedConstants.nonameTracedFunc1.toString());
    }
    
    @Test
    public void testName() {
        assertEquals("Predicate1", ((Named) TracedConstants.namedPredicate).name());
        assertEquals("Predicate", ((Named) TracedConstants.nonamePredicate).name());
        assertEquals("F1", ((Named) TracedConstants.namedNoTracedFunc1).name());
        assertEquals("F2", ((Named) TracedConstants.namedTracedFunc1).name());
        assertEquals("F1", ((Named) TracedConstants.nonameTracedFunc1).name());
    }
}
