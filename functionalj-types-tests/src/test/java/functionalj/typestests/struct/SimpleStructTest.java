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

import static functionalj.types.DefaultValue.NULL;
import static functionalj.typestests.struct.SimpleFromInteface.theSimpleFromInteface;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Test;
import functionalj.types.DefaultTo;
import lombok.val;

public class SimpleStructTest {
    
    @functionalj.types.Struct(name = "SimpleFromInteface", generateNoArgConstructor = true)
    public static interface SimpleDOInterface {
        
        @DefaultTo(NULL)
        public String name();
        
        public default String nameUpperCase() {
            return name().toUpperCase();
        }
    }
    
    @Test
    public void testReadLens_getProperty() {
        val obj1 = new SimpleFromInteface("Obj1");
        assertEquals("Obj1", theSimpleFromInteface.name.apply(obj1));
    }
    
    @Test
    public void testWriteLens_createNewObject() {
        val obj1 = new SimpleFromInteface("Obj1");
        assertEquals("Obj1", obj1.name());
        assertEquals("Object1", theSimpleFromInteface.name.changeTo("Object1").apply(obj1).name());
        assertEquals("SimpleFromInteface", obj1.withName("Object1").getClass().getSimpleName());
        assertEquals("Object1", obj1.withName("Object1").name());
        assertEquals("Obj1", obj1.name());
    }
    
    @Test
    public void testWithStream_createNewObject() {
        val list = Arrays.asList(new SimpleFromInteface("Obj1"), new SimpleFromInteface("Obj2"), new SimpleFromInteface("Obj3"));
        val names = list.stream().map(theSimpleFromInteface.name).collect(toList());
        assertEquals("[Obj1, Obj2, Obj3]", names.toString());
        assertEquals(1, list.stream().filter(theSimpleFromInteface.name.thatEquals("Obj2")).count());
    }
    
    @Test
    public void testDefaultMethod_callNormally() {
        assertEquals("OBJ1", new SimpleFromInteface("Obj1").nameUpperCase());
    }
}
