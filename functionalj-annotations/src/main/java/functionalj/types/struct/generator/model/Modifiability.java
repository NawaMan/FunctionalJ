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
package functionalj.types.struct.generator.model;

import functionalj.types.struct.generator.IGenerateTerm;

/**
 * Representation of modifier.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@SuppressWarnings("javadoc")
public enum Modifiability implements IGenerateTerm {
    
    FINAL, MODIFIABLE;
    
    @Override
    public String toTerm(String currentPackage) {
        return (this == MODIFIABLE)
                ? null
                : name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return toTerm(null);
    }
    
}