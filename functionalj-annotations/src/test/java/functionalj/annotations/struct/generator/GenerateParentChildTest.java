package functionalj.annotations.struct.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.struct.generator.SourceSpec.Configurations;
import functionalj.annotations.struct.generator.model.GenStruct;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentChildTest {
    
    private Configurations configures = new Configurations();
    {
        configures.coupleWithDefinition      = true;
        configures.generateNoArgConstructor  = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass         = true;
        configures.toStringTemplate          = "";
    }
    
    private String  definitionClassName = "Definitions.ParentDef";
    private String  targetClassName     = "Parent";
    private String  packageName         = "me.test";
    private boolean isClass             = false;
    
    private List<Getter> getters = asList(
            new Getter("child", new Type.TypeBuilder()
                                .simpleName("Child")
                                .packageName("me.test")
                                .build())
    );
    
    @Test
    public void testParent() {
        val code = generate();
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import functionalj.annotations.IPostConstruct;\n" + 
                "import functionalj.annotations.IStruct;\n" + 
                "import functionalj.annotations.struct.generator.Getter;\n" + 
                "import functionalj.annotations.struct.generator.Type;\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef,IStruct {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final Child child;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null);\n" + 
                "    }\n" + 
                "    public Parent(Child child) {\n" + 
                "        this.child = $utils.notNull(child);\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Child child() {\n" + 
                "        return child;\n" + 
                "    }\n" + 
                "    public Parent withChild(Child child) {\n" + 
                "        return new Parent(child);\n" + 
                "    }\n" + 
                "    public Parent withChild(Supplier<Child> child) {\n" + 
                "        return new Parent(child.get());\n" + 
                "    }\n" + 
                "    public Parent withChild(Function<Child, Child> child) {\n" + 
                "        return new Parent(child.apply(this.child));\n" + 
                "    }\n" + 
                "    public Parent withChild(BiFunction<Parent, Child, Child> child) {\n" + 
                "        return new Parent(child.apply(this, this.child));\n" + 
                "    }\n" + 
                "    public static Parent fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        \n" + 
                "        Parent obj = new Parent(\n" + 
                "                    (Child)IStruct.fromMapValue(map.get(\"child\"), $schema.get(\"child\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"child\", IStruct.toMapValueObject(child));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"child\", new functionalj.annotations.struct.generator.Getter(\"child\", new Type(null, \"Child\", \"me.test\", java.util.Collections.emptyList()), false, functionalj.annotations.DefaultValue.REQUIRED));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Parent[\" + \"child: \" + child() + \"]\";\n" + 
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
                "        public final ChildLens<HOST> child = createSubLens(Parent::child, Parent::withChild, ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_child child(Child child) {\n" + 
                "            return new Builder_child(child);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_child {\n" + 
                "            \n" + 
                "            private final Child child;\n" + 
                "            \n" + 
                "            private Builder_child(Child child) {\n" + 
                "                this.child = $utils.notNull(child);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public Child child() {\n" + 
                "                return child;\n" + 
                "            }\n" + 
                "            public Builder_child child(Child child) {\n" + 
                "                return new Builder_child(child);\n" + 
                "            }\n" + 
                "            public Parent build() {\n" + 
                "                return new Parent(child());\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}", code);
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
                    null,                // encloseName
                    targetClassName,     // targetClassName
                    packageName,         // targetPackageName
                    isClass,             // isClass
                    null,
                    null,
                    configures,          // Configurations
                    getters,
                    asList("Child"));
        val dataObjSpec = new StructBuilder(sourceSpec).build();
        val generated   = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
    
}
