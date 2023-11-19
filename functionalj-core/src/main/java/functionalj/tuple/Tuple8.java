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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Drop;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.pipeable.Pipeable;
import lombok.val;

/**
 * Represents a 8-element tuple, which can be used to store ten different values of possibly differing types.
 * 
 * @param <T1> the type of the first element in the tuple
 * @param <T2> the type of the second element in the tuple
 * @param <T3> the type of the third element in the tuple
 * @param <T4> the type of the fourth element in the tuple
 * @param <T5> the type of the fifth element in the tuple
 * @param <T6> the type of the sixth element in the tuple
 * @param <T7> the type of the seventh element in the tuple
 * @param <T8> the type of the eighth element in the tuple
 */
public interface Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> extends Pipeable<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> {
    
    /**
     * Creates a new 8-element tuple with the specified values.
     * 
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * @param <T7>   the type of the seventh element
     * @param <T8>   the type of the eighth element
     * 
     * @param _1     the value of the first element
     * @param _2     the value of the second element
     * @param _3     the value of the third element
     * @param _4     the value of the fourth element
     * @param _5     the value of the fifth element
     * @param _6     the value of the sixth element
     * @param _7     the value of the seventh element
     * @param _8     the value of the eighth element
     * @return       a new {@link Tuple8} containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        return new ImmutableTuple8<>(_1, _2, _3, _4, _5, _6, _7, _8);
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
    
    /**
     * Retrieves the fifth element of this tuple.
     * 
     * @return the fifth element of type T5
     */
    public T5 _5();
    
    /**
     * Retrieves the sixth element of this tuple.
     * 
     * @return the sixth element of type T6
     */
    public T6 _6();
    
    /**
     * Retrieves the seventh element of this tuple.
     * 
     * @return the seventh element of type T7
     */
    public T7 _7();
    
    /**
     * Retrieves the eight element of this tuple.
     * 
     * @return the eight element of type T8
     */
    public T8 _8();
    
    
    @Override
    public default Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> __data() {
        return this;
    }
    
