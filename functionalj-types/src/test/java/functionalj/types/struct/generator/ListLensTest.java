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
import static functionalj.types.struct.SourceKind.INTERFACE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.List;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.JavaVersionInfo;
import functionalj.types.Type;
import functionalj.types.struct.SourceKind;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class ListLensTest {
    
    private Configurations configurations = new Configurations();
    
    {
        configurations.coupleWithDefinition = true;
        configurations.generateNoArgConstructor = true;
        configurations.generateAllArgConstructor = true;
        configurations.generateLensClass = true;
        configurations.toStringTemplate = "";
    }
    
    private String definitionClassName = "Definitions.PersonDef";
    
    private String targetClassName = "Person";
    
    private String packageName = "me.test";
    
    private SourceKind sourceKind = INTERFACE;
    
    private List<Getter> getters;
    
    @Test
    public void testListLens_unTyped() {
        getters = asList(new Getter("child", Type.of(List.class)));
        val code = generate();
        assertAsString("package me.test;\n"
              + "\n"
              + "import functionalj.lens.core.LensSpec;\n"
              + "import functionalj.lens.lenses.ListLens;\n"
              + "import functionalj.lens.lenses.ObjectLensImpl;\n"
              + "import functionalj.pipeable.Pipeable;\n"
              + "import functionalj.types.Generated;\n"
              + "import functionalj.types.IPostConstruct;\n"
              + "import functionalj.types.IStruct;\n"
              + "import functionalj.types.struct.generator.Getter;\n"
              + "import java.lang.Exception;\n"
              + "import java.lang.Object;\n"
              + "import java.util.HashMap;\n"
              + "import java.util.List;\n"
              + "import java.util.Map;\n"
              + "import java.util.function.BiFunction;\n"
              + "import java.util.function.Function;\n"
              + "import java.util.function.Supplier;\n"
              + "\n"
              + "@Generated(value = \"FunctionalJ\", date = \"\\E[^\"].+\\Q\", comments = \"me.test.null.Definitions.PersonDef\")\n"
              + "@SuppressWarnings(\"all\")\n"
              + "\n"
              + "public class Person implements Definitions.PersonDef,IStruct,Pipeable<Person> {\n"
              + "    \n"
              + "    public static final Person.PersonLens<Person> thePerson = new Person.PersonLens<>(\"thePerson\", LensSpec.of(Person.class));\n"
              + "    public static final Person.PersonLens<Person> eachPerson = thePerson;\n"
              + "    public final List child;\n"
              + "    \n"
              + "    public Person() {\n"
              + "        this(null);\n"
              + "    }\n"
              + "    public Person(List child) {\n"
              + "        this.child = functionalj.list.ImmutableFuncList.from(child);\n"
              + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
              + "    }\n"
              + "    \n"
              + "    public Person __data() throws Exception  {\n"
              + "        return this;\n"
              + "    }\n"
              + "    public List child() {\n"
              + "        return child;\n"
              + "    }\n"
              + "    public Person withChild(Object ... child) {\n"
              + "        return new Person(java.util.Arrays.asList(child));\n"
              + "    }\n"
              + "    public Person withChild(List child) {\n"
              + "        return new Person(child);\n"
              + "    }\n"
              + "    public Person withChild(Supplier<List> child) {\n"
              + "        return new Person(child.get());\n"
              + "    }\n"
              + "    public Person withChild(Function<List, List> child) {\n"
              + "        return new Person(child.apply(this.child));\n"
              + "    }\n"
              + "    public Person withChild(BiFunction<Person, List, List> child) {\n"
              + "        return new Person(child.apply(this, this.child));\n"
              + "    }\n"
              + "    public static Person fromMap(Map<String, ? extends Object> map) {\n"
              + "        Map<String, Getter> $schema = getStructSchema();\n"
              + "        Person obj = new Person(\n"
              + "                    (List)$utils.extractPropertyFromMap(Person.class, List.class, map, $schema, \"child\")\n"
              + "                );\n"
              + "        return obj;\n"
              + "    }\n"
              + "    public Map<String, Object> __toMap() {\n"
              + "        Map<String, Object> map = new HashMap<>();\n"
              + "        map.put(\"child\", $utils.toMapValueObject(child));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public Map<String, Getter> __getSchema() {\n"
              + "        return getStructSchema();\n"
              + "    }\n"
              + "    public static Map<String, Getter> getStructSchema() {\n"
              + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
              + "        map.put(\"child\", new functionalj.types.struct.generator.Getter(\"child\", new functionalj.types.Type(\"java.util\", null, \"List\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public String toString() {\n"
              + "        return \"Person[\" + \"child: \" + child() + \"]\";\n"
              + "    }\n"
              + "    public int hashCode() {\n"
              + "        return toString().hashCode();\n"
              + "    }\n"
              + "    public boolean equals(Object another) {\n"
              + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n"
              + "    }\n"
              + "    \n"
              + "    public static class PersonLens<HOST> extends ObjectLensImpl<HOST, Person> {\n"
              + "        \n"
              + "        public final ListLens<HOST, Object, ObjectLens<HOST, Object>> child = createSubListLens(\"child\", Person::child, Person::withChild, ObjectLens::of);\n"
              + "        \n"
              + "        public PersonLens(String name, LensSpec<HOST, Person> spec) {\n"
              + "            super(name, spec);\n"
              + "        }\n"
              + "        \n"
              + "    }\n"
              + "    public static final class Builder {\n"
              + "        \n"
              + "        public final PersonBuilder_ready child(List child) {\n"
              + "            return ()->{\n"
              + "                return new Person(\n"
              + "                    child\n"
              + "                );\n"
              + "            };\n"
              + "        }\n"
              + "        \n"
              + "        public static interface PersonBuilder_ready {\n"
              + "            \n"
              + "            public Person build();\n"
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
    
    @Test
    public void testListLens_typed() {
        getters = asList(new Getter("children", Type.of(List.class, new Generic(Type.BIGINTEGER))));
        val code = generate();
        assertAsString("package me.test;\n"
              + "\n"
              + "import functionalj.lens.core.LensSpec;\n"
              + "import functionalj.lens.lenses.BigIntegerLens;\n"
              + "import functionalj.lens.lenses.ListLens;\n"
              + "import functionalj.lens.lenses.ObjectLensImpl;\n"
              + "import functionalj.pipeable.Pipeable;\n"
              + "import functionalj.types.Generated;\n"
              + "import functionalj.types.IPostConstruct;\n"
              + "import functionalj.types.IStruct;\n"
              + "import functionalj.types.struct.generator.Getter;\n"
              + "import java.lang.Exception;\n"
              + "import java.lang.Object;\n"
              + "import java.math.BigInteger;\n"
              + "import java.util.HashMap;\n"
              + "import java.util.List;\n"
              + "import java.util.Map;\n"
              + "import java.util.function.BiFunction;\n"
              + "import java.util.function.Function;\n"
              + "import java.util.function.Supplier;\n"
              + "\n"
              + "@Generated(value = \"FunctionalJ\", date = \"\\E[^\"].+\\Q\", comments = \"me.test.null.Definitions.PersonDef\")\n"
              + "@SuppressWarnings(\"all\")\n"
              + "\n"
              + "public class Person implements Definitions.PersonDef,IStruct,Pipeable<Person> {\n"
              + "    \n"
              + "    public static final Person.PersonLens<Person> thePerson = new Person.PersonLens<>(\"thePerson\", LensSpec.of(Person.class));\n"
              + "    public static final Person.PersonLens<Person> eachPerson = thePerson;\n"
              + "    public final List<BigInteger> children;\n"
              + "    \n"
              + "    public Person() {\n"
              + "        this(null);\n"
              + "    }\n"
              + "    public Person(List<BigInteger> children) {\n"
              + "        this.children = functionalj.list.ImmutableFuncList.from(children);\n"
              + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
              + "    }\n"
              + "    \n"
              + "    public Person __data() throws Exception  {\n"
              + "        return this;\n"
              + "    }\n"
              + "    public List<BigInteger> children() {\n"
              + "        return children;\n"
              + "    }\n"
              + "    public Person withChildren(BigInteger ... children) {\n"
              + "        return new Person(java.util.Arrays.asList(children));\n"
              + "    }\n"
              + "    public Person withChildren(List<BigInteger> children) {\n"
              + "        return new Person(children);\n"
              + "    }\n"
              + "    public Person withChildren(Supplier<List<BigInteger>> children) {\n"
              + "        return new Person(children.get());\n"
              + "    }\n"
              + "    public Person withChildren(Function<List<BigInteger>, List<BigInteger>> children) {\n"
              + "        return new Person(children.apply(this.children));\n"
              + "    }\n"
              + "    public Person withChildren(BiFunction<Person, List<BigInteger>, List<BigInteger>> children) {\n"
              + "        return new Person(children.apply(this, this.children));\n"
              + "    }\n"
              + "    public static Person fromMap(Map<String, ? extends Object> map) {\n"
              + "        Map<String, Getter> $schema = getStructSchema();\n"
              + "        Person obj = new Person(\n"
              + "                    (List<BigInteger>)$utils.extractPropertyFromMap(Person.class, List.class, map, $schema, \"children\")\n"
              + "                );\n"
              + "        return obj;\n"
              + "    }\n"
              + "    public Map<String, Object> __toMap() {\n"
              + "        Map<String, Object> map = new HashMap<>();\n"
              + "        map.put(\"children\", $utils.toMapValueObject(children));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public Map<String, Getter> __getSchema() {\n"
              + "        return getStructSchema();\n"
              + "    }\n"
              + "    public static Map<String, Getter> getStructSchema() {\n"
              + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
              + "        map.put(\"children\", new functionalj.types.struct.generator.Getter(\"children\", new functionalj.types.Type(\"java.util\", null, \"List\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.math.BigInteger\", null, java.util.Arrays.asList(new functionalj.types.Type(\"java.math\", null, \"BigInteger\", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public String toString() {\n"
              + "        return \"Person[\" + \"children: \" + children() + \"]\";\n"
              + "    }\n"
              + "    public int hashCode() {\n"
              + "        return toString().hashCode();\n"
              + "    }\n"
              + "    public boolean equals(Object another) {\n"
              + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n"
              + "    }\n"
              + "    \n"
              + "    public static class PersonLens<HOST> extends ObjectLensImpl<HOST, Person> {\n"
              + "        \n"
              + "        public final ListLens<HOST, BigInteger, BigIntegerLens<HOST>> children = createSubListLens(\"children\", Person::children, Person::withChildren, BigIntegerLens::of);\n"
              + "        \n"
              + "        public PersonLens(String name, LensSpec<HOST, Person> spec) {\n"
              + "            super(name, spec);\n"
              + "        }\n"
              + "        \n"
              + "    }\n"
              + "    public static final class Builder {\n"
              + "        \n"
              + "        public final PersonBuilder_ready children(List<BigInteger> children) {\n"
              + "            return ()->{\n"
              + "                return new Person(\n"
              + "                    children\n"
              + "                );\n"
              + "            };\n"
              + "        }\n"
              + "        \n"
              + "        public static interface PersonBuilder_ready {\n"
              + "            \n"
              + "            public Person build();\n"
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
    
    @Test
    public void testListLens_typed_nested() {
        getters = asList(new Getter("children", Type.of(List.class, new Generic(Type.of(List.class, new Generic(Type.OBJECT))))));
        val code = generate();
        assertAsString("package me.test;\n"
              + "\n"
              + "import functionalj.lens.core.LensSpec;\n"
              + "import functionalj.lens.lenses.ListLens;\n"
              + "import functionalj.lens.lenses.ObjectLensImpl;\n"
              + "import functionalj.pipeable.Pipeable;\n"
              + "import functionalj.types.Generated;\n"
              + "import functionalj.types.IPostConstruct;\n"
              + "import functionalj.types.IStruct;\n"
              + "import functionalj.types.struct.generator.Getter;\n"
              + "import java.lang.Exception;\n"
              + "import java.lang.Object;\n"
              + "import java.util.HashMap;\n"
              + "import java.util.List;\n"
              + "import java.util.Map;\n"
              + "import java.util.function.BiFunction;\n"
              + "import java.util.function.Function;\n"
              + "import java.util.function.Supplier;\n"
              + "\n"
              + "@Generated(value = \"FunctionalJ\", date = \"\\E[^\"].+\\Q\", comments = \"me.test.null.Definitions.PersonDef\")\n"
              + "@SuppressWarnings(\"all\")\n"
              + "\n"
              + "public class Person implements Definitions.PersonDef,IStruct,Pipeable<Person> {\n"
              + "    \n"
              + "    public static final Person.PersonLens<Person> thePerson = new Person.PersonLens<>(\"thePerson\", LensSpec.of(Person.class));\n"
              + "    public static final Person.PersonLens<Person> eachPerson = thePerson;\n"
              + "    public final List<List<Object>> children;\n"
              + "    \n"
              + "    public Person() {\n"
              + "        this(null);\n"
              + "    }\n"
              + "    public Person(List<List<Object>> children) {\n"
              + "        this.children = functionalj.list.ImmutableFuncList.from(children);\n"
              + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
              + "    }\n"
              + "    \n"
              + "    public Person __data() throws Exception  {\n"
              + "        return this;\n"
              + "    }\n"
              + "    public List<List<Object>> children() {\n"
              + "        return children;\n"
              + "    }\n"
              + "    public Person withChildren(List<Object> ... children) {\n"
              + "        return new Person(java.util.Arrays.asList(children));\n"
              + "    }\n"
              + "    public Person withChildren(List<List<Object>> children) {\n"
              + "        return new Person(children);\n"
              + "    }\n"
              + "    public Person withChildren(Supplier<List<List<Object>>> children) {\n"
              + "        return new Person(children.get());\n"
              + "    }\n"
              + "    public Person withChildren(Function<List<List<Object>>, List<List<Object>>> children) {\n"
              + "        return new Person(children.apply(this.children));\n"
              + "    }\n"
              + "    public Person withChildren(BiFunction<Person, List<List<Object>>, List<List<Object>>> children) {\n"
              + "        return new Person(children.apply(this, this.children));\n"
              + "    }\n"
              + "    public static Person fromMap(Map<String, ? extends Object> map) {\n"
              + "        Map<String, Getter> $schema = getStructSchema();\n"
              + "        Person obj = new Person(\n"
              + "                    (List<List<Object>>)$utils.extractPropertyFromMap(Person.class, List.class, map, $schema, \"children\")\n"
              + "                );\n"
              + "        return obj;\n"
              + "    }\n"
              + "    public Map<String, Object> __toMap() {\n"
              + "        Map<String, Object> map = new HashMap<>();\n"
              + "        map.put(\"children\", $utils.toMapValueObject(children));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public Map<String, Getter> __getSchema() {\n"
              + "        return getStructSchema();\n"
              + "    }\n"
              + "    public static Map<String, Getter> getStructSchema() {\n"
              + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
              + "        map.put(\"children\", new functionalj.types.struct.generator.Getter(\"children\", new functionalj.types.Type(\"java.util\", null, \"List\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.util.List\", null, java.util.Arrays.asList(new functionalj.types.Type(\"java.util\", null, \"List\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.lang.Object\", null, java.util.Arrays.asList(new functionalj.types.Type(\"java.lang\", null, \"Object\", java.util.Collections.emptyList()))))))))), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public String toString() {\n"
              + "        return \"Person[\" + \"children: \" + children() + \"]\";\n"
              + "    }\n"
              + "    public int hashCode() {\n"
              + "        return toString().hashCode();\n"
              + "    }\n"
              + "    public boolean equals(Object another) {\n"
              + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n"
              + "    }\n"
              + "    \n"
              + "    public static class PersonLens<HOST> extends ObjectLensImpl<HOST, Person> {\n"
              + "        \n"
              + "        public final ListLens<HOST, Object, ObjectLens<HOST, Object>> children = createSubListLens(\"children\", Person::children, Person::withChildren);\n"
              + "        \n"
              + "        public PersonLens(String name, LensSpec<HOST, Person> spec) {\n"
              + "            super(name, spec);\n"
              + "        }\n"
              + "        \n"
              + "    }\n"
              + "    public static final class Builder {\n"
              + "        \n"
              + "        public final PersonBuilder_ready children(List<List<Object>> children) {\n"
              + "            return ()->{\n"
              + "                return new Person(\n"
              + "                    children\n"
              + "                );\n"
              + "            };\n"
              + "        }\n"
              + "        \n"
              + "        public static interface PersonBuilder_ready {\n"
              + "            \n"
              + "            public Person build();\n"
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
    }
    
    private String generate() {
        SourceSpec sourceSpec = new SourceSpec(
                new JavaVersionInfo(8, 8),
                definitionClassName, // specClassName
                packageName,         // packageName
                null,                // encloseName
                targetClassName,     // targetClassName
                packageName,         // targetPackageName
                sourceKind,
                null,
                null,
                configurations,
                getters,
                emptyList(),
                emptyList());
        val dataObjSpec = new StructClassSpecBuilder(sourceSpec).build();
        val generated = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
}
