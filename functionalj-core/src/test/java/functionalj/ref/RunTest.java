package functionalj.ref;

import static functionalj.ref.Run.With;
import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class RunTest {

    @Test
    public void test() {
        val ref = Ref.ofValue(42).overridable();
        val orgValue = ref.value();
        val newValue = With(ref.butWith(45)).run(()->ref.value());
        assertEquals(42, orgValue.intValue());
        assertEquals(45, newValue.intValue());
    }
    
}
