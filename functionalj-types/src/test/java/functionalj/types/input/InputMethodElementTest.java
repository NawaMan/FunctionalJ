// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types.input;

import static functionalj.types.input.Tests.assertAsString;
import static java.util.Arrays.asList;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

import org.junit.Test;

import functionalj.types.Struct;
import lombok.val;

public class InputMethodElementTest {
    
    @Struct
    public static String method1(String name) throws RuntimeException {
        return name;
    }
    
    @Test
    public void test1() throws Exception {
        val method = this.getClass().getMethod("method1", String.class);
        assertAsString("method1", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.lang.String \\E(arg0|name)\\Q]", asList(method.getParameters()));
        assertAsString("[class java.lang.RuntimeException]", method.getExceptionTypes());
        assertAsString("[@functionalj.types.Struct(\\E.*\\Q)]", method.getAnnotations());
        assertAsString("[]", method.getTypeParameters());
    }
    
    public <T> String method2(T name) throws RuntimeException {
        return "" + name;
    }
    
    @Test
    public void test2() throws Exception {
        val method = this.getClass().getMethod("method2", Object.class);
        assertAsString("method2", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[T \\E(arg0|name)\\Q]", asList(method.getParameters()));
        assertAsString("[class java.lang.RuntimeException]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[T]", method.getTypeParameters());
        val parameter0 = method.getParameters()[0];
        assertAsString("T \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("T", parameter0.getParameterizedType());
        assertAsString("T", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("class java.lang.Object", parameter0.getType());
        val typeParameter0 = method.getTypeParameters()[0];
        assertAsString("T", typeParameter0.toString());
        assertAsString("[class java.lang.Object]", typeParameter0.getBounds());
        assertAsString("T", typeParameter0.getName());
        assertAsString("T", typeParameter0.getTypeName());
    }
    
    public <T extends Number> String method3(T name) {
        return "" + name;
    }
    
    @Test
    public void test3() throws Exception {
        val method = this.getClass().getMethod("method3", Number.class);
        assertAsString("method3", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[T \\E(arg0|name)\\Q]", asList(method.getParameters()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[T]", method.getTypeParameters());
        val parameter0 = method.getParameters()[0];
        assertAsString("T \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("T", parameter0.getParameterizedType());
        assertAsString("T", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("class java.lang.Number", parameter0.getType());
        val typeParameter0 = method.getTypeParameters()[0];
        assertAsString("T", typeParameter0.toString());
        assertAsString("[class java.lang.Number]", typeParameter0.getBounds());
        assertAsString("T", typeParameter0.getName());
        assertAsString("T", typeParameter0.getTypeName());
    }
    
    public <T extends Number> String method4(List<T> name) {
        return "" + name;
    }
    
    @Test
    public void test4() throws Exception {
        val method = this.getClass().getMethod("method4", List.class);
        assertAsString("method4", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.util.List<T> \\E(arg0|name)\\Q]", asList(method.getParameters()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[T]", method.getTypeParameters());
        val parameter0 = method.getParameters()[0];
        assertAsString("java.util.List<T> \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("java.util.List<T>", parameter0.getParameterizedType());
        assertAsString("java.util.List<T>", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("interface java.util.List", parameter0.getType());
        val typeParameter0 = method.getTypeParameters()[0];
        assertAsString("T", typeParameter0.toString());
        assertAsString("[class java.lang.Number]", typeParameter0.getBounds());
        assertAsString("T", typeParameter0.getName());
        assertAsString("T", typeParameter0.getTypeName());
    }
    
    public String method5(List<? extends Number> name) {
        return "" + name;
    }
    
    @Test
    public void test5() throws Exception {
        val method = this.getClass().getMethod("method5", List.class);
        assertAsString("method5", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.util.List<? extends java.lang.Number> \\E(arg0|name)\\Q]", asList(method.getParameters()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[]", method.getTypeParameters());
        val parameter0 = method.getParameters()[0];
        assertAsString("java.util.List<? extends java.lang.Number> \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("java.util.List<? extends java.lang.Number>", parameter0.getParameterizedType());
        assertAsString("java.util.List<? extends java.lang.Number>", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("interface java.util.List", parameter0.getType());
    }
    
    public String method6(List<? super Number> name) {
        return "" + name;
    }
    
    @Test
    public void test6() throws Exception {
        val method = this.getClass().getMethod("method6", List.class);
        assertAsString("method6", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.util.List<? super java.lang.Number>]", asList(method.getGenericParameterTypes()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[]", method.getTypeParameters());
        val parameter0 = method.getParameters()[0];
        assertAsString("java.util.List<? super java.lang.Number> \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("java.util.List<? super java.lang.Number>", parameter0.getParameterizedType());
        assertAsString("java.util.List<? super java.lang.Number>", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("interface java.util.List", parameter0.getType());
        assertAsString("java.util.List<? super java.lang.Number>", method.getGenericParameterTypes()[0]);
        if (method.getGenericParameterTypes()[0] instanceof ParameterizedType) {
            val genericParameterType = (ParameterizedType) method.getGenericParameterTypes()[0];
            assertAsString("java.util.List<? super java.lang.Number>", genericParameterType);
            assertAsString("[? super java.lang.Number]", genericParameterType.getActualTypeArguments());
            assertAsString(null, genericParameterType.getOwnerType());
            assertAsString("interface java.util.List", genericParameterType.getRawType());
            if (genericParameterType.getActualTypeArguments()[0] instanceof WildcardType) {
                val typeArgument = (WildcardType) genericParameterType.getActualTypeArguments()[0];
                assertAsString("? super java.lang.Number", typeArgument);
                assertAsString("[class java.lang.Number]", typeArgument.getLowerBounds());
                assertAsString("[class java.lang.Object]", typeArgument.getUpperBounds());
            }
        }
    }
    
    public String method7(List<? extends Number> name) {
        return "" + name;
    }
    
    @Test
    public void test7() throws Exception {
        val method = this.getClass().getMethod("method7", List.class);
        assertAsString("method7", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.util.List<? extends java.lang.Number>]", asList(method.getGenericParameterTypes()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[]", method.getTypeParameters());
        val parameter0 = method.getParameters()[0];
        assertAsString("java.util.List<? extends java.lang.Number> \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("java.util.List<? extends java.lang.Number>", parameter0.getParameterizedType());
        assertAsString("java.util.List<? extends java.lang.Number>", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("interface java.util.List", parameter0.getType());
        assertAsString("java.util.List<? extends java.lang.Number>", method.getGenericParameterTypes()[0]);
        if (method.getGenericParameterTypes()[0] instanceof ParameterizedType) {
            val genericParameterType = (ParameterizedType) method.getGenericParameterTypes()[0];
            assertAsString("java.util.List<? extends java.lang.Number>", genericParameterType);
            assertAsString("[? extends java.lang.Number]", genericParameterType.getActualTypeArguments());
            assertAsString(null, genericParameterType.getOwnerType());
            assertAsString("interface java.util.List", genericParameterType.getRawType());
            if (genericParameterType.getActualTypeArguments()[0] instanceof WildcardType) {
                val typeArgument = (WildcardType) genericParameterType.getActualTypeArguments()[0];
                assertAsString("? extends java.lang.Number", typeArgument);
                assertAsString("[]", typeArgument.getLowerBounds());
                assertAsString("[class java.lang.Number]", typeArgument.getUpperBounds());
            }
        }
    }
    
    public String method8(List<?> name) {
        return "" + name;
    }
    
    @Test
    public void test8() throws Exception {
        val method = this.getClass().getMethod("method8", List.class);
        assertAsString("method8", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.util.List<?>]", asList(method.getGenericParameterTypes()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[]", method.getTypeParameters());
        val parameter0 = method.getParameters()[0];
        assertAsString("java.util.List<?> \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("java.util.List<?>", parameter0.getParameterizedType());
        assertAsString("java.util.List<?>", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("interface java.util.List", parameter0.getType());
        assertAsString("java.util.List<?>", method.getGenericParameterTypes()[0]);
        if (method.getGenericParameterTypes()[0] instanceof ParameterizedType) {
            val genericParameterType = (ParameterizedType) method.getGenericParameterTypes()[0];
            assertAsString("java.util.List<?>", genericParameterType);
            assertAsString("[?]", genericParameterType.getActualTypeArguments());
            assertAsString(null, genericParameterType.getOwnerType());
            assertAsString("interface java.util.List", genericParameterType.getRawType());
            if (genericParameterType.getActualTypeArguments()[0] instanceof WildcardType) {
                val typeArgument = (WildcardType) genericParameterType.getActualTypeArguments()[0];
                assertAsString("?", typeArgument);
                assertAsString("[]", typeArgument.getLowerBounds());
                assertAsString("[class java.lang.Object]", typeArgument.getUpperBounds());
            }
        }
    }
    
    public <T extends Number> String method9(List<? super T> name) {
        return "" + name;
    }
    
    @Test
    public void test9() throws Exception {
        val method = this.getClass().getMethod("method9", List.class);
        assertAsString("method9", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.util.List<? super T>]", asList(method.getGenericParameterTypes()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[T]", method.getTypeParameters());
        if (method.getTypeParameters()[0] instanceof TypeVariable) {
            @SuppressWarnings("rawtypes")
            val typeVariable = (TypeVariable) method.getTypeParameters()[0];
            assertAsString("T", typeVariable);
            assertAsString("T", typeVariable.getName());
            assertAsString("T", typeVariable.getTypeName());
            assertAsString("[class java.lang.Number]", typeVariable.getBounds());
        }
        val parameter0 = method.getParameters()[0];
        assertAsString("java.util.List<? super T> \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("java.util.List<? super T>", parameter0.getParameterizedType());
        assertAsString("java.util.List<? super T>", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("interface java.util.List", parameter0.getType());
        assertAsString("java.util.List<? super T>", method.getGenericParameterTypes()[0]);
        if (method.getGenericParameterTypes()[0] instanceof ParameterizedType) {
            val genericParameterType = (ParameterizedType) method.getGenericParameterTypes()[0];
            assertAsString("java.util.List<? super T>", genericParameterType);
            assertAsString("[? super T]", genericParameterType.getActualTypeArguments());
            assertAsString(null, genericParameterType.getOwnerType());
            assertAsString("interface java.util.List", genericParameterType.getRawType());
            if (genericParameterType.getActualTypeArguments()[0] instanceof WildcardType) {
                val typeArgument = (WildcardType) genericParameterType.getActualTypeArguments()[0];
                assertAsString("? super T", typeArgument);
                assertAsString("[T]", typeArgument.getLowerBounds());
                assertAsString("true", typeArgument.getLowerBounds()[0] instanceof TypeVariable);
                assertAsString("[class java.lang.Object]", typeArgument.getUpperBounds());
            }
        }
    }
    
    public <T extends Number & CharSequence, D extends CharSequence> String method10(List<? super T> name, D value) {
        return "" + name;
    }
    
    @Test
    public void test10() throws Exception {
        val method = this.getClass().getMethod("method10", List.class, CharSequence.class);
        assertAsString("method10", method.getName());
        assertAsString("class java.lang.String", method.getReturnType());
        assertAsString("[java.util.List<? super T>, D]", asList(method.getGenericParameterTypes()));
        assertAsString("[]", method.getExceptionTypes());
        assertAsString("[]", method.getAnnotations());
        assertAsString("[T, D]", method.getTypeParameters());
        if (method.getTypeParameters()[0] instanceof TypeVariable) {
            @SuppressWarnings("rawtypes")
            val typeVariable = (TypeVariable) method.getTypeParameters()[0];
            assertAsString("T", typeVariable);
            assertAsString("T", typeVariable.getName());
            assertAsString("T", typeVariable.getTypeName());
            assertAsString("[class java.lang.Number, interface java.lang.CharSequence]", typeVariable.getBounds());
        }
        if (method.getTypeParameters()[1] instanceof TypeVariable) {
            @SuppressWarnings("rawtypes")
            val typeVariable = (TypeVariable) method.getTypeParameters()[1];
            assertAsString("D", typeVariable);
            assertAsString("D", typeVariable.getName());
            assertAsString("D", typeVariable.getTypeName());
            assertAsString("[interface java.lang.CharSequence]", typeVariable.getBounds());
        }
        val parameter0 = method.getParameters()[0];
        assertAsString("java.util.List<? super T> \\E(arg0|name)\\Q", parameter0.toString());
        assertAsString("java.util.List<? super T>", parameter0.getParameterizedType());
        assertAsString("java.util.List<? super T>", parameter0.getParameterizedType().getTypeName());
        assertAsString("\\E(arg0|name)\\Q", parameter0.getName());
        assertAsString("interface java.util.List", parameter0.getType());
        assertAsString("java.util.List<? super T>", method.getGenericParameterTypes()[0]);
        if (method.getGenericParameterTypes()[0] instanceof ParameterizedType) {
            val genericParameterType = (ParameterizedType) method.getGenericParameterTypes()[0];
            assertAsString("java.util.List<? super T>", genericParameterType);
            assertAsString("[? super T]", genericParameterType.getActualTypeArguments());
            assertAsString(null, genericParameterType.getOwnerType());
            assertAsString("interface java.util.List", genericParameterType.getRawType());
            if (genericParameterType.getActualTypeArguments()[0] instanceof WildcardType) {
                val typeArgument = (WildcardType) genericParameterType.getActualTypeArguments()[0];
                assertAsString("? super T", typeArgument);
                assertAsString("[T]", typeArgument.getLowerBounds());
                assertAsString("true", typeArgument.getLowerBounds()[0] instanceof TypeVariable);
                assertAsString("[class java.lang.Object]", typeArgument.getUpperBounds());
            }
        }
        val parameter1 = method.getParameters()[1];
        assertAsString("D \\E(arg1|value)\\Q", parameter1.toString());
        assertAsString("D", parameter1.getParameterizedType());
        assertAsString("D", parameter1.getParameterizedType().getTypeName());
        assertAsString("\\E(arg1|value)\\Q", parameter1.getName());
        assertAsString("interface java.lang.CharSequence", parameter1.getType());
        assertAsString("D", method.getGenericParameterTypes()[1]);
        assertAsString("false", method.getGenericParameterTypes()[1] instanceof ParameterizedType);
    }
    
    public <T extends Number> String method11(List<? extends T> name) {
        return "" + name;
    }
    
    @Test
    public void test11() throws Exception {
        val method = this.getClass().getMethod("method11", List.class);
        if (method.getTypeParameters()[0] instanceof TypeVariable) {
            @SuppressWarnings("rawtypes")
            val typeVariable = (TypeVariable) method.getTypeParameters()[0];
            assertAsString("T", typeVariable);
            assertAsString("T", typeVariable.getName());
            assertAsString("T", typeVariable.getTypeName());
            assertAsString("[class java.lang.Number]", typeVariable.getBounds());
        }
        if (method.getGenericParameterTypes()[0] instanceof ParameterizedType) {
            val genericParameterType = (ParameterizedType) method.getGenericParameterTypes()[0];
            assertAsString("java.util.List<? extends T>", genericParameterType);
            assertAsString("[? extends T]", genericParameterType.getActualTypeArguments());
            assertAsString(null, genericParameterType.getOwnerType());
            assertAsString("interface java.util.List", genericParameterType.getRawType());
            if (genericParameterType.getActualTypeArguments()[0] instanceof WildcardType) {
                val typeArgument = (WildcardType) genericParameterType.getActualTypeArguments()[0];
                assertAsString("? extends T", typeArgument);
                assertAsString("[]", typeArgument.getLowerBounds());
                assertAsString("[T]", typeArgument.getUpperBounds());
                assertAsString("true", typeArgument.getUpperBounds()[0] instanceof TypeVariable);
            }
        }
    }
}
