package functionalj.stream;

import static functionalj.lens.Access.$S;
import static org.junit.Assert.assertEquals;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.list.FuncListStream;
import functionalj.list.ImmutableList;
import lombok.val;

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
    
    @Test
    public void testMapWithPrev() {
        val stream = Streamable.of("One", "Two", "Three").mapWithPrev((prev, element) -> prev.orElse("").length() + element.length());
        assertEquals("3, 6, 8", stream.joining(", "));
        assertEquals("3, 6, 8", stream.joining(", "));
    }
    
}
