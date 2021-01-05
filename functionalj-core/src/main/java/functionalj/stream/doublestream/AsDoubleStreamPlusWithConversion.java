// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.doublestream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.doublelist.ImmutableDoubleFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;


public interface AsDoubleStreamPlusWithConversion {
    
    public DoubleStreamPlus doubleStreamPlus();
    
//
//    /** Map the data to long and return the long array of all the results. */
//    @Eager
//    @Terminal
//    public default long[] toLongArray(DoubleToLongFunction toLong) {
//        val streamPlus = doubleStreamPlus();
//        return streamPlus
//                .mapToLong(toLong)
//                .toArray  ();
//    }
    
    /** Map the data to double and return the byte array of all the results. */
    @Eager
    @Terminal
    public default double[] toDoubleArray(DoubleUnaryOperator toDouble) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .map(toDouble)
                .toArray    ();
    }

    //-- toList --

    /** @return the array list containing the elements. */
    @Eager
    @Terminal
    public default ArrayList<Double> toArrayList() {
        //TODO - This is not efficient but without knowing the size, it is not so easy to do efficiently
        //       The proper solution for this is to have the stream itself contain the marker if it knows its size.
        //       May be by using peekSize() method to check the size and the forEach to populate it.
        val streamPlus = doubleStreamPlus();
        val javaList = streamPlus.boxed().toJavaList();
        return new ArrayList<Double>(javaList);
    }
    
    /** @return a functional list containing the elements. */
    @Eager
    @Terminal
    public default DoubleFuncList toFuncList() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .toImmutableList();
    }

    /** @return an immutable list containing the elements. */
    @Eager
    @Terminal
    public default ImmutableDoubleFuncList toImmutableList() {
        val streamPlus = doubleStreamPlus();
        return ImmutableDoubleFuncList.from(streamPlus);
    }
    
    /** @return an Java list containing the elements. */
    @Eager
    @Terminal
    public default List<Double> toJavaList() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .mapToObj(Double::valueOf)
                .collect(Collectors.toList());
    }
    
    /** @return a list containing the elements. */
    @Eager
    @Terminal
    public default FuncList<Double> toList() {
        val streamPlus = doubleStreamPlus();
        return ImmutableList.from(
                streamPlus.boxed());
    }
    
    /** @return a mutable list containing the elements. */
    @Eager
    @Terminal
    public default List<Double> toMutableList() {
        return toArrayList();
    }
    
    //-- join --
    
    /** @return the concatenate of toString() of each elements. */
    @Eager
    @Terminal
    public default String join() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .mapToObj(StrFuncs::toStr)
                .collect (Collectors.joining());
    }
    
    /** @return the concatenate of toString() of each elements with the given delimiter. */
    @Eager
    @Terminal
    public default String join(String delimiter) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .mapToObj(StrFuncs::toStr)
                .collect (Collectors.joining(delimiter));
    }
    
    //-- toListString --
    
    /** @return the to string as a list for this stream. */
    @Eager
    @Terminal
    public default String toListString() {
        val streamPlus = doubleStreamPlus();
        val strValue
                = streamPlus
                .mapToObj(String::valueOf)
                .collect (Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
    //-- toMap --
    
    /**
     * Create a map from the data using the keyMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Double> toMap(DoubleFunction<KEY> keyMapper) {
        val streamPlus = doubleStreamPlus();
        val theMap     = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> i));
        return ImmutableMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            DoubleFunction<KEY>   keyMapper,
            DoubleFunction<VALUE> valueMapper) {
        val streamPlus = doubleStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i)));
        return ImmutableMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            DoubleFunction<KEY>   keyMapper,
            DoubleFunction<VALUE> valueMapper,
            BinaryOperator<VALUE> mergeFunction) {
        val streamPlus = doubleStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i), mergeFunction));
        return ImmutableMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Double> toMap(
            DoubleFunction<KEY>  keyMapper,
            DoubleBinaryOperator mergeFunction) {
        val streamPlus = doubleStreamPlus();
        val theMap = streamPlus
                .boxed()
                .collect(Collectors.toMap(
                        i -> keyMapper.apply(i),
                        i -> i,
                        (a, b) -> mergeFunction.applyAsDouble(a, b)));
        return ImmutableMap.from(theMap);
    }
    
    //-- toSet --
    
    /** @return  a set of the elements. */
    @Eager
    @Terminal
    public default Set<Double> toSet() {
        val streamPlus = doubleStreamPlus();
        return streamPlus.boxed().collect(Collectors.toSet());
    }
    
}
