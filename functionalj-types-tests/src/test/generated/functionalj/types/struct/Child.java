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

@Generated(value = "FunctionalJ",date = "2021-06-26T17:34:49.199243", comments = "functionalj.types.struct.SubDOTest.SubDOTest.IChild")

public class Child implements SubDOTest.IChild,IStruct,Pipeable<Child> {
    
    public static final Child.ChildLens<Child> theChild = new Child.ChildLens<>(LensSpec.of(Child.class));
    public static final Child.ChildLens<Child> eachChild = theChild;
    public final String name;
    
    public Child(String name) {
        this.name = $utils.notNull(name);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Child __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public Child withName(String name) {
        return new Child(name);
    }
    public Child withName(Supplier<String> name) {
        return new Child(name.get());
    }
    public Child withName(Function<String, String> name) {
        return new Child(name.apply(this.name));
    }
    public Child withName(BiFunction<Child, String, String> name) {
        return new Child(name.apply(this, this.name));
    }
    public static Child fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Child obj = new Child(
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
        return "Child[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class ChildLens<HOST> extends ObjectLensImpl<HOST, Child> {
        
        public final StringLens<HOST> name = createSubLens(Child::name, Child::withName, StringLens::of);
        
        public ChildLens(LensSpec<HOST, Child> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final ChildBuilder_ready name(String name) {
            return ()->{
                return new Child(
                    name
                );
            };
        }
        
        public static interface ChildBuilder_ready {
            
            public Child build();
            
            
            
        }
        
        
    }
    
}