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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import functionalj.annotations.UnionType;
import functionalj.annotations.uniontype.generator.Choice;
import functionalj.annotations.uniontype.generator.ChoiceParam;
import functionalj.annotations.uniontype.generator.Generator;
import functionalj.annotations.uniontype.generator.Type;
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
            val type        = (TypeElement)element;
            val simpleName  = type.getSimpleName().toString();
            val isInterface = ElementKind.INTERFACE.equals(element.getKind());
            if (!isInterface) {
                error(element, "Only an interface can be annotated with " + UnionType.class.getSimpleName() + ": " + simpleName);
                continue;
            }
            
            List<Choice> choices = type.getEnclosedElements().stream()
                    .filter(elmt->elmt.getKind().equals(ElementKind.METHOD))
                    .map   (elmt->((ExecutableElement)elmt))
                    .filter(mthd->!mthd.isDefault())
                    .filter(mthd->mthd.getSimpleName().toString().matches("^[A-Z].*$"))
                    .filter(mthd->mthd.getReturnType() instanceof NoType)
                    .map   (mthd->createChoiceFromMethod(element, mthd, type.getEnclosedElements()))
                    .collect(toList());
            
            val packageName    = elementUtils.getPackageOf(type).getQualifiedName().toString();
            val sourceName     = type.getQualifiedName().toString().substring(packageName.length() + 1 );
            val unionType      = element.getAnnotation(UnionType.class);
            val specTargetName = unionType.name();
            val targetName     = ((specTargetName == null) || specTargetName.isEmpty()) ? simpleName : specTargetName;
            val enclosedClass  = sourceName.substring(0, sourceName.length() - simpleName.length() - 1);
            
            try {
                val generator  = new Generator(targetName, new Type(packageName, enclosedClass, simpleName), choices);
                val className  = packageName + "." + targetName;
                val content    = generator.lines().stream().collect(joining("\n"));
                generateCode(element, className, content + "\n" + logs.stream().map("// "::concat).collect(joining("\n")));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                error(element, "Problem generating the class: "
                                + packageName + "." + specTargetName
                                + ": "  + e.getMessage()
                                + ":"   + e.getClass()
                                + " @ " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1] + "\n" + e.getStackTrace()[2]);
            }
        }
        return hasError;
    }
    
    private Choice createChoiceFromMethod(Element element, ExecutableElement method, List<? extends Element> elements) {
        String methodName = method.getSimpleName().toString();
        List<ChoiceParam> params = method.getParameters().stream().map(p -> {
            val name = p.getSimpleName().toString();
            val type = getType(element, p.asType());
            return new ChoiceParam(name, type);
        }).collect(toList());
        
        val hasValidator = elements.stream()
                .filter(elmt -> elmt.getKind().equals(ElementKind.METHOD))
                .map   (elmt -> ((ExecutableElement)elmt))
                .filter(mthd -> mthd.getSimpleName().toString().equals("validate" + methodName))
                .filter(mthd -> mthd.getTypeParameters().size() == method.getTypeParameters().size())
                .filter(mthd -> {
                    for (int i = 0; i < mthd.getTypeParameters().size(); i++) {
                        if (!mthd.getTypeParameters().get(i).equals(method.getTypeParameters().get(i)))
                            return false;
                    }
                    return true;
                })
                .filter(mthd -> {
//                    for (int i = 0; i < mthd.getParameters().size(); i++) {
//                        if (!mthd.getParameters().get(i).getSimpleName().toString().equals(method.getTypeParameters().get(i).getSimpleName().toString()))
//                            return false;
//                    }
                    return true;
                })
                .findFirst()
                .isPresent();
        
        Choice choice = hasValidator ? new Choice(methodName, "validate" + methodName, params) : new Choice(methodName, params);
        System.err.println("choice: " + choice);
        return choice;
    }
    
    private Type getType(Element element, TypeMirror typeMirror) {
        val typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType)
            return new Type(typeStr);
        
        if (typeMirror instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)typeMirror).asElement());
            val typeName = typeElement.getSimpleName().toString();
            if (typeName.equals("String"))
                return new Type("java.lang", "String");
            
            // TODO Generics not yet support
            
            val packageName = getPackageName(element, typeElement);
            return new Type(packageName, null, typeName);
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
