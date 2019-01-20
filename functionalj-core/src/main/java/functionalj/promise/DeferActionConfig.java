package functionalj.promise;

import java.util.function.Consumer;

import functionalj.environments.AsyncRunner;
import functionalj.function.Func0;
import functionalj.function.FuncUnit0;
import functionalj.ref.Ref;
import functionalj.supportive.Default;

public class DeferActionConfig {
    
    @Default
    public static DeferActionConfig newInstance() {
        return new DeferActionConfig();
    }
    
    public static final Ref<DeferActionConfig> current
            = Ref.of(DeferActionConfig.class)
            .orTypeDefaultOrGet(DeferActionConfig::new);
    
    private static final FuncUnit0 DO_NOTHING = ()->{};
    
    private boolean     interruptOnCancel = true;
    private FuncUnit0   onStart           = DO_NOTHING;
    private AsyncRunner runner            = null;
    
    public boolean interruptOnCancel() {
        return interruptOnCancel;
    }
    public DeferActionConfig interruptOnCancel(boolean interruptOnCancel) {
        this.interruptOnCancel = interruptOnCancel;
        return this;
    }
    public FuncUnit0 onStart() {
        return onStart;
    }
    public DeferActionConfig onStart(FuncUnit0 onStart) {
        this.onStart = onStart;
        return this;
    }
    public Consumer<Runnable> runner() {
        return runner;
    }
    public DeferActionConfig runner(AsyncRunner runner) {
        this.runner = runner;
        return this;
    }
    
    public <D> DeferActionBuilder<D> createBuilder(Func0<D> supplier) {
        return new DeferActionBuilder<D>(supplier)
                .interruptOnCancel(interruptOnCancel)
                .onStart(onStart)
                .runner(runner);
    }
    public DeferActionBuilder<Object> createBuilder(FuncUnit0 runnable) {
        return createBuilder(runnable.thenReturnNull());
    }
    
    public <D> DeferAction<D> createAction(Func0<D> supplier) {
        return createBuilder(supplier).build();
    }
    public DeferAction<Object> createAction(FuncUnit0 runnable) {
        return createBuilder(runnable.thenReturnNull()).build();
    }
    
}
