package functionalj.function;

import static org.junit.Assert.*;

import org.junit.Test;

public class TracedTest {
    
    @Test
    public void testTraced() {
        assertEquals("Predicate::Predicate1@functionalj.function.TracedConstants#5", TracedConstants.namedPredicate .toString());
        assertEquals("Predicate@functionalj.function.TracedConstants#6",             TracedConstants.nonamePredicate.toString());
        
        assertEquals("F1::F1",                                        TracedConstants.namedNoTracedFunc1.toString());
        assertEquals("F1::F2@functionalj.function.TracedConstants#9", TracedConstants.namedTracedFunc1  .toString());
        assertEquals("F1@functionalj.function.TracedConstants#10",    TracedConstants.nonameTracedFunc1 .toString());
    }
    @Test
    public void testName() {
        assertEquals("Predicate1", ((Named)TracedConstants.namedPredicate)    .getName());
        assertEquals("Predicate",  ((Named)TracedConstants.nonamePredicate)   .getName());
        assertEquals("F1",         ((Named)TracedConstants.namedNoTracedFunc1).getName());
        assertEquals("F2",         ((Named)TracedConstants.namedTracedFunc1)  .getName());
        assertEquals("F1",         ((Named)TracedConstants.nonameTracedFunc1) .getName());
    }
    
}
