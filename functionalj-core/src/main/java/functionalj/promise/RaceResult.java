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

import static java.util.Objects.requireNonNull;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.result.Result;
import lombok.val;
import lombok.experimental.Delegate;

@SuppressWarnings({ "unchecked" })
public class RaceResult<DATA> implements HasPromise<DATA> {
    
    @SafeVarargs
    public static <D> RaceResult<D> of(StartableAction<D>... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> from(List<? extends StartableAction<D>> actions) {
        return Race(FuncList.from(actions));
    }
    
    @SafeVarargs
    public static <D> RaceResult<D> Race(StartableAction<D>... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> Race(List<? extends StartableAction<D>> actions) {
        val theActions  = FuncList.from(actions).filterNonNull().toImmutableList();
        val actionRef   = new AtomicReference<DeferAction<D>>();
        val deferAction = DeferAction.<D>createNew(OnStart.run(() -> {
            val startedActions
                    = FuncList.from(actions)
                    .filterNonNull()
                    .map(StartableAction::start)
                    .toImmutableList();
            val counter = new AtomicInteger(actions.size());
            val hasNull = new AtomicBoolean(false);
            startedActions.forEach(action -> {
                action.onCompleted(result -> {
                    result.ifPresent(value -> {
                        actionRef.get().complete(value);
                        startedActions.forEach(PendingAction::abort);
                    });
                    result.ifNull(() -> hasNull.set(true));
                    int count = counter.decrementAndGet();
                    if (count == 0) {
                        startedActions.forEach(PendingAction::abort);
                        if (hasNull.get())
                            actionRef.get().completeWith(Result.ofNull());
                        else
                            actionRef.get().abort("Finish without non-null result.");
                    }
                });
            });
        }));
        actionRef.set(deferAction);
        
        val promise = deferAction.getPromise();
        val promises = theActions.map(StartableAction::getPromise).toImmutableList();
        val race = new RaceResult<D>(promise, promises);
        return race;
    }
    
    private final Promise<DATA> promise;
    
    private final ImmutableFuncList<Promise<DATA>> eachPromises;
    
    RaceResult(Promise<DATA> promise, ImmutableFuncList<Promise<DATA>> eachPromises) {
        this.promise = requireNonNull(promise);
        this.eachPromises = requireNonNull(eachPromises);
    }
    
    @Delegate
    public Promise<DATA> getResultPromise() {
        return promise;
    }
    
    public FuncList<Promise<DATA>> getEachPromises() {
        return eachPromises;
    }
    
    @Override
    public String toString() {
        return String.format("RaceResult { promise=%s, eachPromises=%s }", 
                promise, 
                eachPromises);
    }
}
