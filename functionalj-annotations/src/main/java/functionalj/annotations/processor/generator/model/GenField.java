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
package functionalj.annotations.processor.generator.model;

import static functionalj.annotations.processor.generator.ILines.oneLineOf;

import java.util.stream.Stream;

import functionalj.annotations.processor.generator.IGenerateDefinition;
import functionalj.annotations.processor.generator.ILines;
import functionalj.annotations.processor.generator.Type;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;

/**
 * Representation of a generated field.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
@EqualsAndHashCode(callSuper=false)
public class GenField implements IGenerateDefinition {
    
    private Accessibility accessibility;
    private Modifiability modifiability;
    private Scope         scope;
    private String        name;
    private Type          type;
    private String        defaultValue;
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.of(getType());
    }
    
    @Override
    public ILines toDefinition() {
        val def = oneLineOf(
                    accessibility, 
                    scope, 
                    modifiability, 
                    type.simpleNameWithGeneric(), 
                    name
                );
        
        val value = (defaultValue != null) ? " = " + defaultValue : "";
        return ()->Stream.of(def + value + ";");
    }
}