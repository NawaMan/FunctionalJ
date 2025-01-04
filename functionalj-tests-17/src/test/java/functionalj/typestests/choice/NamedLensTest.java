// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
