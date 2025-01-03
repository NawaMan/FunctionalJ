// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.typestests.struct;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import functionalj.types.Struct;
import lombok.val;

public class FromMapTest {
    
    @Struct
    void Birthday(String name, LocalDate date) {
    }
    
    @Test
    public void testToMap() {
        val car = new Car("Subaru", 2010, "Silver");
        val map = car.__toMap();
        assertEquals("{color=Silver, year=2010, make=Subaru}", map.toString());
        assertEquals("Subaru", map.get("make"));
        assertEquals(2010, map.get("year"));
        assertEquals("Silver", map.get("color"));
    }
    
    @Test
    public void testFromMap() {
        val car1 = new Car("Subaru", 2010, "Silver");
        val map = car1.__toMap();
        val car2 = Car.fromMap(map);
        assertEquals(car1, car2);
    }
    
    @Test
    public void testFromMapString() {
        val car1 = new Car("Subaru", 2010, "Silver");
        val map = car1.__toMap();
        map.put("year", "2010");
        val car2 = Car.fromMap(map);
        assertEquals(car1, car2);
        val bd1 = new Birthday("Name", LocalDate.of(2012, 5, 3));
        val bd1_map = bd1.__toMap();
        assertEquals("{date=2012-05-03, name=Name}", bd1_map.toString());
        // ISO dae can be used.
        bd1_map.put("date", "2012-05-03");
        val bd2 = Birthday.fromMap(bd1_map);
        assertEquals(bd1, bd2);
        // Unix time can be used.
        bd1_map.put("date", 1336066800);
        val bd3 = Birthday.fromMap(bd1_map);
        assertEquals(bd1, bd3);
    }
    
    @Test
    public void testReadCsv() {
        val csvString = "Mazda,2008,Black\nBMW,2010,Black\nToyota,2012,White";
        assertEquals(
                "[Car[make=Mazda, year=2008, color=Black], Car[make=BMW, year=2010, color=Black], Car[make=Toyota, year=2012, color=White]]",
                Stream.of(csvString.split("\n"))
                .map(line -> Arrays.asList(line.split(",")))
                .map(each -> {
                    val map = new TreeMap<String, String>();
                    map.put("make", each.get(0));
                    map.put("year", each.get(1));
                    map.put("color", each.get(2));
                    return map;
                })
                .map(each -> Car.fromMap(each))
                .collect(Collectors.toList()).toString());
    }
}
