package functionalj.types;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ReadOnlyMap<KEY, VALUE> extends Map<KEY, VALUE> {
    
    @Override
    public int size();

    @Override
    public default boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Set<KEY> keySet();

    @Override
    public Collection<VALUE> values();

    @Override
    public Set<Entry<KEY, VALUE>> entrySet() ;

    @Override
    public boolean containsKey(Object key);

    @Override
    public boolean containsValue(Object value);

    @Override
    public VALUE get(Object key);
    
    @Override
    public VALUE getOrDefault(Object key, VALUE defaultValue);

    @Override
    public default VALUE put(KEY key, VALUE value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default VALUE remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default void putAll(Map<? extends KEY, ? extends VALUE> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default void clear() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
    @Override
    public default void replaceAll(BiFunction<? super KEY, ? super VALUE, ? extends VALUE> function) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default VALUE putIfAbsent(KEY key, VALUE value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default boolean replace(KEY key, VALUE oldValue, VALUE newValue) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default VALUE replace(KEY key, VALUE value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default VALUE computeIfAbsent(KEY key, Function<? super KEY, ? extends VALUE> mappingFunction) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default VALUE computeIfPresent(KEY key, BiFunction<? super KEY, ? super VALUE, ? extends VALUE> remappingFunction) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default VALUE compute(KEY key, BiFunction<? super KEY, ? super VALUE, ? extends VALUE> remappingFunction) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default VALUE merge(KEY key, VALUE value, BiFunction<? super VALUE, ? super VALUE, ? extends VALUE> remappingFunction) {
        throw new UnsupportedOperationException();
    }
    
}
