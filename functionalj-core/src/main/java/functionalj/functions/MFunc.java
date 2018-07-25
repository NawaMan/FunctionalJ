package functionalj.functions;

import functionalj.kinds.Monad;

@SuppressWarnings("javadoc")
public interface MFunc {

    /**
     * Constructs a MFunc6 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, MONAD, OUTPUT> MFunc1<INPUT, MONAD, OUTPUT> of(Func1<INPUT, Monad<MONAD, OUTPUT>> function) {
        return input -> {
            return function.apply(input);
        };
    }
    
    /**
     * Constructs a MFunc2 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT1, INPUT2, MONAD, OUTPUT> MFunc2<INPUT1, INPUT2, MONAD, OUTPUT> of(Func2<INPUT1, INPUT2, Monad<MONAD, OUTPUT>> function) {
        return (input1, input2) -> {
            return function.apply(input1, input2);
        };
    }

    /**
     * Constructs a MFunc3 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT1, INPUT2, INPUT3, MONAD, OUTPUT> MFunc3<INPUT1, INPUT2, INPUT3, MONAD, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, Monad<MONAD, OUTPUT>> function) {
        return (input1, input2, input3) -> {
            return function.apply(input1, input2, input3);
        };
    }

    /**
     * Constructs a MFunc4 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT1, INPUT2, INPUT3, INPUT4, MONAD, OUTPUT> MFunc4<INPUT1, INPUT2, INPUT3, INPUT4, MONAD, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, Monad<MONAD, OUTPUT>> function) {
        return (input1, input2, input3, input4) -> {
            return function.apply(input1, input2, input3, input4);
        };
    }

    /**
     * Constructs a MFunc5 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, MONAD, OUTPUT> MFunc5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, MONAD, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, Monad<MONAD, OUTPUT>> function) {
        return (input1, input2, input3, input4, input5) -> {
            return function.apply(input1, input2, input3, input4, input5);
        };
    }

    /**
     * Constructs a MFunc6 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, MONAD, OUTPUT> MFunc6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, MONAD, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Monad<MONAD, OUTPUT>> function) {
        return (input1, input2, input3, input4, input5, input6) -> {
            return function.apply(input1, input2, input3, input4, input5, input6);
        };
    }
    
}
