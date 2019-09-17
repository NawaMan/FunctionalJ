package functionalj.stream;

import static functionalj.functions.ObjFuncs.notEqual;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static java.lang.Boolean.TRUE;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func2;
import lombok.val;

public class StreamPlusHelper {
    
    static final Object dummy = new Object();
    
    public static <T> boolean hasAt(Stream<T> stream, long index) {
        return hasAt(stream, index, null);
    }
    
    public static <T> boolean hasAt(Stream<T> stream, long index, AtomicReference<T> StreamPlusValue) {
        // Note: It is done this way to avoid interpreting 'null' as no-value
        
        val ref = new AtomicReference<Object>(dummy);
        stream
            .skip(index)
            .peek(value -> ref.set(value))
            .findFirst()
            .orElse(null);
        
        @SuppressWarnings("unchecked")
        val value = (T)ref.get();
        val found = (dummy != value);
        
        if (StreamPlusValue != null) {
            StreamPlusValue.set(found ? value : null);
        }
        
        return found;
    }
    
    public static <T> boolean equals(Stream<T> stream1, Stream<T> stream2) {
        return !StreamPlus
                .from   (stream1)
                .combine(StreamPlus.from(stream2), AllowUnpaired, notEqual())
                .filter (TRUE::equals)
                .findAny()
                .isPresent();
    }
    
    public static <T> int hashCode(Stream<T> stream) {
        return stream
                .mapToInt(e -> (e == null) ? 0 : e.hashCode())
                .reduce(1, (h, eh) -> 31*h + eh);
    }
    
    public static <T> String toString(Stream<T> stream) {
        return "[" + StreamPlus.from(stream).joinToString(", ") + "]";
    }
    
    public static <DATA, C, B> StreamPlus<C> doZipWith(
            ZipWithOption      option, 
            Func2<DATA, B, C>  merger,
            IteratorPlus<DATA> iteratorA, 
            IteratorPlus<B>    iteratorB) {
        val iterable = new Iterable<C>() {
            @Override
            public Iterator<C> iterator() {
                return new Iterator<C>() {
                    private boolean hasNextA;
                    private boolean hasNextB;
                    
                    public boolean hasNext() {
                        hasNextA = iteratorA.hasNext();
                        hasNextB = iteratorB.hasNext();
                        return (option == ZipWithOption.RequireBoth)
                                ? (hasNextA && hasNextB)
                                : (hasNextA || hasNextB);
                    }
                    public C next() {
                        val nextA = hasNextA ? iteratorA.next() : null;
                        val nextB = hasNextB ? iteratorB.next() : null;
                        return merger.apply(nextA, nextB);
                    }
                };
            }
          
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    public static <DATA> StreamPlus<DATA> doMerge(
            IteratorPlus<DATA> iteratorA, 
            IteratorPlus<DATA> iteratorB) {
        val iterable = new Iterable<DATA>() {
            @Override
            public Iterator<DATA> iterator() {
                return new Iterator<DATA>() {
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
                    public DATA next() {
                        val next = isA ? iteratorA.next() : iteratorB.next();
                        isA = !isA;
                        return next;
                    }
                };
            }
            
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
}
