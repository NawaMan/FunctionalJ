// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types.struct.generator;

import static functionalj.types.TestHelper.assertAsString;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.List;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

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
                                .generics(asList(new Generic(new Type("java.lang", "String"))))
                                .packageName("nullablej.nullable")
                                .build()),
            new Getter("nullableChild", new Type.TypeBuilder()
                                .simpleName("Nullable")
                                .generics(asList(new Generic(new Type("me.test", "Child"))))
                                .packageName("nullablej.nullable")
                                .build())
    );
    
    @Test
    public void testParent() {
        val code = generate();
        /* */
        assertAsString(
                "package me.test;\n"
                + "\n"
                + "import functionalj.lens.core.LensSpec;\n"
                + "import functionalj.lens.lenses.NullableLens;\n"
                + "import functionalj.lens.lenses.ObjectLensImpl;\n"
                + "import functionalj.lens.lenses.StringLens;\n"
                + "import functionalj.pipeable.Pipeable;\n"
                + "import functionalj.types.Generated;\n"
                + "import functionalj.types.IPostConstruct;\n"
                + "import functionalj.types.IStruct;\n"
                + "import functionalj.types.struct.generator.Getter;\n"
                + "import java.lang.Exception;\n"
                + "import java.lang.Object;\n"
                + "import java.util.HashMap;\n"
                + "import java.util.Map;\n"
                + "import java.util.function.BiFunction;\n"
                + "import java.util.function.Function;\n"
                + "import java.util.function.Supplier;\n"
                + "import nullablej.nullable.Nullable;\n"
                + "\n"
                + "@Generated(value = \"FunctionalJ\",date = \"\\E[^\"]+\\Q\", comments = \"me.test.null.Definitions.ParentDef\")\n"
                + "\n"
                + "@SuppressWarnings(\"all\")\n"
                + "\n"
                + "public class Parent implements Definitions.ParentDef,IStruct,Pipeable<Parent> {\n"
                + "    \n"
                + "    public static final Parent.ParentLens<Parent> theParent = new Parent.ParentLens<>(\"theParent\", LensSpec.of(Parent.class));\n"
                + "    public static final Parent.ParentLens<Parent> eachParent = theParent;\n"
                + "    public final Nullable<String> nullableName;\n"
                + "    public final Nullable<Child> nullableChild;\n"
                + "    \n"
                + "    public Parent() {\n"
                + "        this(nullablej.nullable.Nullable.empty(), nullablej.nullable.Nullable.empty());\n"
                + "    }\n"
                + "    public Parent(Nullable<String> nullableName, Nullable<Child> nullableChild) {\n"
                + "        this.nullableName = Nullable.of((nullableName == null) ? null : nullableName.get());\n"
                + "        this.nullableChild = Nullable.of((nullableChild == null) ? null : nullableChild.get());\n"
                + "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n"
                + "    }\n"
                + "    \n"
                + "    public Parent __data() throws Exception  {\n"
                + "        return this;\n"
                + "    }\n"
                + "    public Nullable<String> nullableName() {\n"
                + "        return nullableName;\n"
                + "    }\n"
                + "    public Nullable<Child> nullableChild() {\n"
                + "        return nullableChild;\n"
                + "    }\n"
                + "    public Parent withNullableName(Nullable<String> nullableName) {\n"
                + "        return new Parent(nullableName, nullableChild);\n"
                + "    }\n"
                + "    public Parent withNullableName(Supplier<Nullable<String>> nullableName) {\n"
                + "        return new Parent(nullableName.get(), nullableChild);\n"
                + "    }\n"
                + "    public Parent withNullableName(Function<Nullable<String>, Nullable<String>> nullableName) {\n"
                + "        return new Parent(nullableName.apply(this.nullableName), nullableChild);\n"
                + "    }\n"
                + "    public Parent withNullableName(BiFunction<Parent, Nullable<String>, Nullable<String>> nullableName) {\n"
                + "        return new Parent(nullableName.apply(this, this.nullableName), nullableChild);\n"
                + "    }\n"
                + "    public Parent withNullableChild(Nullable<Child> nullableChild) {\n"
                + "        return new Parent(nullableName, nullableChild);\n"
                + "    }\n"
                + "    public Parent withNullableChild(Supplier<Nullable<Child>> nullableChild) {\n"
                + "        return new Parent(nullableName, nullableChild.get());\n"
                + "    }\n"
                + "    public Parent withNullableChild(Function<Nullable<Child>, Nullable<Child>> nullableChild) {\n"
                + "        return new Parent(nullableName, nullableChild.apply(this.nullableChild));\n"
                + "    }\n"
                + "    public Parent withNullableChild(BiFunction<Parent, Nullable<Child>, Nullable<Child>> nullableChild) {\n"
                + "        return new Parent(nullableName, nullableChild.apply(this, this.nullableChild));\n"
                + "    }\n"
                + "    public static Parent fromMap(Map<String, ? extends Object> map) {\n"
                + "        Map<String, Getter> $schema = getStructSchema();\n"
                + "        Parent obj = new Parent(\n"
                + "                    (Nullable<String>)$utils.extractPropertyFromMap(Parent.class, Nullable.class, map, $schema, \"nullableName\"),\n"
                + "                    (Nullable<Child>)$utils.extractPropertyFromMap(Parent.class, Nullable.class, map, $schema, \"nullableChild\")\n"
                + "                );\n"
                + "        return obj;\n"
                + "    }\n"
                + "    public Map<String, Object> __toMap() {\n"
                + "        Map<String, Object> map = new HashMap<>();\n"
                + "        map.put(\"nullableName\", $utils.toMapValueObject(nullableName));\n"
                + "        map.put(\"nullableChild\", $utils.toMapValueObject(nullableChild));\n"
                + "        return map;\n"
                + "    }\n"
                + "    public Map<String, Getter> __getSchema() {\n"
                + "        return getStructSchema();\n"
                + "    }\n"
                + "    public static Map<String, Getter> getStructSchema() {\n"
                + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
                + "        map.put(\"nullableName\", new functionalj.types.struct.generator.Getter(\"nullableName\", new functionalj.types.Type(\"nullablej.nullable\", null, \"Nullable\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.lang.String\", null, java.util.Arrays.asList(new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n"
                + "        map.put(\"nullableChild\", new functionalj.types.struct.generator.Getter(\"nullableChild\", new functionalj.types.Type(\"nullablej.nullable\", null, \"Nullable\", java.util.Arrays.asList(new functionalj.types.Generic(\"me.test.Child\", null, java.util.Arrays.asList(new functionalj.types.Type(\"me.test\", null, \"Child\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n"
                + "        return map;\n"
                + "    }\n"
                + "    public String toString() {\n"
                + "        return \"Parent[\" + \"nullableName: \" + nullableName() + \", \" + \"nullableChild: \" + nullableChild() + \"]\";\n"
                + "    }\n"
                + "    public int hashCode() {\n"
                + "        return toString().hashCode();\n"
                + "    }\n"
                + "    public boolean equals(Object another) {\n"
                + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n"
                + "    }\n"
                + "    \n"
                + "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n"
                + "        \n"
                + "        public final NullableLens<HOST, String, StringLens<HOST>> nullableName = createSubNullableLens(\"nullableName\", Parent::nullableName, Parent::withNullableName, StringLens::of);\n"
                + "        public final NullableLens<HOST, Child, Child.ChildLens<HOST>> nullableChild = createSubNullableLens(\"nullableChild\", Parent::nullableChild, Parent::withNullableChild, Child.ChildLens::new);\n"
                + "        \n"
                + "        public ParentLens(String name, LensSpec<HOST, Parent> spec) {\n"
                + "            super(name, spec);\n"
                + "        }\n"
                + "        \n"
                + "    }\n"
                + "    public static final class Builder {\n"
                + "        \n"
                + "        public final ParentBuilder_withoutNullableChild nullableName(Nullable<String> nullableName) {\n"
                + "            return (Nullable<Child> nullableChild)->{\n"
                + "            return ()->{\n"
                + "                return new Parent(\n"
                + "                    nullableName,\n"
                + "                    nullableChild\n"
                + "                );\n"
                + "            };\n"
                + "            };\n"
                + "        }\n"
                + "        \n"
                + "        public static interface ParentBuilder_withoutNullableChild {\n"
                + "            \n"
                + "            public ParentBuilder_ready nullableChild(Nullable<Child> nullableChild);\n"
                + "            \n"
                + "        }\n"
                + "        public static interface ParentBuilder_ready {\n"
                + "            \n"
                + "            public Parent build();\n"
                + "            \n"
                + "            \n"
                + "            \n"
                + "        }\n"
                + "        \n"
                + "        \n"
                + "    }\n"
                + "    \n"
                + "}", code);
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
                    emptyList(),
                    asList("Child"));
        val dataObjSpec = new StructSpecBuilder(sourceSpec).build();
        val generated   = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
    
}
