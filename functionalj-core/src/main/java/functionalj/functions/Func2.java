//  ========================================================================
//  Copyright (c) 2017-2018 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.functions;

import java.util.function.BiFunction;

import functionalj.types.ImmutableResult;
import functionalj.types.Result;
import functionalj.types.Tuple2;
import lombok.val;

/**
 * Function of two parameters.
 * 
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func2<INPUT1, INPUT2, OUTPUT> extends BiFunction<INPUT1, INPUT2, OUTPUT> {

    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;
    
    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2) {
        try {
            val output = applyUnsafe(input1, input2);
            return ImmutableResult.of(output);
        } catch (Exception exception) {
            return ImmutableResult.of(null, exception);
        }
    }
    
    public default Func2<INPUT1, INPUT2, Result<OUTPUT>> safely() {
        return Func.from(this::applySafely);
    }

    /**
     * Applies this function to the given input values.
     *
     * @param  input1  the first input.
     * @param  input2  the second input.
     * @return         the function result.
     */
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2) {
        try {
            return applyUnsafe(input1, input2);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception exception) {
            throw new FailException(exception);
        }
    }
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT applyTo(Tuple2<INPUT1, INPUT2> input) {
        return apply(input._1(), input._2());
    }
    
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     * 
     * @param  <FINAL>  the final result value.
     * @param  after    the function to be run after this function.
     * @return          the composed function.
     */
    public default <FINAL> Func2<INPUT1, INPUT2, FINAL> then(Func1<? super OUTPUT, ? extends FINAL> after) {
        return (input1, input2) -> {
            OUTPUT out1 = this.apply(input1, input2);
            FINAL  out2 = after.apply(out1);
            return out2;
        };
    }
    
    /**
     * Create a curry function of the this function.
     * 
     * @return  the curried function.
     */
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> curry() {
        return i1 -> i2 -> this.apply(i1, i2);
    }
    
    
    /**
     * Flip the parameter order.
     * 
     * @return  the Func2 with parameter in a flipped order.
     */
    public default Func2<INPUT2, INPUT1, OUTPUT> flip() {
        return (i2, i1) -> this.apply(i1, i2);
    }
    
    //== Partially apply functions ==
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> apply1(INPUT1 i1) {
        return i2 -> this.apply(i1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> apply2(INPUT2 i2) {
        return i1 -> this.apply(i1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> apply(Absent a1, INPUT2 i2) {
        return i1 -> this.apply(i1, i2);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> apply(INPUT1 i1, Absent a2) {
        return i2 -> this.apply(i1, i2);
    }
    
}