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
package functionalj.function;

import static functionalj.function.Func.f;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.promise.Promise;
import functionalj.result.Result;

public class Func2Test {
    
    private Func2<String, String, String> concat = f(String::concat);
    
    @Test
    public void testApplyBare() {
        assertEquals("Hello world!", "" + concat.apply("Hello", " world!"));
        assertEquals("Result:{ Value: Hello world! }", "" + concat.applyTo(Result.valueOf("Hello"),  Result.valueOf(" world!")));
        assertEquals("Result:{ Value: Hello world! }", "" + concat.applyTo(Promise.ofValue("Hello"), Promise.ofValue(" world!")).getResult());
    }
}
