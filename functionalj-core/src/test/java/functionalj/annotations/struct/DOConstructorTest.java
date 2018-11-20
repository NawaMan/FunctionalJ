package functionalj.annotations.struct;

import static functionalj.annotations.DefaultValue.NULL;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import functionalj.annotations.DefaultTo;
import functionalj.annotations.Struct;

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
        
        @DefaultTo(NULL)
        public String name();
        
    }
    @Test(expected=NoSuchMethodException.class)
    public void testNoAllArgsConstructor() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DONoAllArgsConstructor.class.getConstructor(String.class).newInstance("Obj");
    }
    
}
