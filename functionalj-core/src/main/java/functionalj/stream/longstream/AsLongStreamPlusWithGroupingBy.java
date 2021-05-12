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
package functionalj.stream.longstream;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Supplier;

import functionalj.list.longlist.LongFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;

public interface AsLongStreamPlusWithGroupingBy {
    
    public LongStreamPlus longStreamPlus();
    
    /** Group the elements by determining the grouping keys */
    public default <KEY> FuncMap<KEY, LongFuncList> groupingBy(LongFunction<KEY> keyMapper) {
        Supplier  <Map<KEY, GrowOnlyLongArray>>                               supplier;
        BiConsumer<Map<KEY, GrowOnlyLongArray>, Long>                         accumulator;
        BiConsumer<Map<KEY, GrowOnlyLongArray>, Map<KEY, GrowOnlyLongArray>>  combiner;
        
        Supplier<GrowOnlyLongArray>               collectorSupplier = GrowOnlyLongArray::new;
        Function<GrowOnlyLongArray, LongFuncList> toFuncList         = array -> array.toFuncList();
        
        supplier = LinkedHashMap::new;
        accumulator = (map, each) -> {
            val key = keyMapper.apply(each);
            map.compute(key, (k, a)->{
                if (a == null) {
                    a = collectorSupplier.get();
                }
                a.add(each);
                return a;
            });
        };
        combiner = (map1, map2) -> map1.putAll(map2);
        val theMap = longStreamPlus().boxed().collect(supplier, accumulator, combiner);
        return ImmutableFuncMap
                    .from    (theMap)
                    .mapValue(toFuncList);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            LongFunction<KEY>             keyMapper,
            Function<LongFuncList, VALUE> aggregate) {
        FuncMap<KEY, LongFuncList> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy.mapValue((Function)aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            LongFunction<KEY>                               keyMapper,
            Supplier<LongCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
        FuncMap<KEY, LongFuncList>    groupingBy = groupingBy(keyMapper);
        Function<LongFuncList, VALUE> aggregate  = stream -> stream.collect(collectorSupplier.get());
        FuncMap<KEY, VALUE> mapValue = groupingBy.mapValue((Function)aggregate);
        return (FuncMap<KEY, VALUE>) mapValue;
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            LongFunction<KEY>            keyMapper,
            AsLongStreamProcessor<VALUE> processor) {
        FuncMap<KEY, LongFuncList> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy
                .mapValue(stream -> stream.calculate((LongStreamProcessor) processor.asLongStreamProcessor()));
    }
    
}
