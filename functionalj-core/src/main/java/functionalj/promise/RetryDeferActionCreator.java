package functionalj.promise;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import functionalj.environments.Env;
import functionalj.function.Func0;
import functionalj.function.FuncUnit0;
import functionalj.function.FuncUnit1;
import functionalj.ref.Ref;
import functionalj.result.Result;
import lombok.val;


public class RetryDeferActionCreator {
    
    private static final RetryDeferActionCreator instance = new RetryDeferActionCreator();
    
    public static final Ref<RetryDeferActionCreator> current
            = Ref.of(RetryDeferActionCreator.class)
            .defaultTo(RetryDeferActionCreator.instance);
    
    public <DATA> DeferAction<DATA> createRetryDeferAction(
            boolean            interruptOnCancel,
            FuncUnit0          onStart,
            Consumer<Runnable> runner,
            Retry<DATA>        retry,
            Func0<DATA>        supplier) {
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
                    .subscribe(onCompleteRef.get())
                    .start();
                }
            }
        });
        onCompleteRef.set(onComplete);
        
        builder
        .build()
        .subscribe(onComplete)
        .start();
        
        return finalAction;
    }
    
}
