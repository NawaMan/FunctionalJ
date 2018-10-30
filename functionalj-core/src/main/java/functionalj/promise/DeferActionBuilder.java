package functionalj.promise;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import functionalj.environments.Env;
import functionalj.functions.Func0;
import functionalj.functions.FuncUnit0;
import lombok.val;
import lombok.experimental.Delegate;

public class DeferActionBuilder<DATA> extends StartableAction<DATA> {
    
    private static final FuncUnit0 DO_NOTHING = ()->{};
    
    private final Func0<DATA>  supplier;
    private boolean            interruptOnCancel = true;
    private FuncUnit0          onStart           = DO_NOTHING;
    private Consumer<Runnable> runner            = Env.async();
    private Retry              retry             = Retry.noRetry;
    
    DeferActionBuilder(FuncUnit0 supplier) {
        this(supplier.thenReturnNull());
    }
    
    DeferActionBuilder(Func0<DATA> supplier) {
        this.supplier = requireNonNull(supplier);
    }
    
    public DeferActionBuilder<DATA> interruptOnCancel(boolean interruptOnCancel) {
        this.interruptOnCancel = interruptOnCancel;
        return this;
    }
    
    public DeferActionBuilder<DATA> onStart(FuncUnit0 onStart) {
        this.onStart = (onStart != null) ? onStart : DO_NOTHING;
        return this;
    }
    
    public DeferActionBuilder<DATA> runner(Consumer<Runnable> runner) {
        this.runner = (runner != null) ? runner : Env.async();
        return this;
    }
    public DeferActionBuilder<DATA> retry(int times, long periodMillisecond) {
        this.retry = new Retry(times, periodMillisecond);
        return this;
    }
    public DeferActionBuilder<DATA> retry(Retry retry) {
        this.retry = (retry != null) ? retry : Retry.current.orElse(Retry.noRetry);
        return this;
    }
    
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
        if (retry.times() == Retry.NO_RETRY)
            return DeferAction.create(interruptOnCancel, supplier, onStart, runner);
        
        return RetryDeferActionCreator.current.value()
                .createRetryDeferAction(interruptOnCancel, onStart, runner, retry, supplier);
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
            actionBuilder.retry = new Retry(times, Retry.NO_WAIT);
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
            val retry = new Retry(times, period);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> seconds() {
            val retry = new Retry(times, period * 1000);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> minutes() {
            val retry = new Retry(times, period * 1000 * 60);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> hours() {
            val retry = new Retry(times, period * 1000 * 60 * 60);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> days() {
            val retry = new Retry(times, period * 1000 * 60 * 60 * 24);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
        public DeferActionBuilder<DATA> weeks() {
            val retry = new Retry(times, period * 1000 * 60 * 60 * 24);
            actionBuilder.retry = retry;
            return actionBuilder;
        }
        
    }
    
}
