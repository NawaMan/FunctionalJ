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

import static functionalj.annotations.processor.Core.ObjectLensImpl;
import static functionalj.annotations.processor.generator.ILines.line;
import static functionalj.annotations.processor.generator.model.Accessibility.PUBLIC;
import static functionalj.annotations.processor.generator.model.Modifiability.FINAL;
import static functionalj.annotations.processor.generator.model.Modifiability.MODIFIABLE;
import static functionalj.annotations.processor.generator.model.Scope.INSTANCE;
import static functionalj.annotations.processor.generator.model.Scope.STATIC;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import static java.util.Collections.emptyList;

import functionalj.annotations.processor.Core;
import functionalj.annotations.processor.generator.model.GenClass;
import functionalj.annotations.processor.generator.model.GenConstructor;
import functionalj.annotations.processor.generator.model.GenField;
import functionalj.annotations.processor.generator.model.GenParam;
import lombok.val;

/**
 * Builder for lens class.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class LensClassBuilder {
    
    private SourceSpec sourceSpec;
    
    /**
     * Construct a lens class builder.
     * 
     * @param sourceSpec  the source spec.
     */
    public LensClassBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    /**
     * Build the class.
     * 
     * @return  the generated class.
     */
    public GenClass build() {
        val dataObjClassName = sourceSpec.getTargetClassName();
        val lensType = new Type.TypeBuilder()
                .encloseName(dataObjClassName)
                .simpleName (dataObjClassName + "Lens")
                .packageName(sourceSpec.getPackageName())
                .generics   (asList(new Type("HOST", null)))
                .build();
        val superType = ObjectLensImpl.type();
        
        Stream<GenField> lensFields  = sourceSpec.getGetters().stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec));
        
        val lensSpecType = new Type.TypeBuilder()
                .packageName(Core.LensSpec.type().packageName())
                .simpleName (Core.LensSpec.type().simpleName())
                .generics   (asList(new Type("HOST", null), new Type(dataObjClassName, null)))
                .build();
        
        val consParams  = asList(new GenParam("spec", lensSpecType));
        val consBody    = "super(spec);"; // This ignore the id for now.
        val constructor = new GenConstructor(PUBLIC, lensType.simpleName(), consParams, line(consBody));
        
        val lensClass = new GenClass(
                PUBLIC, STATIC, MODIFIABLE, lensType, "HOST",
                asList   (superType.withGenerics(asList(new Type("HOST", null), new Type(dataObjClassName, null)))),
                emptyList(),
                asList   (constructor),
                lensFields.collect(toList()),
                emptyList(),
                emptyList(),
                emptyList());
        return lensClass;
    }
    
    /**
     * Generate the Lens field ("theXxx" field).
     * 
     * @return the generated field.
     */
    public GenField generateTheLensField() {
        val dataObjClassName = sourceSpec.getTargetClassName();
        val lensType         = sourceSpec.getTargetType().lensType().withGenerics(asList(new Type(dataObjClassName, null)));
        val defaultValue     = String.format("new %1$s<>(%2$s.of(%3$s.class))", lensType.simpleName(), Core.LensSpec.simpleName(), dataObjClassName);
        val theField         = new GenField(PUBLIC, FINAL, STATIC, "the"+dataObjClassName, lensType, defaultValue);
        return theField;
    }
    
    private GenField getterToLensField(Getter getter, String dataObjectClassName, SourceSpec sourceSpec) {
        val dataObjName  = dataObjectClassName;
        val name         = getter.getName();
        val type         = getter.getType().declaredType();
        val withName     = utils.withMethodName(getter);
        
        GenField field;
        if (type.isList()) {
            field = createGenListLensField(dataObjName, name, type, withName);
        } else if (type.isMap()) {
            field = createGenMapLensField(dataObjName, name, type, withName);
        } else if (type.isNullable()) {
            field = createGenNullableLensField(dataObjName, name, type, withName);
        } else if (type.isOptional()) {
            field = createGenOptionalLensField(dataObjName, name, type, withName);
        } else if (type.isMayBe()) {
            field = createGenMayBeLensField(dataObjName, name, type, withName);
        } else {
            field = createLensField(dataObjName, name, type, withName);
        }
        return field;
    }
    
    // TODO - DRY this.

    private GenField createLensField(String dataObjName, String name, Type type, String withName) {
        val lensType     = type.lensType().withGenerics(asList(new Type("HOST", null)));
        val isCustomLens = type.lensType().isCustomLens();
        val spec         = isCustomLens ? lensType.simpleName() + "::new" : lensType.simpleName() + "::of";
        val value        = format("createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }

    private GenField createGenListLensField(String dataObjName, String name, Type type, String withName) {
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(new Type("HOST", null), paramType, paramType.lensType().withGenerics(asList(new Type("HOST", null))));
        val lensType     = type.lensType().withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType().isCustomLens();
        val spec         = isCustomLens ? paramType.lensType().simpleName() + "::new" : "spec->()->spec";
        val value        = format("createSubListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenMapLensField(String dataObjName, String name, Type type, String withName) {
        val keyType      = type.generics().get(0);
        val valueType    = type.generics().get(1);
        val lensGenerics = asList(new Type("HOST", null), 
                                keyType, valueType,
                                keyType  .lensType().withGenerics(asList(new Type("HOST", null))),
                                valueType.lensType().withGenerics(asList(new Type("HOST", null))));
        val lensType  = type.lensType().withGenerics(lensGenerics);
        val keySpec   = keyType  .lensType().isCustomLens() ? keyType  .lensType().simpleName() + "::new" : "spec->()->spec";
        val valueSpec = valueType.lensType().isCustomLens() ? valueType.lensType().simpleName() + "::new" : "spec->()->spec";
        val value     = format("createSubMapLens(%1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        val field     = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenNullableLensField(String dataObjName, String name, Type type, String withName) {
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(new Type("HOST", null), paramType, paramType.lensType().withGenerics(asList(new Type("HOST", null))));
        val lensType     = type.lensType().withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType().isCustomLens();
        val spec         = isCustomLens ? paramType.lensType().simpleName() + "::new" : "spec->()->spec";
        val value        = format("createSubNullableLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenOptionalLensField(String dataObjName, String name, Type type, String withName) {
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(new Type("HOST", null), paramType, paramType.lensType().withGenerics(asList(new Type("HOST", null))));
        val lensType     = type.lensType().withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType().isCustomLens();
        val spec         = isCustomLens ? paramType.lensType().simpleName() + "::new" : "spec->()->spec";
        val value        = format("createSubOptionalLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenMayBeLensField(String dataObjName, String name, Type type, String withName) {
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(new Type("HOST", null), paramType, paramType.lensType().withGenerics(asList(new Type("HOST", null))));
        val lensType     = type.lensType().withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType().isCustomLens();
        val spec         = isCustomLens ? paramType.lensType().simpleName() + "::new" : "spec->()->spec";
        val value        = format("createSubMayBeLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
}
