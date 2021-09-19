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
import functionalj.types.Nullable;
import functionalj.types.Required;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import functionalj.types.Type;
import functionalj.types.input.InputElement;
import functionalj.types.input.InputMethodElement;
import functionalj.types.input.InputTypeElement;
import functionalj.types.input.InputType;
import functionalj.types.input.InputTypeParameterElement;
import functionalj.types.struct.generator.Callable;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.Parameter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import lombok.val;


public class SourceSpecBuilder {
    
    private static final EnumSet<ElementKind> typeElementKinds = EnumSet.of(
            ElementKind.ENUM,
            ElementKind.CLASS,
            ElementKind.ANNOTATION_TYPE,
            ElementKind.INTERFACE,
            ElementKind.METHOD);
    
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
        throw new IllegalArgumentException("Record annotation is only support class or method.");
    }
    private SourceSpec extractSourceSpecType(InputElement element) {
        val type        = element.asTypeElement();
        val simpleName  = element.simpleName();
        val isInterface = element.isInterface();
        val isClass     = element.isClass();
        
        if (!isInterface && !isClass) {
            val structName = Struct.class.getSimpleName();
            val message    = format("Only a class or interface can be annotated with %s: %s", structName, simpleName);
            element.error(message);
            return null;
        }
        
        val localTypeWithLens = element.readLocalTypeWithLens();
        
        List<Getter> getters = type.enclosedElements().stream()
                .filter (elmt -> elmt.isMethodElement())
                .map    (elmt -> elmt.asMethodElement())
                .filter (mthd -> !mthd.isDefault())
                .filter (mthd -> !isClass || mthd.isAbstract())
                .filter (mthd -> !(mthd.getReturnType().isNoType()))
                .filter (mthd -> mthd.getParameters().isEmpty())
                .map    (mthd -> createGetterFromMethod(element, mthd))
                .collect(toList());
        
        if (getters.stream().anyMatch(Objects::isNull))
            return null;
        
        List<Callable> methods = type.enclosedElements().stream()
                .filter (elmt -> elmt.isMethodElement())
                .map    (elmt -> elmt.asMethodElement())
                .filter (mthd -> (mthd.isDefault() || mthd.isStatic()) && !mthd.isAbstract() && !mthd.isPrivate())
                .map    (mthd -> extractMethodSpec(element, mthd))
                .filter (mthd -> mthd != null)
                .collect(toList());
        
        val packageName = type.packageQualifiedName();
        val encloseName = element.enclosingElement().simpleName();
        val sourceName  = type.getQualifiedName().toString().substring(packageName.length() + 1 );
        val struct      = element.annotation(Struct.class);
        val targetName  = targetName();
        val specField   = struct.specField();
        
        val configures = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        
        if (!ensureNoArgConstructorWhenRequireFieldExists(element, getters, packageName, targetName, configures))
            return null;
        
        if (!ensureSerializationMethodMatch(type, getters, packageName, targetName, configures))
            return null;
        
        // TODO - Should look for a validator method.
        val validatorName = (String)null;
        
        try {
            return new SourceSpec(sourceName, packageName, encloseName, targetName, packageName, isClass, specField, validatorName, configures, getters, methods, localTypeWithLens);
        } catch (Exception e) {
            element.error("Problem generating the class: "
                            + packageName + "." + targetName
                            + ": "  + e.getMessage()
                            + ":"   + e.getClass()
                            + stream(e.getStackTrace())
                                .map(st -> "\n    @" + st)
                                .collect(joining()));
            return null;
        }
    }
    
    private Callable extractMethodSpec(InputElement element, InputMethodElement mthdElement) {
        val extractType = (Function<InputType, Type>)(typeMirror -> getType(element, typeMirror));
        
        val name          = mthdElement.simpleName().toString();
        val type          = getType(element, mthdElement.getReturnType());
        val isVarArgs     = mthdElement.isVarArgs();
        val accessibility = mthdElement.accessibility();
        val scope         = mthdElement.scope();
        val modifiability = mthdElement.modifiability();
        val concrecity    = mthdElement.concrecity();
        
        val generics = mthdElement.getTypeParameters().stream()
                .map(t -> getGenericFromTypeParameter(element, t))
                .collect(toList());
        val parameters = mthdElement
                .getParameters().stream()
                .map(param -> new Parameter(param.simpleName().toString(), getType(element, param.asTypeMirror())))
                .collect(toList());
        val exceptions = mthdElement
                .getThrownTypes().stream()
                .map(extractType)
                .collect(toList());
        
        return new Callable(name, type, isVarArgs, accessibility, scope, modifiability, concrecity, parameters, generics, exceptions);
    }

    private Generic getGenericFromTypeParameter(InputElement element, InputTypeParameterElement typeParameter){
        val name   = typeParameter.simpleName();
        val bounds = typeParameter.getBounds().stream()
                    .map    (bound -> getType(element, bound))
                    .collect(toList());
        return new Generic(name, null, bounds);
    }
    
    private SourceSpec extractSourceSpecMethod(InputElement element) {
        val method         = element.asMethodElement();
        val packageName    = element.packageName();
        val encloseName    = element.enclosingElement().simpleName();
        val struct         = element.annotation(Struct.class);
        val specTargetName = targetName();
        val specField      = struct.specField();
        
        val localTypeWithLens = element.readLocalTypeWithLens();
        
        val isClass      = (Boolean)null;
        val sourceName   = (String)null;
        val superPackage = packageName;
        
        val isValidate    = isBooleanStringOrValidation(method.getReturnType());
        val validatorName = isValidate ? method.simpleName() : null;
        val isStatic      = method.isStatic();
        val isPrivate     = method.isPrivate();
        if (isValidate && (!isStatic || isPrivate)) {
            method.error("Validatable struct must come from static and non-private method.");
            return null;
        }
        
        val configures = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        
        val getters = method.getParameters().stream()
                .map    (parameter -> createGetterFromParameter(element, parameter))
                .filter (getter    -> nonNull(getter))
                .collect(toList());
        
        if (!ensureNoArgConstructorWhenRequireFieldExists(element, getters, packageName, specTargetName, configures))
            return null;
        
        try {
            return new SourceSpec(sourceName, superPackage, encloseName, specTargetName, packageName, isClass, specField, validatorName, configures, getters, emptyList() , localTypeWithLens);
        } catch (Exception e) {
            val stacktraces 
                    = stream(e.getStackTrace())
                    .map    (stacktrace -> "\n    @" + stacktrace)
                    .collect(joining());
            val errMsg 
                    = format(
                        "Problem generating the class: %s.%s: %s:%s%s", 
                        packageName, specTargetName, e.getMessage(), e.getClass(), stacktraces);
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
            val typeElement = returnType.asDeclaredType().asTypeElement();
            val fullName    = typeElement.getQualifiedName();
            if ("java.lang.String".equals(fullName))
                return true;
            if ("functionalj.result.ValidationException".equals(fullName))
                return true;
        }
        return false;
    }
    
    private boolean ensureNoArgConstructorWhenRequireFieldExists(
                    InputElement               element, 
                    List<Getter>              getters,
                    String                    packageName, 
                    String                    specTargetName,
                    SourceSpec.Configurations configures) {
        if (!configures.generateNoArgConstructor)
            return true;
        if (getters.stream().noneMatch(Getter::isRequired))
            return true;
        val field  = getters.stream().filter(Getter::isRequired).findFirst().get().name();
        val errMsg = format(
                        "No arg constructor cannot be generate when at least one field is require: %s.%s -> field: %s", 
                        packageName, specTargetName, field);
        element.error(errMsg);
        return false;
    }
    
    private boolean ensureSerializationMethodMatch(
                    InputTypeElement type, 
                    List<Getter>    getters, 
                    String          packageName,
                    String          specTargetName, 
                    Configurations  configures) {
        
        val errMsg = validateSerialization(element, type, getters, packageName, specTargetName, configures);
        if (errMsg == null)
            return true;
        
        type.error(errMsg);
        return false;
    }
    
    private Getter createGetterFromParameter(InputElement element, InputElement p) {
        val name        = p.simpleName().toString();
        val type        = getType(element, p.asTypeMirror());
        val isPrimitive = type.isPrimitive();
        val isNullable  = ((p.annotation(Nullable.class) != null) || (p.annotation(DefaultTo.class) != null));
        val isRequired  =  (p.annotation(Required.class) != null);
        val defTo       = (p.annotation(DefaultTo.class) != null)
                        ? p.annotation(DefaultTo.class).value()
                        : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
        val defValue = (DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(type) : defTo;
        if (!DefaultValue.isSuitable(type, defValue)) {
            element.error("Default value is not suitable for the type: " + type.fullName() + " -> DefaultTo " + defValue);
            return null;
        }
        if (isNullable && isRequired) {
            element.error("Parameter cannot be both Required and Nullable: " + name);
            return null;
        }
        val getter = new Getter(name, type, isNullable, defValue);
        return getter;
    }
    
    private Configurations extractConfigurations(InputElement element, Struct struct) {
        val configures = new Configurations();
        configures.coupleWithDefinition            = struct.coupleWithDefinition();
        configures.generateNoArgConstructor        = struct.generateNoArgConstructor();
        configures.generateRequiredOnlyConstructor = struct.generateRequiredOnlyConstructor();
        configures.generateAllArgConstructor       = struct.generateAllArgConstructor();
        configures.generateLensClass               = struct.generateLensClass();
        configures.generateBuilderClass            = struct.generateBuilderClass();
        configures.publicFields                    = struct.publicFields();
        configures.publicConstructor               = struct.publicConstructor();
        configures.toStringTemplate                = struct.generateToString()    ? struct.toStringTemplate() : null;
        configures.serialize                       = (struct.serialize() != null) ? struct.serialize()        : Serialize.To.NOTHING;
        
        if (!configures.generateNoArgConstructor
         && !configures.generateAllArgConstructor) {
            element.error("generateNoArgConstructor and generateAllArgConstructor must be be false at the same time.");
            return null;
        }
        return configures;
    }
    
    private Getter createGetterFromMethod(InputElement element, InputMethodElement method) {
        try {
            val methodName  = method.simpleName().toString();
            val returnType  = getType(element, method.getReturnType());
            val isPrimitive = returnType.isPrimitive();
            val isNullable  = ((method.annotation(Nullable.class) != null) || (method.annotation(DefaultTo.class) != null));
            val isRequired  =  (method.annotation(Required.class) != null);
            val defTo       = (method.annotation(DefaultTo.class) != null)
                            ? method.annotation(DefaultTo.class).value()
                            : ((isNullable && !isPrimitive)     ? DefaultValue.NULL : DefaultValue.REQUIRED);
            val defValue = ((DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(returnType) : defTo);
            if (!DefaultValue.isSuitable(returnType, defValue)) {
                element.error("Default value is not suitable for the type: " + returnType.fullName() + " -> DefaultTo " + defTo);
                return null;
            }
            if (isNullable && isRequired) {
                element.error("Parameter cannot be both Required and Nullable: " + methodName);
                return null;
            }
            val getter = new Getter(methodName, returnType, isNullable, defValue);
            return getter;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    private Type getType(InputElement element, InputType typeMirror) {
        val typeStr = typeMirror.getToString();
        if (typeMirror.isPrimitiveType())
            return Type.primitiveTypes.get(typeStr);
        
        if (typeMirror.isDeclaredType()) {
            val typeElement = typeMirror.asDeclaredType().asTypeElement();
            val typeName = typeElement.simpleName();
            if (typeName.equals("String"))
                return Type.STRING;
            
            val generics = typeMirror.asDeclaredType().typeArguments().stream()
                    .map(typeArg -> getType(element, typeArg))
                    .map(type    -> new Generic(type))
                    .collect(toList());
            
            val packageName = getPackageName(element, typeElement);
            val encloseElmt = typeElement.enclosingElement();
            val encloseName = typeElementKinds.contains(encloseElmt.kind()) ? encloseElmt.simpleName().toString() : null;
            return new Type(packageName, encloseName, typeName, generics);
        }
        return Type.newVirtualType(typeMirror.getToString());
    }
    
    private String getPackageName(InputElement element, InputTypeElement typeElement) {
        val typePackage = typeElement.packageQualifiedName();
        if (!typePackage.isEmpty())
            return typePackage;
        
        val packageName = element.packageQualifiedName();
        return packageName;
    }
    
}
