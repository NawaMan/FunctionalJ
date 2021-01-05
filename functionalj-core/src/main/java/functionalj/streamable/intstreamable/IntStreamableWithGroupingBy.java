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
package functionalj.streamable.intstreamable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamProcessor;
import lombok.val;


public interface IntStreamableWithGroupingBy extends IntStreamableWithMapToTuple {
    
    /** Group the elements by determining the grouping keys */
    public default <KEY> FuncMap<KEY, IntStreamable> groupingBy(IntFunction<KEY> keyMapper) {
        // TODO - Avoid using boxed.
        Supplier  <Map<KEY, ArrayList<Integer>>>                               supplier;
        BiConsumer<Map<KEY, ArrayList<Integer>>, Integer>                      accumulator;
        BiConsumer<Map<KEY, ArrayList<Integer>>, Map<KEY, ArrayList<Integer>>> combiner;
        
        Supplier<ArrayList<Integer>> collectorSupplier = ArrayList::new;
        Function<ArrayList<Integer>, IntStreamable> toStreamable
                = array -> IntStreamable.from(()->IntStreamPlus.from(array.stream().mapToInt(Integer::intValue)));
                
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
        val theMap = intStream().boxed().collect(supplier, accumulator, combiner);
        return ImmutableMap
                    .from    (theMap)
                    .mapValue(toStreamable);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<KEY>                       keyMapper,
            Function<? super IntStreamable, VALUE> aggregate) {
        return groupingBy(keyMapper)
                .mapValue(aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<KEY>          keyMapper,
            IntStreamProcessor<VALUE> processor) {
        FuncMap<KEY, IntStreamable> groupingBy = groupingBy(keyMapper);
        return groupingBy
                .mapValue(stream -> stream.calculate(processor));
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            IntFunction<? extends KEY>                    keyMapper,
            Supplier<? extends IntStreamProcessor<VALUE>> processorSupplier) {
        return groupingBy(keyMapper)
                .mapValue(stream -> stream.calculate(processorSupplier.get()));
    }
    
}