package nawaman.functionalj.lens;

import static java.util.Arrays.asList;
import static nawaman.functionalj.lens.TryRun.Car.theCar;
import static nawaman.functionalj.lens.TryRun.Driver.theDriver;

import java.util.function.Function;

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
        
        public static class CarLens<HOST> 
                extends    ObjectLensImpl<HOST, Car> 
                implements Function<HOST, Car>, ObjectLens<HOST, Car> {
            
            public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, spec->()->spec);
            
            public CarLens(LensSpec<HOST, Car> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withColor(String newColor) {
                return CarLens.this.color.to(newColor);
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
        
        public static class DriverLens<HOST>
                extends    ObjectLensImpl<HOST, Driver>
                implements ObjectLens<HOST, Driver> {
            
            public final Car.CarLens<HOST> car = createSubLens(Driver::car, Driver::withCar, spec->new Car.CarLens<>(spec));
            
            public DriverLens(LensSpec<HOST, Driver> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withCa(Car newCar) {
                return DriverLens.this.car.to(newCar);
            }
        }
    }
    @ToString
    public static class Company {
        private Driver driver;
        public Company(Driver driver)            { this.driver = driver; }
        public Driver  driver()                  { return driver; }
        public Company withDriver(Driver driver) { return new Company(driver); }
    }
    
    public static void main(String[] args) {
        val car1 = new Car("blue");
        
        System.out.println(theCar.color.applyTo(car1));
        System.out.println(theCar.color.to("green").applyTo(car1));
        System.out.println(theCar.color.isBlank().applyTo(car1));
        System.out.println();
        
        val driver1 = new Driver(car1);
        System.out.println(theDriver.car.color.applyTo(driver1));
        System.out.println(theDriver.car.color.to("green").applyTo(driver1));
        System.out.println(theDriver.car.withColor("red").applyTo(driver1));
        System.out.println(theDriver.car.color.isBlank().applyTo(driver1));
        System.out.println();
        
        val drivers = asList(
                driver1,
                driver1.withCar(new Car("red")),
                theDriver.car.color.to("green").apply(driver1));
        drivers.stream()
        .map(theDriver.car)
        .map(theCar.color.is("blue"))
        .forEach(System.out::println);
    }
    
}
