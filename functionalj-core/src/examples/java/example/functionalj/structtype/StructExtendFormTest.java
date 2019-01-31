package example.functionalj.structtype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Struct;
import lombok.val;

public class StructExtendFormTest {
    
    public static abstract class Greeter {
        
        public abstract String greetWord();
        
        public String greeting(String name) {
            return greetWord() + " " + name + "!";
        }
    }
    
    @Struct
    static abstract class FriendlyGuySpec extends Greeter {
        public abstract String greetWord();
        public void shakeHand() {}
    }
    
    @Test
    public void example01_Extends() {
        val fiendlyGuy = new FriendlyGuy("Hi");
        assertEquals("Hi Bruce Wayne!", fiendlyGuy.greeting("Bruce Wayne"));
    }
    
}
