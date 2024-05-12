// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.doublestream;

import static functionalj.stream.Step.StartAt;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import lombok.val;

public class DoubleStepTest {
    
    @Test
    public void testBasic() {
        val logs = new ArrayList<String>();
        StartAt(10.5).step(0.15).limit(10).mapToObj(value -> format("%f", value)).forEach(value -> logs.add(value));
        assertEquals("[" + "10.500000, " + "10.650000, " + "10.800000, " + "10.950000, " + "11.100000, " + "11.250000, " + "11.400000, " + "11.550000, " + "11.700000, " + "11.850000" + "]", logs.toString());
    }
}
