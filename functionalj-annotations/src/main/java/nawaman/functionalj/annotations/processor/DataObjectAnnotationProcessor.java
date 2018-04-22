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
package nawaman.functionalj.annotations.processor;

import java.io.IOException;
import java.io.Writer;
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
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import lombok.val;
import nawaman.functionalj.annotations.DataObject;
import nawaman.functionalj.annotations.processor.generator.DataObjectBuilder;
import nawaman.functionalj.annotations.processor.generator.Getter;
import nawaman.functionalj.annotations.processor.generator.SourceSpec;
import nawaman.functionalj.annotations.processor.generator.SourceSpec.Configurations;
import nawaman.functionalj.annotations.processor.generator.Type;
import nawaman.functionalj.annotations.processor.generator.model.GenDataObject;

/**
 * Annotation processor for DataObject.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class DataObjectAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;
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
        annotations.add(DataObject.class.getCanonicalName());
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
        for (Element element : roundEnv.getElementsAnnotatedWith(DataObject.class)) {
            val type        = (TypeElement)element;
            val simpleName  = type.getSimpleName().toString();
            val isInterface = ElementKind.INTERFACE.equals(element.getKind());
            val isClass     = ElementKind.CLASS    .equals(element.getKind());
            if (!isInterface && !isClass) {
                error(element, "Only a class nor interface can be annotated with " + DataObject.class.getSimpleName() + ": " + simpleName);
                continue;
            }
            
            List<Getter> getters = type.getEnclosedElements().stream()
                    .filter(elmt  ->elmt.getKind().equals(ElementKind.METHOD))
                    .map   (elmt  ->((ExecutableElement)elmt))
                    .filter(method->method.getParameters().isEmpty())
                    .map   (method->createGetterFromMethod(method))
                    .collect(toList());
            
            val packageName    = elementUtils.getPackageOf(type).getQualifiedName().toString();
            val sourceName     = type.getQualifiedName().toString().substring(packageName.length() + 1 );
            val dataObject     = element.getAnnotation(DataObject.class);
            val specTargetName = dataObject.name();
            val targetName     = ((specTargetName == null) || specTargetName.isEmpty()) ? simpleName : specTargetName;
            
            val configures = new Configurations();
            configures.noArgConstructor  = dataObject.generateNoArgConstructor();
            configures.generateLensClass = dataObject.generateLensClass();
            
            try {
                val sourceSpec
                        = new SourceSpec(
                            sourceName, packageName, targetName, packageName,
                            isClass,
                            configures, getters);
                val dataObjSpec = new DataObjectBuilder(sourceSpec).build();
                val className   = (String)dataObjSpec.type().fullName();
                val content     = new GenDataObject(dataObjSpec).lines().collect(joining("\n"));
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

    private Getter createGetterFromMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        Type   returnType = getType(method.getReturnType());
        Getter getter     = new Getter(methodName, returnType);
        return getter;
    }
    
    private Type getType(TypeMirror typeMirror) {
        val typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType) {
            // This is no right ... but let goes with this.
            if ("int".equals(typeStr))
                return Type.INT;
            if ("boolean".equals(typeStr))
                return Type.BOOL;
        }
        if (typeMirror instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)typeMirror).asElement());
            val typeName = typeElement.getSimpleName().toString();
            if (typeName.equals("String"))
                return Type.STRING;
            
            val packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            return new Type(typeName, packageName);
        }
        return null;
    }
    
    private void generateCode(Element element, String className, String content) throws IOException {
        try (Writer writer = filer.createSourceFile(className, element).openWriter()) {
            writer.write(content);
        }
    }
    
}
