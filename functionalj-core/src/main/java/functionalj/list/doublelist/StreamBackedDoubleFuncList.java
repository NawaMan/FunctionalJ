// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;
import functionalj.list.FuncList.Mode;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.GrowOnlyDoubleArray;
import lombok.NonNull;
import lombok.val;

public class StreamBackedDoubleFuncList implements DoubleFuncList {
    
    private static final DoubleBinaryOperator zeroForEquals = (double i1, double i2) -> i1 == i2 ? 0 : 1;
    
    private static final DoublePredicate notZero = (double i) -> i != 0;
    
    private final Mode mode;
    
    private final GrowOnlyDoubleArray cache = new GrowOnlyDoubleArray();
    
    private final Spliterator.OfDouble spliterator;
    
    public StreamBackedDoubleFuncList(@NonNull DoubleStream stream, @NonNull Mode mode) {
        this.spliterator = stream.spliterator();
        this.mode = mode;
        if (mode.isEager()) {
            size();
        }
    }
    
    public StreamBackedDoubleFuncList(@NonNull DoubleStream stream) {
        this(stream, Mode.cache);
    }
    
    public Mode mode() {
        return mode;
    }
    
    @Override
    public DoubleFuncList toLazy() {
        if (mode.isLazy()) {
            return this;
        }
        return new StreamBackedDoubleFuncList(doubleStreamPlus(), Mode.lazy);
    }
    
    @Override
    public DoubleFuncList toEager() {
        // Just materialize all value.
        int size = size();
        return new ImmutableDoubleFuncList(cache, size, Mode.eager);
    }
    
    @Override
    public DoubleFuncList toCache() {
        if (mode.isCache()) {
            return this;
        }
        return new StreamBackedDoubleFuncList(doubleStreamPlus(), Mode.cache);
    }
    
    @Override
    public DoubleStreamPlus doubleStream() {
        val indexRef = new AtomicInteger(0);
        val valueConsumer = (DoubleConsumer) ((double v) -> cache.add(v));
        val newSpliterator = new Spliterators.AbstractDoubleSpliterator(Long.MAX_VALUE, 0) {
        
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                int index = indexRef.getAndIncrement();
                if (fromCache(consumer, index))
                    return true;
                boolean hadNext = false;
                synchronized (this) {
                    if (index >= cache.length()) {
                        hadNext = spliterator.tryAdvance(valueConsumer);
                    }
                }
                if (fromCache(consumer, index))
                    return true;
                return hadNext;
            }
        
            private boolean fromCache(DoubleConsumer consumer, int index) {
                if (index >= cache.length())
                    return false;
                double value = cache.get(index);
                consumer.accept(value);
                return true;
            }
        };
        val newStream = StreamSupport.doubleStream(newSpliterator, false);
        return DoubleStreamPlus.from(newStream);
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(reduce(43, (hash, each) -> hash * 43 + each));
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsDoubleFuncList))
            return false;
        val anotherList = (DoubleFuncList) o;
        if (size() != anotherList.size())
            return false;
        return !DoubleFuncList.zipOf(this, anotherList.asDoubleFuncList(), zeroForEquals).anyMatch(notZero);
    }
    
    @Override
    public String toString() {
        return asDoubleFuncList().toListString();
    }
}
