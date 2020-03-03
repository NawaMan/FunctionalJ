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

import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;

public interface IntStreamableWithSegment {
    
    public IntStreamPlus stream();
    
    //== segment ==
    
    public default StreamPlus<IntStreamPlus> segment(int count) {
        return ()->{
            return stream()
                    .segment(count);
        };
    }
    public default StreamPlus<IntStreamPlus> segment(
            int count, 
            IncompletedSegment incompletedSegment) {
        return ()->{
            return stream()
                    .segment(count, incompletedSegment);
        };
    }
    public default StreamPlus<IntStreamPlus> segment(
            int count, 
            boolean includeIncompletedSegment) {
        return ()->{
            return stream()
                    .segment(count, includeIncompletedSegment);
        };
    }
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition) {
        return ()->{
            return stream()
                    .segment(startCondition);
        };
    }
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IncompletedSegment incompletedSegment) {
        return ()->{
            return stream()
                    .segment(startCondition, incompletedSegment);
        };
    }
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition, 
            boolean includeIncompletedSegment) {
        return ()->{
            return stream()
                    .segment(startCondition, includeIncompletedSegment);
        };
    }
    
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IntPredicate endCondition) {
        return ()->{
            return stream()
                    .segment(startCondition, endCondition);
        };
    }
    
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IntPredicate endCondition, 
            IncompletedSegment incompletedSegment) {
        return ()->{
            return stream()
                    .segment(startCondition, endCondition, incompletedSegment);
        };
    }
    
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition, 
            IntPredicate endCondition, 
            boolean includeIncompletedSegment) {
        return ()->{
            return stream()
                    .segment(startCondition, endCondition, includeIncompletedSegment);
        };
    }
    
    public default StreamPlus<IntStreamPlus> segmentSize(
            IntUnaryOperator segmentSize) {
        return ()->{
            return stream()
                    .segmentSize(segmentSize);
        };
    }
    
    public default StreamPlus<IntStreamPlus> segmentSize(
            IntUnaryOperator segmentSize, 
            IncompletedSegment incompletedSegment) {
        return ()->{
            return stream()
                    .segmentSize(segmentSize, incompletedSegment);
        };
    }
    
    public default StreamPlus<IntStreamPlus> segmentSize(
            IntUnaryOperator segmentSize, 
            boolean includeTail) {
        return ()->{
            return stream()
                    .segmentSize(segmentSize, includeTail);
        };
    }
    
    //== Collapse ==
    
    public default IntStreamPlus collapseWhen(
            IntPredicate conditionToCollapse, 
            IntBinaryOperator concatFunc) {
        return ()->{
            return stream()
                    .collapseWhen(conditionToCollapse, concatFunc);
        };
    }
    
    public default IntStreamPlus collapseAfter(
            IntPredicate conditionToCollapseNext, 
            IntBinaryOperator concatFunc) {
        return ()->{
            return stream()
                    .collapseAfter(conditionToCollapseNext, concatFunc);
        };
    }
    
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc) {
        return ()->{
            return stream()
                    .collapseSize(segmentSize, concatFunc);
        };
    }
    
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc,
            IncompletedSegment incompletedSegment) {
        return ()->{
            return stream()
                    .collapseSize(segmentSize, concatFunc, incompletedSegment);
        };
    }
    
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize, 
            IntBinaryOperator concatFunc,
            boolean includeTail) {
        return ()->{
            return stream()
                    .collapseSize(segmentSize, concatFunc, includeTail);
        };
    }
}

