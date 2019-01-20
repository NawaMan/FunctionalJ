package functionalj.promise;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import functionalj.environments.AsyncRunner;
import functionalj.environments.Env;
import functionalj.function.Func0;
import functionalj.function.FuncUnit0;
import functionalj.function.FuncUnit1;
import functionalj.ref.Ref;
import functionalj.result.Result;
import lombok.val;


public class RetryableDeferActionCreator {
    
    private static final RetryableDeferActionCreator instance = new RetryableDeferActionCreator();
    
    public static final Ref<RetryableDeferActionCreator> current
            = Ref.of(RetryableDeferActionCreator.class)
            .defaultTo(RetryableDeferActionCreator.instance);
    
    public <DATA> DeferAction<DATA> createRetryDeferAction(
            boolean     interruptOnCancel,
            FuncUnit0   onStart,
            AsyncRunner runner,
            Retry<DATA> retry,
            Func0<DATA> supplier) {
        DeferAction<DATA> finalAction = DeferAction.createNew();
        
        val config = new DeferActionConfig()
                .interruptOnCancel(interruptOnCancel)
                .onStart(onStart)
                .runner(runner);
        
        val couter  = new AtomicInteger(retry.times());
        val builder = config.createBuilder(supplier);
        
        val onCompleteRef = new AtomicReference<FuncUnit1<Result<DATA>>>();
        val onComplete    = (FuncUnit1<Result<DATA>>)(result -> {
            if (result.isPresent()) {
                val value = result.value();
                finalAction.complete(value);
            } else {
                val count = couter.decrementAndGet();
                if (count == 0) {
                    finalAction.abort("Retry exceed: " + retry.times());
                } else {
                    val period = retry.waitTimeMilliSecond();
                    Env.time().sleep(period);
                    
                    builder
                    .build()
                    .onComplete(onCompleteRef.get())
                    .start();
                }
            }
        });
        onCompleteRef.set(onComplete);
        
        builder
        .build()
        .onComplete(onComplete)
        .start();
        
        return finalAction;
    }
    
}
