// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Map;
import java.util.function.Function;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.ObjectLens;

/**
 * Provides utility methods for creating tuple objects of various sizes.
 * This class cannot be instantiated and contains static methods only.
 */
public class Tuple {
    
    //-- TupleX --
    
    /**
     * Creates a new {@link ImmutableTuple2} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * 
     * @return a new {@link ImmutableTuple2} object containing the provided elements
     */
    public static <T1, T2> ImmutableTuple2<T1, T2> of(T1 _1, T2 _2) {
        return tuple2(_1, _2);
    }
    
    /**
     * Creates a new {@link ImmutableTuple2} object from a Map.Entry object.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param entry  the {@link java.util.Map.Entry} object containing the elements to be placed in the tuple
     * @return  a new {@link ImmutableTuple2} object containing the key and value from the {@link java.util.Map.Entry} object, 
     *            or a tuple with null values if the entry is null
     */
    public static <T1, T2> ImmutableTuple2<T1, T2> of(Map.Entry<? extends T1, ? extends T2> entry) {
        return tuple2(entry);
    }
    
    /**
     * Creates a new {@link ImmutableTuple3} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * 
     * @return a new {@link ImmutableTuple3} object containing the provided elements
     */
    public static <T1, T2, T3> ImmutableTuple3<T1, T2, T3> of(T1 _1, T2 _2, T3 _3) {
        return tuple3(_1, _2, _3);
    }
    
    /**
     * Creates a new {@link ImmutableTuple4} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * 
     * @return a new {@link ImmutableTuple4} object containing the provided elements
     */
    public static <T1, T2, T3, T4> ImmutableTuple4<T1, T2, T3, T4> of(T1 _1, T2 _2, T3 _3, T4 _4) {
        return tuple4(_1, _2, _3, _4);
    }
    
    /**
     * Creates a new {@link ImmutableTuple5} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * 
     * @return a new {@link ImmutableTuple5} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5> ImmutableTuple5<T1, T2, T3, T4, T5> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return tuple5(_1, _2, _3, _4, _5);
    }
    
    /**
     * Creates a new {@link ImmutableTuple6} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * 
     * @return a new {@link ImmutableTuple6} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6> ImmutableTuple6<T1, T2, T3, T4, T5, T6> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return tuple6(_1, _2, _3, _4, _5, _6);
    }
    
    /**
     * Creates a new {@link ImmutableTuple7} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * @param <T7>   the type of the seventh element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * 
     * @return a new {@link ImmutableTuple7} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7> ImmutableTuple7<T1, T2, T3, T4, T5, T6, T7> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        return new ImmutableTuple7<>(_1, _2, _3, _4, _5, _6, _7);
    }
    
    /**
     * Creates a new {@link ImmutableTuple8} object from ten provided elements.
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
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * @param _8     the eighth element of the tuple
     * 
     * @return a new {@link ImmutableTuple8} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> ImmutableTuple8<T1, T2, T3, T4, T5, T6, T7, T8> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        return new ImmutableTuple8<>(_1, _2, _3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Creates a new {@link ImmutableTuple9} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * @param <T7>   the type of the seventh element
     * @param <T8>   the type of the eighth element
     * @param <T9>   the type of the ninth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * @param _8     the eighth element of the tuple
     * @param _9     the ninth element of the tuple
     * 
     * @return a new {@link ImmutableTuple9} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ImmutableTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        return new ImmutableTuple9<>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }
    
    /**
     * Creates a new {@link ImmutableTuple10} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * @param <T7>   the type of the seventh element
     * @param <T8>   the type of the eighth element
     * @param <T9>   the type of the ninth element
     * @param <T10>  the type of the tenth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * @param _8     the eighth element of the tuple
     * @param _9     the ninth element of the tuple
     * @param _10    the tenth element of the tuple
     * 
     * @return a new {@link ImmutableTuple10} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ImmutableTuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    //-- TupleX --
    
    /**
     * Creates a new {@link ImmutableTuple2} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * 
     * @return a new {@link ImmutableTuple2} object containing the provided elements
     */
    public static <T1, T2> ImmutableTuple2<T1, T2> tuple2(T1 _1, T2 _2) {
        return new ImmutableTuple2<>(_1, _2);
    }
    
