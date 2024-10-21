// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct;

import static functionalj.types.struct.features.FeatureSerialization.validateSerialization;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.lang.model.element.ElementKind;

import functionalj.types.DefaultTo;
import functionalj.types.DefaultValue;
import functionalj.types.Generic;
import functionalj.types.JavaVersionInfo;
import functionalj.types.Nullable;
import functionalj.types.Required;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import functionalj.types.Type;
import functionalj.types.input.InputElement;
import functionalj.types.input.InputMethodElement;
import functionalj.types.input.InputRecordComponentElement;
import functionalj.types.input.InputType;
import functionalj.types.input.InputTypeElement;
import functionalj.types.input.InputTypeParameterElement;
import functionalj.types.struct.generator.Callable;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.Parameter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;

public class SourceSpecBuilder {
    
    private static final EnumSet<ElementKind> typeElementKinds = EnumSet.of(ElementKind.ENUM, ElementKind.CLASS, ElementKind.ANNOTATION_TYPE, ElementKind.INTERFACE, ElementKind.METHOD);
    
    private final InputElement element;
    
    public SourceSpecBuilder(InputElement element) {
        this.element = element;
    }
    
    public String packageName() {
        return element.packageName();
    }
    
    public String targetName() {
        return element.targetName();
    }
    
    public SourceSpec sourceSpec() {
        if (element.isTypeElement())
            return extractSourceSpecType(element);
        if (element.isMethodElement())
            return extractSourceSpecMethod(element);
        throw new IllegalArgumentException("Struct annotation is only support class or method.");
    }
    
    private SourceSpec extractSourceSpecType(InputElement element) {
        InputTypeElement type        = element.asTypeElement();
        String           simpleName  = element.simpleName();
        boolean          isInterface = element.isInterface();
        boolean          isClass     = element.isClass();
        boolean          isRecord    = element.isRecord();
        if (!isInterface && !isClass && !isRecord) {
            String structName = Struct.class.getSimpleName();
            String message = format("Only a class or interface or record can be annotated with %s: %s. kind=%s", structName, simpleName, element.kind());
            element.error(message);
            return null;
        }
        
        List<String> localTypeWithLens = element.readLocalTypeWithLens();
        List<Getter> getters           = extractGetters(type);
        if (getters.stream().anyMatch(Objects::isNull))
            return null;
        
        List<Callable> methods     = enclosedMethods(element, type);
        String         packageName = type.packageQualifiedName();
        String         encloseName = element.enclosingElement().simpleName();
        String         sourceName  = type.qualifiedName().toString().substring(packageName.length() + 1);
        Struct         struct      = element.annotation(Struct.class);
        String         targetName  = targetName();
        String         specField   = struct.specField();
        Configurations configures  = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        if (!ensureNoArgConstructorWhenRequireFieldExists(element, getters, packageName, targetName, configures))
            return null;
        if (!ensureSerializationMethodMatch(type, getters, packageName, targetName, configures))
            return null;
        // TODO - Should look for a validator method.
        String validatorName = (String) null;
        
        JavaVersionInfo versionInfo = element.versionInfo();
        try {
            return new SourceSpec(versionInfo, sourceName, packageName, encloseName, targetName, packageName, isClass, isInterface, specField, validatorName, configures, getters, methods, localTypeWithLens);
        } catch (Exception e) {
            element.error("Problem generating the class: " + packageName + "." + targetName + ": " + e.getMessage() + ":" + e.getClass() + stream(e.getStackTrace()).map(st -> "\n    @" + st).collect(joining()));
            return null;
        }
    }
    
    private List<Callable> enclosedMethods(InputElement element, InputTypeElement type) {
        return type
                .enclosedElements().stream()
                .filter (elmt -> elmt.isMethodElement())
                .map    (elmt -> elmt.asMethodElement())
                .filter (mthd -> (mthd.isDefault() || mthd.isStatic()) && !mthd.isAbstract() && !mthd.isPrivate())
                .map    (mthd -> extractMethodSpec(element, mthd))
                .filter (mthd -> mthd != null)
                .collect(toList());
    }
    
