package functionalj.typestests.struct;

import static functionalj.typestests.struct.Point.thePoint;
import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.types.Struct;

public class RecordAsSource {
    
    @Struct(name = "Point")
    static record PointSpec(int x, int y) {
        
    }
    
    @Test
    public void test() {
        var point = new Point(3, 5);
        assertEquals(3, point.x);
        assertEquals(5, point.y);
        assertEquals("Point[x: 3, y: 5]", point.toString());
        assertEquals("Point[x: 4, y: 5]", point.withX(4).toString());
        assertEquals("5", "" + point.withY(4).pipeTo(thePoint.x.square().plus(thePoint.y.square()).squareRoot().floorToInt()));
    }
    
}
