package functionalj.stream;

import java.util.Comparator;
import java.util.Optional;

import functionalj.function.Func1;
import functionalj.tuple.Tuple2;

public interface StreamableWithStatistic<DATA> extends AsStreamable<DATA> {
    
    public default int size() {
        return (int)stream().count();
    }
    
    public default <D extends Comparable<D>> Optional<DATA> minBy(
            Func1<DATA, D> mapper) {
        return stream()
                .minBy(mapper);
    }
    
    public default <D extends Comparable<D>> Optional<DATA> maxBy(
            Func1<DATA, D> mapper) {
        return stream()
                .maxBy(mapper);
    }
    
    public default <D> Optional<DATA> minBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return stream()
                .minBy(mapper, comparator);
    }
    
    public default <D> Optional<DATA> maxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return stream()
                .maxBy(mapper, comparator);
    }
    
    public default Tuple2<Optional<DATA>, Optional<DATA>> minMax(
            Comparator<? super DATA> comparator) {
        return stream()
                .minMax(comparator);
    }
    
    public default <D extends Comparable<D>> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D> mapper) {
        return stream()
                .minMaxBy(mapper);
    }
    
    public default <D> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return stream()
                .minMaxBy(mapper, comparator);
    }
    
}
