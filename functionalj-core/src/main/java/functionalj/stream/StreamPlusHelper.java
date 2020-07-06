package functionalj.stream;

import static functionalj.functions.ObjFuncs.notEqual;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static java.lang.Boolean.TRUE;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
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
    
    static <T> boolean equals(Stream<T> stream1, Stream<T> stream2) {
        return !StreamPlus
                .from       (stream1)
                .combineWith(StreamPlus.from(stream2), AllowUnpaired, notEqual())
                .filter     (TRUE::equals)
                .findAny    ()
                .isPresent  ();
    }
    
    public static <T> int hashCode(Stream<T> stream) {
        return stream
                .mapToInt(e -> (e == null) ? 0 : e.hashCode())
                .reduce(1, (h, eh) -> 31*h + eh);
    }
    
    public static <T> String toString(Stream<T> stream) {
        return "[" + StreamPlus.from(stream).joinToString(", ") + "]";
    }
    
    static <DATA, C, B> StreamPlus<C> doZipWith(
            ZipWithOption      option, 
            Func2<DATA, B, C>  merger,
            IteratorPlus<DATA> iteratorA, 
            IteratorPlus<B>    iteratorB) {
        
        val iterator = new Iterator<C>() {
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
        val iterable = new Iterable<C>() {
            @Override
            public Iterator<C> iterator() {
                return iterator;
            }
          
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    static <DATA> StreamPlus<DATA> doMerge(
            Iterator<DATA> iteratorA, 
            Iterator<DATA> iteratorB) {
        val iterable = new Iterable<DATA>() {
            private final Iterator<DATA> iterator = new Iterator<DATA>() {
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
            @Override
            public Iterator<DATA> iterator() {
                return iterator;
            }
        };
        val spliterator = iterable.spliterator();
        val stream      = StreamSupport.stream(spliterator, false);
        return StreamPlus.from(stream);
    }
    
    static <DATA> IteratorPlus<DATA> rawIterator(Stream<DATA> stream) {
        return IteratorPlus.from(stream);
    }
    
    public static <DATA, TARGET> TARGET terminate(
            AsStreamPlus<DATA>          asStreamPlus,
            Func1<Stream<DATA>, TARGET> action) {
        val streamPlus = asStreamPlus.streamPlus();
        try {
            val stream = streamPlus.stream();
            val result = action.apply(stream);
            return result;
        } finally {
            streamPlus.close();
        }
    }
    
    public static <DATA> void terminate(
            AsStreamPlus<DATA>      asStreamPlus,
            FuncUnit1<Stream<DATA>> action) {
        val streamPlus = asStreamPlus.streamPlus();
        try {
            val stream = streamPlus.stream();
            action.accept(stream);
        } finally {
            streamPlus.close();
        }
    }
    
    /** Run the given action sequentially, make sure to set the parallelity of the result back. */
    public static <D, T> StreamPlus<T> sequential(
            AsStreamPlus<D>      asStreamPlus,
            Func1<StreamPlus<D>, StreamPlus<T>> action) {
        val streamPlus = asStreamPlus.streamPlus();
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
    public static <D, T> StreamPlus<T> sequentialToObj(
            AsStreamPlus<D>                     asStreamPlus,
            Func1<StreamPlus<D>, StreamPlus<T>> action) {
        return sequential(asStreamPlus, action);
    }
    
    public static <D, T> StreamPlus<T> derive(
            AsStreamPlus<D>     asStreamPlus,
            Function<Stream<D>, Stream<T>> action) {
        val streamPlus = asStreamPlus.streamPlus();
        val orgStream  = streamPlus.stream();
        val newStream  = action.apply(orgStream);
        return StreamPlus.from(newStream);
    }
    
    public static <D, T> StreamPlus<T> deriveAsObject(
            AsStreamPlus<D>                asStreamPlus,
            Function<Stream<D>, Stream<T>> action) {
        return derive(asStreamPlus, action);
    }
    
}
