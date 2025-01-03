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
package functionalj.stream.doublestream;

import static functionalj.function.Func.f;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;
import functionalj.function.DoubleDoubleFunction;
import functionalj.function.DoubleObjBiFunction;
import functionalj.function.Func1;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import lombok.val;

public class DoubleStreamPlusHelper {
    
    static final Object dummy = new Object();
    
    public static <T> boolean hasAt(DoubleStream stream, long index) {
        return hasAt(stream, index, null);
    }
    
    public static <T> boolean hasAt(DoubleStream stream, long index, double[][] valueRef) {
        // Note: It is done this way to avoid interpreting 'null' as no-value
        val ref = new double[1][];
        stream.skip(index).peek(value -> {
            ref[0] = new double[] { value };
        }).findFirst().orElse(-1);
        val found = ref[0] != null;
        valueRef[0] = ref[0];
        return found;
    }
    
    // -- Terminal --
    static <TARGET> TARGET terminate(AsDoubleStreamPlus asStreamPlus, Function<DoubleStream, TARGET> action) {
        val streamPlus = asStreamPlus.doubleStreamPlus();
        try {
            val stream = streamPlus.doubleStream();
            val result = action.apply(stream);
            return result;
        } finally {
            streamPlus.close();
        }
    }
    
    static void terminate(AsDoubleStreamPlus asStreamPlus, Consumer<DoubleStream> action) {
        val streamPlus = asStreamPlus.doubleStreamPlus();
        try {
            val stream = streamPlus.doubleStream();
            action.accept(stream);
        } finally {
            streamPlus.close();
        }
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    public static <T> DoubleStreamPlus sequential(AsDoubleStreamPlus asStreamPlus, Func1<DoubleStreamPlus, DoubleStreamPlus> action) {
        val streamPlus = asStreamPlus.doubleStreamPlus();
        val isParallel = streamPlus.isParallel();
        val orgDoubleStreamPlus = streamPlus.sequential();
        val newDoubleStreamPlus = action.apply(orgDoubleStreamPlus);
        if (newDoubleStreamPlus.isParallel() == isParallel)
            return newDoubleStreamPlus;
        if (isParallel)
            return newDoubleStreamPlus.parallel();
        return newDoubleStreamPlus.sequential();
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    public static <T> DoubleStreamPlus sequentialToDouble(AsDoubleStreamPlus asStreamPlus, Func1<DoubleStreamPlus, DoubleStreamPlus> action) {
        return sequential(asStreamPlus, action);
    }
    
    /**
     * Run the given action sequentially, make sure to set the parallelity of the result back.
     */
    public static <T> StreamPlus<T> sequentialToObj(AsDoubleStreamPlus asStreamPlus, Func1<DoubleStreamPlus, StreamPlus<T>> action) {
        val streamPlus = asStreamPlus.doubleStreamPlus();
        val isParallel = streamPlus.isParallel();
        val orgDoubleStreamPlus = streamPlus.sequential();
        val newDoubleStreamPlus = action.apply(orgDoubleStreamPlus);
        if (newDoubleStreamPlus.isParallel() == isParallel)
            return newDoubleStreamPlus;
        if (isParallel)
            return newDoubleStreamPlus.parallel();
        return newDoubleStreamPlus.sequential();
    }
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipDoubleWith(DoubleObjBiFunction<B, TARGET> merger, DoubleIteratorPlus iteratorA, IteratorPlus<B> iteratorB) {
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
                double nextA = iteratorA.nextDouble();
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
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipDoubleWith(double defaultValue, DoubleObjBiFunction<B, TARGET> merger, DoubleIteratorPlus iteratorA, IteratorPlus<B> iteratorB) {
        val targetIterator = new Iterator<TARGET>() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return hasNextA || hasNextB;
            }
        
            public TARGET next() {
                double nextA = hasNextA ? iteratorA.nextDouble() : defaultValue;
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
    
    static DoubleStreamPlus doZipDoubleDoubleWith(DoubleBinaryOperator merger, DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB) {
        val iterator = new PrimitiveIterator.OfDouble() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA && hasNextB);
            }
        
            public double nextDouble() {
                if (hasNextA && hasNextB) {
                    double nextA = iteratorA.nextDouble();
                    double nextB = iteratorB.nextDouble();
                    double choice = merger.applyAsDouble(nextA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = DoubleIteratorPlus.from(iterator);
        val iterable = (DoubleIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipDoubleDoubleObjWith(DoubleDoubleFunction<TARGET> merger, DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB) {
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
                    double nextA = iteratorA.nextDouble();
                    double nextB = iteratorB.nextDouble();
                    TARGET choice = merger.apply(nextA, nextB);
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
    
    static <TARGET> StreamPlus<TARGET> doZipDoubleDoubleObjWith(DoubleDoubleFunction<TARGET> merger, DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB, double defaultValue) {
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
                    double nextA = iteratorA.nextDouble();
                    double nextB = iteratorB.nextDouble();
                    TARGET choice = merger.apply(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    double nextA = iteratorA.nextDouble();
                    TARGET choice = merger.apply(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    double nextB = iteratorB.nextDouble();
                    TARGET choice = merger.apply(defaultValue, nextB);
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
    
    static DoubleStreamPlus doZipDoubleDoubleWith(DoubleBinaryOperator merger, DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB, double defaultValue) {
        val iterator = new PrimitiveIterator.OfDouble() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public double nextDouble() {
                if (hasNextA && hasNextB) {
                    double nextA = iteratorA.nextDouble();
                    double nextB = iteratorB.nextDouble();
                    double choice = merger.applyAsDouble(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    double nextA = iteratorA.nextDouble();
                    double choice = merger.applyAsDouble(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    double nextB = iteratorB.nextDouble();
                    double choice = merger.applyAsDouble(defaultValue, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = DoubleIteratorPlus.from(iterator);
        val iterable = (DoubleIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipDoubleDoubleObjWith(DoubleDoubleFunction<TARGET> merger, DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB, double defaultValueA, double defaultValueB) {
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
                    double nextA = iteratorA.nextDouble();
                    double nextB = iteratorB.nextDouble();
                    TARGET choice = merger.apply(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    double nextA = iteratorA.nextDouble();
                    TARGET choice = merger.apply(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    double nextB = iteratorB.nextDouble();
                    TARGET choice = merger.apply(defaultValueA, nextB);
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
    
    static DoubleStreamPlus doZipDoubleDoubleWith(DoubleBinaryOperator merger, DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB, double defaultValueA, double defaultValueB) {
        val iterator = new PrimitiveIterator.OfDouble() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (hasNextA || hasNextB);
            }
        
            public double nextDouble() {
                if (hasNextA && hasNextB) {
                    double nextA = iteratorA.nextDouble();
                    double nextB = iteratorB.nextDouble();
                    double choice = merger.applyAsDouble(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    double nextA = iteratorA.nextDouble();
                    double choice = merger.applyAsDouble(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    double nextB = iteratorB.nextDouble();
                    double choice = merger.applyAsDouble(defaultValueA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = DoubleIteratorPlus.from(iterator);
        val iterable = (DoubleIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static DoubleStreamPlus doMergeInt(DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB) {
        val iterator = new DoubleIteratorPlus() {
        
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
        
            public double nextDouble() {
                val next = isA ? iteratorA.next() : iteratorB.next();
                isA = !isA;
                return next;
            }
        
            @Override
            public OfDouble asIterator() {
                return this;
            }
        };
        val iterable = (DoubleIterable) () -> iterator;
        val spliterator = iterable.spliterator();
        val targetStream = DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iterator::close).runCarelessly();
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static DoubleStreamPlus doChoiceWith(ZipWithOption option, DoubleBinaryOperator merger, DoubleIteratorPlus iteratorA, DoubleIteratorPlus iteratorB) {
        val iterator = new PrimitiveIterator.OfDouble() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth) ? (hasNextA && hasNextB) : (hasNextA || hasNextB);
            }
        
            public double nextDouble() {
                if (hasNextA && hasNextB) {
                    double nextA = iteratorA.nextDouble();
                    double nextB = iteratorB.nextDouble();
                    double choice = merger.applyAsDouble(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    double nextA = iteratorA.nextDouble();
                    return nextA;
                }
                if (hasNextB) {
                    double nextB = iteratorB.nextDouble();
                    return nextB;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = DoubleIteratorPlus.from(iterator);
        val iterable = (DoubleIterable) () -> targetIterator;
        val spliterator = iterable.spliterator();
        val targetStream = DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
}
