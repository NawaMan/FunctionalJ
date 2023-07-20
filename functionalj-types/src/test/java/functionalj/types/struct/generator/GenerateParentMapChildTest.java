// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

public class GenerateParentMapChildTest {
    
    private Configurations configures = new Configurations();
    
    {
        configures.coupleWithDefinition = true;
        configures.generateNoArgConstructor = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass = true;
    }
    
    private String definitionClassName = "Definitions.ParentDef";
    
    private String targetClassName = "Parent";
    
    private String packageName = "me.test";
    
    private boolean isClass = false;
    
    private List<Getter> getters = asList(new Getter("children", new Type.TypeBuilder().simpleName("Map").generics(asList(new Generic(new Type("java.lang", "String")), new Generic(new Type("me.test", "Child")))).packageName("java.util").build()));
    
    @Test
    public void testParent() {
        val code = generate();
        /* */
        assertAsString("package me.test;\n" + "\n" + "import functionalj.lens.core.LensSpec;\n" + "import functionalj.lens.lenses.MapLens;\n" + "import functionalj.lens.lenses.ObjectLensImpl;\n" + "import functionalj.lens.lenses.StringLens;\n" + "import functionalj.pipeable.Pipeable;\n" + "import functionalj.types.Generated;\n" + "import functionalj.types.IPostConstruct;\n" + "import functionalj.types.IStruct;\n" + "import functionalj.types.struct.generator.Getter;\n" + "import java.lang.Exception;\n" + "import java.lang.Object;\n" + "import java.util.HashMap;\n" + "import java.util.Map;\n" + "import java.util.function.BiFunction;\n" + "import java.util.function.Function;\n" + "import java.util.function.Supplier;\n" + "\n" + "@Generated(value = \"FunctionalJ\",date = \"\\E[^\"]+\\Q\", comments = \"me.test.null.Definitions.ParentDef\")\n" + "\n" + "@SuppressWarnings(\"all\")\n" + "\n" + "public class Parent implements Definitions.ParentDef,IStruct,Pipeable<Parent> {\n" + "    \n" + "    public static final Parent.ParentLens<Parent> theParent = new Parent.ParentLens<>(\"theParent\", LensSpec.of(Parent.class));\n" + "    public static final Parent.ParentLens<Parent> eachParent = theParent;\n" + "    public final Map<String, Child> children;\n" + "    \n" + "    public Parent() {\n" + "        this(null);\n" + "    }\n" + "    public Parent(Map<String, Child> children) {\n" + "        this.children = functionalj.map.ImmutableFuncMap.from(children);\n" + "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + "    }\n" + "    \n" + "    public Parent __data() throws Exception  {\n" + "        return this;\n" + "    }\n" + "    public Map<String, Child> children() {\n" + "        return children;\n" + "    }\n" + "    public Parent withChildren(Map<String, Child> children) {\n" + "        return new Parent(children);\n" + "    }\n" + "    public Parent withChildren(Supplier<Map<String, Child>> children) {\n" + "        return new Parent(children.get());\n" + "    }\n" + "    public Parent withChildren(Function<Map<String, Child>, Map<String, Child>> children) {\n" + "        return new Parent(children.apply(this.children));\n" + "    }\n" + "    public Parent withChildren(BiFunction<Parent, Map<String, Child>, Map<String, Child>> children) {\n" + "        return new Parent(children.apply(this, this.children));\n" + "    }\n" + "    public static Parent fromMap(Map<String, ? extends Object> map) {\n" + "        Map<String, Getter> $schema = getStructSchema();\n" + "        Parent obj = new Parent(\n" + "                    (Map<String, Child>)$utils.extractPropertyFromMap(Parent.class, Map.class, map, $schema, \"children\")\n" + "                );\n" + "        return obj;\n" + "    }\n" + "    public Map<String, Object> __toMap() {\n" + "        Map<String, Object> map = new HashMap<>();\n" + "        map.put(\"children\", $utils.toMapValueObject(children));\n" + "        return map;\n" + "    }\n" + "    public Map<String, Getter> __getSchema() {\n" + "        return getStructSchema();\n" + "    }\n" + "    public static Map<String, Getter> getStructSchema() {\n" + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n" + "        map.put(\"children\", new functionalj.types.struct.generator.Getter(\"children\", new functionalj.types.Type(\"java.util\", null, \"Map\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.lang.String\", null, java.util.Arrays.asList(new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()))), new functionalj.types.Generic(\"me.test.Child\", null, java.util.Arrays.asList(new functionalj.types.Type(\"me.test\", null, \"Child\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n" + "        return map;\n" + "    }\n" + "    public String toString() {\n" + "        return \"Parent[\" + \"children: \" + children() + \"]\";\n" + "    }\n" + "    public int hashCode() {\n" + "        return toString().hashCode();\n" + "    }\n" + "    public boolean equals(Object another) {\n" + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n" + "    }\n" + "    \n" + "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + "        \n" + "        public final MapLens<HOST, String, Child, StringLens<HOST>, Child.ChildLens<HOST>> children = createSubMapLens(\"children\", Parent::children, Parent::withChildren, StringLens::of, Child.ChildLens::new);\n" + "        \n" + "        public ParentLens(String name, LensSpec<HOST, Parent> spec) {\n" + "            super(name, spec);\n" + "        }\n" + "        \n" + "    }\n" + "    public static final class Builder {\n" + "        \n" + "        public final ParentBuilder_ready children(Map<String, Child> children) {\n" + "            return ()->{\n" + "                return new Parent(\n" + "                    children\n" + "                );\n" + "            };\n" + "        }\n" + "        \n" + "        public static interface ParentBuilder_ready {\n" + "            \n" + "            public Parent build();\n" + "            \n" + "            \n" + "            \n" + "        }\n" + "        \n" + "        \n" + "    }\n" + "    \n" + "}", code);
        /* */
    }
    
    private String generate() {
        return generate(null);
    }
    
    private String generate(Runnable setting) {
        if (setting != null)
            setting.run();
        SourceSpec sourceSpec = new SourceSpec(// specClassName
        definitionClassName, // packageName
        packageName, // encloseName
        null, // targetClassName
        targetClassName, // targetPackageName
        packageName, // isClass
        isClass, null, null, // Configurations
        configures, getters, emptyList(), asList("Child"));
        val dataObjSpec = new StructSpecBuilder(sourceSpec).build();
        val generated = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
}
