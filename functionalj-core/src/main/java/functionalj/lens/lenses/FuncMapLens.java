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
package functionalj.lens.lenses;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func;
import functionalj.lens.core.AccessParameterized2;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized2;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.map.FuncMap;
import functionalj.tuple.ImmutableTuple2;


public interface FuncMapLens<HOST, KEY, VALUE, 
                            KEYLENS   extends AnyLens<HOST,KEY>, 
                            VALUELENS extends AnyLens<HOST,VALUE>>
                    extends
                        ObjectLens<HOST, FuncMap<KEY, VALUE>>,
                        FuncMapAccess<HOST, KEY, VALUE, KEYLENS, VALUELENS> {
    
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
            FuncMapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(
                    Function<HOST,  FuncMap<KEY, VALUE>>           read,
                    WriteLens<HOST, FuncMap<KEY, VALUE>>           write,
                    Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                    Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        var spec = LensUtils.createFuncMapLensSpec(read, write, keyLensCreator, valueLensCreator);    
        return ()->spec;
    }
    
    public LensSpecParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> lensSpecParameterized2();
    
    @Override
    public default AccessParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> accessParameterized2() {
        return lensSpecParameterized2();
    }

    @Override
    public default LensSpec<HOST, FuncMap<KEY, VALUE>> lensSpec() {
        return lensSpecParameterized2().getSpec();
    }

    @Override
    public default FuncMap<KEY, VALUE> applyUnsafe(HOST host) throws Exception {
        return ObjectLens.super.apply(host);
    }
    
    public default VALUELENS get(KEY key) {
        WriteLens<FuncMap<KEY, VALUE>, VALUE> write = (map, value) -> {
            var newMap = map.with(key, value);
            return newMap;
        };
        Function<FuncMap<KEY, VALUE>, VALUE> read = map -> {
            return map.get(key);
        };
        return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens2);
    }
    
    public default Function<HOST, HOST> changeTo(Predicate<KEY> checker, Function<VALUE, VALUE> mapper) {
        var mapEntry = Func.from((Map.Entry<KEY, VALUE> each) ->{
            var key   = each.getKey();
            var value = each.getValue();
            if (!checker.test(key)) 
                return each;
            
            var newValue = mapper.apply(value);
            return (Map.Entry<KEY, VALUE>)new ImmutableTuple2<KEY, VALUE>(key, newValue);
        });
        
        var newMap = new LinkedHashMap<KEY, VALUE>();
        Consumer<? super Entry<KEY, VALUE>> transformEntry = entry -> {
            var key   = entry.getKey();
            var value = entry.getValue();
            if (!checker.test(key)) 
                newMap.put(key, value);
            else {
                var newValue = mapper.apply(value);
                newMap.put(key, newValue);
            }
        };
        return host -> {
            apply(host).entrySet().stream()
                    .map    (mapEntry)
                    .forEach(transformEntry);
            var newHost = apply(host, FuncMap.from(newMap));
            return newHost;
        };
    }
    
    public default Function<HOST, HOST> changeTo(BiPredicate<KEY, VALUE> checker, Function<VALUE, VALUE> mapper) {
        var mapEntry = Func.from((Map.Entry<KEY, VALUE> each) ->{
            var key   = each.getKey();
            var value = each.getValue();
            if (!checker.test(key, value)) 
                return each;
            
            var newValue = mapper.apply(value);
            return (Map.Entry<KEY, VALUE>)new ImmutableTuple2<KEY, VALUE>(key, newValue);
        });
        
        return host -> {
            var newMap = new LinkedHashMap<KEY, VALUE>();
            Consumer<? super Entry<KEY, VALUE>> transformEntry = entry -> {
                var key   = entry.getKey();
                var value = entry.getValue();
                if (!checker.test(key, value)) 
                    newMap.put(key, value);
                else {
                    var newValue = mapper.apply(value);
                    newMap.put(key, newValue);
                }
            };
            apply(host).entrySet().stream()
                    .map    (mapEntry)
                    .forEach(transformEntry);
            var newHost = apply(host, FuncMap.from(newMap));
            return newHost;
        };
    }
}
