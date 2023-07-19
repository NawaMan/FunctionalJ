package functionalj.map;

import static functionalj.map.FuncMap.newMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import lombok.val;

public class FuncMapBuilderTest {
    
    @Test
    public void testMapBuilder() {
        val map = newMap().with(1, "A").with(2, "B").with(3, "C").with(4, "D").with(5, "E").with(6, "F").with(7, "G").with(8, "H").with(9, "I").with(10, "J").with(11, "J").with(12, "L").with(13, "M").with(14, "N").with(15, "O").with(16, "P").with(17, "Q").with(18, "R").with(19, "S").with(20, "T").with(21, "U").with(22, "V").with(23, "W").with(24, "X").with(25, "Y").with(26, "Z").build();
        assertEquals("{1:A, 2:B, 3:C, 4:D, 5:E, 6:F, 7:G, 8:H, 9:I, 10:J, 11:J, 12:L, 13:M, 14:N, 15:O, 16:P, 17:Q, 18:R, 19:S, 20:T, 21:U, 22:V, 23:W, 24:X, 25:Y, 26:Z}", map.toString());
    }
    
    @Test
    public void testMapBuilder_duplicateOverride() {
        val map = newMap().with(1, "A").with(2, "B").with(3, "C").with(4, "D").with(2, "b").build();
        assertEquals("{1:A, 2:b, 3:C, 4:D}", map.toString());
    }
}
