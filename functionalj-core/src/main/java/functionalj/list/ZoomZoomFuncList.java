package functionalj.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import functionalj.function.IntObjBiFunction;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToBoolean;
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
public class ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST extends AbstractZoomFuncList<HOST, SUPER_HOST, ?>> 
                extends AbstractZoomFuncList<DATA, HOST, FUNCLIST> {
    
    /**
     * Constructs a {@link ZoomZoomFuncList}.
     * Only use it when you know what you are doing. Use {@link FuncList#zoomIn(AnyLens)} instead.
     * 
     * @param host  the host list.
     * @param lens  the lens.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList(FUNCLIST host, AnyLens<HOST, DATA> lens) {
        super((FUNCLIST)(AsFuncList)host, lens);
        
    }
    
    public <D> ZoomZoomFuncList<D, DATA, HOST, AbstractZoomFuncList<DATA, HOST, FUNCLIST>> zoomIn(AnyLens<DATA, D> lens) {
        return new ZoomZoomFuncList<>(this, lens);
    }
    
    //== Mandatory Functionality ==
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> filter(Predicate<DATA> filter) {
        val filtered = source.filter(host -> {
            val value   = lens.apply(host);
            val checked = filter.test(value);
            return checked;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(filtered, lens);
    }
    
    @Override
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> filter(AggregationToBoolean<? super DATA> aggregation) {
        val aggregator = aggregation.newAggregator();
        return filter(aggregator::apply);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> map(UnaryOperator<DATA> mapper) {
        val list
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
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> map(Aggregation<? super DATA, ? extends DATA> aggregation) {
        val aggregator = aggregation.newAggregator();
        return map(aggregator::apply);
    }
    
    @Override
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
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> flatMap(Aggregation<? super DATA, ? extends Collection<? extends DATA>> aggregation) {
        val aggregator = aggregation.newAggregator();
        return flatMap(aggregator::apply);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> peek(Consumer<? super DATA> action) {
        val list = source.peek(host -> {
            val data = lens.apply(host);
            action.accept(data);
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    // -- Limit/Skip --
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> limit(long maxSize) {
        val list = source.limit(maxSize);
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> skip(long n) {
        val list = source.skip(n);
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    // == Access list ==
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> subList(int fromIndexInclusive, int toIndexExclusive) {
        val list = source.subList(fromIndexInclusive, toIndexExclusive);
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    //== Additional Functionality ==
    
    //-- from FuncList --
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> with(int index, DATA value) {
        val list = source.with(index, sourceItem -> {
            val newItem = lens.changeTo(value).apply(sourceItem);
            return newItem;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> with(int index, Function<DATA, DATA> mapper) {
        val list = source.with(index, sourceItem -> {
            val newItem = lens.changeTo(mapper).apply(sourceItem);
            return newItem;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> with(int index, IntObjBiFunction<DATA, DATA> mapper) {
        val list = source.with(index, sourceItem -> {
            val newItem = lens.changeTo(oldValue -> {
                val newValue = mapper.apply(index, oldValue);
                return newValue;
            }).apply(sourceItem);
            return newItem;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> exclude(DATA element) {
        val list = source.filter(host -> !Objects.equals(lens.apply(host), element));
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    // -- Sorted --
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> sorted() {
        val list = source.sorted((hostA, hostB) -> {
            val valueA = lens.apply(hostA);
            val valueB = lens.apply(hostB);
            
            val comparableA = (Comparable)valueA;
            val comparableB = (Comparable)valueB;
            return ((Comparable)comparableA).compareTo(comparableB);
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> sorted(Comparator<? super DATA> comparator) {
        val list = source.sorted((hostA, hostB) -> {
            val valueA = lens.apply(hostA);
            val valueB = lens.apply(hostB);
            return comparator.compare(valueA, valueB);
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> first(int count) {
        val list = source.first(count);
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> last(int count) {
        val list = source.last(count);
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> tail() {
        val list = source.tail();
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    //== FuncListWithFillNull ==
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> fillNull(DATA replacement) {
        val list = source.map(host -> {
            val data = lens.apply(host);
            if (data != null) {
                return host;
            }
            val newData = replacement;
            val newHost = lens.changeTo(newData).apply(host);
            return newHost;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> fillNull(Supplier<DATA> replacement) {
        val list = source.map(host -> {
            val data = lens.apply(host);
            if (data != null) {
                return host;
            }
            val newData = replacement.get();
            val newHost = lens.changeTo(newData).apply(host);
            return newHost;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST> fillNull(Function<HOST, DATA> replacement) {
        val list = source.map(host -> {
            val data = lens.apply(host);
            if (data != null) {
                return host;
            }
            val newData = replacement.apply(host);
            val newHost = lens.changeTo(newData).apply(host);
            return newHost;
        });
        return (ZoomZoomFuncList<DATA, HOST, SUPER_HOST, FUNCLIST>)new ZoomZoomFuncList(list, lens);
    }
    
    // TODO - Add more.
    
    
}
