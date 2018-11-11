package functionalj.ref;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class RunTest {

    @Test
    public void testWith() {
        val ref = Ref.ofValue(42);
        val orgValue = ref.value();
        val newValue = With(ref.butWith(45)).run(()->ref.value());
        assertEquals(42, orgValue.intValue());
        assertEquals(45, newValue.intValue());
    }
    
    @Test
    public void testSubstitutions() {
        val refA = Ref.ofValue("A");
        val refB = Ref.ofValue("B");
        val refC = Ref.ofValue("C");
        
        assertEquals("ABC", refA.get() + refB.get() + refC.get());
        
        val subs =
                With(refA.butWith("a"))
                .and (refB.butWith("b"))
                .and (refC.butWith("c"))
                .run(()->Run.getCurrentSubstitutions());
        assertEquals("abc", 
                Run.with(subs)
                .run(()->refA.get() + refB.get() + refC.get()));
    }
    
    @Test
    public void testSelectiveWith() {
        val refA = Ref.ofValue("A");
        val refB = Ref.ofValue("B");
        val refC = Ref.ofValue("C");
        
        val subs =
                With(refB.butWith("b"))
                .and(refC.butWith("c"))
                .run(()->Run.getCurrentSubstitutions());
        assertEquals("Abc", 
                Run.with(subs)
                .run(()->refA.get() + refB.get() + refC.get()));
    }
    
    @Test
    public void testAsyncWithSubstitution() {
        val refA = Ref.ofValue("A");
        val refB = Ref.ofValue("B");
        val refC = Ref.ofValue("C");
        With(refB.butWith("b").withinThisThread(true))
        .run(()->{
            assertEquals("AbC", refA.get() + refB.get() + refC.get());
            
            assertEquals("aBc",
                With(refA.butWith("a"))
                .and(refC.butWith("c"))
                .runAsync(()-> refA.get() + refB.get() + refC.get())
                .getResult().get());
        });
    }
    
    @Test
    public void testAsyncWithSubstitution_crossThread() {
        val refA = Ref.ofValue("A");
        val refB = Ref.ofValue("B");
        val refC = Ref.ofValue("C");
        With(refB.butWith("b").withinThisThread(false))
        .run(()->{
            assertEquals("AbC", refA.get() + refB.get() + refC.get());
            
            assertEquals("abc",
                    With(refA.butWith("a"))
                    .and(refC.butWith("c"))
                    .runAsync(()-> refA.get() + refB.get() + refC.get())
                    .getResult().get());
        });
    }
    
}
