package functionalj.stream;

import java.util.function.Predicate;

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface StreamPlusWithSplit<DATA>
        extends StreamPlusWithMapTuple<DATA> {
    
    //== split ==
    // Lazy
    
    public default Tuple2<FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate) {
        val temp 
            = mapTuple(
                    it -> predicate.test(it) ? 0 : 1,
                    it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        return Tuple.of(
                list1,
                list2
        );
    }
    
    public default Tuple3<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3
        );
    }
    
    public default Tuple4<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4
        );
    }
    
    public default Tuple5<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    :                       4,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5
        );
    }
    
    public default Tuple6<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4,
            Predicate<? super DATA> predicate5) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    : predicate5.test(it) ? 4
                    :                       5,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).map(it -> it._2());
        val list6 = temp.filter(it -> it._1() == 5).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5,
                list6
        );
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate,
            KEY key2) {
        val temp 
            = mapTuple(
                it -> predicate.test(it) ? 0 : 1,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3, 
                key4, list4);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    :                       4,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).map(it -> it._2()) : FuncList.<DATA>empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3, 
                key4, list4, 
                key5, list5);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5, Predicate<? super DATA> predicate5,
            KEY key6) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    : predicate5.test(it) ? 4
                    :                       5,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).map(it -> it._2()) : FuncList.<DATA>empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).map(it -> it._2()) : FuncList.<DATA>empty();
        val list6 = (key6 != null) ? temp.filter(it -> it._1() == 5).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3, 
                key4, list4, 
                key5, list5,
                key6, list6);
    }
}
