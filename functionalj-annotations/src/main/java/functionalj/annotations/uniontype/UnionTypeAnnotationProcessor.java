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
            
            val generics = extractTypeGenerics(element, typeElement);
            error(element, "Type generics: " + generics);
            
            val choices = typeElement.getEnclosedElements().stream()
                    .filter(elmt->elmt.getKind().equals(ElementKind.METHOD))
                    .map   (elmt->((ExecutableElement)elmt))
                    .filter(mthd->!mthd.isDefault())
                    .filter(mthd->mthd.getSimpleName().toString().matches("^[A-Z].*$"))
                    .filter(mthd->mthd.getReturnType() instanceof NoType)
                    .map   (mthd->createChoiceFromMethod(element, mthd, typeElement.getEnclosedElements()))
                    .collect(toList());
            
            val methods = typeElement.getEnclosedElements().stream()
                    .filter(elmt->elmt.getKind().equals(ElementKind.METHOD))
                    .map   (elmt->((ExecutableElement)elmt))
                    .filter(mthd->!mthd.getSimpleName().toString().startsWith("__"))
                    .filter(mthd->isPublicOrPackage(mthd))
                    .filter(mthd->isDefaultOrStatic(mthd))
                    .map(mthd -> createMethodFromMethodElement(element, mthd))
                    .collect(toList());
            error(element, methods.toString());
            val packageName    = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            val sourceName     = typeElement.getQualifiedName().toString().substring(packageName.length() + 1 );
            val unionType      = element.getAnnotation(UnionType.class);
            val specTargetName = unionType.name();
            val targetName     = ((specTargetName == null) || specTargetName.isEmpty()) ? simpleName : specTargetName;
            val enclosedClass  = sourceName.substring(0, sourceName.length() - simpleName.length() - 1);
            val generator      = new Generator(targetName, new Type(packageName, enclosedClass, simpleName), generics, choices, methods);
            
            try {
                val className  = packageName + "." + targetName;
                val content    = generator.lines().stream().collect(joining("\n"));
                generateCode(element, className, content + "\n" + logs.stream().map("// "::concat).collect(joining("\n")));
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
    
    private boolean isDefaultOrStatic(ExecutableElement mthd) {
        return mthd.isDefault() || mthd.getModifiers().contains(STATIC);
    }
    
    private Method createMethodFromMethodElement(Element element, ExecutableElement mthd) {
        val kind   = mthd.isDefault() ? Kind.DEFAULT : Kind.STATIC;
        val name   = mthd.getSimpleName().toString();
        val type   = typeOf(element, mthd.getReturnType());
        List<MethodParam> params = mthd.getParameters().stream().map(p -> {
            val paramName = p.getSimpleName().toString();
            val paramType = typeOf(element, p.asType());
            return new MethodParam(paramName, paramType);
        }).collect(toList());
        
        val method = new Method(kind, name, type, params);
        error(element, method.toString());
        return method;
    }
    
    private boolean isPublicOrPackage(ExecutableElement mthd) {
        return mthd.getModifiers().contains(PUBLIC)
                 || !(mthd.getModifiers().contains(PRIVATE) && mthd.getModifiers().contains(PROTECTED));
    }
    
    private List<Generic> extractTypeGenerics(Element element, TypeElement type) {
        List<? extends TypeParameterElement> typeParameters = type.getTypeParameters();
        return extractGenerics(element, typeParameters);
    }
    
    private List<Generic> extractGenerics(Element element, List<? extends TypeParameterElement> typeParameters) {
        return typeParameters.stream()
                .map(t -> (TypeParameterElement)t)
                .map(t -> {
                    val boundTypes = ((TypeParameterElement)t).getBounds().stream()
                            .map(TypeMirror.class::cast)
                            .map(tm -> this.typeOf(element, tm))
                            .collect(toList());
                    return new Generic(
                            t.toString(), 
                            t.toString() 
                                + ((boundTypes.isEmpty()) 
                                        ? "" : " extends " + t.getBounds().stream().map(b -> typeOf(element, (TypeMirror)b).getName()).collect(joining(" & "))), boundTypes);
                })
                .collect(toList());
    }
    
    private Choice createChoiceFromMethod(Element element, ExecutableElement method, List<? extends Element> elements) {
        String methodName = method.getSimpleName().toString();
        List<ChoiceParam> params = method.getParameters().stream().map(p -> {
            val name = p.getSimpleName().toString();
            val type = typeOf(element, p.asType());
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
    
    private Type typeOf(Element element, TypeMirror typeMirror) {
        val typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType)
            return new Type(typeStr);
        
        if (typeMirror instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)typeMirror).asElement());
            val typeName    = typeElement.getSimpleName().toString();
            val generics    = extractGenerics(element, typeElement.getTypeParameters());
            val packageName = getPackageName(element, typeElement);
            val type        = new Type(packageName, null, typeName, generics);
            error(element, typeElement.getTypeParameters().toString() + " -- " + type.toString());
            return type;
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
