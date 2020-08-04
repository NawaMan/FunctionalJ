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

import static functionalj.stream.StreamPlusHelper.sequentialToObj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.makers.Sequential;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusWithModify<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
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
    public default StreamPlus<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
        val streamPlus = streamPlus();
        val iterator   = streamPlus.iterator();
        if (!iterator.hasNext())
            return StreamPlus.empty();
        
        val prev = new AtomicReference<DATA>(iterator.next());
        return StreamPlus
                .concat(
                    StreamPlus.of(prev.get()),
                    iterator
                    .stream()
                    .map(each -> {
                        val next = accumulator.apply(prev.get(), each);
                        prev.set(next);
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default StreamPlus<DATA> restate(BiFunction<? super DATA, StreamPlus<DATA>, StreamPlus<DATA>> restater) {
        val func = (UnaryOperator<Tuple2<DATA, StreamPlus<DATA>>>)((Tuple2<DATA, StreamPlus<DATA>> pair) -> {
            val stream = pair._2();
            if (stream == null)
                return null;
            
            Object[] head     = new Object[] { null };
            val      iterator = stream.iterator();
            if (!iterator.hasNext())
                return null;
            
            head[0]  = iterator.next();
            val tail = restater.apply((DATA)head[0], iterator.stream());
            if (tail == null)
                return null;
            
            return Tuple2.of((DATA)head[0], tail);
        });
        val seed = Tuple2.of((DATA)null, this);
        
        // NOTE: The reason for the using untyped-generic is becuase "DATA" of this class is not seen as compatible with StreamPlus's.
        val endStream 
            = StreamPlus
            .iterate  (seed, (UnaryOperator)func)
            .takeUntil(t -> t == null)
            .skip     (1)
            .map      (t -> ((Tuple2)t)._1());
        return endStream;
    }
    
    /**
     * Map each element to a uncompleted action, run them and collect which ever finish first.
     * The result stream will not be the same order with the original one 
     *   -- as stated, the order will be the order of completion.
     * If the result StreamPlus is closed (which is done everytime a terminal operation is done),
     *   the unfinished actions will be canceled.
     */
    public default <T> StreamPlus<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapToAction) {
        val streamPlus = streamPlus();
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
