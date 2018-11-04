package functionalj.stream;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class IntStreamPlusTest {
    
    @Test
    public void testMapToObj() {
        val intStream = IntStreamPlus.of(1, 1, 2, 3, 5, 8);
        assertEquals("1, 1, 2, 3, 5, 8", intStream.mapBy(i -> "" + i).joining(", "));
    }
    
}
