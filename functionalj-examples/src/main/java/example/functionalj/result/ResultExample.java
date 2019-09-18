package example.functionalj.result;

import static functionalj.functions.StrFuncs.matches;
import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

public class ResultExample {
    
    @Test
    public void testHandleException() {
        val wordCount
                = Result.of(()->Files.readAllBytes(Paths.get("FileNotFound.txt")))
                .map        (String::new)
                .map        (matches("[a-zA-Z]+"))
                .map        (Stream::count)
                .ifException(Exception::printStackTrace)
                .orElse     (0L)
                ;
        assertEquals(0L, wordCount.longValue());
    }
    
    @Test
    public void testValidation() {
        val result1
                = Result.valueOf("One Two Three Four Five Six")
                .map     (matches("[a-zA-Z]+"))
                .map     (Stream::count)
                .validate("Too few words: %d", count -> count > 5)
                ;
        assertEquals("Result:{ Value: 6 }", result1.toString());
        
        val result2
                = Result.valueOf("One Two Three Four")
                .map     (matches("[a-zA-Z]+"))
                .map     (Stream::count)
                .validate("Too few words: %d", count -> count > 5)
                ;
        assertEquals("Result:{ Invalid: Too few word: 4 }", result2.toString());
    }
}
