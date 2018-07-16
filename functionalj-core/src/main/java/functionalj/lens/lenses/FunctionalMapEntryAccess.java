package functionalj.lens.lenses;

import java.util.function.Function;

import functionalj.lens.core.AccessParameterized2;
import tuple.Tuple2;

// TODO - See if this can be shared with Tuple2Lens.

@FunctionalInterface
public interface FunctionalMapEntryAccess<HOST, MAPENTRY extends Tuple2<KEY, VALUE>, KEY, VALUE, 
                                KEYACCESS extends AnyAccess<HOST,KEY>, 
                                VALUEACCESS extends AnyAccess<HOST,VALUE>>
                    extends AccessParameterized2<HOST, MAPENTRY, KEY, VALUE, KEYACCESS, VALUEACCESS> {

    public AccessParameterized2<HOST, MAPENTRY, KEY, VALUE, KEYACCESS, VALUEACCESS> accessParameterized2();
    
    @Override
    public default MAPENTRY applyUnsafe(HOST host) throws Exception {
        return accessParameterized2().apply(host);
    }
    
    @Override
    public default KEYACCESS createSubAccess1(Function<MAPENTRY, KEY> accessToParameter) {
        return accessParameterized2().createSubAccess1(accessToParameter);
    }
    
    @Override
    public default VALUEACCESS createSubAccess2(Function<MAPENTRY, VALUE> accessToParameter) {
        return accessParameterized2().createSubAccess2(accessToParameter);
    }
    
    @Override
    default KEYACCESS createSubAccessFromHost1(Function<HOST, KEY> accessToParameter) {
        return accessParameterized2().createSubAccessFromHost1(accessToParameter);
    }
    
    @Override
    default VALUEACCESS createSubAccessFromHost2(Function<HOST, VALUE> accessToParameter) {
        return accessParameterized2().createSubAccessFromHost2(accessToParameter);
    }
    
    public default KEYACCESS key() {
        return accessParameterized2().createSubAccess1((MAPENTRY mapEntry) -> {
            if (mapEntry == null)
                return null;
            return mapEntry._1();
        });
    }

    public default VALUEACCESS value() {
        return accessParameterized2().createSubAccess2((MAPENTRY mapEntry) -> {
            if (mapEntry == null)
                return null;
            return mapEntry._2();
        });
    }
    
}
