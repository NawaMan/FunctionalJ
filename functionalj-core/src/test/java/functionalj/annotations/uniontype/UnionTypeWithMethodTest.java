package functionalj.annotations.uniontype;

import static functionalj.annotations.uniontype.MayBe.Just;
import static functionalj.annotations.uniontype.MayBe.None;
import static functionalj.annotations.uniontype.UnionTypes.Switch;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.annotations.UnionType;

@SuppressWarnings("javadoc")
public class UnionTypeWithMethodTest {
    
    @UnionType(name="MayBe")
    public interface MayBeSpec<T> {
        void None();
        void Just(T value);
        
        default <T> boolean equals(MayBe<T> mayBe, Object obj) {
            if (obj == null)
                return mayBe.isNone();
            return mayBe.toString().equals(obj.toString());
        }
        default <T> int hashCode(MayBe<T> mayBe) {
            if (mayBe.isNone())
                return 0;
            return mayBe.toString().hashCode();
        }
        default <T> String toString(MayBe<T> mayBe) {
            return Switch(mayBe)
                .none("None")
                .just(m -> "Just:" + m.value());
        }
        default <T> boolean isPresent(MayBe<T> mayBe2) {
            return mayBe2.isJust();
        }
        static <T> MayBe<T> valueFor(T value) {
            return (value == null)
                    ? MayBe.None()
                    : MayBe.Just(value);
        }
    }
    
    @Test
    public void testMethod() {
        // NOTE: Default implementation of equals for any Union type will return false if the parameter is null.
        //       This implementation accept null as equals to None.
        assertTrue  (None().equals(null));
        assertEquals("None", None().toString());
        assertEquals(0, None().hashCode());
        
        assertFalse (Just("Hey!").equals(null));
        assertEquals("Just:Hey!",            Just("Hey!").toString());
        assertEquals("Just:Hey!".hashCode(), Just("Hey!").hashCode());
    }
    @Test
    public void testPresent() {
        assertFalse(None().isPresent());
        assertTrue (Just("Hey!").isPresent());
    }
    @Test
    public void testValueFor() {
        assertFalse(MayBe.valueFor(null).isPresent());
        assertTrue (MayBe.valueFor("Hey!").isPresent());
    }
    
}
