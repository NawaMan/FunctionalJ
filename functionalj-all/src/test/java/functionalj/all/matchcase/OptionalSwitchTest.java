package functionalj.all.matchcase;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import functionalj.all.matchcase.Case;
import functionalj.function.Func;
import lombok.val;

public class OptionalSwitchTest {
    
    @Test
    public void testOptionalSwitch() {
        val present = Optional.of("Hello");
        val empty   = Optional.empty();
        
        assertEquals("The data: Hello", 
                Case.of(present)
                .present(data -> "The data: " + data)
                .empty("Empty"));
        assertEquals("Empty", 
                Case.of(empty)
                .present(data -> "The data: " + data)
                .empty("Empty"));
        
        // Swap
        assertEquals("The data: Hello", 
                Case.of(present)
                .empty("Empty")
                .present(data -> "The data: " + data));
        assertEquals("Empty", 
                Case.of(empty)
                .empty("Empty")
                .present(data -> "The data: " + data));
    }
    
    @Test
    public void testIncomplete() {
        val present = Optional.of("Hello");
        assertTrue((Case.of(present).empty("Empty") + "").startsWith("functionalj.all.matchcase.OptionalSwitchCase$OptionalSwitchWithoutPresent"));
    }
    
    @Test
    public void testOptionalSwitch_Conditional() {
        val hello = Optional.of("Hello");
        val yo    = Optional.of("Yo");
        val empty = Optional.<String>empty();
        
        val case1 = Func.f((Optional<String> o) -> 
                Case.of(o)
                .present(str -> str.startsWith("H"), str -> "The 'start with H' str: " + str)
                .present(                            str -> "The str: " + str)
                .empty  ("Empty"));
        
        assertEquals("The 'start with H' str: Hello", case1.apply(hello));
        assertEquals("The str: Yo",                   case1.apply(yo));
        assertEquals("Empty",                         case1.apply(empty));
        
        
        val case2 = Func.f((Optional<String> o) -> 
                Case.of(o)
                .empty  ("Empty")
                .present(str -> str.startsWith("H"), str -> "The 'start with H' str: " + str)
                .present(                            str -> "The str: " + str));
        
        assertEquals("The 'start with H' str: Hello", case2.apply(hello));
        assertEquals("The str: Yo",                   case2.apply(yo));
        assertEquals("Empty",                         case2.apply(empty));
    }
}
