// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.lens.lenses;

import java.util.function.Function;
import functionalj.lens.core.AccessParameterized2;
import functionalj.tuple.ToTuple2Func;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface Tuple2Access<HOST, T1, T2, T1ACCESS extends AnyAccess<HOST, T1>, T2ACCESS extends AnyAccess<HOST, T2>> 
        extends 
            AccessParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1ACCESS, T2ACCESS>, 
            ConcreteAccess<HOST, Tuple2<T1, T2>, Tuple2Access<HOST, T1, T2, T1ACCESS, T2ACCESS>>, 
            ToTuple2Func<HOST, T1, T2> {
    
    public AccessParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1ACCESS, T2ACCESS> accessParameterized2();
    
    @Override
    public default Tuple2<T1, T2> applyUnsafe(HOST host) throws Exception {
        return accessParameterized2().apply(host);
    }
    
    @Override
    public default Tuple2Access<HOST, T1, T2, T1ACCESS, T2ACCESS> newAccess(Function<HOST, Tuple2<T1, T2>> access) {
        val accessParam = new AccessParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1ACCESS, T2ACCESS>() {
            
            @Override
            public Tuple2<T1, T2> applyUnsafe(HOST host) throws Exception {
                return access.apply(host);
            }
            
            @Override
            public T1ACCESS createSubAccessFromHost1(Function<HOST, T1> accessToParameter) {
                return Tuple2Access.this.createSubAccessFromHost1(accessToParameter);
            }
            
            @Override
            public T2ACCESS createSubAccessFromHost2(Function<HOST, T2> accessToParameter) {
                return Tuple2Access.this.createSubAccessFromHost2(accessToParameter);
            }
        };
        return () -> accessParam;
    }
    
    @Override
    public default T1ACCESS createSubAccess1(Function<Tuple2<T1, T2>, T1> accessToParameter) {
        return accessParameterized2().createSubAccess1(Tuple2::_1);
    }
    
    @Override
    public default T2ACCESS createSubAccess2(Function<Tuple2<T1, T2>, T2> accessToParameter) {
        return accessParameterized2().createSubAccess2(Tuple2::_2);
    }
    
    @Override
    default T1ACCESS createSubAccessFromHost1(Function<HOST, T1> accessToParameter) {
        return accessParameterized2().createSubAccessFromHost1(accessToParameter);
    }
    
    @Override
    default T2ACCESS createSubAccessFromHost2(Function<HOST, T2> accessToParameter) {
        return accessParameterized2().createSubAccessFromHost2(accessToParameter);
    }
    
    public default T1ACCESS _1() {
        return accessParameterized2().createSubAccess1(Tuple2::_1);
    }
    
    public default T2ACCESS _2() {
        return accessParameterized2().createSubAccess2(Tuple2::_2);
    }
    
    public default T1ACCESS first() {
        return _1();
    }
    
    public default T2ACCESS second() {
        return _2();
    }
}
