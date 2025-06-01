package functionalj.typestests.struct;

import static functionalj.TestHelper.assertAsString;
import static functionalj.types.DefaultValue.NULL;

import org.junit.Test;

import functionalj.types.DefaultTo;
import functionalj.types.Nullable;
import functionalj.types.Struct;

public class DeconstructuringTest {
	
    @Struct
    static boolean TruckSpec(String make, int year, @Nullable @DefaultTo(NULL) String color) {
        return true;
    }
    
    @Test
    public void testDeconstructuring() {
        var truck = new Truck("Toyota", 2025);
        if (truck instanceof Truck(String make, int year, String color)) {
            assertAsString("Toyota", make);
            assertAsString("2025",   year);
            assertAsString(null,     color);
        }
    }
    
}
