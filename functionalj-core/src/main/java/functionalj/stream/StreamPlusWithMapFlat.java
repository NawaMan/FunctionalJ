// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.function.Function;
import functionalj.function.aggregator.Aggregation;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.list.longlist.LongFuncList;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import lombok.val;

public interface StreamPlusWithMapFlat<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <T> StreamPlus<T> mapFlat(Function<? super DATA, ? extends Iterable<? extends T>> mapper) {
        return streamPlus().flatMap(item -> (StreamPlus) StreamPlus.from(mapper.apply(item)));
    }
    
    public default IntStreamPlus mapFlatToInt(Function<? super DATA, ? extends IntFuncList> mapper) {
        return streamPlus().map(mapper).flatMapToInt(list -> list.intStreamPlus());
    }
    
    public default LongStreamPlus mapFlatToLong(Function<? super DATA, ? extends LongFuncList> mapper) {
        return streamPlus().map(mapper).flatMapToLong(list -> list.longStreamPlus());
    }
    
    public default DoubleStreamPlus mapFlatToDouble(Function<? super DATA, ? extends DoubleFuncList> mapper) {
        return streamPlus().map(mapper).flatMapToDouble(list -> list.doubleStreamPlus());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <T> StreamPlus<T> mapFlatToObj(Function<? super DATA, ? extends Iterable<? extends T>> mapper) {
        val streamPlus = streamPlus();
        return streamPlus.flatMap(item -> (StreamPlus) StreamPlus.from(mapper.apply(item)));
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <T> StreamPlus<T> mapFlat(Aggregation<? super DATA, ? extends Iterable<? extends T>> aggregation) {
        val mapper = aggregation.newAggregator();
        return streamPlus().flatMap(item -> (StreamPlus) StreamPlus.from(mapper.apply(item)));
    }
    
    public default IntStreamPlus mapFlatToInt(Aggregation<? super DATA, ? extends IntFuncList> aggregation) {
        val mapper = aggregation.newAggregator();
        return streamPlus().map(mapper).flatMapToInt(list -> list.intStreamPlus());
    }
    
    public default LongStreamPlus mapFlatToLong(Aggregation<? super DATA, ? extends LongFuncList> aggregation) {
        val mapper = aggregation.newAggregator();
        return streamPlus().map(mapper).flatMapToLong(list -> list.longStreamPlus());
    }
    
    public default DoubleStreamPlus mapFlatToDouble(Aggregation<? super DATA, ? extends DoubleFuncList> aggregation) {
        val mapper = aggregation.newAggregator();
        return streamPlus().map(mapper).flatMapToDouble(list -> list.doubleStreamPlus());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <T> StreamPlus<T> mapFlatToObj(Aggregation<? super DATA, ? extends Iterable<? extends T>> aggregation) {
        val mapper = aggregation.newAggregator();
        return streamPlus().flatMap(item -> (StreamPlus) StreamPlus.from(mapper.apply(item)));
    }
}
