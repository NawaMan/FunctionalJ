package functionalj.list;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import functionalj.function.IntObjBiFunction;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.lens.lenses.AnyLens;
import functionalj.stream.StreamPlusHelper;

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
    
}
