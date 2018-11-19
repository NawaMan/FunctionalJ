package functionalj.annotations.record.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.record.generator.Getter;
import functionalj.annotations.record.generator.RecordBuilder;
import functionalj.annotations.record.generator.SourceSpec;
import functionalj.annotations.record.generator.Type;
import functionalj.annotations.record.generator.SourceSpec.Configurations;
import functionalj.annotations.record.generator.model.GenRecord;
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
                "public class Parent implements Definitions.ParentDef {\n" + 
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
                "    public static Parent fromMap(Map<String, Object> map) {\n" + 
                "        return new Parent(\n" + 
                "                    (Nullable<String>)map.get(\"nullableName\"),\n" + 
                "                    (Nullable<Child>)map.get(\"nullableChild\")\n" + 
                "                );\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        java.util.Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"nullableName\",  (Object)nullableName);\n" + 
                "        map.put(\"nullableChild\",  (Object)nullableChild);\n" + 
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
                "                this.nullableName = nullableName;\n" + 
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
                "                this.nullableChild = nullableChild;\n" + 
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
                    targetClassName,     // targetClassName
                    packageName,         // targetPackageName
                    isClass,             // isClass
                    null,
                    configures,          // Configurations
                    getters);
        val dataObjSpec = new RecordBuilder(sourceSpec).build();
        val generated   = new GenRecord(dataObjSpec).toText();
        return generated;
    }
    
}
