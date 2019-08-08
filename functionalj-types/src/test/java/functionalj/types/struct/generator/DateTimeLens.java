package functionalj.types.struct.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import functionalj.types.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class DateTimeLens {
    
    private Configurations configures = new Configurations();
    {
        configures.coupleWithDefinition      = true;
        configures.generateNoArgConstructor  = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass         = true;
        configures.toStringTemplate          = "";
    }
    
    private String  definitionClassName = "Definitions.PersonDef";
    private String  targetClassName     = "Person";
    private String  packageName         = "me.test";
    private boolean isClass             = false;
    
    private List<Getter> getters = asList(
            new Getter("child", Type.of(LocalDate.class))
    );
    
    @Test
    public void testParent() {
        val code = generate();
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.java.time.LocalDateLens;\n" + 
                "import functionalj.pipeable.Pipeable;\n" + 
                "import functionalj.types.IPostConstruct;\n" + 
                "import functionalj.types.IStruct;\n" + 
                "import functionalj.types.Type;\n" + 
                "import functionalj.types.struct.generator.Getter;\n" + 
                "import java.lang.Exception;\n" + 
                "import java.lang.Object;\n" + 
                "import java.time.LocalDate;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Person.PersonLens;\n" + 
                "\n" + 
                "// me.test.null.Definitions.PersonDef\n" + 
                "\n" + 
                "public class Person implements Definitions.PersonDef,IStruct,Pipeable<Person> {\n" + 
                "    \n" + 
                "    public static final Person.PersonLens<Person> thePerson = new Person.PersonLens<>(LensSpec.of(Person.class));\n" + 
                "    public final LocalDate child;\n" + 
                "    \n" + 
                "    public Person() {\n" + 
                "        this(null);\n" + 
                "    }\n" + 
                "    public Person(LocalDate child) {\n" + 
                "        this.child = $utils.notNull(child);\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Person __data() throws Exception  {\n" + 
                "        return this;\n" + 
                "    }\n" + 
                "    public LocalDate child() {\n" + 
                "        return child;\n" + 
                "    }\n" + 
                "    public Person withChild(LocalDate child) {\n" + 
                "        return new Person(child);\n" + 
                "    }\n" + 
                "    public Person withChild(Supplier<LocalDate> child) {\n" + 
                "        return new Person(child.get());\n" + 
                "    }\n" + 
                "    public Person withChild(Function<LocalDate, LocalDate> child) {\n" + 
                "        return new Person(child.apply(this.child));\n" + 
                "    }\n" + 
                "    public Person withChild(BiFunction<Person, LocalDate, LocalDate> child) {\n" + 
                "        return new Person(child.apply(this, this.child));\n" + 
                "    }\n" + 
                "    public static Person fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        \n" + 
                "        Person obj = new Person(\n" + 
                "                    (LocalDate)$utils.fromMapValue(map.get(\"child\"), $schema.get(\"child\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> __toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"child\", functionalj.types.IStruct.$utils.toMapValueObject(child));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> __getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"child\", new functionalj.types.struct.generator.Getter(\"child\", new functionalj.types.Type(\"java.time\", null, \"LocalDate\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Person[\" + \"child: \" + child() + \"]\";\n" + 
                "    }\n" + 
                "    public int hashCode() {\n" + 
                "        return toString().hashCode();\n" + 
                "    }\n" + 
                "    public boolean equals(Object another) {\n" + 
                "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class PersonLens<HOST> extends ObjectLensImpl<HOST, Person> {\n" + 
                "        \n" + 
                "        public final LocalDateLens<HOST> child = createSubLens(Person::child, Person::withChild, LocalDateLens::of);\n" + 
                "        \n" + 
                "        public PersonLens(LensSpec<HOST, Person> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static final class Builder {\n" + 
                "        \n" + 
                "        public final PersonBuilder_ready child(LocalDate child) {\n" + 
                "            return ()->{\n" + 
                "                return new Person(\n" + 
                "                    child\n" + 
                "                );\n" + 
                "            };\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static interface PersonBuilder_ready {\n" + 
                "            \n" + 
                "            public Person build();\n" + 
                "            \n" + 
                "            \n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}", code);
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
                    emptyList());
        val dataObjSpec = new StructBuilder(sourceSpec).build();
        val generated   = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
    
}
