package functionalj.list;

import static java.util.Objects.requireNonNull;

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
    public ZoomFuncList<DATA, HOST, ? extends AsFuncList<HOST>> map(UnaryOperator<DATA> mapper) {
        val map = source.asFuncList().map(lens.changeTo(d -> mapper.apply(d)));
        return new ZoomFuncList<>(map, lens);
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
    
    // TODO - Add more.
    
}
