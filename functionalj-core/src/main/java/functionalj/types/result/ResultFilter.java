package functionalj.types.result;

import java.util.function.Function;
import java.util.function.Predicate;

import lombok.val;

@SuppressWarnings("javadoc")
public interface ResultFilter<DATA> {

    public Result<DATA> asResult();

    public Result<DATA> filter(Predicate<? super DATA> theCondition);

    public default <T extends DATA> Result<DATA> filter(Class<T> clzz) {
        return filter(clzz::isInstance);
    }

    public default <T extends DATA> Result<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        val thisResult = asResult();
		val value      = thisResult.get();
        if (value == null)
            return thisResult;
        
        if (clzz.isInstance(value))
            return thisResult;

        val target = clzz.cast(value);
        val isPass = theCondition.test(target);
        if (!isPass)
            return Result.ofNull();
        
        return thisResult;
    }

    public default <T> Result<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        val thisResult = asResult();
		val value      = thisResult.get();
        if (value == null)
            return asResult();

        val target = mapper.apply(value);
        val isPass = theCondition.test(target);
        if (!isPass)
            return Result.ofNull();
        
        return asResult();
    }

}