// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.list.AsFuncListHelper.funcListOf;
import static functionalj.list.FuncList.deriveFrom;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import functionalj.function.aggregator.AggregationToBoolean;
import lombok.val;

public interface FuncListWithLimit<DATA> extends AsFuncList<DATA> {
    
    /**
     * Limit the size of the stream to the given size.
     */
    public default FuncList<DATA> limit(Long maxSize) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.limit(maxSize));
    }
    
    /**
     * Skip to the given offset position.
     */
    public default FuncList<DATA> skip(Long startAt) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.skip(startAt));
    }
    
    /**
     * Skip any value while the condition is true.
     */
    public default FuncList<DATA> skipWhile(Predicate<? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.skipWhile(condition));
    }
    
    /**
     * Skip any value while the condition is true.
     */
    public default FuncList<DATA> skipWhile(AggregationToBoolean<? super DATA> aggregationCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.skipUntil(aggregationCondition));
    }
    
    /**
     * Skip any value while the condition is true.
     */
    public default FuncList<DATA> skipWhile(BiPredicate<? super DATA, ? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.skipWhile(condition));
    }
    
    /**
     * Skip any value until the condition is true.
     */
    public default FuncList<DATA> skipUntil(Predicate<? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.skipUntil(condition));
    }
    
    /**
     * Skip any value until the condition is true.
     */
    public default FuncList<DATA> skipUntil(AggregationToBoolean<? super DATA> aggregationCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.skipWhile(aggregationCondition));
    }
    
    /**
     * Skip any value until the condition is true.
     */
    public default FuncList<DATA> skipUntil(BiPredicate<? super DATA, ? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.skipWhile(condition));
    }
    
    /**
     * Accept any value while the condition is true.
     */
    public default FuncList<DATA> acceptWhile(Predicate<? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.acceptWhile(condition));
    }
    
    /**
     * Accept any value while the condition is true.
     */
    public default FuncList<DATA> acceptWhile(AggregationToBoolean<? super DATA> aggregationCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.acceptWhile(aggregationCondition));
    }
    
    /**
     * Accept any value while the condition is true.
     */
    public default FuncList<DATA> acceptWhile(BiPredicate<? super DATA, ? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.acceptWhile(condition));
    }
    
    /**
     * Accept any value until the condition is true.
     */
    public default FuncList<DATA> acceptUntil(Predicate<? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.acceptUntil(condition));
    }
    
    /**
     * Accept any value until the condition is true.
     */
    public default FuncList<DATA> acceptUntil(AggregationToBoolean<? super DATA> aggregationCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.acceptUntil(aggregationCondition));
    }
    
    /**
     * Accept any value until the condition is true.
     */
    public default FuncList<DATA> acceptUntil(BiPredicate<? super DATA, ? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.acceptUntil(condition));
    }
    
    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    public default FuncList<DATA> dropAfter(Predicate<? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.dropAfter(condition));
    }
    
    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    public default FuncList<DATA> dropAfter(AggregationToBoolean<? super DATA> aggregationCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.dropAfter(aggregationCondition));
    }
    
    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    public default FuncList<DATA> dropAfter(BiPredicate<? super DATA, ? super DATA> condition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.dropAfter(condition));
    }
}
