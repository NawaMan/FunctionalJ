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
package functionalj.stream.doublestream;

import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.map.FuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import lombok.val;


public interface DoubleStreamPlusWithGroupingBy extends AsDoubleStreamPlus {
    
    /** Group the elements by determining the grouping keys */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DoubleStreamPlus> groupingBy(
            DoubleFunction<? extends KEY> keyMapper) {
        DoubleStreamable streamable = () -> doubleStreamPlus();
        return (FuncMap<KEY, DoubleStreamPlus>) streamable
                .groupingBy(keyMapper)
                .mapValue  (DoubleStreamable::doubleStreamPlus);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    //Eager
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            DoubleFunction<? extends KEY>     keyMapper,
            Function<DoubleStreamPlus, VALUE> aggregate) {
        val groupingBy = groupingBy(keyMapper);
        val mapValue   = groupingBy.mapValue(aggregate);
        return (FuncMap<KEY, VALUE>)mapValue;
    }
    
    //Eager
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            DoubleFunction<? extends KEY> keyMapper,
            DoubleStreamProcessor<VALUE>  processor) {
        Function<DoubleStreamPlus, VALUE> aggregate = (DoubleStreamPlus stream) -> {
            return (VALUE)processor.process((DoubleStreamPlus)stream);
        };
        return groupingBy(keyMapper, aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings("unchecked")
    public default <KEY, ACCUMULATED, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            DoubleFunction<? extends KEY>                     keyMapper,
            Supplier<DoubleCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
        DoubleStreamable streamable = () -> doubleStreamPlus();
        return (FuncMap<KEY, VALUE>) streamable
                .groupingBy(keyMapper, collectorSupplier);
    }
    
}
