package functionalj.result;

import java.util.function.Predicate;

import lombok.val;

@SuppressWarnings("javadoc")
public interface Validatable<DATA extends Validatable<DATA, VALIDATOR>, VALIDATOR extends Predicate<? super DATA>> {
    
    public Class<VALIDATOR> getValidatorClass();
    
    public default String getValidationErrorTemplateByValue() {
        return null;
    }
    
    public default Valid<DATA> toValidValue() {
        @SuppressWarnings("unchecked")
		val validResult = new Valid<DATA>((DATA)this);
        return validResult;
    } 
    
    
    public static abstract class With<D extends Validatable<D, C>, C extends Predicate<? super D>> implements Validatable<D, C> {
        
        private Class<C> clzz;
        private String   validationErrorTemplateByValue;
        
        protected With(Class<C> clzz) {
            this(clzz, null);
        }
        protected With(Class<C> clzz, String validationErrorTemplateByValue) {
            this.clzz = clzz;
            this.validationErrorTemplateByValue = validationErrorTemplateByValue;
        }
        
        public final Class<C> getValidatorClass() {
            return clzz;
        }
        public final String getValidationErrorTemplateByValue() {
            return validationErrorTemplateByValue;
        }
        
    }
    
}
