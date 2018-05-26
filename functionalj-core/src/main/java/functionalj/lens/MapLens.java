package functionalj.lens;

import static functionalj.lens.Lenses.createMapLensSpec;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func1;
import functionalj.types.Tuple2;
import lombok.val;

public interface MapLens<HOST, KEY, VALUE, 
                            KEYLENS   extends Lens<HOST,KEY>, 
                            VALUELENS extends Lens<HOST,VALUE>>
                    extends
                        ObjectLens<HOST, Map<KEY, VALUE>>,
                        MapAccess<HOST, KEY, VALUE, KEYLENS, VALUELENS> {
    
    public static <HOST, KEY, VALUE, KEYLENS extends Lens<HOST,KEY>, VALUELENS extends Lens<HOST,VALUE>>
            MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(
                    Function<HOST,  Map<KEY, VALUE>>           read,
                    WriteLens<HOST, Map<KEY, VALUE>>           write,
                    Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                    Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val spec = createMapLensSpec(read, write, keyLensCreator, valueLensCreator);    
        return ()->spec;
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
    public default Map<KEY, VALUE> apply(HOST host) {
        return ObjectLens.super.apply(host);
    }
    
    public default VALUELENS get(KEY key) {
        WriteLens<Map<KEY, VALUE>, VALUE> write = (map, value) -> {
            val newMap = new LinkedHashMap<KEY, VALUE>();
            newMap.putAll(map);
            newMap.put(key, value);
            return newMap;
        };
        Function<Map<KEY, VALUE>, VALUE> read = map -> map.get(key);
        return Lenses.createSubLens(this, 
                read,
                write,
                lensSpecParameterized2()::createSubLens2);
    }
    
    public default Function<HOST, HOST> selectiveMap(Predicate<KEY> checker, Function<VALUE, VALUE> mapper) {
        val mapEntry = Func1.of((Map.Entry<KEY, VALUE> each) ->{
            val key   = each.getKey();
            val value = each.getValue();
            if (!checker.test(key)) 
                return each;
            
            val newValue = mapper.apply(value);
            return (Map.Entry<KEY, VALUE>)new Tuple2<KEY, VALUE>(key, newValue);
        });
        
        return host -> {
            val newMap = new LinkedHashMap<KEY, VALUE>();
            apply(host).entrySet().stream()
                    .map    (mapEntry)
                    .forEach(entry -> {
                        val key   = entry.getKey();
                        val value = entry.getValue();
                        if (!checker.test(key)) 
                            newMap.put(key, value);
                        else {
                            val newValue = mapper.apply(value);
                            newMap.put(key, newValue);
                        }
                    });
            val newHost = apply(host, newMap);
            return newHost;
        };
    }

    
    public default Function<HOST, HOST> selectiveMap(BiPredicate<KEY, VALUE> checker, Function<VALUE, VALUE> mapper) {
        val mapEntry = Func1.of((Map.Entry<KEY, VALUE> each) ->{
            val key   = each.getKey();
            val value = each.getValue();
            if (!checker.test(key, value)) 
                return each;
            
            val newValue = mapper.apply(value);
            return (Map.Entry<KEY, VALUE>)new Tuple2<KEY, VALUE>(key, newValue);
        });
        
        return host -> {
            val newMap = new LinkedHashMap<KEY, VALUE>();
            apply(host).entrySet().stream()
                    .map    (mapEntry)
                    .forEach(entry -> {
                        val key   = entry.getKey();
                        val value = entry.getValue();
                        if (!checker.test(key, value)) 
                            newMap.put(key, value);
                        else {
                            val newValue = mapper.apply(value);
                            newMap.put(key, newValue);
                        }
                    });
            val newHost = apply(host, newMap);
            return newHost;
        };
    }
}
