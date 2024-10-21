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

import static functionalj.function.Func.carelessly;
import static functionalj.function.Func.f;
import static functionalj.list.FuncList.listOf;
import static functionalj.promise.RaceResult.Race;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.environments.AsyncRunner;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func10;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import functionalj.function.FuncUnit0;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import functionalj.result.ResultStatus;
import functionalj.result.ValidationException;
import functionalj.tuple.Tuple2;
import functionalj.validator.Validator;
import lombok.val;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DeferAction<DATA> extends UncompletedAction<DATA> implements Pipeable<HasPromise<DATA>> {
    
    public static <D> DeferAction<D> createNew() {
        return of((Class<D>) null);
    }
    
    public static <D> DeferAction<D> createNew(OnStart onStart) {
        return of((Class<D>) null, onStart);
    }
    
    public static <D> DeferAction<D> of(Class<D> clzz) {
        return new DeferAction<D>();
    }
    
    public static <D> DeferAction<D> of(Class<D> clzz, OnStart onStart) {
        return new DeferAction<D>(null, onStart);
    }
    
    public static <D> DeferAction<D> ofValue(D value) {
        val action = new DeferAction<D>();
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
        val action = DeferAction.of((Class<D>) null);
        val pending = action.start();
        completableFucture.handle((value, exception) -> {
            if (exception != null) {
                if (exception instanceof Exception)
                    pending.fail((Exception) exception);
                else
                    pending.fail(new RuntimeException("CompletableFuture completed with failure: ", exception));
            } else {
                pending.complete(value);
            }
            return null;
        });
        return action;
    }
    
    public static PendingAction<Object> run(FuncUnit0 runnable) {
        return DeferAction.from(runnable).start();
    }
    
    public static <D> PendingAction<D> run(Func0<D> supplier) {
        return DeferAction.from(supplier).start();
    }
    
    @SafeVarargs
    public static <D> RaceResult<D> AnyOf(StartableAction<D>... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> AnyOf(List<StartableAction<D>> actions) {
        return Race(actions);
    }
    
    @SafeVarargs
    public static <D> RaceResult<D> race(StartableAction<D>... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> race(List<StartableAction<D>> actions) {
        return Race(actions);
    }
    
    //== From - HasPromise ==
    
    /**
     * Creates a {@code DeferAction} from two {@code HasPromise} instances and a {@code Func2} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param merger    the {@code Func2} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    Func2<T1, T2, D> merger) {
        val merge = (Func1)f((FuncList<Result<Object>> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val mergedResult = Result.ofResults(result1, result2, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from three {@code HasPromise} instances and a {@code Func3} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param promise3  the third promise.
     * @param merger    the {@code Func3} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    Func3<T1, T2, T3, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val mergedResult = Result.ofResults(result1, result2, result3, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from four {@code HasPromise} instances and a {@code Func4} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param promise3  the third promise.
     * @param promise4  the fourth promise.
     * @param merger    the {@code Func4} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    Func4<T1, T2, T3, T4, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val result4  = (Result<T4>) results.get(3);
            val mergedResult = Result.ofResults(result1, result2, result3, result4, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from five {@code HasPromise} instances and a {@code Func5} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param promise3  the third promise.
     * @param promise4  the fourth promise.
     * @param promise5  the fifth promise.
     * @param merger    the {@code Func5} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    Func5<T1, T2, T3, T4, T5, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val result4  = (Result<T4>) results.get(3);
            val result5  = (Result<T5>) results.get(4);
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from six {@code HasPromise} instances and a {@code Func6} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param promise3  the third promise.
     * @param promise4  the fourth promise.
     * @param promise5  the fifth promise.
     * @param promise6  the sixth promise.
     * @param merger    the {@code Func6} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val result4  = (Result<T4>) results.get(3);
            val result5  = (Result<T5>) results.get(4);
            val result6  = (Result<T6>) results.get(5);
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5, promise6, promise6);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from seven {@code HasPromise} instances and a {@code Func7} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param promise3  the third promise.
     * @param promise4  the fourth promise.
     * @param promise5  the fifth promise.
     * @param promise6  the sixth promise.
     * @param promise7  the seventh promise.
     * @param merger    the {@code Func7} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7,
                    Func7<T1, T2, T3, T4, T5, T6, T7, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val result4  = (Result<T4>) results.get(3);
            val result5  = (Result<T5>) results.get(4);
            val result6  = (Result<T6>) results.get(5);
            val result7  = (Result<T7>) results.get(6);
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, result7, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5, promise6, promise6, promise7);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from eight {@code HasPromise} instances and a {@code Func8} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param promise3  the third promise.
     * @param promise4  the fourth promise.
     * @param promise5  the fifth promise.
     * @param promise6  the sixth promise.
     * @param promise7  the seventh promise.
     * @param promise8  the eighth promise.
     * @param merger    the {@code Func8} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7,
                    HasPromise<T8> promise8,
                    Func8<T1, T2, T3, T4, T5, T6, T7, T8, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val result4  = (Result<T4>) results.get(3);
            val result5  = (Result<T5>) results.get(4);
            val result6  = (Result<T6>) results.get(5);
            val result7  = (Result<T7>) results.get(6);
            val result8  = (Result<T8>) results.get(7);
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, result7, result8, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5, promise6, promise6, promise7, promise8);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from nine {@code HasPromise} instances and a {@code Func9} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1  the first promise.
     * @param promise2  the second promise.
     * @param promise3  the third promise.
     * @param promise4  the fourth promise.
     * @param promise5  the fifth promise.
     * @param promise6  the sixth promise.
     * @param promise7  the seventh promise.
     * @param promise8  the eighth promise.
     * @param promise9  the ninth promise.
     * @param merger    the {@code Func9} function that merges the results of the promises.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8, T9> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7,
                    HasPromise<T8> promise8,
                    HasPromise<T9> promise9,
                    Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val result4  = (Result<T4>) results.get(3);
            val result5  = (Result<T5>) results.get(4);
            val result6  = (Result<T6>) results.get(5);
            val result7  = (Result<T7>) results.get(6);
            val result8  = (Result<T8>) results.get(7);
            val result9  = (Result<T9>) results.get(8);
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5, promise6, promise6, promise7, promise8, promise9);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from ten {@code HasPromise} instances and a {@code Func10} merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D> the type of the result produced by the merger function.
     * 
     * @param promise1   the first promise.
     * @param promise2   the second promise.
     * @param promise3   the third promise.
     * @param promise4   the fourth promise.
     * @param promise5   the fifth promise.
     * @param promise6   the sixth promise.
     * @param promise7   the seventh promise.
     * @param promise8   the eighth promise.
     * @param promise9   the ninth promise.
     * @param promise10  the tenth promise.
     * @param merger     the {@code Func10} function that merges the results of the promises.
     * @return           a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> DeferAction<D> from(
                    HasPromise<T1> promise1,
                    HasPromise<T2> promise2,
                    HasPromise<T3> promise3,
                    HasPromise<T4> promise4,
                    HasPromise<T5> promise5,
                    HasPromise<T6> promise6,
                    HasPromise<T7> promise7,
                    HasPromise<T8> promise8,
                    HasPromise<T9> promise9,
                    HasPromise<T10> promise10,
                    Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, D> merger) {
        val merge = (Func1) f((FuncList<Result> results) -> {
            val result1  = (Result<T1>) results.get(0);
            val result2  = (Result<T2>) results.get(1);
            val result3  = (Result<T3>) results.get(2);
            val result4  = (Result<T4>) results.get(3);
            val result5  = (Result<T5>) results.get(4);
            val result6  = (Result<T6>) results.get(5);
            val result7  = (Result<T7>) results.get(6);
            val result8  = (Result<T8>) results.get(7);
            val result9  = (Result<T9>) results.get(8);
            val result10 = (Result<T10>)results.get(9);
            val mergedResult = Result.ofResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10, merger);
            return (Result<D>) mergedResult;
        });
        val promises = listOf(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9, promise10);
        val combiner = new CombineResult(promises, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    /**
     * Creates a {@code DeferAction} from an array of {@code HasPromise} instances and a merger function.
     * This method combines the results of the promises once they are fulfilled and applies the merger function to them.
     * 
     * @param <D>  the type of the result produced by the merger function.
     * @param <T>  the type of the results provided by the promises.
     * 
     * @param merger    a {@code Func1} function that takes a {@code FuncList<T>} of results and merges them into a type {@code D}.
     * @param promises  a varargs array of {@code HasPromise<T>} instances.
     * @return          a {@code DeferAction<D>} that will complete with the result of applying the merger to the results of the promises.
     */
    public static <D, T> DeferAction<D> from(Func1<FuncList<T>, D> merger, HasPromise<T> ... promises) {
        val merge = f((Result<T>[] results) -> {
            val resultList = listOf(results).map(Result::get);
            val mergedResult = merger.apply(resultList);
            return (Result<D>) mergedResult;
        });
        val promiseList = listOf(promises);
        val combiner = new CombineResult(promiseList, merge);
        val action = combiner.getDeferAction();
        return action;
    }
    
    //== Create ==
    
    public static <D> DeferAction<D> create(boolean interruptOnCancel, Func0<D> supplier, Runnable onStart, AsyncRunner runner) {
        return DeferActionCreator.current.value().create(supplier, onStart, interruptOnCancel, runner);
    }
    
    private final Runnable task;
    
    private final DeferAction<?> parent;
    
    DeferAction() {
        this(null, (OnStart) null);
    }
    
    DeferAction(DeferAction<?> parent, Promise<DATA> promise) {
        super(promise);
        this.parent = parent;
        this.task = null;
    }
    
    DeferAction(Runnable task, OnStart onStart) {
        super(onStart);
        this.parent = null;
        this.task = task;
    }
    
    public PendingAction<DATA> start() {
        if (parent != null) {
            parent.start();
        } else {
            val isStarted = promise.start();
            if (!isStarted && (task != null))
                carelessly(task);
        }
        return new PendingAction<>(promise);
    }
    
    public HasPromise<DATA> __data() throws Exception {
        return this;
    }
    
    // == Subscription ==
    public DeferAction<DATA> use(Consumer<Promise<DATA>> consumer) {
        carelessly(() -> {
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
    
    public DeferAction<DATA> onComplete(FuncUnit1<Result<DATA>> resultConsumer, FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
        val subscription = promise.onComplete(Wait.forever(), resultConsumer);
        carelessly(() -> subscriptionConsumer.accept(subscription));
        return this;
    }
    
    public DeferAction<DATA> onComplete(Wait wait, FuncUnit1<Result<DATA>> resultConsumer, FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer) {
        val subscription = promise.onComplete(wait, resultConsumer);
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
    
    // == Functional ==
    public final DeferAction<DATA> filter(Predicate<? super DATA> predicate) {
        val newPromise = promise.filter(predicate);
        return new DeferAction<DATA>(this, newPromise);
    }
    
    public final DeferAction<DATA> peek(FuncUnit1<? super DATA> peeker) {
        val newPromise = promise.peek(peeker);
        return new DeferAction<DATA>(this, (Promise<DATA>) newPromise);
    }
    
    public final <TARGET> DeferAction<TARGET> map(Func1<? super DATA, ? extends TARGET> mapper) {
        val newPromise = promise.map(mapper);
        val newAction = new DeferAction<TARGET>(this, (Promise<TARGET>) newPromise);
        return newAction;
    }
    
    public final <TARGET> DeferAction<TARGET> flatMap(Func1<? super DATA, ? extends HasPromise<? extends TARGET>> mapper) {
        return chain((Func1) mapper);
    }
    
    public final <TARGET> DeferAction<TARGET> chain(Func1<DATA, ? extends HasPromise<TARGET>> mapper) {
        val newPromise = promise.chain(mapper);
        return new DeferAction<TARGET>(this, (Promise<TARGET>) newPromise);
    }
    
    // == Status ==
    public DeferAction<DATA> ifStatusRun(ResultStatus status, Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifStatusRun(status, runnable));
    }
    
    public DeferAction<DATA> ifStatusAccept(ResultStatus status, Consumer<? super DATA> consumer) {
        return new DeferAction<DATA>(this, promise.ifStatusAccept(status, consumer));
    }
    
    public DeferAction<DATA> whenStatusUse(ResultStatus status, DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenStatusUse(status, fallbackValue));
    }
    
    public DeferAction<DATA> whenStatusGet(ResultStatus status, Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenStatusGet(status, fallbackSupplier));
    }
    
    public DeferAction<DATA> whenStatusApply(ResultStatus status, BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenStatusApply(status, recoverFunction));
    }
    
    // == Validation ==
    public DeferAction<DATA> validateNotNull() {
        return new DeferAction<DATA>(this, promise.validateNotNull());
    }
    
    public DeferAction<DATA> validateNotNull(String message) {
        return new DeferAction<DATA>(this, promise.validateNotNull(message));
    }
    
    public DeferAction<DATA> validateUnavailable() {
        return new DeferAction<DATA>(this, promise.validateUnavailable());
    }
    
    public DeferAction<DATA> validateNotReady() {
        return new DeferAction<DATA>(this, promise.validateNotReady());
    }
    
    public DeferAction<DATA> validateResultCancelled() {
        return new DeferAction<DATA>(this, promise.validateResultCancelled());
    }
    
    public DeferAction<DATA> validateResultNotExist() {
        return new DeferAction<DATA>(this, promise.validateResultNotExist());
    }
    
    public DeferAction<DATA> validateNoMoreResult() {
        return new DeferAction<DATA>(this, promise.validateNoMoreResult());
    }
    
    public DeferAction<DATA> validate(String stringFormat, Predicate<? super DATA> validChecker) {
        return new DeferAction<DATA>(this, promise.validate(stringFormat, validChecker));
    }
    
    public <T> DeferAction<DATA> validate(String stringFormat, Func1<? super DATA, T> mapper, Predicate<? super T> validChecker) {
        return new DeferAction<DATA>(this, promise.validate(stringFormat, mapper, validChecker));
    }
    
    public DeferAction<DATA> validate(Validator<DATA> validator) {
        return new DeferAction<DATA>(this, promise.validate(validator));
    }
    
    public DeferAction<Tuple2<DATA, FuncList<ValidationException>>> validate(Validator<? super DATA>... validators) {
        return new DeferAction<Tuple2<DATA, FuncList<ValidationException>>>(this, promise.validate(validators));
    }
    
    public DeferAction<Tuple2<DATA, FuncList<ValidationException>>> validate(List<Validator<? super DATA>> validators) {
        return new DeferAction<Tuple2<DATA, FuncList<ValidationException>>>(this, promise.validate(validators));
    }
    
    public DeferAction<DATA> ensureNotNull() {
        return new DeferAction<DATA>(this, promise.ensureNotNull());
    }
    
    // Alias of whenNotPresentUse
    public DeferAction<DATA> otherwise(DATA elseValue) {
        return new DeferAction<DATA>(this, promise.otherwise(elseValue));
    }
    
    // Alias of whenNotPresentGet
    public DeferAction<DATA> otherwiseGet(Supplier<? extends DATA> elseSupplier) {
        return new DeferAction<DATA>(this, promise.otherwiseGet(elseSupplier));
    }
    
    public DeferAction<DATA> printException() {
        return new DeferAction<DATA>(this, promise.printException());
    }
    
    public DeferAction<DATA> printException(PrintStream printStream) {
        return new DeferAction<DATA>(this, promise.printException(printStream));
    }
    
    public DeferAction<DATA> printException(PrintWriter printWriter) {
        return new DeferAction<DATA>(this, promise.printException(printWriter));
    }
    
    // == Peek ==
    public <T extends DATA> DeferAction<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return new DeferAction<DATA>(this, promise.peek(clzz, theConsumer));
    }
    
    public DeferAction<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return new DeferAction<DATA>(this, promise.peek(selector, theConsumer));
    }
    
    public <T> DeferAction<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return new DeferAction<DATA>(this, promise.peek(mapper, theConsumer));
    }
    
    public <T> DeferAction<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return new DeferAction<DATA>(this, promise.peek(mapper, selector, theConsumer));
    }
    
    // == If+When ==
    public DeferAction<DATA> useData(FuncUnit2<DATA, Exception> processor) {
        return new DeferAction<DATA>(this, promise.useData(processor));
    }
    
    public DeferAction<DATA> whenComplete(FuncUnit2<DATA, Exception> processor) {
        return new DeferAction<DATA>(this, promise.whenComplete(processor));
    }
    
    public DeferAction<DATA> whenComplete(FuncUnit1<Result<DATA>> processor) {
        return new DeferAction<DATA>(this, promise.whenComplete(processor));
    }
    
    // == Present ==
    public DeferAction<DATA> ifPresent(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifPresent(runnable));
    }
    
    public DeferAction<DATA> ifPresent(Consumer<? super DATA> consumer) {
        return new DeferAction<DATA>(this, promise.ifPresent(consumer));
    }
    
    // == Absent ==
    public DeferAction<DATA> ifAbsent(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifAbsent(runnable));
    }
    
    public DeferAction<DATA> ifAbsent(Consumer<? super DATA> consumer) {
        return new DeferAction<DATA>(this, promise.ifAbsent(consumer));
    }
    
    public DeferAction<DATA> ifAbsent(BiConsumer<? super DATA, ? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifAbsent(consumer));
    }
    
    public DeferAction<DATA> whenAbsentUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenAbsentUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenAbsentGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenAbsentGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenAbsentApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenAbsentApply(recoverFunction));
    }
    
    // == Null ==
    public DeferAction<DATA> ifNull(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifNull(runnable));
    }
    
    public DeferAction<DATA> whenNullUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenNullUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenNullGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenNullGet(fallbackSupplier));
    }
    
    // == Value ==
    public DeferAction<DATA> ifValue(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifValue(runnable));
    }
    
    public DeferAction<DATA> ifValue(Consumer<? super DATA> consumer) {
        return new DeferAction<DATA>(this, promise.ifValue(consumer));
    }
    
    // == NotValue ==
    public DeferAction<DATA> ifNotValue(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifNotValue(runnable));
    }
    
    public DeferAction<DATA> ifNotValue(Consumer<? super DATA> consumer) {
        return new DeferAction<DATA>(this, promise.ifNotValue(consumer));
    }
    
    public DeferAction<DATA> ifNotValue(BiConsumer<? super DATA, ? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifNotValue(consumer));
    }
    
    public DeferAction<DATA> whenNotValueUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenNotValueUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenNotValueGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenNotValueGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenNotValueApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenNotValueApply(recoverFunction));
    }
    
    // == Valid ==
    public DeferAction<DATA> ifValid(Consumer<? super DATA> consumer) {
        return new DeferAction<DATA>(this, promise.ifValid(consumer));
    }
    
    // == Invalid ==
    public DeferAction<DATA> ifInvalid(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifInvalid(runnable));
    }
    
    public DeferAction<DATA> ifInvalid(Consumer<? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifInvalid(consumer));
    }
    
    public DeferAction<DATA> whenInvalidUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenInvalidUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenInvalidGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenInvalidGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenInvalidApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenInvalidApply(recoverFunction));
    }
    
    // == NotExist ==
    public DeferAction<DATA> ifNotExist(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifNotExist(runnable));
    }
    
    public DeferAction<DATA> ifNotExist(Consumer<? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifNotExist(consumer));
    }
    
    public DeferAction<DATA> whenNotExistUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenNotExistUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenNotExistGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenNotExistGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenNotExistApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenNotExistApply(recoverFunction));
    }
    
    // == Exception ==
    public DeferAction<DATA> ifException(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifException(runnable));
    }
    
    public DeferAction<DATA> ifException(Consumer<? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifException(consumer));
    }
    
    public DeferAction<DATA> ifExceptionThenPrint() {
        return new DeferAction<DATA>(this, promise.ifExceptionThenPrint());
    }
    
    public DeferAction<DATA> ifExceptionThenPrint(PrintStream printStream) {
        return new DeferAction<DATA>(this, promise.ifExceptionThenPrint(printStream));
    }
    
    public DeferAction<DATA> ifExceptionThenPrint(PrintWriter printWriter) {
        return new DeferAction<DATA>(this, promise.ifExceptionThenPrint(printWriter));
    }
    
    public DeferAction<DATA> whenExceptionUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenExceptionUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenExceptionGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenExceptionGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenExceptionApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenExceptionApply(recoverFunction));
    }
    
    public DeferAction<DATA> recover(DATA fallbackValue) {
        return recover(Exception.class, fallbackValue);
    }
    
    public DeferAction<DATA> recover(Supplier<? extends DATA> fallbackSupplier) {
        return recover(Exception.class, fallbackSupplier);
    }
    
    public DeferAction<DATA> recover(Func1<? super Exception, ? extends DATA> recoverFunction) {
        return recover(Exception.class, recoverFunction);
    }
    
    public DeferAction<DATA> recover(Class<? extends Throwable> problemClass, DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.recover(problemClass, fallbackValue));
    }
    
    public DeferAction<DATA> recover(Class<? extends Throwable> problemClass, Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.recover(problemClass, fallbackSupplier));
    }
    
    public DeferAction<DATA> recover(Class<? extends Throwable> problemClass, Func1<? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.recover(problemClass, recoverFunction));
    }
    
    // == Cancelled ==
    public DeferAction<DATA> ifCancelled(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifCancelled(runnable));
    }
    
    public DeferAction<DATA> whenCancelledUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenCancelledUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenCancelledGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenCancelledGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenCancelledApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenCancelledApply(recoverFunction));
    }
    
    // == Ready ==
    public DeferAction<DATA> ifReady(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifReady(runnable));
    }
    
    public DeferAction<DATA> ifReady(Consumer<? super DATA> consumer) {
        return new DeferAction<DATA>(this, promise.ifReady(consumer));
    }
    
    public DeferAction<DATA> ifReady(BiConsumer<? super DATA, ? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifReady(consumer));
    }
    
    public DeferAction<DATA> whenReadyUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenReadyUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenReadyGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenNotReadyApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenNotReadyApply(recoverFunction));
    }
    
    // == Not Ready ==
    public DeferAction<DATA> ifNotReady(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifNotReady(runnable));
    }
    
    public DeferAction<DATA> ifNotReady(Consumer<? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifNotReady(consumer));
    }
    
    public DeferAction<DATA> whenNotReadyUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenNotReadyUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenNotReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenNotReadyGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenNotReadyApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenNotReadyApply(recoverFunction));
    }
    
    // == No More Result ==
    public DeferAction<DATA> ifNoMore(Runnable runnable) {
        return new DeferAction<DATA>(this, promise.ifNoMore(runnable));
    }
    
    public DeferAction<DATA> ifNoMore(Consumer<? super Exception> consumer) {
        return new DeferAction<DATA>(this, promise.ifNoMore(consumer));
    }
    
    public DeferAction<DATA> whenNoMoreUse(DATA fallbackValue) {
        return new DeferAction<DATA>(this, promise.whenNoMoreUse(fallbackValue));
    }
    
    public DeferAction<DATA> whenNoMoreGet(Supplier<? extends DATA> fallbackSupplier) {
        return new DeferAction<DATA>(this, promise.whenNoMoreGet(fallbackSupplier));
    }
    
    public DeferAction<DATA> whenNoMoreApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return new DeferAction<DATA>(this, promise.whenNoMoreApply(recoverFunction));
    }
}
