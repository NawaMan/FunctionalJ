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
package functionalj.list;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import lombok.val;

public class FuncListBuilderTest {
    
    @Test
    public void testListBuilder() {
        val map = FuncList.newListBuilder(String.class).add("A").add("B").add("C").add("D").add("E").add("F").add("G").add("H").add("I").add("J").add("J").add("L").add("M").add("N").add("O").add("P").add("Q").add("R").add("S").add("T").add("U").add("V").add("W").add("X").add("Y").add("Z").build();
        assertEquals("[A, B, C, D, E, F, G, H, I, J, J, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]", map.toString());
    }
}
