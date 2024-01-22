package functionalj.list;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import functionalj.lens.lenses.AnyLens;
import lombok.val;

/**
 * {@link FuncList} that is zoomed in from another {@link FuncList}.
 * 
 * @param <DATA>      the data type of this {@link FuncList}.
 * @param <HOST>      the data type of source {@link FuncList} that this {@link FuncList} zoomed in from.
 * @param <FUNCLIST>  the type of the source {@link FuncList}.
 */
public class ZoomFuncList<DATA, HOST, FUNCLIST extends AsFuncList<HOST>> implements AsFuncList<DATA> {
    
    final FUNCLIST            source;
    final AnyLens<HOST, DATA> lens;
    
    /**
     * Constructs a {@link ZoomFuncList}.
     * Only use it when you know what you are doing. Use {@link FuncList#zoomIn(AnyLens)} instead.
     * 
     * @param source  the source list.
     * @param lens    the lens.
     */
    public ZoomFuncList(FUNCLIST host, AnyLens<HOST, DATA> lens) {
        this.source = requireNonNull(host, "Host list must not be null.");
        this.lens = requireNonNull(lens, "Lens must not be null.");
    }
    
    @Override
    public FuncList<DATA> asFuncList() {
        return (FuncList<DATA>)source.asFuncList().map(lens);
    }
    
    //== Mandatory Functionality ==
    
    /**
     * Filter elements using the {@link Predicate}.
     * 
     * @param predicate  the predicate to filter the elements to include.
     * @return           the {@link ZoomFuncList} with only the elements selected by the predicate.
     */
    public ZoomFuncList<DATA, HOST, FUNCLIST> filter(Predicate<DATA> predicate) {
        @SuppressWarnings("unchecked")
        val filtered = (FUNCLIST)source.asFuncList().filter(lens, predicate);
        return new ZoomFuncList<>(filtered, lens);
    }
    
    /**
     * Replace the element using the mapper.
     * 
     * @param mapper  the mapper function.
     * @return        the {@link ZoomFuncList} with the new element.
     */
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
    
    /**
     * Zoom further in.
     * 
     * @param <D>   the deeper zoom data type.
     * @param lens  the lens to zoom.
     * @return      the deeper zoom {@link FuncList}.
     */
    public <D> ZoomZoomFuncList<D, DATA, HOST, ZoomFuncList<DATA, HOST, FUNCLIST>> zoomIn(AnyLens<DATA, D> lens) {
        return new ZoomZoomFuncList<>(this, lens);
    }
    
    /**
     * Zoom out to the source {@link FuncList}.
     * 
     * @return  the source (might be modified) {@link FuncList}.
     */
    public FUNCLIST zoomOut() {
        return source;
    }
    
    //== Additional Functionality ==
    
    //-- from FuncList --
    
    // -- FlatMap --
     /**
      * Map a value into a list and then flatten that list
      */
     public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> flatMap(Function<? super DATA, ? extends Collection<? extends DATA>> mapper) {
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
    
    
    
    
    // TODO - Add more.
    
    
    // FlatMap
    // FuncListWithFilter
    // FuncListWithFillNull
    // sortedBy?
//    
//    // -- Peek --
//    /**
//     * Consume each value using the action whenever a termination operation is called
//     */
//    public default FuncList<DATA> peek(Consumer<? super DATA> action) {
//        return deriveFrom(this, stream -> stream.peek(action));
//    }
//    
//    // -- Filter --
//    /**
//     * Select only the element that passes the predicate
//     */
//    public default FuncList<DATA> filter(Predicate<? super DATA> predicate) {
//        return deriveFrom(this, stream -> stream.filter(predicate));
//    }
//    
//    /**
//     * Select only the element that passes the predicate
//     */
//    public default FuncList<DATA> filter(AggregationToBoolean<? super DATA> aggregation) {
//        val mapper = aggregation.newAggregator();
//        return filter(mapper);
//    }
//    
//    /**
//     * Returns the new list from this list without the element.
//     */
//    public default FuncList<DATA> exclude(Object element) {
//        return filter(each -> !Objects.equals(each, element));
//    }
//    
//    /**
//     * Returns the new list from this list without the element at the index.
//     */
//    public default FuncList<DATA> excludeAt(int index) {
//        if (index < 0)
//            throw new IndexOutOfBoundsException("index: " + index);
//        val first = limit(index);
//        val tail = skip(index + 1);
//        return FuncList.concat(first, tail);
//    }
//    
//    /**
//     * Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`.
//     */
//    public default FuncList<DATA> excludeFrom(int fromIndexInclusive, int count) {
//        if (fromIndexInclusive < 0)
//            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
//        if (count <= 0)
//            throw new IndexOutOfBoundsException("count: " + count);
//        val first = limit(fromIndexInclusive);
//        val tail = skip(fromIndexInclusive + count);
//        return FuncList.concat(first, tail);
//    }
//    
//    /**
//     * Returns the new list from this list without the element starting at `fromIndexInclusive` to `toIndexExclusive`.
//     */
//    public default FuncList<DATA> excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
//        if (fromIndexInclusive < 0)
//            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
//        if (toIndexExclusive < 0)
//            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
//        if (fromIndexInclusive > toIndexExclusive)
//            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
//        if (fromIndexInclusive == toIndexExclusive)
//            return this;
//        val first = limit(fromIndexInclusive);
//        val tail = skip(toIndexExclusive);
//        return FuncList.concat(first, tail);
//    }
    
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
