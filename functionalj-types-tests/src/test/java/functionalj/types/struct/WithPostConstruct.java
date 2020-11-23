// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.struct;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import functionalj.types.IPostConstruct;
import functionalj.types.Struct;
import functionalj.types.struct.SimpleWithPostReConstruct;


public class WithPostConstruct {
    
    private static List<String> logs = new ArrayList<>();
    
    @Struct(name="SimpleWithPostReConstruct")
    public static interface SimpleDOWithPostReConstruct extends IPostConstruct {
        public String name();
        
        public default void postConstruct() {
            logs.add("Hello: " + name());
        }
    }
    
    @Test
    public void testPostConstruct_runAfterWith() {
        var object = new SimpleWithPostReConstruct("Obj1");
        assertEquals("[Hello: Obj1]", logs.toString());
        
        object.withName("Object1");
        assertEquals("[Hello: Obj1, Hello: Object1]", logs.toString());
    }
}
