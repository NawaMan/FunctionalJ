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

import java.util.function.BiConsumer;

import functionalj.functions.ThrowFuncs;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.tuple.Tuple2;
import lombok.val;

@FunctionalInterface
public interface FuncUnit2<INPUT1, INPUT2> extends BiConsumer<INPUT1, INPUT2> {
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> of(FuncUnit2<INPUT1, INPUT2> consumer) {
        return consumer;
    }
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> funcUnit2(FuncUnit2<INPUT1, INPUT2> consumer) {
        return consumer;
    }
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> from(BiConsumer<INPUT1, INPUT2> consumer) {
        return consumer::accept;
    }
    
    public void acceptUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;
    
    public default void accept(INPUT1 input1, INPUT2 input2) {
        try {
            acceptUnsafe(input1, input2);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    public default void acceptCarelessly(INPUT1 input1, INPUT2 input2) {
        try {
            acceptUnsafe(input1, input2);
        } catch (Exception e) {
        }
    }
    
    public default FuncUnit2<INPUT1, INPUT2> then(FuncUnit0 after) {
        requireNonNull(after);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            after.runUnsafe();
        };
    }
    public default FuncUnit2<INPUT1, INPUT2> then(FuncUnit2<? super INPUT1, ? super INPUT2> after) {
        requireNonNull(after);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            after.acceptUnsafe(input1, input2);
        };
    }
    
    public default <T> Func2<INPUT1, INPUT2, T> thenReturnNull() {
        return thenReturn(null);
    }
    public default <T> Func2<INPUT1, INPUT2, T> thenReturn(T value) {
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            return value;
        };
    }
    
    public default <T> Func2<INPUT1, INPUT2, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            var value = supplier.applyUnsafe();
            return value;
        };
    }
    
    public default FuncUnit2<INPUT1, INPUT2> ignoreNullInput() {
        return (input1, input2) -> {
            if ((input1 != null)
             && (input2 != null))
                acceptUnsafe(input1, input2);
        };
    }
    
    public default FuncUnit1<Tuple2<INPUT1, INPUT2>> wholly() {
        return tuple -> {
            var _1 = tuple._1();
            var _2 = tuple._2();
            acceptUnsafe(_1, _2);
        };
    }
    
    public default FuncUnit2<INPUT1, INPUT2> carelessly() {
        return this::acceptCarelessly;
    }
    
    public default Func2<INPUT1, INPUT2, Promise<Object>> async() {
        return this.thenReturnNull().async();
    }
    public default Func2<HasPromise<INPUT1>, HasPromise<INPUT2>, Promise<Object>> defer() {
        return (promise1, promise2) -> {
            var func0 = this.thenReturnNull();
            return Promise.from(
                    input1 -> promise1,
                    input2 -> promise2,
                    func0);
        };
    }
    public default FuncUnit1<INPUT1> elevateWith(INPUT2 input2) {
        return (input1) -> acceptUnsafe(input1, input2);
    }
    
    //== Partially apply functions ==
    
    public default FuncUnit0 bind(INPUT1 i1, INPUT2 i2) {
        return () -> this.acceptUnsafe(i1, i2);
    }
    public default FuncUnit1<INPUT2> bind1(INPUT1 i1) {
        return i2 -> this.acceptUnsafe(i1, i2);
    }
    
    public default FuncUnit1<INPUT1> bind2(INPUT2 i2) {
        return i1 -> this.acceptUnsafe(i1, i2);
    }
    
    public default FuncUnit1<INPUT1> bind(Absent a1, INPUT2 i2) {
        return i1 -> this.acceptUnsafe(i1, i2);
    }
    public default FuncUnit1<INPUT2> bind(INPUT1 i1, Absent a2) {
        return i2 -> this.acceptUnsafe(i1, i2);
    }
}
