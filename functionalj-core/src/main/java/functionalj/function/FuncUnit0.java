// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.function;

import static java.util.Objects.requireNonNull;

import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.ref.RunBody;
import lombok.val;

public interface FuncUnit0 extends Runnable, RunBody<RuntimeException> {
    
    public static FuncUnit0 of(FuncUnit0 runnable) {
        return runnable;
    }
    public static FuncUnit0 funcUnit0(FuncUnit0 runnable) {
        return runnable;
    }
    public static FuncUnit0 from(Runnable runnable) {
        return runnable::run;
    }
    public static <EXCEPTION extends Exception> FuncUnit0 from(RunBody<EXCEPTION> runnable) {
        return runnable::run;
    }
    
    void runUnsafe() throws Exception;
    
    public default void run() {
        try {
            runUnsafe();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception exception) {
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default void runCarelessly() {
        try {
            runUnsafe();
        } catch (Exception e) {
        }
    }
    
    public default FuncUnit0 then(FuncUnit0 after) {
        requireNonNull(after);
        return () -> {
            runUnsafe();
            after.runUnsafe();
        };
    }
    
    public default <I, T> Func1<I, T> then(Func1<I, T> function) {
        return input -> {
            runUnsafe();
            val value = function.applyUnsafe(input);
            return value;
        };
    }
    
    public default <T> Func0<T> thenReturnNull() {
        return thenReturn(null);
    }
    public default <T> Func0<T> thenReturn(T value) {
        return () -> {
            runUnsafe();
            return value;
        };
    }
    
    public default <T> Func0<T> thenGet(Func0<T> supplier) {
        return () -> {
            runUnsafe();
            val value = supplier.applyUnsafe();
            return value;
        };
    }
    
    
    public default FuncUnit0 carelessly() {
        return this::runCarelessly;
    }
    
    public default DeferAction<Object> async() {
        return this.thenReturnNull().defer();
    }
    
    public default DeferAction<Object> defer() {
        return this.thenReturnNull().defer();
    }
    
}
