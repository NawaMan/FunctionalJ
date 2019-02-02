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
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.val;

public class Type {
    public final String pckg;
    public final String encloseClass;
    public final String name;
    public final List<Generic> generics;
    
    public Type(String pckg, String name) {
        this(pckg, null, name, new ArrayList<Generic>());
    }
    public Type(String name) {
        this(null, null, name);
    }
    public Type(String pckg, String encloseClass, String name) {
        this(pckg, encloseClass, name, new ArrayList<Generic>());
    }
    public Type(String pckg, String encloseClass, String name, List<Generic> generics) {
        this.pckg = pckg;
        this.name = name;
        this.encloseClass = encloseClass;
        this.generics     = unmodifiableList(generics);
    }
    
    public String getPckg() {
        return pckg;
    }
    public String getEncloseClass() {
        return encloseClass;
    }
    public String getName() {
        return name;
    }
    public List<Generic> getGenerics() {
        return generics;
    }
    public String fullName() {
        return asList(pckg, encloseClass, name)
                .stream()
                .filter(Objects::nonNull)
                .collect(joining("."));
    }
    
    public String toString() {
        val generics = ofNullable(this.generics)
                .filter(l -> !l.isEmpty())
                .map   (l -> this.generics.stream())
                .map   (s -> s.map(g -> g.name))
                .map   (c -> c.collect(joining(",")))
                .map   (s -> "<" + s + ">")
                .orElse("");
        return asList(pckg, encloseClass, name + generics)
                .stream()
                .filter(Objects::nonNull)
                .collect(joining("."));
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
        return name + generics();
    }
    public String genericDefParams() {
        return (generics.isEmpty() ? "" : (generics.stream().map(g -> g.withBound).collect(joining(","))));
    }
    public String genericDef() {
        return (generics.isEmpty() ? "" : ("<" + genericDefParams() + ">"));
    }
    public String typeWithGenericDef() {
        return name + genericDef();
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
    public String toCode() {
        val params = asList(
                toStringLiteral(pckg),
                toStringLiteral(encloseClass),
                toStringLiteral(name),
                toListCode     (generics, Generic::toCode)
        );
        return "new " + Type.class.getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
}