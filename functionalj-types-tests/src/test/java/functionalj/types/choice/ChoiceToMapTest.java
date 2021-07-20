package functionalj.types.choice;

import static functionalj.types.choice.Command.Move;
import static functionalj.types.choice.Command.Rotate;
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
        void Move  (int distance);
    }
    
    @Struct
    void TimedActionModel(
            LocalDateTime time,
            FuncList<Command> commands) {}
    
    @Struct
    void MapTimedActionModel(
            LocalDateTime time,
            FuncMap<String, Command> commands) {}
    
    @Struct
    void DeepTimedAction(
            LocalDateTime time,
            List<List<Command>> commands) {}
    
    @Test
    public void testToMap() {
        assertEquals("{degree=5, type=Rotate}", new TreeMap<>(Rotate(5).__toMap()).toString());
        assertEquals("{distance=7, type=Move}", new TreeMap<>(Move  (7).__toMap()).toString());
        assertEquals(
                "{time=2019-06-10T23:08:34, commands=[{degree=5, type=Rotate}, {distance=7, type=Move}]}",
                new TimedAction(
                        LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34),
                        FuncList.of(Rotate(5), Move(7))).__toMap().toString());
    }
    
    @Test
    public void testFromMap() {
        try {
            val timedAction1
                    = new TimedAction(
                    LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34),
                    FuncList.of(Rotate(5), Move(7)));
            val map = timedAction1.__toMap();
            val timedAction2 = IData.$utils.fromMap(map, TimedAction.class);
            assertEquals(timedAction1, timedAction2);
        } catch (Exception e) {
            
        }
    }
    @Test
    public void testFromMap_map() {
        val mapTimedAction1
        = new MapTimedAction(
                LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34),
                FuncMap.of(
                        "One", Rotate(5),
                        "Two", Move(7)));
        val map = mapTimedAction1.__toMap();
        val mapTimedAction2 = IData.$utils.fromMap(map, MapTimedAction.class);
        assertEquals(mapTimedAction1, mapTimedAction2);
    }
    
    @Test
    public void testFromMap_deepList() {
        val deepTimedAction1
                = new DeepTimedAction(
                        LocalDateTime.of(2019, Month.JUNE, 10, 23, 8, 34),
                        asList(
                            asList(Rotate(5),  Move(7)),
                            asList(Rotate(15), Move(17))
                        ));
        val map = deepTimedAction1.__toMap();
        
        val deepTimedAction2 = DeepTimedAction.fromMap(map);
        System.out.println("timedAction1: " + deepTimedAction1);
        System.out.println("timedAction2: " + deepTimedAction2);
        assertEquals(deepTimedAction1, deepTimedAction2);
    }
    
}
