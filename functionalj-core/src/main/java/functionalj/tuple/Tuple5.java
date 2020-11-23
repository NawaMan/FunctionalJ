// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.tuple;

import static functionalj.function.Absent.__;
import static functionalj.function.Func.it;
import static functionalj.tuple.Keep.keep;

import java.lang.reflect.Array;
import java.util.function.Function;

import functionalj.function.Absent;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;


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
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
        return new Object[] { _1, _2, _3, _4, _5 };
    }
    
    public default <T> T[] toArray(Class<T> type) {
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
        var array = Array.newInstance(type, 5);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        Array.set(array, 2, _3);
        Array.set(array, 3, _4);
        Array.set(array, 4, _5);
        @SuppressWarnings("unchecked")
        var toArray = (T[])array;
        return toArray;
    }
    
    public default FuncList<Object> toList() {
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
        return FuncList.of(_1, _2, _3, _4, _5);
    }
    
    public default <K> FuncMap<K, Object> toMap(K k1, K k2, K k3, K k4, K k5) {
        var e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object)_1()) : null;
        var e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object)_2()) : null;
        var e3 = (k3 != null) ? ImmutableTuple.of(k3, (Object)_3()) : null;
        var e4 = (k4 != null) ? ImmutableTuple.of(k4, (Object)_4()) : null;
        var e5 = (k5 != null) ? ImmutableTuple.of(k5, (Object)_5()) : null;
        return ImmutableMap.ofEntries(e1, e2, e3, e4, e5);
    }
    
    //== mapTo ==
    
    public default <T> T mapTo(Func5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, T> mapper) {
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
        return mapper.apply(_1, _2, _3, _4, _5);
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
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
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
    
    //== Reduce ==
    
    public default <TARGET> TARGET reduce(Func2<T1, T2, TARGET> reducer) {
        var _1     = _1();
        var _2     = _2();
        var target = reducer.apply(_1, _2);
        return target;
    }
    
    public default <TARGET> TARGET reduce(Func3<T1, T2, T3, TARGET> reducer) {
        var _1     = _1();
        var _2     = _2();
        var _3     = _3();
        var target = reducer.apply(_1, _2, _3);
        return target;
    }
    
    public default <TARGET> TARGET reduce(Func4<T1, T2, T3, T4, TARGET> reducer) {
        var _1     = _1();
        var _2     = _2();
        var _3     = _3();
        var _4     = _4();
        var target = reducer.apply(_1, _2, _3, _4);
        return target;
    }
    
    public default <TARGET> TARGET reduce(Func5<T1, T2, T3, T4, T5, TARGET> reducer) {
        var _1     = _1();
        var _2     = _2();
        var _3     = _3();
        var _4     = _4();
        var _5     = _5();
        var target = reducer.apply(_1, _2, _3, _4, _5);
        return target;
    }
    
    //== drop ==
    
    public default Tuple4<T1, T2, T3, T4> drop() {
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
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
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
        return Tuple.of(_2, _3, _4, _5);
    }
    
    public default Tuple4<T1, T3, T4, T5> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5) {
        var _1 = _1();
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
        return Tuple.of(_1, _3, _4, _5);
    }
    
    public default Tuple3<T3, T4, T5> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5) {
        var _3 = _3();
        var _4 = _4();
        var _5 = _5();
        return Tuple.of(_3, _4, _5);
    }
    
    public default Tuple4<T1, T2, T4, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        var _1 = _1();
        var _2 = _2();
        var _4 = _4();
        var _5 = _5();
        return Tuple.of(_1, _2, _4, _5);
    }
    
    public default Tuple3<T2, T4, T5> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        var _2 = _2();
        var _4 = _4();
        var _5 = _5();
        return Tuple.of(_2, _4, _5);
    }
    
    public default Tuple3<T1, T4, T5> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        var _1 = _1();
        var _4 = _4();
        var _5 = _5();
        return Tuple.of(_1, _4, _5);
    }
    
    public default Tuple2<T4, T5> drop(
            Absent drop1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5) {
        var _4 = _4();
        var _5 = _5();
        return Tuple.of(_4, _5);
    }
    
    public default Tuple4<T1, T2, T3, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _5 = _5();
        return Tuple.of(_1, _2, _3, _5);
    }
    
    public default Tuple3<T2, T3, T5> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        var _2 = _2();
        var _3 = _3();
        var _5 = _5();
        return Tuple.of(_2, _3, _5);
    }
    
    public default Tuple3<T1, T3, T5> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        var _1 = _1();
        var _3 = _3();
        var _5 = _5();
        return Tuple.of(_1, _3, _5);
    }
    
    public default Tuple2<T3, T5> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5) {
        var _3 = _3();
        var _5 = _5();
        return Tuple.of(_3, _5);
    }
    
    public default Tuple3<T1, T2, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Keep   keep5) {
        var _1 = _1();
        var _2 = _2();
        var _5 = _5();
        return Tuple.of(_1, _2, _5);
    }
    
    public default Tuple2<T2, T5> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Keep   keep5) {
        var _2 = _2();
        var _5 = _5();
        return Tuple.of(_2, _5);
    }
    
    public default Tuple2<T1, T5> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Absent drop4,
            Keep   keep5) {
        var _1 = _1();
        var _5 = _5();
        return Tuple.of(_1, _5);
    }
    
    public default Tuple4<T1, T2, T3, T4> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        return Tuple.of(_1, _2, _3, _4);
    }
    
    public default Tuple3<T2, T3, T4> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        var _2 = _2();
        var _3 = _3();
        var _4 = _4();
        return Tuple.of(_2, _3, _4);
    }
    
    public default Tuple3<T1, T3, T4> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        var _1 = _1();
        var _3 = _3();
        var _4 = _4();
        return Tuple.of(_1, _3, _4);
    }
    
    public default Tuple2<T3, T4> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5) {
        var _3 = _3();
        var _4 = _4();
        return Tuple.of(_3, _4);
    }
    
    public default Tuple3<T1, T2, T4> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Absent drop5) {
        var _1 = _1();
        var _2 = _2();
        var _4 = _4();
        return Tuple.of(_1, _2, _4);
    }
    
    public default Tuple2<T2, T4> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Absent drop5) {
        var _2 = _2();
        var _4 = _4();
        return Tuple.of(_2, _4);
    }
    
    public default Tuple2<T1, T4> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Absent drop5) {
        var _1 = _1();
        var _4 = _4();
        return Tuple.of(_1, _4);
    }
    
    public default Tuple3<T1, T2, T3> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Absent drop5) {
        var _1 = _1();
        var _2 = _2();
        var _3 = _3();
        return Tuple.of(_1, _2, _3);
    }
    
    public default Tuple2<T2, T3> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Absent drop5) {
        var _2 = _2();
        var _3 = _3();
        return Tuple.of(_2, _3);
    }
    
    public default Tuple2<T1, T3> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Absent drop5) {
        var _1 = _1();
        var _3 = _3();
        return Tuple.of(_1, _3);
    }
    
    public default Tuple2<T1, T2> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Absent drop5) {
        var _1 = _1();
        var _2 = _2();
        return Tuple.of(_1, _2);
    }
    
}
