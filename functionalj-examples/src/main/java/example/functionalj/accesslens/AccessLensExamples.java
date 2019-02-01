package example.functionalj.accesslens;

import static example.functionalj.accesslens.User.theUser;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import example.functionalj.accesslens.User;
import functionalj.types.Struct;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.StringAccess;
import functionalj.lens.lenses.StringLens;
import functionalj.list.FuncList;
import lombok.val;

public class AccessLensExamples {
    
    @Struct
    void UserSpec(String name) {}
    
    @Test
    public void example01_Access() {
        User user1 = new User("John");
        
        StringAccess<User> userName = User::name;
        
        assertEquals("John", userName.apply(user1));
    }
    
    @Test
    public void example02_Compose() {
        User user1 = new User("John");
        
        StringAccess<User>  userName       = User::name;
        IntegerAccess<User> userNameLength = userName.length();
        
        assertEquals("John", userName.apply(user1));
        assertEquals(4, (int)userNameLength.apply(user1));
    }
    
    @Test
    public void example03_Compare() {
        User user1 = new User("John");
        
        StringAccess<User> userName = User::name;
        
        assertFalse(userName.length().thatGreaterThan(4).apply(user1));
        assertTrue (userName.length().thatGreaterThan(4).apply(new User("NawaMan")));
    }
    
    @Test
    public void example04_Stream() {
        val users = FuncList.of(
                    new User("John"),
                    new User("NawaMan"),
                    new User("Jack")
                );
        
        StringAccess<User> userName = User::name;
        
        val usersWithLongName = users.filter(userName.length().thatGreaterThan(4));
        assertEquals("[User[name: NawaMan]]", usersWithLongName.toString());
    }
    
    @Test
    public void example05_CommonAccess() {
        val names = FuncList.of("John", "David", "Adam", "Ben");
        
        val shortNames = names.filter(theString.length().thatLessThan(4));
        val longNames  = names.filter($S.length().thatGreaterThan(4));
        
        assertEquals("[Ben]",   shortNames.toString());
        assertEquals("[David]", longNames.toString());
    }
    
    @Test
    public void example06_LensRead() {
        StringLens<User> userName = theUser.name;
        
        assertEquals("John", userName.apply(new User("John")));    // Read.
    }
    
    @Test
    public void example07_LensChange() {
        StringLens<User> userName = theUser.name;
        
        User user1 = new User("John");
        User user2 = userName.apply(user1, "Jack");    // Write.
        assertEquals("User[name: John]", user1.toString());
        assertEquals("User[name: Jack]", user2.toString());
    }
    
}
