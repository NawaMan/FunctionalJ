// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Func.withIndex;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theList;
import static functionalj.lens.Access.theString;
import static functionalj.lens.LensTest.Car.theCar;
import static functionalj.lens.LensTest.Company.theCompany;
import static functionalj.lens.LensTest.Driver.theDriver;
import static functionalj.list.ImmutableList.listOf;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import functionalj.function.Func1;
import functionalj.lens.LensTest.Driver.DriverLens;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.ListLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import lombok.val;
import nullablej.nullable.Nullable;

public class LensTest {

    private void assertThis(boolean expected, Object actual) {
        assertEquals(expected, actual);
    }
    private void assertThis(String expected, Object actual) {
        if (expected == null)
             assertNull(actual);
        else assertEquals(expected, String.valueOf(actual).replaceAll(this.getClass().getSimpleName() + ".", ""));
    }
    
    @Test
    public void test() {
        var car1 = new Car("blue");
        var driver1 = new Driver(car1);
        
        assertThis("blue",             theCar.color.apply(car1));
        assertThis("Car(color=green)", theCar.color.changeTo("green").apply(car1));
        assertThis(false,              theCar.color.thatIsBlank().apply(car1));
        
        assertThis("blue",                         theDriver.car.color.apply(driver1));
        assertThis("Driver(car=Car(color=green))", theDriver.car.color.changeTo("green").apply(driver1));
        assertThis("Driver(car=Car(color=red))",   theDriver.car.withColor("red").apply(driver1));
        assertThis(false,                          theDriver.car.color.thatIsBlank().apply(driver1));
        
        var drivers = asList(
                driver1,
                driver1.withCar(new Car("red")),
                theDriver.car.color.changeTo("green").apply(driver1));
        var expected = asList(
                true,
                false,
                false);
        drivers.stream()
                .map(theDriver.car)
                .map(theCar.color.thatEquals("blue"))
                .forEach(withIndex((actual, index)->{
                    assertThis(expected.get(index), actual);
                }));
        
        var checkForCompanyWithBlueCar = theCompany.drivers.thatContains(theDriver.car.color.thatEquals("blue"));
        assertThis(false, checkForCompanyWithBlueCar.apply(new Company(asList())));
        assertThis(true,  checkForCompanyWithBlueCar.apply(new Company(asList(driver1))));
        assertThis(true,  checkForCompanyWithBlueCar.apply(new Company(asList(driver1, driver1.withCar(new Car("red"))))));
        
        var findCarColor = theDriver.car.toNullable().thenMap(theCar.color);
        assertThis("Nullable.EMPTY",    findCarColor.apply(new Driver(null)));
        assertThis("Nullable.of(blue)", findCarColor.apply(new Driver(new Car("blue"))));
        
        var company = new Company(asList(driver1, driver1.withCar(new Car("red"))));
         assertEquals("Driver(car=Car(color=blue))", theCompany.drivers.first().apply(company).toString());
    }

    
    public static interface CarSpec {
        public String color();
    }
    public static interface DriverSpec {
        public Car car();
    }
    
    public static class Car implements CarSpec {
        
        public static CarLens<Car> theCar = new CarLens<>(LensSpec.of(Car.class));
        
        private final String color;
        
        public Car(String color) {
            this.color = color;
        }
        
        public String color() {
            return color;
        }
        public Car withColor(String color) {
            return new Car(color);
        }
        public Car withColor(Supplier<String> color) {
            return new Car(color.get());
        }
        public Car withColor(Function<String,String> color) {
            return new Car(color.apply(this.color));
        }
        public Car withColor(BiFunction<Car, String,String> color) {
            return new Car(color.apply(this,this.color));
        }
        
        @Override
        public String toString() {
            return "Car(color=" + color + ")";
        }
        
        
        public static class CarLens<HOST> 
                        extends ObjectLensImpl<HOST, Car>
                        implements ConcreteAccess<HOST, Car, CarLens<HOST>>  {
            
            @Override
            public CarLens<HOST> newAccess(Function<HOST, Car> access) {
                return new CarLens<HOST>(LensSpec.of(access));
            }
            
            public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, spec->()->spec);
            
            public CarLens(LensSpec<HOST, Car> spec) { 
                super(spec);
            }
            
            public final Func1<HOST, HOST> withColor(String newColor) {
                return CarLens.this.color.changeTo(newColor);
            }
            
