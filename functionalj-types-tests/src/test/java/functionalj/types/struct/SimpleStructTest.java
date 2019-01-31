package functionalj.types.struct;

import static functionalj.types.DefaultValue.NULL;
import static functionalj.types.struct.SimpleFromInteface.theSimpleFromInteface;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import functionalj.types.DefaultTo;
import lombok.val;

@SuppressWarnings("javadoc")
public class SimpleStructTest {
    
    @functionalj.types.Struct(name="SimpleFromInteface", generateNoArgConstructor=true)
    public static interface SimpleDOInterface {
        
        @DefaultTo(NULL)
        public String name();
        
        public default String nameUpperCase() {
            return name().toUpperCase();
        }
    }
    
    @Test
    public void testReadLens_getProperty() {
        val obj1 = new SimpleFromInteface("Obj1");
        assertEquals("Obj1", theSimpleFromInteface.name.apply(obj1));
    }
        
    @Test
    public void testWriteLens_createNewObject() {
        val obj1 = new SimpleFromInteface("Obj1");
        assertEquals("Obj1",               obj1.name());
        assertEquals("Object1",            theSimpleFromInteface.name.changeTo("Object1").apply(obj1).name());
        assertEquals("SimpleFromInteface", obj1.withName("Object1").getClass().getSimpleName());
        assertEquals("Object1",            obj1.withName("Object1").name());
        assertEquals("Obj1",               obj1.name());
    }
    
    @Test
    public void testWithStream_createNewObject() {
        val list = Arrays.asList(
                    new SimpleFromInteface("Obj1"),
                    new SimpleFromInteface("Obj2"),
                    new SimpleFromInteface("Obj3")
                );
        val names = list.stream().map(theSimpleFromInteface.name).collect(toList());
        assertEquals("[Obj1, Obj2, Obj3]", names.toString());
        
        assertEquals(1, list.stream().filter(theSimpleFromInteface.name.thatEquals("Obj2")).count());
    }
    
    @Test(expected=NullPointerException.class)
    public void testNoArgConstructor_null() {
        new SimpleFromInteface().name();
    }
    
    @Test
    public void testDefaultMethod_callNormally() {
        assertEquals("OBJ1", new SimpleFromInteface("Obj1").nameUpperCase());
    }
    
}
