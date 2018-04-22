//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
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
package functionalj.annotations.processor.generator;

import java.util.stream.Stream;

/**
 * Classes implementing this interface require other types when generated into code.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public interface IRequireTypes {
    
    /**
     * Returns the stream of types that is required.
     * 
     * @return  the return types.
     */
    public Stream<Type> requiredTypes();
    
}