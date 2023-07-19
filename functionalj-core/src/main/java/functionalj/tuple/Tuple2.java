// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Func.it;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import functionalj.function.Absent;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.pipeable.Pipeable;
import lombok.val;

public interface Tuple2<T1, T2> extends Pipeable<Tuple2<T1, T2>> {
    
    public static <T1, T2> Tuple2<T1, T2> of(Map.Entry<? extends T1, ? extends T2> entry) {
        return (entry == null) ? new ImmutableTuple2<>(null, null) : new ImmutableTuple2<>(entry);
    }
    
    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return new ImmutableTuple2<>(t1, t2);
    }
    
    public T1 _1();
    
    public T2 _2();
    
    @Override
    public default Tuple2<T1, T2> __data() {
        return this;
    }
    
    public default Tuple2<T2, T1> swap() {
        return Tuple.of(_2(), _1());
    }
    
    public default ImmutableTuple2<T1, T2> toImmutableTuple() {
        return ImmutableTuple.of(this);
    }
    
    public default T1 first() {
        return _1();
    }
    
    public default T2 second() {
        return _2();
    }
    
    public default <T3> Tuple3<T1, T2, T3> with(T3 new3) {
        return new Tuple3<T1, T2, T3>() {
        
            @Override
            public T1 _1() {
                return Tuple2.this._1();
            }
        
            @Override
            public T2 _2() {
                return Tuple2.this._2();
            }
        
            @Override
            public T3 _3() {
                return new3;
            }
        };
    }
    
    public default Tuple2<T1, T2> with1(T1 new1) {
        return new Tuple2<T1, T2>() {
        
            @Override
            public T1 _1() {
                return new1;
            }
        
            @Override
            public T2 _2() {
                return Tuple2.this._2();
            }
        };
    }
    
    public default Tuple2<T1, T2> with1(Supplier<T1> supplier1) {
        return new Tuple2<T1, T2>() {
        
            @Override
            public T1 _1() {
                return supplier1.get();
            }
        
            @Override
            public T2 _2() {
                return Tuple2.this._2();
            }
        };
    }
    
    public default Tuple2<T1, T2> with1(Func1<T1, T1> function1) {
        return new Tuple2<T1, T2>() {
        
            @Override
            public T1 _1() {
                return function1.apply(Tuple2.this._1());
            }
        
            @Override
            public T2 _2() {
                return Tuple2.this._2();
            }
        };
    }
    
    public default Tuple2<T1, T2> with2(T2 new2) {
        return new Tuple2<T1, T2>() {
        
            @Override
            public T1 _1() {
                return Tuple2.this._1();
            }
        
            @Override
            public T2 _2() {
                return new2;
            }
        };
    }
    
    public default Tuple2<T1, T2> with2(Supplier<T2> supplier2) {
        return new Tuple2<T1, T2>() {
        
            @Override
            public T1 _1() {
                return Tuple2.this._1();
            }
        
            @Override
            public T2 _2() {
                return supplier2.get();
            }
        };
    }
    
    public default Tuple2<T1, T2> with2(Func1<T2, T2> function2) {
        return new Tuple2<T1, T2>() {
        
            @Override
            public T1 _1() {
                return Tuple2.this._1();
            }
        
            @Override
            public T2 _2() {
                return function2.apply(Tuple2.this._2());
            }
        };
    }
    
    public default Tuple2<T1, T2> withFirst(T1 new1) {
        return with1(new1);
    }
    
    public default Tuple2<T1, T2> withFirst(Supplier<T1> supplier1) {
        return with1(supplier1);
    }
    
    public default Tuple2<T1, T2> withFirst(Func1<T1, T1> function1) {
        return with1(function1);
    }
    
    public default Tuple2<T1, T2> withSecond(T2 new2) {
        return with2(new2);
    }
    
    public default Tuple2<T1, T2> withSecond(Supplier<T2> supplier2) {
        return with2(supplier2);
    }
    
    public default Tuple2<T1, T2> withSecond(Func1<T2, T2> function2) {
        return with2(function2);
    }
    
    public default Object[] toArray() {
        val _1 = _1();
        val _2 = _2();
        return new Object[] { _1, _2 };
    }
    
    public default <T> T[] toArray(Class<T> type) {
        val _1 = _1();
        val _2 = _2();
        val array = Array.newInstance(type, 2);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        @SuppressWarnings("unchecked")
        val toArray = (T[]) array;
        return toArray;
    }
    
    public default FuncList<Object> toList() {
        val _1 = _1();
        val _2 = _2();
        return FuncList.of(_1, _2);
    }
    
    public default <K> FuncMap<K, Object> toMap(K k1, K k2) {
        val e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object) _1()) : null;
        val e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object) _2()) : null;
        return ImmutableFuncMap.ofEntries(e1, e2);
    }
    
    // == mapTo ==
    public default <T> T mapTo(BiFunction<? super T1, ? super T2, T> mapper) {
        val _1 = _1();
        val _2 = _2();
        return mapper.apply(_1, _2);
    }
    
    // == Map ==
    public default <NT1> Tuple2<NT1, T2> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it());
    }
    
    public default <NT2> Tuple2<T1, NT2> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper);
    }
    
    public default <NT1, NT2> Tuple2<NT1, NT2> map(BiFunction<? super T1, ? super T2, Tuple2<NT1, NT2>> mapper) {
        val _1 = _1();
        val _2 = _2();
        return mapper.apply(_1, _2);
    }
    
    public default <NT1, NT2> Tuple2<NT1, NT2> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2) {
        return map(mapper1, mapper2, Tuple::of);
    }
    
    public default <NT1, NT2, T> T map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, BiFunction<? super NT1, ? super NT2, T> mapper) {
        val _1 = _1();
        val _2 = _2();
        val n1 = mapper1.apply(_1);
        val n2 = mapper2.apply(_2);
        return mapper.apply(n1, n2);
    }
    
    public default <NT2> Tuple2<T1, NT2> map(Absent absent1, Function<? super T2, NT2> mapper2) {
        val _1 = _1();
        val _2 = _2();
        val n1 = _1;
        val n2 = mapper2.apply(_2);
        return Tuple.of(n1, n2);
    }
    
    public default <NT1> Tuple2<NT1, T2> map(Function<? super T1, NT1> mapper1, Absent absent2) {
        val _1 = _1();
        val _2 = _2();
        val n1 = mapper1.apply(_1);
        val n2 = _2;
        return Tuple.of(n1, n2);
    }
    
    // == Reduce ==
    public default <TARGET> TARGET reduce(Func2<T1, T2, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val target = reducer.apply(_1, _2);
        return target;
    }
    
    // == drop ==
    public default T1 drop() {
        val _1 = _1();
        return _1;
    }
}
