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

import static functionalj.all.matchcase.MightBe.Just;
import static functionalj.all.matchcase.MightBe.None;

import org.junit.Test;

import functionalj.types.Choice;
import lombok.val;

public class ChoiceCaseTest {
    
    @Choice
    interface MightBeSpec<T> {
        void Just(T value);
        void None();
    }
    
    
    @Test
    public void testMightBe() {
        val just = Just("Hello");
        val none = None();
        
        assertEquals("The data: Just(Hello)", 
                Case.of(just)
                .just(data -> "The data: " + data)
                .none("None"));
        assertEquals("None", 
                Case.of(none)
                .just(data -> "The data: " + data)
                .none("None"));
    }

}
