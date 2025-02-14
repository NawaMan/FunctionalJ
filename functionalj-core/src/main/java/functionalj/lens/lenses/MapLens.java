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
package functionalj.lens.lenses;

import static functionalj.functions.StrFuncs.escapeJava;
import static functionalj.functions.StrFuncs.joinNonNull;
import static functionalj.functions.StrFuncs.toStr;
import static functionalj.functions.StrFuncs.whenBlank;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.function.Func;
import functionalj.function.Named;
import functionalj.lens.core.AccessParameterized2;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized2;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.tuple.ImmutableTuple2;
import lombok.val;

public interface MapLens<HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST, KEY>, VALUELENS extends AnyLens<HOST, VALUE>> extends ObjectLens<HOST, Map<KEY, VALUE>>, MapAccess<HOST, KEY, VALUE, KEYLENS, VALUELENS> {
    
    public static class Impl<H, K, V, KL extends AnyLens<H, K>, VL extends AnyLens<H, V>> extends ObjectLens.Impl<H, Map<K, V>> implements MapLens<H, K, V, KL, VL> {
        
        private LensSpecParameterized2<H, Map<K, V>, K, V, KL, VL> spec;
        
        public Impl(String name, LensSpecParameterized2<H, Map<K, V>, K, V, KL, VL> spec) {
            super(name, spec.getSpec());
            this.spec = spec;
        }
        
        @Override
        public LensSpecParameterized2<H, Map<K, V>, K, V, KL, VL> lensSpecParameterized2() {
            return spec;
        }
    }
    
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST, KEY>, VALUELENS extends AnyLens<HOST, VALUE>> MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(String name, Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val spec = LensUtils.createMapLensSpec(read, write, keyLensCreator, valueLensCreator);
        return new Impl<>(name, spec);
    }
    
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST, KEY>, VALUELENS extends AnyLens<HOST, VALUE>> MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return of(null, read, write, keyLensCreator, valueLensCreator);
    }
    
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST, KEY>, VALUELENS extends AnyLens<HOST, VALUE>> MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, Function<LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return of(null, read, write, (__, spec) -> keyLensCreator.apply(spec), (__, spec) -> valueLensCreator.apply(spec));
    }
    
    public LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> lensSpecParameterized2();
    
    @Override
    public default AccessParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> accessParameterized2() {
        return lensSpecParameterized2();
    }
    
    @Override
    public default LensSpec<HOST, Map<KEY, VALUE>> lensSpec() {
        return lensSpecParameterized2().getSpec();
    }
    
    @Override
    public default Map<KEY, VALUE> applyUnsafe(HOST host) throws Exception {
        return ObjectLens.super.applyUnsafe(host);
    }
    
    public default VALUELENS get(KEY key) {
        Function<Map<KEY, VALUE>, VALUE> read = map -> {
            return map.get(key);
        };
        WriteLens<Map<KEY, VALUE>, VALUE> write = (map, value) -> {
            val newMap = new LinkedHashMap<KEY, VALUE>();
            newMap.putAll(map);
            newMap.put(key, value);
            return newMap;
        };
        val name = (this instanceof Named) ? ((Named) this).name() : null;
        val keyText = escapeJava(toStr(key));
        val lensName = whenBlank(joinNonNull(".", name, "get(\"" + keyText + "\")"), (String) null);
        return LensUtils.createSubLens(this, lensName, read, write, lensSpecParameterized2()::createSubLens2);
    }
    
    public default Function<HOST, HOST> changeEach(Predicate<KEY> checker, Function<VALUE, VALUE> mapper) {
        val mapEntry = Func.from((Map.Entry<KEY, VALUE> each) -> {
            val key = each.getKey();
            val value = each.getValue();
            if (!checker.test(key))
                return each;
            val newValue = mapper.apply(value);
            return (Map.Entry<KEY, VALUE>) new ImmutableTuple2<KEY, VALUE>(key, newValue);
        });
        val newMap = new LinkedHashMap<KEY, VALUE>();
        Consumer<? super Entry<KEY, VALUE>> transformEntry = entry -> {
            val key = entry.getKey();
            val value = entry.getValue();
            if (!checker.test(key))
                newMap.put(key, value);
            else {
                val newValue = mapper.apply(value);
                newMap.put(key, newValue);
            }
        };
        return host -> {
            apply(host).entrySet().stream().map(mapEntry).forEach(transformEntry);
            val newHost = apply(host, newMap);
            return newHost;
        };
    }
    
    public default Function<HOST, HOST> changeEach(BiPredicate<KEY, VALUE> checker, Function<VALUE, VALUE> mapper) {
        val mapEntry = Func.from((Map.Entry<KEY, VALUE> each) -> {
            val key = each.getKey();
            val value = each.getValue();
            if (!checker.test(key, value))
                return each;
            val newValue = mapper.apply(value);
            return (Map.Entry<KEY, VALUE>) new ImmutableTuple2<KEY, VALUE>(key, newValue);
        });
        return host -> {
            val newMap = new LinkedHashMap<KEY, VALUE>();
            Consumer<? super Entry<KEY, VALUE>> transformEntry = entry -> {
                val key = entry.getKey();
                val value = entry.getValue();
                if (!checker.test(key, value))
                    newMap.put(key, value);
                else {
                    val newValue = mapper.apply(value);
                    newMap.put(key, newValue);
                }
            };
            apply(host).entrySet().stream().map(mapEntry).forEach(transformEntry);
            val newHost = apply(host, newMap);
            return newHost;
        };
    }
}
