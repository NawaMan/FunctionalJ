package functionalj.promise;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import functionalj.environments.Console;
import functionalj.functions.Func1;
import functionalj.functions.FuncUnit1;
import functionalj.result.Result;
import lombok.val;

public class Loop<DATA> extends Retry<DATA> {
    
    private final Func1<Result<DATA>, Boolean> breakCondition;
    
    public Loop(Integer count) {
        super(Retry.RETRY_FOREVER, Retry.NO_WAIT);
        val counter = new AtomicInteger(count.intValue());
        val stopPredicate = (Func1<Result<DATA>, Boolean>)(result)->{
            return counter.decrementAndGet() <= 0;
        };
        this.breakCondition = stopPredicate;
    }
    public Loop(Func1<Result<DATA>, Boolean> breakCondition) {
        super(Retry.RETRY_FOREVER, Retry.NO_WAIT);
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
        
        val actionBuilder = config.createBuilder(supplier);
        val onCompleteRef   = new AtomicReference<FuncUnit1<Result<DATA>>>();
        val subscriptionRef = new AtomicReference<Subscription<DATA>>();
        val onComplete    = (FuncUnit1<Result<DATA>>)(result -> {
            val shouldBreak = breakCondition.apply(result);
            if (shouldBreak) {
                val value = result.value();
                finalAction.complete(value);
            } else {
                subscriptionRef.get().unsubscribe();
                
                actionBuilder
                .build()
                .subscribe(onCompleteRef.get())
                .start();
            }
        });
        onCompleteRef.set(onComplete);
        
        val action = actionBuilder.build();
        action.start();
        val promise = action.getPromise();
        val subscription = promise.subscribe(onComplete);
        subscriptionRef.set(subscription);
        
        return finalAction;
    }
    
}
