package functionalj.types;

import static functionalj.lens.Lenses.theString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
    
    @Test
    public void testMapToTuple() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[[One,3], [Two,3], [Three,5], [Four,4], [Five,4], [Six,3], [Seven,5]]", "" + list.map(theString, theString.length()));
    }
    
}
