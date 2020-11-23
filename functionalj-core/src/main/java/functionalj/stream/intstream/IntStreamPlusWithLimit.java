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

import static functionalj.stream.intstream.IntStreamPlusHelper.sequential;

import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.StreamSupport;

import functionalj.stream.markers.Sequential;
import lombok.val;


public interface IntStreamPlusWithLimit {
    
    public IntStreamPlus intStreamPlus();
    
    /** Limit the size of the stream to the given size. */
    public default IntStreamPlus limit(Long maxSize) {
        val streamPlus = intStreamPlus();
        return ((maxSize == null) || (maxSize.longValue() < 0))
                ? streamPlus
                : IntStreamPlus.from(
                        streamPlus
                        .limit((long)maxSize));
    }
    
    /** Skip to the given offset position. */
    public default IntStreamPlus skip(Long offset) {
        val streamPlus = intStreamPlus();
        return ((offset == null) || (offset.longValue() < 0))
                ? streamPlus
                : IntStreamPlus.from(
                        streamPlus
                        .skip((long)offset));
    }
    
    /** Skip any value while the condition is true. */
    @Sequential
    public default IntStreamPlus skipWhile(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                
                if (!condition.test(e))
                    isStillTrue.set(false);
                
                return !isStillTrue.get();
            });
        });
    }
    
    /** Skip any value until the condition is true. */
    @Sequential
    public default IntStreamPlus skipUntil(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                
                if (condition.test(e))
                    isStillTrue.set(false);
                
                return !isStillTrue.get();
            });
        });
    }
    
    /** Accept any value while the condition is true. */
    @Sequential
    public default IntStreamPlus takeWhile(IntPredicate condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            return IntStreamPlus.from(
                    StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        @Override
                        public boolean tryAdvance(IntConsumer consumer) {
                            if (stillGoing) {
                                IntConsumer action = elem -> {
                                    if (condition.test(elem)) {
                                        consumer.accept(elem);
                                    } else {
                                        stillGoing = false;
                                    }
                                };
                                boolean hadNext = splitr.tryAdvance(action);
                                return hadNext && stillGoing;
                            }
                            return false;
                        }
                    }, false)
                );
        });
    }
    
    /** Accept any value until the condition is true. */
    @Sequential
    public default IntStreamPlus takeUntil(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        IntConsumer action = elem -> {
                            if (!condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        };
                        boolean hadNext = splitr.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            }, false);
            return IntStreamPlus.from(resultStream);
        });
    }
    
}
