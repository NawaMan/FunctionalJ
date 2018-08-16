package functionalj.types.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Consumer;

import lombok.val;

@SuppressWarnings("javadoc")
public class DeferAction<DATA> extends AbstractDeferAction<DATA> {
    
    public static <D> DeferAction<D> createNew() {
        return of((Class<D>)null);
    }
    public static <D> DeferAction<D> of(Class<D> clzz) {
        val promise = new Promise<D>();
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
    
}