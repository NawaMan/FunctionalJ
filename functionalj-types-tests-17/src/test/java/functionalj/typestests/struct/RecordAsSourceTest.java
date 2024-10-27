package functionalj.typestests.struct;

import static functionalj.typestests.TestHelper.assertAsString;
import static functionalj.typestests.struct.Dot.theDot;
import static functionalj.typestests.struct.Point.thePoint;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Self;
import functionalj.types.Struct;

public class RecordAsSourceTest {
    
    @Struct(name = "Point")
    static record PointSpec(int x, int y) {
        // Use `Self` as wrapper.  (It as needed for some older compiler).
        public static Self create(int x, int y) {
            return functionalj.types.Self.wrap(new Point(x, y));
        }
        public static double absolute(Point point, double factor) {
            return factor * Math.sqrt(point.x()*point.x() + point.y()*point.y());
        }
    }
    
    @Struct(name = "Dot")
    static record DotSpec(int x, int y) {
        // Use the generate class directly in the signature
        public static Dot create(int x, int y) {
            return new Dot(x, y);
        }
//        public static double absolute(Dot dot, double factor) {
//            return factor * Math.sqrt(point.x()*point.x() + point.y()*point.y());
//        }
    }
    
    @Test
    public void testPoint() {
        var point = new Point(3, 5);
        assertEquals(3, point.x);
        assertEquals(5, point.y);
        assertAsString("Point[x=3, y=5]", point);
        assertAsString("Point[x=4, y=5]", point.withX(4));
        assertEquals(5, point.withY(4).pipeTo(thePoint.x.square().plus(thePoint.y.square()).squareRoot().floorToInt()).intValue());
        
        assertAsString("PointSpec[x=3, y=4]", new PointSpec(3, 4));
        assertAsString("Point[x=3, y=4]",   new Point(3, 4));
        assertAsString("Point[x=3, y=4]",   Point.create(3, 4));
    }
    
    @Test
    public void testDot() {
        var dot = new Dot(3, 5);
        assertEquals(3, dot.x);
        assertEquals(5, dot.y);
        assertAsString("Dot[x=3, y=5]", dot);
        assertAsString("Dot[x=4, y=5]", dot.withX(4));
        assertEquals(5, dot.withY(4).pipeTo(theDot.x.square().plus(theDot.y.square()).squareRoot().floorToInt()).intValue());
        
        assertAsString("DotSpec[x=3, y=4]", new DotSpec(3, 4));
        assertAsString("Dot[x=3, y=4]",   new Dot(3, 4));
        assertAsString("Dot[x=3, y=4]",   Dot.create(3, 4));
    }
    
}
