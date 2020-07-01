package functionalj.stream;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class DoubleIteratorPlusTest {

    @Test
    public void testToStream() {
        val iterator = DoubleIteratorPlus.of(1.0, 1.5, 1.75, 1.875);
        assertEquals("[1.0, 1.5, 1.75, 1.875]", iterator.stream().toListString());
    }

}
