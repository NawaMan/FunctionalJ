package functionalj.promise;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.result.Result;
import lombok.val;

public class Loop<DATA> extends Retry<DATA> {
    
    private final Integer                      count;
    private final Func1<Result<DATA>, Boolean> breakCondition;
    
    public Loop(int count) {
        super(Retry.RETRY_FOREVER, Retry.NO_WAIT);
        this.count          = count;
        this.breakCondition = null;
    }
    public Loop(Func1<Result<DATA>, Boolean> breakCondition) {
        super(Retry.RETRY_FOREVER, Retry.NO_WAIT);
        this.count          = null;
        this.breakCondition = requireNonNull(breakCondition);
    }
    
    @Override
    DeferAction<DATA> create(DeferActionBuilder<DATA> builder) {
        val interruptOnCancel = builder.interruptOnCancel();
        val supplier          = builder.supplier();
        val onStart           = builder.onStart();
        val runner            = builder.runner();
        
        DeferAction<DATA> finalAction = DeferAction.createNew();
        
        val config = new DeferActionConfig()
                .interruptOnCancel(interruptOnCancel)
                .onStart(onStart)
                .runner(runner);
        
        val shouldStop      = shouldStop();
        val actionBuilder   = config.createBuilder(supplier);
        val subscriptionRef = new AtomicReference<SubscriptionRecord<DATA>>();
        val onComplete      = new OnComplete<>(actionBuilder, shouldStop, finalAction, subscriptionRef::get);
        
        val action       = actionBuilder.build();
        val subscription = action.getPromise().subscribe(onComplete);
        action.start();
        subscriptionRef.set(subscription);
        
        return finalAction;
    }
    private Func1<Result<DATA>, Boolean> shouldStop() {
        if (breakCondition != null)
            return breakCondition;
        
        val counter = new AtomicInteger(count.intValue());
        val stopPredicate = (Func1<Result<DATA>, Boolean>)(result)->{
            return counter.decrementAndGet() <= 0;
        };
        return stopPredicate;
    }
    
    static class OnComplete<DATA> implements FuncUnit1<Result<DATA>> {
        
        private final DeferActionBuilder<DATA>           actionBuilder;
        private final Func1<Result<DATA>, Boolean>       shouldStop;
        private final DeferAction<DATA>                  finalAction;
        private final Supplier<SubscriptionRecord<DATA>> subscriptionRef;
        
        public OnComplete(
                DeferActionBuilder<DATA>           actionBuilder,
                Func1<Result<DATA>, Boolean>       shouldStop,
                DeferAction<DATA>                  finalAction,
                Supplier<SubscriptionRecord<DATA>> subscriptionRef) {
            this.actionBuilder = actionBuilder;
            this.shouldStop = shouldStop;
            this.finalAction = finalAction;
            this.subscriptionRef = subscriptionRef;
        }
        
        @Override
        public void acceptUnsafe(Result<DATA> result) throws Exception {
            val shouldBreak = shouldStop.apply(result);
            if (shouldBreak) {
                val value = result.value();
                finalAction.complete(value);
            } else {
                subscriptionRef.get().unsubscribe();
                
                actionBuilder
                .build()
                .subscribe(this)
                .start();
            }
        }
        
    }
    
}
