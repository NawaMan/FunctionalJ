package functionalj.typestests.struct;

import static functionalj.types.DefaultValue.NULL;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.types.DefaultTo;
import functionalj.types.Nullable;
import functionalj.types.Struct;

/**
 * This test aims to specifically test the generation of struct into record.
 * It might repeat with other tests but that is ok.
 **/
public class GenerateStructAsRecordTest {
    
    @Struct
    static boolean CarAsRecordFromMethodSpec(String make, int year, @Nullable @DefaultTo(NULL) String color) {
        return true;
    }
    
    @Struct
    static interface CarAsRecordFromIntefaceSpec {
        String make();
        int year();
        @Nullable @DefaultTo(NULL) String color();
    }
    
    @Struct(coupleWithDefinition = false)
    static abstract class CarAsRecordFromClassSpec {
        public abstract String make();
        public abstract int year();
        public abstract @Nullable @DefaultTo(NULL) String color();
    }
    
    @Struct
    static record CarAsRecordFromRecordSpec(String make, int year, @Nullable @DefaultTo(NULL) String color) {
    }
    
    @Test
    public void testRecrod() {
        assertTrue(CarAsRecordFromMethod.class.isRecord());
        assertTrue(CarAsRecordFromInteface.class.isRecord());
        assertTrue(CarAsRecordFromClass.class.isRecord());
        assertTrue(CarAsRecordFromRecord.class.isRecord());
    }
    
}
