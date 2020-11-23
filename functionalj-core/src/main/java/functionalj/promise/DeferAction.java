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
import static functionalj.function.Func.f;
import static functionalj.list.FuncList.listOf;
import static functionalj.promise.RaceResult.Race;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

import functionalj.environments.AsyncRunner;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit0;
import functionalj.function.FuncUnit1;
import functionalj.function.NamedExpression;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;


public class DeferAction<DATA> extends UncompletedAction<DATA> implements Pipeable<HasPromise<DATA>> {
    
    public static <D> DeferAction<D> createNew() {
        return of((Class<D>)null);
    }
    public static <D> DeferAction<D> createNew(OnStart onStart) {
        return of((Class<D>)null, onStart);
    }
    public static <D> DeferAction<D> of(Class<D> clzz) {
        return new DeferAction<D>();
    }
    public static <D> DeferAction<D> of(Class<D> clzz, OnStart onStart) {
        return new DeferAction<D>(null, onStart);
    }
    
    public static <D> DeferAction<D> ofValue(D value) {
        var action = new DeferAction<D>();
        action.getPromise().makeComplete(value);
        return action;
    }
    
    public static DeferAction<Object> defer(FuncUnit0 runnable) {
        return DeferAction.from(runnable);
    }
    public static <D> DeferAction<D> defer(Func0<D> supplier) {
        return DeferAction.from(supplier);
    }
    public static <D> DeferAction<D> defer(CompletableFuture<D> completableFucture) {
        return DeferAction.from(completableFucture);
    }
    
    public static DeferAction<Object> from(FuncUnit0 runnable) {
        return DeferActionConfig.current.value().createBuilder(runnable).build();
    }
    public static <D> DeferAction<D> from(Func0<D> supplier) {
        return DeferActionConfig.current.value().createBuilder(supplier).build();
    }
    public static <D> DeferAction<D> from(CompletableFuture<D> completableFucture) {
        var action = DeferAction.of((Class<D>)null);
        var pending = action.start();
        
        completableFucture.handle((value, exception) -> {
            if (exception != null) {
                if (exception instanceof Exception)
                     pending.fail((Exception)exception);
                else pending.fail(new RuntimeException("CompletableFuture completed with failure: ", exception));
            } else {
                pending.complete(value);
            }
            
            return null;
        });
        return action;
    }
    
    public static PendingAction<Object> run(FuncUnit0 runnable) {
        return DeferAction.from(runnable)
                .start();
    }
    public static <D> PendingAction<D> run(Func0<D> supplier) {
        return DeferAction.from(supplier)
                .start();
    }
    
