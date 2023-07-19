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
package functionalj.typestests.struct;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.types.Struct;

public class WithFuncListTest {
    
    @Struct(name = "ParentWithFuncList")
    public static interface IParent2 {
        
        public FuncList<String> names();
        
        public FuncList<Child> children();
    }
    
    @Test
    public void testAccessToLens() {
        ParentWithFuncList parent = new ParentWithFuncList(ImmutableFuncList.of("One", "Two", "Three", "Four"), ImmutableFuncList.empty());
        assertEquals("[One, Two, Three, Four]", "" + ParentWithFuncList.theParentWithFuncList.names.apply(parent));
        assertEquals("[(One,3), (Two,3), (Three,5), (Four,4)]", "" + parent.names().mapToTuple(theString, theString.length()));
    }
}
