package functionalj.types.promise;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.types.result.Result;
import lombok.val;

// Need an ability to aborted if no one listening

@SuppressWarnings("javadoc")
public class Promise<DATA> implements HasPromise<DATA> {
    
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
    
    public static <D, T1, T2> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Wait                                       wait,
            BiFunction<T1, T2, D>                      merger) {
        return Promises.of(promise1, promise2, wait, merger);
    }
    public static <D, T1, T2> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            BiFunction<T1, T2, D>                      merger) {
        return Promises.of(promise1, promise2, Wait.forever(), merger);
    }
    
    public static <D, T1, T2, T3> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Wait                                       wait,
            Func3<T1, T2, T3, D>                       merger) {
        return Promises.of(promise1, promise2, promise3, wait, merger);
    }
    public static <D, T1, T2, T3> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Func3<T1, T2, T3, D>                       merger) {
        return Promises.of(promise1, promise2, promise3, Wait.forever(), merger);
    }
    
    public static <D, T1, T2, T3, T4> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Wait                                       wait,
            Func4<T1, T2, T3, T4, D>                   merger) {
        return Promises.of(promise1, promise2, promise3, promise4, wait, merger);
    }
    public static <D, T1, T2, T3, T4> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Func4<T1, T2, T3, T4, D>                   merger) {
        return Promises.of(promise1, promise2, promise3, promise4, Wait.forever(), merger);
    }
    
    public static <D, T1, T2, T3, T4, T5> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Function<Object, ? extends HasPromise<T5>> promise5,
            Wait                                       wait,
            Func5<T1, T2, T3, T4, T5, D>               merger) {
        return Promises.of(promise1, promise2, promise3, promise4, promise5, wait, merger);
    }
    public static <D, T1, T2, T3, T4, T5> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Function<Object, ? extends HasPromise<T5>> promise5,
            Func5<T1, T2, T3, T4, T5, D>               merger) {
        return Promises.of(promise1, promise2, promise3, promise4, promise5, Wait.forever(), merger);
    }
    
    public static <D, T1, T2, T3, T4, T5, T6> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Function<Object, ? extends HasPromise<T5>> promise5,
            Function<Object, ? extends HasPromise<T6>> promise6,
            Wait                                       wait,
            Func6<T1, T2, T3, T4, T5, T6, D>           merger) {
        return Promises.of(promise1, promise2, promise3, promise4, promise5, promise6, wait, merger);
    }
    public static <D, T1, T2, T3, T4, T5, T6> Promise<D> from(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Function<Object, ? extends HasPromise<T5>> promise5,
            Function<Object, ? extends HasPromise<T6>> promise6,
            Func6<T1, T2, T3, T4, T5, T6, D>           merger) {
        return Promises.of(promise1, promise2, promise3, promise4, promise5, promise6, Wait.forever(), merger);
    }
    
    
    // DATA
    //    NOT_START -> NOT START
    //    consumer  -> Pending
    //    result    -> done.
    //      result.cancelled -> aborted
    //      result.completed -> completed
    private final Map<Subscription<DATA>, Consumer<Result<DATA>>> consumers = new ConcurrentHashMap<>();
    private final AtomicReference<Object> dataRef = new AtomicReference<>(PromiseStatus.NOT_STARTED);
    
    public Promise() {}
    
    @Override
    public Promise<DATA> getPromise() {
        return this;
    }
    
    public final PromiseStatus getStatus() {
        val data = dataRef.get();
        if (PromiseStatus.NOT_STARTED.equals(data))
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
    
    boolean start() {
        return dataRef.compareAndSet(PromiseStatus.NOT_STARTED, consumers);
    }
    
    boolean abort() {
        return makeDone(Result.ofCancelled());
    }
    
    boolean abort(String message) {
        return makeDone(Result.ofCancelled(message));
    }
    boolean abort(Throwable cause) {
        return makeDone(Result.ofCancelled(null, cause));
    }
    boolean abort(String message, Throwable cause) {
        return makeDone(Result.ofCancelled(message, cause));
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
    
    private boolean makeDone(Result<DATA> result) {
        if (!dataRef.compareAndSet(consumers, result))
            return false;
        
        val subscribers = new HashMap<>(consumers);
        consumers.clear();
        
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
            Subscription<DATA>     subscription,
            Consumer<Result<DATA>> consumer,
            Result<DATA>           result) {
    }
    
    protected <T> Promise<T> newPromise() {
        return new Promise<T>();
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
    }
    
    private Subscription<DATA> addSubscription(
            Consumer<Result<DATA>> resultConsumer) {
        val subscription = new Subscription<DATA>(this);
        consumers.put(subscription, resultConsumer);
        return subscription;
    }
    
    @SuppressWarnings("unchecked")
    public final Result<DATA> getResult() {
        val data = dataRef.get();
        if (data instanceof Result)
            return (Result<DATA>)data;
        return Result.ofNotReady();
    }
    
    public final SubscriptionHolder<DATA> subscribe() {
        return new SubscriptionHolder<>(Wait.forever(), this);
    }
    
    public final SubscriptionHolder<DATA> subscribe(WaitForever waitForever) {
        return new SubscriptionHolder<>(waitForever, this);
    }
    
    public final SubscriptionHolder<DATA> subscribe(WaitAwhile waitAwhile) {
        return new SubscriptionHolder<>(waitAwhile, this);
    }
    
    public final SubscriptionHolder<DATA> subscribe(WaitOrDefault<? extends DATA> waitOrDefault) {
        return new SubscriptionHolder<>(waitOrDefault, this);
    }
    
    public final Subscription<DATA> subscribe(Consumer<Result<DATA>> resultConsumer) {
        return subscribe(Wait.forever(), resultConsumer);
    }
    
    public final Subscription<DATA> subscribe(
            WaitForever            waitForever, 
            Consumer<Result<DATA>> resultConsumer) {
        return doSubscribe(waitForever, resultConsumer);
    }
    
    public final Subscription<DATA> subscribe(
            WaitAwhile             waitAwhile, 
            Consumer<Result<DATA>> resultConsumer) {
        return doSubscribe(waitAwhile.orCancel(), resultConsumer);
    }
    
    public final Subscription<DATA> subscribe(
            WaitOrDefault<? extends DATA> waitOrDefault, 
            Consumer<Result<DATA>>        resultConsumer) {
        return doSubscribe(waitOrDefault, resultConsumer);
    }
    
    final Subscription<DATA> doSubscribe(
            Wait                   wait, 
            Consumer<Result<DATA>> resultConsumer) {
        val data = dataRef.get();
        if (data instanceof Result) {
            val subscription = new Subscription<DATA>(this);
            @SuppressWarnings("unchecked")
            val result = (Result<DATA>)data;
            try {
                resultConsumer.accept(result);
            } catch (Exception e) {
                handleResultConsumptionExcepion(subscription, resultConsumer, result);
            }
            return subscription;
        }
        
        val hasNotified  = new AtomicBoolean(false);
        val waitSession  = wait.newSession();
        val subscription = addSubscription(result -> {
            if (hasNotified.compareAndSet(false, true)) {
                resultConsumer.accept(result);
            }
        });
        waitSession.onExpired((message, throwable) -> {
            if (hasNotified.compareAndSet(false, true)) {
                subscription.unsubscribe();
                Result<DATA> result;
                try {
                    @SuppressWarnings("unchecked")
                    val supplier = (wait instanceof WaitOrDefault)
                            ? ((WaitOrDefault<DATA>)wait).getDefaultSupplier()
                            : null;
                    if (supplier == null)
                         result = Result.ofCancelled(message, throwable);
                    else result = supplier.get();
                } catch (Exception e) {
                    result = Result.ofCancelled(null, e);
                }
                resultConsumer.accept(result);
            }
        });
        return subscription;
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
        val targetPromise = (Promise<TARGET>)newPromise();
        targetPromise.start();
        
        subscribe(r -> {
            val result = r.map(mapper);
            targetPromise.makeDone((Result<TARGET>) result);
        });
        
        return targetPromise;
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> flatMap(Function<? super DATA, Promise<? extends TARGET>> mapper) {
        val targetPromise = (Promise<TARGET>)newPromise();
        targetPromise.start();
        subscribe(r -> {
            val targetResult = r.map(mapper);
            targetResult.ifPresent(promise -> {
                promise.subscribe(result -> {
                    targetPromise.makeDone((Result<TARGET>) result);
                });
            });
        });
        
        return targetPromise;
    }
    
}
