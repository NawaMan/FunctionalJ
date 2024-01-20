package functionalj.list;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import functionalj.lens.lenses.AnyLens;
import lombok.val;

/**
 * {@link FuncList} that is zoomed in from another {@link ZoomFuncList}.
 * 
 * @param <DATA>        the data type of this {@link FuncList}.
 * @param <HOST>        the data type of the source {@link FuncList}.
 * @param <SUPER_HOST>  
 * @param <FUNCLIST>
 */
public class ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST extends ZoomFuncList<HOST, SUPER_HOST, ?>> 
                extends ZoomFuncList<DATA, HOST, FUNCLIST> {
    
    /**
     * Constructs a {@link ZoomZoomFuncList}.
     * Only use it when you know what you are doing. Use {@link FuncList#zoomIn(AnyLens)} instead.
     * 
     * @param source  the source list.
     * @param lens    the lens.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList(FUNCLIST host, AnyLens<HOST, DATA> lens) {
        super((FUNCLIST)(AsFuncList)host, lens);
        
    }
    
    //== Mandatory Functionality ==
    
    /**
     * Filter elements using the {@link Predicate}.
     * 
     * @param predicate  the predicate to filter the elements to include.
     * @return           the {@link ZoomFuncList} with only the elements selected by the predicate.
     */
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> filter(Predicate<DATA> filter) {
        @SuppressWarnings("unchecked")
        val filtered = (FUNCLIST)source.asFuncList().filter(lens, filter);
        return new ZoomZoomFuncList<>(filtered, lens);
    }
    
    /**
     * Replace the element using the mapper.
     * 
     * @param mapper  the mapper function.
     * @return        the {@link ZoomFuncList} with the new element.
     */
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> map(UnaryOperator<DATA> mapper) {
        val map = source.map(host -> lens.changeTo(d -> mapper.apply(d)).apply(host));
        @SuppressWarnings({ "unchecked", "rawtypes" })
        val result = (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(map, lens);
        return result;
    }
    
    /**
     * Zoom out to the source {@link FuncList}.
     * 
     * @return  the source (might be modified) {@link FuncList}.
     */
    public FUNCLIST zoomOut() {
        val zoomOut = super.zoomOut();
        return zoomOut;
    }
    
    //== Additional Functionality ==
    
    // TODO - Add more.
    
    
}
