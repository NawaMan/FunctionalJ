package functionalj.function;

import functionalj.functions.ThrowFuncs;
import functionalj.tuple.Tuple9;
import lombok.val;

/**
 * Function of nine parameters.
 *
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 * @param <INPUT4>  the forth input data type.
 * @param <INPUT5>  the fifth input data type.
 * @param <INPUT6>  the sixth input data type.
 * @param <INPUT7>  the seventh input data type.
 * @param <INPUT8>  the eighth input data type.
 * @param <INPUT9>  the ninth input data type.
 * @param <OUTPUT>  the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> {
    
    /**
     * Represents a function that takes ten input parameters and produces an output.
     * This is a functional interface whose functional method is {@link #applyUnsafe}.
     * 
     * @param <INPUT1>  the type of the first input parameter
     * @param <INPUT2>  the type of the second input parameter
     * @param <INPUT3>  the type of the third input parameter
     * @param <INPUT4>  the type of the fourth input parameter
     * @param <INPUT5>  the type of the fifth input parameter
     * @param <INPUT6>  the type of the sixth input parameter
     * @param <INPUT7>  the type of the seventh input parameter
     * @param <INPUT8>  the type of the eighth input parameter
     * @param <INPUT9>  the type of the ninth input parameter
     * @param <OUTPUT>  the type of the output produced by this function
     * @return the result of applying this function to the input parameters
     * @throws Exception if the function execution encounters an error
     */
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, INPUT7 input7, INPUT8 input8, INPUT9 input9) throws Exception;
    
    
    //== Apply ==
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input1  the first input.
     * @param  input2  the second input.
     * @param  input3  the third input.
     * @param  input4  the forth input.
     * @param  input5  the fifth input.
     * @param  input6  the sixth input..
     * @param  input7  the seventh input data type.
     * @param  input8  the eighth input data type.
     * @param  input9  the ninth input data type.
     * @return         the function result.
     */
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, INPUT7 input7, INPUT8 input8, INPUT9 input9) {
        try {
            return applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT applyTo(Tuple9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9> input) {
        val _1  = input._1();
        val _2  = input._2();
        val _3  = input._3();
        val _4  = input._4();
        val _5  = input._5();
        val _6  = input._6();
        val _7  = input._7();
        val _8  = input._8();
        val _9  = input._9();
        return apply(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }
    
}
