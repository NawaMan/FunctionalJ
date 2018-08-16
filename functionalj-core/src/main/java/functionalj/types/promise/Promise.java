package functionalj.types.promise;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.types.result.Result;
import lombok.val;

// Need an ability to aborted if no one listening

@SuppressWarnings("javadoc")
public class Promise<DATA> {
    
    public static <D> Promise<D> ofValue(D value) {
        val promise = new Promise<D>();
        val control = new PromiseControl<D>(promise);
        control.start().complete(value);
        return promise;
    }
    
    public static <D> Promise<D> ofException(Exception exception) {
        val promise = new Promise<D>();
        val control = new PromiseControl<D>(promise);
        control.start().fail(exception);
        return promise;
    }
    
    public static <D> Promise<D> ofAborted() {
        val promise = new Promise<D>();
        val control = new PromiseControl<D>(promise);
        control.start().abort();
        return promise;
    }
    
    public static <D> PromiseControl<D> of(Class<D> clzz) {
        val promise = new Promise<D>();
        val control = new PromiseControl<D>(promise);
        return control;
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
        } catch (Throwable e) {
            // Do nothing
        }
        return PromiseStatus.COMPLETED;
    }
    
    //== Internal working ==
    
    void start() {
        dataRef.compareAndSet(PromiseStatus.NOT_STARTED, consumers);
    }
    
    void abort() {
        makeDone(Result.ofCancelled());
    }
    
    void makeComplete(DATA data) {
        val result = Result.of(data);
        makeDone(result);
    }
    
    void makeFail(Exception exception) {
        @SuppressWarnings("unchecked")
        val result = (Result<DATA>)Result.ofException(exception);
        makeDone(result);
    }
    
    private void makeDone(Result<DATA> result) {
        if (!dataRef.compareAndSet(consumers, result))
            return;
        
        val subscribers = new HashMap<>(consumers);
        consumers.clear();
        
        subscribers
        .forEach((subscription, consumer) -> {
            try {
                consumer.accept(result);
            } catch (Throwable e) {
                try {
                    handleResultConsumptionExcepion(subscription, consumer, result);
                } catch (Throwable anotherException) {
                    // Do nothing this time.
                }
            }
        });
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
    
    @SuppressWarnings("unchecked")
    public Promise<DATA> filter(Predicate<? super DATA> predicate) {
        val targetPromise = (Promise<DATA>)newPromise();
        targetPromise.start();
        
        subscribe(r -> {
            val result = r.filter(predicate);
            targetPromise.makeDone((Result<DATA>) result);
        });
        
        return targetPromise;
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Promise<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        val targetPromise = (Promise<TARGET>)newPromise();
        targetPromise.start();
        
        subscribe(r -> {
            val result = r.map(mapper);
            targetPromise.makeDone((Result<TARGET>) result);
        });
        
        return targetPromise;
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Promise<TARGET> flatMap(Function<? super DATA, Promise<? extends TARGET>> mapper) {
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
        return doSubscribe(waitAwhile.orDefaultCancellation(), resultConsumer);
    }
    
    public final Subscription<DATA> subscribe(
            WaitOrDefault<? extends DATA> waitOrDefault, 
            Consumer<Result<DATA>>        resultConsumer) {
        return doSubscribe(waitOrDefault, resultConsumer);
    }
    
    private final Subscription<DATA> doSubscribe(
            Wait                   wait, 
            Consumer<Result<DATA>> resultConsumer) {
        val data = dataRef.get();
        if (data instanceof Result) {
            val subscription = new Subscription<DATA>(this);
            @SuppressWarnings("unchecked")
            val result = (Result<DATA>)data;
            try {
                resultConsumer.accept(result);
            } catch (Throwable e) {
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
                Result<DATA> result;
                try {
                    @SuppressWarnings("unchecked")
                    val supplier = (wait instanceof WaitOrDefault)
                            ? ((WaitOrDefault<DATA>)wait).getDefaultSupplier()
                            : null;
                    if (supplier == null)
                         result = Result.ofCancelled(message, throwable);
                    else result = supplier.get();
                } catch (Throwable e) {
                    result = Result.ofCancelled(null, e);
                }
                resultConsumer.accept(result);
            }
        });
        return subscription;
    }
    
}
