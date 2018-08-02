package functionalj.annotations.dataobject.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.dataobject.generator.SourceSpec.Configurations;
import functionalj.annotations.dataobject.generator.model.GenDataObject;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentNullableChildTest {
    
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
            new Getter("nullableName", new Type.TypeBuilder()
                                .simpleName("Nullable")
                                .generics(asList(new Type("String", "java.lang")))
                                .packageName("nawaman.nullablej.nullable")
                                .build()),
            new Getter("nullableChild", new Type.TypeBuilder()
                                .simpleName("Nullable")
                                .generics(asList(new Type("Child", "me.test")))
                                .packageName("nawaman.nullablej.nullable")
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
                "import functionalj.lens.lenses.NullableLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "import nawaman.nullablej.nullable.Nullable;\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final Nullable<String> nullableName;\n" + 
                "    private final Nullable<Child> nullableChild;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null, null);\n" + 
                "    }\n" + 
                "    public Parent(Nullable<String> nullableName, Nullable<Child> nullableChild) {\n" + 
                "        this.nullableName = Nullable.of((nullableName == null) ? null : nullableName.get());\n" + 
                "        this.nullableChild = Nullable.of((nullableChild == null) ? null : nullableChild.get());\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Nullable<String> nullableName() {\n" + 
                "        return nullableName;\n" + 
                "    }\n" + 
                "    public Nullable<Child> nullableChild() {\n" + 
                "        return nullableChild;\n" + 
                "    }\n" + 
                "    public Parent withNullableName(Nullable<String> nullableName) {\n" + 
                "        return postReConstruct(new Parent(nullableName, nullableChild));\n" + 
                "    }\n" + 
                "    public Parent withNullableName(Supplier<Nullable<String>> nullableName) {\n" + 
                "        return postReConstruct(new Parent(nullableName.get(), nullableChild));\n" + 
                "    }\n" + 
                "    public Parent withNullableName(Function<Nullable<String>, Nullable<String>> nullableName) {\n" + 
                "        return postReConstruct(new Parent(nullableName.apply(this.nullableName), nullableChild));\n" + 
                "    }\n" + 
                "    public Parent withNullableName(BiFunction<Parent, Nullable<String>, Nullable<String>> nullableName) {\n" + 
                "        return postReConstruct(new Parent(nullableName.apply(this, this.nullableName), nullableChild));\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(Nullable<Child> nullableChild) {\n" + 
                "        return postReConstruct(new Parent(nullableName, nullableChild));\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(Supplier<Nullable<Child>> nullableChild) {\n" + 
                "        return postReConstruct(new Parent(nullableName, nullableChild.get()));\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(Function<Nullable<Child>, Nullable<Child>> nullableChild) {\n" + 
                "        return postReConstruct(new Parent(nullableName, nullableChild.apply(this.nullableChild)));\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(BiFunction<Parent, Nullable<Child>, Nullable<Child>> nullableChild) {\n" + 
                "        return postReConstruct(new Parent(nullableName, nullableChild.apply(this, this.nullableChild)));\n" + 
                "    }\n" + 
                "    private static Parent postReConstruct(Parent object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + 
                "        \n" + 
                "        public final NullableLens<HOST, String, StringLens<HOST>> nullableName = createSubNullableLens(Parent::nullableName, Parent::withNullableName, StringLens::of);\n" + 
                "        public final NullableLens<HOST, Child, ChildLens<HOST>> nullableChild = createSubNullableLens(Parent::nullableChild, Parent::withNullableChild, ChildLens::new);\n" + 
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
