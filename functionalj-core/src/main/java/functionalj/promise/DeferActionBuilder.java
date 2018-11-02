package functionalj.promise;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import functionalj.environments.Env;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.FuncUnit0;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import lombok.val;
import lombok.experimental.Delegate;

public class DeferActionBuilder<DATA> extends StartableAction<DATA> implements Pipeable<HasPromise<DATA>> {
    
    private static final FuncUnit0 DO_NOTHING = ()->{};
    
    private final Func0<DATA>  supplier;
    private boolean            interruptOnCancel = true;
    private FuncUnit0          onStart           = DO_NOTHING;
    private Consumer<Runnable> runner            = Env.async();
    
    @SuppressWarnings("unchecked")
    private Retry<DATA> retry = (Retry<DATA>)Retry.noRetry;
    
    DeferActionBuilder(FuncUnit0 supplier) {
        this(supplier.thenReturnNull());
    }
    
    DeferActionBuilder(Func0<DATA> supplier) {
        this.supplier = requireNonNull(supplier);
    }
    
    Func0<DATA> supplier() {
        return this.supplier;
    }
    
    boolean interruptOnCancel() {
        return this.interruptOnCancel;
    }
    
    public DeferActionBuilder<DATA> interruptOnCancel(boolean interruptOnCancel) {
        this.interruptOnCancel = interruptOnCancel;
        return this;
    }
    
    FuncUnit0 onStart() {
        return this.onStart;
    }
    
    public DeferActionBuilder<DATA> onStart(FuncUnit0 onStart) {
        this.onStart = (onStart != null) ? onStart : DO_NOTHING;
        return this;
    }
    
    Consumer<Runnable> runner() {
        return this.runner;
    }
    
    public DeferActionBuilder<DATA> runner(Consumer<Runnable> runner) {
        this.runner = (runner != null) ? runner : Env.async();
        return this;
    }
    
    public DeferActionBuilder<DATA> loopTimes(int times) {
        this.retry = new Loop<DATA>(times);
        return this;
    }
    public DeferActionBuilder<DATA> loopUntil(Func1<Result<DATA>, Boolean> stopPredicate) {
        this.retry = new Loop<DATA>(stopPredicate);
        return this;
    }
    
    public DeferActionBuilder<DATA> retry(int times, long periodMillisecond) {
        this.retry = new Retry<DATA>(times, periodMillisecond);
        return this;
    }
    public DeferActionBuilder<DATA> retry(Retry<DATA> retry) {
        this.retry = (retry != null) ? retry : Retry.defaultRetry();
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public DeferActionBuilder<DATA> noRetry() {
        this.retry = Retry.noRetry;
        return this;
    }
    
    public WaitRetryBuilder<DATA> retryForever() {
        return new WaitRetryBuilder<DATA>(this, Retry.RETRY_FOREVER);
    }
    
    public RetryBuilderTimes<DATA> retry(int times) {
        return new RetryBuilderTimes<DATA>(this, times);
    }
    
    @Delegate
    public DeferAction<DATA> build() {
        return retry.create(this);
    }
    
    //== Aux classes ==
    
    public static class RetryBuilderTimes<DATA> {
        private final DeferActionBuilder<DATA> actionBuilder;
        private final int                      times;
        
        RetryBuilderTimes(DeferActionBuilder<DATA> actionBuilder, int times) {
            this.actionBuilder = actionBuilder;
            this.times         = times;
        }
        
        public WaitRetryBuilder<DATA> times() {
            return new WaitRetryBuilder<DATA>(actionBuilder, times);
        }
        
    }
    
    public static class WaitRetryBuilder<DATA> {
        private final DeferActionBuilder<DATA> actionBuilder;
        private final int                      times;
        
        WaitRetryBuilder(DeferActionBuilder<DATA> actionBuilder, int times) {
            this.actionBuilder = actionBuilder;
            this.times         = times;
        }
        
        public DeferActionBuilder<DATA> noWaitInBetween() {
            actionBuilder.retry = new Retry<DATA>(times, Retry.NO_WAIT);
            return actionBuilder;
        }
        
        public WaitRetryBuilderUnit<DATA> waitFor(long period) {
            return new WaitRetryBuilderUnit<DATA>(actionBuilder, times, period);
        }
        
    }
    
    public static class WaitRetryBuilderUnit<DATA> {
        private final DeferActionBuilder<DATA> actionBuilder;
        private final int                      times;
        private final long                     period;
        
        WaitRetryBuilderUnit(DeferActionBuilder<DATA> actionBuilder, int times, long period) {
            this.actionBuilder = actionBuilder;
            this.times         = times;
            this.period        = period;
        }
        
        public DeferActionBuilder<DATA> milliseconds() {
            val retry = new Retry<DATA>(times, period);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> seconds() {
            val retry = new Retry<DATA>(times, period * 1000);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> minutes() {
            val retry = new Retry<DATA>(times, period * 1000 * 60);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> hours() {
            val retry = new Retry<DATA>(times, period * 1000 * 60 * 60);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> days() {
            val retry = new Retry<DATA>(times, period * 1000 * 60 * 60 * 24);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> weeks() {
            val retry = new Retry<DATA>(times, period * 1000 * 60 * 60 * 24);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
    }
    
}
