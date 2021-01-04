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
package functionalj.list.doublelist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import lombok.val;


public interface DoubleFuncListWithGroupingBy extends DoubleFuncListWithMapToTuple {
    
    /** Group the elements by determining the grouping keys */
    public default <KEY> FuncMap<KEY, DoubleFuncList> groupingBy(DoubleFunction<KEY> keyMapper) {
        // TODO - Avoid using boxed.
        Supplier  <Map<KEY, ArrayList<Double>>>                              supplier;
        BiConsumer<Map<KEY, ArrayList<Double>>, Double>                      accumulator;
        BiConsumer<Map<KEY, ArrayList<Double>>, Map<KEY, ArrayList<Double>>> combiner;
        
        Supplier<ArrayList<Double>> collectorSupplier = ArrayList::new;
        Function<ArrayList<Double>, DoubleFuncList> toStreamable
                = array -> DoubleFuncList.from((DoubleStreamable)(()->DoubleStreamPlus.from(array.stream().mapToDouble(Double::doubleValue))));
                
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
        val theMap = doubleStream().boxed().collect(supplier, accumulator, combiner);
        return ImmutableMap
                    .from    (theMap)
                    .mapValue(toStreamable);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            DoubleFunction<KEY>                     keyMapper,
            Function<? super DoubleFuncList, VALUE> aggregate) {
        return groupingBy(keyMapper)
                .mapValue(aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            DoubleFunction<KEY>          keyMapper,
            DoubleStreamProcessor<VALUE> processor) {
        FuncMap<KEY, DoubleFuncList> groupingBy = groupingBy(keyMapper);
        return groupingBy
                .mapValue(stream -> stream.calculate(processor));
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            DoubleFunction<? extends KEY>                    keyMapper,
            Supplier<? extends DoubleStreamProcessor<VALUE>> processorSupplier) {
        return groupingBy(keyMapper)
                .mapValue(stream -> stream.calculate(processorSupplier.get()));
    }
    
}
