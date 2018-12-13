package functionalj.store;

import static functionalj.lens.Access.$I;

import org.junit.Test;

import lombok.val;

public class StoreTest {

    @Test
    public void testBasic() {
        val store = new Store<>(0);
        System.out.println(store);
        store.change($I.add(1));
        System.out.println(store);
    }

}
