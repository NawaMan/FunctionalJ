// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.task;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
import functionalj.function.FuncUnit1;
import functionalj.function.Named;
import functionalj.list.FuncList;
import functionalj.promise.DeferAction;
import functionalj.promise.DeferActionBuilder;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.task.Tasks.TaskCached;
import functionalj.task.Tasks.TaskCachedFor;
import functionalj.task.Tasks.TaskChain;
import functionalj.task.Tasks.TaskFilter;
import functionalj.task.Tasks.TaskMap;
import functionalj.task.Tasks.TaskMerge2;
import functionalj.task.Tasks.TaskMerge3;
import functionalj.task.Tasks.TaskMerge4;
import functionalj.task.Tasks.TaskMerge5;
import functionalj.task.Tasks.TaskMerge6;
import functionalj.task.Tasks.TaskMerge7;
import functionalj.task.Tasks.TaskMerge8;
import functionalj.task.Tasks.TaskMerge9;
import functionalj.task.Tasks.TaskMerge10;
import functionalj.task.Tasks.TaskPeek;
import functionalj.task.Tasks.TaskPromise;
import functionalj.task.Tasks.TaskRace;
import functionalj.task.Tasks.TaskResult;
import functionalj.task.Tasks.TaskSupplier;
import functionalj.task.Tasks.TaskValue;

@FunctionalInterface
public interface Task<DATA> {
    
    public DeferAction<DATA> createAction();
    
    public static <D> Task<D> ofValue(D value) {
        return new TaskValue<>(value);
    }
    
    public static <D> DeferActionBuilder<D> from(Func0<D> supplier) {
        return new TaskSupplier<>(supplier);
    }
    
    public static <D> Task<D> from(Result<D> result) {
        return new TaskResult<>(result);
    }
    
    public static <D> Task<D> from(Promise<D> promise) {
        return new TaskPromise<>(promise);
    }
    
    //== Merge ==
    
