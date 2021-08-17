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


import static functionalj.types.struct.AnnotationUtils.accessibilityOf;
import static functionalj.types.struct.AnnotationUtils.concrecityOf;
import static functionalj.types.struct.AnnotationUtils.isAbstract;
import static functionalj.types.struct.AnnotationUtils.isPrivate;
import static functionalj.types.struct.AnnotationUtils.isStatic;
import static functionalj.types.struct.AnnotationUtils.modifiabilityOf;
import static functionalj.types.struct.AnnotationUtils.scopeOf;
import static functionalj.types.struct.features.FeatureSerialization.validateSerialization;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import functionalj.types.DefaultTo;
import functionalj.types.DefaultValue;
import functionalj.types.Generic;
import functionalj.types.Nullable;
import functionalj.types.Required;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import functionalj.types.Type;
import functionalj.types.input.Environment;
import functionalj.types.struct.generator.Callable;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.Parameter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import lombok.val;


public class StructSpec {
    
    static public interface Input {
        
        public Element  element();
        public Elements elementUtils();
        public Types    typeUtils();
        public Messager messager();
        
    }
    
    private static final EnumSet<ElementKind> typeElementKinds = EnumSet.of(
            ElementKind.ENUM,
            ElementKind.CLASS,
            ElementKind.ANNOTATION_TYPE,
            ElementKind.INTERFACE,
            ElementKind.METHOD);
    
    private final Environment environment;
    
    public StructSpec(Input input) {
//        this.input       = input;
        this.environment = new Environment(
                        input.element(), 
                        input.elementUtils(),
                        input.typeUtils(),
                        input.messager());
    }
    
    public boolean hasError() {
        return environment.hasError();
    }
    
    private void error(Element e, String msg) {
        environment.error(e, msg);
    }

    public String packageName() {
        return environment.packageName();
    }
    
    public String targetName() {
        return environment.targetName();
    }
    
