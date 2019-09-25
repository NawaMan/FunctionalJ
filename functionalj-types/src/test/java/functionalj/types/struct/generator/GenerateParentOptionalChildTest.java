// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
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
                                .generics(asList(new Generic(new Type("java.lang", "String"))))
                                .packageName("java.util")
                                .build()),
            new Getter("optionalChild", new Type.TypeBuilder()
                                .simpleName("Optional")
                                .generics(asList(new Generic(new Type("me.test", "Child"))))
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
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.OptionalLens;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import functionalj.pipeable.Pipeable;\n" + 
                "import functionalj.types.IPostConstruct;\n" + 
                "import functionalj.types.IStruct;\n" + 
                "import functionalj.types.Type;\n" + 
                "import functionalj.types.struct.generator.Getter;\n" + 
                "import java.lang.Exception;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.Optional;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "import me.test.Parent.ParentLens;\n" + 
                "\n" + 
                "// me.test.null.Definitions.ParentDef\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef,IStruct,Pipeable<Parent> {\n" + 
                "    \n" + 
                "    public static final Parent.ParentLens<Parent> theParent = new Parent.ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    public static final Parent.ParentLens<Parent> eachParent = theParent;\n" + 
                "    public final Optional<String> optionalName;\n" + 
                "    public final Optional<Child> optionalChild;\n" + 
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
                "    public Parent __data() throws Exception  {\n" + 
                "        return this;\n" + 
                "    }\n" + 
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
                "                    (Optional<String>)$utils.fromMapValue(map.get(\"optionalName\"), $schema.get(\"optionalName\")),\n" + 
                "                    (Optional<Child>)$utils.fromMapValue(map.get(\"optionalChild\"), $schema.get(\"optionalChild\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> __toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"optionalName\", functionalj.types.IStruct.$utils.toMapValueObject(optionalName));\n" + 
                "        map.put(\"optionalChild\", functionalj.types.IStruct.$utils.toMapValueObject(optionalChild));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> __getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"optionalName\", new functionalj.types.struct.generator.Getter(\"optionalName\", new functionalj.types.Type(\"java.util\", null, \"Optional\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.lang.String\", \"java.lang.String\", java.util.Arrays.asList(new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"optionalChild\", new functionalj.types.struct.generator.Getter(\"optionalChild\", new functionalj.types.Type(\"java.util\", null, \"Optional\", java.util.Arrays.asList(new functionalj.types.Generic(\"me.test.Child\", \"me.test.Child\", java.util.Arrays.asList(new functionalj.types.Type(\"me.test\", null, \"Child\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
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
                "        public final OptionalLens<HOST, Child, Child.ChildLens<HOST>> optionalChild = createSubOptionalLens(Parent::optionalChild, Parent::withOptionalChild, Child.ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static final class Builder {\n" + 
                "        \n" + 
                "        public final ParentBuilder_withoutOptionalChild optionalName(Optional<String> optionalName) {\n" + 
                "            return (Optional<Child> optionalChild)->{\n" + 
                "            return ()->{\n" + 
                "                return new Parent(\n" + 
                "                    optionalName,\n" + 
                "                    optionalChild\n" + 
                "                );\n" + 
                "            };\n" + 
                "            };\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static interface ParentBuilder_withoutOptionalChild {\n" + 
                "            \n" + 
                "            public ParentBuilder_ready optionalChild(Optional<Child> optionalChild);\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface ParentBuilder_ready {\n" + 
                "            \n" + 
                "            public Parent build();\n" + 
                "            \n" + 
                "            \n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
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
