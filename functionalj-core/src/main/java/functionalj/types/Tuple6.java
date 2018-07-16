package functionalj.types;

import static functionalj.FunctionalJ.it;
import static functionalj.functions.Absent.__;
import static functionalj.functions.Keep.keep;

import java.lang.reflect.Array;
import java.util.function.Function;

import functionalj.functions.Absent;
import functionalj.functions.Func6;
import functionalj.functions.Keep;
import functionalj.pipeable.Pipeable;
import lombok.val;

public interface Tuple6<T1, T2, T3, T4, T5, T6> extends Pipeable<Tuple6<T1, T2, T3, T4, T5, T6>> {

    public T1 _1();
    public T2 _2();
    public T3 _3();
    public T4 _4();
    public T5 _5();
    public T6 _6();
    
    @Override
    public default Tuple6<T1, T2, T3, T4, T5, T6> __data() {
        return this;
    }
    
    public default ImmutableTuple6<T1, T2, T3, T4, T5, T6> toImmutableTuple() {
        return ImmutableTuple.of(this);
    }
    
    public default Object[] toArray() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return new Object[] { _1, _2, _3, _4, _5, _6 };
    }
    
    public default <T> T[] toArray(Class<T> type) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        val array = Array.newInstance(type, 6);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        Array.set(array, 2, _3);
        Array.set(array, 3, _4);
        Array.set(array, 4, _5);
        Array.set(array, 5, _6);
        return (T[])array;
    }
    
    public default FunctionalList<Object> toList() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return FunctionalList.of(_1, _2, _3, _4, _5, _6);
    }
    
    public default <K> FunctionalMap<K, Object> toMap(K k1, K k2, K k3, K k4, K k5, K k6) {
        val e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object)_1()) : null;
        val e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object)_2()) : null;
        val e3 = (k3 != null) ? ImmutableTuple.of(k3, (Object)_3()) : null;
        val e4 = (k4 != null) ? ImmutableTuple.of(k4, (Object)_4()) : null;
        val e5 = (k5 != null) ? ImmutableTuple.of(k5, (Object)_5()) : null;
        val e6 = (k6 != null) ? ImmutableTuple.of(k6, (Object)_6()) : null;
        return ImmutableMap.ofEntries(e1, e2, e3, e4, e5);
    }
    
    
    //== Map ==

    public default <NT1> Tuple6<NT1, T2, T3, T4, T5, T6> map1(Function<? super T1, NT1> mapper) {
        return mapEach(mapper, it(), it(), it(), it(), it());
    }
    
    public default <NT2> Tuple6<T1, NT2, T3, T4, T5, T6> map2(Function<? super T2, NT2> mapper) {
        return mapEach(it(), mapper, it(), it(), it(), it());
    }

    public default <NT3> Tuple6<T1, T2, NT3, T4, T5, T6> map3(Function<? super T3, NT3> mapper) {
        return mapEach(it(), it(), mapper, it(), it(), it());
    }

    public default <NT4> Tuple6<T1, T2, T3, NT4, T5, T6> map4(Function<? super T4, NT4> mapper) {
        return mapEach(it(), it(), it(), mapper, it(), it());
    }
    
    public default <NT5> Tuple6<T1, T2, T3, T4, NT5, T6> map5(Function<? super T5, NT5> mapper) {
        return mapEach(it(), it(), it(), it(), mapper, it());
    }
    
    public default <NT6> Tuple6<T1, T2, T3, T4, T5, NT6> map6(Function<? super T6, NT6> mapper) {
        return mapEach(it(), it(), it(), it(), it(), mapper);
    }
    
    public default <NT1, NT2, NT3, NT4, NT5, NT6> Tuple6<NT1, NT2, NT3, NT4, NT5, NT6> mapEach(
            Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Tuple6<NT1, NT2, NT3, NT4, NT5, NT6>> mapper) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return mapper.apply(_1, _2, _3, _4, _5, _6);
    }
    public default <NT1, NT2, NT3, NT4, NT5, NT6> Tuple6<NT1, NT2, NT3, NT4, NT5, NT6> mapEach(
            Function<? super T1, NT1> mapper1, 
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        val n1 = mapper1.apply(_1);
        val n2 = mapper2.apply(_2);
        val n3 = mapper3.apply(_3);
        val n4 = mapper4.apply(_4);
        val n5 = mapper5.apply(_5);
        val n6 = mapper6.apply(_6);
        return Tuple.of(n1, n2, n3, n4, n5, n6);
    }

    // TODO - Implement these.
    
