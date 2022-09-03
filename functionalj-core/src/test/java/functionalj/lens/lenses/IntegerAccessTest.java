package functionalj.lens.lenses;

import static functionalj.lens.Access.$I;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;


public class IntegerAccessTest {
    
    /**
     * The aim of this test is to check if IntegerAccess perform boxing or not.
     * If the boxing is done, large amount of memory will be used as we are running 100000 ints.
     **/
    @Ignore("This test can easily be interfered by environment so they are to be tested manually for now.")
    @SuppressWarnings("unused")
    @Test
    public void testPrimitive() {
        // TODO - Add refactor this to be more understandable.
        
        int loop = 100000;
        
        long beforeUsedMem = currentUsedMemory();
        val str1 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(1)
                .sum();
        
        long actualUsedMem = memoryUsed(beforeUsedMem);
        System.out.println("actualUsedMem: " + actualUsedMem);
        val str2 = IntStreamPlus
                .infinite()
                .mapToObj(i -> $I.time(2).apply(i))
                .limit(loop)
                .mapToInt(i -> i)
                .sum();
        
        long afterUsedMem2 = currentUsedMemory();
        System.out.println("afterUsedMem2: " + afterUsedMem2 + "; diff: " + (afterUsedMem2 - actualUsedMem));
        long delta2 = Math.round((afterUsedMem2 - beforeUsedMem - actualUsedMem) / loop);
        
        val str3 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(loop)
                .sum();
        
        long afterUsedMem3 = currentUsedMemory();
        System.out.println("afterUsedMem3: " + afterUsedMem3 + "; diff: " + (afterUsedMem3 - afterUsedMem2));
        long delta3 = Math.round((afterUsedMem3 - beforeUsedMem - actualUsedMem) / loop);
        
        val str4 = IntStreamPlus
                .infinite()
                .mapToObj(i -> $I.time(2).apply(i))
                .limit(loop)
                .mapToInt(i -> i)
                .sum();
        
        long afterUsedMem4 = currentUsedMemory();
        System.out.println("afterUsedMem4: " + afterUsedMem4 + "; diff: " + (afterUsedMem4 - afterUsedMem3));
        long delta4 = Math.round((afterUsedMem4 - beforeUsedMem - actualUsedMem) / loop);
        
        val str5 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(loop)
                .sum();
        
        long afterUsedMem5 = currentUsedMemory();
        System.out.println("afterUsedMem5: " + afterUsedMem5 + "; diff: " + (afterUsedMem5 - afterUsedMem4));
        long delta5 = Math.round((afterUsedMem5 - beforeUsedMem - actualUsedMem) / loop);
        
        assertTrue(Math.abs(delta5 - delta4) < 100);
        assertEquals(delta2, delta3);
        assertEquals(delta4, delta5);
    }
    
    private long currentUsedMemory() {
        return Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    }
    
    private long memoryUsed(long beforeUsedMem) {
        long afterUsedMem  = currentUsedMemory();
        return afterUsedMem - beforeUsedMem;
    }
    
    @Ignore("This test should be run manually in debug with break point in the constructor of Integer.")
    @Test
    public void testPrimitive2() {
        int loop  = 100000;
        int start = 0;
        int end   = loop;
        
        
        for (int i = start; i < end; i++) {
            $I.time(2).applyAsInt(i);
        }
        long mem1 = currentUsedMemory();
        for (int i = start; i < end; i++) {
            $I.time(2).applyAsInt(i);
        }
        long mem2 = currentUsedMemory();
        System.out.println("mem1: " + mem1);
        System.out.println("mem2: " + mem2 + "; diff: " + (mem2 - mem1));
        
        for (int i = start; i < end; i++) {
            $I.time(2).applyAsInt(i);
        }
        long mem3 = currentUsedMemory();
        System.out.println("mem3: " + mem3 + "; diff: " + (mem3 - mem2));
        
        for (int i = start; i < end; i++) {
            $I.time(2).applyAsInt(i);
        }
        long mem4 = currentUsedMemory();
        System.out.println("mem4: " + mem4 + "; diff: " + (mem4 - mem3));
        
        start = loop;
        end   = loop*2;
        for (int i = start; i < end; i++) {
            $I.time(2).applyAsInt(i);
        }
        long mem5 = currentUsedMemory();
        System.out.println("mem5: " + mem5 + "; diff: " + (mem5 - mem4));
        
    }
    
}
