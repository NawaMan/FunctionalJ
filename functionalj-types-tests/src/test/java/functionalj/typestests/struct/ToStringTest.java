package functionalj.typestests.struct;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.types.Struct;

public class ToStringTest {

    @Struct(toStringTemplate = "Staff:${name}")
    void Staff(String name, String position, int salary) {
    }

    @Test
    public void testToString() {
        assertEquals("Staff:Tony", new Staff("Tony", "CEO", 1_000_000).toString());
    }
}
