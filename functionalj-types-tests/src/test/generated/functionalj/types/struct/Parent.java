package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(value = "FunctionalJ",date = "2021-06-26T17:34:48.840272", comments = "functionalj.types.struct.SubDOTest.SubDOTest.IParent")

public class Parent implements SubDOTest.IParent,IStruct,Pipeable<Parent> {
    
    public static final Parent.ParentLens<Parent> theParent = new Parent.ParentLens<>(LensSpec.of(Parent.class));
    public static final Parent.ParentLens<Parent> eachParent = theParent;
    public final String name;
    public final Child child;
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec("SubDOTest.IParent", "functionalj.types.struct", "SubDOTest", "Parent", "functionalj.types.struct", false, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("child", new functionalj.types.Type("functionalj.types.struct", null, "Child", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)), java.util.Arrays.asList("Child", "Parent"));
    
    public Parent(String name, Child child) {
        this.name = $utils.notNull(name);
        this.child = $utils.notNull(child);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Parent __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public Child child() {
        return child;
    }
    public Parent withName(String name) {
        return new Parent(name, child);
    }
    public Parent withName(Supplier<String> name) {
        return new Parent(name.get(), child);
    }
    public Parent withName(Function<String, String> name) {
        return new Parent(name.apply(this.name), child);
    }
    public Parent withName(BiFunction<Parent, String, String> name) {
        return new Parent(name.apply(this, this.name), child);
    }
    public Parent withChild(Child child) {
        return new Parent(name, child);
    }
    public Parent withChild(Supplier<Child> child) {
        return new Parent(name, child.get());
    }
    public Parent withChild(Function<Child, Child> child) {
        return new Parent(name, child.apply(this.child));
    }
    public Parent withChild(BiFunction<Parent, Child, Child> child) {
        return new Parent(name, child.apply(this, this.child));
    }
    public static Parent fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Parent obj = new Parent(
                    (String)$utils.fromMapValue(map.get("name"), $schema.get("name")),
                    (Child)$utils.fromMapValue(map.get("child"), $schema.get("child"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", functionalj.types.IStruct.$utils.toMapValueObject(name));
        map.put("child", functionalj.types.IStruct.$utils.toMapValueObject(child));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("child", new functionalj.types.struct.generator.Getter("child", new functionalj.types.Type("functionalj.types.struct", null, "Child", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Parent[" + "name: " + name() + ", " + "child: " + child() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {
        
        public final StringLens<HOST> name = createSubLens(Parent::name, Parent::withName, StringLens::of);
        public final Child.ChildLens<HOST> child = createSubLens(Parent::child, Parent::withChild, Child.ChildLens::new);
        
        public ParentLens(LensSpec<HOST, Parent> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final ParentBuilder_withoutChild name(String name) {
            return (Child child)->{
            return ()->{
                return new Parent(
                    name,
                    child
                );
            };
            };
        }
        
        public static interface ParentBuilder_withoutChild {
            
            public ParentBuilder_ready child(Child child);
            
        }
        public static interface ParentBuilder_ready {
            
            public Parent build();
            
            
            
        }
        
        
    }
    
}