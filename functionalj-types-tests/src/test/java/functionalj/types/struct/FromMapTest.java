package functionalj.types.struct;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import functionalj.types.Struct;
import lombok.val;

public class FromMapTest {
    
    @Struct
    static void Birthday(String name, LocalDate date) {}
    
    @Test
    public void testToMap() {
        val car = new Car("Subaru", 2010, "Silver");
        val map = car.__toMap();
        assertEquals("{color=Silver, year=2010, make=Subaru}", map.toString());
        assertEquals("Subaru", map.get("make"));
        assertEquals(2010,     map.get("year"));
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
        
        val bd1     = new Birthday("Name", LocalDate.of(2012, 5, 3));
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
    
}
