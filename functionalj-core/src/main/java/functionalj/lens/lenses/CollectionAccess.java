// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import functionalj.lens.core.AccessParameterized;
import lombok.val;


@FunctionalInterface
public interface CollectionAccess<HOST, COLLECTION extends Collection<TYPE>, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
        extends ObjectAccess<HOST, COLLECTION>, AccessParameterized<HOST, COLLECTION, TYPE, AnyAccess<HOST,TYPE>> {
    
    public AccessParameterized<HOST, COLLECTION, TYPE, SUBACCESS> accessParameterized();
    
    @Override
    public default COLLECTION applyUnsafe(HOST host) throws Exception {
        return accessParameterized().applyUnsafe(host);
    }
    
    @Override
    public default SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToSub) {
        return accessParameterized().createSubAccessFromHost(accessToSub);
    }
    
    public default IntegerAccess<HOST> size() {
        return intPrimitiveAccess(0, collection -> collection.size());
    }
    
    public default BooleanAccess<HOST> thatIsEmpty() {
        return booleanAccess(false, collection -> collection.isEmpty());
    }
    
    public default BooleanAccess<HOST> thatIsNotEmpty() {
        return booleanAccess(false, collection -> !collection.isEmpty());
    }
    
    public default BooleanAccess<HOST> thatContains(TYPE value) {
        return booleanAccess(false, collection -> collection.contains(value));
    }
    
    public default BooleanAccess<HOST> thatContains(Predicate<TYPE> check) {
        return booleanAccess(false, collection -> collection.stream().anyMatch(check));
    }
    
    public default CollectionAccess<HOST, COLLECTION, TYPE, SUBACCESS> filter(Predicate<TYPE> checker) {
        val spec        = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, COLLECTION, TYPE, SUBACCESS>() {
            @SuppressWarnings("unchecked")
            @Override
            public COLLECTION applyUnsafe(HOST host) throws Exception {
                return (COLLECTION)spec.apply(host).stream().filter(checker).collect(Collectors.toList());
            }
            @Override
            public SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return spec.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    public default ListAccess<HOST, TYPE, SUBACCESS> toList() {
        val spec        = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, List<TYPE>, TYPE, SUBACCESS>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<TYPE> applyUnsafe(HOST host) throws Exception{
                val collection = spec.apply(host);
                if (collection  instanceof List)
                    return (List<TYPE>)collection;
                
                return new ArrayList<TYPE>(collection);
            }
            @Override
            public SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return spec.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
}
