package functionalj.stream.intstream;

import java.util.function.IntPredicate;

import functionalj.map.FuncMap;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface IntStreamableWithSplit extends IntStreamableWithMapTuple {
        
    public IntStreamPlus intStream();
    
    // The most important thing here is to only evaluate the value once.
    // Everything else that contradict that must give. That because we can use regular filter if evaluating once is not important.
    
    //== split ==
    
    // TODO - Try to make it lazy 
    // It is not easy as it seems as there has to be buffer for one branch when go through with another branch.
    // We may need a dynamic collection of all branch as we goes along.
    
    public default Tuple2<IntStreamable, IntStreamable> split(
            IntPredicate predicate) {
        val temp 
            = mapTuple(
                    it -> predicate.test(it) ? 0 : 1,
                    it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToInt(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToInt(it -> it._2());
        return Tuple.of(
                list1,
                list2
            );
    }
    
    public default Tuple3<IntStreamable, IntStreamable, IntStreamable> split(
            IntPredicate predicate1,
            IntPredicate predicate2) {
    val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToInt(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToInt(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToInt(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3
            );
    }
    
    public default Tuple4<IntStreamable, IntStreamable, IntStreamable, IntStreamable> split(
            IntPredicate predicate1,
            IntPredicate predicate2,
            IntPredicate predicate3) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToInt(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToInt(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToInt(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).mapToInt(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4
        );
    }
    
    public default Tuple5<IntStreamable, IntStreamable, IntStreamable, IntStreamable, IntStreamable> split(
            IntPredicate predicate1,
            IntPredicate predicate2,
            IntPredicate predicate3,
            IntPredicate predicate4) {
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
        val list1 = temp.filter(it -> it._1() == 0).mapToInt(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToInt(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToInt(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).mapToInt(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).mapToInt(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5
        );
    }
    
    public default Tuple6<IntStreamable, IntStreamable, IntStreamable, IntStreamable, IntStreamable, IntStreamable> split(
            IntPredicate predicate1,
            IntPredicate predicate2,
            IntPredicate predicate3,
            IntPredicate predicate4,
            IntPredicate predicate5) {
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
        val list1 = temp.filter(it -> it._1() == 0).mapToInt(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToInt(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToInt(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).mapToInt(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).mapToInt(it -> it._2());
        val list6 = temp.filter(it -> it._1() == 5).mapToInt(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5,
                list6
        );
    }
    
    public default <KEY> FuncMap<KEY, IntStreamable> split(
            KEY key1, IntPredicate predicate,
            KEY key2) {
        val temp 
            = mapTuple(
                it -> predicate.test(it) ? 0 : 1,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToInt(it -> it._2()) : IntStreamable.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToInt(it -> it._2()) : IntStreamable.empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2);
    }
    
    public default <KEY> FuncMap<KEY, IntStreamable> split(
            KEY key1, IntPredicate predicate1,
            KEY key2, IntPredicate predicate2,
            KEY key3) {
        val temp 
            = mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToInt(it -> it._2()) : IntStreamable.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToInt(it -> it._2()) : IntStreamable.empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToInt(it -> it._2()) : IntStreamable.empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3);
    }
    
    public default <KEY> FuncMap<KEY, IntStreamable> split(
            KEY key1, IntPredicate predicate1,
            KEY key2, IntPredicate predicate2,
            KEY key3, IntPredicate predicate3,
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
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToInt(it -> it._2()) : IntStreamable.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToInt(it -> it._2()) : IntStreamable.empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToInt(it -> it._2()) : IntStreamable.empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToInt(it -> it._2()) : IntStreamable.empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3, 
                key4, list4);
    }
    
    public default <KEY> FuncMap<KEY, IntStreamable> split(
        KEY key1, IntPredicate predicate1,
        KEY key2, IntPredicate predicate2,
        KEY key3, IntPredicate predicate3,
        KEY key4, IntPredicate predicate4,
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
    val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).mapToInt(it -> it._2()) : IntStreamable.empty();
    return FuncMap.of(
            key1, list1, 
            key2, list2, 
            key3, list3, 
            key4, list4, 
            key5, list5);
    }
    
    public default <KEY> FuncMap<KEY, IntStreamable> split(
        KEY key1, IntPredicate predicate1,
        KEY key2, IntPredicate predicate2,
        KEY key3, IntPredicate predicate3,
        KEY key4, IntPredicate predicate4,
        KEY key5, IntPredicate predicate5,
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
    val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).mapToInt(it -> it._2()) : IntStreamable.empty();
    val list6 = (key6 != null) ? temp.filter(it -> it._1() == 5).mapToInt(it -> it._2()) : IntStreamable.empty();
    return FuncMap.of(
            key1, list1, 
            key2, list2, 
            key3, list3, 
            key4, list4, 
            key5, list5,
            key6, list6);
    }
}
