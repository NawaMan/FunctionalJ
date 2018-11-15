package functionalj.annotations.dataobject;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.DataObject;
import lombok.val;

public class FromMethodTest {
    
    @DataObject(specField="spec")
    public void Car(String make, int year) { }
    
    @Test
    public void test() {
        val car = new Car("Toyota", 2008);
        assertEquals("Car[make: Toyota, year: 2008]", car.toString());
        assertEquals("[make:String, year:int]",
                Car.spec.getGetters()
                .stream()
                .map(g -> g.getName() + ":" + g.getType().simpleName())
                .collect(toList())
                .toString());
    }
    
}
