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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import lombok.val;

public interface AsStreamPlusWithGroupingBy<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /** Group the elements by determining the grouping keys */
    public default <KEY> FuncMap<KEY, FuncList<? super DATA>> groupingBy(Function<? super DATA, KEY> keyMapper) {
        Supplier  <Map<KEY, ArrayList<? super DATA>>>                                    supplier;
        BiConsumer<Map<KEY, ArrayList<? super DATA>>, ? super DATA>                      accumulator;
        BiConsumer<Map<KEY, ArrayList<? super DATA>>, Map<KEY, ArrayList<? super DATA>>> combiner;
        
        Supplier<ArrayList<? super DATA>>                         collectorSupplier = ArrayList::new;
        Function<ArrayList<? super DATA>, FuncList<? super DATA>> toFuncList         = array -> FuncList.from(array);
        
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
        val theMap = streamPlus().collect(supplier, accumulator, combiner);
        return ImmutableFuncMap
                    .from    (theMap)
                    .mapValue(toFuncList);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<DATA, KEY>                         keyMapper,
            Function<? super AsStreamPlus<DATA>, VALUE> aggregate) {
        FuncMap<KEY, FuncList<? super DATA>> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy.mapValue((Function)aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<DATA, KEY>                                   keyMapper,
            Supplier<Collector<? super DATA, ACCUMULATED, VALUE>> collectorSupplier) {
        FuncMap<KEY, FuncList<? super DATA>>    groupingBy = groupingBy(keyMapper);
        Function<? super FuncList<DATA>, VALUE> aggregate  = stream -> stream.collect(collectorSupplier.get());
        FuncMap<KEY, VALUE> mapValue = groupingBy.mapValue((Function)aggregate);
        return (FuncMap<KEY, VALUE>) mapValue;
    }
    
}
