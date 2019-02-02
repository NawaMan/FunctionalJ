// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.lens.LensTest.Car.theCar;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import functionalj.function.Func1;
import functionalj.lens.LensTest.Car;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ListLens;
import functionalj.lens.lenses.ObjectLensImpl;
import lombok.val;

public class ListLensTest {

    
    public static class WithCars {

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
        
        public static class WithCarLens<HOST> extends ObjectLensImpl<HOST, WithCars>{
            
            public final ListLens<HOST, Car, Car.CarLens<HOST>> cars
                    = createSubListLens(WithCars::cars, WithCars::withCars, Car.CarLens::new);
            
            public WithCarLens(LensSpec<HOST, WithCars> spec) { super(spec); }
            
            public final Func1<HOST, HOST> withCars(List<Car> newCars) {
                return WithCarLens.this.cars.changeTo(newCars);
            }
        }
        
    }
    
    @Test
    public void testListLens() {
        val listLens = ListLens.of(WithCars::cars, WithCars::withCars, Car.CarLens::new);
        
        val withCars = new WithCars(asList(new Car("Blue")));
        assertEquals("WithCars [cars=[Car(color=Blue)]]",  withCars.toString());
        assertEquals("Car(color=Blue)",                    listLens.first().apply(withCars).toString());
        assertEquals("Car(color=Blue)",                    listLens.first().toNullable().get().apply(withCars).toString());
        assertEquals("WithCars [cars=[Car(color=Green)]]", listLens.first().withColor("Green").apply(withCars).toString());
        
        assertTrue(listLens.first().toNullable().isPresent().apply(withCars));
        assertEquals("Car(color=Blue)", listLens.first().toNullable().get().asString().apply(withCars));
        
        val withNoCars = new WithCars(asList());
        assertFalse(listLens.first().toNullable().isPresent().apply(withNoCars));
        
        val withTwoCars = new WithCars(asList(new Car("Blue"), new Car("Green")));
        assertEquals("WithCars [cars=[Car(color=Blue), Car(color=Green)]]",   withTwoCars.toString());
        assertEquals("WithCars [cars=[Car(color=Yellow), Car(color=Green)]]", listLens.first().withColor("Yellow").apply(withTwoCars).toString());
        assertEquals("WithCars [cars=[Car(color=Blue), Car(color=Red)]]",     listLens.last() .withColor("Red").apply(withTwoCars).toString());
        
        assertEquals("[Car(color=Blue)]", listLens.filter(theCar.color.thatEquals("Blue")).apply(withTwoCars).toString());
    }
    
    @Test
    public void testListLensChangeTo() {
        val withTwoCars = new WithCars(asList(new Car("Blue"), new Car("Green")));
        assertEquals("WithCars [cars=[Car(color=Blue), Car(color=Green)]]", withTwoCars.toString());
        
        val listLens = ListLens.of(WithCars::cars, WithCars::withCars, Car.CarLens::new);
        assertEquals("WithCars [cars=[Car(color=Red), Car(color=Green)]]", 
                listLens.changeTo(
                        theCar.color.thatIs("Blue"), 
                        theCar.color.changeTo("Red"))
                .apply(withTwoCars).toString());
    }
    
}
