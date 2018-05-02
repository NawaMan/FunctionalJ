package functionalj.lens;

import static functionalj.lens.Access.theItem;
import static functionalj.lens.Access.theString;
import static functionalj.lens.LensTest.Car.theCar;
import static functionalj.lens.LensTest.Company.theCompany;
import static functionalj.lens.LensTest.Driver.theDriver;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import static java.util.Collections.unmodifiableList;

import functionalj.FunctionalJ;
import functionalj.functions.Func1;
import functionalj.lens.LensTest.Driver.DriverLens;
import functionalj.types.MayBe;
import lombok.val;

@SuppressWarnings("javadoc")
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
        val car1 = new Car("blue");
        val driver1 = new Driver(car1);
        
        assertThis("blue",             theCar.color.applyTo(car1));
        assertThis("Car(color=green)", theCar.color.changeTo("green").applyTo(car1));
        assertThis(false,              theCar.color.thatIsBlank().applyTo(car1));
        
        assertThis("blue",                         theDriver.car.color.applyTo(driver1));
        assertThis("Driver(car=Car(color=green))", theDriver.car.color.changeTo("green").applyTo(driver1));
        assertThis("Driver(car=Car(color=red))",   theDriver.car.withColor("red").applyTo(driver1));
        assertThis(false,                          theDriver.car.color.thatIsBlank().applyTo(driver1));
        
        val drivers = asList(
                driver1,
                driver1.withCar(new Car("red")),
                theDriver.car.color.changeTo("green").apply(driver1));
        val expected = asList(
                true,
                false,
                false);
        drivers.stream()
                .map(theDriver.car)
                .map(theCar.color.thatEquals("blue"))
                .forEach(FunctionalJ.withIndex((actual, index)->{
                    assertThis(expected.get(index), actual);
                }));
        
        val checkForCompanyWithBlueCar = theCompany.drivers.thatContains(theDriver.car.color.thatEquals("blue"));
        assertThis(false, checkForCompanyWithBlueCar.applyTo(new Company(asList())));
        assertThis(true,  checkForCompanyWithBlueCar.applyTo(new Company(asList(driver1))));
        assertThis(true,  checkForCompanyWithBlueCar.applyTo(new Company(asList(driver1, driver1.withCar(new Car("red"))))));
        
        val findCarColor = theDriver.car.toMayBe().map(theCar.color);
        assertThis("Nothing",    findCarColor.applyTo(new Driver(null)));
        assertThis("Just(blue)", findCarColor.applyTo(new Driver(new Car("blue"))));
        
        val company = new Company(asList(driver1, driver1.withCar(new Car("red"))));
         assertEquals("Driver(car=Car(color=blue))", theCompany.drivers.first().get().apply(company).toString());
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
        
        @Override
        public String toString() {
            return "Car(color=" + color + ")";
        }
        
        
        public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {
            
            public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, spec->()->spec);
            
            public CarLens(LensSpec<HOST, Car> spec) { super(spec); }
            
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
        
        
        public static class DriverLens<HOST> extends ObjectLensImpl<HOST, Driver> {
            
            public final Car.CarLens<HOST> car = createSubLens(Driver::car, Driver::withCar, spec->new Car.CarLens<>(spec));
            
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
            public final ListLens<HOST, List<Driver>, Driver, Driver.DriverLens<HOST>> drivers
                    = createSubListLens(Company::drivers, Company::withDrivers, DriverLens::new);
            
            public CompanyLens(LensSpec<HOST, Company> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withDrivers(List<Driver> newDrivers) {
                return CompanyLens.this.drivers.changeTo(newDrivers);
            }
        }
    }
    
    // == Imposter ==
    
    public static interface IAmMe<T extends Func1<String, Integer>> extends Func1<String, Integer> {
        T me();
        
        default Integer apply(String input) {
            return me().apply(input);
        }
    }
    
    @Test
    public void testNullSafety() {
        val driverWithNoCar    = new Driver(null);
        val nullSafeGetColor   = theDriver.car.color;
        val nullUnsafeGetColor = theDriver.nullUnsafe().car.color;
        val mayBeGetColor      = nullSafeGetColor.toMayBe();
        
        assertNull(nullSafeGetColor.applyTo(driverWithNoCar));
        assertEquals(MayBe.nothing(), mayBeGetColor.applyTo(driverWithNoCar));
        
        try {
            nullUnsafeGetColor.apply(driverWithNoCar);
            fail("Expect an NPE.");
        } catch (NullPointerException e) {
        }
    }
    
    public class WithNames {
        
        private List<String> names = new ArrayList<>();
        
        public WithNames(List<String> names) {
            this.names.addAll(names);
        }
        
        public List<String> names() {
            return names;
        }
        
        public WithNames withNames(List<String> newNames) {
            return new WithNames(newNames);
        }
    }
    
    @Test
    public void testListAccess() {
        val accSub = new AccessWithSub<WithNames, List<String>, String, StringAccess<WithNames>>() {
            @Override
            public List<String> apply(WithNames input) {
                return input.names();
            }
            @Override
            public StringAccess<WithNames> createSubAccess(Func1<List<String>, String> accessToSub) {
                return withNames -> accessToSub.apply(this.apply(withNames));
            }
        };
        val listAcc = new ListAccess<WithNames, List<String>, String, StringAccess<WithNames>>() {
            @Override
            public AccessWithSub<WithNames, List<String>, String, StringAccess<WithNames>> lensSpecWithSub() {
                return accSub;
            }
        };
        
        assertEquals("One", listAcc.first().get().apply(new WithNames(asList("One", "Two"))));
        assertEquals("One", Optional.of(new WithNames(asList("One", "Two")))
                .map(listAcc.first().get())
                .get());
        assertEquals("[One, Two]", Optional.of(new WithNames(asList("One", "Two", "Three", "Four")))
                .map(listAcc.filter(theString.length().thatEquals(3)))
                .map(theItem().convertToString())
                .get());
        assertEquals("ONE", Optional.of(new WithNames(asList("One", "Two")))
                .map(listAcc.first().get())
                .map(theString.toUpperCase())
                .get());
    }
    
    public class WithCars {

        private List<Car> cars = new ArrayList<>();
        
        public WithCars(List<Car> cars) {
            this.cars.addAll(cars);
        }
        
        public List<Car> cars() {
            return cars;
        }
        
        public WithCars withCars(List<Car> newCars) {
            return new WithCars(newCars);
        }
        
        @Override
        public String toString() {
            return "WithCars [cars=" + cars + "]";
        }
        
    }
    
    @Test
    public void testListLens() {
        val listLens = ListLens.createListLens(WithCars::cars, WithCars::withCars, subSpec -> new Car.CarLens<>(subSpec));
        
        val withCars = new WithCars(asList(new Car("Blue")));
        assertEquals("WithCars [cars=[Car(color=Blue)]]",  withCars.toString());
        assertEquals("Car(color=Blue)",                    listLens.first().get().apply(withCars).toString());
//        assertEquals("WithCars [cars=[Car(color=Green)]]", listLens.first().withColor("Green").apply(withCars).toString());
        
        assertTrue(listLens.first().isPresent().apply(withCars));
        assertEquals("Car(color=Blue)", listLens.first().get().convertToString().apply(withCars));
    }
    
}
