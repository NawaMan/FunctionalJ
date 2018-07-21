package functionalj.types.result;

import java.util.function.Predicate;

public interface Validatable<DATA extends Validatable<DATA, CHECKER>, CHECKER extends Predicate<? super DATA>> {
    
    public Class<CHECKER> getCheckerClass();
    
    public default Valid<DATA> toValidValue() {
        return Valid.valueOf((DATA)this);
    } 
    
    
    public static abstract class With<D extends Validatable<D, C>, C extends Predicate<? super D>> implements Validatable<D, C> {
        
        private Class<C> clzz;
        
        protected With(Class<C> clzz) {
            this.clzz = clzz;
        }
        
        public final Class<C> getCheckerClass() {
            return clzz;
        }
        
    }
    
}
