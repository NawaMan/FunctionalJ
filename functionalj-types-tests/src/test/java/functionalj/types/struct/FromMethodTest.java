// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.struct;

import static functionalj.lens.Access.$I;
import static functionalj.types.DefaultValue.EMPTY;
import static functionalj.types.DefaultValue.NULL;
import static functionalj.types.DefaultValue.ZERO;
import static functionalj.types.struct.Car.theCar;
import static functionalj.types.struct.CarForSale.theCarForSale;
//import static functionalj.types.struct.CarForSale.theCarForSale;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.types.DefaultTo;
import functionalj.types.Nullable;
import functionalj.types.Struct;
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
        
        assertEquals("90000",                   "" + newInventory.cars().mapToInt(theCarForSale.price.price).sum());
        assertEquals("OptionalDouble[30000.0]", "" + newInventory.cars().mapToInt(theCarForSale.price.price).average());
        
        assertEquals(
                "{car={color=Silver, year=2010, make=Subaru}, price={discountPercent=0, price=20000}}",
                new CarForSale(new Car("Subaru", 2010, "Silver"), new Price(20000)).__toMap().toString());
        
        val orgCfS = new CarForSale(new Car("Subaru", 2010, "Silver"), new Price(20000));
        val mapCfS = orgCfS.__toMap();
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
