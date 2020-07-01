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
package functionalj.list;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import functionalj.tuple.Tuple2;

public interface FuncListAdditionalOperations<DATA>
        extends Streamable<DATA> {
    
    public <TARGET> FuncList<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action);
    
    //--map with condition --
    
    public default FuncList<DATA> mapOnly(
            Predicate<? super DATA>      checker, 
            Function<? super DATA, DATA> mapper) {
        return deriveWith(stream -> {
                return StreamPlus
                        .from(stream)
                        .mapOnly(checker, mapper);
            });
    }
    
    public default <T> FuncList<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapIf(checker, mapper, elseMapper);
        });
    }
    
    //-- mapWithIndex --
    
    public default FuncList<Tuple2<Integer, DATA>> mapWithIndex() {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithIndex();
        });
    }
    
    public default <T> FuncList<T> mapWithIndex(
            BiFunction<? super Integer, ? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithIndex(mapper);
        });
    }
    
    public default <T1, T> FuncList<T> mapWithIndex(
                Function<? super DATA, ? extends T1>       mapper1,
                BiFunction<? super Integer, ? super T1, T> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithIndex(mapper1, mapper);
        });
    }
    
    //-- mapWithPrev --
    
    public default <TARGET> FuncList<TARGET> mapWithPrev(
            BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithPrev(mapper);
        });
    }
    
    //-- Filter --
    
    public default FuncList<Tuple2<? super Result<DATA>, ? super DATA>> mapWithPrev() {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithPrev();
        });
    }
    
    public default FuncList<DATA> filterIn(
            Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filterIn(collection);
        });
    }
    
    public default FuncList<DATA> exclude(
            Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .exclude(predicate);
        });
    }
    
    public default FuncList<DATA> excludeIn(
            Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .excludeIn(collection);
        });
    }
    
    public default <T> FuncList<DATA> filter(
            Class<T> clzz) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filter(clzz);
        });
    }
    
    public default <T> FuncList<DATA> filter(
            Class<T>             clzz, 
            Predicate<? super T> theCondition) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filter(clzz, theCondition);
        });
    }
    
    public default <T> FuncList<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filter(mapper, theCondition);
        });
    }
    
    public default FuncList<DATA> filterWithIndex(
            BiFunction<? super Integer, ? super DATA, Boolean> predicate) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filterWithIndex(predicate);
        });
    }
    
    //-- Peek --
    
    public default <T extends DATA> FuncList<DATA> peek(
            Class<T>            clzz, 
            Consumer<? super T> theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peek(clzz, theConsumer);
        });
    }
    public default FuncList<DATA> peek(
            Predicate<? super DATA> selector, 
            Consumer<? super DATA>  theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peek(selector, theConsumer);
        });
    }
    public default <T> FuncList<DATA> peek(
            Function<? super DATA, T> mapper, 
            Consumer<? super T>       theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peek(mapper, theConsumer);
        });
    }
    
    public default <T> FuncList<DATA> peek(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      selector, 
            Consumer<? super T>       theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peek(mapper, selector, theConsumer);
        });
    }
    
    //-- FlatMap --
    
    public default FuncList<DATA> flatMapOnly(
            Predicate<? super DATA>                            checker, 
            Function<? super DATA, ? extends Streamable<DATA>> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .flatMapOnly(
                            checker, 
                            d -> mapper.apply(d).stream());
        });
    }
    public default <T> FuncList<T> flatMapIf(
            Predicate<? super DATA>                         checker, 
            Function<? super DATA, ? extends Streamable<T>> mapper, 
            Function<? super DATA, ? extends Streamable<T>> elseMapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .flatMapIf(
                            checker, 
                            d -> mapper    .apply(d).stream(), 
                            d -> elseMapper.apply(d).stream());
        });
    }
}
