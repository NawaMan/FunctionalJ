// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import functionalj.function.Func6;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import lombok.val;

@SuppressWarnings("javadoc")
public interface Tuple6<T1, T2, T3, T4, T5, T6> extends Pipeable<Tuple6<T1, T2, T3, T4, T5, T6>> {
    
    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return new ImmutableTuple6<>(_1, _2, _3, _4, _5, _6);
    }
    
    
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
        val _6 = _6();
        return FuncList.of(_1, _2, _3, _4, _5, _6);
    }
    
    public default <K> FuncMap<K, Object> toMap(K k1, K k2, K k3, K k4, K k5, K k6) {
        val e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object)_1()) : null;
        val e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object)_2()) : null;
        val e3 = (k3 != null) ? ImmutableTuple.of(k3, (Object)_3()) : null;
        val e4 = (k4 != null) ? ImmutableTuple.of(k4, (Object)_4()) : null;
        val e5 = (k5 != null) ? ImmutableTuple.of(k5, (Object)_5()) : null;
        val e6 = (k6 != null) ? ImmutableTuple.of(k6, (Object)_6()) : null;
        return ImmutableMap.ofEntries(e1, e2, e3, e4, e5, e6);
    }
    
    //== mapTo ==
    
    public default <T> T mapTo(Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, T> mapper) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return mapper.apply(_1, _2, _3, _4, _5, _6);
    }
    
    //== Map ==
    
    public default <NT1> Tuple6<NT1, T2, T3, T4, T5, T6> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it(), it(), it(), it(), it());
    }
    
    public default <NT2> Tuple6<T1, NT2, T3, T4, T5, T6> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper, it(), it(), it(), it());
    }
    
    public default <NT3> Tuple6<T1, T2, NT3, T4, T5, T6> map3(Function<? super T3, NT3> mapper) {
        return map(it(), it(), mapper, it(), it(), it());
    }
    
    public default <NT4> Tuple6<T1, T2, T3, NT4, T5, T6> map4(Function<? super T4, NT4> mapper) {
        return map(it(), it(), it(), mapper, it(), it());
    }
    
    public default <NT5> Tuple6<T1, T2, T3, T4, NT5, T6> map5(Function<? super T5, NT5> mapper) {
        return map(it(), it(), it(), it(), mapper, it());
    }
    
    public default <NT6> Tuple6<T1, T2, T3, T4, T5, NT6> map6(Function<? super T6, NT6> mapper) {
        return map(it(), it(), it(), it(), it(), mapper);
    }
    
    public default <NT1, NT2, NT3, NT4, NT5, NT6> Tuple6<NT1, NT2, NT3, NT4, NT5, NT6> map(
            Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Tuple6<NT1, NT2, NT3, NT4, NT5, NT6>> mapper) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return mapper.apply(_1, _2, _3, _4, _5, _6);
    }
    
    public default <NT1, NT2, NT3, NT4, NT5, NT6, T> T map(
            Function<? super T1, NT1> mapper1, 
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6,
            Func6<? super NT1, ? super NT2, ? super NT3, ? super NT4, ? super NT5, ? super NT6, T> mapper) {
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
        return mapper.apply(n1, n2, n3, n4, n5, n6);
    }
    public default <NT1> Tuple6<NT1, T2, T3, T4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                _4(),
                _5(),
                _6());
    }
    
    public default <NT2> Tuple6<T1, NT2, T3, T4, T5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                _4(),
                _5(),
                _6());
    }
    
    public default <NT1, NT2> Tuple6<NT1, NT2, T3, T4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                _4(),
                _5(),
                _6());
    }
    
    public default <NT3> Tuple6<T1, T2, NT3, T4, T5, T6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                _4(),
                _5(),
                _6());
    }
    
    public default <NT1, NT3> Tuple6<NT1, T2, NT3, T4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                _4(),
                _5(),
                _6());
    }
    
    public default <NT2, NT3> Tuple6<T1, NT2, NT3, T4, T5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                _5(),
                _6());
    }
    
    public default <NT1, NT2, NT3> Tuple6<NT1, NT2, NT3, T4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                _5(),
                _6());
    }
    
    public default <NT4> Tuple6<T1, T2, T3, NT4, T5, T6> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT1, NT4> Tuple6<NT1, T2, T3, NT4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT2, NT4> Tuple6<T1, NT2, T3, NT4, T5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT1, NT2, NT4> Tuple6<NT1, NT2, T3, NT4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT3, NT4> Tuple6<T1, T2, NT3, NT4, T5, T6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT1, NT3, NT4> Tuple6<NT1, T2, NT3, NT4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT2, NT3, NT4> Tuple6<T1, NT2, NT3, NT4, T5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT1, NT2, NT3, NT4> Tuple6<NT1, NT2, NT3, NT4, T5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                _6());
    }
    
    public default <NT5> Tuple6<T1, T2, T3, T4, NT5, T6> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT5> Tuple6<NT1, T2, T3, T4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT2, NT5> Tuple6<T1, NT2, T3, T4, NT5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT2, NT5> Tuple6<NT1, NT2, T3, T4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT3, NT5> Tuple6<T1, T2, NT3, T4, NT5, T6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT3, NT5> Tuple6<NT1, T2, NT3, T4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT2, NT3, NT5> Tuple6<T1, NT2, NT3, T4, NT5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT2, NT3, NT5> Tuple6<NT1, NT2, NT3, T4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT4, NT5> Tuple6<T1, T2, T3, NT4, NT5, T6> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT4, NT5> Tuple6<NT1, T2, T3, NT4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT2, NT4, NT5> Tuple6<T1, NT2, T3, NT4, NT5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT2, NT4, NT5> Tuple6<NT1, NT2, T3, NT4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT3, NT4, NT5> Tuple6<T1, T2, NT3, NT4, NT5, T6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT3, NT4, NT5> Tuple6<NT1, T2, NT3, NT4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT2, NT3, NT4, NT5> Tuple6<T1, NT2, NT3, NT4, NT5, T6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT1, NT2, NT3, NT4, NT5> Tuple6<NT1, NT2, NT3, NT4, NT5, T6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Absent                    absent6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                _6());
    }
    
    public default <NT6> Tuple6<T1, T2, T3, T4, T5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT6> Tuple6<NT1, T2, T3, T4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT6> Tuple6<T1, NT2, T3, T4, T5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT6> Tuple6<NT1, NT2, T3, T4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT3, NT6> Tuple6<T1, T2, NT3, T4, T5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT3, NT6> Tuple6<NT1, T2, NT3, T4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT3, NT6> Tuple6<T1, NT2, NT3, T4, T5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT3, NT6> Tuple6<NT1, NT2, NT3, T4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT4, NT6> Tuple6<T1, T2, T3, NT4, T5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT4, NT6> Tuple6<NT1, T2, T3, NT4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT4, NT6> Tuple6<T1, NT2, T3, NT4, T5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT4, NT6> Tuple6<NT1, NT2, T3, NT4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT3, NT4, NT6> Tuple6<T1, T2, NT3, NT4, T5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT3, NT4, NT6> Tuple6<NT1, T2, NT3, NT4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT3, NT4, NT6> Tuple6<T1, NT2, NT3, NT4, T5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT3, NT4, NT6> Tuple6<NT1, NT2, NT3, NT4, T5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Absent                    absent5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                _5(),
                mapper6.apply(_6()));
    }
    
    public default <NT5, NT6> Tuple6<T1, T2, T3, T4, NT5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT5, NT6> Tuple6<NT1, T2, T3, T4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT5, NT6> Tuple6<T1, NT2, T3, T4, NT5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT5, NT6> Tuple6<NT1, NT2, T3, T4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT3, NT5, NT6> Tuple6<T1, T2, NT3, T4, NT5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT3, NT5, NT6> Tuple6<NT1, T2, NT3, T4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT3, NT5, NT6> Tuple6<T1, NT2, NT3, T4, NT5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT3, NT5, NT6> Tuple6<NT1, NT2, NT3, T4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Absent                    absent4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                _4(),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT4, NT5, NT6> Tuple6<T1, T2, T3, NT4, NT5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT4, NT5, NT6> Tuple6<NT1, T2, T3, NT4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT4, NT5, NT6> Tuple6<T1, NT2, T3, NT4, NT5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT4, NT5, NT6> Tuple6<NT1, NT2, T3, NT4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Absent                    absent3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                _3(),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT3, NT4, NT5, NT6> Tuple6<T1, T2, NT3, NT4, NT5, NT6> map(
            Absent                    absent1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT3, NT4, NT5, NT6> Tuple6<NT1, T2, NT3, NT4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Absent                    absent2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                _2(),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT2, NT3, NT4, NT5, NT6> Tuple6<T1, NT2, NT3, NT4, NT5, NT6> map(
            Absent                    absent1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                _1(),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
    }
    
    public default <NT1, NT2, NT3, NT4, NT5, NT6> Tuple6<NT1, NT2, NT3, NT4, NT5, NT6> map(
            Function<? super T1, NT1> mapper1,
            Function<? super T2, NT2> mapper2,
            Function<? super T3, NT3> mapper3,
            Function<? super T4, NT4> mapper4,
            Function<? super T5, NT5> mapper5,
            Function<? super T6, NT6> mapper6) {
        return Tuple.of(
                mapper1.apply(_1()),
                mapper2.apply(_2()),
                mapper3.apply(_3()),
                mapper4.apply(_4()),
                mapper5.apply(_5()),
                mapper6.apply(_6()));
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
    
    public default <TARGET> TARGET reduce(Func4<T1, T2, T3, T4, TARGET> reducer) {
        val _1     = _1();
        val _2     = _2();
        val _3     = _3();
        val _4     = _4();
        val target = reducer.apply(_1, _2, _3, _4);
        return target;
    }
    
    public default <TARGET> TARGET reduce(Func5<T1, T2, T3, T4, T5, TARGET> reducer) {
        val _1     = _1();
        val _2     = _2();
        val _3     = _3();
        val _4     = _4();
        val _5     = _5();
        val target = reducer.apply(_1, _2, _3, _4, _5);
        return target;
    }
    
    public default <TARGET> TARGET reduce(Func6<T1, T2, T3, T4, T5, T6, TARGET> reducer) {
        val _1     = _1();
        val _2     = _2();
        val _3     = _3();
        val _4     = _4();
        val _5     = _5();
        val _6     = _6();
        val target = reducer.apply(_1, _2, _3, _4, _5, _6);
        return target;
    }
    
    //== drop ==
    
    public default Tuple5<T1, T2, T3, T4, T5> drop() {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_1, _2, _3, _4, _5);
    }
    
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
    
    public default Tuple4<T3, T4, T5, T6> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5,
            Keep   keep6) {
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_3, _4, _5, _6);
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
    
    public default Tuple4<T2, T4, T5, T6> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5,
            Keep   keep6) {
        val _2 = _2();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_2, _4, _5, _6);
    }
    
    public default Tuple4<T1, T4, T5, T6> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5,
            Keep   keep6) {
        val _1 = _1();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_1, _4, _5, _6);
    }
    
    public default Tuple3<T4, T5, T6> drop(
            Absent drop1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Keep   keep5,
            Keep   keep6) {
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_4, _5, _6);
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
    
    public default Tuple4<T2, T3, T5, T6> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5,
            Keep   keep6) {
        val _2 = _2();
        val _3 = _3();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_2, _3, _5, _6);
    }
    
    public default Tuple4<T1, T3, T5, T6> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5,
            Keep   keep6) {
        val _1 = _1();
        val _3 = _3();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_1, _3, _5, _6);
    }
    
    public default Tuple3<T3, T5, T6> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5,
            Keep   keep6) {
        val _3 = _3();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_3, _5, _6);
    }
    
    public default Tuple4<T1, T2, T5, T6> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Keep   keep5,
            Keep   keep6) {
        val _1 = _1();
        val _2 = _2();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_1, _2, _5, _6);
    }
    
    public default Tuple3<T2, T5, T6> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Keep   keep5,
            Keep   keep6) {
        val _2 = _2();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_2, _5, _6);
    }
    
    public default Tuple3<T1, T5, T6> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Absent drop4,
            Keep   keep5,
            Keep   keep6) {
        val _1 = _1();
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_1, _5, _6);
    }
    
    public default Tuple2<T5, T6> drop(
            Absent drop1,
            Absent drop2,
            Absent drop3,
            Absent drop4,
            Keep   keep5,
            Keep   keep6) {
        val _5 = _5();
        val _6 = _6();
        return Tuple.of(_5, _6);
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
    
    public default Tuple4<T2, T3, T4, T6> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_2, _3, _4, _6);
    }
    
    public default Tuple4<T1, T3, T4, T6> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _3 = _3();
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_1, _3, _4, _6);
    }
    
    public default Tuple3<T3, T4, T6> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _3 = _3();
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_3, _4, _6);
    }
    
    public default Tuple4<T1, T2, T4, T6> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _2 = _2();
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_1, _2, _4, _6);
    }
    
    public default Tuple3<T2, T4, T6> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _2 = _2();
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_2, _4, _6);
    }
    
    public default Tuple3<T1, T4, T6> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_1, _4, _6);
    }
    
    public default Tuple2<T4, T6> drop(
            Absent drop1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Absent drop5,
            Keep   keep6) {
        val _4 = _4();
        val _6 = _6();
        return Tuple.of(_4, _6);
    }
    
    public default Tuple4<T1, T2, T3, T6> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _6 = _6();
        return Tuple.of(_1, _2, _3, _6);
    }
    
    public default Tuple3<T2, T3, T6> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Absent drop5,
            Keep   keep6) {
        val _2 = _2();
        val _3 = _3();
        val _6 = _6();
        return Tuple.of(_2, _3, _6);
    }
    
    public default Tuple3<T1, T3, T6> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _3 = _3();
        val _6 = _6();
        return Tuple.of(_1, _3, _6);
    }
    
    public default Tuple2<T3, T6> drop(
            Absent drop1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Absent drop5,
            Keep   keep6) {
        val _3 = _3();
        val _6 = _6();
        return Tuple.of(_3, _6);
    }
    
    public default Tuple3<T1, T2, T6> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _2 = _2();
        val _6 = _6();
        return Tuple.of(_1, _2, _6);
    }
    
    public default Tuple2<T2, T6> drop(
            Absent drop1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Absent drop5,
            Keep   keep6) {
        val _2 = _2();
        val _6 = _6();
        return Tuple.of(_2, _6);
    }
    
    public default Tuple2<T1, T6> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Absent drop4,
            Absent drop5,
            Keep   keep6) {
        val _1 = _1();
        val _6 = _6();
        return Tuple.of(_1, _6);
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
    
    public default Tuple4<T2, T3, T4, T5> drop(
            Absent drop1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
        val _4 = _4();
        val _5 = _5();
        return Tuple.of(_4, _5);
    }
    
    public default Tuple4<T1, T2, T3, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
        val _3 = _3();
        val _5 = _5();
        return Tuple.of(_3, _5);
    }
    
    public default Tuple3<T1, T2, T5> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Keep   keep5,
            Absent drop6) {
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
            Keep   keep5,
            Absent drop6) {
        val _2 = _2();
        val _5 = _5();
        return Tuple.of(_2, _5);
    }
    
    public default Tuple2<T1, T5> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Absent drop4,
            Keep   keep5,
            Absent drop6) {
        val _1 = _1();
        val _5 = _5();
        return Tuple.of(_1, _5);
    }
    
    public default Tuple4<T1, T2, T3, T4> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Keep   keep4,
            Absent drop5,
            Absent drop6) {
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
            Absent drop5,
            Absent drop6) {
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
            Absent drop5,
            Absent drop6) {
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
            Absent drop5,
            Absent drop6) {
        val _3 = _3();
        val _4 = _4();
        return Tuple.of(_3, _4);
    }
    
    public default Tuple3<T1, T2, T4> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Keep   keep4,
            Absent drop5,
            Absent drop6) {
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
            Absent drop5,
            Absent drop6) {
        val _2 = _2();
        val _4 = _4();
        return Tuple.of(_2, _4);
    }
    
    public default Tuple2<T1, T4> drop(
            Keep   keep1,
            Absent drop2,
            Absent drop3,
            Keep   keep4,
            Absent drop5,
            Absent drop6) {
        val _1 = _1();
        val _4 = _4();
        return Tuple.of(_1, _4);
    }
    
    public default Tuple3<T1, T2, T3> drop(
            Keep   keep1,
            Keep   keep2,
            Keep   keep3,
            Absent drop4,
            Absent drop5,
            Absent drop6) {
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
            Absent drop5,
            Absent drop6) {
        val _2 = _2();
        val _3 = _3();
        return Tuple.of(_2, _3);
    }
    
    public default Tuple2<T1, T3> drop(
            Keep   keep1,
            Absent drop2,
            Keep   keep3,
            Absent drop4,
            Absent drop5,
            Absent drop6) {
        val _1 = _1();
        val _3 = _3();
        return Tuple.of(_1, _3);
    }
    
    public default Tuple2<T1, T2> drop(
            Keep   keep1,
            Keep   keep2,
            Absent drop3,
            Absent drop4,
            Absent drop5,
            Absent drop6) {
        val _1 = _1();
        val _2 = _2();
        return Tuple.of(_1, _2);
    }
    
}
