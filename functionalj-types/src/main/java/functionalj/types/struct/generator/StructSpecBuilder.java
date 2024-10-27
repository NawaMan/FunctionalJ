// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static functionalj.types.struct.generator.StructGeneratorHelper.generatePipeMethods;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateSpecField;
import static functionalj.types.struct.generator.StructGeneratorHelper.generateToString;
import static functionalj.types.struct.generator.StructGeneratorHelper.getterToField;
import static functionalj.types.struct.generator.StructGeneratorHelper.getterToGetterMethod;
import static functionalj.types.struct.generator.StructGeneratorHelper.getterToWitherMethods;
import static functionalj.types.struct.generator.StructGeneratorHelper.inheritMethods;
import static functionalj.types.struct.generator.StructGeneratorHelper.noArgConstructor;
import static functionalj.types.struct.generator.StructGeneratorHelper.requiredOnlyConstructor;
import static functionalj.types.struct.generator.StructMapGeneratorHelper.generateFromMap;
import static functionalj.types.struct.generator.StructMapGeneratorHelper.generateToMap;
import static functionalj.types.struct.generator.utils.listOf;
import static functionalj.types.struct.generator.utils.themAll;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.Core;
import functionalj.types.Generic;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenMethod;

/**
 * Builder for a data object.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class StructSpecBuilder {
    
    static final Function<Getter, String> withMethodName = (Function<Getter, String>) (utils::withMethodName);
    
    public final SourceSpec sourceSpec;
    
    /**
     * Constructor for the data object builder.
     *
     * @param sourceSpec  the source spec.
     */
    public StructSpecBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    /**
     * Build the data object.
     *
     * @return  the data object.
     */
    public StructSpec build() {
        List<Type> extendeds = new ArrayList<Type>();
        List<Type> implementeds = new ArrayList<Type>();
        if (sourceSpec.getConfigures().coupleWithDefinition) {
            if ((sourceSpec.isClass() != null) && sourceSpec.isClass()) {
                extendeds.add(sourceSpec.toType());
                
            } else if ((sourceSpec.isInterface() != null) && sourceSpec.isInterface()) {
                implementeds.add(sourceSpec.toType());
                
            }
        }
        
        Type istruct = Type.of(IStruct.class);
        implementeds.add(istruct);
        
        Type    targetType    = new Type(sourceSpec.getTargetPackageName(), sourceSpec.getTargetClassName());
        Generic targetGeneric = new Generic(targetType);
        Type    pipeable      = Core.Pipeable.type().withGenerics(asList(targetGeneric));
        implementeds.add(pipeable);
        
        // val serialize = FeatureSerialization.serializeType(input, type, configures);
        // implementeds.add(serialize);
        List<Getter>             getters        = sourceSpec.getGetters();
        Stream<GenField>         getterFields   = getters.stream().map(getter -> getterToField(sourceSpec, getter));
        GenField                 theField       = null;
        GenField                 eachField      = null;
        GenClass                 lensClass      = null;
        if (sourceSpec.getConfigures().generateLensClass) {
            LensClassBuilder lensClassBuilder = new LensClassBuilder(sourceSpec);
            lensClass = lensClassBuilder.build();
            theField  = lensClassBuilder.generateTheLensField();
            eachField = lensClassBuilder.generateEachLensField();
        }
        GenClass builderClass = null;
        if (sourceSpec.getConfigures().generateBuilderClass) {
            builderClass = new BuilderGenerator(sourceSpec).build();
        }
        Stream<GenField>     specField    = generateSpecField(sourceSpec);
        List<GenField>       fields       = listOf(Stream.of(theField, eachField), getterFields, specField);
        List<GenMethod>      methods      = generateMethods(targetType);
        List<GenConstructor> constructors = listOf(noArgConstructor(sourceSpec), requiredOnlyConstructor(sourceSpec), allArgConstructor(sourceSpec));
        List<GenClass>       innerClasses = listOf(lensClass, builderClass);
        StructSpec           dataObjSpec  = new StructSpec(sourceSpec.getTargetClassName(), sourceSpec.getTargetPackageName(), sourceSpec.getSpecName(), sourceSpec.getPackageName(), extendeds, implementeds, constructors, fields, methods, innerClasses, emptyList());
        return dataObjSpec;
    }

    private List<GenMethod> generateMethods(Type targetType) {
        List<Getter>      getters        = sourceSpec.getGetters();
        Stream<GenMethod> pipeMethod     = generatePipeMethods(targetType);
        Stream<GenMethod> getterMethods  = generateGetterMethods(getters);
        Stream<GenMethod> witherMethods  = generateWitherMethods(getters);
        Stream<GenMethod> schemaMethods  = generateSchemaMethods();
        Stream<GenMethod> objectMethods  = generateObjectMethods(getters);
        Stream<GenMethod> inheritMethods = inheritMethods(sourceSpec);
        
        List<GenMethod> methods = asList(
                    pipeMethod, 
                    getterMethods,
                    witherMethods,
                    schemaMethods,
                    objectMethods,
                    inheritMethods
                )
                .stream()
                .flatMap(themAll())
                .filter(Objects::nonNull)
                .collect(toList());
        return methods;
    }
    
    private Stream<GenMethod> generateGetterMethods(List<Getter> getters) {
        return getters.stream().map(getter -> getterToGetterMethod(getter));
    }
    
    private Stream<GenMethod> generateWitherMethods(List<Getter> getters) {
        return getters
                .stream()
                .flatMap(getter -> getterToWitherMethods(sourceSpec, withMethodName, getter));
    }
    
    private Stream<GenMethod> generateSchemaMethods() {
        GenMethod         fromMap         = generateFromMap(sourceSpec);
        GenMethod         toMap           = generateToMap(sourceSpec);
        GenMethod         getStructSchema = generateGetStructScheme(sourceSpec);
        GenMethod         getSchema       = generateGetSchema(sourceSpec);
        Stream<GenMethod> schemaMethods   = Stream.of(fromMap, toMap, getSchema, getStructSchema);
        return schemaMethods;
    }
    
    private Stream<GenMethod> generateObjectMethods(List<Getter> getters) {
        GenMethod         toString      = generateToString(sourceSpec, getters);
        GenMethod         hashCode      = generateHashCode(sourceSpec);
        GenMethod         equals        = generateEquals(sourceSpec);
        Stream<GenMethod> objectMethods = Stream.of(toString, hashCode, equals);
        return objectMethods;
    }
}
