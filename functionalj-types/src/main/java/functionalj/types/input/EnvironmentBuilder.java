package functionalj.types.input;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class EnvironmentBuilder {
    private final Elements elementUtils;
    private final Types    typeUtils;
    private final Messager messager;
    
    private boolean hasError = false;
    
    public EnvironmentBuilder(Elements elementUtils, Types typeUtils, Messager messager) {
        this.elementUtils = elementUtils;
        this.typeUtils    = typeUtils;
        this.messager     = messager;
    }
    
    public Environment newEnvironment(Element element) {
        return new Environment(element, elementUtils, typeUtils, messager);
    }
    
    public Elements elementUtils() {
        return elementUtils;
    }
    
    public Types typeUtils() {
        return typeUtils;
    }
    
    public Messager messager() {
        return messager;
    }
    
    public void error(Element element, String message) {
        hasError = true;
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
    
    public boolean hasError() {
        return hasError;
    }
    
}
