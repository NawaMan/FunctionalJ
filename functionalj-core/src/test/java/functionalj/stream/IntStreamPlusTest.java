package functionalj.stream;

import static org.junit.Assert.assertEquals;

import java.util.function.IntPredicate;

import org.junit.Test;

import lombok.val;

public class IntStreamPlusTest {
    
    @Test
    public void testMapToObj() {
        val intStream = IntStreamPlus.of(1, 1, 2, 3, 5, 8);
        assertEquals("1, 1, 2, 3, 5, 8", intStream.mapBy(i -> "" + i).joinToString(", "));
    }
    
    @Test
    public void testInfinite() {
        val intStream = IntStreamPlus.infinite();
        assertEquals("5, 6, 7, 8, 9", intStream.skip(5).limit(5).asStream().joinToString(", "));
    }
    
    @Test
    public void testSegment() {
        IntPredicate startCondition = i ->(i % 10) == 3;
        IntPredicate endCondition   = i ->(i % 10) == 6;
        
        assertEquals("53, 54, 55, 56\n" + 
                     "63, 64, 65, 66\n" + 
                     "73, 74, 75, 76",
                IntStreamPlus.infinite().segment(startCondition, endCondition)
                .skip(5)
                .limit(3)
                .map(s -> s.asStream().joinToString(", "))
                .joinToString("\n"));
        
        assertEquals("53, 54, 55, 56\n" + 
                     "63, 64, 65, 66\n" + 
                     "73, 74, 75, 76",
                IntStreamPlus.infinite().segment(startCondition, endCondition, true)
                .skip(5)
                .limit(3)
                .map(s -> s.asStream().joinToString(", "))
                .joinToString("\n"));
        
        assertEquals("53, 54, 55\n" + 
                     "63, 64, 65\n" + 
                     "73, 74, 75",
                IntStreamPlus.infinite().segment(startCondition, endCondition, false)
                .skip(5)
                .limit(3)
                .map(s -> s.asStream().joinToString(", "))
                .joinToString("\n"));
        
        assertEquals("53, 54, 55, 56, 57, 58, 59, 60, 61, 62\n" + 
                     "63, 64, 65, 66, 67, 68, 69, 70, 71, 72\n" + 
                     "73, 74, 75, 76, 77, 78, 79, 80, 81, 82\n" + 
                     "83, 84, 85",
                IntStreamPlus.infinite()
                .skip(50)
                .limit(36)
                .segment(startCondition)
                .map(s -> s.asStream().joinToString(", "))
                .joinToString("\n"));
    }
    
}
