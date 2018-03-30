package nawaman.functionalj;

import static nawaman.functionalj.PointFree.lift;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import lombok.val;
import nawaman.functionalj.types.MayBe;

public class PointFreeTest {
    
    @Test
    public void test() {
        val strLength = lift(String::length);
        val time2     = lift((Integer i) -> 2*i);
        val strLengthTimes2 = strLength.andThen(time2);
        assertEquals("Just(10)", strLengthTimes2.apply(MayBe.of("Hello")));
        assertEquals("Nothing",  strLengthTimes2.apply(MayBe.of(null)));
    }
    
}
