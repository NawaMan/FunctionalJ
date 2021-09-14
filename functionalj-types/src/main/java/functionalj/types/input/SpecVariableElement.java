package functionalj.types.input;

import javax.lang.model.element.VariableElement;

public interface SpecVariableElement extends SpecElement {
    
    public static SpecVariableElement of(Environment environment, VariableElement variableElement) {
        return new Impl(environment, variableElement);
    }
    
    public static class Impl extends SpecElement.Impl implements SpecVariableElement {
        
        private VariableElement variableElement;
        
        public Impl(Environment environment, VariableElement variableElement) {
            super(environment, variableElement);
            this.variableElement = variableElement;
        }
        
        @Override
        public SpecTypeMirror asType() {
            return SpecTypeMirror.of(environment, variableElement.asType());
        }
        
    }
    
    public SpecTypeMirror asType();
    
}
