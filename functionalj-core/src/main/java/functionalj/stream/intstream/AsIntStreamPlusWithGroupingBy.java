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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import functionalj.list.intlist.IntFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import lombok.val;

public interface AsIntStreamPlusWithGroupingBy {
    
    public IntStreamPlus intStreamPlus();
    
    /** Group the elements by determining the grouping keys */
    public default <KEY> FuncMap<KEY, IntFuncList> groupingBy(IntFunction<KEY> keyMapper) {
        Supplier  <Map<KEY, GrowOnlyIntArray>>                              supplier;
        BiConsumer<Map<KEY, GrowOnlyIntArray>, Integer>                     accumulator;
        BiConsumer<Map<KEY, GrowOnlyIntArray>, Map<KEY, GrowOnlyIntArray>>  combiner;
        
        Supplier<GrowOnlyIntArray>              collectorSupplier = GrowOnlyIntArray::new;
        Function<GrowOnlyIntArray, IntFuncList> toFuncList         = array -> array.toFuncList();
        
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
        val theMap = intStreamPlus().boxed().collect(supplier, accumulator, combiner);
        return ImmutableMap
                    .from    (theMap)
                    .mapValue(toFuncList);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<KEY>             keyMapper,
            Function<IntFuncList, VALUE> aggregate) {
        FuncMap<KEY, IntFuncList> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy.mapValue((Function)aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<KEY>                               keyMapper,
            Supplier<IntCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
        FuncMap<KEY, IntFuncList>    groupingBy = groupingBy(keyMapper);
        Function<IntFuncList, VALUE> aggregate  = stream -> stream.collect(collectorSupplier.get());
        FuncMap<KEY, VALUE> mapValue = groupingBy.mapValue((Function)aggregate);
        return (FuncMap<KEY, VALUE>) mapValue;
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<KEY>          keyMapper,
            IntStreamProcessor<VALUE> processor) {
        FuncMap<KEY, IntFuncList> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy
                .mapValue(stream -> stream.calculate((IntStreamProcessor) processor.asIntStreamProcessor()));
    }
    
}
