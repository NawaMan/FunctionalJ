package functionalj.stream.longstream;

import static functionalj.function.Func.f;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import functionalj.function.LongBiFunctionPrimitive;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import lombok.val;

public class LongStreamPlusHelper {
    
    static final Object dummy = new Object();
    
    public static <T> boolean hasAt(LongStream stream, long index) {
        return hasAt(stream, index, null);
    }
    
    public static <T> boolean hasAt(LongStream stream, long index, long[][] valueRef) {
        // Note: It is done this way to avoid interpreting 'null' as no-value
        
        val ref = new long[1][];
        stream
            .skip     (index)
            .peek     (value -> { ref[0] = new long[] { value }; })
            .findFirst()
            .orElse   (-1);
        
        val found = ref[0] != null;
        valueRef[0] = ref[0];
        
        return found;
    }
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipLongWith(
            LongObjBiFunction<B, TARGET> merger,
            LongIteratorPlus             iteratorA, 
            IteratorPlus<B>              iteratorB) {
        
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
                
                long nextA = iteratorA.nextLong();
                val  nextB = iteratorB.next();
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
    
    static <DATA, B, TARGET> StreamPlus<TARGET> doZipLongWith(
            long                         defaultValue,
            LongObjBiFunction<B, TARGET> merger,
            LongIteratorPlus             iteratorA, 
            IteratorPlus<B>              iteratorB) {
        
        val targetIterator = new Iterator<TARGET>() {
            private boolean hasNextA;
            private boolean hasNextB;
            
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return hasNextA || hasNextB;
            }
            public TARGET next() {
                long nextA = hasNextA ? iteratorA.nextLong() : defaultValue;
                val  nextB = hasNextB ? iteratorB.next()    : null;
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
    
    static LongStreamPlus doZipLongLongWith(
            LongBiFunctionPrimitive merger,
            LongIteratorPlus        iteratorA, 
            LongIteratorPlus        iteratorB) {
        
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
                    long nextA  = iteratorA.nextLong();
                    long nextB  = iteratorB.nextLong();
                    long choice = merger.applyAsLongAndLong(nextA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = LongIteratorPlus.from(iterator);
        val iterable       = (LongIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipLongLongWith(
            LongLongBiFunction<TARGET> merger,
            LongIteratorPlus           iteratorA, 
            LongIteratorPlus           iteratorB) {
        
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
                    long   nextA  = iteratorA.nextLong();
                    long   nextB  = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(nextA, nextB);
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
    
    static <TARGET> StreamPlus<TARGET> doZipLongLongObjWith(
            LongLongBiFunction<TARGET> merger,
            LongIteratorPlus           iteratorA, 
            LongIteratorPlus           iteratorB,
            long                       defaultValue) {
        
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
                    long   nextA  = iteratorA.nextLong();
                    long   nextB  = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    long   nextA = iteratorA.nextLong();
                    TARGET choice = merger.applyLong(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    long   nextB = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(defaultValue, nextB);
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
    
    static LongStreamPlus doZipLongLongWith(
            LongBiFunctionPrimitive merger,
            LongIteratorPlus        iteratorA, 
            LongIteratorPlus        iteratorB,
            long                    defaultValue) {
        
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
                    long nextA  = iteratorA.nextLong();
                    long nextB  = iteratorB.nextLong();
                    long choice = merger.applyAsLongAndLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    long nextA = iteratorA.nextLong();
                    long choice = merger.applyAsLongAndLong(nextA, defaultValue);
                    return choice;
                }
                if (hasNextB) {
                    long nextB = iteratorB.nextLong();
                    long choice = merger.applyAsLongAndLong(defaultValue, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = LongIteratorPlus.from(iterator);
        val iterable       = (LongIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static <TARGET> StreamPlus<TARGET> doZipLongLongObjWith(
            LongLongBiFunction<TARGET> merger,
            LongIteratorPlus           iteratorA, 
            LongIteratorPlus           iteratorB,
            long                       defaultValueA,
            long                       defaultValueB) {
        
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
                    long   nextA  = iteratorA.nextLong();
                    long   nextB  = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    long   nextA = iteratorA.nextLong();
                    TARGET choice = merger.applyLong(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    long   nextB = iteratorB.nextLong();
                    TARGET choice = merger.applyLong(defaultValueA, nextB);
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
    
    static LongStreamPlus doZipLongLongWith(
            LongBiFunctionPrimitive merger,
            LongIteratorPlus        iteratorA, 
            LongIteratorPlus        iteratorB,
            long                    defaultValueA,
            long                    defaultValueB) {
        
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
                    long nextA  = iteratorA.nextLong();
                    long nextB  = iteratorB.nextLong();
                    long choice = merger.applyAsLongAndLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    long nextA = iteratorA.nextLong();
                    long choice = merger.applyAsLongAndLong(nextA, defaultValueB);
                    return choice;
                }
                if (hasNextB) {
                    long nextB = iteratorB.nextLong();
                    long choice = merger.applyAsLongAndLong(defaultValueA, nextB);
                    return choice;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = LongIteratorPlus.from(iterator);
        val iterable       = (LongIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static LongStreamPlus doMergeLong(
            LongIteratorPlus iteratorA, 
            LongIteratorPlus iteratorB) {
        val iterator = new LongIteratorPlus() {
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
        val iterable     = (LongIterable)() -> iterator;
        val spliterator  = iterable.spliterator();
        val targetStream = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iterator::close).runCarelessly();
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static LongStreamPlus doChoiceWith(
            ZipWithOption           option, 
            LongBiFunctionPrimitive merger,
            LongIteratorPlus        iteratorA, 
            LongIteratorPlus        iteratorB) {
        
        val iterator = new PrimitiveIterator.OfLong() {
            private boolean hasNextA;
            private boolean hasNextB;
            
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth)
                        ? (hasNextA && hasNextB)
                        : (hasNextA || hasNextB);
            }
            public long nextLong() {
                if (hasNextA && hasNextB) {
                    long nextA  = iteratorA.nextLong();
                    long nextB  = iteratorB.nextLong();
                    long choice = merger.applyAsLongAndLong(nextA, nextB);
                    return choice;
                }
                if (hasNextA) {
                    long nextA = iteratorA.nextLong();
                    return nextA;
                }
                if (hasNextB) {
                    long nextB = iteratorB.nextLong();
                    return nextB;
                }
                throw new NoSuchElementException();
            }
        };
        
        val targetIterator = LongIteratorPlus.from(iterator);
        val iterable       = (LongIterable)() -> targetIterator;
        val spliterator    = iterable.spliterator();
        val targetStream   = LongStreamPlus.from(StreamSupport.longStream(spliterator, false));
        targetStream.onClose(() -> {
            f(iteratorA::close).runCarelessly();
            f(iteratorB::close).runCarelessly();
        });
        return targetStream;
    }
    
    static LongIteratorPlus rawIterator(LongStream stream) {
        return LongIteratorPlus.from(LongStreamPlus.from(stream));
    }

}
