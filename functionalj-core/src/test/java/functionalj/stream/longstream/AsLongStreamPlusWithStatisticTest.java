package functionalj.stream.longstream;

import static functionalj.TestHelper.assertAsString;
import static functionalj.stream.longstream.LongStreamPlus.longs;

import org.junit.Test;

public class AsLongStreamPlusWithStatisticTest {
    
    @Test
    public void testMinBy() {
        assertAsString("OptionalLong[5]",    longs(5L, 40L, 300L, 2000L, 100L).minBy(l -> l));
        assertAsString("OptionalLong[100]",  longs(5L, 40L, 300L, 2000L, 100L).minBy(l -> "" + l));
        assertAsString("OptionalLong[2000]", longs(5L, 40L, 300L, 2000L, 100L).minBy(l -> 10 - ("" + l).length()));
    }
    
}
