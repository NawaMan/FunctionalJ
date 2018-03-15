package nawaman.functionalj.compose;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import org.junit.Test;

import static org.junit.Assert.*;

public class MayBeTest {
    
    @Test
    public void test() {
        assertEquals("MayBe(Hello)",    MayBe.of("Hello").toString());
        assertEquals("MayBe(null)",     MayBe.of(null).toString());
        assertEquals(MayBe.of("Hello"), MayBe.of("Hello"));
        assertEquals(MayBe.of(null),    MayBe.of(null));
    }
    @Test
    public void testMap() {
        assertEquals("MayBe(5)",    MayBe.of("Hello").map(String::length).toString());
        assertEquals("MayBe(null)", MayBe.of((String)null).map(String::length).toString());
        
        assertEquals("MayBe(5)",    map(MayBe.of("Hello"), String::length).toString());
        assertEquals("MayBe(null)", map(MayBe.of(null), String::length).toString());
    }
    @Test
    public void testFMap() {
        assertEquals("MayBe(5)",    MayBe.of("Hello")     .fmap(s->MayBe.of(s.length())).toString());
        assertEquals("MayBe(null)", MayBe.of((String)null).fmap(s->MayBe.of(s.length())).toString());
        
        assertEquals("MayBe(5)",    fmap(MayBe.of("Hello")     , s->MayBe.of(s.length())).toString());
        assertEquals("MayBe(null)", fmap(MayBe.of((String)null), s->MayBe.of(s.length())).toString());
    }
    
    @Test
    public void testChain() {
        assertEquals("5",    String.valueOf(MayBe.of("Hello")     .chain(s->s.length())));
        assertEquals("null", String.valueOf(MayBe.of((String)null).chain(s->s.length())));
        
        assertEquals("5",    String.valueOf(chain(MayBe.of("Hello")     , s->s.length())));
        assertEquals("null", String.valueOf(chain(MayBe.of((String)null), s->s.length())));
    }
    
    public static <TYPE, RESULT, R> R map(IHaveMap<TYPE> withMap, Function<TYPE, RESULT> f) {
        return withMap.map(f);
    }
    public static <TYPE, RESULT, IHAVEFMAP extends IHaveFlatMap<RESULT>> IHAVEFMAP fmap(IHaveFlatMap<TYPE> withFMap, Function<TYPE, IHAVEFMAP> f) {
        return withFMap.fmap(f);
    }
    public static <TYPE, RESULT> RESULT chain(IHaveChain<TYPE> withChain, Function<TYPE, RESULT> f) {
        return withChain.chain(f);
    }
    
}
