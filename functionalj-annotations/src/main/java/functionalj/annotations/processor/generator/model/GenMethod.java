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

import static functionalj.annotations.processor.generator.ILines.indent;
import static functionalj.annotations.processor.generator.ILines.line;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import static java.util.Collections.emptyList;

import functionalj.annotations.processor.generator.IGenerateDefinition;
import functionalj.annotations.processor.generator.ILines;
import functionalj.annotations.processor.generator.Type;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;

/**
 * Representation of generated method.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class GenMethod implements IGenerateDefinition {
    
    private Accessibility  accessibility;
    private Scope          scope;
    private Modifiability  modifiability;
    private Type           type;
    private String         name;
    private List<GenParam> params;
    private ILines         body;
    private List<Type>     usedTypes;
    private boolean        isVarAgrs;
    
    public GenMethod(Accessibility accessibility, Scope scope, Modifiability modifiability, Type type, String name,
            List<GenParam> params, ILines body) {
        this(accessibility, scope, modifiability, type, name, params, body, emptyList(), false);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        Set<Type> types = new HashSet<>();
        types.add(type);
        
        for (GenParam param : params) {
            Type paramType = param.getType();
            if (types.contains(paramType))
                continue;
            paramType
                .requiredTypes()
                .forEach(types::add);
            param
                .requiredTypes()
                .forEach(types::add);
        }
        return types.stream();
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        val paramDefs 
            = params.stream()
                .map(param -> param.toTerm(currentPackage))
                .collect(joining(", "));
        val paramDefsToText
            = isVarAgrs 
            ? paramDefs.replaceAll("([^ ]+)$", "... $1") 
            : paramDefs;
        val definition
                = ILines.oneLineOf(
                    accessibility, modifiability, scope,
                    type.simpleNameWithGeneric(""), name + "(" + paramDefsToText + ")",
                    "{");
        return ILines.flatenLines(
                line(definition),
                indent(body),
                line("}"));
    }
    
}