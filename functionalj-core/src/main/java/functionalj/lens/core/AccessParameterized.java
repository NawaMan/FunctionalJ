package functionalj.lens.core;

import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import lombok.val;

public interface AccessParameterized<HOST, TYPE, PARAMETER, PARAMETERACCESS extends AnyAccess<HOST, PARAMETER>>
                extends AnyAccess<HOST, TYPE> {
    
    public TYPE apply(HOST host);
    
    public PARAMETERACCESS createSubAccessFromHost(Function<HOST, PARAMETER> accessToParameter);
    
    public default PARAMETERACCESS createSubAccess(Function<TYPE, PARAMETER> accessToParameter) {
        return createSubAccessFromHost(host -> {
            val list  = apply(host);
            val value = accessToParameter.apply(list);
            return value;
        });
    }
    
}
