// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;

public interface InputType {
    
    public static InputType of(Environment environment, TypeMirror typeMirror) {
        if (typeMirror == null)
            return null;
        return new Impl(environment, typeMirror);
    }
    
    public static class Impl implements InputType {
        
        final Environment environment;
        
        private final TypeMirror typeMirror;
        
        Impl(Environment environment, TypeMirror typeMirror) {
            this.environment = environment;
            this.typeMirror = typeMirror;
        }
        
        @Override
        public InputPrimitiveType asPrimitiveType() {
            return (typeMirror instanceof PrimitiveType) ? InputPrimitiveType.of(environment, ((PrimitiveType) typeMirror)) : null;
        }
        
        @Override
        public InputDeclaredType asDeclaredType() {
            return (typeMirror instanceof DeclaredType) ? InputDeclaredType.of(environment, (DeclaredType) typeMirror) : null;
        }
        
        public InputTypeVariable asTypeVariable() {
            return (typeMirror instanceof TypeVariable) ? InputTypeVariable.of(environment, (TypeVariable) typeMirror) : null;
        }
        
        @Override
        public boolean isNoType() {
            return typeMirror instanceof NoType;
        }
        
        @Override
        public TypeKind typeKind() {
            return typeMirror.getKind();
        }
        
        @Override
        public String getToString() {
            return typeMirror.toString();
        }
        
        @Override
        public String toString() {
            return typeMirror.toString();
        }
        
        public String insight() {
            String insight = "class=[" + typeMirror.getClass() + "]";
            if (typeMirror instanceof WildcardType) {
                WildcardType wcType = (WildcardType) typeMirror;
                insight += ", extends " + wcType.getExtendsBound();
                insight += ", supers " + wcType.getSuperBound();
            }
            return insight;
        }
    }
    
    public static abstract class Mock implements InputType {
        
        @SuppressWarnings("rawtypes")
        private static Map<Class, InputDeclaredType> declaredTypes = new HashMap<>();
        
        @SuppressWarnings("rawtypes")
        static final InputDeclaredType.Mock.Builder builderFromClass(Class clazz) {
            Set<Modifier> modifiers = new HashSet<Modifier>();
            if (java.lang.reflect.Modifier.isPublic(clazz.getModifiers()))
                modifiers.add(Modifier.PUBLIC);
            if (java.lang.reflect.Modifier.isProtected(clazz.getModifiers()))
                modifiers.add(Modifier.PROTECTED);
            if (java.lang.reflect.Modifier.isPrivate(clazz.getModifiers()))
                modifiers.add(Modifier.PRIVATE);
            if (java.lang.reflect.Modifier.isAbstract(clazz.getModifiers()))
                modifiers.add(Modifier.ABSTRACT);
            // Default?
            if (java.lang.reflect.Modifier.isStatic(clazz.getModifiers()))
                modifiers.add(Modifier.STATIC);
            if (java.lang.reflect.Modifier.isFinal(clazz.getModifiers()))
                modifiers.add(Modifier.FINAL);
            if (java.lang.reflect.Modifier.isTransient(clazz.getModifiers()))
                modifiers.add(Modifier.TRANSIENT);
            if (java.lang.reflect.Modifier.isVolatile(clazz.getModifiers()))
                modifiers.add(Modifier.VOLATILE);
            if (java.lang.reflect.Modifier.isSynchronized(clazz.getModifiers()))
                modifiers.add(Modifier.SYNCHRONIZED);
            if (java.lang.reflect.Modifier.isNative(clazz.getModifiers()))
                modifiers.add(Modifier.NATIVE);
            if (java.lang.reflect.Modifier.isStrict(clazz.getModifiers()))
                modifiers.add(Modifier.STRICTFP);
            
            boolean     isInterface  = clazz.isInterface();
            boolean     isEnum       = clazz.isEnum();
            boolean     isAnnotation = clazz.isAnnotation();
            ElementKind kind         = isInterface ? ElementKind.INTERFACE : isEnum ? ElementKind.ENUM : isAnnotation ? ElementKind.ANNOTATION_TYPE : ElementKind.CLASS;
            String      className    = clazz.getCanonicalName();
            
            InputTypeElement               typeElement = new InputTypeElement.Mock.Builder().toString(className).kind(kind).qualifiedName(className).superClass(fromClass(clazz.getSuperclass())).interfaces(Stream.of(clazz.getInterfaces()).map(Mock::fromClass).collect(toList())).modifiers(modifiers).build();
            InputDeclaredType.Mock.Builder declaredType = new InputDeclaredType.Mock.Builder().kind(TypeKind.DECLARED).toString(className).asTypeElement(typeElement);
            return declaredType;
        }
        
