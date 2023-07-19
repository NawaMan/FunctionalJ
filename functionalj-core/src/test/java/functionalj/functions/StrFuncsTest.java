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
package functionalj.functions;

import static functionalj.functions.StrFuncs.grab;
import static functionalj.functions.StrFuncs.matches;
import static functionalj.functions.StrFuncs.template;
import static functionalj.list.FuncList.listOf;
import static functionalj.stream.StreamPlus.streamOf;
import static org.junit.Assert.assertEquals;
import java.util.regex.Pattern;
import org.junit.Test;
import functionalj.list.FuncList;
import lombok.val;

public class StrFuncsTest {
    
    @Test
    public void testRepeat() {
        assertEquals("AAAAA", StrFuncs.repeat("A", 5));
    }
    
    @Test
    public void testLines() {
        assertEquals("AA, AAA, AAAA, AAAAA", StrFuncs.lines("AA\nAAA\rAAAA\r\nAAAAA").join(", "));
        assertEquals("AA, AAA", StrFuncs.lines("AA\nAAA\rAAAA\r\nAAAAA").limit(2).join(", "));
    }
    
    @Test
    public void testIndent() {
        assertEquals("\tAA\n" + "\tAAA\r" + "\tAAAA\r\n" + "\tAAAAA", StrFuncs.indent("AA\nAAA\rAAAA\r\nAAAAA"));
    }
    
    @Test
    public void testLeftPadding() {
        assertEquals("---AAA", StrFuncs.leftPadding("AAA", '-', 6));
    }
    
    @Test
    public void testMatches() {
        assertEquals("A, AA, AAA, AAAA", matches("ABAABAAABAAAA", "A+").texts().join(", "));
        assertEquals("#{Hello}, #{There}", matches("--#{Hello}--#{There}--", "#\\{[a-zA-Z0-9$_]+\\}").texts().join(", "));
        assertEquals("#{Hello}, #{Here}, #{Hello}, #{There}", FuncList.of("--#{Hello}--#{Here}--", "--#{Hello}--#{There}--").streamPlus().flatMap(matches("#\\{[a-zA-Z0-9$_]+\\}").texts()).join(", "));
    }
    
    @Test
    public void testTemplate() {
        assertEquals("--hello--there-${SS}-", template("--${Hello}--${There}-$${SS}-", str -> str.toLowerCase()));
        assertEquals("--hello--there-$${SS}-", template("--${Hello}--${There}-$$${SS}-", str -> str.toLowerCase()));
        assertEquals("--hello--there-${0S}-", template("--${Hello}--${There}-${0S}-", str -> str.toLowerCase()));
    }
    
    @Test
    public void testGrap() {
        val str = "1 2 3 4 5 6 7 8 9 10 11";
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]", "" + grab(str, "[0-9]+"));
        assertEquals("[[1, 2, 3, 4, 5], [6, 7, 8, 9, 10]]", streamOf("1 2 3 4 5", "6 7 8 9 10").map(grab("[0-9]+")).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", listOf("1 2 3 4 5", "6 7 8 9 10").flatMap(grab("[0-9]+")).toListString());
    }
    
    @Test
    public void testCapture() {
        val pattern = Pattern.compile("(?<key>[^:]+): (?<value>.*)");
        assertEquals("{value:Nawa, key:name}", "" + StrFuncs.capture("name: Nawa", pattern));
    }
}
