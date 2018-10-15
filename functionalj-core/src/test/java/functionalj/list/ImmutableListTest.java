package functionalj.list;

import static functionalj.lens.Access.$S;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.list.ImmutableList;
import lombok.val;

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
    public void testSubList() {
        assertEquals("[Four, Five]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .subList(3, 5)
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
    @Test
    public void testSplit_ensurePredicateGotCalledOncePerItem() {
        val processedStrings = new ArrayList<String>();
        assertEquals("([One, Two],[Four, Five],[Three])", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five")
                .split($S.length().thatEquals(3),
                       it -> {
                           processedStrings.add(it);
                           return it.length() <= 4;
                       })
                .toString());
        assertEquals("[Three, Four, Five]", "" + processedStrings);
    }
    
}
