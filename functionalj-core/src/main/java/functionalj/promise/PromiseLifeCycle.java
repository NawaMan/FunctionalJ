package functionalj.promise;

import static functionalj.function.Func.carelessly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import functionalj.function.FuncUnit1;
import functionalj.result.Result;
import lombok.val;

class PromiseLifeCycle {
    
    static <DATA> boolean makeDone(Promise<DATA> promise, Result<DATA> result) {
        System.out.println("makeDone: " + promise + ", result: " + result);
        
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
    
    static <DATA> Result<DATA> getResult(Promise<DATA> promise, long timeout, TimeUnit unit) {
        System.out.println(promise + ".getResult() = first, status: " + promise.getStatus());
        promise.start();
        if (!promise.isDone()) {
            System.out.println(promise + ".getResult() = not done, status: " + promise.getStatus());
            val latch = new CountDownLatch(1);
            promise.synchronouseOperation(()->{
                promise.onComplete(result -> {
                    latch.countDown();
                    System.out.println(promise + ".getResult() -- latch.countDown(), status: " + promise.getStatus());
                });
                return promise.isDone();
            });
            
            if (!promise.isDone()) {
                try {
                    if ((timeout < 0) || (unit == null))
                         latch.await();
                    else latch.await(timeout, unit);
                    System.out.println(promise + ".getResult() -- latch.await() -- released, status: " + promise.getStatus());
                    
                } catch (InterruptedException exception) {
                    throw new UncheckedInterruptedException(exception);
                }
            }
        }
        
        if (!promise.isDone())
            throw new UncheckedInterruptedException(new InterruptedException());
        
        val currentResult = promise.getCurrentResult();
        System.out.println(promise + ".getResult() = getCurrentResult(): " + currentResult + ", status: " + promise.getStatus());
        return currentResult;
    }
    
}
