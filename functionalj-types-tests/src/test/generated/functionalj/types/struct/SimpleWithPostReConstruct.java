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

@Generated(value = "FunctionalJ",date = "2021-06-26T17:34:49.112636", comments = "functionalj.types.struct.WithPostConstruct.WithPostConstruct.SimpleDOWithPostReConstruct")

public class SimpleWithPostReConstruct implements WithPostConstruct.SimpleDOWithPostReConstruct,IStruct,Pipeable<SimpleWithPostReConstruct> {
    
    public static final SimpleWithPostReConstruct.SimpleWithPostReConstructLens<SimpleWithPostReConstruct> theSimpleWithPostReConstruct = new SimpleWithPostReConstruct.SimpleWithPostReConstructLens<>(LensSpec.of(SimpleWithPostReConstruct.class));
    public static final SimpleWithPostReConstruct.SimpleWithPostReConstructLens<SimpleWithPostReConstruct> eachSimpleWithPostReConstruct = theSimpleWithPostReConstruct;
    public final String name;
    
    public SimpleWithPostReConstruct(String name) {
        this.name = $utils.notNull(name);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public SimpleWithPostReConstruct __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public SimpleWithPostReConstruct withName(String name) {
        return new SimpleWithPostReConstruct(name);
    }
    public SimpleWithPostReConstruct withName(Supplier<String> name) {
        return new SimpleWithPostReConstruct(name.get());
    }
    public SimpleWithPostReConstruct withName(Function<String, String> name) {
        return new SimpleWithPostReConstruct(name.apply(this.name));
    }
    public SimpleWithPostReConstruct withName(BiFunction<SimpleWithPostReConstruct, String, String> name) {
        return new SimpleWithPostReConstruct(name.apply(this, this.name));
    }
    public static SimpleWithPostReConstruct fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        SimpleWithPostReConstruct obj = new SimpleWithPostReConstruct(
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
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "SimpleWithPostReConstruct[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class SimpleWithPostReConstructLens<HOST> extends ObjectLensImpl<HOST, SimpleWithPostReConstruct> {
        
        public final StringLens<HOST> name = createSubLens(SimpleWithPostReConstruct::name, SimpleWithPostReConstruct::withName, StringLens::of);
        
        public SimpleWithPostReConstructLens(LensSpec<HOST, SimpleWithPostReConstruct> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final SimpleWithPostReConstructBuilder_ready name(String name) {
            return ()->{
                return new SimpleWithPostReConstruct(
                    name
                );
            };
        }
        
        public static interface SimpleWithPostReConstructBuilder_ready {
            
            public SimpleWithPostReConstruct build();
            
            
            
        }
        
        
    }
    
}