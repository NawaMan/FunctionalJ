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

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import functionalj.list.ImmutableList;
import functionalj.tuple.ImmutableTuple2;
import functionalj.tuple.IntTuple2;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public final class ImmutableMap<KEY, VALUE> extends FuncMapStream<KEY, VALUE> {
    
    @SuppressWarnings("unchecked")
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> from(Map<? extends KEY, ? extends VALUE> map) {
        return (map instanceof ImmutableMap)
                ? (ImmutableMap<KEY, VALUE>)map
                : new ImmutableMap<KEY, VALUE>(map.entrySet().stream());
    }
    @SuppressWarnings("unchecked")
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> from(FuncMap<? extends KEY, ? extends VALUE> map) {
        return (map instanceof ImmutableMap)
                ? (ImmutableMap<KEY, VALUE>)map
                : new ImmutableMap<KEY, VALUE>(map.entries().stream());
    }
    
    private final boolean isLazy;

    public ImmutableMap(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        this(stream, true);
    }
    public ImmutableMap(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream, boolean isLazy) {
        // TODO - this shitty code have to be replaced .... :-(
        super(null, createPairList(stream));
        this.isLazy = isLazy;
    }
    private static <KEY, VALUE> ImmutableList<IntTuple2<ImmutableTuple2<KEY, VALUE>>> createPairList(
            Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        val list = new ArrayList<KEY>();
        return ImmutableList.from(
                        stream
                        .filter(entry -> !list.contains(entry.getKey()))
                        .peek  (entry -> list.add(entry.getKey()))
                        .map   (entry -> {
                            int keyHash = calculateHash(entry.getKey());
                            @SuppressWarnings("unchecked")
                            ImmutableTuple2<KEY, VALUE> tuple = (entry instanceof ImmutableTuple2)
                                    ? (ImmutableTuple2<KEY, VALUE>)entry
                                    : new ImmutableTuple2<KEY, VALUE>(entry);
                            return new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, tuple);
                        }));
    }
    
    private static Integer calculateHash(Object key) {
        return Nullable.of(key)
                .map(Object::hashCode)
                .orElse(0);
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
        
        val entries = entries();
        return new ImmutableMap<KEY, VALUE>(entries, true);
    }
    public FuncMap<KEY, VALUE> eager() {
        if (!isLazy)
            return this;
        
        val entries = entries();
        return new ImmutableMap<KEY, VALUE>(entries, false);
    }
    
}
