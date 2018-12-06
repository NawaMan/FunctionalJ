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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.ThrowFuncs;
import functionalj.io.IO;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.tuple.Tuple2;
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
    
    public static <I1, I2, O> Func2<I1, I2, O> of(Func2<I1, I2, O> func) {
        return func;
    }
    
    public static <I1, I2, O> Func2<I1, I2, O> from(BiFunction<I1, I2, O> func) {
        return func::apply;
    }
    
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;
    
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
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTranformer.value().apply(e);
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
    public default Func1<INPUT2, OUTPUT> applyTo(INPUT1 input1) {
        return input2 -> apply(input1, input2);
    }
    public default OUTPUT applyTo(INPUT1 input1, INPUT2 input2) {
        return apply(input1, input2);
    }
    public default Result<OUTPUT> applyTo(Result<INPUT1> input1, Result<INPUT2> input2) {
        return Result.ofResults(input1, input2, this);
    }
    public default Promise<OUTPUT> applyTo(HasPromise<INPUT1> input1, HasPromise<INPUT2> input2) {
        return Promise.from(input1, input2, this);
    }
    public default IO<OUTPUT> applyTo(IO<INPUT1> input1, IO<INPUT2> input2) {
        return IO.from(input1, input2, this);
    }
    public default StreamPlus<OUTPUT> applyTo(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    public default StreamPlus<OUTPUT> applyTo(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2, boolean requireBoth) {
        return input1.zipWith(input2, requireBoth, this);
    }
    public default FuncList<OUTPUT> applyTo(FuncList<INPUT1> input1, FuncList<INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    public default FuncList<OUTPUT> applyTo(FuncList<INPUT1> input1, FuncList<INPUT2> input2, boolean requireBoth) {
        return input1.zipWith(input2, requireBoth, this);
    }
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2, boolean requireBoth) {
        return input1.zipWith(input2, requireBoth, this);
    }
    public default Func0<OUTPUT> applyTo(Supplier<INPUT1> input1, Supplier<INPUT2> input2) {
        return ()->apply(input1.get(), input2.get());
    }
    public default <SOURCE> Func1<SOURCE, OUTPUT> applyTo(Func1<SOURCE, INPUT1> input1, Func1<SOURCE, INPUT2> input2) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            return applyTo(i1, i2);
        };
    }
    
    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2) {
        try {
            val output = applyUnsafe(input1, input2);
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
    public default <FINAL> Func2<INPUT1, INPUT2, FINAL> then(Function<? super OUTPUT, ? extends FINAL> after) {
        return (input1, input2) -> {
            OUTPUT out1 = this.applyUnsafe(input1, input2);
            FINAL  out2 = Func.applyUnsafe(after, out1);
            return out2;
        };
    }
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input1, input2)->{
            val result = applySafely(input1, input2);
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return (input1, input2)->{
            val result = applySafely(input1, input2);
            val value  = result.orElseGet(defaultSupplier);
            return value;
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            val result = applySafely(input1, input2);
            val value  = result.orApply(exceptionMapper);
            return value;
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func3<INPUT1, INPUT2, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            val result = applySafely(input1, input2);
            val value  = result.orApply(exception -> exceptionMapper.apply(input1, input2, exception));
            return value;
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func2<Tuple2<INPUT1, INPUT2>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            val result = applySafely(input1, input2);
            val value  = result.orApply(exception -> exceptionMapper.apply(Tuple2.of(input1, input2), exception));
            return value;
        };
    }
    
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, OUTPUT defaultValue) {
        return applySafely(input1, input2).orElse(defaultValue);
    }
    
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input1, input2).orGet(defaultSupplier);
    }
    
    public default Func2<INPUT1, INPUT2, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default Func2<INPUT1, INPUT2, Optional<OUTPUT>> optionally() {
        return (input1, input2) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input1, input2));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    public default Func2<INPUT1, INPUT2, Promise<OUTPUT>> async() {
        return (input1, input2) -> {
            val supplier = (Func0<OUTPUT>)()->{
                return this.applyUnsafe(input1, input2);
            };
            return DeferAction.from(supplier)
                    .start().getPromise();
        };
    }
    
    public default Func2<HasPromise<INPUT1>, HasPromise<INPUT2>, Promise<OUTPUT>> defer() {
        return (promise1, promise2) -> {
            return Promise.from(promise1, promise2, this);
        };
    }
    
    public default FuncUnit2<INPUT1, INPUT2> ignoreResult() {
        return FuncUnit2.of((input1, input2)->applyUnsafe(input1, input2));
    }
    
    public default Func1<Tuple2<INPUT1, INPUT2>, OUTPUT> wholly() {
        return t -> this.applyUnsafe(t._1(), t._2());
    }
    
    /**
     * Flip the parameter order.
     * 
     * @return  the Func2 with parameter in a flipped order.
     */
    public default Func2<INPUT2, INPUT1, OUTPUT> flip() {
        return (i2, i1) -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT2, Func1<INPUT1, OUTPUT>> elevate() {
        return (i2) -> (i1) -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2) {
        return (i1) -> this.applyUnsafe(i1, i2);
    }
    public default Func1<Result<INPUT1>, Result<OUTPUT>> elevateWith(Result<INPUT2> i2) {
        return (i1) -> this.applyTo(i1, i2);
    }
    public default Func1<HasPromise<INPUT1>, Promise<OUTPUT>> elevateWith(HasPromise<INPUT2> i2) {
        return (i1) -> this.applyTo(i1, i2);
    }
    
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> split1() {
        return (i1) -> (i2) -> this.applyUnsafe(i1, i2);
    }
    
    
    //== Partially apply functions ==
    
    @SuppressWarnings("javadoc")
    public default Func0<OUTPUT> bind(INPUT1 i1, INPUT2 i2) {
        return () -> this.applyUnsafe(i1, i2);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> bind1(INPUT1 i1) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> bind2(INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> bind(Absent a1, INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> bind(INPUT1 i1, Absent a2) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
    
}