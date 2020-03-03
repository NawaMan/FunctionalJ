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
package functionalj.ref;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import defaultj.core.Bindings;
import defaultj.core.DefaultProvider;
import defaultj.core.bindings.TypeBinding;
import lombok.val;

public class RefToTest {

    
    // == Bind use in the dependency ==
    
    public static class Car {
        public String zoom() {
            return "FLASH!";
        }
    }
    
    public static class SuperCar extends Car {
        public String zoom() {
            return "SUPER FLASH!!!!";
        }
    }
    
    public static class Person {
        private Car car;
        public Person(Car car) {
            this.car = car;
        }
        public String zoom() {
            return (car != null) ? car.zoom() : "Meh";
        }
    }
    
    static final Ref<Person> personRef = Ref.to(Person.class);
    
    @Test
    public void testBasic() {
        val person = personRef.get();
        assertEquals("FLASH!", person.zoom());
    }
    
    @Test
    public void testOverride1() {
        val typeBinding = new TypeBinding<Car>(SuperCar.class);
        val bindings    = new Bindings.Builder().bind(Car.class, typeBinding).build();
        val provider    = new DefaultProvider.Builder().bingings(bindings).build();
        val zoom = With(RefTo.defaultProvider.butWith(provider)).run(()->{
            return personRef
                    .get()
                    .zoom();
        });
        assertEquals("SUPER FLASH!!!!", zoom);
    }
    
    @Test
    public void testOverride2() {
        val zoom = With(personRef.butFrom(()->new Person(new SuperCar()))).run(()->personRef.get().zoom());
        assertEquals("SUPER FLASH!!!!", zoom);
    }
    
    @Test
    public void testDefaultToDefault() {
        val r = Ref.of(Person.class)
                .defaultToTypeDefault();
        assertEquals("FLASH!", r.value().zoom().toString());
    }
    
    @Test
    public void testWhenAbsent() {
        val r = Ref.of(Person.class)
                .whenAbsentUseTypeDefault()
                .defaultTo(new Person(new SuperCar()));
        assertEquals("SUPER FLASH!!!!", 
                r.value()
                .zoom()
                .toString());
        
        val zoom = With(r.butFrom(()->null))
        .run(()->{
            return r.value()
                    .zoom();
        });
        assertEquals("FLASH!", zoom);
    }

}
