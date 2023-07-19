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
package functionalj.types.choice.generator;

import static functionalj.types.Absent.__;
import static functionalj.types.choice.ChoiceTypes.Match;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.function.Function;
import org.junit.Test;

public class ChoiceTypeExampleTest {
    
    public static interface Choice1TypeSpec {
    
        void White();
    
        void Black();
    
        void RGB(int r, int g, int b);
    
        static void __validateRGB(int r, int g, int b) {
            if ((r < 0) || (r > 255))
                throw new IllegalArgumentException("r: " + r);
            if ((g < 0) || (g > 255))
                throw new IllegalArgumentException("g: " + g);
            if ((b < 0) || (b > 255))
                throw new IllegalArgumentException("b: " + b);
        }
    }
    
    private static Function<BasicColor, String> colorToString = (color -> Match(color).white("White").black("Black").rgb(rgb -> "rgb#(" + rgb.r() + "," + rgb.g() + "," + rgb.b() + ")"));
    
    private static Function<BasicColor, Boolean> isWhite = (color -> Match(color).white(true).orElse(false));
    
    private static Function<BasicColor, Boolean> isBlack = (color -> Match(color).white(false).black(true).orElse(false));
    
    private static Function<BasicColor, Boolean> isFull = (color -> Match(color).white(true).black(true).rgb(__, 0, 0, true).rgb(0, __, 0, () -> true).rgb(0, 0, __, c -> true).rgb(false));
    
    @Test
    public void testWhite() {
        assertEquals("White", colorToString.apply(BasicColor.White()));
    }
    
    @Test
    public void testIsWhite() {
        assertTrue(isWhite.apply(BasicColor.White()));
        assertFalse(isWhite.apply(BasicColor.Black()));
        assertFalse(isWhite.apply(BasicColor.RGB(255, 0, 0)));
    }
    
    @Test
    public void testIsBlack() {
        assertFalse(isBlack.apply(BasicColor.White()));
        assertTrue(isBlack.apply(BasicColor.Black()));
        assertFalse(isBlack.apply(BasicColor.RGB(255, 0, 0)));
    }
    
    @Test
    public void testBlack() {
        assertEquals("Black", colorToString.apply(BasicColor.Black()));
    }
    
    @Test
    public void testRed() {
        assertEquals("rgb#(255,0,0)", colorToString.apply(BasicColor.RGB(255, 0, 0)));
    }
    
    @Test
    public void testIsFull() {
        assertTrue(isFull.apply(BasicColor.White()));
        assertTrue(isFull.apply(BasicColor.Black()));
        assertTrue(isFull.apply(BasicColor.RGB(255, 0, 0)));
        assertTrue(isFull.apply(BasicColor.RGB(0, 255, 0)));
        assertTrue(isFull.apply(BasicColor.RGB(0, 0, 255)));
        assertFalse(isFull.apply(BasicColor.RGB(255, 255, 0)));
        assertFalse(isFull.apply(BasicColor.RGB(45, 66, 0)));
    }
    
    @Test
    public void testEquals() {
        assertFalse(BasicColor.White().equals(BasicColor.RGB(255, 0, 0)));
        assertTrue(BasicColor.White().equals(BasicColor.White()));
        assertTrue(BasicColor.White().equals(BasicColor.RGB(255, 255, 255)));
    }
    
    @Test
    public void testValidation() {
        try {
            BasicColor.RGB(-1, 256, 255);
            fail("Expect an exception!");
        } catch (IllegalArgumentException e) {
            assertEquals("r: -1", e.getMessage());
        }
    }
}
