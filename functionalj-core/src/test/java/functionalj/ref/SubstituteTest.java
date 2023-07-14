package functionalj.ref;

import static org.junit.Assert.*;
import org.junit.Test;
import functionalj.function.Func;
import lombok.val;

public class SubstituteTest {

    @Test
    public void testSubstitute() {
        val value = Ref.ofValue("One");
        val supplierOrg = Func.f(() -> value.get());
        assertEquals("One", supplierOrg.get().toString());
        val supplierNew = Substitute.Using(value.butWith("Two")).arround(supplierOrg);
        assertEquals("Two", supplierNew.get().toString());
    }
}
