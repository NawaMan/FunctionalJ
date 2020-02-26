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

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.stream.Streamable;

public interface IntStreamableWithSegment {
////    
////    public IntStreamable deriveWith(Function<IntStream, IntStream> action);
////    
////    public <TARGET> Streamable<TARGET> deriveFrom(Function<IntStream, Stream<TARGET>> action);
////    
////    
//    //== segment ==
//    
//    public default Streamable<IntStreamPlus> segment(
//            int count) {
//        return deriveFrom(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .segment(count));
//    }
//    public default Streamable<IntStreamPlus> segment(
//            int     count, 
//            boolean includeTail) {
//        return deriveFrom(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .segment(count, includeTail));
//    }
//    public default Streamable<IntStreamPlus> segment(
//            IntPredicate startCondition) {
//        return deriveFrom(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .segment(startCondition));
//    }
//    public default Streamable<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            boolean      includeTail) {
//        return deriveFrom(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .segment(startCondition, includeTail));
//    }
//    
//    public default Streamable<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            IntPredicate endCondition) {
//        return deriveFrom(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .segment(startCondition, endCondition));
//    }
//    
//    public default Streamable<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            IntPredicate endCondition, 
//            boolean      includeTail) {
//        return deriveFrom(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .segment(startCondition, endCondition, includeTail));
//    }
//    
//    public default Streamable<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize) {
//        return deriveFrom(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .segmentSize(segmentSize));
//    }
//    
//    public default IntStreamable collapseWhen(
//            IntPredicate      conditionToCollapse, 
//            IntBinaryOperator concatFunc) {
//        return deriveWith(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .collapseWhen(conditionToCollapse, concatFunc));
//    }
//    
//    public default IntStreamable collapseSize(
//            IntUnaryOperator  segmentSize, 
//            IntBinaryOperator concatFunc) {
//        return deriveWith(stream ->
//                IntStreamPlus
//                    .from(stream)
//                    .collapseSize(segmentSize, concatFunc));
//    }
//    
//    // 
////    @SuppressWarnings("unchecked")
////    public default <TARGET> Streamable<TARGET> collapseSize(
////            Func1<DATA, Integer>          segmentSize, 
////            Func1<DATA, TARGET>           mapper, 
////            Func2<TARGET, TARGET, TARGET> concatFunc) {
////        val firstObj = new Object();
////        return useIterator(iterator -> {
////            val prev = new AtomicReference<Object>(firstObj);
////            StreamPlus<TARGET> resultStream = StreamPlus.generateWith(()->{
////                if (prev.get() == StreamPlusHelper.dummy)
////                    throw new NoMoreResultException();
////                
////                while(true) {
////                    DATA next;
////                    try {
////                        next = iterator.next();
////                    } catch (NoSuchElementException e) {
////                        if (prev.get() == firstObj)
////                            throw new NoMoreResultException();
////                        
////                        val yield = prev.get();
////                        prev.set(StreamPlusHelper.dummy);
////                        return (TARGET)yield;
////                    }
////                    
////                    Integer newSize = segmentSize.apply(next);
////                    if ((newSize == null) || (newSize == 0)) {
////                        continue;
////                    }
////                    
////                    if (newSize == 1) {
////                        val target = (TARGET)mapper.apply((DATA)next);
////                        return target;
////                    }
////                    
////                    TARGET target = (TARGET)mapper.apply((DATA)next);
////                    prev.set(target);
////                    for (int i = 0; i < (newSize - 1); i++) {
////                        try {
////                            next   = iterator.next();
////                            target = (TARGET)mapper.apply((DATA)next);
////                            val prevValue = (TARGET)prev.get();
////                            val newValue  = concatFunc.apply(prevValue, target);
////                            prev.set(newValue);
////                        } catch (NoSuchElementException e) {
////                            val yield = prev.get();
////                            prev.set(StreamPlusHelper.dummy);
////                            return (TARGET)yield;
////                        }
////                    }
////                    
////                    val yield = prev.get();
////                    prev.set(firstObj);
////                    return (TARGET)yield;
////                }
////            });
////            
////            return resultStream;
////        });
////    }
}
