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
package example.functionalj.structtype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Struct;
import lombok.val;

public class StructExtendFormTest {
    
    public static abstract class Greeter {
        
        public abstract String greetWord();
        
        public String greeting(String name) {
            return greetWord() + " " + name + "!";
        }
    }
    
    @Struct
    static abstract class FriendlyGuySpec extends Greeter {
        public abstract String greetWord();
        public void shakeHand() {}
    }
    
    @Test
    public void example01_Extends() {
        val fiendlyGuy = new FriendlyGuy("Hi");
        assertEquals("Hi Bruce Wayne!", fiendlyGuy.greeting("Bruce Wayne"));
    }
    
}
