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
package functionalj.typestests.struct;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.types.Struct;
import functionalj.types.StructToString;

public class ToStringTest {
    
    @Struct
    void Personal(String name, String position, int salary) {
    }
    
    @Struct(toStringMethod = StructToString.Legacy)
    void Individual(String name, String position, int salary) {
    }
    
    @Struct(toStringMethod = StructToString.Record)
    void Human(String name, String position, int salary) {
    }
    
    @Struct(toStringTemplate = "Staff:${name}")
    void Staff(String name, String position, int salary) {
    }
    
    @Test
    public void testToString() {
        assertEquals("Personal[name: Tony, position: CEO, salary: 1000000]",   new Personal("Tony", "CEO", 1_000_000).toString());
        assertEquals("Individual[name: Tony, position: CEO, salary: 1000000]", new Individual("Tony", "CEO", 1_000_000).toString());
        assertEquals("Human[name=Tony, position=CEO, salary=1000000]",         new Human("Tony", "CEO", 1_000_000).toString());
        assertEquals("Staff:Tony",                                             new Staff("Tony", "CEO", 1_000_000).toString());
    }
}
