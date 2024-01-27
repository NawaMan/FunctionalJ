package functionalj.list;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import functionalj.function.IntObjBiFunction;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.lens.lenses.AnyLens;
import functionalj.result.Result;
import functionalj.stream.StreamPlusHelper;
import nullablej.nullable.Nullable;

/**
 * {@link FuncList} that is zoomed in from another {@link FuncList}.
 * 
 * @param <DATA>      the data type of this {@link FuncList}.
 * @param <HOST>      the data type of source {@link FuncList} that this {@link FuncList} zoomed in from.
 * @param <FUNCLIST>  the type of the source {@link FuncList}.
 */
abstract class AbstractZoomFuncList<DATA, HOST, FUNCLIST extends AsFuncList<HOST>> implements AsFuncList<DATA> {
    
    final FUNCLIST            source;
    final AnyLens<HOST, DATA> lens;
    
    /**
     * Constructs a {@link ZoomFuncList}.
     * Only use it when you know what you are doing. Use {@link FuncList#zoomIn(AnyLens)} instead.
     * 
     * @param source  the source list.
     * @param lens    the lens.
     */
    public AbstractZoomFuncList(FUNCLIST host, AnyLens<HOST, DATA> lens) {
        this.source = requireNonNull(host, "Host list must not be null.");
        this.lens = requireNonNull(lens, "Lens must not be null.");
    }
    
    @Override
    public FuncList<DATA> asFuncList() {
        return (FuncList<DATA>)source.asFuncList().map(lens);
    }
    
    /**
     * Zoom out to the source {@link FuncList}.
     * 
     * @return  the source (might be modified) {@link FuncList}.
     */
    public FUNCLIST zoomOut() {
        return source;
    }
    
    //== Mandatory Functionality ==
    
    /**
     * Filter elements using the {@link Predicate}.
     * 
     * @param predicate  the predicate to filter the elements to include.
     * @return           the {@link ZoomFuncList} with only the elements selected by the predicate.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, FUNCLIST> filter(Predicate<DATA> predicate);
    
    /**
     * Select only the element that passes the predicate
     */
    public abstract AbstractZoomFuncList<DATA, HOST, FUNCLIST> filter(AggregationToBoolean<? super DATA> aggregation);
    
    /**
     * Replace the element using the mapper.
     * 
     * @param mapper  the mapper function.
     * @return        the {@link ZoomFuncList} with the new element.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> map(UnaryOperator<DATA> mapper);
    
    /**
     * Map each value into other value using the function.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> map(Aggregation<? super DATA, ? extends DATA> aggregation);
    
    /**
     * Map a value into a list and then flatten that list
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> flatMap(Function<? super DATA, ? extends Collection<? extends DATA>> mapper);
    
    /**
     * Map a value into a list and then flatten that list
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> flatMap(Aggregation<? super DATA, ? extends Collection<? extends DATA>> aggregation);
    
    /**
     * Consume each value using the action whenever a termination operation is called
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> peek(Consumer<? super DATA> action);
    
    // -- Limit/Skip --
    /**
     * Limit the size
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> limit(long maxSize);
    
    /**
     * Trim off the first n values
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> skip(long n);
    
    // == Access list ==
    
    /**
     * Check if this list is empty.
     * 
     * @return  <code>true</code> if this list is empty.
     */
    public boolean isEmpty() {
        return !StreamPlusHelper.hasAt(stream(), 0);
    }
    
    /**
     * Check if the list contains the given data.
     * 
     * @param data  the data.
     * @return  <code>true</code> if the list contains the data.
     */
    public boolean contains(DATA data) {
        return asFuncList().contains(data);
    }
    
    /**
     * Check if the list contains all of the given data.
     * 
     * @param data  the data.
     * @return  <code>true</code> if the list contains the data.
     */
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(each -> stream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    /**
     * Get the data at the index.
     * 
     * @param index  the index.
     * @return       the data at the index.
     */
    public DATA get(int index) {
        return asFuncList().get(index);
    }
    
    /**
     * Returns the first index that the given data is found or -1 if the data is not found.
     * 
     * @param data  the data.
     * @return      the index that the data is first found or -1.
     */
    public int indexOf(Object data) {
        return asFuncList().indexOf(data);
    }
    
    /**
     * Returns the last index that the given data is found or -1 if the data is not found.
     * 
     * @param data  the data.
     * @return      the index that the data is last found or -1.
     */
    public int lastIndexOf(Object o) {
        return asFuncList().lastIndexOf(o);
    }
    
    /**
     * Returns the list iterator representing this list.
     * 
     * @return  the list iterator.
     */
    public ListIterator<DATA> listIterator() {
        return asFuncList().listIterator();
    }
    
    /**
     * Returns the list iterator representing this list at the index.
     * 
     * @return  the list iterator.
     */
    public ListIterator<DATA> listIterator(int index) {
        return asFuncList().listIterator(index);
    }
    
    /**
     * Provide the part of the list from and to.
     * 
     * @param fromIndexInclusive  the start index inclusively.
     * @param toIndexExclusive    the end index exclusively.
     * @return
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> subList(int fromIndexInclusive, int toIndexExclusive);
    
    //== Additional Functionality ==
    
    //-- from FuncList --
    
    /**
     * Returns a new functional list with the value replacing at the index.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> with(int index, DATA value);
    
    /**
     * Returns a new functional list with the new value (calculated from the mapper) replacing at the index.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> with(int index, Function<DATA, DATA> mapper);
    
    /**
     * Returns a new functional list with the new value (calculated from the mapper) replacing at the index.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> with(int index, IntObjBiFunction<DATA, DATA> mapper);
    
    /**
     * Returns the new list from this list without the element.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> exclude(DATA element);
    
//    // -- Distinct --
//    // This cannot be done in the stable way ... let's think about it some more.
//    /**
//     * Remove duplicates
//     */
//    public default FuncList<DATA> distinct() {
//        return deriveFrom(this, stream -> stream.distinct());
//    }
    
    // -- Sorted --
    /**
     * Sort the values in this list
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> sorted();
    
    /**
     * Sort the values in the list using the given comparator
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> sorted(Comparator<? super DATA> comparator);
    
    // == Nullable, Optional and Result
    public Nullable<AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>>> __nullable() {
        return Nullable.of(this);
    }
    
    public Optional<AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>>> __optional() {
        return Optional.of(this);
    }
    
    public Result<AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>>> __result() {
        return Result.valueOf(this);
    }
    
    /**
     * Returns the first element.
     */
    public Optional<DATA> first() {
        return stream().limit(1).findFirst();
    }
    
    /**
     * Returns the first elements
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> first(int count);
    
    /**
     * Returns the last element.
     */
    public Optional<DATA> last() {
        return last(1).findFirst();
    }
    
    /**
     * Returns the last elements
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> last(int count);
    
    /**
     * Returns the element at the index.
     */
    public Optional<DATA> at(int index) {
        return skip(index).limit(1).findFirst();
    }
    
    /**
     * Returns the second to the last elements.
     */
    public abstract AbstractZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> tail();
    
}
