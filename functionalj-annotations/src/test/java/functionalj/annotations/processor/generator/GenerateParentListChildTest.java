package functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.processor.generator.SourceSpec.Configurations;
import functionalj.annotations.processor.generator.model.GenDataObject;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentListChildTest {
    
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
                                .simpleName("List")
                                .generics(asList(new Type("String", "java.lang")))
                                .packageName("java.util")
                                .build()),
            new Getter("children", new Type.TypeBuilder()
                                .simpleName("List")
                                .generics(asList(new Type("Child", "me.test")))
                                .packageName("java.util")
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
                "import functionalj.lens.lenses.ListLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import functionalj.types.list.ImmutableList;\n" + 
                "import java.util.List;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final List<String> names;\n" + 
                "    private final List<Child> children;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null, null);\n" + 
                "    }\n" + 
                "    public Parent(List<String> names, List<Child> children) {\n" + 
                "        this.names = ImmutableList.of(names);\n" + 
                "        this.children = ImmutableList.of(children);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public List<String> names() {\n" + 
                "        return names;\n" + 
                "    }\n" + 
                "    public List<Child> children() {\n" + 
                "        return children;\n" + 
                "    }\n" + 
                "    public Parent withNames(String ... names) {\n" + 
                "        return postReConstruct(new Parent(java.util.Arrays.asList(names), children));\n" + 
                "    }\n" + 
                "    public Parent withNames(List<String> names) {\n" + 
                "        return postReConstruct(new Parent(names, children));\n" + 
                "    }\n" + 
                "    public Parent withNames(Supplier<List<String>> names) {\n" + 
                "        return postReConstruct(new Parent(names.get(), children));\n" + 
                "    }\n" + 
                "    public Parent withNames(Function<List<String>, List<String>> names) {\n" + 
                "        return postReConstruct(new Parent(names.apply(this.names), children));\n" + 
                "    }\n" + 
                "    public Parent withNames(BiFunction<Parent, List<String>, List<String>> names) {\n" + 
                "        return postReConstruct(new Parent(names.apply(this, this.names), children));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Child ... children) {\n" + 
                "        return postReConstruct(new Parent(names, java.util.Arrays.asList(children)));\n" + 
                "    }\n" + 
                "    public Parent withChildren(List<Child> children) {\n" + 
                "        return postReConstruct(new Parent(names, children));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Supplier<List<Child>> children) {\n" + 
                "        return postReConstruct(new Parent(names, children.get()));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Function<List<Child>, List<Child>> children) {\n" + 
                "        return postReConstruct(new Parent(names, children.apply(this.children)));\n" + 
                "    }\n" + 
                "    public Parent withChildren(BiFunction<Parent, List<Child>, List<Child>> children) {\n" + 
                "        return postReConstruct(new Parent(names, children.apply(this, this.children)));\n" + 
                "    }\n" + 
                "    private static Parent postReConstruct(Parent object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + 
                "        \n" + 
                "        public final ListLens<HOST, String, StringLens<HOST>> names = createSubListLens(Parent::names, Parent::withNames, StringLens::of);\n" + 
                "        public final ListLens<HOST, Child, ChildLens<HOST>> children = createSubListLens(Parent::children, Parent::withChildren, ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
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
                    configures,          // Configurations
                    getters);
        val dataObjSpec = new DataObjectBuilder(sourceSpec).build();
        val generated   = new GenDataObject(dataObjSpec).toText();
        return generated;
    }
    
}
