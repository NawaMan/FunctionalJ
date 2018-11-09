package functionalj.stream;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import lombok.val;

public class IteratorPlusTest {
    
    @Test
    public void testPullNext() {
        val iterator = IteratorPlus.of(IntStreamPlus.infinite().limit(5).iterator());
        val logs     = new ArrayList<String>();
        Result<Integer> result;
        while ((result = iterator.pullNext()).isValue()) {
            logs.add("" + result.value());
        }
        assertEquals("[0, 1, 2, 3, 4]", logs.toString());
    }
    
    @Test
    public void testPullNext_null() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        Result<String> result;
        while ((result = iterator.pullNext()).isValue()) {
            logs.add("" + result.value());
        }
        assertEquals("[One, Two, null, Three]", logs.toString());
    }
    
    @Test
    public void testPullNext_multiple() {
        val iterator = IteratorPlus.of(IntStreamPlus.infinite().limit(10).iterator());
        assertEquals("[0, 1, 2, 3, 4]", iterator.pullNext(5).get().stream().toList().toString());
        assertEquals("[5]",             iterator.pullNext(1).get().stream().toList().toString());
        assertEquals("[6, 7]",          iterator.pullNext(2).get().stream().toList().toString());
        assertEquals("[8, 9]",          iterator.pullNext(2).get().stream().toList().toString());
        
        try {
            iterator.pullNext(2).get().stream().toList().toString();
            fail("Exception is expected.");
        } catch (NoMoreResultException e) {
        }
        
        assertTrue(iterator.pullNext(2).isAbsent());
    }
    @Test
    public void testUseNext() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        iterator.useNext(logs::add);
        iterator.useNext(logs::add);
        assertEquals("[One, Two]", logs.toString());
    }
    @Test
    public void testUseNext_multiple() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        iterator.useNext(3, s -> logs.add(s.toList().toString()));
        iterator.useNext(1, s -> logs.add(s.toList().toString()));
        iterator.useNext(1, s -> logs.add(s.toList().toString()));
        assertEquals("[[One, Two, null], [Three]]", logs.toString());
    }
    
    @Test
    public void testMapNext() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        iterator.useNext(logs::add);
        iterator.useNext(logs::add);
        assertEquals("[One, Two]", logs.toString());
    }
    @Test
    public void testMapNext_multiple() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        logs.add(iterator.mapNext(3, s -> s.toList().toString()).get());
        logs.add(iterator.mapNext(1, s -> s.toList().toString()).get());
        
        try {
            logs.add(iterator.mapNext(1, s -> s.toList().toString()).get());
            fail("Exception is expected.");
        } catch (NoMoreResultException e) {
        }
        
        assertTrue(iterator.mapNext(1, s -> s.toList().toString()).isAbsent());
        
        
        assertEquals("[[One, Two, null], [Three]]", logs.toString());
    }
}
