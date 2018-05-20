package functionalj.lens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func1;
import lombok.val;

@FunctionalInterface
public interface MapAccess<HOST, MAP extends Map<KEY, VALUE>, KEY, VALUE, 
                            KEYACCESS extends AnyAccess<HOST,KEY>, 
                            VALUEACCESS extends AnyAccess<HOST,VALUE>>
                    extends AccessParameterized2<HOST, MAP, KEY, VALUE, KEYACCESS, VALUEACCESS> {

    public AccessParameterized2<HOST, MAP, KEY, VALUE, KEYACCESS, VALUEACCESS> accessParameterized2();
    
    @Override
    public default MAP apply(HOST host) {
        return accessParameterized2().apply(host);
    }

    @Override
    public default KEYACCESS createSubAccess1(Function<MAP, KEY> accessToParameter) {
        return keyAccess(accessToParameter);
    }

    @Override
    public default VALUEACCESS createSubAccess2(Function<MAP, VALUE> accessToParameter) {
        return valueAccess(accessToParameter);
    }

    public default KEYACCESS keyAccess(Function<MAP, KEY> accessToParameter) {
        return accessParameterized2().createSubAccess1(accessToParameter);
    }

    public default VALUEACCESS valueAccess(Function<MAP, VALUE> accessToParameter) {
        return accessParameterized2().createSubAccess2(accessToParameter);
    }
    
    public default VALUEACCESS get(KEY key) {
        return valueAccess(map -> map.get(key));
    }
    public default VALUEACCESS getOrDefault(KEY key, VALUE defaultValue) {
        return valueAccess(map -> map.getOrDefault(key, defaultValue));
    }
    
    public default IntegerAccess<HOST> size() {
        return intAccess(0, map -> map.size());
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
    
    // L: put
    // L: filterMap
    
    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
            entries() {
        val spec        = accessParameterized2();
        val specWithSub = new AccessParameterized<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>() {
            @Override
            public Collection<Map.Entry<KEY, VALUE>> apply(HOST host) {
                return new ArrayList<>(spec.apply(host).entrySet());
            }
            @Override
            public MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>
                    createSubAccess(Function<Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>> accessToSub) {
                Func1<MAP, Map.Entry<KEY, VALUE>> accessEntry = Func1.of((MAP map) -> {
                    val entrySet    = map.entrySet();
                    val keyAccess = accessToSub.apply(entrySet);
                    return keyAccess;
                });
                AccessParameterized2<HOST, Map.Entry<KEY,VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> entrySpec = new AccessParameterized2<HOST, Map.Entry<KEY,VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>() {
                    @Override
                    public Entry<KEY, VALUE> apply(HOST host) {
                        val map   = spec.apply(host);
                        val entry = accessEntry.apply(map);
                        return entry;
                    }
                    
                    @Override
                    public KEYACCESS createSubAccess1(Function<Entry<KEY, VALUE>, KEY> accessToKey) {
                        Func1<MAP, KEY> keyAccess = Func1.of((MAP map) -> {
                            val entry = accessEntry.apply(map);
                            val key   = accessToKey.apply(entry);
                            return key;
                        });
                        return spec.createSubAccess1(keyAccess);
                    }
                    
                    @Override
                    public VALUEACCESS createSubAccess2(Function<Entry<KEY, VALUE>, VALUE> accessToValue) {
                        Func1<MAP, VALUE> valueAccess = Func1.of((MAP map) -> {
                            val entry = accessEntry.apply(map);
                            val value = accessToValue.apply(entry);
                            return value;
                        });
                        return spec.createSubAccess2(valueAccess);
                    }
                };
                return  () -> entrySpec;
            }
        };
        return () -> specWithSub;
    }
    
    public default CollectionAccess<HOST, Collection<KEY>, KEY, KEYACCESS> keys() {
        val spec        = accessParameterized2();
        val specWithSub = new AccessParameterized<HOST, Collection<KEY>, KEY, KEYACCESS>() {
            @Override
            public Collection<KEY> apply(HOST host) {
                return spec.apply(host).keySet();
            }
            @Override
            public KEYACCESS createSubAccess(Function<Collection<KEY>, KEY> accessToSub) {
                Func1<MAP, KEY> access = Func1.of((MAP map) -> {
                    val keySet    = map.keySet();
                    val keyAccess = accessToSub.apply(keySet);
                    return keyAccess;
                });
                return spec.createSubAccess1(access);
            }
        };
        return () -> specWithSub;
    }
    
    public default CollectionAccess<HOST, Collection<VALUE>, VALUE, VALUEACCESS> values() {
        val spec        = accessParameterized2();
        val specWithSub = new AccessParameterized<HOST, Collection<VALUE>, VALUE, VALUEACCESS>() {
            @Override
            public Collection<VALUE> apply(HOST host) {
                return spec.apply(host).values();
            }
            @Override
            public VALUEACCESS createSubAccess(Function<Collection<VALUE>, VALUE> accessToSub) {
                Func1<MAP, VALUE> access = Func1.of((MAP map) -> {
                    val values      = map.values();
                    val valueAccess = accessToSub.apply(values);
                    return valueAccess;
                });
                return spec.createSubAccess2(access);
            }
        };
        return () -> specWithSub;
    }
    
}
