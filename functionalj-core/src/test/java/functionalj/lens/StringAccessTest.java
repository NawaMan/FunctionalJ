package functionalj.lens;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theString;
import static functionalj.list.ImmutableFuncList.listOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class StringAccessTest {
    
    @Test
    public void testSplit() {
        val str = "1, 2,3";
        assertEquals("[1, 2, 3]", theString.split("[ \t]*,[ \t]*").apply(str).toString());
    }
    
    @Test
    public void testFormatWith() {
        val lists = listOf("ONE", "TWO", "THREE", "AE", "BEE", "SEE");
        assertAsString(
                "[ONE (3), TWO (3), THREE (5), AE (2), BEE (3), SEE (3)]", 
                lists.map(theString.formatWith("%s (%s)", $S.length())));
        assertAsString(
                "[3 (ONE), 3 (TWO), 5 (THREE), 2 (AE), 3 (BEE), 3 (SEE)]", 
                lists.map(theString.formatWith("%2$s (%1$s)", $S.length())));
    }
    
    @Test
    public void testConcat() {
        val lists = listOf("ONE", "TWO", "THREE", "AE", "BEE", "SEE");
        assertAsString(
                "[ONE-3, TWO-3, THREE-5, AE-2, BEE-3, SEE-3]", 
                lists.map(theString.concat("-", $S.length())));
    }
    
    @Test
    public void testGrab() {
        val str = "1, 2,3";
        assertEquals(
                "[1, 2, 3]", 
                theString.grab("[0-9]+").apply(str).toString());
    }
    
    @Test
    public void testMatches() {
        val str = "1, 2,3";
        assertEquals("[1, 2, 3]", theString.matches("[0-9]+").texts().apply(str).toListString());
    }
    
    @Test
    public void testCapture() {
        val str = "1, 2,3";
        assertEquals("[{num:1}, {num:2}, {num:3}]", theString.capture("(?<num>[0-9]+)").apply(str).toListString());
    }
    
}
