package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static nawaman.functionalj.annotations.processor.generator.DataObjectGenerator.generateRecordClass;
import static nawaman.functionalj.annotations.processor.generator.DataObjectGenerator.generateRecordSpec;

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
        val recordSpec = generateRecordSpec(sourceSpec);
        generateRecordClass(recordSpec)
            .forEach(System.out::println);
    }
    
}
