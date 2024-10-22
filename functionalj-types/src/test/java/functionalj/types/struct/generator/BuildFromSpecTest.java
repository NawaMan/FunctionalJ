// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import java.util.stream.Collectors;
import org.junit.Test;

import functionalj.types.JavaVersionInfo;
import functionalj.types.Serialize;
import lombok.val;

public class BuildFromSpecTest {
    
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(
            new JavaVersionInfo(8, 8), 
            null, 
            "functionalj.types.struct", 
            "FromMapTest", 
            "Birthday", 
            "functionalj.types.struct", 
            null, 
            null, 
            "spec", 
            null, 
            new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, "", Serialize.To.NOTHING, false), 
            java.util.Arrays.asList(
                    new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), 
                    new functionalj.types.struct.generator.Getter("date", new functionalj.types.Type("java.time", null, "LocalDate", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)
            ), 
            emptyList(), 
            java.util.Arrays.asList("Birthday")
        );
    
    @Test
    public void testFromMap() {
        val fromMap = StructMapGeneratorHelper.generateFromMap(spec);
        assertEquals("public static Birthday fromMap(Map<String, ? extends Object> map) {\n" + "    Map<String, Getter> $schema = getStructSchema();\n" + "    Birthday obj = new Birthday(\n" + "                (String)$utils.extractPropertyFromMap(Birthday.class, String.class, map, $schema, \"name\"),\n" + "                (LocalDate)$utils.extractPropertyFromMap(Birthday.class, LocalDate.class, map, $schema, \"date\")\n" + "            );\n" + "    return obj;\n" + "}", fromMap.toDefinition("nawa").lines().collect(Collectors.joining("\n")));
    }
}
