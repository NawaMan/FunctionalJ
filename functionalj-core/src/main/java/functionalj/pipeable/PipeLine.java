// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.pipeable;

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
                    ? new PipeLine.NullSafe<>(functions, Catch.thenReturn(elseValue))
                    : new PipeLine<>         (functions, Catch.thenReturn(elseValue));
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrElseGet(Supplier<OUTPUT> elseSupplier) {
            return thenReturnOrGet(elseSupplier);
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrGet(Supplier<OUTPUT> elseSupplier) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, Catch.thenGet(elseSupplier))
                    : new PipeLine<>         (functions, Catch.thenGet(elseSupplier));
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrThrow() {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, Catch.thenThrow())
                    : new PipeLine<>         (functions, Catch.thenThrow());
        }
        public PipeLine<INPUT, OUTPUT> thenReturnOrThrowRuntimeException() {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, Catch.thenThrowRuntimeException())
                    : new PipeLine<>         (functions, Catch.thenThrowRuntimeException());
        }
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenHandleValue(Function<OUTPUT, FINALOUTPUT> mapper) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, Catch.thenHandleValue(mapper))
                    : new PipeLine<>         (functions, Catch.thenHandleValue(mapper));
        }
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenHandleException(Function<Exception, FINALOUTPUT> mapper) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, Catch.thenHandleException(mapper))
                    : new PipeLine<>         (functions, Catch.thenHandleException(mapper));
        }
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenHandle(BiFunction<OUTPUT, Exception, FINALOUTPUT> mapper) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, Catch.thenHandle(mapper))
                    : new PipeLine<>         (functions, Catch.thenHandle(mapper));
        }
        
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenCatch(Catch<OUTPUT, FINALOUTPUT, ? extends Exception> handler) {
            return isNullSafe
                    ? new PipeLine.NullSafe<>(functions, handler)
                    : new PipeLine<>         (functions, handler);
        }
    }
    
}
