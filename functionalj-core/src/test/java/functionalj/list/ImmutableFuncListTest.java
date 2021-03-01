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
        
        assertEquals("[One, Two, Three]",                 orgList.toString());
        assertEquals("[One, Two, Three, Four.point.one]", listOne.toString());
        assertEquals("[One, Two, Three, Four.point.two]", listTwo.toString());
        
        val listOneA = listOne.append("A");
        val listOneB = listOne.append("B");
        
        val listTwoA = listTwo.append("A");
        val listTwoB = listTwo.append("B");
        
        assertEquals("[One, Two, Three]",                 orgList.toString());
        assertEquals("[One, Two, Three, Four.point.one]", listOne.toString());
        assertEquals("[One, Two, Three, Four.point.two]", listTwo.toString());
        
        assertEquals("[One, Two, Three, Four.point.one, A]", listOneA.toString());
        assertEquals("[One, Two, Three, Four.point.one, B]", listOneB.toString());
        
        assertEquals("[One, Two, Three, Four.point.two, A]", listTwoA.toString());
        assertEquals("[One, Two, Three, Four.point.two, B]", listTwoB.toString());
    }
    
}
