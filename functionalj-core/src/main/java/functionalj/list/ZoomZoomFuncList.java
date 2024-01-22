package functionalj.list;

import java.util.Collection;
import java.util.function.Function;
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> filter(Predicate<DATA> filter) {
        val filtered = source.filter(host -> {
            val value   = lens.apply(host);
            val checked = filter.test(value);
            return checked;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(filtered, lens);
    }
    
    /**
     * Replace the element using the mapper.
     * 
     * @param mapper  the mapper function.
     * @return        the {@link ZoomFuncList} with the new element.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> map(UnaryOperator<DATA> mapper) {
        val map
            = source
            .map(host -> {
                val newHost
                    = lens
                    .changeTo(each -> {
                        val newValue = mapper.apply(each);
                        return newValue;
                    })
                    .apply(host);
                return newHost;
            });
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
    
    //-- from FuncList --
    
    // -- FlatMap --
    /**
     * Map a value into a list and then flatten that list
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> flatMap(Function<? super DATA, ? extends Collection<? extends DATA>> mapper) {
        val list = source.flatMap(host -> {
            val data    = lens.apply(host);
            val results = mapper.apply(data);
            return FuncList.from(results)
                    .map(each -> {
                        val newValue = lens.changeTo(each).apply(host);
                        return newValue;
                    });
        });
        val result = (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
        return result;
    }
    
    // TODO - Add more.
    
    
}
