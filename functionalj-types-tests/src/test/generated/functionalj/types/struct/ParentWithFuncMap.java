package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.FuncMapLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.map.FuncMap;
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

@Generated(value = "FunctionalJ",date = "2021-06-26T17:34:49.099798", comments = "functionalj.types.struct.WithFuncMapTest.WithFuncMapTest.IParent4")

public class ParentWithFuncMap implements WithFuncMapTest.IParent4,IStruct,Pipeable<ParentWithFuncMap> {
    
    public static final ParentWithFuncMap.ParentWithFuncMapLens<ParentWithFuncMap> theParentWithFuncMap = new ParentWithFuncMap.ParentWithFuncMapLens<>(LensSpec.of(ParentWithFuncMap.class));
    public static final ParentWithFuncMap.ParentWithFuncMapLens<ParentWithFuncMap> eachParentWithFuncMap = theParentWithFuncMap;
    public final FuncMap<String, Child> children;
    
    public ParentWithFuncMap(FuncMap<String, Child> children) {
        this.children = functionalj.map.ImmutableFuncMap.from(children);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public ParentWithFuncMap __data() throws Exception  {
        return this;
    }
    public FuncMap<String, Child> children() {
        return children;
    }
    public ParentWithFuncMap withChildren(FuncMap<String, Child> children) {
        return new ParentWithFuncMap(children);
    }
    public ParentWithFuncMap withChildren(Supplier<FuncMap<String, Child>> children) {
        return new ParentWithFuncMap(children.get());
    }
    public ParentWithFuncMap withChildren(Function<FuncMap<String, Child>, FuncMap<String, Child>> children) {
        return new ParentWithFuncMap(children.apply(this.children));
    }
    public ParentWithFuncMap withChildren(BiFunction<ParentWithFuncMap, FuncMap<String, Child>, FuncMap<String, Child>> children) {
        return new ParentWithFuncMap(children.apply(this, this.children));
    }
    public static ParentWithFuncMap fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        ParentWithFuncMap obj = new ParentWithFuncMap(
                    (FuncMap<String, Child>)$utils.fromMapValue(map.get("children"), $schema.get("children"))
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
        map.put("children", new functionalj.types.struct.generator.Getter("children", new functionalj.types.Type("functionalj.map", null, "FuncMap", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.String", null, java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()))), new functionalj.types.Generic("functionalj.types.struct.Child", null, java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.struct", null, "Child", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "ParentWithFuncMap[" + "children: " + children() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class ParentWithFuncMapLens<HOST> extends ObjectLensImpl<HOST, ParentWithFuncMap> {
        
        public final FuncMapLens<HOST, String, Child, StringLens<HOST>, ObjectLens<HOST, Child>> children = createSubFuncMapLens(ParentWithFuncMap::children, ParentWithFuncMap::withChildren, StringLens::of, ObjectLens::of);
        
        public ParentWithFuncMapLens(LensSpec<HOST, ParentWithFuncMap> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final ParentWithFuncMapBuilder_ready children(FuncMap<String, Child> children) {
            return ()->{
                return new ParentWithFuncMap(
                    children
                );
            };
        }
        
        public static interface ParentWithFuncMapBuilder_ready {
            
            public ParentWithFuncMap build();
            
            
            
        }
        
        
    }
    
}