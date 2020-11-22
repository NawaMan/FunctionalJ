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
package functionalj.streamable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;
import lombok.val;

public interface StreamableWithGroupingBy<DATA>
    extends StreamableWithMapToTuple<DATA> {
    
    /** Group the elements by determining the grouping keys */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <KEY> FuncMap<KEY, Streamable<? super DATA>> groupingBy(Function<? super DATA, KEY> keyMapper) {
        Supplier  <Map<KEY, ArrayList<? super DATA>>>                                    supplier;
        BiConsumer<Map<KEY, ArrayList<? super DATA>>, ? super DATA>                      accumulator;
        BiConsumer<Map<KEY, ArrayList<? super DATA>>, Map<KEY, ArrayList<? super DATA>>> combiner;
        
        Supplier<ArrayList<? super DATA>> collectorSupplier = ArrayList::new;
        Function<ArrayList<? super DATA>, Streamable<? super DATA>> toStreamable 
                = array -> (Streamable)(()->StreamPlus.from(array.stream()));
        
        supplier = LinkedHashMap::new;
        accumulator = (map, each) -> {
            var key = keyMapper.apply(each);
            map.compute(key, (k, a)->{
                if (a == null) {
                    a = collectorSupplier.get();
                }
                a.add(each);
                return a;
            });
        };
        combiner = (map1, map2) -> map1.putAll(map2);
        var theMap = streamPlus().collect(supplier, accumulator, combiner);
        return ImmutableMap
                    .from    (theMap)
                    .mapValue(toStreamable);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, KEY>                       keyMapper,
            Function<? super Streamable<? super DATA>, VALUE> aggregate) {
        return groupingBy(keyMapper)
                .mapValue(aggregate);
    }

    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, KEY>          keyMapper,
            StreamProcessor<? super DATA, VALUE> processor) {
        FuncMap<KEY, Streamable<? super DATA>> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy
                .mapValue(stream -> stream.calculate((StreamProcessor) processor));
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY>                 keyMapper,
            Supplier<Collector<? super DATA, ACCUMULATED, VALUE>> collectorSupplier) {
        return groupingBy(keyMapper)
                .mapValue(stream -> (VALUE)stream.collect((Collector)collectorSupplier.get()));
    }
    
}