package functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.processor.generator.SourceSpec.Configurations;
import functionalj.annotations.processor.generator.model.GenDataObject;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentOptionalChildTest {
    
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
            new Getter("optionalName", new Type.TypeBuilder()
                                .simpleName("Optional")
                                .generics(asList(new Type("String", "java.lang")))
                                .packageName("java.util")
                                .build()),
            new Getter("optionalChild", new Type.TypeBuilder()
                                .simpleName("Optional")
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
                "import functionalj.lens.LensSpec;\n" + 
                "import functionalj.lens.ObjectLensImpl;\n" + 
                "import functionalj.lens.OptionalLens;\n" + 
                "import functionalj.lens.StringLens;\n" + 
                "import java.util.Optional;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child.ChildLens;\n" +
                "\n" + 
                "public class Parent implements Definitions.ParentDef {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final Optional<String> optionalName;\n" + 
                "    private final Optional<Child> optionalChild;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null, null);\n" + 
                "    }\n" + 
                "    public Parent(Optional<String> optionalName, Optional<Child> optionalChild) {\n" + 
                "        this.optionalName = optionalName;\n" + 
                "        this.optionalChild = optionalChild;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Optional<String> optionalName() {\n" + 
                "        return optionalName;\n" + 
                "    }\n" + 
                "    public Optional<Child> optionalChild() {\n" + 
                "        return optionalChild;\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(Optional<String> optionalName) {\n" + 
                "        return postReConstruct(new Parent(optionalName, optionalChild));\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(Supplier<Optional<String>> optionalName) {\n" + 
                "        return postReConstruct(new Parent(optionalName.get(), optionalChild));\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(Function<Optional<String>, Optional<String>> optionalName) {\n" + 
                "        return postReConstruct(new Parent(optionalName.apply(this.optionalName), optionalChild));\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(BiFunction<Parent, Optional<String>, Optional<String>> optionalName) {\n" + 
                "        return postReConstruct(new Parent(optionalName.apply(this, this.optionalName), optionalChild));\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(Optional<Child> optionalChild) {\n" + 
                "        return postReConstruct(new Parent(optionalName, optionalChild));\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(Supplier<Optional<Child>> optionalChild) {\n" + 
                "        return postReConstruct(new Parent(optionalName, optionalChild.get()));\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(Function<Optional<Child>, Optional<Child>> optionalChild) {\n" + 
                "        return postReConstruct(new Parent(optionalName, optionalChild.apply(this.optionalChild)));\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(BiFunction<Parent, Optional<Child>, Optional<Child>> optionalChild) {\n" + 
                "        return postReConstruct(new Parent(optionalName, optionalChild.apply(this, this.optionalChild)));\n" + 
                "    }\n" + 
                "    private static Parent postReConstruct(Parent object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + 
                "        \n" + 
                "        public final OptionalLens<HOST, String, StringLens<HOST>> optionalName = createSubOptionalLens(Parent::optionalName, Parent::withOptionalName, spec->()->spec);\n" + 
                "        public final OptionalLens<HOST, Child, ChildLens<HOST>> optionalChild = createSubOptionalLens(Parent::optionalChild, Parent::withOptionalChild, ChildLens::new);\n" + 
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
