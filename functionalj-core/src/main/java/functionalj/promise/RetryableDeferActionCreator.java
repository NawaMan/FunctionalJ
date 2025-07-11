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
package functionalj.promise;

import java.util.concurrent.atomic.AtomicInteger;

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
    
    public <DATA> DeferAction<DATA> createRetryDeferAction(boolean interruptOnCancel, FuncUnit0 onStart, Retry<DATA> retry, Func0<DATA> supplier) {
        val onComplete = new RetryDeferActionOnCompleted<DATA>(interruptOnCancel, onStart, retry, supplier);
        return onComplete.finalAction;
    }
    
    static class RetryDeferActionOnCompleted<DATA> implements FuncUnit1<Result<DATA>> {
        
        private final Retry<DATA>              retry;
        private final DeferActionConfig        config;
        private final DeferActionBuilder<DATA> builder;
        
        private final AtomicInteger     couter;
        private final DeferAction<DATA> finalAction;
        
        public RetryDeferActionOnCompleted(boolean interruptOnCancel, FuncUnit0 onStart, Retry<DATA> retry, Func0<DATA> supplier) {
            this.retry   = retry;
            this.config  = new DeferActionConfig().interruptOnCancel(interruptOnCancel).onStart(onStart);
            this.builder = config.createBuilder(supplier);
            
            this.couter      = new AtomicInteger(retry.times());
            this.finalAction = DeferAction.createNew();
            
            this.builder
	            .build()
	            .onCompleted(this)
	            .start();
        }
        
        public DeferAction<DATA> finalAction() {
            return this.finalAction;
        }
        
        @Override
        public void acceptUnsafe(Result<DATA> result) {
            doRetry(result);
        }
        
        private void doRetry(Result<DATA> result) {
        	if (DeferAction.isMornitoring.get()) {
        		System.err.println(
            			(Env.time().currentMilliSecond() - DeferAction.startTime.get()) 
            			+ ": Arya: doRetry -- result:                   " + result);
        		System.err.println(
            			(Env.time().currentMilliSecond() - DeferAction.startTime.get()) 
            			+ ": Arya: doRetry -- result.isPresent():       " + result.isPresent());
        		System.err.println(
            			(Env.time().currentMilliSecond() - DeferAction.startTime.get()) 
            			+ ": Arya: doRetry -- result.isCancelled():     " + result.isCancelled());
        	}
        	
            if (result.isPresent()) {
                val value = result.value();
                finalAction.complete(value);
            } else if (result.isCancelled()) {
				finalAction.fail(result.getException());
            } else {
                val count = couter.decrementAndGet();
            	if (DeferAction.isMornitoring.get()) {
            		System.err.println(
                			(Env.time().currentMilliSecond() - DeferAction.startTime.get()) 
                			+ ": Arya: doRetry -- couter: " + count);
            	}
                if (count == 0) {
                    finalAction.cancel("Retry exceed: " + retry.times());
                } else {
                    val period = retry.waitTimeMilliSecond();
                    Env.time().sleep(period);

                	if (DeferAction.isMornitoring.get()) {
                		System.err.println(
                    			(Env.time().currentMilliSecond() - DeferAction.startTime.get()) 
                    			+ ": Arya: doRetry -- period: " + period);
                	}
                    
                    builder
                    .build()
                    .onCompleted(this)
                    .start();
                }
            }
        }
        
    }
    
}
