package functionalj.list.longlist;

import static org.junit.Assert.*;
import org.junit.Test;
import lombok.val;

public class ImmutableLongFuncListTest {

    @Test
    public void testAppend() {
        val orgList = ImmutableLongFuncList.of(1, 2, 3);
        val listOne = orgList.append(1001);
        val listTwo = orgList.append(1002);
        assertEquals("[1, 2, 3]", orgList.toString());
        assertEquals("[1, 2, 3, 1001]", listOne.toString());
        assertEquals("[1, 2, 3, 1002]", listTwo.toString());
        val listOneA = listOne.append(70001);
        val listOneB = listOne.append(70002);
        val listTwoA = listTwo.append(70001);
        val listTwoB = listTwo.append(70002);
        assertEquals("[1, 2, 3]", orgList.toString());
        assertEquals("[1, 2, 3, 1001]", listOne.toString());
        assertEquals("[1, 2, 3, 1002]", listTwo.toString());
        assertEquals("[1, 2, 3, 1001, 70001]", listOneA.toString());
        assertEquals("[1, 2, 3, 1001, 70002]", listOneB.toString());
        assertEquals("[1, 2, 3, 1002, 70001]", listTwoA.toString());
        assertEquals("[1, 2, 3, 1002, 70002]", listTwoB.toString());
    }

    @Test
    public void testAppend_fromEmpty() {
        val orgList = ImmutableLongFuncList.empty();
        val listOne = orgList.appendAll(1001, 1002);
        val listTwo = orgList.appendAll(1001, 1002, 1003);
        assertEquals("[]", orgList.toString());
        assertEquals("[1001, 1002]", listOne.toString());
        assertEquals("[1001, 1002, 1003]", listTwo.toString());
    }
}
