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
package functionalj.types.struct.generator;

/**
 * Classes implementing this interface as a definition in code.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public interface IGenerateDefinition extends IRequireTypes {
    
    /**
     * Generate definition.
     * 
     * @param currentPackage  the current package.
     * @return  the definition.
     */
    public ILines toDefinition(String currentPackage);
    
}
