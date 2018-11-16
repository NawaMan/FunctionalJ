package functionalj.annotations.dataobject;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.DataObject;
import functionalj.annotations.Require;
import lombok.val;

public class FromMethodTest {
    
    @DataObject(specField="spec")
    public void Car(String make, int year, @Require(false) String color) { }
    
    @Test
    public void test() {
        val car = new Car("Toyota", 2008, "White");
        assertEquals("Car[make: Toyota, year: 2008, color: White]", car.toString());
        assertEquals("[make:String, year:int, color:String]",
                Car.spec.getGetters()
                .stream()
                .map(g -> g.getName() + ":" + g.getType().simpleName())
                .collect(toList())
                .toString());
        
        val car2 = new Car("Toyota", 2008);
        assertEquals("Car[make: Toyota, year: 2008, color: null]", car2.toString());
        
        val car3 = new Car.Builder()
                .make("BMW")
                .year(2010)
                .build();
        assertEquals("Car[make: BMW, year: 2010, color: null]", car3.toString());
        
        val car4 = new Car.Builder()
                .make("BMW")
                .year(2010)
                .color("Black")
                .build();
        assertEquals("Car[make: BMW, year: 2010, color: Black]", car4.toString());
    }
    
}
