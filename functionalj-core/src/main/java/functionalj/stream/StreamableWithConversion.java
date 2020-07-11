package functionalj.stream;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import functionalj.map.FuncMap;

public interface StreamableWithConversion<DATA> {
    
    public StreamPlus<DATA> stream();
    
    
    //-- toMap --
    
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, KEY> keyMapper) {
        return stream()
                .toMap(keyMapper);
    }
    
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, KEY>  keyMapper,
            Function<? super DATA, VALUE> valueMapper) {
        return stream()
                .toMap(keyMapper, valueMapper);
    }
    
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, KEY>   keyMapper,
            Function<? super DATA, VALUE> valueMapper,
            BinaryOperator<VALUE>                   mergeFunction) {
        return stream()
                .toMap(keyMapper, valueMapper, mergeFunction);
    }
    
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, KEY> keyMapper,
            BinaryOperator<DATA>        mergeFunction) {
        return stream()
                .toMap(keyMapper, mergeFunction);
    }
    
}
