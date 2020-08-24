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
package functionalj.stream.intstream;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import functionalj.map.FuncMap;
import functionalj.stream.IntCollectorPlus;
import functionalj.stream.IntStreamProcessor;
import functionalj.stream.makers.Eager;
import functionalj.stream.makers.Terminal;
import functionalj.streamable.intstreamable.IntStreamable;
import lombok.val;

public interface IntStreamPlusWithGroupingBy extends AsIntStreamPlus {
    
    /** Group the elements by determining the grouping keys */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, IntStreamPlus> groupingBy(
            IntFunction<? extends KEY> keyMapper) {
        IntStreamable streamable = () -> streamPlus();
        return (FuncMap<KEY, IntStreamPlus>) streamable
                .groupingBy(keyMapper)
                .mapValue  (IntStreamable::streamPlus);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    //Eager
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<? extends KEY>     keyMapper,
            Function<IntStreamPlus, VALUE> aggregate) {
        val groupingBy = groupingBy(keyMapper);
        val mapValue = groupingBy.mapValue(aggregate);
        return (FuncMap<KEY, VALUE>)mapValue;
    }
    
    //Eager
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<? extends KEY> keyMapper,
            IntStreamProcessor<VALUE>  processor) {
        Function<IntStreamPlus, VALUE> aggregate = (IntStreamPlus stream) -> {
            return (VALUE)processor.process((IntStreamPlus)stream);
        };
        return groupingBy(keyMapper, aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings("unchecked")
    public default <KEY, ACCUMULATED, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            IntFunction<? extends KEY>                     keyMapper,
            Supplier<IntCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
        IntStreamable streamable = () -> streamPlus();
        return (FuncMap<KEY, VALUE>) streamable
                .groupingBy(keyMapper, collectorSupplier);
    }
    
}
