package functionalj.list;

import static functionalj.lens.Access.theString;
import static functionalj.list.FuncList.ListOf;
import static functionalj.list.ZoomFuncListTest.Car.theCar;
import static functionalj.list.ZoomFuncListTest.Driver.theDriver;
import static functionalj.list.ZoomFuncListTest.DriverBoss.theDriverBoss;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.junit.Test;

import functionalj.function.Func1;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.collect.CollectorToBooleanPlus;
import lombok.val;

public class ZoomFuncListTest {
    
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
    
    
    private final Car car1 = new Car("blue");
    private final Car car2 = new Car("red");
    private final Car car3 = new Car("green");
    private final Driver driver1 = new Driver(car1);
    private final Driver driver2 = new Driver(car2);
    private final Driver driver3 = new Driver(car3);
    
    private final FuncList<Car>        cars    = ListOf(car1, car2, car3);
    private final FuncList<Driver>     drivers = ListOf(driver1, driver2, driver3);
    private final FuncList<DriverBoss> bosses  = (FuncList<DriverBoss>)FuncList.of(new DriverBoss(driver1), new DriverBoss(driver2), new DriverBoss(driver3));
    
    static class CollectFirst extends Aggregation<String, String> {
        private CollectorPlus<String, StringBuffer, String> collectorPlus = new CollectorPlus<String, StringBuffer, String>() {
            public Supplier<StringBuffer> supplier() {
                return () -> new StringBuffer();
            }
            public BiConsumer<StringBuffer, String> accumulator() {
                return (sb, s) -> sb.append(s.charAt(0));
            }
            public BinaryOperator<StringBuffer> combiner() {
                return (sb1, sb2) -> new StringBuffer().append(sb1).append(sb2);
            }
            
            public Function<StringBuffer, String> finisher() {
                return sb -> sb.toString();
            }
            @Override
            public Collector<String, StringBuffer, String> collector() {
                return this;
            }
        };
        
        public CollectorPlus<String, ?, String> collectorPlus() {
            return collectorPlus;
        }
    }
    
    static class CollectToList extends Aggregation<String, FuncList<String>> {
        private CollectorPlus<String, ArrayList<String>, FuncList<String>> collectorPlus = new CollectorPlus<String, ArrayList<String>, FuncList<String>>() {
            public Supplier<ArrayList<String>> supplier() {
                return () -> new ArrayList<String>();
            }
            public BiConsumer<ArrayList<String>, String> accumulator() {
                return (sb, s) -> sb.add("" + s.charAt(0));
            }
            public BinaryOperator<ArrayList<String>> combiner() {
                return (sb1, sb2) -> {
                    val list = new ArrayList<String>();
                    list.addAll(sb1);
                    list.addAll(sb2);
                    return list;
                };
            }
            
            public Function<ArrayList<String>, FuncList<String>> finisher() {
                return sb -> FuncList.from(sb);
            }
            @Override
            public Collector<String, ArrayList<String>, FuncList<String>> collector() {
                return this;
            }
        };
        
        public CollectorPlus<String, ?, FuncList<String>> collectorPlus() {
            return collectorPlus;
        }
    }
    
    static class Alternative extends AggregationToBoolean<String> {
        private CollectorToBooleanPlus<String, AtomicBoolean> collector = new CollectorToBooleanPlus<String, AtomicBoolean>() {
            @Override
            public Collector<String, AtomicBoolean, Boolean> collector() {
                return this;
            }
            @Override
            public Supplier<AtomicBoolean> supplier() {
                return () -> new AtomicBoolean();
            }
            @Override
            public BiConsumer<AtomicBoolean, String> accumulator() {
                return (a, s) -> a.set(!a.get());
            }
            @Override
            public BinaryOperator<AtomicBoolean> combiner() {
                return (a, b) -> new AtomicBoolean(a.get() != b.get());
            }
            @Override
            public Predicate<AtomicBoolean> finisherToBoolean() {
                return a -> a.get();
            }
            @Override
            public Set<Characteristics> characteristics() {
                return Collections.emptySet();
            }
        };
        @Override
        public CollectorToBooleanPlus<String, ?> collectorToBooleanPlus() {
            return collector;
        }
    }
    
