package functionalj.lens;

import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.functions.Func1;
import functionalj.lens.LensTest.Car;
import functionalj.lens.LensTest.CarSpec;
import functionalj.lens.LensTest.Driver;
import functionalj.lens.LensTest.Car.CarLens;
import functionalj.lens.LensTest.Driver.DriverLens;
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
        
        public Nullable<Car> findCar() {
            return Nullable.of(car);
        }
        
        @Override
        public String toString() {
            return "Driver(car=" + car + ")";
        }
        
        
        public static class DriverLens<HOST> extends ObjectLensImpl<HOST, Driver> {
            
            public final Car.CarLens<HOST> car = createSubLens(Driver::car, Driver::withCar, Car.CarLens::new);
//            
//            public final NullableAccess<HOST, Nullable<Car>, Car, Car.CarLens<HOST>> findCar
//                    = new NullableAccess<HOST, Nullable<Car>, NullableLensTest.Car, Car.CarLens<HOST>>() {
//                        public AccessWithSub<HOST, Nullable<Car>, Car, Car.CarLens<HOST>> lensSpecWithSub() {
//                            return new AccessWithSub<HOST, Nullable<Car>, Car, Car.CarLens<HOST>>() {
//                                @Override
//                                public Nullable<Car> apply(HOST host) {
//                                    DriverLens
//                                    return ; Func1.of(Driver::findCar).apply(host);
//                                }
//                                @Override
//                                public Car.CarLens<HOST> createSubAccess(
//                                        Func1<Nullable<Car>, Car> accessToSub) {
//                                    // TODO Auto-generated method stub
//                                    return null;
//                                }
//                            };
//                        }
//                    };
//            
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
    
    @Test
    public void test() {
        val L1 = new ObjectLensImpl<Driver, Car>(LensSpec.of(Driver::car, null));
        val L2 = new ObjectLensImpl<Car, String>(LensSpec.of(Car::color,  null));
    }
    
}
