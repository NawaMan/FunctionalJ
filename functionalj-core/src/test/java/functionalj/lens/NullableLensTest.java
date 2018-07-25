package functionalj.lens;

import static functionalj.lens.NullableLensTest.Driver.theDriver;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.function.Function;

import org.junit.Test;

import functionalj.functions.Func1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensUtils;
import functionalj.lens.lenses.NullableLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class NullableLensTest {

    public static interface CarSpec {
        public String color();
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
            
            public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, StringLens::of);
            
            public CarLens(LensSpec<HOST, Car> spec)   { super(spec); }
            public CarLens(Function<HOST, Car> access) { super(LensSpec.of(access)); }
            
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

        private final Car           firstCar;
        private final Nullable<Car> secondCar;
        
        public Driver(Car firstCar, Nullable<Car> secondCar) {
            this.firstCar  = firstCar;
            this.secondCar = secondCar;
        }
        
        public Car firstCar() {
            return firstCar;
        }
        public Driver withFirstCar(Car car) {
            return new Driver(car, secondCar);
        }
        
        public Nullable<Car> secondCar() {
            return secondCar;
        }
        public Driver withSecondCar(Nullable<Car> secondCar) {
            return new Driver(firstCar, secondCar);
        }
        
        @Override
        public String toString() {
            return "Driver(firstCar=" + firstCar + ",secondCar=" + secondCar + ")";
        }
        
        
        public static class DriverLens<HOST> extends ObjectLensImpl<HOST, Driver> {

            public final Car.CarLens<HOST> firstCar = new Car.CarLens<>(this.lensSpec().then(LensSpec.of(Driver::firstCar, Driver::withFirstCar)));
            
            public final NullableLens<HOST, Car, Car.CarLens<HOST>> secondCar
                        = LensUtils.createNullableLens(
                                this.lensSpec().then(LensSpec.of(Driver::secondCar, Driver::withSecondCar)),
                                Car.CarLens::new);
            
            public DriverLens(LensSpec<HOST, Driver> spec)   { super(spec); }
            public DriverLens(Function<HOST, Driver> access) { super(LensSpec.of(access)); }

            public final DriverLens<HOST> nullSafe() {
                return new DriverLens<>(this.lensSpec().toNullSafe());
            }
            public final DriverLens<HOST> nullUnsafe() {
                return new DriverLens<>(this.lensSpec().toNullUnsafe());
            }
        }
    }
    
    @Test
    public void test() {
        Driver blueDriver = new Driver(new Car("blue"), Nullable.of(new Car("blue")));
        Driver nullDriver = new Driver(null,            Nullable.empty());
        
        assertEquals("Driver(firstCar=Car(color=blue),secondCar=Nullable.of(Car(color=blue)))", blueDriver.toString());
        assertEquals("Driver(firstCar=null,secondCar=Nullable.EMPTY)",                          nullDriver.toString());
        
        val drivers = Arrays.asList(blueDriver, nullDriver);
        assertEquals(
                   "[" 
                +      "Driver(firstCar=Car(color=green),secondCar=Nullable.of(Car(color=green))), "
                +      "Driver(firstCar=null,secondCar=Nullable.EMPTY)"
                +  "]",
                drivers.stream()
                .map(theDriver.firstCar       .withColor("green"))
                .map(theDriver.secondCar.get().withColor("green"))
                .collect(toList()).toString());
    }
    
}
