package functionalj.function;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class NamedTest {

    @Test
    public void test() {
        val namedSupplier = Named.Supplier("GetWord", ()->"Hello");
        assertEquals("Supplier::GetWord", namedSupplier.toString());
    }

}
