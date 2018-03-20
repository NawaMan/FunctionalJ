package nawaman.functionalj.compose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableList;

public class ListLens {
    // Consider using WeakHashMap to prevent memory leak.
    private static final Map<Class, Supplier<List>> newListSuppliers = new ConcurrentHashMap<>();
    public static <TYPE> Function<List<TYPE>, TYPE> read(int index) {
        return list -> list.get(index);
    }
    public static <TYPE> BiFunction<List<TYPE>, TYPE, List<TYPE>> change(int index) {
        return (list,newValue) -> {
            @SuppressWarnings("rawtypes")
            Class<? extends List> listClass = list.getClass();
            @SuppressWarnings("unchecked")
            List<TYPE> newList = newListSuppliers.computeIfAbsent(listClass, clzz->{
                try {
                    listClass.newInstance();
                    return ()->{
                        try {
                            return listClass.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    };
                } catch (InstantiationException | IllegalAccessException e) {
                    return ()-> new ArrayList<>();
                }
            }).get();
            
            newList.addAll(list);
            newList.set(index, newValue);
            return unmodifiableList(newList);
        };
    }
    public static <TYPE> Lens<List<TYPE>, TYPE> at(int index) {
        return Lens.of(change(index), read(index));
    }
}