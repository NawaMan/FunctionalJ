package functionalj.ref;

import static functionalj.list.FuncList.listOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.Test;

import functionalj.ref.OverridableRef;
import functionalj.ref.Ref;
import lombok.val;

public class RefTest {

    @Test
    public void testNull() {
        val ref = Ref.of(String.class).defaultToNull();
        assertNull(ref.value());
    }
    
    @Test
    public void testValue() {
        val ref1 = Ref.ofValue("Value");
        assertEquals("Value", ref1.value());
        
        val ref2 = Ref.ofValue(42);
        assertEquals(42, (int)ref2.value());
    }
    
    @Test
    public void testFrom() {
        val ref1 = Ref.of(String.class).defaultFrom(()->"Value");
        assertEquals("Value", ref1.value());
        
        val ref2 = Ref.of(Integer.class).defaultFrom(()->42);
        assertEquals(42, (int)ref2.value());
        
        val counter = new AtomicInteger();
        val ref3 = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement);
        assertEquals(0, (int)ref3.value());
        assertEquals(1, (int)ref3.value());
        assertEquals(2, (int)ref3.value());
    }
    
    @Test
    public void testCurrentRef() {
        val ref1 = Ref.of(String.class).defaultFrom(()->"OrgValue");
        val ref2 = ref1.overridable();
        assertEquals("OrgValue", ref2.value());
        
        OverridableRef.runWith(
                listOf(ref2.butWith("NewValue")),
                ()->{
                    assertEquals("NewValue", ref2.value());
                });
        
        assertEquals("OrgValue", ref2.value());
    }
    
    public static class Answer {
        private final int number;
        public Answer() { this(42); }
        public Answer(int number) { this.number = number; }
        @Override
        public String toString() { return "Answer [number=" + number + "]"; }
    }
    
    @Test
    public void testRefTo() {
        val ref1 = Ref.to(Answer.class);
        val ref2 = ref1.overridable();
        assertEquals("Answer [number=42]", "" + ref1.value());
        assertEquals("Answer [number=42]", "" + ref2.value());
        
        OverridableRef.runWith(
                listOf(
                    ref1.butWith(new Answer(123)),
                    ref2.butWith(new Answer(123))
                ),
                ()->{
                    assertEquals("Answer [number=42]", "" + ref1.value());
                    assertEquals("Answer [number=123]", "" + ref2.value());
                });
        
        assertEquals("Answer [number=42]", "" + ref1.value());
        assertEquals("Answer [number=42]", "" + ref2.value());
    }
    
    @Test
    public void testRefFunction() {
        Ref<Supplier<String>> ref = Ref.ofValue(()->"Hello world!");
        assertEquals("Hello world!", ref.value().get());
    }
    
    @Test
    public void testBasicRetain() {
        val counter0 = new AtomicInteger();
        val counter1 = new AtomicInteger();
        val counter2 = new AtomicInteger();
        val ref0     = Ref.of(Integer.class).defaultFrom(counter0::getAndIncrement);
        val ref1     = Ref.of(Integer.class).defaultFrom(counter1::getAndIncrement).retained().forever();
        val ref2     = Ref.of(Integer.class).defaultFrom(counter2::getAndIncrement).retained().never();
        assertEquals(0, ref0.value().intValue());
        assertEquals(1, ref0.value().intValue());
        assertEquals(2, ref0.value().intValue());
        assertEquals(0, ref1.value().intValue());
        assertEquals(0, ref1.value().intValue());
        assertEquals(0, ref1.value().intValue());
        assertEquals(0, ref2.value().intValue());
        assertEquals(1, ref2.value().intValue());
        assertEquals(2, ref2.value().intValue());
    }
    
    @Test
    public void testRetainSame() {
        val state    = new AtomicInteger(42);
        val counter  = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref      = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().when(refState).same();
        
        assertEquals(42, state.get());
        assertEquals( 0, ref.value().intValue());
        assertEquals( 0, ref.value().intValue());
        
        state.incrementAndGet();
        assertEquals(43, state.get());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        
        state.incrementAndGet();
        assertEquals(44, state.get());
        assertEquals(2, ref.value().intValue());
        assertEquals(2, ref.value().intValue());
        
        state.decrementAndGet();
        assertEquals(43, state.get());
        assertEquals(3, ref.value().intValue());
        assertEquals(3, ref.value().intValue());
        
        // NOTE: Since Integer caches from -128 to 128, 1024 will always be different objects,
        //       thus, it appears to always be different objects when state is check.
        state.set(1024);
        assertEquals(1024, state.get());
        assertEquals(4, ref.value().intValue());
        assertEquals(5, ref.value().intValue());
        assertEquals(6, ref.value().intValue());
    }
    
    @Test
    public void testRetainEquals() {
        val state    = new AtomicInteger(42);
        val counter  = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref      = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().when(refState).equals();
        
        assertEquals(42, state.get());
        assertEquals( 0, ref.value().intValue());
        assertEquals( 0, ref.value().intValue());
        
        state.incrementAndGet();
        assertEquals(43, state.get());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        
        state.incrementAndGet();
        assertEquals(44, state.get());
        assertEquals(2, ref.value().intValue());
        assertEquals(2, ref.value().intValue());
        
        state.decrementAndGet();
        assertEquals(43, state.get());
        assertEquals(3, ref.value().intValue());
        assertEquals(3, ref.value().intValue());
        
        // NOTE: Since Integer caches from -128 to 128, 1024 will always be different objects,
        //       but they are considered equals, thus, it appears to always be different objects 
        //       but equals to each other so appears to be unchanged when state is check.
        state.set(1024);
        assertEquals(1024, state.get());
        assertEquals(4, ref.value().intValue());
        assertEquals(4, ref.value().intValue());
        assertEquals(4, ref.value().intValue());
    }
    
    @Test
    public void testRetainMatch() {
        val state    = new AtomicInteger(42);
        val counter  = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref      = Ref.of(Integer.class)
                .defaultFrom(counter::getAndIncrement)
                .retained().when(refState).match((Integer s) -> s.intValue() == 42);
        
        assertEquals(42, state.get());
        assertEquals( 0, ref.value().intValue());
        assertEquals( 0, ref.value().intValue());
        assertEquals( 0, ref.value().intValue());
        
        state.set(43);
        assertEquals(43, state.get());
        assertEquals( 1, ref.value().intValue());
        assertEquals( 2, ref.value().intValue());
        assertEquals( 3, ref.value().intValue());
        
        state.set(42);
        assertEquals(42, state.get());
        assertEquals( 3, ref.value().intValue());
        assertEquals( 3, ref.value().intValue());
        assertEquals( 3, ref.value().intValue());
    }
    
    @Test
    public void testRetainPeriod() throws InterruptedException {
        val counter = new AtomicInteger(0);
        val ref     = Ref.of(Integer.class)
                .defaultFrom(counter::getAndIncrement)
                .retained().withIn(100).milliSeconds();
        
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        
        Thread.sleep(100);
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
    }
    
    @Test
    public void testMap() {
//        val counter = new AtomicInteger(0);
//        val ref     = Ref.of(Integer.class)
//                .defaultFrom(counter::getAndIncrement)
//                .valueSupplier().ma
    }
    
}