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

import java.util.List;

import org.junit.Test;

import functionalj.types.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

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
        assertAsString(
                "package me.test;\n"
                + "\n"
                + "import functionalj.lens.core.LensSpec;\n"
                + "import functionalj.lens.lenses.ObjectLensImpl;\n"
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
                + "\n"
                + "@Generated(value = \"FunctionalJ\",date = \"\\E[^\"$]+\\Q\", comments = \"me.test.null.Definitions.ParentDef\")\n"
                + "\n"
                + "@SuppressWarnings(\"all\")\n"
                + "\n"
                + "public class Parent implements Definitions.ParentDef,IStruct,Pipeable<Parent> {\n"
                + "    \n"
                + "    public static final Parent.ParentLens<Parent> theParent = new Parent.ParentLens<>(LensSpec.of(Parent.class));\n"
                + "    public static final Parent.ParentLens<Parent> eachParent = theParent;\n"
                + "    public final Child child;\n"
                + "    \n"
                + "    public Parent() {\n"
                + "        this(null);\n"
                + "    }\n"
                + "    public Parent(Child child) {\n"
                + "        this.child = $utils.notNull(child);\n"
                + "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n"
                + "    }\n"
                + "    \n"
                + "    public Parent __data() throws Exception  {\n"
                + "        return this;\n"
                + "    }\n"
                + "    public Child child() {\n"
                + "        return child;\n"
                + "    }\n"
                + "    public Parent withChild(Child child) {\n"
                + "        return new Parent(child);\n"
                + "    }\n"
                + "    public Parent withChild(Supplier<Child> child) {\n"
                + "        return new Parent(child.get());\n"
                + "    }\n"
                + "    public Parent withChild(Function<Child, Child> child) {\n"
                + "        return new Parent(child.apply(this.child));\n"
                + "    }\n"
                + "    public Parent withChild(BiFunction<Parent, Child, Child> child) {\n"
                + "        return new Parent(child.apply(this, this.child));\n"
                + "    }\n"
                + "    public static Parent fromMap(Map<String, ? extends Object> map) {\n"
                + "        Map<String, Getter> $schema = getStructSchema();\n"
                + "        Parent obj = new Parent(\n"
                + "                    (Child)$utils.extractPropertyFromMap(Parent.class, Child.class, map, $schema, \"child\")\n"
                + "                );\n"
                + "        return obj;\n"
                + "    }\n"
                + "    public Map<String, Object> __toMap() {\n"
                + "        Map<String, Object> map = new HashMap<>();\n"
                + "        map.put(\"child\", functionalj.types.IStruct.$utils.toMapValueObject(child));\n"
                + "        return map;\n"
                + "    }\n"
                + "    public Map<String, Getter> __getSchema() {\n"
                + "        return getStructSchema();\n"
                + "    }\n"
                + "    public static Map<String, Getter> getStructSchema() {\n"
                + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
                + "        map.put(\"child\", new functionalj.types.struct.generator.Getter(\"child\", new functionalj.types.Type(\"me.test\", null, \"Child\", null), false, functionalj.types.DefaultValue.REQUIRED));\n"
                + "        return map;\n"
                + "    }\n"
                + "    public String toString() {\n"
                + "        return \"Parent[\" + \"child: \" + child() + \"]\";\n"
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
                + "        public final Child.ChildLens<HOST> child = createSubLens(Parent::child, Parent::withChild, Child.ChildLens::new);\n"
                + "        \n"
                + "        public ParentLens(LensSpec<HOST, Parent> spec) {\n"
                + "            super(spec);\n"
                + "        }\n"
                + "        \n"
                + "    }\n"
                + "    public static final class Builder {\n"
                + "        \n"
                + "        public final ParentBuilder_ready child(Child child) {\n"
                + "            return ()->{\n"
                + "                return new Parent(\n"
                + "                    child\n"
                + "                );\n"
                + "            };\n"
                + "        }\n"
                + "        \n"
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
