package functionalj.types.promise;

import static functionalj.functions.Func.F;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.functions.Func1;
import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.types.list.FuncList;
import functionalj.types.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public class Promises {
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2> Promise<D> of(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Wait                                       wait,
            BiFunction<T1, T2, D> merger) {
        return processCombine(
                FuncList.of(promise1, promise2),
                F((Result[] results)-> {
                    val result1 = (Result<T1>)results[0];
                    val result2 = (Result<T2>)results[1];
                    val mergedResult = Result.ofResults(result1, result2, merger);
                    return mergedResult;
                }))
                .getPromise();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3> Promise<D> of(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Wait                                       wait,
            Func3<T1, T2, T3, D> merger) {
        return processCombine(
                FuncList.of(promise1, promise2, promise3),
                F((Result[] results)-> {
                    val result1 = (Result<T1>)results[0];
                    val result2 = (Result<T2>)results[1];
                    val result3 = (Result<T3>)results[2];
                    val mergedResult = Result.ofResults(result1, result2, result3, merger);
                    return mergedResult;
                }))
                .getPromise();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4> Promise<D> of(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Wait                                       wait,
            Func4<T1, T2, T3, T4, D> merger) {
        return processCombine(
                FuncList.of(promise1, promise2, promise3, promise4),
                F((Result[] results)-> {
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
    public static <D, T1, T2, T3, T4, T5> Promise<D> of(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Function<Object, ? extends HasPromise<T5>> promise5,
            Wait                                       wait,
            Func5<T1, T2, T3, T4, T5, D> merger) {
        return processCombine(
                FuncList.of(promise1, promise2, promise3, promise4),
                F((Result[] results)-> {
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
    public static <D, T1, T2, T3, T4, T5, T6> Promise<D> of(
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            Function<Object, ? extends HasPromise<T3>> promise3,
            Function<Object, ? extends HasPromise<T4>> promise4,
            Function<Object, ? extends HasPromise<T5>> promise5,
            Function<Object, ? extends HasPromise<T6>> promise6,
            Wait                                       wait,
            Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        return processCombine(
                FuncList.of(promise1, promise2, promise3, promise4, promise5, promise6),
                F((Result[] results)-> {
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
    
    @SuppressWarnings("rawtypes")
    private static <D> PendingAction<D> processCombine(
            FuncList<Function<Object, ? extends HasPromise<? extends Object>>> promises,
            Func1<Result[], Result<D>>                                         mergeFunction) {
        val promiseControl = DeferAction.of((Class<D>)null).start();
        val count          = promises.size();
        val results        = new Result[count];
        val subscriptions  = new Subscription[count];
        val isDone         = new AtomicBoolean(false);
        
        @SuppressWarnings("unchecked")
        val action  = F((Integer index, Result result)->{
            result
            .filter     (__ -> !isDone.get())
            .ifCancelled(() -> cancel         (isDone, promiseControl, subscriptions, index))
            .ifNotReady (() -> handleNotReady (isDone, promiseControl, subscriptions, index, result))
            .ifException(__ -> handleException(isDone, promiseControl, subscriptions, index, result, count))
            .peek       (__ -> results[index] = result)
            .filter     (__ -> count == Stream.of(results).filter(Objects::nonNull).count())
            .filter     (__ -> isDone.compareAndSet(false, true))
            .forValue   (__ -> {
                val mergedResult = mergeFunction.apply(results);
                promiseControl.completeWith(mergedResult);
            });
        });
        
        promises
        .map             ( promise              -> promise.apply(null))
        .map             ( promise              -> promise.getPromise())
        .mapWithIndex    ((index, promise)      -> promise.subscribe(result -> action.accept(index, result)))
        .forEachWithIndex((index, subscription) -> subscriptions[index] = subscription);
        return promiseControl;
    }
    
    @SuppressWarnings("rawtypes")
    private static void unsbscribeAll(Subscription[] subscriptions) {
        for(val subscription : subscriptions) {
            if (subscription != null)
                subscription.unsubscribe();
        }
    }
    
    // Organize the order.
    
    @SuppressWarnings("rawtypes")
    private static <D> void cancel(
            AtomicBoolean    isDone,
            PendingAction<D> promiseControl,
            Subscription[]   subscriptions,
            Integer          index) {
        if (!isDone.compareAndSet(false, true))
            return;
        
        promiseControl.abort("Promise#" + index);
        unsbscribeAll(subscriptions);
    }
    
    @SuppressWarnings("rawtypes")
    private static <D> void handleNotReady(
            AtomicBoolean    isDone,
            PendingAction<D> promiseControl,
            Subscription[]   subscriptions, 
            Integer          index, 
            Result           result) {
        if (!isDone.compareAndSet(false, true))
            return;
        
        promiseControl.abort(
                "Promise#" + index, 
                new IllegalStateException(
                        "Result cannot be in 'not ready' at this point: " + result.getStatus(),
                        result.getException()));
        unsbscribeAll(subscriptions);
    }
    
    @SuppressWarnings("rawtypes")
    private static <D> void handleException(
            AtomicBoolean    isDone,
            PendingAction<D> promiseControl,
            Subscription[]   subscriptions,
            Integer          index,
            Result           result,
            int              count) {
        if (!isDone.compareAndSet(false, true))
            return;
        
        promiseControl.fail(new PromisePartiallyFailException(index, count, result.getException()));
        unsbscribeAll(subscriptions);
    }
    
}
