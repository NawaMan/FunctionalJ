package functionalj.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import functionalj.function.IntObjBiFunction;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.lens.lenses.AnyLens;
import lombok.val;

/**
 * {@link FuncList} that is zoomed in from another {@link FuncList}.
 * 
 * @param <DATA>      the data type of this {@link FuncList}.
 * @param <HOST>      the data type of source {@link FuncList} that this {@link FuncList} zoomed in from.
 * @param <FUNCLIST>  the type of the source {@link FuncList}.
 */
public class ZoomFuncList<DATA, HOST, FUNCLIST extends FuncList<HOST>> extends AbstractZoomFuncList<DATA, HOST, FUNCLIST> {
    
    /**
     * Constructs a {@link ZoomFuncList}.
     * Only use it when you know what you are doing. Use {@link FuncList#zoomIn(AnyLens)} instead.
     * 
     * @param source  the source list.
     * @param lens    the lens.
     */
    public ZoomFuncList(FUNCLIST source, AnyLens<HOST, DATA> lens) {
        super(source, lens);
    }
    
    public <D> ZoomZoomFuncList<D, DATA, HOST, ZoomFuncList<DATA, HOST, FUNCLIST>> zoomIn(AnyLens<DATA, D> lens) {
        return new ZoomZoomFuncList<>(this, lens);
    }
    
    //== Mandatory Functionality ==
    
