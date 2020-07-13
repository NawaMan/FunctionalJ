package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.list.FuncList;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public interface StreamableWithReshape<DATA> extends AsStreamable<DATA> {
    
    public default Streamable<StreamPlus<DATA>> segment(int count) {
        return deriveFrom(this, stream -> stream.segment(count));
    }
    public default Streamable<StreamPlus<DATA>> segment(
            int     count, 
            boolean includeTail) {
        return deriveFrom(this, stream -> stream.segment(count, includeTail));
    }
    public default Streamable<StreamPlus<DATA>> segment(Predicate<DATA> startCondition) {
        return deriveFrom(this, stream -> stream.segment(startCondition));
    }
    public default Streamable<StreamPlus<DATA>> segment(
            Predicate<DATA> startCondition, 
            boolean         includeTail) {
        return deriveFrom(this, stream -> stream.segment(startCondition, includeTail));
    }
    
    public default Streamable<StreamPlus<DATA>> segment(
            Predicate<DATA> startCondition, 
            Predicate<DATA> endCondition) {
        return deriveFrom(this, stream -> stream.segment(startCondition, endCondition));
    }
    
    public default Streamable<StreamPlus<DATA>> segment(
            Predicate<DATA> startCondition, 
            Predicate<DATA> endCondition, 
            boolean         includeTail) {
        return deriveFrom(this, stream -> stream.segment(startCondition, endCondition, includeTail));
    }
    
    public default Streamable<StreamPlus<DATA>> segmentSize(Func1<DATA, Integer> segmentSize) {
        return deriveFrom(this, stream -> stream.segmentSize(segmentSize));
    }
    
    public default Streamable<DATA> collapseWhen(
            Predicate<DATA>         conditionToCollapse, 
            Func2<DATA, DATA, DATA> concatFunc) {
        return deriveFrom(this, stream -> stream.collapseWhen(conditionToCollapse, concatFunc));
    }
    
    public default Streamable<DATA> collapseSize(
            Func1<DATA, Integer>    segmentSize, 
            Func2<DATA, DATA, DATA> concatFunc) {
        return deriveFrom(this, stream -> stream.collapseSize(segmentSize, concatFunc));
    }
    
    public default <TARGET> Streamable<TARGET> collapseSize(
            Func1<DATA, Integer>          segmentSize, 
            Func1<DATA, TARGET>           mapper, 
            Func2<TARGET, TARGET, TARGET> concatFunc) {
        return deriveFrom(this, stream -> stream.collapseSize(segmentSize, mapper, concatFunc));
    }
    
    //-- More - then StreamPlus --
    
    public default <T> Streamable<FuncList<DATA>> segmentByPercentiles(int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(percentileList);
    }
    
    public default <T> Streamable<FuncList<DATA>> segmentByPercentiles(double ... percentiles) {
        // TODO - Make it lazy
        val percentileList = DoubleStreamPlus.of(percentiles).boxed().toImmutableList();
        return segmentByPercentiles(percentileList);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(FuncList<Double> percentiles) {
        val list = streamPlus().sorted().toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, Comparator<T> comparator, int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, double ... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).boxed().toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, Comparator<T> comparator, double ... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).boxed().toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, FuncList<Double> percentiles) {
        val list = streamPlus().sortedBy(mapper).toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, Comparator<T> comparator, FuncList<Double> percentiles) {
        val list = streamPlus().sortedBy(mapper, comparator).toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
}
