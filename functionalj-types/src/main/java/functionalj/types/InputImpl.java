package functionalj.types;

import static java.util.stream.Collectors.toList;

import java.util.EnumSet;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;


public class InputImpl implements Input {
    
    public static interface Source {
        
        Element  element();
        Elements elementUtils();
        Types    typeUtils();
        Messager messager();
        
    }
    
    private static final EnumSet<ElementKind> typeElementKinds = EnumSet.of(
            ElementKind.ENUM,
            ElementKind.CLASS,
            ElementKind.ANNOTATION_TYPE,
            ElementKind.INTERFACE,
            ElementKind.METHOD);
    
    @Value
    @Accessors(fluent = true)
    public static class SourceImpl implements Source {
        
        private final Element  element;
        private final Elements elementUtils;
        private final Types    typeUtils;
        private final Messager messager;
        
    }
    
    private final Source source;
    private boolean hasError = false;
    
    public InputImpl(Source source) {
        this.source = source;
    }
    
    public boolean hasError() {
        return hasError;
    }
    
    public void error(Element element, String message) {
        hasError = true;
        source.messager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
    
    public String packageName() {
        val element = source.element();
        if (element instanceof TypeElement)
            return extractPackageNameType(element);
        if (element instanceof ExecutableElement)
            return extractPackageNameMethod(element);
        throw new IllegalArgumentException("Struct annotation is only support class or method.");
    }
    
    public String targetTypeName() {
        val element        = source.element();
        val struct         = element.getAnnotation(Struct.class);
        val specTargetName = struct.name();
        return specTargetName;
    }

    public Generic getGenericFromTypeParameter(Element element, TypeParameterElement typeParameter){
        val name   = typeParameter.getSimpleName().toString();
        val bounds = typeParameter.getBounds().stream()
                    .map    (bound -> getType(element, bound))
                    .collect(toList());
        return new Generic(name, null, bounds);
    }

    public String extractPackageNameType(Element element) {
        val type        = (TypeElement)element;
        val packageName = source.elementUtils().getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    public String extractPackageNameMethod(Element element) {
        val method      = (ExecutableElement)element;
        val type        = (TypeElement)(method.getEnclosingElement());
        val packageName = source.elementUtils().getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    public Type getType(Element element, TypeMirror typeMirror) {
        val typeStr = typeMirror.toString();
        if (typeMirror instanceof PrimitiveType)
            return Type.primitiveTypes.get(typeStr);
        
        if (typeMirror instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)typeMirror).asElement());
            val typeName = typeElement.getSimpleName().toString();
            if (typeName.equals("String"))
                return Type.STRING;
            
            val generics = ((DeclaredType)typeMirror).getTypeArguments().stream()
                    .map(typeArg -> getType(element, (TypeMirror)typeArg))
                    .map(type    -> new Generic(type))
                    .collect(toList());
            
            val packageName = getPackageName(element, typeElement);
            val encloseElmt = typeElement.getEnclosingElement();
            val encloseName = typeElementKinds.contains(encloseElmt.getKind()) ? encloseElmt.getSimpleName().toString() : null;
            return new Type(packageName, encloseName, typeName, generics);
        }
        return Type.newVirtualType(typeMirror.toString());
    }
    
    public String getPackageName(Element element, TypeElement typeElement) {
        val elementUtils = source.elementUtils();
        val typePackage = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        if (!typePackage.isEmpty())
            return typePackage;
        
        val packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        return packageName;
    }
    
}
