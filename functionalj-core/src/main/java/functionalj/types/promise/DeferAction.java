package functionalj.types.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Consumer;

import functionalj.types.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public class DeferAction<DATA> extends AbstractDeferAction<DATA> {
    
    public static <D> DeferAction<D> createNew() {
        return of((Class<D>)null);
    }
    public static <D> DeferAction<D> of(Class<D> clzz) {
        return of(new Promise<D>());
    }
    public static <D> DeferAction<D> of(Promise<D> promise) {
        val control = new DeferAction<D>(promise);
        return control;
    }
    
    DeferAction(Promise<DATA> promise) {
        super(promise);
    }
    
    public PendingAction<DATA> start() {
        promise.start();
        return new PendingAction<>(promise);
    }
    
    public DeferAction<DATA> peek(Consumer<Promise<DATA>> consumer) {
        return use(consumer);
    }
    public DeferAction<DATA> use(Consumer<Promise<DATA>> consumer) {
        carelessly(()->{
            consumer.accept(promise);
        });
        
        return this;
    }
    
    public DeferAction<DATA> abortNoSubsriptionAfter(Wait wait) {
        promise.abortNoSubsriptionAfter(wait);
        return this;
    }
    
    public DeferAction<DATA> subscribe(Consumer<Result<DATA>> resultConsumer) {
        promise.subscribe(Wait.forever(), resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> subscribe(Wait wait, Consumer<Result<DATA>> resultConsumer) {
        promise.subscribe(wait, resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> subscribe(
            Consumer<Result<DATA>>       resultConsumer,
            Consumer<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> subscribe(
            Wait                         wait,
            Consumer<Result<DATA>>       resultConsumer,
            Consumer<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(wait, resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> eavesdrop(Consumer<Result<DATA>> resultConsumer) {
        promise.eavesdrop(resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> eavesdrop(Wait wait, Consumer<Result<DATA>> resultConsumer) {
        promise.eavesdrop(wait, resultConsumer);
        return this;
    }
    
}