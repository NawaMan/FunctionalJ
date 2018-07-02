package functionalj.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import lombok.val;

public class ResultTest {
    
    @Test
    public void testImmutableResult_value() {
        val result = ImmutableResult.of("VALUE");
        assertEquals("Result:{ Value: VALUE }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertTrue (result.isNotNull());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testImmutableResult_null() {
        val result = ImmutableResult.of(null);
        assertEquals("Result:{ Value: null }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertFalse(result.isPresent());
        assertFalse(result.isNotNull());
        assertTrue (result.isNull());
    }
    
    @Test
    public void testImmutableResult_exception() {
        val result = ImmutableResult.of((String)null).ensureNotNull();
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + result);
        assertFalse(result.isValue());
        assertTrue (result.isException());
        assertFalse(result.isPresent());
        assertFalse(result.isNotNull());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testImmutableResult_map() {
        val result = ImmutableResult.of("VALUE").map(str -> str.length());
        assertEquals("Result:{ Value: 5 }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertTrue (result.isNotNull());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testImmutableResult_failableMap() {
        val result = ImmutableResult.of("VALUE").failableMap(str -> new UnsupportedOperationException("Not support."));
        assertEquals("Result:{ Value: java.lang.UnsupportedOperationException: Not support. }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertTrue (result.isNotNull());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testImmutableResult_mapDerived() {
        val count  = new AtomicInteger(0);
        val result = ImmutableResult.of("VALUE").map(str -> "" + count.incrementAndGet());
        assertEquals("Result:{ Value: 1 }", "" + result);
        assertEquals("Result:{ Value: 2 }", "" + result);
        
        count.set(0);
        assertEquals("Result:{ Value: 1 }", "" + result);
        
        val immutableResult = result.toImmutable();
        assertEquals("Result:{ Value: 3 }", "" + result);
        assertEquals("Result:{ Value: 2 }", "" + immutableResult);
        
        count.set(0);
        val immutableResult2 = result.toImmutable();
        assertEquals("Result:{ Value: 1 }", "" + immutableResult2);
    }
    
    @Test
    public void testImmutableResult_map_null() {
        val result = ImmutableResult.of("VALUE").map(str -> null);
        assertEquals("Result:{ Value: null }", "" + result);
    }
    
}
