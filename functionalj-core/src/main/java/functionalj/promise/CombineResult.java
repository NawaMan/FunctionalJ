package functionalj.promise;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.NamedExpression;
import functionalj.list.FuncList;
import functionalj.result.Result;
import lombok.val;

@SuppressWarnings("rawtypes")
public class CombineResult<D> {
    
    private final Func1<FuncList<Result>, Result<D>> mergeFunc;
    // TODO - Add Else ... which will be called with the current value when unsuccessfull.
    
    private final DeferAction<D>       action;
    private final int                  count;
    private final Result[]             results;
    private final SubscriptionRecord[] subscriptions;
    private final AtomicBoolean        isDone;
    private final Promise<D>           promise;
    
    CombineResult(FuncList<NamedExpression<HasPromise<Object>>> hasPromises,
             Func1<FuncList<Result>, Result<D>>                 mergeFunc) {
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
        .mapWithIndex    ((index, promise) -> promise.onComplete(result -> processResult(index, result)))
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
        
        val resultList   = FuncList.from(results);
        val mergedResult = mergeFunc.apply(resultList);
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
