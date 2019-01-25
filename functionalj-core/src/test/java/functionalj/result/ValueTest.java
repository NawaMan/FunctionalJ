package functionalj.result;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

public class ValueTest {
    
    private static final Result<String> value = Result.valueOf("Test");
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testGet() {
        // NOTE - Not exactly efficient ... but will do for now.
        val length = value.map(str -> {
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
