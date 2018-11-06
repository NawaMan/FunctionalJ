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

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
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
    
    public default Result<OUTPUT> applySafely(INPUT input) {
        try {
            val output = applyUnsafe(input);
            return Result.of(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
    }
    
    public default Func1<INPUT, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
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
            throw Func.exceptionHandler.value().apply(e);
        }
    }
    
    /**
     * Applies this function to the given input value.
     *
     * @param input  the input function.
     * @return the function result.
     */
    public default OUTPUT applyTo(INPUT input) {
        return apply(input);
    }
    
    // TODO add memoize strategies.
    public default Func1<INPUT, OUTPUT> memoize() {
        return Func.cacheFor(this);
    }
    
    public default Func1<HasPromise<INPUT>, Promise<OUTPUT>> defer() {
        return input -> input.getPromise().map(this);
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
    
    public default Func1<INPUT, OUTPUT> elseUse(OUTPUT defaultValue) {
        return (input)->{
            val result = applySafely(input);
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func1<INPUT, OUTPUT> elseGet(Supplier<OUTPUT> defaultSupplier) {
        return (input)->{
            val result = applySafely(input);
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
    public default <FINAL> Func1<INPUT, FINAL> then(Function<? super OUTPUT, ? extends FINAL> after) {
        return input -> {
            OUTPUT out1 = this.applyUnsafe(input);
            FINAL  out2 = Func.applyUnsafe(after, out1);
            return out2;
        };
    }
    
    public default FuncUnit1<INPUT> ignoreResult() {
        return FuncUnit1.of((input1)->applyUnsafe(input1));
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
