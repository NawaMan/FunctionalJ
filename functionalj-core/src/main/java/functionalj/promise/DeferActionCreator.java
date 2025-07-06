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

import static functionalj.function.Func.carelessly;

import java.util.concurrent.atomic.AtomicReference;

import functionalj.function.Func0;
import functionalj.ref.ComputeBody;
import functionalj.ref.Ref;
import functionalj.supportive.Default;
import lombok.val;

// This class create a defer action with a task.
// Since there are a specific steps for this, we have this one in one place.
// Any customization from Configure or Builder will have to go through this to create DeferAction in the same way.
// This way the customization can be done in a limited way.
public class DeferActionCreator {
    
    @Default
    public static final DeferActionCreator instance = new DeferActionCreator();
    
    public static final Ref<DeferActionCreator> current = Ref.of(DeferActionCreator.class).orTypeDefaultOrGet(DeferActionCreator::new);
    
    /**
     * Create a {@link DeferAction}.
     * 
     * @param  <D>                the result type.
     * @param  supplier           the action body.
     * @param  onStart            an action to be done before the {@link DeferAction} started.
     * @param  interruptOnCancel  a flag to interrupt the task if the {@link DeferAction} is cancelled.
     * @return
     */
    public <D> DeferAction<D> create(
    				Func0<D>    supplier,
    				Runnable    onStart,
    				boolean     interruptOnCancel) {
        val promiseRef = new AtomicReference<Promise<D>>();
        val runTask    = new RunTask<D>(interruptOnCancel, supplier, onStart, promiseRef::get);
        val action     = new DeferAction<D>(runTask, null);
        val promise    = action.getPromise();
        runTask.actionRef.set(action);
        promiseRef.set(promise);
        return action;
    }
    
    static class RunTask<D> implements Runnable {
        
        private final boolean                         interruptOnCancel;
        private final Func0<D>                        supplier;
        private final Runnable                        onStart;
        private final Func0<Promise<D>>               promiseRef;
        private final AtomicReference<DeferAction<D>> actionRef = new AtomicReference<>(null);
        private final AtomicReference<Thread>         threadRef = new AtomicReference<>();
        
        RunTask(boolean interruptOnCancel, Func0<D> supplier, Runnable onStart, Func0<Promise<D>> promiseRef) {
            this.interruptOnCancel = interruptOnCancel;
            this.supplier          = supplier;
            this.onStart           = onStart;
            this.promiseRef        = promiseRef;
        }
        
        public void start(AsyncRunner.Strategy strategy) {
            AsyncRunner.run(strategy, null, new Body()).onCompleted(result -> {
                val promise = promiseRef.get();
                Promise.makeDone(promise, result);
            });
        }
        
        @Override
        public void run() {
        	start(AsyncRunner.Strategy.LAUNCH);
        }
        
        public void launch() {
        	start(AsyncRunner.Strategy.LAUNCH);
        }
        
        public void fork() {
        	start(AsyncRunner.Strategy.FORK);
        }
        
        class Body implements ComputeBody<D, RuntimeException> {
            
        	// prepare() is run on the thread that runs the task (NOT the starting thread).
            public void prepare() {
                val promise = promiseRef.get();
                if (!promise.isNotDone())
                    return;

                if (interruptOnCancel) {
                	// The threadRef holds the running thread.
                	// So the cancellation of the promise can interrupt the running thread.
	                threadRef.set(Thread.currentThread());
	                promise.eavesdrop(r -> {
	                    r.ifCancelled(() -> {
	                        val thread = threadRef.get();
	                        if ((thread != null) && !thread.equals(Thread.currentThread())) {
	                            thread.interrupt();
	                        }
	                    });
	                });
                }
                
                carelessly(onStart);
            }
            
            // compute() is run on the thread that runs the task (NOT the starting thread).
            @Override
            public D compute() throws RuntimeException {
                try {
				    return supplier.get();
				    
				} finally {
		            if (interruptOnCancel) {
			            threadRef.set(null);
			            // This is to reset the status in case the task was done
			            // but threadRed is yet to be set to null.
			            Thread.currentThread().isInterrupted();
		            }
				}
            }
            
            /** @return  the DeferAction that own this body. */
            public DeferAction<D> action() {
            	return actionRef.get();
			}
        }
        
    }
}
