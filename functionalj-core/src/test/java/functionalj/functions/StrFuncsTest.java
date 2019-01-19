package functionalj.functions;

import static functionalj.functions.StrFuncs.matches;
import static org.junit.Assert.*;

import org.junit.Test;

public class StrFuncsTest {
    
    @Test
    public void testRepeat() {
        assertEquals("AAAAA", StrFuncs.repeat("A", 5));
    }
    
    @Test
    public void testLines() {
        assertEquals("AA, AAA, AAAA, AAAAA", StrFuncs.lines("AA\nAAA\rAAAA\r\nAAAAA").joinToString(", "));
        assertEquals("AA, AAA",              StrFuncs.lines("AA\nAAA\rAAAA\r\nAAAAA").limit(2).joinToString(", "));
    }
    
    @Test
    public void testIndent() {
        assertEquals(
                "\tAA\n" + 
                "\tAAA\r" + 
                "\tAAAA\r\n" + 
                "\tAAAAA", StrFuncs.indent("AA\nAAA\rAAAA\r\nAAAAA"));
    }
    
    @Test
    public void testLeftPadding() {
        assertEquals("---AAA", StrFuncs.leftPadding("AAA", '-', 6));
    }
    
    @Test
    public void testMatches() {
        assertEquals("A, AA, AAA, AAAA", matches("ABAABAAABAAAA", "A+").joinToString(", "));
        assertEquals("#{Hello}, #{There}", matches("--#{Hello}--#{There}--", "#\\{[a-zA-Z0-9$_]+\\}").joinToString(", "));
    }
    
    @Test
    public void testTemplate() {
        assertEquals("--hello--there-$ss-", StrFuncs.template("--$Hello--$There-$$ss-", str -> str.toLowerCase()));
    }
    
}
