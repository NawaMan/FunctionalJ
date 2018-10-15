package functionalj.types.result;

import java.util.function.Predicate;

import functionalj.functions.Func1;
import lombok.val;

@SuppressWarnings("javadoc")
public interface ResultFilterAddOn<DATA> {
    
    public Result<DATA> asResult();
    
    public Result<DATA> filter(Predicate<? super DATA> theCondition);
    
    public default <T extends DATA> Result<DATA> filter(Class<T> clzz) {
        return filter(clzz::isInstance);
    }
    
    public default <T extends DATA> Result<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return filter(value -> {
            if (clzz.isInstance(value))
                return false;
            
            val target = clzz.cast(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> Result<DATA> filter(Func1<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
}