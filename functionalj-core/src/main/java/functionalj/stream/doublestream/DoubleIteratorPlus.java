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
package functionalj.stream.doublestream;

import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.pipeable.Pipeable;
import functionalj.result.AutoCloseableResult;
import functionalj.result.Result;
import lombok.val;


@FunctionalInterface
public interface DoubleIteratorPlus extends PrimitiveIterator.OfDouble, AutoCloseable, Pipeable<DoubleIteratorPlus>  {
    
    public static DoubleIteratorPlus of(double ... ds) {
        return DoubleIteratorPlus.from(DoubleStreamPlus.of(ds));
    }
    public static DoubleIteratorPlus from(DoubleStream stream) {
        if (stream instanceof DoubleStreamPlus) {
            return new StreamBackedDoubleIteratorPlus(((DoubleStreamPlus)stream).doubleStream());
        }
        return DoubleIteratorPlus.from(stream.iterator());
    }
    public static DoubleIteratorPlus from(PrimitiveIterator.OfDouble iterator) {
        if (iterator instanceof DoubleIteratorPlus)
             return (DoubleIteratorPlus)iterator;
        else return (DoubleIteratorPlus)(()->iterator);
    }
    
    public default DoubleIteratorPlus __data() throws Exception {
        return this;
    }
    
    public default void close() {
        
    }
    
    public default DoubleIteratorPlus onClose(Runnable closeHandler) {
        return this;
    }
    
    public PrimitiveIterator.OfDouble asIterator();
    
    public default DoubleIteratorPlus iterator() {
        return DoubleIteratorPlus.from(asIterator());
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
    public default double nextDouble() {
        return asIterator().nextDouble();
    }
    
    @Override
    public default Double next() {
        return asIterator().next();
    }
    
    public default DoubleStreamPlus stream() {
        val iterable = (DoubleIterable)()->this;
        return DoubleStreamPlus.from(StreamSupport.doubleStream(iterable.spliterator(), false));
    }
    
//    public default DoubleFuncList toList() {
//        return stream().toImmutableList();
//    }
//
    public default OptionalDouble pullNext() {
        if (hasNext())
             return OptionalDouble.of(nextDouble());
        else return OptionalDouble.empty();
    }
    
    public default AutoCloseableResult<DoubleIteratorPlus> pullNext(int count) {
        double[] array = stream().limit(count).toArray();
        if ((array.length == 0) && count != 0)
            return AutoCloseableResult.from(Result.ofNoMore());
        
        val iterator = new ArrayBackedDoubleIteratorPlus(array);
        return AutoCloseableResult.valueOf(iterator);
    }
    
    public default DoubleIteratorPlus useNext(DoubleConsumer usage) {
        if (hasNext()) {
            val next = nextDouble();
            usage.accept(next);
        }
        
        return this;
    }
    
    public default DoubleIteratorPlus useNext(int count, FuncUnit1<DoubleStreamPlus> usage) {
        double[] array = stream().limit(count).toArray();
        if ((array.length != 0) || count == 0) {
            try (val iterator = new ArrayBackedDoubleIteratorPlus(array)) {
                val stream   = iterator.stream();
                usage.accept(stream);
            }
        }
        
        return this;
    }
    
    public default <TARGET> Result<TARGET> mapNext(DoubleFunction<TARGET> mapper) {
        if (hasNext()) {
            val next  = nextDouble();
            val value = mapper.apply(next);
            return Result.valueOf(value);
        } else {
            return Result.ofNoMore();
        }
    }
    
    public default <TARGET> Result<TARGET> mapNext(int count, Func1<DoubleStreamPlus, TARGET> mapper) {
        val array = stream().limit(count).toArray();
        if ((array.length == 0) && (count != 0))
            return Result.ofNoMore();
        
        val input  = ArrayBackedDoubleIteratorPlus.from(array);
        val stream = input.stream();
        val value = mapper.apply(stream);
        return Result.valueOf(value);
    }
    
}
