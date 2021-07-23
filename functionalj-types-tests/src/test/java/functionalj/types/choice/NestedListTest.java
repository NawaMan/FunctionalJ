package functionalj.types.choice;

import java.util.List;

import org.junit.Test;

import functionalj.types.Choice;

public class NestedListTest {
    
    @Choice(specField = "spec")
    interface NestedCommandModel {
        void Rotate(int degree);
        void Move  (int distance);
        void Commands(List<NestedCommand> commands);
    }
    
    @Choice(specField = "spec")
    interface NestedCommandListModel {
        void Rotate(int degree);
        void Move  (int distance);
        void Commands(List<List<NestedCommand>> commands);
    }
    
//    
//    @Choice
//    interface NestedMapStringSpec {
//            String command();
//            List<Map<String, String>> commands();
//    }
    
    @Test
    public void test() {
        // Nothing to test programmatically here
    }
    
}
