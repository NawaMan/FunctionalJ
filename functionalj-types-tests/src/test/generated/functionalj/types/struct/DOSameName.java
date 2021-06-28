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

@Generated(value = "FunctionalJ",date = "2021-06-26T17:34:48.961142", comments = "functionalj.types.struct.DOWithSameClassName.DOWithSameClassName.DOSameName")

public class DOSameName implements DOWithSameClassName.DOSameName,IStruct,Pipeable<DOSameName> {
    
    public static final DOSameName.DOSameNameLens<DOSameName> theDOSameName = new DOSameName.DOSameNameLens<>(LensSpec.of(DOSameName.class));
    public static final DOSameName.DOSameNameLens<DOSameName> eachDOSameName = theDOSameName;
    public final String name;
    
    public DOSameName(String name) {
        this.name = $utils.notNull(name);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public DOSameName __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public DOSameName withName(String name) {
        return new DOSameName(name);
    }
    public DOSameName withName(Supplier<String> name) {
        return new DOSameName(name.get());
    }
    public DOSameName withName(Function<String, String> name) {
        return new DOSameName(name.apply(this.name));
    }
    public DOSameName withName(BiFunction<DOSameName, String, String> name) {
        return new DOSameName(name.apply(this, this.name));
    }
    public static DOSameName fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        DOSameName obj = new DOSameName(
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
        return "DOSameName[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class DOSameNameLens<HOST> extends ObjectLensImpl<HOST, DOSameName> {
        
        public final StringLens<HOST> name = createSubLens(DOSameName::name, DOSameName::withName, StringLens::of);
        
        public DOSameNameLens(LensSpec<HOST, DOSameName> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final DOSameNameBuilder_ready name(String name) {
            return ()->{
                return new DOSameName(
                    name
                );
            };
        }
        
        public static interface DOSameNameBuilder_ready {
            
            public DOSameName build();
            
            
            
        }
        
        
    }
    
}