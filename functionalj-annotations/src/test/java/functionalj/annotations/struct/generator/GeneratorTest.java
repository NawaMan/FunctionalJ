//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.annotations.struct.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.struct.generator.Getter;
import functionalj.annotations.struct.generator.StructBuilder;
import functionalj.annotations.struct.generator.SourceSpec;
import functionalj.annotations.struct.generator.Type;
import functionalj.annotations.struct.generator.SourceSpec.Configurations;
import functionalj.annotations.struct.generator.model.GenStruct;
import lombok.val;

@SuppressWarnings("javadoc")
public class GeneratorTest {
    
    private Configurations configures = new Configurations();
    {
        configures.coupleWithDefinition      = true;
        configures.generateNoArgConstructor  = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass         = true;
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
        val generated = generate();
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import functionalj.annotations.IPostReConstruct;\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.BooleanLens;\n" + 
                "import functionalj.lens.lenses.IntegerLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "\n" + 
                "public class Car implements Definitions.CarDef {\n" + 
                "    \n" + 
                "    public static final CarLens<Car> theCar = new CarLens<>(LensSpec.of(Car.class));\n" + 
                "    private final int anint;\n" + 
                "    private final boolean anbool;\n" + 
                "    private final String anstring;\n" + 
                "    \n" + 
                "    public Car() {\n" + 
                "        this(0, false, null);\n" + 
                "    }\n" + 
                "    public Car(int anint, boolean anbool, String anstring) {\n" + 
                "        this.anint = anint;\n" + 
                "        this.anbool = anbool;\n" + 
                "        this.anstring = anstring;\n" + 
                "    }\n" + 
                "    \n" + 
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
                "        return postReConstruct(new Car(anint, anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnint(Supplier<Integer> anint) {\n" + 
                "        return postReConstruct(new Car(anint.get(), anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnint(Function<Integer, Integer> anint) {\n" + 
                "        return postReConstruct(new Car(anint.apply(this.anint), anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnint(BiFunction<Car, Integer, Integer> anint) {\n" + 
                "        return postReConstruct(new Car(anint.apply(this, this.anint), anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(boolean anbool) {\n" + 
                "        return postReConstruct(new Car(anint, anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(Supplier<Boolean> anbool) {\n" + 
                "        return postReConstruct(new Car(anint, anbool.get(), anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(Function<Boolean, Boolean> anbool) {\n" + 
                "        return postReConstruct(new Car(anint, anbool.apply(this.anbool), anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(BiFunction<Car, Boolean, Boolean> anbool) {\n" + 
                "        return postReConstruct(new Car(anint, anbool.apply(this, this.anbool), anstring));\n" + 
                "    }\n" + 
                "    public Car withAnstring(String anstring) {\n" + 
                "        return postReConstruct(new Car(anint, anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnstring(Supplier<String> anstring) {\n" + 
                "        return postReConstruct(new Car(anint, anbool, anstring.get()));\n" + 
                "    }\n" + 
                "    public Car withAnstring(Function<String, String> anstring) {\n" + 
                "        return postReConstruct(new Car(anint, anbool, anstring.apply(this.anstring)));\n" + 
                "    }\n" + 
                "    public Car withAnstring(BiFunction<Car, String, String> anstring) {\n" + 
                "        return postReConstruct(new Car(anint, anbool, anstring.apply(this, this.anstring)));\n" + 
                "    }\n" + 
                "    private static Car postReConstruct(Car object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    public static Car fromMap(Map<String, Object> map) {\n" + 
                "        return new Car(\n" + 
                "                    (int)map.get(\"anint\"),\n" + 
                "                    (boolean)map.get(\"anbool\"),\n" + 
                "                    (String)map.get(\"anstring\")\n" + 
                "                );\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        java.util.Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"anint\",  (Object)anint);\n" + 
                "        map.put(\"anbool\",  (Object)anbool);\n" + 
                "        map.put(\"anstring\",  (Object)anstring);\n" + 
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
                "        public final IntegerLens<HOST> anint = createSubLens(Car::anint, Car::withAnint, IntegerLens::of);\n" + 
                "        public final BooleanLens<HOST> anbool = createSubLens(Car::anbool, Car::withAnbool, BooleanLens::of);\n" + 
                "        public final StringLens<HOST> anstring = createSubLens(Car::anstring, Car::withAnstring, StringLens::of);\n" + 
                "        \n" + 
                "        public CarLens(LensSpec<HOST, Car> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_anint anint(int anint) {\n" + 
                "            return new Builder_anint(anint);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_anint {\n" + 
                "            \n" + 
                "            private final int anint;\n" + 
                "            \n" + 
                "            private Builder_anint(int anint) {\n" + 
                "                this.anint = anint;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int anint() {\n" + 
                "                return anint;\n" + 
                "            }\n" + 
                "            public Builder_anint anint(int anint) {\n" + 
                "                return new Builder_anint(anint);\n" + 
                "            }\n" + 
                "            public Builder_anint_anbool anbool(boolean anbool) {\n" + 
                "                return new Builder_anint_anbool(this, anbool);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_anint_anbool {\n" + 
                "            \n" + 
                "            private final Builder_anint parent;\n" + 
                "            private final boolean anbool;\n" + 
                "            \n" + 
                "            private Builder_anint_anbool(Builder_anint parent, boolean anbool) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.anbool = anbool;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int anint() {\n" + 
                "                return parent.anint();\n" + 
                "            }\n" + 
                "            public boolean anbool() {\n" + 
                "                return anbool;\n" + 
                "            }\n" + 
                "            public Builder_anint_anbool anint(int anint) {\n" + 
                "                return parent.anint(anint).anbool(anbool);\n" + 
                "            }\n" + 
                "            public Builder_anint_anbool anbool(boolean anbool) {\n" + 
                "                return parent.anbool(anbool);\n" + 
                "            }\n" + 
                "            public Builder_anint_anbool_anstring anstring(String anstring) {\n" + 
                "                return new Builder_anint_anbool_anstring(this, anstring);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_anint_anbool_anstring {\n" + 
                "            \n" + 
                "            private final Builder_anint_anbool parent;\n" + 
                "            private final String anstring;\n" + 
                "            \n" + 
                "            private Builder_anint_anbool_anstring(Builder_anint_anbool parent, String anstring) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.anstring = anstring;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int anint() {\n" + 
                "                return parent.anint();\n" + 
                "            }\n" + 
                "            public boolean anbool() {\n" + 
                "                return parent.anbool();\n" + 
                "            }\n" + 
                "            public String anstring() {\n" + 
                "                return anstring;\n" + 
                "            }\n" + 
                "            public Builder_anint_anbool_anstring anint(int anint) {\n" + 
                "                return parent.anint(anint).anstring(anstring);\n" + 
                "            }\n" + 
                "            public Builder_anint_anbool_anstring anbool(boolean anbool) {\n" + 
                "                return parent.anbool(anbool).anstring(anstring);\n" + 
                "            }\n" + 
                "            public Builder_anint_anbool_anstring anstring(String anstring) {\n" + 
                "                return parent.anstring(anstring);\n" + 
                "            }\n" + 
                "            public Car build() {\n" + 
                "                return new Car(anint(), anbool(), anstring());\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}", generated);
    }
    
    @Test
    public void testDecouplingWithSuper() {
        val generatedWith = generate(()->{
            configures.coupleWithDefinition = true;
        });
        assertTrue(generatedWith.contains("Definitions.CarDef"));
        
        val generatedWithout = generate(()->{
            configures.coupleWithDefinition = false;
        });
        assertFalse(generatedWithout.contains("Definitions.CarDef"));
    }
    
    @Test
    public void testIsClassOrInteface() {
        val generatedWith = generate(()->{
            isClass = true;
        });
        assertTrue(generatedWith.contains(" extends Definitions.CarDef"));
        
        val generatedWithout = generate(()->{
            isClass = false;
        });
        assertTrue(generatedWithout.contains(" implements Definitions.CarDef"));
    }
    
    @Test
    public void testNoArgConstructor() {
        val generatedWith = generate(()->{
            configures.generateNoArgConstructor = true;
        });
        assertTrue(generatedWith.contains("public Car() {"));
        
        val generatedWithout = generate(()->{
            configures.generateNoArgConstructor = false;
        });
        assertFalse(generatedWithout.contains("public Car() {"));
    }
    
    @Test
    public void testAllArgConstructor() {
        val generatedWith = generate(()->{
            configures.generateAllArgConstructor = true;
        });
        assertTrue(generatedWith.contains("public Car(int anint, boolean anbool, String anstring) {"));
        
        val generatedWithout = generate(()->{
            configures.generateAllArgConstructor = false;
        });
        assertTrue(generatedWithout.contains("private Car(int anint, boolean anbool, String anstring) {"));
    }
    
    @Test
    public void testLensClass() {
        val generatedWith = generate(()->{
            configures.generateLensClass = true;
        });
        assertTrue(generatedWith.contains("public static final CarLens<Car> theCar = new CarLens<>(LensSpec.of(Car.class));"));
        assertTrue(generatedWith.contains("public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {"));
        
        val generatedWithout = generate(()->{
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
                    targetClassName,     // targetClassName
                    packageName,         // targetPackageName
                    isClass,             // isClass
                    null,
                    configures,          // Configurations
                    getters);
        val dataObjSpec = new StructBuilder(sourceSpec).build();
        val generated   = new GenStruct(dataObjSpec).toText();
        return generated;
    }
}
