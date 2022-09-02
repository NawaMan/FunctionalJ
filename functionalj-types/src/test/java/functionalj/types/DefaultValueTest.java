package functionalj.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DefaultValueTest {

    @Test
    public void test() {
        assertTrue(DefaultValue.isSuitable(Type.STR, DefaultValue.NULL));
        assertTrue(DefaultValue.isSuitable(new Type("java.lang", null, "String", new String[0]), DefaultValue.NULL));
        
        assertTrue(DefaultValue.isSuitable(Type.UUID, DefaultValue.RANDOM));
        
        assertTrue(DefaultValue.isSuitable(Type.STR, DefaultValue.RANDOM));
        assertTrue(DefaultValue.isSuitable(Type.STR, DefaultValue.NOW));
        assertTrue(DefaultValue.isSuitable(Type.LONG, DefaultValue.RANDOM));
        assertTrue(DefaultValue.isSuitable(Type.LONG, DefaultValue.NOW));
        
        assertTrue(DefaultValue.isSuitable(Core.LocalDate.type(), DefaultValue.NOW));
        assertTrue(DefaultValue.isSuitable(Core.LocalDateTime.type(), DefaultValue.NOW));
        
        assertEquals("java.util.UUID.randomUUID().toString()",     DefaultValue.defaultValueCode(Type.STR, DefaultValue.RANDOM));
        assertEquals("functionalj.types.DefaultValue.RAND.nextLong()", DefaultValue.defaultValueCode(Type.LONG, DefaultValue.RANDOM));
        
        assertEquals("java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME", DefaultValue.defaultValueCode(Type.STR, DefaultValue.NOW));
        assertEquals("System.currentTimeMillis()",                             DefaultValue.defaultValueCode(Type.LONG, DefaultValue.NOW));
        assertEquals("java.time.LocalDate.now()",                              DefaultValue.defaultValueCode(Core.LocalDate.type(), DefaultValue.NOW));
        assertEquals("java.time.LocalDateTime.now()",                          DefaultValue.defaultValueCode(Core.LocalDateTime.type(), DefaultValue.NOW));
    }
    
}
