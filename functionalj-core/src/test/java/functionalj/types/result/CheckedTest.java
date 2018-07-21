package functionalj.types.result;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import functionalj.lens.Access;
import lombok.val;

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
        NonNullNonEmptyString str1 = Result.of("Test").asCheckedValueOF(NonNullNonEmptyString::new);
        NonNullNonEmptyString str2 = Result.of("Test").filter(s -> false).asCheckedValueOF(NonNullNonEmptyString::new);
        assertTrue (str1.isValid());
        assertFalse(str2.isValid());
    }
    
    private Result<Integer> someWork(NonNullNonEmptyString str) {
        return str.map(theString.length());
    }
    private Result<Integer> someWork(String str) {
        return someWork(NonNullNonEmptyString.valueOf(str));
    }
    
    @Test
    public void testParam() {
        assertEquals("Result:{ Value: 5 }", "" + someWork(NonNullNonEmptyString.valueOf("Hello")));
        assertEquals("Result:{ Value: 0 }", "" + someWork(NonNullNonEmptyString.valueOf(null)));
        
        assertEquals("Result:{ Value: 5 }", "" + someWork("Hello"));
        assertEquals("Result:{ Value: 0 }", "" + someWork((String)null));
    }
    
}
