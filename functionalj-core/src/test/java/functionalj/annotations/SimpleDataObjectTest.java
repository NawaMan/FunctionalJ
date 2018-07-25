package functionalj.annotations;

import static functionalj.annotations.SimpleFromInteface.theSimpleFromInteface;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;

@SuppressWarnings("javadoc")
public class SimpleDataObjectTest {
    
    @DataObject(name="SimpleFromInteface")
    public static interface SimpleDOInterface {
        
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
    
    @Test
    public void testNoArgConstructor_null() {
        assertNull(new SimpleFromInteface().name());
    }
    
    @Test
    public void testDefaultMethod_callNormally() {
        assertEquals("OBJ1", new SimpleFromInteface("Obj1").nameUpperCase());
    }
    
}
