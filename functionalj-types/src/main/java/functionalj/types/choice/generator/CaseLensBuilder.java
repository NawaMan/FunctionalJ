// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.choice.generator;

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

import java.util.List;
import java.util.stream.Collectors;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenParam;
import lombok.val;

public class CaseLensBuilder {
    
    private final SourceSpec sourceSpec;
    private final Case       choiceCase;
    
    public CaseLensBuilder(SourceSpec sourceSpec, Case choiceCase) {
        this.sourceSpec = sourceSpec;
        this.choiceCase = choiceCase;
    }
    
    public List<String> build() {
        val packageName      = sourceSpec.sourceType.packageName();
        val dataObjClassName = sourceSpec.targetName + "." + choiceCase.name;
        val lensType = new Type(
                null,
                null,
                choiceCase.name + "Lens",
                asList(new Generic("HOST")));
        val superType = ObjectLensImpl.type();
        
        val lensFields = choiceCase.params.stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec)).collect(toList());
        
        val lensSpecType = new Type(
                Core.LensSpec.type().packageName(),
                null,
                Core.LensSpec.type().simpleName(),
                asList(new Generic("HOST"), new Generic(dataObjClassName)));
        
        val consParams  = asList(new GenParam("spec", lensSpecType));
        val consBody    = "super(spec);"; // This ignore the id for now.
        val constructor = new GenConstructor(PUBLIC, lensType.simpleName(), consParams, line(consBody));
        
        val lensClass = new GenClass(
                PUBLIC, STATIC, MODIFIABLE, lensType, "HOST",
                asList   (superType.withGenerics(asList(new Generic("HOST"), new Generic(dataObjClassName)))),
                emptyList(),
                asList   (constructor),
                lensFields,
                emptyList(),
                emptyList(),
                emptyList());
        return lensClass.toDefinition(packageName).lines().collect(Collectors.toList());
    }
    
    /**
     * Generate the Lens field ("theXxx" field).
     * 
     * @return the generated field.
     */
    public GenField generateTheLensField() {
        val choiceName     = choiceCase.name;
        val choiceTypeName = sourceSpec.sourceType.simpleName();
        val caseName       = choiceCase.name;
        val packageName    = sourceSpec.sourceType.packageName();
        val encloseName    = sourceSpec.sourceType.encloseName();
        val generics       = sourceSpec.generics.stream().map(generic -> generic.name).collect(Collectors.toList());
        val targetType     = new Type(packageName, choiceTypeName, caseName, generics.toArray(new String[0]));
        val lensType       = targetType.lensType(packageName, encloseName, null).withGenerics(asList(new Generic(choiceName)));
        val defaultValue   = String.format("new %1$s<>(%2$s.of(%3$s.class))", lensType.simpleName(), Core.LensSpec.simpleName(), choiceName);
        val theField       = new GenField(PUBLIC, FINAL, STATIC, "the"+choiceName, lensType, defaultValue);
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
    
    static String withMethodName(CaseParam choiceCase) {
        val name = choiceCase.name;
        return "with" + name.substring(0,1).toUpperCase() + name.substring(1);
    }
    private GenField getterToLensField(CaseParam param, String recordClassName, SourceSpec sourceSpec) {
        val recordName = recordClassName;
        val paramName  = param.name;
        val paramType  = new Type(
                (param.type.packageName() == null) ? null : param.type.packageName(),
                param.type.encloseName(),
                param.type.simpleName(),
                param.type.generics().stream().map(generic -> generic.name).collect(toList()).toArray(new String[0]));
        val type        = paramType.declaredType();
        val isPrimitive = paramType.isPrimitive();
        val withName    = withMethodName(param);
        
        GenField field;
        if (type.isList()) {
            field = createGenListLensField(recordName, paramName, type, withName);
        } else if (type.isMap()) {
            field = createGenMapLensField(recordName, paramName, type, withName);
        } else if (type.isFuncList()) {
            field = createGenFuncListLensField(recordName, paramName, type, withName);
        } else if (type.isFuncMap()) {
            field = createGenFuncMapLensField(recordName, paramName, type, withName);
        } else if (type.isNullable()) {
            field = createGenNullableLensField(recordName, paramName, type, withName);
        } else if (type.isOptional()) {
            field = createGenOptionalLensField(recordName, paramName, type, withName);
        } else if (type.isObject()) {
            field = createObjectLensField(recordName, paramName, type, withName);
        } else {
            field = createLensField(recordName, paramName, type, isPrimitive, withName);
        }
        return field;
    }
    
    // TODO - DRY this.
    
    private GenField createLensField(String dataObjName, String name, Type type, boolean isPrimitive, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val lensType     = type.lensType(packageName, encloseName, withLens);
        val lensTypeDef  = getLensTypeDef(lensType);
        val isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        val value        = lensFieldValue(dataObjName, name, withName, type, isPrimitive, lensType, lensTypeDef, isCustomLens);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensTypeDef, value);
        return field;
    }

    private String lensFieldValue(
            String  dataObjName, 
            String  name, 
            String  withName,
            Type    type, 
            boolean isPrimitive,
            Type    lensType,
            Type    lensTypeDef,
            boolean isCustomLens) {
        if (isPrimitive) {
            if (type.equals(Type.INTEGER)
             || type.equals(Type.LONG)
             || type.equals(Type.DOUBLE)
             || type.equals(Type.INT)
             || type.equals(Type.LNG)
             || type.equals(Type.DBL)) {
                val value = format("createSubLensPrimitive(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
        }
        
        val spec         = isCustomLens ? lensTypeDef.simpleName() + "::new" : lensTypeDef.simpleName() + "::of";
        val value        = format("(" + lensType.simpleName() + ")createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        return value;
    }
    
    private Type getLensTypeDef(final Type lensType) {
        if (lensType.fullName().equals(Core.ObjectLens.type().fullName()))
            return lensType.withGenerics(asList(new Generic("HOST"), new Generic(Type.OBJECT)));
        return lensType.withGenerics(asList(new Generic("HOST")));
    }
    
    private GenField createObjectLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST"), new Generic(Type.OBJECT)));
        val isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? lensType.simpleName() + "::new" : lensType.simpleName() + "::of";
        val value        = format("(" + lensType.simpleName() + ")createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenListLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenMapLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val keyGeneric   = type.generics().get(0);
        val valueGeneric = type.generics().get(1);
        val keyType      = keyGeneric.toType();
        val valueType    = valueGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), 
                                keyGeneric, valueGeneric,
                                new Generic(keyType  .lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))),
                                new Generic(valueType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        val lensType  = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val keySpec   = keyType  .lensType(packageName, encloseName, withLens).isCustomLens() ? keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value     = format("createSubMapLens(%1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        val field     = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncListLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubFuncListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncMapLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val keyGeneric   = type.generics().get(0);
        val valueGeneric = type.generics().get(1);
        val keyType      = keyGeneric.toType();
        val valueType    = valueGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), 
                                 keyGeneric, valueGeneric,
                                 new Generic(keyType  .lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))),
                                 new Generic(valueType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        val lensType  = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val keySpec   = keyType  .lensType(packageName, encloseName, withLens).isCustomLens() ? keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value     = format("createSubFuncMapLens(%1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        val field     = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenNullableLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        val value        = format("createSubNullableLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenOptionalLensField(String dataObjName, String name, Type type, String withName) {
        val packageName  = sourceSpec.sourceType.packageName();
        val encloseName  = sourceSpec.sourceType.encloseName();
        val withLens     = sourceSpec.localTypeWithLens;
        val paramGeneric = type.generics().get(0);
        val paramType    = paramGeneric.toType();
        val lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        val lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        val isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        val simpleName   = paramType.lensType(packageName, encloseName, withLens).simpleName();
        val spec         = isCustomLens ? simpleName + "::new" : simpleName + "::of";
        val value        = format("createSubOptionalLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
}
