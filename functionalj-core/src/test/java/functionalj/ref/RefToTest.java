package functionalj.ref;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;
import nawaman.defaultj.core.Bindings;
import nawaman.defaultj.core.DefaultProvider;
import nawaman.defaultj.core.bindings.TypeBinding;

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
                .defaultToDefault();
        assertEquals("FLASH!", r.value().zoom().toString());
    }
    
    @Test
    public void testWhenAbsent() {
        val r = Ref.of(Person.class)
                .whenAbsentUseDefault()
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
