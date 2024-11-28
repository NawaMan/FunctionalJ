package functionalj.function;

import static functionalj.function.Lambda.λ;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;

public class LambdaTest {
    
    static void arraycopy(Object src, Integer srcPos, Object dest, Integer destPos, Integer length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }
    
    @Test
    public void testMethodHandler() {
        val ac = λ(LambdaTest::arraycopy);
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
    }
    
    @Test
    public void testLambda() {
        val ac = λ((Object src, Integer srcPos, Object dest, Integer destPos, Integer length) -> {
            System.arraycopy(src, srcPos, dest, destPos, length);
        });
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
    }
    
    @Test
    public void testMethodHandler_named() {
        val ac = λ("arraycopy", LambdaTest::arraycopy);
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("FU5::arraycopy", ac.toString());
    }
    
    @Test
    public void testLambda_named() {
        val ac = λ("arraycopy", (Object src, Integer srcPos, Object dest, Integer destPos, Integer length) -> {
            System.arraycopy(src, srcPos, dest, destPos, length);
        });
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("FU5::arraycopy", ac.toString());
    }
    
}
