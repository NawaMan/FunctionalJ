package functionalj.streamable.intstreamable;

import java.util.Comparator;
import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;

import functionalj.tuple.Tuple2;

public interface IntStreamableWithStatistic extends AsIntStreamable {

    /** @return  the size of the stream */
    public default int size() {
        return (int)stream().count();
    }

    /** Return the value whose mapped value is the smallest. */
    public default <D extends Comparable<D>> OptionalInt minBy(
            IntFunction<D> mapper) {
        return stream()
                .minBy(mapper);
    }

    /** Return the value whose mapped value is the biggest. */
    public default <D extends Comparable<D>> OptionalInt maxBy(
            IntFunction<D> mapper) {
        return stream()
                .maxBy(mapper);
    }

    /** Return the value whose mapped value is the smallest using the comparator. */
    public default <D> OptionalInt minBy(
            IntFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return stream()
                .minBy(mapper, comparator);
    }

    /** Return the value whose mapped value is the biggest using the comparator. */
    public default <D> OptionalInt maxBy(
            IntFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return stream()
                .maxBy(mapper, comparator);
    }

    /** Return the value is the smallest and the biggest using the comparator. */
    public default Tuple2<OptionalInt, OptionalInt> minMax(
            IntBinaryOperator comparator) {
        return stream()
                .minMax(comparator);
    }

    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Tuple2<OptionalInt, OptionalInt> minMaxBy(
            IntFunction<D> mapper) {
        return stream()
                .minMaxBy(mapper);
    }

    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    public default <D> Tuple2<OptionalInt, OptionalInt> minMaxBy(
            IntFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return stream()
                .minMaxBy(mapper, comparator);
    }

}
