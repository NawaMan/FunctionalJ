package functionalj.types.choice.generator.helpers;

import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.FINAL;
import static functionalj.types.struct.generator.model.Scope.INSTANCE;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import java.util.List;
import functionalj.types.Core;
import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.struct.generator.model.GenField;

public class CaseLensBuilderHelper {
    
    public static GenField createGenListLensField(SourceSpec sourceSpec, String dataObjName, String name, Type type, String withName) {
        String        packageName   = sourceSpec.sourceType.packageName();
        String        encloseName   = sourceSpec.sourceType.encloseName();
        List<String>  withLens      = sourceSpec.localTypeWithLens;
        Generic       paramGeneric  = type.generics().get(0);
        Type          paramType     = paramGeneric.toType();
        Type          paramLensType = paramType.lensType(packageName, encloseName, withLens);
        List<Generic> lensGenerics  = lensGenerics(packageName, encloseName, withLens, paramGeneric, paramType, paramLensType);
        Type          lensType      = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        String        value         = createListLensValue(packageName, encloseName, withLens, dataObjName, name, withName, paramType, paramLensType);
        GenField      field         = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private static String createListLensValue(String packageName, String encloseName, List<String> withLens, String dataObjName, String name, String withName, Type paramType, Type paramLensType) {
        boolean isObjectLens = paramLensType.isObjectLens();
        if (isObjectLens)
            return format("createSubListLens(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
        
        boolean isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String  spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        return format("createSubListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
    }
    
    public static GenField createGenFuncListLensField(SourceSpec sourceSpec, String dataObjName, String name, Type type, String withName) {
        String        packageName   = sourceSpec.sourceType.packageName();
        String        encloseName   = sourceSpec.sourceType.encloseName();
        List<String>  withLens      = sourceSpec.localTypeWithLens;
        Generic       paramGeneric  = type.generics().get(0);
        Type          paramType     = paramGeneric.toType();
        Type          paramLensType = paramType.lensType(packageName, encloseName, withLens);
        List<Generic> lensGenerics  = lensGenerics(packageName, encloseName, withLens, paramGeneric, paramType, paramLensType);
        Type          lensType      = type.lensType(packageName, encloseName, withLens).withGenerics(lensGenerics);
        String        value         = createFuncListLensValue(packageName, encloseName, withLens, dataObjName, name, withName, paramType, paramLensType);
        GenField      field         = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
    private static String createFuncListLensValue(String packageName, String encloseName, List<String> withLens, String dataObjName, String name, String withName, Type paramType, Type paramLensType) {
        boolean isObjectLens = paramLensType.isObjectLens();
        if (isObjectLens) {
            return format("createSubFuncListLens(%1$s::%2$s, %1$s::%3$s)", dataObjName, name, withName);
        }
        boolean isCustomLens = paramType.lensType(packageName, encloseName, withLens).isCustomLens();
        String  spec         = isCustomLens ? paramType.lensType(packageName, encloseName, withLens).simpleName() + "::new" : paramType.lensType(packageName, encloseName, withLens).simpleName() + "::of";
        return format("createSubFuncListLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
    }
    
    private static List<Generic> lensGenerics(String packageName, String encloseName, List<String> withLens, Generic paramGeneric, Type paramType, Type paramLensType) {
        boolean isList       = paramType.isList();
        boolean isObjectLens = paramLensType.isObjectLens();
        Generic hostGeneric  = new Generic("HOST");
        Generic subGeneric   = (isList || isObjectLens) ? new Generic(Type.OBJECT) : paramGeneric;
        Generic lensGeneric  = isList ? new Generic(Core.ObjectLens.type().withGenerics(hostGeneric, subGeneric)) : new Generic(getLensTypeDef(paramLensType));
        return asList(hostGeneric, subGeneric, lensGeneric);
    }
    
    private static Type getLensTypeDef(Type lensType) {
        if (lensType.fullName().equals(Core.ObjectLens.type().fullName())) {
            return lensType.withGenerics(asList(new Generic("HOST"), new Generic("Object")));
        }
        return lensType.withGenerics(asList(new Generic("HOST")));
    }
}
