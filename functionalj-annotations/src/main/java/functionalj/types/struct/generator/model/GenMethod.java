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
package functionalj.annotations.struct.generator.model;

import static functionalj.annotations.struct.generator.ILines.indent;
import static functionalj.annotations.struct.generator.ILines.line;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.annotations.struct.generator.IGenerateDefinition;
import functionalj.annotations.struct.generator.ILines;
import functionalj.annotations.struct.generator.Type;
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
    private List<Type>     exceptions;
    private boolean        isVarAgrs;
    
    /**
     * Constructor a GenMethod.
     * @param accessibility  the method  accessibility.
     * @param scope          the method score.
     * @param modifiability  the method score.
     * @param type           the method return type.
     * @param name           the name of the method.
     * @param params         the parameters if the method.
     * @param body           the method body.
     */
    public GenMethod(Accessibility accessibility, Scope scope, Modifiability modifiability, Type type, String name,
            List<GenParam> params, ILines body) {
        this(accessibility, scope, modifiability, type, name, params, body, emptyList(), emptyList(), false);
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
        @SuppressWarnings({ "rawtypes", "unchecked" })
        val streams = Stream.of(
                    types.stream(),
                    (usedTypes  != null) ? usedTypes .stream() : (Stream<Type>)(Stream)Stream.empty(),
                    (exceptions != null) ? exceptions.stream() : (Stream<Type>)(Stream)Stream.empty()
                );
        return streams.flatMap(s -> s);
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
        val throwing = (exceptions == null || exceptions.isEmpty())
                     ? ""
                     : " throws " + exceptions.stream().map(type -> type.simpleName()).collect(Collectors.joining(", ")) + " ";
        val definition
                = ILines.oneLineOf(
                    accessibility, modifiability, scope,
                    type.simpleNameWithGeneric(""), name + "(" + paramDefsToText + ")",
                    throwing,
                    "{");
        return ILines.flatenLines(
                line(definition),
                indent(body),
                line("}"));
    }
    
}