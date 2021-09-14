package functionalj.types.input;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.TypeParameterElement;

public interface SpecTypeParameterElement extends SpecElement {
    
    public static SpecTypeParameterElement of(Environment environment, TypeParameterElement typeParameterElement) {
        return new Impl(environment, typeParameterElement);
    }
    
    public static class Impl extends SpecElement.Impl implements SpecTypeParameterElement {
        
        final Environment          environment;
        final TypeParameterElement typeParameterElement;
        
        Impl(Environment environment, TypeParameterElement typeParameterElement) {
            super(environment, typeParameterElement);
            this.environment          = environment;
            this.typeParameterElement = typeParameterElement;
        }
        
        @Override
        public List<? extends SpecTypeMirror> getBounds() {
            return typeParameterElement
                    .getBounds().stream()
                    .map(elmt -> SpecTypeMirror.of(environment, elmt))
                    .collect(Collectors.toList());
        }
        
        @Override
        public String toString() {
            return typeParameterElement.toString();
        }
        
    }
    
    public List<? extends SpecTypeMirror> getBounds();
    
}
