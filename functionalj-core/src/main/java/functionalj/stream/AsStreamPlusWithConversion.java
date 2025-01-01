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
package functionalj.stream;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.function.Func;
import functionalj.function.Func3;
import functionalj.function.ToByteFunction;
import functionalj.function.aggregator.Aggregation;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface AsStreamPlusWithConversion<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /**
     * @return a iterator of this FuncList.
     */
    public default IteratorPlus<DATA> iterator() {
        val streamPlus = streamPlus();
        return streamPlus.iterator();
    }
    
    /**
     * @return a spliterator of this FuncList.
     */
    public default Spliterator<DATA> spliterator() {
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
    public default FuncList<DATA> toFuncList() {
        return FuncList.from(streamPlus());
    }
    
    // -- toArray --
    @Eager
    @Terminal
    public default Object[] toArray() {
        val streamPlus = streamPlus();
        return streamPlus.toArray();
    }
    
    @Eager
    @Terminal
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        val streamPlus = streamPlus();
        return streamPlus.toArray(generator);
    }
    
    // -- toArray --
    /**
     * Map the data to byte and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default byte[] toByteArray(ToByteFunction<DATA> toByte) {
        val streamPlus = streamPlus();
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
    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
        val streamPlus = streamPlus();
        return streamPlus.mapToInt(toInt).toArray();
    }
    
    /**
     * Map the data to double and return the byte array of all the results.
     */
    @Eager
    @Terminal
    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
        val streamPlus = streamPlus();
        return streamPlus.mapToDouble(toDouble).toArray();
    }
    
    // -- toList --
    /**
     * @return the array list containing the elements.
     */
    @Eager
    @Terminal
    public default ArrayList<DATA> toArrayList() {
        val streamPlus = streamPlus();
        val newList = new ArrayList<DATA>();
        streamPlus.forEach(value -> newList.add(value));
        return newList;
    }
    
    /**
     * @return an immutable list containing the elements.
     */
    @Eager
    @Terminal
    public default ImmutableFuncList<DATA> toImmutableList() {
        val streamPlus = streamPlus();
        return ImmutableFuncList.from(streamPlus);
    }
    
    /**
     * @return an Java list containing the elements.
     */
    @Eager
    @Terminal
    public default List<DATA> toJavaList() {
        val streamPlus = streamPlus();
        return streamPlus.collect(Collectors.toList());
    }
    
    /**
     * @return a list containing the elements.
     */
    @Eager
    @Terminal
    public default FuncList<DATA> toList() {
        return toImmutableList();
    }
    
    /**
     * @return a mutable list containing the elements.
     */
    @Eager
    @Terminal
    public default List<DATA> toMutableList() {
        return toArrayList();
    }
    
    // -- join --
    /**
     * @return the concatenate of toString() of each elements.
     */
    @Eager
    @Terminal
    public default String join() {
        val streamPlus = streamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining());
    }
    
    /**
     * @return the concatenate of toString() of each elements with the given delimiter.
     */
    @Eager
    @Terminal
    public default String join(String delimiter) {
        val streamPlus = streamPlus();
        return streamPlus.mapToObj(StrFuncs::toStr).collect(Collectors.joining(delimiter));
    }
    
    // -- toListString --
    /**
     * @return the to string as a list for this stream.
     */
    @Eager
    @Terminal
    public default String toListString() {
        val streamPlus = streamPlus();
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
    public default <KEY> FuncMap<KEY, DATA> toMap(Function<? super DATA, KEY> keyMapper) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.collect(Collectors.toMap(keyMapper, data -> data));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Function<? super DATA, KEY> keyMapper, Function<? super DATA, VALUE> valueMapper) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.collect(Collectors.toMap(keyMapper, valueMapper));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Function<? super DATA, KEY> keyMapper, Function<? super DATA, VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, DATA> toMap(Function<? super DATA, KEY> keyMapper, BinaryOperator<DATA> mergeFunction) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.collect(Collectors.toMap(keyMapper, value -> value, mergeFunction));
        return (FuncMap<KEY, DATA>) ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, DATA> toMap(Aggregation<? super DATA, KEY> keyAggregation) {
        val aggregator = keyAggregation.newAggregator();
        return toMap(aggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Aggregation<? super DATA, KEY> keyAggregation, Function<? super DATA, VALUE> valueMapper) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Function<? super DATA, KEY> keyMapper, Aggregation<? super DATA, VALUE> valueAggregation) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Aggregation<? super DATA, KEY> keyAggregation, Aggregation<? super DATA, VALUE> valueAggregation) {
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
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Aggregation<? super DATA, KEY> keyAggregation, Function<? super DATA, VALUE> valueMapper, BinaryOperator<VALUE> mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Function<? super DATA, KEY> keyMapper, Aggregation<? super DATA, VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(Aggregation<? super DATA, KEY> keyAggregation, Aggregation<? super DATA, VALUE> valueAggregation, BinaryOperator<VALUE> mergeFunction) {
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
    public default <KEY> FuncMap<KEY, DATA> toMap(Aggregation<? super DATA, KEY> keyAggregation, BinaryOperator<DATA> mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data from the index and the value.
     */
    @Eager
    @Terminal
    public default FuncMap<Integer, DATA> toMap() {
        val streamPlus    = streamPlus();
        val index         = new AtomicInteger();
        val theMap        = streamPlus.collect(Collectors.toMap(__-> index.getAndIncrement(), Func.it()));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data from the value to the index.
     */
    @Eager
    @Terminal
    public default FuncMap<DATA, Integer> toMapRevert() {
        val streamPlus    = streamPlus();
        val index         = new AtomicInteger();
        val theMap        = streamPlus.collect(Collectors.toMap(Func.it(), __-> index.getAndIncrement()));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the mapper to tuple.
     * When a value mapped to the same key, use the merge function to merge the value.
     * This is actually useful if the data is already a Tuple.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMapTuple(Function<DATA, Tuple2<KEY, VALUE>> mapper) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.map(mapper).collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the mapper to tuple.
     * When a value mapped to the same key, use the merge function to merge the value.
     * This is actually useful if the data is already a Tuple.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMapTuple(Function<DATA, Tuple2<KEY, VALUE>> mapper, BinaryOperator<VALUE> mergeFunction) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.map(mapper).collect(Collectors.toMap(Tuple2::_1, Tuple2::_2, mergeFunction));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the mapper to tuple.
     * When a value mapped to the same key, use the merge function to merge the value.
     * This is actually useful if the data is already a Tuple.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMapEntry(Function<DATA, Map.Entry<KEY, VALUE>> mapper) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.map(mapper).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the mapper to tuple.
     * When a value mapped to the same key, use the merge function to merge the value.
     * This is actually useful if the data is already a Tuple.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMapEntry(Function<DATA, Map.Entry<KEY, VALUE>> mapper, BinaryOperator<VALUE> mergeFunction) {
        val streamPlus = streamPlus();
        val theMap = streamPlus.map(mapper).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction));
        return ImmutableFuncMap.from(theMap);
    }
    
    //-- Zip To Map --
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * An exception will be thrown on any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(AsStreamPlus<VALUE> values) {
        return zipToMap(values.stream());
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * An exception will be thrown on any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(Stream<VALUE> values) {
        return zipToMap(values, ZipWithOption.RequireBoth, (key, a, b) -> {
            if (!Objects.equals(a, b))
                throw new IllegalStateException(String.format("Duplicate key: key=%s, values=%s and %s", key, a, b));
            
            return a;
        });
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(AsStreamPlus<VALUE> values, BinaryOperator<VALUE> reducer) {
        return zipToMap(values.stream(), ZipWithOption.AllowUnpaired, reducer);
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(AsStreamPlus<VALUE> values, ZipWithOption zipWithOption, BinaryOperator<VALUE> reducer) {
        return zipToMap(values.stream(), reducer);
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(Stream<VALUE> values, BinaryOperator<VALUE> reducer) {
        return zipToMap(values, ZipWithOption.AllowUnpaired, (key, a, b) -> reducer.apply(a, b));
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(Stream<VALUE> values, ZipWithOption zipWithOption, BinaryOperator<VALUE> reducer) {
        return zipToMap(values, zipWithOption, (key, a, b) -> reducer.apply(a, b));
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(AsStreamPlus<VALUE> values, Func3<DATA, VALUE, VALUE, VALUE> combiner) {
        return zipToMap(values, ZipWithOption.AllowUnpaired, combiner);
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(AsStreamPlus<VALUE> values, ZipWithOption zipWithOption, Func3<DATA, VALUE, VALUE, VALUE> combiner) {
        return zipToMap(values.stream(), zipWithOption, combiner);
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(Stream<VALUE> values, Func3<DATA, VALUE, VALUE, VALUE> combiner) {
        return zipToMap(values, ZipWithOption.AllowUnpaired, combiner);
    }
    
    /**
     * Create a map by zipping with another list.
     * The zipping stop when all the element of this list is exhausted.
     * If not enough element from another list, the value of that entry will be null.
     * Any null value in this list will be skipped -- meaning that the value at the corresponding list will also be skipped.
     * The combiner will be used to combine values of any duplicated key.
     * 
     * NOTE: This is eager operation so ... it must not be used with infinite stream.
     */
    @Eager
    @Terminal
    public default <VALUE> FuncMap<DATA, VALUE> zipToMap(Stream<VALUE> values, ZipWithOption zipWithOption, Func3<DATA, VALUE, VALUE, VALUE> combiner) {
        val streamPlus = streamPlus();
        val resultMap  = new LinkedHashMap<DATA, VALUE>();
        streamPlus
        .zipWith(values, zipWithOption, (data, value) -> {
            resultMap
            .compute(data, (__, v) -> {
                return (v == null)
                        ? value
                        : combiner.apply(data, v, value);
            });
            
            return null;
        })
        .forEach(__ -> {});
        return ImmutableFuncMap.from(resultMap);
    }
    
    // -- toSet --
    /**
     * @return  a set of the elements.
     */
    @Eager
    @Terminal
    public default Set<DATA> toSet() {
        val streamPlus = streamPlus();
        return streamPlus.collect(Collectors.toSet());
    }
}
