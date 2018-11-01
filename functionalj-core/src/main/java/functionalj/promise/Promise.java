package functionalj.promise;

import static functionalj.functions.Func.carelessly;
import static functionalj.functions.Func.f;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.functions.FuncUnit1;
import functionalj.functions.NamedExpression;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.ref.Ref;
import functionalj.result.HasResult;
import functionalj.result.Result;
import lombok.val;

// TODO - See what we can do with retry.

@SuppressWarnings("javadoc")
public class Promise<DATA> implements HasPromise<DATA>, HasResult<DATA>, Pipeable<HasPromise<DATA>> {
    
    private static final int INITIAL_CAPACITY = 2;
    
    public static final Ref<Long> waitTimeout = Ref.ofValue(-1L);
    
    public static <D> Promise<D> ofResult(HasResult<D> asResult) {
        if (asResult instanceof HasPromise)
            return ((HasPromise<D>)asResult).getPromise();
        
        return DeferAction
                .from(()->asResult.getResult().value())
                .build()
                .getPromise();
    }
    public static <D> Promise<D> ofValue(D value) {
        return DeferAction.of((Class<D>)null)
                .start()
                .complete(value)
                .getPromise();
    }
    
    public static <D> Promise<D> ofException(Exception exception) {
        return DeferAction.of((Class<D>)null)
                .start()
                .fail(exception)
                .getPromise();
    }
    
