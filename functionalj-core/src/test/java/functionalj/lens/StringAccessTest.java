package functionalj.lens;

import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theString;
import static functionalj.list.ImmutableFuncList.listOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.TestHelper;
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
        TestHelper.assertAsString(
                "[ONE (3), TWO (3), THREE (5), AE (2), BEE (3), SEE (3)]",
                lists.map(theString.formatWith("%s (%s)", $S.length())));
    }
    
}
