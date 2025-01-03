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

// import static functionalj.types.DefaultValue.NULL;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
// import functionalj.types.DefaultTo;
import functionalj.types.Struct;

public class DOConstructorTest {
    
    @Struct(name = "DONoNoArgsConstructor", generateNoArgConstructor = false)
    public static interface DONoNoArgsConstructorDef {
        
        public String name();
    }
    
    @Test(expected = NoSuchMethodException.class)
    public void testNoNoArgsConstructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        DONoNoArgsConstructor.class.getDeclaredConstructor(new Class[0]).newInstance();
    }
    
    
    // This correctly has a compilation error of:
    // Problem generating the class: functionalj.typestests.struct.DONoAllArgsConstructor: 
    //      An All-Arg constructor is need when generate a record.:class java.lang.IllegalArgumentException
//    @Struct(name = "DONoAllArgsConstructor", generateNoArgConstructor = true, generateAllArgConstructor = false)
//    public static interface DONoAllArgsConstructorDef {
//        
//        @DefaultTo(NULL)
//        public String name();
//    }
//    
//    @Test(expected = NoSuchMethodException.class)
//    public void testNoAllArgsConstructor() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
//        DONoAllArgsConstructor.class.getConstructor(String.class).newInstance("Obj");
//    }
}
