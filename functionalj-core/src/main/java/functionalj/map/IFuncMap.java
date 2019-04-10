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

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.list.FuncList;

@SuppressWarnings("javadoc")
public interface IFuncMap<KEY, VALUE, SELF extends IFuncMap<KEY, VALUE, ?>> {
    
    public int size();
    
    public boolean isEmpty();
    
    public boolean hasKey(KEY key);
    
    public boolean hasValue(VALUE value);
    
    public boolean hasKey(Predicate<? super KEY> keyCheck);
    
    public boolean hasValue(Predicate<? super VALUE> valueCheck);
    
    public Optional<VALUE> findBy(KEY key);
    
    public FuncList<VALUE> select(Predicate<? super KEY> keyPredicate);
    
    public FuncList<Map.Entry<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    public FuncMap<KEY, VALUE> with(KEY key, VALUE value);
    
    public FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    public FuncMap<KEY, VALUE> exclude(KEY key);
    
    public FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    public FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    public FuncMap<KEY, VALUE> filterByEntry(Predicate<? super Entry<? super KEY, ? super VALUE>> entryCheck);
    
    public FuncList<KEY> keys();
    
    public FuncList<VALUE> values();
    
    public Set<Entry<KEY, VALUE>> entrySet();
    
    public FuncList<Map.Entry<KEY, VALUE>> entries();
    
    public Map<KEY, VALUE> toMap();
    
    public ImmutableMap<KEY, VALUE> toImmutableMap();
    
    public FuncMap<KEY, VALUE> sorted();
    
    public FuncMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator);
    
    public default <TARGET> FuncMap<KEY, TARGET> mapValue(Function<? super VALUE, ? extends TARGET> mapper) {
        return map((k, v)->mapper.apply(v));
    }
    
    public <TARGET> FuncMap<KEY, TARGET> map(BiFunction<? super KEY, ? super VALUE, ? extends TARGET> mapper);
    
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
    public void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action);
    
}
