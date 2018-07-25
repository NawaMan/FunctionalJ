package functionalj.functions;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.val;
import tuple.ImmutableTuple2;
import tuple.Tuple;
import tuple.Tuple2;
import tuple.Tuple3;
import tuple.Tuple4;
import tuple.Tuple5;
import tuple.Tuple6;

@SuppressWarnings("javadoc")
public interface Func {
    
    /**
     * Returns a function that simply return the value.
     * 
     * @param <OUTPUT>  the value type.
     * @param value     the value.
     * @return          the function that return the value.
     */
    public static <OUTPUT> Func0<OUTPUT> supply(OUTPUT value) {
        return () -> value;
    }
    
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     **/
    public static <OUTPUT> Func0<OUTPUT> from(Supplier<OUTPUT> supplier) {
        return supplier::get;
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> from(Function<INPUT, OUTPUT> function) {
        return function::apply;
    }
    
    public static <INPUT1, INPUT2, OUTPUT> Func1<INPUT1, OUTPUT> from(BiFunction<INPUT1, INPUT2, OUTPUT> function, INPUT2 input2) {
        return input1 -> function.apply(input1, input2);
    }
    
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func1<INPUT1, OUTPUT> from(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function, INPUT2 input2, INPUT3 input3) {
        return input1 -> function.apply(input1, input2, input3);
    }

    /**
     * Constructs a Func2 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     **/
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> from(BiFunction<INPUT1, INPUT2, OUTPUT> function) {
        return function::apply;
    }
    
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     **/
    public static <OUTPUT> 
            Func0<OUTPUT> of(Func0<OUTPUT> supplier) {
        return supplier;
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, OUTPUT> 
            Func1<INPUT, OUTPUT> of(Func1<INPUT, OUTPUT> function) {
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
     **/
    public static <INPUT1, INPUT2,OUTPUT> 
            Func2<INPUT1, INPUT2, OUTPUT> of(Func2<INPUT1, INPUT2, OUTPUT> function) {
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
     **/
    public static <INPUT1,INPUT2,INPUT3,OUTPUT> 
            Func3<INPUT1, INPUT2, INPUT3, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
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
     **/
    public static
            <INPUT1,INPUT2,INPUT3,INPUT4,OUTPUT> 
            Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
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
     **/
    public static 
            <INPUT1,INPUT2,INPUT3,INPUT4,INPUT5,OUTPUT> 
            Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
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
     **/
    public static 
            <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> 
            Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return function;
    }
    
    
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
        
        return supplier.get();
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
        
        return function.apply(input);
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
    public static <I1, I2, O, F extends O> O applyOrElse(
            BiFunction<? super I1, ? super I2, O> function, 
            I1                                    input1, 
            I2                                    input2,
            F                                     fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input1, input2);
    }
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, O, F extends O> O applyOrElse(
            Function<Tuple2<? super I1, ? super I2>, O> function, 
            Tuple2<? super I1, ? super I2>              input,
            F                                           fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input);
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
    public static <I1, I2, I3, O, F extends O> O applyOrElse(
            Func3<? super I1, ? super I2, ? super I3, O> function, 
            I1                                           input1,
            I2                                           input2, 
            I3                                           input3, 
            F                                            fallback) {
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
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, O, F extends O> O applyOrElse(
            Function<Tuple3<? super I1, ? super I2, ? super I3>, O> function, 
            Tuple3<? super I1, ? super I2, ? super I3>              input, 
            F                                                       fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input);
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
    public static <I1, I2, I3, I4, O, F extends O> O applyOrElse(
            Func4<? super I1, ? super I2, ? super I3, ? super I4, O> function, 
            I1                                                       input1,
            I2                                                       input2, 
            I3                                                       input3, 
            I4                                                       input4, 
            F                                                        fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input1, input2, input3, input4);
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
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, I4, O, F extends O> O applyOrElse(
            Function<Tuple4<? super I1, ? super I2, ? super I3, ? super I4>, O> function, 
            Tuple4<? super I1, ? super I2, ? super I3, ? super I4>              input, 
            F                                                                   fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input);
    }
    
    //== All ==
    
    /**
     * Create a high-order function that will take another function that take the given input and return output.
     * NOTE: Not sure if this a traverse.
     * 
     * @param <INPUT>   the input data type.
     * @param <OUTPUT>  the output data type.
     * @param input     the input.
     * @return          the high-order function.
     */
    public static <INPUT,OUTPUT> Func1<Function<INPUT,OUTPUT>, OUTPUT> allApplyTo(INPUT input) {
        return func -> {
            return func.apply(input);
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
    
    //== condition ==
    
    public static <INPUT> Func1<INPUT, INPUT> ifThen(
            Predicate<INPUT>    check, 
            Func1<INPUT, INPUT> then) {
        return input -> {
            if (check.test(input))
                 return then.apply(input);
            else return input;
        };
    }
    public static <INPUT> Func1<INPUT, INPUT> ifNotThen(
            Predicate<INPUT>    check, 
            Func1<INPUT, INPUT> then) {
        return input -> {
            if (!check.test(input))
                 return then.apply(input);
            else return input;
        };
    }
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> ifElse(
            Predicate<INPUT>     check, 
            Func1<INPUT, OUTPUT> then, 
            Func1<INPUT, OUTPUT> thenElse) {
        return input -> {
            if (check.test(input))
                 return then    .apply(input);
            else return thenElse.apply(input);
        };
    }
    
    //== toTuple ==
    
    public static <I, T1, T2> Func1<I, Tuple2<T1, T2>> toTuple(
            Function<I, T1> func1, 
            Function<I, T2> func2) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            return Tuple.of(_1, _2);
        };
    }
    
    public static <I, T1, T2> Func1<I, Tuple2<T1, T2>> toTuple(
            Function<I, Map.Entry<? extends T1, ? extends T2>> func) {
        return input -> {
            val entry = func.apply(input);
            return new ImmutableTuple2<>(entry);
        };
    }
    
    public static <I, T1, T2, T3> Func1<I, Tuple3<T1, T2, T3>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            return Tuple.of(_1, _2, _3);
        };
    }
    
