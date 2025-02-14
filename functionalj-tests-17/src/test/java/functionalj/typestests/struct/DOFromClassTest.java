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
// 
import org.junit.Test;
import functionalj.types.Struct;
import lombok.val;

public class DOFromClassTest {
    
    // This is rightly results in a compiler error
    // Problem generating the class: 
    //      functionalj.typestests.struct.DOFromClassCouple: 
    //          With a class as the source and the record as a target, 
    //              it is not possible to couple them as a record cannot extends a class.
    //      :class java.lang.IllegalArgumentException
//    @Struct(name = "DOFromClassCouple", coupleWithDefinition = true)
//    public abstract static class DOFromClassDefCouple {
//        
//        public abstract String name();
//        
//        public abstract int count();
//        
//        public String nameUpperCase() {
//            return name().toUpperCase();
//        }
//    }
//    
//    @Test
//    public void testFromClassCouple() {
//        val obj = new DOFromClassCouple("Obj", 5);
//        assertEquals("Obj", obj.name());
////        assertEquals("OBJ", obj.nameUpperCase());
//    }
    
    @Struct(name = "DOFromClassNoCouple", coupleWithDefinition = false)
    public abstract static class DOFromClassDefNoCouple {
        
        public abstract String name();
        
        public abstract int count();
        
        public String nameUpperCase() {
            return name().toUpperCase();
        }
    }
    
    @Test
    public void testFromClassNoCouple() {
        val obj = new DOFromClassNoCouple("Obj", 5);
        assertEquals("Obj", obj.name());
//        assertEquals("OBJ", obj.nameUpperCase());
    }
}
