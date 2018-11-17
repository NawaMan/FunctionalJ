package functionalj.annotations.dataobject;

import static functionalj.annotations.dataobject.Car.theCar;
import static functionalj.annotations.dataobject.CarForSale.theCarForSale;
import static functionalj.lens.Access.$I;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.DataObject;
import functionalj.annotations.Require;
import functionalj.list.FuncList;
import lombok.val;

public class FromMethodTest {
    
    @DataObject(specField="spec")
    public void Car(String make, int year, @Require(false) String color) { }
    
    @DataObject
    public void Inventory(
            @Require(false) FuncList<CarForSale> cars) {}
    
    @DataObject(specField="spec")
    public void Price(int price, @Require(false) int discountPercent) { }
    
    @DataObject(specField="spec")
    public void CarForSale(Car car, Price price) { }
    
    @Test
    public void tests() {
        val inventory    = new Inventory();
        val newInventory = inventory
                .withCars(list -> list
                    .append(new CarForSale(new Car("Subaru", 2010, "Silver"), new Price(20000)))
                    .append(new CarForSale(new Car("Mazda",  2008, "White"),  new Price(30000)))
                    .append(new CarForSale(new Car("WV",     2012, "Black"),  new Price(40000))));
        System.out.println(inventory);
        System.out.println(newInventory);
        
        System.out.println(newInventory.cars().sum    (theCarForSale.price.price));
        System.out.println(newInventory.cars().average(theCarForSale.price.price));
        
        
//        
//        System.out.println(theCars.cars.at(1).apply(cars));
//        System.out.println(theCars.cars.at(1).apply(newCars));
    }
    
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
        
        val cars = FuncList.of(car4);
        assertEquals("Car[make: BMW, year: 2009, color: Black]",  theCar.year.changeTo($I.subtract(1)).apply(car4).toString());
        assertEquals("Car[make: Benz, year: 2010, color: Black]", theCar.make.changeTo("Benz").apply(car4).toString());
        assertEquals("[Car[make: BMW, year: 2009, color: Black]]", "" + cars.mapOnly(theCar.year.thatEquals(2010), theCar.year.changeTo($I.subtract(1))));
    }
    
}
