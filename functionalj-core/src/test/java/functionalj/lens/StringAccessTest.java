package functionalj.lens;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class StringAccessTest {

    @Test
    public void testSplit() {
        var str = "1, 2,3";
        assertEquals("[1, 2, 3]", theString.split("[ \t]*,[ \t]*").apply(str).toString());
        
    }
    
}