    public static <D> Promise<D> ofAborted() {
        return DeferAction.of((Class<D>)null)
                .start()
                .abort()
                .getPromise();
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2> Promise<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            Func2<T1, T2, D>                merger) {
        return new Combiner(
                FuncList.of(promise1, promise2),
                f((Result[] results)-> {
                    val result1 = (Result<T1>)results[0];
                    val result2 = (Result<T2>)results[1];
                    val mergedResult = Result.ofResults(result1, result2, merger);
                    return mergedResult;
                }))
                .getPromise();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3> Promise<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            Func3<T1, T2, T3, D>            merger) {
        return new Combiner(
                FuncList.of(promise1, promise2, promise3),
                f((Result[] results)-> {
                    val result1 = (Result<T1>)results[0];
                    val result2 = (Result<T2>)results[1];
                    val result3 = (Result<T3>)results[2];
                    val mergedResult = Result.ofResults(result1, result2, result3, merger);
                    return mergedResult;
                }))
                .getPromise();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4> Promise<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            Func4<T1, T2, T3, T4, D>        merger) {
        return new Combiner(
                FuncList.of(promise1, promise2, promise3, promise4),
                f((Result[] results)-> {
                    val result1 = (Result<T1>)results[0];
                    val result2 = (Result<T2>)results[1];
                    val result3 = (Result<T3>)results[2];
                    val result4 = (Result<T4>)results[3];
                    val mergedResult = Result.ofResults(result1, result2, result3, result4, merger);
                    return mergedResult;
                }))
                .getPromise();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4, T5> Promise<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            NamedExpression<HasPromise<T5>> promise5,
            Func5<T1, T2, T3, T4, T5, D>    merger) {
        return new Combiner(
                FuncList.of(promise1, promise2, promise3, promise4),
                f((Result[] results)-> {
                    val result1 = (Result<T1>)results[0];
                    val result2 = (Result<T2>)results[1];
                    val result3 = (Result<T3>)results[2];
                    val result4 = (Result<T4>)results[3];
                    val result5 = (Result<T5>)results[4];
                    val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, merger);
                    return mergedResult;
                }))
                .getPromise();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4, T5, T6> Promise<D> from(
            NamedExpression<HasPromise<T1>>  promise1,
            NamedExpression<HasPromise<T2>>  promise2,
            NamedExpression<HasPromise<T3>>  promise3,
            NamedExpression<HasPromise<T4>>  promise4,
            NamedExpression<HasPromise<T5>>  promise5,
            NamedExpression<HasPromise<T5>>  promise6,
            Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        return new Combiner(
                FuncList.of(promise1, promise2, promise3, promise4, promise5, promise6),
                f((Result[] results)-> {
                    val result1 = (Result<T1>)results[0];
                    val result2 = (Result<T2>)results[1];
                    val result3 = (Result<T3>)results[2];
                    val result4 = (Result<T4>)results[3];
                    val result5 = (Result<T5>)results[4];
                    val result6 = (Result<T6>)results[5];
                    val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, merger);
                    return mergedResult;
                }))
                .getPromise();
    }
    
    
    // DATA
    //    StartableAction -> NOT START
    //    consumer        -> Pending
    //    result          -> done.
    //      result.cancelled -> aborted
    //      result.completed -> completed
    private final Map<Subscription<DATA>, FuncUnit1<Result<DATA>>> consumers     = new ConcurrentHashMap<>();
    private final List<FuncUnit1<Result<DATA>>>                    eavesdroppers = new ArrayList<>(INITIAL_CAPACITY);
    
    private final AtomicReference<Object> dataRef = new AtomicReference<>();
    
    Promise(StartableAction<DATA> action) {
        dataRef.set(action);
    }
    
    Promise(@SuppressWarnings("rawtypes") Promise parent) {
        this.dataRef.set(parent);
    }
    
    @Override
    public Promise<DATA> getPromise() {
        return this;
    }
    
    public HasPromise<DATA> __data() throws Exception {
        return this;
    }
    
    public final PromiseStatus getStatus() {
        val data = dataRef.get();
        if (data instanceof Promise) {
            @SuppressWarnings("unchecked")
            Promise<DATA> promise = (Promise<DATA>)data;
            return promise.getStatus();
        }
        
        if (data instanceof StartableAction)
            return PromiseStatus.NOT_STARTED;
        if (consumers == data)
            return PromiseStatus.PENDING;
        if (data instanceof Result) {
            @SuppressWarnings("unchecked")
            val result = (Result<DATA>)data;
            if (result.isCancelled())
                return PromiseStatus.ABORTED;
            if (result.isReady())
                return PromiseStatus.COMPLETED;
        }
        
        dataRef.set(Result.ofException(new IllegalStateException("Promise is in an unknown state!: " + data)));
        try {
            handleIllegalStatusException(data);
        } catch (Exception e) {
            // Do nothing
        }
        return PromiseStatus.COMPLETED;
    }
    
    //== Internal working ==
    
    @SuppressWarnings("unchecked")
    public final boolean start() {
        val data = dataRef.get();
        if (data instanceof Promise) {
            val parent = (Promise<DATA>)data;
            return parent.start();
        }
        
        if (!(data instanceof StartableAction))
            return false;
        
        val isJustStarted = dataRef.compareAndSet(data, consumers);
        if (isJustStarted) {
            ((StartableAction<DATA>)data).start();
        }
        return isJustStarted;
    }
    
    boolean abort() {
        @SuppressWarnings("unchecked")
        val cancelResult = (Result<DATA>)Result.ofCancelled();
        return makeDone(cancelResult);
    }
    boolean abort(String message) {
        @SuppressWarnings("unchecked")
        val cancelResult = (Result<DATA>)Result.ofCancelled(message);
        return makeDone(cancelResult);
    }
    boolean abort(Exception cause) {
        @SuppressWarnings("unchecked")
        val cancelResult = (Result<DATA>)Result.ofCancelled(null, cause);
        return makeDone(cancelResult);
    }
    boolean abort(String message, Exception cause) {
        @SuppressWarnings("unchecked")
        val cancelResult = (Result<DATA>)Result.ofCancelled(message, cause);
        return makeDone(cancelResult);
    }
    
    boolean makeComplete(DATA data) { 
        val result = Result.of(data);
        return makeDone(result);
    }
    
    boolean makeFail(Exception exception) {
        @SuppressWarnings("unchecked")
        val result = (Result<DATA>)Result.ofException(exception);
        return makeDone(result);
    }
    
    private <T> T synchronouseOperation(Func0<T> operation) {
        synchronized (dataRef) {
            return operation.get();
        }
    }
    
    private boolean makeDone(Result<DATA> result) {
        val isDone = synchronouseOperation(()->{
            val data = dataRef.get();
            if (data instanceof Promise) {
                @SuppressWarnings("unchecked")
                val parent = (Promise<DATA>)data;
                if (!result.isException()) {
                    if (!dataRef.compareAndSet(parent, result))
                        return false;
                } else {
                    parent.makeDone(result);
                }
            } else if (data instanceof StartableAction) {
                if (!dataRef.compareAndSet(data, result))
                    return false;
            } else {
                if (!dataRef.compareAndSet(consumers, result))
                    return false;
            }
            return null;
        });
        
        if (isDone != null)
            return isDone.booleanValue();
            
        val subscribers = new HashMap<Subscription<DATA>, FuncUnit1<Result<DATA>>>(consumers);
        this.consumers.clear();
        
        val eavesdroppers = new ArrayList<Consumer<Result<DATA>>>(this.eavesdroppers);
        this.eavesdroppers.clear();
        
        for (val eavesdropper : eavesdroppers) {
            carelessly(()->{
                eavesdropper.accept(result);
            });
        }
        
        subscribers
        .forEach((subscription, consumer) -> {
            try {
                consumer.accept(result);
            } catch (Exception e) {
                try {
                    handleResultConsumptionExcepion(subscription, consumer, result);
                } catch (Exception anotherException) {
                    // Do nothing this time.
                }
            }
        });
        return true;
    }
    
    //== Customizable ==
    
    protected void handleIllegalStatusException(Object data) {
    }
    
    protected void handleResultConsumptionExcepion(
            Subscription<DATA>      subscription,
            FuncUnit1<Result<DATA>> consumer,
            Result<DATA>            result) {
    }
    
    protected <T> Promise<T> newPromise() {
        return new Promise<T>(this);
    }
    
    //== Basic functionality ==
    
    public final boolean isStarted() {
        return !PromiseStatus.NOT_STARTED.equals(getStatus());
    }
    public final boolean isPending() {
        return PromiseStatus.PENDING.equals(getStatus());
    }
    public final boolean isAborted() {
        return PromiseStatus.ABORTED.equals(getStatus());
    }
    public final boolean isComplete() {
        return PromiseStatus.COMPLETED.equals(getStatus());
    }
    public final boolean isDone() {
        PromiseStatus status;
        return (null != (status = getStatus())) && status.isDone();
    }
    public final boolean isNotDone() {
        return !isDone();
    }
    
    boolean isSubscribed(Subscription<DATA> subscription) {
        return consumers.containsKey(subscription);
    }
    void unsubscribe(Subscription<DATA> subscription) {
        consumers.remove(subscription);
        abortWhenNoSubscription();
    }
    
    private void abortWhenNoSubscription() {
        if (consumers.isEmpty())
            abort("No more listener.");
    }
    
    private Subscription<DATA> listen(boolean isEavesdropping, FuncUnit1<Result<DATA>> resultConsumer) {
        val subscription = new Subscription<DATA>(this);
        if (isEavesdropping)
             eavesdroppers.add(resultConsumer);
        else consumers    .put(subscription, resultConsumer);
        
        return subscription;
    }
    
    public final Result<DATA> getResult() {
        long timeout = waitTimeout.elseUse(-1L).get().longValue();
        return getResult(timeout, null);
    }
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        start();
        if (!this.isDone()) {
            synchronized (this) {
                if (!this.isDone()) {
                    val latch = new CountDownLatch(1);
                    subscribe(result -> {
                        latch.countDown();
                    });
                    
                    try {
                        if ((timeout < 0) || (unit == null))
                             latch.await();
                        else latch.await(timeout, unit);
                        
                    } catch (InterruptedException exception) {
                        throw new UncheckedInterruptedException(exception);
                    }
                }
            }
        }
        
        if (!this.isDone())
            throw new UncheckedInterruptedException(new InterruptedException());
        
        return getCurrentResult();
    }
    
    public final Result<DATA> getCurrentResult() {
        val data = dataRef.get();
        if (data instanceof Result) {
            @SuppressWarnings("unchecked")
            val result = (Result<DATA>)data;
            return result;
        }
        if (data instanceof Promise) {
            @SuppressWarnings("unchecked")
            val parent = (Promise<DATA>)data;
            return parent.getCurrentResult();
        }
        return Result.ofNotReady();
    }
    
    public final SubscriptionHolder<DATA> subscribe() {
        return new SubscriptionHolder<>(false, Wait.forever(), this);
    }
    
    public final SubscriptionHolder<DATA> subscribe(Wait wait) {
        return new SubscriptionHolder<>(false, wait, this);
    }
    
    public final Subscription<DATA> subscribe(FuncUnit1<Result<DATA>> resultConsumer) {
        return subscribe(Wait.forever(), resultConsumer);
    }
    
    public final Subscription<DATA> subscribe(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        return doSubscribe(false, wait, resultConsumer);
    }
    
    public final SubscriptionHolder<DATA> eavesdrop() {
        return new SubscriptionHolder<>(true, Wait.forever(), this);
    }
    
    public final SubscriptionHolder<DATA> eavesdrop(Wait wait) {
        return new SubscriptionHolder<>(true, wait, this);
    }
    
    public final Subscription<DATA> eavesdrop(FuncUnit1<Result<DATA>> resultConsumer) {
        return doSubscribe(true, Wait.forever(), resultConsumer);
    }
    
    public final Subscription<DATA> eavesdrop(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        return doSubscribe(true, wait, resultConsumer);
    }
    
    public final Promise<DATA> abortNoSubsriptionAfter(Wait wait) {
        val subscriptionHolder = subscribe(wait);
        subscriptionHolder.assign(__ -> subscriptionHolder.unsubscribe());
        return this;
    }
    
    @SuppressWarnings("unchecked")
    final Subscription<DATA> doSubscribe(boolean isEavesdropping, Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        val toRunNow           = new AtomicBoolean(false);
        val returnSubscription = (Subscription<DATA>)synchronouseOperation(()->{
            val data = dataRef.get();
            if (data instanceof Result) {
                val subscription = new Subscription<DATA>(this);
                toRunNow.set(true);
                return subscription;
            }
            
            val hasNotified  = new AtomicBoolean(false);
            val waitSession  = wait != null ? wait.newSession() : Wait.forever().newSession();
            val subscription = listen(isEavesdropping, result -> {
                if (hasNotified.compareAndSet(false, true)) {
                    try {
                        resultConsumer.accept(result);
                    } catch (Throwable e) {
                        // TODO - Use null here because don't know what to do.
                        // FIXME
                        handleResultConsumptionExcepion(null, resultConsumer, result);
                    }
                }
            });
            waitSession.onExpired((message, throwable) -> {
                if (!hasNotified.compareAndSet(false, true))
                    return;
                
                subscription.unsubscribe();
                Result<DATA> result;
                try {
                    if (wait instanceof WaitOrDefault) {
                        val supplier = ((WaitOrDefault<DATA>)wait).getDefaultSupplier();
                        if (supplier == null)
                             result = Result.ofCancelled(message, throwable);
                        else result = supplier.get();
                    } else {
                        result = Result.ofCancelled(message, throwable);
                    }
                } catch (Exception e) {
                    result = Result.ofCancelled(null, e);
                }
                try {
                    resultConsumer.accept(result);
                } catch (Throwable e) {
                    // TODO - Use null here because don't know what to do.
                    // FIXME
                    handleResultConsumptionExcepion(null, resultConsumer, result);
                }
            });
            return subscription;
        });
        
        if (toRunNow.get()) {
            // The consumer can be heavy so we remove it out of the locked operation.
            val data = dataRef.get();
            val result = (Result<DATA>)data;
            try {
                resultConsumer.accept(result);
            } catch (Throwable e) {
                handleResultConsumptionExcepion(returnSubscription, resultConsumer, result);
            }
        }
        
        return returnSubscription;
    }
    
    //== Functional ==
    
    @SuppressWarnings("unchecked")
    public final Promise<DATA> filter(Predicate<? super DATA> predicate) {
        val targetPromise = (Promise<DATA>)newPromise();
        targetPromise.start();
        
        subscribe(r -> {
            val result = r.filter(predicate);
            targetPromise.makeDone((Result<DATA>) result);
        });
        
        return targetPromise;
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        requireNonNull(mapper);
        val targetPromise = (Promise<TARGET>)newPromise();
        subscribe(r -> {
            val result = r.map(mapper);
            targetPromise.makeDone((Result<TARGET>) result);
        });
        
        return targetPromise;
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper) {
        requireNonNull(mapper);
        val targetPromise = (Promise<TARGET>)newPromise();
        subscribe(r -> {
            val result = mapper.apply(r);
            targetPromise.makeDone((Result<TARGET>) result);
        });
        
        return targetPromise;
    }
    
    public final <TARGET> Promise<TARGET> flatMap(Function<DATA, ? extends HasPromise<TARGET>> mapper) {
        return chain(mapper);
    }
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> chain(Function<DATA, ? extends HasPromise<TARGET>> mapper) {
        val targetPromise = (Promise<TARGET>)newPromise();
        subscribe(r -> {
            val targetResult = r.map(mapper);
            targetResult.ifPresent(hasPromise -> {
                hasPromise.getPromise().subscribe(result -> {
                    targetPromise.makeDone((Result<TARGET>) result);
                });
            });
        });
        
        return targetPromise;
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> elseUse(TARGET elseValue) {
        val targetPromise = (Promise<TARGET>)newPromise();
        subscribe(result -> {
            result
            .ifPresent(value -> {
                targetPromise.makeDone((Result<TARGET>)result);
            })
            .ifNotPresent(() -> {
                targetPromise.makeDone(Result.of(elseValue));
            });
        });
        
        return targetPromise;
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> elseGet(Supplier<TARGET> elseSupplier) {
        val targetPromise = (Promise<TARGET>)newPromise();
        subscribe(result -> {
            result
            .ifPresent(value -> {
                targetPromise.makeDone((Result<TARGET>)result);
            })
            .ifNotPresent(() -> {
                targetPromise.makeDone(Result.from(elseSupplier));
            });
        });
        
        return targetPromise;
    }
    
    // TODO - Consider if adding whenPresent, whenNull, whenException  add any value.
    // TODO - Consider if adding ifException, ifCancel .... is any useful ... or just subscribe is good enough.
    
    //== Internal ==
    
    @SuppressWarnings("rawtypes")
    private static class Combiner<D> {
        
        private final Func1<Result[], Result<D>> mergeFunc;
        
        private final PendingAction<D> action;
        private final int              count;
        private final Result[]         results;
        private final Subscription[]   subscriptions;
        private final AtomicBoolean    isDone;
        private final Promise<D>       promise;
        
        Combiner(FuncList<NamedExpression<HasPromise<?>>> promises,
                 Func1<Result[], Result<D>>               mergeFunc) {
            this.mergeFunc     = mergeFunc;
            this.action        = DeferAction.of((Class<D>)null).start();
            this.count         = promises.size();
            this.results       = new Result[count];
            this.subscriptions = new Subscription[count];
            this.isDone        = new AtomicBoolean(false);
            
            promises
            .map             ( promise         -> promise.apply(null))
            .map             ( promise         -> promise.getPromise())
            .peek            ( promise         -> promise.start())
            .mapWithIndex    ((index, promise) -> promise.subscribe(result -> processResult(index, result)))
            .forEachWithIndex((index, sub)     -> subscriptions[index] = sub);
            
            this.promise = action.getPromise();
            this.promise.eavesdrop(result->{
                if (result.isCancelled()) {
                    unsbscribeAll();
                }
            });
        }
        
        Promise<D> getPromise() {
            return promise;
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
