// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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


public class CaseLensBuilder {
    
    private final SourceSpec sourceSpec;
    private final Case       choiceCase;
    
    public CaseLensBuilder(SourceSpec sourceSpec, Case choiceCase) {
        this.sourceSpec = sourceSpec;
        this.choiceCase = choiceCase;
    }
    
    public List<String> build() {
        var packageName      = sourceSpec.sourceType.packageName();
        var dataObjClassName = sourceSpec.targetName + "." + choiceCase.name;
        var lensType = new Type(
                null,
                null,
                choiceCase.name + "Lens",
                asList(new Generic("HOST")));
        
        var superType    = ObjectLensImpl.type();
        var lensFields   = choiceCase.params.stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec)).collect(toList());
        var lensSpecType = new Type(
                Core.LensSpec.type().packageName(),
                null,
                Core.LensSpec.type().simpleName(),
                asList(new Generic("HOST"), new Generic(dataObjClassName)));
        
        var consParams  = asList(new GenParam("spec", lensSpecType));
        var consBody    = "super(spec);"; // This ignore the id for now.
        var constructor = new GenConstructor(PUBLIC, lensType.simpleName(), consParams, line(consBody));
        
        var lensClass = new GenClass(
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
        var choiceName     = choiceCase.name;
        var choiceTypeName = sourceSpec.sourceType.simpleName();
        var caseName       = choiceCase.name;
        var packageName    = sourceSpec.sourceType.packageName();
        var encloseName    = sourceSpec.sourceType.encloseName();
        var generics       = sourceSpec.generics.stream().map(generic -> generic.name).collect(Collectors.toList());
        var targetType     = new Type(packageName, choiceTypeName, caseName, generics.toArray(new String[0]));
        var lensType       = targetType.lensType(packageName, encloseName, null).withGenerics(asList(new Generic(choiceName)));
        var defaultValue   = String.format("new %1$s<>(%2$s.of(%3$s.class))", lensType.simpleName(), Core.LensSpec.simpleName(), choiceName);
        var theField       = new GenField(PUBLIC, FINAL, STATIC, "the"+choiceName, lensType, defaultValue);
        return theField;
    }
    
    public GenField generateEachLensField() {
        var theField     = generateTheLensField();
        var choiceName   = theField.getName().replaceFirst("^the", "each");
        var lensType     = theField.getType();
        var defaultValue = theField.getName();
        var eachField    = new GenField(PUBLIC, FINAL, STATIC, choiceName, lensType, defaultValue);
        return eachField;
    }
    
    static String withMethodName(CaseParam choiceCase) {
        var name = choiceCase.name;
        return "with" + name.substring(0,1).toUpperCase() + name.substring(1);
    }
    private GenField getterToLensField(CaseParam param, String recordClassName, SourceSpec sourceSpec) {
        var recordName = recordClassName;
        var paramName  = param.name;
        var paramType  = new Type(
                (param.type.packageName() == null) ? null : param.type.packageName(),
                param.type.encloseName(),
                param.type.simpleName(),
                param.type.generics().stream().map(generic -> generic.name).collect(toList()).toArray(new String[0]));
        var type        = paramType.declaredType();
        var isPrimitive = paramType.isPrimitive();
        var withName    = withMethodName(param);
        
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
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var lensType     = type.lensType(packageName, encloseName, withLens);
        var lensTypeDef  = getLensTypeDef(lensType);
        var isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        var value        = lensFieldValue(dataObjName, name, withName, type, isPrimitive, lensType, lensTypeDef, isCustomLens);
        var field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensTypeDef, value);
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
             || type.equals(Type.INT)) {
                var value = format("createSubLensInt(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.LONG)
             || type.equals(Type.LNG)) {
                var value = format("createSubLensLong(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.DOUBLE)
             || type.equals(Type.DBL)) {
                var value = format("createSubLensDouble(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.BOOLEAN)
             || type.equals(Type.BOOL)) {
                var value = format("createSubLensBoolean(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
        }
        
        var spec         = isCustomLens ? lensTypeDef.simpleName() + "::new" : lensTypeDef.simpleName() + "::of";
        var value        = format("(" + lensType.simpleName() + ")createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        return value;
    }
    
    private Type getLensTypeDef(final Type lensType) {
        if (lensType.fullName().equals(Core.ObjectLens.type().fullName()))
            return lensType.withGenerics(asList(new Generic("HOST"), new Generic(Type.OBJECT)));
        return lensType.withGenerics(asList(new Generic("HOST")));
    }
    
    private GenField createObjectLensField(String dataObjName, String name, Type type, String withName) {
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST"), new Generic(Type.OBJECT)));
        var isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        var spec         = isCustomLens ? lensType.simpleName() + "::new" : lensType.simpleName() + "::of";
        var value        = format("(" + lensType.simpleName() + ")createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        var field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenListLensField(String dataObjName, String name, Type type, String withName) {
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var paramGeneric = type.generics().get(0);
        var paramType    = paramGeneric.toType();
        var lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        var lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        var isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        var spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        var value        = format("createSubListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        var field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenMapLensField(String dataObjName, String name, Type type, String withName) {
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var keyGeneric   = type.generics().get(0);
        var valueGeneric = type.generics().get(1);
        var keyType      = keyGeneric.toType();
        var valueType    = valueGeneric.toType();
        var lensGenerics = asList(new Generic("HOST"),
                                keyGeneric, valueGeneric,
                                new Generic(keyType  .lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))),
                                new Generic(valueType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        var lensType  = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        var keySpec   = keyType  .lensType(packageName, encloseName, withLens).isCustomLens() ? keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::of";
        var valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        var value     = format("createSubMapLens(%1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        var field     = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncListLensField(String dataObjName, String name, Type type, String withName) {
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var paramGeneric = type.generics().get(0);
        var paramType    = paramGeneric.toType();
        var lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        var lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        var isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        var spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        var value        = format("createSubFuncListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        var field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncMapLensField(String dataObjName, String name, Type type, String withName) {
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var keyGeneric   = type.generics().get(0);
        var valueGeneric = type.generics().get(1);
        var keyType      = keyGeneric.toType();
        var valueType    = valueGeneric.toType();
        var lensGenerics = asList(new Generic("HOST"),
                                 keyGeneric, valueGeneric,
                                 new Generic(keyType  .lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))),
                                 new Generic(valueType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        var lensType  = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        var keySpec   = keyType  .lensType(packageName, encloseName, withLens).isCustomLens() ? keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType  .lensType(packageName, encloseName, withLens).simpleName() + "::of";
        var valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        var value     = format("createSubFuncMapLens(%1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        var field     = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenNullableLensField(String dataObjName, String name, Type type, String withName) {
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var paramGeneric = type.generics().get(0);
        var paramType    = paramGeneric.toType();
        var lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        var lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        var isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        var spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        var value        = format("createSubNullableLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        var field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenOptionalLensField(String dataObjName, String name, Type type, String withName) {
        var packageName  = sourceSpec.sourceType.packageName();
        var encloseName  = sourceSpec.sourceType.encloseName();
        var withLens     = sourceSpec.localTypeWithLens;
        var paramGeneric = type.generics().get(0);
        var paramType    = paramGeneric.toType();
        var lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        var lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        var isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        var simpleName   = paramType.lensType(packageName, encloseName, withLens).simpleName();
        var spec         = isCustomLens ? simpleName + "::new" : simpleName + "::of";
        var value        = format("createSubOptionalLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        var field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
}
