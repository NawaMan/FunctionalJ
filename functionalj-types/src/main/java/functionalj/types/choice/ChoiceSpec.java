// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.choice;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import functionalj.types.Choice;
import functionalj.types.DefaultTo;
import functionalj.types.Generic;
import functionalj.types.Nullable;
import functionalj.types.Type;
import functionalj.types.common;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.Method;
//import functionalj.types.choice.generator.model.Method.Kind;
import functionalj.types.choice.generator.model.MethodParam;
import functionalj.types.choice.generator.model.SourceSpec;


public class ChoiceSpec {
    
    static public interface Input {
        
        public Element  element();
        public Elements elementUtils();
        public Messager messager();
        
    }
    
    private final Input input;
    private boolean hasError = false;
    
    public ChoiceSpec(ChoiceSpecInputImpl input) {
        this.input = input;
    }
    
    private void error(String msg) {
        hasError = true;
        var element  = input.element();
        var messager = input.messager();
        messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
    }
    
    public String packageName() {
        var element      = input.element();
        var typeElement  = (TypeElement)element;
        var elementUtils = input.elementUtils();
        return elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }
    
    public String targetName() {
        var element        = input.element();
        var typeElement    = (TypeElement)element;
        var choiceType     = element.getAnnotation(Choice.class);
        var specTargetName = choiceType.name();
        var simpleName     = typeElement.getSimpleName().toString();
        var targetName     = common.extractTargetName(simpleName, specTargetName);
        return targetName;
    }
    
    public String specTargetName() {
        var element        = input.element();
        var choiceType     = element.getAnnotation(Choice.class);
        var specTargetName = choiceType.name();
        return specTargetName;
    }
    
    public SourceSpec sourceSpec() {
        var element      = input.element();
        var typeElement  = (TypeElement)element;
        var elementUtils = input.elementUtils();
        
        var localTypeWithLens = common.readLocalTypeWithLens(element);
        
        var simpleName  = typeElement.getSimpleName().toString();
        var isInterface = ElementKind.INTERFACE.equals(element.getKind());
        if (!isInterface) {
            error("Only an interface can be annotated with " + Choice.class.getSimpleName() + ": " + simpleName);
            return null;
        }
        
        var generics = extractTypeGenerics(null, typeElement);
        
        var packageName    = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        var sourceName     = typeElement.getQualifiedName().toString().substring(packageName.length() + 1 );
        var enclosedClass  = extractEncloseClass(simpleName, sourceName);
        var sourceType     = new Type(packageName, enclosedClass, simpleName, generics);
        var choiceType    = element.getAnnotation(Choice.class);
        var specTargetName = choiceType.name();
        var targetName     = common.extractTargetName(simpleName, specTargetName);
        var targetType     = new Type(packageName, null, targetName, generics);
        
        var specField = emptyToNull(choiceType.specField());
        if ((specField != null) && !specField.matches("^[A-Za-z_$][A-Za-z_$0-9]*$")) {
            error("Source spec field name is not a valid identifier: " + choiceType.specField());
            return null;
        }
        
        var tagMapKeyName = choiceType.tagMapKeyName();
        
        var choices      = extractTypeChoices(targetType, typeElement);
        var methods      = extractTypeMethods(targetType, typeElement);
        var publicFields = choiceType.publicFields();
        var sourceSpec   = new SourceSpec(targetName, sourceType, specField, publicFields, tagMapKeyName, generics, choices, methods, localTypeWithLens);
        return sourceSpec;
    }
    
    public boolean hasError() {
        return hasError;
    }
    
