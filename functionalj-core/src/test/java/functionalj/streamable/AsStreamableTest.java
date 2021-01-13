package functionalj.streamable;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import lombok.val;

public class AsStreamableTest {
    
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testToFuncList_finiteStream() {
        assertStrings("[0, 0, 0, 0, 0]", Streamable.generate(() -> () -> 0).limit(5).toFuncList());
    }
    
    @Test
    public void testToFuncList_infiniteStream() {
        val list = Streamable.generate(() -> {
                val counter = new AtomicInteger();
                return () -> counter.getAndIncrement();
            }).toFuncList();
        assertStrings("[10, 11, 12, 13, 14]", list.skip(10).limit(5));
        assertStrings("5",                    list.get(5));
        
        assertStrings("[0, 1, 2, 3, 4]", list.limit(5).toFuncList());
        // Cannot test this now until we handle inifite list.
        //assertStrings("[0, 0, 0, 0, 0]", list.with(2, i -> i * 10).limit(5));
    }
    
}
