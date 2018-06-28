package functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.processor.generator.SourceSpec.Configurations;
import functionalj.annotations.processor.generator.model.GenDataObject;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentMapChildTest {
    
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
            new Getter("children", new Type.TypeBuilder()
                                .simpleName("Map")
                                .generics(asList(
                                        new Type("String", "java.lang"), 
                                        new Type("Child",  "me.test")))
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
                "import functionalj.lens.LensSpec;\n" + 
                "import functionalj.lens.MapLens;\n" + 
                "import functionalj.lens.ObjectLensImpl;\n" + 
                "import functionalj.lens.StringLens;\n" + 
                "import functionalj.types.ImmutableMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final Map<String, Child> children;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null);\n" + 
                "    }\n" + 
                "    public Parent(Map<String, Child> children) {\n" + 
                "        this.children = ImmutableMap.of(children);\n" +
                "    }\n" + 
                "    \n" + 
                "    public Map<String, Child> children() {\n" + 
                "        return children;\n" + 
                "    }\n" + 
                "    public Parent withChildren(Map<String, Child> children) {\n" + 
                "        return postReConstruct(new Parent(children));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Supplier<Map<String, Child>> children) {\n" + 
                "        return postReConstruct(new Parent(children.get()));\n" + 
                "    }\n" + 
                "    public Parent withChildren(Function<Map<String, Child>, Map<String, Child>> children) {\n" + 
                "        return postReConstruct(new Parent(children.apply(this.children)));\n" + 
                "    }\n" + 
                "    public Parent withChildren(BiFunction<Parent, Map<String, Child>, Map<String, Child>> children) {\n" + 
                "        return postReConstruct(new Parent(children.apply(this, this.children)));\n" + 
                "    }\n" + 
                "    private static Parent postReConstruct(Parent object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + 
                "        \n" + 
                "        public final MapLens<HOST, String, Child, StringLens<HOST>, ChildLens<HOST>> children = createSubMapLens(Parent::children, Parent::withChildren, spec->()->spec, ChildLens::new);\n" + 
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
