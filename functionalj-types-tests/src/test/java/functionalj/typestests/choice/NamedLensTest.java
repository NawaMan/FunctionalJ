package functionalj.typestests.choice;

import static functionalj.typestests.TestHelper.assertAsString;
import static functionalj.typestests.choice.Command.theCommand;
import static functionalj.typestests.choice.Command.Move.theMove;
import static functionalj.typestests.choice.Command.Rotate.theRotate;
import org.junit.Test;
import lombok.val;

public class NamedLensTest {

    // Use choice from ChoiceToMapTest
    @Test
    public void testChoice() {
        val move = Command.Move(42);
        val rotate = Command.Rotate(45);
        assertAsString("Move(42)", move);
        assertAsString("Rotate(45)", rotate);
        assertAsString("theCommand", theCommand);
        assertAsString("theMove", theMove);
        assertAsString("theRotate", theRotate);
        assertAsString("theMove.distance", theMove.distance);
        assertAsString("theRotate.degree", theRotate.degree);
        assertAsString("theCommand.asMove", theCommand.asMove);
        assertAsString("theCommand.asRotate", theCommand.asRotate);
        assertAsString("theCommand.asMove.value.distance", theCommand.asMove.get().distance);
        assertAsString("theCommand.asRotate.value.degree", theCommand.asRotate.value().degree);
        assertAsString("Move(24)", theCommand.changeTo(Command.Move(24)).apply(move));
        assertAsString("Rotate(90)", theCommand.changeTo(Command.Rotate(90)).apply(move));
        assertAsString("Move(24)", theCommand.asMove.value().distance.changeTo(24).apply(move));
        // NPE - rightly so but can we do better
        // assertAsString("Move(24)", theCommand.asRotate.value.degree.changeTo(90).apply(move));
        // NPE - rightly so but can we do better
        // assertAsString("Move(24)", theCommand.asMove.value.distance.changeTo(24).apply(rotate));
        assertAsString("Rotate(90)", theCommand.asRotate.value().degree.changeTo(90).apply(rotate));
    }
}
