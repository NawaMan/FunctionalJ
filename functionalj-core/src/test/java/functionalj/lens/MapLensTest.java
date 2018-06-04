package functionalj.lens;

import static functionalj.lens.Accesses.theString;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import lombok.val;

public class MapLensTest {
    
    @Test
    public void testMapLens() {
        val mapLens = MapLens.<Map<String, String>, String, String, StringLens<Map<String,String>>, StringLens<Map<String,String>>>
                of(
                    map -> map,
                    (map, newMap) -> newMap,
                    spec->()->spec,
                    spec->()->spec);
        
        
        val map = new LinkedHashMap<String, String>();
        map.put("name1", "2");
        map.put("name2", "42");
        
        assertEquals("2",  mapLens.get("name1")        .apply(map));
        assertEquals(null, mapLens.get("name")         .apply(map));
        assertEquals("0",  mapLens.get("name").length().apply(map) + "");
        
        assertEquals("{name1=5, name2=42}", mapLens.get("name1").changeTo("5").apply(map) + "");
        
        assertEquals("{name1=2, name2=24}", mapLens.changeTo(theString.thatEndsWith("2"), __->"24").apply(map) + "");
    }
    
}
