package functionalj.lens.core;

import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import lombok.val;

public interface AccessParameterized2<HOST, TYPE, PARAMETER1, PARAMETER2,
                                        PARAMETERACCESS1 extends AnyAccess<HOST, PARAMETER1>,
                                        PARAMETERACCESS2 extends AnyAccess<HOST, PARAMETER2>>
                 extends AnyAccess<HOST, TYPE> {

    
    public TYPE applyUnsafe(HOST host) throws Exception;
    
    
    public PARAMETERACCESS1 createSubAccessFromHost1(Function<HOST, PARAMETER1> accessToParameter);
    
    public PARAMETERACCESS2 createSubAccessFromHost2(Function<HOST, PARAMETER2> accessToParameter);
    
    public default PARAMETERACCESS1 createSubAccess1(Function<TYPE, PARAMETER1> accessToParameter) {
        return createSubAccessFromHost1(host -> {
            val list  = apply(host);
            val value = accessToParameter.apply(list);
            return value;
        });
    }
    
    public default PARAMETERACCESS2 createSubAccess2(Function<TYPE, PARAMETER2> accessToParameter) {
        return createSubAccessFromHost2(host -> {
            val list  = apply(host);
            val value = accessToParameter.apply(list);
            return value;
        });
    }
    
}
