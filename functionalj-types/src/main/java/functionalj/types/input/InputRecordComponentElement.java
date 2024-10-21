package functionalj.types.input;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public interface InputRecordComponentElement extends InputElement {
    
    public static class Impl extends InputElement.Impl implements InputRecordComponentElement {
        
        Impl(Environment environment, Element element) {
            super(environment, element);
        }
        
        // == Builder ==
        @SuppressWarnings("rawtypes")
        public static class Builder extends InputElement.Mock.Builder {
            
            public Builder simpleName(String simpleName) {
                super.simpleName(simpleName);
                return this;
            }
            
            public Builder packageQualifiedName(String packageQualifiedName) {
                super.packageQualifiedName(packageQualifiedName);
                return this;
            }
            
            public Builder kind(ElementKind kind) {
                super.kind(kind);
                return this;
            }
            
            public Builder modifiers(Modifier... modifiers) {
                super.modifiers(modifiers);
                return this;
            }
            
            public Builder modifiers(Set<Modifier> modifiers) {
                super.modifiers(modifiers);
                return this;
            }
            
            public Builder enclosingElement(InputElement enclosingElement) {
                super.enclosingElement(enclosingElement);
                return this;
            }
            
            public Builder enclosedElements(InputElement... enclosedElements) {
                super.enclosedElements(enclosedElements);
                return this;
            }
            
            public Builder enclosedElements(List<InputElement> enclosedElements) {
                super.enclosedElements(enclosedElements);
                return this;
            }
            
            public Builder enclosedElements(Supplier<List<InputElement>> enclosedElementsSupplier) {
                super.enclosedElements(enclosedElementsSupplier);
                return this;
            }
            
            public Builder annotations(Class clzz, Annotation annotation) {
                super.annotations(clzz, annotation);
                return this;
            }
            
            public Builder annotations(Function<Class, Annotation> annotations) {
                super.annotations(annotations);
                return this;
            }
            
            public Builder asType(InputType asType) {
                super.asType(asType);
                return this;
            }
            
            public Builder componentType(InputType componentType) {
                super.asType(componentType);
                return this;
            }
            
            public Builder printElement(String printElement) {
                super.printElement(printElement);
                return this;
            }
            
            public Builder toString(String toString) {
                super.toString(toString);
                return this;
            }
            
        }
    }
    
    @Override
    public default InputRecordComponentElement asRecordComponentElement() {
        return this;
    }
    
}
