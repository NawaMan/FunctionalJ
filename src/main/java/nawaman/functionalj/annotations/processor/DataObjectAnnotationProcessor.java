package nawaman.functionalj.annotations.processor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import lombok.val;
import nawaman.functionalj.annotations.DataObject;

public class DataObjectAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private boolean hasError;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
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
            val simpleName  = type.getSimpleName();
            val isInterface = ElementKind.INTERFACE.equals(element.getKind());
            val isClass     = ElementKind.CLASS    .equals(element.getKind());
            if (!isInterface && !isClass) {
                error(element, "Only a class nor interface can be annotated with " + DataObject.class.getSimpleName() + ": " + simpleName);
                continue;
            }
            
            val getters = type.getEnclosedElements().stream()
            .filter(elmt  ->elmt.getKind().equals(ElementKind.METHOD))
            .map   (elmt  ->((ExecutableElement)elmt))
            .filter(method->method.getParameters().isEmpty())
            .map   (method->method.getSimpleName() + ": " +  method.getReturnType().toString())
            .collect(Collectors.joining(","));
            
            val packageName = getPackage(type);
            error(element, "Element: " + simpleName + " - " + packageName + " - " + isClass + " & " + isInterface + "(" + getters + ")");
        }
        return hasError;
    }
    
    private String getPackage(TypeElement type) {
        Element element = type;
        while (!element.getKind().equals(ElementKind.PACKAGE))
            element = element.getEnclosingElement();
        return ((QualifiedNameable)element).getQualifiedName().toString();
    }
    
}
