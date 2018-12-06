package functionalj.event;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
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
            .forEach(sub -> {
                try {
                    sub.notifyNext(result);
                } catch (Throwable e) {
                    // TODO: handle exception
                }
            });
    }
    
    private <T> Topic<T> newSubTopic(FuncUnit2<Result<DATA>, Topic<T>> resultConsumer) {
        val topic = new SubTopic<T>(this, resultConsumer);
        return topic;
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Topic<TARGET> map(Func1<? super DATA, ? extends TARGET> mapper) {
        requireNonNull(mapper);
        return (Topic<TARGET>)newSubTopic((Result<DATA> r, Topic<TARGET> targetTopic) -> {
            val result = r.map(mapper);
            targetTopic.notifySubscription((Result<TARGET>)result);
        });
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Topic<TARGET> mapResult(Func1<Result<? super DATA>, Result<? extends TARGET>> mapper) {
        requireNonNull(mapper);
        return (Topic<TARGET>)newSubTopic((Result<DATA> r, Topic<TARGET> targetTopic) -> {
            val result = mapper.apply(r);
            targetTopic.notifySubscription((Result<TARGET>)result);
        });
    }
    public Topic<DATA> filter(Predicate<? super DATA> filter) {
        requireNonNull(filter);
        return (Topic<DATA>)newSubTopic((Result<DATA> r, Topic<DATA> targetTopic) -> {
            val result = r.filter(filter);
            targetTopic.notifySubscription((Result<DATA>)result);
        });
    }
    
    public Topic<DATA> filterResult(Predicate<Result<? super DATA>> filter) {
        requireNonNull(filter);
        return (Topic<DATA>)newSubTopic((Result<DATA> r, Topic<DATA> targetTopic) -> {
            val result = r.flatMap(d -> filter.test(r) ? r : Result.ofNull());
            targetTopic.notifySubscription((Result<DATA>)result);
        });
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
        subscriptions.getAndUpdate(subs -> {
            val newSub = subs.exclude(subscription).toImmutableList();
            if (newSub.isEmpty())
                done();
            return newSub;
        });
    }
    
}
