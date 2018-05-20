package functionalj.lens;

import java.util.function.Function;

public interface AccessParameterized2<HOST, TYPE, PARAMETER1, PARAMETER2,
                                        PARAMETERACCESS1 extends AnyAccess<HOST, PARAMETER1>,
                                        PARAMETERACCESS2 extends AnyAccess<HOST, PARAMETER2>>
                 extends AnyAccess<HOST, TYPE> {

    
    public TYPE apply(HOST host);
    
    public PARAMETERACCESS1 createSubAccess1(Function<TYPE, PARAMETER1> accessToParameter);
    
    public PARAMETERACCESS2 createSubAccess2(Function<TYPE, PARAMETER2> accessToParameter);
    
}
