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

import static functionalj.function.Func.themAll;
import static functionalj.tuple.IntIntTuple.intTuple;
import static java.lang.System.arraycopy;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

import java.util.Collection;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.function.ObjIntBiFunction;
import functionalj.stream.StreamPlus;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.ObjIntTuple;
import lombok.val;

public interface IntStreamPlusAddtionalOperators {
    
    public IntStreamPlus map(IntUnaryOperator mapper);
    
    public <T> StreamPlus<T> mapToObj(IntFunction<? extends T> mapper);
    
    public IntStreamPlus flatMap(IntFunction<? extends IntStream> mapper);
    
    public IntStreamPlus filter(IntPredicate predicate);
    
    public IntStreamPlus peek(IntConsumer action);
    
    
    //--map with condition --
    
    public default IntStreamPlus mapOnly(
            IntPredicate     checker, 
            IntUnaryOperator mapper) {
        return map(d -> checker.test(d) ? mapper.applyAsInt(d) : d);
    }
    
    public default IntStreamPlus mapIf(
            IntPredicate     checker, 
            IntUnaryOperator mapper, 
            IntUnaryOperator elseMapper) {
        return map(d -> {
            return checker.test(d) 
                    ? mapper    .applyAsInt(d) 
                    : elseMapper.applyAsInt(d);
        });
    }
    
    public default <T> StreamPlus<T> mapToObjIf(
            IntPredicate   checker, 
            IntFunction<T> mapper, 
            IntFunction<T> elseMapper) {
        return mapToObj(d -> {
            return checker.test(d) 
                    ? mapper.apply(d) 
                    : elseMapper.apply(d);
        });
    }
    
    //-- mapWithIndex --
    
    public default StreamPlus<IntIntTuple> mapWithIndex() {
        val index = new AtomicInteger();
        return mapToObj(each -> intTuple(index.getAndIncrement(), each));
    }
    
    public default IntStreamPlus mapWithIndex(IntBinaryOperator mapper) {
        val index = new AtomicInteger();
        return map(each -> {
            val i = index.getAndIncrement();
            val target = mapper.applyAsInt(i, each);
            return target;
        });
    }
    
    public default <T> StreamPlus<T> mapToObjWithIndex(IntIntBiFunction<T> mapper) {
        val index = new AtomicInteger();
        return mapToObj(each -> {
            val i = index.getAndIncrement();
            val target = mapper.applyInt(i, each);
            return target;
        });
    }
    
    public default <T1, T> StreamPlus<T> mapWithIndex(
                IntUnaryOperator    valueMapper,
                IntIntBiFunction<T> combiner) {
        val index = new AtomicInteger();
        return mapToObj(each -> {
            val i      = index.getAndIncrement();
            val value  = valueMapper.applyAsInt(each);
            val target = combiner.apply(i, value);
            return target;
        });
    }
    
    public default <T1, T> StreamPlus<T> mapToObjWithIndex(
                IntFunction<? extends T1>       valueMapper,
                IntObjBiFunction<? super T1, T> combiner) {
        val index = new AtomicInteger();
        return mapToObj(each -> {
            val i      = index.getAndIncrement();
            val value  = valueMapper.apply(each);
            val target = combiner.apply(i, value);
            return target;
        });
    }
    
    //-- mapWithPrev --
    
    public default StreamPlus<ObjIntTuple<OptionalInt>> mapWithPrev() {
        val prev = new AtomicReference<OptionalInt>(OptionalInt.empty());
        return mapToObj(element -> {
            val prevValue = prev.get();
            prev.set(OptionalInt.of(element));
            val result = ObjIntTuple.of(prevValue, element);
            return result;
        });
    }
    
    public default <TARGET> StreamPlus<TARGET> mapWithPrev(
            ObjIntBiFunction<OptionalInt, ? extends TARGET> mapper) {
        val prev = new AtomicReference<OptionalInt>(OptionalInt.empty());
        return mapToObj(element -> {
            val prevValue = prev.get();
            val newValue  = mapper.apply(prevValue, element);
            prev.set(OptionalInt.of(element));
            return newValue;
        });
    }
    
    //-- Filter --
    
    public default IntStreamPlus filterIn(int ... array) {
        if ((array == null) || (array.length == 0))
            return IntStreamPlus.empty();
        
        val length = array.length;
        val ints   = new int[length];
        arraycopy(array, 0, ints, 0, length);
        sort(ints);
        return filter(intValue -> {
            int index = binarySearch(ints, intValue);
            boolean included = (index >= 0);
            return included;
        });
    }
    
    public default IntStreamPlus filterIn(Collection<Integer> collection) {
        if ((collection == null) || collection.isEmpty())
            return IntStreamPlus.empty();
        
        return filter(data -> collection.contains(data));
    }
    
    public default IntStreamPlus exclude(IntPredicate predicate) {
        return filter(data -> !predicate.test(data));
    }
    
    public default IntStreamPlus excludeIn(int... array) {
        if ((array == null) || (array.length == 0))
            return IntStreamPlus.empty();
        
        val length = array.length;
        val ints   = new int[length];
        arraycopy(array, 0, ints, 0, length);
        sort(ints);
        return filter(intValue -> {
            int index = binarySearch(ints, intValue);
            boolean included = (index < 0);
            return included;
        });
    }
    
    public default IntStreamPlus excludeIn(Collection<Integer> collection) {
        if ((collection == null) || collection.isEmpty())
            return IntStreamPlus.empty();
        
        return filter(data -> !collection.contains(data));
    }
    
    public default IntStreamPlus filterWithIndex(
            IntBiPredicatePrimitive predicate) {
        val index = new AtomicInteger();
        return filter(each -> {
            val i = index.getAndIncrement();
            return predicate.testIntInt(i, each);
        });
    }
    
    //-- Peek --
    
    public default IntStreamPlus peek(IntPredicate selector, IntConsumer theConsumer) {
        return peek(value -> {
            if (!selector.test(value))
                return;
            
            theConsumer.accept(value);
        });
    }
    public default <T> IntStreamPlus peek(IntFunction<T> mapper, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            theConsumer.accept(target);
        });
    }
    
    public default <T> IntStreamPlus peek(IntFunction<T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
    //-- FlatMap --
    
    public default IntStreamPlus flatMapOnly(
            IntPredicate                     checker, 
            IntFunction<? extends IntStream> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : IntStreamPlus.of(d));
    }
    public default IntStreamPlus flatMapIf(
            IntPredicate checker, 
            IntFunction<? extends IntStream> mapper, 
            IntFunction<? extends IntStream> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> StreamPlus<T> flatMapToObjIf(
            IntPredicate   checker, 
            IntFunction<? extends Stream<T>> mapper, 
            IntFunction<? extends Stream<T>> elseMapper) {
        val stream = mapToObj(d -> {
            return checker.test(d) 
                    ? mapper    .apply(d) 
                    : elseMapper.apply(d);
        });
        return stream.flatMap(themAll());
    }
    
}
