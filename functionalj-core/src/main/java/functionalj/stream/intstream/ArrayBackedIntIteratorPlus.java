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
package functionalj.stream.intstream;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;
import functionalj.function.Func1;
import functionalj.list.intlist.IntFuncList;
import functionalj.result.AutoCloseableResult;
import functionalj.result.Result;
import lombok.val;

public class ArrayBackedIntIteratorPlus implements IntIteratorPlus, PrimitiveIterator.OfInt {
    
    private final int[] array;
    
    private final int start;
    
    private final int end;
    
    private final PrimitiveIterator.OfInt iterator;
    
    private AtomicInteger current = new AtomicInteger();
    
    private volatile Runnable closeHandler = null;
    
    @SafeVarargs
    public static ArrayBackedIntIteratorPlus of(int... array) {
        val copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIntIteratorPlus(copiedArray);
    }
    
    public static ArrayBackedIntIteratorPlus from(int[] array) {
        val copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIntIteratorPlus(copiedArray);
    }
    
    public static ArrayBackedIntIteratorPlus from(int[] array, int start, int length) {
        val copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIntIteratorPlus(copiedArray, start, length);
    }
    
    ArrayBackedIntIteratorPlus(int[] array, int start, int length) {
        this.array = array;
        this.start = Math.max(0, Math.min(array.length - 1, start));
        this.end = Math.max(0, Math.min(array.length, start + length));
        this.iterator = createIterator(array);
        this.current.set(this.start - 1);
    }
    
    ArrayBackedIntIteratorPlus(int[] array) {
        this(array, 0, array.length);
    }
    
    private PrimitiveIterator.OfInt createIterator(int[] array) {
        return new PrimitiveIterator.OfInt() {
        
            @Override
            public boolean hasNext() {
                return current.incrementAndGet() < ArrayBackedIntIteratorPlus.this.end;
            }
        
            @Override
            public int nextInt() {
                val index = current.get();
                if (index >= array.length)
                    throw new NoSuchElementException();
                if (index < 0)
                    throw new NoSuchElementException();
                return array[index];
            }
        };
    }
    
    public IntIteratorPlus newIterator() {
        return new ArrayBackedIntIteratorPlus(array, start, start + end);
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
    
    public IntIteratorPlus onClose(Runnable closeHandler) {
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
    public PrimitiveIterator.OfInt asIterator() {
        return iterator;
    }
    
    public IntStreamPlus stream() {
        return new ArrayBackedIntStreamPlus(this);
    }
    
    public AutoCloseableResult<IntIteratorPlus> pullNext(int count) {
        val oldIndex = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        int newIndex = current.get();
        if ((newIndex >= end) && (count != 0))
            return AutoCloseableResult.from(Result.ofNoMore());
        return AutoCloseableResult.valueOf(new ArrayBackedIntIteratorPlus(array, oldIndex, oldIndex + count));
    }
    
    public <TARGET> Result<TARGET> mapNext(int count, Func1<IntStreamPlus, TARGET> mapper) {
        val old = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        if ((current.get() >= end) && (count != 0))
            return Result.ofNoMore();
        try (val iterator = new ArrayBackedIntIteratorPlus(array, old, old + count)) {
            val stream = iterator.stream();
            val value = mapper.apply(stream);
            return Result.valueOf(value);
        }
    }
    
    public IntFuncList funcList() {
        return IntFuncList.from(() -> {
            val iterable = (IntIterable) () -> newIterator();
            return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
        });
    }
    
    public int[] toArray() {
        int[] copiedArray = Arrays.copyOfRange(array, start, end);
        return copiedArray;
    }
    
    public <A> A[] toArray(IntFunction<A[]> generator) {
        int length = end - start;
        A[] newArray = generator.apply(length);
        System.arraycopy(array, start, newArray, 0, length);
        return newArray;
    }
}
