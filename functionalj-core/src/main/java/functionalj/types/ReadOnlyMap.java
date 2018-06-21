package functionalj.types;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import functionalj.annotations.Child;

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
    
    // TODO - Remove this after they are all done.
    public static Map<String, Child> of(Map<String, Child> children) {
        // TODO Auto-generated method stub
        return null;
    }
}
