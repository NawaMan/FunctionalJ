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
package functionalj.stream.intstream;

import static functionalj.function.Func.f;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;

public class IntStreamPlusHelper {
    
    static final Object dummy = new Object();
    
    public static <T> boolean hasAt(IntStream stream, long index) {
        return hasAt(stream, index, null);
    }
    
    public static <T> boolean hasAt(IntStream stream, long index, int[][] valueRef) {
        // Note: It is done this way to avoid interpreting 'null' as no-value
        val ref = new int[1][];
        stream.skip(index).peek(value -> {
            ref[0] = new int[] { value };
        }).findFirst().orElse(-1);
        val found = ref[0] != null;
        valueRef[0] = ref[0];
        return found;
    }
    
    // -- Terminal --
    static <TARGET> TARGET terminate(AsIntStreamPlus asStreamPlus, Function<IntStream, TARGET> action) {
        val streamPlus = asStreamPlus.intStreamPlus();
        try {
            val stream = streamPlus.intStream();
            val result = action.apply(stream);
            return result;
        } finally {
            streamPlus.close();
        }
    }
    
    static void terminate(AsIntStreamPlus asStreamPlus, Consumer<IntStream> action) {
        val streamPlus = asStreamPlus.intStreamPlus();
        try {
            val stream = streamPlus.intStream();
            action.accept(stream);
        } finally {
            streamPlus.close();
        }
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    static <T> IntStreamPlus sequential(AsIntStreamPlus asStreamPlus, Function<IntStreamPlus, IntStreamPlus> action) {
        val streamPlus = asStreamPlus.intStreamPlus();
        val isParallel = streamPlus.isParallel();
        val orgIntStreamPlus = streamPlus.sequential();
        val newIntStreamPlus = action.apply(orgIntStreamPlus);
        if (newIntStreamPlus.isParallel() == isParallel)
            return newIntStreamPlus;
        if (isParallel)
            return newIntStreamPlus.parallel();
        return newIntStreamPlus.sequential();
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    static <T> IntStreamPlus sequentialToInt(AsIntStreamPlus asStreamPlus, Function<IntStreamPlus, IntStreamPlus> action) {
        return sequential(asStreamPlus, action);
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    static <T> StreamPlus<T> sequentialToObj(AsIntStreamPlus asStreamPlus, Function<IntStreamPlus, StreamPlus<T>> action) {
        val streamPlus = asStreamPlus.intStreamPlus();
        val isParallel = streamPlus.isParallel();
        val orgIntStreamPlus = streamPlus.sequential();
        val newIntStreamPlus = action.apply(orgIntStreamPlus);
        if (newIntStreamPlus.isParallel() == isParallel)
            return newIntStreamPlus;
        if (isParallel)
            return newIntStreamPlus.parallel();
        return newIntStreamPlus.sequential();
    }
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipIntWith(IntObjBiFunction<B, TARGET> merger, IntIteratorPlus iteratorA, IteratorPlus<B> iteratorB) {
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
                val nextA = iteratorA.nextInt();
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
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipIntWith(int defaultValue, IntObjBiFunction<B, TARGET> merger, IntIteratorPlus iteratorA, IteratorPlus<B> iteratorB) {
        val targetIterator = new Iterator<TARGET>() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return hasNextA || hasNextB;
            }
        
            public TARGET next() {
                val nextA = hasNextA ? iteratorA.nextInt() : defaultValue;
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
    
    static IntStreamPlus doZipIntIntWith(IntBinaryOperator merger, IntIteratorPlus iteratorA, IntIteratorPlus iteratorB) {
        val iterator = new PrimitiveIterator.OfInt() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA && hasNextB);
            }
        
            public int nextInt() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextInt();
                    val nextB = iteratorB.nextInt();
                    val choice = merger.applyAsInt(nextA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = IntIteratorPlus.from(iterator);
        val iterable = (IntIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipIntIntObjWith(IntIntBiFunction<TARGET> merger, IntIteratorPlus iteratorA, IntIteratorPlus iteratorB) {
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
                    val nextA = iteratorA.nextInt();
                    val nextB = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(nextA, nextB);
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
    
    static <TARGET> StreamPlus<TARGET> doZipIntIntObjWith(IntIntBiFunction<TARGET> merger, IntIteratorPlus iteratorA, IntIteratorPlus iteratorB, int defaultValue) {
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
                    val nextA = iteratorA.nextInt();
                    val nextB = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextInt();
                    TARGET choice = merger.applyInt(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(defaultValue, nextB);
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
    
    static IntStreamPlus doZipIntIntWith(IntBinaryOperator merger, IntIteratorPlus iteratorA, IntIteratorPlus iteratorB, int defaultValue) {
        val iterator = new PrimitiveIterator.OfInt() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public int nextInt() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextInt();
                    val nextB = iteratorB.nextInt();
                    val choice = merger.applyAsInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextInt();
                    val choice = merger.applyAsInt(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextInt();
                    val choice = merger.applyAsInt(defaultValue, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = IntIteratorPlus.from(iterator);
        val iterable = (IntIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipIntIntObjWith(IntIntBiFunction<TARGET> merger, IntIteratorPlus iteratorA, IntIteratorPlus iteratorB, int defaultValueA, int defaultValueB) {
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
                    val nextA = iteratorA.nextInt();
                    val nextB = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextInt();
                    TARGET choice = merger.applyInt(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(defaultValueA, nextB);
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
    
    static IntStreamPlus doZipIntIntWith(IntBinaryOperator merger, IntIteratorPlus iteratorA, IntIteratorPlus iteratorB, int defaultValueA, int defaultValueB) {
        val iterator = new PrimitiveIterator.OfInt() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public int nextInt() {
                if (hasNextA && hasNextB) {
                    val nextA = iteratorA.nextInt();
                    val nextB = iteratorB.nextInt();
                    val choice = merger.applyAsInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    val nextA = iteratorA.nextInt();
                    val choice = merger.applyAsInt(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    val nextB = iteratorB.nextInt();
                    val choice = merger.applyAsInt(defaultValueA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = IntIteratorPlus.from(iterator);
        val iterable = (IntIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static IntStreamPlus doMergeInt(IntIteratorPlus iteratorA, IntIteratorPlus iteratorB) {
        val iterator = new IntIteratorPlus() {
        
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
        
            public int nextInt() {
                val next = isA ? iteratorA.next() : iteratorB.next();
                isA = !isA;
                return next;
            }
        
            @Override
            public OfInt asIterator() {
                return this;
            }
        };
        val iterable = (IntIterable) () -> iterator;
        val spliterator = iterable.spliterator();
        val targetStream = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iterator::close).runCarelessly();
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
}