    private String extractEncloseClass(final java.lang.String simpleName, final java.lang.String sourceName) {
        try {
            return sourceName.substring(0, sourceName.length() - simpleName.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    private String emptyToNull(String sourceSpec) {
        if (sourceSpec == null)
            return null;
        return sourceSpec.isEmpty() ? null : sourceSpec;
    }
    
    private boolean isDefaultOrStatic(ExecutableElement mthd) {
        return mthd.isDefault()
            || mthd.getModifiers().contains(STATIC);
    }
    
    private Method createMethodFromMethodElement(Type targetType, ExecutableElement mthd) {
        var kind       = mthd.isDefault() ? Method.Kind.DEFAULT : Method.Kind.STATIC;
        var name       = mthd.getSimpleName().toString();
        
        var type       = typeOf(targetType, mthd.getReturnType());
        var params     = extractParameters(targetType, mthd);
        var generics   = extractGenerics(targetType, mthd.getTypeParameters());
        var exceptions = mthd.getThrownTypes().stream().map(t -> typeOf(targetType, t)).collect(toList());
        var method = new Method(kind, name, type, params, generics, exceptions);
        return method;
    }
    
    private List<MethodParam> extractParameters(Type targetType, ExecutableElement mthd) {
        return mthd
                .getParameters().stream()
                .map(p -> {
                    var paramName = p.getSimpleName().toString();
                    var paramType = typeOf(targetType, p.asType());
                    return new MethodParam(paramName, paramType);
                }).collect(toList());
    }
    
    private boolean isPublicOrPackage(ExecutableElement mthd) {
        return mthd.getModifiers().contains(PUBLIC)
          || !(mthd.getModifiers().contains(PRIVATE)
            && mthd.getModifiers().contains(PROTECTED));
    }
    
    private List<Generic> extractTypeGenerics(Type targetType, TypeElement type) {
        List<? extends TypeParameterElement> typeParameters = type.getTypeParameters();
        return extractGenerics(targetType, typeParameters);
    }
    
    private List<Generic> extractGenerics(Type targetType, List<? extends TypeParameterElement> typeParameters) {
        return typeParameters.stream()
                .map(t -> (TypeParameterElement)t)
                .map(t -> parameterGeneric(targetType, t))
                .collect(toList());
    }
    
    private Generic parameterGeneric(Type targetType, TypeParameterElement t) {
        var boundTypes = ((TypeParameterElement)t).getBounds().stream()
                .map(TypeMirror.class::cast)
                .map(tm -> this.typeOf(targetType, tm))
                .collect(toList());
        var paramName = t.toString();
        var boundAsString = (boundTypes.isEmpty()) ? ""
                : " extends " + t.getBounds().stream().map(b -> {
                    Type   typeOf     = typeOf(targetType, (TypeMirror)b);
                    String typeNameOf = typeOf.simpleName();
                    return typeNameOf;
                }).collect(joining(" & "));
        return new Generic(
                paramName,
                paramName + boundAsString,
                boundTypes);
    }
    
    private List<Generic> extractGenericsFromTypeArguments(Type targetType, List<? extends TypeMirror> typeParameters) {
        return typeParameters.stream()
                .map(p -> {
                    var paramName = p.toString();
                    if (p instanceof TypeVariable) {
                        var param = (TypeVariable)p;
                        return new Generic(
                            paramName,
                            paramName
                                + ((param.getLowerBound() == null) ? "" : " super " + param.getLowerBound())
                                + (param.getUpperBound().toString().equals("java.lang.Object") ? "" : " extends " + param.getUpperBound()),
                            Stream.of(
                                typeOf(targetType, param.getLowerBound()),
                                typeOf(targetType, param.getUpperBound())
                            )
                            .filter(Objects::nonNull)
                            .collect(toList())
                        );
                    } else {
                        return new Generic(paramName);
                    }
                })
                .collect(toList());
    }
    
    private List<Method> extractTypeMethods(Type targetType, TypeElement typeElement) {
        return typeElement.getEnclosedElements().stream()
                .filter (elmt->elmt.getKind().equals(ElementKind.METHOD))
                .map    (elmt->((ExecutableElement)elmt))
                .filter (mthd->!mthd.getSimpleName().toString().startsWith("__"))
                .filter (mthd->isPublicOrPackage(mthd))
                .filter (mthd->isDefaultOrStatic(mthd))
                .map    (mthd -> createMethodFromMethodElement(targetType, mthd))
                .collect(toList());
    }
    
    private List<Case> extractTypeChoices(Type targetType, TypeElement typeElement) {
        return typeElement.getEnclosedElements().stream()
                .filter(elmt->elmt.getKind().equals(ElementKind.METHOD))
                .map   (elmt->((ExecutableElement)elmt))
                .filter(mthd->!mthd.isDefault())
                .filter(mthd->mthd.getSimpleName().toString().matches("^[A-Z].*$"))
                .filter(mthd->mthd.getReturnType() instanceof NoType)
                .map   (mthd->createChoiceFromMethod(targetType, mthd, typeElement.getEnclosedElements()))
                .collect(toList());
    }
    
    private Case createChoiceFromMethod(Type targetType, ExecutableElement method, List<? extends Element> elements) {
        var methodName = method.getSimpleName().toString();
        var params = method.getParameters().stream().map(p -> {
            var name       = p.getSimpleName().toString();
            var type       = typeOf(targetType, p.asType());
            var isNullable = (p.getAnnotation(Nullable.class) != null) ? false : true;
            var defValue   = (p.getAnnotation(DefaultTo.class) != null) ? p.getAnnotation(DefaultTo.class).value() : null;
            return new CaseParam(name, type, isNullable, defValue);
        }).collect(toList());
        
        var validateMethodName = "__validate" + methodName;
        var hasValidator = elements.stream()
                .filter(elmt -> elmt.getKind().equals(ElementKind.METHOD))
                .map   (elmt -> ((ExecutableElement)elmt))
                .filter(mthd -> mthd.getSimpleName().toString().equals(validateMethodName))
                .filter(mthd -> mthd.getTypeParameters().size() == method.getTypeParameters().size())
                .filter(mthd -> {
                    for (int i = 0; i < mthd.getTypeParameters().size(); i++) {
                        if (!mthd.getTypeParameters().get(i).equals(method.getTypeParameters().get(i)))
                            return false;
                    }
                    return true;
                })
                .findFirst()
                .isPresent();
        
        var choice = hasValidator ? new Case(methodName, validateMethodName, params) : new Case(methodName, params);
        return choice;
    }
    
    private Type typeOf(Type targetType, TypeMirror typeMirror) {
        if (typeMirror == null)
            return null;
        
        var typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType)
            return new Type(typeStr);
        
        if (typeMirror instanceof DeclaredType) {
            var typeElement  = ((TypeElement)((DeclaredType)typeMirror).asElement());
            var packageName  = getPackageName(typeElement);
            var typeName     = typeElement.getSimpleName().toString();
            var encloseClass = extractEnclosedClassName(typeElement, packageName, typeName);
            var generics     = extractGenericsFromTypeArguments(targetType, ((DeclaredType)typeMirror).getTypeArguments());
            var foundType    = new Type(packageName, encloseClass, typeName, generics);
            if (packageName.equals(Self.class.getPackage().getName()) && typeName.matches("^Self[0-9]?$"))
                return new Type(targetType.packageName(), targetType.encloseName(), targetType.simpleName(), generics);
            
            return foundType;
        }
        
        if (typeMirror instanceof TypeVariable) {
            var varType = (TypeVariable)typeMirror;
            return new Type(null, null, varType.toString());
        }
        
        return null;
    }
    
    private String extractEnclosedClassName(TypeElement typeElement, String packageName, String typeName) {
        String encloseClass = null;
        var qualifiedName = typeElement.getQualifiedName().toString();
        encloseClass  = (typeElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) &&  qualifiedName.endsWith("." + typeName)
                      ? qualifiedName.substring(0, qualifiedName.length() - typeName.length() - 1)
                      : null;
        encloseClass  = (typeElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) && encloseClass != null && encloseClass.startsWith(packageName + ".")
                      ? encloseClass.substring(packageName.length() + 1)
                      : null;
        return encloseClass;
    }
    
    private String getPackageName(TypeElement typeElement) {
        var element      = input.element();
        var elementUtils = input.elementUtils();
        var typePackage  = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        if (!typePackage.isEmpty())
            return typePackage;
        
        var packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        return packageName;
    }
    
}
