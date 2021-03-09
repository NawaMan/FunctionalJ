// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.promise;

import static functionalj.function.Func.carelessly;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.function.NamedExpression;
import functionalj.pipeable.Pipeable;
import functionalj.ref.Ref;
import functionalj.result.HasResult;
import functionalj.result.Result;
import lombok.val;



// TODO - Find a way to make toString more useful ... like giving this a name.
// TODO - Should extract important stuff to PromiseBase ... so it is not flooded with the less important things.

public class Promise<DATA> implements HasPromise<DATA>, HasResult<DATA>, Pipeable<HasPromise<DATA>> {
    
    private static final int INITIAL_CAPACITY = 2;
    
    public static final Ref<Long> waitTimeout = Ref.ofValue(-1L);
    
    public static <D> Promise<D> ofResult(HasResult<D> asResult) {
        if (asResult instanceof HasPromise)
            return ((HasPromise<D>)asResult).getPromise();
        
        return DeferActionBuilder
                .from(()->asResult.getResult().value())
                .build()
                .getPromise();
    }
    public static <D> Promise<D> of(D value) {
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
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            Func2<T1, T2, D>                merger) {
        val action  = DeferAction.from(promise1, promise2, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    public static <D, T1, T2, T3> Promise<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            Func3<T1, T2, T3, D>            merger) {
        val action  = DeferAction.from(promise1, promise2, promise3, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    public static <D, T1, T2, T3, T4> Promise<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            Func4<T1, T2, T3, T4, D>        merger) {
        val action  = DeferAction.from(promise1, promise2, promise3, promise4, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    public static <D, T1, T2, T3, T4, T5> Promise<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            NamedExpression<HasPromise<T5>> promise5,
            Func5<T1, T2, T3, T4, T5, D>    merger) {
        val action  = DeferAction.from(promise1, promise2, promise3, promise4, promise5, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    public static <D, T1, T2, T3, T4, T5, T6> Promise<D> from(
            NamedExpression<HasPromise<T1>>  promise1,
            NamedExpression<HasPromise<T2>>  promise2,
            NamedExpression<HasPromise<T3>>  promise3,
            NamedExpression<HasPromise<T4>>  promise4,
            NamedExpression<HasPromise<T5>>  promise5,
            NamedExpression<HasPromise<T6>>  promise6,
            Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        val action  = DeferAction.from(promise1, promise2, promise3, promise4, promise5, promise6, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    
    // DATA
    //    StartableAction -> NOT START
    //    consumer        -> Pending
    //    result          -> done.
    //      result.cancelled -> aborted
    //      result.completed -> completed
    final Map<SubscriptionRecord<DATA>, FuncUnit1<Result<DATA>>> consumers     = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    final List<FuncUnit1<Result<DATA>>>                          eavesdroppers = new ArrayList<>(INITIAL_CAPACITY);
    
    private static final AtomicInteger ID = new AtomicInteger(1);
    
    final AtomicReference<Object> dataRef = new AtomicReference<>();
    private final int id = ID.getAndIncrement();
    
    public int hashCode() {
        return id;
    }
    public String toString() {
        return "Promise#" + id;
    }
    
    Promise(OnStart onStart) {
        dataRef.set(onStart);
    }
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
    
    public Promise<DATA> named(String name) {
        FuncUnit2<Result<DATA>, Promise<DATA>> resultConsumer = (Result<DATA> r, Promise<DATA> targetPromise) -> {
            targetPromise.makeDone(r);
        };
        val promise = new NamedPromise<DATA>(this, name);
        onComplete(resultConsumer.elevateWith(promise));
        return promise;
    }
    
    public HasPromise<DATA> __data() throws Exception {
        return this;
    }
    
    public final PromiseStatus getStatus() {
        val data = dataRef.get();
        if (data instanceof Promise) {
            @SuppressWarnings("unchecked")
            Promise<DATA> promise = (Promise<DATA>)data;
            PromiseStatus parentStatus = promise.getStatus();
            // Pending ... as the result is not yet propagated down
            if (parentStatus.isNotDone())
                 return parentStatus;
            else return PromiseStatus.PENDING;
        }
        
        if ((data instanceof StartableAction) || (data instanceof OnStart))
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
        
        val isStartAction = (data instanceof StartableAction);
        val isOnStart     = (data instanceof OnStart);
        if (!isStartAction && !isOnStart)
            return false;
        
        val isJustStarted = dataRef.compareAndSet(data, consumers);
        if (isJustStarted) {
            if (isStartAction)  ((StartableAction<DATA>)data).start();
            else if (isOnStart) ((OnStart)data).run();
        }
        return isJustStarted;
    }
    
    OnStart getOnStart() {
        val data = dataRef.get();
        return (data instanceof OnStart) ? (OnStart)data : OnStart.DoNothing;
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
        val result = Result.valueOf(data);
        return makeDone(result);
    }
    
    boolean makeFail(Exception exception) {
        @SuppressWarnings("unchecked")
        val result = (Result<DATA>)Result.ofException(exception);
        return makeDone(result);
    }
    
    <T> T synchronouseOperation(Func0<T> operation) {
        synchronized (dataRef) {
            return operation.get();
        }
    }
    static <DATA> boolean makeDone(Promise<DATA> promise, Result<DATA> result) {
        val isDone = promise.synchronouseOperation(()->{
            val data = promise.dataRef.get();
            try {
                if (data instanceof Promise) {
                    @SuppressWarnings("unchecked")
                    val parent = (Promise<DATA>)data;
                    try {
                        if (!promise.dataRef.compareAndSet(parent, result))
                            return false;
                    } finally {
                        parent.unsubscribe(promise);
                    }
                } else if ((data instanceof StartableAction) || (data instanceof OnStart)) {
                    if (!promise.dataRef.compareAndSet(data, result))
                        return false;
                } else {
                    if (!promise.dataRef.compareAndSet(promise.consumers, result))
                        return false;
                }
                return null;
            } finally {
            }
        });
        
        if (isDone != null)
            return isDone.booleanValue();
        
        val subscribers = new HashMap<SubscriptionRecord<DATA>, FuncUnit1<Result<DATA>>>(promise.consumers);
        promise.consumers.clear();
        
        val eavesdroppers = new ArrayList<Consumer<Result<DATA>>>(promise.eavesdroppers);
        promise.eavesdroppers.clear();
        
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
                    promise.handleResultConsumptionExcepion(subscription, consumer, result);
                } catch (Exception anotherException) {
                    // Do nothing this time.
                }
            }
        });
        return true;
    }
    
    private boolean makeDone(Result<DATA> result) {
        return makeDone(this, result);
    }
    
    //== Customizable ==
    
    protected void handleIllegalStatusException(Object data) {
    }
    
    protected void handleResultConsumptionExcepion(
            SubscriptionRecord<DATA> subscription,
            FuncUnit1<Result<DATA>>  consumer,
            Result<DATA>             result) {
    }
    
    private <T> Promise<T> newSubPromise(FuncUnit2<Result<DATA>, Promise<T>> resultConsumer) {
        val promise = new Promise<T>(this);
        onComplete(resultConsumer.elevateWith(promise));
        return promise;
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
        val status = getStatus();
        val isDone = (null != status) && status.isDone();
        return isDone;
    }
    public final boolean isNotDone() {
        return !isDone();
    }
    
    boolean isSubscribed(SubscriptionRecord<DATA> subscription) {
        return consumers.containsKey(subscription);
    }
    void unsubscribe(SubscriptionRecord<DATA> subscription) {
        consumers.remove(subscription);
        abortWhenNoSubscription();
    }
    void unsubscribe(Promise<DATA> promise) {
        val entry = consumers.entrySet().stream().filter(e -> Objects.equals(e.getKey().getPromise(), promise)).findFirst();
        if (entry.isPresent())
            consumers.remove(entry.get().getKey());
        abortWhenNoSubscription();
    }
    
    private void abortWhenNoSubscription() {
        if (consumers.isEmpty())
            abort("No more listener.");
    }
    
    private SubscriptionRecord<DATA> listen(boolean isEavesdropping, FuncUnit1<Result<DATA>> resultConsumer) {
        val subscription = new SubscriptionRecord<DATA>(this);
        if (isEavesdropping)
             eavesdroppers.add(resultConsumer);
        else consumers    .put(subscription, resultConsumer);
        
        return subscription;
    }
    
    public final Result<DATA> getResult() {
        long timeout = waitTimeout.whenAbsentUse(-1L).get().longValue();
        return getResult(timeout, TimeUnit.MILLISECONDS);
    }
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        start();
        if (!isDone()) {
            val latch = new CountDownLatch(1);
            synchronouseOperation(()->{
                onComplete(result -> {
                    latch.countDown();
                });
                return isDone();
            });
            
            if (!isDone()) {
                try {
                    if ((timeout < 0) || (unit == null))
                         latch.await();
                    else latch.await(timeout, unit);
                    
                } catch (InterruptedException exception) {
                    throw new UncheckedInterruptedException(exception);
                }
            }
        }
        
        if (!isDone())
            throw new UncheckedInterruptedException(new InterruptedException());
        
        val currentResult = getCurrentResult();
        return currentResult;
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
    
    public final SubscriptionRecord<DATA> subscribe(FuncUnit1<DATA> resultConsumer) {
        return onComplete(Wait.forever(), r -> r.ifPresent(resultConsumer));
    }
    
    public final SubscriptionRecord<DATA> subscribe(Wait wait, FuncUnit1<DATA> resultConsumer) {
        return doSubscribe(false, wait, r -> r.ifPresent(resultConsumer));
    }
    
    public final SubscriptionHolder<DATA> onComplete() {
        return new SubscriptionHolder<>(false, Wait.forever(), this);
    }
    
    public final SubscriptionRecord<DATA> onComplete(FuncUnit1<Result<DATA>> resultConsumer) {
        return onComplete(Wait.forever(), resultConsumer);
    }
    
    public final SubscriptionHolder<DATA> onComplete(Wait wait) {
        return new SubscriptionHolder<>(false, wait, this);
    }
    
    public final SubscriptionRecord<DATA> onComplete(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        return doSubscribe(false, wait, resultConsumer);
    }
    
    public final SubscriptionHolder<DATA> eavesdrop() {
        return new SubscriptionHolder<>(true, Wait.forever(), this);
    }
    
    public final SubscriptionHolder<DATA> eavesdrop(Wait wait) {
        return new SubscriptionHolder<>(true, wait, this);
    }
    
    public final SubscriptionRecord<DATA> eavesdrop(FuncUnit1<Result<DATA>> resultConsumer) {
        return doSubscribe(true, Wait.forever(), resultConsumer);
    }
    
    public final SubscriptionRecord<DATA> eavesdrop(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        return doSubscribe(true, wait, resultConsumer);
    }
    
    public final Promise<DATA> abortNoSubscriptionAfter(Wait wait) {
        val subscriptionHolder = onComplete(wait);
        subscriptionHolder.assign(__ -> subscriptionHolder.unsubscribe());
        return this;
    }
    
    @SuppressWarnings("unchecked")
    final SubscriptionRecord<DATA> doSubscribe(boolean isEavesdropping, Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        val toRunNow           = new AtomicBoolean(false);
        val returnSubscription = (SubscriptionRecord<DATA>)synchronouseOperation(()->{
            val data = dataRef.get();
            if (data instanceof Result) {
                val subscription = new SubscriptionRecord<DATA>(this);
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
    
    public final Promise<DATA> filter(Predicate<? super DATA> predicate) {
        requireNonNull(predicate);
        return (Promise<DATA>)newSubPromise((Result<DATA> r, Promise<DATA> targetPromise) -> {
            val result = r.filter(predicate);
            targetPromise.makeDone((Result<DATA>) result);
        });
    }
    
    public final Promise<DATA> peek(FuncUnit1<? super DATA> peeker) {
        requireNonNull(peeker);
        return (Promise<DATA>)newSubPromise((Result<DATA> r, Promise<DATA> targetPromise) -> {
            val result = r.peek(peeker);
            targetPromise.makeDone((Result<DATA>) result);
        });
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> map(Func1<? super DATA, ? extends TARGET> mapper) {
        requireNonNull(mapper);
        return (Promise<TARGET>)newSubPromise((Result<DATA> r, Promise<TARGET> targetPromise) -> {
            val result = r.map(mapper);
            targetPromise.makeDone((Result<TARGET>) result);
        });
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper) {
        requireNonNull(mapper);
        return (Promise<TARGET>)newSubPromise((Result<DATA> r, Promise<TARGET> targetPromise) -> {
            val result = mapper.apply(r);
            targetPromise.makeDone((Result<TARGET>) result);
        });
    }
    
    public final <TARGET> Promise<TARGET> flatMap(Func1<DATA, ? extends HasPromise<TARGET>> mapper) {
        return chain(mapper);
    }
    public final <TARGET> Promise<TARGET> chain(Func1<DATA, ? extends HasPromise<TARGET>> mapper) {
        return (Promise<TARGET>)newSubPromise((Result<DATA> r, Promise<TARGET> targetPromise) -> {
            val targetResult = r.map(mapper);
            targetResult.ifPresent(hasPromise -> {
                hasPromise.getPromise().onComplete(result -> {
                    targetPromise.makeDone((Result<TARGET>) result);
                });
            });
        });
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> whenAbsentUse(TARGET elseValue) {
        return (Promise<TARGET>)newSubPromise((Result<DATA> result, Promise<TARGET> targetPromise) -> {
            result
            .ifPresent(value -> {
                targetPromise.makeDone((Result<TARGET>)result);
            })
            .ifAbsent(() -> {
                targetPromise.makeDone(Result.valueOf(elseValue));
            });
        });
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Promise<TARGET> whenAbsentGet(Supplier<TARGET> elseSupplier) {
        return (Promise<TARGET>)newSubPromise((Result<DATA> result, Promise<TARGET> targetPromise) -> {
            result
            .ifPresent(value -> {
                targetPromise.makeDone((Result<TARGET>)result);
            })
            .ifAbsent(() -> {
                targetPromise.makeDone(Result.from(elseSupplier));
            });
        });
    }
    
    // TODO - Consider if adding whenPresent, whenNull, whenException  add any value.
    // TODO - Consider if adding ifException, ifCancel .... is any useful ... or just subscribe is good enough.
    
}
