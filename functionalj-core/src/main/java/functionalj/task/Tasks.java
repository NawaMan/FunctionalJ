// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static nullablej.nullable.Nullable.nullable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit1;
import functionalj.list.FuncList;
import functionalj.promise.DeferAction;
import functionalj.promise.DeferActionBuilder;
import functionalj.promise.Promise;
import functionalj.promise.RaceResult;
import functionalj.result.Result;
import lombok.val;


public class Tasks {
    
    public static class TaskValue<DATA> implements Task<DATA> {
        private final DATA value;
        public TaskValue(DATA value) {
            this.value = value;
        }
        @Override
        public DeferAction<DATA> createAction() {
            return DeferAction.ofValue(value);
        }
        public String toString() {
            return "Task(" + value + ")";
        }
    }
    
    public static class TaskSupplier<DATA> extends DeferActionBuilder<DATA> implements Task<DATA> {
        public TaskSupplier(Func0<DATA> supplier) {
            super(supplier);
        }
        @Override
        public DeferAction<DATA> createAction() {
            return build();
        }
        public String toString() {
            return "Task(()->" + supplier() + ")";
        }
    }
    
    public static class TaskResult<DATA> implements Task<DATA> {
        private final Result<DATA> result;
        public TaskResult(Result<DATA> result) {
            this.result = result;
        }
        @Override
        public DeferAction<DATA> createAction() {
            val action = DeferAction.of((Class<DATA>)null);
            action.start();
            if (result.isValue())
                 action.complete(result.getValue());
            else action.fail    (result.getException());
            return action;
        }
        public String toString() {
            return "Task(" + result + ")";
        }
    }
    
    public static class TaskPromise<DATA> implements Task<DATA> {
        private final Promise<DATA> promise;
        public TaskPromise(Promise<DATA> promise) {
            this.promise = promise;
        }
        @Override
        public DeferAction<DATA> createAction() {
            val action = DeferAction.of((Class<DATA>)null, ()->{
                if (promise != null)
                    promise.start();
            });
            if (promise != null) {
                promise
                .eavesdrop(result -> {
                    val pendingAction = action.start();
                    if (result.isValue())
                         pendingAction.complete(result.getValue());
                    else pendingAction.fail    (result.getException());
                });
            }
            return action;
        }
        public String toString() {
            return "Task(" + promise + ")";
        }
    }
    
    public static class IOInstance<DATA> implements Task<DATA> {
        private final String toString;
        private final Supplier<DeferAction<DATA>> createAction;
        public IOInstance(String toString, Supplier<DeferAction<DATA>> createAction) {
            this.toString     = (toString != null) ? toString : "Task@" + hashCode();
            this.createAction = Objects.requireNonNull(createAction);
        }
        @Override
        public DeferAction<DATA> createAction() {
            return createAction.get();
        }
        public String toString() {
            return toString;
        }
    }
    
    public static class TaskPeek<SOURCE, DATA> implements Task<DATA> {
        private final Task<DATA>                source;
        private final FuncUnit1<? super DATA> peeker;
        public TaskPeek(Task<DATA> source, FuncUnit1<? super DATA> peeker) {
            this.source = source;
            this.peeker = peeker;
        }
        @Override
        public DeferAction<DATA> createAction() {
            return source.createAction().peek(peeker);
        }
        public String toString() {
            return source + ".peek(" + peeker + ")";
        }
    }
    
    public static class TaskMap<SOURCE, DATA> implements Task<DATA> {
        private final Task<SOURCE>                            source;
        private final Func1<? super SOURCE, ? extends DATA> mapper;
        public TaskMap(Task<SOURCE> source, Func1<? super SOURCE, ? extends DATA> mapper) {
            this.source = source;
            this.mapper = mapper;
        }
        @Override
        public DeferAction<DATA> createAction() {
            DeferAction<DATA> map = source
                    .createAction()
                    .map(mapper);
            return map;
        }
        public String toString() {
            return source + ".map(" + mapper + ")";
        }
    }
    public static class TaskChain<SOURCE, DATA> implements Task<DATA> {
        private final Task<SOURCE>                                source;
        private final Func1<? super SOURCE, Task<? extends DATA>> mapper;
        public TaskChain(Task<SOURCE> source, Func1<? super SOURCE, Task<? extends DATA>> mapper) {
            this.source = source;
            this.mapper = mapper;
        }
        @SuppressWarnings("unchecked")
        @Override
        public DeferAction<DATA> createAction() {
            val ioDefer   = source.createAction().map(mapper);
            val ioPromise = ioDefer.getPromise();
            val action    = (DeferAction<DATA>)DeferAction.createNew(()->ioPromise.start());
            ioPromise
            .subscribe(io -> {
                val pendindAction = action.start();
                io
                .createAction()
                .start()
                .eavesdrop(result -> {
                    if (result.isValue())
                         pendindAction.complete(result.getValue());
                    else pendindAction.fail    (result.getException());
                });
            });
            return action;
        }
        public String toString() {
            return source + ".chain(" + mapper + ")";
        }
    }
    public static class TaskFilter<DATA> implements Task<DATA> {
        private final Task<DATA>                source;
        private final Predicate<? super DATA> predicate;
        public TaskFilter(Task<DATA> source, Predicate<? super DATA> predicate) {
            this.source    = source;
            this.predicate = predicate;
        }
        @Override
        public DeferAction<DATA> createAction() {
            return source.createAction().filter(predicate);
        }
        public String toString() {
            return source + ".filter(" + predicate + ")";
        }
    }
    public static class TaskCached<DATA> implements Task<DATA> {
        private final Task<DATA>          source;
        private final DeferAction<DATA> action;
        public TaskCached(Task<DATA> source) {
            this.source = source;
            this.action = source.createAction();
        }
        @Override
        public DeferAction<DATA> createAction() {
            return action;
        }
        public String toString() {
            return source + ".cached()";
        }
    }
    
