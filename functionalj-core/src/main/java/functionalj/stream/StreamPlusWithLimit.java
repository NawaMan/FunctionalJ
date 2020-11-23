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

import static functionalj.stream.StreamPlusHelper.sequential;

import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import functionalj.stream.makers.Sequential;


public interface StreamPlusWithLimit<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /** Limit the size of the stream to the given size. */
    public default StreamPlus<DATA> limit(Long maxSize) {
        var streamPlus = streamPlus();
        return ((maxSize == null) || (maxSize.longValue() < 0))
                ? streamPlus
                : streamPlus
                    .limit((long)maxSize);
    }
    
    /** Skip to the given offset position. */
    public default StreamPlus<DATA> skip(Long offset) {
        var streamPlus = streamPlus();
        return ((offset == null) || (offset.longValue() < 0))
                ? streamPlus
                : streamPlus
                    .skip((long)offset);
    }
    
    /** Skip any value while the condition is true. */
    @Sequential
    public default StreamPlus<DATA> skipWhile(Predicate<? super DATA> condition) {
        var streamPlus = streamPlus();
        return sequential(streamPlus, stream -> {
            var isStillTrue = new AtomicBoolean(true);
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
    public default StreamPlus<DATA> skipUntil(Predicate<? super DATA> condition) {
        var streamPlus = streamPlus();
        return sequential(streamPlus, stream -> {
            var isStillTrue = new AtomicBoolean(true);
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                
                if (condition.test(e))
                    isStillTrue.set(false);
                
                return !isStillTrue.get();
            });
        });
    }
//
//    /** Accept any value while the condition is true. */
//    @Sequential
//    public default StreamPlus<DATA> takeWhile(Predicate<? super DATA> condition) {
//        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
//        var streamPlus = streamPlus();
//        return sequential(streamPlus, stream -> {
//            var splitr = stream.spliterator();
//            return StreamPlus.from(
//                    StreamSupport.stream(new Spliterators.AbstractSpliterator<DATA>(splitr.estimateSize(), 0) {
//                        boolean stillGoing = true;
//                        @Override
//                        public boolean tryAdvance(Consumer<? super DATA> consumer) {
//                            if (stillGoing) {
//                                Consumer<? super DATA> action = elem -> {
//                                    if (condition.test(elem)) {
//                                        consumer.accept(elem);
//                                    } else {
//                                        stillGoing = false;
//                                    }
//                                };
//                                boolean hadNext = splitr.tryAdvance(action);
//                                return hadNext && stillGoing;
//                            }
//                            return false;
//                        }
//                    }, false)
//                );
//        });
//    }
    
    /** Accept any value until the condition is true. */
    @Sequential
    public default StreamPlus<DATA> takeUntil(Predicate<? super DATA> condition) {
        var streamPlus = streamPlus();
        return sequential(streamPlus, stream -> {
            var splitr = stream.spliterator();
            var resultStream = StreamSupport.stream(new Spliterators.AbstractSpliterator<DATA>(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(Consumer<? super DATA> consumer) {
                    if (stillGoing) {
                        Consumer<? super DATA> action = elem -> {
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
            return StreamPlus.from(resultStream);
        });
    }
    
}
