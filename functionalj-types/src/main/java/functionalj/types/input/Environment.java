package functionalj.types.input;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class Environment {
    
    private final Element  element;
    private final Elements elementUtils;
    private final Types    typeUtils;
    private final Messager messager;
    
    private AtomicBoolean hasError = new AtomicBoolean(false);
    
    public boolean hasError() {
        return hasError.get();
    }
    
    public void error(String msg) {
        hasError.set(true);
        messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
    }
    
    public void error(Element element, String msg) {
        hasError.set(true);
        messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
    }
    
    public String packageName() {
        if (element instanceof TypeElement)
            return extractPackageNameType(element);
        if (element instanceof ExecutableElement)
            return extractPackageNameMethod(element);
        throw new IllegalArgumentException("Struct annotation is only support class or method.");
    }

    private String extractPackageNameType(Element element) {
        val type        = (TypeElement)element;
        val packageName = elementUtils.getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    private String extractPackageNameMethod(Element element) {
        val method      = (ExecutableElement)element;
        val type        = (TypeElement)(method.getEnclosingElement());
        val packageName = elementUtils.getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
}
