package functionalj.types.result;

import java.util.function.Function;
import java.util.function.Predicate;

import nawaman.nullablej.nullable.Nullable;

public interface ResultFlatMapAddOn<DATA> {

    
    /**
     * Returns this object as a result.
     * 
     * @return this object as a result.
     **/
    public Result<DATA> asResult();
    
    
    public <TARGET> Result<TARGET> flatMap(Function<? super DATA, ? extends Nullable<TARGET>> mapper);
    
    
    public default <T> Result<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Result<T>> mapper, 
            Function<? super DATA, Result<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> Result<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Result<T>> mapper2,
            Function<? super DATA, Result<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Result<T>> mapper3,
            Function<? super DATA, Result<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Result<T>> mapper3,
            Predicate<? super DATA> checker4, Function<? super DATA, Result<T>> mapper4,
            Function<? super DATA, Result<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Result<T>> mapper3,
            Predicate<? super DATA> checker4, Function<? super DATA, Result<T>> mapper4,
            Predicate<? super DATA> checker5, Function<? super DATA, Result<T>> mapper5,
            Function<? super DATA, Result<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Result<T>> mapper3,
            Predicate<? super DATA> checker4, Function<? super DATA, Result<T>> mapper4,
            Predicate<? super DATA> checker5, Function<? super DATA, Result<T>> mapper5,
            Predicate<? super DATA> checker6, Function<? super DATA, Result<T>> mapper6,
            Function<? super DATA, Result<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
}
