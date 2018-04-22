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
 * Monadic Function of five parameters.
 *
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 * @param <INPUT4>  the forth input data type.
 * @param <INPUT5>  the fifth input data type.
 * @param <MONAD>   the monad type.
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface MFunc5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, MONAD, OUTPUT> extends Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, Monad<MONAD, OUTPUT>> {
    
    /**
     * Constructs a MFunc5 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <MONAD>   the monad type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, MONAD, OUTPUT> MFunc5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, MONAD, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, Monad<MONAD, OUTPUT>> function) {
        return (input1, input2, input3, input4, input5) -> {
            return function.apply(input1, input2, input3, input4, input5);
        };
    }
    
    
    /**
     * Chain this function to the given function (compose in the Monatic way).
     * 
     * @param  <FINAL>  the final result value.
     * @param  after    the function to be run after this function.
     * @return          the composed function.
     */
    public default <FINAL> MFunc5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, MONAD, FINAL> 
            chain(MFunc1<OUTPUT, MONAD, FINAL> after) {
        return (input1, input2, input3, input4, input5) -> {
            return this.apply(input1, input2, input3, input4, input5)
                     .flatMap(output ->{
                         return after.apply(output);
                     });
        };
    }
    
}