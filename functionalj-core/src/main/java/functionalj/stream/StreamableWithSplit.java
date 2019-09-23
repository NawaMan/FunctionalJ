package functionalj.stream;

import java.util.function.Predicate;

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

public interface StreamableWithSplit<DATA>
    extends StreamableWithMapTuple<DATA> {
    
    
    public StreamPlus<DATA> stream();
    
    
    //== split ==
    // Lazy
    
    public default Tuple2<FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate) {
        return stream()
                .split(predicate);
    }
    
    public default Tuple3<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2) {
        return stream()
                .split(predicate1, predicate2);
    }
    
    public default Tuple4<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3) {
        return stream()
                .split(predicate1, predicate2, predicate3);
    }
    
    public default Tuple5<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4) {
        return stream()
                .split(predicate1, predicate2, predicate3, predicate4);
    }
    
    public default Tuple6<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4,
            Predicate<? super DATA> predicate5) {
        return stream()
                .split(predicate1, predicate2, predicate3, predicate4, predicate5);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate,
            KEY key2) {
        return stream()
                .split(key1, predicate,
                       key2);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3) {
        return stream()
                .split(key1, predicate1,
                       key2, predicate2,
                       key3);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4) {
        return stream()
                .split(key1, predicate1,
                       key2, predicate2,
                       key3, predicate3,
                       key4);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5) {
        return stream()
                .split(key1, predicate1,
                       key2, predicate2,
                       key3, predicate3,
                       key4, predicate4,
                       key5);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5, Predicate<? super DATA> predicate5,
            KEY key6) {
        return stream()
                .split(key1, predicate1,
                       key2, predicate2,
                       key3, predicate3,
                       key4, predicate4,
                       key5, predicate5,
                       key6);
    }
}