// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.lens.ResultAccessTest.Driver.theDriver;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.Test;
import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensUtils;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.ResultLens;
import functionalj.lens.lenses.StringLens;
import functionalj.result.Result;
import lombok.val;

public class ResultAccessTest {
    
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
        
            public CarLens(LensSpec<HOST, Car> spec) {
                super(spec);
            }
        
            public CarLens(Function<HOST, Car> access) {
                super(LensSpec.of(access));
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
        
        private final Car firstCar;
        
        private final Result<Car> secondCar;
        
        public Driver(Car firstCar, Result<Car> secondCar) {
            this.firstCar = firstCar;
            this.secondCar = secondCar;
        }
        
        public Car firstCar() {
            return firstCar;
        }
        
        public Driver withFirstCar(Car car) {
            return new Driver(car, secondCar);
        }
        
        public Result<Car> secondCar() {
            return secondCar;
        }
        
        public Driver withSecondCar(Result<Car> secondCar) {
            return new Driver(firstCar, secondCar);
        }
        
        @Override
        public String toString() {
            return "Driver(firstCar=" + firstCar + ",secondCar=" + secondCar + ")";
        }
        
        public static class DriverLens<HOST> extends ObjectLensImpl<HOST, Driver> {
        
            public final Car.CarLens<HOST> firstCar = new Car.CarLens<>(this.lensSpec().then(LensSpec.of(Driver::firstCar, Driver::withFirstCar)));
        
            public final ResultLens<HOST, Car, Car.CarLens<HOST>> secondCar = LensUtils.createResultLens(this.lensSpec().then(LensSpec.of(Driver::secondCar, Driver::withSecondCar)), Car.CarLens::new);
        
            public DriverLens(LensSpec<HOST, Driver> spec) {
                super(spec);
            }
        
            public DriverLens(Function<HOST, Driver> access) {
                super(LensSpec.of(access));
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
    public void testResultAccessMap() {
        val logs = new ArrayList<String>();
        theDriver.secondCar.thenMap(Car::color).apply(new Driver(new Car("Black"), Result.valueOf(new Car("White")))).pipeTo(me -> logs.add(me.toString()));
        theDriver.secondCar.thenMap(Car::color).apply(new Driver(new Car("Black"), Result.ofNull())).pipeTo(me -> logs.add(me.toString()));
        theDriver.secondCar.thenMap(Car::color).apply(new Driver(new Car("Black"), null)).pipeTo(me -> logs.add(me.toString()));
        assertEquals("[Result:{ Value: White }, Result:{ Value: null }, Result:{ Value: null }]", logs.toString());
    }
}
