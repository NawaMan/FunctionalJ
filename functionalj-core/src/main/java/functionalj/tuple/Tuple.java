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

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.ObjectLens;

@SuppressWarnings("javadoc")
public class Tuple {
    
    public static <T1, T2> ImmutableTuple2<T1, T2> of(T1 _1, T2 _2) {
        return new ImmutableTuple2<>(_1, _2);
    }
    
    public static <T1, T2> ImmutableTuple2<T1, T2> of(Map.Entry<? extends T1, ? extends T2> entry) {
        if (entry == null)
            return new ImmutableTuple2<>(null, null);
        
        return new ImmutableTuple2<>(entry);
    }
    
    public static <T1, T2, T3> ImmutableTuple3<T1, T2, T3> of(T1 _1, T2 _2, T3 _3) {
        return new ImmutableTuple3<>(_1, _2, _3);
    }
    
    public static <T1, T2, T3, T4> ImmutableTuple4<T1, T2, T3, T4> of(T1 _1, T2 _2, T3 _3, T4 _4) {
        return new ImmutableTuple4<>(_1, _2, _3, _4);
    }
    
    public static <T1, T2, T3, T4, T5> ImmutableTuple5<T1, T2, T3, T4, T5> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new ImmutableTuple5<>(_1, _2, _3, _4, _5);
    }
    
    public static <T1, T2, T3, T4, T5, T6> ImmutableTuple6<T1, T2, T3, T4, T5, T6> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return new ImmutableTuple6<>(_1, _2, _3, _4, _5, _6);
    }
    
    // == Use ==
    
    public static <T1, T2> T1 _1(Tuple2<T1, T2> t) { return t._1(); }
    public static <T1, T2> T2 _2(Tuple2<T1, T2> t) { return t._2(); }
    
    public static <T1, T2, T3> T1 _1(Tuple3<T1, T2, T3> t) { return t._1(); }
    public static <T1, T2, T3> T2 _2(Tuple3<T1, T2, T3> t) { return t._2(); }
    public static <T1, T2, T3> T3 _3(Tuple3<T1, T2, T3> t) { return t._3(); }
    
    public static <T1, T2, T3, T4> T1 _1(Tuple4<T1, T2, T3, T4> t) { return t._1(); }
    public static <T1, T2, T3, T4> T2 _2(Tuple4<T1, T2, T3, T4> t) { return t._2(); }
    public static <T1, T2, T3, T4> T3 _3(Tuple4<T1, T2, T3, T4> t) { return t._3(); }
    public static <T1, T2, T3, T4> T4 _4(Tuple4<T1, T2, T3, T4> t) { return t._4(); }
    
    public static <T1, T2, T3, T4, T5> T1 _1(Tuple5<T1, T2, T3, T4, T5> t) { return t._1(); }
    public static <T1, T2, T3, T4, T5> T2 _2(Tuple5<T1, T2, T3, T4, T5> t) { return t._2(); }
    public static <T1, T2, T3, T4, T5> T3 _3(Tuple5<T1, T2, T3, T4, T5> t) { return t._3(); }
    public static <T1, T2, T3, T4, T5> T4 _4(Tuple5<T1, T2, T3, T4, T5> t) { return t._4(); }
    public static <T1, T2, T3, T4, T5> T5 _5(Tuple5<T1, T2, T3, T4, T5> t) { return t._5(); }
    
    public static <T1, T2, T3, T4, T5, T6> T1 _1(Tuple6<T1, T2, T3, T4, T5, T6> t) { return t._1(); }
    public static <T1, T2, T3, T4, T5, T6> T2 _2(Tuple6<T1, T2, T3, T4, T5, T6> t) { return t._2(); }
    public static <T1, T2, T3, T4, T5, T6> T3 _3(Tuple6<T1, T2, T3, T4, T5, T6> t) { return t._3(); }
    public static <T1, T2, T3, T4, T5, T6> T4 _4(Tuple6<T1, T2, T3, T4, T5, T6> t) { return t._4(); }
    public static <T1, T2, T3, T4, T5, T6> T5 _5(Tuple6<T1, T2, T3, T4, T5, T6> t) { return t._5(); }
    public static <T1, T2, T3, T4, T5, T6> T6 _6(Tuple6<T1, T2, T3, T4, T5, T6> t) { return t._6(); }
    
    // == Access & Lens ==
    
    public static IntTuple2Lens<IntTuple2<Object>, Object, ObjectLens<IntTuple2<Object>, Object>>
                        theIntTuple2 = createTheTuple(ObjectLens::of);
    
    public static <T2, T2LENS extends AnyLens<IntTuple2<T2>, T2>> 
            IntTuple2Lens<IntTuple2<T2>, T2, T2LENS> createTheTuple(
                Function<LensSpec<IntTuple2<T2>, T2>, T2LENS> T2LensCreator) {
        return IntTuple2Lens.of(t -> t, (t, newT) -> newT, T2LensCreator);
    }
    
    //== toString ==
    
    public static <T1, T2> String toString(Tuple2<T1, T2> tuple) {
        if (tuple == null)
            return null;
        
        return "(" + tuple._1() + "," + tuple._2() + ")";
    }
    
    public static <T1, T2, T3> String toString(Tuple3<T1, T2, T3> tuple) {
        if (tuple == null)
            return null;
        
        return "(" + tuple._1() + "," + tuple._2() + "," + tuple._3() + ")";
    }
    
    public static <T1, T2, T3, T4> String toString(Tuple4<T1, T2, T3, T4> tuple) {
        if (tuple == null)
            return null;
        
        return "(" + tuple._1() + "," + tuple._2() + "," + tuple._3() + "," + tuple._4() + ")";
    }
    
    public static <T1, T2, T3, T4, T5> String toString(Tuple5<T1, T2, T3, T4, T5> tuple) {
        if (tuple == null)
            return null;
        
        return "(" + tuple._1() + "," + tuple._2() + "," + tuple._3() + "," + tuple._4() + "," + tuple._5() + ")";
    }
    
    public static <T1, T2, T3, T4, T5, T6> String toString(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        if (tuple == null)
            return null;
        
        return "(" + tuple._1() + "," + tuple._2() + "," + tuple._3() + "," + tuple._4() + "," + tuple._5() + "," + tuple._6() + ")";
    }
    
    //== hashCode ==
    
    public static <T1, T2> int hashCode(Tuple2<T1, T2> tuple) {
        if (tuple == null)
            return 0;
        
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tuple._1() == null) ? 0 : tuple._1().hashCode());
        result = prime * result + ((tuple._2() == null) ? 0 : tuple._2().hashCode());
        return result;
    }
    public static <T1, T2, T3> int hashCode(Tuple3<T1, T2, T3> tuple) {
        if (tuple == null)
            return 0;
        
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tuple._1() == null) ? 0 : tuple._1().hashCode());
        result = prime * result + ((tuple._2() == null) ? 0 : tuple._2().hashCode());
        result = prime * result + ((tuple._3() == null) ? 0 : tuple._3().hashCode());
        return result;
    }
    public static <T1, T2, T3, T4> int hashCode(Tuple4<T1, T2, T3, T4> tuple) {
        if (tuple == null)
            return 0;
        
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tuple._1() == null) ? 0 : tuple._1().hashCode());
        result = prime * result + ((tuple._2() == null) ? 0 : tuple._2().hashCode());
        result = prime * result + ((tuple._3() == null) ? 0 : tuple._3().hashCode());
        result = prime * result + ((tuple._4() == null) ? 0 : tuple._4().hashCode());
        return result;
    }
    public static <T1, T2, T3, T4, T5> int hashCode(Tuple5<T1, T2, T3, T4, T5> tuple) {
        if (tuple == null)
            return 0;
        
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tuple._1() == null) ? 0 : tuple._1().hashCode());
        result = prime * result + ((tuple._2() == null) ? 0 : tuple._2().hashCode());
        result = prime * result + ((tuple._3() == null) ? 0 : tuple._3().hashCode());
        result = prime * result + ((tuple._4() == null) ? 0 : tuple._4().hashCode());
        result = prime * result + ((tuple._5() == null) ? 0 : tuple._5().hashCode());
        return result;
    }
    public static <T1, T2, T3, T4, T5, T6> int hashCode(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        if (tuple == null)
            return 0;
        
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tuple._1() == null) ? 0 : tuple._1().hashCode());
        result = prime * result + ((tuple._2() == null) ? 0 : tuple._2().hashCode());
        result = prime * result + ((tuple._3() == null) ? 0 : tuple._3().hashCode());
        result = prime * result + ((tuple._4() == null) ? 0 : tuple._4().hashCode());
        result = prime * result + ((tuple._5() == null) ? 0 : tuple._5().hashCode());
        result = prime * result + ((tuple._6() == null) ? 0 : tuple._6().hashCode());
        return result;
    }
    
    //== equals ==
    
    public static <T1, T2> boolean equals(Tuple2<T1, T2> tuple, Object obj) {
        if (tuple == null)
            return (obj == null);
        
        if (tuple == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Tuple2)
            return false;
        
        @SuppressWarnings("rawtypes")
        Tuple2 other = (Tuple2) obj;
        if (!Objects.equals(tuple._1(), other._1())) return false;
        if (!Objects.equals(tuple._2(), other._2())) return false;
        return true;
    }
    
    public static <T1, T2, T3> boolean equals(Tuple3<T1, T2, T3> tuple, Object obj) {
        if (tuple == null)
            return (obj == null);
        
        if (tuple == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Tuple3)
            return false;
        
        @SuppressWarnings("rawtypes")
        Tuple3 other = (Tuple3) obj;
        if (!Objects.equals(tuple._1(), other._1())) return false;
        if (!Objects.equals(tuple._2(), other._2())) return false;
        if (!Objects.equals(tuple._3(), other._3())) return false;
        return true;
    }
    
    public static <T1, T2, T3, T4> boolean equals(Tuple4<T1, T2, T3, T4> tuple, Object obj) {
        if (tuple == null)
            return (obj == null);
        
        if (tuple == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Tuple4)
            return false;
        
        @SuppressWarnings("rawtypes")
        Tuple4 other = (Tuple4) obj;
        if (!Objects.equals(tuple._1(), other._1())) return false;
        if (!Objects.equals(tuple._2(), other._2())) return false;
        if (!Objects.equals(tuple._3(), other._3())) return false;
        if (!Objects.equals(tuple._4(), other._4())) return false;
        return true;
    }
    
    public static <T1, T2, T3, T4, T5> boolean equals(Tuple5<T1, T2, T3, T4, T5> tuple, Object obj) {
        if (tuple == null)
            return (obj == null);
        
        if (tuple == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Tuple5)
            return false;
        
        @SuppressWarnings("rawtypes")
        Tuple5 other = (Tuple5) obj;
        if (!Objects.equals(tuple._1(), other._1())) return false;
        if (!Objects.equals(tuple._2(), other._2())) return false;
        if (!Objects.equals(tuple._3(), other._3())) return false;
        if (!Objects.equals(tuple._4(), other._4())) return false;
        if (!Objects.equals(tuple._5(), other._5())) return false;
        return true;
    }
    
    public static <T1, T2, T3, T4, T5, T6> boolean equals(Tuple6<T1, T2, T3, T4, T5, T6> tuple, Object obj) {
        if (tuple == null)
            return (obj == null);
        
        if (tuple == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Tuple6)
            return false;
        
        @SuppressWarnings("rawtypes")
        Tuple6 other = (Tuple6) obj;
        if (!Objects.equals(tuple._1(), other._1())) return false;
        if (!Objects.equals(tuple._2(), other._2())) return false;
        if (!Objects.equals(tuple._3(), other._3())) return false;
        if (!Objects.equals(tuple._4(), other._4())) return false;
        if (!Objects.equals(tuple._5(), other._5())) return false;
        if (!Objects.equals(tuple._6(), other._6())) return false;
        return true;
    }
    
}
