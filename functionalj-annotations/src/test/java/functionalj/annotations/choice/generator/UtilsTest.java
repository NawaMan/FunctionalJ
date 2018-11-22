package functionalj.annotations.choice.generator;

import static functionalj.annotations.choice.generator.Utils.toCamelCase;
import static functionalj.annotations.choice.generator.Utils.toStringLiteral;
import static org.junit.Assert.assertEquals;

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
