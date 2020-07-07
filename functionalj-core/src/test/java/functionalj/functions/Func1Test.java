// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Absent.__;
import static functionalj.function.Func.f;
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.$I;
import static functionalj.pipeable.Pipeable.StartWtih;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.Streamable;
import lombok.val;

public class Func1Test {
    
    @Test
    public void testElevate() {
        val concat = Func.of(String::concat);
        val appendSpace       = concat.elevateWith(" ");
        val appendWorld       = concat.elevateWith("World");
        val appendExclamation = concat.bind(__, "!");
        val str = StartWtih("Hello")
                .pipeTo(
                    appendSpace,
                    appendWorld,
                    appendExclamation
                );
        assertEquals("Hello World!", str);
    }
    
    @Test
    public void testDefer() {
        val startTime = System.currentTimeMillis();
        
        val length =   Sleep(50).then(String::valueOf).async().apply("Hello!").getPromise()
                .chain(Sleep(50).then(String::length ).async())
                .getResult();
        
        val duration = System.currentTimeMillis() - startTime;
        assertEquals(6, length.get().intValue());
        
        assertTrue(duration > 100);
    }
    
    @Test
    public void testApply() {
        val result   = Result.valueOf("Hello");
        val promise  = Promise.of("Hello");
        val stream   = f(()->Streamable.infiniteInt().stream().limit(5).map($I.asString()));
        val list     = Streamable.infiniteInt().stream().limit(5).map($I.asString()).toList();
        val map      = Streamable.infiniteInt().stream().limit(5).toMap($I, $I.asString());
        val supplier = f(()   -> "Hello");
        val function = f(name -> "Hello " + name + "!");
        val func     = f(String::length);
        assertEquals("Result:{ Value: Hello }",   "" + result);
        assertEquals("Result:{ Value: Hello }",   "" + promise.getResult());
        assertEquals("0, 1, 2, 3, 4",             "" + stream.get().joinToString(", "));
        assertEquals("[0, 1, 2, 3, 4]",           "" + list);
        assertEquals("{0:0, 1:1, 2:2, 3:3, 4:4}", "" + map);
        assertEquals("Result:{ Value: 5 }",       "" + func.applyTo(result));
        assertEquals("Result:{ Value: 5 }",       "" + func.applyTo(promise).getResult());
        assertEquals("1, 1, 1, 1, 1",             "" + func.applyTo(stream.get()).joinToString(", "));
        assertEquals("[1, 1, 1, 1, 1]",           "" + func.applyTo(list));
        assertEquals("{0:1, 1:1, 2:1, 3:1, 4:1}", "" + func.applyTo(map));
        assertEquals("5",                         "" + func.applyTo(supplier).get());
        assertEquals("11",                        "" + func.applyTo(function).apply("John"));
        assertEquals("8",                         "" + func.applyTo(function).apply(5));
    }
    
}
