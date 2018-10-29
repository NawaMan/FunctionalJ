package functionalj.promise;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import functionalj.environments.Env;
import functionalj.functions.Func0;
import functionalj.functions.FuncUnit0;
import lombok.experimental.Delegate;

public class DeferActionBuilder<DATA> extends StartableAction<DATA> {
    
    private static final Runnable DO_NOTHING = ()->{};
    
    private final Func0<DATA>  supplier;
    private boolean            interruptOnCancel = true;
    private Runnable           onStart           = DO_NOTHING;
    private Consumer<Runnable> runner            = Env.asyncRunner();
    
    DeferActionBuilder(FuncUnit0 supplier) {
        this(supplier.thenReturnNull());
    }
    
    DeferActionBuilder(Func0<DATA> supplier) {
        this.supplier = requireNonNull(supplier);
    }
    
    public DeferActionBuilder<DATA> interruptOnCancel(boolean interruptOnCancel) {
        this.interruptOnCancel = interruptOnCancel;
        return this;
    }
    
    public DeferActionBuilder<DATA> onStart(FuncUnit0 onStart) {
        this.onStart = (onStart != null) ? onStart : DO_NOTHING;
        return this;
    }
    
    public DeferActionBuilder<DATA> runner(Consumer<Runnable> runner) {
        this.runner = (runner != null) ? runner : Env.asyncRunner();
        return this;
    }
    
    @Delegate
    public DeferAction<DATA> build() {
        return DeferAction.create(interruptOnCancel, supplier, onStart, runner);
    }
    
}
