package functionalj.store;

import static functionalj.lens.Access.theInteger;
import static functionalj.store.ChangeResult.Accepted;
import static functionalj.store.ChangeResult.Rejected;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.function.Func2;
import functionalj.result.Result;
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
    
    @Test
    public void testChain() {
        val store = new Store<>(0);
        assertEquals("Store [data=0]", store.toString());
        store
            .change(theInteger.add(1))
            .change(theInteger.add(1));
        assertEquals("Store [data=2]", store.toString());
    }
    @Test
    public void testReject() {
        val positiveNumberAcceptor = (Func2<Integer, Result<Integer>, ChangeResult<Integer>>)((org, res)->{
            return res.map(newValue -> rejectNegative(org, newValue)).get();
        });
        
        val store = new Store<>(0, positiveNumberAcceptor);
        assertEquals("Store [data=0]", store.toString());
        store
            .change(theInteger.add(1))
            .change(theInteger.add(-5))
            .change(theInteger.add(1));
        assertEquals("Store [data=2]", store.toString());
    }
    
    private ChangeResult<Integer> rejectNegative(int orgValue, int newValue) {
        return newValue >= 0
                ? Accepted(null, orgValue, newValue)
                : Rejected(null, orgValue, newValue, orgValue, new ChangeRejectedException("Only positive value is allowed: " + newValue));
    }
    
}
