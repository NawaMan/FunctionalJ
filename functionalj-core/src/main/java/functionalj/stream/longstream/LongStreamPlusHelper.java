// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.longstream;

import static functionalj.function.Func.f;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;

public class LongStreamPlusHelper {
    
    static final Object dummy = new Object();
    
    public static <T> boolean hasAt(LongStream stream, long index) {
        return hasAt(stream, index, null);
    }
    
    public static <T> boolean hasAt(LongStream stream, long index, long[][] valueRef) {
        // Note: It is done this way to avoid interpreting 'null' as no-value
        val ref = new long[1][];
        stream.skip(index).peek(value -> {
            ref[0] = new long[] { value };
        }).findFirst().orElse(-1);
        val found = ref[0] != null;
        valueRef[0] = ref[0];
        return found;
    }
    
    // -- Terminal --
    static <TARGET> TARGET terminate(AsLongStreamPlus asStreamPlus, Function<LongStream, TARGET> action) {
        val streamPlus = asStreamPlus.longStreamPlus();
        try {
            val stream = streamPlus.longStream();
            val result = action.apply(stream);
            return result;
        } finally {
            streamPlus.close();
        }
    }
    
    static void terminate(AsLongStreamPlus asStreamPlus, Consumer<LongStream> action) {
        val streamPlus = asStreamPlus.longStreamPlus();
        try {
            val stream = streamPlus.longStream();
            action.accept(stream);
        } finally {
            streamPlus.close();
        }
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    static <T> LongStreamPlus sequential(AsLongStreamPlus asStreamPlus, Function<LongStreamPlus, LongStreamPlus> action) {
        val streamPlus = asStreamPlus.longStreamPlus();
        val isParallel = streamPlus.isParallel();
        val orgLongStreamPlus = streamPlus.sequential();
        val newLongStreamPlus = action.apply(orgLongStreamPlus);
        if (newLongStreamPlus.isParallel() == isParallel)
            return newLongStreamPlus;
        if (isParallel)
            return newLongStreamPlus.parallel();
        return newLongStreamPlus.sequential();
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    static <T> LongStreamPlus sequentialToInt(AsLongStreamPlus asStreamPlus, Function<LongStreamPlus, LongStreamPlus> action) {
        return sequential(asStreamPlus, action);
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    static <T> StreamPlus<T> sequentialToObj(AsLongStreamPlus asStreamPlus, Function<LongStreamPlus, StreamPlus<T>> action) {
        val streamPlus = asStreamPlus.longStreamPlus();
        val isParallel = streamPlus.isParallel();
        val orgLongStreamPlus = streamPlus.sequential();
        val newLongStreamPlus = action.apply(orgLongStreamPlus);
        if (newLongStreamPlus.isParallel() == isParallel)
            return newLongStreamPlus;
        if (isParallel)
            return newLongStreamPlus.parallel();
        return newLongStreamPlus.sequential();
    }
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipLongWith(LongObjBiFunction<B, TARGET> merger, LongIteratorPlus iteratorA, IteratorPlus<B> iteratorB) {
        val targetIterator = new Iterator<TARGET>() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA && hasNextB);
            }
        
            public TARGET next() {
                if (!hasNextA)
                    throw new NoSuchElementException();
                val nextA = iteratorA.nextLong();
                val nextB = iteratorB.next();
                return merger.apply(nextA, nextB);
            }
        };
        val iterable = (Iterable<TARGET>) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipLongWith(long defaultValue, LongObjBiFunction<B, TARGET> merger, LongIteratorPlus iteratorA, IteratorPlus<B> iteratorB) {
        val targetIterator = new Iterator<TARGET>() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return hasNextA || hasNextB;
            }
        
            public TARGET next() {
                val nextA = hasNextA ? iteratorA.nextLong() : defaultValue;
                B nextB = hasNextB ? iteratorB.next() : null;
                return merger.apply(nextA, nextB);
            }
        };
        val iterable = (Iterable<TARGET>) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static LongStreamPlus doZipLongLongWith(LongBinaryOperator merger, LongIteratorPlus iteratorA, LongIteratorPlus iteratorB) {
        val iterator = new PrimitiveIterator.OfLong() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA && hasNextB);
            }
        
            public long nextLong() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextLong();
                    val nextB = iteratorB.nextLong();
                    val choice = merger.applyAsLong(nextA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = LongIteratorPlus.from(iterator);
        val iterable = (LongIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipLongLongObjWith(LongLongBiFunction<TARGET> merger, LongIteratorPlus iteratorA, LongIteratorPlus iteratorB) {
        val iterator = new Iterator<TARGET>() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA && hasNextB);
            }
        
            public TARGET next() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextLong();
                    val nextB = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(nextA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = IteratorPlus.from(iterator);
        val iterable = (Iterable<TARGET>) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipLongLongObjWith(LongLongBiFunction<TARGET> merger, LongIteratorPlus iteratorA, LongIteratorPlus iteratorB, long defaultValue) {
        val iterator = new Iterator<TARGET>() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public TARGET next() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextLong();
                    val nextB = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextLong();
                    TARGET choice = merger.applyLong(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(defaultValue, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = IteratorPlus.from(iterator);
        val iterable = (Iterable<TARGET>) () -> targetIterator;
        val targetStream = StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static LongStreamPlus doZipLongLongWith(LongBinaryOperator merger, LongIteratorPlus iteratorA, LongIteratorPlus iteratorB, long defaultValue) {
        val iterator = new PrimitiveIterator.OfLong() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public long nextLong() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextLong();
                    val nextB = iteratorB.nextLong();
                    val choice = merger.applyAsLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextLong();
                    val choice = merger.applyAsLong(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextLong();
                    val choice = merger.applyAsLong(defaultValue, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = LongIteratorPlus.from(iterator);
        val iterable = (LongIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipLongLongObjWith(LongLongBiFunction<TARGET> merger, LongIteratorPlus iteratorA, LongIteratorPlus iteratorB, long defaultValueA, long defaultValueB) {
        val iterator = new Iterator<TARGET>() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public TARGET next() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextLong();
                    val nextB = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextLong();
                    TARGET choice = merger.applyLong(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(defaultValueA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = IteratorPlus.from(iterator);
        val iterable = (Iterable<TARGET>) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static LongStreamPlus doZipLongLongWith(LongBinaryOperator merger, LongIteratorPlus iteratorA, LongIteratorPlus iteratorB, long defaultValueA, long defaultValueB) {
        val iterator = new PrimitiveIterator.OfLong() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public long nextLong() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextLong();
                    val nextB = iteratorB.nextLong();
                    val choice = merger.applyAsLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextLong();
                    val choice = merger.applyAsLong(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextLong();
                    val choice = merger.applyAsLong(defaultValueA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = LongIteratorPlus.from(iterator);
        val iterable = (LongIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static LongStreamPlus doMergeLong(LongIteratorPlus iteratorA, LongIteratorPlus iteratorB) {
        val iterator = new LongIteratorPlus() {
        
            private boolean isA = true;
        
            public boolean hasNext() {
                if (isA) {
                    if (iteratorA.hasNext())
                        return true;
                    isA = false;
                    if (iteratorB.hasNext())
                        return true;
                    return false;
                }
                if (iteratorB.hasNext())
                    return true;
                isA = true;
                if (iteratorA.hasNext())
                    return true;
                return false;
            }
        
            public long nextLong() {
                val next = isA ? iteratorA.next() : iteratorB.next();
                isA = !isA;
                return next;
            }
        
            @Override
            public OfLong asIterator() {
                return this;
            }
        };
        val iterable = (LongIterable) () -> iterator;
        val spliterator = iterable.spliterator();
        val targetStream = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iterator::close).runCarelessly();
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
}
