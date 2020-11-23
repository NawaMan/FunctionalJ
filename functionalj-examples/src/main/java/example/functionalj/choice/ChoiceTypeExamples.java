// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package example.functionalj.choice;

import static example.functionalj.choice.LoginStatus.Login.theLogin;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.types.Choice;


public class ChoiceTypeExamples {
    
    @Choice
    interface UpOrDownSpec {
        void Up();
        void Down();
    }
    
    @Choice
    interface LoginStatusSpec {
        void Login(String userName);
        void Logout();
    }
    
    @Test
    public void example01_Choices() {
        UpOrDown direction1 = UpOrDown.up;
        UpOrDown direction2 = UpOrDown.down;
        assertEquals("Up",   direction1.toString());
        assertEquals("Down", direction2.toString());
    }
    
    @Test
    public void example02_Payload() {
        LoginStatus status1 = LoginStatus.Login("root");
        LoginStatus status2 = LoginStatus.Logout();
        assertEquals("Login(root)", status1.toString());
        assertEquals("Logout",      status2.toString());
    }
    
    @Test
    public void example03_isXXX() {
        LoginStatus status1 = LoginStatus.Login("root");
        LoginStatus status2 = LoginStatus.Logout();
        assertTrue (status1.isLogin());
        assertFalse(status1.isLogout());
        assertFalse(status2.isLogin());
        assertTrue (status2.isLogout());
    }
    
    @Test
    public void example04_asXXX() {
        LoginStatus status1 = LoginStatus.Login("root");
        LoginStatus status2 = LoginStatus.Logout();
        assertEquals("Login(root)", status1.asLogin().map(String::valueOf).orElse("Not login"));
        assertEquals("Not login",   status2.asLogin().map(String::valueOf).orElse("Not login"));
    }
    
    @Test
    public void example05_ifXXX() {
        var output = new AtomicReference<String>();
        LoginStatus status = LoginStatus.Login("root");
        status
            .ifLogin(s -> output.set("User: " + s.userName()))
            .ifLogout(()->output.set("User: guess"));
        assertEquals("User: root", output.toString());
    }
    
    @Test
    public void example06_PatternMatching() {
        var f = Func.f((LoginStatus status) -> {
            return status.match()
                    .login (s -> "User: " + s.userName()) 
                    .logout("Guess");
        });
        
        LoginStatus status1 = LoginStatus.Login("root");
        LoginStatus status2 = LoginStatus.Logout();
        
        assertEquals("User: root", f.apply(status1));
        assertEquals("Guess",      f.apply(status2));
    }
    
    @Test
    public void example07_PatternMatchingWithPayLoad() {
        var moderators = asList("Jack", "John");
        var f = Func.f((LoginStatus status) -> {
            return status.match()
                    .login (theLogin.userName.thatEquals("root"),   "Administrator")
                    .login (theLogin.userName.thatIsIn(moderators), "Moderator")
                    .login (s ->                                    "User: " + s.userName())
                    .logout(                                        "Guess");
        });
        
        LoginStatus status1 = LoginStatus.Login("root");
        LoginStatus status2 = LoginStatus.Login("Jack");
        LoginStatus status3 = LoginStatus.Logout();
        
        assertEquals("Administrator", f.apply(status1));
        assertEquals("Moderator",     f.apply(status2));
        assertEquals("Guess",         f.apply(status3));
    }
    
}
