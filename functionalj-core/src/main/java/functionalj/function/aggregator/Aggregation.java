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
package functionalj.function.aggregator;

import functionalj.function.Func1;
import functionalj.stream.AsStreamPlus;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.collect.CollectorToDoublePlus;
import functionalj.stream.collect.CollectorToIntPlus;
import functionalj.stream.collect.CollectorToLongPlus;
import lombok.val;

@FunctionalInterface
public interface Aggregation<SOURCE, TARGET> extends Func1<AsStreamPlus<SOURCE>, TARGET> {
    
    public static <S, A, T> Aggregation<S, T> from(CollectorPlus<S, A, T> collector) {
        return () -> collector;
    }
    
    public static <S, A> AggregationToInt<S> from(CollectorToIntPlus<S, A> collector) {
        return () -> collector;
    }
    
    public static <S, A> AggregationToLong<S> from(CollectorToLongPlus<S, A> collector) {
        return () -> collector;
    }
    
    public static <S, A> AggregationToDouble<S> from(CollectorToDoublePlus<S, A> collector) {
        return () -> collector;
    }
    
    public static <S, A> AggregationToInt<S> forInt(CollectorToIntPlus<S, A> collector) {
        return () -> collector;
    }
    
    public static <S, A> AggregationToLong<S> forLong(CollectorToLongPlus<S, A> collector) {
        return () -> collector;
    }
    
    public static <S, A> AggregationToDouble<S> forDouble(CollectorToDoublePlus<S, A> collector) {
        return () -> collector;
    }
    
    
    public CollectorPlus<SOURCE, ?, TARGET> collector();
    
    
    public default TARGET applyUnsafe(AsStreamPlus<SOURCE> stream) throws Exception {
        val collector = collector();
        return stream.collect(collector);
    }
    
    public default Aggregator<SOURCE, TARGET> newAccumulator() {
        val collector = collector();
        return new Aggregator<>(collector);
    }
    
    public default <INPUT> Aggregation<INPUT, TARGET> of(Func1<INPUT, SOURCE> mapper) {
        val newCollector = collector().of(mapper);
        return () -> newCollector;
    }
    
}
