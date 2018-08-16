package functionalj.types.promise;

import static functionalj.functions.Func.F;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.result.Result;
import functionalj.types.stream.StreamPlus;
import lombok.val;

@SuppressWarnings("javadoc")
public class Promises {
    
    @SuppressWarnings("rawtypes")
    public static <D, T1, T2> Promise<D> of(
            Wait                          wait,
            Function<Object, ? extends HasPromise<T1>> promise1,
            Function<Object, ? extends HasPromise<T2>> promise2,
            BiFunction<T1, T2, D> merger) {
        val count = 2;
        val promiseControl = DeferAction.of((Class<D>)null).start();
        
        val results       = new Result[count];
        val subscriptions = new Subscription[count];
        val isDone        = new AtomicBoolean(false);
        
        @SuppressWarnings("unchecked")
        val action  = F((Integer index, Result result)->{
            if (isDone.get())
                return;
            
            // Cancelled - so unsubscribe everything.
            if (result.isCancelled()) {
                if (isDone.compareAndSet(false, true))
                    cancel(promiseControl, subscriptions, index);
                return;
            }
            // NotReady - this cannot be.
            if (result.isNotReady()) {
                if (isDone.compareAndSet(false, true))
                    handleNotReady(promiseControl, subscriptions, index, result);
                return;
            }
            // Exception - Fail fast, unsubscribe everything.
            if (result.isException()) {
                if (isDone.compareAndSet(false, true))
                    handleException(count, promiseControl, subscriptions, index, result);
                return;
            }
            
            results[index] = result;
            if (count != Stream.of(results).filter(Objects::nonNull).count())
                return;
            
            if (!isDone.compareAndSet(false, true))
                return;
            
            val result1 = (Result<T1>)results[0];
            val result2 = (Result<T2>)results[1];
            val mergedResult = Result.ofResults(result1, result2, merger);
            promiseControl.completeWith(mergedResult);
        });
        
        StreamPlus
        .of(promise1, promise2)
        .map             ( promise              -> promise.apply(null))
        .map             ( promise              -> promise.getPromise())
        .mapWithIndex    ((index, promise)      -> promise.subscribe(result -> action.accept(index, result)))
        .forEachWithIndex((index, subscription) -> subscriptions[index] = subscription);
        
        return promiseControl.getPromise();
    }
    
    @SuppressWarnings("rawtypes")
    private static void unsbscribeAll(Subscription[] subscriptions) {
        for(val subscription : subscriptions) {
            if (subscription != null)
                subscription.unsubscribe();
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static <D> void cancel(
            PendingAction<D> promiseControl,
            Subscription[]              subscriptions,
            Integer                     index) {
        promiseControl.abort("Promise#" + index);
        unsbscribeAll(subscriptions);
    }
    
    @SuppressWarnings("rawtypes")
    private static <D> void handleNotReady(
            PendingAction<D> promiseControl,
            Subscription[]              subscriptions, 
            Integer                     index, 
            Result                      result) {
        promiseControl.abort(
                "Promise#" + index, 
                new IllegalStateException(
                        "Result cannot be in 'not ready' at this point: " + result.getStatus(),
                        result.getException()));
        unsbscribeAll(subscriptions);
    }
    
    @SuppressWarnings("rawtypes")
    private static <D> void handleException(
            int                         count,
            PendingAction<D> promiseControl,
            Subscription[]              subscriptions,
            Integer                     index,
            Result                      result) {
        promiseControl.fail(new PromisePartiallyFailException(index, count, result.getException()));
        unsbscribeAll(subscriptions);
    }
    
}
