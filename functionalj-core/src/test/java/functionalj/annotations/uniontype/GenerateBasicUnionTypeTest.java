package functionalj.annotations.uniontype;

import static functionalj.annotations.uniontype.IUnionType.Switch;
import static functionalj.annotations.uniontype.UpOrDown.Down;
import static functionalj.annotations.uniontype.UpOrDown.Up;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

import functionalj.annotations.UnionType;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateBasicUnionTypeTest {

    @UnionType(name="Color")
    public static interface ColorSpec {
        void White();
        void Black();
        void RGB(int r, int g, int b);
        
        static void validateRGB(int r, int g, int b) {
            if ((r < 0) || (r > 255)) throw new IllegalArgumentException("r: " + r);
            if ((g < 0) || (g > 255)) throw new IllegalArgumentException("g: " + g);
            if ((b < 0) || (b > 255)) throw new IllegalArgumentException("b: " + b);
        }
    }
    
    @UnionType(name="UpOrDown")
    public static interface UpOrDownSpec {
        void Up();
        void Down();
    }
    
    private static Function<Color, Boolean> isWhite = (color->
            Switch(color)
            .white(true)
            .orElse(false)
    );
    
    @Test
    public void testIsWhite() {
        assertTrue (isWhite.apply(Color.White()));
        assertFalse(isWhite.apply(Color.Black()));
        assertFalse(isWhite.apply(Color.RGB(126, 126, 126)));
    }
    
    private static Function<UpOrDown, String> upDownString = (upOrDown->
        IUnionType.Switch(upOrDown)
        .up  ("Go up")
        .down("Go down")
    );
    
    @Test
    public void testGoUpAndDown() {
        assertEquals("Go up",   upDownString.apply(UpOrDown.Up()));
        assertEquals("Go down", upDownString.apply(UpOrDown.Down()));
    }
    
    private static BiFunction<Integer, UpOrDown, Integer> counting = ((count, upOrDown)->
            Switch(upOrDown)
            .up  (() -> count + 1)
            .down(() -> count - 1)
    );
    
    @Test
    public void testAction() {
        val count = new AtomicInteger(0);
        
        assertEquals(0, count.get());
        count.set(counting.apply(count.get(), Up()));
        assertEquals(1, count.get());
        count.set(counting.apply(count.get(), Up()));
        assertEquals(2, count.get());
        count.set(counting.apply(count.get(), Up()));
        assertEquals(3, count.get());
        
        count.set(counting.apply(count.get(), Down()));
        assertEquals(2, count.get());
        count.set(counting.apply(count.get(), Down()));
        assertEquals(1, count.get());
        count.set(counting.apply(count.get(), Down()));
        assertEquals(0, count.get());
        
    }
    
}
