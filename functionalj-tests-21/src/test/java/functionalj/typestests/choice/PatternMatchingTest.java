package functionalj.typestests.choice;

import static functionalj.TestHelper.assertAsString;
import static functionalj.typestests.choice.MyCommand.Commands;
import static functionalj.typestests.choice.MyCommand.Move;
import static functionalj.typestests.choice.MyCommand.Rotate;
import static java.util.Arrays.asList;

import java.util.List;

import org.junit.Test;

import functionalj.types.Choice;

public class PatternMatchingTest {
    
    @Choice
    interface MyCommandModel {
        
        void Rotate(int degree);
        
        void Move(int distance);
        
        void Commands(List<MyCommand> commands);
    }
    
    @Test
    public void testPatternMatching() {
        assertAsString("Rotate 45",                       convertToString(Rotate(45)));
        assertAsString("Move 10",                         convertToString(Move(10)));
        assertAsString("Commands [Rotate(45), Move(10)]", convertToString(Commands(asList(Rotate(45), Move(10)))));
    }
    
    private String convertToString(MyCommand myCommand) {
        var str = switch (myCommand) {
            case Rotate   r -> "Rotate "   + r.degree();
            case Move     m -> "Move "     + m.distance();
            case Commands c -> "Commands " + c.commands();
            default -> throw new IllegalArgumentException("Unexpected value: " + myCommand);
        };
        return str;
    }
    
}