        @SuppressWarnings("rawtypes")
        public static final InputDeclaredType fromClass(Class clazz, InputTypeArgument... typeArguments) {
            if (declaredTypes.containsKey(clazz)) {
                return declaredTypes.get(clazz);
            }
            
            InputDeclaredType.Mock.Builder declaredTypeBuilder = builderFromClass(clazz);
            InputDeclaredType.Mock         declaredType        = declaredTypeBuilder.typeArguments(typeArguments).build();
            declaredTypes.put(clazz, declaredType);
            return declaredType;
        }
        
        // -- Primitives --
        public static final InputPrimitiveType primaryBoolean = new InputPrimitiveType.Mock(TypeKind.BOOLEAN);
        
        public static final InputPrimitiveType primaryByte = new InputPrimitiveType.Mock(TypeKind.BYTE);
        
        public static final InputPrimitiveType primaryShort = new InputPrimitiveType.Mock(TypeKind.SHORT);
        
        public static final InputPrimitiveType primaryInt = new InputPrimitiveType.Mock(TypeKind.INT);
        
        public static final InputPrimitiveType primaryLong = new InputPrimitiveType.Mock(TypeKind.LONG);
        
        public static final InputPrimitiveType primaryChar = new InputPrimitiveType.Mock(TypeKind.CHAR);
        
        public static final InputPrimitiveType primaryFloat = new InputPrimitiveType.Mock(TypeKind.FLOAT);
        
        public static final InputPrimitiveType primaryDouble = new InputPrimitiveType.Mock(TypeKind.DOUBLE);
        
        public static final InputPrimitiveType primaryVoid = new InputPrimitiveType.Mock(TypeKind.VOID);
        
        // -- Boxed --
        public static final InputDeclaredType boxedBoolean = fromClass(Boolean.class);
        
        public static final InputDeclaredType boxedByte = fromClass(Byte.class);
        
        public static final InputDeclaredType boxedShort = fromClass(Short.class);
        
        public static final InputDeclaredType boxedInt = fromClass(Integer.class);
        
        public static final InputDeclaredType boxedLong = fromClass(Long.class);
        
        public static final InputDeclaredType boxedChar = fromClass(Character.class);
        
        public static final InputDeclaredType boxedFloat = fromClass(Float.class);
        
        public static final InputDeclaredType boxedDouble = fromClass(Double.class);
        
        public static final InputDeclaredType boxedVoid = fromClass(Void.class);
        
        public static final InputDeclaredType string = fromClass(String.class);
    }
    
    public default boolean isPrimitiveType() {
        return (asPrimitiveType() != null);
    }
    
    public default boolean isDeclaredType() {
        return (asDeclaredType() != null);
    }
    
    public default boolean isTypeVariable() {
        return (asTypeVariable() != null);
    }
    
    public InputPrimitiveType asPrimitiveType();
    
    public InputDeclaredType asDeclaredType();
    
    public InputTypeVariable asTypeVariable();
    
    public boolean isNoType();
    
    public TypeKind typeKind();
    
    public String getToString();
    
    public default InputTypeArgument toTypeArgument() {
        return new InputTypeArgument.Impl(this);
    }
    
    public default InputTypeArgument toTypeArgument(boolean asExtends) {
        return new InputTypeArgument.Impl(this, asExtends);
    }
    
    public default InputTypeArgument toTypeArgument(boolean asExtends, boolean asSuper) {
        return new InputTypeArgument.Impl(this, asExtends, asSuper);
    }
}
