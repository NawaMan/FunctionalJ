package functionalj.types.list;

import static functionalj.FunctionalJ.withIndex;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theObject;
import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.functions.FuncUnit;
import functionalj.types.map.FunctionalMap;
import functionalj.types.map.ImmutableMap;
import functionalj.types.stream.Streamable;
import functionalj.types.tuple.IntTuple2;
import functionalj.types.tuple.Tuple2;
import functionalj.types.tuple.Tuple3;
import functionalj.types.tuple.Tuple4;
import functionalj.types.tuple.Tuple5;
import functionalj.types.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public abstract class FunctionalList<DATA> 
                    implements 
                        FunctionalListMapAddOn<DATA>,
                        FunctionalListPeekAddOn<DATA>,
                        FunctionalListFlatMapAddOn<DATA>,
                        FunctionalListFilterAddOn<DATA>,
                        ReadOnlyList<DATA>, 
                        Streamable<DATA> {
    
    public static <T> FunctionalList<T> empty() {
        return ImmutableList.empty();
    }
    public static <T> FunctionalList<T> of(Collection<T> data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> ofCollection(Collection<T> data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> ofList(List<T> data) {
        return ImmutableList.of(data);
    }
    @SafeVarargs
    public static <T> FunctionalList<T> of(T ... data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> of(Streamable<T> streamable) {
        return ImmutableList.of(streamable);
    }
    public static <T> FunctionalList<T> ofStream(Stream<T> stream) {
        return new ImmutableList<T>(stream.collect(Collectors.toList()));
    }
    public static <T> FunctionalList<T> of(ReadOnlyList<T> readOnlyList) {
        return ImmutableList.of(readOnlyList);
    }
    public static <T> FunctionalList<T> of(FunctionalList<T> functionalList) {
        return ImmutableList.of(functionalList);
    }
    
    public FunctionalList<DATA> asFunctionalList() {
        return this;
    }
    
    public FunctionalList<DATA> filter(DATA o) {
        return __stream(stream -> stream.filter(each -> Objects.equals(o, each)));
    }
    
    @Override
    public abstract FunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);
    
    @Override
    public <TARGET> FunctionalList<TARGET> __map(Function<? super DATA, ? extends TARGET> mapper) {
        return map(mapper);
    }
    
    @Override
    public <TARGET> FunctionalList<TARGET> __flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return flatMap(mapper);
    }
    
    @Override
    public <TARGET> FunctionalList<TARGET> stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return new FunctionalListStream<>(this, action);
    }
    @Override
    public <TARGET> FunctionalList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return new FunctionalListStream<>(this, stream -> stream.map(mapper));
    }
    
    @Override
    public FunctionalList<DATA> peek(FuncUnit<? super DATA> consumer) {
        return new FunctionalListStream<>(this, stream -> stream.peek(consumer));
    }
    
    @Override

    public <TARGET> FunctionalList<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return new FunctionalListStream<>(this, stream -> stream.flatMap(mapper));
    }
    
    public FunctionalList<DATA> flatMapOnly(Predicate<? super DATA> checker, Function<? super DATA, FunctionalList<DATA>> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : Stream.of(d));
    }
    
    @Override
    public abstract Stream<DATA> stream();
    
    public <TARGET> FunctionalList<TARGET> __stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return new FunctionalListStream<DATA, TARGET>(this, action);
    }
    
    @Override
    public List<DATA> toList() {
        return this;
    }
    
    @Override
    public FunctionalList<DATA> toFunctionalList() {
        return this;
    }
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(this);
    }    
    
    @Override
    public Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    @Override
    public Spliterator<DATA> spliterator() {
        return stream().spliterator();
    }

    @Override
    public int size() {
        return (int)stream().count();
    }

    @Override
    public abstract DATA get(int index);

    public <TARGET> FunctionalList<TARGET> map(BiFunction<? super Integer, ? super DATA, TARGET> mapper) {
        val index = new AtomicInteger();
        return __map(each -> mapper.apply(index.getAndIncrement(), each));
    }
    
    public FunctionalList<Integer> indexesOf(Predicate<? super DATA> check) {
        // TODO - This might be wrong the second time.
        return this
                .map((index, data)-> 
                check.test(data) ? index : -1)
                .filter(theInteger.thatNotEqualsTo(-1));
    }

    @Override
    public FunctionalList<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        // TODO Auto-generated method stub
        return FunctionalListMapAddOn.super.mapOnly(checker, mapper);
    }

    @Override
    public <T> FunctionalList<T> mapIf(Predicate<? super DATA> checker, Function<? super DATA, T> mapper,
            Function<? super DATA, T> elseMapper) {
        // TODO Auto-generated method stub
        return FunctionalListMapAddOn.super.mapIf(checker, mapper, elseMapper);
    }

    @Override
    public FunctionalList<DATA> filter(Predicate<? super DATA> theCondition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int indexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ListIterator<DATA> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<DATA> listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReadOnlyList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public <T1, T2> 
        FunctionalList<Tuple2<T1, T2>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return mapCombine(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    @Override
    public <T1, T2, T> FunctionalList<T> mapCombine(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2, BiFunction<T1, T2, T> function) {
        // TODO Auto-generated method stub
        return FunctionalListMapAddOn.super.mapCombine(mapper1, mapper2, function);
    }
    public <T1, T2, T3> 
        FunctionalList<Tuple3<T1, T2, T3>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return mapCombine(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public <T1, T2, T3, T> 
        FunctionalList<T> mapCombine(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v  = function.apply(v1, v2, v3);
            return v;
        });
    }
    public <T1, T2, T3, T4> 
        FunctionalList<Tuple4<T1, T2, T3, T4>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return mapCombine(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public <T1, T2, T3, T4, T> 
        FunctionalList<T> mapCombine(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
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
    public <T1, T2, T3, T4, T5> 
        FunctionalList<Tuple5<T1, T2, T3, T4, T5>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return mapCombine(mapper1, mapper2, mapper3, mapper4, mapper5,
                   (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    
    public <T1, T2, T3, T4, T5, T> 
        FunctionalList<T> mapCombine(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
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
    public <T1, T2, T3, T4, T5, T6> 
        FunctionalList<Tuple6<T1, T2, T3, T4, T5, T6>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return mapCombine(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    public <T1, T2, T3, T4, T5, T6, T> 
        FunctionalList<T> mapCombine(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6,
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public DATA first() {
        val valueRef = new AtomicReference<DATA>();
        if (!Helper.hasAt(stream(), 0, valueRef)) {
            throw new IndexOutOfBoundsException("1");
        }
        
        return valueRef.get();
    }
    
    public FunctionalList<DATA> rest() {
        return __stream(stream -> stream.skip(1));
    }
    
    public FunctionalList<IntTuple2<DATA>> select(Predicate<? super DATA> check) {
        return this
                .map   (withIndex((data, index)-> check.test(data) ? new IntTuple2<DATA>(index, data) : null))
                .filter(theObject.thatIsNotNull())
                .toFunctionalList();
    }
    
    //== Modified methods ==
    
    @SuppressWarnings("unchecked")
    public FunctionalList<DATA> append( DATA ... values) {
        return __stream(stream -> Stream.concat(stream, Stream.of(values)));
    }
    
    public FunctionalList<DATA> appendAll(Collection<? extends DATA> collection) {
        if ((collection == null) || collection.isEmpty())
            return this;
        
        return __stream(stream -> Stream.concat(stream, collection.stream()));
    }
    
    public FunctionalList<DATA> appendAll(Streamable<? extends DATA> streamable) {
        if (streamable == null)
            return this;
        
        return __stream(stream -> Stream.concat(stream, streamable.stream()));
    }
    
    public FunctionalList<DATA> appendAll(Supplier<Stream<? extends DATA>> supplier) {
        if (supplier == null)
            return this;
        
        return __stream(stream -> Stream.concat(stream, supplier.get()));
    }
    
    public FunctionalList<DATA> with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        val i = new AtomicInteger();
        return __stream(stream -> stream.map(each -> (i.getAndIncrement() == index) ? value : each));
    }
    
    @SuppressWarnings("unchecked")
    public FunctionalList<DATA> insertAt(int index, DATA ... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        return FunctionalListStream.of(streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    Stream.of(elements),
                    iStream.get().skip(index + 1)));
        }));
    }
    
    public FunctionalList<DATA> insertAllAt(int index, Collection<? extends DATA> collection) {
        if ((collection == null)
          || collection.isEmpty())
            return this;
        
        return FunctionalListStream.of(streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    collection.stream(),
                    iStream.get().skip(index + 1)));
        }));
    }
    
    public FunctionalList<DATA> insertAllAt(int index, Streamable<? extends DATA> streamable) {
        if (streamable == null)
            return this;
        
        return FunctionalListStream.of(streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    streamable.stream(),
                    iStream.get().skip(index + 1)));
        }));
    }
    
    public FunctionalList<DATA> excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        return FunctionalListStream.of(streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    iStream.get().limit(index), 
                    iStream.get().skip(index + 2));
        }));
    }
    
    public FunctionalList<DATA> excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        return FunctionalListStream.of(streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(fromIndexInclusive + count));
        }));
    }
    
    public FunctionalList<DATA> excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive
                                            + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
        
        return FunctionalListStream.of(streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(toIndexExclusive + 1));
        }));
    }
    
    public <KEY> FunctionalMap<KEY, FunctionalList<DATA>> groupingBy(Function<? super DATA, ? extends KEY> classifier) {
        val theMap = new HashMap<KEY, FunctionalList<DATA>>();
        stream()
            .collect(Collectors.groupingBy(classifier))
            .forEach((key,list)->theMap.put(key, ImmutableList.of(list)));
        return ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public <KEY> FunctionalMap<KEY, DATA> toMap(Function<? super DATA, ? extends KEY> keyMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, data -> data));
        return (FunctionalMap<KEY, DATA>) ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public <KEY, VALUE> FunctionalMap<KEY, VALUE> toMap(
                Function<? super DATA, ?  extends KEY>   keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper));
        return (FunctionalMap<KEY, VALUE>) ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public <KEY, VALUE> FunctionalMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>   keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper,
                BinaryOperator<VALUE> mergeFunction) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
        return (FunctionalMap<KEY, VALUE>) ImmutableMap.of(theMap);
    }
}
