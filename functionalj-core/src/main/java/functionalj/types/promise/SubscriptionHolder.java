package functionalj.types.promise;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import functionalj.types.result.Result;

public class SubscriptionHolder<DATA> extends Subscription<DATA> {
    
    private final AtomicReference<Consumer<Result<DATA>>> consumer = new AtomicReference<Consumer<Result<DATA>>>(null);
    private final Subscription<DATA> subscription;
    
    SubscriptionHolder(Wait wait, Promise<DATA> promise) {
        super(promise);
        this.subscription = promise.doSubscribe(requireNonNull(wait), result -> {
            Consumer<Result<DATA>> consumer = this.consumer.get();
            if (consumer != null)
                consumer.accept(result);
        });
    }
    
    public Consumer<Result<DATA>> getConsumer() {
        return this.consumer.get();
    }
    
    public SubscriptionHolder<DATA> assign(Consumer<Result<DATA>> consumer) {
        Result<DATA> result = subscription.getPromise().getResult();
        this.consumer.set(consumer);
        if (result.isReady()) {
            consumer.accept(result);
        }
        return this;
    }
    
    public SubscriptionHolder<DATA> unsubscribe() {
        subscription.unsubscribe();
        return this;
    }
    
}
