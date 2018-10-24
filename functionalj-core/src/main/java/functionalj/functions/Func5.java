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

import java.util.function.Supplier;

import functionalj.result.Result;
import functionalj.tuple.Tuple5;
import lombok.val;

/**
 * Function of five parameters.
 * 
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 * @param <INPUT4>  the third input data type.
 * @param <INPUT5>  the third input data type.
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> {
    
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5) throws Exception;
    
    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5) {
        try {
            val output = applyUnsafe(input1, input2, input3, input4, input5);
            return Result.of(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
    }
    
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input1  the first input.
     * @param  input2  the second input.
     * @param  input3  the third input.
     * @param  input4  the forth input.
     * @param  input5  the fifth input.
     * @return         the function result.
     */
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5) {
        try {
            return applyUnsafe(input1, input2, input3, input4, input5);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT apply(Tuple5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5> input) {
        return apply(input._1(), input._2(), input._3(), input._4(), input._5());
    }
    
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> elseUse(OUTPUT defaultValue) {
        return (input1, input2, input3, input4, input5)->{
            val result = applySafely(input1, input2, input3, input4, input5);
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> elseGet(Supplier<OUTPUT> defaultSupplier) {
        return (input1, input2, input3, input4, input5)->{
            val result = applySafely(input1, input2, input3, input4, input5);
            val value  = result.orElseGet(defaultSupplier);
            return value;
        };
    }
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     * 
     * @param  <FINAL>  the final result value.
     * @param  after    the function to be run after this function.
     * @return          the composed function.
     */
    public default <FINAL> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, FINAL> then(Func1<? super OUTPUT, ? extends FINAL> after) {
        return (input1, input2, input3, input4, input5) -> {
            OUTPUT out1 = this.apply(input1, input2, input3, input4, input5);
            FINAL  out2 = after.apply(out1);
            return out2;
        };
    }
    
    public default Func1<Tuple5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5>, OUTPUT> toTupleFunction() {
        return t -> this.apply(t._1(), t._2(), t._3(), t._4(), t._5());
    }
    
    /**
     * Create a curry function of the this function.
     * 
     * @return  the curried function.
     */
    public default Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, Func1<INPUT4, Func1<INPUT5, OUTPUT>>>>> curry() {
        return i1 -> i2 -> i3 -> i4 -> i5 -> this.apply(i1, i2, i3, i4, i5);
    }
    
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> apply1(INPUT1 i1) {
        return (i2,i3,i4,i5) -> this.apply(i1, i2, i3,i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT4, INPUT5, OUTPUT> apply2(INPUT2 i2) {
        return (i1,i3,i4,i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT4, INPUT5, OUTPUT> apply3(INPUT3 i3) {
        return (i1,i2,i4,i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT5, OUTPUT> apply4(INPUT4 i4) {
        return (i1,i2,i3,i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> apply5(INPUT5 i5) {
        return (i1,i2,i3,i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5) {
        return i1 -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5) {
        return i2 -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT3, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return i3 -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT4, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return i4 -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return i5 -> this.apply(i1, i2, i3, i4, i5);
    }
    
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT2, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5) {
        return (i1, i2) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT3, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return (i1, i3) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT4, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return (i1, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT5, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return (i1, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT3, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return (i2, i3) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT4, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return (i2, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT5, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return (i2, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT4, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5) {
        return (i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5) {
        return (i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT4, INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5) {
        return (i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> apply(Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5) {
        return (i1, i2, i3) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5) {
        return (i1, i2, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT5, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5) {
        return (i1, i2, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5) {
        return (i1, i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT5, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5) {
        return (i1, i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5) {
        return (i2, i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT5, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5) {
        return (i2, i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT3, INPUT4, INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, Absent I4, Absent a5) {
        return (i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> apply(Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5) {
        return (i1, i2, i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT5, OUTPUT> apply(Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5) {
        return (i1, i2, i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT4, INPUT5, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5) {
        return (i1, i2, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT4, INPUT5, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5) {
        return (i1, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5) {
        return (i2, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
}