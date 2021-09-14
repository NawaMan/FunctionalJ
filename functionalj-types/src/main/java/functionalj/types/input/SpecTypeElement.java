package functionalj.types.input;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.lang.model.element.TypeElement;

public interface SpecTypeElement extends SpecElement {
    
    public static SpecTypeElement of(Environment environment, TypeElement typeElement) {
        return new Impl(environment, typeElement);
    }
    
    public static class Impl extends SpecElement.Impl implements SpecTypeElement {
        
        final TypeElement typeElement;
        
        Impl(Environment environment, TypeElement typeElement) {
            super(environment, typeElement);
            this.typeElement = typeElement;
        }
        
        @Override
        public String getQualifiedName() {
            return typeElement.getQualifiedName().toString();
        }
        
        @Override
        public List<? extends SpecTypeParameterElement> getTypeParameters() {
            return typeElement
                    .getTypeParameters().stream()
                    .map(element -> SpecTypeParameterElement.of(environment, element))
                    .collect(toList());
        }
        
    }
    
    public String getQualifiedName();

    public List<? extends SpecTypeParameterElement> getTypeParameters();
    
}
