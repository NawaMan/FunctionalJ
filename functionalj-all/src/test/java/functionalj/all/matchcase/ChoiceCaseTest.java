package functionalj.all.matchcase;

import static org.junit.Assert.*;

import static functionalj.all.matchcase.MightBe.Just;
import static functionalj.all.matchcase.MightBe.None;

import org.junit.Test;

import functionalj.types.Choice;
import lombok.val;

public class ChoiceCaseTest {
    
    @Choice
    interface MightBeSpec<T> {
        void Just(T value);
        void None();
    }
    
    
    @Test
    public void testMightBe() {
        val just = Just("Hello");
        val none = None();
        
        assertEquals("The data: Just(Hello)", 
                Case.of(just)
                .just(data -> "The data: " + data)
                .none("None"));
        assertEquals("None", 
                Case.of(none)
                .just(data -> "The data: " + data)
                .none("None"));
    }

}
