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
package functionalj.stream;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import functionalj.function.ToByteFunction;
import functionalj.function.aggregator.Aggregation;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;


public interface AsStreamPlusWithConversion<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    
    /** @return a iterator of this FuncList. */
    public default IteratorPlus<DATA> iterator() {
        val streamPlus = streamPlus();
        return streamPlus
                .iterator();
    }
    
    /** @return a spliterator of this FuncList. */
    public default Spliterator<DATA> spliterator() {
        val iterator = iterator();
        return Spliterators
                .spliteratorUnknownSize(iterator, 0);
    }
    
    /** 
     * @return a functional list containing the elements.
     * 
     * Note: This method will materialize the elements and put in a list.
     **/
    @Eager
    @Terminal
    public default FuncList<DATA> toFuncList() {
        return FuncList.from(streamPlus());
    }
    
    //-- toArray --
    
    @Eager
    @Terminal
    public default Object[] toArray() {
        val streamPlus = streamPlus();
        return streamPlus
                .toArray();
    }
    
    @Eager
    @Terminal
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        val streamPlus = streamPlus();
        return streamPlus
                .toArray(generator);
    }
    
    //-- toArray --
    
    /** Map the data to byte and return the byte array of all the results. */
    @Eager
    @Terminal
    public default byte[] toByteArray(ToByteFunction<DATA> toByte) {
        val streamPlus = streamPlus();
        val byteArray  = new ByteArrayOutputStream();
        streamPlus
        .forEach(d -> {
            byte b = toByte.apply(d);
            byteArray.write(b);
        });
        return byteArray
                .toByteArray();
    }
    
    /** Map the data to int and return the int array of all the results. */
    @Eager
    @Terminal
    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToInt(toInt)
                .toArray ();
    }
    
    /** Map the data to double and return the byte array of all the results. */
    @Eager
    @Terminal
    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToDouble(toDouble)
                .toArray    ();
    }
    
    //-- toList --
    
    /** @return the array list containing the elements. */
    @Eager
    @Terminal
    public default ArrayList<DATA> toArrayList() {
        val streamPlus = streamPlus();
        val newList    = new ArrayList<DATA>();
        streamPlus
            .forEach(value -> newList.add(value));
        return newList;
    }
    
    /** @return an immutable list containing the elements. */
    @Eager
    @Terminal
    public default ImmutableFuncList<DATA> toImmutableList() {
        val streamPlus = streamPlus();
        return ImmutableFuncList.from(streamPlus);
    }
    
    /** @return an Java list containing the elements. */
    @Eager
    @Terminal
    public default List<DATA> toJavaList() {
        val streamPlus = streamPlus();
        return streamPlus
                .collect(Collectors.toList());
    }
    
    /** @return a list containing the elements. */
    @Eager
    @Terminal
    public default FuncList<DATA> toList() {
        return toImmutableList();
    }
    
    /** @return a mutable list containing the elements. */
    @Eager
    @Terminal
    public default List<DATA> toMutableList() {
        return toArrayList();
    }
    
    //-- join --
    
    /** @return the concatenate of toString() of each elements. */
    @Eager
    @Terminal
    public default String join() {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(StrFuncs::toStr)
                .collect (Collectors.joining());
    }
    
    /** @return the concatenate of toString() of each elements with the given delimiter. */
    @Eager
    @Terminal
    public default String join(String delimiter) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(StrFuncs::toStr)
                .collect (Collectors.joining(delimiter));
    }
    
    //-- toListString --
    
    /** @return the to string as a list for this stream. */
    @Eager
    @Terminal
    public default String toListString() {
        val streamPlus = streamPlus();
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
    public default <KEY> FuncMap<KEY, DATA> toMap(Function<? super DATA, KEY> keyMapper) {
        val streamPlus = streamPlus();
        val theMap     = streamPlus.collect(Collectors.toMap(keyMapper, data -> data));
        return ImmutableFuncMap.from(theMap);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, KEY>   keyMapper,
            Function<? super DATA, VALUE> valueMapper) {
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
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, KEY>   keyMapper,
            Function<? super DATA, VALUE> valueMapper,
            BinaryOperator<VALUE>         mergeFunction) {
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
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, KEY> keyMapper,
            BinaryOperator<DATA>        mergeFunction) {
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
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Aggregation<? super DATA, KEY> keyAggregation,
            Function<? super DATA, VALUE>  valueMapper) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, KEY>      keyMapper,
            Aggregation<? super DATA, VALUE> valueAggregation) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * This method throw an exception with duplicate keys.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Aggregation<? super DATA, KEY>   keyAggregation,
            Aggregation<? super DATA, VALUE> valueAggregation) {
        val keyAggregator   = keyAggregation.newAggregator();
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyAggregator, valueAggregator);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Aggregation<? super DATA, KEY> keyAggregation,
            Function<? super DATA, VALUE>  valueMapper,
            BinaryOperator<VALUE>          mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, valueMapper, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, KEY>      keyMapper,
            Aggregation<? super DATA, VALUE> valueAggregation,
            BinaryOperator<VALUE>            mergeFunction) {
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyMapper, valueAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper and the valueMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Aggregation<? super DATA, KEY>   keyAggregation,
            Aggregation<? super DATA, VALUE> valueAggregation,
            BinaryOperator<VALUE>         mergeFunction) {
        val keyAggregator   = keyAggregation.newAggregator();
        val valueAggregator = valueAggregation.newAggregator();
        return toMap(keyAggregator, valueAggregator, mergeFunction);
    }
    
    /**
     * Create a map from the data using the keyMapper.
     * When a value mapped to the same key, use the merge function to merge the value.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Aggregation<? super DATA, KEY> keyAggregation,
            BinaryOperator<DATA>           mergeFunction) {
        val keyAggregator = keyAggregation.newAggregator();
        return toMap(keyAggregator, mergeFunction);
    }
    
    //-- toSet --
    
    /** @return  a set of the elements. */
    @Eager
    @Terminal
    public default Set<DATA> toSet() {
        val streamPlus = streamPlus();
        return streamPlus.collect(Collectors.toSet());
    }
    
}
