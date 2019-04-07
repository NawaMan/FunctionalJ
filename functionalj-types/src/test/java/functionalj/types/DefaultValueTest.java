package functionalj.types;

import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.types.struct.generator.Type;

public class DefaultValueTest {

    @Test
    public void test() {
        assertTrue(DefaultValue.isSuitable(Type.STR, DefaultValue.NULL));
        assertTrue(DefaultValue.isSuitable(new Type(null, "String", "java.lang", new String[0]), DefaultValue.NULL));
    }
    
}