//    
//    public default <NT2, NT3> Tuple3<T1, NT2, NT3> mapEach(
//            Absent                    absent1, 
//            Function<? super T2, NT2> mapper2, 
//            Function<? super T3, NT3> mapper3) {
//        val _1 = _1();
//        val _2 = _2();
//        val _3 = _3();
//        val n1 = _1;
//        val n2 = mapper2.apply(_2);
//        val n3 = mapper3.apply(_3);
//        return Tuple.of(n1, n2, n3);
//    }
//    
//    public default <NT1, NT3> Tuple3<NT1, T2, NT3> mapEach(
//            Function<? super T1, NT1> mapper1, 
//            Absent                    absent2, 
//            Function<? super T3, NT3> mapper3) {
//        val _1 = _1();
//        val _2 = _2();
//        val _3 = _3();
//        val n1 = mapper1.apply(_1);
//        val n2 = _2;
//        val n3 = mapper3.apply(_3);
//        return Tuple.of(n1, n2, n3);
//    }
//    
//    public default <NT1, NT2> Tuple3<NT1, NT2, T3> mapEach(
//            Function<? super T1, NT1> mapper1, 
//            Function<? super T2, NT2> mapper2,
//            Absent                    absent3) {
//        val _1 = _1();
//        val _2 = _2();
//        val _3 = _3();
//        val n1 = mapper1.apply(_1);
//        val n2 = mapper2.apply(_2);
//        val n3 = _3;
//        return Tuple.of(n1, n2, n3);
//    }
//    
//    public default <NT3> Tuple3<T1, T2, NT3> mapEach(
//            Absent                    absent1, 
//            Absent                    absent2, 
//            Function<? super T3, NT3> mapper3) {
//        val _1 = _1();
//        val _2 = _2();
//        val _3 = _3();
//        val n1 = _1;
//        val n2 = _2;
//        val n3 = mapper3.apply(_3);
//        return Tuple.of(n1, n2, n3);
//    }
//    
//    public default <NT2> Tuple3<T1, NT2, T3> mapEach(
//            Absent                    absent1, 
//            Function<? super T2, NT2> mapper2, 
//            Absent                    absent3) {
//        val _1 = _1();
//        val _2 = _2();
//        val _3 = _3();
//        val n1 = _1;
//        val n2 = mapper2.apply(_2);
//        val n3 = _3;
//        return Tuple.of(n1, n2, n3);
//    }
//    
//    public default <NT1> Tuple3<NT1, T2, T3> mapEach(
//            Function<? super T1, NT1> mapper1, 
//            Absent                    absent2,
//            Absent                    absent3) {
//        val _1 = _1();
//        val _2 = _2();
//        val _3 = _3();
//        val n1 = mapper1.apply(_1);
//        val n2 = _2;
//        val n3 = _3;
//        return Tuple.of(n1, n2, n3);
//    }
    
    //== drop ==
    
    public default Tuple5<T2, T3, T4, T5, T6> drop1() {
        return drop(__, keep, keep, keep, keep, keep);
    }
    public default Tuple5<T1, T3, T4, T5, T6> drop2() {
        return drop(keep, __, keep, keep, keep, keep);
    }
    public default Tuple5<T1, T2, T4, T5, T6> drop3() {
        return drop(keep, keep, __, keep, keep, keep);
    }
    public default Tuple5<T1, T2, T3, T5, T6> drop4() {
        return drop(keep, keep, keep, __, keep, keep);
    }
    public default Tuple5<T1, T2, T3, T4, T6> drop5() {
        return drop(keep, keep, keep, keep, __, keep);
    }
    public default Tuple5<T1, T2, T3, T4, T5> drop6() {
        return drop(keep, keep, keep, keep, keep, __);
    }
    
    public default Tuple5<T2, T3, T4, T5, T6> drop(
            Absent drop1, 
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5,
            Keep   keep6) {
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_2, _3, _4, _5, _6);
    }
    
    public default Tuple5<T1, T3, T4, T5, T6> drop(
            Keep   keep1, 
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5,
            Keep   keep6) {
        val _1 = _1();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_1, _3, _4, _5, _6);
    }
    
    public default Tuple5<T1, T2, T4, T5, T6> drop(
            Keep   keep1, 
            Keep   keep2,
            Absent drop3,
            Keep   keep4, 
            Keep   keep5, 
            Keep   keep6) {
        val _1 = _1();
        val _2 = _2();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_1, _2, _4, _5, _6);
    }
    
    public default Tuple5<T1, T2, T3, T5, T6> drop(
            Keep   keep1, 
            Keep   keep2, 
            Keep   keep3,
            Absent drop4, 
            Keep   keep5, 
            Keep   keep6) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_1, _2, _3, _5, _6);
    }
    
    public default Tuple5<T1, T2, T3, T4, T6> drop(
            Keep   keep1, 
            Keep   keep2, 
            Keep   keep3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_1, _2, _3, _4, _6);
    }
    
    public default Tuple5<T1, T2, T3, T4, T5> drop(
            Keep   keep1, 
            Keep   keep2, 
            Keep   keep3,
            Keep   keep4,
            Keep   keep5,
            Absent drop6) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_1, _2, _3, _4, _5);
    }
//    
//    public default Tuple2<T3, T4> drop(
//            Absent drop1, 
//            Absent drop2,
//            Keep   keep3,
//            Keep   keep4) {
//        val _3 = _3();
//        val _4 = _4();
//        return Tuple.of(_3, _4);
//    }
//    
//    public default Tuple2<T2, T4> drop(
//            Absent drop1, 
//            Keep   keep2,
//            Absent drop3,
//            Keep   keep4) {
//        val _2 = _2();
//        val _4 = _4();
//        return Tuple.of(_2, _4);
//    }
//    
//    public default Tuple2<T2, T3> drop(
//            Absent drop1, 
//            Keep   keep2,
//            Keep   keep3,
//            Absent drop4) {
//        val _2 = _2();
//        val _3 = _3();
//        return Tuple.of(_2, _3);
//    }
//    
//    public default Tuple2<T1, T4> drop(
//            Keep   keep1, 
//            Absent drop2, 
//            Absent drop3,
//            Keep   keep4) {
//        val _1 = _1();
//        val _4 = _4();
//        return Tuple.of(_1, _4);
//    }
//    
//    public default Tuple2<T1, T2> drop(
//            Keep   keep1,
//            Keep   keep2, 
//            Absent drop3, 
//            Absent drop4) {
//        val _1 = _1();
//        val _2 = _2();
//        return Tuple.of(_1, _2);
//    }
    
}
