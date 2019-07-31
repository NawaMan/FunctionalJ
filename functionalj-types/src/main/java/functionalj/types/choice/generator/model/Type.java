// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.choice.generator.model;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.val;

public class Type {
    
    public final String  packageName;
    public final String  encloseName;
    public final String  simpleName;
    public final boolean isVirtual;
    public final List<Generic> generics;
    
    public Type(String name) {
        this(null, null, name);
    }
    public Type(String pckg, String name) {
        this(pckg, null, name, new ArrayList<Generic>());
    }
    public Type(String pckg, String encloseClass, String name) {
        this(pckg, encloseClass, name, new ArrayList<Generic>());
    }
    public Type(String pckg, String encloseClass, String name, List<Generic> generics) {
        this.packageName = pckg;
        this.simpleName  = name;
        this.encloseName = encloseClass;
        this.generics    = unmodifiableList(generics);
        this.isVirtual   = false;
    }
    
    public Type(String encloseName, String simpleName, String packageName, boolean isVirtual, List<Generic> generics) {
        this.encloseName = encloseName;
        this.simpleName  = simpleName;
        this.packageName = packageName;
        this.isVirtual   = isVirtual;
        this.generics    = generics;
    }
    
    private Type(String simpleName, boolean isVirtual) {
        if (!isVirtual)
            throw new IllegalArgumentException();
        this.encloseName = null;
        this.simpleName         = simpleName;
        this.packageName         = null;
        this.isVirtual    = isVirtual;
        this.generics     = emptyList();
    }
    
    public static Type newVirtualType(String name) {
        return new Type(name, true);
    }
    
    public String  packageName() { return packageName; }
    public String  encloseName() { return encloseName; }
    public String  simpleName()  { return simpleName; }
    public boolean isVirtual()   { return isVirtual; }
    
    public List<Generic> getGenerics() {
        return (generics == null) ? emptyList() : generics;
    }
    
    public String fullName() {
        return asList(packageName, encloseName, simpleName)
                .stream()
                .filter(Objects::nonNull)
                .collect(joining("."));
    }
    
    public functionalj.types.struct.generator.Type toStructType() {
        if (this.equals(Type.STRING))
            return functionalj.types.struct.generator.Type.STRING;
        if (this.equals(Type.OBJECT))
            return functionalj.types.struct.generator.Type.OBJECT;
        
        
        val encloseName = this.encloseName;
        val simpleName  = this.simpleName;
        val packageName = this.packageName;
        val generics    = this.generics.stream()
                        .map(g -> g.getBoundTypes().stream().findFirst().get())
                        .map(t -> t.toStructType())
                        .collect(toList());
        val structType = new functionalj.types.struct.generator.Type(packageName, encloseName, simpleName, generics);
        return structType;
    }
    
    public static final Type INTEGER = new Type("java.lang", "Integer");
    public static final Type LONG    = new Type("java.lang", "Long");
    public static final Type BOOLEAN = new Type("java.lang", "Boolean");
    public static final Type DOUBLE  = new Type("java.lang", "Double");
    public static final Type BYTE    = new Type("java.lang", "Byte");
    public static final Type SHORT   = new Type("java.lang", "Short");
    public static final Type FLOAT   = new Type("java.lang", "Float");
    public static final Type CHAR    = new Type("java.lang", "Character");
    public static final Type STRING  = new Type("String");
    public static final Type OBJECT  = new Type("Object");
    
    public String genericParams() {
        return (generics.isEmpty() ? "" : (generics.stream().map(g -> g.name).collect(joining(","))));
    }
    public String generics() {
        return (generics.isEmpty() ? "" : ("<" + genericParams() + ">"));
    }
    public String typeWithGenerics() {
        return simpleName + generics();
    }
    public String genericDefParams() {
        return (generics.isEmpty() ? "" : (generics.stream().map(g -> g.withBound).collect(joining(","))));
    }
    public String genericDef() {
        return (generics.isEmpty() ? "" : ("<" + genericDefParams() + ">"));
    }
    public String typeWithGenericDef() {
        return simpleName + genericDef();
    }
    
    public Type getPredicateType() {
        val toString = this.toString();
        if ("int"    .equals(toString)) return INTEGER;
        if ("long"   .equals(toString)) return LONG;
        if ("boolean".equals(toString)) return BOOLEAN;
        if ("double" .equals(toString)) return DOUBLE;
        if ("char"   .equals(toString)) return CHAR;
        if ("byte"   .equals(toString)) return BYTE;
        if ("short"  .equals(toString)) return SHORT;
        if ("float"  .equals(toString)) return FLOAT;
        return this;
    }
    
    public String toString() {
        val generics = ofNullable(this.generics)
                .filter(l -> !l.isEmpty())
                .map   (l -> this.generics.stream())
                .map   (s -> s.map(g -> g.name))
                .map   (c -> c.collect(joining(",")))
                .map   (s -> "<" + s + ">")
                .orElse("");
        return asList(packageName, encloseName, simpleName + generics)
                .stream()
                .filter(Objects::nonNull)
                .collect(joining("."));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Type)
            return toString().equals(String.valueOf(obj));
        if (obj instanceof functionalj.types.struct.generator.Type)
            return toString().equals(String.valueOf(obj));
        return false;
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(packageName),
                toStringLiteral(encloseName),
                toStringLiteral(simpleName),
                toListCode     (generics, Generic::toCode)
        );
        return "new " + Type.class.getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
    @SuppressWarnings("unchecked")
    public <T> Class<T> toClass() {
        if (isPrimitive())
            return getPredicateType().toClass();
        
        try {
            return ((Class<T>)Class.forName(fullName()));
        } catch (ClassNotFoundException e) {
            // Bad bad bad
            throw new RuntimeException(e);
        }
    }
    public boolean isPrimitive() {
        val toString = this.toString();
        if ("int"    .equals(toString)) return true;
        if ("long"   .equals(toString)) return true;
        if ("boolean".equals(toString)) return true;
        if ("double" .equals(toString)) return true;
        if ("char"   .equals(toString)) return true;
        if ("byte"   .equals(toString)) return true;
        if ("short"  .equals(toString)) return true;
        if ("float"  .equals(toString)) return true;
        return false;
    }
}