package functionalj.types;

import static java.util.stream.Collectors.toList;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import lombok.val;

public interface Input {

    
    public boolean hasError();
    
    public void error(Element error, String message);
    
    public String packageName();
    
    public String targetTypeName();
    
    public Generic getGenericFromTypeParameter(Element element, TypeParameterElement typeParameter);
    
    public String extractPackageNameType(Element element);
    
    public String extractPackageNameMethod(Element element);
    
    public Type getType(Element element, TypeMirror typeMirror);
    
    public String getPackageName(Element element, TypeElement typeElement);
    
}
