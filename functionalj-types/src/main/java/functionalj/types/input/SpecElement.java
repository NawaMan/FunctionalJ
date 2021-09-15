package functionalj.types.input;

import static functionalj.types.Utils.blankToNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Function;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import functionalj.types.Choice;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import lombok.val;

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
            environment.markHasError();
        }
        
        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return element.getAnnotation(annotationType);
        }
        
        @Override
        public void generateCode(String className, String content) throws IOException {
            try (Writer writer = environment.filer.createSourceFile(className, element).openWriter()) {
                writer.write(content);
            }
        }
        
        @Override
        public String packageName() {
            if (isTypeElement())
                return extractPackageNameFromType(asTypeElement());
            if (isMethodElement())
                return extractPackageNameFromMethod(asMethodElement());
            throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
        }
        
        @Override
        public String sourceName() {
            val packageName = packageName();
            if (isTypeElement()) {
                val typeElement = asTypeElement();
                return typeElement.getPackageQualifiedName().substring(packageName.length() + 1 );
            }
            if (isMethodElement()) {
                return null;
            }
            throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
        }
        
        private String extractPackageNameFromType(SpecTypeElement type) {
            val packageName = type.getPackageQualifiedName();
            return packageName;
        }
        
        private String extractPackageNameFromMethod(SpecMethodElement method) {
            val type        = method.getEnclosingElement().asTypeElement();
            val packageName = type.getPackageQualifiedName();
            return packageName;
        }
        
        @Override
        public <T extends Annotation, D> D useAnnotation(Class<T> annotationClass, Function<T, D> action) {
            val annotation = element.getAnnotation(annotationClass);
            return action.apply(annotation);
        }
        
        @Override
        public String elementSimpleName() {
            return element.getSimpleName().toString();
        }
        
        @Override
        public List<String> readLocalTypeWithLens() {
            return getEnclosingElement()
                    .getEnclosedElements().stream()
                    .filter (elmt -> elmt.isStructOrChoise())
                    .map    (elmt -> targetName(elmt))
                    .filter (name -> nonNull(name))
                    .collect(toList());
        }
        
        //== From annotation ==
        
        @Override
        public String targetName() {
            return targetName(this);
        }
        
        private String targetName(SpecElement element) {
            val specifiedTargetName = specifiedTargetName();
            val simpleName          = element.getSimpleName().toString();
            return environment.extractTargetName(simpleName, specifiedTargetName);
        }
        
        @Override
        public String specifiedTargetName() {
            if (getAnnotation(Struct.class) != null) {
                return blankToNull(element.getAnnotation(Struct.class).name());
                
            }
            if (getAnnotation(Choice.class) != null) {
                return blankToNull(element.getAnnotation(Choice.class).name());
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public String specifiedSpecField() {
            if (getAnnotation(Struct.class) != null) {
                val specField = getAnnotation(Struct.class).specField();
                return blankToNull(specField);
            }
            if (getAnnotation(Choice.class) != null) {
                val specField = element.getAnnotation(Choice.class).specField();
                return blankToNull(specField);
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public Serialize.To specifiedSerialize() {
            if (getAnnotation(Struct.class) != null) {
                return getAnnotation(Struct.class).serialize();
            }
            if (getAnnotation(Choice.class) != null) {
                return getAnnotation(Choice.class).serialize();
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public String choiceTagMapKeyName() {
            if (getAnnotation(Choice.class) != null) {
                val tagMapKeyName = element.getAnnotation(Choice.class).tagMapKeyName();
                return blankToNull(tagMapKeyName);
            } else {
                return null;
            }
        }
        
        @Override
        public boolean specifiedPublicField() {
            if (getAnnotation(Struct.class) != null) {
                return getAnnotation(Struct.class).publicFields();
            }
            if (getAnnotation(Choice.class) != null) {
                return getAnnotation(Choice.class).publicFields();
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public List<? extends SpecTypeParameterElement> typeParameters() {
            return asTypeElement().getTypeParameters();
        }
        
        @Override
        public String printElement() {
            try (val writer = new StringWriter()) {
                environment.elementUtils.printElements(writer, element);
                return writer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        
    }
    
    public String packageName();
    
    public String sourceName();
    
    public String elementSimpleName();
    
    public List<String> readLocalTypeWithLens();
    
    public String getSimpleName();
    
    public String getPackageQualifiedName();
    
    public String getEnclosingElementSimpleName();
    
    public ElementKind getKind();
    
    public SpecElement getEnclosingElement();
    
    public boolean isStructOrChoise();
    
    public boolean isInterface();
    
    public boolean isClass();
    
    public boolean isTypeElement();
    
    public SpecTypeElement asTypeElement();
    
    public boolean isMethodElement();
    
    public SpecMethodElement asMethodElement();
    
    public List<? extends SpecElement> getEnclosedElements();
    
    public void error(String msg);
    
    public <A extends Annotation> A getAnnotation(Class<A> annotationType);
    
    public void generateCode(String className, String content) throws IOException;
    
    public <T extends Annotation, D> D useAnnotation(Class<T> annotationClass, Function<T, D> action);
    
    public String targetName();
    
    public String specifiedTargetName();
    public String specifiedSpecField();
    
    public Serialize.To specifiedSerialize();
    
    public String choiceTagMapKeyName();
    
    public boolean specifiedPublicField();
    
    public List<? extends SpecTypeParameterElement> typeParameters();
    
    public String printElement();
    
}
