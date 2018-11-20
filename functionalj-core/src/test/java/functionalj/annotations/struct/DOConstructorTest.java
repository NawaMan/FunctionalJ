package functionalj.annotations.struct;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import functionalj.annotations.Struct;
import functionalj.annotations.struct.DONoAllArgsConstructor;
import functionalj.annotations.struct.DONoNoArgsConstructor;
import functionalj.annotations.Require;

@SuppressWarnings("javadoc")
public class DOConstructorTest {
    
    @Struct(
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
    
    @Struct(
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
