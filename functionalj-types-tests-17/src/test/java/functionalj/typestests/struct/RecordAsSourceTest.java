package functionalj.typestests.struct;

import static functionalj.typestests.struct.Point.thePoint;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Struct;

public class RecordAsSourceTest {
    
    @Struct(name = "Point")
    static record PointSpec(int x, int y) {
        public static Point create(int x, int y) {
            return new Point(x, y);
        }
        public static double absolute(Point point, double factor) {
            return factor * Math.sqrt(point.x()*point.x() + point.y()*point.y());
        }
    }
    
    @Test
    public void test() {
        var point = new Point(3, 5);
        assertEquals(3, point.x);
        assertEquals(5, point.y);
//        assertEquals(5, point.absolute(5));
        assertEquals("Point[x: 3, y: 5]", point.toString());
        assertEquals("Point[x: 4, y: 5]", point.withX(4).toString());
        assertEquals("5", "" + point.withY(4).pipeTo(thePoint.x.square().plus(thePoint.y.square()).squareRoot().floorToInt()));
        
        System.out.println(new PointSpec(3, 4));
        System.out.println(new Point(3, 4));
        System.out.println(Point.create(3, 4));
    }
    
}
