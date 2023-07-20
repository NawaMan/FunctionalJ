// ===========e=================================================================
// Copyright (cnener) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.types.Core.ObjectLensImpl;
import static functionalj.types.choice.generator.helpers.CaseLensBuilderHelper.createGenFuncListLensField;
import static functionalj.types.choice.generator.helpers.CaseLensBuilderHelper.createGenListLensField;
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
import functionalj.types.Core;
import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenParam;

public class CaseLensBuilder {
    
    private final SourceSpec sourceSpec;
    
    private final Case choiceCase;
    
    public CaseLensBuilder(SourceSpec sourceSpec, Case choiceCase) {
        this.sourceSpec = sourceSpec;
        this.choiceCase = choiceCase;
    }
    
    public List<String> build() {
        String         packageName      = sourceSpec.sourceType.packageName();
        String         dataObjClassName = sourceSpec.targetName + "." + choiceCase.name;
        Type           lensType         = new Type(null, null, choiceCase.name + "Lens", asList(new Generic("HOST")));
        Type           superType        = ObjectLensImpl.type();
        List<GenField> lensFields       = choiceCase.params.stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec)).collect(toList());
        Type           lensSpecType     = new Type(Core.LensSpec.type().packageName(), null, Core.LensSpec.type().simpleName(), asList(new Generic("HOST"), new Generic(dataObjClassName)));
        List<GenParam> consParams       = asList(new GenParam("name", Type.STRING), new GenParam("spec", lensSpecType));
        // This ignore the id for now.
        String         consBody    = "super(name, spec);";
        GenConstructor constructor = new GenConstructor(PUBLIC, lensType.simpleName(), consParams, line(consBody));
        GenClass       lensClass   = new GenClass(PUBLIC, STATIC, MODIFIABLE, lensType, "HOST", asList(superType.withGenerics(asList(new Generic("HOST"), new Generic(dataObjClassName)))), emptyList(), asList(constructor), lensFields, emptyList(), emptyList(), emptyList());
        return lensClass.toDefinition(packageName).lines().collect(Collectors.toList());
    }
    
    /**
     * Generate the Lens field ("theXxx" field).
     *
     * @return the generated field.
     */
    public GenField generateTheLensField() {
        String       choiceName     = choiceCase.name;
        String       choiceTypeName = sourceSpec.sourceType.simpleName();
        String       caseName       = choiceCase.name;
        String       packageName    = sourceSpec.sourceType.packageName();
        String       encloseName    = sourceSpec.sourceType.encloseName();
        List<String> generics       = sourceSpec.generics.stream().map(generic -> generic.name).collect(Collectors.toList());
        Type         targetType     = new Type(packageName, choiceTypeName, caseName, generics.toArray(new String[0]));
        Type         lensType       = targetType.lensType(packageName, encloseName, null).withGenerics(asList(new Generic(choiceName)));
        String       defaultValue   = format("new %1$s<>(\"the%3$s\", %2$s.of(%3$s.class))", lensType.simpleName(), Core.LensSpec.simpleName(), choiceName);
        GenField     theField       = new GenField(PUBLIC, FINAL, STATIC, "the" + choiceName, lensType, defaultValue);
        return theField;
    }
    
    public GenField generateEachLensField() {
        GenField theField     = generateTheLensField();
        String   choiceName   = theField.getName().replaceFirst("^the", "each");
        Type     lensType     = theField.getType();
        String   defaultValue = theField.getName();
        GenField eachField    = new GenField(PUBLIC, FINAL, STATIC, choiceName, lensType, defaultValue);
        return eachField;
    }
    
    static String withMethodName(CaseParam choiceCase) {
        String name = choiceCase.name();
        return "with" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    private GenField getterToLensField(CaseParam param, String recordClassName, SourceSpec sourceSpec) {
        String   recordName  = recordClassName;
        String   paramName   = param.name();
        Type     paramType   = new Type((param.type().packageName() == null) ? null : param.type().packageName(), param.type().encloseName(), param.type().simpleName(), param.type().generics().stream().map(generic -> generic.name).collect(toList()).toArray(new String[0]));
        Type     type        = paramType.declaredType();
        boolean  isPrimitive = paramType.isPrimitive();
        String   withName    = withMethodName(param);
        GenField field;
        if (type.isList()) {
            field = createGenListLensField(sourceSpec, recordName, paramName, type, withName);
        } else if (type.isMap()) {
            field = createGenMapLensField(recordName, paramName, type, withName);
        } else if (type.isFuncList()) {
            field = createGenFuncListLensField(sourceSpec, recordName, paramName, type, withName);
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
        String       packageName  = sourceSpec.sourceType.packageName();
        String       encloseName  = sourceSpec.sourceType.encloseName();
        List<String> withLens     = sourceSpec.localTypeWithLens;
        Type         lensType     = type.lensType(packageName, encloseName, withLens);
        Type         lensTypeDef  = getLensTypeDef(lensType, type);
        boolean      isCustomLens = lensType.isCustomLens();
        String       value        = lensFieldValue(dataObjName, name, withName, type, isPrimitive, lensType, lensTypeDef, isCustomLens);
        GenField     field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensTypeDef, value);
        return field;
    }
    
    private String lensFieldValue(String dataObjName, String name, String withName, Type type, boolean isPrimitive, Type lensType, Type lensTypeDef, boolean isCustomLens) {
        if (isPrimitive) {
            if (type.equals(Type.INTEGER) || type.equals(Type.INT)) {
                String value = format("createSubLensInt(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.LONG) || type.equals(Type.LNG)) {
                String value = format("createSubLensLong(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.DOUBLE) || type.equals(Type.DBL)) {
                String value = format("createSubLensDouble(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
            if (type.equals(Type.BOOLEAN) || type.equals(Type.BOOL)) {
                String value = format("createSubLensBoolean(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
                return value;
            }
        }
        // TODO - BigInteger, BigDecimal Java time and others
        String spec = isCustomLens ? lensTypeDef.simpleName() + "::new" : lensTypeDef.simpleName() + "::of";
        String value = format(getLensCast(lensType) + "createSubLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        return value;
    }
    
    private Type getLensTypeDef(final Type lensType, Type type) {
        if (lensType.fullName().equals(Core.ObjectLens.type().fullName())) {
            return lensType.withGenerics(asList(new Generic("HOST"), new Generic("Object")));
        }
        return lensType.withGenerics(asList(new Generic("HOST")));
    }
    
    private GenField createObjectLensField(String dataObjName, String name, Type type, String withName) {
        String       packageName  = sourceSpec.sourceType.packageName();
        String       encloseName  = sourceSpec.sourceType.encloseName();
        List<String> withLens     = sourceSpec.localTypeWithLens;
        Type         lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST"), new Generic(Type.OBJECT)));
        boolean      isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        String       spec         = isCustomLens ? lensType.simpleName() + "::new" : lensType.simpleName() + "::of";
        String       value        = format(getLensCast(lensType) + " createSubLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        GenField     field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private String getLensCast(Type lensType) {
        String simpleName = lensType.simpleName();
        if (simpleName.equals("ObjectLens")) {
            return "(" + simpleName + ")";
        }
        return "(" + simpleName + "<HOST>)";
    }
    
    private GenField createGenMapLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName = sourceSpec.sourceType.packageName();
        String        encloseName = sourceSpec.sourceType.encloseName();
        List<String>  withLens = sourceSpec.localTypeWithLens;
        Generic       keyGeneric = type.generics().get(0);
        Generic       valueGeneric = type.generics().get(1);
        Type          keyType = keyGeneric.toType();
        Type          valueType = valueGeneric.toType();
        List<Generic> lensGenerics = asList(new Generic("HOST"), keyGeneric, valueGeneric, new Generic(keyType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))), new Generic(valueType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        Type          lensType = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        String        keySpec = keyType.lensType(packageName, encloseName, withLens).isCustomLens() ? keyType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value = format("createSubMapLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        GenField      field = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncMapLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName = sourceSpec.sourceType.packageName();
        String        encloseName = sourceSpec.sourceType.encloseName();
        List<String>  withLens = sourceSpec.localTypeWithLens;
        Generic       keyGeneric = type.generics().get(0);
        Generic       valueGeneric = type.generics().get(1);
        Type          keyType = keyGeneric.toType();
        Type          valueType = valueGeneric.toType();
        List<Generic> lensGenerics = asList(new Generic("HOST"), keyGeneric, valueGeneric, new Generic(keyType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))), new Generic(valueType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        Type          lensType = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        String        keySpec = keyType.lensType(packageName, encloseName, withLens).isCustomLens() ? keyType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        valueSpec = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value = format("createSubFuncMapLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        GenField      field = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenNullableLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName  = sourceSpec.sourceType.packageName();
        String        encloseName  = sourceSpec.sourceType.encloseName();
        List<String>  withLens     = sourceSpec.localTypeWithLens;
        Generic       paramGeneric = type.generics().get(0);
        Type          paramType    = paramGeneric.toType();
        List<Generic> lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        Type          lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        boolean       isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String        spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value        = format("createSubNullableLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        GenField      field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenOptionalLensField(String dataObjName, String name, Type type, String withName) {
        String      packageName = sourceSpec.sourceType.packageName();
        String      encloseName = sourceSpec.sourceType.encloseName();
        List<String> withLens = sourceSpec.localTypeWithLens;
        Generic      paramGeneric = type.generics().get(0);
        Type         paramType = paramGeneric.toType();
        List<Generic> lensGenerics = asList(new Generic("HOST"), paramGeneric, new Generic(paramType.lensType(packageName, encloseName, withLens).withGenerics(asList(new Generic("HOST")))));
        Type          lensType = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        boolean       isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String        simpleName = paramType.lensType(packageName, encloseName, withLens).simpleName();
        String        spec = isCustomLens ? simpleName + "::new" : simpleName + "::of";
        String        value = format("createSubOptionalLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        GenField      field = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
}
