// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list.longlist;

import static functionalj.list.longlist.LongFuncList.deriveFrom;
import static functionalj.list.longlist.LongFuncList.deriveToObj;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.list.FuncList;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.longstream.LongStreamPlus;

public interface LongFuncListWithModify extends AsLongFuncList {
    
    /**
     * Accumulate the previous to the next element.
     *
     * For example:
     *      inputs = [i1, i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *      and ~ is a accumulate function
     *
     * From this we get
     *      acc0  = head of inputs =&gt; i1
     *      rest0 = tail of inputs =&gt; [i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *
     * The outputs are:
     *     output0 = acc0 with acc1 = acc0 ~ rest0 and rest1 = rest of rest0
     *     output1 = acc1 with acc2 = acc1 ~ rest1 and rest2 = rest of rest1
     *     output2 = acc2 with acc3 = acc2 ~ rest2 and rest3 = rest of rest2
     *     ...
     */
    public default LongFuncList accumulate(LongBinaryOperator accumulator) {
        return deriveFrom(this, stream -> stream.accumulate(accumulator));
    }
    
    // == restate ==
    /**
     * Use each of the element to recreate the stream by applying each element to the rest of the stream and repeat.
     *
     * For example:
     *      inputs = [i1, i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *      and ~ is a restate function
     *
     * From this we get
     *      head0 = head of inputs = i1
     *      rest0 = tail of inputs = [i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *
     * The outputs are:
     *     output0 = head0 with rest1 = head0 ~ rest0 and head1 = head of rest0
     *     output1 = head1 with rest2 = head1 ~ rest1 and head2 = head of rest2
     *     output2 = head2 with rest3 = head2 ~ rest2 and head3 = head of rest3
     *     ...
     */
    public default LongFuncList restate(LongObjBiFunction<LongStreamPlus, LongStreamPlus> restater) {
        return deriveFrom(this, stream -> stream.restate(restater));
    }
    
    // == Spawn ==
    /**
     * Map each element to a uncompleted action, run them and collect which ever finish first.
     * The result stream will not be the same order with the original one
     *   -- as stated, the order will be the order of completion.
     * If the result StreamPlus is closed (which is done everytime a terminal operation is done),
     *   the unfinished actions will be canceled.
     */
    public default <T> FuncList<Result<T>> spawn(LongFunction<? extends UncompletedAction<T>> mapToAction) {
        return deriveToObj(this, stream -> stream.spawn(mapToAction));
    }
}
