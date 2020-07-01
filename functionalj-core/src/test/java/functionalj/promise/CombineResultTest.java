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
package functionalj.promise;

import static functionalj.list.FuncList.listOf;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

public class CombineResultTest {

    @Test
    public void testMerge() {
        val logs = new ArrayList<String>();
        
        val action1  = DeferAction.createNew().start();
        val action2  = DeferAction.createNew().start();
        val action3  = DeferAction.createNew().start();
        val combiner = new CombineResult<>(
                listOf(action1, action2, action3),
                l -> Result.valueOf(l.toString()));
        
        val combine = combiner.getDeferAction()
        .onComplete(result -> {
            logs.add(result.toString());
        });
        
        logs.add("Done prepare");
        
        logs.add("About to do one: " + action1.getPromise().getStatus());
        action1.complete("One");
        
        logs.add("About to do two: " + action2.getPromise().getStatus());
        action2.complete("Two");
        
        logs.add("About to do three: " + action3.getPromise().getStatus());
        action3.complete("Three");
        
        // Abort ... but after done so no effect.
        combine.abort();
        logs.add(combine.getResult().toString());
        
        assertEquals(
                "Done prepare\n" + 
                "About to do one: PENDING\n" + 
                "About to do two: PENDING\n" + 
                "About to do three: PENDING\n" + 
                "Result:{ Value: [Result:{ Value: One }, Result:{ Value: Two }, Result:{ Value: Three }] }\n" + 
                "Result:{ Value: [Result:{ Value: One }, Result:{ Value: Two }, Result:{ Value: Three }] }",
                logs.stream().collect(joining("\n")));
    }
}
