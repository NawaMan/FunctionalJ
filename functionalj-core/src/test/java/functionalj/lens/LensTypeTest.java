package functionalj.lens;

import static functionalj.lens.Access.theList;
import static functionalj.list.ImmutableList.listOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class LensTypeTest {
    
    @Test
    public void testLensCreator() {
        val lists = listOf(
                    listOf("ONE", "TWO", "THREE"),
                    listOf("AE", "BEE", "SEE")
                );

        assertEquals("[true, false]", "" + lists.map(theList.first().thatEquals("ONE")));
        
        val theStrListLens = theList.of(LensTypes.STRING());
        
        assertEquals("[one, ae]", "" + lists.map(theStrListLens     .first().toLowerCase()));
        assertEquals("[one, ae]", "" + lists.map(theList.of(LensTypes.STRING()).first().toLowerCase()));
    }
    
}
