package nawaman.functionalj.compose;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;

@FunctionalInterface
public interface Lens<TYPE, SUB> {
    
    public static <TYPE, SUB> Lens<TYPE, SUB> of(LensSpec<TYPE, SUB> spec) {
        return ()->spec;
    }
    public static <TYPE, SUB> Lens<TYPE, SUB> of(BiFunction<TYPE, SUB, TYPE> setter, Function<TYPE, SUB> getter) {
        return ()->new LensSpec<>(setter, getter);
    }
    public static <TYPE> Lens<List<TYPE>, TYPE> listAt(int index) {
        return ListLens.at(index);
    }
    public static <TYPE> Lens<TYPE[], TYPE> arrayAt(int index) {
        return ArrayLens.at(index);
    }
    public static <KEY, TYPE> Lens<Map<KEY, TYPE>, TYPE> mapAt(KEY key) {
        return MapLens.at(key);
    }
    
    public LensSpec<TYPE, SUB> getLensSpec();
    
    public default Function<TYPE, SUB> read() {
        return getLensSpec().read();
    }
    public default BiFunction<TYPE, SUB, TYPE> change() {
        return getLensSpec().change();
    }
    public default Function<TYPE, TYPE> map(Function<SUB, SUB> mapper) {
        val spec = getLensSpec();
        return t -> spec.change().apply(t, spec.read().andThen(mapper).apply(t));
    }
    public default Function<TYPE, TYPE> peek(BiConsumer<TYPE,SUB> consume) {
        val spec = getLensSpec();
        return t -> {
            consume.accept(t, spec.read().apply(t));
            return t;
        };
    }
    public default <SUBSUB> Lens<TYPE, SUBSUB> compose(Lens<SUB, SUBSUB> subLense) {
        val spec = getLensSpec();
        val newRead  = spec.read().andThen(subLense.read());
        val newWrite = (BiFunction<TYPE, SUBSUB, TYPE>)(t,ss) -> {
            val subRead    = spec.read().apply(t);
            val subChanged = subLense.change().apply(subRead, ss);
            val change     = spec.change().apply(t, subChanged);
            return change;
        };
        return (Lens<TYPE, SUBSUB>)()->new LensSpec<TYPE, SUBSUB>(newWrite, newRead);
    }
    
}