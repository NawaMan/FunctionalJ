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
    
    public <D> DeferAction<D> create(Func0<D> supplier, Runnable onStart, boolean interruptOnCancel, AsyncRunner runner) {
        val promiseRef = new AtomicReference<Promise<D>>();
        val runTask    = new RunTask<D>(interruptOnCancel, supplier, onStart, runner, promiseRef::get);
        val action     = new DeferAction<D>(runTask, null);
        val promise    = action.getPromise();
        promiseRef.set(promise);
        return action;
    }
    
    private static class RunTask<D> implements Runnable {
        
        private final boolean                 interruptOnCancel;
        private final Func0<D>                supplier;
        private final Runnable                onStart;
        private final AsyncRunner             runner;
        private final Func0<Promise<D>>       promiseRef;
        private final AtomicReference<Thread> threadRef = new AtomicReference<Thread>();
        
        RunTask(boolean interruptOnCancel, Func0<D> supplier, Runnable onStart, AsyncRunner runner, Func0<Promise<D>> promiseRef) {
            this.interruptOnCancel = interruptOnCancel;
            this.supplier          = supplier;
            this.onStart           = onStart;
            this.runner            = runner;
            this.promiseRef        = promiseRef;
        }
        
        @Override
        public void run() {
            AsyncRunner.run(runner, new Body()).onCompleted(result -> {
                val promise = promiseRef.get();
                Promise.makeDone(promise, result);
            });
        }
        
        class Body implements ComputeBody<D, RuntimeException> {
            
            public void prepared() {
                val promise = promiseRef.get();
                if (!promise.isNotDone())
                    return;
                setupInterruptOnCancel(promise);
                carelessly(onStart);
            }
            
            @Override
            public D compute() throws RuntimeException {
                return this.runSupplier();
            }
            
            private D runSupplier() {
                try {
                    return supplier.get();
                } finally {
                    doInterruptOnCancel();
                }
            }
            
            private void setupInterruptOnCancel(Promise<D> promise) {
                if (!interruptOnCancel)
                    return;
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
        }
        
        private void doInterruptOnCancel() {
            if (!interruptOnCancel)
                return;
            threadRef.set(null);
            // This is to reset the status in case the task was done
            // but threadRed is yet to be set to null.
            Thread.currentThread().isInterrupted();
        }
    }
}
