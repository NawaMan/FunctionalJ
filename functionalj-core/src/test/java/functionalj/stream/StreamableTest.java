package functionalj.types;

import static functionalj.lens.Access.$S;
import static org.junit.Assert.assertEquals;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.types.list.FuncListStream;
import functionalj.types.list.ImmutableList;

public class StreamableTest {
    
    @Test
    public void testSelectiveMap() {
        assertEquals("[One, --Two, Three, Four, Five]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five").mapOnly("Two"::equals, str -> "--" + str).toString());
    }
    
    @Test
    public void testSplit() {
        assertEquals("([One, Two],[Four, Five],[Three])", 
                FuncListStream.from((Supplier<Stream<String>>)()->Stream.of("One", "Two", "Three", "Four", "Five"))
                .split($S.length().thatEquals(3),
                       $S.length().thatLessThanOrEqualsTo(4))
                .toString());
    }
    
}
