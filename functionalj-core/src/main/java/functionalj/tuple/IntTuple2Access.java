package functionalj.tuple;

import java.util.function.Function;

import functionalj.lens.core.AccessParameterized;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.IntegerAccess;

@FunctionalInterface
public interface IntTuple2Access<HOST, T2, T2ACCESS extends AnyAccess<HOST,T2>>
        extends AccessParameterized<HOST, IntTuple2<T2>, T2, T2ACCESS> {

    public AccessParameterized<HOST, IntTuple2<T2>, T2, T2ACCESS> accessParameterized();
    
    @Override
    public default IntTuple2<T2> applyUnsafe(HOST host) throws Exception {
        return accessParameterized().apply(host);
    }
    
    @Override
    public default T2ACCESS createSubAccess(Function<IntTuple2<T2>, T2> accessToParameter) {
        return accessParameterized().createSubAccess(IntTuple2::_2);
    }

    @Override
    public default T2ACCESS createSubAccessFromHost(Function<HOST, T2> accessToParameter) {
        return accessParameterized().createSubAccessFromHost(accessToParameter);
    }
    
    public default IntegerAccess<HOST> _1() {
        return intAccess(0, IntTuple2::_1);
    }
    public default T2ACCESS T2() {
        return accessParameterized().createSubAccess(IntTuple2::_2);
    }
    
}