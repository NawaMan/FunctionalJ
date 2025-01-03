// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

import functionalj.function.aggregator.DoubleAggregation;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.doublelist.ImmutableDoubleFuncList;
import functionalj.list.doublelist.StreamBackedDoubleFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsDoubleStreamPlusWithConversion {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /**
     * @return a iterator of this FuncList.
     */
    public default DoubleIteratorPlus iterator() {
        val streamPlus = doubleStreamPlus();
        return streamPlus.iterator();
    }
    
    /**
     * @return a spliterator of this FuncList.
     */
    public default Spliterator.OfDouble spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    /**
     * @return a functional list containing the elements.
     */
    @Eager
    @Terminal
    public default DoubleFuncList toFuncList() {
        val streamPlus = doubleStreamPlus();
        return new StreamBackedDoubleFuncList(streamPlus);
    }
    
    // -- toArray --
    /**
     * Map the data to double and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default double[] toDoubleArray(DoubleUnaryOperator toDouble) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.map(toDouble).toArray();
    }
    
    // -- toList --
    /**
     * @return the array list containing the elements.
     */
    @Eager
    @Terminal
    public default ArrayList<Double> toArrayList() {
        // TODO - This is not efficient but without knowing the size, it is not so easy to do efficiently
        // The proper solution for this is to have the stream itself contain the marker if it knows its size.
        // May be by using peekSize() method to check the size and the forEach to populate it.
        val streamPlus = doubleStreamPlus();
        val javaList = streamPlus.boxed().toJavaList();
        return new ArrayList<Double>(javaList);
    }
    
    /**
     * @return an immutable list containing the elements.
     */
    @Eager
    @Terminal
    public default ImmutableDoubleFuncList toImmutableList() {
        val streamPlus = doubleStreamPlus();
        return ImmutableDoubleFuncList.from(streamPlus);
    }
    
    /**
     * @return an Java list containing the elements.
     */
    @Eager
    @Terminal
    public default List<Double> toJavaList() {
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToObj(Double::valueOf).collect(Collectors.toList());
    }
    
    /**
     * @return a list containing the elements.
     */
    @Eager
    @Terminal
    public default FuncList<Double> toList() {
        val streamPlus = doubleStreamPlus();
        return ImmutableFuncList.from(streamPlus.boxed());
    }
    
    /**
     * @return a mutable list containing the elements.
     */
    @Eager
    @Terminal
    public default List<Double> toMutableList() {
        return toArrayList();
    }
    
    // -- join --
    /**
     * @return the concatenate of toString() of each elements.
     */
    @Eager
    @Terminal
    public default String join() {
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining());
    }
    
    /**
     * @return the concatenate of toString() of each elements with the given delimiter.
     */
    @Eager
    @Terminal
    public default String join(String delimiter) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining(delimiter));
    }
    
    // -- toListString --
    /**
     * @return the to string as a list for this stream.
     */
    @Eager
    @Terminal
    public default String toListString() {
        val streamPlus = doubleStreamPlus();
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
    public default <KEY> FuncMap<KEY, Double> toMap(DoubleFunction<KEY> keyMapper) {
        val streamPlus = doubleStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> i));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleFunction<KEY> keyMapper, DoubleFunction<VALUE> valueMapper) {
        val streamPlus = doubleStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i)));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleFunction<KEY> keyMapper, DoubleFunction<VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val streamPlus = doubleStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> valueMapper.apply(i), mergeFunction));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Double> toMap(DoubleFunction<KEY> keyMapper, DoubleBinaryOperator mergeFunction) {
        val streamPlus = doubleStreamPlus();
        val theMap = streamPlus.boxed().collect(Collectors.toMap(i -> keyMapper.apply(i), i -> i, (a, b) -> mergeFunction.applyAsDouble(a, b)));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, Double> toMap(DoubleAggregation<KEY> keyAggregation) {
        val aggregator = keyAggregation.newAggregator();
        return toMap(aggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleAggregation<KEY> keyAggregation, DoubleFunction<VALUE> valueMapper) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleFunction<KEY> keyMapper, DoubleAggregation<VALUE> valueAggregation) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleAggregation<KEY> keyAggregation, DoubleAggregation<VALUE> valueAggregation) {
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
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleAggregation<KEY> keyAggregation, DoubleFunction<VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleFunction<KEY> keyMapper, DoubleAggregation<VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(DoubleAggregation<KEY> keyAggregation, DoubleAggregation<VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
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
    public default <KEY> FuncMap<KEY, Double> toMap(DoubleAggregation<KEY> keyAggregation, DoubleBinaryOperator mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, mergeFunction);
    }
    
    /** Create a map from the data from the index and the value. */
    @Eager
    @Terminal
    public default FuncMap<Integer, Double> toMap() {
        return doubleStreamPlus().boxed().toMap();
    }
    
    /** Create a map from the data from the value to the index. */
    @Eager
    @Terminal
    public default FuncMap<Double, Integer> toMapRevert() {
        return doubleStreamPlus().boxed().toMapRevert();
    }
    
    // -- toSet --
    /**
     * @return  a set of the elements.
     */
    @Eager
    @Terminal
    public default Set<Double> toSet() {
        val streamPlus = doubleStreamPlus();
        return streamPlus.boxed().collect(Collectors.toSet());
    }
}
