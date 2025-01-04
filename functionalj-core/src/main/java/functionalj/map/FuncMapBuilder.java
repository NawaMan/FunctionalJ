// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.List;
import functionalj.map.FuncMap.UnderlineMap;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;

public class FuncMapBuilder<K, V> {
    
    private final List<Tuple2<K, V>> entries;
    
    public FuncMapBuilder() {
        this.entries = new ArrayList<Tuple2<K, V>>();
    }
    
    private FuncMapBuilder(List<Tuple2<K, V>> entries) {
        this.entries = entries;
    }
    
    public FuncMapBuilder<K, V> with(K key, V value) {
        this.entries.add(Tuple.of(key, value));
        return new FuncMapBuilder<>(this.entries);
    }
    
    public ImmutableFuncMap<K, V> build() {
        val map = FuncMap.underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        for (Tuple2<K, V> entry : this.entries) {
            K key = entry._1();
            V value = entry._2();
            map.put(key, value);
        }
        return new ImmutableFuncMap<K, V>(map);
    }
}
