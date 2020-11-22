// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import functionalj.types.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class GeneratorTest {
    
    private Configurations configures = new Configurations();
    {
        configures.coupleWithDefinition      = true;
        configures.generateNoArgConstructor  = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass         = true;
        configures.toStringTemplate          = "";
    }
    
    private String  definitionClassName = "Definitions.CarDef";
    private String  targetClassName     = "Car";
    private String  packageName         = "me.test";
    private boolean isClass             = false;
    
    private List<Getter> getters = asList(
            new Getter("anint",    Type.INT),
            new Getter("anbool",   Type.BOOL),
            new Getter("anstring", Type.STRING)
    );
    
    @Test
    public void testStandard() {
        var generated = generate();
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.BooleanLens;\n" + 
                "import functionalj.lens.lenses.IntegerLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
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
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Car.CarLens;\n" + 
                "\n" + 
                "// me.test.null.Definitions.CarDef\n" + 
                "\n" + 
                "public class Car implements Definitions.CarDef,IStruct,Pipeable<Car> {\n" + 
                "    \n" + 
                "    public static final Car.CarLens<Car> theCar = new Car.CarLens<>(LensSpec.of(Car.class));\n" + 
                "    public static final Car.CarLens<Car> eachCar = theCar;\n" + 
                "    public final int anint;\n" + 
                "    public final boolean anbool;\n" + 
                "    public final String anstring;\n" + 
                "    \n" + 
                "    public Car() {\n" + 
                "        this(0, false, null);\n" + 
                "    }\n" + 
                "    public Car(int anint, boolean anbool, String anstring) {\n" + 
                "        this.anint = anint;\n" + 
                "        this.anbool = anbool;\n" + 
                "        this.anstring = $utils.notNull(anstring);\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Car __data() throws Exception  {\n" + 
                "        return this;\n" + 
                "    }\n" + 
                "    public int anint() {\n" + 
                "        return anint;\n" + 
                "    }\n" + 
                "    public boolean anbool() {\n" + 
                "        return anbool;\n" + 
                "    }\n" + 
                "    public String anstring() {\n" + 
                "        return anstring;\n" + 
                "    }\n" + 
                "    public Car withAnint(int anint) {\n" + 
                "        return new Car(anint, anbool, anstring);\n" + 
                "    }\n" + 
                "    public Car withAnint(Supplier<Integer> anint) {\n" + 
                "        return new Car(anint.get(), anbool, anstring);\n" + 
                "    }\n" + 
                "    public Car withAnint(Function<Integer, Integer> anint) {\n" + 
                "        return new Car(anint.apply(this.anint), anbool, anstring);\n" + 
                "    }\n" + 
                "    public Car withAnint(BiFunction<Car, Integer, Integer> anint) {\n" + 
                "        return new Car(anint.apply(this, this.anint), anbool, anstring);\n" + 
                "    }\n" + 
                "    public Car withAnbool(boolean anbool) {\n" + 
                "        return new Car(anint, anbool, anstring);\n" + 
                "    }\n" + 
                "    public Car withAnbool(Supplier<Boolean> anbool) {\n" + 
                "        return new Car(anint, anbool.get(), anstring);\n" + 
                "    }\n" + 
                "    public Car withAnbool(Function<Boolean, Boolean> anbool) {\n" + 
                "        return new Car(anint, anbool.apply(this.anbool), anstring);\n" + 
                "    }\n" + 
                "    public Car withAnbool(BiFunction<Car, Boolean, Boolean> anbool) {\n" + 
                "        return new Car(anint, anbool.apply(this, this.anbool), anstring);\n" + 
                "    }\n" + 
                "    public Car withAnstring(String anstring) {\n" + 
                "        return new Car(anint, anbool, anstring);\n" + 
                "    }\n" + 
                "    public Car withAnstring(Supplier<String> anstring) {\n" + 
                "        return new Car(anint, anbool, anstring.get());\n" + 
                "    }\n" + 
                "    public Car withAnstring(Function<String, String> anstring) {\n" + 
                "        return new Car(anint, anbool, anstring.apply(this.anstring));\n" + 
                "    }\n" + 
                "    public Car withAnstring(BiFunction<Car, String, String> anstring) {\n" + 
                "        return new Car(anint, anbool, anstring.apply(this, this.anstring));\n" + 
                "    }\n" + 
                "    public static Car fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        \n" + 
                "        Car obj = new Car(\n" + 
                "                    (int)$utils.fromMapValue(map.get(\"anint\"), $schema.get(\"anint\")),\n" + 
                "                    (boolean)$utils.fromMapValue(map.get(\"anbool\"), $schema.get(\"anbool\")),\n" + 
                "                    (String)$utils.fromMapValue(map.get(\"anstring\"), $schema.get(\"anstring\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> __toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"anint\", functionalj.types.IStruct.$utils.toMapValueObject(anint));\n" + 
                "        map.put(\"anbool\", functionalj.types.IStruct.$utils.toMapValueObject(anbool));\n" + 
                "        map.put(\"anstring\", functionalj.types.IStruct.$utils.toMapValueObject(anstring));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> __getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"anint\", new functionalj.types.struct.generator.Getter(\"anint\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"anbool\", new functionalj.types.struct.generator.Getter(\"anbool\", new functionalj.types.Type(null, null, \"boolean\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"anstring\", new functionalj.types.struct.generator.Getter(\"anstring\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Car[\" + \"anint: \" + anint() + \", \" + \"anbool: \" + anbool() + \", \" + \"anstring: \" + anstring() + \"]\";\n" + 
                "    }\n" + 
                "    public int hashCode() {\n" + 
                "        return toString().hashCode();\n" + 
                "    }\n" + 
                "    public boolean equals(Object another) {\n" + 
                "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {\n" + 
                "        \n" + 
                "        public final IntegerLens<HOST> anint = createSubLensInt(Car::anint, Car::withAnint);\n" + 
                "        public final BooleanLens<HOST> anbool = createSubLensBoolean(Car::anbool, Car::withAnbool);\n" + 
                "        public final StringLens<HOST> anstring = createSubLens(Car::anstring, Car::withAnstring, StringLens::of);\n" + 
                "        \n" + 
                "        public CarLens(LensSpec<HOST, Car> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static final class Builder {\n" + 
                "        \n" + 
                "        public final CarBuilder_withoutAnbool anint(int anint) {\n" + 
                "            return (boolean anbool)->{\n" + 
                "            return (String anstring)->{\n" + 
                "            return ()->{\n" + 
                "                return new Car(\n" + 
                "                    anint,\n" + 
                "                    anbool,\n" + 
                "                    anstring\n" + 
                "                );\n" + 
                "            };\n" + 
                "            };\n" + 
                "            };\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static interface CarBuilder_withoutAnbool {\n" + 
                "            \n" + 
                "            public CarBuilder_withoutAnstring anbool(boolean anbool);\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface CarBuilder_withoutAnstring {\n" + 
                "            \n" + 
                "            public CarBuilder_ready anstring(String anstring);\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface CarBuilder_ready {\n" + 
                "            \n" + 
                "            public Car build();\n" + 
                "            \n" + 
                "            \n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}", generated);
    }
    
    @Test
    public void testDecouplingWithSuper() {
        var generatedWith = generate(()->{
            configures.coupleWithDefinition = true;
        });
        assertTrue(generatedWith.contains(" implements Definitions.CarDef"));
        
        var generatedWithout = generate(()->{
            configures.coupleWithDefinition = false;
        });
        assertFalse(generatedWithout.contains(" implements Definitions.CarDef"));
    }
    
    @Test
    public void testIsClassOrInteface() {
        var generatedWith = generate(()->{
            isClass = true;
        });
        assertTrue(generatedWith.contains(" extends Definitions.CarDef"));
        
        var generatedWithout = generate(()->{
            isClass = false;
        });
        assertTrue(generatedWithout.contains(" implements Definitions.CarDef"));
    }
    
    @Test
    public void testNoArgConstructor() {
        var generatedWith = generate(()->{
            configures.generateNoArgConstructor = true;
        });
        assertTrue(generatedWith.contains("public Car() {"));
        
        var generatedWithout = generate(()->{
            configures.generateNoArgConstructor = false;
        });
        assertFalse(generatedWithout.contains("public Car() {"));
    }
    
    @Test
    public void testAllArgConstructor() {
        var generatedWith = generate(()->{
            configures.generateAllArgConstructor = true;
        });
        assertTrue(generatedWith.contains("public Car(int anint, boolean anbool, String anstring) {"));
        
        var generatedWithout = generate(()->{
            configures.generateAllArgConstructor = false;
        });
        assertTrue(generatedWithout.contains("private Car(int anint, boolean anbool, String anstring) {"));
    }
    
    @Test
    public void testLensClass() {
        var generatedWith = generate(()->{
            configures.generateLensClass = true;
        });
        assertTrue(generatedWith.contains("public static final Car.CarLens<Car> theCar = new Car.CarLens<>(LensSpec.of(Car.class));"));
        assertTrue(generatedWith.contains("public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {"));
        
        var generatedWithout = generate(()->{
            configures.generateLensClass = false;
        });
        assertFalse(generatedWithout.contains("public static final CarLens<Car> theCar = new CarLens<>(LensSpec.of(Car.class));"));
        assertFalse(generatedWithout.contains("public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {"));
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
        var dataObjSpec = new StructBuilder(sourceSpec).build();
        var generated   = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
}
