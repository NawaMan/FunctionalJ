package functionalj.lens.lenses;

import static functionalj.lens.Access.$I;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

// This test check if memory is taken (from boxing) when when perform integer primitive access.
// It might fail if there are other thread running at the same time and using memory.
// If it is run by itself and pass, then it is successful as there is no extra memory is used.
public class IntegerAccessTest {
    
    @SuppressWarnings("unused")
    @Test
    public void test() {
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        var str1 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(1)
                .sum();
        
        long afterUsedMem  = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualUsedMem = afterUsedMem - beforeUsedMem;
        
        var str2 = IntStreamPlus
                .infinite()
                .mapToObj(i -> $I.time(2).apply(i))
                .limit(100000)
                .mapToInt(i -> i)
                .sum();
        
        long afterUsedMem2 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta2 = Math.round((afterUsedMem2 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        var str3 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(100000)
                .sum();
        
        long afterUsedMem3 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta3 = Math.round((afterUsedMem3 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        var str4 = IntStreamPlus
                .infinite()
                .mapToObj(i -> $I.time(2).apply(i))
                .limit(100000)
                .mapToInt(i -> i)
                .sum();
        
        long afterUsedMem4 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta4 = Math.round((afterUsedMem4 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        var str5 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(100000)
                .sum();
        
        long afterUsedMem5 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta5 = Math.round((afterUsedMem5 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        System.out.println("Mem2: " + delta2);
        System.out.println("Mem3: " + delta3);
        System.out.println("Mem4: " + delta4);
        System.out.println("Mem5: " + delta5);
        
        System.out.println(str2);
        System.out.println(str3);
        System.out.println(str4);
        System.out.println(str5);
        assertTrue(delta3 < delta4);
        assertEquals(delta2, delta3);
        assertEquals(delta4, delta5);
    }

}
