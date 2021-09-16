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
package functionalj.types.choice;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.lang.model.element.ElementKind;

import functionalj.types.Choice;
import functionalj.types.DefaultTo;
import functionalj.types.Generic;
import functionalj.types.Nullable;
import functionalj.types.Required;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.Method;
import functionalj.types.choice.generator.model.MethodParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.input.SpecElement;
import functionalj.types.input.SpecMethodElement;
import functionalj.types.input.SpecTypeElement;
import functionalj.types.input.SpecTypeMirror;
import functionalj.types.input.SpecTypeParameterElement;
import lombok.val;


public class ChoiceSpec {
    
    private final SpecElement element;
    private boolean hasError = false;
    
    public ChoiceSpec(SpecElement element) {
        this.element = element;
    }
    
    public String packageName() {
        return element.packageName();
    }
    
    public String targetName() {
        return element.targetName();
    }
    
    public String specifiedTargetName() {
        return element.specifiedTargetName();
    }
    
    public SourceSpec sourceSpec() {
        val typeElement       = element.asTypeElement();
        val localTypeWithLens = element.readLocalTypeWithLens();
        val simpleName        = element.simpleName();
        val isInterface       = element.isInterface();
        if (!isInterface) {
            element.error("Only an interface can be annotated with " + Choice.class.getSimpleName() + ": " + simpleName);
            return null;
        }
        
        val generics = extractTypeGenerics(null, typeElement);
        
        val packageName    = typeElement.packageQualifiedName();
        val sourceName     = typeElement.getQualifiedName().substring(packageName.length() + 1 );
        val enclosedClass  = extractEncloseClass(simpleName, sourceName);
        val sourceType     = new Type(packageName, enclosedClass, simpleName, generics);
        val targetName     = element.targetName();
        val targetType     = new Type(packageName, null, targetName, generics);
        
        val specField = element.specifiedSpecField();
        if ((specField != null) && !specField.matches("^[A-Za-z_$][A-Za-z_$0-9]*$")) {
            element.error("Source spec field name is not a valid identifier: " + specField);
            return null;
        }
        
        val tagMapKeyName = element.choiceTagMapKeyName();
        val serialize     = element.specifiedSerialize();
        
        val choices      = extractTypeChoices(targetType, typeElement);
        val methods      = extractTypeMethods(targetType, typeElement);
        val publicFields = element.specifiedPublicField();
        val sourceSpec   = new SourceSpec(targetName, sourceType, specField, publicFields, tagMapKeyName, serialize, generics, choices, methods, localTypeWithLens);
        return sourceSpec;
    }
    
    public boolean hasError() {
        return hasError;
    }
    
