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
package functionalj.lens;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;
import functionalj.lens.lenses.MapLens;
import functionalj.lens.lenses.StringLens;
import lombok.val;

public class MapLensTest {
    
    @Test
    public void testMapLens() {
        val mapLens = MapLens.<Map<String, String>, String, String, StringLens<Map<String, String>>, StringLens<Map<String, String>>>of(map -> map, (map, newMap) -> newMap, spec -> () -> spec, spec -> () -> spec);
        val map = new LinkedHashMap<String, String>();
        map.put("name1", "2");
        map.put("name2", "42");
        assertEquals("2", mapLens.get("name1").apply(map));
        assertEquals(null, mapLens.get("name").apply(map));
        assertEquals("0", mapLens.get("name").length().apply(map) + "");
        assertEquals("{name1=5, name2=42}", mapLens.get("name1").changeTo("5").apply(map) + "");
        assertEquals("{name1=2, name2=24}", mapLens.changeEach(theString.thatEndsWith("2"), __ -> "24").apply(map) + "");
    }
}
