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
package functionalj.annotations.record.generator.model;

import functionalj.annotations.record.generator.IGenerateTerm;

/**
 * Accessibility of a class or elements.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@SuppressWarnings("javadoc")
public enum Accessibility implements IGenerateTerm {
    
    PUBLIC, PRIVATE, PROTECTED, PACKAGE;
    
    @Override
    public String toTerm(String currentPackage) {
        return (this == PACKAGE)
                ? null
                : name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return toTerm(null);
    }
}