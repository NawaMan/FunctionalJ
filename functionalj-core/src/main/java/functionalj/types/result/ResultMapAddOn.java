package functionalj.types.result;

import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.types.map.FuncMap;
import functionalj.types.map.ImmutableMap;
import functionalj.types.tuple.Tuple2;
import functionalj.types.tuple.Tuple3;
import functionalj.types.tuple.Tuple4;
import functionalj.types.tuple.Tuple5;
import functionalj.types.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public interface ResultMapAddOn<DATA> {
    
    public <TARGET> Result<TARGET> map(Function<? super DATA, ? extends TARGET> mapper);
    
    public default <TARGET> Result<TARGET> mapTo(Func1<? super DATA, TARGET> mapper) {
        return map(mapper);
    }
    public default Result<DATA> mapIf(Predicate<? super DATA> checker, Func1<? super DATA, DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>  checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>  checker2, Func1<? super DATA, T> mapper2, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Func1<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Func1<? super DATA, T> mapper3, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Func1<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Func1<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Func1<? super DATA, T> mapper4, 
            Func1<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Func1<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Func1<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Func1<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Func1<? super DATA, T> mapper5, 
            Func1<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Func1<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Func1<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Func1<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Func1<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Func1<? super DATA, T> mapper6, 
            Func1<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Func1<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Func1<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Func1<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Func1<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Func1<? super DATA, T> mapper6, 
            Predicate<? super DATA>   checker7, Func1<? super DATA, T> mapper7, 
            Func1<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : checker7.test(d) ? mapper7.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Func1<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Func1<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Func1<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Func1<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Func1<? super DATA, T> mapper6, 
            Predicate<? super DATA>   checker7, Func1<? super DATA, T> mapper7, 
            Predicate<? super DATA>   checker8, Func1<? super DATA, T> mapper8, 
            Func1<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : checker7.test(d) ? mapper7.apply(d)
                 : checker8.test(d) ? mapper8.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> mapIf(
            Predicate<? super DATA>   checker1, Func1<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Func1<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Func1<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Func1<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Func1<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Func1<? super DATA, T> mapper6, 
            Predicate<? super DATA>   checker7, Func1<? super DATA, T> mapper7, 
            Predicate<? super DATA>   checker8, Func1<? super DATA, T> mapper8, 
            Predicate<? super DATA>   checker9, Func1<? super DATA, T> mapper9, 
            Func1<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : checker7.test(d) ? mapper7.apply(d)
                 : checker8.test(d) ? mapper8.apply(d)
                 : checker9.test(d) ? mapper9.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    
    //== Map to tuple. ==
    // ++ Generated with: GeneratorFunctorMapToTupleToObject ++

    public default <T1, T2> 
        Result<Tuple2<T1, T2>> mapTuple(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2) {
        return mapThen(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> 
        Result<Tuple3<T1, T2, T3>> mapTuple(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> 
        Result<Tuple4<T1, T2, T3, T4>> mapTuple(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3,
                Func1<? super DATA, ? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        Result<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3,
                Func1<? super DATA, ? extends T4> mapper4,
                Func1<? super DATA, ? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5,
                   (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    public default <T1, T2, T3, T4, T5, T6> 
        Result<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3,
                Func1<? super DATA, ? extends T4> mapper4,
                Func1<? super DATA, ? extends T5> mapper5,
                Func1<? super DATA, ? extends T6> mapper6) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    //-- Map and combine --
    
    public default <T1, T2, T> 
        Result<T> mapThen(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func2<T1, T2, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v  = function.apply(v1, v2);
            return v;
        });
    }
    public default <T1, T2, T3, T> 
        Result<T> mapThen(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v  = function.apply(v1, v2, v3);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T> 
        Result<T> mapThen(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3,
                Func1<? super DATA, ? extends T4> mapper4,
                Func4<T1, T2, T3, T4, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v  = function.apply(v1, v2, v3, v4);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T5, T> 
        Result<T> mapThen(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3,
                Func1<? super DATA, ? extends T4> mapper4,
                Func1<? super DATA, ? extends T5> mapper5,
                Func5<T1, T2, T3, T4, T5, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v  = function.apply(v1, v2, v3, v4, v5);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T5, T6, T> 
        Result<T> mapThen(
                Func1<? super DATA, ? extends T1> mapper1,
                Func1<? super DATA, ? extends T2> mapper2,
                Func1<? super DATA, ? extends T3> mapper3,
                Func1<? super DATA, ? extends T4> mapper4,
                Func1<? super DATA, ? extends T5> mapper5,
                Func1<? super DATA, ? extends T6> mapper6,
                Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v  = function.apply(v1, v2, v3, v4, v5, v6);
            return v;
        });
    }
    
    // -- Generated with: GeneratorFunctorMapToTupleToObject --
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Func1<? super DATA, ? extends VALUE> mapper) {
        return map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Func1<? super DATA, ? extends VALUE> mapper4) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Func1<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Func1<? super DATA, ? extends VALUE> mapper5) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Func1<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Func1<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Func1<? super DATA, ? extends VALUE> mapper6) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Func1<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Func1<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Func1<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Func1<? super DATA, ? extends VALUE> mapper7) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Func1<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Func1<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Func1<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Func1<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Func1<? super DATA, ? extends VALUE> mapper8) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Func1<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Func1<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Func1<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Func1<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Func1<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Func1<? super DATA, ? extends VALUE> mapper9) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Func1<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Func1<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Func1<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Func1<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Func1<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Func1<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Func1<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Func1<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Func1<? super DATA, ? extends VALUE> mapper9,
            KEY key10, Func1<? super DATA, ? extends VALUE> mapper10) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data),
                key10, mapper10.apply(data)));
    }
    
}
