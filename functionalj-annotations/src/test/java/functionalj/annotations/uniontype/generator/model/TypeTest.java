package functionalj.annotations.uniontype.generator.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TypeTest {
    
    @Test
    public void testPredicateType() {
        assertEquals("java.lang.Integer", Type.INTEGER.toString());
    }
    
}
