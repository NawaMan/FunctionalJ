package functionalj.stream;

import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.stream.intstream.IntStreamable;

public class IntStreamableTest {

    @Test
    public void test() {
        assertEquals(
                Streamable.of(1, 3, 4, 5, 7).toList(), 
                IntStreamable.ints(1, 3, 4, 5, 7).boxed().toList());
    }

}
