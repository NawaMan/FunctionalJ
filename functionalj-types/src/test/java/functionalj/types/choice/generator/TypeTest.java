package functionalj.types.choice.generator;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import functionalj.types.Type;
import lombok.val;

public class TypeTest {
    
    @Test
    public void test() {
        val myDouble = new Type("java.lang.Double");
        assertTrue(Type.DOUBLE.equals(myDouble));
        val map = new HashSet<Type>();
        map.add(Type.DOUBLE);
        assertTrue(map.contains(myDouble));
    }
}
