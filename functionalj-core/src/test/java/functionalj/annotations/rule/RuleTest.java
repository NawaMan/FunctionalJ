package functionalj.annotations.rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.Rule;
import functionalj.annotations.Struct;
import functionalj.result.ValidationException;

public class RuleTest {
    
    @Rule("Int value must be positive: %s")
    static boolean IntPositive(int intValue) { return intValue >= 0; }
    
    @Rule static String Int255(int intValue) {
        return ((intValue >= 0) && (intValue <= 255))
                ? null
                : ("Int value must be between 0 to 255 inclusively: " + intValue);
    }
    @Rule static String Email(String emailStr) {
        return (emailStr.matches("^[^@]+@[^.]+\\..{1,3}$"))
                ? null
                : ("Not a valid email address: " + emailStr);
    }
    @Rule(extendRule="functionalj.annotations.rule.Email")
    static ValidationException VerifiedEmail(String email) {
        if ("nawa@nawaman.net".equals(email))
            return null;
        
        return new ValidationException("Unverified email: " + email);
    }
    // TODO - Unable to get the fully qualified name for a class being generated. So gotta put the full things here.
    @Rule
    static String UsableCar(functionalj.annotations.struct.Car car) {
        return null;
    }
    
    @Struct(specField = "spec")
    void TestStruct(IntPositive positveInt) {
        
    }
    
    @Test
    public void testIntPositive() {
        assertEquals("Result:{ Value: 15 }", "" + IntPositive.from(15));
        assertEquals("Result:{ Value: 0 }",  "" + IntPositive.from( 0));
        assertEquals(
                "Result:{ Invalid: Int value must be positive: -5 }",
                "" + IntPositive.from(-5));
    }
    @Test
    public void testInt255() {
        assertEquals("Result:{ Value: 15 }",  "" + Int255.from(15));
        assertEquals("Result:{ Value: 0 }",   "" + Int255.from( 0));
        assertEquals("Result:{ Invalid: Int value must be between 0 to 255 inclusively: 260 }", "" + Int255.from(260));
        assertEquals("Result:{ Invalid: Int value must be between 0 to 255 inclusively: -5 }",  "" + Int255.from(-5));
    }
    @Test
    public void testEmail() {
        assertEquals("Result:{ Value: nawa@nawaman.net }",   "" + Email.from("nawa@nawaman.net"));
        assertEquals("Result:{ Value: nawaman@gmail.com }",  "" + Email.from("nawaman@gmail.com"));
        assertEquals("Result:{ Invalid: Not a valid email address: nawaman.net }",  "" + Email.from("nawaman.net"));
    }
    @Test
    public void testVerifiedEmail() {
        assertEquals("Result:{ Value: nawa@nawaman.net }",                             "" + VerifiedEmail.from("nawa@nawaman.net"));
        assertEquals("Result:{ Invalid: Not a valid email address: nawanawaman.net }", "" + VerifiedEmail.from("nawanawaman.net"));
        assertEquals("Result:{ Invalid: Unverified email: nawaman@gmail.com }",        "" + VerifiedEmail.from("nawaman@gmail.com"));
    }

}
