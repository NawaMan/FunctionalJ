// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.typestests.rule;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.result.ValidationException;
import functionalj.types.Rule;
import functionalj.types.Struct;

public class RuleTest {

    @Rule("Int value must be positive: %s")
    static boolean IntPositive(int intValue) {
        return intValue >= 0;
    }

    @Rule
    static String Int255(int intValue) {
        return ((intValue >= 0) && (intValue <= 255)) ? null : ("Int value must be between 0 to 255 inclusively: " + intValue);
    }

    @Rule
    static String Email(String emailStr) {
        return (emailStr.matches("^[^@]+@[^.]+\\..{1,3}$")) ? null : ("Not a valid email address: " + emailStr);
    }

    @Rule(extendRule = "functionalj.typestests.rule.Email")
    static ValidationException VerifiedEmail(String email) {
        if ("name@email.com".equals(email))
            return null;
        return new ValidationException("Unverified email: " + email);
    }

    // TODO - Unable to get the fully qualified name for a class being generated. So gotta put the full things here.
    @Rule
    static String UsableCar(functionalj.typestests.struct.Car car) {
        return null;
    }

    @Struct
    void TestStruct(IntPositive positveInt) {
    }

    @Test
    public void testIntPositive() {
        assertEquals("IntPositive:{ Value: 15 }", "" + IntPositive.from(15));
        assertEquals("IntPositive:{ Value: 0 }", "" + IntPositive.from(0));
        assertEquals("IntPositive:{ Invalid: Int value must be positive: -5 }", "" + IntPositive.from(-5));
    }

    @Test
    public void testInt255() {
        assertEquals("Int255:{ Value: 15 }", "" + Int255.from(15));
        assertEquals("Int255:{ Value: 0 }", "" + Int255.from(0));
        assertEquals("Int255:{ Invalid: Int value must be between 0 to 255 inclusively: 260 }", "" + Int255.from(260));
        assertEquals("Int255:{ Invalid: Int value must be between 0 to 255 inclusively: -5 }", "" + Int255.from(-5));
    }

    @Test
    public void testEmail() {
        assertEquals("Email:{ Value: name@email.com }", "" + Email.from("name@email.com"));
        assertEquals("Email:{ Invalid: Not a valid email address: name_email.com }", "" + Email.from("name_email.com"));
    }

    @Test
    public void testVerifiedEmail() {
        assertEquals("VerifiedEmail:{ Value: name@email.com }", "" + VerifiedEmail.from("name@email.com"));
        assertEquals("VerifiedEmail:{ Invalid: Not a valid email address: name_email.com }", "" + VerifiedEmail.from("name_email.com"));
        assertEquals("VerifiedEmail:{ Invalid: Unverified email: name@email.net }", "" + VerifiedEmail.from("name@email.net"));
    }
}
