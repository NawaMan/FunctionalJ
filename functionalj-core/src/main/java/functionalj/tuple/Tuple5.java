package functionalj.tuple;

import static functionalj.functions.Absent.__;
import static functionalj.functions.Func.it;
import static functionalj.functions.Keep.keep;
import static java.util.stream.Collectors.joining;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Function;

import functionalj.functions.Absent;
import functionalj.functions.Func5;
import functionalj.functions.Keep;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import lombok.val;

@SuppressWarnings("javadoc")
public interface Tuple5<T1, T2, T3, T4, T5> extends Pipeable<Tuple5<T1, T2, T3, T4, T5>> {
    
    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new ImmutableTuple5<>(_1, _2, _3, _4, _5);
    }
    
    public T1 _1();
    public T2 _2();
    public T3 _3();
    public T4 _4();
    public T5 _5();
    
    @Override
    public default Tuple5<T1, T2, T3, T4, T5> __data() {
        return this;
    }
    
    public default ImmutableTuple5<T1, T2, T3, T4, T5> toImmutableTuple() {
        return ImmutableTuple.of(this);
    }
    
    public default Object[] toArray() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return new Object[] { _1, _2, _3, _4, _5 };
    }
    
    public default <T> T[] toArray(Class<T> type) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val array = Array.newInstance(type, 5);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        Array.set(array, 2, _3);
        Array.set(array, 3, _4);
        Array.set(array, 4, _5);
        @SuppressWarnings("unchecked")
        val toArray = (T[])array;
        return toArray;
    }
    
    public default FuncList<Object> toList() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return FuncList.of(_1, _2, _3, _4, _5);
    }
    
    public default <K> FuncMap<K, Object> toMap(K k1, K k2, K k3, K k4, K k5) {
        val e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object)_1()) : null;
        val e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object)_2()) : null;
        val e3 = (k3 != null) ? ImmutableTuple.of(k3, (Object)_3()) : null;
        val e4 = (k4 != null) ? ImmutableTuple.of(k4, (Object)_4()) : null;
        val e5 = (k5 != null) ? ImmutableTuple.of(k5, (Object)_5()) : null;
        return ImmutableMap.ofEntries(e1, e2, e3, e4, e5);
    }
    
    
    //== Map ==

    public default <NT1> Tuple5<NT1, T2, T3, T4, T5> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it(), it(), it(), it());
    }
    
    public default <NT2> Tuple5<T1, NT2, T3, T4, T5> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper, it(), it(), it());
    }

    public default <NT3> Tuple5<T1, T2, NT3, T4, T5> map3(Function<? super T3, NT3> mapper) {
        return map(it(), it(), mapper, it(), it());
    }

    public default <NT4> Tuple5<T1, T2, T3, NT4, T5> map4(Function<? super T4, NT4> mapper) {
        return map(it(), it(), it(), mapper, it());
    }
    
    public default <NT5> Tuple5<T1, T2, T3, T4, NT5> map5(Function<? super T5, NT5> mapper) {
        return map(it(), it(), it(), it(), mapper);
    }
    
    public default <NT1, NT2, NT3, NT4, NT5> Tuple5<NT1, NT2, NT3, NT4, NT5> map(
            Func5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Tuple5<NT1, NT2, NT3, NT4, NT5>> mapper) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return mapper.apply(_1, _2, _3, _4, _5);
    }
    
    public default <NT1> Tuple5<NT1, T2, T3, T4, T5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                _4(),
                _5());
    }
    
    public default <NT2> Tuple5<T1, NT2, T3, T4, T5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                _4(),
                _5());
    }
    
    public default <NT1, NT2> Tuple5<NT1, NT2, T3, T4, T5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                _4(),
                _5());
    }
    
    public default <NT3> Tuple5<T1, T2, NT3, T4, T5> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                _4(),
                _5());
    }
    
    public default <NT1, NT3> Tuple5<NT1, T2, NT3, T4, T5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                _4(),
                _5());
    }
    
    public default <NT2, NT3> Tuple5<T1, NT2, NT3, T4, T5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                _5());
    }
    
    public default <NT1, NT2, NT3> Tuple5<NT1, NT2, NT3, T4, T5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                _5());
    }
    
    public default <NT4> Tuple5<T1, T2, T3, NT4, T5> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT1, NT4> Tuple5<NT1, T2, T3, NT4, T5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT2, NT4> Tuple5<T1, NT2, T3, NT4, T5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT1, NT2, NT4> Tuple5<NT1, NT2, T3, NT4, T5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT3, NT4> Tuple5<T1, T2, NT3, NT4, T5> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT1, NT3, NT4> Tuple5<NT1, T2, NT3, NT4, T5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT2, NT3, NT4> Tuple5<T1, NT2, NT3, NT4, T5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT1, NT2, NT3, NT4> Tuple5<NT1, NT2, NT3, NT4, T5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5());
    }
    
    public default <NT5> Tuple5<T1, T2, T3, T4, NT5> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT5> Tuple5<NT1, T2, T3, T4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT2, NT5> Tuple5<T1, NT2, T3, T4, NT5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT2, NT5> Tuple5<NT1, NT2, T3, T4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT3, NT5> Tuple5<T1, T2, NT3, T4, NT5> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT3, NT5> Tuple5<NT1, T2, NT3, T4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT2, NT3, NT5> Tuple5<T1, NT2, NT3, T4, NT5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT2, NT3, NT5> Tuple5<NT1, NT2, NT3, T4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()));
    }
    
    public default <NT4, NT5> Tuple5<T1, T2, T3, NT4, NT5> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT4, NT5> Tuple5<NT1, T2, T3, NT4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    public default <NT2, NT4, NT5> Tuple5<T1, NT2, T3, NT4, NT5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT2, NT4, NT5> Tuple5<NT1, NT2, T3, NT4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    public default <NT3, NT4, NT5> Tuple5<T1, T2, NT3, NT4, NT5> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT3, NT4, NT5> Tuple5<NT1, T2, NT3, NT4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    public default <NT2, NT3, NT4, NT5> Tuple5<T1, NT2, NT3, NT4, NT5> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    public default <NT1, NT2, NT3, NT4, NT5> Tuple5<NT1, NT2, NT3, NT4, NT5> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()));
    }
    
    //== drop ==
    
    public default Tuple4<T1, T2, T3, T4> drop() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_1, _2, _3, _4);
    }
    
    public default Tuple4<T2, T3, T4, T5> drop1() {
        return drop(__, keep, keep, keep, keep);
    }
    public default Tuple4<T1, T3, T4, T5> drop2() {
        return drop(keep, __, keep, keep, keep);
    }
    public default Tuple4<T1, T2, T4, T5> drop3() {
        return drop(keep, keep, __, keep, keep);
    }
    public default Tuple4<T1, T2, T3, T5> drop4() {
        return drop(keep, keep, keep, __, keep);
    }
    public default Tuple4<T1, T2, T3, T4> drop5() {
        return drop(keep, keep, keep, keep, __);
    }
    public default Tuple4<T2, T3, T4, T5> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5) {
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_2, _3, _4, _5);
    }
    
    public default Tuple4<T1, T3, T4, T5> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5) {
        val _1 = _1();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_1, _3, _4, _5);
    }
    
    public default Tuple3<T3, T4, T5> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5) {
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_3, _4, _5);
    }
    
    public default Tuple4<T1, T2, T4, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        val _1 = _1();
        val _2 = _2();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_1, _2, _4, _5);
    }
    
    public default Tuple3<T2, T4, T5> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        val _2 = _2();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_2, _4, _5);
    }
    
    public default Tuple3<T1, T4, T5> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        val _1 = _1();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_1, _4, _5);
    }
    
    public default Tuple2<T4, T5> drop(
            Absent drop1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_4, _5);
    }
    
    public default Tuple4<T1, T2, T3, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _5 = _5();
        return Tuple.of(_1, _2, _3, _5);
    }
    
    public default Tuple3<T2, T3, T5> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        val _2 = _2();
        val _3 = _3();
        val _5 = _5();
        return Tuple.of(_2, _3, _5);
    }
    
    public default Tuple3<T1, T3, T5> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        val _1 = _1();
        val _3 = _3();
        val _5 = _5();
        return Tuple.of(_1, _3, _5);
    }
    
    public default Tuple2<T3, T5> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        val _3 = _3();
        val _5 = _5();
        return Tuple.of(_3, _5);
    }
    
    public default Tuple3<T1, T2, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Keep   keep5) {
        val _1 = _1();
        val _2 = _2();
        val _5 = _5();
        return Tuple.of(_1, _2, _5);
    }
    
    public default Tuple2<T2, T5> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Keep   keep5) {
        val _2 = _2();
        val _5 = _5();
        return Tuple.of(_2, _5);
    }
    
    public default Tuple2<T1, T5> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Absent drop4,
            Keep   keep5) {
        val _1 = _1();
        val _5 = _5();
        return Tuple.of(_1, _5);
    }
    
    public default Tuple4<T1, T2, T3, T4> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_1, _2, _3, _4);
    }
    
    public default Tuple3<T2, T3, T4> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_2, _3, _4);
    }
    
    public default Tuple3<T1, T3, T4> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        val _1 = _1();
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_1, _3, _4);
    }
    
    public default Tuple2<T3, T4> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_3, _4);
    }
    
    public default Tuple3<T1, T2, T4> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Absent drop5) {
        val _1 = _1();
        val _2 = _2();
        val _4 = _4();
        return Tuple.of(_1, _2, _4);
    }
    
    public default Tuple2<T2, T4> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Absent drop5) {
        val _2 = _2();
        val _4 = _4();
        return Tuple.of(_2, _4);
    }
    
    public default Tuple2<T1, T4> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Absent drop5) {
        val _1 = _1();
        val _4 = _4();
        return Tuple.of(_1, _4);
    }
    
    public default Tuple3<T1, T2, T3> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Absent drop5) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        return Tuple.of(_1, _2, _3);
    }
    
    public default Tuple2<T2, T3> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Absent drop5) {
        val _2 = _2();
        val _3 = _3();
        return Tuple.of(_2, _3);
    }
    
    public default Tuple2<T1, T3> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Absent drop5) {
        val _1 = _1();
        val _3 = _3();
        return Tuple.of(_1, _3);
    }
    
    public default Tuple2<T1, T2> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Absent drop5) {
        val _1 = _1();
        val _2 = _2();
        return Tuple.of(_1, _2);
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 2*2*2*2*2; i++) {
            val b0 = (0 != (i & 1));
            val b1 = (0 != (i & 2));
            val b2 = (0 != (i & 2*2));
            val b3 = (0 != (i & 2*2*2));
            val b4 = (0 != (i & 2*2*2*2));
            val d0 = (b0 ? "Absent drop" : "Keep   keep") + 1 + ",";
            val d1 = (b1 ? "Absent drop" : "Keep   keep") + 2 + ",";
            val d2 = (b2 ? "Absent drop" : "Keep   keep") + 3 + ",";
            val d3 = (b3 ? "Absent drop" : "Keep   keep") + 4 + ",";
            val d4 = (b4 ? "Absent drop" : "Keep   keep") + 5 + ") {";
            val count = (b0 ? 1 : 0)
                      + (b1 ? 1 : 0)
                      + (b2 ? 1 : 0)
                      + (b3 ? 1 : 0)
                      + (b4 ? 1 : 0);
            if (count == 5)
                continue;
            if (count == 0)
                continue;
            if (count == 4)
                continue;
            
            val includes = new ArrayList<String>();
            val excludes = new ArrayList<String>();
            if (b0) includes.add("" + 1);
            if (b1) includes.add("" + 2);
            if (b2) includes.add("" + 3);
            if (b3) includes.add("" + 4);
            if (b4) includes.add("" + 5);
            if (!b0) excludes.add("" + 1);
            if (!b1) excludes.add("" + 2);
            if (!b2) excludes.add("" + 3);
            if (!b3) excludes.add("" + 4);
            if (!b4) excludes.add("" + 5);
            
            System.out.println("    public default Tuple" + (5 - count) + "<" + excludes.stream().map("T"::concat).collect(joining(", ")) + "> drop(");
            System.out.println("            " + d0);
            System.out.println("            " + d1);
            System.out.println("            " + d2);
            System.out.println("            " + d3);
            System.out.println("            " + d4);
            
            excludes.stream()
            .map("_"::concat)
            .map(each -> "        val " + each + " = " + each + "();")
            .forEach(System.out::println);
            
            System.out.println("        return Tuple.of(" + excludes.stream().map("_"::concat).collect(joining(", ")) + ");");
            System.out.println("    }");
            
            System.out.println("    ");
        }
    }
    
}
