package functionalj.types.result;

import java.util.function.Predicate;

public interface Validatable<DATA, CHECKER extends Predicate<? super DATA>> {
    
    public Class<CHECKER> getCheckerClass();
    
    
    public static abstract class With<D, C extends Predicate<? super D>> implements Validatable<D, C> {
        
        private Class<C> clzz;
        
        protected With(Class<C> clzz) {
            this.clzz = clzz;
        }
        
        public final Class<C> getCheckerClass() {
            return clzz;
        }
        
    }
    
}
