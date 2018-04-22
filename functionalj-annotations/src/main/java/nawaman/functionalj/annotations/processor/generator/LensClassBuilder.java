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

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.annotations.processor.generator.model.Accessibility.PUBLIC;
import static nawaman.functionalj.annotations.processor.generator.model.Modifiability.FINAL;
import static nawaman.functionalj.annotations.processor.generator.model.Modifiability.MODIFIABLE;
import static nawaman.functionalj.annotations.processor.generator.model.Scope.INSTANCE;
import static nawaman.functionalj.annotations.processor.generator.model.Scope.STATIC;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import static java.util.Collections.emptyList;

import lombok.val;
import nawaman.functionalj.annotations.processor.generator.model.GenClass;
import nawaman.functionalj.annotations.processor.generator.model.GenConstructor;
import nawaman.functionalj.annotations.processor.generator.model.GenField;
import nawaman.functionalj.annotations.processor.generator.model.GenParam;
import nawaman.functionalj.lens.ObjectLensImpl;

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
     * @param sourceSpec
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
                .simpleName(dataObjClassName + "Lens")
                .packageName(sourceSpec.getPackageName())
                .generic("HOST")
                .build();
        val superType = Type.of(ObjectLensImpl.class);
        
        Stream<GenField> lensFields  = sourceSpec.getGetters().stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec));
        
        val lensSpecType = new Type.TypeBuilder().packageName("nawaman.functionalj.lens").simpleName("LensSpec").generic("HOST, " + dataObjClassName).build();
        val consParams   = asList(new GenParam("spec", lensSpecType));
        val consBody     = "super(spec);"; // This ignore the id for now.
        val constructors = new GenConstructor(PUBLIC, lensType.simpleName(), consParams, line(consBody));
        
        val lensClass = new GenClass(
                PUBLIC, STATIC, MODIFIABLE, lensType, "HOST",
                asList(superType.withGeneric("HOST, " + dataObjClassName)),
                emptyList(),
                asList(constructors),
                lensFields.collect(toList()),
                emptyList(),
                emptyList(),
                emptyList());
        return lensClass;
    }
    
    private GenField getterToLensField(Getter getter, String dataObjectClassName, SourceSpec sourceSpec) {
        val dataObjName = dataObjectClassName;
        val name        = getter.getName();
        val type        = getter.getType().declaredType();
        val lensType    = type.lensType().withGeneric("HOST");
        val withName    = utils.withMethodName(getter);
        val spec        = "spec->()->spec"; // If Custom lens -> spec->new Brand.BrandLens<>(spec)
        val value       = format("createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field       = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
}
