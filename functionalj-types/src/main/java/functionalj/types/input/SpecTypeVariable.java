package functionalj.types.input;

import javax.lang.model.type.TypeVariable;

public interface SpecTypeVariable {
    
    public static SpecTypeVariable of(Environment environment, TypeVariable typeVariable) {
        return new Impl(environment, typeVariable);
    }
    
    public static class Impl implements SpecTypeVariable {
        
        final Environment  environment;
        final TypeVariable typeVariable;
        
        Impl(Environment environment, TypeVariable typeVariable) {
            this.environment   = environment;
            this.typeVariable  = typeVariable;
        }
        
        @Override
        public SpecTypeMirror getLowerBound() {
            return SpecTypeMirror.of(environment, typeVariable.getLowerBound());
        }
        
        @Override
        public SpecTypeMirror getUpperBound() {
            return SpecTypeMirror.of(environment, typeVariable.getUpperBound());
        }
        
        @Override
        public String toString() {
            return typeVariable.toString();
        }
        
    }
    
    public SpecTypeMirror getLowerBound();
    
    public SpecTypeMirror getUpperBound();
    
}
