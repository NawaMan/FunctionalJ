package functionalj.types.result;

@SuppressWarnings("javadoc")
public interface ResultFilter<DATA> {

    // public Result<DATA> asResult();

    // public Result<DATA> filter(Predicate<? super DATA> theCondition);

    // public final <T extends DATA> Result<DATA> filter(Class<T> clzz) {
    //     return filter(clzz::isInstance);
    // }

    // public final <T extends DATA> Result<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
    //     val value = get();
    //     if (value == null)
    //         return asResult();
        
    //     if (clzz.isInstance(value))
    //         return asResult();

    //     val target = clzz.cast(value);
    //     val isPass = theCondition.test(target);
    //     if (!isPass)
    //         return Result.ofNull();
        
    //     return asResult();
    // }

    // public final <T> Result<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
    //     DATA value = get();
    //     if (value == null)
    //         return asResult();

    //     val target = mapper.apply(value);
    //     val isPass = theCondition.test(target);
    //     if (!isPass)
    //         return Result.ofNull();
        
    //     return asResult();
    // }

}