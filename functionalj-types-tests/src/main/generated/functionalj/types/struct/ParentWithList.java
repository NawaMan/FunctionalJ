package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ListLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.Child;
import functionalj.types.struct.generator.Getter;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

// functionalj.types.struct.WithListTest.WithListTest.IParent2

public class ParentWithList implements WithListTest.IParent2,IStruct,Pipeable<ParentWithList> {
    
    public static final ParentWithList.ParentWithListLens<ParentWithList> theParentWithList = new ParentWithList.ParentWithListLens<>(LensSpec.of(ParentWithList.class));
    public static final ParentWithList.ParentWithListLens<ParentWithList> eachParentWithList = theParentWithList;
    public final List<String> names;
    public final List<Child> children;
    
    public ParentWithList(List<String> names, List<Child> children) {
        this.names = ImmutableList.from(names);
        this.children = ImmutableList.from(children);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public ParentWithList __data() throws Exception  {
        return this;
    }
    public List<String> names() {
        return names;
    }
    public List<Child> children() {
        return children;
    }
    public ParentWithList withNames(String ... names) {
        return new ParentWithList(java.util.Arrays.asList(names), children);
    }
    public ParentWithList withNames(List<String> names) {
        return new ParentWithList(names, children);
    }
    public ParentWithList withNames(Supplier<List<String>> names) {
        return new ParentWithList(names.get(), children);
    }
    public ParentWithList withNames(Function<List<String>, List<String>> names) {
        return new ParentWithList(names.apply(this.names), children);
    }
    public ParentWithList withNames(BiFunction<ParentWithList, List<String>, List<String>> names) {
        return new ParentWithList(names.apply(this, this.names), children);
    }
    public ParentWithList withChildren(Child ... children) {
        return new ParentWithList(names, java.util.Arrays.asList(children));
    }
    public ParentWithList withChildren(List<Child> children) {
        return new ParentWithList(names, children);
    }
    public ParentWithList withChildren(Supplier<List<Child>> children) {
        return new ParentWithList(names, children.get());
    }
    public ParentWithList withChildren(Function<List<Child>, List<Child>> children) {
        return new ParentWithList(names, children.apply(this.children));
    }
    public ParentWithList withChildren(BiFunction<ParentWithList, List<Child>, List<Child>> children) {
        return new ParentWithList(names, children.apply(this, this.children));
    }
    public static ParentWithList fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        ParentWithList obj = new ParentWithList(
                    (List<String>)$utils.fromMapValue(map.get("names"), $schema.get("names")),
                    (List<Child>)$utils.fromMapValue(map.get("children"), $schema.get("children"))
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
        Map<String, Getter> map = new HashMap<>();
        map.put("names", new functionalj.types.struct.generator.Getter("names", new functionalj.types.Type("java.util", null, "List", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.String", "java.lang.String", java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("children", new functionalj.types.struct.generator.Getter("children", new functionalj.types.Type("java.util", null, "List", java.util.Arrays.asList(new functionalj.types.Generic("functionalj.types.struct.Child", "functionalj.types.struct.Child", java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.struct", null, "Child", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "ParentWithList[" + "names: " + names() + ", " + "children: " + children() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class ParentWithListLens<HOST> extends ObjectLensImpl<HOST, ParentWithList> {
        
        public final ListLens<HOST, String, StringLens<HOST>> names = createSubListLens(ParentWithList::names, ParentWithList::withNames, StringLens::of);
        public final ListLens<HOST, Child, ObjectLens<HOST, Child>> children = createSubListLens(ParentWithList::children, ParentWithList::withChildren, ObjectLens::of);
        
        public ParentWithListLens(LensSpec<HOST, ParentWithList> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final ParentWithListBuilder_withoutChildren names(List<String> names) {
            return (List<Child> children)->{
            return ()->{
                return new ParentWithList(
                    names,
                    children
                );
            };
            };
        }
        
        public static interface ParentWithListBuilder_withoutChildren {
            
            public ParentWithListBuilder_ready children(List<Child> children);
            
        }
        public static interface ParentWithListBuilder_ready {
            
            public ParentWithList build();
            
            
            
        }
        
        
    }
    
}