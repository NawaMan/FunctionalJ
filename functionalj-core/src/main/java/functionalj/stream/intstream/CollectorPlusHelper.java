package functionalj.stream.intstream;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collector.Characteristics;

public class CollectorPlusHelper {
    
    private static final Set<Characteristics> unorderedConcurrent = unmodifiableSet(EnumSet.of(UNORDERED, CONCURRENT));
    
    public static Set<Characteristics> unorderedConcurrent() {
        return unorderedConcurrent;
    }
    
}
