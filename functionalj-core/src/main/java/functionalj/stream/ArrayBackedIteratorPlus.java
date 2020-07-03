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
package functionalj.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.result.AutoCloseableResult;
import functionalj.result.Result;
import lombok.val;

public class ArrayBackedIteratorPlus<DATA> implements IteratorPlus<DATA> {
    
    private final DATA[] array;
    private final int    start;
    private final int    end;
    private final Iterator<DATA> iterator;
    
    private AtomicInteger current = new AtomicInteger();
    
    private volatile Runnable closeHandler = null;
    
    @SafeVarargs
    public static <DATA> ArrayBackedIteratorPlus<DATA> of(DATA ... array) {
        DATA[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIteratorPlus<DATA>(copiedArray);
    }
    public static <DATA> ArrayBackedIteratorPlus<DATA> from(DATA[] array) {
        DATA[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIteratorPlus<DATA>(copiedArray);
    }
    public static <DATA> ArrayBackedIteratorPlus<DATA> from(DATA[] array, int start, int length) {
        DATA[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIteratorPlus<DATA>(copiedArray, start, length);
    }
    
    ArrayBackedIteratorPlus(DATA[] array, int start, int length) {
        this.array = array;
        this.start = Math.max(0, Math.min(array.length - 1, start));
        this.end   = Math.max(0, Math.min(array.length    , start + length));
        this.iterator = createIterator(array);
        this.current.set(this.start - 1);
    }
    
    ArrayBackedIteratorPlus(DATA[] array) {
        this(array, 0, array.length);
    }
    
    private Iterator<DATA> createIterator(DATA[] array) {
        return new Iterator<DATA>() {
            @Override
            public boolean hasNext() {
                return current.incrementAndGet() < ArrayBackedIteratorPlus.this.end;
            }
            @Override
            public DATA next() {
                int index = current.get();
                if (index >= array.length)
                    throw new NoSuchElementException();
                if (index < 0)
                    throw new NoSuchElementException();
                
                return array[index];
            }
        };
    }
    
    public IteratorPlus<DATA> newIterator() {
        return new ArrayBackedIteratorPlus<>(array, start, start + end);
    }
    
    public int getStart() {
        return start;
    }
    
    public int getLength() {
        return end - start;
    }
    
    public void close() {
        if (this.closeHandler != null) {
            this.closeHandler.run();
        }
    }
    
    public IteratorPlus<DATA> onClose(Runnable closeHandler) {
        if (closeHandler != null) {
            synchronized (this) {
                if (this.closeHandler == null) {
                    this.closeHandler = closeHandler;
                } else {
                    val thisCloseHandler = this.closeHandler;
                    this.closeHandler = new Runnable() {
                        @Override
                        public void run() {
                            thisCloseHandler.run();
                            closeHandler.run();
                        }
                    };
                }
            }
        }
        return this;
    }
    
    @Override
    public Iterator<DATA> asIterator() {
        return iterator;
    }
    
    public StreamPlus<DATA> stream() {
        return new ArrayBackedStreamPlus<DATA>(this);
    }
    
    public AutoCloseableResult<IteratorPlus<DATA>> pullNext(int count) {
        val oldIndex = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        int newIndex = current.get();
        if ((newIndex >= end) && (count != 0))
            return AutoCloseableResult.from(Result.ofNoMore());
        
        return AutoCloseableResult.valueOf(new ArrayBackedIteratorPlus<DATA>(array, oldIndex, oldIndex + count));
    }
    
    public <TARGET> Result<TARGET> mapNext(int count, Func1<StreamPlus<DATA>, TARGET> mapper) {
        val old = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        if ((current.get() >= end) && (count != 0))
            return Result.ofNoMore();
        
        try (val iterator = new ArrayBackedIteratorPlus<DATA>(array, old, old + count)){
            val stream = iterator.stream();
            val value = mapper.apply(stream);
            return Result.valueOf(value);
        }
    }
    
    public Streamable<DATA> streamable() {
        return (Streamable<DATA>)()->{
            val iterable = (Iterable<DATA>)()->newIterator();
            return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
        };
    }
    
    public DATA[] toArray() {
        DATA[] copiedArray = Arrays.copyOfRange(array, start, end);
        return copiedArray;
    }
    
    public <A> A[] toArray(IntFunction<A[]> generator) {
        int length = end - start;
        A[] newArray = generator.apply(length);
        System.arraycopy(array, start, newArray, 0, length);
        return newArray;
    }
    
}
