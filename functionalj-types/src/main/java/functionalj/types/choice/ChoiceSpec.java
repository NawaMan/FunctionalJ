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
import functionalj.types.DefaultValue;
import functionalj.types.Generic;
import functionalj.types.Nullable;
import functionalj.types.Required;
import functionalj.types.Serialize.To;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.Method;
import functionalj.types.choice.generator.model.Method.Kind;
import functionalj.types.choice.generator.model.MethodParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.input.InputElement;
import functionalj.types.input.InputMethodElement;
import functionalj.types.input.InputType;
import functionalj.types.input.InputTypeArgument;
import functionalj.types.input.InputTypeElement;
import functionalj.types.input.InputTypeParameterElement;
import functionalj.types.input.InputTypeVariable;

public class ChoiceSpec {
    
    private final InputElement element;
    
    public ChoiceSpec(InputElement element) {
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
        InputTypeElement typeElement       = element.asTypeElement();
        List<String>     localTypeWithLens = element.readLocalTypeWithLens();
        String           simpleName        = element.simpleName();
        boolean          isInterface       = element.isInterface();
        if (!isInterface) {
            element.error("Only an interface can be annotated with " + Choice.class.getSimpleName() + ": " + simpleName);
            return null;
        }
        List<Generic> generics      = extractTypeGenerics(null, typeElement);
        String        packageName   = typeElement.packageQualifiedName();
        String        sourceName    = typeElement.qualifiedName().substring(packageName.length() + 1);
        String        enclosedClass = extractEncloseClass(simpleName, sourceName);
        Type          sourceType    = new Type(packageName, enclosedClass, simpleName, generics);
        String        targetName    = element.targetName();
        Type          targetType    = new Type(packageName, null, targetName, generics);
        String        specField     = element.specifiedSpecField();
        if ((specField != null) && !specField.matches("^[A-Za-z_$][A-Za-z_$0-9]*$")) {
            element.error("Source spec field name is not a valid identifier: " + specField);
            return null;
        }
        String       tagMapKeyName = element.choiceTagMapKeyName();
        To           serialize     = element.specifiedSerialize();
        List<Case>   choices       = extractTypeChoices(targetType, typeElement);
        List<Method> methods       = extractTypeMethods(targetType, typeElement);
        boolean      publicFields  = element.specifiedPublicField();
        SourceSpec   sourceSpec    = new SourceSpec(targetName, sourceType, specField, publicFields, tagMapKeyName, serialize, generics, choices, methods, localTypeWithLens);
        return sourceSpec;
    }
    
