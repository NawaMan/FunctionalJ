package functionalj.lens.lenses;

import static functionalj.lens.Access.$I;
import static functionalj.lens.Access.theInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.result.Result;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;


public class IntegerAccessTest {
    
    @Test
    public void testDigitAt() {
        val value = Result.ofValue(123456789);
        assertEquals("0", value.map(theInt.digitAt(-1).asString()).get());
        assertEquals("9", value.map(theInt.digitAt(0) .asString()).get());
        assertEquals("8", value.map(theInt.digitAt(1) .asString()).get());
        assertEquals("7", value.map(theInt.digitAt(2) .asString()).get());
        assertEquals("6", value.map(theInt.digitAt(3) .asString()).get());
        assertEquals("5", value.map(theInt.digitAt(4) .asString()).get());
        assertEquals("4", value.map(theInt.digitAt(5) .asString()).get());
        assertEquals("3", value.map(theInt.digitAt(6) .asString()).get());
        assertEquals("2", value.map(theInt.digitAt(7) .asString()).get());
        assertEquals("1", value.map(theInt.digitAt(8) .asString()).get());
        assertEquals("0", value.map(theInt.digitAt(9) .asString()).get());
        assertEquals("0", value.map(theInt.digitAt(10).asString()).get());
    }
    
    @Test
    public void testDigitValueAt() {
        val value = Result.ofValue(123456789);
        assertEquals("0",         value.map(theInt.digitValueAt(-1).asString()).get());
        assertEquals("9",         value.map(theInt.digitValueAt(0) .asString()).get());
        assertEquals("80",        value.map(theInt.digitValueAt(1) .asString()).get());
        assertEquals("700",       value.map(theInt.digitValueAt(2) .asString()).get());
        assertEquals("6000",      value.map(theInt.digitValueAt(3) .asString()).get());
        assertEquals("50000",     value.map(theInt.digitValueAt(4) .asString()).get());
        assertEquals("400000",    value.map(theInt.digitValueAt(5) .asString()).get());
        assertEquals("3000000",   value.map(theInt.digitValueAt(6) .asString()).get());
        assertEquals("20000000",  value.map(theInt.digitValueAt(7) .asString()).get());
        assertEquals("100000000", value.map(theInt.digitValueAt(8) .asString()).get());
        assertEquals("0",         value.map(theInt.digitValueAt(9) .asString()).get());
        assertEquals("0",         value.map(theInt.digitValueAt(10).asString()).get());
    }
    
    @Test
    public void testFactorValueAt() {
        val value = Result.ofValue(123456789);
        assertEquals("0",         value.map(theInt.factorValueAt(-1).asString()).get());
        assertEquals("1",         value.map(theInt.factorValueAt(0) .asString()).get());
        assertEquals("10",        value.map(theInt.factorValueAt(1) .asString()).get());
        assertEquals("100",       value.map(theInt.factorValueAt(2) .asString()).get());
        assertEquals("1000",      value.map(theInt.factorValueAt(3) .asString()).get());
        assertEquals("10000",     value.map(theInt.factorValueAt(4) .asString()).get());
        assertEquals("100000",    value.map(theInt.factorValueAt(5) .asString()).get());
        assertEquals("1000000",   value.map(theInt.factorValueAt(6) .asString()).get());
        assertEquals("10000000",  value.map(theInt.factorValueAt(7) .asString()).get());
        assertEquals("100000000", value.map(theInt.factorValueAt(8) .asString()).get());
        assertEquals("0",         value.map(theInt.factorValueAt(9) .asString()).get());
        assertEquals("0",         value.map(theInt.factorValueAt(10).asString()).get());
    }
    
    /**
     * The aim of this test is to check if IntegerAccess perform boxing or not.
     * If the boxing is done, large amount of memory will be used as we are running 100000 ints.
     **/
    @SuppressWarnings("unused")
    @Test
    public void testPrimitive() {
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        val str1 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(1)
                .sum();
        
        long afterUsedMem  = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualUsedMem = afterUsedMem - beforeUsedMem;
        
        val str2 = IntStreamPlus
                .infinite()
                .mapToObj(i -> $I.time(2).apply(i))
                .limit(100000)
                .mapToInt(i -> i)
                .sum();
        
        long afterUsedMem2 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta2 = Math.round((afterUsedMem2 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        val str3 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(100000)
                .sum();
        
        long afterUsedMem3 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta3 = Math.round((afterUsedMem3 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        val str4 = IntStreamPlus
                .infinite()
                .mapToObj(i -> $I.time(2).apply(i))
                .limit(100000)
                .mapToInt(i -> i)
                .sum();
        
        long afterUsedMem4 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta4 = Math.round((afterUsedMem4 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        val str5 = IntStreamPlus
                .infinite()
                .map($I.time(2))
                .limit(100000)
                .sum();
        
        long afterUsedMem5 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long delta5 = Math.round((afterUsedMem5 - beforeUsedMem - actualUsedMem) / 1000000.0);
        
        assertTrue(Math.abs(delta5 - delta4) < 100);
        assertEquals(delta2, delta3);
        assertEquals(delta4, delta5);
    }

}
