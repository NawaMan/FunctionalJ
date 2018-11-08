package functionalj.promise;

import static functionalj.function.Func.carelessly;
import static functionalj.function.Func.f;
import static functionalj.list.FuncList.listOf;
import static functionalj.promise.RaceResult.Race;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit0;
import functionalj.function.FuncUnit1;
import functionalj.function.NamedExpression;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
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
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            Func2<T1, T2, D>                merger) {
        val merge = f((Result[] results)-> {
            val result1 = (Result<T1>)results[0];
            val result2 = (Result<T2>)results[1];
            val mergedResult = Result.ofResults(result1, result2, merger);
            return (Result<D>)mergedResult;
        });
        val promises = listOf(promise1, promise2);
        val combiner = new Combiner(promises, merge);
        val action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            Func3<T1, T2, T3, D>            merger) {
        val merge = f((Result[] results)-> {
            val result1 = (Result<T1>)results[0];
            val result2 = (Result<T2>)results[1];
            val result3 = (Result<T3>)results[2];
            val mergedResult = Result.ofResults(result1, result2, result3, merger);
            return (Result<D>)mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3);
        val combiner = new Combiner(promises, merge);
        val action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            Func4<T1, T2, T3, T4, D>        merger) {
        val merge = f((Result[] results)-> {
            val result1 = (Result<T1>)results[0];
            val result2 = (Result<T2>)results[1];
            val result3 = (Result<T3>)results[2];
            val result4 = (Result<T4>)results[3];
            val mergedResult = Result.ofResults(result1, result2, result3, result4, merger);
            return (Result<D>)mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4);
        val combiner = new Combiner(promises, merge);
        val action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4, T5> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            NamedExpression<HasPromise<T5>> promise5,
            Func5<T1, T2, T3, T4, T5, D>    merger) {
        val merge = f((Result[] results)-> {
            val result1 = (Result<T1>)results[0];
            val result2 = (Result<T2>)results[1];
            val result3 = (Result<T3>)results[2];
            val result4 = (Result<T4>)results[3];
            val result5 = (Result<T5>)results[4];
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, merger);
            return (Result<D>)mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5);
        val combiner = new Combiner(promises, merge);
        val action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4, T5, T6> DeferAction<D> from(
            NamedExpression<HasPromise<T1>>  promise1,
            NamedExpression<HasPromise<T2>>  promise2,
            NamedExpression<HasPromise<T3>>  promise3,
            NamedExpression<HasPromise<T4>>  promise4,
            NamedExpression<HasPromise<T5>>  promise5,
            NamedExpression<HasPromise<T6>>  promise6,
            Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        val merge = f((Result[] results)-> {
            val result1 = (Result<T1>)results[0];
            val result2 = (Result<T2>)results[1];
            val result3 = (Result<T3>)results[2];
            val result4 = (Result<T4>)results[3];
            val result5 = (Result<T5>)results[4];
            val result6 = (Result<T6>)results[5];
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, merger);
            return (Result<D>)mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5, promise6);
        val combiner = new Combiner(promises, merge);
        val action   = combiner.getDeferAction();
        return action;
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
            FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
        val subscription = promise.subscribe(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> subscribe(
            Wait                          wait,
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
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
    
    
    
    
    //== Internal ==
    
    @SuppressWarnings("rawtypes")
    private static class Combiner<D> {
        
        private final Func1<Result[], Result<D>> mergeFunc;
        // TODO - Add Else ... which will be called with the current value when unsuccessfull.
        
        private final DeferAction<D> action;
        private final int            count;
        private final Result[]       results;
        private final SubscriptionRecord[] subscriptions;
        private final AtomicBoolean  isDone;
        private final Promise<D>     promise;
        
        Combiner(FuncList<NamedExpression<HasPromise<Object>>> hasPromises,
                 Func1<Result[], Result<D>>                    mergeFunc) {
            this.mergeFunc     = mergeFunc;
            this.count         = hasPromises.size();
            this.results       = new Result[count];
            this.subscriptions = new SubscriptionRecord[count];
            this.isDone        = new AtomicBoolean(false);
            
            val promises = hasPromises
            .map(promise -> promise.getExpression())
            .map(promise -> promise.getPromise());
            
            this.action = DeferAction.of((Class<D>)null, OnStart.run(()->{
                promises.forEach(promise -> promise.start());
            }));
            
            promises
            .mapWithIndex    ((index, promise) -> promise.subscribe(result -> processResult(index, result)))
            .forEachWithIndex((index, sub)     -> subscriptions[index] = sub);
            
            this.promise = action.getPromise();
            this.promise.eavesdrop(result->{
                if (result.isCancelled()) {
                    unsbscribeAll();
                }
            });
        }
        
        DeferAction<D> getDeferAction() {
            return action;
        }
        
        private <T> void processResult(int index, Result<T> result) {
            if (isDone.get())
                return;
            
            if (result.isCancelled())
                doneAsCancelled(index);
            
            if (result.isNotReady())
                doneAsNotReady(index, result);
            
            if (result.isException())
                doneAsException(index, result);
            
            results[index] = result;
            
            if (!(count == Stream.of(results).filter(Objects::nonNull).count()))
                return;
            
            if (!isDone.compareAndSet(false, true))
                return;
            
            val mergedResult = mergeFunc.apply(results);
            action.completeWith(mergedResult);
        }
        
        private void unsbscribeAll() {
            for(val subscription : subscriptions) {
                if (subscription != null)
                    subscription.unsubscribe();
            }
        }
        
        private void doneAsCancelled(int index) {
            if (!isDone.compareAndSet(false, true))
                return;
            
            action.abort("Promise#" + index);
            unsbscribeAll();
        }
        
        private void doneAsNotReady(
                int    index, 
                Result result) {
            if (!isDone.compareAndSet(false, true))
                return;
            
            action.abort(
                    "Promise#" + index, 
                    new IllegalStateException(
                            "Result cannot be in 'not ready' at this point: " + result.getStatus(),
                            result.getException()));
            unsbscribeAll();
        }
        
        private void doneAsException(
                int index,
                Result  result) {
            if (!isDone.compareAndSet(false, true))
                return;
            
            action.fail(new PromisePartiallyFailException(index, count, result.getException()));
            unsbscribeAll();
        }
    }
    
}