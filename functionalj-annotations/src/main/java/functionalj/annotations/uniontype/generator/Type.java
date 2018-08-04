package functionalj.annotations.uniontype.generator;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.Objects;

import lombok.val;

public class Type {
    public final String pckg;
    public final String encloseClass;
    public final String name;
    public Type(String pckg, String name) {
        this(pckg, null, name);
    }
    public Type(String name) {
        this(null, null, name);
    }
    public Type(String pckg, String encloseClass, String name) {
        this.pckg = pckg;
        this.name = name;
        this.encloseClass = encloseClass;
    }
    public String toString() {
        return asList(pckg, encloseClass, name)
                .stream()
                .filter(Objects::nonNull)
                .collect(joining("."));
    }
    
    public static final Type integerType = new Type("java.lang", "Integer");
    public static final Type longType    = new Type("java.lang", "Long");
    public static final Type booleanType = new Type("java.lang", "Boolean");
    public static final Type doubleType  = new Type("java.lang", "Double");
    public static final Type byteType    = new Type("java.lang", "Byte");
    public static final Type shortType   = new Type("java.lang", "Short");
    public static final Type floatType   = new Type("java.lang", "Float");
    public static final Type charType    = new Type("java.lang", "Character");
    
    public Type getPredicateType() {
        val toString = this.toString();
        if ("int"    .equals(toString)) return integerType;
        if ("long"   .equals(toString)) return longType;
        if ("boolean".equals(toString)) return booleanType;
        if ("double" .equals(toString)) return doubleType;
        if ("char"   .equals(toString)) return charType;
        if ("byte"   .equals(toString)) return byteType;
        if ("short"  .equals(toString)) return shortType;
        if ("float"  .equals(toString)) return floatType;
        return this;
    }
}