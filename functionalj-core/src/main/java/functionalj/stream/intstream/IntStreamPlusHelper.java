package functionalj.stream.intstream;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.stream.IntIterable;
import functionalj.stream.IntIteratorPlus;
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
            .skip(index)
            .peek(value -> { ref[0] = new int[] { value }; })
            .findFirst()
            .orElse(-1);
        
        val found = ref[0] != null;
        valueRef[0] = ref[0];
        
        return found;
    }
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipIntWith(
            ZipWithOption               option, 
            IntObjBiFunction<B, TARGET> merger,
            IntIteratorPlus             iteratorA, 
            IteratorPlus<B>             iteratorB) {
        
        val iterator = new Iterator<TARGET>() {
            private boolean hasNextA;
            private boolean hasNextB;
            
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth)
                        ? (hasNextA && hasNextB)
                        : hasNextA;
            }
            public TARGET next() {
                if (!hasNextA)
                    throw new NoSuchElementException();
                
                int nextA = iteratorA.nextInt();
                val nextB = hasNextB ? iteratorB.next() : null;
                return merger.apply(nextA, nextB);
            }
        };
        val iterable = new Iterable<TARGET>() {
            @Override
            public Iterator<TARGET> iterator() {
                return iterator;
            }
          
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
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
        val intIterator = IntIteratorPlus.from(iterator);
        val iterable = new IntIterable() {
            @Override
            public IntIteratorPlus iterator() {
                return intIterator;
            }
            
        };
        return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
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
        val intIterator = IteratorPlus.from(iterator);
        val iterable = new Iterable<TARGET>() {
            @Override
            public IteratorPlus<TARGET> iterator() {
                return intIterator;
            }
            
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
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
        val intIterator = IteratorPlus.from(iterator);
        val iterable = new Iterable<TARGET>() {
            @Override
            public IteratorPlus<TARGET> iterator() {
                return intIterator;
            }
            
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
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
        val intIterator = IntIteratorPlus.from(iterator);
        val iterable = new IntIterable() {
            @Override
            public IntIteratorPlus iterator() {
                return intIterator;
            }
            
        };
        return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
    }
    
    static IntStreamPlus doMergeInt(
            IntIteratorPlus iteratorA, 
            IntIteratorPlus iteratorB) {
        val iterable = new IntIterable() {
            private final IntIteratorPlus iterator = new IntIteratorPlus() {
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
            @Override
            public IntIteratorPlus iterator() {
                return iterator;
            }
        };
        val spliterator = iterable.spliterator();
        val stream      = StreamSupport.intStream(spliterator, false);
        return IntStreamPlus.from(stream);
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
        val intIterator = IntIteratorPlus.from(iterator);
        val iterable = new IntIterable() {
            @Override
            public IntIteratorPlus iterator() {
                return intIterator;
            }
            
        };
        return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
    }
    
    static IntIteratorPlus rawIterator(IntStream stream) {
        return IntIteratorPlus.from(IntStreamPlus.from(stream));
    }

}
