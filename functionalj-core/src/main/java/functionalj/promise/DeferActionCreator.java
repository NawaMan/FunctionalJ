package functionalj.promise;

import java.util.function.Consumer;

import functionalj.functions.Func0;
import functionalj.ref.RefTo.Default;

@FunctionalInterface
public interface DeferActionCreator {
    
    @Default
    public static final DeferActionCreator instance = new DefaultDeferActionCreator();
    
    public <D> DeferAction<D> create(boolean interruptOnCancel, Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner);
    
}