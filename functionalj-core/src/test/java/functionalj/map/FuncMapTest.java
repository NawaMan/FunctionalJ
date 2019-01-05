package functionalj.map;

import static functionalj.function.Func.f;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class FuncMapTest {

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

}
