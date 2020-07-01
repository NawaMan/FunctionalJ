// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import lombok.val;

public final class ImmutableMap<KEY, VALUE> extends FuncMapDerived<KEY, VALUE, VALUE> {
    
    @SuppressWarnings("unchecked")
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> from(Map<? extends KEY, ? extends VALUE> map) {
        return (map instanceof ImmutableMap)
                ? (ImmutableMap<KEY, VALUE>)map
                : new ImmutableMap<KEY, VALUE>(map);
    }
    @SuppressWarnings("unchecked")
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> from(FuncMap<? extends KEY, ? extends VALUE> map) {
        return (map instanceof ImmutableMap)
                ? (ImmutableMap<KEY, VALUE>)map
                : new ImmutableMap<KEY, VALUE>(map);
    }
    
    @SuppressWarnings("unchecked")
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> empty() {
        return empty;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final ImmutableMap empty = new ImmutableMap(Collections.emptyMap());
    
    private final boolean isLazy;
    
    ImmutableMap(Map<? extends KEY, ? extends VALUE> map) {
        this(map, true);
    }
    ImmutableMap(Map<? extends KEY, ? extends VALUE> map, boolean isLazy) {
        super(createBaseMap(map), null);
        this.isLazy = isLazy;
    }
    
    @SuppressWarnings("unchecked")
    private static <K,V> Map<K, V> createBaseMap(Map<? extends K, ? extends V> map) {
        return (map instanceof ImmutableMap) ? (ImmutableMap<K, V>) map : new LinkedHashMap<K, V>(map);
    }
    
    public boolean isLazy() {
        return isLazy;
    }
    
    public boolean isEager() {
        return !isLazy;
    }
    
    public FuncMap<KEY, VALUE> lazy() {
        if (isLazy)
            return this;
        
        return new ImmutableMap<KEY, VALUE>(this, true);
    }
    public FuncMap<KEY, VALUE> eager() {
        if (!isLazy)
            return this;
        
        return new ImmutableMap<KEY, VALUE>(this, false);
    }
    
    @Override
    public FuncMap<KEY, VALUE> sorted() {
        if (map instanceof TreeMap)
            return this;
        
        val sortedMap = new TreeMap<KEY, VALUE>();
        entryStream()
            .forEach(e -> sortedMap.put(e.getKey(), e.getValue()));
        return new ImmutableMap<KEY, VALUE>(sortedMap, isLazy());
    }
    
}
