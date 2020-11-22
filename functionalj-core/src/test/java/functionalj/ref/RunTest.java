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
package functionalj.ref;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class RunTest {

    @Test
    public void testWith() {
        var ref = Ref.ofValue(42);
        var orgValue = ref.value();
        var newValue = With(ref.butWith(45)).run(()->ref.value());
        assertEquals(42, orgValue.intValue());
        assertEquals(45, newValue.intValue());
    }
    
    @Test
    public void testSubstitutions() {
        var refA = Ref.ofValue("A");
        var refB = Ref.ofValue("B");
        var refC = Ref.ofValue("C");
        
        assertEquals("ABC", refA.get() + refB.get() + refC.get());
        
        var subs =
                With(refA.butWith("a"))
                .and (refB.butWith("b"))
                .and (refC.butWith("c"))
                .run(()->Substitution.getCurrentSubstitutions());
        assertEquals("abc", 
                Run.with(subs)
                .run(()->refA.get() + refB.get() + refC.get()));
    }
    
    @Test
    public void testSelectiveWith() {
        var refA = Ref.ofValue("A");
        var refB = Ref.ofValue("B");
        var refC = Ref.ofValue("C");
        
        var subs =
                With(refB.butWith("b"))
                .and(refC.butWith("c"))
                .run(()->Substitution.getCurrentSubstitutions());
        assertEquals("Abc", 
                Run.with(subs)
                .run(()->refA.get() + refB.get() + refC.get()));
    }
    
    @Test
    public void testAsyncWithSubstitution() {
        var refA = Ref.ofValue("A");
        var refB = Ref.ofValue("B");
        var refC = Ref.ofValue("C");
        With(refB.butWith("b").withinThisThread())
        .run(()->{
            assertEquals("AbC", refA.get() + refB.get() + refC.get());
            
            assertEquals("a and c should be in effect but B should go back to the original one", "aBc", 
                With(refA.butWith("a"))
                .and(refC.butWith("c"))
                .asynchronously()
                .run(()-> refA.get() + refB.get() + refC.get())
                .getResult().get());
        });
    }
    
    @Test
    public void testAsyncWithSubstitution_localThread() {
        var refA = Ref.ofValue("A");
        var refB = Ref.ofValue("B");
        var refC = Ref.ofValue("C");
        With(refB.butWith("b").withinThisThread(true))
        .run(()->{
            assertEquals("AbC", refA.get() + refB.get() + refC.get());
            
            assertEquals("a and c should be in effect but B should go back to the original one", "aBc",
                With(refA.butWith("a"))
                .and(refC.butWith("c"))
                .asynchronously()
                .run(()-> refA.get() + refB.get() + refC.get())
                .getResult().get());
        });
    }
    
    @Test
    public void testAsyncWithSubstitution_default_crossThread() {
        var refA = Ref.ofValue("A");
        var refB = Ref.ofValue("B");
        var refC = Ref.ofValue("C");
        With(refB.butWith("b"))
        .run(()->{
            assertEquals("AbC", refA.get() + refB.get() + refC.get());
            
            assertEquals("a, and c should be in effect", "abc",
                    With(refA.butWith("a"))
                    .and(refC.butWith("c"))
                    .asynchronously()
                    .run(()-> refA.get() + refB.get() + refC.get())
                    .getResult().get());
        });
    }
    
    @Test
    public void testAsyncWithSubstitution_crossThread() {
        var refA = Ref.ofValue("A");
        var refB = Ref.ofValue("B");
        var refC = Ref.ofValue("C");
        With(refB.butWith("b").withinThisThread(false))
        .run(()->{
            assertEquals("AbC", refA.get() + refB.get() + refC.get());
            
            assertEquals("a, and c should be in effect", "abc",
                    With(refA.butWith("a"))
                    .and(refC.butWith("c"))
                    .asynchronously()
                    .run(()-> refA.get() + refB.get() + refC.get())
                    .getResult().get());
        });
    }
    
}
