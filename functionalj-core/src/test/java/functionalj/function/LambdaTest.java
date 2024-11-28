package functionalj.function;

import static functionalj.function.Lambda.λ;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.junit.Test;

import lombok.val;

public class LambdaTest {
    
    static void arraycopy(Object src, Integer srcPos, Object dest, Integer destPos, Integer length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }
    
    static Object copy(Object src, Integer srcPos, Integer length) {
        val dest = Array.newInstance(src.getClass().getComponentType(), length);
        System.arraycopy(src, srcPos, dest, 0, length);
        return dest;
    }
    
    @Test
    public void testMethodHandler() {
        val ac1 = λ(LambdaTest::arraycopy);
        val ac2 = λ(LambdaTest::copy);
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac1.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString((int[])ac2.apply(src, 3, 5)));
    }
    
    @Test
    public void testLambda() {
        val ac1 = λ((Object src, Integer srcPos, Object dest, Integer destPos, Integer length) -> {
            System.arraycopy(src, srcPos, dest, destPos, length);
        });
        val ac2 = λ("copy", (Object src, Integer srcPos, Integer length) -> {
            return copy(src, srcPos, length);
        });
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac1.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString((int[])ac2.apply(src, 3, 5)));
    }
    
    @Test
    public void testMethodHandler_named() {
        val ac1 = λ("arraycopy", LambdaTest::arraycopy);
        val ac2 = λ("copy",      LambdaTest::copy);
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac1.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString((int[])ac2.apply(src, 3, 5)));
        assertEquals("FU5::arraycopy", ac1.toString());
        assertEquals("F3::copy",       ac2.toString());
    }
    
    @Test
    public void testLambda_named() {
        val ac1 = λ("arraycopy", (Object src, Integer srcPos, Object dest, Integer destPos, Integer length) -> {
            System.arraycopy(src, srcPos, dest, destPos, length);
        });
        val ac2 = λ("copy", (Object src, Integer srcPos, Integer length) -> {
            return copy(src, srcPos, length);
        });
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        ac1.accept(src, 3, dst, 0, 5);
        
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString((int[])ac2.apply(src, 3, 5)));
        assertEquals("FU5::arraycopy", ac1.toString());
        assertEquals("F3::copy",       ac2.toString());
    }
    
    @Test
    public void testMethodHandler_apply() {
        val ac1 = λ(LambdaTest::arraycopy);
        val ac2 = λ(LambdaTest::copy);
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        
        λ(ac1, src, 3, dst, 0, 5);
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString((int[])λ(ac2, src, 3, 5)));
    }
    
    @Test
    public void testLambda_apply() {
        val ac1 = λ((Object src, Integer srcPos, Object dest, Integer destPos, Integer length) -> {
            System.arraycopy(src, srcPos, dest, destPos, length);
        });
        val ac2 = λ((Object src, Integer srcPos, Integer length) -> {
            return copy(src, srcPos, length);
        });
        
        val src = new int[] {1, 2, 3, 5, 7, 11, 13, 17, 23};
        val dst = new int[5];
        
        λ(ac1, src, 3, dst, 0, 5);
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString(dst));
        assertEquals("[5, 7, 11, 13, 17]", Arrays.toString((int[])λ(ac2, src, 3, 5)));
    }
    
}
