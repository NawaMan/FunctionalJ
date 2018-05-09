package functionalj.lens;

import static functionalj.compose.Functional.pipe;

import java.util.function.Function;

import org.junit.Test;

import functionalj.functions.Func1;
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
            
            public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, spec->()->spec);
            
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
        
        public Nullable<Car> findCar() {
            return Nullable.of(car);
        }
        
        @Override
        public String toString() {
            return "Driver(car=" + car + ")";
        }
        
        
        public static class DriverLens<HOST> extends ObjectLensImpl<HOST, Driver> {
            
            public final Car.CarLens<HOST> car = createSubLens(Driver::car, Driver::withCar, Car.CarLens::new);
            
            public final NullableAccess<HOST, Car, Car.CarLens<HOST>> findCar
                        = AnyAccess.createNullableAccess(DriverLens.this.car.then(Nullable::of), Car.CarLens::new);

            public DriverLens(LensSpec<HOST, Driver> spec)   { super(spec); }
            public DriverLens(Function<HOST, Driver> access) { super(LensSpec.of(access)); }
            
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
    
    @Test
    public void test() {
        val L1 = new ObjectLensImpl<Driver, Car>(LensSpec.of(Driver::car, null));
        val L2 = new ObjectLensImpl<Car, String>(LensSpec.of(Car::color,  null));
    }
    
}
