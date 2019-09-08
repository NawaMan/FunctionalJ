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

// functionalj.types.struct.DOConstructorTest.DOConstructorTest.DONoAllArgsConstructorDef

public class DONoAllArgsConstructor implements DOConstructorTest.DONoAllArgsConstructorDef,IStruct,Pipeable<DONoAllArgsConstructor> {
    
    public static final DONoAllArgsConstructor.DONoAllArgsConstructorLens<DONoAllArgsConstructor> theDONoAllArgsConstructor = new DONoAllArgsConstructor.DONoAllArgsConstructorLens<>(LensSpec.of(DONoAllArgsConstructor.class));
    public final String name;
    
    public DONoAllArgsConstructor() {
        this(null);
    }
    private DONoAllArgsConstructor(String name) {
        this.name = java.util.Optional.ofNullable(name).orElseGet(()->null);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public DONoAllArgsConstructor __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public DONoAllArgsConstructor withName(String name) {
        return new DONoAllArgsConstructor(name);
    }
    public DONoAllArgsConstructor withName(Supplier<String> name) {
        return new DONoAllArgsConstructor(name.get());
    }
    public DONoAllArgsConstructor withName(Function<String, String> name) {
        return new DONoAllArgsConstructor(name.apply(this.name));
    }
    public DONoAllArgsConstructor withName(BiFunction<DONoAllArgsConstructor, String, String> name) {
        return new DONoAllArgsConstructor(name.apply(this, this.name));
    }
    public static DONoAllArgsConstructor fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        DONoAllArgsConstructor obj = new DONoAllArgsConstructor(
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
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));
        return map;
    }
    public String toString() {
        return "DONoAllArgsConstructor[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class DONoAllArgsConstructorLens<HOST> extends ObjectLensImpl<HOST, DONoAllArgsConstructor> {
        
        public final StringLens<HOST> name = createSubLens(DONoAllArgsConstructor::name, DONoAllArgsConstructor::withName, StringLens::of);
        
        public DONoAllArgsConstructorLens(LensSpec<HOST, DONoAllArgsConstructor> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final DONoAllArgsConstructorBuilder_ready name(String name) {
            return ()->{
                return new DONoAllArgsConstructor(
                    name
                );
            };
        }
        
        public static interface DONoAllArgsConstructorBuilder_ready {
            
            public DONoAllArgsConstructor build();
            
            
            
        }
        
        
        public DONoAllArgsConstructor build() {
            return name(null).build();
        }
        
    }
    
}