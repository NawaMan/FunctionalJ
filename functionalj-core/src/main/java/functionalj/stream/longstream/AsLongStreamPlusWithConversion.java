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
package functionalj.stream.longstream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.stream.Collectors;
import functionalj.function.aggregator.LongAggregation;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.list.longlist.ImmutableLongFuncList;
import functionalj.list.longlist.LongFuncList;
import functionalj.list.longlist.StreamBackedLongFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsLongStreamPlusWithConversion {
    
    public LongStreamPlus longStreamPlus();
    
    /**
     * @return a iterator of this FuncList.
     */
    public default LongIteratorPlus iterator() {
        val streamPlus = longStreamPlus();
        return streamPlus.iterator();
    }
    
    /**
     * @return a spliterator of this FuncList.
     */
    public default Spliterator.OfLong spliterator() {
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
    public default LongFuncList toFuncList() {
        return new StreamBackedLongFuncList(this.longStreamPlus());
    }
    
    // -- toArray --
    @Eager
    @Terminal
    public default long[] toArray() {
        val streamPlus = longStreamPlus();
        return streamPlus.toArray();
    }
    
    /**
     * Map the data to int and return the int array of all the results.
     */
    @Eager
    @Terminal
    public default int[] toIntArray(LongToIntFunction toInt) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToInt(toInt).toArray();
    }
    
    /**
     * Map the data to double and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default double[] toDoubleArray(LongToDoubleFunction toDouble) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToDouble(toDouble).toArray();
    }
    
    /**
     * Map the data to double and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default double[] toDoubleArray() {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToDouble(l -> (double) l).toArray();
    }
    
    // -- toList --
    /**
     * @return the array list containing the elements.
     */
    @Eager
    @Terminal
    public default ArrayList<Long> toArrayList() {
        val streamPlus = longStreamPlus();
        val newList = new ArrayList<Long>();
        streamPlus.forEach(value -> newList.add(value));
        return newList;
    }
    
    /**
     * @return an immutable list containing the elements.
     */
    @Eager
    @Terminal
    public default ImmutableLongFuncList toImmutableList() {
        val streamPlus = longStreamPlus();
        return ImmutableLongFuncList.from(streamPlus);
    }
    
    /**
     * @return an Java list containing the elements.
     */
    @Eager
    @Terminal
    public default List<Long> toJavaList() {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(Long::valueOf).collect(Collectors.toList());
    }
    
    /**
     * @return a list containing the elements.
     */
    @Eager
    @Terminal
    public default FuncList<Long> toList() {
        val streamPlus = longStreamPlus();
        return ImmutableFuncList.from(streamPlus.boxed());
    }
    
    /**
     * @return a mutable list containing the elements.
     */
    @Eager
    @Terminal
    public default List<Long> toMutableList() {
        return toArrayList();
    }
    
    // -- join --
    /**
     * @return the concatenate of toString() of each elements.
     */
    @Eager
    @Terminal
    public default String join() {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining());
    }
    
    /**
     * @return the concatenate of toString() of each elements with the given delimiter.
     */
    @Eager
    @Terminal
    public default String join(String delimiter) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining(delimiter));
    }
    
    // -- toListString --
    /**
     * @return the to string as a list for this stream.
     */
    @Eager
    @Terminal
    public default String toListString() {
        val streamPlus = longStreamPlus();
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
    public default <KEY> FuncMap<KEY, Long> toMap(LongFunction<KEY> keyMapper) {
        val streamPlus = longStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> i));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongFunction<KEY> keyMapper, LongFunction<VALUE> valueMapper) {
        val streamPlus = longStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i)));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongFunction<KEY> keyMapper, LongFunction<VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val streamPlus = longStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i), mergeFunction));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Long> toMap(LongFunction<KEY> keyMapper, LongBinaryOperator mergeFunction) {
        val streamPlus = longStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> i, (a, b) -> mergeFunction.applyAsLong(a, b)));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Long> toMap(LongAggregation<KEY> keyAggregation) {
        val aggregator = keyAggregation.newAggregator();
        return toMap(aggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongAggregation<KEY> keyAggregation, LongFunction<VALUE> valueMapper) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongFunction<KEY> keyMapper, LongAggregation<VALUE> valueAggregation) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongAggregation<KEY> keyAggregation, LongAggregation<VALUE> valueAggregation) {
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
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongAggregation<KEY> keyAggregation, LongFunction<VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongFunction<KEY> keyMapper, LongAggregation<VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(LongAggregation<KEY> keyAggregation, LongAggregation<VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
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
    public default <KEY> FuncMap<KEY, Long> toMap(LongAggregation<KEY> keyAggregation, LongBinaryOperator mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, mergeFunction);
    }
    
    // -- toSet --
    /**
     * @return  a set of the elements.
     */
    @Eager
    @Terminal
    public default Set<Long> toSet() {
        val streamPlus = longStreamPlus();
        return streamPlus.boxed().collect(Collectors.toSet());
    }
}
