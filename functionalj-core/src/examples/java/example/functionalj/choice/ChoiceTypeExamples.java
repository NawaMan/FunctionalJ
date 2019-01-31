package example.functionalj.choice;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.types.Choice;
import functionalj.function.Func;
import lombok.val;

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
        val output = new AtomicReference<String>();
        LoginStatus status = LoginStatus.Login("root");
        status
            .ifLogin(s -> output.set("User: " + s.userName()))
            .ifLogout(()->output.set("User: guess"));
        assertEquals("User: root", output.toString());
    }
    
    @Test
    public void example06_PatternMatching() {
        val f = Func.f((LoginStatus status) -> {
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
        val moderators = asList("Jack", "John");
        val f = Func.f((LoginStatus status) -> {
            return status.match()
                    .loginOf("root",               "Administrator")
                    .loginOf(moderators::contains, "Moderator")
                    .login  (s ->                  "User: " + s.userName())
                    .logout (                      "Guess");
        });
        
        LoginStatus status1 = LoginStatus.Login("root");
        LoginStatus status2 = LoginStatus.Login("Jack");
        LoginStatus status3 = LoginStatus.Logout();
        
        assertEquals("Administrator", f.apply(status1));
        assertEquals("Moderator",     f.apply(status2));
        assertEquals("Guess",         f.apply(status3));
    }
    
}
