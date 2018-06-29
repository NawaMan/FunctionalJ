package functionalj.lens;

import static functionalj.lens.Lenses.ofString;
import static functionalj.lens.Lenses.theList;
import static functionalj.types.ImmutableList.listOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class LensCreatorTest {
    

    @Test
    public void testLensCreator() {
        val lists = listOf(
                    listOf("ONE", "TWO", "THREE"),
                    listOf("AE", "BEE", "SEE")
                );
        
        val theStrListLens = Lenses.theList(Lenses.ofString());
        
        assertEquals("[one, ae]", "" + lists.map(theStrListLens     .first().toLowerCase()));
        assertEquals("[one, ae]", "" + lists.map(theList(ofString()).first().toLowerCase()));
    }
}
