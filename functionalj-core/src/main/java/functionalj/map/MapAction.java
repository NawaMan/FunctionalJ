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

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import functionalj.function.Func2;

abstract class MapAction<K, S, V> {
    
    static class With<K, V> extends MapAction<K, V, V> {
        
        final K key;
        
        final V value;
        
        With(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    static class FilterKey<K, V> extends MapAction<K, V, V> {
        
        final Predicate<? super K> keyCheck;
        
        FilterKey(Predicate<? super K> keyCheck) {
            this.keyCheck = keyCheck;
        }
    }
    
    static class FilterBoth<K, V> extends MapAction<K, V, V> {
        
        final BiPredicate<? super K, ? super V> check;
        
        FilterBoth(BiPredicate<? super K, ? super V> check) {
            this.check = check;
        }
    }
    
    static class Mapping<K, S, V> extends MapAction<K, S, V> {
        
        final Func2<? super K, ? super S, ? extends V> mapper;
        
        public Mapping(final Func2<? super K, ? super S, ? extends V> mapper) {
            this.mapper = mapper;
        }
    }
}