    private List<Getter> extractGetters(InputTypeElement type) {
        if (type.isClass()) {
            return extractGettersFromClass(type);
        }
        if (type.isInterface()) {
            return extractGettersFromInterface(type);
        }
        if (type.isRecord()) {
            return extractGettersFromRecord(type);
        }
        
        throw new IllegalStateException(
                format("The type are not a class, interface nor record when it should: type=%s of kind=%s", 
                        type.simpleName(),
                        type.kind()));
    }
    
    private List<Getter> extractGettersFromClass(InputTypeElement type) {
        return type
                .enclosedElements().stream()
                .filter (elmt -> elmt.isMethodElement())
                .map    (elmt -> elmt.asMethodElement())
                .filter (mthd -> !mthd.isDefault())
                .filter (mthd -> mthd.isAbstract())
                .filter (mthd -> !(mthd.returnType().isNoType()))
                .filter (mthd -> mthd.parameters().isEmpty())
                .map    (mthd -> createGetterFromMethod(type, mthd))
                .collect(toList());
    }
    
    private List<Getter> extractGettersFromInterface(InputTypeElement type) {
        return type
                .enclosedElements().stream()
                .filter (elmt -> elmt.isMethodElement())
                .map    (elmt -> elmt.asMethodElement())
                .filter (mthd -> !mthd.isDefault())
                .filter (mthd -> !(mthd.returnType().isNoType()))
                .filter (mthd -> mthd.parameters().isEmpty())
                .map    (mthd -> createGetterFromMethod(type, mthd))
                .collect(toList());
    }
    
    private List<Getter> extractGettersFromRecord(InputTypeElement type) {
        return type
                .enclosedElements().stream()
                .filter (elmt -> elmt.isRecordComponentElement())
                .map    (elmt -> elmt.asRecordComponentElement())
                .map    (recd -> createGetterFromRecordComponent(type, recd))
                .collect(toList());
    }
    
    private Callable extractMethodSpec(InputElement element, InputMethodElement mthdElement) {
        Function<InputType, Type> extractType   = (Function<InputType, Type>) (typeMirror -> getType(element, typeMirror));
        String                    name          = mthdElement.simpleName().toString();
        Type                      type          = getType(element, mthdElement.returnType());
        boolean                   isVarArgs     = mthdElement.isVarArgs();
        Accessibility             accessibility = mthdElement.accessibility();
        Scope                     scope         = mthdElement.scope();
        Modifiability             modifiability = mthdElement.modifiability();
        Concrecity                concrecity    = mthdElement.concrecity();
        List<Generic>             generics      = mthdElement.typeParameters().stream().map(t -> getGenericFromTypeParameter(element, t)).collect(toList());
        List<Parameter>           parameters    = mthdElement.parameters().stream().map(param -> new Parameter(param.simpleName().toString(), getType(element, param.asType()))).collect(toList());
        List<Type>                exceptions    = mthdElement.thrownTypes().stream().map(extractType).collect(toList());
        return new Callable(name, type, isVarArgs, accessibility, scope, modifiability, concrecity, parameters, generics, exceptions);
    }
    
    private Generic getGenericFromTypeParameter(InputElement element, InputTypeParameterElement typeParameter) {
        String     name   = typeParameter.simpleName();
        List<Type> bounds = typeParameter.bounds().stream().map(bound -> getType(element, bound)).collect(toList());
        return new Generic(name, null, bounds);
    }
    
