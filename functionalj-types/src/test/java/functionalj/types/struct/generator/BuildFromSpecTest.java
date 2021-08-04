package functionalj.types.struct.generator;

import static org.junit.Assert.assertEquals;


import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Serialize;
import lombok.val;

public class BuildFromSpecTest {
    
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(null, "functionalj.types.struct", "FromMapTest", "Birthday", "functionalj.types.struct", null, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, "", Serialize.To.NOTHING), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("date", new functionalj.types.Type("java.time", null, "LocalDate", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)), java.util.Arrays.asList("Birthday"));
    
    @Test
    public void testFromMap() {
        val fromMap = StructMapGeneratorHelper.generateFromMap(spec);
        assertEquals(
                "public static Birthday fromMap(Map<String, ? extends Object> map) {\n"
                + "    Map<String, Getter> $schema = getStructSchema();\n"
                + "    Birthday obj = new Birthday(\n"
                + "                (String)$utils.extractPropertyFromMap(Birthday.class, String.class, map, $schema, \"name\"),\n"
                + "                (LocalDate)$utils.extractPropertyFromMap(Birthday.class, LocalDate.class, map, $schema, \"date\")\n"
                + "            );\n"
                + "    return obj;\n"
                + "}",
                fromMap.toDefinition("nawa").lines().collect(Collectors.joining("\n")));
    }
    
}
