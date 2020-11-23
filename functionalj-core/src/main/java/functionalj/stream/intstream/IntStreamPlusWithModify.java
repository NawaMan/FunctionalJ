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

import static functionalj.stream.intstream.IntStreamPlusHelper.sequentialToObj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import functionalj.function.FuncUnit1;
import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntObjBiFunction;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.markers.Sequential;
import functionalj.tuple.IntTuple2;
import lombok.val;


public interface IntStreamPlusWithModify {
    
    public IntStreamPlus intStreamPlus();
    
    /**
     * Accumulate the previous to the next element.
     * 
     * For example:
     *      inputs = [i1, i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *      and ~ is a accumulate function
     * 
     * From this we get
     *      acc0  = head of inputs => i1
     *      rest0 = tail of inputs => [i2, i3, i4, i5, i6, i7, i8, i9, i10]
     * 
     * The outputs are:
     *     output0 = acc0 with acc1 = acc0 ~ rest0 and rest1 = rest of rest0
     *     output1 = acc1 with acc2 = acc1 ~ rest1 and rest2 = rest of rest1
     *     output2 = acc2 with acc3 = acc2 ~ rest2 and rest3 = rest of rest2
     *     ...
     */
    @Sequential(knownIssue = true, comment = "Need to enforce the sequential.")
    public default IntStreamPlus accumulate(IntBiFunctionPrimitive accumulator) {
        val streamPlus = intStreamPlus();
        val iterator   = streamPlus.iterator();
        if (!iterator.hasNext())
            return IntStreamPlus.empty();
        
        val prev = new int[] { iterator.nextInt() };
        return IntStreamPlus
                .concat(
                    IntStreamPlus.of(prev[0]),
                    iterator
                    .stream()
                    .map(n -> {
                        val next = accumulator.applyAsIntAndInt(n, prev[0]);
                        prev[0] = next;
                        return next;
                    })
                 );
    }
    
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
     **/
    @Sequential(knownIssue = true, comment = "Need to enforce the sequential.")
    public default IntStreamPlus restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
        val streamPlus = intStreamPlus();
        val func = (UnaryOperator<IntTuple2<IntStreamPlus>>)((IntTuple2<IntStreamPlus> pair) -> {
            val stream = pair._2();
            if (stream == null)
                return null;
            
            val iterator = stream.iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = new int[] { iterator.nextInt() };
            val tail = IntObjBiFunction.apply(restater, head[0], IntIteratorPlus.from(iterator).stream());
            if (tail == null)
                return null;
            
            return IntTuple2.<IntStreamPlus>of(head[0], tail);
        });
        val seed = IntTuple2.<IntStreamPlus>of(0, streamPlus);
        IntStreamPlus endStream 
            = StreamPlus
            .iterate  (seed, func)
            .takeUntil(t -> t == null)
            .skip     (1)
            .mapToInt (t -> t._1());
        return endStream;
    }
    
    /**
     * Map each element to a uncompleted action, run them and collect which ever finish first.
     * The result stream will not be the same order with the original one 
     *   -- as stated, the order will be the order of completion.
     * If the result StreamPlus is closed (which is done everytime a terminal operation is done),
     *   the unfinished actions will be canceled.
     */
    public default <T> StreamPlus<Result<T>> spawn(IntFunction<? extends UncompletedAction<T>> mapToAction) {
        val streamPlus = intStreamPlus();
        return sequentialToObj(streamPlus, stream -> {
            val results = new ArrayList<DeferAction<T>>();
            val index   = new AtomicInteger(0);
            
            FuncUnit1<UncompletedAction<T>> setOnComplete = action -> 
                action
                .getPromise()
                .onComplete(result -> {
                    val thisIndex  = index.getAndIncrement();
                    val thisAction = results.get(thisIndex);
                    if (result.isValue())
                         thisAction.complete(result.value());
                    else thisAction.fail    (result.exception());
                });
            
            List<? extends UncompletedAction<T>> actions 
                = stream
                .mapToObj(mapToAction)
                .peek    (action -> results.add(DeferAction.<T>createNew()))
                .peek    (action -> setOnComplete.accept(action))
                .peek    (action -> action.start())
                .collect (Collectors.toList())
                ;
            
            val resultStream 
                = StreamPlus
                .from(results.stream().map(action -> action.getResult()));
            resultStream
                .onClose(()->actions.forEach(action -> action.abort("Stream closed!")));
            
            return resultStream;
        });
    }
}
