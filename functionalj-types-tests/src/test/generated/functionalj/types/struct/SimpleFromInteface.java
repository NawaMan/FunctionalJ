package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
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
import javax.annotation.Generated;

@Generated(value = "FunctionalJ",date = "2021-06-27T22:28:42.470330", comments = "functionalj.types.struct.SimpleStructTest.SimpleStructTest.SimpleDOInterface")

public class SimpleFromInteface implements SimpleStructTest.SimpleDOInterface,IStruct,Pipeable<SimpleFromInteface> {
    
    public static final SimpleFromInteface.SimpleFromIntefaceLens<SimpleFromInteface> theSimpleFromInteface = new SimpleFromInteface.SimpleFromIntefaceLens<>(LensSpec.of(SimpleFromInteface.class));
    public static final SimpleFromInteface.SimpleFromIntefaceLens<SimpleFromInteface> eachSimpleFromInteface = theSimpleFromInteface;
    public final String name;
    
    public SimpleFromInteface() {
        this(null);
    }
    public SimpleFromInteface(String name) {
        this.name = java.util.Optional.ofNullable(name).orElseGet(()->null);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public SimpleFromInteface __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public SimpleFromInteface withName(String name) {
        return new SimpleFromInteface(name);
    }
    public SimpleFromInteface withName(Supplier<String> name) {
        return new SimpleFromInteface(name.get());
    }
    public SimpleFromInteface withName(Function<String, String> name) {
        return new SimpleFromInteface(name.apply(this.name));
    }
    public SimpleFromInteface withName(BiFunction<SimpleFromInteface, String, String> name) {
        return new SimpleFromInteface(name.apply(this, this.name));
    }
    public static SimpleFromInteface fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        SimpleFromInteface obj = new SimpleFromInteface(
                    (String)$utils.fromMapValue(map.get("name"), $schema.get("name"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", functionalj.types.IStruct.$utils.toMapValueObject(name));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));
        return map;
    }
    public String toString() {
        return "SimpleFromInteface[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class SimpleFromIntefaceLens<HOST> extends ObjectLensImpl<HOST, SimpleFromInteface> {
        
        public final StringLens<HOST> name = createSubLens(SimpleFromInteface::name, SimpleFromInteface::withName, StringLens::of);
        
        public SimpleFromIntefaceLens(LensSpec<HOST, SimpleFromInteface> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final SimpleFromIntefaceBuilder_ready name(String name) {
            return ()->{
                return new SimpleFromInteface(
                    name
                );
            };
        }
        
        public static interface SimpleFromIntefaceBuilder_ready {
            
            public SimpleFromInteface build();
            
            
            
        }
        
        
        public SimpleFromInteface build() {
            return name(null).build();
        }
        
    }
    
}