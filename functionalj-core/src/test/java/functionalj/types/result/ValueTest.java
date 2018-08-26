package functionalj.types.result;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.junit.Test;

import lombok.val;

public class ValueTest {
    
    private static final Result<String> value = Result.of("Test");
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testGet() {
        // NOTE - Not exactly efficient ... but will do for now.
        val logs = new ArrayList<String>();
        val length =  value.map(str -> {
            return str.length();
        });
        
        assertStrings("Result:{ Value: 4 }", length);
    }
    
    @Test
    public void testLazy() {
        val logs = new ArrayList<String>();
        val length =  value.map(str -> {
            logs.add(str);
            return str.length();
        });
        logs.add("--- After map but logged first ---");
        
        assertStrings("Result:{ Value: Test }", value);
        assertStrings("[--- After map but logged first ---]",    logs);
        
        // First use will have 'Test' added to the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[--- After map but logged first ---, Test]",    logs);
        
        // Second use will also have 'Test' added to the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[--- After map but logged first ---, Test, Test]",    logs);
    }
    
    @Test
    public void testNonLazy() {
        val logs = new ArrayList<String>();
        val value = new Value<String>("Test");
        val length =  value.map(str -> {
            logs.add(str);
            return str.length();
        });
        logs.add("--- After map but logged first ---");
        
        assertStrings("Result:{ Value: Test }", value);
        assertStrings("[Test, --- After map but logged first ---]",    logs);
        
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[Test, --- After map but logged first ---]",    logs);
        
        // Second use will not add the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[Test, --- After map but logged first ---]",    logs);
        // Third use will not add the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[Test, --- After map but logged first ---]",    logs);
        // Forth use will not add the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[Test, --- After map but logged first ---]",    logs);
    }
    
    @Test
    public void testLazyNonLazy() {
        val logs = new ArrayList<String>();
        val length =  value.map(str -> {
            logs.add(str);
            return str.length();
        });
        logs.add("--- After map but logged first ---");
        
        assertStrings("Result:{ Value: Test }", value);
        assertStrings("[--- After map but logged first ---]",    logs);
        
        // First use will have 'Test' added to the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[--- After map but logged first ---, Test]",    logs);
        
        // Second use will also have 'Test' added to the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[--- After map but logged first ---, Test, Test]",    logs);
        
        val length2 = length.asValue();
        
        // Second use will also have 'Test' added to the log.
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[--- After map but logged first ---, Test, Test, Test]",    logs);
        assertStrings("Result:{ Value: 4 }", length);
        assertStrings("[--- After map but logged first ---, Test, Test, Test]",    logs);
        
    }
    
    @Test
    public void testPrintException() {
        val buffer = new StringWriter();
        val length =  value.map(str -> {
            throw new NullPointerException();
        });
        
        assertStrings(
                "Result:{ Exception: java.lang.NullPointerException }",
                length.printException(new PrintWriter(buffer)));
        assertStrings("java.lang.NullPointerException", buffer.toString().split("\n")[0]);
    }
    
}