    public SourceSpec sourceSpec() {
        val element = environment.element();
        if (element instanceof TypeElement)
            return extractSourceSpecType(element);
        if (element instanceof ExecutableElement)
            return extractSourceSpecMethod(element);
        throw new IllegalArgumentException("Record annotation is only support class or method.");
    }
    private SourceSpec extractSourceSpecType(Element element) {
        val type        = (TypeElement)element;
        val simpleName  = type.getSimpleName().toString();
        val isInterface = ElementKind.INTERFACE.equals(element.getKind());
        val isClass     = ElementKind.CLASS    .equals(element.getKind());
        
        if (!isInterface && !isClass) {
            error(element, "Only a class or interface can be annotated with " + Struct.class.getSimpleName() + ": " + simpleName);
            return null;
        }
        
        val localTypeWithLens = environment.readLocalTypeWithLens();
        
        List<Getter> getters = type.getEnclosedElements().stream()
                .filter (elmt  ->elmt.getKind().equals(ElementKind.METHOD))
                .map    (elmt  ->((ExecutableElement)elmt))
                .filter (method->!method.isDefault())
                .filter (method->!isClass || isAbstract(environment, method))
                .filter (method->!(method.getReturnType() instanceof NoType))
                .filter (method->method.getParameters().isEmpty())
                .map    (method->createGetterFromMethod(element, method))
                .collect(toList());
        if (getters.stream().anyMatch(g -> g == null))
            return null;
        
        List<Callable> methods = type.getEnclosedElements().stream()
                .filter (elmt -> elmt.getKind().equals(ElementKind.METHOD))
                .map    (elmt -> ((ExecutableElement)elmt))
                .filter (mthd -> (mthd.isDefault() || isStatic(mthd)) && !isAbstract(environment, mthd) && !isPrivate(mthd))
                .map    (mthd -> extractMethodSpec(element, mthd))
                .filter (mthd -> mthd != null)
                .collect(toList());
        
        val packageName = environment.elementUtils().getPackageOf(type).getQualifiedName().toString();
        val encloseName = element.getEnclosingElement().getSimpleName().toString();
        val sourceName  = type.getQualifiedName().toString().substring(packageName.length() + 1 );
        val struct      = element.getAnnotation(Struct.class);
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
            error(element, "Problem generating the class: "
                            + packageName + "." + targetName
                            + ": "  + e.getMessage()
                            + ":"   + e.getClass()
                            + stream(e.getStackTrace())
                                .map(st -> "\n    @" + st)
                                .collect(joining()));
            return null;
        }
    }
    
    private Callable extractMethodSpec(Element element, ExecutableElement mthdElement) {
        val extractType = (Function<TypeMirror, Type>)(typeMirror -> getType(element, typeMirror));
        
        val name          = mthdElement.getSimpleName().toString();
        val type          = getType(element, mthdElement.getReturnType());
        val isVarArgs     = mthdElement.isVarArgs();
        val accessibility = accessibilityOf(mthdElement);
        val scope         = scopeOf(mthdElement);
        val modifiability = modifiabilityOf(mthdElement);
        val concrecity    = concrecityOf(mthdElement);
        
        val generics = mthdElement.getTypeParameters().stream()
                .map(t -> getGenericFromTypeParameter(element, t))
                .collect(toList());
        val parameters = mthdElement
                .getParameters().stream()
                .map(param -> new Parameter(param.getSimpleName().toString(), getType(element, param.asType())))
                .collect(toList());
        val exceptions = mthdElement
                .getThrownTypes().stream()
                .map(extractType)
                .collect(toList());
        
        return new Callable(name, type, isVarArgs, accessibility, scope, modifiability, concrecity, parameters, generics, exceptions);
    }

    private Generic getGenericFromTypeParameter(Element element, TypeParameterElement typeParameter){
        val name   = typeParameter.getSimpleName().toString();
        val bounds = typeParameter.getBounds().stream()
                    .map    (bound -> getType(element, bound))
                    .collect(toList());
        return new Generic(name, null, bounds);
    }
    
    private SourceSpec extractSourceSpecMethod(Element element) {
        val method         = (ExecutableElement)element;
        val packageName    = environment.packageName();
        val encloseName    = element.getEnclosingElement().getSimpleName().toString();
        val struct         = element.getAnnotation(Struct.class);
        val specTargetName = targetName();
        val specField      = struct.specField();
        
        val localTypeWithLens = environment.readLocalTypeWithLens();
        
        val isClass      = (Boolean)null;
        val sourceName   = (String)null;
        val superPackage = packageName;
        
        val isValidate    = isBooleanStringOrValidation(method.getReturnType());
        val validatorName = isValidate ? method.getSimpleName().toString() : null;
        val isStatic      = method.getModifiers().contains(Modifier.STATIC);
        val isPrivate     = method.getModifiers().contains(Modifier.PRIVATE);
        if (isValidate && (!isStatic || isPrivate)) {
            error(method, "Validatable struct must come from static and non-private method.");
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
            error(element, errMsg);
            return null;
        }
    }
    
    private boolean isBooleanStringOrValidation(TypeMirror returnType) {
        if (returnType instanceof PrimitiveType) {
            if ("boolean".equals(((PrimitiveType)returnType).toString()))
                return true;
        }
        if (returnType instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)returnType).asElement());
            val fullName    = typeElement.getQualifiedName().toString();
            if ("java.lang.String".equals(fullName))
                return true;
            if ("functionalj.result.ValidationException".equals(fullName))
                return true;
        }
        return false;
    }
    
    private boolean ensureNoArgConstructorWhenRequireFieldExists(
                    Element                   element, 
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
        error(element, errMsg);
        return false;
    }
    
    private boolean ensureSerializationMethodMatch(
                    TypeElement    type, 
                    List<Getter>   getters, 
                    String         packageName,
                    String         specTargetName, 
                    Configurations configures) {
        
        val errMsg = validateSerialization(environment, type, getters, packageName, specTargetName, configures);
        if (errMsg == null)
            return true;
        
        error(type, errMsg);
        return false;
    }
    
    private Getter createGetterFromParameter(Element element, VariableElement p) {
        val name        = p.getSimpleName().toString();
        val type        = getType(element, p.asType());
        val isPrimitive = type.isPrimitive();
        val isNullable  = ((p.getAnnotation(Nullable.class) != null) || (p.getAnnotation(DefaultTo.class) != null));
        val isRequired  =  (p.getAnnotation(Required.class) != null);
        val defTo       = (p.getAnnotation(DefaultTo.class) != null)
                        ? p.getAnnotation(DefaultTo.class).value()
                        : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
        val defValue = (DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(type) : defTo;
        if (!DefaultValue.isSuitable(type, defValue)) {
            error(element, "Default value is not suitable for the type: " + type.fullName() + " -> DefaultTo " + defValue);
            return null;
        }
        if (isNullable && isRequired) {
            error(element, "Parameter cannot be both Required and Nullable: " + name);
            return null;
        }
        val getter = new Getter(name, type, isNullable, defValue);
        return getter;
    }
    
    private Configurations extractConfigurations(Element element, Struct struct) {
        val configures = new Configurations();
        configures.coupleWithDefinition            = struct.coupleWithDefinition();
        configures.generateNoArgConstructor        = struct.generateNoArgConstructor();
        configures.generateRequiredOnlyConstructor = struct.generateRequiredOnlyConstructor();
        configures.generateAllArgConstructor       = struct.generateAllArgConstructor();
        configures.generateLensClass               = struct.generateLensClass();
        configures.generateBuilderClass            = struct.generateBuilderClass();
        configures.publicFields                    = struct.publicFields();
        configures.publicConstructor               = struct.publicConstructor();
        configures.toStringTemplate                = !struct.generateToString()   ? null : struct.toStringTemplate();
        configures.serialize                       = (struct.serialize() != null) ? struct.serialize() : Serialize.To.NOTHING;
        
        if (!configures.generateNoArgConstructor
         && !configures.generateAllArgConstructor) {
            error(element, "generateNoArgConstructor and generateAllArgConstructor must be be false at the same time.");
            return null;
        }
        return configures;
    }
    
    private Getter createGetterFromMethod(Element element, ExecutableElement method) {
        try {
            val methodName  = method.getSimpleName().toString();
            val returnType  = getType(element, method.getReturnType());
            val isPrimitive = returnType.isPrimitive();
            val isNullable  = ((method.getAnnotation(Nullable.class) != null) || (method.getAnnotation(DefaultTo.class) != null));
            val isRequired  =  (method.getAnnotation(Required.class) != null);
            val defTo       = (method.getAnnotation(DefaultTo.class) != null)
                            ? method.getAnnotation(DefaultTo.class).value()
                            : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
            val defValue = ((DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(returnType) : defTo);
            if (!DefaultValue.isSuitable(returnType, defValue)) {
                error(element, "Default value is not suitable for the type: " + returnType.fullName() + " -> DefaultTo " + defTo);
                return null;
            }
            if (isNullable && isRequired) {
                error(element, "Parameter cannot be both Required and Nullable: " + methodName);
                return null;
            }
            val getter = new Getter(methodName, returnType, isNullable, defValue);
            return getter;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    private Type getType(Element element, TypeMirror typeMirror) {
        val typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType)
            return Type.primitiveTypes.get(typeStr);
        
        if (typeMirror instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)typeMirror).asElement());
            val typeName = typeElement.getSimpleName().toString();
            if (typeName.equals("String"))
                return Type.STRING;
            
            val generics = ((DeclaredType)typeMirror).getTypeArguments().stream()
                    .map(typeArg -> getType(element, (TypeMirror)typeArg))
                    .map(type    -> new Generic(type))
                    .collect(toList());
            
            val packageName = getPackageName(element, typeElement);
            val encloseElmt = typeElement.getEnclosingElement();
            val encloseName = typeElementKinds.contains(encloseElmt.getKind()) ? encloseElmt.getSimpleName().toString() : null;
            return new Type(packageName, encloseName, typeName, generics);
        }
        return Type.newVirtualType(typeMirror.toString());
    }
    
    private String getPackageName(Element element, TypeElement typeElement) {
        val elementUtils = environment.elementUtils();
        val typePackage = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        if (!typePackage.isEmpty())
            return typePackage;
        
        val packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        return packageName;
    }
    
}
