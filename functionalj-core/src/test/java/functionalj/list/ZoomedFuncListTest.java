package functionalj.list;

import static org.junit.Assert.assertEquals;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import lombok.val;

public class ZoomedFuncListTest {
    
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
        
        public Car withColor(Function<String, String> color) {
            return new Car(color.apply(this.color));
        }
        
        public Car withColor(BiFunction<Car, String, String> color) {
            return new Car(color.apply(this, this.color));
        }
        
        @Override
        public String toString() {
            return "Car(color=" + color + ")";
        }
        
        public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> implements ConcreteAccess<HOST, Car, CarLens<HOST>> {
            
            @Override
            public CarLens<HOST> newAccess(Function<HOST, Car> access) {
                return new CarLens<HOST>(LensSpec.of(access));
            }
            
            public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, spec -> () -> spec);
            
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
        
        public static class DriverLens<HOST> extends ObjectLensImpl<HOST, Driver> implements ConcreteAccess<HOST, Driver, DriverLens<HOST>> {
        
            @Override
            public DriverLens<HOST> newAccess(Function<HOST, Driver> access) {
                return new DriverLens<HOST>(LensSpec.of(access));
            }
            
            public final Car.CarLens<HOST> car = new Car.CarLens<>(this.lensSpec().then(LensSpec.of(Driver::car, Driver::withCar)));
            
            public DriverLens(LensSpec<HOST, Driver> spec) {
                super(spec);
            }
            
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
    
    public static class DriverBoss {
        
        public static DriverBossLens<DriverBoss> theDriverBoss = new DriverBossLens<>(LensSpec.of(DriverBoss.class));
        
        private final Driver driver;
        
        public DriverBoss(Driver driver) {
            this.driver = driver;
        }
        
        public Driver driver() {
            return driver;
        }
        
        public DriverBoss withDriver(Driver driver) {
            return new DriverBoss(driver);
        }
        
        @Override
        public String toString() {
            return "DriverBoss(driver=" + driver + ")";
        }
        
        public static class DriverBossLens<HOST> extends ObjectLensImpl<HOST, DriverBoss> implements ConcreteAccess<HOST, DriverBoss, DriverBossLens<HOST>> {
            
            @Override
            public DriverBossLens<HOST> newAccess(Function<HOST, DriverBoss> access) {
                return new DriverBossLens<HOST>(LensSpec.of(access));
            }
            
            public final Driver.DriverLens<HOST> driver = new Driver.DriverLens<>(this.lensSpec().then(LensSpec.of(DriverBoss::driver, DriverBoss::withDriver)));
            
            public DriverBossLens(LensSpec<HOST, DriverBoss> spec) {
                super(spec);
            }
            
            public final Func1<HOST, HOST> withDriver(Driver newDriver) {
                return DriverBossLens.this.driver.changeTo(newDriver);
            }
            
            public final DriverBossLens<HOST> nullSafe() {
                return new DriverBossLens<>(this.lensSpec().toNullSafe());
            }
            
            public final DriverBossLens<HOST> nullUnsafe() {
                return new DriverBossLens<>(this.lensSpec().toNullUnsafe());
            }
        }
    }
    
    
    @Test
    public void testZoomBasic() {
        val car1 = new Car("blue");
        val car2 = new Car("red");
        val car3 = new Car("green");
        val driver1 = new Driver(car1);
        val driver2 = new Driver(car2);
        val driver3 = new Driver(car3);
        
        FuncList<DriverBoss> bosses = (FuncList<DriverBoss>)FuncList.of(new DriverBoss(driver1), new DriverBoss(driver2), new DriverBoss(driver3));
        val bossDrivers      = new ZoomFuncList<Driver, DriverBoss, FuncList<DriverBoss>>(bosses, DriverBoss.theDriverBoss.driver);
        val driverCars       = new ZoomZoomFuncList<>(bossDrivers, Driver.theDriver.car);
        val driverCarColors  = new ZoomZoomFuncList<>(driverCars, Car.theCar.color);
        val carsWithNewColor = driverCarColors.map(String::toUpperCase);
        
        assertEquals("[BLUE, RED, GREEN]",                      carsWithNewColor.toListString());
        assertEquals("class functionalj.list.ZoomZoomFuncList", carsWithNewColor.getClass().toString());
        
        assertEquals("[Car(color=BLUE), Car(color=RED), Car(color=GREEN)]", carsWithNewColor.zoomOut().toListString());
        assertEquals("class functionalj.list.ZoomZoomFuncList",             carsWithNewColor.zoomOut().getClass().toString());
        
        assertEquals("[Driver(car=Car(color=BLUE)), Driver(car=Car(color=RED)), Driver(car=Car(color=GREEN))]",
                     carsWithNewColor.zoomOut().zoomOut().toListString());
        assertEquals("class functionalj.list.ZoomFuncList",
                     carsWithNewColor.zoomOut().zoomOut().getClass().toString());
        
        
        val drivers = carsWithNewColor.zoomOut().zoomOut();
        assertEquals("[Driver(car=Car(color=BLUE)), Driver(car=Car(color=RED)), Driver(car=Car(color=GREEN))]",
                     drivers.toListString());
        assertEquals("class functionalj.list.ZoomFuncList",
                     drivers.getClass().toString());
        
        val zoomOut = drivers.zoomOut();
        assertEquals("[DriverBoss(driver=Driver(car=Car(color=BLUE))), DriverBoss(driver=Driver(car=Car(color=RED))), DriverBoss(driver=Driver(car=Car(color=GREEN)))]",
                     zoomOut.toString());
        assertEquals("[DriverBoss(driver=Driver(car=Car(color=BLUE))), DriverBoss(driver=Driver(car=Car(color=RED))), DriverBoss(driver=Driver(car=Car(color=GREEN)))]",
                     zoomOut.toListString());
        
        
        assertEquals("[DriverBoss(driver=Driver(car=Car(color=BLUE))), DriverBoss(driver=Driver(car=Car(color=RED))), DriverBoss(driver=Driver(car=Car(color=GREEN)))]", 
                bosses
                .zoomIn(DriverBoss.theDriverBoss.driver)
                .zoomIn(Driver.theDriver.car)
                .zoomIn(Car.theCar.color)
                .map(String::toUpperCase)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
        
        assertEquals("[BLUE, RED, GREEN]",
                     bosses
                     .zoomIn(DriverBoss.theDriverBoss.driver.car.color)
                     .map(String::toUpperCase)
                     .toListString());
        assertEquals("[DriverBoss(driver=Driver(car=Car(color=BLUE))), DriverBoss(driver=Driver(car=Car(color=RED))), DriverBoss(driver=Driver(car=Car(color=GREEN)))]",
                     bosses
                     .zoomIn(DriverBoss.theDriverBoss.driver.car.color)
                     .map(String::toUpperCase)
                     .zoomOut()
                     .toListString());
    }
    
}
