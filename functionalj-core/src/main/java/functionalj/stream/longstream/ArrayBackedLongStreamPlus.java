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
package functionalj.stream.longstream;

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import lombok.val;

// This class along with ArrayBackedIntIteratorPlus helps improve performance when do pullNext, useNext and mapNext
// with multiple value to run faster.
public class ArrayBackedLongStreamPlus implements LongStreamPlus {
    
    private final ArrayBackedLongIteratorPlus iterator;
    
    private final LongStreamPlus stream;
    
    @SafeVarargs
    public static LongStreamPlus of(long... array) {
        val iterator = ArrayBackedLongIteratorPlus.of(array);
        val stream = new ArrayBackedLongStreamPlus(iterator);
        return stream;
    }
    
    public static LongStreamPlus from(long[] array) {
        val iterator = ArrayBackedLongIteratorPlus.of(array);
        val stream = new ArrayBackedLongStreamPlus(iterator);
        return stream;
    }
    
    public static LongStreamPlus from(long[] array, int start, int length) {
        val iterator = (ArrayBackedLongIteratorPlus) ArrayBackedLongIteratorPlus.from(array, start, length);
        val stream = new ArrayBackedLongStreamPlus(iterator);
        return stream;
    }
    
    ArrayBackedLongStreamPlus(ArrayBackedLongIteratorPlus iterator) {
        this.iterator = iterator;
        val iterable = (LongIterable) () -> iterator;
        this.stream = LongStreamPlus.from(StreamSupport.longStream(iterable.spliterator(), false));
    }
    
    @Override
    public LongStream longStream() {
        return stream;
    }
    
    @Override
    public void close() {
        iterator.close();
        stream.close();
    }
    
    @Override
    public LongStreamPlus onClose(Runnable closeHandler) {
        iterator.onClose(closeHandler);
        stream.onClose(closeHandler);
        return derive(stream -> {
            return stream.onClose(closeHandler);
        });
    }
    
    public LongIteratorPlus iterator() {
        return iterator;
    }
    
    @Override
    public long[] toArray() {
        return iterator.toArray();
    }
}
