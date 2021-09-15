package functionalj.types.input;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class Environment {
    
    final Elements elementUtils;
    final Types    typeUtils;
    final Messager messager;
    final Filer    filer;
    
    private final AtomicBoolean hasError = new AtomicBoolean(false);
    
    public Environment(Elements elementUtils, Types typeUtils, Messager messager, Filer filer) {
        this.elementUtils = elementUtils;
        this.typeUtils    = typeUtils;
        this.messager     = messager;
        this.filer        = filer;
    }
    
    public void markHasError() {
        hasError.set(true);
    }
    
    public boolean hasError() {
        return hasError.get();
    }
    
    public String extractTargetName(String simpleName, String specTargetName) {
        if ((specTargetName != null) && !specTargetName.isEmpty())
            return specTargetName;
        
        if (simpleName.matches("^.*Spec$"))
            return simpleName.replaceAll("Spec$", "");
        
        if (simpleName.matches("^.*Model$"))
            return simpleName.replaceAll("Model$", "");
        
        return simpleName;
    }
    
}
