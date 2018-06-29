package functionalj.types;

import static functionalj.lens.Accesses.theString;
import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.lens.Accesses;
import lombok.val;

public class FunctionalListTest {
    
    @Test
    public void testIndexes() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[0, 1, 5]", "" + list.indexesOf(theString.length().thatLessThan(4)));
    }
    
    @Test
    public void testSelect() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[[0,One], [1,Two], [5,Six]]", "" + list.select(theString.length().thatLessThan(4)));
    }
    
}