package functionalj.annotations.rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.Rule;
import functionalj.result.ValidationException;

public class RuleTest {
    
    @Rule
    static boolean IntPositive(int intValue) {
        return intValue >= 0;
    }
    @Rule
    static String Int255(int intValue) {
        return ((intValue >= 0) && (intValue <= 255))
                ? null
                : ("Int value must be between 0 to 255 inclusively: " + intValue);
    }
    @Rule
    static String Email(String emailStr) {
        return (emailStr.matches("^[^@]+@[^.]+\\..{1,3}$"))
                ? null
                : ("Not a valid email address: " + emailStr);
    }
    // Umm - It should be good to allow auto-unwrap 
    @Rule
    static ValidationException VerifiedEmail(functionalj.annotations.rule.Email email) {
        if ("nawa@nawaman.net".equals(email.value()))
            return null;
        
        return new ValidationException("Unverified email: " + email.value());
    }
    // TODO - Unable to get the fully qualified name for a class being generated. So gotta put the full things here.
    @Rule
    static String UsableCar(functionalj.annotations.struct.Car car) {
        return null;
    }
    
    @Test
    public void testIntPositive() {
        assertEquals("Result:{ Value: 15 }", "" + IntPositive.from(15));
        assertEquals("Result:{ Value: 0 }",  "" + IntPositive.from( 0));
        assertEquals("Result:{ Exception: functionalj.result.ValidationException: IntPositive }", "" + IntPositive.from(-5));
    }
    @Test
    public void testInt255() {
        assertEquals("Result:{ Value: 15 }",  "" + Int255.from(15));
        assertEquals("Result:{ Value: 0 }",   "" + Int255.from( 0));
        assertEquals("Result:{ Exception: functionalj.result.ValidationException: Int value must be between 0 to 255 inclusively: 260 }", "" + Int255.from(260));
        assertEquals("Result:{ Exception: functionalj.result.ValidationException: Int value must be between 0 to 255 inclusively: -5 }", "" + Int255.from(-5));
    }
    @Test
    public void testEmail() {
        assertEquals("Result:{ Value: nawa@nawaman.net }",   "" + Email.from("nawa@nawaman.net"));
        assertEquals("Result:{ Value: nawaman@gmail.com }",  "" + Email.from("nawaman@gmail.com"));
        assertEquals("Result:{ Exception: functionalj.result.ValidationException: Not a valid email address: nawaman.net }",  "" + Email.from("nawaman.net"));
    }
    @Test
    public void testVerifiedEmail() {
        assertEquals("Result:{ Value: Result:{ Value: nawa@nawaman.net } }",                                               "" + VerifiedEmail.from(Email.from("nawa@nawaman.net")));
        assertEquals("Result:{ Exception: functionalj.result.ValidationException: Unverified email: nawaman@gmail.com }",  "" + VerifiedEmail.from(Email.from("nawaman@gmail.com")));
    }
}
