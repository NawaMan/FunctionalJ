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
package functionalj.stream.intstream;

import java.util.Collection;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
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
import functionalj.stream.Streamable;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.ObjIntTuple;

public interface IntStreamableAddtionalOperators {
////    
////    public IntStreamable deriveWith(Function<IntStream, IntStream> action);
////    
////    public <TARGET> Streamable<TARGET> deriveFrom(Function<IntStream, Stream<TARGET>> action);
////    
////    
//    //--map with condition --
//    
//    public default IntStreamable mapOnly(
//            IntPredicate     checker, 
//            IntUnaryOperator mapper) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapOnly(checker, mapper));
//    }
//    
//    public default IntStreamable mapIf(
//            IntPredicate     checker, 
//            IntUnaryOperator mapper, 
//            IntUnaryOperator elseMapper) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapIf(checker, mapper, elseMapper));
//    }
//    
//    public default <T> Streamable<T> mapToObjIf(
//            IntPredicate   checker, 
//            IntFunction<T> mapper, 
//            IntFunction<T> elseMapper) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapToObjIf(checker, mapper, elseMapper));
//    }
//    
//    //-- mapWithIndex --
//    
//    
//    public default IntStreamable mapWithIndex(
//            IntBinaryOperator mapper) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapWithIndex(mapper));
//    }
//    
//    public default Streamable<IntIntTuple> mapWithIndex() {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapWithIndex());
//    }
//    
//    public default <T> Streamable<T> mapToObjWithIndex(
//            IntIntBiFunction<T> mapper) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapToObjWithIndex(mapper));
//    }
//    
//    public default <T1, T> Streamable<T> mapToObjWithIndex(
//                IntFunction<? extends T1>       valueMapper,
//                IntObjBiFunction<? super T1, T> combiner) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapToObjWithIndex(valueMapper, combiner));
//    }
//    
//    //-- mapWithPrev --
//    
//    public default Streamable<ObjIntTuple<OptionalInt>> mapWithPrev() {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapWithPrev());
//    }
//    
//    public default <TARGET> Streamable<TARGET> mapWithPrev(
//            ObjIntBiFunction<OptionalInt, ? extends TARGET> mapper) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapWithPrev(mapper));
//    }
//    
//    //-- Filter --
//    
//    public default IntStreamable filter(
//            IntUnaryOperator mapper, 
//            IntPredicate     predicate) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .filter(mapper, predicate));
//    }
//    
//    public default <T> IntStreamable filter(
//            IntFunction<T>       mapper, 
//            Predicate<? super T> theCondition) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .filter(mapper, theCondition));
//    }
//    
//    public default IntStreamable filterIn(int[] array) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .filterIn(array));
//    }
//    
//    public default IntStreamable filterIn(Collection<Integer> collection) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .filterIn(collection));
//    }
//    
//    public default IntStreamable exclude(IntPredicate predicate) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .exclude(predicate));
//    }
//    
//    public default IntStreamable excludeIn(int[] array) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .excludeIn(array));
//    }
//    
//    public default IntStreamable excludeIn(Collection<Integer> collection) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .excludeIn(collection));
//    }
//    
//    public default IntStreamable filterWithIndex(
//            IntBiPredicatePrimitive predicate) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .filterWithIndex(predicate));
//    }
//    
//    //-- Peek --
//    
//    public default IntStreamable peek(
//            IntPredicate selector, 
//            IntConsumer  theConsumer) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .peek(selector, theConsumer));
//    }
//    public default <T> IntStreamable peek(
//            IntFunction<T>      mapper, 
//            Consumer<? super T> theConsumer) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .peek(mapper, theConsumer));
//    }
//    
//    public default <T> IntStreamable peek(
//            IntFunction<T>       mapper, 
//            Predicate<? super T> selector, 
//            Consumer<? super T>  theConsumer) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .peek(mapper, selector, theConsumer));
//    }
//    
//    //-- FlatMap --
//    
//    public default IntStreamable flatMapOnly(
//            IntPredicate                     checker, 
//            IntFunction<? extends IntStream> mapper) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .flatMapOnly(checker, mapper));
//    }
//    public default IntStreamable flatMapIf(
//            IntPredicate                     checker, 
//            IntFunction<? extends IntStream> mapper, 
//            IntFunction<? extends IntStream> elseMapper) {
//        return deriveWith(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .flatMapIf(checker, mapper, elseMapper));
//    }
//    
//    public default <T> Streamable<T> flatMapToObjIf(
//            IntPredicate                     checker, 
//            IntFunction<? extends Stream<T>> mapper, 
//            IntFunction<? extends Stream<T>> elseMapper) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .flatMapToObjIf(checker, mapper, elseMapper));
//    }
//    
}