    private String extractEncloseClass(String simpleName, String sourceName) {
        try {
            return sourceName.substring(0, sourceName.length() - simpleName.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    private boolean isDefaultOrStatic(InputMethodElement mthd) {
        return mthd.isDefault() || mthd.isStatic();
    }
    
    private Method createMethodFromMethodElement(Type targetType, InputMethodElement mthd) {
        Kind              kind       = mthd.isDefault() ? Method.Kind.DEFAULT : Method.Kind.STATIC;
        String            name       = mthd.simpleName();
        Type              type       = typeOf(targetType, mthd.returnType());
        List<MethodParam> params     = extractParameters(targetType, mthd);
        List<Generic>     generics   = extractGenerics(targetType, mthd.typeParameters());
        List<Type>        exceptions = mthd.thrownTypes().stream().map(t -> typeOf(targetType, t)).collect(toList());
        Method            method     = new Method(kind, name, type, params, generics, exceptions);
        return method;
    }
    
    private List<MethodParam> extractParameters(Type targetType, InputMethodElement mthd) {
        return mthd.parameters().stream().map(p -> {
            String paramName = p.simpleName();
            Type   paramType = typeOf(targetType, p.asType());
            return new MethodParam(paramName, paramType);
        }).collect(toList());
    }
    
    private boolean isPublicOrPackage(InputMethodElement mthd) {
        return mthd.isPublic() || !(mthd.isPrivate() && mthd.isProtected());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<Generic> extractTypeGenerics(Type targetType, InputTypeElement type) {
        List typeParameters = type.typeParameters();
        return extractGenerics(targetType, typeParameters);
    }
    
    private List<Generic> extractGenerics(Type targetType, List<? extends InputTypeParameterElement> typeParameters) {
        return typeParameters.stream().map(t -> parameterGeneric(targetType, t)).collect(toList());
    }
    
    private Generic parameterGeneric(Type targetType, InputTypeParameterElement t) {
        List<Type> boundTypes = t.bounds().stream().map(tm -> typeOf(targetType, tm)).collect(toList());
        String     paramName  = t.toString();
        String     bounds     = t.bounds().stream().map(bount -> {
            Type   typeOf     = typeOf(targetType, bount);
            String typeNameOf = typeOf.simpleName();
            return typeNameOf;
        }).collect(joining(" & "));
        
        String boundAsString = (boundTypes.isEmpty()) ? "" : " extends " + bounds;
        return new Generic(paramName, paramName + boundAsString, boundTypes);
    }
    
    private List<Generic> extractGenericsFromTypeArguments(Type targetType, List<InputTypeArgument> typeParameters) {
        return typeParameters.stream().map(typeArgument -> {
            // TODO - Take care of the bound
            InputType p = typeArgument.inputType();
            String paramName = p.toString();
            if (p.isTypeVariable()) {
                InputTypeVariable param = p.asTypeVariable();
                return new Generic(paramName, paramName + ((param.getLowerBound() == null) ? "" : " super " + param.getLowerBound()) + (param.getUpperBound().toString().equals("java.lang.Object") ? "" : " extends " + param.getUpperBound()), Stream.of(typeOf(targetType, param.getLowerBound()), typeOf(targetType, param.getUpperBound())).filter(Objects::nonNull).collect(toList()));
            } else {
                return new Generic(paramName);
            }
        }).collect(toList());
    }
    
    private List<Method> extractTypeMethods(Type targetType, InputTypeElement typeElement) {
        return typeElement.enclosedElements().stream().filter(elmt -> elmt.isMethodElement()).map(elmt -> elmt.asMethodElement()).filter(mthd -> !mthd.simpleName().startsWith("__")).filter(mthd -> isPublicOrPackage(mthd)).filter(mthd -> isDefaultOrStatic(mthd)).map(mthd -> createMethodFromMethodElement(targetType, mthd)).collect(toList());
    }
    
    private List<Case> extractTypeChoices(Type targetType, InputTypeElement typeElement) {
        try {
            return typeElement.enclosedElements().stream().filter(elmt -> elmt.isMethodElement()).map(elmt -> elmt.asMethodElement()).filter(mthd -> !mthd.isDefault()).filter(mthd -> mthd.simpleName().matches("^[A-Z].*$")).filter(mthd -> mthd.returnType().isNoType()).map(mthd -> createChoiceFromMethod(targetType, mthd, typeElement.enclosedElements())).collect(toList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
    
    private Case createChoiceFromMethod(Type targetType, InputMethodElement method, List<? extends InputElement> elements) {
        String methodName = method.simpleName();
        List<CaseParam> params = method.parameters().stream().map(param -> {
            String       name       = param.simpleName().toString();
            Type         type       = typeOf(targetType, param.asType());
            boolean      isNullable = (param.annotation(Nullable.class) != null);
            boolean      isRequired = (param.annotation(Required.class) != null);
            DefaultValue defValue   = (param.annotation(DefaultTo.class) != null) ? param.annotation(DefaultTo.class).value() : null;
            if (isNullable && isRequired) {
                element.error("Parameter cannot be both Required and Nullable: " + name);
            }
            return new CaseParam(name, type, isNullable, defValue);
        }).collect(toList());
        
        String                   validateMethodName = "__validate" + methodName;
        List<InputMethodElement> validateMethods    = elements.stream().filter(elmt -> elmt.isMethodElement()).map(elmt -> elmt.asMethodElement()).filter(mthd -> mthd.simpleName().equals(validateMethodName)).collect(toList());
        ensureValidatorParameters(method, validateMethods, validateMethodName);
        ensureValidatorModifier(validateMethods, validateMethodName);
        
        boolean hasValidator = hasValidator(method, validateMethods);
        Case    choice       = hasValidator ? new Case(methodName, validateMethodName, params) : new Case(methodName, params);
        return choice;
    }
    
    private void ensureValidatorModifier(List<InputMethodElement> validateMethods, String validateMethodName) {
        validateMethods.stream().filter(mthd -> {
            if (!mthd.isStatic()) {
                element.error("Validator method must be static: " + validateMethodName);
            }
            if (mthd.isPrivate()) {
                element.error("Validator method must not be private: " + validateMethodName);
            }
            return false;
        }).forEach(mthd -> {
        });
    }
    
    private void ensureValidatorParameters(InputMethodElement method, List<InputMethodElement> validateMethods, String validateMethodName) {
        validateMethods.stream().filter(mthd -> {
            int methodParamSize = method.typeParameters().size();
            int mthdParamSize = mthd.typeParameters().size();
            if (mthdParamSize != methodParamSize) {
                String expected = "expect " + methodParamSize + " but found " + mthdParamSize;
                element.error("Validator method must have the same parameters as the case: " + validateMethodName + ": " + expected);
                return true;
            }
            for (int i = 0; i < mthd.typeParameters().size(); i++) {
                InputTypeParameterElement methodParam = method.typeParameters().get(i);
                InputTypeParameterElement mthdParam   = mthd.typeParameters().get(i);
                if (!mthdParam.equals(methodParam)) {
                    String expected = "parameter " + i + " expected to be " + methodParam + " but found to be " + mthdParam;
                    element.error("Validator method must have the same parameters as the case: " + validateMethodName + ": " + expected);
                    return true;
                }
            }
            return false;
        }).forEach(mthd -> {
            // TODO -- Review this .... Do nothing?
        });
    }
    
    private boolean hasValidator(InputMethodElement method, List<InputMethodElement> validateMethods) {
        return validateMethods.stream().filter(mthd -> mthd.typeParameters().size() == method.typeParameters().size()).filter(mthd -> {
            for (int i = 0; i < mthd.typeParameters().size(); i++) {
                if (!mthd.typeParameters().get(i).equals(method.typeParameters().get(i)))
                    return false;
            }
            return true;
        }).findFirst().isPresent();
    }
    
    private Type typeOf(Type targetType, InputType typeMirror) {
        if (typeMirror == null)
            return null;
        String typeStr = typeMirror.toString();
        if (typeMirror.isPrimitiveType())
            return new Type(typeStr);
        if (typeMirror.isDeclaredType()) {
            InputTypeElement typeElement  = typeMirror.asDeclaredType().asTypeElement();
            String           packageName  = getPackageName(typeElement);
            String           typeName     = typeElement.simpleName();
            String           encloseClass = extractEnclosedClassName(typeElement, packageName, typeName);
            List<Generic>    generics     = extractGenericsFromTypeArguments(targetType, typeMirror.asDeclaredType().typeArguments());
            Type             foundType    = new Type(packageName, encloseClass, typeName, generics);
            if (packageName.equals(Self.class.getPackage().getName()) && typeName.matches("^Self[0-9]?$"))
                return new Type(targetType.packageName(), targetType.encloseName(), targetType.simpleName(), generics);
            return foundType;
        }
        if (typeMirror.isTypeVariable()) {
            InputTypeVariable varType = typeMirror.asTypeVariable();
            return new Type(null, null, varType.toString());
        }
        return null;
    }
    
    private String extractEnclosedClassName(InputTypeElement typeElement, String packageName, String typeName) {
        String encloseClass  = null;
        String qualifiedName = typeElement.qualifiedName().toString();
        encloseClass = (typeElement.enclosingElement().kind() != ElementKind.PACKAGE) && qualifiedName.endsWith("." + typeName) ? qualifiedName.substring(0, qualifiedName.length() - typeName.length() - 1) : null;
        encloseClass = (typeElement.enclosingElement().kind() != ElementKind.PACKAGE) && encloseClass != null && encloseClass.startsWith(packageName + ".") ? encloseClass.substring(packageName.length() + 1) : null;
        return encloseClass;
    }
    
    private String getPackageName(InputTypeElement typeElement) {
        String typePackage = typeElement.packageQualifiedName();
        if (!typePackage.isEmpty())
            return typePackage;
        String packageName = element.packageQualifiedName();
        return packageName;
    }
}
