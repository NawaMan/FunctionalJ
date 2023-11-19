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
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.pipeable.Pipeable;
import lombok.val;

/**
 * Represents a 2-element tuple, which can be used to store ten different values of possibly differing types.
 * 
 * @param <T1> the type of the first element in the tuple
 * @param <T2> the type of the second element in the tuple
 */
public interface Tuple2<T1, T2> extends Pipeable<Tuple2<T1, T2>> {
    
    /**
     * Creates a new Tuple2 object from a Map.Entry object.
     * If the entry is null, a Tuple2 with null values is returned.
     * 
     * @param <T1>  the type of the first element in the tuple
     * @param <T2>  the type of the second element in the tuple
     * @param entry the Map.Entry object containing the elements to be placed in the tuple
     * @return a new Tuple2 object containing the key and value from the Map.Entry object, or a tuple with null values if the entry is null
     */
    public static <T1, T2> Tuple2<T1, T2> of(Map.Entry<? extends T1, ? extends T2> entry) {
        return (entry == null) ? new ImmutableTuple2<>(null, null) : new ImmutableTuple2<>(entry);
    }
    
    /**
     * Creates a new 3-element tuple with the specified values.
     * 
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * 
     * @param _1     the value of the first element
     * @param _2     the value of the second element
     * @return       a new {@link Tuple2} containing the provided elements
     */
    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return new ImmutableTuple2<>(t1, t2);
    }
    
    /**
     * Retrieves the first element of this tuple.
     * 
     * @return the first element of type T1
     */
    public T1 _1();
    
    /**
     * Retrieves the second element of this tuple.
     * 
     * @return the second element of type T2
     */
    public T2 _2();
    
    /**
     * Retrieves the first element of this tuple.
     * 
     * @return the first element of the tuple
     */
    public default T1 first() {
        return _1();
    }
    
    /**
     * Retrieves the second element of this tuple.
     * 
     * @return the second element of the tuple
     */
    public default T2 second() {
        return _2();
    }
    
    
    @Override
    public default Tuple2<T1, T2> __data() {
        return this;
    }
    
    /**
     * Swaps the elements of this tuple, returning a new {@link Tuple2} with the elements in reversed order.
     * 
     * @return a new {@link Tuple2} object where the first element is the second element of this tuple and the second element is the first element of this tuple
     */
    public default Tuple2<T2, T1> swap() {
        return Tuple.of(_2(), _1());
    }
    
    //== with ==
    
    /**
     * Creates a new {@link Tuple3} by appending an additional element to this tuple.
     * 
     * @param <T3>  the type of the new forth element
     * @param new3  the new element to be added as the third element of the tuple
     * @return a new {@link Tuple3} object consisting of the elements of this tuple followed by the new element
     */
    public default <T3> Tuple3<T1, T2, T3> extendWith(T3 new3) {
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
    
    /**
     * Delegated Tuple.
     * 
     * @param <D1>  the first data type.
     * @param <D2>  the second data type.
     */
    public static class Delegated<D1, D2> 
                implements Tuple2<D1, D2> {
        
        private final Tuple2<D1, D2> source;
        
        Delegated(Tuple2<D1, D2> source) {
            this.source = source;
        }
        @Override
        public D1 _1() {
            return __data()._1();
        }
        @Override
        public D2 _2() {
            return source._2();
        }
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the first element of this tuple with a new element.
     *
     * @param newValue  the new value to replace the first element of the tuple
     * @return            the newly {@link Tuple2} with values from this tuple except for the first element.
     */
    public default Tuple2<T1, T2> with1(T1 newValue) {
        return new Tuple2.Delegated<T1, T2>(this) {
            @Override
            public T1 _1() {
                return newValue;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the first element of this tuple with the result of a supplier function.
     *
     * @param supplier1   a supplier ({@link Func0}) of T1 that provides the new first element
     * @return            the newly {@link Tuple2} with values from this tuple except for the first element.
     */
    public default Tuple2<T1, T2> with1(Func0<T1> supplier1) {
        return new Tuple2.Delegated<T1, T2>(this) {
            @Override
            public T1 _1() {
                val newValue = supplier1.get();
                return newValue;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple2} by applying a function to the second element of this tuple.
     *
     * @param  function1   a function ({@link Func1}) function that takes and returns a T1, applied to the first element of this tuple
     * @return             the newly {@link Tuple2} with values from this tuple except for the first element.
     */
    public default Tuple2<T1, T2> with1(Func1<T1, T1> function1) {
        return new Tuple2.Delegated<T1, T2>(this) {
            @Override
            public T1 _1() {
                val oldValue = Tuple2.this._1();
                val newValue = function1.apply(oldValue);
                return newValue;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the second element of this tuple with a new element.
     *
     * @param newValue  the new value to replace the second element of the tuple
     * @return            the newly {@link Tuple2} with values from this tuple except for the second element.
     */
    public default Tuple2<T1, T2> with2(T2 newValue) {
        return new Tuple2.Delegated<T1, T2>(this) {
            @Override
            public T2 _2() {
                return newValue;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the second element of this tuple with the result of a supplier function.
     *
     * @param supplier2   a supplier ({@link Func0}) of T2 that provides the new second element
     * @return            the newly {@link Tuple2} with values from this tuple except for the second element.
     */
    public default Tuple2<T1, T2> with2(Func0<T2> supplier2) {
        return new Tuple2.Delegated<T1, T2>(this) {
            @Override
            public T2 _2() {
                val newValue = supplier2.get();
                return newValue;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple2} by applying a function to the second element of this tuple.
     *
     * @param  function2   a function ({@link Func1}) function that takes and returns a T2, applied to the second element of this tuple
     * @return             the newly {@link Tuple2} with values from this tuple except for the second element.
     */
    public default Tuple2<T1, T2> with2(Func1<T2, T2> function2) {
        return new Tuple2.Delegated<T1, T2>(this) {
            @Override
            public T2 _2() {
                val oldValue = Tuple2.this._2();
                val newValue = function2.apply(oldValue);
                return newValue;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the first element of this tuple with a new element.
     *
     * @param newValue  the new value to replace the first element of the tuple
     * @return            the newly {@link Tuple2} with values from this tuple except for the first element.
     */
    public default Tuple2<T1, T2> withFirst(T1 newValue) {
        return with1(newValue);
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the first element of this tuple with the result of a supplier function.
     *
     * @param supplier1   a supplier ({@link Func0}) of T1 that provides the new first element
     * @return            the newly {@link Tuple2} with values from this tuple except for the first element.
     */
    public default Tuple2<T1, T2> withFirst(Func0<T1> supplier1) {
        return with1(supplier1);
    }
    
    /**
     * Creates a new {@link Tuple2} by applying a function to the second element of this tuple.
     *
     * @param  function1   a function ({@link Func1}) function that takes and returns a T1, applied to the first element of this tuple
     * @return             the newly {@link Tuple2} with values from this tuple except for the first element.
     */
    public default Tuple2<T1, T2> withFirst(Func1<T1, T1> function1) {
        return with1(function1);
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the second element of this tuple with a new element.
     *
     * @param newValue  the new value to replace the second element of the tuple
     * @return            the newly {@link Tuple2} with values from this tuple except for the second element.
     */
    public default Tuple2<T1, T2> withSecond(T2 newValue) {
        return with2(newValue);
    }
    
    /**
     * Creates a new {@link Tuple2} by replacing the second element of this tuple with the result of a supplier function.
     *
     * @param supplier2   a supplier ({@link Func0}) of T2 that provides the new second element
     * @return            the newly {@link Tuple2} with values from this tuple except for the second element.
     */
    public default Tuple2<T1, T2> withSecond(Func0<T2> supplier2) {
        return with2(supplier2);
    }
    
    /**
     * Creates a new {@link Tuple2} by applying a function to the second element of this tuple.
     *
     * @param  function2   a function ({@link Func1}) function that takes and returns a T2, applied to the second element of this tuple
     * @return             the newly {@link Tuple2} with values from this tuple except for the second element.
     */
    public default Tuple2<T1, T2> withSecond(Func1<T2, T2> function2) {
        return with2(function2);
    }
    
    
    //== Converts ==
    
    /**
     * Converts this tuple to an {@link ImmutableTuple2} with the same elements.
     * 
     * @return an {@link ImmutableTuple2} object containing the same elements as this tuple
     */
    public default ImmutableTuple2<T1, T2> toImmutableTuple() {
        return ImmutableTuple.of(this);
    }
    
    /**
     * Converts this tuple to an array.
     * 
     * @return an array containing the elements of this tuple
     */
    public default Object[] toArray() {
        val _1 = _1();
        val _2 = _2();
        return new Object[] { _1, _2 };
    }
    
    /**
     * Converts this tuple to an array of a specified type.
     * If the elements of this tuple are not of the specified type, an {@link ArrayStoreException} may be thrown at runtime.
     * 
     * @param <T>   the component type of the array
     * @param type  the {@link Class} object representing the component type of the array
     * @return an array containing the elements of this tuple, of type T
     */
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
    
    /**
     * Converts this tuple to a {@link FuncList} containing its elements.
     * 
     * @return a {@link FuncList} containing the elements of this tuple
     */
    public default FuncList<Object> toList() {
        val _1 = _1();
        val _2 = _2();
        return FuncList.of(_1, _2);
    }
    
    /**
     * Creates a lazily-evaluated Stream from the elements of this tuple.
     * Each element is added to the stream one by one in a lazy manner.
     * 
     * @return a Stream containing the elements of this tuple
     */
    public default Stream<Object> toLazyStream() {
        val index       = new AtomicInteger();
        val spliterator = new Spliterators.AbstractSpliterator<Object>(4, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super Object> action) {
                int i = index.getAndIncrement();
                switch (i) {
                    case 0: action.accept(_1()); return true;
                    case 1: action.accept(_2()); return true;
                    default: return false;
                }
            }
        };
        return StreamSupport.stream(spliterator, false);
    }
    
    /**
     * Converts this tuple to a {@link FuncMap} with specified keys for its elements.
     * If a key is null, the corresponding entry is not included in the map.
     * 
     * @param <K> the type of keys in the resulting map
     * @param k1  the key for the first element of the tuple
     * @param k2  the key for the second element of the tuple
     * @return a {@link FuncMap} where the elements of this tuple are mapped to the specified keys
     */
    public default <K> FuncMap<K, Object> toMap(K k1, K k2) {
        val e1 = (k1 != null) ? ImmutableTuple.of(k1, (Object) _1()) : null;
        val e2 = (k2 != null) ? ImmutableTuple.of(k2, (Object) _2()) : null;
        return ImmutableFuncMap.ofEntries(e1, e2);
    }
    
    // == mapTo ==
    
    /**
     * Applies a {@link BiFunction} to the elements of this tuple, transforming them into a single value.
     * 
     * @param <T>    the type of the result produced by the mapper
     * @param mapper the {@link BiFunction} that takes two arguments corresponding to the elements of this tuple and produces a result
     * @return the result of applying the mapper function to the elements of this tuple
     */
    public default <T> T mapWith(BiFunction<? super T1, ? super T2, T> mapper) {
        val _1 = _1();
        val _2 = _2();
        return mapper.apply(_1, _2);
    }
    
    // == Map ==
    
    /**
     * Applies a mapping function to the first element of this tuple, producing a tuple with a new type for the first element.
     * 
     * @param <NT1>  the new type of the first element after mapping
     * @param mapper the {@link Function} that takes the first element of this tuple and produces an element of type NT1
     * @return a new Tuple2 with the first element replaced by the result of the mapping function, and the second element remaining the same
     */
    public default <NT1> Tuple2<NT1, T2> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it());
    }
    
    /**
     * Applies a mapping function to the second element of this tuple, producing a tuple with a new type for the second element.
     * 
     * @param <NT2>  the new type of the second element after mapping
     * @param mapper the {@link Function} that takes the second element of this tuple and produces an element of type NT2
     * @return a new Tuple2 with the second element replaced by the result of the mapping function, and the first element remaining the same
     */
    public default <NT2> Tuple2<T1, NT2> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper);
    }
    
    public default <NT1, NT2> Tuple2<NT1, NT2> map(BiFunction<? super T1, ? super T2, Tuple2<NT1, NT2>> mapper) {
        val _1 = _1();
        val _2 = _2();
        return mapper.apply(_1, _2);
    }
    
    // == Reduce ==
    
    public default <TARGET> TARGET reduce(Func2<T1, T2, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val target = reducer.apply(_1, _2);
        return target;
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1> Tuple2<NT1, T2> map(Function<? super T1, NT1> mapper1, Keep keep2) {
        val _1   = _1();
        val _2   = _2();
        val new1 = mapper1.apply(_1);
        return Tuple.of(new1, _2);
    }
    
    // == Map -- Mix ==
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2> Tuple2<T1, NT2> map(Keep keep1, Function<? super T2, NT2> mapper2) {
        val _1   = _1();
        val _2   = _2();
        val new2 = mapper2.apply(_2);
        return Tuple.of(_1, new2);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2> Tuple2<NT1, NT2> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2) {
        val _1   = _1();
        val _2   = _2();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        return Tuple.of(new1, new2);
    }
    
}
