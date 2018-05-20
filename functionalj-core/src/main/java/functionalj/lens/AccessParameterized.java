package functionalj.lens;

import java.util.function.Function;

public interface AccessParameterized<HOST, TYPE, PARAMETER, PARAMETERACCESS extends AnyAccess<HOST, PARAMETER>>
                extends AnyAccess<HOST, TYPE> {
    
    public TYPE apply(HOST host);
    
    public PARAMETERACCESS createSubAccess(Function<TYPE, PARAMETER> accessToParameter);
    
//    
//    public default NullableAccess<HOST, PARAMETER, PARAMETERACCESS> createNullableParameterAccess(Function<TYPE, PARAMETER> getElement) {
//        val accessWithSub = new AccessParameterized<HOST, Nullable<PARAMETER>, PARAMETER, PARAMETERACCESS>() {
//            @Override
//            public Nullable<PARAMETER> apply(HOST host) {
//                val list    = AccessParameterized.this.apply(host);
//                val element = getElement.apply(list);
//                return Nullable.of(element);
//            }
//            @Override
//            public PARAMETERACCESS createSubAccess(Function<Nullable<PARAMETER>, PARAMETER> accessToSub) {
//                return AccessParameterized.this.createSubAccess((Function<TYPE, PARAMETER>)getElement);
//            }
//        };
//        return ()->accessWithSub;
//    }
}
