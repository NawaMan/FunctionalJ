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

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import functionalj.result.Result;
import lombok.val;

public class SubscriptionRecord<DATA> implements HasPromise<DATA> {
    
    public static enum SubscriptionStatus {
        
        AWAITING, UNSUBSCRIBED, CANCELLED, COMPLETED;
        
        public boolean isAwaiting() {
            return AWAITING.equals(this);
        }
        
        public boolean isUnsubscribed() {
            return UNSUBSCRIBED.equals(this);
        }
        
        public boolean isCancelled() {
            return CANCELLED.equals(this);
        }
        
        public boolean isCompleted() {
            return COMPLETED.equals(this);
        }
        
        public boolean isNotDone() {
            return !isDone();
        }
        
        public boolean isDone() {
            return isCancelled() || isCompleted();
        }
    }
    
    private final Promise<DATA> promise;
    
    SubscriptionRecord(Promise<DATA> promise) {
        this.promise = Objects.requireNonNull(promise);
    }
    
    public Promise<DATA> getPromise() {
        return promise;
    }
    
    public SubscriptionStatus getSubscriptionStatus() {
        val promiseStatus = promise.getStatus();
        Objects.requireNonNull(promiseStatus);
        if (promiseStatus.isComplete())
            return SubscriptionStatus.COMPLETED;
        if (promiseStatus.isAborted())
            return SubscriptionStatus.CANCELLED;
        if (!promise.isSubscribed(this))
            return SubscriptionStatus.UNSUBSCRIBED;
        return SubscriptionStatus.AWAITING;
    }
    
    public SubscriptionRecord<DATA> unsubscribe() {
        promise.unsubscribe(this);
        return this;
    }
    
    public boolean isAwaiting() {
        return getSubscriptionStatus().isAwaiting();
    }
    
    public boolean isUnsubscribed() {
        return getSubscriptionStatus().isUnsubscribed();
    }
    
    public boolean isCancelled() {
        return getSubscriptionStatus().isCancelled();
    }
    
    public boolean isCompleted() {
        return getSubscriptionStatus().isCompleted();
    }
    
    public boolean isNotDone() {
        return getSubscriptionStatus().isNotDone();
    }
    
    public boolean isDone() {
        return getSubscriptionStatus().isDone();
    }
    
    public final Result<DATA> getResult() {
        return promise.getResult(-1, null);
    }
    
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        return promise.getResult(timeout, unit);
    }
}
