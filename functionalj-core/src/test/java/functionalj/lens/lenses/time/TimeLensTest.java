// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.lenses.time;

import static functionalj.lens.lenses.java.time.LocalDateLens.theLocalDate;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import org.junit.Test;
import lombok.val;

public class TimeLensTest {
    
    @Test
    public void testLocalDate() {
        val localDate1 = LocalDate.of(2019, 3, 3);
        val localDate2 = theLocalDate.day.changeTo(5).apply(localDate1);
        assertEquals("2019-03-03", localDate1.toString());
        assertEquals("2019-03-05", localDate2.toString());
        val localDate3 = theLocalDate.month.toMay.apply(localDate2);
        assertEquals("2019-05-05", localDate3.toString());
        val localDateTime1 = theLocalDate.atTime(6, 0).apply(localDate3);
        assertEquals("2019-05-05T06:00", localDateTime1.toString());
        val period1 = theLocalDate.periodFrom(localDate1).apply(localDate3);
        assertEquals("P2M2D", period1.toString());
    }
}
