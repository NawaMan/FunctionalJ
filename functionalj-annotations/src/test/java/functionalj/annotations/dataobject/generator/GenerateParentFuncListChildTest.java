package functionalj.annotations.dataobject.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.dataobject.generator.SourceSpec.Configurations;
import functionalj.annotations.dataobject.generator.model.GenDataObject;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentFuncListChildTest {
    
    private Configurations configures = new Configurations();
    {
        configures.coupleWithDefinition      = true;
        configures.generateNoArgConstructor  = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass         = true;
    }
    
    private String  definitionClassName = "Definitions.ParentDef";
    private String  targetClassName     = "Parent";
    private String  packageName         = "me.test";
    private boolean isClass             = false;
    
    private List<Getter> getters = asList(
            new Getter("names", new Type.TypeBuilder()
                                .simpleName("FuncList")
                                .generics(asList(new Type("String", "java.lang")))
                                .packageName("functionalj.list")
                                .build()),
            new Getter("children", new Type.TypeBuilder()
                                .simpleName("FuncList")
                                .generics(asList(new Type("Child", "me.test")))
                                .packageName("functionalj.list")
                                .build())
    );
    
    @Test
    public void testParent() {
        val code = generate();
        /* */
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import functionalj.annotations.IPostReConstruct;\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.FuncListLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import functionalj.list.FuncList;\n" + 
                "import functionalj.list.ImmutableList;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final FuncList<String> names;\n" + 
                "    private final FuncList<Child> children;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null, null);\n" + 
                "    }\n" + 
                "    public Parent(FuncList<String> names, FuncList<Child> children) {\n" + 
                "        this.names = ImmutableList.from(names);\n" + 
                "        this.children = ImmutableList.from(children);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public FuncList<String> names() {\n" + 
                "        return names;\n" + 
                "    }\n" + 
                "    public FuncList<Child> children() {\n" + 
                "        return children;\n" + 
                "    }\n" + 
                "    public Parent withNames(String ... names) {\n" + 
                "        return postReConstruct(new Parent(functionalj.list.ImmutableList.of(names), children));\n" + 
                "    }\n" + 
                "    public Parent withNames(FuncList<String> names) {\n" + 
                "        return postReConstruct(new Parent(names, children));\n" + 
                "    }\n" + 
                "    public Parent withNames(Supplier<FuncList<String>> names) {\n" + 
                "        return postReConstruct(new Parent(names.get(), children));\n" + 
                "    }\n" + 
                "    public Parent withNames(Function<FuncList<String>, FuncList<String>> names) {\n" + 
                "        return postReConstruct(new Parent(names.apply(this.names), children));\n" + 
                "    }\n" + 
                "    public Parent withNames(BiFunction<Parent, FuncList<String>, FuncList<String>> names) {\n" + 
                "        return postReConstruct(new Parent(names.apply(this, this.names), children));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Child ... children) {\n" + 
                "        return postReConstruct(new Parent(names, functionalj.list.ImmutableList.of(children)));\n" + 
                "    }\n" + 
                "    public Parent withChildren(FuncList<Child> children) {\n" + 
                "        return postReConstruct(new Parent(names, children));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Supplier<FuncList<Child>> children) {\n" + 
                "        return postReConstruct(new Parent(names, children.get()));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Function<FuncList<Child>, FuncList<Child>> children) {\n" + 
                "        return postReConstruct(new Parent(names, children.apply(this.children)));\n" + 
                "    }\n" + 
                "    public Parent withChildren(BiFunction<Parent, FuncList<Child>, FuncList<Child>> children) {\n" + 
                "        return postReConstruct(new Parent(names, children.apply(this, this.children)));\n" + 
                "    }\n" + 
                "    private static Parent postReConstruct(Parent object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Parent[\" + \"names: \" + names() + \", \" + \"children: \" + children() + \"]\";\n" + 
                "    }\n" + 
                "    public int hashCode() {\n" + 
                "        return toString().hashCode();\n" + 
                "    }\n" + 
                "    public boolean equals(Object another) {\n" + 
                "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + 
                "        \n" + 
                "        public final FuncListLens<HOST, String, StringLens<HOST>> names = createSubFuncListLens(Parent::names, Parent::withNames, StringLens::of);\n" + 
                "        public final FuncListLens<HOST, Child, ChildLens<HOST>> children = createSubFuncListLens(Parent::children, Parent::withChildren, ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_names names(FuncList<String> names) {\n" + 
                "            return new Builder_names(names);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_names {\n" + 
                "            \n" + 
                "            private final FuncList<String> names;\n" + 
                "            \n" + 
                "            private Builder_names(FuncList<String> names) {\n" + 
                "                this.names = names;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public FuncList<String> names() {\n" + 
                "                return names;\n" + 
                "            }\n" + 
                "            public Builder_names names(FuncList<String> names) {\n" + 
                "                return new Builder_names(names);\n" + 
                "            }\n" + 
                "            public Builder_names_children children(FuncList<Child> children) {\n" + 
                "                return new Builder_names_children(this, children);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_names_children {\n" + 
                "            \n" + 
                "            private final Builder_names parent;\n" + 
                "            private final FuncList<Child> children;\n" + 
                "            \n" + 
                "            private Builder_names_children(Builder_names parent, FuncList<Child> children) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.children = children;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public FuncList<String> names() {\n" + 
                "                return parent.names();\n" + 
                "            }\n" + 
                "            public FuncList<Child> children() {\n" + 
                "                return children;\n" + 
                "            }\n" + 
                "            public Builder_names_children names(FuncList<String> names) {\n" + 
                "                return parent.names(names).children(children);\n" + 
                "            }\n" + 
                "            public Builder_names_children children(FuncList<Child> children) {\n" + 
                "                return parent.children(children);\n" + 
                "            }\n" + 
                "            public Parent build() {\n" + 
                "                return new Parent(names(), children());\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}", code);
        /* */
    }
    
    private String generate() {
        return generate(null);
    }
    
    private String generate(Runnable setting) {
        if (setting != null)
            setting.run();
        
        SourceSpec sourceSpec = new SourceSpec(
                    definitionClassName, // specClassName
                    packageName,         // packageName
                    targetClassName,     // targetClassName
                    packageName,         // targetPackageName
                    isClass,             // isClass
                    null,
                    configures,          // Configurations
                    getters);
        val dataObjSpec = new DataObjectBuilder(sourceSpec).build();
        val generated   = new GenDataObject(dataObjSpec).toText();
        return generated;
    }
    
}
