package functionalj.types.struct.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.StructBuilder;
import functionalj.types.struct.generator.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
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
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.NullableLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import functionalj.pipeable.Pipeable;\n" + 
                "import functionalj.types.IPostConstruct;\n" + 
                "import functionalj.types.IStruct;\n" + 
                "import functionalj.types.struct.generator.Getter;\n" + 
                "import functionalj.types.struct.generator.Type;\n" + 
                "import java.lang.Exception;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "import nawaman.nullablej.nullable.Nullable;\n" + 
                "\n" + 
                "// me.test.null.Definitions.ParentDef\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef,IStruct,Pipeable<Parent> {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final Nullable<String> nullableName;\n" + 
                "    private final Nullable<Child> nullableChild;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(nawaman.nullablej.nullable.Nullable.empty(), nawaman.nullablej.nullable.Nullable.empty());\n" + 
                "    }\n" + 
                "    public Parent(Nullable<String> nullableName, Nullable<Child> nullableChild) {\n" + 
                "        this.nullableName = Nullable.of((nullableName == null) ? null : nullableName.get());\n" + 
                "        this.nullableChild = Nullable.of((nullableChild == null) ? null : nullableChild.get());\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Parent __data()  throws Exception  {\n" + 
                "        return this;\n" + 
                "    }\n" + 
                "    public Nullable<String> nullableName() {\n" + 
                "        return nullableName;\n" + 
                "    }\n" + 
                "    public Nullable<Child> nullableChild() {\n" + 
                "        return nullableChild;\n" + 
                "    }\n" + 
                "    public Parent withNullableName(Nullable<String> nullableName) {\n" + 
                "        return new Parent(nullableName, nullableChild);\n" + 
                "    }\n" + 
                "    public Parent withNullableName(Supplier<Nullable<String>> nullableName) {\n" + 
                "        return new Parent(nullableName.get(), nullableChild);\n" + 
                "    }\n" + 
                "    public Parent withNullableName(Function<Nullable<String>, Nullable<String>> nullableName) {\n" + 
                "        return new Parent(nullableName.apply(this.nullableName), nullableChild);\n" + 
                "    }\n" + 
                "    public Parent withNullableName(BiFunction<Parent, Nullable<String>, Nullable<String>> nullableName) {\n" + 
                "        return new Parent(nullableName.apply(this, this.nullableName), nullableChild);\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(Nullable<Child> nullableChild) {\n" + 
                "        return new Parent(nullableName, nullableChild);\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(Supplier<Nullable<Child>> nullableChild) {\n" + 
                "        return new Parent(nullableName, nullableChild.get());\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(Function<Nullable<Child>, Nullable<Child>> nullableChild) {\n" + 
                "        return new Parent(nullableName, nullableChild.apply(this.nullableChild));\n" + 
                "    }\n" + 
                "    public Parent withNullableChild(BiFunction<Parent, Nullable<Child>, Nullable<Child>> nullableChild) {\n" + 
                "        return new Parent(nullableName, nullableChild.apply(this, this.nullableChild));\n" + 
                "    }\n" + 
                "    public static Parent fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Parent obj = new Parent(\n" + 
                "                    (Nullable<String>)IStruct.fromMapValue(map.get(\"nullableName\"), $schema.get(\"nullableName\")),\n" + 
                "                    (Nullable<Child>)IStruct.fromMapValue(map.get(\"nullableChild\"), $schema.get(\"nullableChild\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"nullableName\", IStruct.toMapValueObject(nullableName));\n" + 
                "        map.put(\"nullableChild\", IStruct.toMapValueObject(nullableChild));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"nullableName\", new functionalj.types.struct.generator.Getter(\"nullableName\", new Type(null, \"Nullable\", \"nawaman.nullablej.nullable\", java.util.Arrays.asList(new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()))), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"nullableChild\", new functionalj.types.struct.generator.Getter(\"nullableChild\", new Type(null, \"Nullable\", \"nawaman.nullablej.nullable\", java.util.Arrays.asList(new Type(null, \"Child\", \"me.test\", java.util.Collections.emptyList()))), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Parent[\" + \"nullableName: \" + nullableName() + \", \" + \"nullableChild: \" + nullableChild() + \"]\";\n" + 
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
                "        public final NullableLens<HOST, String, StringLens<HOST>> nullableName = createSubNullableLens(Parent::nullableName, Parent::withNullableName, StringLens::of);\n" + 
                "        public final NullableLens<HOST, Child, ChildLens<HOST>> nullableChild = createSubNullableLens(Parent::nullableChild, Parent::withNullableChild, ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_nullableName nullableName(Nullable<String> nullableName) {\n" + 
                "            return new Builder_nullableName(nullableName);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_nullableName {\n" + 
                "            \n" + 
                "            private final Nullable<String> nullableName;\n" + 
                "            \n" + 
                "            private Builder_nullableName(Nullable<String> nullableName) {\n" + 
                "                this.nullableName = Nullable.of((nullableName == null) ? null : nullableName.get());\n" + 
                "            }\n" + 
                "            \n" + 
                "            public Nullable<String> nullableName() {\n" + 
                "                return nullableName;\n" + 
                "            }\n" + 
                "            public Builder_nullableName nullableName(Nullable<String> nullableName) {\n" + 
                "                return new Builder_nullableName(nullableName);\n" + 
                "            }\n" + 
                "            public Builder_nullableName_nullableChild nullableChild(Nullable<Child> nullableChild) {\n" + 
                "                return new Builder_nullableName_nullableChild(this, nullableChild);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_nullableName_nullableChild {\n" + 
                "            \n" + 
                "            private final Builder_nullableName parent;\n" + 
                "            private final Nullable<Child> nullableChild;\n" + 
                "            \n" + 
                "            private Builder_nullableName_nullableChild(Builder_nullableName parent, Nullable<Child> nullableChild) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.nullableChild = Nullable.of((nullableChild == null) ? null : nullableChild.get());\n" + 
                "            }\n" + 
                "            \n" + 
                "            public Nullable<String> nullableName() {\n" + 
                "                return parent.nullableName();\n" + 
                "            }\n" + 
                "            public Nullable<Child> nullableChild() {\n" + 
                "                return nullableChild;\n" + 
                "            }\n" + 
                "            public Builder_nullableName_nullableChild nullableName(Nullable<String> nullableName) {\n" + 
                "                return parent.nullableName(nullableName).nullableChild(nullableChild);\n" + 
                "            }\n" + 
                "            public Builder_nullableName_nullableChild nullableChild(Nullable<Child> nullableChild) {\n" + 
                "                return parent.nullableChild(nullableChild);\n" + 
                "            }\n" + 
                "            public Parent build() {\n" + 
                "                return new Parent(nullableName(), nullableChild());\n" + 
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
