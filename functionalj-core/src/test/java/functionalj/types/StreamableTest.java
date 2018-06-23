package functionalj.types;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StreamableTest {
    
    @Test
    public void testSelectiveMap() {
        assertEquals("[One, --Two, Three, Four, Five]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five").selectiveMap("Two"::equals, str -> "--" + str).toString());
    }
    
}
