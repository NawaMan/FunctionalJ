package functionalj.promise;

import static functionalj.function.Func.carelessly;

import java.util.concurrent.atomic.AtomicReference;

import functionalj.environments.AsyncRunner;
import functionalj.function.Func0;
import functionalj.ref.Ref;
import functionalj.result.Result;
import functionalj.supportive.Default;
import lombok.val;

// This class create a defer action with a task.
// Since there are a specific steps for this, we have this one in one place.
// Any customization from Configure or Builder will have to go through this to create DeferAction in the same way.
// This way the customization can be done in a limited way.
public class DeferActionCreator {
    
    @Default
    public static final DeferActionCreator instance = new DeferActionCreator();
    
    public static final Ref<DeferActionCreator> current
                = Ref.of(DeferActionCreator.class)
                        .orTypeDefaultOrGet(DeferActionCreator::new);
    
    public <D> DeferAction<D> create(
            Func0<D>    supplier,
            Runnable    onStart,
            boolean     interruptOnCancel,
            AsyncRunner runner) {
        val promiseRef = new AtomicReference<Promise<D>>();
        val runTask    = new RunTask<D>(interruptOnCancel, supplier, onStart, runner, promiseRef::get);
        val action     = new DeferAction<D>(runTask, null);
        promiseRef.set(action.getPromise());
        return action;
    }
    
    private static class RunTask<D> implements Runnable {
        
        private final boolean           interruptOnCancel;
        private final Func0<D>          supplier;
        private final Runnable          onStart;
        private final AsyncRunner       runner;
        private final Func0<Promise<D>> promiseRef;
        
        private final AtomicReference<Thread> threadRef  = new AtomicReference<Thread>();
        
        public RunTask(boolean    interruptOnCancel, 
                Func0<D>          supplier, 
                Runnable          onStart, 
                AsyncRunner       runner,
                Func0<Promise<D>> promiseRef) {
            this.interruptOnCancel = interruptOnCancel;
            this.supplier = supplier;
            this.onStart = onStart;
            this.runner = runner;
            this.promiseRef = promiseRef;
        }
        
        @Override
        public void run() {
            AsyncRunner.run(runner, this::body);
        }
        
        private void body() {
            val promise = promiseRef.get();
            if (!promise.isNotDone()) 
                return;
            
            setupInterruptOnCancel(promise);
            
            carelessly(onStart);
            
            val action = new PendingAction<D>(promise);
            val result = Result.from(this::runSupplier);
            action.completeWith(result);
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
                    if ((thread != null) && !thread.equals(Thread.currentThread()))
                        thread.interrupt();
                });
            });
        }
        
        private void doInterruptOnCancel() {
            if (!interruptOnCancel)
                return;
            
            threadRef.set(null);
            // This is to reset the status in case the task was done
            //   but threadRed is yet to be set to null.
            Thread.currentThread().isInterrupted();
        }
    }
    
}
