// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.streamable.intstreamable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

import functionalj.function.IntToByteFunction;
import functionalj.list.FuncList;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.map.FuncMap;

public interface IntStreamableWithConversion extends AsIntStreamable {
    
    /** Map the data to byte and return the byte array of all the results. */
    public default byte[] toByteArray(IntToByteFunction toByte) {
        return stream()
                .toByteArray(toByte);
    }
    
    /** Map the data to int and return the byte array of all the results. */
    public default int[] toIntArray(IntUnaryOperator toInt) {
        return stream()
                .toIntArray(toInt);
    }
    
    /** Map the data to long and return the byte array of all the results. */
    public default long[] toLongArray(IntToLongFunction toLong) {
        return stream()
                .toLongArray(toLong);
    }
    
    /** Map the data to double and return the byte array of all the results. */
    public default double[] toDoubleArray(IntToDoubleFunction toDouble) {
        return stream()
                .toDoubleArray(toDouble);
    }
    
    //-- toList --
    
    /** @return the array list containing the elements. */
    public default ArrayList<Integer> toArrayList() {
        return stream()
                .toArrayList();
    }
    
    /** @return a functional list containing the elements. */
    public default IntFuncList toFuncList() {
        return stream()
                .toFuncList();
    }
    
    /** @return an immutable list containing the elements. */
    public default ImmutableIntFuncList toImmutableList() {
        return stream()
                .toImmutableList();
    }
    
    /** @return an Java list containing the elements. */
    public default List<Integer> toJavaList() {
        return stream()
                .toJavaList();
    }
    
    /** @return a list containing the elements. */
    public default FuncList<Integer> toList() {
        return stream()
                .toList();
    }
    
    /** @return a mutable list containing the elements. */
    public default List<Integer> toMutableList() {
        return stream()
                .toMutableList();
    }
    
    //-- join --
    
    /** @return the concatenate of toString() of each elements. */
    public default String join() {
        return stream()
                .join();
    }
    
    /** @return the concatenate of toString() of each elements with the given delimiter. */
    public default String join(String delimiter) {
        return stream()
                .join(delimiter);
    }
    
    //-- toListString --
    
    /** @return the to string as a list for this stream. */
    public default String toListString() {
        return stream()
                .toListString();
    }
    
    //-- toMap --
    
    /**
     * Create a map from the data using the keyMapper.
     * This method throw an exception with duplicate keys.
     */
    public default <KEY> FuncMap<KEY, Integer> toMap(IntFunction<KEY> keyMapper) {
        return stream()
                .toMap(keyMapper);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            IntFunction<KEY>   keyMapper,
            IntFunction<VALUE> valueMapper) {
        return stream()
                .toMap(keyMapper, valueMapper);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            IntFunction<KEY>      keyMapper,
            IntFunction<VALUE>    valueMapper,
            BinaryOperator<VALUE> mergeFunction) {
        return stream()
                .toMap(keyMapper, valueMapper, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    public default <KEY> FuncMap<KEY, Integer> toMap(
            IntFunction<KEY>  keyMapper,
            IntBinaryOperator mergeFunction) {
        return stream()
                .toMap(keyMapper, mergeFunction);
    }
    
    //-- toSet --
    
    /** @return  a set of the elements. */
    public default Set<Integer> toSet() {
        return stream()
                .toSet();
    }
    
}
