// ============================================================================
// Copyright(c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.all.matchcase;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import functionalj.all.matchcase.Case;
import functionalj.function.Func;
import lombok.val;

public class OptionalSwitchTest {
    
    @Test
    public void testOptionalSwitch() {
        val present = Optional.of("Hello");
        val empty   = Optional.empty();
        
        assertEquals("The data: Hello", 
                Case.of(present)
                .present(data -> "The data: " + data)
                .empty("Empty"));
        assertEquals("Empty", 
                Case.of(empty)
                .present(data -> "The data: " + data)
                .empty("Empty"));
        
        // Swap
        assertEquals("The data: Hello", 
                Case.of(present)
                .empty("Empty")
                .present(data -> "The data: " + data));
        assertEquals("Empty", 
                Case.of(empty)
                .empty("Empty")
                .present(data -> "The data: " + data));
    }
    
    @Test
    public void testIncomplete() {
        val present = Optional.of("Hello");
        assertTrue((Case.of(present).empty("Empty") + "").startsWith("functionalj.all.matchcase.OptionalSwitchCase$OptionalSwitchWithoutPresent"));
    }
    
    @Test
    public void testOptionalSwitch_Conditional() {
        val hello = Optional.of("Hello");
        val yo    = Optional.of("Yo");
        val empty = Optional.<String>empty();
        
        val case1 = Func.f((Optional<String> o) -> 
                Case.of(o)
                .present(str -> str.startsWith("H"), str -> "The 'start with H' str: " + str)
                .present(                            str -> "The str: " + str)
                .empty  ("Empty"));
        
        assertEquals("The 'start with H' str: Hello", case1.apply(hello));
        assertEquals("The str: Yo",                   case1.apply(yo));
        assertEquals("Empty",                         case1.apply(empty));
        
        
        val case2 = Func.f((Optional<String> o) -> 
                Case.of(o)
                .empty  ("Empty")
                .present(str -> str.startsWith("H"), str -> "The 'start with H' str: " + str)
                .present(                            str -> "The str: " + str));
        
        assertEquals("The 'start with H' str: Hello", case2.apply(hello));
        assertEquals("The str: Yo",                   case2.apply(yo));
        assertEquals("Empty",                         case2.apply(empty));
    }
}
