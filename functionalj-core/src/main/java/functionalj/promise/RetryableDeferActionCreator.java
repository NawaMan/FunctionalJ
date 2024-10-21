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
package functionalj.promise;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import functionalj.environments.AsyncRunner;
import functionalj.environments.Env;
import functionalj.function.Func0;
import functionalj.function.FuncUnit0;
import functionalj.function.FuncUnit1;
import functionalj.ref.Ref;
import functionalj.result.Result;
import lombok.val;

public class RetryableDeferActionCreator {
    
    private static final RetryableDeferActionCreator instance = new RetryableDeferActionCreator();
    
    public static final Ref<RetryableDeferActionCreator> current = Ref.of(RetryableDeferActionCreator.class).defaultTo(RetryableDeferActionCreator.instance);
    
    public <DATA> DeferAction<DATA> createRetryDeferAction(boolean interruptOnCancel, FuncUnit0 onStart, AsyncRunner runner, Retry<DATA> retry, Func0<DATA> supplier) {
        DeferAction<DATA> finalAction = DeferAction.createNew();
        val config = new DeferActionConfig().interruptOnCancel(interruptOnCancel).onStart(onStart).runner(runner);
        val couter = new AtomicInteger(retry.times());
        val builder = config.createBuilder(supplier);
        val onCompleteRef = new AtomicReference<FuncUnit1<Result<DATA>>>();
        val onComplete = (FuncUnit1<Result<DATA>>) (result -> {
            if (result.isPresent()) {
                val value = result.value();
                finalAction.complete(value);
            } else {
                val count = couter.decrementAndGet();
                if (count == 0) {
                    finalAction.abort("Retry exceed: " + retry.times());
                } else {
                    val period = retry.waitTimeMilliSecond();
                    Env.time().sleep(period);
                    builder.build().onComplete(onCompleteRef.get()).start();
                }
            }
        });
        onCompleteRef.set(onComplete);
        builder.build().onComplete(onComplete).start();
        return finalAction;
    }
}
