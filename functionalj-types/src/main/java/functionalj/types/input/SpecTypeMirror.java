package functionalj.types.input;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

public interface SpecTypeMirror {
    
    public static SpecTypeMirror of(Environment environment, TypeMirror typeMirror) {
        return new Impl(environment, typeMirror);
    }
    
    public static class Impl implements SpecTypeMirror {
        
        final Environment environment;
        final TypeMirror  typeMirror;
        
        Impl(Environment environment, TypeMirror typeMirror) {
            this.environment = environment;
            this.typeMirror  = typeMirror;
        }
        
        @Override
        public boolean isPrimitiveType() {
            return (typeMirror instanceof PrimitiveType);
        }
        
        @Override
        public PrimitiveType asPrimitiveType() {
            return isPrimitiveType()
                    ? ((PrimitiveType)typeMirror)
                    : null;
        }
        
        @Override
        public boolean isDeclaredType() {
            return (typeMirror instanceof DeclaredType);
        }
        
        @Override
        public SpecTypeElement asDeclaredType() {
            return SpecTypeElement.of(environment, ((TypeElement)((DeclaredType)typeMirror).asElement()));
        }
        
        public boolean isTypeVariable() {
            return (typeMirror instanceof TypeVariable);
        }
        
        public SpecTypeVariable asTypeVariable() {
            return SpecTypeVariable.of(environment, (TypeVariable)typeMirror);
        }
        
        @Override
        public boolean isNoType() {
            return typeMirror instanceof NoType;
        }
        
        @Override
        public List<? extends SpecTypeMirror> getTypeArguments() {
            return ((DeclaredType)typeMirror)
                    .getTypeArguments().stream()
                    .map(elmt -> SpecTypeMirror.of(environment, elmt))
                    .collect(Collectors.toList());
        }
        
        @Override
        public String getToString() {
            return typeMirror.toString();
        }
        
        @Override
        public String toString() {
            return typeMirror.toString();
        }
        
    }
    
    public boolean isPrimitiveType();
//    
    public PrimitiveType asPrimitiveType();
//    
    public boolean isDeclaredType();
    
    public SpecTypeElement asDeclaredType();
    
    public boolean isTypeVariable();
    
    public SpecTypeVariable asTypeVariable();
    
    public boolean isNoType();
    
    public List<? extends SpecTypeMirror> getTypeArguments();
    
    public String getToString();
    
}
