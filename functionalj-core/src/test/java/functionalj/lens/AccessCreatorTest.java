package functionalj.lens;

import static functionalj.lens.Accesses.ofString;
import static functionalj.lens.Accesses.theList;
import static functionalj.types.ImmutableList.listOf;
import static org.junit.Assert.assertEquals;

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
        
        assertEquals("[one, ae]", "" + lists.map(theStrList         .first().toLowerCase()));
        assertEquals("[one, ae]", "" + lists.map(theList(ofString()).first().toLowerCase()));
    }
    
}
