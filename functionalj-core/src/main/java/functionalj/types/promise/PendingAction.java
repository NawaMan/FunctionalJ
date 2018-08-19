package functionalj.types.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.FuncUnit1;
import functionalj.types.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public class PendingAction<DATA> extends AbstractDeferAction<DATA> {
    
    PendingAction(Promise<DATA> promise) {
        super(promise);
    }
    
    public CompletedAction<DATA> abort() {
        promise.abort();
        return new CompletedAction<DATA>(promise);
    }
    public CompletedAction<DATA> abort(String message) {
        promise.abort(message);
        return new CompletedAction<DATA>(promise);
    }
    public CompletedAction<DATA> abort(Throwable cause) {
        promise.abort(cause);
        return new CompletedAction<DATA>(promise);
    }
    public CompletedAction<DATA> abort(String message, Throwable cause) {
        promise.abort(message, cause);
        return new CompletedAction<DATA>(promise);
    }
    
    // For internal use only -- This will be wrong if the result is not ready.
    CompletedAction<DATA> completeWith(Result<DATA> result) {
        if (result.isException())
             promise.makeFail    (result.getException());
        else promise.makeComplete(result.get());
        
        return new CompletedAction<DATA>(promise);
    }
    
    public CompletedAction<DATA> complete(DATA value) {
        promise.makeComplete(value);
        return new CompletedAction<DATA>(promise);
    }
    
    public CompletedAction<DATA> fail(Exception exception) {
        promise.makeFail(exception);
        return new CompletedAction<DATA>(promise);
    }
    
    // TODO - Add methods that allow the caller to know if the proceeding success or fail.
    // TODO - Add methods that the proceeding MUST be done or exception should be thrown.
    
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
    
    public final DeferAction<DATA> filter(Predicate<? super DATA> predicate) {
        val newPromise = promise.filter(predicate);
        return new DeferAction<DATA>(newPromise);
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> DeferAction<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        val newPromise = promise.map(mapper);
        return new DeferAction<TARGET>((Promise<TARGET>)newPromise);
    }
    
    public final <TARGET> DeferAction<TARGET> flatMap(Function<? super DATA, HasPromise<? extends TARGET>> mapper) {
        val newPromise = promise.flatMap(mapper);
        return new DeferAction<TARGET>((Promise<TARGET>)newPromise);
    }
    
    // TODO - Other F-M-FM methods.
    
    
}