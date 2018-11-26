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
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import lombok.val;

/**
 * Function of one parameter.
 * 
 * @param <INPUT>   the input data type.
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func1<INPUT, OUTPUT> extends Function<INPUT, OUTPUT> {
    
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
    
    public static <I1, O> Func1<I1, O> from(Function<I1, O> func) {
        return func::apply;
    }
    
    
    public OUTPUT applyUnsafe(INPUT input) throws Exception;
    
    
    /**
     * Applies this function to the given input value.
     *
     * @param input  the input function.
     * @return the function result.
     */
    public default OUTPUT apply(INPUT input) {
        try {
            return applyUnsafe(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTranformer.value().apply(e);
        }
    }
    
    public default OUTPUT applyToNull() {
        return apply((INPUT)null);
    }
    public default OUTPUT applyTo(INPUT input) {
        return apply(input);
    }
    public default Result<OUTPUT> applyTo(Result<INPUT> input) {
        return input.map(this);
    }
    public default Promise<OUTPUT> applyTo(HasPromise<INPUT> input) {
        return input.getPromise().map(this);
    }
    public default StreamPlus<OUTPUT> applyTo(StreamPlus<INPUT> input) {
        return input.map(this);
    }
    public default FuncList<OUTPUT> applyTo(FuncList<INPUT> input) {
        return input.map(this);
    }
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(FuncMap<KEY, INPUT> input) {
        return input.map(this);
    }
    public default Func0<OUTPUT> applyTo(Supplier<INPUT> input) {
        return ()->apply(input.get());
    }
    public default <T> Func1<T, OUTPUT> applyTo(Function<T, INPUT> input) {
        return t -> apply(input.apply(t));
    }
    
    public default Result<OUTPUT> applySafely(INPUT input) {
        try {
            val output = applyUnsafe(input);
            return Result.of(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
    }
    
    // TODO add memoize strategies.
    public default Func1<INPUT, OUTPUT> memoize() {
        return Func.cacheFor(this);
    }
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     * 
     * @param  <FINAL>  the final result value.
     * @param  after    the function to be run after this function.
     * @return          the composed function.
     */
    public default <FINAL> Func1<INPUT, FINAL> then(Function<? super OUTPUT, ? extends FINAL> after) {
        return input -> {
            OUTPUT out1 = this.applyUnsafe(input);
            FINAL  out2 = Func.applyUnsafe(after, out1);
            return out2;
        };
    }
    
    public default Func1<INPUT, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input)->{
            val result = applySafely(input);
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func1<INPUT, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return (input)->{
            val result = applySafely(input);
            val value  = result.orElseGet(defaultSupplier);
            return value;
        };
    }
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input)->{
            val result = applySafely(input);
            val value  = result.orApply(exceptionMapper);
            return value;
        };
    }
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Func2<INPUT, Exception, OUTPUT> exceptionMapper) {
        return (input)->{
            val result = applySafely(input);
            val value  = result.orApply(exception -> exceptionMapper.apply(input, exception));
            return value;
        };
    }
    
    public default OUTPUT orElse(INPUT input, OUTPUT defaultValue) {
        return applySafely(input).orElse(defaultValue);
    }
    
    public default OUTPUT orGet(INPUT input, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input).orGet(defaultSupplier);
    }
    
    public default Func1<INPUT, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default Func1<INPUT, Optional<OUTPUT>> optionally() {
        return (input) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    public default Func1<INPUT, Promise<OUTPUT>> async() {
        return input -> {
            val supplier = (Func0<OUTPUT>)()->{
                return this.applyUnsafe(input);
            };
            return DeferAction.from(supplier)
                    .start().getPromise();
        };
    }
    public default Func1<HasPromise<INPUT>, Promise<OUTPUT>> defer() {
        return input -> input.getPromise().map(this);
    }
    
    public default FuncUnit1<INPUT> ignoreResult() {
        return FuncUnit1.of((input1)->applyUnsafe(input1));
    }
    
    public default Predicate<INPUT> toPredicate() {
        return toPredicate(Boolean.TRUE::equals);
    }
    public default Predicate<INPUT> toPredicate(Func1<OUTPUT, Boolean> converter) {
        val converted = this.then(converter);
        return Func.toPredicate(converted);
    }
    
    /**
     * Create a bind function (a supplier) of the this function.
     * 
     * @param   input  the input value.
     * @return         the Supplier.
     */
    public default Func0<OUTPUT> bind(INPUT input) {
        return () -> {
            return this.applyUnsafe(input);
        };
    }
    
}