    /**
     * Converts this tuple into an immutable form, {@link ImmutableTuple8}.
     * This method provides a way to ensure that the tuple's elements cannot be modified after conversion.
     * 
     * @return an immutable tuple with the same elements as this tuple
     */
    public default ImmutableTuple8<T1, T2, T3, T4, T5, T6, T7, T8> toImmutableTuple() {
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
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return new Object[] { _1, _2, _3, _4, _5, _6, _7, _8 };
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
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        val array = Array.newInstance(type, 6);
        Array.set(array, 0, _1);
        Array.set(array, 1, _2);
        Array.set(array, 2, _3);
        Array.set(array, 3, _4);
        Array.set(array, 4, _5);
        Array.set(array, 5, _6);
        Array.set(array, 6, _7);
        Array.set(array, 7, _8);
        
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
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return FuncList.of(_1, _2, _3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Creates a lazily-evaluated Stream from the elements of this tuple.
     * Each element is added to the stream one by one in a lazy manner.
     * 
     * @return a Stream containing the elements of this tuple
     */
    public default Stream<Object> toLazyStream() {
        val index       = new AtomicInteger();
        val spliterator = new Spliterators.AbstractSpliterator<Object>(8, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super Object> action) {
                int i = index.getAndIncrement();
                switch (i) {
                    case 0: action.accept(_1()); return true;
                    case 1: action.accept(_2()); return true;
                    case 2: action.accept(_3()); return true;
                    case 3: action.accept(_4()); return true;
                    case 4: action.accept(_5()); return true;
                    case 5: action.accept(_6()); return true;
                    case 6: action.accept(_7()); return true;
                    case 7: action.accept(_8()); return true;
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
     * @param k5    the key for the fifth element of the tuple
     * @param k6    the key for the sixth element of the tuple
     * @param k7    the key for the seventh element of the tuple
     * @param k8    the key for the eighth element of the tuple
     * @return      a {@link FuncMap} containing the mapped keys and tuple elements
     */
    public default <K> FuncMap<K, Object> toMap(K k1, K k2, K k3, K k4, K k5, K k6, K k7, K k8) {
        val e1  = (k1  != null) ? ImmutableTuple.of(k1,  (Object) _1()) : null;
        val e2  = (k2  != null) ? ImmutableTuple.of(k2,  (Object) _2()) : null;
        val e3  = (k3  != null) ? ImmutableTuple.of(k3,  (Object) _3()) : null;
        val e4  = (k4  != null) ? ImmutableTuple.of(k4,  (Object) _4()) : null;
        val e5  = (k5  != null) ? ImmutableTuple.of(k5,  (Object) _5()) : null;
        val e6  = (k6  != null) ? ImmutableTuple.of(k6,  (Object) _6()) : null;
        val e7  = (k7  != null) ? ImmutableTuple.of(k7,  (Object) _7()) : null;
        val e8  = (k8  != null) ? ImmutableTuple.of(k8,  (Object) _8()) : null;
        return ImmutableFuncMap.ofEntries(e1, e2, e3, e4, e5, e6, e7, e8);
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
    public default <T> T mapWith(Func8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, T> mapper) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return mapper.apply(_1, _2, _3, _4, _5, _6, _7, _8);
    }
    
    // == Map -- Each ==
    
    /**
     * Transforms the first element of this tuple using the provided mapper function.
     * This method creates a new tuple where the first element is the result of applying the mapper function
     * to the current first element, while all other elements remain unchanged.
     * 
     * @param <NT1>   the type of the new first element after transformation
     * @param mapper  the function to transform the first element of the tuple
     * @return        a new {@link Tuple8} with the first element transformed and all other elements unchanged
     */
    public default <NT1> Tuple8<NT1, T2, T3, T4, T5, T6, T7, T8> map1(Function<? super T1, NT1> mapper) {
        return map(mapper, it(), it(), it(), it(), it(), it(), it());
    }
    
    /**
     * Transforms the second element of this tuple using the provided mapper function.
     * This method creates a new tuple where the second element is the result of applying the mapper function
     * to the current second element, while all other elements remain unchanged.
     * 
     * @param <NT2>   the type of the new second element after transformation
     * @param mapper  the function to transform the second element of the tuple
     * @return        a new {@link Tuple8} with the second element transformed and all other elements unchanged
     */
    public default <NT2> Tuple8<T1, NT2, T3, T4, T5, T6, T7, T8> map2(Function<? super T2, NT2> mapper) {
        return map(it(), mapper, it(), it(), it(), it(), it(), it());
    }
    
    /**
     * Transforms the third element of this tuple using the provided mapper function.
     * This method creates a new tuple where the third element is the result of applying the mapper function
     * to the current third element, while all other elements remain unchanged.
     * 
     * @param <NT3>   the type of the new third element after transformation
     * @param mapper  the function to transform the third element of the tuple
     * @return        a new {@link Tuple8} with the third element transformed and all other elements unchanged
     */
    public default <NT3> Tuple8<T1, T2, NT3, T4, T5, T6, T7, T8> map3(Function<? super T3, NT3> mapper) {
        return map(it(), it(), mapper, it(), it(), it(), it(), it());
    }
    
    /**
     * Transforms the fourth element of this tuple using the provided mapper function.
     * This method creates a new tuple where the fourth element is the result of applying the mapper function
     * to the current fourth element, while all other elements remain unchanged.
     * 
     * @param <NT4>   the type of the new fourth element after transformation
     * @param mapper  the function to transform the fourth element of the tuple
     * @return        a new {@link Tuple8} with the fourth element transformed and all other elements unchanged
     */
    public default <NT4> Tuple8<T1, T2, T3, NT4, T5, T6, T7, T8> map4(Function<? super T4, NT4> mapper) {
        return map(it(), it(), it(), mapper, it(), it(), it(), it());
    }
    
    /**
     * Transforms the fifth element of this tuple using the provided mapper function.
     * This method creates a new tuple where the fifth element is the result of applying the mapper function
     * to the current fifth element, while all other elements remain unchanged.
     * 
     * @param <NT5>   the type of the new fifth element after transformation
     * @param mapper  the function to transform the fifth element of the tuple
     * @return        a new {@link Tuple8} with the fifth element transformed and all other elements unchanged
     */
    public default <NT5> Tuple8<T1, T2, T3, T4, NT5, T6, T7, T8> map5(Function<? super T5, NT5> mapper) {
        return map(it(), it(), it(), it(), mapper, it(), it(), it());
    }
    
    /**
     * Transforms the sixth element of this tuple using the provided mapper function.
     * This method creates a new tuple where the sixth element is the result of applying the mapper function
     * to the current sixth element, while all other elements remain unchanged.
     * 
     * @param <NT6>   the type of the new sixth element after transformation
     * @param mapper  the function to transform the sixth element of the tuple
     * @return        a new {@link Tuple8} with the sixth element transformed and all other elements unchanged
     */
    public default <NT6> Tuple8<T1, T2, T3, T4, T5, NT6, T7, T8> map6(Function<? super T6, NT6> mapper) {
        return map(it(), it(), it(), it(), it(), mapper, it(), it());
    }
    
    /**
     * Transforms the seventh element of this tuple using the provided mapper function.
     * This method creates a new tuple where the seventh element is the result of applying the mapper function
     * to the current seventh element, while all other elements remain unchanged.
     * 
     * @param <NT7>   the type of the new seventh element after transformation
     * @param mapper  the function to transform the seventh element of the tuple
     * @return        a new {@link Tuple8} with the seventh element transformed and all other elements unchanged
     */
    public default <NT7> Tuple8<T1, T2, T3, T4, T5, T6, NT7, T8> map7(Function<? super T7, NT7> mapper) {
        return map(it(), it(), it(), it(), it(), it(), mapper, it());
    }
    
    /**
     * Transforms the eighth element of this tuple using the provided mapper function.
     * This method creates a new tuple where the eighth element is the result of applying the mapper function
     * to the current eighth element, while all other elements remain unchanged.
     * 
     * @param <NT8>   the type of the new eighth element after transformation
     * @param mapper  the function to transform the eighth element of the tuple
     * @return        a new {@link Tuple8} with the eighth element transformed and all other elements unchanged
     */
    public default <NT8> Tuple8<T1, T2, T3, T4, T5, T6, T7, NT8> map8(Function<? super T8, NT8> mapper) {
        return map(it(), it(), it(), it(), it(), it(), it(), mapper);
    }
    
    // == Map -- Combine ==
    
    /**
     * Applies a function to the elements of this tuple, resulting in a new tuple with potentially different element types.
     * The provided function takes ten arguments, corresponding to the elements of this tuple, and returns a new {@link Tuple8}.
     * This method is useful for simultaneously transforming all elements of the tuple, allowing for different types for each element in the result.
     * 
     * @param <NT1>   the type of the first element in the new tuple
     * @param <NT2>   the type of the second element in the new tuple
     * @param <NT3>   the type of the third element in the new tuple
     * @param <NT4>   the type of the fourth element in the new tuple
     * @param <NT5>   the type of the fifth element in the new tuple
     * @param <NT6>   the type of the sixth element in the new tuple
     * @param <NT7>   the type of the seventh element in the new tuple
     * @param <NT8>   the type of the eighth element in the new tuple
     * @param mapper  the function to apply to the tuple elements, returning a new tuple
     * @return        a new {@link Tuple8} as the result of the transformation
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT6, NT7, NT8> Tuple8<NT1, NT2, NT3, NT4, NT5, NT6, NT7, NT8> mapCombine(Func8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, Tuple8<NT1, NT2, NT3, NT4, NT5, NT6, NT7, NT8>> mapper) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return mapper.apply(_1, _2, _3, _4, _5, _6, _7, _8);
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
     * @param <NT5>    the type of the result from the mapper for the fifth element
     * @param <NT6>    the type of the result from the mapper for the sixth element
     * @param <NT7>    the type of the result from the mapper for the seventh element
     * @param <NT8>    the type of the result from the mapper for the eighth element
     * @param <T>      the final result type
     * 
     * @param mapper1  the function to transform the first element of the tuple
     * @param mapper2  the function to transform the second element of the tuple
     * @param mapper3  the function to transform the third element of the tuple
     * @param mapper4  the function to transform the fourth element of the tuple
     * @param mapper5  the function to transform the fifth element of the tuple
     * @param mapper6  the function to transform the sixth element of the tuple
     * @param mapper7  the function to transform the seventh element of the tuple
     * @param mapper8  the function to transform the eighth element of the tuple
     * 
     * @param mapper   the combining function applied to the results of individual mappers
     * @return         the final result of type T obtained after applying all the mapping and combining functions
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT6, NT7, NT8, T> T mapCombine(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8, Func8<? super NT1, ? super NT2, ? super NT3, ? super NT4, ? super NT5, ? super NT6, ? super NT7, ? super NT8, T> mapper) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        val n1 = mapper1.apply(_1);
        val n2 = mapper2.apply(_2);
        val n3 = mapper3.apply(_3);
        val n4 = mapper4.apply(_4);
        val n5 = mapper5.apply(_5);
        val n6 = mapper6.apply(_6);
        val n7 = mapper7.apply(_7);
        val n8 = mapper8.apply(_8);
        return mapper.apply(n1, n2, n3, n4, n5, n6, n7, n8);
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
    
    /**
     * Reduces the first five elements of this tuple to a single value using the provided reducer function.
     * The reducer function takes the first, second, third, fourth, and fifth elements of the tuple and combines them into a result of type TARGET.
     * 
     * @param <TARGET>  the type of the result after reduction
     * @param reducer   the function to combine the first five elements of the tuple
     * @return          the result of the reduction, of type TARGET
     */
    public default <TARGET> TARGET reduce(Func5<T1, T2, T3, T4, T5, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val target = reducer.apply(_1, _2, _3, _4, _5);
        return target;
    }
    
    /**
     * Reduces the first six elements of this tuple to a single value using the provided reducer function.
     * The reducer function takes the first, second, third, fourth, fifth, and sixth elements of the tuple and combines them into a result of type TARGET.
     * 
     * @param <TARGET>  the type of the result after reduction
     * @param reducer   the function to combine the first six elements of the tuple
     * @return          the result of the reduction, of type TARGET
     */
    public default <TARGET> TARGET reduce(Func6<T1, T2, T3, T4, T5, T6, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        val target = reducer.apply(_1, _2, _3, _4, _5, _6);
        return target;
    }
    
    /**
     * Reduces the first seven elements of this tuple to a single value using the provided reducer function.
     * The reducer function takes the first, second, third, fourth, fifth, sixth, and seventh elements of the tuple and combines them into a result of type TARGET.
     * 
     * @param <TARGET>  the type of the result after reduction
     * @param reducer   the function to combine the first seven elements of the tuple
     * @return          the result of the reduction, of type TARGET
     */
    public default <TARGET> TARGET reduce(Func7<T1, T2, T3, T4, T5, T6, T7, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        val _7 = _7();
        val target = reducer.apply(_1, _2, _3, _4, _5, _6, _7);
        return target;
    }
    
    /**
     * Reduces the first eight elements of this tuple to a single value using the provided reducer function.
     * The reducer function takes the first, second, third, fourth, fifth, sixth, seventh, and eighth elements of the tuple and combines them into a result of type TARGET.
     * 
     * @param <TARGET>  the type of the result after reduction
     * @param reducer   the function to combine the first eight elements of the tuple
     * @return          the result of the reduction, of type TARGET
     */
    public default <TARGET> TARGET reduce(Func8<T1, T2, T3, T4, T5, T6, T7, T8, TARGET> reducer) {
        val _1 = _1();
        val _2 = _2();
        val _3 = _3();
        val _4 = _4();
        val _5 = _5();
        val _6 = _6();
        val _7 = _7();
        val _8 = _8();
        val target = reducer.apply(_1, _2, _3, _4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1> Tuple8<NT1, T2, T3, T4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        return Tuple.of(new1, _2, _3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2> Tuple8<T1, NT2, T3, T4, T5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        return Tuple.of(_1, new2, _3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3> Tuple8<T1, T2, NT3, T4, T5, T6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        return Tuple.of(_1, _2, new3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4> Tuple8<T1, T2, T3, NT4, T5, T6, T7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, _2, _3, new4, _5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5> Tuple8<T1, T2, T3, T4, NT5, T6, T7, T8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, _2, _3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT6> Tuple8<T1, T2, T3, T4, T5, NT6, T7, T8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, _3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT7> Tuple8<T1, T2, T3, T4, T5, T6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT8> Tuple8<T1, T2, T3, T4, T5, T6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, _5, _6, _7, new8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2> Tuple8<NT1, NT2, T3, T4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        return Tuple.of(new1, new2, _3, _4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3> Tuple8<NT1, T2, NT3, T4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        return Tuple.of(new1, _2, new3, _4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3> Tuple8<T1, NT2, NT3, T4, T5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        return Tuple.of(_1, new2, new3, _4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4> Tuple8<NT1, T2, T3, NT4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, _2, _3, new4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4> Tuple8<T1, NT2, T3, NT4, T5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, new2, _3, new4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4> Tuple8<T1, T2, NT3, NT4, T5, T6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, _2, new3, new4, _5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5> Tuple8<NT1, T2, T3, T4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, _2, _3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5> Tuple8<T1, NT2, T3, T4, NT5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, new2, _3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5> Tuple8<T1, T2, NT3, T4, NT5, T6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, _2, new3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5> Tuple8<T1, T2, T3, NT4, NT5, T6, T7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, _2, _3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT6> Tuple8<NT1, T2, T3, T4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, _3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT6> Tuple8<T1, NT2, T3, T4, T5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, _3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT6> Tuple8<T1, T2, NT3, T4, T5, NT6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, new3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT6> Tuple8<T1, T2, T3, NT4, T5, NT6, T7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, _3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5, NT6> Tuple8<T1, T2, T3, T4, NT5, NT6, T7, T8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, _3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT7> Tuple8<NT1, T2, T3, T4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT7> Tuple8<T1, NT2, T3, T4, T5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT7> Tuple8<T1, T2, NT3, T4, T5, T6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT7> Tuple8<T1, T2, T3, NT4, T5, T6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5, NT7> Tuple8<T1, T2, T3, T4, NT5, T6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT6, NT7> Tuple8<T1, T2, T3, T4, T5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT8> Tuple8<NT1, T2, T3, T4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT8> Tuple8<T1, NT2, T3, T4, T5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT8> Tuple8<T1, T2, NT3, T4, T5, T6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT8> Tuple8<T1, T2, T3, NT4, T5, T6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5, NT8> Tuple8<T1, T2, T3, T4, NT5, T6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT6, NT8> Tuple8<T1, T2, T3, T4, T5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT7, NT8> Tuple8<T1, T2, T3, T4, T5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, _5, _6, new7, new8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3> Tuple8<NT1, NT2, NT3, T4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        return Tuple.of(new1, new2, new3, _4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4> Tuple8<NT1, NT2, T3, NT4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, new2, _3, new4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4> Tuple8<NT1, T2, NT3, NT4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, _2, new3, new4, _5, _6, _7, _8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4> Tuple8<T1, NT2, NT3, NT4, T5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(_1, new2, new3, new4, _5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5> Tuple8<NT1, NT2, T3, T4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, new2, _3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5> Tuple8<NT1, T2, NT3, T4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, _2, new3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5> Tuple8<T1, NT2, NT3, T4, NT5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, new2, new3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5> Tuple8<NT1, T2, T3, NT4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, _2, _3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5> Tuple8<T1, NT2, T3, NT4, NT5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, new2, _3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5> Tuple8<T1, T2, NT3, NT4, NT5, T6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, _2, new3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT6> Tuple8<NT1, NT2, T3, T4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, _3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT6> Tuple8<NT1, T2, NT3, T4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, new3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT6> Tuple8<T1, NT2, NT3, T4, T5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, new3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT6> Tuple8<NT1, T2, T3, NT4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, _3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT6> Tuple8<T1, NT2, T3, NT4, T5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, _3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT6> Tuple8<T1, T2, NT3, NT4, T5, NT6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, new3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5, NT6> Tuple8<NT1, T2, T3, T4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, _3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5, NT6> Tuple8<T1, NT2, T3, T4, NT5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, _3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5, NT6> Tuple8<T1, T2, NT3, T4, NT5, NT6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, new3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5, NT6> Tuple8<T1, T2, T3, NT4, NT5, NT6, T7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, _3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT7> Tuple8<NT1, NT2, T3, T4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT7> Tuple8<NT1, T2, NT3, T4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT7> Tuple8<T1, NT2, NT3, T4, T5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT7> Tuple8<NT1, T2, T3, NT4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT7> Tuple8<T1, NT2, T3, NT4, T5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT7> Tuple8<T1, T2, NT3, NT4, T5, T6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5, NT7> Tuple8<NT1, T2, T3, T4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5, NT7> Tuple8<T1, NT2, T3, T4, NT5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5, NT7> Tuple8<T1, T2, NT3, T4, NT5, T6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5, NT7> Tuple8<T1, T2, T3, NT4, NT5, T6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT6, NT7> Tuple8<NT1, T2, T3, T4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT6, NT7> Tuple8<T1, NT2, T3, T4, T5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT6, NT7> Tuple8<T1, T2, NT3, T4, T5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT6, NT7> Tuple8<T1, T2, T3, NT4, T5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5, NT6, NT7> Tuple8<T1, T2, T3, T4, NT5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT8> Tuple8<NT1, NT2, T3, T4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT8> Tuple8<NT1, T2, NT3, T4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT8> Tuple8<T1, NT2, NT3, T4, T5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT8> Tuple8<NT1, T2, T3, NT4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT8> Tuple8<T1, NT2, T3, NT4, T5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT8> Tuple8<T1, T2, NT3, NT4, T5, T6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5, NT8> Tuple8<NT1, T2, T3, T4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5, NT8> Tuple8<T1, NT2, T3, T4, NT5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5, NT8> Tuple8<T1, T2, NT3, T4, NT5, T6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5, NT8> Tuple8<T1, T2, T3, NT4, NT5, T6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT6, NT8> Tuple8<NT1, T2, T3, T4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT6, NT8> Tuple8<T1, NT2, T3, T4, T5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT6, NT8> Tuple8<T1, T2, NT3, T4, T5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT6, NT8> Tuple8<T1, T2, T3, NT4, T5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5, NT6, NT8> Tuple8<T1, T2, T3, T4, NT5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT7, NT8> Tuple8<NT1, T2, T3, T4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT7, NT8> Tuple8<T1, NT2, T3, T4, T5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT7, NT8> Tuple8<T1, T2, NT3, T4, T5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT7, NT8> Tuple8<T1, T2, T3, NT4, T5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5, NT7, NT8> Tuple8<T1, T2, T3, T4, NT5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT6, NT7, NT8> Tuple8<T1, T2, T3, T4, T5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, _5, new6, new7, new8);
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
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4> Tuple8<NT1, NT2, NT3, NT4, T5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        return Tuple.of(new1, new2, new3, new4, _5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5> Tuple8<NT1, NT2, NT3, T4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, new2, new3, _4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5> Tuple8<NT1, NT2, T3, NT4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, new2, _3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5> Tuple8<NT1, T2, NT3, NT4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, _2, new3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5> Tuple8<T1, NT2, NT3, NT4, NT5, T6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(_1, new2, new3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT6> Tuple8<NT1, NT2, NT3, T4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, new3, _4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT6> Tuple8<NT1, NT2, T3, NT4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, _3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT6> Tuple8<NT1, T2, NT3, NT4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, new3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT6> Tuple8<T1, NT2, NT3, NT4, T5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, new3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5, NT6> Tuple8<NT1, NT2, T3, T4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, _3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5, NT6> Tuple8<NT1, T2, NT3, T4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, new3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5, NT6> Tuple8<T1, NT2, NT3, T4, NT5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, new3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5, NT6> Tuple8<NT1, T2, T3, NT4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, _3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5, NT6> Tuple8<T1, NT2, T3, NT4, NT5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, _3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5, NT6> Tuple8<T1, T2, NT3, NT4, NT5, NT6, T7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, _2, new3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT7> Tuple8<NT1, NT2, NT3, T4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, _4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT7> Tuple8<NT1, NT2, T3, NT4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT7> Tuple8<NT1, T2, NT3, NT4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT7> Tuple8<T1, NT2, NT3, NT4, T5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5, NT7> Tuple8<NT1, NT2, T3, T4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5, NT7> Tuple8<NT1, T2, NT3, T4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5, NT7> Tuple8<T1, NT2, NT3, T4, NT5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5, NT7> Tuple8<NT1, T2, T3, NT4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5, NT7> Tuple8<T1, NT2, T3, NT4, NT5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5, NT7> Tuple8<T1, T2, NT3, NT4, NT5, T6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT6, NT7> Tuple8<NT1, NT2, T3, T4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT6, NT7> Tuple8<NT1, T2, NT3, T4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT6, NT7> Tuple8<T1, NT2, NT3, T4, T5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT6, NT7> Tuple8<NT1, T2, T3, NT4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT6, NT7> Tuple8<T1, NT2, T3, NT4, T5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT6, NT7> Tuple8<T1, T2, NT3, NT4, T5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5, NT6, NT7> Tuple8<NT1, T2, T3, T4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5, NT6, NT7> Tuple8<T1, NT2, T3, T4, NT5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5, NT6, NT7> Tuple8<T1, T2, NT3, T4, NT5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5, NT6, NT7> Tuple8<T1, T2, T3, NT4, NT5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, _3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT8> Tuple8<NT1, NT2, NT3, T4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT8> Tuple8<NT1, NT2, T3, NT4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT8> Tuple8<NT1, T2, NT3, NT4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT8> Tuple8<T1, NT2, NT3, NT4, T5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5, NT8> Tuple8<NT1, NT2, T3, T4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5, NT8> Tuple8<NT1, T2, NT3, T4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5, NT8> Tuple8<T1, NT2, NT3, T4, NT5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5, NT8> Tuple8<NT1, T2, T3, NT4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5, NT8> Tuple8<T1, NT2, T3, NT4, NT5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5, NT8> Tuple8<T1, T2, NT3, NT4, NT5, T6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT6, NT8> Tuple8<NT1, NT2, T3, T4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT6, NT8> Tuple8<NT1, T2, NT3, T4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT6, NT8> Tuple8<T1, NT2, NT3, T4, T5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT6, NT8> Tuple8<NT1, T2, T3, NT4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT6, NT8> Tuple8<T1, NT2, T3, NT4, T5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT6, NT8> Tuple8<T1, T2, NT3, NT4, T5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5, NT6, NT8> Tuple8<NT1, T2, T3, T4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5, NT6, NT8> Tuple8<T1, NT2, T3, T4, NT5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5, NT6, NT8> Tuple8<T1, T2, NT3, T4, NT5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5, NT6, NT8> Tuple8<T1, T2, T3, NT4, NT5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT7, NT8> Tuple8<NT1, NT2, T3, T4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT7, NT8> Tuple8<NT1, T2, NT3, T4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT7, NT8> Tuple8<T1, NT2, NT3, T4, T5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT7, NT8> Tuple8<NT1, T2, T3, NT4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT7, NT8> Tuple8<T1, NT2, T3, NT4, T5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT7, NT8> Tuple8<T1, T2, NT3, NT4, T5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5, NT7, NT8> Tuple8<NT1, T2, T3, T4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5, NT7, NT8> Tuple8<T1, NT2, T3, T4, NT5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5, NT7, NT8> Tuple8<T1, T2, NT3, T4, NT5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5, NT7, NT8> Tuple8<T1, T2, T3, NT4, NT5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT6, NT7, NT8> Tuple8<NT1, T2, T3, T4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT6, NT7, NT8> Tuple8<T1, NT2, T3, T4, T5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT6, NT7, NT8> Tuple8<T1, T2, NT3, T4, T5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT6, NT7, NT8> Tuple8<T1, T2, T3, NT4, T5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT5, NT6, NT7, NT8> Tuple8<T1, T2, T3, T4, NT5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5> Tuple8<NT1, NT2, NT3, NT4, NT5, T6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        return Tuple.of(new1, new2, new3, new4, new5, _6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT6> Tuple8<NT1, NT2, NT3, NT4, T5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, new3, new4, _5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5, NT6> Tuple8<NT1, NT2, NT3, T4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, new3, _4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5, NT6> Tuple8<NT1, NT2, T3, NT4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, _3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5, NT6> Tuple8<NT1, T2, NT3, NT4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, _2, new3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5, NT6> Tuple8<T1, NT2, NT3, NT4, NT5, NT6, T7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(_1, new2, new3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT7> Tuple8<NT1, NT2, NT3, NT4, T5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, new4, _5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5, NT7> Tuple8<NT1, NT2, NT3, T4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, _4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5, NT7> Tuple8<NT1, NT2, T3, NT4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5, NT7> Tuple8<NT1, T2, NT3, NT4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5, NT7> Tuple8<T1, NT2, NT3, NT4, NT5, T6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT6, NT7> Tuple8<NT1, NT2, NT3, T4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, _4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT6, NT7> Tuple8<NT1, NT2, T3, NT4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT6, NT7> Tuple8<NT1, T2, NT3, NT4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT6, NT7> Tuple8<T1, NT2, NT3, NT4, T5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5, NT6, NT7> Tuple8<NT1, NT2, T3, T4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5, NT6, NT7> Tuple8<NT1, T2, NT3, T4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5, NT6, NT7> Tuple8<T1, NT2, NT3, T4, NT5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5, NT6, NT7> Tuple8<NT1, T2, T3, NT4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, _3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5, NT6, NT7> Tuple8<T1, NT2, T3, NT4, NT5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, _3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5, NT6, NT7> Tuple8<T1, T2, NT3, NT4, NT5, NT6, NT7, T8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, _2, new3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT8> Tuple8<NT1, NT2, NT3, NT4, T5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, _5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5, NT8> Tuple8<NT1, NT2, NT3, T4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5, NT8> Tuple8<NT1, NT2, T3, NT4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5, NT8> Tuple8<NT1, T2, NT3, NT4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5, NT8> Tuple8<T1, NT2, NT3, NT4, NT5, T6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT6, NT8> Tuple8<NT1, NT2, NT3, T4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT6, NT8> Tuple8<NT1, NT2, T3, NT4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT6, NT8> Tuple8<NT1, T2, NT3, NT4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT6, NT8> Tuple8<T1, NT2, NT3, NT4, T5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5, NT6, NT8> Tuple8<NT1, NT2, T3, T4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5, NT6, NT8> Tuple8<NT1, T2, NT3, T4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5, NT6, NT8> Tuple8<T1, NT2, NT3, T4, NT5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5, NT6, NT8> Tuple8<NT1, T2, T3, NT4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5, NT6, NT8> Tuple8<T1, NT2, T3, NT4, NT5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5, NT6, NT8> Tuple8<T1, T2, NT3, NT4, NT5, NT6, T7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT7, NT8> Tuple8<NT1, NT2, NT3, T4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT7, NT8> Tuple8<NT1, NT2, T3, NT4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT7, NT8> Tuple8<NT1, T2, NT3, NT4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT7, NT8> Tuple8<T1, NT2, NT3, NT4, T5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5, NT7, NT8> Tuple8<NT1, NT2, T3, T4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5, NT7, NT8> Tuple8<NT1, T2, NT3, T4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5, NT7, NT8> Tuple8<T1, NT2, NT3, T4, NT5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5, NT7, NT8> Tuple8<NT1, T2, T3, NT4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5, NT7, NT8> Tuple8<T1, NT2, T3, NT4, NT5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5, NT7, NT8> Tuple8<T1, T2, NT3, NT4, NT5, T6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT6, NT7, NT8> Tuple8<NT1, NT2, T3, T4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT6, NT7, NT8> Tuple8<NT1, T2, NT3, T4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT6, NT7, NT8> Tuple8<T1, NT2, NT3, T4, T5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT6, NT7, NT8> Tuple8<NT1, T2, T3, NT4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT6, NT7, NT8> Tuple8<T1, NT2, T3, NT4, T5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT6, NT7, NT8> Tuple8<T1, T2, NT3, NT4, T5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT5, NT6, NT7, NT8> Tuple8<NT1, T2, T3, T4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT5, NT6, NT7, NT8> Tuple8<T1, NT2, T3, T4, NT5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT5, NT6, NT7, NT8> Tuple8<T1, T2, NT3, T4, NT5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT4, NT5, NT6, NT7, NT8> Tuple8<T1, T2, T3, NT4, NT5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, _3, new4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT6> Tuple8<NT1, NT2, NT3, NT4, NT5, NT6, T7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        return Tuple.of(new1, new2, new3, new4, new5, new6, _7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT7> Tuple8<NT1, NT2, NT3, NT4, NT5, T6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, new4, new5, _6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT6, NT7> Tuple8<NT1, NT2, NT3, NT4, T5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, new4, _5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5, NT6, NT7> Tuple8<NT1, NT2, NT3, T4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, _4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5, NT6, NT7> Tuple8<NT1, NT2, T3, NT4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, _3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5, NT6, NT7> Tuple8<NT1, T2, NT3, NT4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, _2, new3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5, NT6, NT7> Tuple8<T1, NT2, NT3, NT4, NT5, NT6, NT7, T8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(_1, new2, new3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT8> Tuple8<NT1, NT2, NT3, NT4, NT5, T6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, new5, _6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT6, NT8> Tuple8<NT1, NT2, NT3, NT4, T5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, _5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5, NT6, NT8> Tuple8<NT1, NT2, NT3, T4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5, NT6, NT8> Tuple8<NT1, NT2, T3, NT4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5, NT6, NT8> Tuple8<NT1, T2, NT3, NT4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5, NT6, NT8> Tuple8<T1, NT2, NT3, NT4, NT5, NT6, T7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT7, NT8> Tuple8<NT1, NT2, NT3, NT4, T5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, _5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5, NT7, NT8> Tuple8<NT1, NT2, NT3, T4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5, NT7, NT8> Tuple8<NT1, NT2, T3, NT4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5, NT7, NT8> Tuple8<NT1, T2, NT3, NT4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5, NT7, NT8> Tuple8<T1, NT2, NT3, NT4, NT5, T6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT6, NT7, NT8> Tuple8<NT1, NT2, NT3, T4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT6, NT7, NT8> Tuple8<NT1, NT2, T3, NT4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT6, NT7, NT8> Tuple8<NT1, T2, NT3, NT4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT6, NT7, NT8> Tuple8<T1, NT2, NT3, NT4, T5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT5, NT6, NT7, NT8> Tuple8<NT1, NT2, T3, T4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT5, NT6, NT7, NT8> Tuple8<NT1, T2, NT3, T4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT5, NT6, NT7, NT8> Tuple8<T1, NT2, NT3, T4, NT5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT4, NT5, NT6, NT7, NT8> Tuple8<NT1, T2, T3, NT4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, _3, new4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT4, NT5, NT6, NT7, NT8> Tuple8<T1, NT2, T3, NT4, NT5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, _3, new4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT3, NT4, NT5, NT6, NT7, NT8> Tuple8<T1, T2, NT3, NT4, NT5, NT6, NT7, NT8> map(Keep keep1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, _2, new3, new4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param keep8    the placeholder for the original value of the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT6, NT7> Tuple8<NT1, NT2, NT3, NT4, NT5, NT6, NT7, T8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Keep keep8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        return Tuple.of(new1, new2, new3, new4, new5, new6, new7, _8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param keep7    the placeholder for the original value of the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT6, NT8> Tuple8<NT1, NT2, NT3, NT4, NT5, NT6, T7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Keep keep7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, new5, new6, _7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param keep6    the placeholder for the original value of the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT7, NT8> Tuple8<NT1, NT2, NT3, NT4, NT5, T6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Keep keep6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, new5, _6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param keep5    the placeholder for the original value of the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT6, NT7, NT8> Tuple8<NT1, NT2, NT3, NT4, T5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Keep keep5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, _5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param keep4    the placeholder for the original value of the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT5, NT6, NT7, NT8> Tuple8<NT1, NT2, NT3, T4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Keep keep4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, _4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param keep3    the placeholder for the original value of the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT4, NT5, NT6, NT7, NT8> Tuple8<NT1, NT2, T3, NT4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Keep keep3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, _3, new4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param keep2    the placeholder for the original value of the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT3, NT4, NT5, NT6, NT7, NT8> Tuple8<NT1, T2, NT3, NT4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Keep keep2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, _2, new3, new4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param keep1    the placeholder for the original value of the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT2, NT3, NT4, NT5, NT6, NT7, NT8> Tuple8<T1, NT2, NT3, NT4, NT5, NT6, NT7, NT8> map(Keep keep1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(_1, new2, new3, new4, new5, new6, new7, new8);
    }
    
    /**
     * Maps the elements of this Tuple to a new Tuple using the provided mappers.
     * 
     * @param <NT1>    the type to which the first element should be mapped.
     * @param <NT2>    the type to which the second element should be mapped.
     * @param <NT3>    the type to which the third element should be mapped.
     * @param <NT4>    the type to which the forth element should be mapped.
     * @param <NT5>    the type to which the fifth element should be mapped.
     * @param <NT6>    the type to which the sixth element should be mapped.
     * @param <NT7>    the type to which the seventh element should be mapped.
     * @param <NT8>    the type to which the eighth element should be mapped.
     * @param mapper1  a function to map the first element.
     * @param mapper2  a function to map the second element.
     * @param mapper3  a function to map the third element.
     * @param mapper4  a function to map the forth element.
     * @param mapper5  a function to map the fifth element.
     * @param mapper6  a function to map the sixth element.
     * @param mapper7  a function to map the seventh element.
     * @param mapper8  a function to map the eighth element.
     * @return         a new Tuple containing the elements mapped by the provided functions.
     */
    public default <NT1, NT2, NT3, NT4, NT5, NT6, NT7, NT8> Tuple8<NT1, NT2, NT3, NT4, NT5, NT6, NT7, NT8> map(Function<? super T1, NT1> mapper1, Function<? super T2, NT2> mapper2, Function<? super T3, NT3> mapper3, Function<? super T4, NT4> mapper4, Function<? super T5, NT5> mapper5, Function<? super T6, NT6> mapper6, Function<? super T7, NT7> mapper7, Function<? super T8, NT8> mapper8) {
        val _1   = _1();
        val _2   = _2();
        val _3   = _3();
        val _4   = _4();
        val _5   = _5();
        val _6   = _6();
        val _7   = _7();
        val _8   = _8();
        val new1 = mapper1.apply(_1);
        val new2 = mapper2.apply(_2);
        val new3 = mapper3.apply(_3);
        val new4 = mapper4.apply(_4);
        val new5 = mapper5.apply(_5);
        val new6 = mapper6.apply(_6);
        val new7 = mapper7.apply(_7);
        val new8 = mapper8.apply(_8);
        return Tuple.of(new1, new2, new3, new4, new5, new6, new7, new8);
    }
    
    // == drop ==
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T2> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T3> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T3> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T4> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T4> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T3, T4> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        return Tuple.of(_3, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T5> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _5  = _5();
        return Tuple.of(_1, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T5> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _5  = _5();
        return Tuple.of(_2, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T3, T5> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _3  = _3();
        val _5  = _5();
        return Tuple.of(_3, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T4, T5> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T6> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _6  = _6();
        return Tuple.of(_1, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T6> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _6  = _6();
        return Tuple.of(_2, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T3, T6> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _3  = _3();
        val _6  = _6();
        return Tuple.of(_3, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T4, T6> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T5, T6> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T7> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _7  = _7();
        return Tuple.of(_1, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T7> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _7  = _7();
        return Tuple.of(_2, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T3, T7> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _7  = _7();
        return Tuple.of(_3, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T4, T7> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T5, T7> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T6, T7> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T1, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _8  = _8();
        return Tuple.of(_1, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T2, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _8  = _8();
        return Tuple.of(_2, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T3, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _8  = _8();
        return Tuple.of(_3, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T4, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T5, T8> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T6, T8> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple2<T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T3> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T4> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T3, T4> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
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
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T3, T4> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        return Tuple.of(_2, _3, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T5> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        return Tuple.of(_1, _2, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T3, T5> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        return Tuple.of(_1, _3, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T3, T5> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        return Tuple.of(_2, _3, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T4, T5> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_1, _4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T4, T5> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_2, _4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T4, T5> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_3, _4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T6> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _6  = _6();
        return Tuple.of(_1, _2, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T3, T6> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _6  = _6();
        return Tuple.of(_1, _3, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T3, T6> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        return Tuple.of(_2, _3, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T4, T6> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_1, _4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T4, T6> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_2, _4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T4, T6> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_3, _4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T5, T6> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T5, T6> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_2, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T5, T6> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_3, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T4, T5, T6> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T7> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _7  = _7();
        return Tuple.of(_1, _2, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T3, T7> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _7  = _7();
        return Tuple.of(_1, _3, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T3, T7> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _7  = _7();
        return Tuple.of(_2, _3, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T4, T7> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_1, _4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T4, T7> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_2, _4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T4, T7> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_3, _4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T5, T7> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T5, T7> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_2, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T5, T7> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_3, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T4, T5, T7> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T6, T7> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T6, T7> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T6, T7> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_3, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T4, T6, T7> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T5, T6, T7> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T2, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _8  = _8();
        return Tuple.of(_1, _2, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T3, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _8  = _8();
        return Tuple.of(_1, _3, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T3, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _8  = _8();
        return Tuple.of(_2, _3, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T4, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_1, _4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T4, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_2, _4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T4, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_3, _4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T5, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T5, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_2, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T5, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_3, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T4, T5, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T6, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T6, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T6, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_3, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T4, T6, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T5, T6, T8> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T1, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T2, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T3, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T4, T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T5, T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple3<T6, T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T3, T4> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        return Tuple.of(_1, _2, _3, _4);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T3, T5> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        return Tuple.of(_1, _2, _3, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T4, T5> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_1, _2, _4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T4, T5> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_1, _3, _4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T4, T5> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_2, _3, _4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T3, T6> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        return Tuple.of(_1, _2, _3, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T4, T6> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_1, _2, _4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T4, T6> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_1, _3, _4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T4, T6> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_2, _3, _4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T5, T6> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _2, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T5, T6> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _3, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T5, T6> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_2, _3, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T4, T5, T6> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T4, T5, T6> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_2, _4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T4, T5, T6> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_3, _4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T3, T7> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T4, T7> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_1, _2, _4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T4, T7> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_1, _3, _4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T4, T7> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_2, _3, _4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T5, T7> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _2, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T5, T7> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _3, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T5, T7> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_2, _3, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T4, T5, T7> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T4, T5, T7> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_2, _4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T4, T5, T7> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_3, _4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T6, T7> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T6, T7> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _3, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T6, T7> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _3, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T4, T6, T7> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T4, T6, T7> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T4, T6, T7> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_3, _4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T5, T6, T7> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T5, T6, T7> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T5, T6, T7> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_3, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T4, T5, T6, T7> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T3, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T4, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T4, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T4, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T5, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _2, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T5, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _3, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T5, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_2, _3, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T4, T5, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T4, T5, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_2, _4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T4, T5, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_3, _4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T6, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T6, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _3, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T6, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _3, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T4, T6, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T4, T6, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T4, T6, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_3, _4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T5, T6, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T5, T6, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T5, T6, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_3, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T4, T5, T6, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T2, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T3, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T3, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T4, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T4, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T4, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T5, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T5, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T5, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T4, T5, T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T1, T6, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T2, T6, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T3, T6, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T4, T6, T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple4<T5, T6, T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T4, T5> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        return Tuple.of(_1, _2, _3, _4, _5);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T4, T6> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        return Tuple.of(_1, _2, _3, _4, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T5, T6> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _2, _3, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T4, T5, T6> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _2, _4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T4, T5, T6> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _3, _4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T4, T5, T6> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_2, _3, _4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T4, T7> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _4, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T5, T7> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T4, T5, T7> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _2, _4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T4, T5, T7> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _3, _4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T4, T5, T7> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_2, _3, _4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T6, T7> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T4, T6, T7> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T4, T6, T7> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _3, _4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T4, T6, T7> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _3, _4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T5, T6, T7> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T5, T6, T7> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _3, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T5, T6, T7> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _3, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T4, T5, T6, T7> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T4, T5, T6, T7> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T3, T4, T5, T6, T7> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_3, _4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T4, T8> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _4, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T5, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T4, T5, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T4, T5, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T4, T5, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T6, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T4, T6, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T4, T6, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T4, T6, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T5, T6, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T5, T6, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _3, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T5, T6, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _3, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T4, T5, T6, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T4, T5, T6, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T3, T4, T5, T6, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_3, _4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T3, T7, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T4, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T4, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T4, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T5, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T5, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T5, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T4, T5, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T4, T5, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T3, T4, T5, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T2, T6, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T3, T6, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T3, T6, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T4, T6, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T4, T6, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T3, T4, T6, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T1, T5, T6, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T2, T5, T6, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T3, T5, T6, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple5<T4, T5, T6, T7, T8> drop(Drop drop1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_4, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T4, T5, T6> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        return Tuple.of(_1, _2, _3, _4, _5, _6);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T4, T5, T7> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _4, _5, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T4, T6, T7> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _4, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T5, T6, T7> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T4, T5, T6, T7> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T3, T4, T5, T6, T7> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _3, _4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T2, T3, T4, T5, T6, T7> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_2, _3, _4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T4, T5, T8> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _4, _5, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T4, T6, T8> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _4, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T5, T6, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T4, T5, T6, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T3, T4, T5, T6, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T2, T3, T4, T5, T6, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T4, T7, T8> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _4, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T5, T7, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T4, T5, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T3, T4, T5, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T2, T3, T4, T5, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T3, T6, T7, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T4, T6, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T3, T4, T6, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T2, T3, T4, T6, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T2, T5, T6, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T3, T5, T6, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T2, T3, T5, T6, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T1, T4, T5, T6, T7, T8> drop(Keep keep1, Drop drop2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _4, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T2, T4, T5, T6, T7, T8> drop(Drop drop1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _4, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple6<T3, T4, T5, T6, T7, T8> drop(Drop drop1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param drop8  flag to drop the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T1, T2, T3, T4, T5, T6, T7> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Drop drop8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        return Tuple.of(_1, _2, _3, _4, _5, _6, _7);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param drop7  flag to drop the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T1, T2, T3, T4, T5, T6, T8> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Drop drop7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _4, _5, _6, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param drop6  flag to drop the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T1, T2, T3, T4, T5, T7, T8> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Drop drop6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _4, _5, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param drop5  flag to drop the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T1, T2, T3, T4, T6, T7, T8> drop(Keep keep1, Keep keep2, Keep keep3, Keep keep4, Drop drop5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _4, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param drop4  flag to drop the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T1, T2, T3, T5, T6, T7, T8> drop(Keep keep1, Keep keep2, Keep keep3, Drop drop4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _3  = _3();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _3, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param keep2  flag to keep the second element
     * @param drop3  flag to drop the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T1, T2, T4, T5, T6, T7, T8> drop(Keep keep1, Keep keep2, Drop drop3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _2  = _2();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _2, _4, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param keep1  flag to keep the first element
     * @param drop2  flag to drop the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T1, T3, T4, T5, T6, T7, T8> drop(Keep keep1, Drop drop2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _1  = _1();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_1, _3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Drops specified elements from this tuple, returning a new tuple with the remaining elements.
     * 
     * @param drop1  flag to drop the first element
     * @param keep2  flag to keep the second element
     * @param keep3  flag to keep the third element
     * @param keep4  flag to keep the forth element
     * @param keep5  flag to keep the fifth element
     * @param keep6  flag to keep the sixth element
     * @param keep7  flag to keep the seventh element
     * @param keep8  flag to keep the eighth element
     * @return       a new Tuple containing the elements that were kept
     */
    public default Tuple7<T2, T3, T4, T5, T6, T7, T8> drop(Drop drop1, Keep keep2, Keep keep3, Keep keep4, Keep keep5, Keep keep6, Keep keep7, Keep keep8) {
        val _2  = _2();
        val _3  = _3();
        val _4  = _4();
        val _5  = _5();
        val _6  = _6();
        val _7  = _7();
        val _8  = _8();
        return Tuple.of(_2, _3, _4, _5, _6, _7, _8);
    }
    
}
