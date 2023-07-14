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
package functionalj.stream;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.result.AutoCloseableResult;
import functionalj.result.Result;
import lombok.val;

@FunctionalInterface
public interface IteratorPlus<DATA> extends Iterator<DATA>, AutoCloseable, Pipeable<IteratorPlus<DATA>> {

    @SuppressWarnings("unchecked")
    public static <D> IteratorPlus<D> of(D... ds) {
        return IteratorPlus.from(StreamPlus.of(ds));
    }

    public static <D> IteratorPlus<D> from(Stream<D> stream) {
        if (stream instanceof StreamPlus) {
            return new StreamBackedIteratorPlus<>(((StreamPlus<D>) stream).stream());
        }
        return IteratorPlus.from(stream.iterator());
    }

    public static <D> IteratorPlus<D> from(Iterator<D> iterator) {
        if (iterator instanceof IteratorPlus)
            return (IteratorPlus<D>) iterator;
        else
            return new IteratorPlus<D>() {

                @Override
                public Iterator<D> asIterator() {
                    return iterator;
                }
            };
    }

    public default IteratorPlus<DATA> __data() throws Exception {
        return this;
    }

    public default void close() {
    }

    public default IteratorPlus<DATA> onClose(Runnable closeHandler) {
        return this;
    }

    public Iterator<DATA> asIterator();

    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(asIterator());
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
    public default DATA next() {
        return asIterator().next();
    }

    public default StreamPlus<DATA> stream() {
        val iterable = (Iterable<DATA>) () -> this;
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }

    public default FuncList<DATA> toList() {
        return stream().toList();
    }

    public default Result<DATA> pullNext() {
        if (hasNext())
            return Result.valueOf(next());
        else
            return Result.ofNoMore();
    }

    @SuppressWarnings("unchecked")
    public default AutoCloseableResult<IteratorPlus<DATA>> pullNext(int count) {
        Object[] array = stream().limit(count).toArray();
        if ((array.length == 0) && count != 0)
            return AutoCloseableResult.from(Result.ofNoMore());
        @SuppressWarnings("resource")
        val iterator = (ArrayBackedIteratorPlus<DATA>) new ArrayBackedIteratorPlus<Object>(array);
        return AutoCloseableResult.valueOf(iterator);
    }

    public default IteratorPlus<DATA> useNext(FuncUnit1<DATA> usage) {
        if (hasNext()) {
            val next = next();
            usage.accept(next);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public default IteratorPlus<DATA> useNext(int count, FuncUnit1<StreamPlus<DATA>> usage) {
        Object[] array = stream().limit(count).toArray();
        if ((array.length != 0) || count == 0) {
            try (val iterator = (ArrayBackedIteratorPlus<DATA>) new ArrayBackedIteratorPlus<Object>(array)) {
                val stream = iterator.stream();
                usage.accept(stream);
            }
        }
        return this;
    }

    public default <TARGET> Result<TARGET> mapNext(Func1<DATA, TARGET> mapper) {
        if (hasNext()) {
            val next = next();
            val value = mapper.apply(next);
            return Result.valueOf(value);
        } else {
            return Result.ofNoMore();
        }
    }

    public default <TARGET> Result<TARGET> mapNext(int count, Func1<StreamPlus<DATA>, TARGET> mapper) {
        val array = stream().limit(count).toArray();
        if ((array.length == 0) && (count != 0))
            return Result.ofNoMore();
        @SuppressWarnings("unchecked")
        val input = (IteratorPlus<DATA>) ArrayBackedIteratorPlus.from(array);
        val stream = input.stream();
        val value = mapper.apply(stream);
        return Result.valueOf(value);
    }
}
