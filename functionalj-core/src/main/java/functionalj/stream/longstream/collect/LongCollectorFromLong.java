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
package functionalj.stream.longstream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

import functionalj.stream.longstream.LongStreamPlus;
import lombok.val;

public class LongCollectorFromLong<ACCUMULATED, RESULT> 
        implements LongCollectorPlus<ACCUMULATED, RESULT> {
    
    private final LongCollectorPlus<ACCUMULATED, RESULT> collector;
    private final LongUnaryOperator                      mapper;
    
    public LongCollectorFromLong(LongCollectorPlus<ACCUMULATED, RESULT> collector, LongUnaryOperator mapper) {
        this.collector = collector;
        this.mapper    = mapper;
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    @Override
    public ObjLongConsumer<ACCUMULATED> longAccumulator() {
        val accumulator = collector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsLong(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Long> accumulator() {
        val accumulator = collector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsLong(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    
    @Override
    public Function<ACCUMULATED, RESULT> finisher() {
        return collector.finisher();
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    @Override
    public RESULT process(LongStreamPlus stream) {
        return collector.process(stream);
    }
}
