package functionalj.annotations.dataobject;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import functionalj.annotations.DataObject;
import functionalj.annotations.Require;

@SuppressWarnings("javadoc")
public class DOConstructorTest {
    
    @DataObject(
            name = "DONoNoArgsConstructor",
            generateNoArgConstructor = false
        )
    public static interface DONoNoArgsConstructorDef {
        
        public String name();
        
    }
    @Test (expected=InstantiationException.class)
    public void testNoNoArgsConstructor() throws InstantiationException, IllegalAccessException {
        DONoNoArgsConstructor.class.newInstance();
    }
    
    @DataObject(
            name = "DONoAllArgsConstructor",
            generateNoArgConstructor  = true,
            generateAllArgConstructor = false
        )
    public static interface DONoAllArgsConstructorDef {
        
        @Require(false)
        public String name();
        
    }
    @Test(expected=NoSuchMethodException.class)
    public void testNoAllArgsConstructor() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DONoAllArgsConstructor.class.getConstructor(String.class).newInstance("Obj");
    }
    
}
