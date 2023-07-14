package functionalj.list.doublelist;

import static org.junit.Assert.*;
import org.junit.Test;
import lombok.val;

public class ImmutableDoubleFuncListTest {

    @Test
    public void testAppend() {
        val orgList = ImmutableDoubleFuncList.of(1.0, 2.0, 3.0);
        val listOne = orgList.append(1001.0);
        val listTwo = orgList.append(1002.0);
        assertEquals("[1.0, 2.0, 3.0]", orgList.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0]", listOne.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0]", listTwo.toString());
        val listOneA = listOne.append(70001.0);
        val listOneB = listOne.append(70002.0);
        val listTwoA = listTwo.append(70001.0);
        val listTwoB = listTwo.append(70002.0);
        assertEquals("[1.0, 2.0, 3.0]", orgList.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0]", listOne.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0]", listTwo.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0, 70001.0]", listOneA.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0, 70002.0]", listOneB.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0, 70001.0]", listTwoA.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0, 70002.0]", listTwoB.toString());
    }

    @Test
    public void testAppend_fromEmpty() {
        val orgList = ImmutableDoubleFuncList.empty();
        val listOne = orgList.appendAll(1001, 1002);
        val listTwo = orgList.appendAll(1001, 1002, 1003);
        assertEquals("[]", orgList.toString());
        assertEquals("[1001.0, 1002.0]", listOne.toString());
        assertEquals("[1001.0, 1002.0, 1003.0]", listTwo.toString());
    }
}
