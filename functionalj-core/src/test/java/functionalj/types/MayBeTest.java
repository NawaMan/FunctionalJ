package functionalj.types;

import static functionalj.PointFree.map;
import static functionalj.types.MayBe.mayBe;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.functions.Func1;
import functionalj.types.MayBe;
import lombok.val;

@SuppressWarnings("javadoc")
public class MayBeTest {
    
    @Test
    public void test() {
        assertEquals("Just(Hello)",    MayBe.of("Hello").toString());
        assertEquals("Nothing",         MayBe.of(null)   .toString());
        assertEquals(MayBe.of("Hello"), MayBe.of("Hello"));
        assertEquals(MayBe.of(null),    MayBe.of(null));
    }
    
    @Test
    public void testMap() {
        assertEquals("Just(5)", MayBe.of("Hello")     .map(String::length).toString());
        assertEquals("Nothing", MayBe.of((String)null).map(String::length).toString());
    }
    
    @Test
    public void testFlatMap() {
        assertEquals("Just(5)", MayBe.of("Hello")     .flatMap(s->MayBe.of(s.length())).toString());
        assertEquals("Nothing", MayBe.of((String)null).flatMap(s->MayBe.of(s.length())).toString());
    }
    
    @Test
    public void testPointFreeMap() {
        assertEquals("Just(5)", map(MayBe.of("Hello"), String::length).toString());
        assertEquals("Nothing", map(MayBe.of(null),    String::length).toString());
    }
    
    @Test
    public void testWrapFunction() {
        val strLenght = Func1.of(String::length);
        assertEquals(MayBe.nothing(), mayBe(strLenght).apply(null));
    }
    
}
