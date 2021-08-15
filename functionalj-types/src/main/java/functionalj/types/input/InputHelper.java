package functionalj.types.input;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.Self;
import lombok.val;

public class InputHelper {
//    
//    public List<Generic> genericsFromTypeParameters(Type targetType, List<? extends TypeMirror> typeParameters) {
//        return typeParameters.stream()
//                        .map(genericFromTypeParameter(targetType))
//                        .collect(toList());
//    }
//
//    private Function<TypeMirror, Generic> genericFromTypeParameter(Type targetType) {
//        return typeMirror -> {
//            val typeName = typeMirror.toString();
//            if (typeMirror instanceof TypeVariable) {
//                val typeVariable = (TypeVariable)typeMirror;
//                return new Generic(
//                    typeName,
//                    typeName
//                        + ((typeVariable.getLowerBound() == null) ? "" : " super " + typeVariable.getLowerBound())
//                        + (typeVariable.getUpperBound().toString().equals("java.lang.Object") ? "" : " extends " + typeVariable.getUpperBound()),
//                    Stream.of(
//                        typeOf(targetType, typeVariable.getLowerBound()),
//                        typeOf(targetType, typeVariable.getUpperBound())
//                    )
//                    .filter(Objects::nonNull)
//                    .collect(toList())
//                );
//            } else {
//                return new Generic(typeName);
//            }
//        };
//    }
//    
//    public Type typeOf(Type targetType, TypeMirror typeMirror) {
//        if (typeMirror == null)
//            return null;
//        
//        val typeStr = typeMirror.toString();
//        if (typeMirror instanceof PrimitiveType)
//            return new Type(typeStr);
//        
//        if (typeMirror instanceof DeclaredType) {
//            val typeElement  = ((TypeElement)((DeclaredType)typeMirror).asElement());
//            val packageName  = getPackageName(typeElement);
//            val typeName     = typeElement.getSimpleName().toString();
//            val encloseClass = extractEnclosedClassName(typeElement, packageName, typeName);
//            val generics     = extractGenericsFromTypeArguments(targetType, ((DeclaredType)typeMirror).getTypeArguments());
//            val foundType    = new Type(packageName, encloseClass, typeName, generics);
//            if (packageName.equals(Self.class.getPackage().getName()) && typeName.matches("^Self[0-9]?$"))
//                return new Type(targetType.packageName(), targetType.encloseName(), targetType.simpleName(), generics);
//            
//            return foundType;
//        }
//        
//        if (typeMirror instanceof TypeVariable) {
//            val varType = (TypeVariable)typeMirror;
//            return new Type(null, null, varType.toString());
//        }
//        
//        return null;
//    }
//    
//    private String extractEnclosedClassName(TypeElement typeElement, String packageName, String typeName) {
//        String encloseClass = null;
//        val qualifiedName = typeElement.getQualifiedName().toString();
//        encloseClass  = (typeElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) &&  qualifiedName.endsWith("." + typeName)
//                      ? qualifiedName.substring(0, qualifiedName.length() - typeName.length() - 1)
//                      : null;
//        encloseClass  = (typeElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) && encloseClass != null && encloseClass.startsWith(packageName + ".")
//                      ? encloseClass.substring(packageName.length() + 1)
//                      : null;
//        return encloseClass;
//    }
//    
//    public String getPackageName(TypeElement typeElement) {
//        val element      = input.element();
//        val elementUtils = input.elementUtils();
//        val typePackage  = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
//        if (!typePackage.isEmpty())
//            return typePackage;
//        
//        val packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
//        return packageName;
//    }
}
