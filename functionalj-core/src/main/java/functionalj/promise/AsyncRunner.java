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

import static functionalj.functions.ThrowFuncs.logUnthrowable;
import static functionalj.ref.Run.With;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;

import functionalj.environments.Env;
import functionalj.environments.VirtualThreadRunner;
import functionalj.exception.FunctionInvocationException;
import functionalj.exception.WrappedThrowableException;
import functionalj.function.Annotated;
import functionalj.ref.ComputeBody;
import functionalj.ref.RunBody;
import functionalj.ref.Substitution;
import lombok.val;

/**
 * Asynchronous runner for a {@link Runnable}.
 */
@FunctionalInterface
public interface AsyncRunner extends functionalj.function.FuncUnit1<java.lang.Runnable> {
	
    public static AsyncRunner of(functionalj.function.FuncUnit1<java.lang.Runnable> runner) {
        if (runner instanceof AsyncRunner)
            return (AsyncRunner)runner;
        return runner::acceptUnsafe;
    }
    
    public static NamedAsyncRunner of(String name, functionalj.function.FuncUnit1<java.lang.Runnable> runner) {
        return withName(name, runner);
    }
    
    public static NamedAsyncRunner withName(String name, functionalj.function.FuncUnit1<java.lang.Runnable> runner) {
        if (runner instanceof NamedAsyncRunner) {
            val named = (NamedAsyncRunner)runner;
            if (named.name().equals(name))
                return named;
        }
        
        return new NamedAsyncRunner(name, runner);
    }
    
    public static class NamedAsyncRunner extends Annotated.FuncUnit1<java.lang.Runnable> implements AsyncRunner {
        
        public NamedAsyncRunner(String name, functionalj.function.FuncUnit1<java.lang.Runnable> runner) {
            super(name, runner);
        }
        
    }
    
    public static <EXCEPTION extends Exception> Promise<Object> run(RunBody<EXCEPTION> runnable) {
        return run(null, runnable);
    }
    
    public static <DATA, EXCEPTION extends Exception> Promise<DATA> run(ComputeBody<DATA, EXCEPTION> body) {
        return run(null, body);
    }
    
    public static <EXCEPTION extends Exception> Promise<Object> run(AsyncRunner runner, RunBody<EXCEPTION> runnable) {
        return run(runner, () -> {
            runnable.run();
            return null;
        });
    }
    
    public static <DATA, EXCEPTION extends Exception> Promise<DATA> run(AsyncRunner runner, ComputeBody<DATA, EXCEPTION> body) {
        val theRunner  = (runner != null) ? runner : Env.async();
    	val deferValue = new DeferValue<DATA>();
        val substitutions
                = Substitution
                .getCurrentSubstitutions()
                .exclude(Substitution::isThreadLocal);
        
        val latch = new CountDownLatch(1);
        theRunner.accept(() -> {
            try {
            	try {
	                With(substitutions)
	                .run(() -> {
	                    body.prepared();
	                    latch.countDown();
	                    DATA value = body.compute();
	                    deferValue.assign(value);
	                });
	            } catch (FunctionInvocationException exception) {
	                val cause = exception.getCause();
	                throw cause;
                }
            } catch (Throwable throwable) {
                val exception = WrappedThrowableException.exceptionOf(throwable);
                deferValue.fail(exception);
                logUnthrowable(exception);
            }
        });
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return deferValue;
    }
    
    public static final AsyncRunner onSameThread = AsyncRunner.withName("RunOnSameThread", runnable -> {
        runnable.run();
    });
    
    public static final AsyncRunner onNewThread = AsyncRunner.withName("RunOnNewThread", runnable -> {
        new Thread(runnable).start();
    });
    
    public static final AsyncRunner threadFactory = AsyncRunner.withName("ThreadFactory", runnable -> {
        Executors.defaultThreadFactory().newThread(runnable).start();
    });
    
    public static final AsyncRunner forkJoinPool = AsyncRunner.withName("ForkJoinPool", runnable -> {
        ForkJoinPool.commonPool().execute(runnable);
    });
    
    public static AsyncRunner virtualThread(AsyncRunner fallback) {
        return VirtualThreadRunner.asAsyncRunner(fallback);
    }
    
    public static AsyncRunner threadFactory(ThreadFactory threadFactory) {
        return AsyncRunner.withName("ThreadFactory:" + threadFactory, runnable -> threadFactory.newThread(runnable).start());
    }
    
    public static AsyncRunner executorService(ExecutorService executorService) {
        return AsyncRunner.withName("ExecutorService:" + executorService, runnable -> executorService.execute(runnable));
    }
    
    //== Functionality ==
    
    @Override
    public void acceptUnsafe(Runnable runnable) throws Exception;
    
    /** @return a scoped {@link AsyncRunner} of this {@link AsyncRunner}. **/
    public default AsyncRunner withNoScope() {
        return this;
    }
    
    
}
