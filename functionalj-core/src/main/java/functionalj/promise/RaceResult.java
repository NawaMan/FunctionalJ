package functionalj.promise;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.result.Result;
import lombok.val;
import lombok.experimental.Delegate;

public class RaceResult<DATA> implements HasPromise<DATA> {
    
    @SafeVarargs
    public static <D> RaceResult<D> of(StartableAction<D> ... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> from(List<? extends StartableAction<D>> actions) {
        return Race(FuncList.from(actions));
    }
    
    @SafeVarargs
    public static <D> RaceResult<D> Race(StartableAction<D> ... actions) {
        return Race(FuncList.of(actions));
    }
    
    public static <D> RaceResult<D> Race(List<? extends StartableAction<D>> actions) {
        DeferAction<D> deferAction   = DeferAction.createNew();
        val            pendingAction = deferAction.start();
        
        val startedActions = FuncList.from(actions)
                .filterNonNull()
                .map(StartableAction::start)
                .toImmutableList();
        
        val counter = new AtomicInteger(actions.size());
        val hasNull = new AtomicBoolean(false);
        startedActions
        .forEach(action -> {
            action.subscribe(result -> {
                result.ifPresent(value ->{
                    pendingAction.complete(value);
                    startedActions.forEach(PendingAction::abort);
                });
                result.ifNull(() -> hasNull.set(true));
                
                int count = counter.decrementAndGet();
                if (count == 0) {
                    startedActions.forEach(PendingAction::abort);
                    
                    if (hasNull.get())
                         pendingAction.completeWith(Result.ofNull());
                    else pendingAction.abort("Finish without non-null result.");
                }
            });
        });
        
        val promise  = pendingAction.getPromise();
        val promises = startedActions.map(PendingAction::getPromise).toImmutableList();
        val race     = new RaceResult<D>(promise, promises);
        return race;
    }
    
    private final Promise<DATA>                promise;
    private final ImmutableList<Promise<DATA>> eachPromises;
    
    RaceResult(Promise<DATA> promise, ImmutableList<Promise<DATA>> eachPromises) {
        this.promise      = requireNonNull(promise);
        this.eachPromises = requireNonNull(eachPromises);
    }
    
    @Delegate
    public Promise<DATA> getResultPromise() {
        return promise;
    }
    public FuncList<Promise<DATA>> getEachPromises() {
        return eachPromises;
    }
    
}
