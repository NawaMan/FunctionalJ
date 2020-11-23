// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.Test;

import functionalj.lens.core.AccessParameterized2;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.MapAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;


public class MapAccessTest {
    
    @Test
    public void testMapAccess() {
        val mapAccess = new MapAccess<
                                Map<String, Integer>, String, Integer, 
                                StringAccess<Map<String, Integer>>, IntegerAccess<Map<String, Integer>>>() {
            @Override
            public AccessParameterized2<Map<String, Integer>, Map<String, Integer>, String, Integer, StringAccess<Map<String, Integer>>, IntegerAccess<Map<String, Integer>>> accessParameterized2() {
                return new AccessParameterized2<Map<String,Integer>, Map<String,Integer>, String, Integer, StringAccess<Map<String,Integer>>, IntegerAccess<Map<String,Integer>>>() {
                    @Override
                    public Map<String, Integer> applyUnsafe(Map<String, Integer> host) throws Exception {
                        return host;
                    }
                    @Override
                    public StringAccess<Map<String, Integer>> createSubAccess1(
                            Function<Map<String, Integer>, String> accessToParameter) {
                        return map -> accessToParameter.apply(map);
                    }
                    @Override
                    public IntegerAccess<Map<String, Integer>> createSubAccess2(
                            Function<Map<String, Integer>, Integer> accessToParameter) {
                        return IntegerAccess.of(accessToParameter);
                    }
                    @Override
                    public StringAccess<Map<String, Integer>> createSubAccessFromHost1(
                            Function<Map<String, Integer>, String> accessToParameter) {
                        return accessToParameter::apply;
                    }
                    @Override
                    public IntegerAccess<Map<String, Integer>> createSubAccessFromHost2(
                            Function<Map<String, Integer>, Integer> accessToParameter) {
                        return IntegerAccess.of(accessToParameter);
                    }
                };
            }
        };
        
        val map = new LinkedHashMap<String, Integer>();
        map.put("name1", 2);
        map.put("name2", 4);
        assertEquals("[name1, name2]",     mapAccess.keys()   .apply(map).toString());
        assertEquals("[2, 4]",             mapAccess.values() .apply(map).toString());
        assertEquals("[name1=2, name2=4]", mapAccess.entries().apply(map).toString());
        
        assertEquals("2", mapAccess.get("name1").apply(map).toString());
        assertEquals("4", mapAccess.get("name2").apply(map).toString());
        
        
        assertEquals("[name1=2]", mapAccess.filterEntries(entry -> entry.getKey().endsWith("1")).apply(map).toString());
        assertEquals("[name1=2]", mapAccess.filter(theString.thatEndsWith("1")).apply(map).toString());
    }
    
}
