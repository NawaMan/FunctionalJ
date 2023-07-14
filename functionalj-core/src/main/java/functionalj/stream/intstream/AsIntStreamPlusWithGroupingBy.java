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
package functionalj.stream.intstream;

import static functionalj.function.Func.f;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import functionalj.function.aggregator.IntAggregation;
import functionalj.list.intlist.IntFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import lombok.val;

public interface AsIntStreamPlusWithGroupingBy {

    public IntStreamPlus intStreamPlus();

    /**
     * Group the elements by determining the grouping keys
     */
    public default <KEY> FuncMap<KEY, IntFuncList> groupingBy(IntFunction<KEY> keyMapper) {
        val toFuncList = f((GrowOnlyIntArray array) -> array.toFuncList());
        val newArray = f(() -> new GrowOnlyIntArray());
        val streamPlus = this.intStreamPlus();
        Supplier<Map<KEY, GrowOnlyIntArray>> supplier;
        BiConsumer<Map<KEY, GrowOnlyIntArray>, Integer> accumulator;
        BiConsumer<Map<KEY, GrowOnlyIntArray>, Map<KEY, GrowOnlyIntArray>> combiner;
        supplier = LinkedHashMap::new;
        accumulator = (map, each) -> {
            val key = keyMapper.apply(each);
            map.compute(key, (k, a) -> {
                if (a == null) {
                    a = newArray.get();
                }
                a.add(each);
                return a;
            });
        };
        combiner = (map1, map2) -> map1.putAll(map2);
        val theMap = streamPlus.boxed().collect(supplier, accumulator, combiner);
        return ImmutableFuncMap.from(theMap).mapValue(toFuncList);
    }

    /**
     * Group the elements by determining the grouping keys
     */
    public default <KEY> FuncMap<KEY, IntFuncList> groupingBy(IntAggregation<KEY> keyAggregation) {
        val keyMapper = keyAggregation.newAggregator();
        return groupingBy(keyMapper);
    }

    /**
     * Group the elements by determining the grouping keys and aggregate the result
     */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(IntFunction<KEY> keyMapper, Function<IntFuncList, VALUE> aggregate) {
        return groupingBy(keyMapper).mapValue(aggregate);
    }

    /**
     * Group the elements by determining the grouping keys and aggregate the result
     */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(IntAggregation<KEY> keyAggregation, Function<IntFuncList, VALUE> aggregate) {
        val keyMapper = keyAggregation.newAggregator();
        return groupingBy(keyMapper, aggregate);
    }

    /**
     * Group the elements by determining the grouping keys and aggregate the result
     */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(IntFunction<KEY> keyMapper, IntAggregation<VALUE> aggregate) {
        val valueMapper = f((IntFuncList list) -> list.aggregate(aggregate));
        return groupingBy(keyMapper).mapValue(valueMapper);
    }

    /**
     * Group the elements by determining the grouping keys and aggregate the result
     */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(IntAggregation<KEY> keyAggregation, IntAggregation<VALUE> aggregate) {
        val keyMapper = keyAggregation.newAggregator();
        return groupingBy(keyMapper, aggregate);
    }

    /**
     * Group the elements by determining the grouping keys and aggregate the result
     */
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(IntFunction<KEY> keyMapper, Supplier<IntCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
        val valueMapper = f((IntFuncList list) -> list.collect(collectorSupplier.get()));
        return groupingBy(keyMapper).mapValue(valueMapper);
    }

    /**
     * Group the elements by determining the grouping keys and aggregate the result
     */
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(IntAggregation<KEY> keyAggregation, Supplier<IntCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
        val keyMapper = keyAggregation.newAggregator();
        return groupingBy(keyMapper, collectorSupplier);
    }
}
