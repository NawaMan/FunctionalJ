// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("javadoc")
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
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default VALUE remove(Object key) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default void putAll(Map<? extends KEY, ? extends VALUE> m) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default void clear() {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
    @Override
    public default void replaceAll(BiFunction<? super KEY, ? super VALUE, ? extends VALUE> function) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default VALUE putIfAbsent(KEY key, VALUE value) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default boolean remove(Object key, Object value) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default boolean replace(KEY key, VALUE oldValue, VALUE newValue) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default VALUE replace(KEY key, VALUE value) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default VALUE computeIfAbsent(KEY key, Function<? super KEY, ? extends VALUE> mappingFunction) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default VALUE computeIfPresent(KEY key, BiFunction<? super KEY, ? super VALUE, ? extends VALUE> remappingFunction) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default VALUE compute(KEY key, BiFunction<? super KEY, ? super VALUE, ? extends VALUE> remappingFunction) {
        throw new ReadOnlyMapException(this);
    }
    
    @Override
    public default VALUE merge(KEY key, VALUE value, BiFunction<? super VALUE, ? super VALUE, ? extends VALUE> remappingFunction) {
        throw new ReadOnlyMapException(this);
    }
    
}
