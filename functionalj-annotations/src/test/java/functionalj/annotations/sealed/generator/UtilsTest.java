package functionalj.annotations.sealed.generator;

import static functionalj.annotations.sealed.generator.Utils.toCamelCase;
import static functionalj.annotations.sealed.generator.Utils.toStringLiteral;
import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {
    
    @Test
    public void testCamelCase() {
        assertEquals("repeatAll", toCamelCase("RepeatAll"));
        assertEquals("rgbColor",  toCamelCase("RGBColor"));
        assertEquals("rgb",       toCamelCase("RGB"));
        assertEquals("repeat",    toCamelCase("repeat"));
        assertEquals("repeat",    toCamelCase("Repeat"));
        assertEquals("repeat",    toCamelCase("REPEAT"));
    }
    @Test
    public void testStringLiteral() {
        assertEquals("\"-\\n-\\r-\\'-\\\"-\\\\\"-\"", toStringLiteral("-\n-\r-\'-\"-\\\"-"));
    }

}
