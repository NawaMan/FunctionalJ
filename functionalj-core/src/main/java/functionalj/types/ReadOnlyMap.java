package functionalj.types;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

public class ReadOnlyMap<KEY, VALUE> implements Map<KEY, VALUE> {
    
    private final Map<KEY, VALUE> data;
    
    public static <KEY, VALUE> ReadOnlyMap of(Map<KEY, VALUE> data) {
        if (data instanceof ReadOnlyMap)
            return (ReadOnlyMap)data;
        
        return new ReadOnlyMap<>(data);
    }
    
    public ReadOnlyMap(Map<KEY, VALUE> data) {
        this.data = (data instanceof ReadOnlyMap) ? data : unmodifiableMap(new LinkedHashMap<>(data));
    }
    
    // TODO - Add with and append.

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public VALUE get(Object key) {
        return data.get(key);
    }

    @Override
    public VALUE put(KEY key, VALUE value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VALUE remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends KEY, ? extends VALUE> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<KEY> keySet() {
        return unmodifiableSet(data.keySet());
    }

    @Override
    public Collection<VALUE> values() {
        return unmodifiableCollection(data.values());
    }

    @Override
    public Set<Entry<KEY, VALUE>> entrySet() {
        return unmodifiableSet(data.entrySet());
    }
}
