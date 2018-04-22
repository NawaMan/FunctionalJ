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
package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.annotations.processor.generator.model.Accessibility.PRIVATE;
import static nawaman.functionalj.annotations.processor.generator.model.Accessibility.PUBLIC;
import static nawaman.functionalj.annotations.processor.generator.model.Modifiability.FINAL;
import static nawaman.functionalj.annotations.processor.generator.model.Modifiability.MODIFIABLE;
import static nawaman.functionalj.annotations.processor.generator.model.Scope.INSTANCE;
import static nawaman.functionalj.annotations.processor.generator.model.Scope.STATIC;
import static nawaman.functionalj.annotations.processor.generator.utils.themAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import static java.util.Collections.emptyList;

import lombok.val;
import nawaman.functionalj.annotations.processor.Core;
import nawaman.functionalj.annotations.processor.generator.model.GenConstructor;
import nawaman.functionalj.annotations.processor.generator.model.GenField;
import nawaman.functionalj.annotations.processor.generator.model.GenMethod;
import nawaman.functionalj.annotations.processor.generator.model.GenParam;

/**
 * Builder for a data object.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class DataObjectBuilder {
    
    private static final String POST_CONSTRUCT = "postConstruct";
    
    private SourceSpec sourceSpec;
    
    /**
     * Constructor for the data object builder.
     * 
     * @param sourceSpec  the source spec.
     */
    public DataObjectBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    /**
     * Build the data object.
     * 
     * @return  the data object.
     **/
    public DataObjectSpec build() {
        val extendeds    = new ArrayList<Type>();
        val implementeds = new ArrayList<Type>();
        
        if (sourceSpec.getConfigures().coupleWithDefinition) {
            if (sourceSpec.isClass())
                 extendeds   .add(sourceSpec.toType());
            else implementeds.add(sourceSpec.toType());
        }
        
        val withMethodName = (Function<Getter, String>)(utils::withMethodName);
        val ipostConstruct = Core.IPostConstruct.simpleName();
        val postConstructMethod = new GenMethod(
                PRIVATE, STATIC, MODIFIABLE,
                sourceSpec.getTargetType(), POST_CONSTRUCT,
                asList(new GenParam("object", sourceSpec.getTargetType())),
                ILines.line(
                asList(
                    "if (object instanceof " + ipostConstruct + ")",
                    "    ((" + ipostConstruct + ")object).postConstruct();",
                    "return object;"
                )));
        
        val getters = sourceSpec.getGetters();
        val getterFields  = getters.stream().map(getter -> getterToField(getter));
        val getterMethods = getters.stream().map(getter -> getterToGetterMethod(getter));
        val witherMethods = getters.stream().map(getter -> getterToWitherMethod(sourceSpec, withMethodName, getter));
        
        val noArgsConstructor = (Function<SourceSpec, GenConstructor>)((SourceSpec spec) ->{
            val name        = spec.getTargetClassName();
            val paramString = spec.getGetters().stream()
                    .map(getter -> getter.getType().defaultValue())
                    .map(String::valueOf)
                    .collect(joining(", "));
            val body = "this(" + paramString + ");";
            return new GenConstructor(PUBLIC, name, emptyList(), line(body));
        });
        val allArgsConstructor = (Function<SourceSpec, GenConstructor>)((SourceSpec spec) ->{
            val name = spec.getTargetClassName();
            List<GenParam> params = spec.getGetters().stream()
                    .map(getter -> {
                        val paramName = getter.getName();
                        val paramType = getter.getType();
                        return new GenParam(paramName, paramType);
                    })
                    .collect(toList());
            val body = spec.getGetters().stream()
                    .map(Getter::getName)
                    .map(arg ->String.format("this.%1$s = %1$s;", arg));
            return new GenConstructor(PUBLIC, name, params, ILines.of(()->body));
        });
        
        val dataObjClassName = sourceSpec.getTargetClassName();
        val lensType         = sourceSpec.getTargetType().lensType().withGeneric(dataObjClassName);
        val defaultValue     = String.format("new %1$s<>(%2$s.of(%3$s.class))", lensType.simpleName(), Core.LensSpec.simpleName(), dataObjClassName);
        val theField         = new GenField(PUBLIC, FINAL, STATIC, "the"+dataObjClassName, lensType, defaultValue);
        
        val fields = asList(
                    Stream.of(theField),
                    getterFields
                ).stream()
                .filter(Objects::nonNull)
                .flatMap(themAll())
                .collect(toList());
        val flatMap = Arrays.<Stream<GenMethod>>asList(
                    getterMethods,
                    witherMethods,
                    Stream.of(postConstructMethod)
                 );
        val methods = flatMap.stream().flatMap(themAll()).collect(toList());
        val constructors = new ArrayList<GenConstructor>();
        if (sourceSpec.getConfigures().generateNoArgConstructor)
            constructors.add((GenConstructor)noArgsConstructor.apply(sourceSpec));
        constructors.add((GenConstructor)allArgsConstructor.apply(sourceSpec));
        
        val lensClassBuilder = new LensClassBuilder(sourceSpec);
        val lensClass        = lensClassBuilder.build();
        
        val innerClasses = asList(lensClass);
        
        val dataObjSpec = new DataObjectSpec(
                sourceSpec.getTargetClassName(),
                sourceSpec.getTargetPackageName(),
                sourceSpec.getSpecClassName(),
                sourceSpec.getPackageName(),
                extendeds, implementeds,
                constructors, fields, methods, innerClasses, emptyList());
        return dataObjSpec;
    }
    
    private GenField getterToField(Getter getter) {
        // It should be good to convert this to tuple2 and apply to the method.
        val name  = getter.getName();
        val type  = getter.getType();
        val field = new GenField(PRIVATE, FINAL, INSTANCE, name, type, null);
        return field;
    }
    
    private GenMethod getterToWitherMethod(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name = withMethodName.apply(getter);
        val type = sourceSpec.getTargetType();
        val params = asList(new GenParam(getter.getName(), getter.getType()));
        val paramCall = sourceSpec.getGetters().stream().map(Getter::getName).collect(joining(", "));
        val returnLine = "return " + POST_CONSTRUCT + "(new " + sourceSpec.getTargetClassName() + "(" + paramCall + "));";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    
    private GenMethod getterToGetterMethod(Getter getter) {
        val name = getter.getName();
        val type = getter.getType();
        val params = new ArrayList<GenParam>();
        val body   = "return " + getter.getName() + ";";
        val method = new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(body));
        return method;
    }
    
}
