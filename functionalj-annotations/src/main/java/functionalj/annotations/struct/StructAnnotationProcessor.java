//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.annotations.struct;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
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
//import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import functionalj.annotations.DefaultTo;
import functionalj.annotations.DefaultValue;
import functionalj.annotations.Nullable;
import functionalj.annotations.Struct;
import functionalj.annotations.common;
import functionalj.annotations.struct.generator.Getter;
import functionalj.annotations.struct.generator.SourceSpec;
import functionalj.annotations.struct.generator.SourceSpec.Configurations;
import functionalj.annotations.struct.generator.StructBuilder;
import functionalj.annotations.struct.generator.Type;
import functionalj.annotations.struct.generator.model.GenStruct;
import lombok.val;

/**
 * Annotation processor for Struct.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class StructAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;
//    private Types    typeUtils;
    private Filer    filer;
    private Messager messager;
    private boolean  hasError;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        elementUtils = processingEnv.getElementUtils();
        filer        = processingEnv.getFiler();
        messager     = processingEnv.getMessager();
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Struct.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    private void error(Element e, String msg) {
        hasError = true;
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
    
    private static final EnumSet<ElementKind> typeElementKinds = EnumSet.of(
            ElementKind.ENUM,
            ElementKind.CLASS,
            ElementKind.ANNOTATION_TYPE,
            ElementKind.INTERFACE,
            ElementKind.METHOD);
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO - Should find a way to warn when a field is not immutable.
        hasError = false;
        for (Element element : roundEnv.getElementsAnnotatedWith(Struct.class)) {
            val packageName    = extractPackageName(element);
            val specTargetName = extractTargetTypeName(element);
            
            try {
                val sourceSpec = extractSourceSpec(element);
                if (sourceSpec == null)
                    continue;
                
                val dataObjSpec = new StructBuilder(sourceSpec).build();
                val className   = (String)dataObjSpec.type().fullName("");
                val content     = new GenStruct(sourceSpec, dataObjSpec).lines().collect(joining("\n"));
                generateCode(element, className, content);
            } catch (Exception e) {
                error(element, "Problem generating the class: "
                                + packageName + "." + specTargetName
                                + ": "  + e.getMessage()
                                + ":"   + e.getClass()
                                + " @ " + e.getStackTrace()[0]);
            }
        }
        return hasError;
    }
    
    private String extractPackageName(Element element) {
        if (element instanceof TypeElement)
            return extractPackageNameType(element);
        if (element instanceof ExecutableElement)
            return extractPackageNameMethod(element);
        throw new IllegalArgumentException("Struct annotation is only support class or method.");
    }
    private String extractPackageNameType(Element element) {
        val type        = (TypeElement)element;
        val packageName = elementUtils.getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    private String extractPackageNameMethod(Element element) {
        val method      = (ExecutableElement)element;
        val type        = (TypeElement)(method.getEnclosingElement());
        val packageName = elementUtils.getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    private String extractTargetTypeName(Element element) {
        val struct         = element.getAnnotation(Struct.class);
        val specTargetName = struct.name();
        return specTargetName;
    }
    
    private SourceSpec extractSourceSpec(Element element) {
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
        
        val localTypeWithLens = common.readLocalTypeWithLens(element);
        
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
        
        val packageName    = elementUtils.getPackageOf(type).getQualifiedName().toString();
        val encloseName    = element.getEnclosingElement().getSimpleName().toString();
        val sourceName     = type.getQualifiedName().toString().substring(packageName.length() + 1 );
        val struct         = element.getAnnotation(Struct.class);
        val specTargetName = struct.name();
        val targetName     = common.extractTargetName(simpleName, struct.name());
        val specField      = struct.specField();
        
        val configures = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        
        if (!ensureNoArgConstructorWhenRequireFieldExists(element, getters, packageName, specTargetName, configures))
            return null;
        
        val validatorName = (String)null;
        
        try {
            return new SourceSpec(sourceName, packageName, encloseName, targetName, packageName, isClass, specField, validatorName, configures, getters, localTypeWithLens);
        } catch (Exception e) {
            error(element, "Problem generating the class: "
                            + packageName + "." + specTargetName
                            + ": "  + e.getMessage()
                            + ":"   + e.getClass()
                            + " @ " + e.getStackTrace()[0]);
            return null;
        }
    }
    
    private SourceSpec extractSourceSpecMethod(Element element) {
        val method         = (ExecutableElement)element;
        val packageName    = extractPackageName(element);
        val encloseName    = element.getEnclosingElement().getSimpleName().toString();
        val struct         = element.getAnnotation(Struct.class);
        val specTargetName = common.extractTargetName(method.getSimpleName().toString(), struct.name());
        val specField      = struct.specField();
        
        val localTypeWithLens = common.readLocalTypeWithLens(element);
        
        val isClass      = (Boolean)null;
        val sourceName   = (String)null;
        val superPackage = packageName;
        
        val validatorName = isBooleanStringOrValidation(method.getReturnType()) ? method.getSimpleName().toString() : null;
        
        val configures = extractConfigurations(element, struct);
        if (configures == null)
            return null;
        
        val getters = method.getParameters().stream()
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
                            + " @ " + e.getStackTrace()[0]);
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
    
    private boolean ensureNoArgConstructorWhenRequireFieldExists(Element element, List<Getter> getters,
            final java.lang.String packageName, final java.lang.String specTargetName,
            final functionalj.annotations.struct.generator.SourceSpec.Configurations configures) {
        if (!configures.generateNoArgConstructor)
            return true;
        if (getters.stream().noneMatch(g->g.getDefaultTo() != DefaultValue.REQUIRED))
            return true;
        val field = getters.stream().filter(g->g.isRequired()).findFirst().get().getName();
        error(element, "No arg constructor cannot be generate when at least one field is require: "
                        + packageName + "." + specTargetName + " -> field: " + field);
        return false;
    }
    
    private Getter createGetterFromParameter(Element element, VariableElement p){
        val name        = p.getSimpleName().toString();
        val type        = getType(element, p.asType());
        val isPrimitive = type.isPrimitive();
        val isNullable  = (p.getAnnotation(Nullable.class) != null) ? true : false;
        val defTo       = (p.getAnnotation(DefaultTo.class) != null)
                        ? p.getAnnotation(DefaultTo.class).value()
                        : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
        val defValue = (DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(type) : defTo;
        if (!DefaultValue.isSuitable(type, defValue)) {
            error(element, "Default value is not suitable for the type: " + type.fullName() + " -> DefaultTo " + defTo);
            return null;
        }
        if (!isNullable && (defValue == DefaultValue.NULL)) {
            error(element, "Default value cannot be null: " + type.fullName() + " -> DefaultTo " + defTo);
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
        try (val writer = new StringWriter()) {
            elementUtils.printElements(writer, method);
            return writer.toString().contains(" abstract ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Getter createGetterFromMethod(Element element, ExecutableElement method) {
        try {
            val methodName  = method.getSimpleName().toString();
            val returnType  = getType(element, method.getReturnType());
            val isPrimitive = returnType.isPrimitive();
            val isNullable  = (element.getAnnotation(Nullable.class) != null) ? true : false;
            val defTo       = (element.getAnnotation(DefaultTo.class) != null)
                            ? element.getAnnotation(DefaultTo.class)
                            : ((isNullable && !isPrimitive) ? DefaultValue.NULL : DefaultValue.REQUIRED);
            val defValue = (DefaultValue)((DefaultValue.UNSPECIFIED == defTo) ? DefaultValue.getUnspecfiedValue(returnType) : defTo);
            if (!DefaultValue.isSuitable(returnType, defValue)) {
                error(element, "Default value is not suitable for the type: " + returnType.fullName() + " -> DefaultTo " + defTo);
                return null;
            }
            if (!isNullable && (defValue == DefaultValue.NULL)) {
                error(element, "Default value cannot be null: " + returnType.fullName() + " -> DefaultTo " + defTo);
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
                    .collect(toList());
            
            val packageName = getPackageName(element, typeElement);
            val encloseElmt = typeElement.getEnclosingElement();
            val encloseName = typeElementKinds.contains(encloseElmt.getKind()) ? encloseElmt.getSimpleName().toString() : null;
            return new Type(encloseName, typeName, packageName, generics);
        }
        return Type.newVirtualType(typeMirror.toString());
    }
    
    private String getPackageName(Element element, TypeElement typeElement) {
        val typePackage = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        if (!typePackage.isEmpty())
            return typePackage;
        
        val packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        return packageName;
    }
    
    private void generateCode(Element element, String className, String content) throws IOException {
        try (Writer writer = filer.createSourceFile(className, element).openWriter()) {
            writer.write(content);
        }
    }
    
}
