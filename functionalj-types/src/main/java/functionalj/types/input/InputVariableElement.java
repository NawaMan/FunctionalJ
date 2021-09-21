package functionalj.types.input;

import javax.lang.model.element.VariableElement;

public interface InputVariableElement extends InputElement {
    
    public static class Impl extends InputElement.Impl implements InputVariableElement {
        
        @SuppressWarnings("unused")
        private final VariableElement variableElement;
        
        Impl(Environment environment, VariableElement variableElement) {
            super(environment, variableElement);
            this.variableElement = variableElement;
        }
        
        
    }
    
    public default InputTypeElement asTypeElement() {
        return null;
    }
    
    public default InputMethodElement asMethodElement() {
        return null;
    }
    
    public default InputVariableElement asVariableElement() {
        return this;
    }
    
}
