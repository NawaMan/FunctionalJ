package functionalj.annotations.struct.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.struct.generator.SourceSpec.Configurations;
import functionalj.annotations.struct.generator.model.GenStruct;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentOptionalChildTest {
    
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
                "import functionalj.annotations.IPostConstruct;\n" + 
                "import functionalj.annotations.IStruct;\n" + 
                "import functionalj.annotations.struct.generator.Getter;\n" + 
                "import functionalj.annotations.struct.generator.Type;\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.OptionalLens;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.Optional;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "\n" + 
                "// me.test.null.Definitions.ParentDef\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef,IStruct {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final Optional<String> optionalName;\n" + 
                "    private final Optional<Child> optionalChild;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(java.util.Optional.empty(), java.util.Optional.empty());\n" + 
                "    }\n" + 
                "    public Parent(Optional<String> optionalName, Optional<Child> optionalChild) {\n" + 
                "        this.optionalName = $utils.notNull(optionalName);\n" + 
                "        this.optionalChild = $utils.notNull(optionalChild);\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Optional<String> optionalName() {\n" + 
                "        return optionalName;\n" + 
                "    }\n" + 
                "    public Optional<Child> optionalChild() {\n" + 
                "        return optionalChild;\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(Optional<String> optionalName) {\n" + 
                "        return new Parent(optionalName, optionalChild);\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(Supplier<Optional<String>> optionalName) {\n" + 
                "        return new Parent(optionalName.get(), optionalChild);\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(Function<Optional<String>, Optional<String>> optionalName) {\n" + 
                "        return new Parent(optionalName.apply(this.optionalName), optionalChild);\n" + 
                "    }\n" + 
                "    public Parent withOptionalName(BiFunction<Parent, Optional<String>, Optional<String>> optionalName) {\n" + 
                "        return new Parent(optionalName.apply(this, this.optionalName), optionalChild);\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(Optional<Child> optionalChild) {\n" + 
                "        return new Parent(optionalName, optionalChild);\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(Supplier<Optional<Child>> optionalChild) {\n" + 
                "        return new Parent(optionalName, optionalChild.get());\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(Function<Optional<Child>, Optional<Child>> optionalChild) {\n" + 
                "        return new Parent(optionalName, optionalChild.apply(this.optionalChild));\n" + 
                "    }\n" + 
                "    public Parent withOptionalChild(BiFunction<Parent, Optional<Child>, Optional<Child>> optionalChild) {\n" + 
                "        return new Parent(optionalName, optionalChild.apply(this, this.optionalChild));\n" + 
                "    }\n" + 
                "    public static Parent fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Parent obj = new Parent(\n" + 
                "                    (Optional<String>)IStruct.fromMapValue(map.get(\"optionalName\"), $schema.get(\"optionalName\")),\n" + 
                "                    (Optional<Child>)IStruct.fromMapValue(map.get(\"optionalChild\"), $schema.get(\"optionalChild\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"optionalName\", IStruct.toMapValueObject(optionalName));\n" + 
                "        map.put(\"optionalChild\", IStruct.toMapValueObject(optionalChild));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"optionalName\", new functionalj.annotations.struct.generator.Getter(\"optionalName\", new Type(null, \"Optional\", \"java.util\", java.util.Arrays.asList(new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()))), false, functionalj.annotations.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"optionalChild\", new functionalj.annotations.struct.generator.Getter(\"optionalChild\", new Type(null, \"Optional\", \"java.util\", java.util.Arrays.asList(new Type(null, \"Child\", \"me.test\", java.util.Collections.emptyList()))), false, functionalj.annotations.DefaultValue.REQUIRED));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Parent[\" + \"optionalName: \" + optionalName() + \", \" + \"optionalChild: \" + optionalChild() + \"]\";\n" + 
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
                "        public final OptionalLens<HOST, String, StringLens<HOST>> optionalName = createSubOptionalLens(Parent::optionalName, Parent::withOptionalName, StringLens::of);\n" + 
                "        public final OptionalLens<HOST, Child, ChildLens<HOST>> optionalChild = createSubOptionalLens(Parent::optionalChild, Parent::withOptionalChild, ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_optionalName optionalName(Optional<String> optionalName) {\n" + 
                "            return new Builder_optionalName(optionalName);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_optionalName {\n" + 
                "            \n" + 
                "            private final Optional<String> optionalName;\n" + 
                "            \n" + 
                "            private Builder_optionalName(Optional<String> optionalName) {\n" + 
                "                this.optionalName = $utils.notNull(optionalName);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public Optional<String> optionalName() {\n" + 
                "                return optionalName;\n" + 
                "            }\n" + 
                "            public Builder_optionalName optionalName(Optional<String> optionalName) {\n" + 
                "                return new Builder_optionalName(optionalName);\n" + 
                "            }\n" + 
                "            public Builder_optionalName_optionalChild optionalChild(Optional<Child> optionalChild) {\n" + 
                "                return new Builder_optionalName_optionalChild(this, optionalChild);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_optionalName_optionalChild {\n" + 
                "            \n" + 
                "            private final Builder_optionalName parent;\n" + 
                "            private final Optional<Child> optionalChild;\n" + 
                "            \n" + 
                "            private Builder_optionalName_optionalChild(Builder_optionalName parent, Optional<Child> optionalChild) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.optionalChild = $utils.notNull(optionalChild);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public Optional<String> optionalName() {\n" + 
                "                return parent.optionalName();\n" + 
                "            }\n" + 
                "            public Optional<Child> optionalChild() {\n" + 
                "                return optionalChild;\n" + 
                "            }\n" + 
                "            public Builder_optionalName_optionalChild optionalName(Optional<String> optionalName) {\n" + 
                "                return parent.optionalName(optionalName).optionalChild(optionalChild);\n" + 
                "            }\n" + 
                "            public Builder_optionalName_optionalChild optionalChild(Optional<Child> optionalChild) {\n" + 
                "                return parent.optionalChild(optionalChild);\n" + 
                "            }\n" + 
                "            public Parent build() {\n" + 
                "                return new Parent(optionalName(), optionalChild());\n" + 
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
                    null,                // encloseName
                    targetClassName,     // targetClassName
                    packageName,         // targetPackag eName
                    isClass,             // isClass
                    null,
                    null,                // Validate
                    configures,          // Configurations
                    getters,
                    asList("Child"));
        val dataObjSpec = new StructBuilder(sourceSpec).build();
        val generated   = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
    
}
