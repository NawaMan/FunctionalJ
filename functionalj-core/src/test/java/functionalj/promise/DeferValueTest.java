package functionalj.promise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import org.junit.Test;
import lombok.val;

public class DeferValueTest {
    
    @Test
    public void testAssign() {
        val defer = DeferValue.of(String.class);
        assertEquals("Result:{ NotReady }", "" + defer.getCurrentResult());
        assertTrue(defer.assign("good-value"));
        assertEquals("Result:{ Value: good-value }", "" + defer.getCurrentResult());
        assertFalse(defer.assign("bad-value"));
        assertEquals("Result:{ Value: good-value }", "" + defer.getCurrentResult());
        try {
            defer.assignOrThrow("very-bad-value");
            fail();
        } catch (Exception e) {
        }
        assertEquals("Result:{ Value: good-value }", "" + defer.getCurrentResult());
    }
    
    @Test
    public void testFail() {
        val defer = DeferValue.of(String.class);
        assertEquals("Result:{ NotReady }", "" + defer.getCurrentResult());
        assertTrue(defer.fail(new NullPointerException()));
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + defer.getCurrentResult());
        assertFalse(defer.assign("some-value"));
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + defer.getCurrentResult());
        try {
            defer.failOrThrow(new IllegalAccessException());
            fail();
        } catch (Exception e) {
        }
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + defer.getCurrentResult());
    }
    
    @Test
    public void testComplete_connect_value() {
        val defer1 = DeferValue.of(String.class);
        val defer2 = defer1.whenAbsentUse("default-2");
        val defer3 = defer2.whenAbsentUse("default-3");
        val defer4 = defer3.whenAbsentUse("default-4");
        defer3.assign("the-value");
        assertEquals("Result:{ Value: the-value }", "" + defer1.getCurrentResult());
        assertEquals("Result:{ Value: the-value }", "" + defer2.getCurrentResult());
        assertEquals("Result:{ Value: the-value }", "" + defer3.getCurrentResult());
        assertEquals("Result:{ Value: the-value }", "" + defer4.getCurrentResult());
    }
    
    @Test
    public void testComplete_connect_null() {
        val logs = new ArrayList<String>();
        val defer1 = DeferValue.of(String.class).ifAbsent(() -> logs.add("defer1: null"));
        val defer2 = defer1.whenAbsentUse("default-2").ifAbsent(() -> logs.add("defer2: null"));
        val defer3 = defer1.whenNullUse("default-3").ifAbsent(() -> logs.add("defer3: null"));
        val defer4 = defer3.ifNull(() -> logs.add("defer4: null"));
        defer3.assign(null);
        assertEquals("Result:{ Value: null }", "" + defer1.getCurrentResult());
        assertEquals("Result:{ Value: default-2 }", "" + defer2.getCurrentResult());
        assertEquals("Result:{ Value: default-3 }", "" + defer3.getCurrentResult());
        assertEquals("Result:{ Value: default-3 }", "" + defer4.getCurrentResult());
        assertEquals("[defer1: null]", "" + logs);
    }
    
    @Test
    public void testComplete_connect_abort() {
        val logs = new ArrayList<String>();
        val defer1 = DeferValue.of(String.class).ifAbsent(() -> logs.add("defer1: absent")).ifNull(() -> logs.add("defer1: null"));
        val defer2 = defer1.whenAbsentUse("default-2").whenAbsentUse("default-2");
        val defer3 = defer1.whenAbsentUse("default-3").whenNullUse("default-3").ifNull(() -> logs.add("defer3: null"));
        val defer4 = defer1.whenAbsentUse("default-4").ifNull(() -> logs.add("defer4: null"));
        defer3.abort();
        assertEquals("Result:{ Cancelled }", "" + defer1.getCurrentResult());
        assertEquals("Result:{ Value: default-2 }", "" + defer2.getCurrentResult());
        assertEquals("Result:{ Value: default-3 }", "" + defer3.getCurrentResult());
        assertEquals("Result:{ Value: default-4 }", "" + defer4.getCurrentResult());
    }
    
    @Test
    public void testAssign_When() {
        val normalLater = DeferValue.of(String.class);
        val defaultLater = DeferValue.of(String.class).whenAbsentUse("default");
        assertEquals("Result:{ NotReady }", "" + normalLater.getCurrentResult());
        assertEquals("Result:{ NotReady }", "" + defaultLater.getCurrentResult());
        normalLater.assign(null);
        defaultLater.assign(null);
        assertEquals("Result:{ Value: null }", "" + normalLater.getCurrentResult());
        assertEquals("Result:{ Value: default }", "" + defaultLater.getCurrentResult());
    }
    
    @Test
    public void testAssign_When_null_absent() {
        val logs = new ArrayList<String>();
        val orgDefer = DeferValue.of(String.class);
        val newDefer = orgDefer.ifAbsent(() -> logs.add("absent")).ifNull(() -> logs.add("null"));
        orgDefer.assign(null);
        assertEquals("Result:{ Value: null }", "" + orgDefer.getCurrentResult());
        assertEquals("Result:{ Value: null }", "" + newDefer.getCurrentResult());
        assertEquals("[absent, null]", "" + logs);
    }
}
