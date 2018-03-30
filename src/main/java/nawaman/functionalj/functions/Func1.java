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
package nawaman.functionalj.functions;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Function of one parameter.
 * 
 * @author NawaMan -- nawa@nawaman.net
 *
 * @param <INPUT>   the input data type.
 * @param <OUTPUT>  the output data type.
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
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> of(Func1<INPUT, OUTPUT> function) {
        return function;
    }
    
    
    /**
     * Applies this function to the given input value.
     *
     * @param input  the input function.
     * @return the function result.
     */
    public OUTPUT apply(INPUT input);
    
    
    /**
     * Compose this function to the given function.
     * 
     * @param  <FINAL>  the final result value.
     * @param  after    the function to be run after this function.
     * @return          the composed function.
     */
    public default <FINAL> Function<INPUT, FINAL> andThen(Func1<? super OUTPUT, ? extends FINAL> after) {
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
