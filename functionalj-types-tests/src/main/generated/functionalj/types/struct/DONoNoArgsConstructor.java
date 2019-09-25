package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

// functionalj.types.struct.DOConstructorTest.DOConstructorTest.DONoNoArgsConstructorDef

public class DONoNoArgsConstructor implements DOConstructorTest.DONoNoArgsConstructorDef,IStruct,Pipeable<DONoNoArgsConstructor> {
    
    public static final DONoNoArgsConstructor.DONoNoArgsConstructorLens<DONoNoArgsConstructor> theDONoNoArgsConstructor = new DONoNoArgsConstructor.DONoNoArgsConstructorLens<>(LensSpec.of(DONoNoArgsConstructor.class));
    public static final DONoNoArgsConstructor.DONoNoArgsConstructorLens<DONoNoArgsConstructor> eachDONoNoArgsConstructor = theDONoNoArgsConstructor;
    public final String name;
    
    public DONoNoArgsConstructor(String name) {
        this.name = $utils.notNull(name);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public DONoNoArgsConstructor __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public DONoNoArgsConstructor withName(String name) {
        return new DONoNoArgsConstructor(name);
    }
    public DONoNoArgsConstructor withName(Supplier<String> name) {
        return new DONoNoArgsConstructor(name.get());
    }
    public DONoNoArgsConstructor withName(Function<String, String> name) {
        return new DONoNoArgsConstructor(name.apply(this.name));
    }
    public DONoNoArgsConstructor withName(BiFunction<DONoNoArgsConstructor, String, String> name) {
        return new DONoNoArgsConstructor(name.apply(this, this.name));
    }
    public static DONoNoArgsConstructor fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        DONoNoArgsConstructor obj = new DONoNoArgsConstructor(
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
        Map<String, Getter> map = new HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "DONoNoArgsConstructor[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class DONoNoArgsConstructorLens<HOST> extends ObjectLensImpl<HOST, DONoNoArgsConstructor> {
        
        public final StringLens<HOST> name = createSubLens(DONoNoArgsConstructor::name, DONoNoArgsConstructor::withName, StringLens::of);
        
        public DONoNoArgsConstructorLens(LensSpec<HOST, DONoNoArgsConstructor> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final DONoNoArgsConstructorBuilder_ready name(String name) {
            return ()->{
                return new DONoNoArgsConstructor(
                    name
                );
            };
        }
        
        public static interface DONoNoArgsConstructorBuilder_ready {
            
            public DONoNoArgsConstructor build();
            
            
            
        }
        
        
    }
    
}