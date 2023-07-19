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
package functionalj.stream.longstream;

import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import functionalj.list.longlist.LongFuncList;
import functionalj.pipeable.Pipeable;
import functionalj.result.AutoCloseableResult;
import functionalj.result.Result;
import lombok.val;

@FunctionalInterface
public interface LongIteratorPlus extends PrimitiveIterator.OfLong, AutoCloseable, Pipeable<LongIteratorPlus> {
    
    public static LongIteratorPlus of(long... ds) {
        return LongIteratorPlus.from(LongStreamPlus.of(ds));
    }
    
    public static LongIteratorPlus from(LongStream stream) {
        if (stream instanceof LongStreamPlus) {
            return new StreamBackedLongIteratorPlus(((LongStreamPlus) stream).longStream());
        }
        return LongIteratorPlus.from(stream.iterator());
    }
    
    public static LongIteratorPlus from(PrimitiveIterator.OfLong iterator) {
        if (iterator instanceof LongIteratorPlus)
            return (LongIteratorPlus) iterator;
        else
            return new LongIteratorPlus() {
        
                @Override
                public PrimitiveIterator.OfLong asIterator() {
                    return iterator;
                }
            };
    }
    
    public default LongIteratorPlus __data() throws Exception {
        return this;
    }
    
    public default void close() {
    }
    
    public default LongIteratorPlus onClose(Runnable closeHandler) {
        return this;
    }
    
    public PrimitiveIterator.OfLong asIterator();
    
    public default LongIteratorPlus iterator() {
        return LongIteratorPlus.from(asIterator());
    }
    
    @Override
    public default boolean hasNext() {
        val hasNext = asIterator().hasNext();
        if (!hasNext) {
            close();
        }
        return hasNext;
    }
    
    @Override
    public default long nextLong() {
        return asIterator().nextLong();
    }
    
    @Override
    public default Long next() {
        return asIterator().next();
    }
    
    public default LongStreamPlus stream() {
        val iterable = (LongIterable) () -> this;
        return LongStreamPlus.from(StreamSupport.longStream(iterable.spliterator(), false));
    }
    
    public default LongFuncList toList() {
        return stream().toImmutableList();
    }
    
    public default OptionalLong pullNext() {
        if (hasNext())
            return OptionalLong.of(nextLong());
        else
            return OptionalLong.empty();
    }
    
    public default AutoCloseableResult<LongIteratorPlus> pullNext(int count) {
        long[] array = stream().limit(count).toArray();
        if ((array.length == 0) && count != 0)
            return AutoCloseableResult.from(Result.ofNoMore());
        val iterator = new ArrayBackedLongIteratorPlus(array);
        return AutoCloseableResult.valueOf(iterator);
    }
    
    public default LongIteratorPlus useNext(LongConsumer usage) {
        if (hasNext()) {
            val next = nextLong();
            usage.accept(next);
        }
        return this;
    }
    
    public default LongIteratorPlus useNext(int count, Consumer<LongStreamPlus> usage) {
        long[] array = stream().limit(count).toArray();
        if ((array.length != 0) || count == 0) {
            try (val iterator = new ArrayBackedLongIteratorPlus(array)) {
                val stream = iterator.stream();
                usage.accept(stream);
            }
        }
        return this;
    }
    
    public default <TARGET> Result<TARGET> mapNext(LongFunction<TARGET> mapper) {
        if (hasNext()) {
            val next = nextLong();
            val value = mapper.apply(next);
            return Result.valueOf(value);
        } else {
            return Result.ofNoMore();
        }
    }
    
    public default <TARGET> Result<TARGET> mapNext(int count, Function<LongStreamPlus, TARGET> mapper) {
        val array = stream().limit(count).toArray();
        if ((array.length == 0) && (count != 0))
            return Result.ofNoMore();
        val input = ArrayBackedLongIteratorPlus.from(array);
        val stream = input.stream();
        val value = mapper.apply(stream);
        return Result.valueOf(value);
    }
}
