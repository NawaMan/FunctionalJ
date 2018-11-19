package functionalj.annotations.record;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import functionalj.annotations.Record;
import functionalj.annotations.Require;
import functionalj.annotations.record.DONoAllArgsConstructor;
import functionalj.annotations.record.DONoNoArgsConstructor;

@SuppressWarnings("javadoc")
public class DOConstructorTest {
    
    @Record(
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
    
    @Record(
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
