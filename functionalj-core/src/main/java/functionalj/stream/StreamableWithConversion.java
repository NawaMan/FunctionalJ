package functionalj.stream;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import functionalj.function.Func1;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.map.FuncMap;
import lombok.val;

public interface StreamableWithConversion<DATA> extends AsStreamable<DATA> {
    
    public default String joinToString() {
        return stream().map(StrFuncs::toStr)
                .collect(Collectors.joining());
    }
    public default String joinToString(String delimiter) {
        return stream().map(StrFuncs::toStr)
                .collect(Collectors.joining(delimiter));
    }
    
    public default <T> T[] toArray(T[] a) {
        return StreamPlus.of(stream()).toJavaList().toArray(a);
    }
    
    public default List<DATA> toJavaList() {
        return stream().collect(Collectors.toList());
    }
    
    public default byte[] toByteArray(Func1<DATA, Byte> toByte) {
        val byteArray = new ByteArrayOutputStream();
        stream().forEach(d -> byteArray.write(toByte.apply(d)));
        return byteArray.toByteArray();
    }
    
    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
        return stream().mapToInt(toInt).toArray();
    }
//    
//    public default long[] toLongArray(ToLongFunction<DATA> toLong) {
//        return mapToLong(toLong).toArray();
//    }
//    
//    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
//        return mapToDouble(toDouble).toArray();
//    }
    
    public default FuncList<DATA> toList() {
        return toImmutableList();
    }
    
    public default FuncList<DATA> toFuncList() {
        return toImmutableList();
    }
    
    public default FuncList<DATA> toLazyList() {
//        return stream().toLazyList();
        return null;
    }
    
    public default String toListString() {
        return stream().toListString();
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.from(stream());
    }
    
    public default List<DATA> toMutableList() {
        return toArrayList();
    }
    
    public default ArrayList<DATA> toArrayList() {
        return new ArrayList<DATA>(toJavaList());
    }
    
    public default Set<DATA> toSet() {
        return new HashSet<DATA>(stream().collect(Collectors.toSet()));
    }
    
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
