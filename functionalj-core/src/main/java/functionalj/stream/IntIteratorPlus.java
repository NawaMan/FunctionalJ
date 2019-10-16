// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.result.Result;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

@FunctionalInterface
public interface IntIteratorPlus extends PrimitiveIterator.OfInt {
    
    public static IntIteratorPlus of(int ... ds) {
        return IntIteratorPlus.from(IntStreamPlus.of(ds));
    }
    public static IntIteratorPlus from(IntStreamPlus stream) {
        val iterator 
                = (stream instanceof IntStreamPlus) 
                ? ((IntStreamPlus)stream).__iterator()
                : stream.iterator();
        return from(iterator);
    }
    public static IntIteratorPlus from(PrimitiveIterator.OfInt iterator) {
        if (iterator instanceof IntIteratorPlus)
             return (IntIteratorPlus)iterator;
        else return (IntIteratorPlus)(()->iterator);
    }
    
    public PrimitiveIterator.OfInt asIterator();
    
    public default IntIteratorPlus iterator() {
        return IntIteratorPlus.from(asIterator());
    }
    
    @Override
    public default boolean hasNext() {
        return asIterator().hasNext();
    }
    
    @Override
    public default int nextInt() {
        return asIterator().nextInt();
    }
    
    @Override
    public default Integer next() {
        return asIterator().next();
    }
    
    public default IntStreamPlus stream() {
        val iterable = (IntIterable)()->this;
        return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
    }
    
    // TODO - When we have IntFunc ....
//    public default FuncList<DATA> toList() {
//        return stream().toList();
//    }
    
    public default OptionalInt pullNext() {
        if (hasNext())
             return OptionalInt.of(nextInt());
        else return OptionalInt.empty();
    }
    
    public default Result<IntIteratorPlus> pullNext(int count) {
        int[] array = stream().limit(count).toArray();
        if ((array.length == 0) && count != 0)
            return Result.ofNoMore();
        
        val iterator = IntIteratorPlus.of(array);
        return Result.valueOf(iterator);
    }
    
    public default IntIteratorPlus useNext(IntConsumer usage) {
        if (hasNext()) {
            val next = nextInt();
            usage.accept(next);
        }
        
        return this;
    }
    
    public default IntIteratorPlus useNext(int count, FuncUnit1<IntStreamPlus> usage) {
        int[] array = stream().limit(count).toArray();
        if ((array.length != 0) || count == 0) {
            val iterator = IntIteratorPlus.of(array);
            val stream   = iterator.stream();
            usage.accept(stream);
        }
        
        return this;
    }
    
    public default <TARGET> Result<TARGET> mapNext(IntFunction<TARGET> mapper) {
        if (hasNext()) {
            val next  = nextInt();
            val value = mapper.apply(next);
            return Result.valueOf(value);
        } else {
            return Result.ofNoMore();
        }
    }
    
    public default <TARGET> Result<TARGET> mapNext(int count, Func1<IntStreamPlus, TARGET> mapper) {
        val array = stream().limit(count).toArray();
        if ((array.length == 0) && (count != 0))
            return Result.ofNoMore();
        
        val input  = IntIteratorPlus.of(array);
        val stream = input.stream();
        val value = mapper.apply(stream);
        return Result.valueOf(value);
    }
    
}
