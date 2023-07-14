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
package functionalj.environments;

import static functionalj.list.FuncList.listOf;
import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.stream.StreamPlus;
import lombok.val;

public class LogTest {

    @Test
    public void testLog() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub)).run(() -> {
            Log.log("One");
            Log.log("2: ", "Two", " --");
            Log.logEach("Three", "Four");
            Log.logEach("-->", listOf("Three", "Four"), "<--");
            Log.logBy(() -> "42");
            val outLines = StreamPlus.from(stub.outLines()).toJavaList();
            assertEquals("[" + "One, " + "2: Two --, " + "Three, Four, " + "-->Three<--, -->Four<--, " + "42" + "]", outLines.toString());
            stub.clearOutLines();
        });
    }

    @Test
    public void testLogErr() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub)).run(() -> {
            try {
                throw new NullPointerException("NULL!!!");
            } catch (Exception e) {
                Log.logErr("Error!", e);
            }
            val expected = "Error!\n" + "java.lang.NullPointerException: NULL!!!";
            assertEquals(expected, stub.errLines().limit(2).join("\n"));
        });
    }
}
