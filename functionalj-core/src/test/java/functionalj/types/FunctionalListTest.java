package functionalj.types;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import functionalj.types.list.ImmutableList;
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
        assertEquals("[(0,One), (1,Two), (5,Six)]", "" + list.select(theString.length().thatLessThan(4)));
    }
    
    @Test
    public void testMapToTuple() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[(One,3), (Two,3), (Three,5), (Four,4), (Five,4), (Six,3), (Seven,5)]", "" + list.map(theString, theString.length()));
    }
    
    @Test
    public void testFlatMapOnly() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[One, One, Two, Two, Three, Four, Five, Six, Six, Seven]",
                "" + list.flatMapOnly(theString.length().thatLessThan(4), s -> ImmutableList.of(s, s)));
    }
    
    @Test
    public void testFlatMapIf() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[One, One, Two, Two, Three, Three, Three, Four, Four, Four, Five, Five, Five, Six, Six, Seven, Seven, Seven]",
                "" + list.flatMapIf(
                        theString.length().thatLessThan(4),
                        s -> ImmutableList.of(s, s),
                        s -> ImmutableList.of(s, s, s)));
    }
    
    @Test
    public void testToMap() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        val index = new AtomicInteger();
        assertEquals("["
                        + "{index:0, word:One, length:3}, "
                        + "{index:1, word:Two, length:3}, "
                        + "{index:2, word:Three, length:5}, "
                        + "{index:3, word:Four, length:4}, "
                        + "{index:4, word:Five, length:4}, "
                        + "{index:5, word:Six, length:3}, "
                        + "{index:6, word:Seven, length:5}]",
                "" + list.toMap(
                        "index",   __ -> index.getAndIncrement(), 
                        "word",   theString, 
                        "length", theString.length().asString()));
    }
    
    @Test
    public void testStreamCloseHasNoEffect() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        val lengthLessThanFive = list.indexesOf(theString.length().thatLessThan(4));
        val isOnClosedCalled = new AtomicBoolean(false);
        assertEquals("[0, 1, 5]", "" + lengthLessThanFive);
        assertEquals("[0, 1, 5]", "" + lengthLessThanFive);
        lengthLessThanFive.onClose(()->{
            if (isOnClosedCalled.get())
                fail("Hash already called.");
            
            isOnClosedCalled.set(true);
        });
        lengthLessThanFive.close();
        assertEquals("[0, 1, 5]", "" + lengthLessThanFive);
        assertEquals("[0, 1, 5]", "" + lengthLessThanFive);
        
        assertFalse(isOnClosedCalled.get());
    }
    
}
