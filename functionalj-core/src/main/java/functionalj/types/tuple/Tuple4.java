package functionalj.types.tuple;

import static functionalj.FunctionalJ.it;
import static functionalj.functions.Absent.__;
import static functionalj.functions.Keep.keep;

import java.lang.reflect.Array;
import java.util.function.Function;

import functionalj.functions.Absent;
import functionalj.functions.Keep;
import functionalj.pipeable.Pipeable;
import functionalj.types.list.FunctionalList;
import functionalj.types.map.FunctionalMap;
import functionalj.types.map.ImmutableMap;
import lombok.val;

@SuppressWarnings("javadoc")
public interface Tuple4<T1, T2, T3, T4> extends Pipeable<Tuple4<T1, T2, T3, T4>> {

    public T1 _1();
    public T2 _2();
    public T3 _3();
    public T4 _4();
    
    @Override
    public default Tuple4<T1, T2, T3, T4> __data() {
        return this;
    }
    
    public default ImmutableTuple4<T1, T2, T3, T4> toImmutableTuple() {
        return ImmutableTuple.of(this);
    }
    
    public default Object[] toArray() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        return new Object[] { _1, _2, _3, _4 };
    }
    
    public default <T> T[] toArray(Class<T> type) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val array = Array.newInstance(type, 4);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        Array.set(array, 2, _3);
        Array.set(array, 3, _4);
        @SuppressWarnings("unchecked")
		val toArray = (T[])array;
		return toArray;
    }
    
    public default FunctionalList<Object> toList() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        return FunctionalList.of(_1, _2, _3, _4);
    }
    
    public default <K> FunctionalMap<K, Object> toMap(K k1, K k2, K k3, K k4) {
        val e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object)_1()) : null;
        val e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object)_2()) : null;
        val e3 = (k3 != null) ? ImmutableTuple.of(k3, (Object)_3()) : null;
        val e4 = (k4 != null) ? ImmutableTuple.of(k4, (Object)_4()) : null;
        return ImmutableMap.ofEntries(e1, e2, e3, e4);
    }
    
    
    //== Map ==
    
    public default <NT1> Tuple4<NT1, T2, T3, T4> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it(), it(), it());
    }
    
    public default <NT2> Tuple4<T1, NT2, T3, T4> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper, it(), it());
    }
    
    public default <NT3> Tuple4<T1, T2, NT3, T4> map3(Function<? super T3, NT3> mapper) {
        return map(it(), it(), mapper, it());
    }
    
    public default <NT4> Tuple4<T1, T2, T3, NT4> map4(Function<? super T4, NT4> mapper) {
        return map(it(), it(), it(), mapper);
    }
    
    public default <NT1> Tuple4<NT1, T2, T3, T4> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                _4());
    }
    
    public default <NT2> Tuple4<T1, NT2, T3, T4> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                _4());
    }
    
    public default <NT1, NT2> Tuple4<NT1, NT2, T3, T4> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                _4());
    }
    
    public default <NT3> Tuple4<T1, T2, NT3, T4> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                _4());
    }
    
    public default <NT1, NT3> Tuple4<NT1, T2, NT3, T4> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                _4());
    }
    
    public default <NT2, NT3> Tuple4<T1, NT2, NT3, T4> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4());
    }
    
    public default <NT1, NT2, NT3> Tuple4<NT1, NT2, NT3, T4> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4());
    }
    
    public default <NT4> Tuple4<T1, T2, T3, NT4> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                mapper4.apply(_4()));
    }
    
    public default <NT1, NT4> Tuple4<NT1, T2, T3, NT4> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                mapper4.apply(_4()));
    }
    
    public default <NT2, NT4> Tuple4<T1, NT2, T3, NT4> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()));
    }
    
    public default <NT1, NT2, NT4> Tuple4<NT1, NT2, T3, NT4> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()));
    }
    
    public default <NT3, NT4> Tuple4<T1, T2, NT3, NT4> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()));
    }
    
    public default <NT1, NT3, NT4> Tuple4<NT1, T2, NT3, NT4> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()));
    }
    
    public default <NT2, NT3, NT4> Tuple4<T1, NT2, NT3, NT4> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()));
    }
    
    public default <NT1, NT2, NT3, NT4> Tuple4<NT1, NT2, NT3, NT4> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()));
    }
    
    //== drop ==
    
    public default Tuple3<T2, T3, T4> drop1() {
        return drop(__, keep, keep, keep);
    }
    public default Tuple3<T1, T3, T4> drop2() {
        return drop(keep, __, keep, keep);
    }
    public default Tuple3<T1, T2, T4> drop3() {
        return drop(keep, keep, __, keep);
    }
    public default Tuple3<T1, T2, T3> drop4() {
        return drop(keep, keep, keep, __);
    }
    
    public default Tuple3<T2, T3, T4> drop(
            Absent drop1, 
            Keep   keep2,
            Keep   keep3,
            Keep   keep4) {
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_2, _3, _4);
    }
    
    public default Tuple3<T1, T3, T4> drop(
            Keep   keep1, 
            Absent drop2,
            Keep   keep3,
            Keep   keep4) {
        val _1 = _1();
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_1, _3, _4);
    }
    
    public default Tuple3<T1, T2, T4> drop(
            Keep   keep1, 
            Keep   keep2,
            Absent drop3,
            Keep   keep4) {
        val _1 = _1();
        val _2 = _2();
        val _4 = _4();
        return Tuple.of(_1, _2, _4);
    }
    
    public default Tuple3<T1, T2, T3> drop(
            Keep   keep1, 
            Keep   keep2, 
            Keep   keep3,
            Absent drop4) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        return Tuple.of(_1, _2, _3);
    }
    
    public default Tuple2<T3, T4> drop(
            Absent drop1, 
            Absent drop2,
            Keep   keep3,
            Keep   keep4) {
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_3, _4);
    }
    
    public default Tuple2<T2, T4> drop(
            Absent drop1, 
            Keep   keep2,
            Absent drop3,
            Keep   keep4) {
        val _2 = _2();
        val _4 = _4();
        return Tuple.of(_2, _4);
    }
    
    public default Tuple2<T2, T3> drop(
            Absent drop1, 
            Keep   keep2,
            Keep   keep3,
            Absent drop4) {
        val _2 = _2();
        val _3 = _3();
        return Tuple.of(_2, _3);
    }
    
    public default Tuple2<T1, T4> drop(
            Keep   keep1, 
            Absent drop2, 
            Absent drop3,
            Keep   keep4) {
        val _1 = _1();
        val _4 = _4();
        return Tuple.of(_1, _4);
    }
    
    public default Tuple2<T1, T2> drop(
            Keep   keep1,
            Keep   keep2, 
            Absent drop3, 
            Absent drop4) {
        val _1 = _1();
        val _2 = _2();
        return Tuple.of(_1, _2);
    }
    
}
