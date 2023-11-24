// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Arrays.stream;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.environments.Env;
import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.supportive.CallerId;
import functionalj.tuple.ImmutableTuple2;
import lombok.val;

public interface Func {
    
    
    
    //==================================================
    //== STATIC METHODS ================================
    //==================================================
    
    // == Provide different name for more readability ==
    /**
     * A shorter way to use Function.identity() -- alias for itself() and themAll().
     *
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     */
    public static <TYPE> Func1<TYPE, TYPE> it() {
        return it -> it;
    }
    
    /**
     * A shorter way to use Function.identity() -- alias for it() and themAll().
     *
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     */
    public static <TYPE> Func1<TYPE, TYPE> itself() {
        return it -> it;
    }
    
    /**
     * A shorter way to use Function.identity() -- alias for it() and itself().
     *
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     */
    public static <TYPE> Func1<TYPE, TYPE> themAll() {
        return it -> it;
    }
    
    public static <T> Predicate<T> alwaysTrue() {
        return t -> true;
    }
    
    public static <T> Predicate<T> alwaysFalse() {
        return t -> false;
    }
    
    /**
     * A shorter way to do flatmap on list directly.
     *
     * @param <IN>   the input data type.
     * @param <OUT>  the output data type.
     * @return the function that take list and change to stream.
     */
    public static <IN extends List<? extends OUT>, OUT> Func1<? super IN, Stream<? extends OUT>> allLists() {
        return it -> it.stream();
    }
    
    /**
     * A shorter way to do flatmap on list directly from the result of the given mapper function.
     *
     * @param <IN>    the input data type.
     * @param <OUT>   the output data type.
     * @param mapper  the mapper function.
     * @return the function that will apply the given mapper functio to the input and change the result list to stream.
     */
    public static <IN, OUT> Func1<IN, Stream<OUT>> allList(Func1<IN, ? extends List<OUT>> mapper) {
        return it -> mapper.applyUnsafe(it).stream();
    }
    
    public static <INPUT> Predicate<INPUT> only(Function<INPUT, Boolean> check) {
        return input -> check.apply(input);
    }
    
    public static <I, O1, I2> Predicate<I> only(Function<I, O1> mapper, BiPredicate<O1, I2> checker, I2 tailInput) {
        return i -> checker.test(mapper.apply(i), tailInput);
    }
    
    // == List ==
    @SafeVarargs
    public static <T> FuncList<T> listOf(T... data) {
        return ImmutableFuncList.of(data);
    }
    
