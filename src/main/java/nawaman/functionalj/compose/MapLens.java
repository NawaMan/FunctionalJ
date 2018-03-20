package nawaman.functionalj.compose;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableMap;

public class MapLens {
    // Consider using WeakHashMap to prevent memory leak.
    private static final Map<Class, Supplier<Map>> newMapSuppliers = new ConcurrentHashMap<>();
    
    public static <KEY, TYPE> Function<Map<KEY, TYPE>, TYPE> read(KEY key) {
        return map -> map.get(key);
    }
    public static <KEY,TYPE> BiFunction<Map<KEY, TYPE>, TYPE, Map<KEY, TYPE>> change(KEY key) {
        return (map,value) -> {
            @SuppressWarnings("rawtypes")
            Class<? extends Map> mapClass = map.getClass();
            @SuppressWarnings("unchecked")
            Map<KEY, TYPE> newMap = newMapSuppliers.computeIfAbsent(mapClass, clzz->{
                try {
                    mapClass.newInstance();
                    return ()->{
                        try {
                            return mapClass.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    };
                } catch (InstantiationException | IllegalAccessException e) {
                    return ()-> new HashMap<>();
                }
            }).get();
            
            newMap.putAll(map);
            newMap.put(key, value);
            return unmodifiableMap(newMap);
        };
    }
    
    public static <KEY, TYPE> Lens<Map<KEY, TYPE>, TYPE> at(KEY key) {
        return Lens.of(change(key), read(key));
    }
}