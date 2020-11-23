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
package functionalj.stream.intstream;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;



// This class along with ArrayBackedIntIteratorPlus helps improve performance when do pullNext, useNext and mapNext
//   with multiple value to run faster.
public class ArrayBackedIntStreamPlus implements IntStreamPlus {

    private final ArrayBackedIntIteratorPlus iterator;
    private final IntStreamPlus              stream;
    
    @SafeVarargs
    public static IntStreamPlus of(int ... array) {
        var iterator = ArrayBackedIntIteratorPlus.of(array);
        var stream   = new ArrayBackedIntStreamPlus(iterator);
        return stream;
    }
    public static IntStreamPlus from(int[] array) {
        var iterator = ArrayBackedIntIteratorPlus.of(array);
        var stream   = new ArrayBackedIntStreamPlus(iterator);
        return stream;
    }
    public static IntStreamPlus from(int[] array, int start, int length) {
        var iterator = (ArrayBackedIntIteratorPlus)ArrayBackedIntIteratorPlus.from(array, start, length);
        var stream   = new ArrayBackedIntStreamPlus(iterator);
        return stream;
    }
    
    ArrayBackedIntStreamPlus(ArrayBackedIntIteratorPlus iterator) {
        this.iterator = iterator;
        
        var iterable = (IntIterable)()->iterator;
        this.stream  = IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
    }
    
    @Override
    public IntStream intStream() {
        return stream;
    }
    
    @Override
    public void close() {
        iterator.close();
        stream.close();
    }
    
    @Override
    public IntStreamPlus onClose(Runnable closeHandler) {
        iterator.onClose(closeHandler);
        stream.onClose(closeHandler);
        return derive(stream -> {
            return stream
                    .onClose(closeHandler);
        });
    }
    
    public IntIteratorPlus iterator() {
        return iterator;
    }
    
    @Override
    public int[] toArray() {
        return iterator.toArray();
    }
    
}
