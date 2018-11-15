//  ========================================================================
//  Copyright (c) 2017-2018 Nawapunth Manusitthipol (NawaMan).
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
package functionalj.annotations.uniontype;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import functionalj.annotations.UnionType;
import functionalj.annotations.uniontype.generator.Generator;
import functionalj.annotations.uniontype.generator.model.Choice;
import functionalj.annotations.uniontype.generator.model.ChoiceParam;
import functionalj.annotations.uniontype.generator.model.Generic;
import functionalj.annotations.uniontype.generator.model.Method;
import functionalj.annotations.uniontype.generator.model.Method.Kind;
import functionalj.annotations.uniontype.generator.model.MethodParam;
import functionalj.annotations.uniontype.generator.model.Type;
import lombok.val;

/**
 * Annotation processor for UnionType.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class UnionTypeAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer    filer;
    private Messager messager;
    private boolean  hasError;
    private List<String> logs = new ArrayList<String>();
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        elementUtils = processingEnv.getElementUtils();
        filer        = processingEnv.getFiler();
        messager     = processingEnv.getMessager();
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(UnionType.class.getCanonicalName());
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
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        hasError = false;
        for (Element element : roundEnv.getElementsAnnotatedWith(UnionType.class)) {
            val typeElement = (TypeElement)element;
            val simpleName  = typeElement.getSimpleName().toString();
            val isInterface = ElementKind.INTERFACE.equals(element.getKind());
            if (!isInterface) {
                error(element, "Only an interface can be annotated with " + UnionType.class.getSimpleName() + ": " + simpleName);
                continue;
            }
            
            val generics = extractTypeGenerics(element, null, typeElement);
            
            val packageName    = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            val sourceName     = typeElement.getQualifiedName().toString().substring(packageName.length() + 1 );
            val enclosedClass  = sourceName.substring(0, sourceName.length() - simpleName.length() - 1);
            val sourceType     = new Type(packageName, enclosedClass, simpleName, generics);
            val unionType      = element.getAnnotation(UnionType.class);
            val specTargetName = unionType.name();
            val targetName     = extractTargetName(simpleName, specTargetName);
            val targetType     = new Type(packageName, null, targetName, generics);
            
            val specField = emptyToNull(unionType.sourceSpec());
            if ((specField != null) && !specField.matches("^[A-Za-z_$][A-Za-z_$0-9]*$")) {
                error(element, "Source spec field name is not a valid identifier: " + unionType.sourceSpec());
                continue;
            }
            
            val choices   = extractTypeChoices(element, targetType, typeElement);
            val methods   = extractTypeMethods(element, targetType, typeElement);
            val generator = new Generator(targetName, sourceType, specField, generics, choices, methods);
            
            try {
                val className  = packageName + "." + targetName;
                val content    = generator.lines().stream().collect(joining("\n"));
                val logString  = "\n" + logs.stream().map("// "::concat).collect(joining("\n"));
                generateCode(element, className, content + logString);
            } catch (Exception e) {
                e.printStackTrace(System.err);
                error(element, "Problem generating the class: "
                                + packageName + "." + specTargetName
                                + ": "  + e.getMessage()
                                + ":"   + e.getClass()
                                + ":"   + Arrays.asList(typeElement.getTypeParameters())
                                + ":"   + generator
                                + " @ " + Stream.of(e.getStackTrace()).map(String::valueOf).collect(toList()));
            }
        }
        return hasError;
    }
    
    private String emptyToNull(String sourceSpec) {
        if (sourceSpec == null)
            return null;
        return sourceSpec.isEmpty() ? null : sourceSpec;
    }
    
    private String extractTargetName(String simpleName, String specTargetName) {
        if ((specTargetName != null) && !specTargetName.isEmpty())
            return specTargetName;
        
        if (simpleName.matches("^.*Spec$"))
            return simpleName.replaceAll("Spec$", "");
        if (simpleName.matches("^.*Model$"))
            return simpleName.replaceAll("Model$", "");
        
        return simpleName;
    }
    
    private boolean isDefaultOrStatic(ExecutableElement mthd) {
        return mthd.isDefault()
            || mthd.getModifiers().contains(STATIC);
    }
    
    private Method createMethodFromMethodElement(Element element, Type targetType, ExecutableElement mthd) {
        val kind       = mthd.isDefault() ? Kind.DEFAULT : Kind.STATIC;
        val name       = mthd.getSimpleName().toString();
        val type       = typeOf(element, targetType, mthd.getReturnType());
        val params     = extractParameters(element, targetType, mthd);
        val generics   = extractGenerics(element, targetType, mthd.getTypeParameters());
        val exceptions = mthd.getThrownTypes().stream().map(t -> typeOf(element, targetType, t)).collect(toList());
        val method = new Method(kind, name, type, params, generics, exceptions);
        return method;
    }
    
    private List<MethodParam> extractParameters(Element element, Type targetType, ExecutableElement mthd) {
        return mthd.getParameters().stream().map(p -> {
            val paramName = p.getSimpleName().toString();
            val paramType = typeOf(element, targetType, p.asType());
            return new MethodParam(paramName, paramType);
        }).collect(toList());
    }
    
    private boolean isPublicOrPackage(ExecutableElement mthd) {
        return mthd.getModifiers().contains(PUBLIC)
          || !(mthd.getModifiers().contains(PRIVATE)
            && mthd.getModifiers().contains(PROTECTED));
    }
    
    private List<Generic> extractTypeGenerics(Element element, Type targetType, TypeElement type) {
        List<? extends TypeParameterElement> typeParameters = type.getTypeParameters();
        return extractGenerics(element, targetType, typeParameters);
    }
    
    private List<Generic> extractGenerics(Element element, Type targetType, List<? extends TypeParameterElement> typeParameters) {
        return typeParameters.stream()
                .map(t -> (TypeParameterElement)t)
                .map(t -> parameterGeneric(element, targetType, t))
                .collect(toList());
    }
    
    private Generic parameterGeneric(Element element, Type targetType, TypeParameterElement t) {
        val boundTypes = ((TypeParameterElement)t).getBounds().stream()
                .map(TypeMirror.class::cast)
                .map(tm -> this.typeOf(element, targetType, tm))
                .collect(toList());
        val paramName = t.toString();
        val boundAsString = (boundTypes.isEmpty()) ? ""
                : " extends " + t.getBounds().stream().map(b -> typeOf(element, targetType, (TypeMirror)b).getName()).collect(joining(" & "));
        return new Generic(
                paramName, 
                paramName 
                    + boundAsString,
                boundTypes);
    }
    
    private List<Generic> extractGenericsFromTypeArguments(Element element, Type targetType, List<? extends TypeMirror> typeParameters) {
        return typeParameters.stream()
                .map(p -> {
                    val paramName = p.toString();
                    if (p instanceof TypeVariable) {
                        val param = (TypeVariable)p;
                        return new Generic(
                            paramName,
                            paramName
                                + ((param.getLowerBound() == null) ? "" : " super " + param.getLowerBound())
                                + (param.getUpperBound().toString().equals("java.lang.Object") ? "" : " extends " + param.getUpperBound()),
                            Stream.of(
                                typeOf(element, targetType, param.getLowerBound()),
                                typeOf(element, targetType, param.getUpperBound())
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
    
    private List<Method> extractTypeMethods(Element element, Type targetType, TypeElement typeElement) {
        return typeElement.getEnclosedElements().stream()
                .filter(elmt->elmt.getKind().equals(ElementKind.METHOD))
                .map   (elmt->((ExecutableElement)elmt))
                .filter(mthd->!mthd.getSimpleName().toString().startsWith("__"))
                .filter(mthd->isPublicOrPackage(mthd))
                .filter(mthd->isDefaultOrStatic(mthd))
                .map(mthd -> createMethodFromMethodElement(element, targetType, mthd))
                .collect(toList());
    }
    
    private List<Choice> extractTypeChoices(Element element, Type targetType, TypeElement typeElement) {
        return typeElement.getEnclosedElements().stream()
                .filter(elmt->elmt.getKind().equals(ElementKind.METHOD))
                .map   (elmt->((ExecutableElement)elmt))
                .filter(mthd->!mthd.isDefault())
                .filter(mthd->mthd.getSimpleName().toString().matches("^[A-Z].*$"))
                .filter(mthd->mthd.getReturnType() instanceof NoType)
                .map   (mthd->createChoiceFromMethod(element, targetType, mthd, typeElement.getEnclosedElements()))
                .collect(toList());
    }
    
    private Choice createChoiceFromMethod(Element element, Type targetType, ExecutableElement method, List<? extends Element> elements) {
        String methodName = method.getSimpleName().toString();
        List<ChoiceParam> params = method.getParameters().stream().map(p -> {
            val name = p.getSimpleName().toString();
            val type = typeOf(element, targetType, p.asType());
            return new ChoiceParam(name, type);
        }).collect(toList());
        
        val validateMethodName = "__validate" + methodName;
        val hasValidator = elements.stream()
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
        
        Choice choice = hasValidator ? new Choice(methodName, validateMethodName, params) : new Choice(methodName, params);
        return choice;
    }
    
    private Type typeOf(Element element, Type targetType, TypeMirror typeMirror) {
        if (typeMirror == null)
            return null;
        
        val typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType)
            return new Type(typeStr);
        
        if (typeMirror instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)typeMirror).asElement());
            val typeName    = typeElement.getSimpleName().toString();
            val generics    = extractGenericsFromTypeArguments(element, targetType, ((DeclaredType)typeMirror).getTypeArguments());
            val packageName = getPackageName(element, typeElement);
            val foundType = new Type(packageName, null, typeName, generics);
            if (packageName.equals(Self.class.getPackage().getName()) && typeName.matches("^Self[0-9]+$"))
                return new Type(targetType.pckg, targetType.encloseClass, targetType.name, generics);
            
            return foundType;
        }
        
        if (typeMirror instanceof TypeVariable) {
            val varType = (TypeVariable)typeMirror;
            return new Type(null, null, varType.toString());
        }
        
        return null;
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
