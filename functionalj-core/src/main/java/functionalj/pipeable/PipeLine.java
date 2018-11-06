package functionalj.pipeable;

import static functionalj.pipeable.Catch.handle;
import static functionalj.pipeable.Catch.handleException;
import static functionalj.pipeable.Catch.handleValue;
import static functionalj.pipeable.Catch.orElse;
import static functionalj.pipeable.Catch.orElseGet;
import static functionalj.pipeable.Catch.orThrow;
import static functionalj.pipeable.Catch.orThrowRuntimeException;
import static functionalj.pipeable.Catch.toResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable.__internal;
import lombok.val;

@SuppressWarnings("javadoc")
public class PipeLine<INPUT, OUTPUT> implements Func1<INPUT, OUTPUT> {
    
    private static final boolean NULL_SAFE   = true;
    private static final boolean NULL_UNSAFE = false;
    
    @SuppressWarnings("rawtypes")
    private final ImmutableList<Func1> functions;
    
    @SuppressWarnings("rawtypes")
    private final Catch catchHandler;
    
    @SuppressWarnings("rawtypes")
    private PipeLine(List<Func1> functions, Catch catchHandler) {
        this.functions    = ImmutableList.from(functions);
        this.catchHandler = catchHandler;
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public OUTPUT applyUnsafe(INPUT input) throws Exception {
        try {
            Object data = input;
            for (int i = 0; i < functions.size(); i++) {
                val func1 = functions.get(i);
                data = __internal.apply(func1, data);
            }
            if (catchHandler == null)
                return (OUTPUT)data;
            
            return (OUTPUT)catchHandler.doCatch(data, null);
            
        } catch (Exception e) {
            
            if (catchHandler == null)
                return (OUTPUT)null;
            return (OUTPUT)catchHandler.doCatch(null, e);
        }
    }
    
    public static <I> Builder<I, I> of(Class<I> inputType) {
        val builder = new Builder<I, I>(NULL_UNSAFE);
        return builder;
    }
    public static <I> Builder<I, I> ofNullable(Class<I> inputType) {
        val builder = new Builder<I, I>(NULL_SAFE);
        return builder;
    }
    
    public static <I, O> Builder<I, O> from(Func1<I, O> func1) {
        val builder = new Builder<I, O>(NULL_UNSAFE);
        builder.functions.add(func1);
        return builder;
    }
    
    
    static class NullSafe<INPUT, OUTPUT> extends PipeLine<INPUT, OUTPUT> implements NullSafeOperator<INPUT, OUTPUT> {
        @SuppressWarnings("rawtypes")
        private NullSafe(List<Func1> functions, Catch catchHandler) {
            super(functions, catchHandler);
        }
    }
    
    public static class Builder<INPUT, OUTPUT> {
        
        private final boolean isNullSafe;
        
        @SuppressWarnings("rawtypes")
        private final List<Func1> functions = new ArrayList<Func1>();
        
        private Builder(boolean isNullSafe) {
            this.isNullSafe = isNullSafe;
        }
        
        @SuppressWarnings("rawtypes")
        private Builder(boolean isNullSafe, List<Func1> functions, Func1 func1) {
            this.isNullSafe = isNullSafe;
            this.functions.addAll(functions);
            this.functions.add(func1);
        }
        
        public <O> Builder<INPUT, O> then(Func1<? super OUTPUT, O> func) {
            return new Builder<INPUT, O>(isNullSafe, functions, func);
        }
        
        public PipeLine<INPUT, OUTPUT> thenReturn() {
            return isNullSafe
                ? new PipeLine.NullSafe<>(functions, null)
                : new PipeLine<>         (functions, null);
        }
        
        public PipeLine<INPUT, OUTPUT> thenReturnAsResult() {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, toResult())
                    : new PipeLine<>         (functions, toResult());
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrElse(OUTPUT elseValue) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, orElse(elseValue))
                    : new PipeLine<>         (functions, orElse(elseValue));
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrElseGet(Supplier<OUTPUT> elseSupplier) {
            return thenReturnOrGet(elseSupplier);
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrGet(Supplier<OUTPUT> elseSupplier) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, orElseGet(elseSupplier))
                    : new PipeLine<>         (functions, orElseGet(elseSupplier));
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrThrow() {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, orThrow())
                    : new PipeLine<>         (functions, orThrow());
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrThrowRuntimeException() {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, orThrowRuntimeException())
                    : new PipeLine<>         (functions, orThrowRuntimeException());
        }
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenHandleValue(Function<OUTPUT, FINALOUTPUT> mapper) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, handleValue(mapper))
                    : new PipeLine<>         (functions, handleValue(mapper));
        }
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenHandleException(Function<Exception, FINALOUTPUT> mapper) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, handleException(mapper))
                    : new PipeLine<>         (functions, handleException(mapper));
        }
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenHandle(BiFunction<OUTPUT, Exception, FINALOUTPUT> mapper) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, handle(mapper))
                    : new PipeLine<>         (functions, handle(mapper));
        }
        
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenCatch(Catch<OUTPUT, FINALOUTPUT, ? extends Exception> handler) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, handler)
                    : new PipeLine<>         (functions, handler);
        }
    }
    
}
