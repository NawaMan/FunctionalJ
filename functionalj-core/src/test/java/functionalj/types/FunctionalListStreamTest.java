package functionalj.types;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.types.list.FunctionalListStream;
import functionalj.types.stream.Streamable;
import lombok.val;

public class FunctionalListStreamTest {
    
    @Test
    public void testStream() {
        val rawList = (List<String>)asList("One", "Two", "Three");
        val newList = new FunctionalListStream<String, Integer>((Streamable<String>)(()->rawList.stream()), stream -> stream.map(String::length));
        assertEquals("[3, 3, 5]", newList.stream().collect(toList()).toString());
    }
    
    @Test
    public void testAppend() {
        val rawList = (List<String>)asList("One", "Two", "Three");
        val newList = new FunctionalListStream<String, Integer>((Streamable<String>)()->rawList.stream(), stream -> stream.map(String::length));
        assertEquals("[3, 3, 5, 6]", newList.append(6).toString());
    }
    
    @Test
    public void testSet() {
        val rawList = (List<String>)asList("One", "Two", "Three");
        val newList = new FunctionalListStream<String, Integer>((Streamable<String>)()->rawList.stream(), stream -> stream.map(String::length));
        assertEquals("[3, 1, 5]", newList.with(1, 1).toString());
    }
    
}