    private SourceSpec extractSourceSpecMethod(InputElement element) {
        InputMethodElement method            = element.asMethodElement();
        String             packageName       = element.packageName();
        String             encloseName       = element.enclosingElement().simpleName();
        Struct             struct            = element.annotation(Struct.class);
        String             specTargetName    = targetName();
        String             specField         = struct.specField();
        List<String>       localTypeWithLens = element.readLocalTypeWithLens();
        Boolean            isClass           = (Boolean) null;
        Boolean            isInterface       = (Boolean) null;
        String             sourceName        = (String) null;
        String             superPackage      = packageName;
        boolean            isValidate        = isBooleanStringOrValidation(method.returnType());
        String             validatorName     = isValidate ? method.simpleName() : null;
        boolean            isStatic          = method.isStatic();
        boolean            isPrivate         = method.isPrivate();
        if (isValidate && (!isStatic || isPrivate)) {
            method.error("Validatable struct must come from static and non-private method.");
            return null;
        }
        
        Configurations configures = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        
        List<Getter> getters = method.parameters().stream().map(parameter -> createGetterFromParameter(element, parameter)).filter(getter -> nonNull(getter)).collect(toList());
        if (!ensureNoArgConstructorWhenRequireFieldExists(element, getters, packageName, specTargetName, configures))
            return null;
        
        JavaVersionInfo versionInfo = element.versionInfo();
        
        try {
            return new SourceSpec(versionInfo, sourceName, superPackage, encloseName, specTargetName, packageName, isClass, isInterface, specField, validatorName, configures, getters, emptyList(), localTypeWithLens);
        } catch (Exception e) {
            String stacktraces = stream(e.getStackTrace()).map(stacktrace -> "\n    @" + stacktrace).collect(joining());
            String errMsg      = format("Problem generating the class: %s.%s: %s:%s%s", packageName, specTargetName, e.getMessage(), e.getClass(), stacktraces);
            element.error(errMsg);
            return null;
        }
    }
    
    private boolean isBooleanStringOrValidation(InputType returnType) {
        if (returnType.isPrimitiveType()) {
            if ("boolean".equals(returnType.getToString()))
                return true;
        }
        if (returnType.isDeclaredType()) {
            InputTypeElement typeElement = returnType.asDeclaredType().asTypeElement();
            String           fullName    = typeElement.qualifiedName();
            if ("java.lang.String".equals(fullName))
                return true;
            if ("functionalj.result.ValidationException".equals(fullName))
                return true;
        }
        return false;
    }
    
    private boolean ensureNoArgConstructorWhenRequireFieldExists(InputElement element, List<Getter> getters, String packageName, String specTargetName, SourceSpec.Configurations configures) {
        if (!configures.generateNoArgConstructor)
            return true;
        if (getters.stream().noneMatch(Getter::isRequired))
            return true;
        String field  = getters.stream().filter(Getter::isRequired).findFirst().get().name();
        String errMsg = format("No arg constructor cannot be generate when at least one field is require: %s.%s -> field: %s", packageName, specTargetName, field);
        element.error(errMsg);
        return false;
    }
    
    private boolean ensureSerializationMethodMatch(InputTypeElement type, List<Getter> getters, String packageName, String specTargetName, Configurations configures) {
        String errMsg = validateSerialization(element, type, getters, packageName, specTargetName, configures);
        if (errMsg == null)
            return true;
        
        type.error(errMsg);
        return false;
    }
    
