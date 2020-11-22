// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Func.carelessly;

import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import lombok.val;

public class PendingAction<DATA> extends UncompletedAction<DATA> implements Pipeable<HasPromise<DATA>> {
    
    PendingAction(Promise<DATA> promise) {
        super(promise);
    }
    
    public PendingAction<DATA> start() {
        return this;
    }
    
    public HasPromise<DATA> __data() throws Exception {
        return this;
    }
    
    //== Subscription ==
    
    public PendingAction<DATA> use(FuncUnit1<Promise<DATA>> consumer) {
        carelessly(()->{
            consumer.accept(promise);
        });
        
        return this;
    }
    
    public PendingAction<DATA> abortNoSubsriptionAfter(Wait wait) {
        promise.abortNoSubscriptionAfter(wait);
        return this;
    }
    
    public PendingAction<DATA> subscribe(FuncUnit1<DATA> resultConsumer) {
        promise.subscribe(Wait.forever(), resultConsumer);
        return this;
    }
    
    public final PendingAction<DATA> subscribe(Wait wait, FuncUnit1<DATA> resultConsumer) {
        promise.subscribe(wait, resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> onComplete(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.onComplete(Wait.forever(), resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> onComplete(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.onComplete(wait, resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> onComplete(
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
        var subscription = promise.onComplete(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public PendingAction<DATA> onComplete(
            Wait                          wait,
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
        var subscription = promise.onComplete(wait, resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public PendingAction<DATA> eavesdrop(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(resultConsumer);
        return this;
    }
    
    public PendingAction<DATA> eavesdrop(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(wait, resultConsumer);
        return this;
    }
    
    //== Functional ==
    
    public final PendingAction<DATA> filter(Predicate<? super DATA> predicate) {
        var newPromise = promise.filter(predicate);
        return new PendingAction<DATA>(newPromise);
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> PendingAction<TARGET> map(Func1<? super DATA, ? extends TARGET> mapper) {
        var newPromise = promise.map(mapper);
        return new PendingAction<TARGET>((Promise<TARGET>)newPromise);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public final <TARGET> PendingAction<TARGET> flatMap(Func1<? super DATA, ? extends HasPromise<? extends TARGET>> mapper) {
        return chain((Func1)mapper);
    }
    
    public final <TARGET> PendingAction<TARGET> chain(Func1<DATA, ? extends HasPromise<TARGET>> mapper) {
        var newPromise = promise.flatMap(mapper);
        return new PendingAction<TARGET>((Promise<TARGET>)newPromise);
    }
    
    // TODO - Other F-M-FM methods.
    
    
}