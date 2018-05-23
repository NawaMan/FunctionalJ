package functionalj.lens;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

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

    public static <KEYLENS extends Lens<HOST, KEY>, HOST, VALUELENS extends Lens<HOST, VALUE>, KEY, VALUE>
            LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createMapLensSpec(
                    Function<HOST,  Map<KEY, VALUE>>           read,
                    WriteLens<HOST, Map<KEY, VALUE>>           write,
                    Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                    Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return new LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS>() {

            @Override
            public LensSpec<HOST, Map<KEY, VALUE>> getSpec() {
                return LensSpec.of(read, write);
            }

            @Override
            public KEYLENS createSubLens1(
                    LensSpec<HOST, KEY> subSpec) {
                return keyLensCreator.apply(subSpec);
            }

            @Override
            public VALUELENS createSubLens2(
                    LensSpec<HOST, VALUE> subSpec) {
                return valueLensCreator.apply(subSpec);
            }
        };
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
    
}
