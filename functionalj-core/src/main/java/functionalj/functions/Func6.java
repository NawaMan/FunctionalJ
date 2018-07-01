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

import functionalj.types.Tuple6;

/**
 * Function of five parameters.
 * 
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 * @param <INPUT4>  the forth input data type.
 * @param <INPUT5>  the fifth input data type.
 * @param <INPUT6>  the sixth input data type.
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> {
    
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
            Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> 
            of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return function;
    }
    
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input1  the first input.
     * @param  input2  the second input.
     * @param  input3  the third input.
     * @param  input4  the forth input.
     * @param  input5  the fifth input.
     * @param  input6  the sixth input.
     * @return         the function result.
     */
    public OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6);
    
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT apply(Tuple6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> input) {
        return apply(input._1(), input._2(), input._3(), input._4(), input._5(), input._6());
    }
    
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     * 
     * @param  <FINAL>  the final result value.
     * @param  after    the function to be run after this function.
     * @return          the composed function.
     */
    public default <FINAL> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, FINAL> then(Func1<? super OUTPUT, ? extends FINAL> after) {
        return (input1, input2, input3, input4, input5, input6) -> {
            OUTPUT out1 = this.apply(input1, input2, input3, input4, input5, input6);
            FINAL  out2 = after.apply(out1);
            return out2;
        };
    }
    
    /**
     * Create a curry function of the this function.
     * 
     * @return  the curried function.
     */
    public default Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, Func1<INPUT4, Func1<INPUT5, Func1<INPUT6, OUTPUT>>>>>> curry() {
        return i1 -> i2 -> i3 -> i4 -> i5 -> i6 -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> apply1(INPUT1 i1) {
        return (i2, i3, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> apply2(INPUT2 i2) {
        return (i1, i3, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> apply3(INPUT3 i3) {
        return (i1, i2, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> apply4(INPUT4 i4) {
        return (i1, i2, i3, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> apply5(INPUT5 i5) {
        return (i1, i2, i3, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> apply6(INPUT6 i6) {
        return (i1, i2, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i2) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT3, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i3) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT4, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT2, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT3, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1, i3) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT4, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT5, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT3, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i2, i3) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT4, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i2, i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT5, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i2, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i2, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT4, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i3, i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i3, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i3, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT4, INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT4, INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> apply(Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2, i3) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2, i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT5, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i2, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT6, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i2, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i3, i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT5, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i3, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i3, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT4, INPUT5, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT4, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT5, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i2, i3, i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT5, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i2, i3, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i2, i3, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT4, INPUT5, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i2, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT4, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i2, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i2, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT3, INPUT4, INPUT5, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT3, INPUT4, INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i3, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT3, INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i3, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT4, INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> apply(Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2, i3, i4) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT5, OUTPUT> apply(Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i2, i3, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT6, OUTPUT> apply(Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i2, i3, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT4, INPUT5, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i2, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT4, INPUT6, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i2, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT5, INPUT6, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i2, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT4, INPUT5, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT4, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i3, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT5, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i3, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT4, INPUT5, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i1, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i2, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i2, i3, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i2, i3, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i2, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return (i3, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> apply(Absent a1, Absent a2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i2, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> apply(Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i2, i3, i4, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> apply(Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i2, i3, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> apply(Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i1, i2, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> apply(Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return (i1, i3, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> apply(INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return (i2, i3, i4, i5, i6) -> this.apply(i1, i2, i3, i4, i5, i6);
    }
    
}
