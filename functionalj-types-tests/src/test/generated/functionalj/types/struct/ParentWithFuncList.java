package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.FuncListLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.list.FuncList;
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

@Generated(value = "FunctionalJ",date = "2021-06-27T22:28:42.389826", comments = "functionalj.types.struct.WithFuncListTest.WithFuncListTest.IParent2")

public class ParentWithFuncList implements WithFuncListTest.IParent2,IStruct,Pipeable<ParentWithFuncList> {
    
    public static final ParentWithFuncList.ParentWithFuncListLens<ParentWithFuncList> theParentWithFuncList = new ParentWithFuncList.ParentWithFuncListLens<>(LensSpec.of(ParentWithFuncList.class));
    public static final ParentWithFuncList.ParentWithFuncListLens<ParentWithFuncList> eachParentWithFuncList = theParentWithFuncList;
    public final FuncList<String> names;
    public final FuncList<Child> children;
    
    public ParentWithFuncList(FuncList<String> names, FuncList<Child> children) {
        this.names = functionalj.list.ImmutableFuncList.from(names);
        this.children = functionalj.list.ImmutableFuncList.from(children);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public ParentWithFuncList __data() throws Exception  {
        return this;
    }
    public FuncList<String> names() {
        return names;
    }
    public FuncList<Child> children() {
        return children;
    }
    public ParentWithFuncList withNames(String ... names) {
        return new ParentWithFuncList(functionalj.list.ImmutableFuncList.of(names), children);
    }
    public ParentWithFuncList withNames(FuncList<String> names) {
        return new ParentWithFuncList(names, children);
    }
    public ParentWithFuncList withNames(Supplier<FuncList<String>> names) {
        return new ParentWithFuncList(names.get(), children);
    }
    public ParentWithFuncList withNames(Function<FuncList<String>, FuncList<String>> names) {
        return new ParentWithFuncList(names.apply(this.names), children);
    }
    public ParentWithFuncList withNames(BiFunction<ParentWithFuncList, FuncList<String>, FuncList<String>> names) {
        return new ParentWithFuncList(names.apply(this, this.names), children);
    }
    public ParentWithFuncList withChildren(Child ... children) {
        return new ParentWithFuncList(names, functionalj.list.ImmutableFuncList.of(children));
    }
    public ParentWithFuncList withChildren(FuncList<Child> children) {
        return new ParentWithFuncList(names, children);
    }
    public ParentWithFuncList withChildren(Supplier<FuncList<Child>> children) {
        return new ParentWithFuncList(names, children.get());
    }
    public ParentWithFuncList withChildren(Function<FuncList<Child>, FuncList<Child>> children) {
        return new ParentWithFuncList(names, children.apply(this.children));
    }
    public ParentWithFuncList withChildren(BiFunction<ParentWithFuncList, FuncList<Child>, FuncList<Child>> children) {
        return new ParentWithFuncList(names, children.apply(this, this.children));
    }
    public static ParentWithFuncList fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        ParentWithFuncList obj = new ParentWithFuncList(
                    (FuncList<String>)$utils.fromMapValue(map.get("names"), $schema.get("names")),
                    (FuncList<Child>)$utils.fromMapValue(map.get("children"), $schema.get("children"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("names", functionalj.types.IStruct.$utils.toMapValueObject(names));
        map.put("children", functionalj.types.IStruct.$utils.toMapValueObject(children));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("names", new functionalj.types.struct.generator.Getter("names", new functionalj.types.Type("functionalj.list", null, "FuncList", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.String", null, java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("children", new functionalj.types.struct.generator.Getter("children", new functionalj.types.Type("functionalj.list", null, "FuncList", java.util.Arrays.asList(new functionalj.types.Generic("functionalj.types.struct.Child", null, java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.struct", null, "Child", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "ParentWithFuncList[" + "names: " + names() + ", " + "children: " + children() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class ParentWithFuncListLens<HOST> extends ObjectLensImpl<HOST, ParentWithFuncList> {
        
        public final FuncListLens<HOST, String, StringLens<HOST>> names = createSubFuncListLens(ParentWithFuncList::names, ParentWithFuncList::withNames, StringLens::of);
        public final FuncListLens<HOST, Child, ObjectLens<HOST, Child>> children = createSubFuncListLens(ParentWithFuncList::children, ParentWithFuncList::withChildren, ObjectLens::of);
        
        public ParentWithFuncListLens(LensSpec<HOST, ParentWithFuncList> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final ParentWithFuncListBuilder_withoutChildren names(FuncList<String> names) {
            return (FuncList<Child> children)->{
            return ()->{
                return new ParentWithFuncList(
                    names,
                    children
                );
            };
            };
        }
        
        public static interface ParentWithFuncListBuilder_withoutChildren {
            
            public ParentWithFuncListBuilder_ready children(FuncList<Child> children);
            
        }
        public static interface ParentWithFuncListBuilder_ready {
            
            public ParentWithFuncList build();
            
            
            
        }
        
        
    }
    
}