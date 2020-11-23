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
package functionalj.types.struct;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import functionalj.types.DefaultTo;
import functionalj.types.DefaultValue;
import functionalj.types.Generic;
import functionalj.types.Nullable;
import functionalj.types.Struct;
import functionalj.types.Type;
import functionalj.types.common;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.SourceSpec.Configurations;


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
    
    private final Input input;
    private boolean hasError = false;
    
    public StructSpec(Input input) {
        this.input = input;
    }
    
    public boolean hasError() {
        return hasError;
    }
    
    private void error(Element e, String msg) {
        hasError = true;
        input.messager().printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
    
    public String packageName() {
        var element = input.element();
        if (element instanceof TypeElement)
            return extractPackageNameType(element);
        if (element instanceof ExecutableElement)
            return extractPackageNameMethod(element);
        throw new IllegalArgumentException("Struct annotation is only support class or method.");
    }
    
    public String targetTypeName() {
        var element        = input.element();
        var struct         = element.getAnnotation(Struct.class);
        var specTargetName = struct.name();
        return specTargetName;
    }
    
    public SourceSpec sourceSpec() {
        var element = input.element();
        if (element instanceof TypeElement)
            return extractSourceSpecType(element);
        if (element instanceof ExecutableElement)
            return extractSourceSpecMethod(element);
        throw new IllegalArgumentException("Record annotation is only support class or method.");
    }
    private SourceSpec extractSourceSpecType(Element element) {
        var type        = (TypeElement)element;
        var simpleName  = type.getSimpleName().toString();
        var isInterface = ElementKind.INTERFACE.equals(element.getKind());
        var isClass     = ElementKind.CLASS    .equals(element.getKind());
        if (!isInterface && !isClass) {
            error(element, "Only a class or interface can be annotated with " + Struct.class.getSimpleName() + ": " + simpleName);
            return null;
        }
        
        var localTypeWithLens = common.readLocalTypeWithLens(element);
        
        List<Getter> getters = type.getEnclosedElements().stream()
                .filter(elmt  ->elmt.getKind().equals(ElementKind.METHOD))
                .map   (elmt  ->((ExecutableElement)elmt))
                .filter(method->!method.isDefault())
                .filter(method->!isClass || isAbstract(method))
                .filter(method->!(method.getReturnType() instanceof NoType))
                .filter(method->method.getParameters().isEmpty())
                .map   (method->createGetterFromMethod(element, method))
                .collect(toList());
        if (getters.stream().anyMatch(g -> g == null))
            return null;
        
        var packageName    = input.elementUtils().getPackageOf(type).getQualifiedName().toString();
        var encloseName    = element.getEnclosingElement().getSimpleName().toString();
        var sourceName     = type.getQualifiedName().toString().substring(packageName.length() + 1 );
        var struct         = element.getAnnotation(Struct.class);
        var specTargetName = struct.name();
        var targetName     = common.extractTargetName(simpleName, struct.name());
        var specField      = struct.specField();
        
        var configures = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        
        if (!ensureNoArgConstructorWhenRequireFieldExists(element, getters, packageName, specTargetName, configures))
            return null;
        
        var validatorName = (String)null;
        
        try {
            return new SourceSpec(sourceName, packageName, encloseName, targetName, packageName, isClass, specField, validatorName, configures, getters, localTypeWithLens);
        } catch (Exception e) {
            error(element, "Problem generating the class: "
                            + packageName + "." + specTargetName
                            + ": "  + e.getMessage()
                            + ":"   + e.getClass()
                            + stream(e.getStackTrace())
                                .map(st -> "\n    @" + st)
                                .collect(joining()));
            return null;
        }
    }
    
    private SourceSpec extractSourceSpecMethod(Element element) {
        var method         = (ExecutableElement)element;
        var packageName    = packageName();
        var encloseName    = element.getEnclosingElement().getSimpleName().toString();
        var struct         = element.getAnnotation(Struct.class);
        var specTargetName = common.extractTargetName(method.getSimpleName().toString(), struct.name());
        var specField      = struct.specField();
        
        var localTypeWithLens = common.readLocalTypeWithLens(element);
        
        var isClass      = (Boolean)null;
        var sourceName   = (String)null;
        var superPackage = packageName;
        
        var validatorName = isBooleanStringOrValidation(method.getReturnType()) ? method.getSimpleName().toString() : null;
        
        var configures = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        
        var getters = method.getParameters().stream()
                .map(p -> createGetterFromParameter(element, p))
                .filter(Objects::nonNull)
                .collect(toList());
        
        if (!ensureNoArgConstructorWhenRequireFieldExists(element, getters, packageName, specTargetName, configures))
            return null;
        
        try {
            return new SourceSpec(sourceName, superPackage, encloseName, specTargetName, packageName, isClass, specField, validatorName, configures, getters, localTypeWithLens);
        } catch (Exception e) {
            error(element, "Problem generating the class: "
                            + packageName + "." + specTargetName
                            + ": "  + e.getMessage()
                            + ":"   + e.getClass()
                            + stream(e.getStackTrace())
                                .map(st -> "\n    @" + st)
                                .collect(joining()));
            return null;
        }
    }
    
    private String extractPackageNameType(Element element) {
        var type        = (TypeElement)element;
        var packageName = input.elementUtils().getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    private String extractPackageNameMethod(Element element) {
        var method      = (ExecutableElement)element;
        var type        = (TypeElement)(method.getEnclosingElement());
        var packageName = input.elementUtils().getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    private boolean isBooleanStringOrValidation(TypeMirror returnType) {
        if (returnType instanceof PrimitiveType) {
            if ("boolean".equals(((PrimitiveType)returnType).toString()))
                return true;
        }
        if (returnType instanceof DeclaredType) {
            var typeElement = ((TypeElement)((DeclaredType)returnType).asElement());
            var fullName    = typeElement.getQualifiedName().toString();
            if ("java.lang.String".equals(fullName))
                return true;
            if ("functionalj.result.ValidationException".equals(fullName))
                return true;
        }
        return false;
    }
    
    private boolean ensureNoArgConstructorWhenRequireFieldExists(Element element, List<Getter> getters,
            final java.lang.String packageName, final java.lang.String specTargetName,
            final functionalj.types.struct.generator.SourceSpec.Configurations configures) {
        if (!configures.generateNoArgConstructor)
            return true;
        if (getters.stream().noneMatch(Getter::isRequired))
            return true;
        var field = getters.stream().filter(Getter::isRequired).findFirst().get().getName();
        error(element, "No arg constructor cannot be generate when at least one field is require: "
                        + packageName + "." + specTargetName + " -> field: " + field);
        return false;
    }
    
    private Getter createGetterFromParameter(Element element, VariableElement p){
        var name        = p.getSimpleName().toString();
        var type        = getType(element, p.asType());
        var isPrimitive = type.isPrimitive();
        var isNullable  = ((p.getAnnotation(Nullable.class) != null) || (p.getAnnotation(DefaultTo.class) != null));
        var defTo       = (p.getAnnotation(DefaultTo.class) != null)
                        ? p.getAnnotation(DefaultTo.class).value()
                        : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
        var defValue = (DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(type) : defTo;
        if (!DefaultValue.isSuitable(type, defValue)) {
            error(element, "Default value is not suitable for the type: " + type.fullName() + " -> DefaultTo " + defValue);
            return null;
        }
        var getter = new Getter(name, type, isNullable, defValue);
        return getter;
    }
    
    private Configurations extractConfigurations(Element element, Struct struct) {
        var configures = new Configurations();
        configures.coupleWithDefinition            = struct.coupleWithDefinition();
        configures.generateNoArgConstructor        = struct.generateNoArgConstructor();
        configures.generateRequiredOnlyConstructor = struct.generateRequiredOnlyConstructor();
        configures.generateAllArgConstructor       = struct.generateAllArgConstructor();
        configures.generateLensClass               = struct.generateLensClass();
        configures.generateBuilderClass            = struct.generateBuilderClass();
        configures.publicFields                    = struct.publicFields();
        configures.publicConstructor               = struct.publicConstructor();
        configures.toStringTemplate                = !struct.generateToString() ? null : struct.toStringTemplate();
        
        if (!configures.generateNoArgConstructor
         && !configures.generateAllArgConstructor) {
            error(element, "generateNoArgConstructor and generateAllArgConstructor must be be false at the same time.");
            return null;
        }
        return configures;
    }
    
    private boolean isAbstract(ExecutableElement method) {
        // Seriously ... no other way?
        try (var writer = new StringWriter()) {
            input.elementUtils().printElements(writer, method);
            return writer.toString().contains(" abstract ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Getter createGetterFromMethod(Element element, ExecutableElement method) {
        try {
            var methodName  = method.getSimpleName().toString();
            var returnType  = getType(element, method.getReturnType());
            var isPrimitive = returnType.isPrimitive();
            var isNullable  = ((method.getAnnotation(Nullable.class) != null) || (method.getAnnotation(DefaultTo.class) != null));
            var defTo       = (method.getAnnotation(DefaultTo.class) != null)
                            ? method.getAnnotation(DefaultTo.class).value()
                            : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
            var defValue = ((DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(returnType) : defTo);
            if (!DefaultValue.isSuitable(returnType, defValue)) {
                error(element, "Default value is not suitable for the type: " + returnType.fullName() + " -> DefaultTo " + defTo);
                return null;
            }
            var getter = new Getter(methodName, returnType, isNullable, defValue);
            return getter;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    private Type getType(Element element, TypeMirror typeMirror) {
        var typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType)
            return Type.primitiveTypes.get(typeStr);
        
        if (typeMirror instanceof DeclaredType) {
            var typeElement = ((TypeElement)((DeclaredType)typeMirror).asElement());
            var typeName = typeElement.getSimpleName().toString();
            if (typeName.equals("String"))
                return Type.STRING;
            
            var generics = ((DeclaredType)typeMirror).getTypeArguments().stream()
                    .map(typeArg -> getType(element, (TypeMirror)typeArg))
                    .map(type    -> new Generic(type))
                    .collect(toList());
            
            var packageName = getPackageName(element, typeElement);
            var encloseElmt = typeElement.getEnclosingElement();
            var encloseName = typeElementKinds.contains(encloseElmt.getKind()) ? encloseElmt.getSimpleName().toString() : null;
            return new Type(packageName, encloseName, typeName, generics);
        }
        return Type.newVirtualType(typeMirror.toString());
    }
    
    private String getPackageName(Element element, TypeElement typeElement) {
        var elementUtils = input.elementUtils();
        var typePackage = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        if (!typePackage.isEmpty())
            return typePackage;
        
        var packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        return packageName;
    }
    
}
