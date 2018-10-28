package functionalj.promise;

import java.util.function.Consumer;

import functionalj.functions.Func0;

@FunctionalInterface
public interface DeferActionCreator {
    
    public static final DeferActionCreator instance = new DefaultDeferActionCreator();
    
    public <D> DeferAction<D> create(boolean interruptOnCancel, Func0<D> supplier, Runnable onStart, Consumer<Runnable> runner);
    
}