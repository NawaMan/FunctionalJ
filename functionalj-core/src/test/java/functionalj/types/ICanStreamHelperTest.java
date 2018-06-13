package functionalj.types;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.val;

public class ICanStreamHelperTest {
    
    @Test
    public void at() {
        val list     = Arrays.asList("One", "Two", "Three");
        val valueRef = new AtomicReference<String>();
        
        assertTrue(ICanStream.Helper.hasAt(list.stream(), 0, valueRef));
        assertEquals("One", valueRef.get());
        
        assertTrue(ICanStream.Helper.hasAt(list.stream(), 1, valueRef));
        assertEquals("Two", valueRef.get());

        assertTrue(ICanStream.Helper.hasAt(list.stream(), 2, valueRef));
        assertEquals("Three", valueRef.get());
        
        assertFalse(ICanStream.Helper.hasAt(list.stream(), 3, valueRef));
        assertEquals(null, valueRef.get());
    }
    
}
