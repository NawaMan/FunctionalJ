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
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Drop;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.pipeable.Pipeable;
import lombok.val;

/**
 * Represents a 4-element tuple, which can be used to store ten different values of possibly differing types.
 * 
 * @param <T1> the type of the first element in the tuple
 * @param <T2> the type of the second element in the tuple
 * @param <T3> the type of the third element in the tuple
 * @param <T4> the type of the fourth element in the tuple
 */
public interface Tuple4<T1, T2, T3, T4> extends Pipeable<Tuple4<T1, T2, T3, T4>> {
    
    /**
     * Creates a new 4-element tuple with the specified values.
     * 
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * 
     * @param _1     the value of the first element
     * @param _2     the value of the second element
     * @param _3     the value of the third element
     * @param _4     the value of the fourth element
     * @return       a new {@link Tuple4} containing the provided elements
     */
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 _1, T2 _2, T3 _3, T4 _4) {
        return new ImmutableTuple4<>(_1, _2, _3, _4);
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
     * Retrieves the third element of this tuple.
     * 
     * @return the third element of type T3
     */
    public T3 _3();
    
    /**
     * Retrieves the forth element of this tuple.
     * 
     * @return the forth element of type T4
     */
    public T4 _4();
    
    
    @Override
    public default Tuple4<T1, T2, T3, T4> __data() {
        return this;
    }
    
    //== with ==
    
    /**
     * Creates a new {@link Tuple5} by appending an additional element to this tuple.
     * 
     * @param <T5>  the type of the new forth element
     * @param new5  the new element to be added as the third element of the tuple
     * @return a new {@link Tuple5} object consisting of the elements of this tuple followed by the new element
     */
    public default <T5> Tuple5<T1, T2, T3, T4, T5> extendWith(T5 new5) {
        return new Tuple5<T1, T2, T3, T4, T5>() {
            @Override
            public T1 _1() {
                return Tuple4.this._1();
            }
            @Override
            public T2 _2() {
                return Tuple4.this._2();
            }
            @Override
            public T3 _3() {
                return Tuple4.this._3();
            }
            @Override
            public T4 _4() {
                return Tuple4.this._4();
            }
            @Override
            public T5 _5() {
                return new5;
            }
        };
    }
    
    /**
     * Delegated Tuple.
     * 
     * @param <D1>   the first data type.
     * @param <D2>   the second data type.
     * @param <D3>   the third data type.
     * @param <D4>   the forth data type.
     */
    public static class Derived<D1, D2, D3, D4> 
                implements Tuple4<D1, D2, D3, D4> {
        
        private final Tuple4<D1, D2, D3, D4> source;
        
        Derived(Tuple4<D1, D2, D3, D4> source) {
            this.source = source;
        }
        @Override
        public D1 _1() {
            return source._1();
        }
        @Override
        public D2 _2() {
            return source._2();
        }
        @Override
        public D3 _3() {
            return source._3();
        }
        @Override
        public D4 _4() {
            return source._4();
        }
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the first element of this tuple with a new element.
     * 
     * @param new1  the new element to replace the first element of the tuple
     * @return a new {@link Tuple4} object with the first element replaced by the new element and the second element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with1(T1 new1) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T1 _1() {
                return new1;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the first element of this tuple with the result of a supplier function.
     * 
     * @param supplier1  a {@link Supplier} of T1 that provides the new first element
     * @return a new {@link Tuple4} object with the first element replaced by the result of the supplier function and the second element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with1(Supplier<T1> supplier1) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T1 _1() {
                return supplier1.get();
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by applying a function to the first element of this tuple.
     * 
     * @param function1  a {@link Func1} function that takes and returns a T1, applied to the first element of this tuple
     * @return a new {@link Tuple4} object with the first element transformed by the provided function and the second element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with1(Func1<T1, T1> function1) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T1 _1() {
                return function1.apply(Tuple4.this._1());
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the second element of this tuple with a new element.
     * 
     * @param new2  the new element to replace the second element of the tuple
     * @return a new {@link Tuple4} object with the second element replaced by the new element and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with2(T2 new2) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T2 _2() {
                return new2;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the second element of this tuple with the result of a supplier function.
     * 
     * @param supplier2  a {@link Supplier} of T2 that provides the new second element
     * @return a new {@link Tuple4} object with the second element replaced by the result of the supplier function and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with2(Supplier<T2> supplier2) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T2 _2() {
                return supplier2.get();
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by applying a function to the second element of this tuple.
     * 
     * @param function2  a {@link Func1} function that takes and returns a T2, applied to the second element of this tuple
     * @return a new {@link Tuple4} object with the second element transformed by the provided function and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with2(Func1<T2, T2> function2) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T2 _2() {
                return function2.apply(Tuple4.this._2());
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the second element of this tuple with a new element.
     * 
     * @param new3  the new element to replace the second element of the tuple
     * @return a new {@link Tuple4} object with the second element replaced by the new element and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with3(T3 new3) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T3 _3() {
                return new3;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the second element of this tuple with the result of a supplier function.
     * 
     * @param supplier3  a {@link Supplier} of T2 that provides the new second element
     * @return a new {@link Tuple4} object with the second element replaced by the result of the supplier function and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with3(Supplier<T3> supplier3) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T3 _3() {
                return supplier3.get();
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by applying a function to the second element of this tuple.
     * 
     * @param function3  a {@link Func1} function that takes and returns a T3, applied to the second element of this tuple
     * @return a new {@link Tuple4} object with the second element transformed by the provided function and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with3(Func1<T3, T3> function3) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T3 _3() {
                return function3.apply(Tuple4.this._3());
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the second element of this tuple with a new element.
     * 
     * @param new4  the new element to replace the second element of the tuple
     * @return a new {@link Tuple4} object with the second element replaced by the new element and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with4(T4 new4) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T4 _4() {
                return new4;
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by replacing the second element of this tuple with the result of a supplier function.
     * 
     * @param supplier4  a {@link Supplier} of T2 that provides the new second element
     * @return a new {@link Tuple4} object with the second element replaced by the result of the supplier function and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with4(Supplier<T4> supplier4) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T4 _4() {
                return supplier4.get();
            }
        };
    }
    
    /**
     * Creates a new {@link Tuple4} by applying a function to the second element of this tuple.
     * 
     * @param  function4  a {@link Func1} function that takes and returns a T4, applied to the second element of this tuple
     * @return            a new {@link Tuple4} object with the second element transformed by the provided function and the first element remaining the same
     */
    public default Tuple4<T1, T2, T3, T4> with4(Func1<T4, T4> function4) {
        return new Tuple4.Derived<T1, T2, T3, T4>(this) {
            @Override
            public T4 _4() {
                return function4.apply(Tuple4.this._4());
            }
        };
    }
    
    //== Converts ==
    
    /**
     * Converts this tuple into an immutable form, {@link ImmutableTuple4}.
     * This method provides a way to ensure that the tuple's elements cannot be modified after conversion.
     * 
     * @return an immutable tuple with the same elements as this tuple
     */
    public default ImmutableTuple4<T1, T2, T3, T4> toImmutableTuple() {
        return ImmutableTuple.of(this);
    }
    
    /**
     * Converts the elements of this tuple into an array.
     * This method provides a convenient way to access all elements of the tuple in an array format.
     * 
     * @return an array of {@link Object} containing the elements of this tuple
     */
    public default Object[] toArray() {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        return new Object[] { _1, _2, _3, _4 };
    }
    
    /**
     * Converts the elements of this tuple into a typed array.
     * This method allows for the creation of an array with a specific type, 
     * using the provided class type for the array's elements.
     * 
     * @param <T>   the component type of the array to be returned
     * @param type  the {@link Class} object representing the component type of the array
     * @return      an array of type T containing the elements of this tuple
     * @throws      ArrayStoreException if an element of this tuple is not of the specified type
     */
    public default <T> T[] toArray(Class<T> type) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val array = Array.newInstance(type, 4);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        Array.set(array, 2, _3);
        Array.set(array, 3, _4);
        
        @SuppressWarnings("unchecked")
        val toArray = (T[]) array;
        return toArray;
    }
    
    /**
     * Converts the elements of this tuple into a {@link FuncList}.
     * This method provides a way to access all elements of the tuple in a list format, 
     * facilitating operations that are more conveniently performed on lists.
     * 
     * @return a {@link FuncList} containing the elements of this tuple
     */
    public default FuncList<Object> toList() {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        return FuncList.of(_1, _2, _3, _4);
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
                    case 2: action.accept(_3()); return true;
                    case 3: action.accept(_4()); return true;
                    default: return false;
                }
            }
        };
        return StreamSupport.stream(spliterator, false);
    }
    
    /**
     * Converts the elements of this tuple into a {@link FuncMap} using the provided keys.
     * Each element of the tuple is mapped to a corresponding key.
     * If any key is null, the corresponding tuple element is not included in the map.
     * 
     * @param <K>   the type of the keys used for mapping
     * @param k1    the key for the first element of the tuple
     * @param k2    the key for the second element of the tuple
     * @param k3    the key for the third element of the tuple
     * @param k4    the key for the fourth element of the tuple
     * @return      a {@link FuncMap} containing the mapped keys and tuple elements
     */
    public default <K> FuncMap<K, Object> toMap(K k1, K k2, K k3, K k4) {
        val e1  = (k1  != null) ? ImmutableTuple.of(k1,  (Object) _1()) : null;
        val e2  = (k2  != null) ? ImmutableTuple.of(k2,  (Object) _2()) : null;
        val e3  = (k3  != null) ? ImmutableTuple.of(k3,  (Object) _3()) : null;
        val e4  = (k4  != null) ? ImmutableTuple.of(k4,  (Object) _4()) : null;
        return ImmutableFuncMap.ofEntries(e1, e2, e3, e4, e4);
    }
    
    // == mapTo ==
    
    /**
     * Applies a function to the elements of this tuple, transforming them into a single result.
     * The provided function takes ten arguments, corresponding to the elements of the tuple,
     * and returns a value of type T. This method is useful for combining tuple elements in a custom manner.
     * 
     * @param <T>     the type of the result
     * @param mapper  the function to apply to the tuple elements, accepting ten arguments
     * @return        the result of applying the mapper function to the tuple elements
     */
    public default <T> T mapWith(Func4<? super T1, ? super T2, ? super T3, ? super T4, T> mapper) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        return mapper.apply(_1, _2, _3, _4);
    }
    
    // == Map -- Each ==
    
    /**
     * Transforms the first element of this tuple using the provided mapper function.
     * This method creates a new tuple where the first element is the result of applying the mapper function
     * to the current first element, while all other elements remain unchanged.
     * 
     * @param <NT1>   the type of the new first element after transformation
     * @param mapper  the function to transform the first element of the tuple
     * @return        a new {@link Tuple4} with the first element transformed and all other elements unchanged
     */
    public default <NT1> Tuple4<NT1, T2, T3, T4> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it(), it(), it());
    }
    
    /**
     * Transforms the second element of this tuple using the provided mapper function.
     * This method creates a new tuple where the second element is the result of applying the mapper function
     * to the current second element, while all other elements remain unchanged.
     * 
     * @param <NT2>   the type of the new second element after transformation
     * @param mapper  the function to transform the second element of the tuple
     * @return        a new {@link Tuple4} with the second element transformed and all other elements unchanged
     */
    public default <NT2> Tuple4<T1, NT2, T3, T4> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper, it(), it());
    }
    
    /**
     * Transforms the third element of this tuple using the provided mapper function.
     * This method creates a new tuple where the third element is the result of applying the mapper function
     * to the current third element, while all other elements remain unchanged.
     * 
     * @param <NT3>   the type of the new third element after transformation
     * @param mapper  the function to transform the third element of the tuple
     * @return        a new {@link Tuple4} with the third element transformed and all other elements unchanged
     */
    public default <NT3> Tuple4<T1, T2, NT3, T4> map3(Function<? super T3, NT3> mapper) {
        return map(it(), it(), mapper, it());
    }
    
    /**
     * Transforms the fourth element of this tuple using the provided mapper function.
     * This method creates a new tuple where the fourth element is the result of applying the mapper function
     * to the current fourth element, while all other elements remain unchanged.
     * 
     * @param <NT4>   the type of the new fourth element after transformation
     * @param mapper  the function to transform the fourth element of the tuple
     * @return        a new {@link Tuple4} with the fourth element transformed and all other elements unchanged
     */
    public default <NT4> Tuple4<T1, T2, T3, NT4> map4(Function<? super T4, NT4> mapper) {
        return map(it(), it(), it(), mapper);
    }
    
    // == Map -- Combine ==
    
    /**
     * Applies a function to the elements of this tuple, resulting in a new tuple with potentially different element types.
     * The provided function takes ten arguments, corresponding to the elements of this tuple, and returns a new {@link Tuple4}.
     * This method is useful for simultaneously transforming all elements of the tuple, allowing for different types for each element in the result.
     * 
     * @param <NT1>   the type of the first element in the new tuple
     * @param <NT2>   the type of the second element in the new tuple
     * @param <NT3>   the type of the third element in the new tuple
     * @param <NT4>   the type of the fourth element in the new tuple
     * @param mapper  the function to apply to the tuple elements, returning a new tuple
     * @return        a new {@link Tuple4} as the result of the transformation
     */
    public default <NT1, NT2, NT3, NT4> Tuple4<NT1, NT2, NT3, NT4> mapCombine(Func4<? super T1, ? super T2, ? super T3, ? super T4, Tuple4<NT1, NT2, NT3, NT4>> mapper) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        return mapper.apply(_1, _2, _3, _4);
    }
    
    // == Map -- Each + Combine ==
    
    /**
     * Applies individual mapping functions to each element of this tuple, followed by a combining function.
     * Each element of the tuple is first transformed using its corresponding mapper function.
     * Then, a combining function is applied to the results, producing a final result of type T.
     * 
     * @param <NT1>    the type of the result from the mapper for the first element
     * @param <NT2>    the type of the result from the mapper for the second element
     * @param <NT3>    the type of the result from the mapper for the third element
     * @param <NT4>    the type of the result from the mapper for the fourth element
     * @param <T>      the final result type
     * 
     * @param mapper1  the function to transform the first element of the tuple
     * @param mapper2  the function to transform the second element of the tuple
     * @param mapper3  the function to transform the third element of the tuple
     * @param mapper4  the function to transform the fourth element of the tuple
     * 
     * @param mapper   the combining function applied to the results of individual mappers
     * @return         the final result of type T obtained after applying all the mapping and combining functions
     */
    public default <NT1, NT2, NT3, NT4, T> T mapCombine(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Func4<? super NT1, ? super NT2, ? super NT3, ? super NT4, T> mapper) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val n1 = mapper1.apply(_1);
        val n2 = mapper2.apply(_2);
        val n3 = mapper3.apply(_3);
        val n4 = mapper4.apply(_4);
        return mapper.apply(n1, n2, n3, n4);
    }
    
    // == Reduce ==
    
    /**
     * Reduces the first two elements of this tuple to a single value using the provided reducer function.
     * The reducer function takes the first and second elements of the tuple and combines them into a result of type TARGET.
     * 
     * @param <TARGET>  the type of the result after reduction
     * @param reducer   the function to combine the first two elements of the tuple
     * @return          the result of the reduction, of type TARGET
     */
    public default <TARGET> TARGET reduce(Func2<T1, T2, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val target = reducer.apply(_1, _2);
        return target;
    }
    
    /**
     * Reduces the first three elements of this tuple to a single value using the provided reducer function.
     * The reducer function takes the first, second, and third elements of the tuple and combines them into a result of type TARGET.
     * 
     * @param <TARGET>  the type of the result after reduction
     * @param reducer   the function to combine the first three elements of the tuple
     * @return          the result of the reduction, of type TARGET
     */
    public default <TARGET> TARGET reduce(Func3<T1, T2, T3, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val target = reducer.apply(_1, _2, _3);
        return target;
    }
    
    /**
     * Reduces the first four elements of this tuple to a single value using the provided reducer function.
     * The reducer function takes the first, second, third, and fourth elements of the tuple and combines them into a result of type TARGET.
     * 
     * @param <TARGET>  the type of the result after reduction
     * @param reducer   the function to combine the first four elements of the tuple
     * @return          the result of the reduction, of type TARGET
     */
    public default <TARGET> TARGET reduce(Func4<T1, T2, T3, T4, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val target = reducer.apply(_1, _2, _3, _4);
        return target;
    }
    
    // == Map -- Mix ==
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1> Tuple4<NT1, T2, T3, T4> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        return Tuple.of(new1, _2, _3, _4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2> Tuple4<T1, NT2, T3, T4> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new2 = mapper2.apply(_2);
        return Tuple.of(_1, new2, _3, _4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3> Tuple4<T1, T2, NT3, T4> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new3 = mapper3.apply(_3);
        return Tuple.of(_1, _2, new3, _4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4> Tuple4<T1, T2, T3, NT4> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, _2, _3, new4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2> Tuple4<NT1, NT2, T3, T4> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        return Tuple.of(new1, new2, _3, _4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3> Tuple4<NT1, T2, NT3, T4> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        return Tuple.of(new1, _2, new3, _4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3> Tuple4<T1, NT2, NT3, T4> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        return Tuple.of(_1, new2, new3, _4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4> Tuple4<NT1, T2, T3, NT4> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, _2, _3, new4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4> Tuple4<T1, NT2, T3, NT4> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, new2, _3, new4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4> Tuple4<T1, T2, NT3, NT4> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, _2, new3, new4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3> Tuple4<NT1, NT2, NT3, T4> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        return Tuple.of(new1, new2, new3, _4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4> Tuple4<NT1, NT2, T3, NT4> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, new2, _3, new4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4> Tuple4<NT1, T2, NT3, NT4> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, _2, new3, new4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4> Tuple4<T1, NT2, NT3, NT4> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, new2, new3, new4);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4> Tuple4<NT1, NT2, NT3, NT4> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, new2, new3, new4);
    }
    
    //== drop each ==
    
    /** @return  a {@link Tuple3} with values from this tuple except for the first element. */
    public default Tuple3<T2, T3, T4> drop() {
        return drop1();
    }
    
    /** @return  a {@link Tuple3} with values from this tuple except for the first element. */
    public default Tuple3<T2, T3, T4> drop1() {
        return Tuple.of(_2(), _3(), _4());
    }
    
    /** @return  a {@link Tuple3} with values from this tuple except for the second element. */
    public default Tuple3<T1, T3, T4> drop2() {
        return Tuple.of(_1(), _3(), _4());
    }
    
    /** @return  a {@link Tuple3} with values from this tuple except for the third element. */
    public default Tuple3<T1, T2, T4> drop3() {
        return Tuple.of(_1(), _2(), _4());
    }
    
    /** @return  a {@link Tuple3} with values from this tuple except for the forth element. */
    public default Tuple3<T1, T2, T3> drop4() {
        return Tuple.of(_1(), _2(), _3());
    }
    
    //== drop - mix ==
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T2> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4) {
        val _1  = _1();
        val _2  = _2();
        return Tuple.of(_1, _2);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T3> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4) {
        val _1  = _1();
        val _3  = _3();
        return Tuple.of(_1, _3);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T3> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4) {
        val _2  = _2();
        val _3  = _3();
        return Tuple.of(_2, _3);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T4> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4) {
        val _1  = _1();
        val _4  = _4();
        return Tuple.of(_1, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T4> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4) {
        val _2  = _2();
        val _4  = _4();
        return Tuple.of(_2, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T3, T4> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4) {
        val _3  = _3();
        val _4  = _4();
        return Tuple.of(_3, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T3> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        return Tuple.of(_1, _2, _3);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T4> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        return Tuple.of(_1, _2, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T3, T4> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        return Tuple.of(_1, _3, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T3, T4> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        return Tuple.of(_2, _3, _4);
    }
    
}
