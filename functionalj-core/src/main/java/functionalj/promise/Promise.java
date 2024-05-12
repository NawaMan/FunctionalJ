// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Apply.$;
import static functionalj.function.Func.carelessly;
import static java.util.Objects.requireNonNull;

import java.io.PrintStream;
import java.io.PrintWriter;
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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func10;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.ref.Ref;
import functionalj.result.HasResult;
import functionalj.result.Result;
import functionalj.result.ResultStatus;
import functionalj.result.ValidationException;
import functionalj.tuple.Tuple2;
import functionalj.validator.Validator;
import lombok.val;

// TODO - Should extract important stuff to PromiseBase ... so it is not flooded with the less important things.
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Promise<DATA> implements HasPromise<DATA>, HasResult<DATA>, Pipeable<HasPromise<DATA>>, PromiseChainAddOn<DATA>, PromiseFilterAddOn<DATA>, PromiseMapAddOn<DATA>, PromisePeekAddOn<DATA>, PromiseStatusAddOn<DATA> {
    
    private static final int INITIAL_CAPACITY = 2;
    
    public static final Ref<Long> waitTimeout = Ref.ofValue(-1L);
    
    public static <D> Promise<D> ofResult(HasResult<D> asResult) {
        if (asResult instanceof HasPromise)
            return ((HasPromise<D>) asResult).getPromise();
        return DeferActionBuilder.from(() -> asResult.getResult().value()).build().getPromise();
    }
    
    public static <D> Promise<D> ofValue(D value) {
        return DeferAction.of((Class<D>) null).start().complete(value).getPromise();
    }
    
    public static <D> Promise<D> ofException(Exception exception) {
        return DeferAction.of((Class<D>) null).start().fail(exception).getPromise();
    }
    
    public static <D> Promise<D> ofAborted() {
        return DeferAction.of((Class<D>) null).start().abort().getPromise();
    }
    
    //== from ==
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func2} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param merger     the {@code Func2} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    Func2<T1, T2, D> merger) {
        val action = DeferAction.from(promise1, promise2, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func3} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param merger     the {@code Func3} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    Func3<T1, T2, T3, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func4} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * @param <T4>  the type of the result provided by the fourth promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param merger     the {@code Func4} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    Func4<T1, T2, T3, T4, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, promise4, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func5} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * @param <T4>  the type of the result provided by the fourth promise.
     * @param <T5>  the type of the result provided by the fifth promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param promise5   the fifth promise.
     * @param merger     the {@code Func5} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    Func5<T1, T2, T3, T4, T5, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, promise4, promise5, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func6} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * @param <T4>  the type of the result provided by the fourth promise.
     * @param <T5>  the type of the result provided by the fifth promise.
     * @param <T6>  the type of the result provided by the sixth promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param promise5   the fifth promise.
     * @param promise6   the sixth promise.
     * @param merger     the {@code Func6} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, promise4, promise5, promise6, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func7} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * @param <T4>  the type of the result provided by the fourth promise.
     * @param <T5>  the type of the result provided by the fifth promise.
     * @param <T6>  the type of the result provided by the sixth promise.
     * @param <T7>  the type of the result provided by the seventh promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param promise5   the fifth promise.
     * @param promise6   the sixth promise.
     * @param promise7   the seventh promise.
     * @param merger     the {@code Func7} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7, 
                    Func7<T1, T2, T3, T4, T5, T6, T7, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, promise4, promise5, promise6, promise7, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func8} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * @param <T4>  the type of the result provided by the fourth promise.
     * @param <T5>  the type of the result provided by the fifth promise.
     * @param <T6>  the type of the result provided by the sixth promise.
     * @param <T7>  the type of the result provided by the seventh promise.
     * @param <T8>  the type of the result provided by the eighth promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param promise5   the fifth promise.
     * @param promise6   the sixth promise.
     * @param promise7   the seventh promise.
     * @param promise8   the eighth promise.
     * @param merger     the {@code Func8} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7, 
                    HasPromise<T8> promise8, 
                    Func8<T1, T2, T3, T4, T5, T6, T7, T8, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func9} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * @param <T4>  the type of the result provided by the fourth promise.
     * @param <T5>  the type of the result provided by the fifth promise.
     * @param <T6>  the type of the result provided by the sixth promise.
     * @param <T7>  the type of the result provided by the seventh promise.
     * @param <T8>  the type of the result provided by the eighth promise.
     * @param <T9>  the type of the result provided by the ninth promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param promise5   the fifth promise.
     * @param promise6   the sixth promise.
     * @param promise7   the seventh promise.
     * @param promise8   the eighth promise.
     * @param promise9   the ninth promise.
     * @param merger     the {@code Func9} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8, T9> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7, 
                    HasPromise<T8> promise8, 
                    HasPromise<T9> promise9, 
                    Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    /**
     * Combines ten {@code HasPromise} instances with a {@code Func10} merger function to create a {@code Promise<D>}.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying a merger function to their results.
     * 
     * @param <D>   the type of the result produced by the merger function.
     * @param <T1>  the type of the result provided by the first promise.
     * @param <T2>  the type of the result provided by the second promise.
     * @param <T3>  the type of the result provided by the third promise.
     * @param <T4>  the type of the result provided by the fourth promise.
     * @param <T5>  the type of the result provided by the fifth promise.
     * @param <T6>  the type of the result provided by the sixth promise.
     * @param <T7>  the type of the result provided by the seventh promise.
     * @param <T8>  the type of the result provided by the eighth promise.
     * @param <T9>  the type of the result provided by the ninth promise.
     * @param <T10> the type of the result provided by the tenth promise.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param promise5   the fifth promise.
     * @param promise6   the sixth promise.
     * @param promise7   the seventh promise.
     * @param promise8   the eighth promise.
     * @param promise9   the ninth promise.
     * @param promise10  the tenth promise.
     * @param merger     the {@code Func10} function that merges the results of the promises.
     * @return           a {@code Promise<D>} that will be fulfilled with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Promise<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7, 
                    HasPromise<T8> promise8, 
                    HasPromise<T9> promise9, 
                    HasPromise<T10> promise10, 
                    Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, D> merger) {
        val action = DeferAction.from(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9, promise10, merger);
        val promise = action.getPromise();
        return promise;
    }
    
    
    //== Data ==
    
    // DATA
    // StartableAction -> NOT START
    // consumer        -> Pending
    // result          -> done.
    // result.cancelled -> aborted
    // result.completed -> completed
    final Map<SubscriptionRecord<DATA>, FuncUnit1<Result<DATA>>> consumers = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    
    final List<FuncUnit1<Result<DATA>>> eavesdroppers = new ArrayList<>(INITIAL_CAPACITY);
    
    private static final AtomicInteger ID = new AtomicInteger(1);
    
    final AtomicReference<Object> dataRef = new AtomicReference<>();
    
    final int id = ID.getAndIncrement();
    
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
    
    Promise(Promise parent) {
        this.dataRef.set(parent);
    }
    
    Promise<DATA> parent() {
        val data = this.dataRef.get();
        return (data instanceof Promise) ? (Promise) data : null;
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
            Promise<DATA> promise = (Promise<DATA>) data;
            PromiseStatus parentStatus = promise.getStatus();
            // Pending ... as the result is not yet propagated down
            if (parentStatus.isNotDone())
                return parentStatus;
            else
                return PromiseStatus.PENDING;
        }
        if ((data instanceof StartableAction) || (data instanceof OnStart))
            return PromiseStatus.NOT_STARTED;
        if (consumers == data)
            return PromiseStatus.PENDING;
        if (data instanceof Result) {
            val result = (Result<DATA>) data;
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
    
    // == Internal working ==
    public final boolean start() {
        val data = dataRef.get();
        if (data instanceof Promise) {
            val parent = (Promise<DATA>) data;
            return parent.start();
        }
        val isStartAction = (data instanceof StartableAction);
        val isOnStart = (data instanceof OnStart);
        if (!isStartAction && !isOnStart)
            return false;
        val isJustStarted = dataRef.compareAndSet(data, consumers);
        if (isJustStarted) {
            if (isStartAction)
                ((StartableAction<DATA>) data).start();
            else if (isOnStart)
                ((OnStart) data).run();
        }
        return isJustStarted;
    }
    
    OnStart getOnStart() {
        val data = dataRef.get();
        return (data instanceof OnStart) ? (OnStart) data : OnStart.DoNothing;
    }
    
    boolean abort() {
        val cancelResult = (Result<DATA>) Result.ofCancelled();
        return makeDone(cancelResult);
    }
    
    boolean abort(String message) {
        val cancelResult = (Result<DATA>) Result.ofCancelled(message);
        return makeDone(cancelResult);
    }
    
    boolean abort(Exception cause) {
        val cancelResult = (Result<DATA>) Result.ofCancelled(null, cause);
        return makeDone(cancelResult);
    }
    
    boolean abort(String message, Exception cause) {
        val cancelResult = (Result<DATA>) Result.ofCancelled(message, cause);
        return makeDone(cancelResult);
    }
    
    boolean makeComplete(DATA data) {
        val result = Result.valueOf(data);
        return makeDone(result);
    }
    
    boolean makeFail(Exception exception) {
        val result = (Result<DATA>) Result.ofException(exception);
        return makeDone(result);
    }
    
    <T> T synchronouseOperation(Func0<T> operation) {
        synchronized (dataRef) {
            return operation.get();
        }
    }
    
    static <DATA> boolean makeDone(Promise<DATA> promise, Result<DATA> result) {
        val isDone = promise.synchronouseOperation(() -> {
            val data = promise.dataRef.get();
            try {
                if (data instanceof Promise) {
                    val parent = (Promise<DATA>) data;
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
            carelessly(() -> {
                eavesdropper.accept(result);
            });
        }
        subscribers.forEach((subscription, consumer) -> {
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
    
    // == Customizable ==
    protected void handleIllegalStatusException(Object data) {
    }
    
    protected void handleResultConsumptionExcepion(SubscriptionRecord<DATA> subscription, FuncUnit1<Result<DATA>> consumer, Result<DATA> result) {
    }
    
    private <T> Promise<T> newSubPromise(FuncUnit2<Result<DATA>, Promise<T>> resultConsumer) {
        val promise = new Promise<T>(this);
        onComplete(resultConsumer.elevateWith(promise));
        return promise;
    }
    
    // == Basic functionality ==
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
        else
            consumers.put(subscription, resultConsumer);
        return subscription;
    }
    
    public final DATA get() throws Exception {
        return getResult().get();
    }
    
    public final Result<DATA> getResult() {
        long timeout = waitTimeout.whenAbsentUse(-1L).get().longValue();
        return getResult(timeout, TimeUnit.MILLISECONDS);
    }
    
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        start();
        if (!isDone()) {
            val latch = new CountDownLatch(1);
            synchronouseOperation(() -> {
                onComplete(result -> {
                    latch.countDown();
                });
                return isDone();
            });
            if (!isDone()) {
                try {
                    if ((timeout < 0) || (unit == null))
                        latch.await();
                    else
                        latch.await(timeout, unit);
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
            val result = (Result<DATA>) data;
            return result;
        }
        if (data instanceof Promise) {
            val parent = (Promise<DATA>) data;
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
    
    final SubscriptionRecord<DATA> doSubscribe(boolean isEavesdropping, Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        val toRunNow = new AtomicBoolean(false);
        val returnSubscription = (SubscriptionRecord<DATA>) synchronouseOperation(() -> {
            val data = dataRef.get();
            if (data instanceof Result) {
                val subscription = new SubscriptionRecord<DATA>(this);
                toRunNow.set(true);
                return subscription;
            }
            val hasNotified = new AtomicBoolean(false);
            val waitSession = wait != null ? wait.newSession() : Wait.forever().newSession();
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
                        val supplier = ((WaitOrDefault<DATA>) wait).getDefaultSupplier();
                        if (supplier == null)
                            result = Result.ofCancelled(message, throwable);
                        else
                            result = supplier.get();
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
            val result = (Result<DATA>) data;
            try {
                resultConsumer.accept(result);
            } catch (Throwable e) {
                handleResultConsumptionExcepion(returnSubscription, resultConsumer, result);
            }
        }
        return returnSubscription;
    }
    
    // == Functional ==
    public Promise<DATA> filter(Predicate<? super DATA> predicate) {
        requireNonNull(predicate);
        return (Promise<DATA>) newSubPromise((Result<DATA> r, Promise<DATA> targetPromise) -> {
            val result = r.filter(predicate);
            targetPromise.makeDone((Result<DATA>) result);
        });
    }
    
    public Promise<DATA> peek(Consumer<? super DATA> peeker) {
        requireNonNull(peeker);
        return (Promise<DATA>) newSubPromise((Result<DATA> r, Promise<DATA> targetPromise) -> {
            val result = r.peek(peeker);
            targetPromise.makeDone((Result<DATA>) result);
        });
    }
    
    public <TARGET> Promise<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        requireNonNull(mapper);
        return (Promise<TARGET>) newSubPromise((Result<DATA> r, Promise<TARGET> targetPromise) -> {
            val result = r.map(mapper);
            targetPromise.makeDone((Result<TARGET>) result);
        });
    }
    
    public <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper) {
        requireNonNull(mapper);
        return (Promise<TARGET>) newSubPromise((Result<DATA> r, Promise<TARGET> targetPromise) -> {
            val result = mapper.apply(r);
            targetPromise.makeDone((Result<TARGET>) result);
        });
    }
    
    public <TARGET> Promise<TARGET> flatMap(Function<? super DATA, ? extends HasPromise<TARGET>> mapper) {
        return chain(mapper);
    }
    
    public <TARGET> Promise<TARGET> chain(Function<? super DATA, ? extends HasPromise<TARGET>> mapper) {
        return (Promise<TARGET>) newSubPromise((Result<DATA> r, Promise<TARGET> targetPromise) -> {
            val targetResult = r.map(mapper);
            targetResult.ifPresent(hasPromise -> {
                hasPromise.getPromise().onComplete(result -> {
                    targetPromise.makeDone((Result<TARGET>) result);
                });
            });
        });
    }
    
    public Promise<DATA> or(Result<DATA> anotherResult) {
        return mapResult(r -> r.or((Result) anotherResult));
    }
    
    public <T> Promise<T> mapValue(BiFunction<DATA, Exception, Result<T>> processor) {
        return mapResult(result -> {
            val excception = result.exception();
            val value = (excception != null) ? null : result.value();
            return $(processor::apply, (DATA)value, excception);
        });
    }
    
    public <T extends DATA> Promise<T> as(Class<T> onlyClass) {
        return as(onlyClass);
    }
    
    public Promise<DATA> mapException(Function<? super Exception, ? extends Exception> mapper) {
        return (Promise) mapResult(result -> result.mapException(mapper));
    }
    
    public <OPERANT, TARGET> Promise<TARGET> mapWith(BiFunction<? super DATA, ? super OPERANT, ? extends TARGET> func, Result<OPERANT> operantResult) {
        return (Promise) mapResult(result -> result.mapWith((Func2) func, operantResult));
    }
    
    public Promise<DATA> forValue(Consumer<? super DATA> theConsumer) {
        return (Promise) mapResult(result -> result.forValue((Consumer) theConsumer));
    }
    
    // == Status ==
    public Promise<DATA> ifStatusRun(ResultStatus status, Runnable runnable) {
        return (Promise) mapResult(result -> result.ifStatusRun(status, runnable));
    }
    
    public Promise<DATA> ifStatusAccept(ResultStatus status, Consumer<? super DATA> consumer) {
        return (Promise) mapResult(result -> result.ifStatusAccept(status, (Consumer) consumer));
    }
    
    public Promise<DATA> whenStatusUse(ResultStatus status, DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenStatusUse(status, fallbackValue));
    }
    
    public Promise<DATA> whenStatusGet(ResultStatus status, Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenStatusGet(status, fallbackSupplier));
    }
    
    public Promise<DATA> whenStatusApply(ResultStatus status, BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenStatusApply(status, (BiFunction) recoverFunction));
    }
    
    // == Validation ==
    public Promise<DATA> validateNotNull() {
        return (Promise) mapResult(result -> result.validateNotNull());
    }
    
    public Promise<DATA> validateNotNull(String message) {
        return (Promise) mapResult(result -> result.validateNotNull(message));
    }
    
    public Promise<DATA> validateUnavailable() {
        return (Promise) mapResult(result -> result.validateUnavailable());
    }
    
    public Promise<DATA> validateNotReady() {
        return (Promise) mapResult(result -> result.validateNotReady());
    }
    
    public Promise<DATA> validateResultCancelled() {
        return (Promise) mapResult(result -> result.validateResultCancelled());
    }
    
    public Promise<DATA> validateResultNotExist() {
        return (Promise) mapResult(result -> result.validateResultNotExist());
    }
    
    public Promise<DATA> validateNoMoreResult() {
        return (Promise) mapResult(result -> result.validateNoMoreResult());
    }
    
    public Promise<DATA> validate(String stringFormat, Predicate<? super DATA> validChecker) {
        return (Promise) mapResult(result -> result.validate(stringFormat, (Predicate) validChecker));
    }
    
    public <T> Promise<DATA> validate(String stringFormat, Func1<? super DATA, T> mapper, Predicate<? super T> validChecker) {
        return (Promise) mapResult(result -> result.validate(stringFormat, (Func1) mapper, (Predicate) validChecker));
    }
    
    public Promise<DATA> validate(Validator<DATA> validator) {
        return (Promise) mapResult(result -> result.validate((Validator) validator));
    }
    
    public Promise<Tuple2<DATA, FuncList<ValidationException>>> validate(Validator<? super DATA>... validators) {
        return (Promise) mapResult(result -> result.validate((Validator[]) validators));
    }
    
    public Promise<Tuple2<DATA, FuncList<ValidationException>>> validate(List<Validator<? super DATA>> validators) {
        return (Promise) mapResult(result -> result.validate((List) validators));
    }
    
    public Promise<DATA> ensureNotNull() {
        return (Promise) mapResult(result -> result.ensureNotNull());
    }
    
    // Alias of whenNotPresentUse
    public Promise<DATA> otherwise(DATA elseValue) {
        return (Promise) mapResult(result -> result.otherwise(elseValue));
    }
    
    // Alias of whenNotPresentGet
    public Promise<DATA> otherwiseGet(Supplier<? extends DATA> elseSupplier) {
        return (Promise) mapResult(result -> result.otherwiseGet(elseSupplier));
    }
    
    public Promise<DATA> printException() {
        return (Promise) mapResult(result -> result.printException());
    }
    
    public Promise<DATA> printException(PrintStream printStream) {
        return (Promise) mapResult(result -> result.printException(printStream));
    }
    
    public Promise<DATA> printException(PrintWriter printWriter) {
        return (Promise) mapResult(result -> result.printException(printWriter));
    }
    
    // == Disambiguous ==
    @Override
    public Promise<DATA> ifException(Consumer<? super Exception> consumer) {
        return PromiseStatusAddOn.super.ifException(consumer);
    }
    
    @Override
    public Promise<DATA> ifExceptionThenPrint() {
        return PromiseStatusAddOn.super.ifExceptionThenPrint();
    }
    
    @Override
    public Promise<DATA> ifExceptionThenPrint(PrintStream printStream) {
        return PromiseStatusAddOn.super.ifExceptionThenPrint(printStream);
    }
    
    @Override
    public Promise<DATA> ifExceptionThenPrint(PrintWriter printWriter) {
        return PromiseStatusAddOn.super.ifExceptionThenPrint(printWriter);
    }
    
    @Override
    public Promise<DATA> whenAbsentUse(DATA fallbackValue) {
        return PromiseStatusAddOn.super.whenAbsentUse(fallbackValue);
    }
    
    @Override
    public Promise<DATA> whenAbsentGet(Supplier<? extends DATA> fallbackSupplier) {
        return PromiseStatusAddOn.super.whenAbsentGet(fallbackSupplier);
    }
}