    public static class TaskCachedFor<CONTEXT, DATA> implements Task<DATA> {
        private final Task<DATA>                           source;
        private final Supplier<CONTEXT>                  contextSupplier;
        private final BiPredicate<CONTEXT, CONTEXT>      staleChecker;
        private final AtomicReference<CONTEXT>           contxtRef;
        private final AtomicReference<DeferAction<DATA>> actionRef;
        public TaskCachedFor(Task<DATA> source, Supplier<CONTEXT> contextSupplier, BiPredicate<CONTEXT, CONTEXT> staleChecker) {
            this.source          = source;
            this.contextSupplier = contextSupplier;
            this.staleChecker    = staleChecker;
            this.actionRef       = new AtomicReference<>(null);
            this.contxtRef       = new AtomicReference<>(contextSupplier.get());
        }
        @Override
        public DeferAction<DATA> createAction() {
            val act = actionRef.get();
            val ctx = contxtRef.get();
            val newRef = contextSupplier.get();
            if (staleChecker.test(ctx, newRef) || (act == null)) {
                actionRef.compareAndSet(act, source.createAction());
                contxtRef.set(newRef);
                return actionRef.get();
            }
            
            return act;
        }
        public String toString() {
            return source + ".cachedFor(" + contextSupplier + "," + staleChecker + ")";
        }
    }
    
    // merge, race, loop, branch
    
    public static class TaskMerge2<I1, I2, DATA> implements Task<DATA> {
        private final Task<I1> input1;
        private final Task<I2> input2;
        private final Func2 <I1, I2, DATA> merger;
        public TaskMerge2(Task<I1> input1, Task<I2> input2, Func2<I1, I2, DATA> merger) {
            this.input1 = input1;
            this.input2 = input2;
            this.merger = merger;
        }
        @Override
        public DeferAction<DATA> createAction() {
            val action1 = input1.createAction();
            val action2 = input2.createAction();
            val action  = DeferAction.from(action1, action2, merger);
            return action;
        }
        @Override
        public String toString() {
            return merger + "(" + input1 + ", " + input2 + ")";
        }
    }
    
    public static class TaskMerge3<I1, I2, I3, DATA> implements Task<DATA> {
        private final Task<I1> input1;
        private final Task<I2> input2;
        private final Task<I3> input3;
        private final Func3<I1, I2, I3, DATA> merger;
        public TaskMerge3(Task<I1> input1, Task<I2> input2, Task<I3> input3, Func3<I1, I2, I3, DATA> merger) {
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.merger = merger;
        }
        @Override
        public DeferAction<DATA> createAction() {
            val action1 = input1.createAction();
            val action2 = input2.createAction();
            val action3 = input3.createAction();
            val action  = DeferAction.from(action1, action2, action3, merger);
            return action;
        }
        @Override
        public String toString() {
            return merger + "(" + input1 + ", " + input2 + ", " + input3 + ")";
        }
    }
    
    public static class TaskMerge4<I1, I2, I3, I4, DATA> implements Task<DATA> {
        private final Task<I1> input1;
        private final Task<I2> input2;
        private final Task<I3> input3;
        private final Task<I4> input4;
        private final Func4<I1, I2, I3, I4, DATA> merger;
        public TaskMerge4(Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Func4<I1, I2, I3, I4, DATA> merger) {
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.input4 = input4;
            this.merger = merger;
        }
        @Override
        public DeferAction<DATA> createAction() {
            val action1 = input1.createAction();
            val action2 = input2.createAction();
            val action3 = input3.createAction();
            val action4 = input4.createAction();
            val action  = DeferAction.from(action1, action2, action3, action4, merger);
            return action;
        }
        @Override
        public String toString() {
            return merger + "(" + input1 + ", " + input2 + ", " + input3 + ", " + input4 + ")";
        }
    }
    
