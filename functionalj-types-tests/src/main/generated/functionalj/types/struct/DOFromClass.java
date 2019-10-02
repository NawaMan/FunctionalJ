package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
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

// functionalj.types.struct.DOFromClassTest.DOFromClassTest.DOFromClassDef

public class DOFromClass extends DOFromClassTest.DOFromClassDef implements IStruct,Pipeable<DOFromClass> {
    
    public static final DOFromClass.DOFromClassLens<DOFromClass> theDOFromClass = new DOFromClass.DOFromClassLens<>(LensSpec.of(DOFromClass.class));
    public static final DOFromClass.DOFromClassLens<DOFromClass> eachDOFromClass = theDOFromClass;
    public final String name;
    public final int count;
    
    public DOFromClass(String name, int count) {
        this.name = $utils.notNull(name);
        this.count = count;
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public DOFromClass __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public int count() {
        return count;
    }
    public DOFromClass withName(String name) {
        return new DOFromClass(name, count);
    }
    public DOFromClass withName(Supplier<String> name) {
        return new DOFromClass(name.get(), count);
    }
    public DOFromClass withName(Function<String, String> name) {
        return new DOFromClass(name.apply(this.name), count);
    }
    public DOFromClass withName(BiFunction<DOFromClass, String, String> name) {
        return new DOFromClass(name.apply(this, this.name), count);
    }
    public DOFromClass withCount(int count) {
        return new DOFromClass(name, count);
    }
    public DOFromClass withCount(Supplier<Integer> count) {
        return new DOFromClass(name, count.get());
    }
    public DOFromClass withCount(Function<Integer, Integer> count) {
        return new DOFromClass(name, count.apply(this.count));
    }
    public DOFromClass withCount(BiFunction<DOFromClass, Integer, Integer> count) {
        return new DOFromClass(name, count.apply(this, this.count));
    }
    public static DOFromClass fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        DOFromClass obj = new DOFromClass(
                    (String)$utils.fromMapValue(map.get("name"), $schema.get("name")),
                    (int)$utils.fromMapValue(map.get("count"), $schema.get("count"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", functionalj.types.IStruct.$utils.toMapValueObject(name));
        map.put("count", functionalj.types.IStruct.$utils.toMapValueObject(count));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("count", new functionalj.types.struct.generator.Getter("count", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "DOFromClass[" + "name: " + name() + ", " + "count: " + count() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class DOFromClassLens<HOST> extends ObjectLensImpl<HOST, DOFromClass> {
        
        public final StringLens<HOST> name = createSubLens(DOFromClass::name, DOFromClass::withName, StringLens::of);
        public final IntegerLens<HOST> count = createSubLensInt(DOFromClass::count, DOFromClass::withCount);
        
        public DOFromClassLens(LensSpec<HOST, DOFromClass> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final DOFromClassBuilder_withoutCount name(String name) {
            return (int count)->{
            return ()->{
                return new DOFromClass(
                    name,
                    count
                );
            };
            };
        }
        
        public static interface DOFromClassBuilder_withoutCount {
            
            public DOFromClassBuilder_ready count(int count);
            
        }
        public static interface DOFromClassBuilder_ready {
            
            public DOFromClass build();
            
            
            
        }
        
        
    }
    
}