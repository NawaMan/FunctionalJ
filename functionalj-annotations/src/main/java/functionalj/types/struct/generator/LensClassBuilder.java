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
package functionalj.annotations.struct.generator;

import static functionalj.annotations.struct.Core.ObjectLensImpl;
import static functionalj.annotations.struct.generator.ILines.line;
import static functionalj.annotations.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.annotations.struct.generator.model.Modifiability.FINAL;
import static functionalj.annotations.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.annotations.struct.generator.model.Scope.INSTANCE;
import static functionalj.annotations.struct.generator.model.Scope.STATIC;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.stream.Stream;

import functionalj.annotations.struct.Core;
import functionalj.annotations.struct.generator.model.GenClass;
import functionalj.annotations.struct.generator.model.GenConstructor;
import functionalj.annotations.struct.generator.model.GenField;
import functionalj.annotations.struct.generator.model.GenParam;
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
        
        // TODO - methods to access.
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
        val packageName      = sourceSpec.getTargetPackageName();
        val encloseName      = sourceSpec.getEncloseName();
        val lensType         = sourceSpec.getTargetType().lensType(packageName, encloseName, null).withGenerics(asList(new Type(dataObjClassName, null)));
        val defaultValue     = String.format("new %1$s<>(%2$s.of(%3$s.class))", lensType.simpleName(), Core.LensSpec.simpleName(), dataObjClassName);
        val theField         = new GenField(PUBLIC, FINAL, STATIC, "the"+dataObjClassName, lensType, defaultValue);
        return theField;
    }
    
    private GenField getterToLensField(Getter getter, String recordClassName, SourceSpec sourceSpec) {
        val recordName = recordClassName;
        val name       = getter.getName();
        val type       = getter.getType().declaredType();
        val withName   = utils.withMethodName(getter);
        
        GenField field;
        if (type.isList()) {
            field = createGenListLensField(recordName, name, type, withName);
        } else if (type.isMap()) {
            field = createGenMapLensField(recordName, name, type, withName);
        } else if (type.isFuncList()) {
            field = createGenFuncListLensField(recordName, name, type, withName);
        } else if (type.isFuncMap()) {
            field = createGenFuncMapLensField(recordName, name, type, withName);
        } else if (type.isNullable()) {
            field = createGenNullableLensField(recordName, name, type, withName);
        } else if (type.isOptional()) {
            field = createGenOptionalLensField(recordName, name, type, withName);
        } else {
            field = createLensField(recordName, name, type, withName);
        }
        return field;
    }
    
    // TODO - DRY this.
    
    private GenField createLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val lensType     = type.lensType(packageName, encloseName, withLens);
        val lensTypeDef  = getLensTypeDef(type, lensType);
        val isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? lensTypeDef.simpleName() + "::new" : lensTypeDef.simpleName() + "::of";
        val value        = format("createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensTypeDef, value);
        return field;
    }
    
    private Type getLensTypeDef(Type orgType, Type lensType) {
        if (lensType.fullName().equals(Core.ObjectLens.type().fullName()))
            return lensType.withGenerics(asList(new Type("HOST", null), orgType));
        return lensType.withGenerics(asList(new Type("HOST", null)));
    }
    
    private GenField createGenListLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(
                                new Type("HOST", null), 
                                paramType, 
                                getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens)));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenMapLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val keyType      = type.generics().get(0);
        val valueType    = type.generics().get(1);
        val lensGenerics = asList(new Type("HOST", null), 
                                keyType, valueType,
                                getLensTypeDef(keyType,   keyType.lensType(packageName, encloseName, withLens)),
                                getLensTypeDef(valueType, valueType.lensType(packageName, encloseName, withLens)));
        val lensType  = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val keySpec   = keyType  .lensType(packageName, encloseName, withLens).isCustomLens() ? keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value     = format("createSubMapLens(%1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        val field     = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncListLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(
                            new Type("HOST", null), 
                            paramType, 
                            getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens)));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubFuncListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncMapLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val keyType      = type.generics().get(0);
        val valueType    = type.generics().get(1);
        val lensGenerics = asList(new Type("HOST", null), 
                                keyType, valueType,
                                getLensTypeDef(keyType,   keyType.lensType(packageName, encloseName, withLens)),
                                getLensTypeDef(valueType, valueType.lensType(packageName, encloseName, withLens)));
        val lensType  = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val keySpec   = keyType  .lensType(packageName, encloseName, withLens).isCustomLens() ? keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value     = format("createSubFuncMapLens(%1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        val field     = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenNullableLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(new Type("HOST", null), paramType, getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens)));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubNullableLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenOptionalLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val paramType    = type.generics().get(0);
        val lensGenerics = asList(new Type("HOST", null), paramType, getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens)));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubOptionalLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
}
