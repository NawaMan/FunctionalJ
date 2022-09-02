package functionalj.types.input;

import static functionalj.types.input.Tests.assertAsString;

import java.lang.reflect.AnnotatedTypeVariable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

import org.junit.Test;

import lombok.val;

public class InputTypeElementTest {
    
    static class Clazz1<T> {
        public void method(T t) {}
        public <V extends Number> void method2(List<? extends T> t, V v) {}
        public void method3(List<? super T> t) {}
    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void test1() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
        val clazz = Class.forName("functionalj.types.input.InputTypeElementTest$Clazz1");
        assertAsString("functionalj.types.input.InputTypeElementTest$Clazz1", clazz.getTypeName());
        assertAsString("Clazz1",                                              clazz.getSimpleName());
        assertAsString("functionalj.types.input",                             clazz.getPackage().getName());
        assertAsString("[T]",                                                 clazz.getTypeParameters());
        assertAsString("[class java.lang.Object]",                            clazz.getTypeParameters()[0].getBounds());
        
        val method = clazz.getMethod("method", Object.class);
        assertAsString("T",                      method.getAnnotatedParameterTypes()[0].getType());
        assertAsString("class java.lang.Object", ((AnnotatedTypeVariable)method.getAnnotatedParameterTypes()[0]).getAnnotatedBounds()[0].getType());
        
        val method2 = clazz.getMethod("method2", List.class, Number.class);
        assertAsString("java.util.List<? extends T>", method2.getAnnotatedParameterTypes()[0].getType());
        assertAsString(null,                          ((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getOwnerType());
        assertAsString("interface java.util.List",    ((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getRawType());
        assertAsString("java.util.List<? extends T>", ((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getTypeName());
        assertAsString("? extends T",                 ((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getActualTypeArguments()[0]);
        assertAsString("? extends T",                 ((WildcardType)((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getActualTypeArguments()[0]).getTypeName());
        assertAsString("[]",                          ((WildcardType)((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getActualTypeArguments()[0]).getLowerBounds());
        assertAsString("[T]",                         ((WildcardType)((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getActualTypeArguments()[0]).getUpperBounds());
        assertAsString("T",                           ((WildcardType)((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getActualTypeArguments()[0]).getUpperBounds()[0].getTypeName());
        
        assertAsString("class functionalj.types.input.InputTypeElementTest$Clazz1", ((TypeVariable)((WildcardType)((ParameterizedType)method2.getAnnotatedParameterTypes()[0].getType()).getActualTypeArguments()[0]).getUpperBounds()[0]).getGenericDeclaration());
        
        assertAsString("V", ((TypeVariable)method2.getAnnotatedParameterTypes()[1].getType()));
        assertAsString("public void functionalj.types.input.InputTypeElementTest$Clazz1.method2(java.util.List,java.lang.Number)",
                       ((TypeVariable)method2.getAnnotatedParameterTypes()[1].getType()).getGenericDeclaration());
        assertAsString("class java.lang.reflect.Method",
                       ((TypeVariable)method2.getAnnotatedParameterTypes()[1].getType()).getGenericDeclaration().getClass());
    }
    
}
