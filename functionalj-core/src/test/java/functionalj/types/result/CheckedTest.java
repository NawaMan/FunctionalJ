package functionalj.types.result;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Test;

public class CheckedTest {
    
    public static final class NonNullNonEmptyString extends Checked<String, Predicate<String>> {
        
        private NonNullNonEmptyString(String value, Exception exception) {
            super(value, exception);
        }
        
        public static NonNullNonEmptyString valueOf(String value) {
            return Checked.valueOf(value, NonNullNonEmptyString::new);
        }

        @Override
        protected Predicate<String> getChecker() {
            return str -> !str.isEmpty();
        }
    }
    
    @Test
    public void testValid() {
        assertTrue( NonNullNonEmptyString.valueOf("Hello")     .isValid());
        assertFalse(NonNullNonEmptyString.valueOf("")          .isValid());
        assertFalse(NonNullNonEmptyString.valueOf((String)null).isValid());
    }
    
    public static final class NonEmptyString extends Checked.NullSafe<String, Predicate<String>> {
        
        private NonEmptyString(String value, Exception exception) {
            super(value, exception);
        }
        
        public static NonEmptyString of(String value) {
            return Checked.valueOf(value, NonEmptyString::new);
        }

        @Override
        protected Predicate<String> getChecker() {
            return str -> (str == null) || !str.isEmpty();
        }
    }
    
    @Test
    public void testValidNullSafe() {
        assertTrue (NonEmptyString.of("Hello")     .isValid());
        assertFalse(NonEmptyString.of("")          .isValid());
        assertTrue (NonEmptyString.of((String)null).isValid());
    }
    
    @Test
    public void testChecked() {
        NonNullNonEmptyString str1 = ImmutableResult.of("Test").toChecked(NonNullNonEmptyString::new);
        NonNullNonEmptyString str2 = ImmutableResult.of("Test").filter(s -> false).toChecked(NonNullNonEmptyString::new);
        assertTrue (str1.isValid());
        assertFalse(str2.isValid());
    }
    
}
