package functionalj.annotations;

import static functionalj.annotations.SimpleFromInteface.theSimpleFromInteface;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Test;

import static java.util.stream.Collectors.toList;

import lombok.val;

@SuppressWarnings("javadoc")
public class SimpleDataObjectTest {
    
    @DataObject(name="SimpleFromInteface")
    public static interface SimpleDOInterface {
        
        public String name();
        
//        public default String nameUpperCase() {
//            return name().toUpperCase();
//        }
    }
        
    @Test
    public void testSimpleDoInterface_readLens() {
        val obj1 = new SimpleFromInteface("Obj1");
        assertEquals("Obj1", theSimpleFromInteface.name.apply(obj1));
    }
        
    @Test
    public void testSimpleDoInterface_writeLens() {
        val obj1 = new SimpleFromInteface("Obj1");
        assertEquals("Obj1",               obj1.name());
        assertEquals("Object1",            theSimpleFromInteface.name.changeTo("Object1").apply(obj1).name());
        assertEquals("SimpleFromInteface", obj1.withName("Object1").getClass().getSimpleName());
        assertEquals("Object1",            obj1.withName("Object1").name());
        assertEquals("Obj1",               obj1.name());
    }
    
    @Test
    public void testSimpleDoInterface_withStream() {
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
    public void testSimpleDoInterface_noArgConstructor() {
        assertNull(new SimpleFromInteface().name());
        
    }
    
}
