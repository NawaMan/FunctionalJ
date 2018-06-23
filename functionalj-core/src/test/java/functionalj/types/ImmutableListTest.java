package functionalj.types;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImmutableListTest {
    
    @Test
    public void testAppend() {
        assertEquals("[One, Two, Three, Four, Five, Six, Seven]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five").append("Six", "Seven").toString());
    }
    
    @Test
    public void testMap() {
        assertEquals("[3, 3, 5, 4, 4, 3, 5]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .map(String::length)
                        .toString());
    }
    
    @Test
    public void testSubList_same() {
        assertEquals("[]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .subList(3, 3)
                        .toString());
    }
    @Test
    public void testSorted() {
        assertEquals("[1, 2, One, Two]", "" + ImmutableList.of("1", "One", "2", "Two").sorted());
        assertEquals("[1, 2, One, Two]", "" + ImmutableList.of("2", "Two", "1", "One").sorted());
    }
    
}
