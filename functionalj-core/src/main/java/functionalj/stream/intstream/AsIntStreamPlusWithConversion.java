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
package functionalj.stream.intstream;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import functionalj.function.IntToByteFunction;
import functionalj.function.IntToCharFunction;
import functionalj.function.aggregator.IntAggregation;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsIntStreamPlusWithConversion {
    
    public IntStreamPlus intStreamPlus();
    
    /**
     * @return a iterator of this FuncList.
     */
    public default IntIteratorPlus iterator() {
        val streamPlus = intStreamPlus();
        return streamPlus.iterator();
    }
    
    /**
     * @return a spliterator of this FuncList.
     */
    public default Spliterator.OfInt spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    /**
     * @return a functional list containing the elements.
     *
     * Note: This method will materialize the elements and put in a list.
     */
    @Eager
    @Terminal
    public default IntFuncList toFuncList() {
        return IntFuncList.from(intStreamPlus());
    }
    
    // -- toArray --
    @Eager
    @Terminal
    public default int[] toArray() {
        val streamPlus = intStreamPlus();
        return streamPlus.toArray();
    }
    
    /**
     * Map the data to byte and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default byte[] toByteArray(IntToByteFunction toByte) {
        val streamPlus = intStreamPlus();
        val byteArray = new ByteArrayOutputStream();
        streamPlus.forEach(d -> {
            byte b = toByte.apply(d);
            byteArray.write(b);
        });
        return byteArray.toByteArray();
    }
    
    /**
     * Map the data to int and return the int array of all the results.
     */
    @Eager
    @Terminal
    public default char[] toCharArray(IntToCharFunction toChar) {
        val streamPlus = intStreamPlus();
        val charArray = new CharArrayWriter();
        streamPlus.forEach(d -> {
            char c = toChar.applyAsChar(d);
            charArray.write(c);
        });
        return charArray.toCharArray();
    }
    
    /**
     * Map the data to int and return the int array of all the results.
     */
    @Eager
    @Terminal
    public default char[] toCharArray() {
        return toCharArray(i -> (char) i);
    }
    
    /**
     * Map the data to int and return the int array of all the results.
     */
    @Eager
    @Terminal
    public default int[] toIntArray(IntUnaryOperator toInt) {
        val streamPlus = intStreamPlus();
        return streamPlus.map(toInt).toArray();
    }
    
    /**
     * Map the data to int and return the int array of all the results.
     */
    @Eager
    @Terminal
    public default int[] toIntArray() {
        return toArray();
    }
    
    /**
     * Map the data to double and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default double[] toDoubleArray(IntToDoubleFunction toDouble) {
        val streamPlus = intStreamPlus();
        return streamPlus.mapToDouble(toDouble).toArray();
    }
    
    /**
     * Map the data to double and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default double[] toDoubleArray() {
        val streamPlus = intStreamPlus();
        return streamPlus.mapToDouble(i -> (double) i).toArray();
    }
    
    // -- toList --
    /**
     * @return the array list containing the elements.
     */
    @Eager
    @Terminal
    public default ArrayList<Integer> toArrayList() {
        val streamPlus = intStreamPlus();
        val newList = new ArrayList<Integer>();
        streamPlus.forEach(value -> newList.add(value));
        return newList;
    }
    
    /**
     * @return an immutable list containing the elements.
     */
    @Eager
    @Terminal
    public default ImmutableIntFuncList toImmutableList() {
        val streamPlus = intStreamPlus();
        return ImmutableIntFuncList.from(streamPlus);
    }
    
    /**
     * @return an Java list containing the elements.
     */
    @Eager
    @Terminal
    public default List<Integer> toJavaList() {
        val streamPlus = intStreamPlus();
        return streamPlus.mapToObj(Integer::valueOf).collect(Collectors.toList());
    }
    
    /**
     * @return a list containing the elements.
     */
    @Eager
    @Terminal
    public default FuncList<Integer> toList() {
        val streamPlus = intStreamPlus();
        return ImmutableFuncList.from(streamPlus.boxed());
    }
    
    /**
     * @return a mutable list containing the elements.
     */
    @Eager
    @Terminal
    public default List<Integer> toMutableList() {
        return toArrayList();
    }
    
    // -- join --
    /**
     * @return the concatenate of toString() of each elements.
     */
    @Eager
    @Terminal
    public default String join() {
        val streamPlus = intStreamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining());
    }
    
    /**
     * @return the concatenate of toString() of each elements with the given delimiter.
     */
    @Eager
    @Terminal
    public default String join(String delimiter) {
        val streamPlus = intStreamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining(delimiter));
    }
    
    // -- toListString --
    /**
     * @return the to string as a list for this stream.
     */
    @Eager
    @Terminal
    public default String toListString() {
        val streamPlus = intStreamPlus();
        val strValue = streamPlus.mapToObj(String::valueOf).collect(Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
    // -- toMap --
    /**
     * Create a map from the data using the keyMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Integer> toMap(IntFunction<KEY> keyMapper) {
        val streamPlus = intStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> i));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntFunction<KEY> keyMapper, IntFunction<VALUE> valueMapper) {
        val streamPlus = intStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i)));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntFunction<KEY> keyMapper, IntFunction<VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val streamPlus = intStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i), mergeFunction));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Integer> toMap(IntFunction<KEY> keyMapper, IntBinaryOperator mergeFunction) {
        val streamPlus = intStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> i, (a, b) -> mergeFunction.applyAsInt(a, b)));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Integer> toMap(IntAggregation<KEY> keyAggregation) {
        val aggregator = keyAggregation.newAggregator();
        return toMap(aggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntAggregation<KEY> keyAggregation, IntFunction<VALUE> valueMapper) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntFunction<KEY> keyMapper, IntAggregation<VALUE> valueAggregation) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntAggregation<KEY> keyAggregation, IntAggregation<VALUE> valueAggregation) {
        val keyAggregator = keyAggregation.newAggregator();
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyAggregator, valueAggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntAggregation<KEY> keyAggregation, IntFunction<VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntFunction<KEY> keyMapper, IntAggregation<VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(IntAggregation<KEY> keyAggregation, IntAggregation<VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyAggregator, valueAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Integer> toMap(IntAggregation<KEY> keyAggregation, IntBinaryOperator mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, mergeFunction);
    }
    
    // -- toSet --
    /**
     * @return  a set of the elements.
     */
    @Eager
    @Terminal
    public default Set<Integer> toSet() {
        val streamPlus = intStreamPlus();
        return streamPlus.boxed().collect(Collectors.toSet());
    }
}