    @Test
    public void testZoom() {
        val carColors = cars.zoomIn(theCar.color);
        assertEquals(
                "[BLUE, RED, GREEN]",
                carColors.map(String::toUpperCase).toListString());
        assertEquals(
                "[Car(color=BLUE), Car(color=RED), Car(color=GREEN)]",
                carColors.map(String::toUpperCase).zoomOut().toListString());
    }
    
    @Test
    public void testZoom2() {
        val carColors = drivers.zoomIn(theDriver.car);
        assertEquals(
                "["
                + "Car(color=BLUE), "
                + "Car(color=RED), "
                + "Car(color=GREEN)"
                + "]",
                carColors.map(car -> new Car(car.color.toUpperCase())).toListString());
        assertEquals(
                "["
                + "Driver(car=Car(color=BLUE)), "
                + "Driver(car=Car(color=RED)), "
                + "Driver(car=Car(color=GREEN))"
                + "]",
                carColors.map(car -> new Car(car.color.toUpperCase())).zoomOut().toListString());
    }
    
    @Test
    public void testZoomZoom() {
        val bossDrivers      = new ZoomFuncList<Driver, DriverBoss, FuncList<DriverBoss>>(bosses, theDriverBoss.driver);
        val driverCars       = new ZoomZoomFuncList<>(bossDrivers, theDriver.car);
        val driverCarColors  = new ZoomZoomFuncList<>(driverCars, theCar.color);
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
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .map(String::toUpperCase)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
        
        assertEquals("[BLUE, RED, GREEN]",
                     bosses
                     .zoomIn(theDriverBoss.driver.car.color)
                     .map(String::toUpperCase)
                     .toListString());
        assertEquals("[DriverBoss(driver=Driver(car=Car(color=BLUE))), DriverBoss(driver=Driver(car=Car(color=RED))), DriverBoss(driver=Driver(car=Car(color=GREEN)))]",
                     bosses
                     .zoomIn(theDriverBoss.driver.car.color)
                     .map(String::toUpperCase)
                     .zoomOut()
                     .toListString());
    }
    
