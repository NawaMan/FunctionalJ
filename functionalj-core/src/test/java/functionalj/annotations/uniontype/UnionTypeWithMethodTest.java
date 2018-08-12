package functionalj.annotations.uniontype;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import functionalj.annotations.UnionType;

@SuppressWarnings("javadoc")
public class UnionTypeWithMethodTest {
    
    @UnionType(name="MayBe")
    public interface MayBeSpec<T> {
        void None();
        void Just(T value);
        
//        default <T> boolean equals(MayBe<T> mayBe, Object obj) {
//            if (obj == null)
//                return mayBe.isNone();
//            return mayBe.toString().equals(obj.toString());
//        }
        default <T> int hashCode(MayBe<T> mayBe2) {
            MayBe<T> theMayBe = mayBe2;
            if (theMayBe.isNone())
                return 0;
            return theMayBe.toString().hashCode();
        }
//        default <T> boolean isPresent(MayBe<T> mayBe) {
//            return mayBe.isJust();
//        }
    }
    
    @Test
    public void testSourceSpec() {
        
    }
    
    @Test
    public void testMethod() {
        // NOTE: Default implementation of equals for any Union type will return false if the parameter is null.
        //       This implementation accept null as equals to None.
        assertTrue(MayBe.None().equals(null));
    }
    @Test
    public void testPresent() {
//        assertTrue(MayBe.None().isPresent());
    }
    
}