    private Getter createGetterFromParameter(InputElement element, InputElement p) {
        String       name        = p.simpleName();
        Type         type        = getType(element, p.asType());
        boolean      isPrimitive = type.isPrimitive();
        boolean      isNullable  = ((p.annotation(Nullable.class) != null) || (p.annotation(DefaultTo.class) != null));
        boolean      isRequired  = (p.annotation(Required.class)  != null);
        DefaultValue defTo       = (p.annotation(DefaultTo.class) != null) ? p.annotation(DefaultTo.class).value() : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
        DefaultValue defValue    = (DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(type) : defTo;
        if (!DefaultValue.isSuitable(type, defValue)) {
            element.error("Default value is not suitable for the type: " + type.fullName() + " -> DefaultTo " + defValue);
            return null;
        }
        if (isNullable && isRequired) {
            element.error("Parameter cannot be both Required and Nullable: " + name);
            return null;
        }
        Getter getter = new Getter(name, type, isNullable, defValue);
        return getter;
    }
    
    private Configurations extractConfigurations(InputElement element, Struct struct) {
        Configurations configures = new Configurations();
        configures.coupleWithDefinition            = struct.coupleWithDefinition();
        configures.generateNoArgConstructor        = struct.generateNoArgConstructor();
        configures.generateRequiredOnlyConstructor = struct.generateRequiredOnlyConstructor();
        configures.generateAllArgConstructor       = struct.generateAllArgConstructor();
        configures.generateLensClass               = struct.generateLensClass();
        configures.generateBuilderClass            = struct.generateBuilderClass();
        configures.publicFields                    = struct.publicFields();
        configures.publicConstructor               = struct.publicConstructor();
        configures.toStringTemplate                = struct.generateToString() ? struct.toStringTemplate() : null;
        configures.serialize                       = (struct.serialize() != null) ? struct.serialize() : Serialize.To.NOTHING;
        if (!configures.generateNoArgConstructor && !configures.generateAllArgConstructor) {
            element.error("generateNoArgConstructor and generateAllArgConstructor must be be false at the same time.");
            return null;
        }
        return configures;
    }
    
    private Getter createGetterFromMethod(InputElement element, InputMethodElement method) {
        try {
            String       methodName  = method.simpleName().toString();
            Type         returnType  = getType(element, method.returnType());
            boolean      isPrimitive = returnType.isPrimitive();
            boolean      isNullable  = ((method.annotation(Nullable.class) != null) || (method.annotation(DefaultTo.class) != null));
            boolean      isRequired  = (method.annotation(Required.class) != null);
            DefaultValue defTo       = (method.annotation(DefaultTo.class) != null) ? method.annotation(DefaultTo.class).value() : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
            DefaultValue defValue    = ((DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(returnType) : defTo);
            if (!DefaultValue.isSuitable(returnType, defValue)) {
                element.error("Default value is not suitable for the type: " + returnType.fullName() + " -> DefaultTo " + defTo);
                return null;
            }
            if (isNullable && isRequired) {
                element.error("Parameter cannot be both Required and Nullable: " + methodName);
                return null;
            }
            Getter getter = new Getter(methodName, returnType, isNullable, defValue);
            return getter;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    private Getter createGetterFromRecordComponent(InputElement element, InputRecordComponentElement recordComponent) {
        try {
            String       name = recordComponent.simpleName().toString();
            Type         type = getType(element, recordComponent.asType());
            boolean      isPrimitive = type.isPrimitive();
            boolean      isNullable  = ((recordComponent.annotation(Nullable.class) != null) || (recordComponent.annotation(DefaultTo.class) != null));
            boolean      isRequired  = (recordComponent.annotation(Required.class) != null);
            DefaultValue defTo       = (recordComponent.annotation(DefaultTo.class) != null) ? recordComponent.annotation(DefaultTo.class).value() : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
            DefaultValue defValue    = ((DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(type) : defTo);
            if (!DefaultValue.isSuitable(type, defValue)) {
                element.error("Default value is not suitable for the type: " + type.fullName() + " -> DefaultTo " + defTo);
                return null;
            }
            if (isNullable && isRequired) {
                element.error("Parameter cannot be both Required and Nullable: " + name);
                return null;
            }
            
            Getter getter = new Getter(name, type, isNullable, defValue);
            return getter;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }
    
    private Type getType(InputElement element, InputType typeMirror) {
        String typeStr = typeMirror.getToString();
        if (typeMirror.isPrimitiveType())
            return Type.primitiveTypes.get(typeStr);
        
        if (typeMirror.isDeclaredType()) {
            InputTypeElement typeElement = typeMirror.asDeclaredType().asTypeElement();
            String           typeName    = typeElement.simpleName();
            if (typeName.equals("String"))
                return Type.STRING;
            
            List<Generic> generics = typeMirror.asDeclaredType().typeArguments().stream().map(typeArg -> {
                Type type = getType(element, typeArg.inputType());
                // TODO - Take care of the bound.
                return new Generic(type);
            }).collect(toList());
            
            String       packageName = getPackageName(element, typeElement);
            InputElement encloseElmt = typeElement.enclosingElement();
            String       encloseName = typeElementKinds.contains(encloseElmt.kind()) ? encloseElmt.simpleName().toString() : null;
            return new Type(packageName, encloseName, typeName, generics);
        }
        return Type.newVirtualType(typeMirror.getToString());
    }
    
    private String getPackageName(InputElement element, InputTypeElement typeElement) {
        String typePackage = typeElement.packageQualifiedName();
        if (!typePackage.isEmpty())
            return typePackage;
        
        String packageName = element.packageQualifiedName();
        return packageName;
    }
}
