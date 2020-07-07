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

import static functionalj.stream.StreamPlusHelper.derive;
import static functionalj.tuple.Tuple.tuple2;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.result.Result;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusAddtionalOperators<DATA> extends AsStreamPlus<DATA> {
    
    //--map with condition --
    
    public default StreamPlus<DATA> mapOnly(
            Predicate<? super DATA>      checker, 
            Function<? super DATA, DATA> mapper) {
        return streamPlus()
                .map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return streamPlus()
                .map(d -> {
                    return checker.test(d) 
                            ? mapper    .apply(d) 
                            : elseMapper.apply(d);
                });
    }
    
    public default <T> StreamPlus<T> mapToObjIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return streamPlus()
                .mapToObj(d -> {
                    return checker.test(d) 
                            ? mapper.apply(d) 
                            : elseMapper.apply(d);
                });
    }
    
    //-- mapWithIndex --
    
    public default StreamPlus<Tuple2<Integer, DATA>> mapWithIndex() {
        val index = new AtomicInteger();
        return streamPlus()
                .mapToObj(each -> tuple2(index.getAndIncrement(), each));
    }
    
    public default <T> StreamPlus<T> mapWithIndex(BiFunction<? super Integer, ? super DATA, T> mapper) {
        val index = new AtomicInteger();
        return streamPlus()
                .mapToObj(each -> {
                    val i = index.getAndIncrement();
                    val target = mapper.apply(i, each);
                    return target;
                });
    }
    
    public default <T> StreamPlus<T> mapWithIndexToObj(BiFunction<? super Integer, ? super DATA, T> mapper) {
        val index = new AtomicInteger();
        return streamPlus()
                .mapToObj(each -> {
                    val i = index.getAndIncrement();
                    val target = mapper.apply(i, each);
                    return target;
                });
    }
    
    public default <T1, T> StreamPlus<T> mapWithIndex(
                Function<? super DATA, ? extends T1>       valueMapper,
                BiFunction<? super Integer, ? super T1, T> combiner) {
        val index = new AtomicInteger();
        return streamPlus()
                .mapToObj(each -> {
                    val i      = index.getAndIncrement();
                    val value  = valueMapper.apply(each);
                    val target = combiner.apply(i, value);
                    return target;
                });
    }
    
    public default <T1, T> StreamPlus<T> mapToObjWithIndex(
                Function<? super DATA, ? extends T1>       mapper1,
                BiFunction<? super Integer, ? super T1, T> mapper) {
        return streamPlus()
                .mapWithIndex(mapper1, mapper);
    }
    
    //-- mapWithPrev --
    
    public default StreamPlus<Tuple2<? super Result<DATA>, ? super DATA>> mapWithPrev() {
        val prev = new AtomicReference<Result<DATA>>(Result.ofNotExist());
        return streamPlus()
                .mapToObj(element -> {
                    val prevValue = prev.get();
                    prev.set(Result.valueOf(element));
                    val result = Tuple.of(prevValue, element);
                    return result;
                });
    }
    
    public default <TARGET> StreamPlus<TARGET> mapWithPrev(
            BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        val prev = new AtomicReference<Result<DATA>>(Result.ofNotExist());
        return streamPlus()
                .mapToObj(element -> {
                    val prevValue = prev.get();
                    val newValue  = mapper.apply(prevValue, element);
                    prev.set(Result.valueOf(element));
                    return newValue;
                });
    }
    
    //-- Filter --
    
    public default StreamPlus<DATA> filterNonNull() {
        return derive(this, stream -> stream.filter(Objects::nonNull));
    }
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> filterIn(DATA ... items) {
        return filterIn(asList((DATA[])items));
    }
    
    public default StreamPlus<DATA> filterIn(Collection<? super DATA> collection) {
        return derive(this, stream -> {
            return (collection == null)
                ? Stream.empty()
                : stream.filter(data -> collection.contains(data));
        });
    }
    
    public default StreamPlus<DATA> exclude(Predicate<? super DATA> predicate) {
        return derive(this, stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(data -> !predicate.test(data));
        });
    }
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> excludeIn(DATA ... items) {
        return streamPlus()
                .excludeIn(asList((DATA[])items));
    }
    
    public default StreamPlus<DATA> excludeIn(Collection<? super DATA> collection) {
        return derive(this, stream -> {
            return (collection == null)
                ? stream
                : stream.filter(data -> !collection.contains(data));
        });
    }
    
    public default <T> StreamPlus<DATA> filter(Class<T> clzz) {
        return streamPlus()
                .filter(clzz::isInstance);
    }
    
    public default <T> StreamPlus<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return streamPlus()
                .filter(value -> {
                    if (!clzz.isInstance(value))
                        return false;
                    
                    val target = clzz.cast(value);
                    val isPass = theCondition.test(target);
                    return isPass;
                });
    }
    
    public default <T> StreamPlus<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return streamPlus()
                .filter(value -> {
                    val target = mapper.apply(value);
                    val isPass = theCondition.test(target);
                    return isPass;
                });
    }
    
    public default StreamPlus<DATA> filterWithIndex(BiFunction<? super Integer, ? super DATA, Boolean> predicate) {
        val index = new AtomicInteger();
        return streamPlus()
                .filter(each -> {
                    return (predicate != null) 
                            && predicate.apply(index.getAndIncrement(), each);
                });
    }
    
    //-- Peek --
    
    public default <T extends DATA> StreamPlus<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return streamPlus()
                .peek(value -> {
                    if (!clzz.isInstance(value))
                        return;
                    
                    val target = clzz.cast(value);
                    theConsumer.accept(target);
                });
    }
    public default StreamPlus<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return streamPlus()
                .peek(value -> {
                    if (!selector.test(value))
                        return;
                    
                    theConsumer.accept(value);
                });
    }
    public default <T> StreamPlus<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return streamPlus()
                .peek(value -> {
                    val target = mapper.apply(value);
                    theConsumer.accept(target);
                });
    }
    
    public default <T> StreamPlus<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return streamPlus()
                .peek(value -> {
                    val target = mapper.apply(value);
                    if (selector.test(target))
                        theConsumer.accept(target);
                });
    }
    
    //-- FlatMap --
    
    public default StreamPlus<DATA> flatMapOnly(
            Predicate<? super DATA>                        checker, 
            Function<? super DATA, ? extends Stream<DATA>> mapper) {
        return streamPlus()
                .flatMap(d -> checker.test(d) ? mapper.apply(d) : StreamPlus.of(d));
    }
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Stream<T>> mapper, 
            Function<? super DATA, Stream<T>> elseMapper) {
        return streamPlus()
                .flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    public default <T> StreamPlus<T> flatMapToObjIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Stream<T>> mapper, 
            Function<? super DATA, Stream<T>> elseMapper) {
        return streamPlus()
                .flatMapIf(checker, mapper, elseMapper);
    }
    
}
