package functionalj.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Consumer;
import java.util.function.Predicate;

import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.functions.FuncUnit0;
import functionalj.functions.FuncUnit1;
import functionalj.ref.Ref;
import functionalj.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public class DeferAction<DATA> extends UncompleteAction<DATA> {
    
    public static final Ref<Boolean> interruptOnCancel
            = Ref.ofValue(true);
    
    public static final Ref<DeferActionCreator> creator
            = Ref.of(DeferActionCreator.class)
            .defaultTo(DeferActionCreator.instance);
    
    public static <D> DeferAction<D> createNew() {
        return of((Class<D>)null);
    }
    public static <D> DeferAction<D> of(Class<D> clzz) {
        return new DeferAction<D>(new Promise<D>());
    }
    
    public static DeferActionBuilder<Object> from(FuncUnit0 runnable) {
        return new DeferActionBuilder<Object>(runnable);
    }
    public static <D> DeferActionBuilder<D> from(Func0<D> supplier) {
        return new DeferActionBuilder<D>(supplier);
    }
    
    public static PendingAction<Object> run(FuncUnit0 runnable) {
        return from(runnable)
                .start();
    }
    public static <D> PendingAction<D> run(Func0<D> supplier) {
        return from(supplier)
                .start();
    }
    
    public static <D> DeferAction<D> create(
            boolean            interruptOnCancel,
            Func0<D>           supplier,
            Runnable           onStart,
            Consumer<Runnable> runner) {
        return creator.value()
                .create(interruptOnCancel, supplier, onStart, runner);
    }
    
    private final Runnable task;
    
    private final DeferAction<?> parent;
    
    DeferAction(Promise<DATA> promise) {
        this(promise, null);
    }
    DeferAction(DeferAction<?> parent, Promise<DATA> promise) {
        super(promise);
        this.parent = parent;
        this.task = null;
    }
    DeferAction(Promise<DATA> promise, Runnable task) {
        super(promise);
        this.parent = null;
        this.task = task;
    }
    
    public PendingAction<DATA> start() {
        if (parent != null) {
            parent.start();
        } else {
            val isStarted = promise.isStarted();
            promise.start();
            
            if (!isStarted && (task != null))
                carelessly(task);
        }
        return new PendingAction<>(promise);
    }
    
    //== Subscription ==
    
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
    
    public DeferAction<DATA> subscribe(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.subscribe(Wait.forever(), resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> subscribe(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.subscribe(wait, resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> subscribe(
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> subscribe(
            Wait                          wait,
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<Subscription<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(wait, resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> eavesdrop(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> eavesdrop(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(wait, resultConsumer);
        return this;
    }
    
    //== Functional ==
    
    public final DeferAction<DATA> filter(Predicate<? super DATA> predicate) {
        val newPromise = promise.filter(predicate);
        return new DeferAction<DATA>(this, newPromise);
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> DeferAction<TARGET> map(Func1<? super DATA, ? extends TARGET> mapper) {
        val newPromise = promise.map(mapper);
        return new DeferAction<TARGET>(this, (Promise<TARGET>)newPromise);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final <TARGET> DeferAction<TARGET> flatMap(Func1<? super DATA, HasPromise<? extends TARGET>> mapper) {
        return chain((Func1)mapper);
    }
    
    public final <TARGET> DeferAction<TARGET> chain(Func1<DATA, HasPromise<TARGET>> mapper) {
        val newPromise = promise.flatMap(mapper);
        return new DeferAction<TARGET>(this, (Promise<TARGET>)newPromise);
    }
    
    // TODO - Other F-M-FM methods.
    
}