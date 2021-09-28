package functionalj.types.input;

import static functionalj.types.input.Tests.assertAsString;
import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class InputTypeElementTest {
    
    static class Clazz1<T> {
        public void method(T t) {}
    }
    
    @Test
    public void test1() throws ClassNotFoundException {
        val clazz = Class.forName("functionalj.types.input.InputTypeElementTest$Clazz1");
        assertAsString("functionalj.types.input.InputTypeElementTest$Clazz1", clazz.getTypeName());
        assertAsString("Clazz1",                                              clazz.getSimpleName());
        assertAsString("functionalj.types.input",                             clazz.getPackage().getName());
        assertAsString("[T]",                                                 clazz.getTypeParameters());
    }
    
}
