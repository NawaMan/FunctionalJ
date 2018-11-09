package functionalj.reactive;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.list.FuncList;
import functionalj.result.Result;
import lombok.val;

public class Topic<DATA> {
    
    private final AtomicReference<FuncList<Subscription<DATA>>> subscriptions 
            = new AtomicReference<>(FuncList.empty());
    
    private final AtomicBoolean isActive = new AtomicBoolean(true);
    
    public boolean isActive() {
        return isActive.get();
    }
    
    boolean publish(DATA data) {
        boolean stillActive = isActive.get();
        if (stillActive) {
            val result = Result.of(data);
            notifySubscription(result);
        }
        return stillActive;
    }
    
    void done() {
        notifySubscription(Result.ofNoMore());
        
        isActive.set(false);
        subscriptions.set(FuncList.empty());
    }
    
    private void notifySubscription(Result<DATA> result) {
        subscriptions
            .get()
            .filter (Subscription::isActive)
            .forEach(sub -> sub.notifyNext(result));
    }
    
    public Subscription<DATA> subscribe(FuncUnit1<DATA> subscribe) {
        return subscribe(subscribe.thenReturn(Subscription.Continue));
    }
    public Subscription<DATA> subscribe(Func1<DATA, Cancellation> subscribe) {
        return onNext(result -> {
            return result.map(subscribe).orElse(Subscription.Continue);
        });
    }
    
    public Subscription<DATA> onNext(FuncUnit1<Result<DATA>> subscribe) {
        return onNext(subscribe.thenReturn(Subscription.Continue));
    }
    public Subscription<DATA> onNext(Func1<Result<DATA>, Cancellation> subscribe) {
        requireNonNull(subscribe);
        val subscription = new Subscription<DATA>(this, subscribe);
        subscriptions.getAndUpdate(subs -> {
            if (subs.isEmpty() && !isActive.get()) {
                unsubcribe(subscription);
                return subs;
            }
            return subs.append(subscription).toImmutableList();
        });
        return subscription;
    }
    
    void unsubcribe(Subscription<DATA> subscription) {
        subscription.notifyNext(Result.ofNoMore());
        subscriptions.getAndUpdate(subs -> subs.exclude(subscription).toImmutableList());
    }
    
}
