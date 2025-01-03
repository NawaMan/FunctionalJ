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
package functionalj.lens.lenses;

import static functionalj.function.Func.f;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.WriteLens;
import functionalj.list.FuncList;
import lombok.val;

public class FuncListLensTest {
    
    private final FuncListLens<FuncList<String>, String, StringLens<FuncList<String>>> listLens;
    
    public FuncListLensTest() {
        val readLens = f((FuncList<String> list) -> list);
        val writeLens = (WriteLens<FuncList<String>, FuncList<String>>) ((list, newList) -> newList);
        val subCreator = f((LensSpec<FuncList<String>, String> spec) -> StringLens.of(spec));
        listLens = FuncListLens.of(readLens, writeLens, subCreator);
    }
    
    @Test
    public void testAt() {
        val list = FuncList.of("Zero", "One", "Two", "Three", "Four");
        val at3 = listLens.at(3);
        assertEquals("Three", at3.apply(list).toString());
        assertEquals("[Zero, One, Two, Tri, Four]", at3.changeTo("Tri").apply(list).toString());
    }
    
    @Test
    public void testEach() {
        val list = FuncList.of("Zero", "One", "Two", "Three", "Four");
        val lengthStr = listLens.each().changeTo(s -> "" + s.length());
        assertEquals("[4, 3, 3, 5, 4]", lengthStr.apply(list).toString());
        val changeShort = listLens.eachOf(theString.length().thatLessThan(4)).changeToNull();
        assertEquals("[Zero, null, null, Three, Four]", changeShort.apply(list).toString());
        val addTail = listLens.eachOf(theString.length().thatLessThan(4)).changeTo(str -> (str + "~~~~").substring(0, 4));
        assertEquals("[Zero, One~, Two~, Three, Four]", addTail.apply(list).toString());
    }
}
