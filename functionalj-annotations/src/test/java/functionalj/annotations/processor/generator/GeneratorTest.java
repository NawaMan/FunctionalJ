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
package functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.processor.generator.SourceSpec.Configurations;
import functionalj.annotations.processor.generator.model.GenDataObject;
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
                "    public Car withAnint(Supplier<int> anint) {\n" + 
                "        return postReConstruct(new Car(anint.get(), anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnint(Function<int, int> anint) {\n" + 
                "        return postReConstruct(new Car(anint.apply(this.anint), anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnint(BiFunction<Car, int, int> anint) {\n" + 
                "        return postReConstruct(new Car(anint.apply(this, this.anint), anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(boolean anbool) {\n" + 
                "        return postReConstruct(new Car(anint, anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(Supplier<boolean> anbool) {\n" + 
                "        return postReConstruct(new Car(anint, anbool.get(), anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(Function<boolean, boolean> anbool) {\n" + 
                "        return postReConstruct(new Car(anint, anbool.apply(this.anbool), anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(BiFunction<Car, boolean, boolean> anbool) {\n" + 
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
                "    \n" + 
                "    public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {\n" + 
                "        \n" + 
                "        public final IntegerLens<HOST> anint = createSubLens(Car::anint, Car::withAnint, spec->()->spec);\n" + 
                "        public final BooleanLens<HOST> anbool = createSubLens(Car::anbool, Car::withAnbool, spec->()->spec);\n" + 
                "        public final StringLens<HOST> anstring = createSubLens(Car::anstring, Car::withAnstring, spec->()->spec);\n" + 
                "        \n" + 
                "        public CarLens(LensSpec<HOST, Car> spec) {\n" + 
                "            super(spec);\n" + 
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
                    configures,          // Configurations
                    getters);
        val dataObjSpec = new DataObjectBuilder(sourceSpec).build();
        val generated   = new GenDataObject(dataObjSpec).toText();
        return generated;
    }
}