    // == Of ==
    /**
     * Constructs a Func0 from supplier or lambda.
     *
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> of(Func0<OUTPUT> supplier) {
        return supplier;
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> of(Func1<INPUT, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> of(Func2<INPUT1, INPUT2, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func3 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func4 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func5 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a {@link Func7} from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result {@link Func7}.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> Func7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> of(Func7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a {@link Func8} from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result {@link Func8}.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> Func8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> of(Func8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a {@link Func9} from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result {@link Func9}.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> of(Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a {@link Func10} from function or lambda.
     *
     * @param  function   the function or lambda.
     * @param  <INPUT1>   the first input data type.
     * @param  <INPUT2>   the second input data type.
     * @param  <INPUT3>   the third input data type.
     * @param  <INPUT4>   the forth input data type.
     * @param  <INPUT5>   the fifth input data type.
     * @param  <INPUT6>   the sixth input data type.
     * @param  <INPUT7>   the seventh input data type.
     * @param  <INPUT8>   the eighth input data type.
     * @param  <INPUT9>   the ninth input data type.
     * @param  <INPUT10>  the tenth input data type.
     * @param  <OUTPUT>   the output data type.
     * @return            the result {@link Func10}.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> of(Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> function) {
        return function;
    }
    
    //== FuncUnit1 ==
    
    public static <INPUT> FuncUnit1<INPUT> of(FuncUnit1<INPUT> consumer) {
        return FuncUnit1.of(consumer);
    }
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> of(FuncUnit2<INPUT1, INPUT2> consumer) {
        return FuncUnit2.of(consumer);
    }
    
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> of(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return FuncUnit3.of(consumer);
    }
    
    // == From ==
    public static FuncUnit0 from(Runnable runnable) {
        if (runnable instanceof FuncUnit0)
            return (FuncUnit0) runnable;
        return runnable::run;
    }
    
    public static <INPUT> Predicate<INPUT> from(String name, Predicate<INPUT> predicate) {
        return Named.predicate(name, predicate);
    }
    
    public static <INPUT> FuncUnit1<INPUT> from(Consumer<INPUT> consumer) {
        return FuncUnit1.from(consumer);
    }
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> from(BiConsumer<INPUT1, INPUT2> consumer) {
        return FuncUnit2.from(consumer);
    }
    
    /**
     * Constructs a Func0 from supplier or lambda.
     *
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> from(Supplier<OUTPUT> supplier) {
        return Func0.from(supplier);
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> from(Function<INPUT, OUTPUT> function) {
        return Func1.from(function);
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> from(BiFunction<INPUT1, INPUT2, OUTPUT> function) {
        return Func2.from(function);
    }
    
    // == f - no name ==
    /**
     * Constructs a Func0 from supplier or lambda.
     *
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> f(Func0<OUTPUT> supplier) {
        return supplier;
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> f(Func1<INPUT, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> f(Func2<INPUT1, INPUT2, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func3 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> f(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func4 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> f(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func5 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> f(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func6 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> f(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return function;
    }
    
    public static FuncUnit0 f(FuncUnit0 runnable) {
        return runnable;
    }
    
    public static <INPUT> FuncUnit1<INPUT> f(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> f(FuncUnit2<INPUT1, INPUT2> consumer) {
        return consumer;
    }
    
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> f(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer;
    }
    
    // == f - with name ==
    /**
     * Constructs a name Func0.
     *
     * @param  name      the function name.
     * @param  function  the original function.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> f(String name, Func0<OUTPUT> function) {
        return Named.func0(name, function);
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> f(String name, Func1<INPUT, OUTPUT> function) {
        return Named.func1(name, function);
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> f(String name, Func2<INPUT1, INPUT2, OUTPUT> function) {
        return Named.func2(name, function);
    }
    
    /**
     * Constructs a Func3 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> f(String name, Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
        return Named.func3(name, function);
    }
    
    /**
     * Constructs a Func4 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> f(String name, Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
        return Named.func4(name, function);
    }
    
    /**
     * Constructs a Func5 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> f(String name, Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return Named.func5(name, function);
    }
    
    /**
     * Constructs a Func6 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> f(String name, Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return Named.func6(name, function);
    }
    
    public static FuncUnit0 f(String name, FuncUnit0 function) {
        return Named.funcUnit0(name, function);
    }
    
    public static <INPUT> FuncUnit1<INPUT> f(String name, FuncUnit1<INPUT> function) {
        return Named.funcUnit1(name, function);
    }
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> f(String name, FuncUnit2<INPUT1, INPUT2> function) {
        return Named.funcUnit2(name, function);
    }
    
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> f(String name, FuncUnit3<INPUT1, INPUT2, INPUT3> function) {
        return Named.funcUnit3(name, function);
    }
    
    // == F (traced location) - no name ==
    /**
     * Constructs a Func0 from supplier or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> F(Func0<OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func0(function));
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> F(Func1<INPUT, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func1(function));
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> F(Func2<INPUT1, INPUT2, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func2(function));
    }
    
    /**
     * Constructs a Func3 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> F(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func3(function));
    }
    
    /**
     * Constructs a Func4 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> F(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func4(function));
    }
    
    /**
     * Constructs a Func5 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> F(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func5(function));
    }
    
    /**
     * Constructs a Func6 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> F(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func6(function));
    }
    
    public static FuncUnit0 F(FuncUnit0 function) {
        return CallerId.instance.trace(caller -> Traced.FuncUnit0(function));
    }
    
    public static <INPUT> FuncUnit1<INPUT> F(FuncUnit1<INPUT> function) {
        return CallerId.instance.trace(caller -> Traced.FuncUnit1(function));
    }
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> F(FuncUnit2<INPUT1, INPUT2> function) {
        return CallerId.instance.trace(caller -> Traced.FuncUnit2(function));
    }
    
    // == F (traced location) - with name ==
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> F(String name, Func0<OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func0(name, function));
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> F(String name, Func1<INPUT, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func1(name, function));
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> F(String name, Func2<INPUT1, INPUT2, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func2(name, function));
    }
    
    /**
     * Constructs a Func3 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> F(String name, Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func3(name, function));
    }
    
    /**
     * Constructs a Func4 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func4.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> F(String name, Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func4(name, function));
    }
    
    /**
     * Constructs a Func5 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func5.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> F(String name, Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func5(name, function));
    }
    
    /**
     * Constructs a Func6 from function or lambda.
     * 
     * @param  name      the function name.
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func6.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> F(String name, Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return CallerId.instance.trace(caller -> Traced.Func6(name, function));
    }
    
    public static FuncUnit0 F(String name, FuncUnit0 function) {
        return CallerId.instance.trace(caller -> Traced.FuncUnit0(name, function));
    }
    
    public static <INPUT> FuncUnit1<INPUT> F(String name, FuncUnit1<INPUT> function) {
        return CallerId.instance.trace(caller -> Traced.FuncUnit1(name, function));
    }
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> F(String name, FuncUnit2<INPUT1, INPUT2> function) {
        return CallerId.instance.trace(caller -> Traced.FuncUnit2(name, function));
    }
    
    // == Elevate (instant bind) ==
    public static <I1, I2, O> Func1<I1, O> elevate(BiFunction<I1, I2, O> func, I2 input2) {
        return input1 -> Func.applyUnsafe(func, input1, input2);
    }
    
    public static <I1, I2, I3, O> Func1<I1, O> elevate(Func3<I1, I2, I3, O> func, I2 input2, I3 input3) {
        return input1 -> func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, I4, O> Func1<I1, O> elevate(Func4<I1, I2, I3, I4, O> func, I2 input2, I3 input3, I4 input4) {
        return input1 -> func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, O> Func1<I1, O> elevate(Func5<I1, I2, I3, I4, I5, O> func, I2 input2, I3 input3, I4 input4, I5 input5) {
        return input1 -> func.apply(input1, input2, input3, input4, input5);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Func1<I1, O> elevate(Func6<I1, I2, I3, I4, I5, I6, O> func, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return input1 -> func.apply(input1, input2, input3, input4, input5, input6);
    }
    
    // == Join with delimit ==
    public static <IN> Func1<IN, Stream<IN>> delimitWith(IN delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter, in);
        };
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(Supplier<? extends IN> delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter.get(), in);
        };
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(Func1<IN, ? extends IN> delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter.applyUnsafe(in), in);
        };
    }
    
    @SafeVarargs
    public static <T> Stream<T> streamConcat(Stream<T>... streams) {
        return stream(streams).filter(Objects::nonNull).flatMap(themAll());
    }
    
    public static Consumer<String> throwThis(Function<String, ? extends RuntimeException> exceptionCreator) {
        return errMsg -> {
            if (errMsg == null)
                return;
            val exception = exceptionCreator.apply(errMsg);
            if (exception == null)
                return;
            throw exception;
        };
    }
    
    // == Index ==
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> withIndex(Func2<INPUT, Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.applyUnsafe(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> usingIndex(Func1<Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.applyUnsafe(index.getAndIncrement());
    }
    
    public static class WithIndex<DATA> extends ImmutableTuple2<DATA, Integer> {
        
        public WithIndex(DATA _1, Integer _2) {
            super(_1, _2);
        }
        
        public int index() {
            return _2.intValue();
        }
        
        public DATA value() {
            return _1;
        }
        
        public String toString() {
            // TODO - Umm .. this feels useful but it will break Tuple2.toString() contract.
            return "[#" + _2 + ":" + _1 + "]";
        }
    }
    
    public static interface WithIndexFunction<INPUT, OUTPUT> extends Func1<WithIndex<INPUT>, OUTPUT> {
    }
    
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> withIndex(WithIndexFunction<INPUT, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> {
            val withIndex = new WithIndex<INPUT>(input, index.getAndIncrement());
            return body.applyUnsafe(withIndex);
        };
    }
    
    public static <INPUT> Func1<INPUT, WithIndex<INPUT>> withIndex() {
        val index = new AtomicInteger();
        return input -> new WithIndex<INPUT>(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> FuncUnit1<INPUT> withIndex(FuncUnit2<INPUT, Integer> body) {
        val index = new AtomicInteger();
        return input -> body.acceptUnsafe(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> FuncUnit1<INPUT> withIndex(FuncUnit1<Integer> body) {
        val index = new AtomicInteger();
        return input -> body.acceptUnsafe(index.getAndIncrement());
    }
    
    // == Cache and Lazy ==
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> cacheFor(Function<INPUT, OUTPUT> inFunction) {
        val cache = new ConcurrentHashMap<INPUT, OUTPUT>();
        return in -> cache.computeIfAbsent(in, inFunction::apply);
    }
    
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> cacheFor(long timeMilliSecond, Function<INPUT, OUTPUT> inFunction) {
        val cache = new ConcurrentHashMap<INPUT, OUTPUT>();
        val expiredTime = new ConcurrentHashMap<INPUT, Long>();
        return in -> {
            if (expiredTime.contains(in) && expiredTime.get(in) > Env.time().currentMilliSecond()) {
                cache.remove(in);
            }
            return cache.computeIfAbsent(in, key -> {
                expiredTime.put(key, Env.time().currentMilliSecond() + timeMilliSecond);
                return inFunction.apply(key);
            });
        };
    }
    
    @SuppressWarnings("unchecked")
    public static <TYPE> Func0<TYPE> lazy(Supplier<TYPE> supplier) {
        val reference = new AtomicReference<Object>();
        val startKey = new Object();
        return () -> {
            if (reference.compareAndSet(null, startKey)) {
                try {
                    val value = supplier.get();
                    reference.set((Supplier<TYPE>) (() -> value));
                } catch (RuntimeException e) {
                    reference.set(e);
                }
            }
            while (!(reference.get() instanceof Supplier)) {
                if (reference.get() instanceof RuntimeException)
                    throw (RuntimeException) reference.get();
            }
            return ((Supplier<TYPE>) reference.get()).get();
        };
    }
    
    // == Recursive ==
    public static <I1, I2, R> Func1<I1, R> recusive(Absent absent, I2 i2, Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        Func2<I1, I2, R> grt = recusive(func3);
        return grt.bind(absent, i2);
    }
    
    public static <I1, I2, R> Func1<I2, R> recusive(I1 i1, Absent absent, Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        Func2<I1, I2, R> grt = recusive(func3);
        return grt.bind(i1, absent);
    }
    
    public static <I, R> Func1<I, R> recusive(Func2<Func1<I, R>, I, R> func2) {
        AtomicReference<Func1<I, R>> selfRef = new AtomicReference<>();
        Supplier<Func1<I, R>> self = selfRef::get;
        Func1<I, R> selfFunc = (_i) -> func2.applyUnsafe(self.get(), _i);
        selfRef.set(cacheFor(selfFunc));
        return selfFunc;
    }
    
    public static <I1, I2, R> Func2<I1, I2, R> recusive(Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        AtomicReference<Func2<I1, I2, R>> selfRef = new AtomicReference<>();
        Supplier<Func2<I1, I2, R>> self = selfRef::get;
        Func2<I1, I2, R> selfFunc = (i1, i2) -> func3.applyUnsafe(self.get(), i1, i2);
        selfRef.set(selfFunc);
        return selfFunc;
    }
    
    // == Apply - Unsafe ==
    public static <O, I> O applyUnsafe(Function<I, O> func, I input) throws Exception {
        if (func instanceof Func1)
            return ((Func1<I, O>) func).applyUnsafe(input);
        else
            return func.apply(input);
    }
    
    public static <O, I1, I2> O applyUnsafe(BiFunction<I1, I2, O> func, I1 input1, I2 input2) throws Exception {
        if (func instanceof Func1)
            return ((Func2<I1, I2, O>) func).applyUnsafe(input1, input2);
        else
            return func.apply(input1, input2);
    }
    
    public static <O> O getUnsafe(Supplier<O> func) throws Exception {
        if (func instanceof Func1)
            return ((Supplier<O>) func).get();
        else
            return func.get();
    }
    
    public static void runUnsafe(Runnable runnable) throws Exception {
        if (runnable instanceof Func1)
            ((FuncUnit0) runnable).runUnsafe();
        else
            runnable.run();
    }
    
    public static <I> void acceptUnsafe(Consumer<I> func, I input) throws Exception {
        if (func instanceof Func1)
            ((FuncUnit1<I>) func).acceptUnsafe(input);
        else
            func.accept(input);
    }
    
    public static <I1, I2> void acceptUnsafe(BiConsumer<I1, I2> func, I1 input1, I2 input2) throws Exception {
        if (func instanceof Func1)
            ((FuncUnit2<I1, I2>) func).acceptUnsafe(input1, input2);
        else
            func.accept(input1, input2);
    }
    
    // == Apply - Carelessly ==
    public static void carelessly(Runnable runnable) {
        if (runnable == null)
            return;
        try {
            runnable.run();
        } catch (Exception e) {
            // Do nothing
        }
    }
    
    public static void carelessly(FuncUnit0 action) {
        if (action == null)
            return;
        try {
            action.run();
        } catch (Exception e) {
            // Do nothing
        }
    }
    
    public static void gracefully(Runnable runnable) {
        if (runnable == null)
            return;
        try {
            runnable.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.get().apply(e);
        }
    }
    
    public static void gracefully(FuncUnit0 action) {
        if (action == null)
            return;
        try {
            action.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.get().apply(e);
        }
    }
    
    // == Instant apply ==
    /**
     * Get the value from the supplier that might be null.
     *
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param supplier  the suppler.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <O, F extends O> O getOrElse(Supplier<O> supplier, F fallback) {
        if (supplier == null)
            return fallback;
        val value = supplier.get();
        return (value == null) ? fallback : value;
    }
    
    /**
     * Apply the input value to the function that might be null.
     *
     * @param <I>       the input type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I, O, F extends O> O applyOrElse(Function<? super I, O> function, I input, F fallback) {
        if (function == null)
            return fallback;
        val value = function.apply(input);
        return (value == null) ? fallback : value;
    }
    
    /**
     * Apply the input value to the function that might be null.
     *
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input1    the input 1 value.
     * @param input2    the input 2 value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, O, F extends O> O applyOrElse(BiFunction<? super I1, ? super I2, O> function, I1 input1, I2 input2, F fallback) {
        if (function == null)
            return fallback;
        val value = function.apply(input1, input2);
        return (value == null) ? fallback : value;
    }
    
    /**
     * Apply the input value to the function that might be null.
     *
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <I3>      the input 3 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input1    the input 1 value.
     * @param input2    the input 2 value.
     * @param input3    the input 3 value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, O, F extends O> O applyOrElse(Func3<? super I1, ? super I2, ? super I3, O> function, I1 input1, I2 input2, I3 input3, F fallback) {
        if (function == null)
            return fallback;
        return function.apply(input1, input2, input3);
    }
    
    /**
     * Apply the input value to the function that might be null.
     *
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <I3>      the input 3 type.
     * @param <I4>      the input 4 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input1    the input 1 value.
     * @param input2    the input 2 value.
     * @param input3    the input 3 value.
     * @param input4    the input 4 value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, I4, O, F extends O> O applyOrElse(Func4<? super I1, ? super I2, ? super I3, ? super I4, O> function, I1 input1, I2 input2, I3 input3, I4 input4, F fallback) {
        if (function == null)
            return fallback;
        return function.apply(input1, input2, input3, input4);
    }
    
    // == All ==
    /**
     * Create a high-order function that will take another function that take the given input and return output.
     * NOTE: Not sure if this a traverse.
     *
     * @param <INPUT>   the input data type.
     * @param <OUTPUT>  the output data type.
     * @param input     the input.
     * @return          the high-order function.
     */
    public static <INPUT, OUTPUT> Func1<Func1<INPUT, OUTPUT>, OUTPUT> allApplyTo(INPUT input) {
        return func -> {
            return func.applyUnsafe(input);
        };
    }
    
