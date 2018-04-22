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

import static functionalj.annotations.processor.generator.ILines.flatenLines;
import static functionalj.annotations.processor.generator.ILines.indent;
import static functionalj.annotations.processor.generator.ILines.line;
import static functionalj.annotations.processor.generator.ILines.linesOf;
import static functionalj.annotations.processor.generator.ILines.oneLineOf;
import static functionalj.annotations.processor.generator.ILines.withSeparateIndentedSpace;
import static functionalj.annotations.processor.generator.model.utils.allLists;
import static functionalj.annotations.processor.generator.model.utils.themAll;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import functionalj.annotations.processor.generator.IGenerateDefinition;
import functionalj.annotations.processor.generator.ILines;
import functionalj.annotations.processor.generator.IRequireTypes;
import functionalj.annotations.processor.generator.Type;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;

/**
 * Representation of a generated class.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
@Accessors(fluent=true)
public class GenClass implements IGenerateDefinition {
    
    private final Accessibility accessibility;
    private final Scope         scope;
    private final Modifiability modifiability;
    
    private final Type       type;
    private final String     generic;
    private final List<Type> extendeds;
    private final List<Type> implementeds;
    private final List<GenConstructor> constructors;
    private final List<GenField>       fields;
    private final List<GenMethod>      methods;
    private final List<GenClass>      innerClasses;
    private final List<ILines>         mores;
    
    @Override
    public Stream<Type> requiredTypes() {
        return asList(
                        asList((IRequireTypes)(()->Stream.of(type))),
                        asList((IRequireTypes)(()->extendeds.stream())),
                        asList((IRequireTypes)(()->implementeds.stream())),
                        constructors,
                        fields,
                        mores
                ).stream()
                .flatMap(allLists())
                .map    (IRequireTypes.class::cast)
                .map    (IRequireTypes::requiredTypes)
                .flatMap(themAll());
    }
    
    @Override
    public ILines toDefinition() {
        val extendedList    = extendeds()   .stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        val implementedList = implementeds().stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        
        val fieldDefs       = fields()      .stream().map(GenField      ::toDefinition).collect(toList());
        val constructorDefs = constructors().stream().map(GenConstructor::toDefinition).collect(toList());
        val methodDefs      = methods()     .stream().map(GenMethod     ::toDefinition).collect(toList());
        val innerClassDefs  = innerClasses().stream().map(GenClass      ::toDefinition).collect(toList());
        val moreDefs        = mores()       .stream().collect(toList());
        
        val className = type().simpleNameWithGeneric();
        val firstLine
                = oneLineOf(
                    accessibility, scope, modifiability, "class", className,
                    utils.prefixWith(extendedList,    "extends "),
                    utils.prefixWith(implementedList, "implements "),
                    "{");
        
        val lastLine = "}";
        
        val componentLines
                = linesOf(
                    fieldDefs,
                    constructorDefs,
                    methodDefs,
                    innerClassDefs,
                    moreDefs);
        
        val lines = flatenLines(withSeparateIndentedSpace(
                line  (firstLine),
                indent(componentLines),
                line  (lastLine)));
        return lines;
    }
}