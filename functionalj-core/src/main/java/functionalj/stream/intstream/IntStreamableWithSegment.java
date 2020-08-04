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

import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import functionalj.stream.IncompletedSegment;
import functionalj.streamable.Streamable;

public interface IntStreamableWithSegment {
    
    public IntStreamPlus intStream();
    
    //== segment ==
    
    public default Streamable<IntStreamPlus> segmentSize(int count) {
        return ()->{
            return intStream()
                    .segmentSize(count);
        };
    }
    
    public default Streamable<IntStreamPlus> segmentSize(
            int count, 
            IncompletedSegment incompletedSegment) {
        return ()->{
            return intStream()
                    .segmentSize(count, incompletedSegment);
        };
    }
    
    public default Streamable<IntStreamPlus> segmentSize(
            int count, 
            boolean includeIncompletedSegment) {
        return ()->{
            return intStream()
                    .segmentSize(count, includeIncompletedSegment);
        };
    }
    
    public default Streamable<IntStreamPlus> segment(
            IntPredicate startCondition) {
        return ()->{
            return intStream()
                    .segment(startCondition);
        };
    }
    
    public default Streamable<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IncompletedSegment incompletedSegment) {
        return ()->{
            return intStream()
                    .segment(startCondition, incompletedSegment);
        };
    }
    
    public default Streamable<IntStreamPlus> segment(
            IntPredicate startCondition, 
            boolean includeIncompletedSegment) {
        return ()->{
            return intStream()
                    .segment(startCondition, includeIncompletedSegment);
        };
    }
    
    public default Streamable<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IntPredicate endCondition) {
        return ()->{
            return intStream()
                    .segment(startCondition, endCondition);
        };
    }
    
    public default Streamable<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IntPredicate endCondition, 
            IncompletedSegment incompletedSegment) {
        return ()->{
            return intStream()
                    .segment(startCondition, endCondition, incompletedSegment);
        };
    }
    
    public default Streamable<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IntPredicate endCondition, 
            boolean includeIncompletedSegment) {
        return ()->{
            return intStream()
                    .segment(startCondition, endCondition, includeIncompletedSegment);
        };
    }
    
    public default Streamable<IntStreamPlus> segmentSize(
            IntFunction<Integer> segmentSize) {
        return ()->{
            return intStream()
                    .segmentSize(segmentSize);
        };
    }
    
    public default Streamable<IntStreamPlus> segmentSize(
            IntFunction<Integer> segmentSize, 
            IncompletedSegment   incompletedSegment) {
//        return ()->{
//            return intStream()
//                    .segmentSize(segmentSize, incompletedSegment);
//        };
        return null;
    }
    
    public default Streamable<IntStreamPlus> segmentSize(
            IntUnaryOperator segmentSize, 
            boolean includeTail) {
//        return ()->{
//            return intStream()
//                    .segmentSize(segmentSize, includeTail);
//        };
        return null;
    }
    
    //== Collapse ==
    
    public default IntStreamable collapseWhen(
            IntPredicate conditionToCollapse, 
            IntBinaryOperator concatFunc) {
        return ()->{
            return intStream()
                    .collapseWhen(conditionToCollapse, concatFunc);
        };
    }
    
    public default IntStreamable collapseAfter(
            IntPredicate conditionToCollapseNext, 
            IntBinaryOperator concatFunc) {
        return ()->{
            return intStream()
                    .collapseAfter(conditionToCollapseNext, concatFunc);
        };
    }
    
    public default IntStreamable collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc) {
//        return ()->{
//            return intStream()
//                    .collapseSize(segmentSize, concatFunc);
//        };
        return null;
    }
    
    public default IntStreamable collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc,
            IncompletedSegment incompletedSegment) {
//        return ()->{
//            return intStream()
//                    .collapseSize(segmentSize, concatFunc, incompletedSegment);
//        };
        return null;
    }
    
    public default IntStreamable collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc,
            boolean includeTail) {
//        return ()->{
//            return intStream()
//                    .collapseSize(segmentSize, concatFunc, includeTail);
//        };
        return null;
    }
}

