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
package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;
import nawaman.functionalj.annotations.processor.generator.SourceSpec.Configurations;
import nawaman.functionalj.annotations.processor.generator.model.GenDataObject;

@SuppressWarnings("javadoc")
public class GeneratorTest {
    
    @Test
    public void test() {
        val configures     = new Configurations();
        configures.noArgConstructor  = true;
        configures.generateLensClass = true;
        val sourceSpec = new SourceSpec(
                    "Model.ICar",   // specClassName
                    "me.test",      // packageName
                    "Car",          // targetClassName
                    "me.test",      // targetPackageName
                    false,          // isClass
                    configures,     // Configurations
                    asList(
                            new Getter("anint", Type.INT),
                            new Getter("anbool", Type.BOOL),
                            new Getter("anstring", Type.STRING)
                    )
                );
        
        val dataObjSpec = new DataObjectBuilder(sourceSpec).build();
        val generated   = new GenDataObject(dataObjSpec).toText();
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import nawaman.functionalj.lens.BooleanLens;\n" + 
                "import nawaman.functionalj.lens.IPostConstruct;\n" + 
                "import nawaman.functionalj.lens.IntegerLens;\n" + 
                "import nawaman.functionalj.lens.LensSpec;\n" + 
                "import nawaman.functionalj.lens.ObjectLensImpl;\n" + 
                "import nawaman.functionalj.lens.StringLens;\n" + 
                "\n" + 
                "public class Car implements Model.ICar {\n" + 
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
                "        return postProcess(new Car(anint, anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnbool(boolean anbool) {\n" + 
                "        return postProcess(new Car(anint, anbool, anstring));\n" + 
                "    }\n" + 
                "    public Car withAnstring(String anstring) {\n" + 
                "        return postProcess(new Car(anint, anbool, anstring));\n" + 
                "    }\n" + 
                "    private Car postProcess(Car object) {\n" + 
                "        if (object instanceof IPostConstruct)\n" + 
                "            ((IPostConstruct)object).postConstruct();\n" + 
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
    
}
