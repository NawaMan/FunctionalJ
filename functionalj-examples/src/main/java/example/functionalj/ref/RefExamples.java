package example.functionalj.ref;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

import functionalj.ref.Ref;
import lombok.val;

public class RefExamples {
    
    static Ref<Function<String, String>> greeting = Ref.ofValue(RefExamples::defaultGreeting);
    static Ref<Consumer<String>>         println  = Ref.ofValue(System.out ::println);
    
    
    private static String defaultGreeting(String name) {
        return String.format("Hello %s!!", name);
    }
    
    public static void greet(String name) {
        var greetingString = greeting.value().apply(name);
        println.value().accept(greetingString);
    }
    
    public static void main(String[] args) {
        // Production
        greet("Jack");
    }
    
    @Test
    public void testDefaultMessage() {
        var logs = new ArrayList<String>();
        With(println.butWith(logs::add))
        .run(()-> {
            greet("Jack");
        });
        assertEquals("[Hello Jack!!]", logs.toString());
    }
    
    @Test
    public void testCustomMessage() {
        var logs = new ArrayList<String>();
        With(println .butWith(logs::add),
             greeting.butWith(name -> "What's up " + name + "?"))
        .run(()-> {
            greet("Jack");
        });
        assertEquals("[What's up Jack?]", logs.toString());
    }
    
}
