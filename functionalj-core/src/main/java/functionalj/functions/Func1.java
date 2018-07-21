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

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.types.result.ImmutableResult;
import functionalj.types.result.Result;
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

    public OUTPUT applyUnsafe(INPUT input) throws Exception;
    
    public default Result<OUTPUT> applySafely(INPUT input) {
        try {
            val output = applyUnsafe(input);
            return ImmutableResult.of(output);
        } catch (Exception exception) {
            return ImmutableResult.of(null, exception);
        }
    }
    
    public default Func1<INPUT, Result<OUTPUT>> safely() {
        return Func.from(this::applySafely);
    }
    
    // TODO add memoize.
    
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
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
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
            OUTPUT out1 = this.apply(input);
            FINAL  out2 = after.apply(out1);
            return out2;
        };
    }
    
    /**
     * Create a curry function (a supplier) of the this function.
     * 
     * @param   input  the input value.
     * @return         the Supplier.
     */
    public default Supplier<OUTPUT> curry(INPUT input) {
        return () -> {
            return this.apply(input);
        };
    }
    
}