    @Test
    public void testFilter() {
        assertEquals("[DriverBoss(driver=Driver(car=Car(color=blue))), DriverBoss(driver=Driver(car=Car(color=green)))]", 
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .filter(theString.thatNotEquals("red"))
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testFilter_aggregate() {
        val alternative = new Alternative();
        
        assertEquals(
                "[Car(color=blue), Car(color=green)]",
                cars.zoomIn(theCar.color).filter(alternative).zoomOut().toListString());
        
        assertEquals("[DriverBoss(driver=Driver(car=Car(color=blue))), DriverBoss(driver=Driver(car=Car(color=green)))]", 
                bosses
                .zoomIn(DriverBoss.theDriverBoss.driver)
                .zoomIn(Driver.theDriver.car)
                .zoomIn(theCar.color)
                .filter(alternative)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testMap_function() {
        assertEquals(
                "[Car(color=BLUE), Car(color=RED), Car(color=GREEN)]",
                cars.zoomIn(theCar.color).map(String::toUpperCase).zoomOut().toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=BLUE))), "
                + "DriverBoss(driver=Driver(car=Car(color=RED))), "
                + "DriverBoss(driver=Driver(car=Car(color=GREEN)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .map(String::toUpperCase)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testMap_aggregator() {
        val collectFirst = new CollectFirst();
        assertEquals(
                "[Car(color=b), Car(color=br), Car(color=brg)]",
                cars.zoomIn(theCar.color).map(collectFirst).zoomOut().toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=b))), "
                + "DriverBoss(driver=Driver(car=Car(color=br))), "
                + "DriverBoss(driver=Driver(car=Car(color=brg)))"
                + "]",
                bosses
                .zoomIn(DriverBoss.theDriverBoss.driver)
                .zoomIn(Driver.theDriver.car)
                .zoomIn(theCar.color)
                .map(collectFirst)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testFlatMap() {
        val result = bosses
        .zoomIn(DriverBoss.theDriverBoss.driver)
        .zoomIn(Driver.theDriver.car)
        .zoomIn(theCar.color)
        .flatMap(color -> FuncList.cycle(color).limit(color.length()))
        .zoomOut()
        .zoomOut()
        .zoomOut();
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=red))), "
                + "DriverBoss(driver=Driver(car=Car(color=red))), "
                + "DriverBoss(driver=Driver(car=Car(color=red))), "
                + "DriverBoss(driver=Driver(car=Car(color=green))), "
                + "DriverBoss(driver=Driver(car=Car(color=green))), "
                + "DriverBoss(driver=Driver(car=Car(color=green))), "
                + "DriverBoss(driver=Driver(car=Car(color=green))), "
                + "DriverBoss(driver=Driver(car=Car(color=green)))]",
                result.toListString());
    }
    
    @Test
    public void testFlatMap_aggregator() {
        // [b]
        // [b, r]
        // [b, r, g]
        
        val collectToList = new CollectToList();
        assertEquals(
                "[Car(color=b), Car(color=b), Car(color=r), Car(color=b), Car(color=r), Car(color=g)]",
                cars.zoomIn(theCar.color).flatMap(collectToList).zoomOut().toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=b))), "
                + "DriverBoss(driver=Driver(car=Car(color=b))), "
                + "DriverBoss(driver=Driver(car=Car(color=r))), "
                + "DriverBoss(driver=Driver(car=Car(color=b))), "
                + "DriverBoss(driver=Driver(car=Car(color=r))), "
                + "DriverBoss(driver=Driver(car=Car(color=g)))"
                + "]",
                bosses
                .zoomIn(DriverBoss.theDriverBoss.driver)
                .zoomIn(Driver.theDriver.car)
                .zoomIn(theCar.color)
                .flatMap(collectToList)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testPeek() {
        val logs1 = new ArrayList<String>();
        val logs2 = new ArrayList<String>();
        cars
        .zoomIn(theCar.color)
        .filter(theString.thatNotEquals("red"))
        .peek(logs1::add)
        .zoomOut()
        .peek(car -> logs2.add(car.toString()))
        .toListString();
        
        assertEquals(
                "[blue, green]",
                logs1.toString());
        assertEquals(
                "[Car(color=blue), Car(color=green)]",
                logs2.toString());
        
        
        val logs3 = new ArrayList<String>();
        val logs4 = new ArrayList<String>();
        bosses
        .zoomIn(DriverBoss.theDriverBoss.driver)
        .zoomIn(Driver.theDriver.car)
        .zoomIn(theCar.color)
        .filter(theString.thatNotEquals("red"))
        .peek(logs3::add)
        .zoomOut()
        .zoomOut()
        .zoomOut()
        .peek(boss -> logs4.add(boss.toString()))
        .toListString();
        
        assertEquals(
                "[blue, green]",
                logs3.toString());
        assertEquals(
                "[DriverBoss(driver=Driver(car=Car(color=blue))), DriverBoss(driver=Driver(car=Car(color=green)))]",
                logs4.toString());
    }
    
    @Test
    public void testLimitSkip() {
        assertEquals(
                "[Car(color=blue), Car(color=red)]",
                cars
                .zoomIn(theCar.color)
                .limit(2)
                .zoomOut()
                .toListString());
        
        assertEquals(
                "[Car(color=red), Car(color=green)]",
                cars
                .zoomIn(theCar.color)
                .skip(1)
                .limit(2)
                .zoomOut()
                .toListString());
        
        assertEquals(
                "[DriverBoss(driver=Driver(car=Car(color=blue))), DriverBoss(driver=Driver(car=Car(color=red)))]",
                bosses
                .zoomIn(DriverBoss.theDriverBoss.driver)
                .zoomIn(Driver.theDriver.car)
                .zoomIn(theCar.color)
                .limit(2)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
        
        assertEquals(
                "[DriverBoss(driver=Driver(car=Car(color=red))), DriverBoss(driver=Driver(car=Car(color=green)))]",
                bosses
                .zoomIn(DriverBoss.theDriverBoss.driver)
                .zoomIn(Driver.theDriver.car)
                .zoomIn(theCar.color)
                .skip(1)
                .limit(2)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testContains() {
        assertTrue(cars.zoomIn(theCar.color).contains("red"));
        assertFalse(cars.zoomIn(theCar.color).contains("brown"));
        
        assertTrue(bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .contains("red"));
        
        assertFalse(bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .contains("brown"));
    }
    
    @Test
    public void testGet() {
        assertTrue(cars.zoomIn(theCar.color).contains("red"));
        assertFalse(cars.zoomIn(theCar.color).contains("brown"));
        
        assertEquals("red",
                "" + bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .get(1));
        
        assertEquals("red",
                "" + bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .get(1));
    }
    
    @Test
    public void testSubList() {
        assertEquals(
                "[Car(color=red)]",
                cars
                .zoomIn(theCar.color)
                .subList(1, 2)
                .zoomOut()
                .toListString());
        
        assertEquals(
                "[DriverBoss(driver=Driver(car=Car(color=red)))]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .subList(1, 2)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testWith() {
        assertEquals(
                "[Car(color=blue), Car(color=yellow), Car(color=green)]",
                cars
                .zoomIn(theCar.color)
                .with(1, "yellow")
                .zoomOut()
                .toListString());
        assertEquals(
                "[Car(color=blue), Car(color=red), Car(color=darkgreen)]",
                cars
                .zoomIn(theCar.color)
                .with(2, c -> "dark" + c)
                .zoomOut()
                .toListString());
        assertEquals(
                "[Car(color=blue), Car(color=red), Car(color=darkgreen#2)]",
                cars
                .zoomIn(theCar.color)
                .with(2, (i,c) -> "dark" + c + "#" + i)
                .zoomOut()
                .toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=yellow))), "
                + "DriverBoss(driver=Driver(car=Car(color=green)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .with(1, "yellow")
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=red))), "
                + "DriverBoss(driver=Driver(car=Car(color=darkgreen)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .with(2, c -> "dark" + c)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=red))), "
                + "DriverBoss(driver=Driver(car=Car(color=darkgreen#2)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .with(2, (i,c) -> "dark" + c + "#" + i)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testExclude() {
        assertEquals(
                "[Car(color=red), Car(color=green)]",
                cars
                .zoomIn(theCar.color)
                .exclude("blue")
                .zoomOut()
                .toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=red))), "
                + "DriverBoss(driver=Driver(car=Car(color=green)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .exclude("blue")
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testSort() {
        assertEquals(
                "[Car(color=blue), Car(color=green), Car(color=red)]",
                cars
                .zoomIn(theCar.color)
                .sorted()
                .zoomOut()
                .toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=green))), "
                + "DriverBoss(driver=Driver(car=Car(color=red)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .sorted()
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    @Test
    public void testSort_withComparator() {
        assertEquals(
                "[Car(color=red), Car(color=green), Car(color=blue)]",
                cars
                .zoomIn(theCar.color)
                .sorted(Comparator.reverseOrder())
                .zoomOut()
                .toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=red))), "
                + "DriverBoss(driver=Driver(car=Car(color=green))), "
                + "DriverBoss(driver=Driver(car=Car(color=blue)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .sorted(Comparator.reverseOrder())
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
    // First, First(count), Last, Last(count), tail
    
    @Test
    public void testFirst() {
        assertEquals(
                "Optional[blue]",
                cars
                .zoomIn(theCar.color)
                .first()
                .toString());
        
        assertEquals(
                "Optional[blue]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .first()
                .toString());
    }
    
    @Test
    public void testFirst_count() {
        assertEquals(
                "[blue, red]",
                cars
                .zoomIn(theCar.color)
                .first(2)
                .toListString());
        assertEquals(
                "[Car(color=blue), Car(color=red)]",
                cars
                .zoomIn(theCar.color)
                .first(2)
                .zoomOut()
                .toListString());
        
        assertEquals(
                "[blue, red]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .first(2)
                .toListString());
        
        assertEquals(
                "["
                + "DriverBoss(driver=Driver(car=Car(color=blue))), "
                + "DriverBoss(driver=Driver(car=Car(color=red)))"
                + "]",
                bosses
                .zoomIn(theDriverBoss.driver)
                .zoomIn(theDriver.car)
                .zoomIn(theCar.color)
                .first(2)
                .zoomOut()
                .zoomOut()
                .zoomOut()
                .toListString());
    }
    
}
