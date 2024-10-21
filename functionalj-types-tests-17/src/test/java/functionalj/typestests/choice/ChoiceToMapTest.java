// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.typestests.choice.Command.Move;
import static functionalj.typestests.choice.Command.Rotate;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.TreeMap;
import org.junit.Test;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.types.Choice;
import functionalj.types.IData;
import functionalj.types.Struct;
import lombok.val;

public class ChoiceToMapTest {
    
    @Choice(tagMapKeyName = "type")
    interface CommandModel {
        
        void Rotate(int degree);
        
        void Move(int distance);
    }
    
    @Struct
    void TimedActionModel(LocalDateTime time, FuncList<Command> commands) {
    }
    
    @Struct
    void MapTimedActionModel(LocalDateTime time, FuncMap<String, Command> commands) {
    }
    
    @Struct
    void DeepTimedAction(LocalDateTime time, List<List<Command>> commands) {
    }
    
    @Test
    public void testToMap() {
        assertEquals("{degree=5, type=Rotate}", new TreeMap<>(Rotate(5).__toMap()).toString());
        assertEquals("{distance=7, type=Move}", new TreeMap<>(Move(7).__toMap()).toString());
        assertEquals("{time=2019-06-10T23:08:34, commands=[{degree=5, type=Rotate}, {distance=7, type=Move}]}", new TimedAction(LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34), FuncList.of(Rotate(5), Move(7))).__toMap().toString());
    }
    
    @Test
    public void testFromMap() {
        try {
            val timedAction1 = new TimedAction(LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34), FuncList.of(Rotate(5), Move(7)));
            val map = timedAction1.__toMap();
            val timedAction2 = IData.$utils.fromMap(map, TimedAction.class);
            assertEquals(timedAction1, timedAction2);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testFromMap_map() {
        val mapTimedAction1 = new MapTimedAction(LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34), FuncMap.of("One", Rotate(5), "Two", Move(7)));
        val map = mapTimedAction1.__toMap();
        val mapTimedAction2 = IData.$utils.fromMap(map, MapTimedAction.class);
        assertEquals(mapTimedAction1, mapTimedAction2);
    }
    
    @Test
    public void testFromMap_deepList() {
        val deepTimedAction1 = new DeepTimedAction(LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34), asList(asList(Rotate(5), Move(7)), asList(Rotate(15), Move(17))));
        val map = deepTimedAction1.__toMap();
        val deepTimedAction2 = DeepTimedAction.fromMap(map);
        assertEquals(deepTimedAction1, deepTimedAction2);
    }
}