    @SafeVarargs
    public static <D> RaceResult<D> AnyOf(StartableAction<D> ... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> AnyOf(List<StartableAction<D>> actions) {
        return Race(actions);
    }
    @SafeVarargs
    public static <D> RaceResult<D> race(StartableAction<D> ... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> race(List<StartableAction<D>> actions) {
        return Race(actions);
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            Func2<T1, T2, D>                merger) {
        var merge = (Func1)f((FuncList<Result> results)-> {
            var result1 = (Result<T1>)results.get(0);
            var result2 = (Result<T2>)results.get(1);
            var mergedResult = Result.ofResults(result1, result2, merger);
            return (Result<D>)mergedResult;
        });
        var promises = listOf(promise1, promise2);
        var combiner = new CombineResult(promises, merge);
        var action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            Func3<T1, T2, T3, D>            merger) {
        var merge = (Func1)f((FuncList<Result> results)-> {
            var result1 = (Result<T1>)results.get(0);
            var result2 = (Result<T2>)results.get(1);
            var result3 = (Result<T3>)results.get(2);
            var mergedResult = Result.ofResults(result1, result2, result3, merger);
            return (Result<D>)mergedResult;
        });
        var promises = listOf(promise1, promise2, promise3);
        var combiner = new CombineResult(promises, merge);
        var action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            Func4<T1, T2, T3, T4, D>        merger) {
        var merge = (Func1)f((FuncList<Result> results)-> {
            var result1 = (Result<T1>)results.get(0);
            var result2 = (Result<T2>)results.get(1);
            var result3 = (Result<T3>)results.get(2);
            var result4 = (Result<T4>)results.get(3);
            var mergedResult = Result.ofResults(result1, result2, result3, result4, merger);
            return (Result<D>)mergedResult;
        });
        var promises = listOf(promise1, promise2, promise3, promise4);
        var combiner = new CombineResult(promises, merge);
        var action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4, T5> DeferAction<D> from(
            NamedExpression<HasPromise<T1>> promise1,
            NamedExpression<HasPromise<T2>> promise2,
            NamedExpression<HasPromise<T3>> promise3,
            NamedExpression<HasPromise<T4>> promise4,
            NamedExpression<HasPromise<T5>> promise5,
            Func5<T1, T2, T3, T4, T5, D>    merger) {
        var merge = (Func1)f((FuncList<Result> results)-> {
            var result1 = (Result<T1>)results.get(0);
            var result2 = (Result<T2>)results.get(1);
            var result3 = (Result<T3>)results.get(2);
            var result4 = (Result<T4>)results.get(3);
            var result5 = (Result<T5>)results.get(4);
            var mergedResult = Result.ofResults(result1, result2, result3, result4, result5, merger);
            return (Result<D>)mergedResult;
        });
        var promises = listOf(promise1, promise2, promise3, promise4, promise5);
        var combiner = new CombineResult(promises, merge);
        var action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T1, T2, T3, T4, T5, T6> DeferAction<D> from(
            NamedExpression<HasPromise<T1>>  promise1,
            NamedExpression<HasPromise<T2>>  promise2,
            NamedExpression<HasPromise<T3>>  promise3,
            NamedExpression<HasPromise<T4>>  promise4,
            NamedExpression<HasPromise<T5>>  promise5,
            NamedExpression<HasPromise<T6>>  promise6,
            Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        var merge = (Func1)f((FuncList<Result> results)-> {
            var result1 = (Result<T1>)results.get(0);
            var result2 = (Result<T2>)results.get(1);
            var result3 = (Result<T3>)results.get(2);
            var result4 = (Result<T4>)results.get(3);
            var result5 = (Result<T5>)results.get(4);
            var result6 = (Result<T6>)results.get(5);
            var mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, merger);
            return (Result<D>)mergedResult;
        });
        var promises = listOf(promise1, promise2, promise3, promise4, promise5, promise6);
        var combiner = new CombineResult(promises, merge);
        var action   = combiner.getDeferAction();
        return action;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D, T> DeferAction<D> from(
            Func1<FuncList<T>, D> merger,
            NamedExpression<HasPromise<T>> ... promises) {
        var merge = f((Result<T>[] results)-> {
            var resultList = listOf(results).map(Result::get);
            var mergedResult = merger.apply(resultList);
            return (Result<D>)mergedResult;
        });
        var promiseList = listOf(promises);
        var combiner    = new CombineResult(promiseList, merge);
        var action      = combiner.getDeferAction();
        return action;
    }
    
    
    public static <D> DeferAction<D> create(
            boolean     interruptOnCancel,
            Func0<D>    supplier,
            Runnable    onStart,
            AsyncRunner runner) {
        return DeferActionCreator.current.value()
                .create(supplier, onStart, interruptOnCancel, runner);
    }
    
    private final Runnable task;
    
    private final DeferAction<?> parent;
    
    DeferAction() {
        this(null, (OnStart)null);
    }
    DeferAction(DeferAction<?> parent, Promise<DATA> promise) {
        super(promise);
        this.parent = parent;
        this.task   = null;
    }
    DeferAction(Runnable task, OnStart onStart) {
        super(onStart);
        this.parent = null;
        this.task   = task;
    }
    
    public PendingAction<DATA> start() {
        if (parent != null) {
            parent.start();
        } else {
            var isStarted = promise.start();
            
            if (!isStarted && (task != null))
                carelessly(task);
        }
        return new PendingAction<>(promise);
    }
    
    public HasPromise<DATA> __data() throws Exception {
        return this;
    }
    
    //== Subscription ==
    
    public DeferAction<DATA> use(Consumer<Promise<DATA>> consumer) {
        carelessly(()->{
            consumer.accept(promise);
        });
        
        return this;
    }
    
    public DeferAction<DATA> abortNoSubsriptionAfter(Wait wait) {
        promise.abortNoSubscriptionAfter(wait);
        return this;
    }
    
    public DeferAction<DATA> subscribe(FuncUnit1<DATA> resultConsumer) {
        promise.subscribe(Wait.forever(), resultConsumer);
        return this;
    }
    
    public final DeferAction<DATA> subscribe(Wait wait, FuncUnit1<DATA> resultConsumer) {
        promise.subscribe(wait, resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> onComplete(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.onComplete(Wait.forever(), resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> onComplete(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.onComplete(wait, resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> onComplete(
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
        var subscription = promise.onComplete(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> onComplete(
            Wait                          wait,
            FuncUnit1<Result<DATA>>       resultConsumer,
            FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
        var subscription = promise.onComplete(wait, resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> eavesdrop(FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(resultConsumer);
        return this;
    }
    
    public DeferAction<DATA> eavesdrop(Wait wait, FuncUnit1<Result<DATA>> resultConsumer) {
        promise.eavesdrop(wait, resultConsumer);
        return this;
    }
    
    //== Functional ==
    
    public final DeferAction<DATA> filter(Predicate<? super DATA> predicate) {
        var newPromise = promise.filter(predicate);
        return new DeferAction<DATA>(this, newPromise);
    }
    
    public final DeferAction<DATA> peek(FuncUnit1<? super DATA> peeker) {
        var newPromise = promise.peek(peeker);
        return new DeferAction<DATA>(this, (Promise<DATA>)newPromise);
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> DeferAction<TARGET> map(Func1<? super DATA, ? extends TARGET> mapper) {
        var newPromise = promise.map(mapper);
        var newAction  = new DeferAction<TARGET>(this, (Promise<TARGET>)newPromise);
        return newAction;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final <TARGET> DeferAction<TARGET> flatMap(Func1<? super DATA, ? extends HasPromise<? extends TARGET>> mapper) {
        return chain((Func1)mapper);
    }
    
    public final <TARGET> DeferAction<TARGET> chain(Func1<DATA, ? extends HasPromise<TARGET>> mapper) {
        var newPromise = promise.chain(mapper);
        return new DeferAction<TARGET>(this, (Promise<TARGET>)newPromise);
    }
    
    // TODO - Other F-M-FM methods.
    
}