    public static class TaskMerge5<I1, I2, I3, I4, I5, DATA> implements Task<DATA> {
        private final Task<I1> input1;
        private final Task<I2> input2;
        private final Task<I3> input3;
        private final Task<I4> input4;
        private final Task<I5> input5;
        private final Func5<I1, I2, I3, I4, I5, DATA> merger;
        public TaskMerge5(Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Func5<I1, I2, I3, I4, I5, DATA> merger) {
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.input4 = input4;
            this.input5 = input5;
            this.merger = merger;
        }
        @Override
        public DeferAction<DATA> createAction() {
            val action1 = input1.createAction();
            val action2 = input2.createAction();
            val action3 = input3.createAction();
            val action4 = input4.createAction();
            val action5 = input5.createAction();
            val action  = DeferAction.from(action1, action2, action3, action4, action5, merger);
            return action;
        }
        @Override
        public String toString() {
            return merger + "(" + input1 + ", " + input2 + ", " + input3 + ", " + input4 + ", " + input5 + ")";
        }
    }
    
    public static class TaskMerge6<I1, I2, I3, I4, I5, I6, DATA> implements Task<DATA> {
        private final Task<I1> input1;
        private final Task<I2> input2;
        private final Task<I3> input3;
        private final Task<I4> input4;
        private final Task<I5> input5;
        private final Task<I6> input6;
        private final Func6<I1, I2, I3, I4, I5, I6, DATA> merger;
        public TaskMerge6(Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Func6<I1, I2, I3, I4, I5, I6, DATA> merger) {
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.input4 = input4;
            this.input5 = input5;
            this.input6 = input6;
            this.merger = merger;
        }
        @Override
        public DeferAction<DATA> createAction() {
            val action1 = input1.createAction();
            val action2 = input2.createAction();
            val action3 = input3.createAction();
            val action4 = input4.createAction();
            val action5 = input5.createAction();
            val action6 = input6.createAction();
            val action  = DeferAction.from(action1, action2, action3, action4, action5, action6, merger);
            return action;
        }
        @Override
        public String toString() {
            return merger + "(" + input1 + ", " + input2 + ", " + input3 + ", " + input4 + ", " + input5 + ", " + input6 + ")";
        }
    }
    
    public static class TaskRace<D> implements Task<D> {
        private final FuncList<Task<D>> list;
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public TaskRace(List<? extends Task<D>> list) {
            this.list = (FuncList)nullable(list).map(FuncList::from).orElse(FuncList.empty());
        }
        @Override
        public DeferAction<D> createAction() {
            val actions    = list.map(io -> io.createAction());
            val raceResult = RaceResult.from(actions);
            val promise    = raceResult.getResultPromise();
            val action     = DeferAction.of((Class<D>)null, ()->{
                if (promise != null)
                    promise.start();
            });
            if (promise != null) {
                promise
                .eavesdrop(result -> {
                    val pendingAction = action.start();
                    if (result.isValue())
                         pendingAction.complete(result.getValue());
                    else pendingAction.fail    (result.getException());
                });
            }
            return action;
        }
        @Override
        public String toString() {
            return "Race(" + list.join(",") + ")";
        }
        
    }
    
    public static class TaskDoUntil<D> implements Task<D> {
        private final Task<D>                body;
        private final Predicate<Result<D>> breakCheck;
        public TaskDoUntil(Task<D> body, Predicate<Result<D>> breakCheck) {
            this.body       = body;
            this.breakCheck = breakCheck;
        }
        @Override
        public DeferAction<D> createAction() {
            val actionRef = new AtomicReference<DeferAction<D>>();
            val action = DeferAction.of((Class<D>)null, ()->{
                doBody(actionRef);
            });
            actionRef.set(action);
            return action;
        }
        private void doBody(AtomicReference<DeferAction<D>> actionRef) {
            val bodyAction = body.createAction();
            // Ummm not sure if subscribe is good here
            bodyAction.onComplete(result -> {
                val isToBreak = breakCheck.test(result);
                if (isToBreak) {
                    val action = actionRef.get().start();
                    if (result.isValue())
                         action.complete(result.value());
                    else action.fail    (result.exception());
                } else {
                    doBody(actionRef);
                }
            });
            bodyAction.start();
        }
        @Override
        public String toString() {
            return "DoUntil(do: " + body + ", util: " + breakCheck + ")";
        }
    }
    
}