    public static <I, T1, T2, T3, T4> Func1<I, Tuple4<T1, T2, T3, T4>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3,
            Function<I, T4 > func4) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            val _4 = func4.apply(input);
            return Tuple.of(_1, _2, _3, _4);
        };
    }
    
    public static <I, T1, T2, T3, T4, T5> Func1<I, Tuple5<T1, T2, T3, T4, T5>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3,
            Function<I, T4 > func4,
            Function<I, T5 > func5) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            val _4 = func4.apply(input);
            val _5 = func5.apply(input);
            return Tuple.of(_1, _2, _3, _4, _5);
        };
    }

    public static <I, T1, T2, T3, T4, T5, T6> Func1<I, Tuple6<T1, T2, T3, T4, T5, T6>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3,
            Function<I, T4 > func4,
            Function<I, T5 > func5,
            Function<I, T6 > func6) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            val _4 = func4.apply(input);
            val _5 = func5.apply(input);
            val _6 = func6.apply(input);
            return Tuple.of(_1, _2, _3, _4, _5, _6);
        };
    }
    
    //== Partial apply ==
    
    public static <INPUT1, INPUT2, OUTPUT> Func1<INPUT1, Func1<INPUT2, OUTPUT>> curry(BiFunction<INPUT1, INPUT2, OUTPUT> func) {
        return i1 -> i2 -> func.apply(i1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, OUTPUT> Func1<INPUT1, OUTPUT> of(BiFunction<INPUT1, INPUT2, OUTPUT> func, Absent a1, INPUT2 i2) {
        if (func instanceof Func2)
            return ((Func2<INPUT1, INPUT2, OUTPUT>)func).apply(a1, i2);
        
        return Func.from(func).apply(a1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, OUTPUT> Func1<INPUT2, OUTPUT> of(BiFunction<INPUT1, INPUT2, OUTPUT> func, INPUT1 i1, Absent a2) {
        if (func instanceof Func2)
            return ((Func2<INPUT1, INPUT2, OUTPUT>)func).apply(i1, a2);
        
        return Func.from(func).apply(i1, a2);
    }
    
    //-- Func3 --

    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, OUTPUT>>> curry(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func) {
        return i1 -> i2 ->i3 -> func.apply(i1, i2, i3);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func1<INPUT1, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3) {
        return func.apply(a1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func1<INPUT2, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3) {
        return func.apply(i1, a2, i3);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func1<INPUT3, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3) {
        return func.apply(i1, i2, a3);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3) {
        return func.apply(a1, a2, i3);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func2<INPUT1, INPUT3, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3) {
        return func.apply(a1, i2, a3);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func2<INPUT2, INPUT3, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3) {
        return func.apply(i1, a2, a3);
    }
    
    //-- Func4 --

    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, Func1<INPUT4, OUTPUT>>>> curry(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func) {
        return i1 -> i2 -> i3 -> i4 -> func.apply(i1, i2, i3, i4);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func1<INPUT1, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return func.apply(a1, i2, i3, i4);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func1<INPUT2, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return func.apply(i1, a2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func1<INPUT3, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return func.apply(i1, i2, a3, i4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func1<INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return func.apply(i1, i2, i3, a4);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return func.apply(a1, a2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func2<INPUT1, INPUT3, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return func.apply(a1, i2, a3, i4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func2<INPUT1, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return func.apply(a1, i2, i3, a4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func2<INPUT2, INPUT3, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, INPUT4 i4) {
        return func.apply(i1, a2, a3, i4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func2<INPUT2, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, Absent a4) {
        return func.apply(i1, a2, i3, a4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func2<INPUT3, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, Absent a4) {
        return func.apply(i1, i2, a3, a4);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, Absent a1, Absent a2, Absent a3, INPUT4 i4) {
        return func.apply(a1, a2, a3, i4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func3<INPUT1, INPUT2, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, Absent a4) {
        return func.apply(a1, a2, i3, a4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func3<INPUT1, INPUT3, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, Absent a4) {
        return func.apply(a1, i2, a3, a4);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func3<INPUT2, INPUT3, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, Absent a4) {
        return func.apply(i1, a2, a3, a4);
    }
    
    //-- Func5 --
    
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, Func1<INPUT4, Func1<INPUT5, OUTPUT>>>>> curry(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func) {
        return i1 -> i2 -> i3 -> i4 -> i5 -> func.apply(i1, i2, i3, i4, i5);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func1<INPUT1, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5) {
        return func.apply(a1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func1<INPUT2, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5) {
        return func.apply(i1, a2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func1<INPUT3, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return func.apply(i1, i2, a3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func1<INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return func.apply(i1, i2, i3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func1<INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return func.apply(i1, i2, i3, i4, a5);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5) {
        return func.apply(a1, a2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT1, INPUT3, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return func.apply(a1, i2, a3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT1, INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return func.apply(a1, i2, i3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT1, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return func.apply(a1, i2, i3, i4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT2, INPUT3, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return func.apply(i1, a2, a3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT2, INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return func.apply(i1, a2, i3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT2, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return func.apply(i1, a2, i3, i4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT3, INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5) {
        return func.apply(i1, i2, a3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT3, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5) {
        return func.apply(i1, i2, a3, i4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func2<INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5) {
        return func.apply(i1, i2, i3, a4, a5);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return func.apply(a1, a2, a3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT1, INPUT2, INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return func.apply(a1, a2, i3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT1, INPUT2, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return func.apply(a1, a2, i3, i4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT1, INPUT3, INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5) {
        return func.apply(a1, i2, a3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT1, INPUT3, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5) {
        return func.apply(a1, i2, a3, i4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT2, INPUT3, INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5) {
        return func.apply(i1, a2, a3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT2, INPUT3, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5) {
        return func.apply(i1, a2, a3, i4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func3<INPUT3, INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, Absent I4, Absent a5) {
        return func.apply(i1, i2, a3, I4, a5);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5) {
        return func.apply(a1, a2, a3, a4, i5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5) {
        return func.apply(a1, a2, a3, i4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func4<INPUT1, INPUT2, INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5) {
        return func.apply(a1, a2, i3, a4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func4<INPUT1, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5) {
        return func.apply(a1, i2, a3, a4, a5);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func4<INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5) {
        return func.apply(i1, a2, a3, a4, a5);
    }
    
    //-- Func6 --
    
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, Func1<INPUT4, Func1<INPUT5, Func1<INPUT6, OUTPUT>>>>>> curry(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func) {
        return i1 -> i2 -> i3 -> i4 -> i5 -> i6 -> func.apply(i1, i2, i3, i4, i5, i6);
    }

    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT1, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT2, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return func.apply(i1, a2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT3, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return func.apply(i1, i2, a3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(i1, i2, i3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(i1, i2, i3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(i1, i2, i3, i4, i5, a6);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, a2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT1, INPUT3, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, i2, a3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT1, INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, i2, i3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT1, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(a1, i2, i3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT1, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(a1, i2, i3, i4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT2, INPUT3, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return func.apply(i1, a2, a3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT2, INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(i1, a2, i3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT2, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(i1, a2, i3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT2, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(i1, a2, i3, i4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT3, INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(i1, i2, a3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT3, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(i1, i2, a3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT3, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(i1, i2, a3, i4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(i1, i2, i3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(i1, i2, i3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func2<INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(i1, i2, i3, i4, a5, a6);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, a2, a3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT2, INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, a2, i3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT2, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(a1, a2, i3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT2, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(a1, a2, i3, i4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT3, INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, i2, a3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT3, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(a1, i2, a3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT3, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(a1, i2, a3, i4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(a1, i2, i3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(a1, i2, i3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT1, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(a1, i2, i3, i4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT2, INPUT3, INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(i1, a2, a3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT2, INPUT3, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(i1, a2, a3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT2, INPUT3, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(i1, a2, a3, i4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT2, INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(i1, a2, i3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT2, INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(i1, a2, i3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT2, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(i1, a2, i3, i4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT3, INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(i1, i2, a3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT3, INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(i1, i2, a3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT3, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(i1, i2, a3, i4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func3<INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return func.apply(i1, i2, i3, a4, a5, a6);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return func.apply(a1, a2, a3, a4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return func.apply(a1, a2, a3, i4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return func.apply(a1, a2, a3, i4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT2, INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(a1, a2, i3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT2, INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(a1, a2, i3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT2, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(a1, a2, i3, i4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(a1, i2, a3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT3, INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(a1, i2, a3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT3, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(a1, i2, a3, i4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT1, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return func.apply(a1, i2, i3, a4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(i1, a2, a3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(i1, a2, a3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(i1, a2, a3, i4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return func.apply(i1, a2, i3, a4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func4<INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return func.apply(i1, i2, a3, a4, a5, a6);
    }
    
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return func.apply(a1, a2, a3, a4, a5, i6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return func.apply(a1, a2, a3, a4, i5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return func.apply(a1, a2, a3, i4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func5<INPUT1, INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return func.apply(a1, a2, i3, a4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func5<INPUT1, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return func.apply(a1, i2, a3, a4, a5, a6);
    }
    @SuppressWarnings("javadoc")
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return func.apply(i1, a2, a3, a4, a5, a6);
    }
}
