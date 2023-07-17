package functionalj.types.input;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
import functionalj.types.Generated;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.struct.generator.Getter;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Generated(value = "FunctionalJ",date = "2023-07-17T00:04:34.760", comments = "functionalj.types.input.InputMethodElementTest")

@SuppressWarnings("all")

public class method1 implements IStruct,Pipeable<method1> {
    
    public static final method1.method1Lens<method1> themethod1 = new method1.method1Lens<>("themethod1", LensSpec.of(method1.class));
    public static final method1.method1Lens<method1> eachmethod1 = themethod1;
    public final String name;
    
    public method1(String name) {
        this.name = $utils.notNull(name);
        functionalj.result.ValidationException.ensure(functionalj.types.input.InputMethodElementTest.method1(name), this);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public method1 __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public method1 withName(String name) {
        return new method1(name);
    }
    public method1 withName(Supplier<String> name) {
        return new method1(name.get());
    }
    public method1 withName(Function<String, String> name) {
        return new method1(name.apply(this.name));
    }
    public method1 withName(BiFunction<method1, String, String> name) {
        return new method1(name.apply(this, this.name));
    }
    public static method1 fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        method1 obj = new method1(
                    (String)$utils.extractPropertyFromMap(method1.class, String.class, map, $schema, "name")
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", $utils.toMapValueObject(name));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "method1[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class method1Lens<HOST> extends ObjectLensImpl<HOST, method1> {
        
        public final StringLens<HOST> name = createSubLens("name", method1::name, method1::withName, StringLens::of);
        
        public method1Lens(String name, LensSpec<HOST, method1> spec) {
            super(name, spec);
        }
        
    }
    public static final class Builder {
        
        public final method1Builder_ready name(String name) {
            return ()->{
                return new method1(
                    name
                );
            };
        }
        
        public static interface method1Builder_ready {
            
            public method1 build();
            
            
            
        }
        
        
    }
    
}

//    - Parameter [name] is a type element: false
//    - Parameter [name] toString         : name
//    - Parameter [name] simple name      : name
//    - Parameter [name] asType.toString  : java.lang.String
//    - Parameter [name] asType.kind      : DECLARED
//    - Parameter [name] asType.class     : class=[class com.sun.tools.javac.code.Type$ClassType]
//    - Parameter [name] asType.asTypeElement                     : java.lang.String
//    - Parameter [name] asType.asTypeElement.simpleName          : String
//    - Parameter [name] asType.asTypeElement.packageQualifiedName: java.lang
//    - Parameter [name] asType.asTypeElement.kind                : CLASS
//    - Parameter [name] asType.asTypeElement.modifiers           : [public, final]
//    - Parameter [name] asType.asTypeElement.enclosingElement    : java.lang
//    - Parameter [name] asType.asTypeElement.enclosedElements    : [java.lang.String.CaseInsensitiveComparator, value, hash, serialVersionUID, serialPersistentFields, CASE_INSENSITIVE_ORDER, String(), String(java.lang.String), String(char[]), String(char[],int,int), String(int[],int,int), String(byte[],int,int,int), String(byte[],int), checkBounds(byte[],int,int), String(byte[],int,int,java.lang.String), String(byte[],int,int,java.nio.charset.Charset), String(byte[],java.lang.String), String(byte[],java.nio.charset.Charset), String(byte[],int,int), String(byte[]), String(java.lang.StringBuffer), String(java.lang.StringBuilder), String(char[],boolean), length(), isEmpty(), charAt(int), codePointAt(int), codePointBefore(int), codePointCount(int,int), offsetByCodePoints(int,int), getChars(char[],int), getChars(int,int,char[],int), getBytes(int,int,byte[],int), getBytes(java.lang.String), getBytes(java.nio.charset.Charset), getBytes(), equals(java.lang.Object), contentEquals(java.lang.StringBuffer), nonSyncContentEquals(java.lang.AbstractStringBuilder), contentEquals(java.lang.CharSequence), equalsIgnoreCase(java.lang.String), compareTo(java.lang.String), compareToIgnoreCase(java.lang.String), regionMatches(int,java.lang.String,int,int), regionMatches(boolean,int,java.lang.String,int,int), startsWith(java.lang.String,int), startsWith(java.lang.String), endsWith(java.lang.String), hashCode(), indexOf(int), indexOf(int,int), indexOfSupplementary(int,int), lastIndexOf(int), lastIndexOf(int,int), lastIndexOfSupplementary(int,int), indexOf(java.lang.String), indexOf(java.lang.String,int), indexOf(char[],int,int,java.lang.String,int), indexOf(char[],int,int,char[],int,int,int), lastIndexOf(java.lang.String), lastIndexOf(java.lang.String,int), lastIndexOf(char[],int,int,java.lang.String,int), lastIndexOf(char[],int,int,char[],int,int,int), substring(int), substring(int,int), subSequence(int,int), concat(java.lang.String), replace(char,char), matches(java.lang.String), contains(java.lang.CharSequence), replaceFirst(java.lang.String,java.lang.String), replaceAll(java.lang.String,java.lang.String), replace(java.lang.CharSequence,java.lang.CharSequence), split(java.lang.String,int), split(java.lang.String), join(java.lang.CharSequence,java.lang.CharSequence...), join(java.lang.CharSequence,java.lang.Iterable<? extends java.lang.CharSequence>), toLowerCase(java.util.Locale), toLowerCase(), toUpperCase(java.util.Locale), toUpperCase(), trim(), toString(), toCharArray(), format(java.lang.String,java.lang.Object...), format(java.util.Locale,java.lang.String,java.lang.Object...), valueOf(java.lang.Object), valueOf(char[]), valueOf(char[],int,int), copyValueOf(char[],int,int), copyValueOf(char[]), valueOf(boolean), valueOf(char), valueOf(int), valueOf(long), valueOf(float), valueOf(double), intern(), <clinit>()]
//    - Parameter [name] asType.asTypeElement.qualifiedName       : java.lang.String
//    - Parameter [name] asType.asTypeElement.packageName         : java.lang
//    - Parameter [name] asType.asTypeElement.asType              : java.lang.String
//    - Parameter [name] asType.asTypeElement.getToString         : java.lang.String
//    - Parameter [name] asType.isDeclaredType                    : true
//    - Parameter [name] asType.isNoType                          : false
//    - Parameter [name] asType.typeKind                          : DECLARED
//    - Parameter [name] asType.getToString                       : java.lang.String
//    - Parameter [name] asType.typeArguments                     : []
//  ------------------------------------------------