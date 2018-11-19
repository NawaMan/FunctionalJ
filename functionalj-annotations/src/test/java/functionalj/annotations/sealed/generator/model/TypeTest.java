package functionalj.annotations.sealed.generator.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.sealed.generator.model.Type;

public class TypeTest {
    
    @Test
    public void testPredicateType() {
        assertEquals("java.lang.Integer", Type.INTEGER.toString());
    }
    
}
