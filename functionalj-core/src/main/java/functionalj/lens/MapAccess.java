package functionalj.lens;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

import functionalj.functions.Func1;
import lombok.val;

@FunctionalInterface
public interface MapAccess<HOST, KEY, VALUE, 
                            KEYACCESS extends AnyAccess<HOST,KEY>, 
                            VALUEACCESS extends AnyAccess<HOST,VALUE>>
                    extends AccessParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> {

    public AccessParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> accessParameterized2();
    
    @Override
    public default Map<KEY, VALUE> apply(HOST host) {
        return accessParameterized2().apply(host);
    }

    @Override
    public default KEYACCESS createSubAccess1(Function<Map<KEY, VALUE>, KEY> accessToParameter) {
        return keyAccess(accessToParameter);
    }

    @Override
    public default VALUEACCESS createSubAccess2(Function<Map<KEY, VALUE>, VALUE> accessToParameter) {
        return valueAccess(accessToParameter);
    }

    public default KEYACCESS keyAccess(Function<Map<KEY, VALUE>, KEY> accessToParameter) {
        return accessParameterized2().createSubAccess1(accessToParameter);
    }

    public default VALUEACCESS valueAccess(Function<Map<KEY, VALUE>, VALUE> accessToParameter) {
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
    
    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>,
                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
            entries() {
        val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map -> map.entrySet());
        return () -> entryCollectionSpec;
    }
    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, 
                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
            filterEntries(Predicate<Map.Entry<KEY, VALUE>> entryPredicate) {
        val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map->{
            return map.entrySet().stream().filter(entryPredicate).collect(toSet());
        });
        return () -> entryCollectionSpec;
    }
    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, 
                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
            filterEntries(BiPredicate<KEY, VALUE> entryBiPredicate) {
        val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map->{
            return map.entrySet().stream()
                    .filter(entry -> entryBiPredicate.test(entry.getKey(), entry.getValue()))
                    .collect(toSet());
        });
        return () -> entryCollectionSpec;
    }
    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, 
                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
            filter(Predicate<KEY> keyPredicate) {
        val entryCollectionSpec = Helper.createEntryCollectionSpec(accessParameterized2(), map->{
            return map.entrySet().stream()
                    .filter(entry->keyPredicate.test(entry.getKey()))
                    .collect(toSet());
        });
        return () -> entryCollectionSpec;
    }
    
    public default CollectionAccess<HOST, Collection<KEY>, KEY, KEYACCESS> keys() {
        val keyCollectionSpec = Helper.createKeyCollectionSpec(accessParameterized2(), Map::keySet);
        return () -> keyCollectionSpec;
    }
    
    public default CollectionAccess<HOST, Collection<VALUE>, VALUE, VALUEACCESS> values() {
        val valueCollectionSpec = Helper.createValueCollectionSpec(accessParameterized2(), Map::values);
        return () -> valueCollectionSpec;
    }
    
    public static class Helper {
        
        public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST,KEY>, VALUEACCESS extends AnyAccess<HOST,VALUE>>
            AccessParameterized<HOST, Collection<KEY>, KEY, KEYACCESS> createKeyCollectionSpec(
                    AccessParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> spec,
                    Function<Map<KEY, VALUE>, Collection<KEY>> getKeys) {
            return new AccessParameterized<HOST, Collection<KEY>, KEY, KEYACCESS>() {
                @Override
                public Collection<KEY> apply(HOST host) {
                    return getKeys.apply(spec.apply(host));
                }
                @Override
                public KEYACCESS createSubAccess(Function<Collection<KEY>, KEY> accessToKey) {
                    return spec.createSubAccess1(map -> {
                        val keySet    = getKeys.apply(map);
                        val keyAccess = accessToKey.apply(keySet);
                        return keyAccess;
                    });
                }
            };
        }

        public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST,KEY>, VALUEACCESS extends AnyAccess<HOST,VALUE>>
            AccessParameterized<HOST, Collection<VALUE>, VALUE, VALUEACCESS> createValueCollectionSpec(
                    AccessParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> spec,
                    Function<Map<KEY, VALUE>, Collection<VALUE>> getValues) {
            return new AccessParameterized<HOST, Collection<VALUE>, VALUE, VALUEACCESS>() {
                @Override
                public Collection<VALUE> apply(HOST host) {
                    return getValues.apply(spec.apply(host));
                }
                @Override
                public VALUEACCESS createSubAccess(Function<Collection<VALUE>, VALUE> accessToValue) {
                    return spec.createSubAccess2(map -> {
                        val values      = getValues.apply(map);
                        val valueAccess = accessToValue.apply(values);
                        return valueAccess;
                    });
                }
            };
        }

        public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST,KEY>, VALUEACCESS extends AnyAccess<HOST,VALUE>>
            AccessParameterized<HOST, Collection<Entry<KEY, VALUE>>, Entry<KEY, VALUE>, MapEntryAccess<HOST, Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>
            createEntryCollectionSpec(
                    AccessParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> spec,
                    Function<Map<KEY, VALUE>, Collection<Map.Entry<KEY, VALUE>>> accessEntrySet) {
            return new AccessParameterized<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>,
                                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>() {
                @Override
                public Collection<Map.Entry<KEY, VALUE>> apply(HOST host) {
                    return accessEntrySet.apply(spec.apply(host));
                }
                @Override
                public MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>
                        createSubAccess(Function<Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>> accessToSub) {
                    val entrySpec = createEntrySpec(spec, (Map<KEY, VALUE> map) -> {
                        val keyAccess = accessToSub.apply(accessEntrySet.apply(map));
                        return keyAccess;
                    });
                    return  () -> entrySpec;
                }
            };
        }

        public static <HOST, KEY, VALUE, KEYACCESS extends AnyAccess<HOST,KEY>, VALUEACCESS extends AnyAccess<HOST,VALUE>>
            AccessParameterized2<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> createEntrySpec(
                AccessParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> mapAccessSpec,
                Func1<Map<KEY, VALUE>, Map.Entry<KEY, VALUE>>                                   accessEntry) {
            AccessParameterized2<HOST, Map.Entry<KEY,VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS> entrySpec
            = new AccessParameterized2<HOST, Map.Entry<KEY,VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>() {
                @Override
                public Entry<KEY, VALUE> apply(HOST host) {
                    val map   = mapAccessSpec.apply(host);
                    val entry = accessEntry.apply(map);
                    return entry;
                }
                
                @Override
                public KEYACCESS createSubAccess1(Function<Entry<KEY, VALUE>, KEY> accessToKey) {
                    return mapAccessSpec.createSubAccess1(map -> {
                        val entry = accessEntry.apply(map);
                        val key   = accessToKey.apply(entry);
                        return key;
                    });
                }
                
                @Override
                public VALUEACCESS createSubAccess2(Function<Entry<KEY, VALUE>, VALUE> accessToValue) {
                    return mapAccessSpec.createSubAccess2(map -> {
                        val entry = accessEntry.apply(map);
                        val value = accessToValue.apply(entry);
                        return value;
                    });
                }
            };
            return entrySpec;
        }
    }
    
}
