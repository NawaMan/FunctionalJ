package functionalj.typestests.struct;

import static functionalj.typestests.struct.Point.thePoint;
import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.types.Struct;
import functionalj.types.choice.Self;
import functionalj.typestests.choice.LinkedList;

public class RecordAsSource {
    
    @Struct(name = "Point")
    static record PointSpec(int x, int y) {
        static Self create(int x, int y) {
            return Self.wrap(new Point(x, y));
        }
        double absolute(Self self) {
            var point = (Point)Self.unwrap(self);
            return Math.sqrt(point.x()*point.x() + point.y()*point.y());
        }
    }
    
    @Test
    public void test() {
        var point = new Point(3, 5);
        assertEquals(3, point.x);
        assertEquals(5, point.y);
//        assertEquals(5, point.absolute());
        assertEquals("Point[x: 3, y: 5]", point.toString());
        assertEquals("Point[x: 4, y: 5]", point.withX(4).toString());
        assertEquals("5", "" + point.withY(4).pipeTo(thePoint.x.square().plus(thePoint.y.square()).squareRoot().floorToInt()));
    }
    
}
