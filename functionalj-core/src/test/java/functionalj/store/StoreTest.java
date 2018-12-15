package functionalj.store;

import static functionalj.lens.Access.theInteger;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class StoreTest {

    @Test
    public void testBasic() {
        val store = new Store<>(0);
        assertEquals("Store [data=0]", store.toString());
        ChangeResult<Integer> result = store.change(theInteger.add(1));
        assertEquals("Store [data=1]", store.toString());
        assertEquals("Accepted(0,1)",  result.toString());
    }

}
