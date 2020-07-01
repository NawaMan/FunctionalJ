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

import java.util.function.Consumer;

import functionalj.functions.ThrowFuncs;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import lombok.val;

public interface FuncUnit1<INPUT> extends Consumer<INPUT> {
    
    public static <INPUT> FuncUnit1<INPUT> of(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    public static <INPUT> FuncUnit1<INPUT> funcUnit1(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    public static <INPUT> FuncUnit1<INPUT> from(Consumer<INPUT> consumer) {
        return consumer::accept;
    }
    
    public void acceptUnsafe(INPUT input) throws Exception;
    
    public default void accept(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    public default void acceptCarelessly(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (Exception e) {
        }
    }
    
    public default FuncUnit1<INPUT> then(FuncUnit0 after) {
        requireNonNull(after);
        return input -> {
            acceptUnsafe(input);
            after.runUnsafe();
        };
    }
    public default FuncUnit1<INPUT> then(FuncUnit1<? super INPUT> after) {
        requireNonNull(after);
        return input -> {
            acceptUnsafe(input);
            after.acceptUnsafe(input);
        };
    }
    
    public default <T> Func1<INPUT, T> thenReturnNull() {
        return thenReturn(null);
    }
    public default <T> Func1<INPUT, T> thenReturn(T value) {
        return input -> {
            acceptUnsafe(input);
            return value;
        };
    }
    
    public default <T> Func1<INPUT, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return input -> {
            acceptUnsafe(input);
            val value = supplier.applyUnsafe();
            return value;
        };
    }
    
    public default FuncUnit1<INPUT> ignoreNullInput() {
        return input -> {
            if (input != null)
                acceptUnsafe(input);
        };
    }
    
    public default FuncUnit1<INPUT> carelessly() {
        return this::acceptCarelessly;
    }
    
    public default Func1<INPUT, Promise<Object>> async() {
        return this.thenReturnNull().async();
    }
    public default Func1<HasPromise<INPUT>, Promise<Object>> defer() {
        return input -> {
            val func0 = this.thenReturnNull();
            return input.getPromise().map(func0);
        };
    }
    
    public default FuncUnit0 bind(INPUT i) {
        return () -> this.acceptUnsafe(i);
    }
}
