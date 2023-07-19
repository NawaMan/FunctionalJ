// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Collection;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.function.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.AccessParameterized2;
import functionalj.map.FuncMap;
import functionalj.tuple.Tuple2;
import lombok.val;

@FunctionalInterface
public interface FuncMapAccess<HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST, KEY>, VALUEACCESS extends AnyAccess<HOST, VALUE>> extends AccessParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> {
    
    public static <H, K, V, KA extends AnyAccess<H, K>, VA extends AnyAccess<H, V>> FuncMapAccess<H, K, V, KA, VA> of(Function<H, Map<K, V>> mapAccess, Function<Function<H, K>, KA> createKeyAccess, Function<Function<H, V>, VA> createValueAccess) {
        val accessParameterized2 = new AccessParameterized2<H, FuncMap<K, V>, K, V, KA, VA>() {
    
            @Override
            public FuncMap<K, V> applyUnsafe(H host) throws Exception {
                return FuncMap.from(mapAccess.apply(host));
            }
    
            @Override
            public KA createSubAccessFromHost1(Function<H, K> accessToParameter) {
                return createKeyAccess.apply(accessToParameter);
            }
    
            @Override
            public VA createSubAccessFromHost2(Function<H, V> accessToParameter) {
                return createValueAccess.apply(accessToParameter);
            }
        };
        return () -> accessParameterized2;
    }
    
    public AccessParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> accessParameterized2();
    
    @Override
    public default FuncMap<KEY, VALUE> applyUnsafe(HOST host) throws Exception {
        return accessParameterized2().apply(host);
    }
    
    @Override
    public default KEYACCESS createSubAccess1(Function<FuncMap<KEY, VALUE>, KEY> accessToParameter) {
        return keyAccess(accessToParameter);
    }
    
    @Override
    public default VALUEACCESS createSubAccess2(Function<FuncMap<KEY, VALUE>, VALUE> accessToParameter) {
        return valueAccess(accessToParameter);
    }
    
    @Override
    public default KEYACCESS createSubAccessFromHost1(Function<HOST, KEY> accessToParameter) {
        return accessParameterized2().createSubAccessFromHost1(accessToParameter);
    }
    
    @Override
    public default VALUEACCESS createSubAccessFromHost2(Function<HOST, VALUE> accessToParameter) {
        return accessParameterized2().createSubAccessFromHost2(accessToParameter);
    }
    
    public default KEYACCESS keyAccess(Function<FuncMap<KEY, VALUE>, KEY> accessToParameter) {
        return accessParameterized2().createSubAccess1(accessToParameter);
    }
    
    public default VALUEACCESS valueAccess(Function<FuncMap<KEY, VALUE>, VALUE> accessToParameter) {
        return accessParameterized2().createSubAccess2(accessToParameter);
    }
    
    public default VALUEACCESS get(KEY key) {
        return valueAccess(map -> map.get(key));
    }
    
    public default VALUEACCESS getOrDefault(KEY key, VALUE defaultValue) {
        return valueAccess(map -> map.getOrDefault(key, defaultValue));
    }
    
    // TODO - Uncomment.
    public default IntegerAccess<HOST> size() {
        return intPrimitiveAccess(0, map -> map.size());
    }
    
    public default BooleanAccess<HOST> isEmpty() {
        return booleanAccess(true, map -> map.isEmpty());
    }
    
    public default BooleanAccess<HOST> containsKey(KEY key) {
        return booleanAccess(false, map -> map.containsKey(key));
    }
    
    public default BooleanAccess<HOST> containsKey(Predicate<KEY> keyPredicate) {
        return booleanAccess(false, map -> map.keySet().stream().anyMatch(keyPredicate));
    }
    
    public default BooleanAccess<HOST> containsValue(VALUE value) {
        return booleanAccess(false, map -> map.containsValue(value));
    }
    
    public default BooleanAccess<HOST> containsValue(Predicate<VALUE> valuePredicate) {
        return booleanAccess(false, map -> map.values().stream().anyMatch(valuePredicate));
    }
    
    public default BooleanAccess<HOST> containsEntry(Predicate<Map.Entry<KEY, VALUE>> entryPredicate) {
        return booleanAccess(false, map -> map.entrySet().stream().anyMatch(entryPredicate));
    }
    
    public default BooleanAccess<HOST> containsEntry(BiPredicate<KEY, VALUE> entryBiPredicate) {
        return containsEntry(entry -> entryBiPredicate.test(entry.getKey(), entry.getValue()));
    }
    
    // 
    // public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>,
    // MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>
    // entries() {
    // val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map -> map.entrySet());
    // return () -> entryCollectionSpec;
    // }
    // public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>,
    // MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>
    // filterEntries(Predicate<Map.Entry<KEY, VALUE>> entryPredicate) {
    // val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map->{
    // return map.entrySet().stream().filter(entryPredicate).collect(toSet());
    // });
    // return () -> entryCollectionSpec;
    // }
    // public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>,
    // MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>
    // filterEntries(BiPredicate<KEY, VALUE> entryBiPredicate) {
    // val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map->{
    // return map.entrySet().stream()
    // .filter(entry -> entryBiPredicate.test(entry.getKey(), entry.getValue()))
    // .collect(toSet());
    // });
    // return () -> entryCollectionSpec;
    // }
    // public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>,
    // MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>
    // filter(Predicate<KEY> keyPredicate) {
    // val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map->{
    // return map.entrySet().stream()
    // .filter(entry->keyPredicate.test(entry.getKey()))
    // .collect(toSet());
    // });
    // return () -> entryCollectionSpec;
    // }
    public default CollectionAccess<HOST, Collection<KEY>, KEY, KEYACCESS> keys() {
        val keyCollectionSpec = FuncMapAccessHelper.createKeyCollectionSpec(accessParameterized2(), Map::keySet);
        return () -> keyCollectionSpec;
    }
    