    private String extractEncloseClass(String simpleName, String sourceName) {
        try {
            return sourceName.substring(0, sourceName.length() - simpleName.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    private boolean isDefaultOrStatic(SpecMethodElement mthd) {
        return mthd.isDefault()
            || mthd.isStatic();
    }
    
    private Method createMethodFromMethodElement(Type targetType, SpecMethodElement mthd) {
        val kind       = mthd.isDefault() ? Method.Kind.DEFAULT : Method.Kind.STATIC;
        val name       = mthd.simpleName();
        
        val type       = typeOf(targetType, mthd.getReturnType());
        val params     = extractParameters(targetType, mthd);
        val generics   = extractGenerics(targetType, mthd.getTypeParameters());
        val exceptions = mthd.getThrownTypes().stream().map(t -> typeOf(targetType, t)).collect(toList());
        val method = new Method(kind, name, type, params, generics, exceptions);
        return method;
    }
    
    private List<MethodParam> extractParameters(Type targetType, SpecMethodElement mthd) {
        return mthd
                .getParameters().stream()
                .map(p -> {
                    val paramName = p.simpleName();
                    val paramType = typeOf(targetType, p.asTypeMirror());
                    return new MethodParam(paramName, paramType);
                }).collect(toList());
    }
    
    private boolean isPublicOrPackage(SpecMethodElement mthd) {
        return mthd.isPublic()
          || !(mthd.isPrivate()
            && mthd.isProtected());
    }
    
    private List<Generic> extractTypeGenerics(Type targetType, SpecTypeElement type) {
        val typeParameters = type.typeParameters();
        return extractGenerics(targetType, typeParameters);
    }
    
    private List<Generic> extractGenerics(Type targetType, List<? extends SpecTypeParameterElement> typeParameters) {
        return typeParameters.stream()
                .map(t -> parameterGeneric(targetType, t))
                .collect(toList());
    }
    
    private Generic parameterGeneric(Type targetType, SpecTypeParameterElement t) {
        val boundTypes = t.getBounds().stream()
                .map(tm -> typeOf(targetType, tm))
                .collect(toList());
        val paramName = t.toString();
        val bounds    = t.getBounds().stream()
                        .map(bount -> {
                            val typeOf     = typeOf(targetType, bount);
                            val typeNameOf = typeOf.simpleName();
                            return typeNameOf;
                        }).collect(joining(" & "));
        val boundAsString = (boundTypes.isEmpty()) ? "" : " extends " + bounds;
        return new Generic(
                paramName,
                paramName + boundAsString,
                boundTypes);
    }
    
    private List<Generic> extractGenericsFromTypeArguments(Type targetType, List<? extends SpecTypeMirror> typeParameters) {
        return typeParameters.stream()
                .map(p -> {
                    val paramName = p.toString();
                    if (p.isTypeVariable()) {
                        val param = p.asTypeVariable();
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
    
    private List<Method> extractTypeMethods(Type targetType, SpecTypeElement typeElement) {
        return typeElement.enclosedElements().stream()
                .filter (elmt -> elmt.isMethodElement())
                .map    (elmt -> elmt.asMethodElement())
                .filter (mthd -> !mthd.simpleName().startsWith("__"))
                .filter (mthd -> isPublicOrPackage(mthd))
                .filter (mthd -> isDefaultOrStatic(mthd))
                .map    (mthd -> createMethodFromMethodElement(targetType, mthd))
                .collect(toList());
    }
    
    private List<Case> extractTypeChoices(Type targetType, SpecTypeElement typeElement) {
        try {
            return typeElement.enclosedElements().stream()
                    .filter (elmt -> elmt.isMethodElement())
                    .map    (elmt -> elmt.asMethodElement())
                    .filter (mthd -> !mthd.isDefault())
                    .filter (mthd -> mthd.simpleName().matches("^[A-Z].*$"))
                    .filter (mthd -> mthd.getReturnType().isNoType())
                    .map    (mthd -> createChoiceFromMethod(targetType, mthd, typeElement.enclosedElements()))
                    .collect(toList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
    
    private Case createChoiceFromMethod(Type targetType, SpecMethodElement method, List<? extends SpecElement> elements) {
        val methodName = method.simpleName();
        
        List<CaseParam> params
            = method
            .getParameters().stream()
            .map(param -> {
                val name       = param.simpleName().toString();
                val type       = typeOf(targetType, param.asTypeMirror());
                val isNullable = (param.getAnnotation(Nullable.class) != null);
                val isRequired = (param.getAnnotation(Required.class) != null);
                val defValue   = (param.getAnnotation(DefaultTo.class) != null)
                               ?  param.getAnnotation(DefaultTo.class).value()
                               :  null;
                
                if (isNullable && isRequired) {
                    element.error("Parameter cannot be both Required and Nullable: " + name);
                }
                return new CaseParam(name, type, isNullable, defValue);
            }).collect(toList());
        
        val validateMethodName = "__validate" + methodName;
        val validateMethods 
                = elements.stream()
                .filter (elmt -> elmt.isMethodElement())
                .map    (elmt -> elmt.asMethodElement())
                .filter (mthd -> mthd.simpleName().equals(validateMethodName))
                .collect(toList());
        
        ensureValidatorParameters(method, validateMethods, validateMethodName);
        ensureValidatorModifier  (validateMethods, validateMethodName);
        val hasValidator = hasValidator(method, validateMethods);
        
        Case choice
                = hasValidator
                ? new Case(methodName, validateMethodName, params)
                : new Case(methodName, params);
        return choice;
    }
    
    private void ensureValidatorModifier(List<SpecMethodElement> validateMethods, String validateMethodName) {
        validateMethods.stream()
        .filter(mthd -> {
            if (!mthd.isStatic()) {
                element.error("Validator method must be static: " + validateMethodName);
            }
            if (mthd.isPrivate()) {
                element.error("Validator method must not be private: " + validateMethodName);
            }
            return false;
        })
        .forEach(mthd -> {});
    }
    
    private void ensureValidatorParameters(SpecMethodElement method, List<SpecMethodElement> validateMethods, String validateMethodName) {
        validateMethods.stream()
        .filter(mthd -> {
            int methodParamSize = method.getTypeParameters().size();
            int mthdParamSize   = mthd.getTypeParameters().size();
            if (mthdParamSize != methodParamSize) {
                val expected = "expect " + methodParamSize + " but found " + mthdParamSize;
                element.error("Validator method must have the same parameters as the case: " + validateMethodName + ": " + expected);
                return true;
            }
            
            for (int i = 0; i < mthd.getTypeParameters().size(); i++) {
                val methodParam = method.getTypeParameters().get(i);
                val mthdParam   = mthd.getTypeParameters().get(i);
                if (!mthdParam.equals(methodParam)) {
                    val expected = "parameter " + i + " expected to be " + methodParam + " but found to be " + mthdParam;
                    element.error("Validator method must have the same parameters as the case: " + validateMethodName + ": " + expected);
                    return true;
                }
            }
            return false;
        })
        .forEach(mthd -> {});
    }
    
    private boolean hasValidator(SpecMethodElement method, List<SpecMethodElement> validateMethods) {
        return validateMethods.stream()
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
    }
    
    private Type typeOf(Type targetType, SpecTypeMirror typeMirror) {
        if (typeMirror == null)
            return null;
        
        val typeStr = typeMirror.toString();
        if (typeMirror.isPrimitiveType())
            return new Type(typeStr);
        
        if (typeMirror.isDeclaredType()) {
            val typeElement  = typeMirror.asDeclaredType();
            val packageName  = getPackageName(typeElement);
            val typeName     = typeElement.simpleName().toString();
            val encloseClass = extractEnclosedClassName(typeElement, packageName, typeName);
            val generics     = extractGenericsFromTypeArguments(targetType, typeMirror.getTypeArguments());
            val foundType    = new Type(packageName, encloseClass, typeName, generics);
            if (packageName.equals(Self.class.getPackage().getName()) && typeName.matches("^Self[0-9]?$"))
                return new Type(targetType.packageName(), targetType.encloseName(), targetType.simpleName(), generics);
            
            return foundType;
        }
        
        if (typeMirror.isTypeVariable()) {
            val varType = typeMirror.asTypeVariable();
            return new Type(null, null, varType.toString());
        }
        
        return null;
    }
    
    private String extractEnclosedClassName(SpecTypeElement typeElement, String packageName, String typeName) {
        String encloseClass = null;
        val qualifiedName = typeElement.getQualifiedName().toString();
        encloseClass  = (typeElement.enclosingElement().getKind() != ElementKind.PACKAGE) &&  qualifiedName.endsWith("." + typeName)
                      ? qualifiedName.substring(0, qualifiedName.length() - typeName.length() - 1)
                      : null;
        encloseClass  = (typeElement.enclosingElement().getKind() != ElementKind.PACKAGE) && encloseClass != null && encloseClass.startsWith(packageName + ".")
                      ? encloseClass.substring(packageName.length() + 1)
                      : null;
        return encloseClass;
    }
    
    private String getPackageName(SpecTypeElement typeElement) {
        val typePackage = typeElement.packageQualifiedName();
        if (!typePackage.isEmpty())
            return typePackage;
        
        val packageName = element.packageQualifiedName();
        return packageName;
    }
    
}
