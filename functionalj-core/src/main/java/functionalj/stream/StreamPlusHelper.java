package functionalj.stream;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;


public class StreamPlusHelper {
    
    static final Object dummy = new Object();
    
    public static <T> boolean hasAt(Stream<T> stream, long index) {
        return hasAt(stream, index, null);
    }
    
    public static <T> boolean hasAt(Stream<T> stream, long index, AtomicReference<T> StreamPlusValue) {
        // Note: It is done this way to avoid interpreting 'null' as no-value
        
        var ref = new AtomicReference<Object>(dummy);
        stream
            .skip(index)
            .peek(value -> ref.set(value))
            .findFirst()
            .orElse(null);
        
        @SuppressWarnings("unchecked")
        var value = (T)ref.get();
        var found = (dummy != value);
        
        if (StreamPlusValue != null) {
            StreamPlusValue.set(found ? value : null);
        }
        
        return found;
    }
    
    static <DATA, C, B> StreamPlus<C> doZipWith(
            ZipWithOption      option, 
            Func2<DATA, B, C>  merger,
            IteratorPlus<DATA> iteratorA, 
            IteratorPlus<B>    iteratorB) {
        
        var iterator = new Iterator<C>() {
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
                var nextA = hasNextA ? iteratorA.next() : null;
                var nextB = hasNextB ? iteratorB.next() : null;
                return merger.apply(nextA, nextB);
            }
        };
        var iterable = new Iterable<C>() {
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
        var iterable = new Iterable<DATA>() {
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
                    var next = isA ? iteratorA.next() : iteratorB.next();
                    isA = !isA;
                    return next;
                }
            };
            @Override
            public Iterator<DATA> iterator() {
                return iterator;
            }
        };
        var spliterator = iterable.spliterator();
        var stream      = StreamSupport.stream(spliterator, false);
        return StreamPlus.from(stream);
    }
    
    static <DATA> IteratorPlus<DATA> rawIterator(Stream<DATA> stream) {
        return IteratorPlus.from(stream);
    }
    
    public static <DATA, TARGET> TARGET terminate(
            AsStreamPlus<DATA>          asStreamPlus,
            Func1<Stream<DATA>, TARGET> action) {
        var streamPlus = asStreamPlus.streamPlus();
        try {
            var stream = streamPlus.stream();
            var result = action.apply(stream);
            return result;
        } finally {
            streamPlus.close();
        }
    }
    
    public static <DATA> void terminate(
            AsStreamPlus<DATA>      asStreamPlus,
            FuncUnit1<Stream<DATA>> action) {
        var streamPlus = asStreamPlus.streamPlus();
        try {
            var stream = streamPlus.stream();
            action.accept(stream);
        } finally {
            streamPlus.close();
        }
    }
    
    /** Run the given action sequentially, make sure to set the parallelity of the result back. */
    static <D, T> StreamPlus<T> sequential(
            AsStreamPlus<D>      asStreamPlus,
            Func1<StreamPlus<D>, StreamPlus<T>> action) {
        var streamPlus = asStreamPlus.streamPlus();
        var isParallel = streamPlus.isParallel();
        
        var orgIntStreamPlus = streamPlus.sequential();
        var newIntStreamPlus = action.apply(orgIntStreamPlus);
        if (newIntStreamPlus.isParallel() == isParallel)
            return newIntStreamPlus;
        
        if (isParallel)
            return newIntStreamPlus.parallel();
        
        return newIntStreamPlus.sequential();
    }
    
    /** Run the given action sequentially, make sure to set the parallelity of the result back. */
    static <D, T> StreamPlus<T> sequentialToObj(
            AsStreamPlus<D>                     asStreamPlus,
            Func1<StreamPlus<D>, StreamPlus<T>> action) {
        return sequential(asStreamPlus, action);
    }
    
    static <D, T> StreamPlus<T> derive(
            AsStreamPlus<D>     asStreamPlus,
            Function<Stream<D>, Stream<T>> action) {
        var streamPlus = asStreamPlus.streamPlus();
        var orgStream  = streamPlus.stream();
        var newStream  = action.apply(orgStream);
        return StreamPlus.from(newStream);
    }
    
}