    public default CollectionAccess<HOST, Collection<VALUE>, VALUE, VALUEACCESS> values() {
        val valueCollectionSpec = FuncMapAccessHelper.createValueCollectionSpec(accessParameterized2(), Map::values);
        return () -> valueCollectionSpec;
    }
}

class FuncMapAccessHelper {
    
    public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST, KEY>, VALUEACCESS extends AnyAccess<HOST, VALUE>> AccessParameterized<HOST, Collection<KEY>, KEY, KEYACCESS> createKeyCollectionSpec(AccessParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> spec, Function<FuncMap<KEY, VALUE>, Collection<KEY>> getKeys) {
        return new AccessParameterized<HOST, Collection<KEY>, KEY, KEYACCESS>() {
    
            @Override
            public Collection<KEY> applyUnsafe(HOST host) throws Exception {
                return getKeys.apply(spec.apply(host));
            }
    
            @Override
            public KEYACCESS createSubAccessFromHost(Function<HOST, KEY> accessToParameter) {
                return spec.createSubAccessFromHost1(accessToParameter);
            }
        };
    }
    
    public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST, KEY>, VALUEACCESS extends AnyAccess<HOST, VALUE>> AccessParameterized<HOST, Collection<VALUE>, VALUE, VALUEACCESS> createValueCollectionSpec(AccessParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> spec, Function<FuncMap<KEY, VALUE>, Collection<VALUE>> getValues) {
        return new AccessParameterized<HOST, Collection<VALUE>, VALUE, VALUEACCESS>() {
    
            @Override
            public Collection<VALUE> applyUnsafe(HOST host) throws Exception {
                return getValues.apply(spec.apply(host));
            }
    
            @Override
            public VALUEACCESS createSubAccessFromHost(Function<HOST, VALUE> accessToParameter) {
                return spec.createSubAccessFromHost2(accessToParameter);
            }
        };
    }
    
    public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST, KEY>, VALUEACCESS extends AnyAccess<HOST, VALUE>> AccessParameterized<HOST, Collection<Tuple2<KEY, VALUE>>, Tuple2<KEY, VALUE>, FuncMapEntryAccess<HOST, Tuple2<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> createEntryCollectionSpec(AccessParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> spec, Function<FuncMap<KEY, VALUE>, Collection<Tuple2<KEY, VALUE>>> accessEntrySet) {
        val access = new AccessParameterized<HOST, Collection<Tuple2<KEY, VALUE>>, Tuple2<KEY, VALUE>, FuncMapEntryAccess<HOST, Tuple2<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>() {
    
            @Override
            public Collection<Tuple2<KEY, VALUE>> applyUnsafe(HOST host) throws Exception {
                return accessEntrySet.apply(spec.apply(host));
            }
    
            @Override
            public FuncMapEntryAccess<HOST, Tuple2<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> createSubAccessFromHost(Function<HOST, Tuple2<KEY, VALUE>> accessToParameter) {
                // TODO - generalized this or just move it to other place.
                return new FuncMapEntryAccess<HOST, Tuple2<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>() {
    
                    @Override
                    public AccessParameterized2<HOST, Tuple2<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> accessParameterized2() {
                        AccessParameterized2<HOST, Tuple2<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> accessParameterized2 = new AccessParameterized2<HOST, Tuple2<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>() {
    
                            @Override
                            public Tuple2<KEY, VALUE> applyUnsafe(HOST host) throws Exception {
                                val entry = accessToParameter.apply(host);
                                return entry;
                            }
    
                            @Override
                            public KEYACCESS createSubAccessFromHost1(Function<HOST, KEY> accessToParameter) {
                                return spec.createSubAccessFromHost1(accessToParameter);
                            }
    
                            @Override
                            public VALUEACCESS createSubAccessFromHost2(Function<HOST, VALUE> accessToParameter) {
                                return spec.createSubAccessFromHost2(accessToParameter);
                            }
                        };
                        return accessParameterized2;
                    }
                };
            }
        };
        return access;
    }
    
    public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST, KEY>, VALUEACCESS extends AnyAccess<HOST, VALUE>> AccessParameterized2<HOST, FuncMap.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> createEntrySpec(AccessParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> mapAccessSpec, Func1<FuncMap<KEY, VALUE>, FuncMap.Entry<KEY, VALUE>> accessEntry) {
        AccessParameterized2<HOST, FuncMap.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> entrySpec = new AccessParameterized2<HOST, FuncMap.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>() {
    
            @Override
            public FuncMap.Entry<KEY, VALUE> applyUnsafe(HOST host) throws Exception {
                val map = mapAccessSpec.apply(host);
                val entry = accessEntry.apply(map);
                return entry;
            }
    
            @Override
            public KEYACCESS createSubAccessFromHost1(Function<HOST, KEY> accessToParameter) {
                return mapAccessSpec.createSubAccessFromHost1(accessToParameter);
            }
    
            @Override
            public VALUEACCESS createSubAccessFromHost2(Function<HOST, VALUE> accessToParameter) {
                return mapAccessSpec.createSubAccessFromHost2(accessToParameter);
            }
        };
        return entrySpec;
    }
}
