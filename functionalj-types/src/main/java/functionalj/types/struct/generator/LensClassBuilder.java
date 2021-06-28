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

import static functionalj.types.struct.Core.ObjectLensImpl;
import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.FINAL;
import static functionalj.types.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.types.struct.generator.model.Scope.INSTANCE;
import static functionalj.types.struct.generator.model.Scope.STATIC;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.stream.Stream;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenParam;
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
                .generics   (asList(new Generic("HOST")))
                .build();
        val superType = ObjectLensImpl.type();
        
        // TODO - methods to access.
        Stream<GenField> lensFields  = sourceSpec.getGetters().stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec));
        
        val lensSpecType = new Type.TypeBuilder()
                .packageName(Core.LensSpec.type().packageName())
                .simpleName (Core.LensSpec.type().simpleName())
                .generics   (asList(new Generic("HOST"), new Generic(dataObjClassName)))
                .build();
        
        val consParams  = asList(new GenParam("spec", lensSpecType));
        val consBody    = "super(spec);"; // This ignore the id for now.
        val constructor = new GenConstructor(PUBLIC, lensType.simpleName(), consParams, line(consBody));
        
        val lensClass = new GenClass(
                PUBLIC, STATIC, MODIFIABLE, lensType, "HOST",
                asList   (superType.withGenerics(asList(new Generic("HOST"), new Generic(dataObjClassName)))),
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
        val lensType         = sourceSpec.getTargetType().lensType(packageName, encloseName, null).withGenerics(asList(new Generic(dataObjClassName)));
        val defaultValue     = String.format("new %1$s<>(%2$s.of(%3$s.class))", lensType.simpleName(), Core.LensSpec.simpleName(), dataObjClassName);
        val theField         = new GenField(PUBLIC, FINAL, STATIC, "the"+dataObjClassName, lensType, defaultValue);
        return theField;
    }
    
    public GenField generateEachLensField() {
        val theField     = generateTheLensField();
        val choiceName   = theField.getName().replaceFirst("^the", "each");
        val lensType     = theField.getType();
        val defaultValue = theField.getName();
        val eachField    = new GenField(PUBLIC, FINAL, STATIC, choiceName, lensType, defaultValue);
        return eachField;
    }
    
    private GenField getterToLensField(Getter getter, String recordClassName, SourceSpec sourceSpec) {
        val recordName  = recordClassName;
        val name        = getter.getName();
        val type        = getter.getType().declaredType();
        val isPrimitive = getter.getType().isPrimitive();
        val withName    = utils.withMethodName(getter);
        
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
            field = createLensField(recordName, name, type, isPrimitive, withName);
        }
        return field;
    }
    
    // TODO - DRY this.
    
    private GenField createLensField(String dataObjName, String name, Type type, boolean isPrimitive, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val lensType     = type.lensType(packageName, encloseName, withLens);
        val lensTypeDef  = getLensTypeDef(type, lensType);
        val isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        val value        = lensFieldValue(dataObjName, name, withName, type, isPrimitive, lensTypeDef, isCustomLens);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensTypeDef, value);
        return field;
    }
    
    private String lensFieldValue(
            String  dataObjName,
            String  name,
            String  withName,
            Type    type,
            boolean isPrimitive,
            Type    lensTypeDef,
            boolean isCustomLens) {
        if (isPrimitive) {
            if (type.equals(Type.INTEGER)
             || type.equals(Type.INT)) {
                val value = format("createSubLensInt(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.LONG)
             || type.equals(Type.LNG)) {
                val value = format("createSubLensLong(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.DOUBLE)
             || type.equals(Type.DBL)) {
                val value = format("createSubLensDouble(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.BOOLEAN)
             || type.equals(Type.BOOL)) {
                val value = format("createSubLensBoolean(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
        }
        
        val spec  = isCustomLens ? lensTypeDef.simpleName() + "::new" : lensTypeDef.simpleName() + "::of";
        val value = format("createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        return value;
    }
    
    private Type getLensTypeDef(Type orgType, Type lensType) {
        if (lensType.fullName().equals(Core.ObjectLens.type().fullName()))
            return lensType.withGenerics(asList(new Generic("HOST"), new Generic(orgType)));
        return lensType.withGenerics(asList(new Generic("HOST")));
    }
    
    private GenField createGenListLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.getPackageName();
        val encloseName  = sourceSpec.getEncloseName();
        val withLens     = sourceSpec.getTypeWithLens();
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(
                                new Generic("HOST"),
                                paramGeneric,
                                new Generic(getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens))));
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
        val keyGeneric   = type.generics().get(0);
        val valueGeneric = type.generics().get(1);
        val keyType      = keyGeneric.toType();
        val valueType    = valueGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"),
                                  keyGeneric,
                                  valueGeneric,
                                  new Generic(getLensTypeDef(keyType,   keyType.lensType(packageName, encloseName, withLens))),
                                  new Generic(getLensTypeDef(valueType, valueType.lensType(packageName, encloseName, withLens))));
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
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(
                            new Generic("HOST"),
                            paramGeneric,
                            new Generic(getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens))));
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
        val keyGeneric   = type.generics().get(0);
        val valueGeneric = type.generics().get(1);
        val keyType      = keyGeneric.toType();
        val valueType    = valueGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"),
                                  keyGeneric,
                                  valueGeneric,
                                  new Generic(getLensTypeDef(keyType,   keyType.lensType(packageName, encloseName, withLens))),
                                  new Generic(getLensTypeDef(valueType, valueType.lensType(packageName, encloseName, withLens))));
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
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens))));
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
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens))));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubOptionalLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
}
