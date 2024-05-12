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
import lombok.val;

/**
 * Provides utility methods for creating immutable tuple objects of various sizes.
 * This class cannot be instantiated and contains static methods only.
 */
public class ImmutableTuple {
    
    private ImmutableTuple() {
    }
    
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
        return new ImmutableTuple2<>(_1, _2);
    }
    
    /**
     * Creates a new {@link ImmutableTuple2} object from a Map.Entry object.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param entry  the {@link Map.Entry} object containing the elements to be placed in the tuple
     * @return  a new {@link ImmutableTuple2} object containing the key and value from the {@link Map.Entry} object, 
     *            or a tuple with null values if the entry is null
     */
    public static <T1, T2> ImmutableTuple2<T1, T2> of(Map.Entry<? extends T1, ? extends T2> entry) {
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
    public static <T1, T2, T3> ImmutableTuple3<T1, T2, T3> of(T1 _1, T2 _2, T3 _3) {
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
    public static <T1, T2, T3, T4> ImmutableTuple4<T1, T2, T3, T4> of(T1 _1, T2 _2, T3 _3, T4 _4) {
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
    public static <T1, T2, T3, T4, T5> ImmutableTuple5<T1, T2, T3, T4, T5> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
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
    public static <T1, T2, T3, T4, T5, T6> ImmutableTuple6<T1, T2, T3, T4, T5, T6> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
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
    
    //-- From Tuple --
    
    /**
     * Creates an {@link ImmutableTuple2} object from an existing {@link Tuple2} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple2}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple3} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param tuple  the {@link Tuple2} object to be converted into an {@link ImmutableTuple2}
     * @return an {@link ImmutableTuple2} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2> ImmutableTuple2<T1, T2> of(Tuple2<T1, T2> tuple) {
        if (tuple instanceof ImmutableTuple2)
            return (ImmutableTuple2<T1, T2>) tuple;
        if (tuple == null)
            return new ImmutableTuple2<>(null, null);
        val _1 = tuple._1();
        val _2 = tuple._2();
        return new ImmutableTuple2<>(_1, _2);
    }
    
    /**
     * Creates an {@link ImmutableTuple3} object from an existing {@link Tuple3} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple3}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple3} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param tuple  the {@link Tuple3} object to be converted into an {@link ImmutableTuple3}
     * @return an {@link ImmutableTuple3} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3> ImmutableTuple3<T1, T2, T3> of(Tuple3<T1, T2, T3> tuple) {
        if (tuple instanceof ImmutableTuple3)
            return (ImmutableTuple3<T1, T2, T3>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple3<>(null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        return new ImmutableTuple3<>(_1, _2, _3);
    }
    
    /**
     * Creates an {@link ImmutableTuple4} object from an existing {@link Tuple4} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple4}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple4} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param tuple  the {@link Tuple4} object to be converted into an {@link ImmutableTuple4}
     * @return an {@link ImmutableTuple4} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3, T4> ImmutableTuple4<T1, T2, T3, T4> of(Tuple4<T1, T2, T3, T4> tuple) {
        if (tuple instanceof ImmutableTuple4)
            return (ImmutableTuple4<T1, T2, T3, T4>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple4<>(null, null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        val _4 = tuple._4();
        return new ImmutableTuple4<>(_1, _2, _3, _4);
    }
    
    /**
     * Creates an {@link ImmutableTuple5} object from an existing {@link Tuple5} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple5}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple5} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param <T5>   the type of the fifth element in the tuple
     * @param tuple  the {@link Tuple5} object to be converted into an {@link ImmutableTuple5}
     * @return an {@link ImmutableTuple5} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3, T4, T5> ImmutableTuple5<T1, T2, T3, T4, T5> of(Tuple5<T1, T2, T3, T4, T5> tuple) {
        if (tuple instanceof ImmutableTuple5)
            return (ImmutableTuple5<T1, T2, T3, T4, T5>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple5<>(null, null, null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        val _4 = tuple._4();
        val _5 = tuple._5();
        return new ImmutableTuple5<>(_1, _2, _3, _4, _5);
    }
    
    /**
     * Creates an {@link ImmutableTuple6} object from an existing {@link Tuple6} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple6}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple6} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param <T5>   the type of the fifth element in the tuple
     * @param <T6>   the type of the sixth element in the tuple
     * @param tuple  the {@link Tuple6} object to be converted into an {@link ImmutableTuple6}
     * @return an {@link ImmutableTuple6} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6> ImmutableTuple6<T1, T2, T3, T4, T5, T6> of(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        if (tuple instanceof ImmutableTuple6)
            return (ImmutableTuple6<T1, T2, T3, T4, T5, T6>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple6<>(null, null, null, null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        val _4 = tuple._4();
        val _5 = tuple._5();
        val _6 = tuple._6();
        return new ImmutableTuple6<>(_1, _2, _3, _4, _5, _6);
    }
    
    /**
     * Creates an {@link ImmutableTuple7} object from an existing {@link Tuple7} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple7}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple7} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param <T5>   the type of the fifth element in the tuple
     * @param <T6>   the type of the sixth element in the tuple
     * @param <T7>   the type of the seventh element in the tuple
     * @param tuple  the {@link Tuple7} object to be converted into an {@link ImmutableTuple7}
     * @return an {@link ImmutableTuple7} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7> ImmutableTuple7<T1, T2, T3, T4, T5, T6, T7> of(Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple) {
        if (tuple instanceof ImmutableTuple7)
            return (ImmutableTuple7<T1, T2, T3, T4, T5, T6, T7>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple7<>(null, null, null, null, null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        val _4 = tuple._4();
        val _5 = tuple._5();
        val _6 = tuple._6();
        val _7 = tuple._7();
        return new ImmutableTuple7<>(_1, _2, _3, _4, _5, _6, _7);
    }
    
    /**
     * Creates an {@link ImmutableTuple8} object from an existing {@link Tuple8} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple8}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple8} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param <T5>   the type of the fifth element in the tuple
     * @param <T6>   the type of the sixth element in the tuple
     * @param <T7>   the type of the seventh element in the tuple
     * @param <T8>   the type of the eighth element in the tuple
     * @param tuple  the {@link Tuple8} object to be converted into an {@link ImmutableTuple8}
     * @return an {@link ImmutableTuple8} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> ImmutableTuple8<T1, T2, T3, T4, T5, T6, T7, T8> of(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
        if (tuple instanceof ImmutableTuple8)
            return (ImmutableTuple8<T1, T2, T3, T4, T5, T6, T7, T8>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple8<>(null, null, null, null, null, null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        val _4 = tuple._4();
        val _5 = tuple._5();
        val _6 = tuple._6();
        val _7 = tuple._7();
        val _8 = tuple._8();
        return new ImmutableTuple8<>(_1, _2, _3, _4, _5, _6, _7, _8);
    }
    
    /**
     * Creates an {@link ImmutableTuple9} object from an existing {@link Tuple9} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple9}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple9} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param <T5>   the type of the fifth element in the tuple
     * @param <T6>   the type of the sixth element in the tuple
     * @param <T7>   the type of the seventh element in the tuple
     * @param <T8>   the type of the eighth element in the tuple
     * @param <T9>   the type of the ninth element in the tuple
     * @param tuple  the {@link Tuple9} object to be converted into an {@link ImmutableTuple9}
     * @return an {@link ImmutableTuple9} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ImmutableTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        if (tuple instanceof ImmutableTuple9)
            return (ImmutableTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple9<>(null, null, null, null, null, null, null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        val _4 = tuple._4();
        val _5 = tuple._5();
        val _6 = tuple._6();
        val _7 = tuple._7();
        val _8 = tuple._8();
        val _9 = tuple._9();
        return new ImmutableTuple9<>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }
    
    /**
     * Creates an {@link ImmutableTuple10} object from an existing {@link Tuple10} object.
     * If the provided tuple is already an instance of {@link ImmutableTuple10}, it is returned directly.
     * Otherwise, a new {@link ImmutableTuple10} object is created with the elements of the provided tuple.
     *
     * @param <T1>   the type of the first element in the tuple
     * @param <T2>   the type of the second element in the tuple
     * @param <T3>   the type of the third element in the tuple
     * @param <T4>   the type of the fourth element in the tuple
     * @param <T5>   the type of the fifth element in the tuple
     * @param <T6>   the type of the sixth element in the tuple
     * @param <T7>   the type of the seventh element in the tuple
     * @param <T8>   the type of the eighth element in the tuple
     * @param <T9>   the type of the ninth element in the tuple
     * @param <T10>  the type of the tenth element in the tuple
     * @param tuple  the {@link Tuple10} object to be converted into an {@link ImmutableTuple10}
     * @return an {@link ImmutableTuple10} object containing the elements from the provided tuple, or a tuple with all null values if the provided tuple is null
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ImmutableTuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> of(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        if (tuple instanceof ImmutableTuple10)
            return (ImmutableTuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>) tuple;
        
        if (tuple == null)
            return new ImmutableTuple10<>(null, null, null, null, null, null, null, null, null, null);
        
        val _1 = tuple._1();
        val _2 = tuple._2();
        val _3 = tuple._3();
        val _4 = tuple._4();
        val _5 = tuple._5();
        val _6 = tuple._6();
        val _7 = tuple._7();
        val _8 = tuple._8();
        val _9 = tuple._9();
        val _10 = tuple._10();
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
}
