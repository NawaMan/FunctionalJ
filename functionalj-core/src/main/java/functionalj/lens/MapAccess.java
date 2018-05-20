package functionalj.lens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

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
        return accessParameterized2().createSubAccess1(accessToParameter);
    }

    @Override
    public default VALUEACCESS createSubAccess2(Function<MAP, VALUE> accessToParameter) {
        return accessParameterized2().createSubAccess2(accessToParameter);
    }
    
    public default VALUEACCESS get(KEY key) {
        val accessToValue = Func1.of((MAP map)->{
            return (VALUE)map.get(key);
        });
        return createSubAccess2(accessToValue);
    }
    public default VALUEACCESS getOrDefault(KEY key, VALUE defaultValue) {
        val accessToValue = Func1.of((MAP map)->{
            return (VALUE)map.getOrDefault(key, defaultValue);
        });
        return createSubAccess2(accessToValue);
    }
    
    public default ListAccess<HOST, List<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
            entries() {
        val spec        = accessParameterized2();
        val specWithSub = new AccessParameterized<HOST, List<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>>() {
            @Override
            public List<Map.Entry<KEY, VALUE>> apply(HOST host) {
                return new ArrayList<>(spec.apply(host).entrySet());
            }
            @Override
            public MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>
                    createSubAccess(Function<List<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>> accessToSub) {
                Func1<MAP, Map.Entry<KEY, VALUE>> accessEntry = Func1.of((MAP map) -> {
                    val entrySet    = map.entrySet();
                    val newList   = new ArrayList<Map.Entry<KEY, VALUE>>(entrySet);
                    val keyAccess = accessToSub.apply(newList);
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
                        val keyAccess = Func1.of((MAP map) -> {
                            val entry = accessEntry.apply(map);
                            val key   = accessToKey.apply(entry);
                            return key;
                        });
                        return spec.createSubAccess1(keyAccess);
                    }
                    
                    @Override
                    public VALUEACCESS createSubAccess2(Function<Entry<KEY, VALUE>, VALUE> accessToValue) {
                        val valueAccess = Func1.of((MAP map) -> {
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
    
    public default ListAccess<HOST, List<KEY>, KEY, KEYACCESS> keys() {
        val spec        = accessParameterized2();
        val specWithSub = new AccessParameterized<HOST, List<KEY>, KEY, KEYACCESS>() {
            @Override
            public List<KEY> apply(HOST host) {
                return new ArrayList<>(spec.apply(host).keySet());
            }
            @Override
            public KEYACCESS createSubAccess(Function<List<KEY>, KEY> accessToSub) {
                Func1<MAP, KEY> access = Func1.of((MAP map) -> {
                    val keySet    = map.keySet();
                    val newList   = new ArrayList<KEY>(keySet); // TODO - Find the way to deal with this.
                    val keyAccess = accessToSub.apply(newList);
                    return keyAccess;
                });
                return spec.createSubAccess1(access);
            }
        };
        return () -> specWithSub;
    }
    
    public default ListAccess<HOST, List<VALUE>, VALUE, VALUEACCESS> values() {
        val spec        = accessParameterized2();
        val specWithSub = new AccessParameterized<HOST, List<VALUE>, VALUE, VALUEACCESS>() {
            @Override
            public List<VALUE> apply(HOST host) {
                return new ArrayList<>(spec.apply(host).values());
            }
            @Override
            public VALUEACCESS createSubAccess(Function<List<VALUE>, VALUE> accessToSub) {
                Func1<MAP, VALUE> access = Func1.of((MAP map) -> {
                    val values      = map.values();
                    val newList     = new ArrayList<VALUE>(values);
                    val valueAccess = accessToSub.apply(newList);
                    return valueAccess;
                });
                return spec.createSubAccess2(access);
            }
        };
        return () -> specWithSub;
    }
    
}
