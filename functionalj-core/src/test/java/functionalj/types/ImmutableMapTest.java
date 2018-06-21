package functionalj.types;

import static org.junit.Assert.*;

import org.junit.Test;

public class ImmutableMapTest {
    
    @Test
    public void testToString() {
        assertEquals("[1, 2]",         "" + ImmutableMap.of("1", "One", "2", "Two").keys().sorted());
        assertEquals("[One, Two]",     "" + ImmutableMap.of("1", "One", "2", "Two").values().sorted());
        assertEquals("{1:One, 2:Two}", "" + ImmutableMap.of("1", "One", "2", "Two").sorted());
    }
    
    @Test
    public void testWith() {
        assertEquals("{1:One, 2:Two, 3:Three}", "" + ImmutableMap.of("1", "One", "2", "Two").with("3", "Three").sorted());
        assertEquals("{1:Un, 2:Two}",           "" + ImmutableMap.of("1", "One", "2", "Two").with("1", "Un").sorted());
        assertEquals("{1:One, 2:Du}",           "" + ImmutableMap.of("1", "One", "2", "Two").with("2", "Du").sorted());
    }
    
    @Test
    public void testSorted() {
        assertEquals("{1:One, 2:Two}", "" + ImmutableMap.of("1", "One", "2", "Two").sorted());
        assertEquals("{1:One, 2:Two}", "" + ImmutableMap.of("2", "Two", "1", "One").sorted());
    }
}
