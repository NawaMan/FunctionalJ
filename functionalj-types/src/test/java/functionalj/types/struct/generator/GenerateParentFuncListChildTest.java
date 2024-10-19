// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.GenStruct;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public class GenerateParentFuncListChildTest {
    
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
    
    private List<Getter> getters = asList(new Getter("names", new Type.TypeBuilder().simpleName("FuncList").generics(asList(new Generic(new Type("java.lang", "String")))).packageName("functionalj.list").build()), new Getter("children", new Type.TypeBuilder().simpleName("FuncList").generics(asList(new Generic(new Type("me.test", "Child")))).packageName("functionalj.list").build()));
    
    private List<Callable> callables = asList(new Callable("name1", new Type("java.lang", "String"), // isVarArgs
    true, Accessibility.PUBLIC, Scope.INSTANCE, Modifiability.MODIFIABLE, Concrecity.CONCRETE, asList(new Parameter("param1", new Type("java.lang", "String")), new Parameter("param2", new Type(null, null, "java.lang.String[]", emptyList()))), emptyList(), emptyList()));
    
    @Test
    public void testParent() {
        val code = generate();
        /* */
        assertAsString(
                "package me.test;\n"
              + "\n"
              + "import functionalj.lens.core.LensSpec;\n"
              + "import functionalj.lens.lenses.FuncListLens;\n"
              + "import functionalj.lens.lenses.ObjectLensImpl;\n"
              + "import functionalj.lens.lenses.StringLens;\n"
              + "import functionalj.list.FuncList;\n"
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
              + "@Generated(value = \"FunctionalJ\",date = \"\\E[^\"]+\\Q\", comments = \"me.test.null.Definitions.ParentDef\")\n"
              + "\n"
              + "@SuppressWarnings(\"all\")\n"
              + "\n"
              + "public class Parent implements Definitions.ParentDef,IStruct,Pipeable<Parent> {\n"
              + "    \n"
              + "    public static final Parent.ParentLens<Parent> theParent = new Parent.ParentLens<>(\"theParent\", LensSpec.of(Parent.class));\n"
              + "    public static final Parent.ParentLens<Parent> eachParent = theParent;\n"
              + "    public final FuncList<String> names;\n"
              + "    public final FuncList<Child> children;\n"
              + "    \n"
              + "    public Parent() {\n"
              + "        this(functionalj.list.FuncList.empty(), functionalj.list.FuncList.empty());\n"
              + "    }\n"
              + "    public Parent(FuncList<String> names, FuncList<Child> children) {\n"
              + "        this.names = functionalj.list.ImmutableFuncList.from(names);\n"
              + "        this.children = functionalj.list.ImmutableFuncList.from(children);\n"
              + "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n"
              + "    }\n"
              + "    \n"
              + "    public Parent __data() throws Exception  {\n"
              + "        return this;\n"
              + "    }\n"
              + "    public FuncList<String> names() {\n"
              + "        return names;\n"
              + "    }\n"
              + "    public FuncList<Child> children() {\n"
              + "        return children;\n"
              + "    }\n"
              + "    public Parent withNames(String ... names) {\n"
              + "        return new Parent(functionalj.list.ImmutableFuncList.of(names), children);\n"
              + "    }\n"
              + "    public Parent withNames(FuncList<String> names) {\n"
              + "        return new Parent(names, children);\n"
              + "    }\n"
              + "    public Parent withNames(Supplier<FuncList<String>> names) {\n"
              + "        return new Parent(names.get(), children);\n"
              + "    }\n"
              + "    public Parent withNames(Function<FuncList<String>, FuncList<String>> names) {\n"
              + "        return new Parent(names.apply(this.names), children);\n"
              + "    }\n"
              + "    public Parent withNames(BiFunction<Parent, FuncList<String>, FuncList<String>> names) {\n"
              + "        return new Parent(names.apply(this, this.names), children);\n"
              + "    }\n"
              + "    public Parent withChildren(Child ... children) {\n"
              + "        return new Parent(names, functionalj.list.ImmutableFuncList.of(children));\n"
              + "    }\n"
              + "    public Parent withChildren(FuncList<Child> children) {\n"
              + "        return new Parent(names, children);\n"
              + "    }\n"
              + "    public Parent withChildren(Supplier<FuncList<Child>> children) {\n"
              + "        return new Parent(names, children.get());\n"
              + "    }\n"
              + "    public Parent withChildren(Function<FuncList<Child>, FuncList<Child>> children) {\n"
              + "        return new Parent(names, children.apply(this.children));\n"
              + "    }\n"
              + "    public Parent withChildren(BiFunction<Parent, FuncList<Child>, FuncList<Child>> children) {\n"
              + "        return new Parent(names, children.apply(this, this.children));\n"
              + "    }\n"
              + "    public static Parent fromMap(Map<String, ? extends Object> map) {\n"
              + "        Map<String, Getter> $schema = getStructSchema();\n"
              + "        Parent obj = new Parent(\n"
              + "                    (FuncList<String>)$utils.extractPropertyFromMap(Parent.class, FuncList.class, map, $schema, \"names\"),\n"
              + "                    (FuncList<Child>)$utils.extractPropertyFromMap(Parent.class, FuncList.class, map, $schema, \"children\")\n"
              + "                );\n"
              + "        return obj;\n"
              + "    }\n"
              + "    public Map<String, Object> __toMap() {\n"
              + "        Map<String, Object> map = new HashMap<>();\n"
              + "        map.put(\"names\", $utils.toMapValueObject(names));\n"
              + "        map.put(\"children\", $utils.toMapValueObject(children));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public Map<String, Getter> __getSchema() {\n"
              + "        return getStructSchema();\n"
              + "    }\n"
              + "    public static Map<String, Getter> getStructSchema() {\n"
              + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
              + "        map.put(\"names\", new functionalj.types.struct.generator.Getter(\"names\", new functionalj.types.Type(\"functionalj.list\", null, \"FuncList\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.lang.String\", null, java.util.Arrays.asList(new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        map.put(\"children\", new functionalj.types.struct.generator.Getter(\"children\", new functionalj.types.Type(\"functionalj.list\", null, \"FuncList\", java.util.Arrays.asList(new functionalj.types.Generic(\"me.test.Child\", null, java.util.Arrays.asList(new functionalj.types.Type(\"me.test\", null, \"Child\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public String toString() {\n"
              + "        return \"Parent[\" + \"names: \" + names() + \", \" + \"children: \" + children() + \"]\";\n"
              + "    }\n"
              + "    public int hashCode() {\n"
              + "        return toString().hashCode();\n"
              + "    }\n"
              + "    public boolean equals(Object another) {\n"
              + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n"
              + "    }\n"
              + "    public String name1(String param1, java.lang.String ... param2) {\n"
              + "        return Definitions.ParentDef.super.name1(param1, param2);\n"
              + "    }\n"
              + "    \n"
              + "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n"
              + "        \n"
              + "        public final FuncListLens<HOST, String, StringLens<HOST>> names = createSubFuncListLens(\"names\", Parent::names, Parent::withNames, StringLens::of);\n"
              + "        public final FuncListLens<HOST, Child, Child.ChildLens<HOST>> children = createSubFuncListLens(\"children\", Parent::children, Parent::withChildren, Child.ChildLens::new);\n"
              + "        \n"
              + "        public ParentLens(String name, LensSpec<HOST, Parent> spec) {\n"
              + "            super(name, spec);\n"
              + "        }\n"
              + "        \n"
              + "    }\n"
              + "    public static final class Builder {\n"
              + "        \n"
              + "        public final ParentBuilder_withoutChildren names(FuncList<String> names) {\n"
              + "            return (FuncList<Child> children)->{\n"
              + "            return ()->{\n"
              + "                return new Parent(\n"
              + "                    names,\n"
              + "                    children\n"
              + "                );\n"
              + "            };\n"
              + "            };\n"
              + "        }\n"
              + "        \n"
              + "        public static interface ParentBuilder_withoutChildren {\n"
              + "            \n"
              + "            public ParentBuilder_ready children(FuncList<Child> children);\n"
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
              + "}",
              code);
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
        configures, getters, callables, asList("Child"));
        val dataObjSpec = new StructSpecBuilder(sourceSpec).build();
        val generated = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
}
