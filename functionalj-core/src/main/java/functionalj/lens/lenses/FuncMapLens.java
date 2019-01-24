package functionalj.lens.lenses;

import java.util.function.Function;

import functionalj.lens.core.AccessParameterized2;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized2;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.map.FuncMap;
import lombok.val;

@SuppressWarnings("javadoc")
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
        val spec = LensUtils.createFuncMapLensSpec(read, write, keyLensCreator, valueLensCreator);    
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
    
    // TODO - Fix this.
    
    public default VALUELENS get(KEY key) {
        WriteLens<FuncMap<KEY, VALUE>, VALUE> write = (map, value) -> {
            val newMap = map.with(key, value);
            return newMap;
        };
        Function<FuncMap<KEY, VALUE>, VALUE> read = map -> {
            return map.get(key);
        };
        return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens2);
    }
    
//    public default Function<HOST, HOST> changeTo(Predicate<KEY> checker, Function<VALUE, VALUE> mapper) {
//        val mapEntry = Func1.of((Map.Entry<KEY, VALUE> each) ->{
//            val key   = each.getKey();
//            val value = each.getValue();
//            if (!checker.test(key)) 
//                return each;
//            
//            val newValue = mapper.apply(value);
//            return (Map.Entry<KEY, VALUE>)new ImmutableTuple2<KEY, VALUE>(key, newValue);
//        });
//        
//        val newMap = new LinkedHashMap<KEY, VALUE>();
//        Consumer<? super Entry<KEY, VALUE>> transformEntry = entry -> {
//            val key   = entry.getKey();
//            val value = entry.getValue();
//            if (!checker.test(key)) 
//                newMap.put(key, value);
//            else {
//                val newValue = mapper.apply(value);
//                newMap.put(key, newValue);
//            }
//        };
//        return host -> {
//            apply(host).entrySet().stream()
//                    .map    (mapEntry)
//                    .forEach(transformEntry);
//            val newHost = apply(host, newMap);
//            return newHost;
//        };
//    }
//
//    
//    public default Function<HOST, HOST> changeTo(BiPredicate<KEY, VALUE> checker, Function<VALUE, VALUE> mapper) {
//        val mapEntry = Func1.of((Map.Entry<KEY, VALUE> each) ->{
//            val key   = each.getKey();
//            val value = each.getValue();
//            if (!checker.test(key, value)) 
//                return each;
//            
//            val newValue = mapper.apply(value);
//            return (Map.Entry<KEY, VALUE>)new ImmutableTuple2<KEY, VALUE>(key, newValue);
//        });
//        
//        return host -> {
//            val newMap = new LinkedHashMap<KEY, VALUE>();
//            Consumer<? super Entry<KEY, VALUE>> transformEntry = entry -> {
//                val key   = entry.getKey();
//                val value = entry.getValue();
//                if (!checker.test(key, value)) 
//                    newMap.put(key, value);
//                else {
//                    val newValue = mapper.apply(value);
//                    newMap.put(key, newValue);
//                }
//            };
//            apply(host).entrySet().stream()
//                    .map    (mapEntry)
//                    .forEach(transformEntry);
//            val newHost = apply(host, newMap);
//            return newHost;
//        };
//    }
}
