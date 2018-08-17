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
        return new Combiner(
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
        return new Combiner(
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
        return new Combiner(
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
        return new Combiner(
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
        return new Combiner(
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
    private static class Combiner<D> {
        
        private final Func1<Result[], Result<D>> mergeFunc;
        
        private final PendingAction<D> action;
        private final int              count;
        private final Result[]         results;
        private final Subscription[]   subscriptions;
        private final AtomicBoolean    isDone;
        private final Promise<D>       promise;
        
        Combiner(FuncList<Function<Object, ? extends HasPromise<? extends Object>>> promises,
                 Func1<Result[], Result<D>>                                         mergeFunc) {
            this.mergeFunc     = mergeFunc;
            this.action        = DeferAction.of((Class<D>)null).start();
            this.count         = promises.size();
            this.results       = new Result[count];
            this.subscriptions = new Subscription[count];
            this.isDone        = new AtomicBoolean(false);
            
            promises
            .map             ( promise         -> promise.apply(null))
            .map             ( promise         -> promise.getPromise())
            .mapWithIndex    ((index, promise) -> promise.subscribe(result -> processResult(index, result)))
            .forEachWithIndex((index, sub)     -> subscriptions[index] = sub);
            
            this.promise = action.getPromise();
        }
        
        Promise<D> getPromise() {
            return promise;
        }
        
        private <T> void processResult(int index, Result<T> result) {
            result
            .filter     (__ -> !isDone.get())
            .ifCancelled(() -> doneAsCancelled(index))
            .ifNotReady (() -> doneAsNotReady (index, result))
            .ifException(__ -> doneAsException(index, result))
            .peek       (__ -> results[index] = result)
            .filter     (__ -> count == Stream.of(results).filter(Objects::nonNull).count())
            .filter     (__ -> isDone.compareAndSet(false, true))
            .forValue   (__ -> {
                val mergedResult = mergeFunc.apply(results);
                action.completeWith(mergedResult);
            });
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
