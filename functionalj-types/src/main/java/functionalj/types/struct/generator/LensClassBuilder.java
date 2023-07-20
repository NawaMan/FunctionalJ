// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static functionalj.types.Core.ObjectLensImpl;
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
import java.util.stream.Stream;
import functionalj.types.Core;
import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenParam;

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
        String dataObjClassName = sourceSpec.getTargetClassName();
        Type   lensType = new Type.TypeBuilder().encloseName(dataObjClassName).simpleName(dataObjClassName + "Lens").packageName(sourceSpec.getPackageName()).generics(asList(new Generic("HOST"))).build();
        Type   superType = ObjectLensImpl.type();
        // TODO - methods to access.
        Stream<GenField> lensFields   = sourceSpec.getGetters().stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec));
        Type             lensSpecType = new Type.TypeBuilder().packageName(Core.LensSpec.type().packageName()).simpleName(Core.LensSpec.type().simpleName()).generics(asList(new Generic("HOST"), new Generic(dataObjClassName))).build();
        List<GenParam>   consParams   = asList(new GenParam("name", Type.STRING), new GenParam("spec", lensSpecType));
        // This ignore the id for now.
        String         consBody    = "super(name, spec);";
        GenConstructor constructor = new GenConstructor(PUBLIC, lensType.simpleName(), consParams, line(consBody));
        GenClass       lensClass   = new GenClass(PUBLIC, STATIC, MODIFIABLE, lensType, "HOST", asList(superType.withGenerics(asList(new Generic("HOST"), new Generic(dataObjClassName)))), emptyList(), asList(constructor), lensFields.collect(toList()), emptyList(), emptyList(), emptyList());
        return lensClass;
    }
    
    /**
     * Generate the Lens field ("theXxx" field).
     *
     * @return the generated field.
     */
    public GenField generateTheLensField() {
        String   dataObjClassName = sourceSpec.getTargetClassName();
        String   packageName      = sourceSpec.getTargetPackageName();
        String   encloseName      = sourceSpec.getEncloseName();
        Type     lensType         = sourceSpec.getTargetType().lensType(packageName, encloseName, null).withGenerics(asList(new Generic(dataObjClassName)));
        String   lensTypeName     = lensType.simpleName();
        String   lensSpecTypeName = Core.LensSpec.simpleName();
        String   defaultValue     = format("new %1$s<>(\"the%3$s\", %2$s.of(%3$s.class))", lensTypeName, lensSpecTypeName, dataObjClassName);
        GenField theField         = new GenField(PUBLIC, FINAL, STATIC, "the" + dataObjClassName, lensType, defaultValue);
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
    
    private GenField getterToLensField(Getter getter, String recordClassName, SourceSpec sourceSpec) {
        String  recordName  = recordClassName;
        String  name        = getter.name();
        Type    type        = getter.type().declaredType();
        boolean isPrimitive = getter.type().isPrimitive();
        String  withName    = utils.withMethodName(getter);
        if (type.isList()) {
            return createGenListLensField(recordName, name, type, withName);
        }
        if (type.isMap()) {
            return createGenMapLensField(recordName, name, type, withName);
        }
        if (type.isFuncList()) {
            return createGenFuncListLensField(recordName, name, type, withName);
        }
        if (type.isFuncMap()) {
            return createGenFuncMapLensField(recordName, name, type, withName);
        }
        if (type.isNullable()) {
            return createGenNullableLensField(recordName, name, type, withName);
        }
        if (type.isOptional()) {
            return createGenOptionalLensField(recordName, name, type, withName);
        }
        
        return createLensField(recordName, name, type, isPrimitive, withName);
    }
    
    // TODO - DRY this.
    private GenField createLensField(String dataObjName, String name, Type type, boolean isPrimitive, String withName) {
        String       packageName  = sourceSpec.getPackageName();
        String       encloseName  = sourceSpec.getEncloseName();
        List<String> withLens     = sourceSpec.getTypeWithLens();
        Type         lensType     = type.lensType(packageName, encloseName, withLens);
        Type         lensTypeDef  = getLensTypeDef(type, lensType);
        boolean      isCustomLens = type.lensType(packageName, encloseName, withLens).isCustomLens();
        String       value        = lensFieldValue(dataObjName, name, withName, type, isPrimitive, lensTypeDef, isCustomLens);
        GenField     field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensTypeDef, value);
        return field;
    }
    
    private String lensFieldValue(String dataObjName, String name, String withName, Type type, boolean isPrimitive, Type lensTypeDef, boolean isCustomLens) {
        if (isPrimitive) {
            if (type.equals(Type.INTEGER) || type.equals(Type.INT)) {
                return format("createSubLensInt(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
            }
            if (type.equals(Type.LONG) || type.equals(Type.LNG)) {
                return  format("createSubLensLong(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
            }
            if (type.equals(Type.DOUBLE) || type.equals(Type.DBL)) {
                return format("createSubLensDouble(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
            }
            if (type.equals(Type.BOOLEAN) || type.equals(Type.BOOL)) {
                return format("createSubLensBoolean(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
            }
        }
        String spec = isCustomLens ? lensTypeDef.simpleName() + "::new" : lensTypeDef.simpleName() + "::of";
        return format("createSubLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
    }
    
    private Type getLensTypeDef(Type orgType, Type lensType) {
        if (lensType.fullName().equals(Core.ObjectLens.type().fullName())) {
            return lensType.withGenerics(asList(new Generic("HOST"), new Generic(orgType)));
        }
        return lensType.withGenerics(asList(new Generic("HOST")));
    }
    
    private GenField createGenListLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName  = sourceSpec.getPackageName();
        String        encloseName  = sourceSpec.getEncloseName();
        List<String>  withLens     = sourceSpec.getTypeWithLens();
        List<Generic> generics     = type.generics();
        Generic       paramGeneric = (generics.size() >= 1) ? generics.get(0) : new Generic(Type.OBJECT);
        Type          paramType    = paramGeneric.toType();
        List<Generic> lensGenerics = lensGenerics(packageName, encloseName, withLens, paramGeneric, paramType);
        Type          lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        boolean       isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String        value        = listLensValue(dataObjName, name, withName, packageName, encloseName, withLens, paramType, isCustomLens);
        GenField      field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private List<Generic> lensGenerics(String packageName, String encloseName, List<String> withLens, Generic paramGeneric, Type paramType) {
        boolean isList      = paramType.isList();
        Generic hostGeneric = new Generic("HOST");
        Generic subGeneric  = isList ? new Generic(Type.OBJECT) : paramGeneric;
        Generic lensGeneric = isList ? new Generic(Core.ObjectLens.type().withGenerics(hostGeneric, subGeneric)) : new Generic(getLensTypeDef(paramType, paramType.lensType(packageName, encloseName, withLens)));
        return asList(hostGeneric, subGeneric, lensGeneric);
    }
    
    private String listLensValue(String dataObjName, String name, String withName, String packageName, String encloseName, List<String> withLens, Type paramType, boolean isCustomLens) {
        if (paramType.isList()) {
            return format("createSubListLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
        }
        String lensType = paramType.lensType(packageName, encloseName, withLens).simpleName();
        String spec     = isCustomLens ? lensType + "::new" : lensType + "::of";
        return format("createSubListLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
    }
    
    private GenField createGenMapLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName  = sourceSpec.getPackageName();
        String        encloseName  = sourceSpec.getEncloseName();
        List<String>  withLens     = sourceSpec.getTypeWithLens();
        Generic       keyGeneric   = type.generics().get(0);
        Generic       valueGeneric = type.generics().get(1);
        Type          keyType      = keyGeneric.toType();
        Type          valueType    = valueGeneric.toType();
        List<Generic> lensGenerics = asList(new Generic("HOST"), keyGeneric, valueGeneric, new Generic(getLensTypeDef(keyType, keyType.lensType(packageName, encloseName, withLens))), new Generic(getLensTypeDef(valueType, valueType.lensType(packageName, encloseName, withLens))));
        Type          lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        String        keySpec      = keyType.lensType(packageName, encloseName, withLens).isCustomLens() ? keyType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        valueSpec    = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value        = format("createSubMapLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        GenField      field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncListLensField(String dataObjName, String name, Type type, String withName) {
        String       packageName   = sourceSpec.getPackageName();
        String       encloseName   = sourceSpec.getEncloseName();
        List<String>  withLens     = sourceSpec.getTypeWithLens();
        Generic       paramGeneric = type.generics().get(0);
        Type          paramType    = paramGeneric.toType();
        List<Generic> lensGenerics = lensGenerics(packageName, encloseName, withLens, paramGeneric, paramType);
        Type          lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        boolean       isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String        spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value        = format("createSubFuncListLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        GenField      field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenFuncMapLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName  = sourceSpec.getPackageName();
        String        encloseName  = sourceSpec.getEncloseName();
        List<String>  withLens     = sourceSpec.getTypeWithLens();
        Generic       keyGeneric   = type.generics().get(0);
        Generic       valueGeneric = type.generics().get(1);
        Type          keyType      = keyGeneric.toType();
        Type          valueType    = valueGeneric.toType();
        List<Generic> lensGenerics = asList(new Generic("HOST"), keyGeneric, valueGeneric, new Generic(getLensTypeDef(keyType, keyType.lensType(packageName, encloseName, withLens))), new Generic(getLensTypeDef(valueType, valueType.lensType(packageName, encloseName, withLens))));
        Type          lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        String        keySpec      = keyType.lensType(packageName, encloseName, withLens).isCustomLens() ? keyType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : keyType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        valueSpec    = valueType.lensType(packageName, encloseName, withLens).isCustomLens() ? valueType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : valueType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value        = format("createSubFuncMapLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s, %5$s)", dataObjName, name, withName, keySpec, valueSpec);
        GenField      field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenNullableLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName  = sourceSpec.getPackageName();
        String        encloseName  = sourceSpec.getEncloseName();
        List<String>  withLens     = sourceSpec.getTypeWithLens();
        Generic       paramGeneric = type.generics().get(0);
        Type          paramType    = paramGeneric.toType();
        List<Generic> lensGenerics = lensGenerics(packageName, encloseName, withLens, paramGeneric, paramType);
        Type          lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        boolean       isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String        spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value        = format("createSubNullableLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        GenField      field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private GenField createGenOptionalLensField(String dataObjName, String name, Type type, String withName) {
        String        packageName  = sourceSpec.getPackageName();
        String        encloseName  = sourceSpec.getEncloseName();
        List<String>  withLens     = sourceSpec.getTypeWithLens();
        Generic       paramGeneric = type.generics().get(0);
        Type          paramType    = paramGeneric.toType();
        List<Generic> lensGenerics = lensGenerics(packageName, encloseName, withLens, paramGeneric, paramType);
        Type          lensType     = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        boolean       isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String        spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        String        value        = format("createSubOptionalLens(\"%2$s\", %1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        GenField      field        = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
}
