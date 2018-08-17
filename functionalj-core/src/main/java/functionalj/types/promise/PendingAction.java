package functionalj.types.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Consumer;

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
    
    public PendingAction<DATA> peek(Consumer<Promise<DATA>> consumer) {
        return use(consumer);
    }
    public PendingAction<DATA> use(Consumer<Promise<DATA>> consumer) {
        carelessly(()->{
            consumer.accept(promise);
        });
        
        return this;
    }
    
    public PendingAction<DATA> abortNoSubsriptionAfter(Wait wait) {
        promise.abortNoSubsriptionAfter(wait);
        return this;
    }
    
    public PendingAction<DATA> subscribe(Consumer<Result<DATA>> resultConsumer) {
        promise.subscribe(Wait.forever(), resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> subscribe(Wait wait, Consumer<Result<DATA>> resultConsumer) {
        promise.subscribe(wait, resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> subscribe(
            Consumer<Result<DATA>>       resultConsumer,
            Consumer<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public PendingAction<DATA> subscribe(
            Wait                         wait,
            Consumer<Result<DATA>>       resultConsumer,
            Consumer<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(wait, resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public PendingAction<DATA> eavesdrop(Consumer<Result<DATA>> resultConsumer) {
        promise.eavesdrop(resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> eavesdrop(Wait wait, Consumer<Result<DATA>> resultConsumer) {
        promise.eavesdrop(wait, resultConsumer);
        return this;
    }
    
}