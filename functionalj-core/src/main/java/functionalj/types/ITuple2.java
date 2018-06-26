package functionalj.types;

import java.util.function.Function;

import functionalj.lens.AccessParameterized2;
import functionalj.lens.AnyAccess;

public interface ITuple2<T1, T2> {

    public T1 _1();
    public T2 _2();
    
    
    
    public static interface ITuple2Access<HOST, _1, _2, 
                            _1ACCESS extends AnyAccess<HOST,_1>, 
                            _2ACCESS extends AnyAccess<HOST,_2>>
            extends AccessParameterized2<HOST, Tuple2<_1, _2>, _1, _2, _1ACCESS, _2ACCESS> {

        public AccessParameterized2<HOST, Tuple2<_1, _2>, _1, _2, _1ACCESS, _2ACCESS> accessParameterized2();
        
        @Override
        public default Tuple2<_1, _2> apply(HOST host) {
            return accessParameterized2().apply(host);
        }

        @Override
        public default _1ACCESS createSubAccess1(Function<Tuple2<_1, _2>, _1> accessToParameter) {
            return accessParameterized2().createSubAccess1(Tuple2::_1);
        }

        @Override
        public default _2ACCESS createSubAccess2(Function<Tuple2<_1, _2>, _2> accessToParameter) {
            return accessParameterized2().createSubAccess2(Tuple2::_2);
        }

        @Override
        default _1ACCESS createSubAccessFromHost1(Function<HOST, _1> accessToParameter) {
            return accessParameterized2().createSubAccessFromHost1(accessToParameter);
        }

        @Override
        default _2ACCESS createSubAccessFromHost2(Function<HOST, _2> accessToParameter) {
            return accessParameterized2().createSubAccessFromHost2(accessToParameter);
        }
        
        public default _1ACCESS _1() {
            return accessParameterized2().createSubAccess1(Tuple2::_1);
        }
        public default _2ACCESS _2() {
            return accessParameterized2().createSubAccess2(Tuple2::_2);
        }
    }
    
}
