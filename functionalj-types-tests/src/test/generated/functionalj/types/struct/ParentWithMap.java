package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.MapLens;
import functionalj.lens.lenses.ObjectLens;
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

@Generated(value = "FunctionalJ",date = "2021-06-26T17:34:48.721361", comments = "functionalj.types.struct.WithMapTest.WithMapTest.IParent4")

public class ParentWithMap implements WithMapTest.IParent4,IStruct,Pipeable<ParentWithMap> {
    
    public static final ParentWithMap.ParentWithMapLens<ParentWithMap> theParentWithMap = new ParentWithMap.ParentWithMapLens<>(LensSpec.of(ParentWithMap.class));
    public static final ParentWithMap.ParentWithMapLens<ParentWithMap> eachParentWithMap = theParentWithMap;
    public final Map<String, Child> children;
    
    public ParentWithMap(Map<String, Child> children) {
        this.children = functionalj.map.ImmutableFuncMap.from(children);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public ParentWithMap __data() throws Exception  {
        return this;
    }
    public Map<String, Child> children() {
        return children;
    }
    public ParentWithMap withChildren(Map<String, Child> children) {
        return new ParentWithMap(children);
    }
    public ParentWithMap withChildren(Supplier<Map<String, Child>> children) {
        return new ParentWithMap(children.get());
    }
    public ParentWithMap withChildren(Function<Map<String, Child>, Map<String, Child>> children) {
        return new ParentWithMap(children.apply(this.children));
    }
    public ParentWithMap withChildren(BiFunction<ParentWithMap, Map<String, Child>, Map<String, Child>> children) {
        return new ParentWithMap(children.apply(this, this.children));
    }
    public static ParentWithMap fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        ParentWithMap obj = new ParentWithMap(
                    (Map<String, Child>)$utils.fromMapValue(map.get("children"), $schema.get("children"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("children", functionalj.types.IStruct.$utils.toMapValueObject(children));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("children", new functionalj.types.struct.generator.Getter("children", new functionalj.types.Type("java.util", null, "Map", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.String", null, java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()))), new functionalj.types.Generic("functionalj.types.struct.Child", null, java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.struct", null, "Child", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "ParentWithMap[" + "children: " + children() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class ParentWithMapLens<HOST> extends ObjectLensImpl<HOST, ParentWithMap> {
        
        public final MapLens<HOST, String, Child, StringLens<HOST>, ObjectLens<HOST, Child>> children = createSubMapLens(ParentWithMap::children, ParentWithMap::withChildren, StringLens::of, ObjectLens::of);
        
        public ParentWithMapLens(LensSpec<HOST, ParentWithMap> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final ParentWithMapBuilder_ready children(Map<String, Child> children) {
            return ()->{
                return new ParentWithMap(
                    children
                );
            };
        }
        
        public static interface ParentWithMapBuilder_ready {
            
            public ParentWithMap build();
            
            
            
        }
        
        
    }
    
}