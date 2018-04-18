package nawaman.functionalj.lens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableList;

public class ListAtLens {
    
    // TODO - Consider using WeakHashMap to prevent memory leak.
    // TODO - Might be more efficient to create a revert linked list to implement this.
    // TODO - Add Array index out-of-bound safe --- may be a part of nullSafety
    
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
        Function<List<TYPE>, TYPE>               read  = read(index);
        BiFunction<List<TYPE>, TYPE, List<TYPE>> write = change(index);
        LensSpec<List<TYPE>, TYPE>               spec  = LensSpec.of(read, write);
        return Lens.of(spec);
    }
    public static <TYPE> Lens<List<TYPE>, TYPE> head(int index) {
        return at(0);
    }
}