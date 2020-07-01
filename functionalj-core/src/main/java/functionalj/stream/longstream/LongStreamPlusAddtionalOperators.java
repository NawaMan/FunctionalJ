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
package functionalj.stream.longstream;

import static functionalj.function.Func.themAll;
import static java.lang.System.arraycopy;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

import java.util.Collection;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.IntLongPredicatePrimitive;
import functionalj.function.IntLongToIntFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.function.ObjLongBiFunction;
import functionalj.stream.StreamPlus;
import functionalj.tuple.IntLongTuple;
import functionalj.tuple.ObjLongTuple;
import lombok.val;

public interface LongStreamPlusAddtionalOperators {
    
    public LongStreamPlus map(LongUnaryOperator mapper);
    
    public <T> StreamPlus<T> mapToObj(LongFunction<? extends T> mapper);
    
    public LongStreamPlus flatMap(LongFunction<? extends LongStream> mapper);
    
    public LongStreamPlus filter(LongPredicate predicate);
    
    public LongStreamPlus peek(LongConsumer action);
    
    
    //--map with condition --
    
    public default LongStreamPlus mapOnly(
            LongPredicate     checker, 
            LongUnaryOperator mapper) {
        return map(d -> checker.test(d) ? mapper.applyAsLong(d) : d);
    }
    
    public default LongStreamPlus mapIf(
            LongPredicate     checker, 
            LongUnaryOperator mapper, 
            LongUnaryOperator elseMapper) {
        return map(d -> {
            return checker.test(d) 
                    ? mapper    .applyAsLong(d) 
                    : elseMapper.applyAsLong(d);
        });
    }
    
    public default <T> StreamPlus<T> mapToObjIf(
            LongPredicate   checker, 
            LongFunction<T> mapper, 
            LongFunction<T> elseMapper) {
        return mapToObj(d -> {
            return checker.test(d) 
                    ? mapper.apply(d) 
                    : elseMapper.apply(d);
        });
    }
    
    //-- mapWithIndex --
    
    public default LongStreamPlus mapWithIndex(
            LongBinaryOperator mapper) {
        val index = new AtomicInteger();
        return map(each -> {
            val i = index.getAndIncrement();
            val target = mapper.applyAsLong(i, each);
            return target;
        });
    }
    
    public default StreamPlus<IntLongTuple> mapWithIndex() {
        val index = new AtomicInteger();
        return mapToObj(each -> {
            val i = index.getAndIncrement();
            val intTuple = IntLongTuple.of(i, each);
            return intTuple;
        });
    }
    
    public default <T> StreamPlus<T> mapToObjWithIndex(
            IntLongToIntFunction<T> mapper) {
        val index = new AtomicInteger();
        return mapToObj(each -> {
            val i = index.getAndIncrement();
            val target = mapper.applyInt(i, each);
            return target;
        });
    }
    
    public default <T1, T> StreamPlus<T> mapToObjWithIndex(
                LongFunction<? extends T1>      valueMapper,
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
    
    public default StreamPlus<ObjLongTuple<OptionalLong>> mapWithPrev() {
        val prev = new AtomicReference<OptionalLong>(OptionalLong.empty());
        return mapToObj(element -> {
            val prevValue = prev.get();
            val next      = OptionalLong.of(element);
            val result    = ObjLongTuple.of(prevValue, element);
            prev.set(next);
            return result;
        });
    }
    
    public default <TARGET> StreamPlus<TARGET> mapWithPrev(
            ObjLongBiFunction<OptionalLong, ? extends TARGET> mapper) {
        val prev = new AtomicReference<OptionalLong>(OptionalLong.empty());
        return mapToObj(element -> {
            val prevValue = prev.get();
            val newValue  = mapper.apply(prevValue, element);
            val next      = OptionalLong.of(element);
            prev.set(next);
            return newValue;
        });
    }
    
    //-- Filter --
    
    public default LongStreamPlus filterIn(long ... array) {
        if ((array == null) || (array.length == 0))
            return LongStreamPlus.empty();
        
        val length = array.length;
        val longs   = new long[length];
        arraycopy(array, 0, longs, 0, length);
        sort(longs);
        return filter(intValue -> {
            int index = binarySearch(longs, intValue);
            boolean included = (index >= 0);
            return included;
        });
    }
    
    public default LongStreamPlus filterIn(Collection<Long> collection) {
        if ((collection == null) || collection.isEmpty())
            return LongStreamPlus.empty();
        
        return filter(data -> collection.contains(data));
    }
    
    public default LongStreamPlus exclude(LongPredicate predicate) {
        return filter(data -> !predicate.test(data));
    }
    
    public default LongStreamPlus excludeIn(long... array) {
        if ((array == null) || (array.length == 0))
            return LongStreamPlus.empty();
        
        val length = array.length;
        val longs  = new long[length];
        arraycopy(array, 0, longs, 0, length);
        sort(longs);
        return filter(intValue -> {
            int index = binarySearch(longs, intValue);
            boolean included = (index < 0);
            return included;
        });
    }
    
    public default LongStreamPlus excludeIn(Collection<Long> collection) {
        if ((collection == null) || collection.isEmpty())
            return LongStreamPlus.empty();
        
        return filter(data -> !collection.contains(data));
    }
    
    public default LongStreamPlus filterWithIndex(
            IntLongPredicatePrimitive predicate) {
        val index = new AtomicInteger();
        return filter(each -> {
            val i = index.getAndIncrement();
            return predicate.testIntLong(i, each);
        });
    }
    
    //-- Peek --
    
    public default LongStreamPlus peek(LongPredicate selector, LongConsumer theConsumer) {
        return peek(value -> {
            if (!selector.test(value))
                return;
            
            theConsumer.accept(value);
        });
    }
    public default <T> LongStreamPlus peek(LongFunction<T> mapper, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            theConsumer.accept(target);
        });
    }
    
    public default <T> LongStreamPlus peek(LongFunction<T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
    //-- FlatMap --
    
    public default LongStreamPlus flatMapOnly(
            LongPredicate                      checker, 
            LongFunction<? extends LongStream> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : LongStreamPlus.of(d));
    }
    public default LongStreamPlus flatMapIf(
            LongPredicate checker, 
            LongFunction<? extends LongStreamPlus> mapper, 
            LongFunction<? extends LongStreamPlus> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> StreamPlus<T> flatMapToObjIf(
            LongPredicate   checker, 
            LongFunction<? extends Stream<T>> mapper, 
            LongFunction<? extends Stream<T>> elseMapper) {
        val stream = mapToObj(d -> {
            return checker.test(d) 
                    ? mapper    .apply(d) 
                    : elseMapper.apply(d);
        });
        return stream.flatMap(themAll());
    }
    
}
