package functionalj.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Predicate;

import functionalj.functions.Func1;
import functionalj.functions.FuncUnit1;
import functionalj.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public class PendingAction<DATA> extends UncompleteAction<DATA> {
    
    PendingAction(Promise<DATA> promise) {
        super(promise);
    }
    
    public PendingAction<DATA> start() {
        return this;
    }
    
    //== Subscription ==
    
    public PendingAction<DATA> use(FuncUnit1<Promise<DATA>> consumer) {
        carelessly(()->{
            consumer.accept(promise);
        });
        
        return this;
    }
    
    public PendingAction<DATA> abortNoSubsriptionAfter(Wait wait) {
        promise.abortNoSubsriptionAfter(wait);
        return this;
    }
    
    public PendingAction<DATA> subscribe(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.subscribe(Wait.forever(), resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> subscribe(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.subscribe(wait, resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> subscribe(
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public PendingAction<DATA> subscribe(
            Wait                          wait,
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(wait, resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public PendingAction<DATA> eavesdrop(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> eavesdrop(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(wait, resultConsumer);
        return this;
    }
    
    //== Functional ==
    
    public final PendingAction<DATA> filter(Predicate<? super DATA> predicate) {
        val newPromise = promise.filter(predicate);
        return new PendingAction<DATA>(newPromise);
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> PendingAction<TARGET> map(Func1<? super DATA, ? extends TARGET> mapper) {
        val newPromise = promise.map(mapper);
        return new PendingAction<TARGET>((Promise<TARGET>)newPromise);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public final <TARGET> PendingAction<TARGET> flatMap(Func1<? super DATA, ? extends HasPromise<? extends TARGET>> mapper) {
        return chain((Func1)mapper);
    }
    
    public final <TARGET> PendingAction<TARGET> chain(Func1<DATA, ? extends HasPromise<TARGET>> mapper) {
        val newPromise = promise.flatMap(mapper);
        return new PendingAction<TARGET>((Promise<TARGET>)newPromise);
    }
    
    // TODO - Other F-M-FM methods.
    
    
}