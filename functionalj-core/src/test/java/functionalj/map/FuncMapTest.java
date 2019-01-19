package functionalj.map;

import static functionalj.function.Func.f;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import lombok.val;

public class FuncMapTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void test() {
        val map1 = FuncMap.of(
                1, "One",
                2, "Two",
                3, "Three",
                4, "Four");
        val map2 = FuncMap.of(
                1, "ONE",
                2, "TWO",
                5, "FIVE",
                6, "FOUR");
        val merger = f((String s1, String s2) -> s1 + s2);
        assertEquals("{1:OneONE, 2:TwoTWO}",                                                  "" + map1.zipWith(map2, merger));
        assertEquals("{1:OneONE, 2:TwoTWO, 3:Threenull, 4:Fournull, 5:nullFIVE, 6:nullFOUR}", "" + map1.zipWith(map2, AllowUnpaired, merger));
    }
    
    @Test
    public void testEquals() {
        val map = FuncMap.of(
                1, "One",
                2, "Two",
                3, "Three",
                4, "Four");
        val map1 = FuncMap.of(
                1, 3,
                2, 3,
                3, 5,
                4, 4);
        val map2 = map.map(String::length);
        assertTrue(map1.equals(map2));
    }
    
    @Test
    public void testLazy() {
        val counter = new AtomicInteger(0);
        val map = FuncMap.of(
                1, "One",
                2, "Two",
                3, "Three",
                4, "Four",
                5, "Five",
                6, "Six",
                7, "Seven");
        val value = map.map(i -> counter.getAndIncrement()).entries().limit(4).joinToString(", ");
        assertStrings("(1,0), (2,1), (3,2), (4,3)", value);
        assertStrings("4",                          counter.get());
    }
    
    @Test
    public void testEager() {
        val counter = new AtomicInteger(0);
        val map = FuncMap.of(
                1, "One",
                2, "Two",
                3, "Three",
                4, "Four",
                5, "Five",
                6, "Six",
                7, "Seven")
                .eager();
        val value = map
                .map(i -> counter.getAndIncrement())
                .entries().limit(4).joinToString(", ");
        assertStrings("(1,0), (2,1), (3,2), (4,3)", value);
        assertStrings("7",                          counter.get());
    }
    
}
