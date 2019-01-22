package functionalj.list;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.list.FuncListStream;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import lombok.val;

@SuppressWarnings("javadoc")
public class FuncListStreamTest {
    
    @SuppressWarnings("resource")
    @Test
    public void testStream() {
        val rawList    = (List<String>)asList("One", "Two", "Three");
        val streamable = (Streamable<String>)(()->StreamPlus.from(rawList.stream()));
        val newList    = new FuncListStream<String, Integer>(streamable, stream -> stream.map(String::length));
        assertEquals("[3, 3, 5]", newList.stream().collect(toList()).toString());
    }
    
    @SuppressWarnings("resource")
    @Test
    public void testAppend() {
        val rawList    = (List<String>)asList("One", "Two", "Three");
        val streamable = (Streamable<String>)(()->StreamPlus.from(rawList.stream()));
        val newList    = new FuncListStream<String, Integer>(streamable, stream -> stream.map(String::length));
        assertEquals("[3, 3, 5, 6]", newList.append(6).toString());
    }
    
    @SuppressWarnings("resource")
    @Test
    public void testSet() {
        val rawList    = (List<String>)asList("One", "Two", "Three");
        val streamable = (Streamable<String>)(()->StreamPlus.from(rawList.stream()));
        val newList    = new FuncListStream<String, Integer>(streamable, stream -> stream.map(String::length));
        assertEquals("[3, 1, 5]", newList.with(1, 1).toString());
    }
    
}
