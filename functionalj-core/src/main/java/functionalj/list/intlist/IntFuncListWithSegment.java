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
package functionalj.list.intlist;

public interface IntFuncListWithSegment {
//    
//    public IntStreamPlus intStream();
//    
//    public IntFuncList derive(IntStreamable streamable);
//    
//    public <TARGET> FuncList<TARGET> deriveToList(Streamable<TARGET> streamable);
//    
//    //== segment ==
//    
//    public default FuncList<IntStreamPlus> segment(int count) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(count);
//        });
//    }
//    public default FuncList<IntStreamPlus> segment(
//            int count, 
//            IncompletedSegment incompletedSegment) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(count, incompletedSegment);
//        });
//    }
//    public default FuncList<IntStreamPlus> segment(
//            int count, 
//            boolean includeIncompletedSegment) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(count, includeIncompletedSegment);
//        });
//    }
//    public default FuncList<IntStreamPlus> segment(
//            IntPredicate startCondition) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(startCondition);
//        });
//    }
//    public default FuncList<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            IncompletedSegment incompletedSegment) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(startCondition, incompletedSegment);
//        });
//    }
//    public default FuncList<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            boolean includeIncompletedSegment) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(startCondition, includeIncompletedSegment);
//        });
//    }
//    
//    public default FuncList<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            IntPredicate endCondition) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(startCondition, endCondition);
//        });
//    }
//    
//    public default FuncList<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            IntPredicate endCondition, 
//            IncompletedSegment incompletedSegment) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(startCondition, endCondition, incompletedSegment);
//        });
//    }
//    
//    public default FuncList<IntStreamPlus> segment(
//            IntPredicate startCondition, 
//            IntPredicate endCondition, 
//            boolean includeIncompletedSegment) {
//        return deriveToList(()->{
//            return intStream()
//                    .segment(startCondition, endCondition, includeIncompletedSegment);
//        });
//    }
//    
//    public default FuncList<IntStreamPlus> segmentSize(
//            IntUnaryOperator segmentSize) {
//        return deriveToList(()->{
//            return intStream()
//                    .segmentSize(segmentSize);
//        });
//    }
//    
//    public default FuncList<IntStreamPlus> segmentSize(
//            IntUnaryOperator segmentSize, 
//            IncompletedSegment incompletedSegment) {
//        return deriveToList(()->{
//            return intStream()
//                    .segmentSize(segmentSize, incompletedSegment);
//        });
//    }
//    
//    public default FuncList<IntStreamPlus> segmentSize(
//            IntUnaryOperator segmentSize, 
//            boolean includeTail) {
//        return deriveToList(()->{
//            return intStream()
//                    .segmentSize(segmentSize, includeTail);
//        });
//    }
//    
//    //== Collapse ==
//    
//    public default IntFuncList collapseWhen(
//            IntPredicate conditionToCollapse, 
//            IntBinaryOperator concatFunc) {
//        return derive(()->{
//            return intStream()
//                    .collapseWhen(conditionToCollapse, concatFunc);
//        });
//    }
//    
//    public default IntFuncList collapseAfter(
//            IntPredicate conditionToCollapseNext, 
//            IntBinaryOperator concatFunc) {
//        return derive(()->{
//            return intStream()
//                    .collapseAfter(conditionToCollapseNext, concatFunc);
//        });
//    }
//    
//    public default IntFuncList collapseSize(
//            IntUnaryOperator  segmentSize, 
//            IntBinaryOperator concatFunc) {
//        return derive(()->{
//            return intStream()
//                    .collapseSize(segmentSize, concatFunc);
//        });
//    }
//    
//    public default IntFuncList collapseSize(
//            IntUnaryOperator  segmentSize, 
//            IntBinaryOperator concatFunc,
//            IncompletedSegment incompletedSegment) {
//        return derive(()->{
//            return intStream()
//                    .collapseSize(segmentSize, concatFunc, incompletedSegment);
//        });
//    }
//    
//    public default IntFuncList collapseSize(
//            IntUnaryOperator  segmentSize, 
//            IntBinaryOperator concatFunc,
//            boolean includeTail) {
//        return derive(()->{
//            return intStream()
//                    .collapseSize(segmentSize, concatFunc, includeTail);
//        });
//    }
}

