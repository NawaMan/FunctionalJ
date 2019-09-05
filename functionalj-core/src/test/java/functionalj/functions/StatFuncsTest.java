package functionalj.functions;

import static functionalj.functions.StatFuncs.minOf;
import static functionalj.functions.StatFuncs.maxOf;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.list.FuncList;
import lombok.val;

public class StatFuncsTest {

    @Test
    public void testMinAsInt() {
        val list = FuncList.listOf("One", "Two", "Three", "Four", "Five", "Six");
        assertEquals("(3,5)", 
                "" + list.get(
                        minOf(theString.length()), 
                        maxOf(theString.length())));
    }

}
