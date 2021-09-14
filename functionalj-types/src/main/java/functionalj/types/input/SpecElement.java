package functionalj.types.input;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import functionalj.types.Choice;
import functionalj.types.Struct;

public interface SpecElement {
    
    public static SpecElement of(Environment environment, Element element) {
        return new Impl(environment, element);
    }
    
    public static class Impl implements SpecElement {
        
        final Environment environment;
        final Element     element;
        
        Impl(Environment environment, Element element) {
            this.environment = environment;
            this.element     = element;
        }
        
        @Override
        public String getSimpleName() {
            return element.getSimpleName().toString();
        }
        
        @Override
        public String getPackageQualifiedName() {
            return environment.elementUtils.getPackageOf(element).getQualifiedName().toString();
        }
        
        @Override
        public String getEnclosingElementSimpleName() {
            return element.getEnclosingElement().getSimpleName().toString();
        }
        
        @Override
        public ElementKind getKind() {
            return element.getKind();
        }
        
        @Override
        public SpecElement getEnclosingElement() {
            return SpecElement.of(environment, element.getEnclosingElement());
        }
        
        @Override
        public boolean isStructOrChoise() {
            return (element.getAnnotation(Struct.class) != null)
                || (element.getAnnotation(Choice.class) != null);
        }
        
        @Override
        public boolean isInterface() {
            return ElementKind.INTERFACE.equals(element.getKind());
        }
        
        @Override
        public boolean isClass() {
            return ElementKind.CLASS.equals(element.getKind());
        }
        
        @Override
        public boolean isMethod() {
            return ElementKind.METHOD.equals(element.getKind());
        }
        
        @Override
        public boolean isTypeElement() {
            return (element instanceof TypeElement);
        }
        
        @Override
        public SpecTypeElement asTypeElement() {
            return isTypeElement() 
                    ? SpecTypeElement.of(environment, ((TypeElement)element)) 
                    : null;
        }
        
        @Override
        public boolean isMethodElement() {
            return (element instanceof ExecutableElement);
        }
        
        @Override
        public SpecMethodElement asMethodElement() {
            return isMethodElement() 
                            ? SpecMethodElement.of(environment, ((ExecutableElement)element)) 
                            : null;
        }
        
        @Override
        public List<? extends SpecElement> getEnclosedElements() {
            return element
                    .getEnclosedElements().stream()
                    .map(element -> SpecElement.of(environment, element))
                    .collect(toList());
        }
        
        @Override
        public void error(String msg) {
            environment.messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
        }
        
        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return element.getAnnotation(annotationType);
        }
        
        public void generateCode(String className, String content) throws IOException {
            try (Writer writer = environment.filer.createSourceFile(className, element).openWriter()) {
                writer.write(content);
            }
        }
        
    }
    
    public String getSimpleName();
    
    public String getPackageQualifiedName();
    
    public String getEnclosingElementSimpleName();
    
    public ElementKind getKind();
    
    public SpecElement getEnclosingElement();
    
    public boolean isStructOrChoise();
    
    public boolean isInterface();
    
    public boolean isClass();
    
    public boolean isMethod();
    
    public boolean isTypeElement();
    
    public SpecTypeElement asTypeElement();
    
    public boolean isMethodElement();
    
    public SpecMethodElement asMethodElement();
    
    public List<? extends SpecElement> getEnclosedElements();
    
    public void error(String msg);
    
    public <A extends Annotation> A getAnnotation(Class<A> annotationType);
    
    public void generateCode(String className, String content) throws IOException;
    
}
