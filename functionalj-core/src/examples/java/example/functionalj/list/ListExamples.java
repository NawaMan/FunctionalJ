package example.functionalj.list;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import lombok.val;

public class ListExamples {
    
    @Test
    public void exampleListMap() {
        List<String>        list = FuncList.of("I", "Me", "Myself");
        Map<String, Double> map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
        assertEquals("[I, Me, Myself]",                  list.toString());
        assertEquals("{One:1.0, PI:3.14159, E:2.71828}", map.toString());
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
        assertEquals("{One:1.0, PI:3.14159, E:2.71828}",           map .toString());
        assertEquals("[I, Me, Myself, First-Person]",              newList.toString());
        assertEquals("{One:1.0, PI:3.14159, E:2.71828, Ten:10.0}", newMap .toString());
    }
    
    @Test
    public void exampleFunctional() {
        val list = FuncList.of("I", "Me", "Myself");
        val map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
        assertEquals("[1, 2, 6]",          list.map(String::length).toString());
        assertEquals("{One:1, PI:3, E:3}", map .map(Math::round)   .toString());
    }
    
}
