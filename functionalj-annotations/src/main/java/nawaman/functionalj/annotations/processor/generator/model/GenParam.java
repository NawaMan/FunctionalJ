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
package nawaman.functionalj.annotations.processor.generator.model;

import java.util.stream.Stream;

import lombok.Value;
import lombok.experimental.Wither;
import nawaman.functionalj.annotations.processor.generator.IGenerateTerm;
import nawaman.functionalj.annotations.processor.generator.Type;

/**
 * Representation of generated term.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
public class GenParam implements IGenerateTerm {
    private String name;
    private Type   type;
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.of(type);
    }
    
    @Override
    public String toTerm() {
        return type.simpleNameWithGeneric() + " " + name;
    }
}