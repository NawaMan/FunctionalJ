package functionalj.promise;

import static functionalj.functions.Func.carelessly;
import static functionalj.promise.RaceResult.Race;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.functions.FuncUnit0;
import functionalj.functions.FuncUnit1;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.result.OnStart;
import functionalj.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public class DeferAction<DATA> extends UncompleteAction<DATA> implements Pipeable<HasPromise<DATA>> {
    
    public static <D> DeferAction<D> createNew() {
        return of((Class<D>)null);
    }
    public static <D> DeferAction<D> createNew(OnStart onStart) {
        return of((Class<D>)null, onStart);
    }
    public static <D> DeferAction<D> of(Class<D> clzz) {
        return new DeferAction<D>();
    }
    public static <D> DeferAction<D> of(Class<D> clzz, OnStart onStart) {
        return new DeferAction<D>(null, onStart);
    }
    
    public static <D> DeferAction<D> ofValue(D value) {
        val action = new DeferAction<D>();
        action.getPromise().makeComplete(value);
        return action;
    }
    
    public static DeferActionBuilder<Object> from(FuncUnit0 runnable) {
        return DeferActionConfig.current.value().createBuilder(runnable);
    }
    public static <D> DeferActionBuilder<D> from(Func0<D> supplier) {
        return DeferActionConfig.current.value().createBuilder(supplier);
    }
    
    public static PendingAction<Object> run(FuncUnit0 runnable) {
        return from(runnable)
                .start();
    }
    public static <D> PendingAction<D> run(Func0<D> supplier) {
        return from(supplier)
                .start();
    }
    
    @SafeVarargs
    public static <D> RaceResult<D> AnyOf(StartableAction<D> ... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> AnyOf(List<StartableAction<D>> actions) {
        return Race(actions);
    }
    @SafeVarargs
    public static <D> RaceResult<D> race(StartableAction<D> ... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> race(List<StartableAction<D>> actions) {
        return Race(actions);
    }
    
    public static <D> DeferAction<D> create(
            boolean            interruptOnCancel,
            Func0<D>           supplier,
            Runnable           onStart,
            Consumer<Runnable> runner) {
        return DeferActionCreator.current.value()
                .create(interruptOnCancel, supplier, onStart, runner);
    }
    
    private final Runnable task;
    
    private final DeferAction<?> parent;
    
    DeferAction() {
        this(null, (OnStart)null);
    }
    DeferAction(DeferAction<?> parent, Promise<DATA> promise) {
        super(promise);
        this.parent = parent;
        this.task   = null;
    }
    DeferAction(Runnable task, OnStart onStart) {
        super(onStart);
        this.parent = null;
        this.task   = task;
    }
    
    public PendingAction<DATA> start() {
        if (parent != null) {
            parent.start();
        } else {
            val isStarted = promise.start();
            
            if (!isStarted && (task != null))
                carelessly(task);
        }
        return new PendingAction<>(promise);
    }
    
    public HasPromise<DATA> __data() throws Exception {
        return this;
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
    public final <TARGET> DeferAction<TARGET> flatMap(Func1<? super DATA, ? extends HasPromise<? extends TARGET>> mapper) {
        return chain((Func1)mapper);
    }
    
    public final <TARGET> DeferAction<TARGET> chain(Func1<DATA, ? extends HasPromise<TARGET>> mapper) {
        val newPromise = promise.chain(mapper);
        return new DeferAction<TARGET>(this, (Promise<TARGET>)newPromise);
    }
    
    // TODO - Other F-M-FM methods.
    
}