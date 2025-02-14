// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.types.StructToString.Legacy;
import static functionalj.types.TestHelper.assertAsString;
import static java.util.Collections.emptyList;

import org.junit.Test;

import functionalj.types.JavaVersionInfo;
import functionalj.types.Serialize;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class NonPublicConstructorTest {
    
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(new JavaVersionInfo(8, 8), null, "example.functionalj.accesslens", "StructTypeExample", "Person", "example.functionalj.accesslens", null, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, null, false, true, true, true, true, true, false, Serialize.To.NOTHING, Legacy, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("firstName", new Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("midName", new Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL), new functionalj.types.struct.generator.Getter("lastName", new Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)), emptyList(), java.util.Arrays.asList("Person"));
    
    private String generate() {
        val dataObjSpec = new StructClassSpecBuilder(spec).build();
        val generated = new GenStruct(spec, dataObjSpec).toText();
        return generated;
    }
    
    @Test
    public void test() {
        val generated = generate();
        assertAsString(expected, generated);
    }
    
    private String expected = 
                  "package example.functionalj.accesslens;\n"
                + "\n"
                + "import functionalj.lens.core.LensSpec;\n"
                + "import functionalj.lens.lenses.ObjectLensImpl;\n"
                + "import functionalj.lens.lenses.StringLens;\n"
                + "import functionalj.pipeable.Pipeable;\n"
                + "import functionalj.types.Generated;\n"
                + "import functionalj.types.IPostConstruct;\n"
                + "import functionalj.types.IStruct;\n"
                + "import functionalj.types.struct.generator.Getter;\n"
                + "import functionalj.types.struct.generator.SourceSpec;\n"
                + "import java.lang.Exception;\n"
                + "import java.lang.Object;\n"
                + "import java.util.HashMap;\n"
                + "import java.util.Map;\n"
                + "import java.util.function.BiFunction;\n"
                + "import java.util.function.Function;\n"
                + "import java.util.function.Supplier;\n"
                + "\n"
                + "@Generated(value = \"FunctionalJ\", date = \"\\E[^\"]+\\Q\", comments = \"example.functionalj.accesslens.StructTypeExample\")\n"
                + "@SuppressWarnings(\"all\")\n"
                + "\n"
                + "public class Person implements IStruct,Pipeable<Person> {\n"
                + "    \n"
                + "    public static final Person.PersonLens<Person> thePerson = new Person.PersonLens<>(\"thePerson\", LensSpec.of(Person.class));\n"
                + "    public static final Person.PersonLens<Person> eachPerson = thePerson;\n"
                + "    public final String firstName;\n"
                + "    public final String midName;\n"
                + "    public final String lastName;\n"
                + "    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(new functionalj.types.JavaVersionInfo(8, 8), null, \"example.functionalj.accesslens\", \"StructTypeExample\", \"Person\", \"example.functionalj.accesslens\", null, \"spec\", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, null, false, true, true, true, true, true, false, functionalj.types.Serialize.To.NOTHING, functionalj.types.StructToString.Legacy, \"\"), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter(\"firstName\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter(\"midName\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL), new functionalj.types.struct.generator.Getter(\"lastName\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)), java.util.Collections.emptyList(), java.util.Arrays.asList(\"Person\"));\n"
                + "    \n"
                + "    Person(String firstName, String lastName) {\n"
                + "        this($utils.notNull(firstName), null, $utils.notNull(lastName));\n"
                + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
                + "    }\n"
                + "    Person(String firstName, String midName, String lastName) {\n"
                + "        this.firstName = $utils.notNull(firstName);\n"
                + "        this.midName = java.util.Optional.ofNullable(midName).orElseGet(()->null);\n"
                + "        this.lastName = $utils.notNull(lastName);\n"
                + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
                + "    }\n"
                + "    \n"
                + "    public Person __data() throws Exception  {\n"
                + "        return this;\n"
                + "    }\n"
                + "    public String firstName() {\n"
                + "        return firstName;\n"
                + "    }\n"
                + "    public String midName() {\n"
                + "        return midName;\n"
                + "    }\n"
                + "    public String lastName() {\n"
                + "        return lastName;\n"
                + "    }\n"
                + "    public Person withFirstName(String firstName) {\n"
                + "        return new Person(firstName, midName, lastName);\n"
                + "    }\n"
                + "    public Person withFirstName(Supplier<String> firstName) {\n"
                + "        return new Person(firstName.get(), midName, lastName);\n"
                + "    }\n"
                + "    public Person withFirstName(Function<String, String> firstName) {\n"
                + "        return new Person(firstName.apply(this.firstName), midName, lastName);\n"
                + "    }\n"
                + "    public Person withFirstName(BiFunction<Person, String, String> firstName) {\n"
                + "        return new Person(firstName.apply(this, this.firstName), midName, lastName);\n"
                + "    }\n"
                + "    public Person withMidName(String midName) {\n"
                + "        return new Person(firstName, midName, lastName);\n"
                + "    }\n"
                + "    public Person withMidName(Supplier<String> midName) {\n"
                + "        return new Person(firstName, midName.get(), lastName);\n"
                + "    }\n"
                + "    public Person withMidName(Function<String, String> midName) {\n"
                + "        return new Person(firstName, midName.apply(this.midName), lastName);\n"
                + "    }\n"
                + "    public Person withMidName(BiFunction<Person, String, String> midName) {\n"
                + "        return new Person(firstName, midName.apply(this, this.midName), lastName);\n"
                + "    }\n"
                + "    public Person withLastName(String lastName) {\n"
                + "        return new Person(firstName, midName, lastName);\n"
                + "    }\n"
                + "    public Person withLastName(Supplier<String> lastName) {\n"
                + "        return new Person(firstName, midName, lastName.get());\n"
                + "    }\n"
                + "    public Person withLastName(Function<String, String> lastName) {\n"
                + "        return new Person(firstName, midName, lastName.apply(this.lastName));\n"
                + "    }\n"
                + "    public Person withLastName(BiFunction<Person, String, String> lastName) {\n"
                + "        return new Person(firstName, midName, lastName.apply(this, this.lastName));\n"
                + "    }\n"
                + "    public static Person fromMap(Map<String, ? extends Object> map) {\n"
                + "        Map<String, Getter> $schema = getStructSchema();\n"
                + "        Person obj = new Person(\n"
                + "                    (String)$utils.extractPropertyFromMap(Person.class, String.class, map, $schema, \"firstName\"),\n"
                + "                    (String)$utils.extractPropertyFromMap(Person.class, String.class, map, $schema, \"midName\"),\n"
                + "                    (String)$utils.extractPropertyFromMap(Person.class, String.class, map, $schema, \"lastName\")\n"
                + "                );\n"
                + "        return obj;\n"
                + "    }\n"
                + "    public Map<String, Object> __toMap() {\n"
                + "        Map<String, Object> map = new HashMap<>();\n"
                + "        map.put(\"firstName\", $utils.toMapValueObject(firstName));\n"
                + "        map.put(\"midName\", $utils.toMapValueObject(midName));\n"
                + "        map.put(\"lastName\", $utils.toMapValueObject(lastName));\n"
                + "        return map;\n"
                + "    }\n"
                + "    public Map<String, Getter> __getSchema() {\n"
                + "        return getStructSchema();\n"
                + "    }\n"
                + "    public static Map<String, Getter> getStructSchema() {\n"
                + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
                + "        map.put(\"firstName\", new functionalj.types.struct.generator.Getter(\"firstName\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
                + "        map.put(\"midName\", new functionalj.types.struct.generator.Getter(\"midName\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n"
                + "        map.put(\"lastName\", new functionalj.types.struct.generator.Getter(\"lastName\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
                + "        return map;\n"
                + "    }\n"
                + "    public String toString() {\n"
                + "        return \"Person[\" + \"firstName: \" + firstName() + \", \" + \"midName: \" + midName() + \", \" + \"lastName: \" + lastName() + \"]\";\n"
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
                + "        public final StringLens<HOST> firstName = createSubLens(\"firstName\", Person::firstName, Person::withFirstName, StringLens::of);\n"
                + "        public final StringLens<HOST> midName = createSubLens(\"midName\", Person::midName, Person::withMidName, StringLens::of);\n"
                + "        public final StringLens<HOST> lastName = createSubLens(\"lastName\", Person::lastName, Person::withLastName, StringLens::of);\n"
                + "        \n"
                + "        public PersonLens(String name, LensSpec<HOST, Person> spec) {\n"
                + "            super(name, spec);\n"
                + "        }\n"
                + "        \n"
                + "    }\n"
                + "    public static final class Builder {\n"
                + "        \n"
                + "        public final PersonBuilder_withoutMidName firstName(String firstName) {\n"
                + "            return (String midName)->{\n"
                + "            return (String lastName)->{\n"
                + "            return ()->{\n"
                + "                return new Person(\n"
                + "                    firstName,\n"
                + "                    midName,\n"
                + "                    lastName\n"
                + "                );\n"
                + "            };\n"
                + "            };\n"
                + "            };\n"
                + "        }\n"
                + "        \n"
                + "        public static interface PersonBuilder_withoutMidName {\n"
                + "            \n"
                + "            public PersonBuilder_withoutLastName midName(String midName);\n"
                + "            \n"
                + "            public default PersonBuilder_ready lastName(String lastName){\n"
                + "                return midName(null).lastName(lastName);\n"
                + "            }\n"
                + "            \n"
                + "        }\n"
                + "        public static interface PersonBuilder_withoutLastName {\n"
                + "            \n"
                + "            public PersonBuilder_ready lastName(String lastName);\n"
                + "            \n"
                + "        }\n"
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
                + "}";
}
