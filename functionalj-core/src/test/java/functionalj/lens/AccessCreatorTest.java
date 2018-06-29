package functionalj.lens;

import static functionalj.lens.Accesses.ofString;
import static functionalj.lens.Accesses.theList;
import static functionalj.types.ImmutableList.listOf;

import java.util.List;

import org.junit.Test;

import lombok.val;

public class AccessCreatorTest {

    private static final ListAccess<List<String>, String, StringAccess<List<String>>> theStrList = theList(ofString());
    
    @Test
    public void testAccessCreator() {
        val lists = listOf(
                    listOf("ONE", "TWO", "THREE"),
                    listOf("AE", "BEE", "SEE")
                );
        
        System.out.println(lists.map(theList(ofString()).first().toLowerCase()));
        System.out.println(lists.map(theList(ofString()).first().toLowerCase()));
    }
    
}
