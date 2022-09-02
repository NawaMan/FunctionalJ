// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.typestests.choice;

import static functionalj.types.choice.ChoiceTypes.Match;
import static functionalj.typestests.choice.UpOrDown.Down;
import static functionalj.typestests.choice.UpOrDown.Up;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Choice;
import lombok.val;


@Choice
interface UpOrDownSpec {
    void Up();
    void Down();
}

public class GenerateBasicChoiceTypeTest {

    @Choice(specField="spec")
    public static interface ColorSpec {
        void White();
        void Black();
        void RGB(int r, int g, int b);
        
        static void __validateRGB(int r, int g, int b) {
            if ((r < 0) || (r > 255)) throw new IllegalArgumentException("r: " + r);
            if ((g < 0) || (g > 255)) throw new IllegalArgumentException("g: " + g);
            if ((b < 0) || (b > 255)) throw new IllegalArgumentException("b: " + b);
        }
    }
    
    private static Function<Color, Boolean> isWhite = (color->
            Match(color)
            .white (true)
            .orElse(false)
    );
    private static Function<Color, Boolean> isBlack = (color->
            color.match()
            .white (false)
            .black (true)
            .orElse(false)
    );
    
    @Test
    public void testIsWhite() {
        assertTrue (isWhite.apply(Color.White()));
        assertFalse(isWhite.apply(Color.Black()));
        assertFalse(isWhite.apply(Color.RGB(126, 126, 126)));
    }
    
    @Test
    public void testIsBlack() {
        assertFalse(isBlack.apply(Color.White()));
        assertTrue (isBlack.apply(Color.Black()));
        assertFalse(isBlack.apply(Color.RGB(126, 126, 126)));
    }
    
    private static Function<UpOrDown, String> upDownString = (upOrDown->
        Match(upOrDown)
        .up  ("Go up")
        .down("Go down")
    );
    
    @Test
    public void testGoUpAndDown() {
        assertEquals("Go up",   upDownString.apply(UpOrDown.Up()));
        assertEquals("Go down", upDownString.apply(UpOrDown.Down()));
    }
    
    private static BiFunction<Integer, UpOrDown, Integer> counting = ((count, upOrDown)->
        Match(upOrDown)
            .up  (count + 1)
            .down(count - 1)
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
    
    @Test
    public void testSpecCode() {
        assertEquals("[White, Black, RGB]", Color.spec.choices.stream().map(c -> c.name).collect(Collectors.toList()).toString());
    }
    
    @Test
    public void testColorChoiceLens() {
        assertEquals(null,    Color.theColor.asBlack.get().asString().apply(Color.white));
        assertEquals("Black", Color.theColor.asBlack.get().asString().apply(Color.black));
    }
    
}
