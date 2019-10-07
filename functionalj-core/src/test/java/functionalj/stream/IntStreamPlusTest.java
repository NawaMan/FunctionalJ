// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream;

import static org.junit.Assert.assertEquals;

import java.util.function.IntPredicate;

import org.junit.Test;

import lombok.val;

public class IntStreamPlusTest {
    
    @Test
    public void testOf() {
        val intArray  = new int[] {1, 1, 2, 3, 5, 8};
        assertEquals("[1, 1, 2, 3, 5, 8]", IntStreamPlus.of(intArray).toListString());
        
        val stream = IntStreamPlus.of(intArray);
        intArray[0] = 0;
        assertEquals("[1, 1, 2, 3, 5, 8]", stream.toListString());
        // NOICE ------^  The value are not changed after.
    }
    
    @Test
    public void testMapToObj() {
        val intStream = IntStreamPlus.of(1, 1, 2, 3, 5, 8);
        assertEquals("1, 1, 2, 3, 5, 8", intStream.mapToObj(i -> "" + i).joinToString(", "));
    }
    
    @Test
    public void testInfinite() {
        val intStream = IntStreamPlus.infinite();
        assertEquals("5, 6, 7, 8, 9", intStream.skip(5).limit(5).asStream().joinToString(", "));
    }
    
    @Test
    public void testIterate() {
        val intStream = IntStreamPlus.iterate(1, a -> a + 1);
        assertEquals("6, 7, 8, 9, 10", intStream.skip(5).limit(5).asStream().joinToString(", "));
        
        val intStream2 = IntStreamPlus.iterate(1, 1, (a, b) -> a + b);
        assertEquals("1, 1, 2, 3, 5, 8, 13", intStream2.limit(7).asStream().joinToString(", "));
    }
//    
//    @Test
//    public void testSegment() {
//        IntPredicate startCondition = i ->(i % 10) == 3;
//        IntPredicate endCondition   = i ->(i % 10) == 6;
//        
//        assertEquals("53, 54, 55, 56\n" + 
//                     "63, 64, 65, 66\n" + 
//                     "73, 74, 75, 76",
//                IntStreamPlus.infinite().segment(startCondition, endCondition)
//                .skip(5)
//                .limit(3)
//                .map(s -> s.asStream().joinToString(", "))
//                .joinToString("\n"));
//        
//        assertEquals("53, 54, 55, 56\n" + 
//                     "63, 64, 65, 66\n" + 
//                     "73, 74, 75, 76",
//                IntStreamPlus.infinite().segment(startCondition, endCondition, true)
//                .skip(5)
//                .limit(3)
//                .map(s -> s.asStream().joinToString(", "))
//                .joinToString("\n"));
//        
//        assertEquals("53, 54, 55\n" + 
//                     "63, 64, 65\n" + 
//                     "73, 74, 75",
//                IntStreamPlus.infinite().segment(startCondition, endCondition, false)
//                .skip(5)
//                .limit(3)
//                .map(s -> s.asStream().joinToString(", "))
//                .joinToString("\n"));
//        
//        assertEquals("53, 54, 55, 56, 57, 58, 59, 60, 61, 62\n" + 
//                     "63, 64, 65, 66, 67, 68, 69, 70, 71, 72\n" + 
//                     "73, 74, 75, 76, 77, 78, 79, 80, 81, 82\n" + 
//                     "83, 84, 85",
//                IntStreamPlus.infinite()
//                .skip(50)
//                .limit(36)
//                .segment(startCondition)
//                .map(s -> s.asStream().joinToString(", "))
//                .joinToString("\n"));
//    }
    
}