    /**
     * Creates a new {@link TaskMerge2} instance that merges the results of two tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge2} instance that merges the results of the specified tasks
     */
    public static <I1, I2, D> Task<D> from(Task<I1> io1, Task<I2> io2, Func2<I1, I2, D> merger) {
        return new TaskMerge2<>(io1, io2, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge3} instance that merges the results of three tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <I3>  the type of the result of the third task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge3} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Func3<I1, I2, I3, D> merger) {
        return new TaskMerge3<>(io1, io2, io3, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge4} instance that merges the results of four tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <I3>  the type of the result of the third task
     * @param <I4>  the type of the result of the fourth task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param io4     the fourth task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge4} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, I4, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Task<I4> io4, Func4<I1, I2, I3, I4, D> merger) {
        return new TaskMerge4<>(io1, io2, io3, io4, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge5} instance that merges the results of five tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <I3>  the type of the result of the third task
     * @param <I4>  the type of the result of the fourth task
     * @param <I5>  the type of the result of the fifth task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param io4     the fourth task
     * @param io5     the fifth task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge5} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, I4, I5, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Task<I4> io4, Task<I5> io5, Func5<I1, I2, I3, I4, I5, D> merger) {
        return new TaskMerge5<>(io1, io2, io3, io4, io5, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge6} instance that merges the results of six tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <I3>  the type of the result of the third task
     * @param <I4>  the type of the result of the fourth task
     * @param <I5>  the type of the result of the fifth task
     * @param <I6>  the type of the result of the sixth task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param io4     the fourth task
     * @param io5     the fifth task
     * @param io6     the sixth task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge6} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, I4, I5, I6, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Task<I4> io4, Task<I5> io5, Task<I6> io6, Func6<I1, I2, I3, I4, I5, I6, D> merger) {
        return new TaskMerge6<>(io1, io2, io3, io4, io5, io6, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge7} instance that merges the results of seven tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <I3>  the type of the result of the third task
     * @param <I4>  the type of the result of the fourth task
     * @param <I5>  the type of the result of the fifth task
     * @param <I6>  the type of the result of the sixth task
     * @param <I7>  the type of the result of the seventh task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param io4     the fourth task
     * @param io5     the fifth task
     * @param io6     the sixth task
     * @param io7     the seventh task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge7} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, I4, I5, I6, I7, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Task<I4> io4, Task<I5> io5, Task<I6> io6, Task<I7> io7, Func7<I1, I2, I3, I4, I5, I6, I7, D> merger) {
        return new TaskMerge7<>(io1, io2, io3, io4, io5, io6, io7, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge8} instance that merges the results of eight tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <I3>  the type of the result of the third task
     * @param <I4>  the type of the result of the fourth task
     * @param <I5>  the type of the result of the fifth task
     * @param <I6>  the type of the result of the sixth task
     * @param <I7>  the type of the result of the seventh task
     * @param <I8>  the type of the result of the eighth task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param io4     the fourth task
     * @param io5     the fifth task
     * @param io6     the sixth task
     * @param io7     the seventh task
     * @param io8     the eighth task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge8} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Task<I4> io4, Task<I5> io5, Task<I6> io6, Task<I7> io7, Task<I8> io8, Func8<I1, I2, I3, I4, I5, I6, I7, I8, D> merger) {
        return new TaskMerge8<>(io1, io2, io3, io4, io5, io6, io7, io8, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge9} instance that merges the results of nine tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>  the type of the result of the first task
     * @param <I2>  the type of the result of the second task
     * @param <I3>  the type of the result of the third task
     * @param <I4>  the type of the result of the fourth task
     * @param <I5>  the type of the result of the fifth task
     * @param <I6>  the type of the result of the sixth task
     * @param <I7>  the type of the result of the seventh task
     * @param <I8>  the type of the result of the eighth task
     * @param <I9>  the type of the result of the ninth task
     * @param <D>   the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param io4     the fourth task
     * @param io5     the fifth task
     * @param io6     the sixth task
     * @param io7     the seventh task
     * @param io8     the eighth task
     * @param io9     the ninth task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge9} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Task<I4> io4, Task<I5> io5, Task<I6> io6, Task<I7> io7, Task<I8> io8, Task<I9> io9, Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, D> merger) {
        return new TaskMerge9<>(io1, io2, io3, io4, io5, io6, io7, io8, io9, merger);
    }
    
    /**
     * Creates a new {@link TaskMerge10} instance that merges the results of ten tasks using a specified merger function.
     * This static method facilitates the combination of multiple asynchronous tasks into a single Task object.
     * 
     * @param <I1>   the type of the result of the first task
     * @param <I2>   the type of the result of the second task
     * @param <I3>   the type of the result of the third task
     * @param <I4>   the type of the result of the fourth task
     * @param <I5>   the type of the result of the fifth task
     * @param <I6>   the type of the result of the sixth task
     * @param <I7>   the type of the result of the seventh task
     * @param <I8>   the type of the result of the eighth task
     * @param <I9>   the type of the result of the ninth task
     * @param <I10>  the type of the result of the tenth task
     * @param <D>    the type of the data produced by the merger of the task results
     * 
     * @param io1     the first task
     * @param io2     the second task
     * @param io3     the third task
     * @param io4     the fourth task
     * @param io5     the fifth task
     * @param io6     the sixth task
     * @param io7     the seventh task
     * @param io8     the eighth task
     * @param io9     the ninth task
     * @param i10     the tenth task
     * @param merger  the function to merge the results of the tasks
     * @return a new {@link TaskMerge10} instance that merges the results of the specified tasks
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, D> Task<D> from(Task<I1> io1, Task<I2> io2, Task<I3> io3, Task<I4> io4, Task<I5> io5, Task<I6> io6, Task<I7> io7, Task<I8> io8, Task<I9> io9, Task<I10> i10, Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, D> merger) {
        return new TaskMerge10<>(io1, io2, io3, io4, io5, io6, io7, io8, io9, i10, merger);
    }
    
    //==
    
    @SafeVarargs
    public static <D> Task<D> firstOf(Task<D>... ios) {
        return new TaskRace<D>(FuncList.from(ios));
    }
    
    public static <D> Task<D> firstFrom(List<? extends Task<D>> ios) {
        return new TaskRace<D>(FuncList.from(ios));
    }
    
    public static <D> Task<D> doUntil(Task<D> body, Predicate<Result<D>> breakCondition) {
        return new Tasks.TaskDoUntil<D>(body, breakCondition);
    }
    
    // TODO - Do while that has Task<Boolean> as a way to check if the loop should still continue.
    public default Task<DATA> peek(FuncUnit1<? super DATA> keeper) {
        return new TaskPeek<>(this, keeper);
    }
    
    public default <TARGET> Task<TARGET> map(Func1<? super DATA, TARGET> mapper) {
        return new TaskMap<>(this, mapper);
    }
    
    public default <TARGET> Task<TARGET> flatMap(Func1<? super DATA, Task<? extends TARGET>> mapper) {
        return new TaskChain<>(this, mapper);
    }
    
    public default <TARGET> Task<TARGET> chain(Func1<? super DATA, Task<? extends TARGET>> mapper) {
        return new TaskChain<>(this, mapper);
    }
    
    public default Task<DATA> filter(Predicate<? super DATA> predicate) {
        return new TaskFilter<>(this, predicate);
    }
    
    public default <S> Task<DATA> cached() {
        return new TaskCached<>(this);
    }
    
    public default <S> Task<DATA> cached(Supplier<S> theContext) {
        return cached(theContext, Named.BiPredicate("when-change", (S o, S n) -> !Objects.equals(o, n)));
    }
    
    public default <S> Task<DATA> cached(Supplier<S> contextSupplier, BiPredicate<S, S> staleChecker) {
        return new TaskCachedFor<>(this, contextSupplier, staleChecker);
    }
}
