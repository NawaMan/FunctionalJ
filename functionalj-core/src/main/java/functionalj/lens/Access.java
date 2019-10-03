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
package functionalj.lens;

import java.math.BigDecimal;
import java.math.BigInteger;

import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensType;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.BigDecimalLens;
import functionalj.lens.lenses.BigIntegerLens;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.BooleanLens;
import functionalj.lens.lenses.ComparableLens;
import functionalj.lens.lenses.DoubleToDoubleAccessPrimitive;
import functionalj.lens.lenses.IntegerToIntegerAccessPrimitive;
import functionalj.lens.lenses.LongToLongAccessPrimitive;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.StringLens;
import functionalj.lens.lenses.Tuple2Lens;
import functionalj.tuple.Tuple2;

@SuppressWarnings("javadoc")
public interface Access {
    
    public static final AnyLens<Object, Object>         theObject  = AnyLens    .of(LensSpec.of(Object.class));
    public static final BooleanLens<Boolean>            theBoolean = BooleanLens.of(LensSpec.of(Boolean.class));
    public static final StringLens<String>              theString  = StringLens .of(LensSpec.of(String.class));
    public static final IntegerToIntegerAccessPrimitive theInteger = i -> i;
    public static final LongToLongAccessPrimitive       theLong    = l -> l;
    public static final DoubleToDoubleAccessPrimitive   theDouble  = d -> d;
    
    public static final BigIntegerLens<BigInteger> theBigInteger = BigIntegerLens.of(LensSpec.of(BigInteger.class));
    public static final BigDecimalLens<BigDecimal> theBigDecimal = BigDecimalLens.of(LensSpec.of(BigDecimal.class));
    
    public static final AnyLens<Object, Object>         $O = theObject;
    public static final BooleanLens<Boolean>            $B = theBoolean;
    public static final StringLens<String>              $S = theString;
    public static final IntegerToIntegerAccessPrimitive $I = theInteger;
    public static final LongToLongAccessPrimitive       $L = theLong;
    public static final DoubleToDoubleAccessPrimitive   $D = theDouble;
    public static final BigIntegerLens<BigInteger>      $BI = theBigInteger;
    public static final BigDecimalLens<BigDecimal>      $BD = theBigDecimal;
    
    public static final BooleanAccessPrimitive<Object> True  = any -> true;
    public static final BooleanAccessPrimitive<Object> False = any -> false;
    
    public static final Accesses.TheListLens   theList   = new Accesses.TheListLens();
    public static final Accesses.TheTuple2Lens theTuple2 = new Accesses.TheTuple2Lens();
    
    public static <T> AnyLens<T, T> theItem() {
        return AnyLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T> ObjectLens<T, T> theObject() {
        return ObjectLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T extends Comparable<T>> ComparableLens<T, T> theComparable() {
        return ComparableLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T1, T2, 
            T1ACCESS extends AnyAccess<Tuple2<T1, T2>, T1>, T2ACCESS extends AnyAccess<Tuple2<T1, T2>, T2>, 
            T1LENS   extends AnyLens<Tuple2<T1, T2>, T1>,   T2LENS   extends AnyLens<Tuple2<T1, T2>, T2>>
        Tuple2Lens<Tuple2<T1,T2>, T1, T2, T1LENS, T2LENS> theTupleOf(
             LensType<Tuple2<T1, T2>, T1, T1ACCESS, T1LENS> t1Type,
             LensType<Tuple2<T1, T2>, T2, T2ACCESS, T2LENS> t2Type) {
        return theTuple2.of(t1Type, t2Type);
    }
    
    //-- Each --
    
    public static final AnyLens<Object, Object>         eachObject  = theObject;
    public static final BooleanLens<Boolean>            eachBoolean = theBoolean;
    public static final StringLens<String>              eachString  = theString;
    public static final IntegerToIntegerAccessPrimitive eachInteger = theInteger;
    public static final LongToLongAccessPrimitive       eachLong    = theLong;
    public static final DoubleToDoubleAccessPrimitive   eachDouble  = theDouble;
    
    public static final BigIntegerLens<BigInteger> eachBigInteger = theBigInteger;
    public static final BigDecimalLens<BigDecimal> eachBigDecimal = theBigDecimal;
    
    public static final Accesses.TheListLens   eachList   = theList;
    public static final Accesses.TheTuple2Lens eachTuple2 = theTuple2;
    
    public static <T> AnyLens<T, T>    eachItem()   { return theItem(); }
    public static <T> ObjectLens<T, T> eachObject() { return theObject(); }
    
    public static <T extends Comparable<T>> ComparableLens<T, T> eachComparable() {
        return theComparable();
    }
    public static <T1, T2, 
            T1ACCESS extends AnyAccess<Tuple2<T1, T2>, T1>, T2ACCESS extends AnyAccess<Tuple2<T1, T2>, T2>, 
            T1LENS   extends AnyLens<Tuple2<T1, T2>, T1>,   T2LENS   extends AnyLens<Tuple2<T1, T2>, T2>>
        Tuple2Lens<Tuple2<T1,T2>, T1, T2, T1LENS, T2LENS> eachTupleOf(
             LensType<Tuple2<T1, T2>, T1, T1ACCESS, T1LENS> t1Type,
             LensType<Tuple2<T1, T2>, T2, T2ACCESS, T2LENS> t2Type) {
        return theTupleOf(t1Type, t2Type);
    }
}
