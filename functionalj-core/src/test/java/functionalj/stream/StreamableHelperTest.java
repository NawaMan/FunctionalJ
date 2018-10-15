package functionalj.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.types.stream.Streamable;
import lombok.val;

public class StreamableHelperTest {
    
    @Test
    public void at() {
        val list     = Arrays.asList("One", "Two", "Three");
        val valueRef = new AtomicReference<String>();
        
        assertTrue(Streamable.Helper.hasAt(list.stream(), 0, valueRef));
        assertEquals("One", valueRef.get());
        
        assertTrue(Streamable.Helper.hasAt(list.stream(), 1, valueRef));
        assertEquals("Two", valueRef.get());

        assertTrue(Streamable.Helper.hasAt(list.stream(), 2, valueRef));
        assertEquals("Three", valueRef.get());
        
        assertFalse(Streamable.Helper.hasAt(list.stream(), 3, valueRef));
        assertEquals(null, valueRef.get());
    }
    
}
