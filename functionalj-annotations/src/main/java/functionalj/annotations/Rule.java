package functionalj.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a method to create a rule type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Rule {
    
    /** The error message used in the case of boolean validation */
    public String value() default "";
    
}
