// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Objects.requireNonNull;

import functionalj.environments.AsyncRunner;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.FuncUnit0;
import functionalj.result.Result;
import functionalj.task.Task;
import lombok.val;

public class DeferActionBuilder<DATA> implements Task<DATA> {
    
    private static final FuncUnit0 DO_NOTHING = ()->{};
    
    private final String      toString;
    private final Func0<DATA> supplier;
    private boolean           interruptOnCancel = true;
    private FuncUnit0         onStart           = DO_NOTHING;
    private AsyncRunner       runner            = null;
    
    @SuppressWarnings("unchecked")
    private Retry<DATA> retry = (Retry<DATA>)Retry.noRetry;
    
    public static final <D> DeferActionBuilder<D> from(FuncUnit0 runnable) {
        return new DeferActionBuilder<>(runnable);
    }
    public static final <D> DeferActionBuilder<D> from(Func0<D> supplier) {
        return new DeferActionBuilder<>(supplier);
    }
    public static final <D> DeferActionBuilder<D> from(String toString, FuncUnit0 runnable) {
        return new DeferActionBuilder<>(toString, runnable);
    }
    public static final <D> DeferActionBuilder<D> from(String toString, Func0<D> supplier) {
        return new DeferActionBuilder<>(toString, supplier);
    }
    
    public DeferActionBuilder(FuncUnit0 runnable) {
        this(null, runnable);
    }
    public DeferActionBuilder(String toString, FuncUnit0 runnable) {
        this(toString, runnable.thenReturnNull());
    }
    
    public DeferActionBuilder(Func0<DATA> supplier) {
        this(null, supplier);
    }
    public DeferActionBuilder(String toString, Func0<DATA> supplier) {
        this.toString = (toString != null) ? toString : "Task#" + supplier.toString();
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
    
    AsyncRunner runner() {
        return this.runner;
    }
    
    public DeferActionBuilder<DATA> runner(AsyncRunner runner) {
        this.runner = runner;
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
    
    public DeferAction<DATA> build() {
        return retry.create(this);
    }
    
    public DeferAction<DATA> createAction() {
        return build();
    }
    
    @Override
    public String toString() {
        return toString;
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
