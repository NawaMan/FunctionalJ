// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens;

import static functionalj.lens.Access.theItem;
import static functionalj.lens.Access.theString;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.Test;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.lenses.ListAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

public class ListAccessTest {
    
    public class WithNames {
        private List<String> names = new ArrayList<>();
        
        public WithNames(List<String> names) {
            this.names.addAll(names);
        }
        
        public List<String> names() {
            return names;
        }
        
        public WithNames withNames(List<String> newNames) {
            return new WithNames(newNames);
        }
    }
    
    @Test
    public void testListAccess() {
        val accSub = new AccessParameterized<WithNames, List<String>, String, StringAccess<WithNames>>() {
            @Override
            public List<String> applyUnsafe(WithNames input) throws Exception {
                return input.names();
            }
            
            @Override
            public StringAccess<WithNames> createSubAccessFromHost(Function<WithNames, String> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        val listAcc = new ListAccess<WithNames, String, StringAccess<WithNames>>() {
            @Override
            public AccessParameterized<WithNames, List<String>, String, StringAccess<WithNames>> accessParameterized() {
                return accSub;
            }
        };
        assertEquals("One",        listAcc.first().apply(new WithNames(asList("One", "Two"))));
        assertEquals("One",        Optional.of(new WithNames(asList("One", "Two")))                 .map(listAcc.first()).get());
        assertEquals("[One, Two]", Optional.of(new WithNames(asList("One", "Two", "Three", "Four"))).map(listAcc.filter(theString.length().thatEquals(3))).map(theItem().asString()).get());
        assertEquals("ONE",        Optional.of(new WithNames(asList("One", "Two")))                 .map(listAcc.first())                                 .map(theString.toUpperCase()).get());
    }
}