    /**
     * Creates a new {@link ImmutableTuple2} object from a Map.Entry object.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param entry  the {@link java.util.Map.Entry} object containing the elements to be placed in the tuple
     * @return  a new {@link ImmutableTuple2} object containing the key and value from the {@link java.util.Map.Entry} object, 
     *            or a tuple with null values if the entry is null
     */
    public static <T1, T2> ImmutableTuple2<T1, T2> tuple2(Map.Entry<? extends T1, ? extends T2> entry) {
        if (entry == null)
            return new ImmutableTuple2<>(null, null);
        return new ImmutableTuple2<>(entry);
    }
    
    /**
     * Creates a new {@link ImmutableTuple3} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * 
     * @return a new {@link ImmutableTuple3} object containing the provided elements
     */
    public static <T1, T2, T3> ImmutableTuple3<T1, T2, T3> tuple3(T1 _1, T2 _2, T3 _3) {
        return new ImmutableTuple3<>(_1, _2, _3);
    }
    
    /**
     * Creates a new {@link ImmutableTuple4} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * 
     * @return a new {@link ImmutableTuple4} object containing the provided elements
     */
    public static <T1, T2, T3, T4> ImmutableTuple4<T1, T2, T3, T4> tuple4(T1 _1, T2 _2, T3 _3, T4 _4) {
        return new ImmutableTuple4<>(_1, _2, _3, _4);
    }
    
    /**
     * Creates a new {@link ImmutableTuple5} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * 
     * @return a new {@link ImmutableTuple5} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5> ImmutableTuple5<T1, T2, T3, T4, T5> tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new ImmutableTuple5<>(_1, _2, _3, _4, _5);
    }
    
    /**
     * Creates a new {@link ImmutableTuple6} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * 
     * @return a new {@link ImmutableTuple6} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6> ImmutableTuple6<T1, T2, T3, T4, T5, T6> tuple6(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return new ImmutableTuple6<>(_1, _2, _3, _4, _5, _6);
    }
    
    /**
     * Creates a new {@link ImmutableTuple7} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * @param <T7>   the type of the seventh element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * 
     * @return a new {@link ImmutableTuple7} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7> ImmutableTuple7<T1, T2, T3, T4, T5, T6, T7> tuple7(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        return new ImmutableTuple7<>(_1, _2, _3, _4, _5, _6, _7);
    }
    
    /**
     * Creates a new {@link ImmutableTuple8} object from ten provided elements.
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
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * @param _8     the eighth element of the tuple
     * 
     * @return a new {@link ImmutableTuple8} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> ImmutableTuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple8(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        return new ImmutableTuple8<>(_1, _2, _3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Creates a new {@link ImmutableTuple9} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * @param <T7>   the type of the seventh element
     * @param <T8>   the type of the eighth element
     * @param <T9>   the type of the ninth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * @param _8     the eighth element of the tuple
     * @param _9     the ninth element of the tuple
     * 
     * @return a new {@link ImmutableTuple9} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ImmutableTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple9(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        return new ImmutableTuple9<>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }
    
    /**
     * Creates a new {@link ImmutableTuple10} object from ten provided elements.
     *
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @param <T5>   the type of the fifth element
     * @param <T6>   the type of the sixth element
     * @param <T7>   the type of the seventh element
     * @param <T8>   the type of the eighth element
     * @param <T9>   the type of the ninth element
     * @param <T10>  the type of the tenth element
     * 
     * @param _1     the first element of the tuple
     * @param _2     the second element of the tuple
     * @param _3     the third element of the tuple
     * @param _4     the fourth element of the tuple
     * @param _5     the fifth element of the tuple
     * @param _6     the sixth element of the tuple
     * @param _7     the seventh element of the tuple
     * @param _8     the eighth element of the tuple
     * @param _9     the ninth element of the tuple
     * @param _10    the tenth element of the tuple
     * 
     * @return a new {@link ImmutableTuple10} object containing the provided elements
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ImmutableTuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple10(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    // == Use ==
    
    //-- Tuple2 --
    
    /**
     * Retrieves the first element from a {@link Tuple2} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param tuple the {@link Tuple2} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2> T1 _1(Tuple2<T1, T2> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple2} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param tuple the {@link Tuple2} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2> T2 _2(Tuple2<T1, T2> tuple) {
        return tuple._2();
    }
    
    //-- Tuple3 --
    
    /**
     * Retrieves the first element from a {@link Tuple3} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param tuple the {@link Tuple3} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3> T1 _1(Tuple3<T1, T2, T3> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple3} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param tuple the {@link Tuple3} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3> T2 _2(Tuple3<T1, T2, T3> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple3} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param tuple the {@link Tuple3} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3> T3 _3(Tuple3<T1, T2, T3> tuple) {
        return tuple._3();
    }
    
    //-- Tuple4 --
    
    /**
     * Retrieves the first element from a {@link Tuple4} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param tuple the {@link Tuple4} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3, T4> T1 _1(Tuple4<T1, T2, T3, T4> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple4} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param tuple the {@link Tuple4} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3, T4> T2 _2(Tuple4<T1, T2, T3, T4> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple4} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param tuple the {@link Tuple4} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3, T4> T3 _3(Tuple4<T1, T2, T3, T4> tuple) {
        return tuple._3();
    }
    
    /**
     * Retrieves the forth element fromTuple4ink Tuple4} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param tuple the {@link Tuple4} object from which the forth element is to be retrieved
     * @return the forth element of the tuple
     */
    public static <T1, T2, T3, T4> T4 _4(Tuple4<T1, T2, T3, T4> tuple) {
        return tuple._4();
    }
    
