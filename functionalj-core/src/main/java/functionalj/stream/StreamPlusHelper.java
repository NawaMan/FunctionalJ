package functionalj.stream;

import static functionalj.functions.ObjFuncs.notEqual;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static java.lang.Boolean.TRUE;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

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
    
}
