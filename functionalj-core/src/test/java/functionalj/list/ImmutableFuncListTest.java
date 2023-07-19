package functionalj.list;

import static org.junit.Assert.*;
import org.junit.Test;
import lombok.val;

public class ImmutableFuncListTest {
    
    @Test
    public void testAppend() {
        val orgList = ImmutableFuncList.of("One", "Two", "Three");
        val listOne = orgList.append("Four.point.one");
        val listTwo = orgList.append("Four.point.two");
        assertEquals("[One, Two, Three]", orgList.toString());
        assertEquals("[One, Two, Three, Four.point.one]", listOne.toString());
        assertEquals("[One, Two, Three, Four.point.two]", listTwo.toString());
        val listOneA = listOne.append("A");
        val listOneB = listOne.append("B");
        val listTwoA = listTwo.append("A");
        val listTwoB = listTwo.append("B");
        assertEquals("[One, Two, Three]", orgList.toString());
        assertEquals("[One, Two, Three, Four.point.one]", listOne.toString());
        assertEquals("[One, Two, Three, Four.point.two]", listTwo.toString());
        assertEquals("[One, Two, Three, Four.point.one, A]", listOneA.toString());
        assertEquals("[One, Two, Three, Four.point.one, B]", listOneB.toString());
        assertEquals("[One, Two, Three, Four.point.two, A]", listTwoA.toString());
        assertEquals("[One, Two, Three, Four.point.two, B]", listTwoB.toString());
    }
    
    @Test
    public void testAppend_fromEmpty() {
        val orgList = ImmutableFuncList.empty();
        val listOne = orgList.appendAll("Four.point.one-A", "Four.point.one-B");
        val listTwo = orgList.appendAll("Four.point.two-A", "Four.point.two-B", "Four.point.two-C");
        assertEquals("[]", orgList.toString());
        assertEquals("[Four.point.one-A, Four.point.one-B]", listOne.toString());
        assertEquals("[Four.point.two-A, Four.point.two-B, Four.point.two-C]", listTwo.toString());
    }
}
