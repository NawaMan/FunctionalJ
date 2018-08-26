package functionalj.types.result;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTest {
    
    private static final Result<String> result = Result.of("Test");
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testMap() {
        assertStrings("Result:{ Value: Test }", result);
        assertStrings("Result:{ Value: 4 }",    result.map(str -> str.length()));
    }
    
}
