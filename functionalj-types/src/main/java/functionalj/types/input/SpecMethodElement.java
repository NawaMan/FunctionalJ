package functionalj.types.input;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public interface SpecMethodElement extends SpecElement {
    
    public static SpecMethodElement of(Environment environment, ExecutableElement executableElement) {
        return new Impl(environment, executableElement);
    }
    
    public static class Impl extends SpecElement.Impl implements SpecMethodElement {
        
        final ExecutableElement executableElement;
        
        Impl(Environment environment, ExecutableElement executableElement) {
            super(environment, executableElement);
            this.executableElement = executableElement;
        }
        
        @Override
        public SpecTypeMirror getReturnType() {
            val returnType = executableElement.getReturnType();
            return SpecTypeMirror.of(environment, returnType);
        }
        
        @Override
        public boolean isStatic() {
            return executableElement.getModifiers().contains(Modifier.STATIC);
        }
        
        @Override
        public boolean isPublic() {
            return executableElement.getModifiers().contains(Modifier.PUBLIC);
        }
        
        @Override
        public boolean isPrivate() {
            return executableElement.getModifiers().contains(Modifier.PRIVATE);
        }
        
        @Override
        public boolean isProtected() {
            return executableElement.getModifiers().contains(Modifier.PROTECTED);
        }
        
        @Override
        public boolean isDefault() {
            return executableElement.isDefault();
        }
        
        @Override
        public boolean isAbstract() {
            // Seriously ... no other way?
            try (val writer = new StringWriter()) {
                environment.elementUtils().printElements(writer, executableElement);
                return writer.toString().contains(" abstract ");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        
        @Override
        public boolean isVarArgs() {
            return executableElement.isVarArgs();
        }
        
        @Override
        public Accessibility accessibility() {
            if (executableElement.getModifiers().contains(Modifier.PRIVATE))
                return Accessibility.PRIVATE;
            if (executableElement.getModifiers().contains(Modifier.DEFAULT))
                return Accessibility.PACKAGE;
            if (executableElement.getModifiers().contains(Modifier.PROTECTED))
                return Accessibility.PROTECTED;
            if (executableElement.getModifiers().contains(Modifier.PUBLIC))
                return Accessibility.PUBLIC;
            return Accessibility.PACKAGE;
        }
        
        @Override
        public Scope scope() {
            return executableElement.getModifiers().contains(Modifier.STATIC) ? Scope.STATIC : Scope.INSTANCE;
        }
        
        @Override
        public Modifiability modifiability() {
            return executableElement.getModifiers().contains(Modifier.FINAL) ? Modifiability.FINAL : Modifiability.MODIFIABLE;
        }
        
        @Override
        public Concrecity concrecity() {
            if (isDefault())
                return Concrecity.DEFAULT;
            return executableElement.getModifiers().contains(Modifier.ABSTRACT) ? Concrecity.ABSTRACT : Concrecity.CONCRETE;
        }
        
        @Override
        public List<? extends SpecVariableElement> getParameters() {
            return executableElement
                    .getParameters().stream()
                    .map(element -> SpecVariableElement.of(environment, element))
                    .collect(Collectors.toList());
        }
        
        @Override
        public List<? extends SpecTypeParameterElement> getTypeParameters() {
            return executableElement
                            .getTypeParameters().stream()
                            .map(element -> SpecTypeParameterElement.of(environment, element))
                            .collect(Collectors.toList());
        }
        
        public List<? extends SpecTypeMirror> getThrownTypes() {
            return executableElement
                            .getThrownTypes().stream()
                            .map(element -> SpecTypeMirror.of(environment, element))
                            .collect(Collectors.toList());
        }
        
        @Override
        public List<? extends SpecElement> getEnclosedElements() {
            return executableElement
                            .getEnclosedElements().stream()
                            .map(element -> SpecElement.of(environment, element))
                            .collect(Collectors.toList());
        }
        
    }
    
    public SpecTypeMirror getReturnType();
    
    public boolean isStatic();
    
    public boolean isPrivate();
    
    public boolean isPublic();
    
    public boolean isProtected();
    
    public boolean isDefault();
    
    public boolean isAbstract();
    
    public boolean isVarArgs();
    
    public Accessibility accessibility();
    
    public Scope scope();
    
    public Modifiability modifiability();
    
    public Concrecity concrecity();
    
    public List<? extends SpecVariableElement> getParameters();
    
    public List<? extends SpecTypeParameterElement> getTypeParameters();
    
    public List<? extends SpecTypeMirror> getThrownTypes();
    
    public List<? extends SpecElement> getEnclosedElements();
    
}
