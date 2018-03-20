package nawaman.functionalj.compose;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;

public class ArrayLens {
    public static <TYPE> Function<TYPE[], TYPE> get(int index) {
        return array -> array[index];
    }
    public static <TYPE> BiFunction<TYPE[], TYPE, TYPE[]> set(int index) {
        return (array,key) -> {
            val newA = (TYPE[])Array.newInstance(array.getClass().getComponentType(), array.length);
            System.arraycopy(array, 0, newA, 0, array.length);
            newA[index] = key;
            return newA;
        };
    }
    public static <TYPE> Lens<TYPE[], TYPE> at(int index) {
        return Lens.of(set(index), get(index));
    }
}