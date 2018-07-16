package functionalj.types;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.map.ImmutableMap;

public class ImmutableMapTest {

    @Test
    public void testGetFindSelect() {
        assertEquals("Two",        "" + ImmutableMap.of("1", "One", "2", "Two").get("2"));
        assertEquals("Three",      "" + ImmutableMap.of("1", "One", "2", "Two").getOrDefault("3", "Three"));
        assertEquals("Two",        "" + ImmutableMap.of("1", "One", "2", "Two").findBy("2").get());
        assertEquals("Three",      "" + ImmutableMap.of("1", "One", "2", "Two").findBy("3").orElse("Three"));
        assertEquals("[One, Two]", "" + ImmutableMap.of("1", "One", "2", "Two", "10", "Ten").select(key -> key.length() == 1));
    }
    
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
    
    @Test
    public void testDefaultTo() {
//        assertEquals("{1:One, 2:Two, 3:Three}", "" + ImmutableMap.of("1", "One", "2", "Two").defaultTo("3", "Three"));
        assertEquals("{1:One, 2:Two}",          "" + ImmutableMap.of("1", "One", "2", "Two").defaultTo("2", "Four"));
    }
}
