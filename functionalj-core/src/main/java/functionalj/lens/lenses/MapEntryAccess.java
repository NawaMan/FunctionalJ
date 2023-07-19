// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Map;
import java.util.function.Function;
import functionalj.lens.core.AccessParameterized2;

@FunctionalInterface
public interface MapEntryAccess<HOST, MAPENTRY extends Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS extends AnyAccess<HOST, KEY>, VALUEACCESS extends AnyAccess<HOST, VALUE>> extends AccessParameterized2<HOST, MAPENTRY, KEY, VALUE, KEYACCESS, VALUEACCESS> {
    
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
