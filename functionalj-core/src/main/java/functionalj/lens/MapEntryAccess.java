package functionalj.lens;

import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface MapEntryAccess<HOST, MAPENTRY extends Map.Entry<KEY, VALUE>, KEY, VALUE, 
                                KEYACCESS extends AnyAccess<HOST,KEY>, 
                                VALUEACCESS extends AnyAccess<HOST,VALUE>>
                    extends AccessParameterized2<HOST, MAPENTRY, KEY, VALUE, KEYACCESS, VALUEACCESS> {

    public AccessParameterized2<HOST, MAPENTRY, KEY, VALUE, KEYACCESS, VALUEACCESS> accessParameterized2();
    
    @Override
    public default MAPENTRY apply(HOST host) {
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
            return mapEntry.getKey();
        });
    }

    public default VALUEACCESS value() {
        return accessParameterized2().createSubAccess2((MAPENTRY mapEntry) -> {
            if (mapEntry == null)
                return null;
            return mapEntry.getValue();
        });
    }
    
}
