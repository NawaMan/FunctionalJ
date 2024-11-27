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
package functionalj.ref;

import static functionalj.TestHelper.assertAsString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.function.Func;
import lombok.val;

public class SubstituteTest {
    
    @Test
    public void testSubstitute() {
        val value = Ref.ofValue("One");
        val supplierOrg = Func.f(() -> value.get());
        assertEquals("One", supplierOrg.get().toString());
        val supplierNew = Substitute.Using(value.butWith("Two")).arround(supplierOrg);
        assertEquals("Two", supplierNew.get().toString());
    }
    
    @Test
    public void testToString() {
        assertAsString(
                "Value [value=Two, ref()=functionalj.ref.SubstituteTest#\\E[0-9]+\\Q:Ref<String>]", 
                Ref.ofValue("One").butWith("Two"));
    }
    
}
