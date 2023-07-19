package functionalj.typestests.struct;

import java.util.List;
import org.junit.Test;
import functionalj.types.Struct;

public class NestedListTest {
    
    @Struct
    void NestedListStringSpec(List<List<String>> commands) {
    }
    
    @Struct
    void NestedListNestedListSpec(List<List<NestedListNestedList>> commands) {
    }
    
    // @Struct
    // void NestedMapStringSpec(
    // List<Map<String, String>> commands) {}
    // 
    @Test
    public void test() {
        // Nothing to test programmatically here
    }
}