    /**
     * Create a high-order function that will take another function that take the given input and return output.
     * NOTE: Not sure if this a traverse.
     *
     * @param <INPUT>   the input data type.
     * @param input     the input.
     * @return          the high-order function.
     */
    public static <INPUT> Predicate<Function<INPUT, Boolean>> allCheckWith(INPUT input) {
        return func -> {
            return func.apply(input);
        };
    }
    
    // == Condition ==
    public static <INPUT> Func1<INPUT, INPUT> ifThen(Predicate<INPUT> check, Function<INPUT, INPUT> then) {
        return input -> {
            if (check.test(input))
                return Func.applyUnsafe(then, input);
            else
                return input;
        };
    }
    
    public static <INPUT> Func1<INPUT, INPUT> ifNotThen(Predicate<INPUT> check, Func1<INPUT, INPUT> then) {
        return input -> {
            if (!check.test(input))
                return Func.applyUnsafe(then, input);
            else
                return input;
        };
    }
    
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> ifElse(Predicate<INPUT> check, Function<INPUT, OUTPUT> then, Function<INPUT, OUTPUT> thenElse) {
        return input -> {
            if (check.test(input))
                return Func.applyUnsafe(then, input);
            else
                return Func.applyUnsafe(thenElse, input);
        };
    }
    
    // == Conversion ==
    /**
     * Change the input function to a preficate.
     *
     * @param <INPUT> the input type.
     * @param func    the function that takes input and returns boolean.
     * @return the predicate of the same functionality.
     */
    public static <INPUT> Predicate<INPUT> toPredicate(Func1<INPUT, Boolean> func) {
        return input -> func.apply(input);
    }
    
}
