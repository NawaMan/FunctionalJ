package functionalj.promise;

import static functionalj.functions.Func.carelessly;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.environments.Env;
import functionalj.functions.Func;
import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.functions.FuncUnit1;
import functionalj.ref.Ref;
import functionalj.result.Result;
import lombok.val;

// TODO : Make sure to allow an easy wrapping of all executions (onStart, task and notification) 
//   so that we can implement tracing and ref.

@SuppressWarnings("javadoc")
public class DeferAction<DATA> extends AbstractDeferAction<DATA> {
    
    public static final Ref<Creator> defaultCreator = Ref.dictactedTo(DeferAction::doFrom);
    public static final Ref<Creator> creator        = Ref.of(Creator.class).defaultFrom(defaultCreator);
    
    @FunctionalInterface
    public static interface Creator {
        
        public <D> DeferAction<D> create(Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner);
        
    }
    
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
        val runner = Env.asyncRunner.get();
        return from(Func.from(supplier), runner);
    }
    public static <D> DeferAction<D> from(Func0<D> supplier) {
        val runner = Env.asyncRunner.get();
        return from(supplier, runner);
    }
    public static <D> DeferAction<D> from(Func0<D> supplier, Consumer<Runnable> runner){
        return from(supplier, null, runner);
    }
    public static <D> DeferAction<D> from(Func0<D> supplier, Runnable onStart){
        val runner = Env.asyncRunner.get();
        return from(supplier, onStart, runner);
    }
    
    public static <D> DeferAction<D> from(Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner) {
        return creator.orGet(defaultCreator).create(supplier, onStart, runner);
    }
    private static <D> DeferAction<D> doFrom(Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner) {
        val promise = new Promise<D>();
        return new DeferAction<D>(promise, () -> {
            val runnable = (Runnable)() -> {
                if (!promise.isNotDone()) 
                    return;
                
                carelessly(onStart);
                
                val action = new PendingAction<D>(promise);
                Result.from(supplier)
                .ifException(action::fail)
                .ifValue    (action::complete);
            };
            runner.accept(runnable);
        });
    }
    
    public static <D> PendingAction<D> run(Func0<D> supplier) {
        val runner = Env.asyncRunner();
        return run(supplier, runner);
    }
    public static <D> PendingAction<D> run(Func0<D> supplier, Consumer<Runnable> runner){
        return run(supplier, null, runner);
    }
    public static <D> PendingAction<D> run(Func0<D> supplier, Runnable onStart){
        val runner = Env.asyncRunner();
        return run(supplier, onStart, runner);
    }
    public static <D> PendingAction<D> run(Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner) {
        return from(supplier, onStart, runner).start();
    }
    
    private final Runnable task;
    
    private final DeferAction<?> parent;
    
    protected DeferAction(Promise<DATA> promise) {
        this(promise, null);
    }
    DeferAction(DeferAction<?> parent, Promise<DATA> promise) {
        super(promise);
        this.parent = parent;
        this.task = null;
    }
    protected DeferAction(Promise<DATA> promise, Runnable task) {
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