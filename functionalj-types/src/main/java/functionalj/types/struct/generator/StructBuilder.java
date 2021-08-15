// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
// ----------------------------------------------------------------------------
// MIT License
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.StructGeneratorHelper.allArgConstructor;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateEquals;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateGetSchema;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateGetStructScheme;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateHashCode;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateSpecField;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateToString;
import static functionalj.types.struct.generator.StructGeneratorHelper.getterToField;
import static functionalj.types.struct.generator.StructGeneratorHelper.getterToGetterMethod;
import static functionalj.types.struct.generator.StructGeneratorHelper.getterToWitherMethods;
import static functionalj.types.struct.generator.StructGeneratorHelper.inheriitMethods;
import static functionalj.types.struct.generator.StructGeneratorHelper.noArgConstructor;
import static functionalj.types.struct.generator.StructGeneratorHelper.requiredOnlyConstructor;
import static functionalj.types.struct.generator.StructMapGeneratorHelper.generateFromMap;
import static functionalj.types.struct.generator.StructMapGeneratorHelper.generateToMap;
import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.types.struct.generator.model.Scope.INSTANCE;
import static functionalj.types.struct.generator.utils.listOf;
import static functionalj.types.struct.generator.utils.themAll;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.Generic;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenMethod;
import lombok.val;


/**
 * Builder for a data object.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class StructBuilder {
    
    public final SourceSpec sourceSpec;
    
    /**
     * Constructor for the data object builder.
     *
     * @param sourceSpec  the source spec.
     */
    public StructBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    /**
     * Build the data object.
     *
     * @return  the data object.
     **/
    public StructSpec build() {
        val extendeds    = new ArrayList<Type>();
        val implementeds = new ArrayList<Type>();
        
        if (sourceSpec.getConfigures().coupleWithDefinition) {
            if (sourceSpec.getIsClass() != null) {
                if (sourceSpec.isClass())
                     extendeds   .add(sourceSpec.toType());
                else implementeds.add(sourceSpec.toType());
            }
        }
        
        val istruct = Type.of(IStruct.class);
        implementeds.add(istruct);
        
        val targetType    = new Type(sourceSpec.getTargetPackageName(), sourceSpec.getTargetClassName());
        val targetGeneric = new Generic(targetType);
        val pipeable      = Core.Pipeable.type().withGenerics(asList(targetGeneric));
        implementeds.add(pipeable);
        
//        val serialize = FeatureSerialization.serializeType(input, type, configures);
//        implementeds.add(serialize);
        
        
        val pipeMethod = new GenMethod("__data", targetType, PUBLIC, INSTANCE, MODIFIABLE, emptyList(), emptyList(), false, false, ILines.line("return this;"), emptyList(), asList(Type.of(Exception.class)));
        
        val withMethodName = (Function<Getter, String>)(utils::withMethodName);
        val getters        = sourceSpec.getGetters();
        val getterFields   = getters.stream().map    (getter -> getterToField(sourceSpec, getter));
        val getterMethods  = getters.stream().map    (getter -> getterToGetterMethod(getter));
        val witherMethods  = getters.stream().flatMap(getter -> getterToWitherMethods(sourceSpec, withMethodName, getter));
        
        GenField theField = null;
        GenField eachField = null;
        GenClass lensClass = null;
        if (sourceSpec.getConfigures().generateLensClass) {
            val lensClassBuilder = new LensClassBuilder(sourceSpec);
            lensClass            = lensClassBuilder.build();
            theField             = lensClassBuilder.generateTheLensField();
            eachField            = lensClassBuilder.generateEachLensField();
        }
        GenClass builderClass = null;
        if (sourceSpec.getConfigures().generateBuilderClass) {
            builderClass = new BuilderGenerator(sourceSpec).build();
        }
        
        val specField = generateSpecField(sourceSpec);
        
        val toString = generateToString(sourceSpec, getters);
        val hashCode = generateHashCode(sourceSpec);
        val equals   = generateEquals(sourceSpec);
        
        val fields = listOf(
                    Stream.of(theField, eachField),
                    getterFields,
                    specField
                 );
        
        val fromMap = generateFromMap(sourceSpec);
        val toMap   = generateToMap  (sourceSpec);
        
        val getStructSchema = generateGetStructScheme(sourceSpec);
        val getSchema       = generateGetSchema(sourceSpec);
        
        val methods = Arrays.<Stream<GenMethod>>asList(
                    Stream.of(pipeMethod),
                    getterMethods,
                    witherMethods,
                    Stream.of(fromMap, toMap, getSchema, getStructSchema),
                    Stream.of(toString, hashCode, equals).filter(Objects::nonNull),
                    inheriitMethods(sourceSpec.getSpecName(), sourceSpec.getMethods())
                 ).stream()
                .flatMap(themAll())
                .collect(toList());
        
        val constructors = listOf(
                    noArgConstructor(sourceSpec),
                    requiredOnlyConstructor(sourceSpec),
                    allArgConstructor(sourceSpec)
                );
        
        val innerClasses = listOf(
                    lensClass,
                    builderClass
                );
        
        val dataObjSpec = new StructSpec(
                sourceSpec.getTargetClassName(),
                sourceSpec.getTargetPackageName(),
                sourceSpec.getSpecName(),
                sourceSpec.getPackageName(),
                extendeds, implementeds,
                constructors, fields, methods, innerClasses, emptyList());
        return dataObjSpec;
    }
    
}
