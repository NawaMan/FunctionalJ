package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static nawaman.functionalj.annotations.processor.generator.DataObjectBuilder.generateDataObjSpec;
import static nawaman.functionalj.annotations.processor.generator.DataObjectCodeGenerator.generateDataObjectClass;
import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import nawaman.functionalj.annotations.processor.generator.SourceSpec.Configurations;

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
        
        val dataObjSpec = generateDataObjSpec(sourceSpec);
        val generated   = generateDataObjectClass(dataObjSpec).collect(Collectors.joining("\n"));
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
                "    public final static Car.CarLens<Car> theCar = new CarLens<>(LensSpec.of(Car.class));;\n" + 
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
                "        if (object instanceof     ((%1$s)object).postConstruct();)\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {\n" + 
                "    \n" + 
                "        public final IntegerLens<HOST> anint = createSubLens(Car::anint, Car::withAnint, spec->()->spec);\n" + 
                "        public final BooleanLens<HOST> anbool = createSubLens(Car::anbool, Car::withAnbool, spec->()->spec);\n" + 
                "        public final StringLens<HOST> anstring = createSubLens(Car::anstring, Car::withAnstring, spec->()->spec);\n" + 
                "    \n" + 
                "        public CarLens(LensSpec<HOST, Car> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "    \n" + 
                "    }\n" + 
                "    \n" + 
                "}", generated);
    }
    
}
