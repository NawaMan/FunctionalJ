package nawaman.functionalj.lens;

import static java.util.Arrays.asList;
import static nawaman.functionalj.lens.TryRun.Car.theCar;
import static nawaman.functionalj.lens.TryRun.Company.theCompany;
import static nawaman.functionalj.lens.TryRun.Driver.theDriver;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

import lombok.ToString;
import lombok.val;
import nawaman.functionalj.functions.Func1;

@SuppressWarnings("javadoc")
public class TryRun {
    
    public static interface CarSpec {
        public String color();
    }
    public static interface DriverSpec {
        public Car car();
    }
    
    @ToString
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
        
        public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {
            
            public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, spec->()->spec);
            
            public CarLens(LensSpec<HOST, Car> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withColor(String newColor) {
                return CarLens.this.color.changeTo(newColor);
            }
        }
    }
    @ToString
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
        
        public static class DriverLens<HOST> extends ObjectLensImpl<HOST, Driver> {
            
            public final Car.CarLens<HOST> car = createSubLens(Driver::car, Driver::withCar, spec->new Car.CarLens<>(spec));
            
            public DriverLens(LensSpec<HOST, Driver> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withCar(Car newCar) {
                return DriverLens.this.car.changeTo(newCar);
            }
        }
    }
    @ToString
    public static class Company {
        public static CompanyLens<Company> theCompany = new CompanyLens<>(LensSpec.of(Company.class));
        
        private final List<Driver> drivers;
        public Company(List<Driver> drivers)             { this.drivers = unmodifiableList(new ArrayList<>(drivers)); }
        public List<Driver> drivers()                    { return drivers; }
        public Company withDrivers(List<Driver> drivers) { return new Company(drivers); }
        
        public static class CompanyLens<HOST> extends ObjectLensImpl<HOST, Company>{
            
            public final CollectionLens<HOST, Driver, List<Driver>> drivers = createSubLens(Company::drivers, Company::withDrivers, spec->()->spec);
            
            public CompanyLens(LensSpec<HOST, Company> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withDrivers(List<Driver> newDrivers) {
                return CompanyLens.this.drivers.changeTo(newDrivers);
            }
        }
    }
    
    public static void main(String[] args) {
        val car1 = new Car("blue");
        
        System.out.println(theCar.color.applyTo(car1));
        System.out.println(theCar.color.changeTo("green").applyTo(car1));
        System.out.println(theCar.color.thatIsBlank().applyTo(car1));
        System.out.println();
        
        val driver1 = new Driver(car1);
        System.out.println(theDriver.car.color.applyTo(driver1));
        System.out.println(theDriver.car.color.changeTo("green").applyTo(driver1));
        System.out.println(theDriver.car.withColor("red").applyTo(driver1));
        System.out.println(theDriver.car.color.thatIsBlank().applyTo(driver1));
        System.out.println();
        
        val drivers = asList(
                driver1,
                driver1.withCar(new Car("red")),
                theDriver.car.color.changeTo("green").apply(driver1));
        drivers.stream()
        .map(theDriver.car)
        .map(theCar.color.thatIs("blue"))
        .forEach(System.out::println);
        System.out.println();
        
        val check = theCompany.drivers.thatContains(theDriver.car.color.thatIsNot("blue"));
        System.out.println(check.applyTo(new Company(asList())));
        System.out.println(check.applyTo(new Company(asList(driver1))));
        System.out.println(check.applyTo(new Company(asList(driver1, driver1.withCar(new Car("red"))))));
        System.out.println();
        
        val findCarColor = theDriver.car.toMayBe().map(theCar.color);
        System.out.println(findCarColor.applyTo(new Driver(null)));
        System.out.println(findCarColor.applyTo(new Driver(new Car("blue"))));
        System.out.println();
    }
    
}
