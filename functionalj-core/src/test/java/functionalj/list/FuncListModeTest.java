package functionalj.list;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import functionalj.list.FuncList.Mode;
import lombok.val;

public class FuncListModeTest {
    
    @Test
    public void testMode_changeAndStay() {
        val orgList = FuncList.of(1, 2, 3, 4);
        assertEquals(Mode.lazy, orgList.mode());
        assertEquals(Mode.lazy, orgList.toLazy().mode());
        assertEquals(Mode.lazy, orgList.toLazy().map(i -> i * 2).mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToInt(i -> i * 2).mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToInt(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToLong(i -> i * 2).mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToLong(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToDouble(i -> i * 2).mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToDouble(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.eager, orgList.toEager().mode());
        assertEquals(Mode.eager, orgList.toEager().map(i -> i * 2).mode());
        assertEquals(Mode.eager, orgList.toEager().mapToInt(i -> i * 2).mode());
        assertEquals(Mode.eager, orgList.toEager().mapToInt(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.eager, orgList.toEager().mapToLong(i -> i * 2).mode());
        assertEquals(Mode.eager, orgList.toEager().mapToLong(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.eager, orgList.toEager().mapToDouble(i -> i * 2).mode());
        assertEquals(Mode.eager, orgList.toEager().mapToDouble(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.cache, orgList.toCache().mode());
        assertEquals(Mode.cache, orgList.toCache().map(i -> i * 2).mode());
        assertEquals(Mode.cache, orgList.toCache().mapToInt(i -> i * 2).mode());
        assertEquals(Mode.cache, orgList.toCache().mapToInt(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.cache, orgList.toCache().mapToLong(i -> i * 2).mode());
        assertEquals(Mode.cache, orgList.toCache().mapToLong(i -> i * 2).map(i -> i + 1).mode());
        assertEquals(Mode.cache, orgList.toCache().mapToDouble(i -> i * 2).mode());
        assertEquals(Mode.cache, orgList.toCache().mapToDouble(i -> i * 2).map(i -> i + 1).mode());
    }
    
    @Test
    public void testMode_oneTimeThings() {
        val orgList = FuncList.of(1, 2, 3, 4);
        assertEquals(Mode.lazy, orgList.mode());
        assertEquals(Mode.lazy, orgList.toLazy().mode());
        assertEquals(Mode.lazy, orgList.toLazy().map(i -> i * 2).freeze().mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToInt(i -> i * 2).freeze().mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToLong(i -> i * 2).freeze().mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToDouble(i -> i * 2).freeze().mode());
        assertEquals(Mode.lazy, orgList.toLazy().map(i -> i * 2).cache().mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToInt(i -> i * 2).cache().mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToLong(i -> i * 2).cache().mode());
        assertEquals(Mode.lazy, orgList.toLazy().mapToDouble(i -> i * 2).cache().mode());
        assertEquals(Mode.eager, orgList.toEager().mode());
        assertEquals(Mode.eager, orgList.toEager().map(i -> i * 2).freeze().mode());
        assertEquals(Mode.eager, orgList.toEager().mapToInt(i -> i * 2).freeze().mode());
        assertEquals(Mode.eager, orgList.toEager().mapToLong(i -> i * 2).freeze().mode());
        assertEquals(Mode.eager, orgList.toEager().mapToDouble(i -> i * 2).freeze().mode());
        assertEquals(Mode.eager, orgList.toEager().mode());
        assertEquals(Mode.eager, orgList.toEager().map(i -> i * 2).cache().mode());
        assertEquals(Mode.eager, orgList.toEager().mapToInt(i -> i * 2).cache().mode());
        assertEquals(Mode.eager, orgList.toEager().mapToLong(i -> i * 2).cache().mode());
        assertEquals(Mode.eager, orgList.toEager().mapToDouble(i -> i * 2).cache().mode());
        assertEquals(Mode.cache, orgList.toCache().mode());
        assertEquals(Mode.cache, orgList.toCache().map(i -> i * 2).freeze().mode());
        assertEquals(Mode.cache, orgList.toCache().mapToInt(i -> i * 2).freeze().mode());
        assertEquals(Mode.cache, orgList.toCache().mapToLong(i -> i * 2).freeze().mode());
        assertEquals(Mode.cache, orgList.toCache().mapToDouble(i -> i * 2).freeze().mode());
        assertEquals(Mode.cache, orgList.toCache().mode());
        assertEquals(Mode.cache, orgList.toCache().map(i -> i * 2).cache().mode());
        assertEquals(Mode.cache, orgList.toCache().mapToInt(i -> i * 2).cache().mode());
        assertEquals(Mode.cache, orgList.toCache().mapToLong(i -> i * 2).cache().mode());
        assertEquals(Mode.cache, orgList.toCache().mapToDouble(i -> i * 2).cache().mode());
    }
    
    @Test
    public void testLazy() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toLazy();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2);
        assertEquals("[]", logs.toString());
        newList.limit(3).size();
        assertEquals("[1, 2, 3]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 1, 2, 3, 4]", logs.toString());
    }
    
    @Test
    public void testEager() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toEager();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2);
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        newList.limit(3).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
    }
    
    @Test
    public void testCache() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toCache();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2);
        assertEquals("[]", logs.toString());
        newList.limit(3).size();
        assertEquals("[1, 2, 3]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4]", logs.toString());
    }
    
    @Test
    public void testLazy_thenFreeze() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toLazy();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2).freeze();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        assertEquals(Mode.lazy, newList.mode());
        newList.limit(3).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
    }
    
    @Test
    public void testEager_thenFreeze() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toEager();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2).freeze();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        assertEquals(Mode.eager, newList.mode());
        newList.limit(3).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
    }
    
    @Test
    public void testCache_thenFreeze() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toCache();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2).freeze();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        assertEquals(Mode.cache, newList.mode());
        newList.limit(3).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
    }
    
    @Test
    public void testLazy_thenCache() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toLazy();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2).cache();
        assertEquals("[]", logs.toString());
        assertEquals(Mode.lazy, newList.mode());
        newList.limit(3).size();
        assertEquals("[1, 2, 3]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4]", logs.toString());
    }
    
    @Test
    public void testEager_thenCache() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toEager();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2).cache();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        assertEquals(Mode.eager, newList.mode());
        newList.limit(3).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4, 5]", logs.toString());
    }
    
    @Test
    public void testCache_thenCache() {
        val logs = new ArrayList<String>();
        val orgList = FuncList.of(1, 2, 3, 4, 5).toCache();
        val newList = orgList.peek(i -> logs.add("" + i)).map(i -> i * 2).cache();
        assertEquals("[]", logs.toString());
        assertEquals(Mode.cache, newList.mode());
        newList.limit(3).size();
        assertEquals("[1, 2, 3]", logs.toString());
        newList.limit(4).size();
        assertEquals("[1, 2, 3, 4]", logs.toString());
    }
}
