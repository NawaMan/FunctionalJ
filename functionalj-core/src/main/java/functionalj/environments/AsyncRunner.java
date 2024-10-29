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
package functionalj.environments;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;

import functionalj.function.FuncUnit1;
import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.Promise;
import functionalj.ref.ComputeBody;
import functionalj.ref.Run;
import functionalj.ref.RunBody;
import functionalj.ref.Substitution;
import lombok.val;

/**
 * Runnder for async runnable.
 */
@FunctionalInterface
public interface AsyncRunner extends FuncUnit1<Runnable> {
    
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
        val action = DeferAction.of((Class<DATA>) null).start();
        val theRunner = (runner != null) ? runner : Env.async();
        val substitutions = Substitution.getCurrentSubstitutions().exclude(Substitution::isThreadLocal);
        val latch = new CountDownLatch(1);
        theRunner.accept(() -> {
            try {
                Scoped run must be done some where 
                Run.with(substitutions).run(() -> {
                    body.prepared();
                    latch.countDown();
                    DATA value = body.compute();
                    action.complete(value);
                });
            } catch (Exception exception) {
                action.fail(exception);
                ThrowFuncs.handleNoThrow(exception);
            } catch (Throwable exception) {
                // TODO - Make a dedicate exception.
                action.fail(new Exception(exception));
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        val promise = action.getPromise();
        return promise;
    }
    
    public static final AsyncRunner onSameThread = runnable -> {
        runnable.run();
    };
    
    public static final AsyncRunner onNewThread = runnable -> {
        new Thread(runnable).start();
    };
    
    public static final AsyncRunner threadFactory = runnable -> {
        Executors.defaultThreadFactory().newThread(runnable).start();
    };
    
    public static final AsyncRunner completeableFuture = runnable -> {
        CompletableFuture.runAsync(runnable);
    };
    
    public static final AsyncRunner forkJoinPool = runnable -> {
        ForkJoinPool.commonPool().execute(runnable);
    };
    
    public static AsyncRunner virtualThread(AsyncRunner fallback) {
        return VirtualThreadRunner.asAsyncRunner(fallback);
    }
    
    public static AsyncRunner threadFactory(ThreadFactory threadFactory) {
        return runnable -> threadFactory.newThread(runnable).start();
    }
    
    public static AsyncRunner executorService(ExecutorService executorService) {
        return runnable -> executorService.execute(runnable);
    }
    
    public static class ScopedAsyncRunner implements AsyncRunner {
        
        private final AsyncRunner asyncRunner;
        
        public ScopedAsyncRunner(AsyncRunner asyncRunner) {
            this.asyncRunner = requireNonNull(asyncRunner);
        }
        
        @Override
        public void acceptUnsafe(Runnable runnable) throws Exception {
            if (asyncRunner == onSameThread) {
                runnable.run();
                return;
            }
            
            val subThreads = new ConcurrentHashMap<Thread, Thread>();
            
            val subAsyncRunnder = (new AsyncRunner() {
                @Override
                public void acceptUnsafe(Runnable subRunnable) throws Exception {
                    val currentThread = Thread.currentThread();
                    subThreads.put(currentThread, currentThread);
                    
                    subRunnable.run();
                }
            }).withScope();
            
            try {
                System.out.println("subAsyncRunnder: " + subAsyncRunnder);
                Run.with(Env.refs.async.butWith(subAsyncRunnder)).run(() -> {
                    System.out.println("F: " + Env.refs.async.get());
                    System.out.println("F: " + Thread.currentThread());
                    runnable.run();
                    
                    subThreads.keySet().forEach(thread -> {
                        try {
                            thread.join();
                        } catch (Throwable e) {
                            // Nothing to do.
                        }
                    });
                });
            } finally {
                subThreads.keySet().forEach(thread -> {
                    try {
                        thread.interrupt();
                    } catch (Throwable e) {
                        // Nothing to do.
                    }
                });
            }
        }
        
        @Override
        public ScopedAsyncRunner withScope() {
            return this;
        }
        
        @Override
        public AsyncRunner withNoScope() {
            return asyncRunner;
        }
        
    }
    
    //== Functionality ==
    
    @Override
    public void acceptUnsafe(Runnable runnable) throws Exception;
    
    /** @return a scoped {@link AsyncRunner} of this {@link AsyncRunner}. **/
    public default ScopedAsyncRunner withScope() {
        return new ScopedAsyncRunner(this);
    }
    
    /** @return a scoped {@link AsyncRunner} of this {@link AsyncRunner}. **/
    public default AsyncRunner withNoScope() {
        return this;
    }
    
    
}
