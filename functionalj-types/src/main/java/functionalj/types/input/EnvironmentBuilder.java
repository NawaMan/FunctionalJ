package functionalj.types.input;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class EnvironmentBuilder {
    private final Elements elementUtils;
    private final Types    typeUtils;
    private final Messager messager;
    private final Filer    filer;
    
    private boolean hasError = false;
    
    public EnvironmentBuilder(Elements elementUtils, Types typeUtils, Messager messager, Filer filer) {
        this.elementUtils = elementUtils;
        this.typeUtils    = typeUtils;
        this.messager     = messager;
        this.filer        = filer;
    }
    
    public Environment newEnvironment(Element element) {
        return new Environment(element, elementUtils, typeUtils, messager, filer);
    }
    
    public boolean hasError() {
        return hasError;
    }
    
}
