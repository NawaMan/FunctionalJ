package functionalj.stream;

import static functionalj.functions.ObjFuncs.notEqual;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static java.lang.Boolean.TRUE;

public class StreamPlusUtils {
    
    public static <T> boolean equals(AsStreamPlus<T> stream1, AsStreamPlus<T> stream2) {
        return !stream1.streamPlus().zipWith(stream2.streamPlus(), AllowUnpaired, notEqual()).filter(TRUE::equals).findAny().isPresent();
    }
    
    public static <T> int hashCode(AsStreamPlus<T> stream) {
        return stream.streamPlus().mapToInt(e -> (e == null) ? 0 : e.hashCode()).reduce(1, (h, eh) -> 31 * h + eh);
    }
    
    public static <T> String toString(AsStreamPlus<T> stream) {
        return "[" + stream.join(", ") + "]";
    }
}
