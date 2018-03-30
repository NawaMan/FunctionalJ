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
package nawaman.functionalj.kinds;

import nawaman.functionalj.functions.Func1;

/**
 * Functor is data structure with one parameterized type and the map function.
 * 
 * <p>
 * {@code F[a].map(f[a->b]) -> F[b] }
 * </p>
 * 
 * @author NawaMan -- nawa@nawaman.net
 *
 * @param <TYPE>  the functor type.
 * @param <DATA>  the data type
 */
public interface Functor<TYPE,DATA> {
    
    /**
     * Map this functor to another functor using the mapper function.
     * 
     * @param <TARGET>  the target data type.
     * @param mapper    the mapper function.
     * @return          another functor.
     */
    public <TARGET> Functor<TYPE,TARGET> map(Func1<DATA, TARGET> mapper);
    
}