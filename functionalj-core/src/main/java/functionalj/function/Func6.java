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
package functionalj.function;

import java.util.Optional;
import java.util.function.Supplier;

import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.tuple.Tuple6;
import lombok.val;

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

    public static <I1, I2, I3, I4, I5, I6, O> Func6<I1, I2, I3, I4, I5, I6, O> of(Func6<I1, I2, I3, I4, I5, I6, O> func) {
        return func;
    }
    
    
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6) throws Exception;
    
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
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6) {
        try {
            return applyUnsafe(input1, input2, input3, input4, input5, input6);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw Func.exceptionHandler.value().apply(e);
        }
    }
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT applyTo(Tuple6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> input) {
        return apply(input._1(), input._2(), input._3(), input._4(), input._5(), input._6());
    }
    
    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6) {
        try {
            val output = applyUnsafe(input1, input2, input3, input4, input5, input6);
            return Result.of(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
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
            OUTPUT out1 = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
            FINAL  out2 = Func.applyUnsafe(after, out1);
            return out2;
        };
    }
    
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input1, input2, input3, input4, input5, input6)->{
            val result = applySafely(input1, input2, input3, input4, input5, input6);
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return (input1, input2, input3, input4, input5, input6)->{
            val result = applySafely(input1, input2, input3, input4, input5, input6);
            val value  = result.orElseGet(defaultSupplier);
            return value;
        };
    }
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4, input5, input6)->{
            val result = applySafely(input1, input2, input3, input4, input5, input6);
            val value  = result.orApply(exceptionMapper);
            return value;
        };
    }
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentApply(Func2<Tuple6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4, input5, input6)->{
            val result = applySafely(input1, input2, input3, input4, input5, input6);
            val value  = result.orApply(exception -> exceptionMapper.apply(Tuple6.of(input1, input2, input3, input4, input5, input6), exception));
            return value;
        };
    }
    
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, OUTPUT defaultValue) {
        return applySafely(input1, input2, input3, input4, input5, input6).orElse(defaultValue);
    }
    
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input1, input2, input3, input4, input5, input6).orGet(defaultSupplier);
    }
    
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Optional<OUTPUT>> optionally() {
        return (input1, input2, input3, input4, input5, input6) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input1, input2, input3, input4, input5, input6));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Promise<OUTPUT>> async() {
        return (input1, input2, input3, input4, input5, input6) -> {
            val supplier = (Func0<OUTPUT>)()->{
                return this.applyUnsafe(input1, input2, input3, input4, input5, input6);
            };
            return DeferAction.from(supplier)
                    .start().getPromise();
        };
    }
    
    public default Func6<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, HasPromise<INPUT4>, HasPromise<INPUT5>, HasPromise<INPUT6>, Promise<OUTPUT>> defer() {
        return (promise1, promise2, promise3, promise4, promise5, promise6) -> {
            return Promise.from(promise1, promise2, promise3, promise4, promise5, promise6, this);
        };
    }
    
    public default Func1<Tuple6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6>, OUTPUT> wholly() {
        return t -> this.applyUnsafe(t._1(), t._2(), t._3(), t._4(), t._5(), t._6());
    }
    
    /**
     * Create a curry function of the this function.
     * 
     * @return  the curried function.
     */
    public default Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, Func1<INPUT4, Func1<INPUT5, Func1<INPUT6, OUTPUT>>>>>> curry() {
        return i1 -> i2 -> i3 -> i4 -> i5 -> i6 -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    /**
     * Flip the parameter order.
     * 
     * @return  the Func6 with parameter in a flipped order.
     */
    public default Func6<INPUT6, INPUT5, INPUT4, INPUT3, INPUT2, INPUT1, OUTPUT> flip() {
        return (i6, i5, i4, i3, i2, i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    //== Partially apply functions ==
    
    @SuppressWarnings("javadoc")
    public default Func0<OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return () -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> bind1(INPUT1 i1) {
        return (i2, i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> bind2(INPUT2 i2) {
        return (i1, i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> bind3(INPUT3 i3) {
        return (i1, i2, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> bind4(INPUT4 i4) {
        return (i1, i2, i3, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> bind5(INPUT5 i5) {
        return (i1, i2, i3, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> bind6(INPUT6 i6) {
        return (i1, i2, i3, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i2) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT3, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i3) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT4, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT5, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT2, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT3, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1, i3) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT4, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT5, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT3, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i2, i3) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT4, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i2, i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT5, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i2, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i2, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT4, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i3, i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT5, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i3, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i3, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT4, INPUT5, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT4, INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> bind(Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2, i3) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2, i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT5, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i2, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT6, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i2, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT5, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i3, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i3, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT4, INPUT5, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT4, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT5, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i2, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT5, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i2, i3, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i2, i3, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT4, INPUT5, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i2, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT4, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i2, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i2, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT3, INPUT4, INPUT5, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i3, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT3, INPUT4, INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i3, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT3, INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i3, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT4, INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> bind(Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5, INPUT6 i6) {
        return (i1, i2, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT5, OUTPUT> bind(Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5, INPUT6 i6) {
        return (i1, i2, i3, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT6, OUTPUT> bind(Absent a1, Absent a2, Absent a3, INPUT4 i4, INPUT5 i5, Absent a6) {
        return (i1, i2, i3, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT4, INPUT5, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i2, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT4, INPUT6, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i2, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT2, INPUT5, INPUT6, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i2, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT4, INPUT5, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i3, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT4, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i3, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT3, INPUT5, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i3, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT1, INPUT4, INPUT5, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i1, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i2, i3, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i2, i3, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i2, i3, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i2, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func4<INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return (i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> bind(Absent a1, Absent a2, Absent a3, Absent a4, Absent a5, INPUT6 i6) {
        return (i1, i2, i3, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> bind(Absent a1, Absent a2, Absent a3, Absent a4, INPUT5 i5, Absent a6) {
        return (i1, i2, i3, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> bind(Absent a1, Absent a2, Absent a3, INPUT4 i4, Absent a5, Absent a6) {
        return (i1, i2, i3, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, Absent a4, Absent a5, Absent a6) {
        return (i1, i2, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT1, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return (i1, i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    @SuppressWarnings("javadoc")
    public default Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, Absent a4, Absent a5, Absent a6) {
        return (i2, i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
    }
    
}
