package functionalj.stream.intstream;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collector.Characteristics;

public class CollectorPlusHelper {
    
    private static final Set<Characteristics> characteristics = unmodifiableSet(EnumSet.of(CONCURRENT, UNORDERED));
    
    public static Set<Characteristics> characteristics() {
        return characteristics;
    }
}
