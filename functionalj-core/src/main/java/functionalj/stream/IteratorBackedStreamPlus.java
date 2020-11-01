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

import java.util.Iterator;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.val;

// This class along with ArrayBackedIteratorPlus helps improve performance when do pullNext, useNext and mapNext 
//   with multiple value to run faster.
public class IteratorBackedStreamPlus<DATA> implements StreamPlus<DATA> {
    
    private final IteratorPlus<DATA> iterator;
    private final StreamPlus<DATA>   stream;
    
    @SafeVarargs
    public static <DATA> StreamPlus<DATA> of(DATA ... array) {
        val iterator = ArrayBackedIteratorPlus.of(array);
        val stream   = new IteratorBackedStreamPlus<>(iterator);
        return stream;
    }
    public static <DATA> StreamPlus<DATA> from(DATA[] array) {
        val iterator = ArrayBackedIteratorPlus.of(array);
        val stream   = new IteratorBackedStreamPlus<>(iterator);
        return stream;
    }
    public static <DATA> StreamPlus<DATA> from(DATA[] array, int start, int length) {
        @SuppressWarnings("unchecked")
        val iterator = (ArrayBackedIteratorPlus<DATA>)ArrayBackedIteratorPlus.of(array, start, length);
        val stream   = new IteratorBackedStreamPlus<>(iterator);
        return stream;
    }
    
    public static <DATA> StreamPlus<DATA> from(Iterator<DATA> iterator) {
        return new IteratorBackedStreamPlus<DATA>(iterator);
    }
    
    public static <DATA> StreamPlus<DATA> from(Stream<DATA> stream) {
        return new IteratorBackedStreamPlus<DATA>(stream.iterator());
    }
    
    IteratorBackedStreamPlus(Iterator<DATA> iterator) {
        this.iterator = IteratorPlus.from(iterator);
        
        val iterable = (Iterable<DATA>)()->iterator;
        this.stream  = (StreamPlus<DATA>)(()->StreamSupport.stream(iterable.spliterator(), false));
    }
    
    public Stream<DATA> stream() {
        return stream;
    }
    
    @Override
    public void close() {
        iterator.close();
        stream.close();
    }
    
    @Override
    public StreamPlus<DATA> onClose(Runnable closeHandler) {
        iterator.onClose(closeHandler);
        stream.onClose(closeHandler);
        return derive(stream -> { 
            stream
                .stream()
                .onClose(closeHandler);
            return stream;
        });
    }
    
    public IteratorPlus<DATA> iterator() {
        return iterator;
    }
    
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }
    
}
