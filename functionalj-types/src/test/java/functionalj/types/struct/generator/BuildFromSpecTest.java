package functionalj.types.struct.generator;

import static org.junit.Assert.*;

import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class BuildFromSpecTest {
    
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(null, "functionalj.types.struct", "FromMapTest", "Birthday", "functionalj.types.struct", null, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("date", new functionalj.types.Type("java.time", null, "LocalDate", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)), java.util.Arrays.asList("Birthday"));
    
    @Test
    public void test() {
//        val dataObjSpec = new StructBuilder(spec).build();
//        val generated   = new GenStruct(spec, dataObjSpec).toText();
//        System.out.println(generated);
//        
        
        val fromMap = StructMapGeneratorHelper.generateFromMap(spec);
        System.out.println(fromMap.toDefinition("nawa").lines().collect(Collectors.joining("\n")));
    }
    
}
