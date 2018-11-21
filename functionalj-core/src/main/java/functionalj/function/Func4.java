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
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.tuple.Tuple4;
import lombok.val;

/**
 * Function of four parameters.
 * 
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 * @param <INPUT4>  the third input data type.
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> {

    public static <I1, I2, I3, I4, O> Func4<I1, I2, I3, I4, O> of(Func4<I1, I2, I3, I4, O> func) {
        return func;
    }
    
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) throws Exception;
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input1  the first input.
     * @param  input2  the second input.
     * @param  input3  the third input.
     * @param  input4  the forth input.
     * @return         the function result.
     */
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) {
        try {
            return applyUnsafe(input1, input2, input3, input4);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTranformer.value().apply(e);
        }
    }
    
    public default Result<OUTPUT> apply(Result<INPUT1> input1, Result<INPUT2> input2, Result<INPUT3> input3, Result<INPUT4> input4) {
        return Result.ofResults(input1, input2, input3, input4, this);
    }
    public default Promise<OUTPUT> apply(HasPromise<INPUT1> input1, HasPromise<INPUT2> input2, HasPromise<INPUT3> input3, HasPromise<INPUT4> input4) {
        return Promise.from(input1, input2, input3, input4, this);
    }
    
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT applyTo(Tuple4<INPUT1, INPUT2, INPUT3, INPUT4> input) {
        return apply(input._1(), input._2(), input._3(), input._4());
    }
    
    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) {
        try {
            val output = applyUnsafe(input1, input2, input3, input4);
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
    public default <FINAL> Func4<INPUT1, INPUT2, INPUT3, INPUT4, FINAL> then(Function<? super OUTPUT, ? extends FINAL> after) {
        return (input1, input2, input3, input4) -> {
            OUTPUT out1 = this.applyUnsafe(input1, input2, input3, input4);
            FINAL  out2 = Func.applyUnsafe(after, out1);
            return out2;
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input1, input2, input3, input4)->{
            val result = applySafely(input1, input2, input3, input4);
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return (input1, input2, input3, input4)->{
            val result = applySafely(input1, input2, input3, input4);
            val value  = result.orElseGet(defaultSupplier);
            return value;
        };
    }
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4)->{
            val result = applySafely(input1, input2, input3, input4);
            val value  = result.orApply(exceptionMapper);
            return value;
        };
    }
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Func5<INPUT1, INPUT2, INPUT3, INPUT4, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4)->{
            val result = applySafely(input1, input2, input3, input4);
            val value  = result.orApply(exception -> exceptionMapper.apply(input1, input2, input3, input4, exception));
            return value;
        };
    }
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Func2<Tuple4<INPUT1, INPUT2, INPUT3, INPUT4>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4)->{
            val result = applySafely(input1, input2, input3, input4);
            val value  = result.orApply(exception -> exceptionMapper.apply(Tuple4.of(input1, input2, input3, input4), exception));
            return value;
        };
    }
    
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, OUTPUT defaultValue) {
        return applySafely(input1, input2, input3, input4).orElse(defaultValue);
    }
    
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input1, input2, input3, input4).orGet(defaultSupplier);
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Optional<OUTPUT>> optionally() {
        return (input1, input2, input3, input4) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input1, input2, input3, input4));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, HasPromise<OUTPUT>> async() {
        return (input1, input2, input3, input4) -> {
            val supplier = (Func0<OUTPUT>)()->{
                return this.applyUnsafe(input1, input2, input3, input4);
            };
            return DeferAction.from(supplier)
                    .start().getPromise();
        };
    }
    
    public default Func4<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, HasPromise<INPUT4>, Promise<OUTPUT>> defer() {
        return (promise1, promise2, promise3, promise4) -> {
            return Promise.from(promise1, promise2, promise3, promise4, this);
        };
    }
    
    public default Func1<Tuple4<INPUT1, INPUT2, INPUT3, INPUT4>, OUTPUT> wholly() {
        return t -> this.applyUnsafe(t._1(), t._2(), t._3(), t._4());
    }
    
    /**
     * Create a curry function of the this function.
     * 
     * @return  the curried function.
     */
    public default Func1<INPUT1, Func1<INPUT2, Func1<INPUT3, Func1<INPUT4, OUTPUT>>>> curry() {
        return i1 -> i2 -> i3 -> i4 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    /**
     * Flip the parameter order.
     * 
     * @return  the Func4 with parameter in a flipped order.
     */
    public default Func4<INPUT4, INPUT3, INPUT2, INPUT1, OUTPUT> flip() {
        return (i4, i3, i2, i1) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return (i1) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    //== Partially apply functions ==
    
    @SuppressWarnings("javadoc")
    public default Func0<OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return () -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> bind1(INPUT1 i1) {
        return (i2,i3,i4) -> this.applyUnsafe(i1, i2, i3,i4);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> bind2(INPUT2 i2) {
        return (i1,i3,i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> bind3(INPUT3 i3) {
        return (i1,i2,i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> bind4(INPUT4 i4) {
        return (i1,i2,i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return i1 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return i2 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT3, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return i3 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT4, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return i4 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT2, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return (i1, i2) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT3, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return (i1, i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT1, INPUT4, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return (i1, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT3, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4) {
        return (i2, i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT2, INPUT4, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4) {
        return (i2, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func2<INPUT3, INPUT4, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4) {
        return (i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> bind(Absent a1, Absent a2, Absent a3, INPUT4 i4) {
        return (i1, i2, i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, Absent a4) {
        return (i1, i2, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, Absent a4) {
        return (i1, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    @SuppressWarnings("javadoc")
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, Absent a4) {
        return (i2, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
}