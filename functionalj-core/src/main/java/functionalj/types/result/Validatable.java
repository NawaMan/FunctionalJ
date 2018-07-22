package functionalj.types.result;

import java.util.function.Predicate;

public interface Validatable<DATA extends Validatable<DATA, VALIDATOR>, VALIDATOR extends Predicate<? super DATA>> {
    
    public Class<VALIDATOR> getValidatorClass();
    
    public default Valid<DATA> toValidValue() {
        return Valid.valueOf((DATA)this);
    } 
    
    
    public static abstract class With<D extends Validatable<D, C>, C extends Predicate<? super D>> implements Validatable<D, C> {
        
        private Class<C> clzz;
        
        protected With(Class<C> clzz) {
            this.clzz = clzz;
        }
        
        public final Class<C> getValidatorClass() {
            return clzz;
        }
        
    }
    
}
