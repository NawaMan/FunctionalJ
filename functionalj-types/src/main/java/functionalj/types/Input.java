package functionalj.types;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;

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
