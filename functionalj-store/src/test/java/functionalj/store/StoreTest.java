package functionalj.store;

import static functionalj.lens.Access.theInteger;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.function.Func2;
import functionalj.result.Result;
import lombok.val;


public class StoreTest {
    
    @Test
    public void testBasic() {
         val  store = new Store<>(0);
        assertEquals("Store [data=0]", store.toString());
        ChangeResult<Integer> result = store.change(theInteger.plus(1));
        assertEquals("Store [data=1]", store.toString());
        assertEquals("ChangeResult [store=Store [data=1], originalData=0, status=Accepted(1)]",  result.toString());
    }
    
    @Test
    public void testChain() {
         val  store = new Store<>(0);
        assertEquals("Store [data=0]", store.toString());
        store
            .change(theInteger.plus(1))
            .change(theInteger.plus(1));
        assertEquals("Store [data=2]", store.toString());
    }
    @Test
    public void testReject1() {
         val  positiveNumberAcceptor = (Func2<Integer, Result<Integer>, ChangeResult<Integer>>)((org, res)->{
            return res.map(newValue -> rejectNegative(org, newValue)).get();
        });
        
         val  store = new Store<>(0, positiveNumberAcceptor);
        assertEquals("Store [data=0]", store.toString());
        store
            .change(theInteger.plus(1))
            .change(theInteger.plus(-5))
            .change(theInteger.plus(1));
        assertEquals("Store [data=1]", store.toString());
    }
    @Test
    public void testReject2() {
         val  positiveNumberAcceptor = (Func2<Integer, Result<Integer>, ChangeResult<Integer>>)((org, res)->{
            return res.map(newValue -> rejectNegative(org, newValue)).get();
        });
        
         val  store = new Store<>(0, positiveNumberAcceptor);
        assertEquals("Store [data=0]", store.toString());
        store.change(theInteger.plus(1));
        assertEquals("Store [data=1]", store.toString());
        store.change(theInteger.plus(-5));
        assertEquals("Store [data=1]", store.toString());
        store.change(theInteger.plus(1));
        assertEquals("Store [data=2]", store.toString());
    }
    
    private ChangeResult<Integer> rejectNegative(int orgValue, int newValue) {
        return newValue >= 0
                ? new ChangeResult<Integer>(null, orgValue, ResultStatus.Accepted(newValue))
                : new ChangeResult<Integer>(null, orgValue, ResultStatus.Rejected(newValue, orgValue, new ChangeRejectedException("Only positive value is allowed: " + newValue)));
    }
    
    @Test
    public void testUse() {
         val  log   = new ArrayList<String>();
         val  store = new Store<>(0);
        assertEquals("Store [data=0]", store.toString());
        store   .change(theInteger.plus(1))
        .store().use   (value -> log.add("" + value));
        assertEquals("Store [data=1]", store.toString());
    }
    
}
