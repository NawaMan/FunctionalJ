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

import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.StructBuilder;
import functionalj.types.struct.generator.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentMapChildTest {
    
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
            new Getter("children", new Type.TypeBuilder()
                                .simpleName("Map")
                                .generics(asList(
                                        new Type("String", "java.lang"), 
                                        new Type("Child",  "me.test")))
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
                "import functionalj.lens.lenses.MapLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import functionalj.map.ImmutableMap;\n" + 
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
                "import me.test.Parent.ParentLens;\n" + 
                "\n" + 
                "// me.test.null.Definitions.ParentDef\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef,IStruct,Pipeable<Parent> {\n" + 
                "    \n" + 
                "    public static final Parent.ParentLens<Parent> theParent = new Parent.ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    public final Map<String, Child> children;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null);\n" + 
                "    }\n" + 
                "    public Parent(Map<String, Child> children) {\n" + 
                "        this.children = ImmutableMap.from(children);\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Parent __data() throws Exception  {\n" + 
                "        return this;\n" + 
                "    }\n" + 
                "    public Map<String, Child> children() {\n" + 
                "        return children;\n" + 
                "    }\n" + 
                "    public Parent withChildren(Map<String, Child> children) {\n" + 
                "        return new Parent(children);\n" + 
                "    }\n" + 
                "    public Parent withChildren(Supplier<Map<String, Child>> children) {\n" + 
                "        return new Parent(children.get());\n" + 
                "    }\n" + 
                "    public Parent withChildren(Function<Map<String, Child>, Map<String, Child>> children) {\n" + 
                "        return new Parent(children.apply(this.children));\n" + 
                "    }\n" + 
                "    public Parent withChildren(BiFunction<Parent, Map<String, Child>, Map<String, Child>> children) {\n" + 
                "        return new Parent(children.apply(this, this.children));\n" + 
                "    }\n" + 
                "    public static Parent fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Parent obj = new Parent(\n" + 
                "                    (Map<String, Child>)IStruct.fromMapValue(map.get(\"children\"), $schema.get(\"children\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"children\", IStruct.toMapValueObject(children));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"children\", new functionalj.types.struct.generator.Getter(\"children\", new Type(null, \"Map\", \"java.util\", java.util.Arrays.asList(new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), new Type(null, \"Child\", \"me.test\", java.util.Collections.emptyList()))), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Parent[\" + \"children: \" + children() + \"]\";\n" + 
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
                "        public final MapLens<HOST, String, Child, StringLens<HOST>, Child.ChildLens<HOST>> children = createSubMapLens(Parent::children, Parent::withChildren, StringLens::of, Child.ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static final class Builder {\n" + 
                "        \n" + 
                "        public final ParentBuilder_ready children(Map<String, Child> children) {\n" + 
                "            return ()->{\n" + 
                "                return new Parent(\n" + 
                "                    children\n" + 
                "                );\n" + 
                "            };\n" + 
                "        }\n" + 
                "        \n" + 
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
