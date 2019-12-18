// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package example.functionalj.list;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import lombok.val;

public class ListMapExamples {
    
    @Test
    public void exampleListMap() {
        List<String>        list = FuncList.of("I", "Me", "Myself");
        Map<String, Double> map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
        assertEquals("[I, Me, Myself]",                  list.toString());
        assertEquals("{E:2.71828, One:1.0, PI:3.14159}", map.toString());
    }
    
    @Test
    public void exampleReadOnly() {
        List<String>        list = FuncList.of("I", "Me", "Myself");
        Map<String, Double> map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
        assertEquals(3,         list.size());
        assertEquals(3,         map.size());
        assertEquals("Me",      list.get(1).toString());
        assertEquals("3.14159", map.get("PI").toString());
    }
    
    @Test
    public void exampleUnsupportException() {
        val list = FuncList.of("I", "Me", "Myself");
        try {
            list.add("We");
            fail("Expect an error!");
        } catch (UnsupportedOperationException e) {
        }
        
        val map = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
        try {
            map.put("Ten", 10.0);
            fail("Expect an error!");
        } catch (UnsupportedOperationException e) {
        }
    }
    
    @Test
    public void exampleImmutableModification() {
        val list = FuncList.of("I", "Me", "Myself");
        val map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
        
        val newList = list.append("First-Person");
        val newMap  = map .with("Ten", 10.0);
        
        assertEquals("[I, Me, Myself]",                            list.toString());
        assertEquals("{E:2.71828, One:1.0, PI:3.14159}",           map .toString());
        assertEquals("[I, Me, Myself, First-Person]",              newList.toString());
        assertEquals("{E:2.71828, One:1.0, PI:3.14159, Ten:10.0}", newMap .toString());
    }
    
    @Test
    public void exampleFunctional() {
        val list = FuncList.of("I", "Me", "Myself");
        val map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
        assertEquals("[1, 2, 6]",          list.map     (String::length).toString());
        assertEquals("{E:3, One:1, PI:3}", map .mapValue(Math::round)   .toString());
    }
    
    @Test
    public void exampleImmutable() {
        val cats         = FuncList.of("Kitty", "Tigger", "Striped", "Oreo", "Simba", "Scar", "Felix", "Pete", "Schrödinger's");
        val rand         = new Random();
        val deadNotAlive = Func.f((String s) -> rand.nextBoolean()).toPredicate();
        val deadCats     = cats.filter(deadNotAlive);
        assertNotEquals(deadCats, deadCats);
        
        val surelyDeadCats = deadCats.toImmutableList();
        assertEquals(surelyDeadCats, surelyDeadCats);
    }
    
}
