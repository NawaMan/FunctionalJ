package functionalj.annotations.dataobject.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.dataobject.generator.SourceSpec.Configurations;
import functionalj.annotations.dataobject.generator.model.GenDataObject;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentMayBeChildTest {
    
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
            new Getter("mayBeName", new Type.TypeBuilder()
                                .simpleName("MayBe")
                                .generics(asList(new Type("String", "java.lang")))
                                .packageName("functionalj.types")
                                .build()),
            new Getter("mayBeChild", new Type.TypeBuilder()
                                .simpleName("MayBe")
                                .generics(asList(new Type("Child", "me.test")))
                                .packageName("functionalj.types")
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
                "import functionalj.lens.lenses.MayBeLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import functionalj.types.MayBe;\n" + 
                
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" +
                "\n" + 
                "public class Parent implements Definitions.ParentDef {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final MayBe<String> mayBeName;\n" + 
                "    private final MayBe<Child> mayBeChild;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null, null);\n" + 
                "    }\n" + 
                "    public Parent(MayBe<String> mayBeName, MayBe<Child> mayBeChild) {\n" + 
                "        this.mayBeName = mayBeName;\n" + 
                "        this.mayBeChild = mayBeChild;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public MayBe<String> mayBeName() {\n" + 
                "        return mayBeName;\n" + 
                "    }\n" + 
                "    public MayBe<Child> mayBeChild() {\n" + 
                "        return mayBeChild;\n" + 
                "    }\n" + 
                "    public Parent withMayBeName(MayBe<String> mayBeName) {\n" + 
                "        return postReConstruct(new Parent(mayBeName, mayBeChild));\n" + 
                "    }\n" + 
                "    public Parent withMayBeName(Supplier<MayBe<String>> mayBeName) {\n" + 
                "        return postReConstruct(new Parent(mayBeName.get(), mayBeChild));\n" + 
                "    }\n" + 
                "    public Parent withMayBeName(Function<MayBe<String>, MayBe<String>> mayBeName) {\n" + 
                "        return postReConstruct(new Parent(mayBeName.apply(this.mayBeName), mayBeChild));\n" + 
                "    }\n" + 
                "    public Parent withMayBeName(BiFunction<Parent, MayBe<String>, MayBe<String>> mayBeName) {\n" + 
                "        return postReConstruct(new Parent(mayBeName.apply(this, this.mayBeName), mayBeChild));\n" + 
                "    }\n" + 
                "    public Parent withMayBeChild(MayBe<Child> mayBeChild) {\n" + 
                "        return postReConstruct(new Parent(mayBeName, mayBeChild));\n" + 
                "    }\n" + 
                "    public Parent withMayBeChild(Supplier<MayBe<Child>> mayBeChild) {\n" + 
                "        return postReConstruct(new Parent(mayBeName, mayBeChild.get()));\n" + 
                "    }\n" + 
                "    public Parent withMayBeChild(Function<MayBe<Child>, MayBe<Child>> mayBeChild) {\n" + 
                "        return postReConstruct(new Parent(mayBeName, mayBeChild.apply(this.mayBeChild)));\n" + 
                "    }\n" + 
                "    public Parent withMayBeChild(BiFunction<Parent, MayBe<Child>, MayBe<Child>> mayBeChild) {\n" + 
                "        return postReConstruct(new Parent(mayBeName, mayBeChild.apply(this, this.mayBeChild)));\n" + 
                "    }\n" + 
                "    private static Parent postReConstruct(Parent object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + 
                "        \n" + 
                "        public final MayBeLens<HOST, String, StringLens<HOST>> mayBeName = createSubMayBeLens(Parent::mayBeName, Parent::withMayBeName, StringLens::of);\n" + 
                "        public final MayBeLens<HOST, Child, ChildLens<HOST>> mayBeChild = createSubMayBeLens(Parent::mayBeChild, Parent::withMayBeChild, ChildLens::new);\n" + 
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
