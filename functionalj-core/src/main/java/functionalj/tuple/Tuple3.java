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
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import lombok.val;


public interface Tuple3<T1, T2, T3> extends Pipeable<Tuple3<T1, T2, T3>> {
    
    public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 _1, T2 _2, T3 _3) {
        return new ImmutableTuple3<>(_1, _2, _3);
    }
    
    public T1 _1();
    public T2 _2();
    public T3 _3();
    
    @Override
    public default Tuple3<T1, T2, T3> __data() {
        return this;
    }
    
    public default ImmutableTuple3<T1, T2, T3> toImmutableTuple() {
        return ImmutableTuple.of(this);
    }
    
    public default Object[] toArray() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        return new Object[] { _1, _2, _3 };
    }
    
    public default <T> T[] toArray(Class<T> type) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val array = Array.newInstance(type, 3);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        Array.set(array, 2, _3);
        @SuppressWarnings("unchecked")
        val toArray = (T[])array;
        return toArray;
    }
    
    public default FuncList<Object> toList() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        return FuncList.of(_1, _2, _3);
    }
    
    public default <K> FuncMap<K, Object> toMap(K k1, K k2, K k3) {
        val e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object)_1()) : null;
        val e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object)_2()) : null;
        val e3 = (k3 != null) ? ImmutableTuple.of(k3, (Object)_3()) : null;
        return ImmutableMap.ofEntries(e1, e2, e3);
    }
    
    //== mapTo ==
    
    public default <T> T mapTo(Func3<? super T1, ? super T2, ? super T3, T> mapper) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        return mapper.apply(_1, _2, _3);
    }
    
    //== Map ==
    
    public default <NT1> Tuple3<NT1, T2, T3> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it(), it());
    }
    
    public default <NT2> Tuple3<T1, NT2, T3> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper, it());
    }

    public default <NT3> Tuple3<T1, T2, NT3> map3(Function<? super T3, NT3> mapper) {
        return map(it(), it(), mapper);
    }
    
    public default <NT1, NT2, NT3> Tuple3<NT1, NT2, NT3> map(
            Func3<? super T1, ? super T2, ? super T3, Tuple3<NT1, NT2, NT3>> mapper) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        return mapper.apply(_1, _2, _3);
    }
    
    public default <NT1, NT2, NT3> Tuple3<NT1, NT2, NT3> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3) {
        return map(mapper1, mapper2, mapper3, Tuple::of);
    }
    
    public default <NT1, NT2, NT3, T> T map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Func3<? super NT1, ? super NT2, ? super NT3, T> mapper) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val n1 = mapper1.apply(_1);
        val n2 = mapper2.apply(_2);
        val n3 = mapper3.apply(_3);
        return mapper.apply(n1, n2, n3);
    }
    
    public default <NT2, NT3> Tuple3<T1, NT2, NT3> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val n1 = _1;
        val n2 = mapper2.apply(_2);
        val n3 = mapper3.apply(_3);
        return Tuple.of(n1, n2, n3);
    }
    
    public default <NT1, NT3> Tuple3<NT1, T2, NT3> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val n1 = mapper1.apply(_1);
        val n2 = _2;
        val n3 = mapper3.apply(_3);
        return Tuple.of(n1, n2, n3);
    }
    
    public default <NT1, NT2> Tuple3<NT1, NT2, T3> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val n1 = mapper1.apply(_1);
        val n2 = mapper2.apply(_2);
        val n3 = _3;
        return Tuple.of(n1, n2, n3);
    }
    
    public default <NT3> Tuple3<T1, T2, NT3> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val n1 = _1;
        val n2 = _2;
        val n3 = mapper3.apply(_3);
        return Tuple.of(n1, n2, n3);
    }
    
    public default <NT2> Tuple3<T1, NT2, T3> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val n1 = _1;
        val n2 = mapper2.apply(_2);
        val n3 = _3;
        return Tuple.of(n1, n2, n3);
    }
    
    public default <NT1> Tuple3<NT1, T2, T3> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val n1 = mapper1.apply(_1);
        val n2 = _2;
        val n3 = _3;
        return Tuple.of(n1, n2, n3);
    }
    
    //== Reduce ==
    
    public default <TARGET> TARGET reduce(Func2<T1, T2, TARGET> reducer) {
        val _1     = _1();
        val _2     = _2();
        val target = reducer.apply(_1, _2);
        return target;
    }
    
    public default <TARGET> TARGET reduce(Func3<T1, T2, T3, TARGET> reducer) {
        val _1     = _1();
        val _2     = _2();
        val _3     = _3();
        val target = reducer.apply(_1, _2, _3);
        return target;
    }
    
    //== drop ==
    
    public default Tuple2<T1, T2> drop() {
        val _1 = _1();
        val _2 = _2();
        return Tuple.of(_1, _2);
    }
    
    public default Tuple2<T2, T3> drop1() {
        return drop(__, keep, keep);
    }
    public default Tuple2<T1, T3> drop2() {
        return drop(keep, __, keep);
    }
    public default Tuple2<T1, T2> drop3() {
        return drop(keep, keep, __);
    }
    
    public default Tuple2<T2, T3> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3) {
        val _2 = _2();
        val _3 = _3();
        return Tuple.of(_2, _3);
    }
    
    public default Tuple2<T1, T3> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3) {
        val _1 = _1();
        val _3 = _3();
        return Tuple.of(_1, _3);
    }
    
    public default Tuple2<T1, T2> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3) {
        val _1 = _1();
        val _2 = _2();
        return Tuple.of(_1, _2);
    }
    
}