    @Override
    public ZoomFuncList<DATA, HOST, FUNCLIST> filter(Predicate<DATA> predicate) {
        @SuppressWarnings("unchecked")
        val filtered = (FUNCLIST)source.asFuncList().filter(lens, predicate);
        return new ZoomFuncList<>(filtered, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, FUNCLIST> filter(AggregationToBoolean<? super DATA> aggregation) {
        val aggregator = aggregation.newAggregator();
        return filter(aggregator::apply);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> map(UnaryOperator<DATA> mapper) {
        val map
            = source
            .asFuncList()
            .map(host -> {
                val newHost 
                    = lens
                    .changeTo(data -> {
                        val newValue = mapper.apply(data);
                        return newValue;
                    })
                    .apply(host);
                return newHost;
            });
        val result = (ZoomFuncList<DATA, HOST, FuncList<HOST>>)new ZoomFuncList(map, lens);
        return result;
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> map(Aggregation<? super DATA, ? extends DATA> aggregation) {
        val aggregator = aggregation.newAggregator();
        return map(aggregator::apply);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends FuncList<HOST>> flatMap(Function<? super DATA, ? extends Collection<? extends DATA>> mapper) {
        val list = source.asFuncList().flatMap(host -> {
            val data    = lens.apply(host);
            val results = mapper.apply(data);
            return FuncList.from(results)
                    .map(each -> {
                        val newValue = lens.changeTo(each).apply(host);
                        return newValue;
                    });
        });
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends FuncList<HOST>> flatMap(Aggregation<? super DATA, ? extends Collection<? extends DATA>> aggregation) {
        val aggregator = aggregation.newAggregator();
        return flatMap(aggregator::apply);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends FuncList<HOST>> peek(Consumer<? super DATA> action) {
        val list = source.asFuncList().peek(host -> {
            val data = lens.apply(host);
            action.accept(data);
        });
        return new ZoomFuncList<>(list, lens);
    }
    
    // -- Limit/Skip --
    @Override
    public AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> limit(long maxSize) {
        val list = source.asFuncList().limit(maxSize);
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> skip(long n) {
        val list = source.asFuncList().skip(n);
        return new ZoomFuncList<>(list, lens);
    }
    
    // == Access list ==
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> subList(int fromIndexInclusive, int toIndexExclusive) {
        val list = source.asFuncList().subList(fromIndexInclusive, toIndexExclusive);
        return new ZoomFuncList<>(list, lens);
    }
     
     //== Additional Functionality ==
     
     //-- from FuncList --
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> with(int index, DATA value) {
        val list = source.asFuncList().with(index, sourceItem -> {
            val newItem = lens.changeTo(value).apply(sourceItem);
            return newItem;
        });
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> with(int index, Function<DATA, DATA> mapper) {
        val list = source.asFuncList().with(index, sourceItem -> {
            val newItem = lens.changeTo(mapper).apply(sourceItem);
            return newItem;
        });
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> with(int index, IntObjBiFunction<DATA, DATA> mapper) {
        val list = source.asFuncList().with(index, sourceItem -> {
            val newItem = lens.changeTo(oldValue -> {
                val newValue = mapper.apply(index, oldValue);
                return newValue;
            }).apply(sourceItem);
            return newItem;
        });
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> exclude(DATA element) {
        val list = source.asFuncList().filter(host -> !Objects.equals(lens.apply(host), element));
        return new ZoomFuncList<>(list, lens);
    }
    
    // -- Sorted --
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> sorted() {
        val list = source.asFuncList().sorted((hostA, hostB) -> {
            val valueA = lens.apply(hostA);
            val valueB = lens.apply(hostB);
            
            val comparableA = (Comparable)valueA;
            val comparableB = (Comparable)valueB;
            return ((Comparable)comparableA).compareTo(comparableB);
        });
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> sorted(Comparator<? super DATA> comparator) {
        val list = source.asFuncList().sorted((hostA, hostB) -> {
            val valueA = lens.apply(hostA);
            val valueB = lens.apply(hostB);
            return comparator.compare(valueA, valueB);
        });
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> first(int count) {
        val list = source.asFuncList().first(count);
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> last(int count) {
        val list = source.asFuncList().last(count);
        return new ZoomFuncList<>(list, lens);
    }
    
    @Override
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> tail() {
        val list = source.asFuncList().tail();
        return new ZoomFuncList<>(list, lens);
    }
    
    
    // TODO - Add more.
    
    
//    
//    /**
//     * Peek only the value that is an instance of the give class.
//     */
//    public default <T extends DATA> FuncList<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.peek(clzz, theConsumer));
//    }
//    
//    /**
//     * Peek only the value that is selected with selector.
//     */
//    public default FuncList<DATA> peekBy(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.peekBy(selector, theConsumer));
//    }
//    
//    // TODO - peekByInt, peekByLong, peekByDouble, peekByObj
//    // TODO - peekAsInt, peekAsLong, peekAsDouble, peekAsObj
//    /**
//     * Peek the mapped value using the mapper.
//     */
//    public default <T> FuncList<DATA> peekAs(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.peekAs(mapper, theConsumer));
//    }
//    
//    /**
//     * Peek only the mapped value using the mapper.
//     */
//    public default <T> FuncList<DATA> peekBy(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super DATA> theConsumer) {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.peekBy(mapper, selector, theConsumer));
//    }
//    
//    /**
//     * Peek only the mapped value using the mapper that is selected by the selector.
//     */
//    public default <T> FuncList<DATA> peekAs(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.peekAs(mapper, selector, theConsumer));
//    }
    
//    /**
//     * @return  the stream of each value and index.
//     */
//    public default FuncList<IntTuple2<DATA>> mapWithIndex() {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.mapWithIndex());
//    }
//    
//    /**
//     * Create a stream whose value is the combination between value of this stream and its index.
//     */
//    public default <TARGET> FuncList<TARGET> mapWithIndex(IntObjBiFunction<? super DATA, TARGET> combinator) {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.mapWithIndex(combinator));
//    }
    
    
    
//    public default <T> FuncList<T> mapMulti(BiConsumer<DATA, Consumer<? super T>> mapper) {
//        val funcList = funcListOf(this);
//        return deriveFrom(funcList, stream -> stream.mapMulti(mapper));
//    }
//    
//    public default IntFuncList mapMultiToInt(BiConsumer<DATA, IntConsumer> mapper) {
//        val funcList = funcListOf(this);
//        return IntFuncList.deriveFrom(funcList, stream -> stream.mapMultiToInt(mapper));
//    }
//    
//    public default LongFuncList mapMultiToLong(BiConsumer<DATA, LongConsumer> mapper) {
//        val funcList = funcListOf(this);
//        return LongFuncList.deriveFrom(funcList, stream -> stream.mapMultiToLong(mapper));
//    }
//    
//    public default DoubleFuncList mapMultiToDouble(BiConsumer<DATA, DoubleConsumer> mapper) {
//        val funcList = funcListOf(this);
//        return DoubleFuncList.deriveFrom(funcList, stream -> stream.mapMultiToDouble(mapper));
//    }
//    
//    public default <T> FuncList<T> mapMultiToObj(BiConsumer<DATA, Consumer<? super T>> mapper) {
//        return mapMulti(mapper);
//    }
    
    
    
    
    
    
    
    
    
    
}
