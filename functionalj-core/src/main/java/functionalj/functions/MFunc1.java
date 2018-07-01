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

import functionalj.kinds.Monad;


/**
 * Monadic Function of two parameters.
 * 
 * @param <INPUT>   the input data type.
 * @param <MONAD>   the monad type.
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface MFunc1<INPUT, MONAD, OUTPUT> extends Func1<INPUT, Monad<MONAD, OUTPUT>> {
    
    /**
     * Constructs a MFunc6 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, MONAD, OUTPUT> MFunc1<INPUT, MONAD, OUTPUT> of(Func1<INPUT, Monad<MONAD, OUTPUT>> function) {
        return input -> {
            return function.apply(input);
        };
    }
    
    
    /**
     * Chain this function to the given function (compose in the Monatic way).
     * 
     * @param  <FINAL>  the final result value.
     * @param  after    the function to be run after this function.
     * @return          the composed function.
     */
    public default <FINAL> MFunc1<INPUT, MONAD, FINAL> 
            chain(MFunc1<OUTPUT, MONAD, FINAL> after) {
        return input -> {
            return this.apply(input)
                     ._flatMap(output ->{
                         return after.apply(output);
                     });
        };
    }
    
}