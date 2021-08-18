package functionalj.types.input;

import static functionalj.types.Utils.blankToNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import functionalj.types.Choice;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import lombok.val;

public class Environment {
    
    private final Element       element;
    private final Elements      elementUtils;
    private final Types         typeUtils;
    private final Messager      messager;
    private final Filer         filer;
    private final AtomicBoolean hasError = new AtomicBoolean(false);
    
    public Environment(Element element, Elements elementUtils, Types typeUtils, Messager messager, Filer filer) {
        this.element      = element;
        this.elementUtils = elementUtils;
        this.typeUtils    = typeUtils;
        this.messager     = messager;
        this.filer        = filer;
    }
    
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
            return extractPackageNameFromType((TypeElement)element);
        if (element instanceof ExecutableElement)
            return extractPackageNameFromMethod((ExecutableElement)element);
        throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
    }
    
    public String sourceName() {
        val packageName = packageName();
        if (element instanceof TypeElement) {
            val typeElement = (TypeElement)element;
            return typeElement.getQualifiedName().toString().substring(packageName.length() + 1 );
        }
        if (element instanceof ExecutableElement) {
            return null;
        }
        throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
    }
    
    private String extractPackageNameFromType(TypeElement type) {
        val packageName = elementUtils.getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    private String extractPackageNameFromMethod(ExecutableElement method) {
        val type        = (TypeElement)(method.getEnclosingElement());
        val packageName = elementUtils.getPackageOf(type).getQualifiedName().toString();
        return packageName;
    }
    
    public <T extends Annotation, D> D useAnnotation(Class<T> annotationClass, Function<T, D> action) {
        val annotation = element.getAnnotation(annotationClass);
        return action.apply(annotation);
    }
    
    public String elementSimpleName() {
        return element.getSimpleName().toString();
    }
    
    public List<String> readLocalTypeWithLens() {
        return element
                .getEnclosingElement()
                .getEnclosedElements().stream()
                .filter (elmt -> isStructOrChoise(elmt))
                .map    (elmt -> targetName(elmt))
                .filter (name -> nonNull(name))
                .collect(toList());
    }
    
    private boolean isStructOrChoise(Element elmt) {
        return (elmt.getAnnotation(Struct.class) != null)
            || (elmt.getAnnotation(Choice.class) != null);
    }
    
    //== From annotation ==
    
    public String targetName() {
        return targetName(element);
    }
    
    private String targetName(Element element) {
        val specifiedTargetName = specifiedTargetName();
        val simpleName          = element.getSimpleName().toString();
        return extractTargetName(simpleName, specifiedTargetName);
    }
    
    public String specifiedTargetName() {
        if (element.getAnnotation(Struct.class) != null) {
            return blankToNull(element.getAnnotation(Struct.class).name());
            
        }
        if (element.getAnnotation(Choice.class) != null) {
            return blankToNull(element.getAnnotation(Choice.class).name());
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + element);
    }
    
    public String specifiedSpecField() {
        if (element.getAnnotation(Struct.class) != null) {
            val specField = element.getAnnotation(Struct.class).specField();
            return blankToNull(specField);
        }
        if (element.getAnnotation(Choice.class) != null) {
            val specField = element.getAnnotation(Choice.class).specField();
            return blankToNull(specField);
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + element);
    }
    
    public Serialize.To specifiedSerialize() {
        if (element.getAnnotation(Struct.class) != null) {
            return element.getAnnotation(Struct.class).serialize();
        }
        if (element.getAnnotation(Choice.class) != null) {
            return element.getAnnotation(Choice.class).serialize();
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + element);
    }
    
    public String choiceTagMapKeyName() {
        if (element.getAnnotation(Choice.class) != null) {
            val tagMapKeyName = element.getAnnotation(Choice.class).tagMapKeyName();
            return blankToNull(tagMapKeyName);
        } else {
            return null;
        }
    }
    
    public boolean specifiedPublicField() {
        if (element.getAnnotation(Struct.class) != null) {
            return element.getAnnotation(Struct.class).publicFields();
        }
        if (element.getAnnotation(Choice.class) != null) {
            return element.getAnnotation(Choice.class).publicFields();
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + element);
    }
    
    //== Contains logics ==
    
    public String extractTargetName(String simpleName, String specTargetName) {
        if ((specTargetName != null) && !specTargetName.isEmpty())
            return specTargetName;
        
        if (simpleName.matches("^.*Spec$"))
            return simpleName.replaceAll("Spec$", "");
        
        if (simpleName.matches("^.*Model$"))
            return simpleName.replaceAll("Model$", "");
        
        return simpleName;
    }
    
    public void generateCode(String className, String content) throws IOException {
        try (Writer writer = filer.createSourceFile(className, element).openWriter()) {
            writer.write(content);
        }
    }
    
    public boolean isInterface() {
        return ElementKind.INTERFACE.equals(element.getKind());
    }
    
    // To get rid off.
    
    public Element element() {
        return element;
    }
    
    public Elements elementUtils() {
        return elementUtils;
    }
    
    public List<? extends TypeParameterElement> typeParameters() {
        val typeElement = (TypeElement)element;
        return typeElement.getTypeParameters();
    }
    
}
