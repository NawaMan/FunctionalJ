package functionalj.annotations.struct;

import static functionalj.annotations.DefaultValue.EMPTY;
import static functionalj.annotations.DefaultValue.NULL;
import static functionalj.annotations.DefaultValue.ZERO;
import static functionalj.annotations.struct.Car.theCar;
import static functionalj.annotations.struct.CarForSale.theCarForSale;
import static functionalj.lens.Access.$I;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.DefaultTo;
import functionalj.annotations.Nullable;
import functionalj.annotations.Struct;
import functionalj.list.FuncList;
import lombok.val;

public class FromMethodTest {
    
    @Struct(specField="spec")
    static boolean Car(String make, int year, @Nullable @DefaultTo(NULL) String color) { return true; }
    
    @Struct
    public void Inventory(
            @DefaultTo(EMPTY) FuncList<CarForSale> cars) {}
    
    @Struct(specField="spec")
    public void Price(int price, 
            @DefaultTo(ZERO) int discountPercent) { }
    
    @Struct(specField="spec")
    public void CarForSale(Car car, Price price) { }
    
    @Test
    public void tests() {
        val inventory    = new Inventory();
        val newInventory = inventory
                .withCars(list -> list
                    .append(new CarForSale(new Car("Subaru", 2010, "Silver"), new Price(20000)))
                    .append(new CarForSale(new Car("Mazda",  2008, "White"),  new Price(30000)))
                    .append(new CarForSale(new Car("WV",     2012, "Black"),  new Price(40000))));
        assertEquals("Inventory[cars: []]", "" + inventory);
        assertEquals("Inventory["
                + "cars: ["
                    + "CarForSale[car: Car[make: Subaru, year: 2010, color: Silver], price: Price[price: 20000, discountPercent: 0]], "
                    + "CarForSale[car: Car[make: Mazda, year: 2008, color: White], price: Price[price: 30000, discountPercent: 0]], "
                    + "CarForSale[car: Car[make: WV, year: 2012, color: Black], price: Price[price: 40000, discountPercent: 0]]"
                + "]]", newInventory.toString());
        
        assertEquals("90000",                   "" + newInventory.cars().sum    (theCarForSale.price.price));
        assertEquals("OptionalDouble[30000.0]", "" + newInventory.cars().average(theCarForSale.price.price));
        
        assertEquals(
                "{car={color=Silver, year=2010, make=Subaru}, price={discountPercent=0, price=20000}}",
                new CarForSale(new Car("Subaru", 2010, "Silver"), new Price(20000)).toMap().toString());
        
        val orgCfS = new CarForSale(new Car("Subaru", 2010, "Silver"), new Price(20000));
        val mapCfS = orgCfS.toMap();
        val newCfS = CarForSale.fromMap(mapCfS);
        assertEquals("{car={color=Silver, year=2010, make=Subaru}, price={discountPercent=0, price=20000}}", mapCfS.toString());
        assertEquals("CarForSale[car: Car[make: Subaru, year: 2010, color: Silver], price: Price[price: 20000, discountPercent: 0]]", orgCfS.toString());
        assertEquals("CarForSale[car: Car[make: Subaru, year: 2010, color: Silver], price: Price[price: 20000, discountPercent: 0]]", newCfS.toString());
        assertEquals(
                orgCfS.toString(),
                newCfS.toString());
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
