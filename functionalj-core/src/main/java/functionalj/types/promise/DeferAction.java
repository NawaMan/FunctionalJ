package functionalj.types.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Consumer;
import java.util.function.Supplier;

import functionalj.functions.Func;
import functionalj.functions.Func0;
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
        val control = new DeferAction<D>(promise, null);
        return control;
    }
    
    public static <D> DeferAction<D> from(Supplier<D> supplier) {
        return from(Func.from(supplier), AsyncRunner.threadFactory);
    }
    public static <D> DeferAction<D> from(Func0<D> supplier) {
        return from(supplier, AsyncRunner.threadFactory);
    }
    public static <D> DeferAction<D> from(Func0<D> supplier, Consumer<Runnable> runner){
        return from(supplier, null, runner);
    }
    public static <D> DeferAction<D> from(Func0<D> supplier, Runnable onStart){
        return from(supplier, onStart, AsyncRunner.threadFactory);
    }
    public static <D> DeferAction<D> from(Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner) {
        val promise = new Promise<D>();
        return new DeferAction<D>(promise, () -> {
            val runnable = new Runnable() {
                @Override
                public void run() {
                    if (!promise.isNotDone()) 
                        return;
                    
                    carelessly(onStart);
                    
                    val action = new PendingAction<D>(promise);
                    Result.from(supplier)
                    .ifException(action::fail)
                    .ifValue    (action::complete);
                }
            };
            runner.accept(runnable);
        });
    }
    
    public static <D> PendingAction<D> run(Func0<D> supplier) {
        return run(supplier, AsyncRunner.threadFactory);
    }
    public static <D> PendingAction<D> run(Func0<D> supplier, Consumer<Runnable> runner){
        return run(supplier, null, runner);
    }
    public static <D> PendingAction<D> run(Func0<D> supplier, Runnable onStart){
        return run(supplier, onStart, AsyncRunner.threadFactory);
    }
    public static <D> PendingAction<D> run(Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner) {
        return from(supplier, onStart, runner).start();
    }
    
    private final Runnable task;
    
    protected DeferAction(Promise<DATA> promise, Runnable task) {
        super(promise);
        this.task = task;
    }
    
    public PendingAction<DATA> start() {
        val isStarted = promise.isStarted();
        promise.start();
        
        if (!isStarted && (task != null))
            carelessly(task);
        
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