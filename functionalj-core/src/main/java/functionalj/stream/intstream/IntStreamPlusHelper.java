package functionalj.stream.intstream;

import static functionalj.function.Func.f;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import lombok.val;


public class IntStreamPlusHelper {
    
    static final Object dummy = new Object();
    
    public static <T> boolean hasAt(IntStream stream, long index) {
        return hasAt(stream, index, null);
    }
    
    public static <T> boolean hasAt(IntStream stream, long index, int[][] valueRef) {
        // Note: It is done this way to avoid interpreting 'null' as no-value
        
        val ref = new int[1][];
        stream
            .skip     (index)
            .peek     (value -> { ref[0] = new int[] { value }; })
            .findFirst()
            .orElse   (-1);
        
        val found = ref[0] != null;
        valueRef[0] = ref[0];
        
        return found;
    }
    
    /** Run the given action sequentially, make sure to set the parallelity of the result back. */
    public static <T> IntStreamPlus sequential(
            AsIntStreamPlus      asStreamPlus,
            Func1<IntStreamPlus, IntStreamPlus> action) {
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
    
    /** Run the given action sequentially, make sure to set the parallelity of the result back. */
    public static <T> IntStreamPlus sequentialToInt(
            AsIntStreamPlus                     asStreamPlus,
            Func1<IntStreamPlus, IntStreamPlus> action) {
        return sequential(asStreamPlus, action);
    }
    
    /** Run the given action sequentially, make sure to set the parallelity of the result back. */
    public static <T> StreamPlus<T> sequentialToObj(
            AsIntStreamPlus                     asStreamPlus,
            Func1<IntStreamPlus, StreamPlus<T>> action) {
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
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipIntWith(
            IntObjBiFunction<B, TARGET> merger,
            IntIteratorPlus             iteratorA,
            IteratorPlus<B>             iteratorB) {
        
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
                
                int nextA = iteratorA.nextInt();
                val nextB = iteratorB.next();
                return merger.apply(nextA, nextB);
            }
        };
        
        val iterable     = (Iterable<TARGET>)() -> targetIterator;
        val spliterator  = iterable.spliterator();
        val targetStream = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipIntWith(
            int                         defaultValue,
            IntObjBiFunction<B, TARGET> merger,
            IntIteratorPlus             iteratorA,
            IteratorPlus<B>             iteratorB) {
        
        val targetIterator = new Iterator<TARGET>() {
            private boolean hasNextA;
            private boolean hasNextB;
            
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return hasNextA || hasNextB;
            }
            public TARGET next() {
                int nextA = hasNextA ? iteratorA.nextInt() : defaultValue;
                val nextB = hasNextB ? iteratorB.next()    : null;
                return merger.apply(nextA, nextB);
            }
        };
        val iterable     = (Iterable<TARGET>)() -> targetIterator;
        val spliterator  = iterable.spliterator();
        val targetStream = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static IntStreamPlus doZipIntIntWith(
            IntBiFunctionPrimitive merger,
            IntIteratorPlus        iteratorA,
            IntIteratorPlus        iteratorB) {
        
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
                    int nextA  = iteratorA.nextInt();
                    int nextB  = iteratorB.nextInt();
                    int choice = merger.applyAsIntAndInt(nextA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = IntIteratorPlus.from(iterator);
        val iterable       = (IntIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipIntIntObjWith(
            IntIntBiFunction<TARGET> merger,
            IntIteratorPlus          iteratorA,
            IntIteratorPlus          iteratorB) {
        
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
                    int    nextA  = iteratorA.nextInt();
                    int    nextB  = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(nextA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = IteratorPlus.from(iterator);
        val iterable       = (Iterable<TARGET>)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipIntIntObjWith(
            IntIntBiFunction<TARGET> merger,
            IntIteratorPlus          iteratorA,
            IntIteratorPlus          iteratorB,
            int                      defaultValue) {
        
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
                    int    nextA  = iteratorA.nextInt();
                    int    nextB  = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    int    nextA = iteratorA.nextInt();
                    TARGET choice = merger.applyInt(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    int    nextB = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(defaultValue, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        val targetIterator = IteratorPlus.from(iterator);
        val iterable       = (Iterable<TARGET>)() -> targetIterator;
        val targetStream   = StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static IntStreamPlus doZipIntIntWith(
            IntBiFunctionPrimitive merger,
            IntIteratorPlus        iteratorA,
            IntIteratorPlus        iteratorB,
            int                    defaultValue) {
        
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
                    int nextA  = iteratorA.nextInt();
                    int nextB  = iteratorB.nextInt();
                    int choice = merger.applyAsIntAndInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    int nextA = iteratorA.nextInt();
                    int choice = merger.applyAsIntAndInt(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    int nextB = iteratorB.nextInt();
                    int choice = merger.applyAsIntAndInt(defaultValue, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = IntIteratorPlus.from(iterator);
        val iterable       = (IntIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipIntIntObjWith(
            IntIntBiFunction<TARGET> merger,
            IntIteratorPlus          iteratorA,
            IntIteratorPlus          iteratorB,
            int                      defaultValueA,
            int                      defaultValueB) {
        
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
                    int    nextA  = iteratorA.nextInt();
                    int    nextB  = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    int    nextA = iteratorA.nextInt();
                    TARGET choice = merger.applyInt(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    int    nextB = iteratorB.nextInt();
                    TARGET choice = merger.applyInt(defaultValueA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = IteratorPlus.from(iterator);
        val iterable       = (Iterable<TARGET>)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = StreamPlus.from(StreamSupport.stream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static IntStreamPlus doZipIntIntWith(
            IntBiFunctionPrimitive merger,
            IntIteratorPlus        iteratorA,
            IntIteratorPlus        iteratorB,
            int                    defaultValueA,
            int                    defaultValueB) {
        
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
                    int nextA  = iteratorA.nextInt();
                    int nextB  = iteratorB.nextInt();
                    int choice = merger.applyAsIntAndInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    int nextA = iteratorA.nextInt();
                    int choice = merger.applyAsIntAndInt(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    int nextB = iteratorB.nextInt();
                    int choice = merger.applyAsIntAndInt(defaultValueA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = IntIteratorPlus.from(iterator);
        val iterable       = (IntIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static IntStreamPlus doMergeInt(
            IntIteratorPlus iteratorA,
            IntIteratorPlus iteratorB) {
        @SuppressWarnings("resource")
        val iterator = new IntIteratorPlus() {
            private boolean isA = true;
            
            public boolean hasNext() {
                if (isA) {
                    if (iteratorA.hasNext()) return true;
                    isA = false;
                    if (iteratorB.hasNext()) return true;
                    return false;
                }
                
                if (iteratorB.hasNext()) return true;
                isA = true;
                if (iteratorA.hasNext()) return true;
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
        val iterable     = (IntIterable)() -> iterator;
        val spliterator  = iterable.spliterator();
        val targetStream = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iterator::close).runCarelessly();
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static IntStreamPlus doChoiceWith(
            ZipWithOption          option,
            IntBiFunctionPrimitive merger,
            IntIteratorPlus        iteratorA,
            IntIteratorPlus        iteratorB) {
        
        val iterator = new PrimitiveIterator.OfInt() {
            private boolean hasNextA;
            private boolean hasNextB;
            
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth)
                        ? (hasNextA && hasNextB)
                        : (hasNextA || hasNextB);
            }
            public int nextInt() {
                if (hasNextA && hasNextB) {
                    int nextA  = iteratorA.nextInt();
                    int nextB  = iteratorB.nextInt();
                    int choice = merger.applyAsIntAndInt(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    int nextA = iteratorA.nextInt();
                    return nextA;
                }
                if (hasNextB) {
                    int nextB = iteratorB.nextInt();
                    return nextB;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = IntIteratorPlus.from(iterator);
        val iterable       = (IntIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
}