    //-- Tuple5 --
    
    /**
     * Retrieves the first element from a {@link Tuple5} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the {@link Tuple5} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3, T4, T5> T1 _1(Tuple5<T1, T2, T3, T4, T5> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple5} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the {@link Tuple5} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3, T4, T5> T2 _2(Tuple5<T1, T2, T3, T4, T5> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple5} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the {@link Tuple5} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3, T4, T5> T3 _3(Tuple5<T1, T2, T3, T4, T5> tuple) {
        return tuple._3();
    }
    
    /**
     * Retrieves the forth element from a {@link Tuple5} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the {@link Tuple5} object from which the forth element is to be retrieved
     * @return the forth element of the tuple
     */
    public static <T1, T2, T3, T4, T5> T4 _4(Tuple5<T1, T2, T3, T4, T5> tuple) {
        return tuple._4();
    }
    
    /**
     * Retrieves the fifth element from a {@link Tuple5} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the {@link Tuple5} object from which the fifth element is to be retrieved
     * @return the fifth element of the tuple
     */
    public static <T1, T2, T3, T4, T5> T5 _5(Tuple5<T1, T2, T3, T4, T5> tuple) {
        return tuple._5();
    }
    
    //-- Tuple6 --
    
    /**
     * Retrieves the first element from a {@link Tuple6} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6> T1 _1(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple6} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6> T2 _2(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple6} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6> T3 _3(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return tuple._3();
    }
    
    /**
     * Retrieves the forth element from a {@link Tuple6} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object from which the forth element is to be retrieved
     * @return the forth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6> T4 _4(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return tuple._4();
    }
    
    /**
     * Retrieves the fifth element from a {@link Tuple6} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object from which the fifth element is to be retrieved
     * @return the fifth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6> T5 _5(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return tuple._5();
    }
    
    /**
     * Retrieves the sixth element from a {@link Tuple6} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object from which the sixth element is to be retrieved
     * @return the sixth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6> T6 _6(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return tuple._6();
    }
    
    //-- Tuple7 --
    
    /**
     * Retrieves the first element from a {@link Tuple7} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T1 _1(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple7} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7> T2 _2(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple7} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7> T3 _3(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return tuple._3();
    }
    
    /**
     * Retrieves the forth element from a {@link Tuple7} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object from which the forth element is to be retrieved
     * @return the forth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7> T4 _4(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return tuple._4();
    }
    
    /**
     * Retrieves the fifth element from a {@link Tuple7} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object from which the fifth element is to be retrieved
     * @return the fifth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7> T5 _5(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return tuple._5();
    }
    
    /**
     * Retrieves the sixth element from a {@link Tuple7} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object from which the sixth element is to be retrieved
     * @return the sixth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7> T6 _6(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return tuple._6();
    }
    
    /**
     * Retrieves the seventh element from a {@link Tuple7} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object from which the seventh element is to be retrieved
     * @return the seventh element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7> T7 _7(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return tuple._7();
    }
    
    //-- Tuple8 --
    
    /**
     * Retrieves the first element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T1 _1(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T2 _2(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T3 _3(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._3();
    }
    
    /**
     * Retrieves the forth element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the forth element is to be retrieved
     * @return the forth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T4 _4(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._4();
    }
    
    /**
     * Retrieves the fifth element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the fifth element is to be retrieved
     * @return the fifth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T5 _5(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._5();
    }
    
    /**
     * Retrieves the sixth element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the sixth element is to be retrieved
     * @return the sixth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T6 _6(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._6();
    }
    
    /**
     * Retrieves the seventh element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the seventh element is to be retrieved
     * @return the seventh element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T7 _7(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._7();
    }
    
    /**
     * Retrieves the eighth element from a {@link Tuple8} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object from which the eighth element is to be retrieved
     * @return the eighth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> T8 _8(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return tuple._8();
    }
    
    //-- Tuple9 --
    
    /**
     * Retrieves the first element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T1 _1(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T2 _2(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T3 _3(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._3();
    }
    
    /**
     * Retrieves the forth element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the forth element is to be retrieved
     * @return the forth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T4 _4(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._4();
    }
    
    /**
     * Retrieves the fifth element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the fifth element is to be retrieved
     * @return the fifth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T5 _5(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._5();
    }
    
    /**
     * Retrieves the sixth element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the sixth element is to be retrieved
     * @return the sixth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T6 _6(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._6();
    }
    
    /**
     * Retrieves the seventh element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the seventh element is to be retrieved
     * @return the seventh element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T7 _7(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._7();
    }
    
    /**
     * Retrieves the eighth element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the eighth element is to be retrieved
     * @return the eighth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T8 _8(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._8();
    }
    
    /**
     * Retrieves the ninth element from a {@link Tuple9} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object from which the ninth element is to be retrieved
     * @return the ninth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> T9 _9(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return tuple._9();
    }
    
    //-- Tuple10 --
    
    /**
     * Retrieves the first element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the first element is to be retrieved
     * @return the first element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T1 _1(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._1();
    }
    
    /**
     * Retrieves the second element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the second element is to be retrieved
     * @return the second element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T2 _2(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._2();
    }
    
    /**
     * Retrieves the third element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the third element is to be retrieved
     * @return the third element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T3 _3(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._3();
    }
    
    /**
     * Retrieves the forth element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the forth element is to be retrieved
     * @return the forth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T4 _4(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._4();
    }
    
    /**
     * Retrieves the fifth element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the fifth element is to be retrieved
     * @return the fifth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T5 _5(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._5();
    }
    
    /**
     * Retrieves the sixth element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the sixth element is to be retrieved
     * @return the sixth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T6 _6(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._6();
    }
    
    /**
     * Retrieves the seventh element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the seventh element is to be retrieved
     * @return the seventh element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T7 _7(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._7();
    }
    
    /**
     * Retrieves the eighth element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the eighth element is to be retrieved
     * @return the eighth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T8 _8(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._8();
    }
    
    /**
     * Retrieves the ninth element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the ninth element is to be retrieved
     * @return the ninth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T9 _9(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._9();
    }
    
    /**
     * Retrieves the tenth element from a {@link Tuple10} object.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object from which the tenth element is to be retrieved
     * @return the tenth element of the tuple
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T10 _10(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return tuple._10();
    }
    
    // == Access & Lens ==
    
    public static IntTuple2Lens<IntTuple2<Object>, Object, ObjectLens<IntTuple2<Object>, Object>> theIntTuple2 = createTheTuple(ObjectLens::of);
    
    public static <T2, T2LENS extends AnyLens<IntTuple2<T2>, T2>> IntTuple2Lens<IntTuple2<T2>, T2, T2LENS> createTheTuple(Function<LensSpec<IntTuple2<T2>, T2>, T2LENS> T2LensCreator) {
        return IntTuple2Lens.of(t -> t, (t, newT) -> newT, T2LensCreator);
    }
    
    // == toString ==
    
    /**
     * Converts a {@link Tuple2} to its string representation.
     * If the provided tuple is null, the method returns null.
     * Otherwise, it delegates to the tuple's {@code toString()} method.
     * 
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param tuple  the {@link Tuple2} instance to be converted to a string
     * @return       the string representation of the tuple, or null if the tuple is null
     */
    public static <T1, T2> String toString(Tuple2<T1, T2> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a Tuple3 object containing elements of types T1, T2, and T3 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param tuple the Tuple3 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3)", or null if the tuple is null
     */
    public static <T1, T2, T3> String toString(Tuple3<T1, T2, T3> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a Tuple4 object containing elements of types T1, T2, T3, and T4 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param tuple the Tuple4 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3,element4)", or null if the tuple is null
     */
    public static <T1, T2, T3, T4> String toString(Tuple4<T1, T2, T3, T4> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a {@link Tuple5} object containing elements of types T1 through T5 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the Tuple10 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3,...,element5)", or null if the tuple is null
     */
    public static <T1, T2, T3, T4, T5> String toString(Tuple5<T1, T2, T3, T4, T5> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a {@link Tuple6} object containing elements of types T1 through T6 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the Tuple10 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3,...,element6)", or null if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6> String toString(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a {@link Tuple7} object containing elements of types T1 through T7 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the Tuple10 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3,...,element7)", or null if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7> String toString(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a {@link Tuple8} object containing elements of types T1 through T8 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the Tuple10 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3,...,element8)", or null if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> String toString(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a {@link Tuple9} object containing elements of types T1 through T9 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the Tuple10 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3,...,element9)", or null if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> String toString(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    /**
     * Converts a {@link Tuple10} object containing elements of types T1 through T10 to its string representation.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the Tuple10 object to be converted into a string representation
     * @return the string representation of the tuple in the format "(element1,element2,element3,...,element10)", or null if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> String toString(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return (tuple == null) ? null : tuple.toString();
    }
    
    // == hashCode ==
    
    /**
     * Calculates the hash code for a {@link Tuple2} object containing elements of types T1 through T2.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param tuple the {@link Tuple2} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2> int hashCode(Tuple2<T1, T2> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple3} object containing elements of types T1 through T3.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param tuple the {@link Tuple3} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3> int hashCode(Tuple3<T1, T2, T3> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple4} object containing elements of types T1 through T4.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param tuple the {@link Tuple4} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3, T4> int hashCode(Tuple4<T1, T2, T3, T4> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple5} object containing elements of types T1 through T5.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the {@link Tuple5} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3, T4, T5> int hashCode(Tuple5<T1, T2, T3, T4, T5> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple6} object containing elements of types T1 through T6.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6> int hashCode(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple7} object containing elements of types T1 through T7.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7> int hashCode(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple8} object containing elements of types T1 through T8.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> int hashCode(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple9} object containing elements of types T1 through T9.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> int hashCode(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    /**
     * Calculates the hash code for a {@link Tuple10} object containing elements of types T1 through T10.
     * This method uses a prime number strategy for generating a hash code.
     * 
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object for which the hash code is to be calculated
     * @return the hash code of the tuple, or 0 if the tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> int hashCode(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return (tuple == null) ? 0 : tuple.hashCode();
    }
    
    // == equals ==
    
    /**
     * Compares a {@link Tuple2} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple2}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param tuple the {@link Tuple2} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple2} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2> boolean equals(Tuple2<T1, T2> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple3} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple3}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param tuple the {@link Tuple3} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple3} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3> boolean equals(Tuple3<T1, T2, T3> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple4} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple4}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param tuple the {@link Tuple4} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple4} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3, T4> boolean equals(Tuple4<T1, T2, T3, T4> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple5} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple5}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param tuple the {@link Tuple5} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple5} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3, T4, T5> boolean equals(Tuple5<T1, T2, T3, T4, T5> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple6} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple6}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param tuple the {@link Tuple6} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple6} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3, T4, T5, T6> boolean equals(Tuple6<T1, T2, T3, T4, T5, T6> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple7} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple7}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param tuple the {@link Tuple7} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple7} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3, T4, T5, T6, T7> boolean equals(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple8} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple8}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param tuple the {@link Tuple8} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple8} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> boolean equals(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple9} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple9}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param tuple the {@link Tuple9} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple9} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> boolean equals(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
    /**
     * Compares a {@link Tuple10} object with another object for equality. 
     * Equality is determined based on the equality of elements within the {@link Tuple10}.
     *
     * @param <T1> the type of the first element in the tuple
     * @param <T2> the type of the second element in the tuple
     * @param <T3> the type of the third element in the tuple
     * @param <T4> the type of the fourth element in the tuple
     * @param <T5> the type of the fifth element in the tuple
     * @param <T6> the type of the sixth element in the tuple
     * @param <T7> the type of the seventh element in the tuple
     * @param <T8> the type of the eighth element in the tuple
     * @param <T9> the type of the ninth element in the tuple
     * @param <T10> the type of the tenth element in the tuple
     * @param tuple the {@link Tuple10} object to be compared for equality
     * @param obj the object to be compared with the tuple
     * @return true if the given object is also a {@link Tuple10} and the elements of both tuples are equal; false otherwise
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> boolean equals(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple, Object obj) {
        return (tuple == null) ? (obj == null) : tuple.equals(obj);
    }
    
}
