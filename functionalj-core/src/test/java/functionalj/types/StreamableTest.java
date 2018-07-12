package functionalj.types;

import static functionalj.lens.Access.$S;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.lens.Access;

public class StreamableTest {
    
    @Test
    public void testSelectiveMap() {
        assertEquals("[One, --Two, Three, Four, Five]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five").selectiveMap("Two"::equals, str -> "--" + str).toString());
    }
    @Test
    public void testSplit() {
        assertEquals("[[One, Two],[Four, Five],[Three]]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five")
                .split($S.length().thatEquals(3),
                       $S.length().thatEquals(4))
                .toString());
    }
    
}