            public final CarLens<HOST> nullSafe() {
                return new CarLens<>(this.lensSpec().toNullSafe());
            }
            public final CarLens<HOST> nullAware() {
                return new CarLens<>(this.lensSpec().toNullSafe());
            }
        }
    }
    public static class Driver {
        
        public static DriverLens<Driver> theDriver = new DriverLens<>(LensSpec.of(Driver.class));
        
        private final Car car;
        
        public Driver(Car car) {
            this.car = car;
        }
        
        public Car car() {
            return car;
        }
        public Driver withCar(Car car) {
            return new Driver(car);
        }
        
        @Override
        public String toString() {
            return "Driver(car=" + car + ")";
        }
        
        
        public static class DriverLens<HOST> 
                extends 
                    ObjectLensImpl<HOST, Driver>
                implements
                    ConcreteAccess<HOST, Driver, DriverLens<HOST>> {
            
            @Override
            public DriverLens<HOST> newAccess(Function<HOST, Driver> access) {
                return new DriverLens<HOST>(LensSpec.of(access));
            }
            
            public final Car.CarLens<HOST> car = new Car.CarLens<>(this.lensSpec().then(LensSpec.of(Driver::car, Driver::withCar)));
            
            public DriverLens(LensSpec<HOST, Driver> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withCar(Car newCar) {
                return DriverLens.this.car.changeTo(newCar);
            }
            
            public final DriverLens<HOST> nullSafe() {
                return new DriverLens<>(this.lensSpec().toNullSafe());
            }
            public final DriverLens<HOST> nullUnsafe() {
                return new DriverLens<>(this.lensSpec().toNullUnsafe());
            }
        }
    }
    public static class Company {
        public static CompanyLens<Company> theCompany = new CompanyLens<>(LensSpec.of(Company.class));
        
        private final List<Driver> drivers;
        public Company(List<Driver> drivers)             { this.drivers = unmodifiableList(new ArrayList<>(drivers)); }
        public List<Driver> drivers()                    { return drivers; }
        public Company withDrivers(List<Driver> drivers) { return new Company(drivers); }
        
        @Override
        public String toString() {
            return "Company [drivers=" + drivers + "]";
        }
        
        
        public static class CompanyLens<HOST> extends ObjectLensImpl<HOST, Company>{
            
            // Will need a way to allow the collection lens to create the lens of the element.
            public final ListLens<HOST, Driver, Driver.DriverLens<HOST>> drivers
                    = createSubListLens(Company::drivers, Company::withDrivers, DriverLens::new);
            
            public CompanyLens(LensSpec<HOST, Company> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withDrivers(List<Driver> newDrivers) {
                return CompanyLens.this.drivers.changeTo(newDrivers);
            }
        }
    }
    
    // == Imposter ==
    
    @Test
    public void testNullSafety() {
        var driverWithNoCar     = new Driver(null);
        var nullSafeGetColor    = theDriver.car.color;
        var nullUnsafeGetDriver = theDriver.nullUnsafe();
        var nullUnsafeGetCar    = nullUnsafeGetDriver.car;
        var nullUnsafeGetColor  = nullUnsafeGetCar.color;
        var mayBeGetColor       = nullSafeGetColor.toNullable();
        
        assertNull(nullSafeGetColor.apply(driverWithNoCar));
        assertEquals(Nullable.empty(), mayBeGetColor.apply(driverWithNoCar));
        
        try {
            nullUnsafeGetColor.apply(driverWithNoCar);
            fail("Expect an NPE.");
        } catch (NullPointerException e) {
        }
    }
    
    public void testNullSafetyMethods() {
        var driverWithNoCar = new Driver(null);
        
        assertNull(theDriver.car.color.apply(driverWithNoCar));
        assertEquals("N/A", theDriver.car.color.orDefaultTo("N/A").apply(driverWithNoCar));
    }
    
    public void testShortHand() {
        var lists = listOf(
                    listOf("ONE", "TWO", "THREE"),
                    listOf("AE", "BEE", "SEE")
                );
        
        var theStrListLens = theList.of(LensTypes.STRING());
        assertEquals(
                "[[ONE (3), TWO, THREE], [AE (2), BEE, SEE]]",
                "" + lists
                        .map(theStrListLens
                                .first()
                                .changeTo(theString.concat(" (", $S.length(), ")"))));
        
        assertEquals(
                "[[ONE (3), TWO, THREE], [AE (2), BEE, SEE]]",
                "" + lists
                        .map(theStrListLens
                                .first()
                                .changeTo(theString.format("%s (%s)", $S, $S.length()))));
    }
    
}
